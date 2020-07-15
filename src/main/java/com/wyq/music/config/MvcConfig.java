package com.wyq.music.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class MvcConfig extends WebMvcConfigurationSupport {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyIntercep()).addPathPatterns("/**").excludePathPatterns("/**/*.js","/","/login", "/**/*.png", "/**/*.jpg", "/**/*.css");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //registry.addResourceHandler("/css/**","/js/**").addResourceLocations("classpath:/static/css/","classpath:/static/js/");
        registry.addResourceHandler("/static/css/**","/static/js/**","static/img/**","static/music/**").addResourceLocations("classpath:/static/css/","classpath:/static/js/","classpath:/static/img/","classpath:/static/music/");
        //下面的这种写法，页面引入的时候前缀需要加上static，addResourceHandler只是一个逻辑的地址，不是实际地址，addResourceLocations是真正的地址
    }
}
