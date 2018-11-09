package com.rzspider.project.spider.spidermanage.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rzspider.common.constant.UserConstants;
import com.rzspider.common.utils.StringUtils;
import com.rzspider.project.spider.spidermanage.mapper.SpiderListMapper;
import com.rzspider.project.spider.customspider.domain.Customspider;
import com.rzspider.project.spider.spidermanage.domain.SpiderList;
import com.rzspider.project.spider.spidermanage.domain.SpiderManage;
import com.rzspider.project.spider.spidermanage.service.ISpiderListService;
import com.rzspider.project.spider.spidertask.utils.SpidertaskUtils;

/**
 * 总爬虫详情 服务层实现
 * 
 * @author ricozhou
 * @date 2018-06-02
 */
@Service
public class SpiderListServiceImpl implements ISpiderListService {
	@Autowired
	private SpiderListMapper spiderListMapper;

	/**
	 * 查询总爬虫详情信息
	 * 
	 * @param spiderId
	 *            总爬虫详情ID
	 * @return 总爬虫详情信息
	 */
	@Override
	public SpiderList selectSpiderListById(Integer spiderId) {
		return spiderListMapper.selectSpiderListById(spiderId);
	}

	/**
	 * 查询总爬虫详情信息
	 * 
	 * @param spiderId
	 *            总爬虫详情ID
	 * @return 总爬虫详情信息
	 */
	@Override
	public SpiderList selectSpiderListBySpiderBackId(Integer spiderBackId) {
		return spiderListMapper.selectSpiderListBySpiderBackId(spiderBackId);
	}

	/**
	 * 查询总爬虫详情列表
	 * 
	 * @param spiderList
	 *            总爬虫详情信息
	 * @return 总爬虫详情集合
	 */
	@Override
	public List<SpiderList> selectSpiderListList(SpiderList spiderList) {
		return spiderListMapper.selectSpiderListList(spiderList);
	}

	/**
	 * 新增总爬虫详情
	 * 
	 * @param spiderList
	 *            总爬虫详情信息
	 * @return 结果
	 */
	@Override
	public int insertSpiderList(SpiderList spiderList) {
		return spiderListMapper.insertSpiderList(spiderList);
	}

	/**
	 * 修改总爬虫详情
	 * 
	 * @param spiderList
	 *            总爬虫详情信息
	 * @return 结果
	 */
	@Override
	public int updateSpiderList(SpiderList spiderList) {
		return spiderListMapper.updateSpiderList(spiderList);
	}

	/**
	 * 保存总爬虫详情
	 * 
	 * @param spiderList
	 *            总爬虫详情信息
	 * @return 结果
	 */
	@Override
	public int saveSpiderList(SpiderList spiderList) {
		Integer spiderId = spiderList.getSpiderId();
		int rows = 0;
		if (StringUtils.isNotNull(spiderId)) {
			rows = spiderListMapper.updateSpiderList(spiderList);
		} else {
			rows = spiderListMapper.insertSpiderList(spiderList);
		}
		return rows;
	}

	/**
	 * 删除总爬虫详情信息
	 * 
	 * @param spiderId
	 *            总爬虫详情ID
	 * @return 结果
	 */
	@Override
	public int deleteSpiderListById(Integer spiderId) {
		return spiderListMapper.deleteSpiderListById(spiderId);
	}

	/**
	 * 批量删除总爬虫详情对象
	 * 
	 * @param spiderIds
	 *            需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int batchDeleteSpiderList(Integer[] spiderIds) {
		return spiderListMapper.batchDeleteSpiderList(spiderIds);
	}

	// 校验后台ID
	@Override
	public String checkSpiderBackIdUnique(Customspider customspider) {
		if (customspider.getCustomSpiderId() == null) {
			customspider.setCustomSpiderId(-1);
		}
		Integer customSpiderId = customspider.getCustomSpiderId();
		SpiderList info = spiderListMapper.selectSpiderListBySpiderBackId(customspider.getCustomSpiderBackId());
		if (StringUtils.isNotNull(info) && StringUtils.isNotNull(info.getSpiderId())
				&& info.getCustomSpiderId() != customSpiderId) {
			return UserConstants.SPIDER_BACK_ID_NOT_UNIQUE;
		}
		return UserConstants.SPIDER_BACK_ID_UNIQUE;
	}

	@Override
	public List<SpiderList> selectSpiderListListByStatus(Integer status) {
		List<SpiderList> list = spiderListMapper.selectSpiderListListByStatus(status);
		for (SpiderList sList : list) {
			sList.setSpiderDefaultParams(
					SpidertaskUtils.formatJson(StringUtils.getNotNullString(sList.getSpiderDefaultParams())));
		}
		return list;
	}

}
