package com.rzspider.project.spider.customspider.domain;

import com.rzspider.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 自定义爬虫详情表 spider_customspider
 * 
 * @author ricozhou
 * @date 2018-06-01
 */
public class Customspider extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 自定义爬虫ID */
	private Integer customSpiderId;
	/** 自定义爬虫后台编号ID */
	private Integer customSpiderBackId;

	/** 简单描述 */
	private String spiderDes;
	/** 版本号 */
	private String spiderVersion;

	/** JAVA爬虫包名后缀 */
	private String spiderJavaPackagePrefix;
	/** 自定义爬虫类型 */
	private Integer customSpiderType;
	/** 爬虫类型名字 */
	private String spiderCodeTypeName;
	/** 爬虫代码目录 */
	private String spiderCodeTypeFolder;
	// 默认参数全部属性
	private String spiderDefaultParamsAll;
	// 默认参数
	private String spiderDefaultParams;
	/** 自定义爬虫状态 */
	private Integer customStatus;
	/** 自定义爬虫运行状态 */
	// 0是正在测试运行，1是停止中
	private Integer runStatus;
	/** 爬虫入口文件名 */
	// java不带后缀其它的带后缀
	private String entryFileName;
	/** 创建者 */
	private String createBy;
	/** 创建时间 */
	private Date createTime;
	/** 更新者 */
	private String updateBy;
	/** 更新时间 */
	private Date updateTime;
	/** 备注 */
	private String remark;

	public String getSpiderDes() {
		return spiderDes;
	}

	public void setSpiderDes(String spiderDes) {
		this.spiderDes = spiderDes;
	}

	public String getSpiderVersion() {
		return spiderVersion;
	}

	public void setSpiderVersion(String spiderVersion) {
		this.spiderVersion = spiderVersion;
	}

	public String getSpiderDefaultParamsAll() {
		return spiderDefaultParamsAll;
	}

	public void setSpiderDefaultParamsAll(String spiderDefaultParamsAll) {
		this.spiderDefaultParamsAll = spiderDefaultParamsAll;
	}

	public String getEntryFileName() {
		return entryFileName;
	}

	public void setEntryFileName(String entryFileName) {
		this.entryFileName = entryFileName;
	}

	public Integer getRunStatus() {
		return runStatus;
	}

	public void setRunStatus(Integer runStatus) {
		this.runStatus = runStatus;
	}

	public String getSpiderJavaPackagePrefix() {
		return spiderJavaPackagePrefix;
	}

	public void setSpiderJavaPackagePrefix(String spiderJavaPackagePrefix) {
		this.spiderJavaPackagePrefix = spiderJavaPackagePrefix;
	}

	public String getSpiderDefaultParams() {
		return spiderDefaultParams;
	}

	public void setSpiderDefaultParams(String spiderDefaultParams) {
		this.spiderDefaultParams = spiderDefaultParams;
	}

	public String getSpiderCodeTypeName() {
		return spiderCodeTypeName;
	}

	public void setSpiderCodeTypeName(String spiderCodeTypeName) {
		this.spiderCodeTypeName = spiderCodeTypeName;
	}

	public String getSpiderCodeTypeFolder() {
		return spiderCodeTypeFolder;
	}

	public void setSpiderCodeTypeFolder(String spiderCodeTypeFolder) {
		this.spiderCodeTypeFolder = spiderCodeTypeFolder;
	}

	/**
	 * 设置：自定义爬虫ID
	 */
	public void setCustomSpiderId(Integer customSpiderId) {
		this.customSpiderId = customSpiderId;
	}

	/**
	 * 获取：自定义爬虫ID
	 */
	public Integer getCustomSpiderId() {
		return customSpiderId;
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
	 * 设置：自定义爬虫状态
	 */
	public void setCustomStatus(Integer customStatus) {
		this.customStatus = customStatus;
	}

	/**
	 * 获取：自定义爬虫状态
	 */
	public Integer getCustomStatus() {
		return customStatus;
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

	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
	}

	@Override
	public String toString() {
		return "Customspider [customSpiderId=" + customSpiderId + ", customSpiderBackId=" + customSpiderBackId
				+ ", spiderDes=" + spiderDes + ", spiderVersion=" + spiderVersion + ", spiderJavaPackagePrefix="
				+ spiderJavaPackagePrefix + ", customSpiderType=" + customSpiderType + ", spiderCodeTypeName="
				+ spiderCodeTypeName + ", spiderCodeTypeFolder=" + spiderCodeTypeFolder + ", spiderDefaultParamsAll="
				+ spiderDefaultParamsAll + ", spiderDefaultParams=" + spiderDefaultParams + ", customStatus="
				+ customStatus + ", runStatus=" + runStatus + ", entryFileName=" + entryFileName + ", createBy="
				+ createBy + ", createTime=" + createTime + ", updateBy=" + updateBy + ", updateTime=" + updateTime
				+ ", remark=" + remark + "]";
	}

}
