package com.wyq.music.controller;

import com.wyq.music.entity.Music;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MusicController {
    //处理登录请求
    @RequestMapping("/login")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response,
    @RequestParam(value = "username",required = false) String username,@RequestParam(value = "password",required = false) String password){
        HttpSession session = request.getSession();
        ModelAndView modelAndView=new ModelAndView();
        Object user = session.getAttribute("user");
        if(user!=null){
            modelAndView.setViewName("index");
            return modelAndView;
        }
        if("admin".equals(username) && "123".equals(password)){
            session.setAttribute("user","admin");
            modelAndView.setViewName("index");
        }else{
            modelAndView.setViewName("login");
        }
        return modelAndView;
    }
    //听歌请求，返回歌曲列表
    @RequestMapping("/sing")
    public ModelAndView sing(HttpServletRequest request, HttpServletResponse response){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("index_sing");
        return modelAndView;
    }
    //返回json数据
    @RequestMapping("/data")
    @ResponseBody
    public List<Music> getdata(){
        List<Music> list=new ArrayList<Music>();
        for (int i = 0; i <100 ; i++) {
            Music music=new Music(i,"a"+i,"a","a",10,"");
            list.add(music);
        }
        return list;
    }
}

