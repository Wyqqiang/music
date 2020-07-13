package com.wyq.music.util;

import com.wyq.music.entity.Music;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.id3.ID3v23Frame;

import java.io.File;
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