package com.rzspider.project.commontool.toollist.service;

import com.rzspider.project.commontool.toollist.domain.Toollist;
import com.rzspider.project.commontool.toolmanage.domain.Toolmanage;

import java.util.List;

/**
 * 通用工具列 服务层
 * 
 * @author ricozhou
 * @date 2018-06-13
 */
public interface IToollistService {

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
	 * 保存通用工具列
	 * 
	 * @param toollist
	 *            通用工具列信息
	 * @return 结果
	 */
	public int saveToollist(Toollist toollist);

	/**
	 * 删除通用工具列信息
	 * 
	 * @param toolListId
	 *            通用工具列ID
	 * @return 结果
	 */
	public int deleteToollistById(Integer toolListId);

	/**
	 * 批量删除通用工具列信息
	 * 
	 * @param toolListIds
	 *            需要删除的数据ID
	 * @return 结果
	 */
	public int batchDeleteToollist(Integer[] toolListIds);

	// 校验工具昵称
	public String checkToolNickNameUnique(Toollist toollist);

//	public String getFileName(Toolmanage toolmanage, String string);

	public byte[] downToolFile(Toolmanage toolmanage, String setupexeFileName);

	String getFileName(Toolmanage toolmanage, String string);

}
