package com.rzspider.project.commontool.toolmanage.service;

import java.io.File;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rzspider.common.constant.FileExtensionConstant;
import com.rzspider.common.constant.FileMessageConstant;
import com.rzspider.common.constant.UserConstants;
import com.rzspider.common.utils.StringUtils;
import com.rzspider.common.utils.security.ShiroUtils;
import com.rzspider.framework.config.FilePathConfig;
import com.rzspider.framework.web.domain.Message;
import com.rzspider.project.commontool.toolmanage.mapper.ToolmanageMapper;
import com.rzspider.project.common.file.utilt.FileUtils;
import com.rzspider.project.commontool.toolmanage.domain.Toolmanage;
import com.rzspider.project.commontool.toolmanage.service.IToolmanageService;
import com.rzspider.project.spider.spidermanage.domain.SpiderManage;

/**
 * 通用工具管理 服务层实现
 * 
 * @author ricozhou
 * @date 2018-06-13
 */
@Service
public class ToolmanageServiceImpl implements IToolmanageService {
	@Autowired
	private ToolmanageMapper toolmanageMapper;

	/**
	 * 查询通用工具管理信息
	 * 
	 * @param toolId
	 *            通用工具管理ID
	 * @return 通用工具管理信息
	 */
	@Override
	public Toolmanage selectToolmanageById(Integer toolId) {
		return toolmanageMapper.selectToolmanageById(toolId);
	}

	/**
	 * 查询通用工具管理列表
	 * 
	 * @param toolmanage
	 *            通用工具管理信息
	 * @return 通用工具管理集合
	 */
	@Override
	public List<Toolmanage> selectToolmanageList(Toolmanage toolmanage) {
		return toolmanageMapper.selectToolmanageList(toolmanage);
	}

	/**
	 * 新增通用工具管理
	 * 
	 * @param toolmanage
	 *            通用工具管理信息
	 * @return 结果
	 */
	@Override
	public int insertToolmanage(Toolmanage toolmanage) {
		return toolmanageMapper.insertToolmanage(toolmanage);
	}

	/**
	 * 修改通用工具管理
	 * 
	 * @param toolmanage
	 *            通用工具管理信息
	 * @return 结果
	 */
	@Override
	public int updateToolmanage(Toolmanage toolmanage) {
		return toolmanageMapper.updateToolmanage(toolmanage);
	}

	/**
	 * 保存通用工具管理
	 * 
	 * @param toolmanage
	 *            通用工具管理信息
	 * @return 结果
	 */
	@Override
	public int saveToolmanage(Toolmanage toolmanage) {
		Integer toolId = toolmanage.getToolId();
		int rows = 0;
		if (StringUtils.isNotNull(toolId)) {
			toolmanage.setUpdateBy(ShiroUtils.getLoginName());
			rows = toolmanageMapper.updateToolmanage(toolmanage);
		} else {
			toolmanage.setCreateBy(ShiroUtils.getLoginName());
			rows = toolmanageMapper.insertToolmanage(toolmanage);
		}
		return rows;
	}

	/**
	 * 删除通用工具管理信息
	 * 
	 * @param toolId
	 *            通用工具管理ID
	 * @return 结果
	 */
	@Override
	public int deleteToolmanageById(Integer toolId) {
		return toolmanageMapper.deleteToolmanageById(toolId);
	}

