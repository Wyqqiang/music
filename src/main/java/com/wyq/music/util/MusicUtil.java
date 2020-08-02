package com.wyq.music.util;

import com.wyq.music.entity.Music;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.id3.AbstractID3v2Frame;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.ID3v23Frame;
import org.jaudiotagger.tag.id3.framebody.FrameBodyAPIC;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

public class MusicUtil {

    private static final String SONG_NAME_KEY = "TIT2";
    private static final String ARTIST_KEY = "TPE1";
    private static final String ALBUM_KEY = "TALB";
    //把歌曲放在list中，不用做数据库
    public static Set<Music> set=new HashSet<Music>();

    /**
     * 获取MP3封面图片
     * @param filepath
     * @return
     */
    public static byte[] getMP3Image(String filepath) {
        byte[] imageData = null;
        try {
            File file=new File(filepath);
            MP3File mp3file = new MP3File(file);
            AbstractID3v2Tag tag = mp3file.getID3v2Tag();
            AbstractID3v2Frame frame = (AbstractID3v2Frame) tag.getFrame("APIC");
            FrameBodyAPIC body = (FrameBodyAPIC) frame.getBody();
            imageData = body.getImageData();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return imageData;
    }

    /**
     *获取mp3图片并将其保存至指定路径下
     * @param mp3File mp3文件对象
     * @param mp3ImageSavePath mp3图片保存位置（默认mp3ImageSavePath +"\" mp3File文件名 +".jpg" ）
     * @param cover 是否覆盖已有图片
     * @return 生成图片全路径
     */
    public static String saveMP3Image(String mp3File, String mp3ImageSavePath, boolean cover) {
        //生成mp3图片路径
        String mp3FileLabel = getFileLabel(new File(mp3File).getName());
        String mp3ImageFullPath = mp3ImageSavePath + ("\\" + mp3FileLabel + ".jpg");

        //若为非覆盖模式，图片存在则直接返回（不再创建）
        if( !cover ) {
            File tempFile = new File(mp3ImageFullPath) ;
            if(tempFile.exists()) {
                return mp3ImageFullPath;
            }
        }

        //生成mp3存放目录
        File saveDirectory = new File(mp3ImageSavePath);
        saveDirectory.mkdirs();

        //获取mp3图片
        byte imageData[] = getMP3Image(mp3File);
        //若图片不存在，则直接返回null
        if (null == imageData || imageData.length == 0) {
            return null;
        }
        //保存mp3图片文件
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mp3ImageFullPath);
            fos.write(imageData);
            fos.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return mp3ImageFullPath;
    }
    /**
     * 仅返回文件名（不包含.类型）
     * @param fileName
     * @return
     */
    public static String getFileLabel(String fileName) {
        int indexOfDot = fileName.lastIndexOf(".");
        fileName = fileName.substring(0,(indexOfDot==-1?fileName.length():indexOfDot));
        return fileName;
    }

    /**
     * 通过歌曲文件地址, 获取歌曲信息
     *
     * @param filePath 歌曲文件地址
     * @return 歌曲信息
     * @throws Exception 可能抛出空指针异常
     */
    public static Music getMusicInfo(String filePath) throws Exception {

        Music music = null;

        try {

            MP3File mp3File = (MP3File) AudioFileIO.read(new File(filePath));

            MP3AudioHeader audioHeader = (MP3AudioHeader) mp3File.getAudioHeader();

            // 歌曲名称
            String songName = getInfoFromFrameMap(mp3File, SONG_NAME_KEY);
            // 歌手名称
            String artist = getInfoFromFrameMap(mp3File, ARTIST_KEY);
            // 歌曲专辑
            String album = getInfoFromFrameMap(mp3File, ALBUM_KEY);
            // 播放时长
            int duration = audioHeader.getTrackLength();
            String [] sonFile=filePath.split("\\\\");
            filePath=sonFile[sonFile.length-1];
            filePath = filePath.replaceAll(" ", "").replaceAll("&","");
            // 封装到music对象
            music = new Music(songName, artist, album, duration, filePath);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("文件读取失败！" + e);
        }

        return music;
    }

    /**
     * 通过键值,获取歌曲中对应的字段信息
     *
     * @param mp3File mp3音乐文件
     * @param key     键值
     * @return 歌曲信息
     * @throws Exception 可能抛出空指针异常
     */
    public static String getInfoFromFrameMap(MP3File mp3File, String key) throws Exception {
        ID3v23Frame frame = (ID3v23Frame) mp3File.getID3v2Tag().frameMap.get(key);
        return frame.getContent();
    }
    public static String getFileName(String agent, String filename) throws UnsupportedEncodingException {
        if (agent.contains("MSIE")){
            filename = URLEncoder.encode(filename, "utf-8");
            filename = filename.replace("+", "");
        }else if (agent.contains("Firefox")){
            Base64.Encoder encoder = Base64.getEncoder();
            filename = "=?utf-8?B?"+encoder.encodeToString(filename.getBytes("utf-8")) + "?=";
        }else{
            filename = URLEncoder.encode(filename, "utf-8");
        }
        return filename;
    }
}