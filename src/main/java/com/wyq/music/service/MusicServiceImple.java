package com.wyq.music.service;

import com.wyq.music.dao.MusicDao;
import com.wyq.music.entity.Music;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MusicServiceImple implements  MusicService {

    @Autowired
    MusicDao musicDao;

    @Override
    public List<Music> getAll() {
        return musicDao.findAll();
    }

    @Override
    public void saveOne(Music music) {
        musicDao.save(music);

    }
    //分页查询
    @Override
    public Page<Music> pages(int page, int size) {
        Pageable pageable=PageRequest.of(page,size,Sort.by("name"));
        Page<Music> page_data = musicDao.findAll( pageable);
        return page_data;
    }

}
