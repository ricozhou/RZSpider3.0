package com.rzspider.project.spider.spidermanage.service;

import com.rzspider.project.spider.customspider.domain.Customspider;
import com.rzspider.project.spider.spidermanage.domain.SpiderList;
import java.util.List;

/**
 * 总爬虫详情 服务层
 * 
 * @author ricozhou
 * @date 2018-06-02
 */
public interface ISpiderListService 
{
	
	/**
     * 查询总爬虫详情信息
     * 
     * @param spiderId 总爬虫详情ID
     * @return 总爬虫详情信息
     */
	public SpiderList selectSpiderListById(Integer spiderId);
	
	/**
     * 查询总爬虫详情列表
     * 
     * @param spiderList 总爬虫详情信息
     * @return 总爬虫详情集合
     */
	public List<SpiderList> selectSpiderListList(SpiderList spiderList);
	
	/**
     * 新增总爬虫详情
     * 
     * @param spiderList 总爬虫详情信息
     * @return 结果
     */
	public int insertSpiderList(SpiderList spiderList);
	
	/**
     * 修改总爬虫详情
     * 
     * @param spiderList 总爬虫详情信息
     * @return 结果
     */
	public int updateSpiderList(SpiderList spiderList);
	
	/**
     * 保存总爬虫详情
     * 
     * @param spiderList 总爬虫详情信息
     * @return 结果
     */
	public int saveSpiderList(SpiderList spiderList);
	
	/**
     * 删除总爬虫详情信息
     * 
     * @param spiderId 总爬虫详情ID
     * @return 结果
     */
	public int deleteSpiderListById(Integer spiderId);
	
	/**
     * 批量删除总爬虫详情信息
     * 
     * @param spiderIds 需要删除的数据ID
     * @return 结果
     */
	public int batchDeleteSpiderList(Integer[] spiderIds);

	public String checkSpiderBackIdUnique(Customspider customspider);

	public List<SpiderList> selectSpiderListListByStatus(Integer status);

	public SpiderList selectSpiderListBySpiderBackId(Integer spiderBackId);
	
}
