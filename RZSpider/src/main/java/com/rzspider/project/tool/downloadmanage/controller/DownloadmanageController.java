package com.rzspider.project.tool.downloadmanage.controller;

import java.util.List;

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

import com.rzspider.project.tool.downloadmanage.domain.Downloadmanage;
import com.rzspider.project.tool.downloadmanage.service.IDownloadmanageService;
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
 * 下载管理详情 信息操作处理
 * 
 * @author ricozhou
 * @date 2018-09-07
 */
@Controller
@RequestMapping("/tool/downloadmanage")
public class DownloadmanageController extends BaseController {
	private String prefix = "tool/downloadmanage";

	@Autowired
	private IDownloadmanageService downloadmanageService;

	@GetMapping()
	@RequiresPermissions("tool:downloadmanage:view")
	public String downloadmanage() {
		return prefix + "/downloadmanage";
	}

	/**
	 * 查询下载管理详情列表
	 */
	@RequiresPermissions("tool:downloadmanage:list")
	@GetMapping("/list")
	@ResponseBody
	public TableDataInfo list(Downloadmanage downloadmanage) {
		startPage();
		List<Downloadmanage> list = downloadmanageService.selectDownloadmanageList(downloadmanage);
		return getDataTable(list);
	}

	/**
	 * 新增下载管理详情
	 */
	@RequiresPermissions("tool:downloadmanage:add")
	@GetMapping("/add")
	public String add() {
		return prefix + "/add";
	}

	/**
	 * 修改下载管理详情
	 */
	@Log(title = "系统工具", action = "下载管理-下载修改")
	@RequiresPermissions("tool:downloadmanage:edit")
	@GetMapping("/edit/{downloadmanageId}")
	public String edit(@PathVariable("downloadmanageId") Integer downloadmanageId, Model model) {
		Downloadmanage downloadmanage = downloadmanageService.selectDownloadmanageById(downloadmanageId);
		model.addAttribute("downloadmanage", downloadmanage);
		return prefix + "/edit";
	}

	/**
	 * 保存下载管理详情
	 */
	@Log(title = "系统工具", action = "下载管理-下载保存")
	@RequiresPermissions("tool:downloadmanage:save")
	@PostMapping("/save")
	@ResponseBody
	public Message save(Downloadmanage downloadmanage) {
		if (downloadmanageService.saveDownloadmanage(downloadmanage) > 0) {
			return Message.success();
		}
		return Message.error();
	}

	/**
	 * 删除下载管理详情
	 */
	@Log(title = "系统工具", action = "下载管理-删除")
	@RequiresPermissions("tool:downloadmanage:remove")
	@PostMapping("/remove/{downloadmanageId}")
	@ResponseBody
	public Message remove(@PathVariable("downloadmanageId") Integer downloadmanageId) {
		if (downloadmanageService.deleteDownloadmanageById(downloadmanageId) > 0) {
			return Message.success();
		}
		return Message.error();
	}

	/**
	 * 批量删除下载管理详情
	 */
	@Log(title = "系统工具", action = "下载管理-批量删除")
	@RequiresPermissions("tool:downloadmanage:batchRemove")
	@PostMapping("/batchRemove")
	@ResponseBody
	public Message remove(@RequestParam("ids[]") Integer[] downloadmanageIds) {
		int rows = downloadmanageService.batchDeleteDownloadmanage(downloadmanageIds);
		if (rows > 0) {
			return Message.success();
		}
		return Message.error();
	}

	/**
	 * 上传文件
	 */
	@Log(title = "系统工具", action = "下载管理-文件上传")
	@RequiresPermissions("tool:downloadmanage:uploadFile")
	@ResponseBody
	@PostMapping("/uploadFile")
	public Message uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		// 判断文件大小,不超过500m
		try {
			FileUploadUtils.assertAllowedSetSize(file, FileUploadUtils.DEFAULT_MAX_SIZE_500M);
		} catch (FileSizeLimitExceededException e1) {
			e1.printStackTrace();
			return Message.error(FileMessageConstant.FILE_MESSAGE_SIZE_GREATER_FIVE_HUNDRED_M);
		}
		// 原始名字
		String fileName = file.getOriginalFilename();
		// 重命名
		fileName = FileUploadUtils.renameToUUID(fileName);
		// 先上传
		try {
			FileUploadUtils.uploadFile(file.getBytes(), FilePathConfig.getDownloadManagePath(), fileName);
			// 返回文件名
			Message message = new Message();
			message = message.success();
			message.put(ReturnMessageConstant.RETURN_MESSAGE_KEY_2, fileName);
			return message;
		} catch (Exception e) {
			return Message.error(FileMessageConstant.FILE_MESSAGE_FILE_UPLOAD_FAILED);
		}
	}

}
