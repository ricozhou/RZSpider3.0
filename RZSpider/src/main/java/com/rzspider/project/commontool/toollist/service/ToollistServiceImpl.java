package com.rzspider.project.commontool.toollist.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rzspider.common.constant.CommonSymbolicConstant;
import com.rzspider.common.constant.UserConstants;
import com.rzspider.common.utils.OtherUtils;
import com.rzspider.common.utils.StringUtils;
import com.rzspider.common.utils.security.ShiroUtils;
import com.rzspider.framework.config.FilePathConfig;
import com.rzspider.project.commontool.toollist.mapper.ToollistMapper;
import com.rzspider.project.book.bookmanage.utils.ExcelUtils;
import com.rzspider.project.commontool.toolmanage.domain.Toolmanage;
import com.rzspider.project.commontool.toollist.domain.Toollist;
import com.rzspider.project.commontool.toollist.service.IToollistService;
import com.rzspider.project.commontool.toolmanage.domain.Toolmanage;
import com.rzspider.project.system.role.service.IRoleService;

/**
 * 通用工具列 服务层实现
 * 
 * @author ricozhou
 * @date 2018-06-13
 */
@Service
public class ToollistServiceImpl implements IToollistService {
	@Autowired
	private ToollistMapper toollistMapper;

	/**
	 * 查询通用工具列信息
	 * 
	 * @param toolListId
	 *            通用工具列ID
	 * @return 通用工具列信息
	 */
	@Override
	public Toollist selectToollistById(Integer toolListId) {
		return toollistMapper.selectToollistById(toolListId);
	}

	/**
	 * 查询通用工具列列表
	 * 
	 * @param toollist
	 *            通用工具列信息
	 * @return 通用工具列集合
	 */
	@Override
	public List<Toollist> selectToollistList(Toollist toollist) {
		toollist.setCreateBy(ShiroUtils.getLoginName());
		return toollistMapper.selectToollistList(toollist);
	}

	/**
	 * 新增通用工具列
	 * 
	 * @param toollist
	 *            通用工具列信息
	 * @return 结果
	 */
	@Override
	public int insertToollist(Toollist toollist) {
		return toollistMapper.insertToollist(toollist);
	}

	/**
	 * 修改通用工具列
	 * 
	 * @param toollist
	 *            通用工具列信息
	 * @return 结果
	 */
	@Override
	public int updateToollist(Toollist toollist) {
		return toollistMapper.updateToollist(toollist);
	}

	/**
	 * 保存通用工具列
	 * 
	 * @param toollist
	 *            通用工具列信息
	 * @return 结果
	 */
	@Override
	public int saveToollist(Toollist toollist) {
		Integer toolListId = toollist.getToolListId();
		int rows = 0;
		if (StringUtils.isNotNull(toolListId)) {
			toollist.setUpdateBy(ShiroUtils.getLoginName());
			rows = toollistMapper.updateToollist(toollist);
		} else {
			toollist.setCreateBy(ShiroUtils.getLoginName());
			rows = toollistMapper.insertToollist(toollist);
		}
		return rows;
	}

	/**
	 * 删除通用工具列信息
	 * 
	 * @param toolListId
	 *            通用工具列ID
	 * @return 结果
	 */
	@Override
	public int deleteToollistById(Integer toolListId) {
		return toollistMapper.deleteToollistById(toolListId);
	}

	/**
	 * 批量删除通用工具列对象
	 * 
	 * @param toolListIds
	 *            需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int batchDeleteToollist(Integer[] toolListIds) {
		return toollistMapper.batchDeleteToollist(toolListIds);
	}

	// 校验工具昵称
	@Override
	public String checkToolNickNameUnique(Toollist toollist) {
		if (toollist.getToolListId() == null) {
			toollist.setToolListId(-1);
		}
		Integer toolListId = toollist.getToolListId();
		Toollist info = toollistMapper.checkToolNickNameUnique(toollist);
		if (StringUtils.isNotNull(info) && StringUtils.isNotNull(info.getToolListId())
				&& info.getToolListId() != toolListId) {
			return UserConstants.TOOL_NICK_NAME_NOT_UNIQUE;
		}
		return UserConstants.TOOL_NICK_NAME_UNIQUE;
	}

	// 获取文件名
	@Override
	public String getFileName(Toolmanage toolmanage, String string) {
		String fileName;
		String basePath = FilePathConfig.getUploadToolPath() + File.separator + toolmanage.getToolBackId();
		File file = new File(basePath);
		// 所有文件名
		File[] files = file.listFiles();
		// 遍历查询
		String filesuffix = CommonSymbolicConstant.EMPTY_STRING;
		for (File file2 : files) {
			if (file2.isFile()) {
				if (file2.getName()
						.startsWith((toolmanage.getToolName() + CommonSymbolicConstant.UNDERLINE + string))) {
					// 取得后缀
					filesuffix = file2.getName()
							.substring(file2.getName().lastIndexOf(CommonSymbolicConstant.POINT) + 1);
					break;
				}
			}
		}
		if (CommonSymbolicConstant.EMPTY_STRING.equals(filesuffix)) {
			return null;
		}
		fileName = toolmanage.getToolName() + CommonSymbolicConstant.UNDERLINE + string + CommonSymbolicConstant.POINT
				+ filesuffix;
		return fileName;
	}

	// 下载取得数据
	@Override
	public byte[] downToolFile(Toolmanage toolmanage, String fileName) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		// 读取文件放入byte流中
		String filePath = FilePathConfig.getUploadToolPath() + File.separator + toolmanage.getToolBackId()
				+ File.separator + fileName;
		File file = new File(filePath);
		if (!file.exists() || !file.isFile()) {
			return null;
		}
		try {
			int length = 0;
			FileInputStream inputStream = new FileInputStream(file);
			byte[] buffer = new byte[100];
			while ((length = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, length);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outputStream.toByteArray();
	}

}
