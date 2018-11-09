package com.rzspider.project.spider.spidertask.domain;

import com.rzspider.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 爬虫任务详情表 spider_spidertask
 * 
 * @author ricozhou
 * @date 2018-05-29
 */
public class Spidertask extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 任务ID */
	private Integer taskId;
	/** 任务名称 */
	private String taskName;
	/** 爬虫ID */
	private Integer spiderId;
	/** 爬虫任务后台编号ID */
	private Integer spiderBackId;
	/** 爬虫名称 */
	private String spiderName;
	/** 爬虫类型 */
	private String spiderType;
	/** 爬虫创建类型 */
	private Integer createType;
	/** 爬虫状态 */
	private Integer status;
	/** 可用状态 */
	private Integer taskStatus;
	/** 任务状态 */
	private Integer jobStatus;
	/** 任务执行频次 */
	private Integer taskExecfrequency;
	// 默认参数全部属性
	private String spiderDefaultParamsAll;
	/** 任务参数 */
	private String taskParams;

	// 以下是为了拼接cron表达式
	/** 任务cron表达式 */
	private String cronExpression;
	/** 任务频次具体 */
	private Integer times;
	/** 任务频次后缀 */
	private Integer taskExecfrequency2;
	/** 任务首次启动时间 */
	private String firstStartTime;

	/** 用户ID */
	private Integer userId;
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

	@Override
	public String toString() {
		return "Spidertask [taskId=" + taskId + ", taskName=" + taskName + ", spiderId=" + spiderId + ", spiderBackId="
				+ spiderBackId + ", spiderName=" + spiderName + ", spiderType=" + spiderType + ", createType="
				+ createType + ", status=" + status + ", taskStatus=" + taskStatus + ", jobStatus=" + jobStatus
				+ ", taskExecfrequency=" + taskExecfrequency + ", spiderDefaultParamsAll=" + spiderDefaultParamsAll
				+ ", taskParams=" + taskParams + ", cronExpression=" + cronExpression + ", times=" + times
				+ ", taskExecfrequency2=" + taskExecfrequency2 + ", firstStartTime=" + firstStartTime + ", userId="
				+ userId + ", createBy=" + createBy + ", createTime=" + createTime + ", updateBy=" + updateBy
				+ ", updateTime=" + updateTime + ", remark=" + remark + "]";
	}

	public String getSpiderDefaultParamsAll() {
		return spiderDefaultParamsAll;
	}

	public void setSpiderDefaultParamsAll(String spiderDefaultParamsAll) {
		this.spiderDefaultParamsAll = spiderDefaultParamsAll;
	}

	public Integer getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(Integer jobStatus) {
		this.jobStatus = jobStatus;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public Integer getTaskExecfrequency2() {
		return taskExecfrequency2;
	}

	public void setTaskExecfrequency2(Integer taskExecfrequency2) {
		this.taskExecfrequency2 = taskExecfrequency2;
	}

	public String getFirstStartTime() {
		return firstStartTime;
	}

	public void setFirstStartTime(String firstStartTime) {
		this.firstStartTime = firstStartTime;
	}

	public String getSpiderType() {
		return spiderType;
	}

	public void setSpiderType(String spiderType) {
		this.spiderType = spiderType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 设置：任务ID
	 */
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	/**
	 * 获取：任务ID
	 */
	public Integer getTaskId() {
		return taskId;
	}

	/**
	 * 设置：任务名称
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	/**
	 * 获取：任务名称
	 */
	public String getTaskName() {
		return taskName;
	}

	/**
	 * 设置：爬虫ID
	 */
	public void setSpiderId(Integer spiderId) {
		this.spiderId = spiderId;
	}

	/**
	 * 获取：爬虫ID
	 */
	public Integer getSpiderId() {
		return spiderId;
	}

	/**
	 * 设置：爬虫任务后台编号ID
	 */
	public void setSpiderBackId(Integer spiderBackId) {
		this.spiderBackId = spiderBackId;
	}

	/**
	 * 获取：爬虫任务后台编号ID
	 */
	public Integer getSpiderBackId() {
		return spiderBackId;
	}

	/**
	 * 设置：爬虫名称
	 */
	public void setSpiderName(String spiderName) {
		this.spiderName = spiderName;
	}

	/**
	 * 获取：爬虫名称
	 */
	public String getSpiderName() {
		return spiderName;
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
	 * 设置：爬虫状态
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 获取：爬虫状态
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 设置：任务状态
	 */
	public void setTaskStatus(Integer taskStatus) {
		this.taskStatus = taskStatus;
	}

	/**
	 * 获取：任务状态
	 */
	public Integer getTaskStatus() {
		return taskStatus;
	}

	/**
	 * 设置：任务执行频次
	 */
	public void setTaskExecfrequency(Integer taskExecfrequency) {
		this.taskExecfrequency = taskExecfrequency;
	}

	/**
	 * 获取：任务执行频次
	 */
	public Integer getTaskExecfrequency() {
		return taskExecfrequency;
	}

	/**
	 * 设置：任务参数
	 */
	public void setTaskParams(String taskParams) {
		this.taskParams = taskParams;
	}

	/**
	 * 获取：任务参数
	 */
	public String getTaskParams() {
		return taskParams;
	}

	/**
	 * 设置：用户ID
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * 获取：用户ID
	 */
	public Integer getUserId() {
		return userId;
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

}
