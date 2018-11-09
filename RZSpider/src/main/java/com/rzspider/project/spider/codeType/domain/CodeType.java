package com.rzspider.project.spider.codeType.domain;

import com.rzspider.framework.web.domain.BaseEntity;

/**
 * 爬虫代码类型详情表 spider_code_type
 * 
 * @author ricozhou
 * @date 2018-06-08
 */
public class CodeType extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 爬虫代码类型ID */
	private Integer spiderCodeTypeId;
	/** 爬虫代码类型标志ＩＤ */
	private Integer customSpiderType;
	/** 爬虫代码类型名称 */
	private String spiderCodeTypeName;
	/** 爬虫代码类型对应目录 */
	private String spiderCodeTypeFolder;
	/** 爬虫代码类型状态 */
	private Integer status;

	/**
	 * 设置：爬虫代码类型ID
	 */
	public void setSpiderCodeTypeId(Integer spiderCodeTypeId) 
	{
		this.spiderCodeTypeId = spiderCodeTypeId;
	}
	
	/**
	 * 获取：爬虫代码类型ID
	 */
	public Integer getSpiderCodeTypeId() 
	{
		return spiderCodeTypeId;
	}
	
	/**
	 * 设置：爬虫代码类型标志ＩＤ
	 */
	public void setCustomSpiderType(Integer customSpiderType) 
	{
		this.customSpiderType = customSpiderType;
	}
	
	/**
	 * 获取：爬虫代码类型标志ＩＤ
	 */
	public Integer getCustomSpiderType() 
	{
		return customSpiderType;
	}
	
	/**
	 * 设置：爬虫代码类型名称
	 */
	public void setSpiderCodeTypeName(String spiderCodeTypeName) 
	{
		this.spiderCodeTypeName = spiderCodeTypeName;
	}
	
	/**
	 * 获取：爬虫代码类型名称
	 */
	public String getSpiderCodeTypeName() 
	{
		return spiderCodeTypeName;
	}
	
	/**
	 * 设置：爬虫代码类型对应目录
	 */
	public void setSpiderCodeTypeFolder(String spiderCodeTypeFolder) 
	{
		this.spiderCodeTypeFolder = spiderCodeTypeFolder;
	}
	
	/**
	 * 获取：爬虫代码类型对应目录
	 */
	public String getSpiderCodeTypeFolder() 
	{
		return spiderCodeTypeFolder;
	}
	
	/**
	 * 设置：爬虫代码类型状态
	 */
	public void setStatus(Integer status) 
	{
		this.status = status;
	}
	
	/**
	 * 获取：爬虫代码类型状态
	 */
	public Integer getStatus() 
	{
		return status;
	}

	@Override
	public String toString() {
		return "CodeType [spiderCodeTypeId=" + spiderCodeTypeId + ", customSpiderType=" + customSpiderType
				+ ", spiderCodeTypeName=" + spiderCodeTypeName + ", spiderCodeTypeFolder=" + spiderCodeTypeFolder
				+ ", status=" + status + "]";
	}
	
}
