package com.rzspider.project.commontool.toolmanage.mapper;

import com.rzspider.project.commontool.toolmanage.domain.Toolmanage;
import java.util.List;

/**
 * 通用工具管理 数据层
 * 
 * @author ricozhou
 * @date 2018-06-13
 */
public interface ToolmanageMapper {

	/**
	 * 查询通用工具管理信息
	 * 
	 * @param toolId
	 *            通用工具管理ID
	 * @return 通用工具管理信息
	 */
	public Toolmanage selectToolmanageById(Integer toolId);

	/**
	 * 查询通用工具管理列表
	 * 
	 * @param toolmanage
	 *            通用工具管理信息
	 * @return 通用工具管理集合
	 */
	public List<Toolmanage> selectToolmanageList(Toolmanage toolmanage);

	/**
	 * 新增通用工具管理
	 * 
	 * @param toolmanage
	 *            通用工具管理信息
	 * @return 结果
	 */
	public int insertToolmanage(Toolmanage toolmanage);

	/**
	 * 修改通用工具管理
	 * 
	 * @param toolmanage
	 *            通用工具管理信息
	 * @return 结果
	 */
	public int updateToolmanage(Toolmanage toolmanage);

	/**
	 * 删除通用工具管理
	 * 
	 * @param toolId
	 *            通用工具管理ID
	 * @return 结果
	 */
	public int deleteToolmanageById(Integer toolId);

	/**
	 * 批量删除通用工具管理
	 * 
	 * @param toolIds
	 *            需要删除的数据ID
	 * @return 结果
	 */
	public int batchDeleteToolmanage(Integer[] toolIds);

	// 校验工具名称
	public Toolmanage checkToolNameUnique(Toolmanage toolmanage);

	// 可用工具
	public List<Toolmanage> selectAllToolNameByStatus(Integer status);
	// 校验工具后台id
	public Toolmanage checktoolBackIdUnique(Toolmanage toolmanage);

	public Toolmanage selectToolmanageByToolName(String toolName);

	public Toolmanage selectToolmanageByToolBackId(Integer toolBackId);

	public List<Toolmanage> selectToolmanageListByStatusAndToolType(int status, int toolType);

}