package com.rzspider.project.spider.spidertask.service;

import com.rzspider.project.spider.spidertask.domain.Spidertaskinfo;
import com.rzspider.project.spider.spidertask.mainwork.domain.StartSpiderInfo;

import java.util.List;

/**
 * 爬虫任务运行记录详情 服务层
 * 
 * @author ricozhou
 * @date 2018-06-05
 */
public interface ISpidertaskinfoService 
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
     * 保存爬虫任务运行记录详情
     * 
     * @param spidertaskinfo 爬虫任务运行记录详情信息
     * @return 结果
     */
	public int saveSpidertaskinfo(Spidertaskinfo spidertaskinfo);
	
	/**
     * 删除爬虫任务运行记录详情信息
     * 
     * @param taskInfoId 爬虫任务运行记录详情ID
     * @return 结果
     */
	public int deleteSpidertaskinfoById(Integer taskInfoId);
	
	/**
     * 批量删除爬虫任务运行记录详情信息
     * 
     * @param taskInfoIds 需要删除的数据ID
     * @return 结果
     */
	public int batchDeleteSpidertaskinfo(Integer[] taskInfoIds);

    //查看任务状态
	public int checkSpidertaskinfoStatus(Integer taskInfoId);

	//根据taskid查询
	public List<Spidertaskinfo> selectSpidertaskinfoByTaskId(Integer taskId);

	//启动
	public boolean start(StartSpiderInfo startSpiderInfo);

	//改变状态
	public int chanageSpidertaskinfoTaskStatus(Spidertaskinfo spidertaskinfo2);
	//中止
	public boolean stop(StartSpiderInfo startSpiderInfo);

	public int chanageSpidertaskinfoTaskStatus2(Spidertaskinfo spidertaskinfo);

	void firstExection(StartSpiderInfo startSpiderInfo);

	void finallyExection(StartSpiderInfo startSpiderInfo, Integer finishStatus);
	
}
