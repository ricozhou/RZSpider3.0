package com.rzspider.project.commontool.toolrun.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.imageio.stream.FileImageInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
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

import com.rzspider.common.constant.CommonSymbolicConstant;
import com.rzspider.common.constant.FileExtensionConstant;
import com.rzspider.common.constant.FileMessageConstant;
import com.rzspider.common.constant.GetMessageConstant;
import com.rzspider.common.constant.ReturnMessageConstant;
import com.rzspider.common.constant.project.CommonToolConstant;
import com.rzspider.common.constant.project.SpiderConstant;
import com.rzspider.common.constant.project.ToolrunConstant;
import com.rzspider.common.utils.FileUploadUtils;
import com.rzspider.common.utils.ImageUtils;
import com.rzspider.common.utils.StringUtils;
import com.rzspider.framework.aspectj.lang.annotation.Log;
import com.rzspider.framework.config.FilePathConfig;
import com.rzspider.framework.web.controller.BaseController;
import com.rzspider.framework.web.domain.Message;
import com.rzspider.project.common.file.service.IFileService;
import com.rzspider.project.common.file.utilt.FileUtils;
import com.rzspider.project.commontool.toolrun.domain.CobeImage;
import com.rzspider.project.commontool.toolrun.domain.CommonToolEntity;
import com.rzspider.project.commontool.toolrun.domain.FormatText;
import com.rzspider.project.commontool.toolrun.domain.ImgToChar;
import com.rzspider.project.commontool.toolrun.domain.MatchRegularExpression;
import com.rzspider.project.commontool.toolrun.domain.ORCode;
import com.rzspider.project.commontool.toolrun.service.IToolrunService;
import com.rzspider.project.spider.customspider.domain.FileTree;

/**
 * 通用工具运行 信息操作处理
 * 
 * @author ricozhou
 * @date 2018-07-23
 */
@Controller
@RequestMapping("/commontool/toolrun")
public class ToolrunController extends BaseController {
	private String prefix = "commontool/toolrun";

	@Autowired
	private IToolrunService toolrunService;
	@Autowired
	private IFileService fileService;

