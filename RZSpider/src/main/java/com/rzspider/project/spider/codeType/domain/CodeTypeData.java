package com.rzspider.project.spider.codeType.domain;

import com.rzspider.framework.web.domain.BaseEntity;

/**
 * 爬虫代码类型数据表 spider_code_type_data
 * 
 * @author ricozhou
 * @date 2018-09-03
 */
public class CodeTypeData extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** ID */
	private Integer id;
	/** 爬虫代码类型标志ＩＤ */
	private Integer customSpiderType;
	/** 爬虫代码类型名称 */
	private String customSpiderTypeName;
	/** 爬虫代码类型状态 */
	private Integer status;

	/**
	 * 设置：ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 获取：ID
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 设置：爬虫代码类型标志ＩＤ
	 */
	public void setCustomSpiderType(Integer customSpiderType) {
		this.customSpiderType = customSpiderType;
	}

	/**
	 * 获取：爬虫代码类型标志ＩＤ
	 */
	public Integer getCustomSpiderType() {
		return customSpiderType;
	}

	/**
	 * 设置：爬虫代码类型名称
	 */
	public void setCustomSpiderTypeName(String customSpiderTypeName) {
		this.customSpiderTypeName = customSpiderTypeName;
	}

	/**
	 * 获取：爬虫代码类型名称
	 */
	public String getCustomSpiderTypeName() {
		return customSpiderTypeName;
	}

	/**
	 * 设置：爬虫代码类型状态
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 获取：爬虫代码类型状态
	 */
	public Integer getStatus() {
		return status;
	}

}
