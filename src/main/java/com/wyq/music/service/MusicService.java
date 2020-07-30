package com.wyq.music.service;

import com.wyq.music.entity.Music;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MusicService {
    List<Music> getAll();

    void saveOne(Music music);

    Page<Music> pages(int page, int size);
}
