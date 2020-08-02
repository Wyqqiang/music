package com.wyq.music.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;
@Entity
public class Music implements Serializable {

    /**
     * 音乐编号
     */
    @Id
    @GeneratedValue
    private int id;
    /**
     * 音乐名称
     */
    @Column
    private String name;
    /**
     * 歌手名称
     */
    @Column
    private String singer;
    /**
     * 专辑名称
     */
    @Column
    private String album;
    /**
     * 播放时长
     */
    @Column
    private int duration;
    /**
     * 音乐文件存放路径
     */
    @Column
    private String path;
    /**
     * 音乐文件存放路径
     */
    @Column
    private String imgPath;

    public Music() {

    }

    public Music(int id, String name, String singer, String album, String path,String imgpath) {
        this.id = id;
        this.name = name;
        this.singer = singer;
        this.album = album;
        this.path = path;
        this.imgPath=imgpath;
    }

    public Music(String name, String singer, String album, int duration, String path) {
        this.name = name;
        this.singer = singer;
        this.album = album;
        this.duration = duration;
        this.path = path;
    }
    public Music( String name, String singer, String album, int duration, String path,String imgpath) {

        this.name = name;
        this.singer = singer;
        this.album = album;
        this.duration = duration;
        this.path = path;
        this.imgPath=imgpath;
    }
    public Music(int id, String name, String singer, String album, int duration, String path,String imgpath) {

        this.id = id;
        this.name = name;
        this.singer = singer;
        this.album = album;
        this.duration = duration;
        this.path = path;
        this.imgPath=imgpath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Music)) {
            return false;
        }
        Music music = (Music) o;
        return getId() == music.getId() &&
                getDuration() == music.getDuration() &&
                getName().equals(music.getName()) &&
                getSinger().equals(music.getSinger()) &&
                getAlbum().equals(music.getAlbum()) &&
                getPath().equals(music.getPath());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getSinger(), getAlbum(), getDuration(), getPath());
    }

    @Override
    public String toString() {
        return "Music{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", singer='" + singer + '\'' +
                ", album='" + album + '\'' +
                ", duration=" + duration +
                ", path='" + path + '\'' +
                '}';
    }
}
