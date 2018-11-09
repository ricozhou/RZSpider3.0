package com.rzspider.project.commontool.toollist.controller;

import java.io.IOException;
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

import com.rzspider.project.common.file.utilt.FileUtils;
import com.rzspider.project.commontool.toollist.domain.Toollist;
import com.rzspider.project.commontool.toollist.service.IToollistService;
import com.rzspider.project.commontool.toolmanage.domain.Toolmanage;
import com.rzspider.project.commontool.toolmanage.service.IToolmanageService;
import com.rzspider.project.tool.baseset.service.IBasesetService;
import com.rzspider.common.constant.CommonSymbolicConstant;
import com.rzspider.common.constant.FileMessageConstant;
import com.rzspider.common.constant.project.CommonToolConstant;
import com.rzspider.common.utils.StringUtils;
import com.rzspider.framework.aspectj.lang.annotation.Log;
import com.rzspider.framework.web.controller.BaseController;
import com.rzspider.framework.web.page.TableDataInfo;
import com.rzspider.framework.web.domain.Message;

/**
 * 通用工具列 信息操作处理
 * 
 * @author ricozhou
 * @date 2018-06-13
 */
@Controller
@RequestMapping("/commontool/toollist")
public class ToollistController extends BaseController {
	private String prefix = "commontool/toollist";

	@Autowired
	private IToollistService toollistService;
	@Autowired
	private IToolmanageService toolmanageService;
	@Autowired
	private IBasesetService basesetService;

	@Log(title = "通用工具", action = "工具列表-列表查看")
	@GetMapping()
	@RequiresPermissions("commontool:toollist:view")
	public String toollist() {
		return prefix + "/toollist";
	}

	/**
	 * 查询通用工具列列表
	 */
	@RequiresPermissions("commontool:toollist:list")
	@GetMapping("/list")
	@ResponseBody
	public TableDataInfo list(Toollist toollist) {
		startPage();
		List<Toollist> list = toollistService.selectToollistList(toollist);
		return getDataTable(list);
	}

	/**
	 * 新增通用工具列
	 */
	@Log(title = "通用工具", action = "工具列表-工具新增")
	@RequiresPermissions("commontool:toollist:add")
	@GetMapping("/add")
	public String add() {
		return prefix + "/add";
	}

	/**
	 * 修改通用工具列
	 */
	@Log(title = "通用工具", action = "工具列表-工具修改")
	@RequiresPermissions("commontool:toollist:edit")
	@GetMapping("/edit/{toolListId}")
	public String edit(@PathVariable("toolListId") Integer toolListId, Model model) {
		Toollist toollist = toollistService.selectToollistById(toolListId);
		model.addAttribute("toollist", toollist);
		return prefix + "/edit";
	}

	/**
	 * 详情通用工具列
	 */
	@Log(title = "通用工具", action = "工具列表-工具详情")
	@GetMapping("/detail/{toolListId}")
	public String detail(@PathVariable("toolListId") Integer toolListId, Model model) {
		Toollist toollist = toollistService.selectToollistById(toolListId);
		model.addAttribute("toollist", toollist);
		return prefix + "/detail";
	}

	/**
	 * 保存通用工具列
	 */
	@Log(title = "通用工具", action = "工具列表-工具保存")
	@RequiresPermissions("commontool:toollist:save")
	@PostMapping("/save")
	@ResponseBody
	public Message save(Toollist toollist) {
		Integer toolListId = toollist.getToolListId();
		if (StringUtils.isNotNull(toolListId)) {
			// 判断是否还存在
			Toolmanage toolmanage = toolmanageService.selectToolmanageByToolName(toollist.getToolName());
			if (toolmanage == null) {
				return Message.error(CommonToolConstant.COMMON_TOOL_TOOL_NO_EXIST);
			}
		}
		if (toollistService.saveToollist(toollist) > 0) {
			return Message.success();
		}
		return Message.error();
	}

	/**
	 * 删除通用工具列
	 */
	@Log(title = "通用工具", action = "工具列表-工具删除")
	@RequiresPermissions("commontool:toollist:remove")
	@PostMapping("/remove/{toolListId}")
	@ResponseBody
	public Message remove(@PathVariable("toolListId") Integer toolListId) {
		if (toollistService.deleteToollistById(toolListId) > 0) {
			return Message.success();
		}
		return Message.error();
	}

