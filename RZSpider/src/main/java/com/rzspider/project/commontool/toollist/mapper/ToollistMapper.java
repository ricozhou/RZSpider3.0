package com.rzspider.project.commontool.toollist.mapper;

import com.rzspider.project.commontool.toollist.domain.Toollist;
import java.util.List;

/**
 * 通用工具列 数据层
 * 
 * @author ricozhou
 * @date 2018-06-13
 */
public interface ToollistMapper {

	/**
	 * 查询通用工具列信息
	 * 
	 * @param toolListId
	 *            通用工具列ID
	 * @return 通用工具列信息
	 */
	public Toollist selectToollistById(Integer toolListId);

	/**
	 * 查询通用工具列列表
	 * 
	 * @param toollist
	 *            通用工具列信息
	 * @return 通用工具列集合
	 */
	public List<Toollist> selectToollistList(Toollist toollist);

	/**
	 * 新增通用工具列
	 * 
	 * @param toollist
	 *            通用工具列信息
	 * @return 结果
	 */
	public int insertToollist(Toollist toollist);

	/**
	 * 修改通用工具列
	 * 
	 * @param toollist
	 *            通用工具列信息
	 * @return 结果
	 */
	public int updateToollist(Toollist toollist);

	/**
	 * 删除通用工具列
	 * 
	 * @param toolListId
	 *            通用工具列ID
	 * @return 结果
	 */
	public int deleteToollistById(Integer toolListId);

	/**
	 * 批量删除通用工具列
	 * 
	 * @param toolListIds
	 *            需要删除的数据ID
	 * @return 结果
	 */
	public int batchDeleteToollist(Integer[] toolListIds);

	// 校验工具昵称
	public Toollist checkToolNickNameUnique(Toollist toollist);

}