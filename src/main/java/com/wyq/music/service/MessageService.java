package com.wyq.music.service;

import com.wyq.music.entity.Message;


import java.util.List;

public interface MessageService {
    //查询全部
    List<Message> getAll();
    //保存消息
    void save(Message message);
}
