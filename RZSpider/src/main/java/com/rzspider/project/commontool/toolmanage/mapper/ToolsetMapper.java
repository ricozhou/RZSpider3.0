package com.rzspider.project.commontool.toolmanage.mapper;

import com.rzspider.project.commontool.toolmanage.domain.Toolset;
import java.util.List;

/**
 * 通用工具管理工具设置 数据层
 * 
 * @author ricozhou
 * @date 2018-08-27
 */
public interface ToolsetMapper {

	/**
	 * 查询通用工具管理工具设置信息
	 * 
	 * @param id
	 *            通用工具管理工具设置ID
	 * @return 通用工具管理工具设置信息
	 */
	public Toolset selectToolsetById(Integer id);

	/**
	 * 修改通用工具管理工具设置
	 * 
	 * @param toolset
	 *            通用工具管理工具设置信息
	 * @return 结果
	 */
	public int updateOcrToolset(Toolset toolset);

}