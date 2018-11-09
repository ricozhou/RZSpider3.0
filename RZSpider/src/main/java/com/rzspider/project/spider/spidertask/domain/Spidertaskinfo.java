package com.rzspider.project.spider.spidertask.domain;

import com.rzspider.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 爬虫任务运行记录详情表 spider_spidertaskinfo
 * 
 * @author ricozhou
 * @date 2018-06-05
 */
public class Spidertaskinfo extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 任务详情ID */
	private Integer taskInfoId;
	/** 任务详情ID */
	private Integer spiderBackId;
	/** 任务ID */
	private Integer taskId;
	/** 任务名称 */
	private String taskName;
	/** 爬虫名称 */
	private String spiderName;
	/** 任务状态 */
	private Integer taskStatus;
	/** 完成状态 */
	// 2是全部完成，1是部分完成，0未完成
	private Integer finishStatus;
	// 默认参数全部属性
	private String spiderDefaultParamsAll;
	/** 任务参数 */
	private String taskParams;
	/** 启动时间 */
	private Date startTime;
	/** 结束时间 */
	private Date endTime;
	/** 耗时时间 */
	private String consumeTime;
	/** 爬取结果 */
	private String spiderResult;

	public String getSpiderDefaultParamsAll() {
		return spiderDefaultParamsAll;
	}

	public void setSpiderDefaultParamsAll(String spiderDefaultParamsAll) {
		this.spiderDefaultParamsAll = spiderDefaultParamsAll;
	}

	public Integer getFinishStatus() {
		return finishStatus;
	}

	public void setFinishStatus(Integer finishStatus) {
		this.finishStatus = finishStatus;
	}

	public String getConsumeTime() {
		return consumeTime;
	}

	public void setConsumeTime(String consumeTime) {
		this.consumeTime = consumeTime;
	}

	public Integer getSpiderBackId() {
		return spiderBackId;
	}

	public void setSpiderBackId(Integer spiderBackId) {
		this.spiderBackId = spiderBackId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 设置：任务详情ID
	 */
	public void setTaskInfoId(Integer taskInfoId) {
		this.taskInfoId = taskInfoId;
	}

	/**
	 * 获取：任务详情ID
	 */
	public Integer getTaskInfoId() {
		return taskInfoId;
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
	 * 设置：启动时间
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * 获取：启动时间
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * 设置：结束时间
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * 获取：结束时间
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * 设置：爬取结果
	 */
	public void setSpiderResult(String spiderResult) {
		this.spiderResult = spiderResult;
	}

	/**
	 * 获取：爬取结果
	 */
	public String getSpiderResult() {
		return spiderResult;
	}

	@Override
	public String toString() {
		return "Spidertaskinfo [taskInfoId=" + taskInfoId + ", spiderBackId=" + spiderBackId + ", taskId=" + taskId
				+ ", taskName=" + taskName + ", spiderName=" + spiderName + ", taskStatus=" + taskStatus
				+ ", finishStatus=" + finishStatus + ", spiderDefaultParamsAll=" + spiderDefaultParamsAll
				+ ", taskParams=" + taskParams + ", startTime=" + startTime + ", endTime=" + endTime + ", consumeTime="
				+ consumeTime + ", spiderResult=" + spiderResult + "]";
	}

}
