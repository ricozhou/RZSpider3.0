package com.rzspider;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * web容器中进行部署
 * 
 * @author ricozhou
 */
public class RZSpiderServletInitializer extends SpringBootServletInitializer
{
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
    {
        return application.sources(RZSpiderApplication.class);
    }

}
