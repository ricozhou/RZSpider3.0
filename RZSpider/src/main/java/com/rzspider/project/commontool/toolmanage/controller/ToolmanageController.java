package com.rzspider.project.commontool.toolmanage.controller;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.tomcat.util.http.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.rzspider.project.common.file.FileType;
import com.rzspider.project.common.file.domain.FileDao;
import com.rzspider.project.common.file.service.IFileService;
import com.rzspider.project.common.file.utilt.FileUtils;
import com.rzspider.project.commontool.toolmanage.domain.Toolmanage;
import com.rzspider.project.commontool.toolmanage.service.IToolmanageService;
import com.rzspider.project.spider.codeType.domain.CodeType;
import com.rzspider.project.spider.spidermanage.domain.SpiderManage;
import com.rzspider.common.constant.CommonConstant;
import com.rzspider.common.constant.FileMessageConstant;
import com.rzspider.common.constant.project.CommonToolConstant;
import com.rzspider.common.utils.FileUploadUtils;
import com.rzspider.common.utils.StringUtils;
import com.rzspider.framework.aspectj.lang.annotation.Log;
import com.rzspider.framework.config.FilePathConfig;
import com.rzspider.framework.web.controller.BaseController;
import com.rzspider.framework.web.page.TableDataInfo;
import com.rzspider.framework.web.domain.Message;

/**
 * 通用工具管理 信息操作处理
 * 
 * @author ricozhou
 * @date 2018-06-13
 */
@Controller
@RequestMapping("/commontool/toolmanage")
public class ToolmanageController extends BaseController {
	private String prefix = "commontool/toolmanage";

	@Autowired
	private IToolmanageService toolmanageService;
	@Autowired
	private IFileService fileService;

	@Log(title = "通用工具", action = "工具管理-管理查看")
	@GetMapping()
	@RequiresPermissions("commontool:toolmanage:view")
	public String toolmanage() {
		return prefix + "/toolmanage";
	}

	/**
	 * 查询通用工具管理列表
	 */
	@RequiresPermissions("commontool:toolmanage:list")
	@GetMapping("/list")
	@ResponseBody
	public TableDataInfo list(Toolmanage toolmanage) {
		startPage();
		List<Toolmanage> list = toolmanageService.selectToolmanageList(toolmanage);
		return getDataTable(list);
	}

	/**
	 * 预览通用工具管理
	 */
	@Log(title = "通用工具", action = "工具管理-工具预览")
	@RequiresPermissions("commontool:toollist:run")
	@GetMapping("/preview/{toolBackId}")
	public String preview(@PathVariable("toolBackId") Integer toolBackId, Model model) {
		model.addAttribute("toolBackId", toolBackId);
		return "commontool/toolrun/" + toolBackId;
	}

	/**
	 * 新增通用工具管理
	 */
	@Log(title = "通用工具", action = "工具管理-工具新增")
	@RequiresPermissions("commontool:toolmanage:add")
	@GetMapping("/add")
	public String add() {
		return prefix + "/add";
	}

	/**
	 * 修改通用工具管理
	 */
	@Log(title = "通用工具", action = "工具管理-工具修改")
	@RequiresPermissions("commontool:toolmanage:edit")
	@GetMapping("/edit/{toolId}")
	public String edit(@PathVariable("toolId") Integer toolId, Model model) {
		Toolmanage toolmanage = toolmanageService.selectToolmanageById(toolId);
		model.addAttribute("toolmanage", toolmanage);
		return prefix + "/edit";
	}

	/**
	 * 保存通用工具管理
	 */
	@Log(title = "通用工具", action = "工具管理-工具保存")
	@RequiresPermissions("commontool:toolmanage:save")
	@Transactional(rollbackFor = Exception.class)
	@PostMapping("/save")
	@ResponseBody
	public Message save(Toolmanage toolmanage) {
		// check文件名
		// 校验文件名
		if (!FileUtils.isValidFileName(toolmanage.getToolName())
				|| !FileUtils.isValidFileName(String.valueOf(toolmanage.getToolBackId()))) {
			return Message.error(CommonToolConstant.COMMON_TOOL_TOOL_NAME_ILLEGAL);
		}

		// 创建文件夹
		String filePath = FilePathConfig.getUploadToolPath() + File.separator + toolmanage.getToolBackId();
		if (!FileUtils.createFolder(filePath)) {
			return Message.error(FileMessageConstant.FILE_MESSAGE_FILE_UNKNOWN_MISTAKE);
		}
		// 转移文件（更换思路，不再重命名，下载的时候在重命名）
		if (!toolmanageService.copyToolFileToFolder(toolmanage)) {
			return Message.error(FileMessageConstant.FILE_MESSAGE_FILE_SAVE_FAILED);
		}

		// 保存至数据库
		if (toolmanageService.saveToolmanage(toolmanage) > 0) {
			return Message.success();
		}
		return Message.error();
	}

