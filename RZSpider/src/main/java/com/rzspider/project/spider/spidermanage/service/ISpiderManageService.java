package com.rzspider.project.spider.spidermanage.service;

import java.util.List;
import java.util.Set;

import com.rzspider.project.spider.spidermanage.domain.SpiderList;
import com.rzspider.project.spider.spidermanage.domain.SpiderManage;
import com.rzspider.project.system.role.domain.Role;

/**
 * 爬虫业务层
 * 
 * @author ricozhou
 */
public interface ISpiderManageService {

	/**
	 * 根据条件分页查询角色数据
	 * 
	 * @param spiderManage
	 *            爬虫信息
	 * @return 爬虫数据集合信息
	 */
	public List<SpiderManage> selectSpiderManageList(SpiderManage spiderManage);

	/**
	 * 校验爬虫名称是否唯一
	 */
	public String checkSpiderNameUnique(SpiderManage spiderManage);

	// 爬虫后台ID是否唯一
	public String checkSpiderBackIdUnique(SpiderManage spiderManage);

	// 根据ID查询ID
	public SpiderManage selectSpiderManageById(Long spiderId);

	// 保存爬虫
	public int saveSpider(SpiderManage spiderManage);

	// 根据id删除爬虫
	public int deleteSpiderManageById(Long spiderId);

	// 批量删除ID
	public int batchDeleteSpiderManage(Long[] ids);

	public SpiderManage selectSpiderManageByName(SpiderManage spiderManage);

	public List<SpiderManage> selectSpiderManageList2(SpiderManage spiderManage);

	public SpiderManage checkSpiderExist(Long spiderId);

	public SpiderManage checkSpiderExist2(Integer taskId, String createName);

	public SpiderManage checkSpiderExist3(Integer taskId);

	public int saveSpidermanageParams(SpiderManage spiderManage);

}
