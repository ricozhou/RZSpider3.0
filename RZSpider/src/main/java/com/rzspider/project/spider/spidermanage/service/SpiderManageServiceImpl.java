package com.rzspider.project.spider.spidermanage.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rzspider.common.constant.UserConstants;
import com.rzspider.common.utils.OtherUtils;
import com.rzspider.common.utils.StringUtils;
import com.rzspider.common.utils.security.ShiroUtils;
import com.rzspider.project.spider.spidermanage.domain.SpiderList;
import com.rzspider.project.spider.spidermanage.domain.SpiderManage;
import com.rzspider.project.spider.spidermanage.mapper.SpiderManageMapper;
import com.rzspider.project.system.role.domain.Role;
import com.rzspider.project.system.role.domain.RoleMenu;
import com.rzspider.project.system.role.mapper.RoleMapper;
import com.rzspider.project.system.role.mapper.RoleMenuMapper;
import com.rzspider.project.system.role.service.IRoleService;
import com.rzspider.project.system.user.mapper.UserRoleMapper;

/**
 * 爬虫业务层处理
 * 
 * @author ricozhou
 */
@Service("spiderManageService")
public class SpiderManageServiceImpl implements ISpiderManageService {

	@Autowired
	private SpiderManageMapper spiderManageMapper;

	/**
	 * 根据条件分页查询爬虫数据
	 * 
	 */
	@Override
	public List<SpiderManage> selectSpiderManageList(SpiderManage spiderManage) {
//		spiderManage.setCreateBy(ShiroUtils.getLoginName());
		return spiderManageMapper.selectSpiderManageList2(spiderManage);
	}

	/**
	 * 根据条件分页查询爬虫数据
	 * 
	 */
	@Override
	public List<SpiderManage> selectSpiderManageList2(SpiderManage spiderManage) {
//		spiderManage.setCreateBy(ShiroUtils.getLoginName());
		// 查询可用
		spiderManage.setStatus(0);
		return spiderManageMapper.selectSpiderManageList3(spiderManage);
	}

	/**
	 * 校验爬虫名称是否唯一
	 * 
	 */
	@Override
	public String checkSpiderNameUnique(SpiderManage spiderManage) {
		if (spiderManage.getSpiderId() == null) {
			spiderManage.setSpiderId(-1L);
		}
		Long spiderId = spiderManage.getSpiderId();
//		spiderManage.setCreateBy(ShiroUtils.getLoginName());
		SpiderManage info = spiderManageMapper.checkSpiderNameUnique(spiderManage);
		if (StringUtils.isNotNull(info) && StringUtils.isNotNull(info.getSpiderId())
				&& info.getSpiderId() != spiderId) {
			return UserConstants.SPIDER_NAME_NOT_UNIQUE;
		}
		return UserConstants.SPIDER_NAME_UNIQUE;
	}

	// 爬虫后台ID是否唯一
	@Override
	public String checkSpiderBackIdUnique(SpiderManage spiderManage) {
		if (spiderManage.getSpiderId() == null) {
			spiderManage.setSpiderId(-1L);
		}
		Long spiderId = spiderManage.getSpiderId();
//		spiderManage.setCreateBy(ShiroUtils.getLoginName());
		SpiderManage info = spiderManageMapper.checkSpiderBackIdUnique(spiderManage);
		if (StringUtils.isNotNull(info) && StringUtils.isNotNull(info.getSpiderId())
				&& info.getSpiderId() != spiderId) {
			return UserConstants.SPIDER_BACK_ID_NOT_UNIQUE;
		}
		return UserConstants.SPIDER_BACK_ID_UNIQUE;
	}

	// 保存爬虫
	@Override
	public int saveSpider(SpiderManage spiderManage) {
		int rows = 1;
		Long spiderId = spiderManage.getSpiderId();
		if (StringUtils.isNotNull(spiderId)) {
			spiderManage.setUpdateBy(ShiroUtils.getLoginName());
			// // 修改爬虫信息
			rows = spiderManageMapper.updateSpiderManage(spiderManage);
		} else {
			spiderManage.setCreateBy(ShiroUtils.getLoginName());
			// 新增爬虫信息
			rows = spiderManageMapper.insertSpider(spiderManage);
		}
		// ShiroUtils.clearCachedAuthorizationInfo();
		return rows;
	}

	// 通过ID查询爬虫
	@Override
	public SpiderManage selectSpiderManageById(Long spiderId) {
		return spiderManageMapper.selectSpiderManageById(spiderId);
	}

	// 根据ID删除爬虫
	@Override
	public int deleteSpiderManageById(Long spiderId) {
		return spiderManageMapper.deleteSpiderManageById(spiderId);
	}

	// 批量删除爬虫
	@Override
	public int batchDeleteSpiderManage(Long[] ids) {
		return spiderManageMapper.batchDeleteSpiderManage(ids);
	}

	@Override
	public SpiderManage selectSpiderManageByName(SpiderManage spiderManage) {
		SpiderManage info = spiderManageMapper.selectSpiderManageByName(spiderManage);
		return info;
	}

	// 判断是否存在
	@Override
	public SpiderManage checkSpiderExist(Long spiderId) {
		return spiderManageMapper.selectSpiderListBySpiderId(spiderId);
	}

	// 判断是否存在(根据任务id)爬虫管理查询
	@Override
	public SpiderManage checkSpiderExist2(Integer taskId, String createName) {
		return spiderManageMapper.selectSpiderListByTaskId(taskId, createName);
	}

	// 判断是否存在(根据任务id)从总爬虫判断
	@Override
	public SpiderManage checkSpiderExist3(Integer taskId) {
		return spiderManageMapper.selectSpiderListByTaskId2(taskId);
	}

	//保存参数
	@Override
	public int saveSpidermanageParams(SpiderManage spiderManage) {
		return spiderManageMapper.updateSpidermanageParams(spiderManage);
	}

}
