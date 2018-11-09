package com.rzspider.project.spider.spidertask.mapper;

import com.rzspider.project.spider.spidertask.domain.Spidertask;
import java.util.List;	

/**
 * 爬虫任务详情 数据层
 * 
 * @author ricozhou
 * @date 2018-05-29
 */
public interface SpidertaskMapper 
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
     * 删除爬虫任务详情
     * 
     * @param taskId 爬虫任务详情ID
     * @return 结果
     */
	public int deleteSpidertaskById(Integer taskId);
	
	/**
     * 批量删除爬虫任务详情
     * 
     * @param taskIds 需要删除的数据ID
     * @return 结果
     */
	public int batchDeleteSpidertask(Integer[] taskIds);

	public Spidertask checkTaskNameUnique(Spidertask spidertask);

	public int updateSpidertaskParams(Spidertask spidertask);

	public int updateSpiderJobStatus(Spidertask spidertask);

	public int updateSpidertaskCronExpression(Spidertask spidertask);
	
}