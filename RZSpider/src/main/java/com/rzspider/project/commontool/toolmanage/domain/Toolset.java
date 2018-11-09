package com.rzspider.project.commontool.toolmanage.domain;

import com.rzspider.framework.web.domain.BaseEntity;

/**
 * 通用工具管理工具设置表 commontool_toolmanage_toolset
 * 
 * @author ricozhou
 * @date 2018-08-27
 */
public class Toolset extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/** id */
	private Integer id;
	/** OCR识别模式 */
	private Integer toolOcrsetType;
	/** 百度OCRAPIID */
	private String toolOcrsetBaiduocrApiId;
	/** 百度OCRAPIKEY */
	private String toolOcrsetBaiduocrApiKey;
	/** 百度OCRSECRETKEY */
	private String toolOcrsetBaiduocrSecretKey;
	/** 备注 */
	private String toolOcrsetRemark;

	/**
	 * 设置：id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 获取：id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 设置：OCR识别模式
	 */
	public void setToolOcrsetType(Integer toolOcrsetType) {
		this.toolOcrsetType = toolOcrsetType;
	}

	/**
	 * 获取：OCR识别模式
	 */
	public Integer getToolOcrsetType() {
		return toolOcrsetType;
	}

	/**
	 * 设置：百度OCRAPIID
	 */
	public void setToolOcrsetBaiduocrApiId(String toolOcrsetBaiduocrApiId) {
		this.toolOcrsetBaiduocrApiId = toolOcrsetBaiduocrApiId;
	}

	/**
	 * 获取：百度OCRAPIID
	 */
	public String getToolOcrsetBaiduocrApiId() {
		return toolOcrsetBaiduocrApiId;
	}

	/**
	 * 设置：百度OCRAPIKEY
	 */
	public void setToolOcrsetBaiduocrApiKey(String toolOcrsetBaiduocrApiKey) {
		this.toolOcrsetBaiduocrApiKey = toolOcrsetBaiduocrApiKey;
	}

	/**
	 * 获取：百度OCRAPIKEY
	 */
	public String getToolOcrsetBaiduocrApiKey() {
		return toolOcrsetBaiduocrApiKey;
	}

	/**
	 * 设置：百度OCRSECRETKEY
	 */
	public void setToolOcrsetBaiduocrSecretKey(String toolOcrsetBaiduocrSecretKey) {
		this.toolOcrsetBaiduocrSecretKey = toolOcrsetBaiduocrSecretKey;
	}

	/**
	 * 获取：百度OCRSECRETKEY
	 */
	public String getToolOcrsetBaiduocrSecretKey() {
		return toolOcrsetBaiduocrSecretKey;
	}

	/**
	 * 设置：备注
	 */
	public void setToolOcrsetRemark(String toolOcrsetRemark) {
		this.toolOcrsetRemark = toolOcrsetRemark;
	}

	/**
	 * 获取：备注
	 */
	public String getToolOcrsetRemark() {
		return toolOcrsetRemark;
	}

	@Override
	public String toString() {
		return "ToolmanageToolset [id=" + id + ", toolOcrsetType=" + toolOcrsetType + ", toolOcrsetBaiduocrApiId="
				+ toolOcrsetBaiduocrApiId + ", toolOcrsetBaiduocrApiKey=" + toolOcrsetBaiduocrApiKey
				+ ", toolOcrsetBaiduocrSecretKey=" + toolOcrsetBaiduocrSecretKey + ", toolOcrsetRemark="
				+ toolOcrsetRemark + "]";
	}
}
