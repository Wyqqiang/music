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
import java.io.*;
import java.lang.reflect.Method;
import java.net.URLDecoder;
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

    //上传请求
    @RequestMapping(value = "/upload",method= RequestMethod.POST)
    public ModelAndView upload(@RequestParam("") MultipartFile file, HttpServletRequest request,
                               HttpServletResponse response) throws IOException {
        String basepath = System.getProperty("user.dir");
        String base=basepath+"\\music\\src\\main\\resources\\static\\music";
        System.out.println("path==="+base);
        file.transferTo(new File( base,file.getOriginalFilename()));
        //上传成功的话，把信息存入数据库中
        try {
            String originalFilename = file.getOriginalFilename();
            Music musicInfo = MusicUtil.getMusicInfo(base+"\\"+originalFilename);
            //String name, String singer, String album, int duration, String path
            //测试得到图片信息
            MusicUtil.saveMP3Image(base+"\\"+originalFilename,basepath+"\\music\\src\\main\\resources\\static\\img",true);
            Music music=new Music(musicInfo.getName(),musicInfo.getSinger(),musicInfo.getAlbum(),musicInfo.getDuration(),"/static/music/"+musicInfo.getPath(),"/static/img/"+MusicUtil.getFileLabel(new File(base+"\\"+originalFilename).getName())+".jpg");
            musicService.saveOne(music);

        } catch (Exception e) {
            e.printStackTrace();
        }
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("status","success");
        modelAndView.setViewName("index");
        return modelAndView;
    }
    //下载请求
    @RequestMapping(value = "/download")
    public void upload(HttpServletRequest request,HttpServletResponse response){
        try{
            response.setContentType("text/html;charset=utf-8");
            //获取文件名
            String filename1 = request.getParameter("filename");
            String filename = URLDecoder.decode(filename1, "UTF-8");
            filename=filename.split("music/")[1];
            String basepath = System.getProperty("user.dir");
            String base=basepath+"\\music\\src\\main\\resources\\static\\music\\"+filename;
            //byte[] bytes1 = filename.getBytes("utf-8");
            //filename=new String(bytes1);
            //文件所在的文件夹
//            String folder="/file/";
//            //首先去创建目录，如果目录存在的话，生成文件，否则创建目录
//            File fileDir=new File(folder);
//            if(!fileDir.exists()){
//                fileDir.mkdir();
//            }
//
//            //在目录下创建文件
//            String realfile=folder+""+filename;
//            File file=new File(realfile);
//            if(!file.exists()){
//                file.createNewFile();
//            }
            //解决下载文件时中文不能正常显示
            String agent = request.getHeader("user-agent");
            String downloadFilename = MusicUtil.getFileName(agent, filename);

            //通知浏览器以下载的方式打开
            //response.setContentType("application/octet-stream;charset=UTF-8");
            response.addHeader("Content-type", "appllication/octet-stream");
            response.addHeader("Content-Disposition", "attachment;filename="+downloadFilename);
            //通过文件输入流读取文件
//            InputStream in=getServletContext().getResourceAsStream(realfile);
            InputStream in= new FileInputStream(base);
            OutputStream out=response.getOutputStream();
            byte[] bytes=new byte[1024];
            int len=0;
            while ((len=in.read(bytes))!=-1){
                out.write(bytes,0,len);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //分页查询
    @ResponseBody
    @RequestMapping("/find_music")
    public Map<String,Object> save(@RequestParam("page") String page, @RequestParam("size") String size){
        Map<String,Object> map= new HashMap<String,Object>();
        Page<Music> pages = musicService.pages(Integer.parseInt(page)-1, Integer.parseInt(size));
        List<Music> content = musicService.pages(Integer.parseInt(page)-1, Integer.parseInt(size)).getContent();
        map.put("list",content);
        map.put("totalPage",pages.getTotalPages());
        map.put("page",Integer.parseInt(page));
        map.put("pageSize",Integer.parseInt(size));
        return map;
    }


}

