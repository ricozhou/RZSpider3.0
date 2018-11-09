package com.rzspider.project.blog.blogbrowe.domain;

import com.rzspider.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 博客浏览日志表 blog_blogbrowe
 * 
 * @author ricozhou
 * @date 2018-10-25
 */
public class Blogbrowe extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** id */
	private Integer blogBroweId;
	/** 客户端平台 */
	private String clientPlatform;
	/** 客户端UA */
	private String clientUserAgent;
	/** 浏览器系统 */
	private String clientBrowsePlatform;
	/** 浏览器名称 */
	private String clientBrowseName;
	/** 浏览器版本 */
	private String clientBrowseVersion;
	/** 浏览器ip */
	private String clientBrowseIp;
	/** 浏览器所在城市 */
	private String clientBrowseCity;
	/** 浏览器客户端类型 */
	private String clientBrowseAppAndPc;
	/** 浏览地址 */
	private String clientBrowseUrl;
	/** 创建时间 */
	private Date createTime;

	/**
	 * 设置：id
	 */
	public void setBlogBroweId(Integer blogBroweId) {
		this.blogBroweId = blogBroweId;
	}

	/**
	 * 获取：id
	 */
	public Integer getBlogBroweId() {
		return blogBroweId;
	}

	/**
	 * 设置：客户端平台
	 */
	public void setClientPlatform(String clientPlatform) {
		this.clientPlatform = clientPlatform;
	}

	/**
	 * 获取：客户端平台
	 */
	public String getClientPlatform() {
		return clientPlatform;
	}

	/**
	 * 设置：客户端UA
	 */
	public void setClientUserAgent(String clientUserAgent) {
		this.clientUserAgent = clientUserAgent;
	}

	/**
	 * 获取：客户端UA
	 */
	public String getClientUserAgent() {
		return clientUserAgent;
	}

	/**
	 * 设置：浏览器系统
	 */
	public void setClientBrowsePlatform(String clientBrowsePlatform) {
		this.clientBrowsePlatform = clientBrowsePlatform;
	}

	/**
	 * 获取：浏览器系统
	 */
	public String getClientBrowsePlatform() {
		return clientBrowsePlatform;
	}

	/**
	 * 设置：浏览器名称
	 */
	public void setClientBrowseName(String clientBrowseName) {
		this.clientBrowseName = clientBrowseName;
	}

	/**
	 * 获取：浏览器名称
	 */
	public String getClientBrowseName() {
		return clientBrowseName;
	}

	/**
	 * 设置：浏览器版本
	 */
	public void setClientBrowseVersion(String clientBrowseVersion) {
		this.clientBrowseVersion = clientBrowseVersion;
	}

	/**
	 * 获取：浏览器版本
	 */
	public String getClientBrowseVersion() {
		return clientBrowseVersion;
	}

	/**
	 * 设置：浏览器ip
	 */
	public void setClientBrowseIp(String clientBrowseIp) {
		this.clientBrowseIp = clientBrowseIp;
	}

	/**
	 * 获取：浏览器ip
	 */
	public String getClientBrowseIp() {
		return clientBrowseIp;
	}

	/**
	 * 设置：浏览器所在城市
	 */
	public void setClientBrowseCity(String clientBrowseCity) {
		this.clientBrowseCity = clientBrowseCity;
	}

	/**
	 * 获取：浏览器所在城市
	 */
	public String getClientBrowseCity() {
		return clientBrowseCity;
	}

	/**
	 * 设置：浏览器客户端类型
	 */
	public void setClientBrowseAppAndPc(String clientBrowseAppAndPc) {
		this.clientBrowseAppAndPc = clientBrowseAppAndPc;
	}

	/**
	 * 获取：浏览器客户端类型
	 */
	public String getClientBrowseAppAndPc() {
		return clientBrowseAppAndPc;
	}

	/**
	 * 设置：浏览地址
	 */
	public void setClientBrowseUrl(String clientBrowseUrl) {
		this.clientBrowseUrl = clientBrowseUrl;
	}

	/**
	 * 获取：浏览地址
	 */
	public String getClientBrowseUrl() {
		return clientBrowseUrl;
	}

	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

}
