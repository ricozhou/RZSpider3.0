package com.rzspider.project.spider.customspider.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rzspider.common.constant.CommonSymbolicConstant;
import com.rzspider.common.constant.FileExtensionConstant;
import com.rzspider.common.constant.FileMessageConstant;
import com.rzspider.common.constant.project.SpiderConstant;
import com.rzspider.common.constant.project.SpiderMessageConstant;
import com.rzspider.framework.aspectj.lang.annotation.Log;
import com.rzspider.framework.web.controller.BaseController;
import com.rzspider.framework.web.page.TableDataInfo;
import com.rzspider.project.common.file.utilt.FileUtils;
import com.rzspider.project.spider.customspider.domain.Customspider;
import com.rzspider.project.spider.customspider.domain.CustomspiderBackupcode;
import com.rzspider.project.spider.customspider.domain.FileTree;
import com.rzspider.project.spider.customspider.service.ICSFileService;
import com.rzspider.project.spider.customspider.service.ICustomspiderBackupcodeService;
import com.rzspider.project.spider.spidertask.utils.SpidertaskUtils;
import com.rzspider.framework.web.domain.Message;

/**
 * 自定义爬虫代码备份 信息操作处理
 * 
 * @author ricozhou
 * @date 2018-08-29
 */
@Controller
@RequestMapping("/spider/customspider/backupcode")
public class CustomspiderBackupcodeController extends BaseController {
	private String prefix = "spider/customspider/backupcode";
	@Autowired
	private ICSFileService cSFileService;
	@Autowired
	private ICustomspiderBackupcodeService customspiderBackupcodeService;

	@RequiresPermissions("spider:customspider:backupcode:view")
	@GetMapping()
	public String customspiderBackupcode() {
		return prefix + "/backupcode";
	}

	/**
	 * 查询自定义爬虫代码备份列表
	 */
	@Log(title = "网页爬虫", action = "自定义爬虫-备份代码查询")
	@RequiresPermissions("spider:customspider:backupcode:list")
	@GetMapping("/list")
	@ResponseBody
	public TableDataInfo list(CustomspiderBackupcode customspiderBackupcode) {
		startPage();
		List<CustomspiderBackupcode> list = customspiderBackupcodeService
				.selectCustomspiderBackupcodeList(customspiderBackupcode);
		return getDataTable(list);
	}

	/**
	 * 删除自定义爬虫代码备份
	 */
	@Log(title = "网页爬虫", action = "自定义爬虫-备份代码删除")
	@RequiresPermissions("spider:customspider:backupcode:remove")
	@PostMapping("/remove/{spiderCustomspiderBackupcodeId}")
	@ResponseBody
	public Message remove(@PathVariable("spiderCustomspiderBackupcodeId") Integer spiderCustomspiderBackupcodeId) {
		if (customspiderBackupcodeService.deleteCustomspiderBackupcodeById(spiderCustomspiderBackupcodeId) > 0) {
			return Message.success();
		}
		return Message.error();
	}

	/**
	 * 批量删除自定义爬虫代码备份
	 */
	@Log(title = "网页爬虫", action = "自定义爬虫-备份代码批量删除")
	@RequiresPermissions("spider:customspider:backupcode:batchRemove")
	@PostMapping("/batchRemove")
	@ResponseBody
	public Message remove(@RequestParam("ids[]") Integer[] spiderCustomspiderBackupcodeIds) {
		int rows = customspiderBackupcodeService.batchDeleteCustomspiderBackupcode(spiderCustomspiderBackupcodeIds);
		if (rows > 0) {
			return Message.success();
		}
		return Message.error();
	}

	// 备份代码内容至数据库
	@Log(title = "网页爬虫", action = "自定义爬虫-备份代码内容至数据库")
	@PostMapping("/backupCodeToDB")
	@ResponseBody
	public Message backupCodeToDB(FileTree fileTree) {
		if (fileTree != null) {
			CustomspiderBackupcode customspiderBackupcode = customspiderBackupcodeService
					.getCustomSpiderBackupCode(fileTree);
			if (customspiderBackupcode != null) {
				// 插入到数据库，后台id，代码类型，爬虫版本，文件名，文件路径，内容
				// 插入更新表
				if (customspiderBackupcodeService.saveCustomspiderBackupcode(customspiderBackupcode) > 0) {
					return Message.success(SpiderMessageConstant.SPIDER_MESSAGE_CUSTOMSPIDER_BACKUPCODE_SUCCESS);
				}
			}
		}

		return Message.error(SpiderMessageConstant.SPIDER_MESSAGE_CUSTOMSPIDER_BACKUPCODE_FAILED);
	}

	/**
	 * 查看内容
	 */
	@RequiresPermissions("spider:customspider:backupcode:codeContent")
	@GetMapping("/codeContent/{spiderCustomspiderBackupcodeId}")
	public String codeContent(@PathVariable("spiderCustomspiderBackupcodeId") Integer spiderCustomspiderBackupcodeId,
			Model model) {
		CustomspiderBackupcode customspiderBackupcode = customspiderBackupcodeService
				.selectCustomspiderBackupcodeById(spiderCustomspiderBackupcodeId);
		model.addAttribute("customspiderBackupcode", customspiderBackupcode);
		return prefix + "/codeContent";
	}

	/**
	 * 代码内容导出
	 */
	@RequiresPermissions("spider:customspider:backupcode:exportCode")
	@GetMapping("/exportCode/{spiderCustomspiderBackupcodeId}")
	public void exportCode(HttpServletResponse response,
			@PathVariable("spiderCustomspiderBackupcodeId") Integer spiderCustomspiderBackupcodeId) throws IOException {
		response.reset();
		// 查出详情
		CustomspiderBackupcode customspiderBackupcode = customspiderBackupcodeService
				.selectCustomspiderBackupcodeById(spiderCustomspiderBackupcodeId);
		if (customspiderBackupcode.getSpiderFileCodeContent() == null) {
			return;
		}
		byte[] data = customspiderBackupcode.getSpiderFileCodeContent().getBytes();
		String fileName = customspiderBackupcode.getSpiderFileName();
		// 处理中文乱码
		try {
			fileName = FileUtils.getNewString(fileName);
		} catch (Exception e) {
			e.printStackTrace();
			fileName = SpiderConstant.SPIDER_EXPORTFILE_BACKUPCODE_DEFAULT_NAME;
		}
		response.reset();
		response.setHeader(FileMessageConstant.FILE_CONTENT_DISPOSITION,
				FileMessageConstant.FILE_ATTACHMENT_FILENAME + fileName);
		response.addHeader(FileMessageConstant.FILE_CONTENT_LENGTH, CommonSymbolicConstant.EMPTY_STRING + data.length);
		response.setContentType(FileMessageConstant.FILE_CONTENT_TYPE);

		IOUtils.write(data, response.getOutputStream());
		response.getOutputStream().close();
	}
}
