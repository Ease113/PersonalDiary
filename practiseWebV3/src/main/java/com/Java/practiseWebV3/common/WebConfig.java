package com.Java.practiseWebV3.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Autowired
	private final NoCacheInterceptor noCacheInterceptor;

    public WebConfig(NoCacheInterceptor noCacheInterceptor) {
        this.noCacheInterceptor = noCacheInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(noCacheInterceptor)
                .addPathPatterns("/", "/Registration/doRegistration", "/logOut"); // Apply to specific paths
    }
}
