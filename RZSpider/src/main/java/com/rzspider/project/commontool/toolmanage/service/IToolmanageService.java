package com.rzspider.project.commontool.toolmanage.service;

import com.rzspider.framework.web.domain.Message;
import com.rzspider.project.commontool.toolmanage.domain.Toolmanage;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/**
 * 通用工具管理 服务层
 * 
 * @author ricozhou
 * @date 2018-06-13
 */
public interface IToolmanageService {

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
	 * 保存通用工具管理
	 * 
	 * @param toolmanage
	 *            通用工具管理信息
	 * @return 结果
	 */
	public int saveToolmanage(Toolmanage toolmanage);

	/**
	 * 删除通用工具管理信息
	 * 
	 * @param toolId
	 *            通用工具管理ID
	 * @return 结果
	 */
	public int deleteToolmanageById(Integer toolId);

	/**
	 * 批量删除通用工具管理信息
	 * 
	 * @param toolIds
	 *            需要删除的数据ID
	 * @return 结果
	 */
	public int batchDeleteToolmanage(Integer[] toolIds);

	// 校验工具名称
	public String checkToolNameUnique(Toolmanage toolmanage);

	// 可用工具
	public List<Toolmanage> selectAllToolNameByStatus(Integer status);

	// 校验工具后台id
	public String checktoolBackIdUnique(Toolmanage toolmanage);

	public Message uploadToolFile(MultipartFile file, Toolmanage toolmanage);

	// 转移文件
	public boolean copyToolFileToFolder(Toolmanage toolmanage);

	// 更新文件名
//	public boolean updateToolFileName(Toolmanage toolmanage, Toolmanage toolmanage2);

	public Toolmanage selectToolmanageByToolName(String toolName);

	public Toolmanage selectToolmanageByToolBackId(Integer toolBackId);

	public List<Toolmanage> selectToolmanageListByStatusAndToolType(int status,int toolType);

}
