package com.rzspider.project.tool.downloadmanage.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rzspider.common.constant.CommonSymbolicConstant;
import com.rzspider.common.constant.FileMessageConstant;
import com.rzspider.common.constant.project.CommonToolConstant;
import com.rzspider.common.constant.project.ToolConstant;
import com.rzspider.common.utils.DateUtils;
import com.rzspider.framework.aspectj.lang.annotation.Log;
import com.rzspider.framework.web.controller.BaseController;
import com.rzspider.project.common.file.utilt.FileUtils;
import com.rzspider.project.tool.downloadmanage.domain.Downloadmanage;
import com.rzspider.project.tool.downloadmanage.service.IDownloadmanageService;

/**
 * 下载管理详情 信息操作处理
 * 
 * @author ricozhou
 * @date 2018-09-07
 */
@Controller
@RequestMapping("/download")
public class DownloadFileController extends BaseController {
	@Autowired
	private IDownloadmanageService downloadmanageService;

	/**
	 * 下载
	 */
	@Log(title = "系统工具", action = "下载管理-下载文件")
	@GetMapping("/{downloadFileUrlKey}")
	public String downloadFile(HttpServletResponse response,
			@PathVariable("downloadFileUrlKey") String downloadFileUrlKey, Model model) {
		response.reset();
		Downloadmanage downloadmanage = downloadmanageService
				.selectDownloadmanageByDownloadFileUrlKey(downloadFileUrlKey);

		if (downloadmanage == null) {
			model.addAttribute("msg", "文件已不存在");
			return "error/downerror";
		}

		if (downloadmanage.getStatus() != 0) {
			model.addAttribute("msg", "文件已被禁止下载");
			return "error/downerror";
		}
		if (downloadmanage.getDownloadFileDownNum() > downloadmanage.getDownloadFileLimitDownNum()) {
			model.addAttribute("msg", "文件已超过限定下载次数");
			return "error/downerror";
		}
		Date downDate = DateUtils.formatStringDate(downloadmanage.getDownloadFileLimitDownTime(),
				DateUtils.YYYY_MM_DD_HH_MM_SS);
		Date nowDate = new Date();
		if (nowDate.after(downDate)) {
			model.addAttribute("msg", "文件已超过限定下载时间");
			return "error/downerror";
		}

		byte[] data = downloadmanageService.getDownloadFileToBytes(downloadmanage);
		if (data == null) {
			model.addAttribute("msg", "文件读取失败，请联系管理员：2320095772@qq.com");
			return "error/downerror";
		}
		String fileName = downloadmanage.getDownloadFileName() + downloadmanage.getDownloadFileExtensionName();
		// 处理中文乱码
		try {
			fileName = FileUtils.getNewString(fileName);
		} catch (Exception e) {
			e.printStackTrace();
			fileName = ToolConstant.TOOL_BASESET_DOWNLOAD_MANAGE_DOWN_DEFAULT_NAME
					+ downloadmanage.getDownloadFileExtensionName();
		}

		response.setHeader(FileMessageConstant.FILE_CONTENT_DISPOSITION,
				FileMessageConstant.FILE_ATTACHMENT_FILENAME + fileName);
		response.addHeader(FileMessageConstant.FILE_CONTENT_LENGTH, CommonSymbolicConstant.EMPTY_STRING + data.length);
		response.setContentType(FileMessageConstant.FILE_CONTENT_TYPE);

		try {
			IOUtils.write(data, response.getOutputStream());
			response.getOutputStream().close();
		} catch (IOException e) {
			e.printStackTrace();
			model.addAttribute("msg", "未知错误，请联系管理员：2320095772@qq.com");
			return "error/downerror";
		}

		// 更新下载数量
		downloadmanageService.updateDownloadmanageDownloadFileDownNum(downloadmanage.getDownloadmanageId(),
				downloadmanage.getDownloadFileDownNum() + 1);
		return null;

	}

}