	/**
	 * 删除通用工具管理
	 */
	@Log(title = "通用工具", action = "工具管理-工具删除")
	@RequiresPermissions("commontool:toolmanage:remove")
	@Transactional(rollbackFor = Exception.class)
	@PostMapping("/remove/{toolId}")
	@ResponseBody
	public Message remove(@PathVariable("toolId") Integer toolId) {
		// 查询后台id删除文件夹
		Toolmanage toolmanage = toolmanageService.selectToolmanageById(toolId);
		// 删除文件夹
		if (toolmanage != null) {
			String filePath = FilePathConfig.getUploadToolPath() + File.separator + toolmanage.getToolBackId();
			if (!FileUtils.deleteFile(filePath)) {
				return Message.error(FileMessageConstant.FILE_MESSAGE_FILE_DELETE_FAILED);
			}
		}

		if (toolmanageService.deleteToolmanageById(toolId) > 0) {
			return Message.success();
		}
		return Message.error();
	}

	/**
	 * 批量删除通用工具管理
	 */
	@Log(title = "通用工具", action = "工具管理-批量删除")
	@RequiresPermissions("commontool:toolmanage:batchRemove")
	@Transactional(rollbackFor = Exception.class)
	@PostMapping("/batchRemove")
	@ResponseBody
	public Message remove(@RequestParam("ids[]") Integer[] toolIds) {
		// 查询后台id删除文件夹
		Toolmanage toolmanage;
		for (Integer toolId : toolIds) {
			// 查询后台id删除文件夹
			toolmanage = toolmanageService.selectToolmanageById(toolId);
			// 删除文件夹
			if (toolmanage != null) {
				String filePath = FilePathConfig.getUploadToolPath() + File.separator + toolmanage.getToolBackId();
				if (!FileUtils.deleteFile(filePath)) {
					return Message.error(FileMessageConstant.FILE_MESSAGE_FILE_DELETE_FAILED);
				}
			}
		}
		int rows = toolmanageService.batchDeleteToolmanage(toolIds);
		if (rows > 0) {
			return Message.success();
		}
		return Message.error();
	}

	/**
	 * 校验工具名称
	 */
	@Log(title = "通用工具", action = "工具管理-校验工具名称")
	@PostMapping("/checkToolNameUnique")
	@ResponseBody
	public String checkToolNameUnique(Toolmanage toolmanage) {
		String uniqueFlag = CommonConstant.UNIQUE;
		if (toolmanage != null) {
			uniqueFlag = toolmanageService.checkToolNameUnique(toolmanage);
		}
		return uniqueFlag;
	}

	/**
	 * 校验工具后台id
	 */
	@Log(title = "通用工具", action = "工具管理-校验后台ID")
	@PostMapping("/checkToolBackIdUnique")
	@ResponseBody
	public String checktoolBackIdUnique(Toolmanage toolmanage) {
		String uniqueFlag = CommonConstant.UNIQUE;
		if (toolmanage != null) {
			uniqueFlag = toolmanageService.checktoolBackIdUnique(toolmanage);
		}
		return uniqueFlag;
	}

	// 可用工具
	@Log(title = "通用工具", action = "工具管理-查询可用工具")
	@GetMapping("/selectAllToolName")
	@ResponseBody
	public List<Toolmanage> selectAllToolName() {
		List<Toolmanage> list = toolmanageService.selectAllToolNameByStatus(0);
		return list;
	}

	/**
	 * 上传工具文件
	 */
	@Log(title = "通用工具", action = "工具管理-上传工具文件")
	@Transactional(rollbackFor = Exception.class)
	@ResponseBody
	@PostMapping("/uploadToolFile")
	public Message uploadToolFile(@RequestParam("file") MultipartFile file, HttpServletRequest request,
			Toolmanage toolmanage) {
		// 总体思路是先上传到指定文件夹下，并文件名保存到数据库，并返回文件名，最后表单提交的时候一并修改文件名获着重新复制过去
		// 同一处理
		Message message = toolmanageService.uploadToolFile(file, toolmanage);
		if (!CommonConstant.MESSAGE_CODE.equals(message.get(CommonConstant.MESSAGE_CODE_NAME).toString())) {
			return message;
		}

		// 重命名
		String fileName = file.getOriginalFilename();
		// 重命名
		fileName = FileUploadUtils.renameToUUID(fileName);

		// 先上传
		try {
			FileUploadUtils.uploadFile(file.getBytes(), FilePathConfig.getUploadToolPath(), fileName);
		} catch (Exception e) {
			return Message.error(FileMessageConstant.FILE_MESSAGE_FILE_UPLOAD_FAILED);
		}
		return Message.success(fileName);
	}
}
