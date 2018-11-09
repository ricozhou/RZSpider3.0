package com.rzspider.project.common.spiderdata.domain;

import com.rzspider.framework.web.domain.BaseEntity;

/**
 * 爬虫数据详情表 spider_data
 * 
 * @author ricozhou
 * @date 2018-06-11
 */
public class Spiderdata extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private Integer spiderDataId;
	/**  */
	private Integer taskInfoId;
	/**  */
	private String jsonData;
	/**  */
	private Integer taskFlag;

	/**
	 * 设置：
	 */
	public void setSpiderdataId(Integer spiderDataId) 
	{
		this.spiderDataId = spiderDataId;
	}
	
	/**
	 * 获取：
	 */
	public Integer getSpiderdataId() 
	{
		return spiderDataId;
	}
	
	/**
	 * 设置：
	 */
	public void setTaskInfoId(Integer taskInfoId) 
	{
		this.taskInfoId = taskInfoId;
	}
	
	/**
	 * 获取：
	 */
	public Integer getTaskInfoId() 
	{
		return taskInfoId;
	}
	
	/**
	 * 设置：
	 */
	public void setJsonData(String jsonData) 
	{
		this.jsonData = jsonData;
	}
	
	/**
	 * 获取：
	 */
	public String getJsonData() 
	{
		return jsonData;
	}
	
	/**
	 * 设置：
	 */
	public void setTaskFlag(Integer taskFlag) 
	{
		this.taskFlag = taskFlag;
	}
	
	/**
	 * 获取：
	 */
	public Integer getTaskFlag() 
	{
		return taskFlag;
	}

	@Override
	public String toString() {
		return "Spiderdata [spiderDataId=" + spiderDataId + ", taskInfoId=" + taskInfoId + ", jsonData=" + jsonData
				+ ", taskFlag=" + taskFlag + "]";
	}
	
}
