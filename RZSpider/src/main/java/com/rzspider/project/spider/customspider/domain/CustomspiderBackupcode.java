package com.rzspider.project.spider.customspider.domain;

import com.rzspider.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 自定义爬虫代码备份表 spider_customspider_backupcode
 * 
 * @author ricozhou
 * @date 2018-08-29
 */
public class CustomspiderBackupcode extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 自定义爬虫备份代码ID */
	private Integer spiderCustomspiderBackupcodeId;
	/** 自定义爬虫后台编号ID */
	private Integer customSpiderBackId;
	/** 自定义爬虫类型 */
	private Integer customSpiderType;
	// 备份代码类型 0是自动1是手动
	private Integer backupcodeType;
	/** 爬虫类型名字 */
	private String spiderCodeTypeName;
	/** 爬虫版本 */
	private String spiderVersion;
	/** 爬虫代码文件名 */
	private String spiderFileName;
	/** 爬虫代码文件路径 */
	private String spiderFilePath;
	/** 爬虫代码文件内容 */
	private String spiderFileCodeContent;
	/** 创建者 */
	private String createBy;
	/** 创建时间 */
	private Date createTime;
	/** 更新者 */
	private String updateBy;
	/** 更新时间 */
	private Date updateTime;

	public Integer getBackupcodeType() {
		return backupcodeType;
	}

	public void setBackupcodeType(Integer backupcodeType) {
		this.backupcodeType = backupcodeType;
	}

	public String getSpiderCodeTypeName() {
		return spiderCodeTypeName;
	}

	public void setSpiderCodeTypeName(String spiderCodeTypeName) {
		this.spiderCodeTypeName = spiderCodeTypeName;
	}

	/**
	 * 设置：自定义爬虫备份代码ID
	 */
	public void setSpiderCustomspiderBackupcodeId(Integer spiderCustomspiderBackupcodeId) {
		this.spiderCustomspiderBackupcodeId = spiderCustomspiderBackupcodeId;
	}

	/**
	 * 获取：自定义爬虫备份代码ID
	 */
	public Integer getSpiderCustomspiderBackupcodeId() {
		return spiderCustomspiderBackupcodeId;
	}

	/**
	 * 设置：自定义爬虫后台编号ID
	 */
	public void setCustomSpiderBackId(Integer customSpiderBackId) {
		this.customSpiderBackId = customSpiderBackId;
	}

	/**
	 * 获取：自定义爬虫后台编号ID
	 */
	public Integer getCustomSpiderBackId() {
		return customSpiderBackId;
	}

	/**
	 * 设置：自定义爬虫类型
	 */
	public void setCustomSpiderType(Integer customSpiderType) {
		this.customSpiderType = customSpiderType;
	}

	/**
	 * 获取：自定义爬虫类型
	 */
	public Integer getCustomSpiderType() {
		return customSpiderType;
	}

	/**
	 * 设置：爬虫版本
	 */
	public void setSpiderVersion(String spiderVersion) {
		this.spiderVersion = spiderVersion;
	}

	/**
	 * 获取：爬虫版本
	 */
	public String getSpiderVersion() {
		return spiderVersion;
	}

	/**
	 * 设置：爬虫代码文件名
	 */
	public void setSpiderFileName(String spiderFileName) {
		this.spiderFileName = spiderFileName;
	}

	/**
	 * 获取：爬虫代码文件名
	 */
	public String getSpiderFileName() {
		return spiderFileName;
	}

	/**
	 * 设置：爬虫代码文件路径
	 */
	public void setSpiderFilePath(String spiderFilePath) {
		this.spiderFilePath = spiderFilePath;
	}

	/**
	 * 获取：爬虫代码文件路径
	 */
	public String getSpiderFilePath() {
		return spiderFilePath;
	}

	/**
	 * 设置：爬虫代码文件内容
	 */
	public void setSpiderFileCodeContent(String spiderFileCodeContent) {
		this.spiderFileCodeContent = spiderFileCodeContent;
	}

	/**
	 * 获取：爬虫代码文件内容
	 */
	public String getSpiderFileCodeContent() {
		return spiderFileCodeContent;
	}

	/**
	 * 设置：创建者
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	/**
	 * 获取：创建者
	 */
	public String getCreateBy() {
		return createBy;
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

	/**
	 * 设置：更新者
	 */
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	/**
	 * 获取：更新者
	 */
	public String getUpdateBy() {
		return updateBy;
	}

	/**
	 * 设置：更新时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 获取：更新时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	@Override
	public String toString() {
		return "CustomspiderBackupcode [spiderCustomspiderBackupcodeId=" + spiderCustomspiderBackupcodeId
				+ ", customSpiderBackId=" + customSpiderBackId + ", customSpiderType=" + customSpiderType
				+ ", backupcodeType=" + backupcodeType + ", spiderCodeTypeName=" + spiderCodeTypeName
				+ ", spiderVersion=" + spiderVersion + ", spiderFileName=" + spiderFileName + ", spiderFilePath="
				+ spiderFilePath + ", spiderFileCodeContent=" + spiderFileCodeContent + ", createBy=" + createBy
				+ ", createTime=" + createTime + ", updateBy=" + updateBy + ", updateTime=" + updateTime + "]";
	}

}
