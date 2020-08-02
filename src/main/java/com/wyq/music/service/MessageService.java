package com.wyq.music.service;

import com.wyq.music.entity.Message;
import org.springframework.data.domain.Page;


import java.util.List;

public interface MessageService {
    //查询全部
    List<Message> getAll();
    //保存消息
    void save(Message message);
    //分页查询
    Page<Message> pages(int page, int size);
}