	// 上传工具类图片文件并返回文件名
	/**
	 * 上传文件,图片
	 */
	@Log(title = "通用工具", action = "工具运行-上传文件")
	@ResponseBody
	@PostMapping("/uploadToolFile/{flag}")
	public Message uploadToolFile(@PathVariable("flag") Integer flag, @RequestParam("file") MultipartFile file,
			HttpServletRequest request) {
		// 原始名字
		String fileName = file.getOriginalFilename();
		if (flag == 0) {
			// 图片
			if (!fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_IMAGE_JPG)
					&& !fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_IMAGE_PNG)
					&& !fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_IMAGE_JPEG)
					&& !fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_IMAGE_GIF)) {
				return Message.error(FileMessageConstant.FILE_MESSAGE_FORMAT_INCORRENT);
			}
		} else if (flag == 1) {
			//先这样
			return Message.error(FileMessageConstant.FILE_MESSAGE_FORMAT_INCORRENT);
		}

		// 重命名
		fileName = FileUploadUtils.renameToUUID(fileName);
		// 先上传
		try {
			FileUploadUtils.uploadFile(file.getBytes(), FilePathConfig.getUploadCachePath(), fileName);
			// 返回文件名
			return Message.success(fileName);
		} catch (Exception e) {
			return Message.error(FileMessageConstant.FILE_MESSAGE_FILE_UPLOAD_FAILED);
		}
	}

	/**
	 * 运行通用工具,生成二维码
	 */
	@Log(title = "通用工具", action = "工具运行-生成二维码")
	// @RequiresPermissions("commontool:toollist:run")
	@PostMapping("/runORCodeCreate")
	@ResponseBody
	public Message runORCodeCreate(ORCode orCode) {
		// 判断是否被禁用
		int flag = toolrunService.checkCommontoolStatus(orCode.getToolBackId());
		// 0是正常，1是禁用，2是不存在
		if (flag == 1) {
			return Message.error(ToolrunConstant.TOOL_BASESET_MESSAGE_RUN_TOOL_DISABLED);
		} else if (flag == 2) {
			return Message.error(ToolrunConstant.TOOL_BASESET_MESSAGE_RUN_TOOL_NOTEXIST);
		}

		Message message = new Message();
		try {
			String fileName = UUID.randomUUID() + CommonSymbolicConstant.POINT + orCode.getOrCodeImgFormat();
			if (toolrunService.runORCodeCreate(orCode, fileName)) {
				message = message.success();
				message.put(ReturnMessageConstant.RETURN_MESSAGE_KEY_2, fileName);
				return message;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Message.error(ToolrunConstant.TOOL_BASESET_MESSAGE_RUN_ORCREATE_FAILED);
	}

	/**
	 * 运行通用工具，解析二维码
	 */
	@Log(title = "通用工具", action = "工具运行-解析二维码")
	// @RequiresPermissions("commontool:toollist:run")
	@PostMapping("/runORCodeAnalyze")
	@ResponseBody
	public Message runORCodeAnalyze(CommonToolEntity commonToolEntity) {
		// 判断是否被禁用
		int flag = toolrunService.checkCommontoolStatus(commonToolEntity.getToolBackId());
		// 0是正常，1是禁用，2是不存在
		if (flag == 1) {
			return Message.error(ToolrunConstant.TOOL_BASESET_MESSAGE_RUN_TOOL_DISABLED);
		} else if (flag == 2) {
			return Message.error(ToolrunConstant.TOOL_BASESET_MESSAGE_RUN_TOOL_NOTEXIST);
		}
		Message message = new Message();
		try {
			String exportContent = toolrunService.runORCodeAnalyze(commonToolEntity);
			if (exportContent != null) {
				message = message.success();
				message.put(ReturnMessageConstant.RETURN_MESSAGE_KEY_3, exportContent);
				return message;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Message.error(ToolrunConstant.TOOL_BASESET_MESSAGE_RUN_ORANALYZE_FAILED);
	}

	/**
	 * 运行通用工具，ocr识别文字
	 */
	@Log(title = "通用工具", action = "工具运行-OCR识别文字")
	// @RequiresPermissions("commontool:toollist:run")
	@PostMapping("/runOCRRead")
	@ResponseBody
	public Message runOCRRead(CommonToolEntity commonToolEntity) {
		// 判断是否被禁用
		int flag = toolrunService.checkCommontoolStatus(commonToolEntity.getToolBackId());
		// 0是正常，1是禁用，2是不存在
		if (flag == 1) {
			return Message.error(ToolrunConstant.TOOL_BASESET_MESSAGE_RUN_TOOL_DISABLED);
		} else if (flag == 2) {
			return Message.error(ToolrunConstant.TOOL_BASESET_MESSAGE_RUN_TOOL_NOTEXIST);
		}
		Message message = new Message();
		try {
			String exportContent = toolrunService.runOCRRead(commonToolEntity);
			if (exportContent != null) {
				message = message.success();
				message.put(ReturnMessageConstant.RETURN_MESSAGE_KEY_3, exportContent);
				return message;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Message.error(ToolrunConstant.TOOL_BASESET_MESSAGE_RUN_OCRREAD_FAILED);
	}

	/**
	 * 运行通用工具，格式化文本
	 */
	@Log(title = "通用工具", action = "工具运行-格式化文本")
	// @RequiresPermissions("commontool:toollist:run")
	@PostMapping("/runFormatText")
	@ResponseBody
	public Message runFormatText(FormatText formatText) {
		// 判断是否被禁用
		int flag = toolrunService.checkCommontoolStatus(formatText.getToolBackId());
		// 0是正常，1是禁用，2是不存在
		if (flag == 1) {
			return Message.error(ToolrunConstant.TOOL_BASESET_MESSAGE_RUN_TOOL_DISABLED);
		} else if (flag == 2) {
			return Message.error(ToolrunConstant.TOOL_BASESET_MESSAGE_RUN_TOOL_NOTEXIST);
		}
		Message message = new Message();
		try {
			String exportContent = toolrunService.runFormatText(formatText);
			if (exportContent != null) {
				message = message.success();
				message.put(ReturnMessageConstant.RETURN_MESSAGE_KEY_3, exportContent);
				return message;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Message.error(ToolrunConstant.TOOL_BASESET_MESSAGE_RUN_FORMATTEXT_FAILED);
	}

	/**
	 * 运行通用工具，匹配正则表达式
	 */
	@Log(title = "通用工具", action = "工具运行-匹配正则表达式")
	// @RequiresPermissions("commontool:toollist:run")
	@PostMapping("/runMatchRegularExpression")
	@ResponseBody
	public Message runMatchRegularExpression(MatchRegularExpression matchRegularExpression) {
		// 判断是否被禁用
		int flag = toolrunService.checkCommontoolStatus(matchRegularExpression.getToolBackId());
		// 0是正常，1是禁用，2是不存在
		if (flag == 1) {
			return Message.error(ToolrunConstant.TOOL_BASESET_MESSAGE_RUN_TOOL_DISABLED);
		} else if (flag == 2) {
			return Message.error(ToolrunConstant.TOOL_BASESET_MESSAGE_RUN_TOOL_NOTEXIST);
		}
		Message message = new Message();
		try {
			String[] exportContent = toolrunService.runMatchRegularExpression(matchRegularExpression);
			if (exportContent != null) {
				message = message.success();
				message.put(ReturnMessageConstant.RETURN_MESSAGE_KEY_4, exportContent[0]);
				message.put(ReturnMessageConstant.RETURN_MESSAGE_KEY_3, exportContent[1]);
				return message;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Message.error(ToolrunConstant.TOOL_BASESET_MESSAGE_RUN_MATCHREGULAREXPRESSION_FAILED);
	}

	/**
	 * 运行通用工具,生成字符画
	 */
	@Log(title = "通用工具", action = "工具运行-生成字符画")
	// @RequiresPermissions("commontool:toollist:run")
	@PostMapping("/runImgToChar")
	@ResponseBody
	public Message runImgToChar(ImgToChar imgToChar) {
		// 判断是否被禁用
		int flag = toolrunService.checkCommontoolStatus(imgToChar.getToolBackId());
		// 0是正常，1是禁用，2是不存在
		if (flag == 1) {
			return Message.error(ToolrunConstant.TOOL_BASESET_MESSAGE_RUN_TOOL_DISABLED);
		} else if (flag == 2) {
			return Message.error(ToolrunConstant.TOOL_BASESET_MESSAGE_RUN_TOOL_NOTEXIST);
		}
		Message message = new Message();
		try {
			// 防止网络图片无法读取后缀，直接写死png
			// 需要判断是否是gif
			String filePre = ImageUtils.getImageFormat(imgToChar.getImageUrl());
			if (filePre == null) {
				return Message.error(ToolrunConstant.TOOL_BASESET_MESSAGE_RUN_IMGTOCHAR_FAILED);
			}
			// 设置图片格式
			imgToChar.setImgPre(filePre);
			String fileName = UUID.randomUUID() + CommonSymbolicConstant.POINT + filePre;
			if (toolrunService.runImgToChar(imgToChar, fileName)) {
				message = message.success();
				message.put(ReturnMessageConstant.RETURN_MESSAGE_KEY_2, fileName);
				return message;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Message.error(ToolrunConstant.TOOL_BASESET_MESSAGE_RUN_IMGTOCHAR_FAILED);
	}

	/**
	 * 运行通用工具,生成魔方图案
	 */
	@Log(title = "通用工具", action = "工具运行-生成魔方图案")
	// @RequiresPermissions("commontool:toollist:run")
	@PostMapping("/runCobeImageCreate")
	@ResponseBody
	public Message runCobeImageCreate(CobeImage cobeImage) {
		// 判断是否被禁用
		int flag = toolrunService.checkCommontoolStatus(cobeImage.getToolBackId());
		// 0是正常，1是禁用，2是不存在
		if (flag == 1) {
			return Message.error(ToolrunConstant.TOOL_BASESET_MESSAGE_RUN_TOOL_DISABLED);
		} else if (flag == 2) {
			return Message.error(ToolrunConstant.TOOL_BASESET_MESSAGE_RUN_TOOL_NOTEXIST);
		}
		Message message = new Message();
		try {
			String fileName = UUID.randomUUID() + FileExtensionConstant.FILE_EXTENSION_POINT_IMAGE_PNG;
			if (toolrunService.runCobeImageCreate(cobeImage, fileName)) {
				message = message.success();
				message.put(ReturnMessageConstant.RETURN_MESSAGE_KEY_2, fileName);
				return message;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Message.error(ToolrunConstant.TOOL_BASESET_MESSAGE_RUN_COBEIMAGECREATE_FAILED);
	}

	/**
	 * 运行通用工具，格式化代码
	 */
	@Log(title = "通用工具", action = "工具运行-格式化代码")
	// @RequiresPermissions("commontool:toollist:run")
	@PostMapping("/runFormatCode")
	@ResponseBody
	public Message runFormatCode(FormatText formatText) {
		// 判断是否被禁用
		int flag = toolrunService.checkCommontoolStatus(formatText.getToolBackId());
		// 0是正常，1是禁用，2是不存在
		if (flag == 1) {
			return Message.error(ToolrunConstant.TOOL_BASESET_MESSAGE_RUN_TOOL_DISABLED);
		} else if (flag == 2) {
			return Message.error(ToolrunConstant.TOOL_BASESET_MESSAGE_RUN_TOOL_NOTEXIST);
		}
		Message message = new Message();
		try {
			String[] exportContent = toolrunService.runFormatCode(formatText);
			if (exportContent != null) {
				message = message.success();
				message.put(ReturnMessageConstant.RETURN_MESSAGE_KEY_5, exportContent[0]);
				message.put(ReturnMessageConstant.RETURN_MESSAGE_KEY_3, exportContent[1]);
				return message;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Message.error(ToolrunConstant.TOOL_BASESET_MESSAGE_RUN_FORMATCODE_FAILED);
	}

	/**
	 * 下载前判断图片是否存在
	 */
	@Log(title = "通用工具", action = "工具运行-校验工具存在")
	@PostMapping("/checkImgFileExist")
	@ResponseBody
	public Message checkImgFileExist(CommonToolEntity commonToolEntity) {
		// 判断是否被禁用
		int flag = toolrunService.checkCommontoolStatus(commonToolEntity.getToolBackId());
		// 0是正常，1是禁用，2是不存在
		if (flag == 1) {
			return Message.error(ToolrunConstant.TOOL_BASESET_MESSAGE_RUN_TOOL_DISABLED);
		} else if (flag == 2) {
			return Message.error(ToolrunConstant.TOOL_BASESET_MESSAGE_RUN_TOOL_NOTEXIST);
		}
		String filePath = FilePathConfig.getUploadCachePath() + File.separator + commonToolEntity.getContent();
		File file = new File(filePath);
		if (file.exists()) {
			return Message.success();
		}
		return Message.error();
	}

	/**
	 * 下载图片
	 */
	@Log(title = "通用工具", action = "工具运行-下载图片")
	@GetMapping("/downloadImg")
	public void exportFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String imgFileName = request.getParameter(GetMessageConstant.GET_MESSAGE_KEY_1);
		response.reset();
		String url = FilePathConfig.getUploadCachePath() + File.separator + imgFileName;
		// 获取文件
		File file = new File(url);
		if (!file.exists()) {
			return;
		}
		// 将图片读成二进制流
		FileImageInputStream fs = new FileImageInputStream(file);
		int streamLength = (int) fs.length();
		byte[] data = new byte[streamLength];
		fs.read(data, 0, streamLength);
		fs.close();
		String fileName = ToolrunConstant.TOOL_BASESET_MESSAGE_RUN_IMG_DEFAULT_FILENAME
				+ FileExtensionConstant.FILE_EXTENSION_IMAGE_JPG;
		// 处理中文乱码
		try {
			String fileNamePre = CommonSymbolicConstant.POINT + FileUtils.getFileNameFromPoint(imgFileName);
			fileName = ToolrunConstant.TOOL_BASESET_MESSAGE_RUN_IMG_DEFAULT_FILENAME + fileNamePre;
			fileName = FileUtils.getNewString(fileName);
		} catch (Exception e) {
			e.printStackTrace();
			fileName = ToolrunConstant.TOOL_BASESET_MESSAGE_RUN_IMG_DEFAULT_FILENAME
					+ FileExtensionConstant.FILE_EXTENSION_IMAGE_JPG;
		}
		response.reset();
		response.setHeader(FileMessageConstant.FILE_CONTENT_DISPOSITION,
				FileMessageConstant.FILE_ATTACHMENT_FILENAME + fileName);
		response.addHeader(FileMessageConstant.FILE_CONTENT_LENGTH, CommonSymbolicConstant.EMPTY_STRING + data.length);
		response.setContentType(FileMessageConstant.FILE_CONTENT_TYPE);

		IOUtils.write(data, response.getOutputStream());
		response.getOutputStream().close();
	}
	// @GetMapping()
	// @RequiresPermissions("commontool:toolmanage:view")
	// public String toolmanage() {
	// return prefix + "/toolmanage";
	// }
	//
	// /**
	// * 查询通用工具管理列表
	// */
	// @RequiresPermissions("commontool:toolmanage:list")
	// @GetMapping("/list")
	// @ResponseBody
	// public TableDataInfo list(Toolmanage toolmanage) {
	// startPage();
	// List<Toolmanage> list =
	// toolmanageService.selectToolmanageList(toolmanage);
	// return getDataTable(list);
	// }
	//
	// /**
	// * 预览通用工具管理
	// */
	// @RequiresPermissions("commontool:toollist:run")
	// @GetMapping("/preview/{toolBackId}")
	// public String preview(@PathVariable("toolBackId") Integer toolBackId,
	// Model model) {
	// model.addAttribute("toolBackId", toolBackId);
	// return "commontool/toolrun/" + toolBackId;
	// }
	//
	// /**
	// * 新增通用工具管理
	// */
	// @RequiresPermissions("commontool:toolmanage:add")
	// @GetMapping("/add")
	// public String add() {
	// return prefix + "/add";
	// }
	//
	// /**
	// * 修改通用工具管理
	// */
	// @RequiresPermissions("commontool:toolmanage:edit")
	// @GetMapping("/edit/{toolId}")
	// public String edit(@PathVariable("toolId") Integer toolId, Model model) {
	// Toolmanage toolmanage = toolmanageService.selectToolmanageById(toolId);
	// model.addAttribute("toolmanage", toolmanage);
	// return prefix + "/edit";
	// }
	//
	// /**
	// * 保存通用工具管理
	// */
	// @RequiresPermissions("commontool:toolmanage:save")
	// @Transactional(rollbackFor = Exception.class)
	// @PostMapping("/save")
	// @ResponseBody
	// public Message save(Toolmanage toolmanage) {
	// // check文件名
	// // 校验文件名
	// if (!FileUtils.isValidFileName(toolmanage.getToolName())
	// ||
	// !FileUtils.isValidFileName(String.valueOf(toolmanage.getToolBackId()))) {
	// return Message.error(CommonToolConstant.COMMON_TOOL_TOOL_NAME_ILLEGAL);
	// }
	//
	// // 创建文件夹
	// String filePath = FilePathConfig.getUploadToolPath() + File.separator +
	// toolmanage.getToolBackId();
	// if (!FileUtils.createFolder(filePath)) {
	// return
	// Message.error(FileMessageConstant.FILE_MESSAGE_FILE_UNKNOWN_MISTAKE);
	// }
	// // 转移文件（更换思路，不再重命名，下载的时候在重命名）
	// if (!toolmanageService.copyToolFileToFolder(toolmanage)) {
	// return Message.error(FileMessageConstant.FILE_MESSAGE_FILE_SAVE_FAILED);
	// }
	//
	// // 保存至数据库
	// if (toolmanageService.saveToolmanage(toolmanage) > 0) {
	// return Message.success();
	// }
	// return Message.error();
	// }
	//
	// /**
	// * 删除通用工具管理
	// */
	// @RequiresPermissions("commontool:toolmanage:remove")
	// @Transactional(rollbackFor = Exception.class)
	// @PostMapping("/remove/{toolId}")
	// @ResponseBody
	// public Message remove(@PathVariable("toolId") Integer toolId) {
	// // 查询后台id删除文件夹
	// Toolmanage toolmanage = toolmanageService.selectToolmanageById(toolId);
	// // 删除文件夹
	// if (toolmanage != null) {
	// String filePath = FilePathConfig.getUploadToolPath() + File.separator +
	// toolmanage.getToolBackId();
	// if (!FileUtils.deleteFile(filePath)) {
	// return
	// Message.error(FileMessageConstant.FILE_MESSAGE_FILE_DELETE_FAILED);
	// }
	// }
	//
	// if (toolmanageService.deleteToolmanageById(toolId) > 0) {
	// return Message.success();
	// }
	// return Message.error();
	// }
	//
	// /**
	// * 批量删除通用工具管理
	// */
	// @RequiresPermissions("commontool:toolmanage:batchRemove")
	// @Transactional(rollbackFor = Exception.class)
	// @PostMapping("/batchRemove")
	// @ResponseBody
	// public Message remove(@RequestParam("ids[]") Integer[] toolIds) {
	// // 查询后台id删除文件夹
	// Toolmanage toolmanage;
	// for (Integer toolId : toolIds) {
	// // 查询后台id删除文件夹
	// toolmanage = toolmanageService.selectToolmanageById(toolId);
	// // 删除文件夹
	// if (toolmanage != null) {
	// String filePath = FilePathConfig.getUploadToolPath() + File.separator +
	// toolmanage.getToolBackId();
	// if (!FileUtils.deleteFile(filePath)) {
	// return
	// Message.error(FileMessageConstant.FILE_MESSAGE_FILE_DELETE_FAILED);
	// }
	// }
	// }
	// int rows = toolmanageService.batchDeleteToolmanage(toolIds);
	// if (rows > 0) {
	// return Message.success();
	// }
	// return Message.error();
	// }
	//
	// /**
	// * 校验工具名称
	// */
	// @PostMapping("/checkToolNameUnique")
	// @ResponseBody
	// public String checkToolNameUnique(Toolmanage toolmanage) {
	// String uniqueFlag = CommonConstant.UNIQUE;
	// if (toolmanage != null) {
	// uniqueFlag = toolmanageService.checkToolNameUnique(toolmanage);
	// }
	// return uniqueFlag;
	// }
	//
	// /**
	// * 校验工具后台id
	// */
	// @PostMapping("/checkToolBackIdUnique")
	// @ResponseBody
	// public String checktoolBackIdUnique(Toolmanage toolmanage) {
	// String uniqueFlag = CommonConstant.UNIQUE;
	// if (toolmanage != null) {
	// uniqueFlag = toolmanageService.checktoolBackIdUnique(toolmanage);
	// }
	// return uniqueFlag;
	// }
	//
	// // 可用工具
	// @GetMapping("/selectAllToolName")
	// @ResponseBody
	// public List<Toolmanage> selectAllToolName() {
	// List<Toolmanage> list = toolmanageService.selectAllToolNameByStatus(0);
	// return list;
	// }
	//
	// /**
	// * 上传工具文件
	// */
	// @Transactional(rollbackFor = Exception.class)
	// @ResponseBody
	// @PostMapping("/uploadToolFile")
	// public Message uploadToolFile(@RequestParam("file") MultipartFile file,
	// HttpServletRequest request,
	// Toolmanage toolmanage) {
	// // 总体思路是先上传到指定文件夹下，并文件名保存到数据库，并返回文件名，最后表单提交的时候一并修改文件名获着重新复制过去
	// // 同一处理
	// Message message = toolmanageService.uploadToolFile(file, toolmanage);
	// if
	// (!CommonConstant.MESSAGE_CODE.equals(message.get(CommonConstant.MESSAGE_CODE_NAME).toString()))
	// {
	// return message;
	// }
	//
	// // 重命名
	// String fileName = file.getOriginalFilename();
	// // 重命名
	// fileName = FileUploadUtils.renameToUUID(fileName);
	//
	// // 先上传
	// try {
	// FileUploadUtils.uploadFile(file.getBytes(),
	// FilePathConfig.getUploadToolPath(), fileName);
	// } catch (Exception e) {
	// return
	// Message.error(FileMessageConstant.FILE_MESSAGE_FILE_UPLOAD_FAILED);
	// }
	// return Message.success(fileName);
	// }
}
