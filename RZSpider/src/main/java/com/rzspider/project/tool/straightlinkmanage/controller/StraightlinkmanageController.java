package com.rzspider.project.tool.straightlinkmanage.controller;

import java.io.File;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.tomcat.util.http.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.rzspider.project.common.file.utilt.FileUtils;
import com.rzspider.project.tool.straightlinkmanage.domain.Straightlinkmanage;
import com.rzspider.project.tool.straightlinkmanage.service.IStraightlinkmanageService;
import com.rzspider.common.constant.CommonConstant;
import com.rzspider.common.constant.CommonSymbolicConstant;
import com.rzspider.common.constant.FileExtensionConstant;
import com.rzspider.common.constant.FileMessageConstant;
import com.rzspider.common.constant.ReturnMessageConstant;
import com.rzspider.common.utils.FileUploadUtils;
import com.rzspider.framework.aspectj.lang.annotation.Log;
import com.rzspider.framework.config.FilePathConfig;
import com.rzspider.framework.web.controller.BaseController;
import com.rzspider.framework.web.page.TableDataInfo;
import com.rzspider.framework.web.domain.Message;

/**
 * 直链管理详情 信息操作处理
 * 
 * @author ricozhou
 * @date 2018-10-16
 */
@Controller
@RequestMapping("/tool/straightlinkmanage")
public class StraightlinkmanageController extends BaseController {
	private String prefix = "tool/straightlinkmanage";

	@Autowired
	private IStraightlinkmanageService straightlinkmanageService;

	@GetMapping()
	@RequiresPermissions("tool:straightlinkmanage:view")
	public String straightlinkmanage() {
		return prefix + "/straightlinkmanage";
	}

	/**
	 * 查询直链管理详情列表
	 */
	@RequiresPermissions("tool:straightlinkmanage:list")
	@GetMapping("/list")
	@ResponseBody
	public TableDataInfo list(Straightlinkmanage straightlinkmanage) {
		startPage();
		List<Straightlinkmanage> list = straightlinkmanageService.selectStraightlinkmanageList(straightlinkmanage);
		return getDataTable(list);
	}

	/**
	 * 新增直链管理详情
	 */
	@RequiresPermissions("tool:straightlinkmanage:add")
	@GetMapping("/add")
	public String add() {
		return prefix + "/add";
	}

	/**
	 * 修改直链管理详情
	 */
	@RequiresPermissions("tool:straightlinkmanage:edit")
	@GetMapping("/edit/{straightlinkmanageId}")
	public String edit(@PathVariable("straightlinkmanageId") Integer straightlinkmanageId, Model model) {
		Straightlinkmanage straightlinkmanage = straightlinkmanageService
				.selectStraightlinkmanageById(straightlinkmanageId);
		model.addAttribute("straightlinkmanage", straightlinkmanage);
		return prefix + "/edit";
	}

	/**
	 * 保存直链管理详情
	 */
	@RequiresPermissions("tool:straightlinkmanage:save")
	@PostMapping("/save")
	@ResponseBody
	public Message save(Straightlinkmanage straightlinkmanage) {
		if (straightlinkmanageService.saveStraightlinkmanage(straightlinkmanage) > 0) {
			return Message.success();
		}
		return Message.error();
	}

	/**
	 * 删除直链管理详情
	 */
	@RequiresPermissions("tool:straightlinkmanage:remove")
	@PostMapping("/remove/{straightlinkmanageId}")
	@ResponseBody
	public Message remove(@PathVariable("straightlinkmanageId") Integer straightlinkmanageId) {
		if (straightlinkmanageService.deleteStraightlinkmanageById(straightlinkmanageId) > 0) {
			return Message.success();
		}
		return Message.error();
	}

	/**
	 * 批量删除直链管理详情
	 */
	@RequiresPermissions("tool:straightlinkmanage:batchRemove")
	@PostMapping("/batchRemove")
	@ResponseBody
	public Message remove(@RequestParam("ids[]") Integer[] straightlinkmanageIds) {
		int rows = straightlinkmanageService.batchDeleteStraightlinkmanage(straightlinkmanageIds);
		if (rows > 0) {
			return Message.success();
		}
		return Message.error();
	}

	/**
	 * 上传文件
	 */
	@Log(title = "系统工具", action = "直链管理-文件上传")
	@RequiresPermissions("tool:straightlinkmanage:uploadFile")
	@ResponseBody
	@PostMapping("/uploadFile")
	public Message uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request,
			String straightlinkFileUrlKey) {
		// 更新
		// 判断文件大小,不超过500m
		try {
			FileUploadUtils.assertAllowedSetSize(file, FileUploadUtils.DEFAULT_MAX_SIZE_100M);
		} catch (FileSizeLimitExceededException e1) {
			e1.printStackTrace();
			return Message.error(FileMessageConstant.FILE_MESSAGE_SIZE_GREATER_ONE_HUNDRED_M);
		}
		// 原始名字
		String fileName = file.getOriginalFilename();
		// 重命名
		fileName = FileUploadUtils.renameToUUID(fileName);
		if (straightlinkFileUrlKey == null || CommonSymbolicConstant.EMPTY_STRING.equals(straightlinkFileUrlKey)
				|| CommonConstant.UNDEFINED.equals(straightlinkFileUrlKey)) {
			straightlinkFileUrlKey = String.valueOf(UUID.randomUUID());
		} else {
			// 验证 straightlinkFileUrlKey

		}
		// 每一个直链单独给一个文件夹
		try {
			FileUploadUtils.uploadFile(file.getBytes(),
					FilePathConfig.getStraightlinkManagePath() + File.separator + straightlinkFileUrlKey,
					file.getOriginalFilename());
			// 如果是压缩文件还要解压
			File file2 = new File(FilePathConfig.getStraightlinkManagePath() + File.separator + straightlinkFileUrlKey
					+ File.separator + file.getOriginalFilename());
			if (fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_COMPRESSEDFILE_ZIP)) {
				FileUtils.unZipFiles(file2,
						FilePathConfig.getStraightlinkManagePath() + File.separator + straightlinkFileUrlKey, false);
				file2.delete();
			}
			// 返回文件名
			Message message = new Message();
			message = message.success();
			message.put(ReturnMessageConstant.RETURN_MESSAGE_KEY_2, fileName);
			message.put(ReturnMessageConstant.RETURN_MESSAGE_KEY_6, straightlinkFileUrlKey);
			return message;
		} catch (Exception e) {
			return Message.error(FileMessageConstant.FILE_MESSAGE_FILE_UPLOAD_FAILED);
		}
	}

	/**
	 * 删除缓存文件夹
	 */
	@PostMapping("/deleteCacheFile")
	@ResponseBody
	public Message deleteCacheFile(String straightlinkFileUrlKey) {
		String path = FilePathConfig.getStraightlinkManagePath() + File.separator + straightlinkFileUrlKey;
		if (FileUtils.deleteFile(path)) {
			return Message.success();
		}
		return Message.error();
	}
}
