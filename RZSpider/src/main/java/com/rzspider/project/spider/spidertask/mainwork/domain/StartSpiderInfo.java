package com.rzspider.project.spider.spidertask.mainwork.domain;

import com.rzspider.project.spider.customspider.domain.Customspider;

//爬虫最终开始工作的封装类
public class StartSpiderInfo {
	// 此次爬虫任务详情id
	private Integer taskInfoId;
	// 爬虫后台id
	public Integer spiderBackId;
	// 创建的类型，0是自带，1是创建（需要动态执行的.java文件）
	private int createType;
	/** 爬虫语言类型 */
	private Integer spiderLanguageType;

	// 爬虫参数
	public String spiderParams;

	// 自定义爬虫详情
	public Customspider customspider;

	public Customspider getCustomspider() {
		return customspider;
	}

	public void setCustomspider(Customspider customspider) {
		this.customspider = customspider;
	}

	public Integer getTaskInfoId() {
		return taskInfoId;
	}

	public void setTaskInfoId(Integer taskInfoId) {
		this.taskInfoId = taskInfoId;
	}

	public Integer getSpiderBackId() {
		return spiderBackId;
	}

	public void setSpiderBackId(Integer spiderBackId) {
		this.spiderBackId = spiderBackId;
	}

	public int getCreateType() {
		return createType;
	}

	public void setCreateType(int createType) {
		this.createType = createType;
	}

	public Integer getSpiderLanguageType() {
		return spiderLanguageType;
	}

	public void setSpiderLanguageType(Integer spiderLanguageType) {
		this.spiderLanguageType = spiderLanguageType;
	}

	public String getSpiderParams() {
		return spiderParams;
	}

	public void setSpiderParams(String spiderParams) {
		this.spiderParams = spiderParams;
	}

	@Override
	public String toString() {
		return "StartSpiderInfo [taskInfoId=" + taskInfoId + ", spiderBackId=" + spiderBackId + ", createType="
				+ createType + ", spiderLanguageType=" + spiderLanguageType + ", spiderParams=" + spiderParams
				+ ", customspider=" + customspider + "]";
	}

}
