package com.wyq.music.service;

import com.wyq.music.entity.Music;

import java.util.List;

public interface MusicService {
    List<Music> getAll();

    void saveOne(Music music);
}