	/**
	 * 批量删除通用工具管理对象
	 * 
	 * @param toolIds
	 *            需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int batchDeleteToolmanage(Integer[] toolIds) {
		return toolmanageMapper.batchDeleteToolmanage(toolIds);
	}

	// 校验工具名称
	@Override
	public String checkToolNameUnique(Toolmanage toolmanage) {
		if (toolmanage.getToolId() == null) {
			toolmanage.setToolId(-1);
		}
		Integer toolId = toolmanage.getToolId();
		Toolmanage info = toolmanageMapper.checkToolNameUnique(toolmanage);
		if (StringUtils.isNotNull(info) && StringUtils.isNotNull(info.getToolId()) && info.getToolId() != toolId) {
			return UserConstants.TOOL_NAME_NOT_UNIQUE;
		}
		return UserConstants.TOOL_NAME_UNIQUE;
	}

	// 校验工具后台id
	@Override
	public String checktoolBackIdUnique(Toolmanage toolmanage) {
		if (toolmanage.getToolId() == null) {
			toolmanage.setToolId(-1);
		}
		Integer toolId = toolmanage.getToolId();
		Toolmanage info = toolmanageMapper.checktoolBackIdUnique(toolmanage);
		if (StringUtils.isNotNull(info) && StringUtils.isNotNull(info.getToolId()) && info.getToolId() != toolId) {
			return UserConstants.TOOL_BACK_ID_NOT_UNIQUE;
		}
		return UserConstants.TOOL_BACK_ID_UNIQUE;
	}

	// 可用工具
	@Override
	public List<Toolmanage> selectAllToolNameByStatus(Integer status) {
		return toolmanageMapper.selectAllToolNameByStatus(status);
	}

	// 统一处理
	@Override
	public Message uploadToolFile(MultipartFile file, Toolmanage toolmanage) {
		// 判断文件大小，格式等等
		try {
			FileUtils.assertAllowed(file);
		} catch (FileSizeLimitExceededException e1) {
			e1.printStackTrace();
			return Message.error(FileMessageConstant.FILE_MESSAGE_SIZE_GREATER_HUNDRED_M);
		}
		// 原始名字
		String fileName = file.getOriginalFilename();
		if (!fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_COMPRESSEDFILE_ZIP)
				&& !fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_COMPRESSEDFILE_RAR)
				&& !fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_COMPRESSEDFILE_7Z)) {
			return Message.error(FileMessageConstant.FILE_MESSAGE_FORMAT_INCORRENT);
		}
		return Message.success();
	}

	// （更换思路，不再重命名，下载的时候在重命名）
	// 转移文件
	@Override
	public boolean copyToolFileToFolder(Toolmanage toolmanage) {
		String basePath1 = FilePathConfig.getUploadToolPath() + File.separator + toolmanage.getToolBackId();
		String basePath2 = FilePathConfig.getUploadToolPath();

		if (toolmanage.getSrcFileName() != null && !toolmanage.getSrcFileName().isEmpty()) {
			if (!FileUtils.copyFileToFolder(basePath2 + File.separator + toolmanage.getSrcFileName(),
					basePath1 + File.separator + toolmanage.getSrcFileName())) {
				return false;
			}
			if (StringUtils.isNotNull(toolmanage.getToolId())) {
				// 先把以前的文件如果存在则删除先
				// 查询原名
				Toolmanage toolmanage2 = toolmanageMapper.selectToolmanageById(toolmanage.getToolId());
				// 删除文件
				if (!FileUtils.deleteFile(basePath1 + File.separator + toolmanage2.getSrcFileName())) {
					return false;
				}
			}

		}
		if (toolmanage.getExecexeFileName() != null && !toolmanage.getExecexeFileName().isEmpty()) {
			if (!FileUtils.copyFileToFolder(basePath2 + File.separator + toolmanage.getExecexeFileName(),
					basePath1 + File.separator + toolmanage.getExecexeFileName())) {
				return false;
			}
			if (StringUtils.isNotNull(toolmanage.getToolId())) {
				// 先把以前的文件如果存在则删除先
				// 查询原名
				Toolmanage toolmanage2 = toolmanageMapper.selectToolmanageById(toolmanage.getToolId());
				// 删除文件
				if (!FileUtils.deleteFile(basePath1 + File.separator + toolmanage2.getExecexeFileName())) {
					return false;
				}
			}
		}
		if (toolmanage.getSetupexeName() != null && !toolmanage.getSetupexeName().isEmpty()) {
			if (!FileUtils.copyFileToFolder(basePath2 + File.separator + toolmanage.getSetupexeName(),
					basePath1 + File.separator + toolmanage.getSetupexeName())) {
				return false;
			}
			if (StringUtils.isNotNull(toolmanage.getToolId())) {
				// 先把以前的文件如果存在则删除先
				// 查询原名
				Toolmanage toolmanage2 = toolmanageMapper.selectToolmanageById(toolmanage.getToolId());
				// 删除文件
				if (!FileUtils.deleteFile(basePath1 + File.separator + toolmanage2.getSetupexeName())) {
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public Toolmanage selectToolmanageByToolName(String toolName) {
		return toolmanageMapper.selectToolmanageByToolName(toolName);
	}

	@Override
	public Toolmanage selectToolmanageByToolBackId(Integer toolBackId) {
		return toolmanageMapper.selectToolmanageByToolBackId(toolBackId);
	}

	// 查询可用
	@Override
	public List<Toolmanage> selectToolmanageListByStatusAndToolType(int status, int toolType) {
		return toolmanageMapper.selectToolmanageListByStatusAndToolType(status,toolType);
	}

}