	/**
	 * 批量删除通用工具列
	 */
	@Log(title = "通用工具", action = "工具列表-批量删除")
	@RequiresPermissions("commontool:toollist:batchRemove")
	@PostMapping("/batchRemove")
	@ResponseBody
	public Message remove(@RequestParam("ids[]") Integer[] toolListIds) {
		int rows = toollistService.batchDeleteToollist(toolListIds);
		if (rows > 0) {
			return Message.success();
		}
		return Message.error();
	}

	/**
	 * 校验工具昵称
	 */
	@Log(title = "通用工具", action = "工具列表-校验昵称")
	@PostMapping("/checkToolNickNameUnique")
	@ResponseBody
	public String checkToolNickNameUnique(Toollist toollist) {
		String uniqueFlag = CommonToolConstant.COMMON_TOOL_UNIQUE_FLAG;
		if (toollist != null) {
			uniqueFlag = toollistService.checkToolNickNameUnique(toollist);
		}
		return uniqueFlag;
	}

	/**
	 * 下载源码
	 */
	@Log(title = "通用工具", action = "工具列表-下载源码")
	@RequiresPermissions("commontool:toollist:downSrcFile")
	@GetMapping("/downSrcFile/{toolBackId}")
	public void downSrcFile(HttpServletResponse response, @PathVariable("toolBackId") Integer toolBackId)
			throws IOException {
		response.reset();
		// 查询是否存在
		// 判断是否还存在
		Toolmanage toolmanage = toolmanageService.selectToolmanageByToolBackId(toolBackId);
		if (toolmanage == null) {
			// return Message.error("工具已不存在，请删除");
			return;
		}
		// 返回对应文件名
		String fileName = toolmanage.getSrcFileName();
		String srcFileName = (basesetService.getDownloadFileNamePrefix() != null
				? (basesetService.getDownloadFileNamePrefix() + CommonSymbolicConstant.UNDERLINE)
				: CommonSymbolicConstant.EMPTY_STRING) + toolmanage.getToolName()
				+ CommonToolConstant.COMMON_TOOL_SRC_FILENAME_INFIX + FileUtils.getFileNameFromPoint(fileName);
		byte[] data = toollistService.downToolFile(toolmanage, fileName);
		if (data == null) {
			return;
		}
		// 处理中文乱码
		try {
			srcFileName = FileUtils.getNewString(srcFileName);
		} catch (Exception e) {
			e.printStackTrace();
			srcFileName = CommonToolConstant.COMMON_TOOL_DEFULAT_SRC_FILENAME_PREFIX
					+ FileUtils.getFileNameFromPoint(fileName);
		}

		response.setHeader(FileMessageConstant.FILE_CONTENT_DISPOSITION,
				FileMessageConstant.FILE_ATTACHMENT_FILENAME + srcFileName);
		response.addHeader(FileMessageConstant.FILE_CONTENT_LENGTH, CommonSymbolicConstant.EMPTY_STRING + data.length);
		response.setContentType(FileMessageConstant.FILE_CONTENT_TYPE);

		IOUtils.write(data, response.getOutputStream());
		response.getOutputStream().close();
	}

