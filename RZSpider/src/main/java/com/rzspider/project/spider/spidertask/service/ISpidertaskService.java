package com.rzspider.project.spider.spidertask.service;

import com.rzspider.project.spider.spidertask.domain.Spidertask;
import com.rzspider.project.spider.spidertask.domain.Spidertaskinfo;

import java.util.List;

/**
 * 爬虫任务详情 服务层
 * 
 * @author ricozhou
 * @date 2018-05-29
 */
public interface ISpidertaskService 
{
	
	/**
     * 查询爬虫任务详情信息
     * 
     * @param taskId 爬虫任务详情ID
     * @return 爬虫任务详情信息
     */
	public Spidertask selectSpidertaskById(Integer taskId);
	
	/**
     * 查询爬虫任务详情列表
     * 
     * @param spidertask 爬虫任务详情信息
     * @return 爬虫任务详情集合
     */
	public List<Spidertask> selectSpidertaskList(Spidertask spidertask);
	
	/**
     * 新增爬虫任务详情
     * 
     * @param spidertask 爬虫任务详情信息
     * @return 结果
     */
	public int insertSpidertask(Spidertask spidertask);
	
	/**
     * 修改爬虫任务详情
     * 
     * @param spidertask 爬虫任务详情信息
     * @return 结果
     */
	public int updateSpidertask(Spidertask spidertask);
	
	/**
     * 保存爬虫任务详情
     * 
     * @param spidertask 爬虫任务详情信息
     * @return 结果
     */
	public int saveSpidertask(Spidertask spidertask);
	
	/**
     * 删除爬虫任务详情信息
     * 
     * @param taskId 爬虫任务详情ID
     * @return 结果
     */
	public int deleteSpidertaskById(Integer taskId);
	
	/**
     * 批量删除爬虫任务详情信息
     * 
     * @param taskIds 需要删除的数据ID
     * @return 结果
     */
	public int batchDeleteSpidertask(Integer[] taskIds);

	public String checkTaskNameUnique(Spidertask spidertask);

	public int changeStatus(Spidertask spidertask);

	public int saveSpidertaskParams(Spidertask spidertask);

	//添加任务
	public int startTask(Spidertask spidertask);

	public Spidertaskinfo addTask2(Spidertask spidertask);

	public int pauseJob(Spidertask spidertask);

	public int resumeJob(Spidertask spidertask);
	
}
