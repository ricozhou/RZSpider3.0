package com.rzspider.framework.config;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 通用配置
 * 
 * @author ricozhou
 */
@Configuration
public class ResourcesConfig extends WebMvcConfigurerAdapter {

	/**
	 * 首页地址
	 */
	@Value("${shiro.user.indexUrl}")
	public String indexUrl;

	/**
	 * 默认首页的设置，当输入域名是可以自动跳转到默认指定的网页
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("forward:" + indexUrl);
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
		super.addViewControllers(registry);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/profile/**")
				.addResourceLocations("file:" + new File(RZSpiderConfig.getProfile()).getAbsolutePath());
		// 内部存储文件跳转
		registry.addResourceHandler("/files/**").addResourceLocations(
				"file:///" + new File(FilePathConfig.getUploadPath()).getAbsolutePath() + File.separator);
		// 缓存文件跳转
		registry.addResourceHandler("/cachefiles/**").addResourceLocations(
				"file:///" + new File(FilePathConfig.getUploadCachePath()).getAbsolutePath() + File.separator);
		// 博客文件跳转
		registry.addResourceHandler("/blogfiles/**").addResourceLocations(
				"file:///" + new File(FilePathConfig.getUploadBlogPath()).getAbsolutePath() + File.separator);
		// 直链文件跳转
		registry.addResourceHandler("/docfiles/**").addResourceLocations(
				"file:///" + new File(FilePathConfig.getStraightlinkManagePath()).getAbsolutePath() + File.separator);
	}
}