	/**
	 * 下载可执行exe
	 */
	@Log(title = "通用工具", action = "工具列表-下载可执行exe")
	@RequiresPermissions("commontool:toollist:downExecexeFile")
	@GetMapping("/downExecexeFile/{toolBackId}")
	public void downExecexeFile(HttpServletResponse response, @PathVariable("toolBackId") Integer toolBackId)
			throws IOException {
		response.reset();
		// 查询是否存在
		// 判断是否还存在
		Toolmanage toolmanage = toolmanageService.selectToolmanageByToolBackId(toolBackId);
		if (toolmanage == null) {
			return;
		}

		// 返回对应文件名
		String fileName = toolmanage.getExecexeFileName();
		String execexeFileName = (basesetService.getDownloadFileNamePrefix() != null
				? (basesetService.getDownloadFileNamePrefix() + CommonSymbolicConstant.UNDERLINE)
				: CommonSymbolicConstant.EMPTY_STRING) + toolmanage.getToolName()
				+ CommonToolConstant.COMMON_TOOL_EXECEXE_FILENAME_INFIX + FileUtils.getFileNameFromPoint(fileName);
		byte[] data = toollistService.downToolFile(toolmanage, fileName);
		if (data == null) {
			return;
		}
		// 处理中文乱码
		try {
			execexeFileName = FileUtils.getNewString(execexeFileName);
		} catch (Exception e) {
			e.printStackTrace();
			execexeFileName = CommonToolConstant.COMMON_TOOL_DEFULAT_EXECEXE_FILENAME_PREFIX
					+ FileUtils.getFileNameFromPoint(fileName);
		}
		response.setHeader(FileMessageConstant.FILE_CONTENT_DISPOSITION,
				FileMessageConstant.FILE_ATTACHMENT_FILENAME + execexeFileName);
		response.addHeader(FileMessageConstant.FILE_CONTENT_LENGTH, CommonSymbolicConstant.EMPTY_STRING + data.length);
		response.setContentType(FileMessageConstant.FILE_CONTENT_TYPE);

		IOUtils.write(data, response.getOutputStream());
		response.getOutputStream().close();
	}

	/**
	 * 下载安装版exe
	 */
	@Log(title = "通用工具", action = "工具列表-下载安装版exe")
	@RequiresPermissions("commontool:toollist:downSetupexeFile")
	@GetMapping("/downSetupexeFile/{toolBackId}")
	public void downSetupexeFile(HttpServletResponse response, @PathVariable("toolBackId") Integer toolBackId)
			throws IOException {
		response.reset();
		// 查询是否存在
		// 判断是否还存在
		Toolmanage toolmanage = toolmanageService.selectToolmanageByToolBackId(toolBackId);
		if (toolmanage == null) {
			return;
		}

		// 返回对应文件名
		String fileName = toolmanage.getSetupexeName();
		String setupexeFileName = (basesetService.getDownloadFileNamePrefix() != null
				? (basesetService.getDownloadFileNamePrefix() + CommonSymbolicConstant.UNDERLINE)
				: CommonSymbolicConstant.EMPTY_STRING) + toolmanage.getToolName()
				+ CommonToolConstant.COMMON_TOOL_SETUPEXE_FILENAME_INFIX + FileUtils.getFileNameFromPoint(fileName);
		byte[] data = toollistService.downToolFile(toolmanage, fileName);
		if (data == null) {
			return;
		}
		// 处理中文乱码
		try {
			setupexeFileName = FileUtils.getNewString(setupexeFileName);
		} catch (Exception e) {
			e.printStackTrace();
			setupexeFileName = CommonToolConstant.COMMON_TOOL_DEFULAT_SETUPEXE_FILENAME_PREFIX
					+ FileUtils.getFileNameFromPoint(fileName);
		}
		response.setHeader(FileMessageConstant.FILE_CONTENT_DISPOSITION,
				FileMessageConstant.FILE_ATTACHMENT_FILENAME + setupexeFileName);
		response.addHeader(FileMessageConstant.FILE_CONTENT_LENGTH, CommonSymbolicConstant.EMPTY_STRING + data.length);
		response.setContentType(FileMessageConstant.FILE_CONTENT_TYPE);

		IOUtils.write(data, response.getOutputStream());
		response.getOutputStream().close();
	}

	/**
	 * 运行通用工具管理
	 */
	@Log(title = "通用工具", action = "工具列表-工具运行")
	@RequiresPermissions("commontool:toollist:run")
	@GetMapping("/startTool/{toolBackId}")
	public String startTool(@PathVariable("toolBackId") Integer toolBackId, Model model) {
		// 判断是否可用
		Toolmanage toolmanage = toolmanageService.selectToolmanageByToolBackId(toolBackId);
		if (toolmanage == null) {
			// 不存在界面跳转
			return "commontool/toolrun/error";
		}
		if (toolmanage.getStatus() != 0) {
			// 不可用界面跳转
			return "commontool/toolrun/disabled";
		}
		model.addAttribute("toolBackId", toolBackId);
		return "commontool/toolrun/" + toolBackId;
	}
}
