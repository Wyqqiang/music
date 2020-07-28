package com.wyq.music.service;

import com.wyq.music.dao.MusicDao;
import com.wyq.music.entity.Music;
import org.springframework.beans.factory.annotation.Autowired;
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


}
