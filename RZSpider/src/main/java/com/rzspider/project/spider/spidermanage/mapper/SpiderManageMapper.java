package com.rzspider.project.spider.spidermanage.mapper;

import java.util.List;

import com.rzspider.project.spider.spidermanage.domain.SpiderList;
import com.rzspider.project.spider.spidermanage.domain.SpiderManage;
import com.rzspider.project.system.role.domain.Role;

/**
 * 爬虫表 数据层
 * 
 * @author ricozhou
 */
public interface SpiderManageMapper {
	// 校验爬虫名称是否唯一
	public SpiderManage checkSpiderNameUnique(SpiderManage spiderManage);

	// 查询爬虫
	public List<SpiderManage> selectSpiderManageList(SpiderManage spiderManage);

	// 插入爬虫
	public int insertSpider(SpiderManage spiderManage);

	// 校验爬虫后台ID
	public SpiderManage checkSpiderBackIdUnique(SpiderManage spiderManage);

	// 通过ID查询爬虫
	public SpiderManage selectSpiderManageById(Long spiderId);

	// 修改爬虫
	public int updateSpiderManage(SpiderManage spiderManage);

	// 根据ID删除爬虫
	public int deleteSpiderManageById(Long spiderId);

	// 批量删除爬虫
	public int batchDeleteSpiderManage(Long[] ids);

	public SpiderManage selectSpiderManageByName(SpiderManage spiderManage);

	public List<SpiderManage> selectSpiderManageList2(SpiderManage spiderManage);

	public List<SpiderManage> selectSpiderManageList3(SpiderManage spiderManage);

	public SpiderManage selectSpiderListBySpiderId(Long spiderId);

	public SpiderManage selectSpiderListByTaskId(Integer taskId, String createName);

	public SpiderManage selectSpiderListByTaskId2(Integer taskId);

	public int updateSpidermanageParams(SpiderManage spiderManage);

}
