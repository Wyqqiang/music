package com.wyq.music.dao;

import com.wyq.music.entity.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface MusicDao extends JpaRepository<Music,Integer> {
}
