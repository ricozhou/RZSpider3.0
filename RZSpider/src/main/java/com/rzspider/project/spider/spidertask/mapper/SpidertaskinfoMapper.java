package com.rzspider.project.spider.spidertask.mapper;

import com.rzspider.project.spider.spidertask.domain.Spidertaskinfo;
import java.util.List;	

/**
 * 爬虫任务运行记录详情 数据层
 * 
 * @author ricozhou
 * @date 2018-06-05
 */
public interface SpidertaskinfoMapper 
{

	/**
     * 查询爬虫任务运行记录详情信息
     * 
     * @param taskInfoId 爬虫任务运行记录详情ID
     * @return 爬虫任务运行记录详情信息
     */
	public Spidertaskinfo selectSpidertaskinfoById(Integer taskInfoId);
	
	/**
     * 查询爬虫任务运行记录详情列表
     * 
     * @param spidertaskinfo 爬虫任务运行记录详情信息
     * @return 爬虫任务运行记录详情集合
     */
	public List<Spidertaskinfo> selectSpidertaskinfoList(Spidertaskinfo spidertaskinfo);
	
	/**
     * 新增爬虫任务运行记录详情
     * 
     * @param spidertaskinfo 爬虫任务运行记录详情信息
     * @return 结果
     */
	public int insertSpidertaskinfo(Spidertaskinfo spidertaskinfo);
	
	/**
     * 修改爬虫任务运行记录详情
     * 
     * @param spidertaskinfo 爬虫任务运行记录详情信息
     * @return 结果
     */
	public int updateSpidertaskinfo(Spidertaskinfo spidertaskinfo);
	
	/**
     * 删除爬虫任务运行记录详情
     * 
     * @param taskInfoId 爬虫任务运行记录详情ID
     * @return 结果
     */
	public int deleteSpidertaskinfoById(Integer taskInfoId);
	
	/**
     * 批量删除爬虫任务运行记录详情
     * 
     * @param taskInfoIds 需要删除的数据ID
     * @return 结果
     */
	public int batchDeleteSpidertaskinfo(Integer[] taskInfoIds);

	//根据id查询
	public List<Spidertaskinfo> selectSpidertaskinfoByTaskId(Integer taskId);

	//改变状态
	public int updateSpidertaskinfoTaskStatus(Spidertaskinfo spidertaskinfo2);

	public int updateSpidertaskinfoTaskStatus2(Spidertaskinfo spidertaskinfo);

	public int insertSpidertaskinfo2(Spidertaskinfo spidertaskinfo);
	
}