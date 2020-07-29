package com.wyq.music.service;

import com.wyq.music.dao.MessageDao;
import com.wyq.music.entity.Message;
import com.wyq.music.entity.Music;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageDao dao;


    @Override
    public List<Message> getAll() {
        return dao.findAll();
    }

    @Override
    public void save(Message message) {
        dao.save(message);
    }
}
