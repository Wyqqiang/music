package com.wyq.music.controller;

import com.wyq.music.entity.Music;
import com.wyq.music.service.MusicService;
import com.wyq.music.util.MusicUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MusicController {
    //注入服务层
    @Autowired
    MusicService musicService;

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
        List<Music> list = musicService.getAll();
        return list;
    }

    //上传请求
    @RequestMapping(value = "/upload",method= RequestMethod.POST)
    public ModelAndView upload(@RequestParam("") MultipartFile file, HttpServletRequest request,
                               HttpServletResponse response) throws IOException {
        String basepath = System.getProperty("user.dir");
        String base=basepath+"\\src\\main\\resources\\static\\music";
        System.out.println("path==="+base);
        file.transferTo(new File( base,file.getOriginalFilename()));
        //上传成功的话，把信息存入数据库中
        try {
            String originalFilename = file.getOriginalFilename();
            Music musicInfo = MusicUtil.getMusicInfo(base+"\\"+originalFilename);
            //String name, String singer, String album, int duration, String path
            Music music=new Music(musicInfo.getName(),musicInfo.getSinger(),musicInfo.getAlbum(),musicInfo.getDuration(),"/static/music/"+musicInfo.getPath());
            musicService.saveOne(music);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("status","success");
        modelAndView.setViewName("index");
        return modelAndView;
    }

    //分页查询
    @ResponseBody
    @RequestMapping("/find_music")
    public Map<String,Object> save(@RequestParam("page") String page, @RequestParam("size") String size){
        Map<String,Object> map= new HashMap<String,Object>();
        Page<Music> pages = musicService.pages(Integer.parseInt(page), Integer.parseInt(size));
        List<Music> content = musicService.pages(Integer.parseInt(page), Integer.parseInt(size)).getContent();
        map.put("list",content);
        map.put("totalPage",pages.getTotalPages());
        map.put("page",Integer.parseInt(page));
        map.put("pageSize",Integer.parseInt(size));
        return map;
    }
}

