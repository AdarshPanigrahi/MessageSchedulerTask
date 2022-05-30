package com.app.MessageScheduler.authenticator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class webConfig implements WebMvcConfigurer {

    @Autowired
    ClientAuth clientAuth;

    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(clientAuth).addPathPatterns("/schedule/message");
    }

}
