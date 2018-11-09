package com.rzspider.project.spider.spidermanage.domain;

import com.rzspider.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 总爬虫详情表 spider_spider_list
 * 
 * @author ricozhou
 * @date 2018-06-02
 */
public class SpiderList extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 爬虫ID */
	private Integer spiderId;
	/** 自定义爬虫ID */
	private Integer customSpiderId;
	/** 爬虫后台编号ID */
	private Integer spiderBackId;
	/** 简单描述 */
	private String spiderDes;
	/** 版本号 */
	private String spiderVersion;
	/** 爬虫状态 */
	private Integer status;
	/** 爬虫创建类型 */
	private Integer createType;
	/** 自定义爬虫类型 */
	private Integer customSpiderType;
	// 默认参数全部属性
	private String spiderDefaultParamsAll;
	// 默认参数
	private String spiderDefaultParams;
	/** 更新者 */
	private String updateBy;

	@Override
	public String toString() {
		return "SpiderList [spiderId=" + spiderId + ", customSpiderId=" + customSpiderId + ", spiderBackId="
				+ spiderBackId + ", spiderDes=" + spiderDes + ", spiderVersion=" + spiderVersion + ", status=" + status
				+ ", createType=" + createType + ", customSpiderType=" + customSpiderType + ", spiderDefaultParamsAll="
				+ spiderDefaultParamsAll + ", spiderDefaultParams=" + spiderDefaultParams + ", updateBy=" + updateBy
				+ ", updateTime=" + updateTime + "]";
	}

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

	public String getSpiderDefaultParams() {
		return spiderDefaultParams;
	}

	public void setSpiderDefaultParams(String spiderDefaultParams) {
		this.spiderDefaultParams = spiderDefaultParams;
	}

	/** 更新时间 */
	private Date updateTime;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 设置：爬虫ID
	 */
	public void setSpiderId(Integer spiderId) {
		this.spiderId = spiderId;
	}

	public Integer getCustomSpiderId() {
		return customSpiderId;
	}

	public void setCustomSpiderId(Integer customSpiderId) {
		this.customSpiderId = customSpiderId;
	}

	/**
	 * 获取：爬虫ID
	 */
	public Integer getSpiderId() {
		return spiderId;
	}

	/**
	 * 设置：爬虫后台编号ID
	 */
	public void setSpiderBackId(Integer spiderBackId) {
		this.spiderBackId = spiderBackId;
	}

	/**
	 * 获取：爬虫后台编号ID
	 */
	public Integer getSpiderBackId() {
		return spiderBackId;
	}

	/**
	 * 设置：爬虫创建类型
	 */
	public void setCreateType(Integer createType) {
		this.createType = createType;
	}

	/**
	 * 获取：爬虫创建类型
	 */
	public Integer getCreateType() {
		return createType;
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

}
