package com.rzspider.project.tool.baseset.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rzspider.common.constant.UserConstants;
import com.rzspider.common.utils.StringUtils;
import com.rzspider.common.utils.security.ShiroUtils;
import com.rzspider.project.tool.baseset.mapper.BasesetMapper;
import com.rzspider.project.commontool.toolmanage.domain.Toolmanage;
import com.rzspider.project.tool.baseset.domain.Baseset;
import com.rzspider.project.tool.baseset.service.IBasesetService;

/**
 * 基础设置详情 服务层实现
 * 
 * @author ricozhou
 * @date 2018-06-23
 */
@Service
public class BasesetDetaileditServiceImpl implements IBasesetDetaileditService {
	@Autowired
	private BasesetMapper basesetMapper;

	/**
	 * 保存首页介绍示例详情
	 */
	@Override
	public int loginSetsave(Baseset baseset) {
		baseset.setUpdateBy(ShiroUtils.getLoginName());
		int rows = basesetMapper.updateLoginsetEdit(baseset);
		return rows;
	}

	/**
	 * 保存首页介绍示例详情
	 */
	@Override
	public int homeIntroductionSave(Baseset baseset) {
		baseset.setUpdateBy(ShiroUtils.getLoginName());
		int rows = basesetMapper.updateBasesetEdit(baseset);
		return rows;
	}

	/**
	 * 保存代码示例详情
	 */
	@Override
	public int spiderCodeExampleSave(Baseset baseset) {
		baseset.setUpdateBy(ShiroUtils.getLoginName());
		int rows = basesetMapper.updateBasesetEdit(baseset);
		return rows;
	}

	// 保存手册
	@Override
	public int spiderManualSave(Baseset baseset) {
		baseset.setUpdateBy(ShiroUtils.getLoginName());
		int rows = basesetMapper.updateManualEdit(baseset);
		return rows;
	}

}
