package com.rzspider.project.commontool.toolmanage.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rzspider.common.utils.StringUtils;
import com.rzspider.project.commontool.toolmanage.domain.Toolset;
import com.rzspider.project.commontool.toolmanage.mapper.ToolsetMapper;

/**
 * 通用工具管理工具设置 服务层实现
 * 
 * @author ricozhou
 * @date 2018-08-27
 */
@Service
public class ToolsetServiceImpl implements IToolsetService {
	@Autowired
	private ToolsetMapper toolsetMapper;

	/**
	 * 查询通用工具管理工具设置信息
	 * 
	 * @param id
	 *            通用工具管理工具设置ID
	 * @return 通用工具管理工具设置信息
	 */
	@Override
	public Toolset selectToolsetById(Integer id) {
		return toolsetMapper.selectToolsetById(id);
	}

	/**
	 * 保存通用工具管理工具设置
	 * 
	 * @param toolmanageToolset
	 *            通用工具管理工具设置信息
	 * @return 结果
	 */
	@Override
	public int ocrSaveToolset(Toolset toolset) {
		return toolsetMapper.updateOcrToolset(toolset);
	}
}
