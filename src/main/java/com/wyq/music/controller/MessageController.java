package com.wyq.music.controller;

import com.wyq.music.entity.Message;
import com.wyq.music.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
public class MessageController {
    @Autowired
    MessageService service;
    //保存
    @RequestMapping("/save")
    public ModelAndView save(@RequestParam("message") String message,@RequestParam("date") Date date){
        ModelAndView modelAndView=new ModelAndView("index");
        modelAndView.addObject("status","message");
        Message message1=new Message(message, date);
        service.save(message1);
        return  modelAndView;
    }

    //查询所有
//    @RequestMapping("/find")
//    public ModelAndView save(@RequestParam("message") String message,@RequestParam("date") Date date){
//        ModelAndView modelAndView=new ModelAndView("index");
//        modelAndView.addObject("status","message");
//        Message message1=new Message(message, date);
//        service.save(message1);
//        return  modelAndView;
//    }
}
