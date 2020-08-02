package com.wyq.music.controller;

import com.wyq.music.entity.Message;
import com.wyq.music.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MessageController {
    @Autowired
    MessageService service;
    //保存
    @RequestMapping("/save_message")
    public ModelAndView save(@RequestParam("message") String message){
        ModelAndView modelAndView=new ModelAndView("index");
        modelAndView.addObject("status","message");
        Message message1=new Message(message, new Date());
        service.save(message1);
        return  modelAndView;
    }

    //分页查询
    @ResponseBody
    @RequestMapping("/find_message")
    public Map<String,Object> save(@RequestParam("page") String page, @RequestParam("size") String size){
        Map<String,Object> map= new HashMap<String,Object>();
        Page<Message> pages = service.pages(Integer.parseInt(page)-1, Integer.parseInt(size));
        List<Message> content = service.pages(Integer.parseInt(page)-1, Integer.parseInt(size)).getContent();
        map.put("list",content);
        map.put("totalPage",pages.getTotalPages());
        map.put("page",Integer.parseInt(page));
        map.put("pageSize",Integer.parseInt(size));
        return map;
    }
}
