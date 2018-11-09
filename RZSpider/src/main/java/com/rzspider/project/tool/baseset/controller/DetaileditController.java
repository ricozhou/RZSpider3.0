package com.rzspider.project.tool.baseset.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
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

import com.rzspider.common.constant.CommonSymbolicConstant;
import com.rzspider.common.constant.FileExtensionConstant;
import com.rzspider.common.constant.FileMessageConstant;
import com.rzspider.common.constant.FileOtherConstant;
import com.rzspider.common.constant.OtherConstant;
import com.rzspider.common.constant.ReturnMessageConstant;
import com.rzspider.common.constant.project.ToolConstant;
import com.rzspider.common.utils.FileUploadUtils;
import com.rzspider.framework.aspectj.lang.annotation.Log;
import com.rzspider.framework.config.FilePathConfig;
import com.rzspider.framework.web.controller.BaseController;
import com.rzspider.framework.web.domain.Message;
import com.rzspider.project.common.file.utilt.FileUtils;
import com.rzspider.project.tool.baseset.domain.Baseset;
import com.rzspider.project.tool.baseset.service.IBasesetDetaileditService;
import com.rzspider.project.tool.baseset.service.IBasesetService;
import com.rzspider.project.tool.baseset.utils.BasesetUtils;

/**
 * 基础设置详情编辑 信息操作处理
 * 
 * @author ricozhou
 * @date 2018-06-23
 */
@Controller
@RequestMapping("/tool/baseset/detailedit")
public class DetaileditController extends BaseController {
	private String prefix = "tool/baseset/detailedit";

	@Autowired
	private IBasesetService basesetService;
	@Autowired
	private IBasesetDetaileditService basesetDetaileditService;

	/* java代码示例详情 */
	@Log(title = "基础设置", action = "详细信息-java代码示例查询")
	@RequiresPermissions("tool:baseset:detailedit:spiderJavaCodeExampleEdit")
	@GetMapping("/spiderJavaCodeExampleEdit/{basesetId}")
	public String spiderJavaCodeExampleEdit(@PathVariable("basesetId") Integer basesetId, Model model) {
		Baseset baseset = basesetService.selectBasesetById(basesetId);
		model.addAttribute("baseset", baseset);
		return prefix + "/spiderJavaCodeExampleEdit";
	}

	/* python代码示例详情 */
	@Log(title = "基础设置", action = "详细信息-Python代码示例查询")
	@RequiresPermissions("tool:baseset:detailedit:spiderPythonCodeExampleEdit")
	@GetMapping("/spiderPythonCodeExampleEdit/{basesetId}")
	public String spiderPythonCodeExampleEdit(@PathVariable("basesetId") Integer basesetId, Model model) {
		Baseset baseset = basesetService.selectBasesetById(basesetId);
		model.addAttribute("baseset", baseset);
		return prefix + "/spiderPythonCodeExampleEdit";
	}

	/* javascript代码示例详情 */
	@Log(title = "基础设置", action = "详细信息-js代码示例查询")
	@RequiresPermissions("tool:baseset:detailedit:spiderJavascriptCodeExampleEdit")
	@GetMapping("/spiderJavascriptCodeExampleEdit/{basesetId}")
	public String spiderJavascriptCodeExampleEdit(@PathVariable("basesetId") Integer basesetId, Model model) {
		Baseset baseset = basesetService.selectBasesetById(basesetId);
		model.addAttribute("baseset", baseset);
		return prefix + "/spiderJavascriptCodeExampleEdit";
	}

	/* 登录设置详情 */
	@GetMapping("/loginSetEdit/{basesetId}")
	public String loginSetEdit(@PathVariable("basesetId") Integer basesetId, Model model) {
		Baseset baseset = basesetService.selectBasesetById(basesetId);
		model.addAttribute("baseset", baseset);
		return prefix + "/loginSetEdit";
	}

	/* 登录背景预览详情 */
	@Log(title = "基础设置", action = "详细信息-登录背景预览")
	@GetMapping("/previewLoginbg/{loginbgName}/{loginbgType}")
	public String previewLoginbg(@PathVariable("loginbgName") String loginbgName,
			@PathVariable("loginbgType") Integer loginbgType, Model model) {
		// 如果是默认则返回空值
		// 如果是地址则解码返回url
		// 如果是特效，则返回下拉下标
		if (loginbgType == 0) {
			if (!CommonSymbolicConstant.NEGATIVE_1.equals(loginbgName)) {
				// 地址解码
				loginbgName = URLDecoder.decode(loginbgName);
			} else {
				loginbgName = CommonSymbolicConstant.EMPTY_STRING;
			}
		} else if (loginbgType == 1) {
			loginbgName = FileOtherConstant.FILE_JUMP_PATH_PREFIX + loginbgName;
		}
		model.addAttribute("loginbgType", loginbgType);
		model.addAttribute("loginbgName", loginbgName);
		return prefix + "/previewLoginbg";
	}

	/* 首页介绍示例详情 */
	@Log(title = "基础设置", action = "详细信息-首页示例查询")
	@GetMapping("/homeIntroductionEdit/{basesetId}")
	public String homeIntroductionEdit(@PathVariable("basesetId") Integer basesetId, Model model) {
		Baseset baseset = basesetService.selectBasesetById(basesetId);
		model.addAttribute("baseset", baseset);
		return prefix + "/homeIntroductionEdit";
	}

	/* 网站手册示例详情 */
	@Log(title = "基础设置", action = "详细信息-网站手册查询")
	@GetMapping("/spiderWebsiteManualEdit/{basesetId}")
	public String spiderWebsiteManualEdit(@PathVariable("basesetId") Integer basesetId, Model model) {
		Baseset baseset = basesetService.selectBasesetById(basesetId);
		model.addAttribute("baseset", baseset);
		return prefix + "/spiderWebsiteManualEdit";
	}

	/* 手册预览 */
	@Log(title = "基础设置", action = "详细信息-手册预览")
	@GetMapping("/previewManual/{flag}")
	public String previewManual(@PathVariable("flag") Integer flag, Model model) {
		// 获取已启用主题
		Baseset baseset = basesetService.getBaseset();
		String pdfName = CommonSymbolicConstant.EMPTY_STRING;
		if (flag == 0) {
			if (baseset.getSpiderWebsiteManualType() == 1) {
				model.addAttribute("spiderManualType", 1);
				model.addAttribute("spiderManual", baseset.getSpiderWebsiteManual());
			} else if (baseset.getSpiderWebsiteManualType() == 0) {
				// 方式一，生成pdf文档再预览pdf
				// pdfName = UUID.randomUUID() + ".pdf";
				// if
				// (BasesetUtils.htmlCodeComeString(baseset.getSpiderWebsiteManual(),
				// FilePathConfig.getUploadPath() + File.separator + pdfName)) {
				// model.addAttribute("spiderManualType", 0);
				// model.addAttribute("spiderManual", pdfName);
				// }
				// 方式二，直接预览网页
				// 取出网页
				model.addAttribute("spiderManualType", 0);
				model.addAttribute("spiderManual", baseset.getSpiderWebsiteManual());
			}
		} else if (flag == 1) {
			if (baseset.getSpiderUseManualType() == 1) {
				model.addAttribute("spiderManualType", 1);
				model.addAttribute("spiderManual", baseset.getSpiderUseManual());
			} else if (baseset.getSpiderUseManualType() == 0) {
				// 方式一，生成pdf文档再预览pdf
				// pdfName = UUID.randomUUID() + ".pdf";
				// if
				// (BasesetUtils.htmlCodeComeString(baseset.getSpiderUseManual(),
				// FilePathConfig.getUploadPath() + File.separator + pdfName)) {
				// model.addAttribute("spiderManualType", 0);
				// model.addAttribute("spiderManual", pdfName);
				// }
				// 方式二，直接预览网页
				// 取出网页
				model.addAttribute("spiderManualType", 0);
				model.addAttribute("spiderManual", baseset.getSpiderUseManual());
			}
		}
		return prefix + "/previewManual";
	}

	/* 手册下载 */
	@Log(title = "基础设置", action = "详细信息-手册下载")
	@GetMapping("/downloadManual/{flag}")
	public void downloadManual(@PathVariable("flag") Integer flag, HttpServletResponse response) throws IOException {
		response.reset();
		// 获取已启用主题
		Baseset baseset = basesetService.getBaseset();
		String baseName = CommonSymbolicConstant.EMPTY_STRING;
		String fileName = CommonSymbolicConstant.EMPTY_STRING;
		String pdfName = CommonSymbolicConstant.EMPTY_STRING;
		if (flag == 0) {
			baseName = ToolConstant.TOOL_MANUAL_WEBSITEMANUAL_DEFAULT_NAME;
			if (baseset.getSpiderWebsiteManualType() == 1) {
				fileName = baseset.getSpiderWebsiteManual();
			} else if (baseset.getSpiderWebsiteManualType() == 0) {
				pdfName = UUID.randomUUID() + FileExtensionConstant.FILE_EXTENSION_POINT_FILE_PDF;
				if (BasesetUtils.htmlCodeComeString(baseset.getSpiderWebsiteManual(),
						FilePathConfig.getUploadCachePath() + File.separator + pdfName)) {
					fileName = pdfName;
				}
			}
		} else if (flag == 1) {
			baseName = ToolConstant.TOOL_MANUAL_USEMANUAL_DEFAULT_NAME;
			if (baseset.getSpiderUseManualType() == 1) {
				fileName = baseset.getSpiderUseManual();
			} else if (baseset.getSpiderUseManualType() == 0) {
				pdfName = UUID.randomUUID() + FileExtensionConstant.FILE_EXTENSION_POINT_FILE_PDF;
				if (BasesetUtils.htmlCodeComeString(baseset.getSpiderUseManual(),
						FilePathConfig.getUploadCachePath() + File.separator + pdfName)) {
					fileName = pdfName;
				}
			}
		}

		// 数据byte
		byte[] data = basesetService.getPdfToByte(fileName);

		String manualName = (!CommonSymbolicConstant.EMPTY_STRING.equals(basesetService.getDownloadFileNamePrefix())
				? (basesetService.getDownloadFileNamePrefix() + CommonSymbolicConstant.UNDERLINE)
				: CommonSymbolicConstant.EMPTY_STRING) + baseName;
		// 处理中文乱码
		try {
			manualName = FileUtils.getNewString(manualName);
		} catch (Exception e) {
			e.printStackTrace();
			manualName = baseName;
		}
		response.setHeader(FileMessageConstant.FILE_CONTENT_DISPOSITION,
				FileMessageConstant.FILE_ATTACHMENT_FILENAME + manualName);
		response.addHeader(FileMessageConstant.FILE_CONTENT_LENGTH, CommonSymbolicConstant.EMPTY_STRING + data.length);
		response.setContentType(FileMessageConstant.FILE_CONTENT_TYPE);

		IOUtils.write(data, response.getOutputStream());
		response.getOutputStream().close();
	}

	/* 爬虫手册示例详情 */
	@Log(title = "基础设置", action = "详细信息-爬虫手册查询")
	@GetMapping("/spiderUseManualEdit/{basesetId}")
	public String spiderUseManualEdit(@PathVariable("basesetId") Integer basesetId, Model model) {
		Baseset baseset = basesetService.selectBasesetById(basesetId);
		model.addAttribute("baseset", baseset);
		return prefix + "/spiderUseManualEdit";
	}

	/* 音乐工具编辑详情 */
	@GetMapping("/spiderMusicToolEdit/{basesetId}")
	public String spiderMusicToolEdit(@PathVariable("basesetId") Integer basesetId, Model model) {
		model.addAttribute("basesetId", basesetId);
		return prefix + "/musiclist/spiderMusicToolEdit";
	}

	/**
	 * 保存登录设置详情
	 */
	@Log(title = "基础设置", action = "详细信息-登录设置保存")
	@PostMapping("/loginSetsave")
	@ResponseBody
	public Message loginSetsave(Baseset baseset) {
		if (basesetDetaileditService.loginSetsave(baseset) > 0) {
			return Message.success();
		}
		return Message.error(ToolConstant.TOOL_SAVE_FAILED);
	}

	/**
	 * 保存首页介绍示例详情
	 */
	@Log(title = "基础设置", action = "详细信息-首页示例保存")
	@PostMapping("/homeIntroductionSave")
	@ResponseBody
	public Message homeIntroductionSave(Baseset baseset) {
		if (basesetDetaileditService.homeIntroductionSave(baseset) > 0) {
			return Message.success();
		}
		return Message.error(ToolConstant.TOOL_SAVE_FAILED);
	}

	/**
	 * 保存代码示例详情
	 */
	@Log(title = "基础设置", action = "详细信息-示例代码保存")
	@RequiresPermissions("tool:baseset:detailedit:spiderJavaCodeExampleEdit")
	@PostMapping("/spiderCodeExampleSave")
	@ResponseBody
	public Message spiderCodeExampleSave(Baseset baseset) {
		if (basesetDetaileditService.spiderCodeExampleSave(baseset) > 0) {
			return Message.success();
		}
		return Message.error(ToolConstant.TOOL_SAVE_FAILED);
	}

	/**
	 * 上传文件，直接获取内容存储到数据库
	 */
	@Log(title = "基础设置", action = "详细信息-文件上传")
	@ResponseBody
	@PostMapping("/uploadFile/{basesetId}/{flag}")
	public Message uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request,
			@PathVariable("basesetId") Integer basesetId, @PathVariable("flag") Integer flag) {
		String basePath = FilePathConfig.getUploadCachePath();
		// 判断文件大小，格式等等
		try {
			FileUploadUtils.assertAllowed(file);
		} catch (FileSizeLimitExceededException e1) {
			e1.printStackTrace();
			return Message.error(FileMessageConstant.FILE_MESSAGE_SIZE_GREATER_TEN_M);
		}
		// 原始名字
		String fileName = file.getOriginalFilename();

		if (flag == 0) {
			// java文件
			if (!fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_CODEFILE_JAVA)
					&& !fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_FILE_TXT)) {
				return Message.error(FileMessageConstant.FILE_MESSAGE_FORMAT_INCORRENT);
			}
		} else if (flag == 1) {
			// Python文件
			if (!fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_CODEFILE_PY)
					&& !fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_FILE_TXT)) {
				return Message.error(FileMessageConstant.FILE_MESSAGE_FORMAT_INCORRENT);
			}
		} else if (flag == 2) {
			// js文件
			if (!fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_CODEFILE_JS)
					&& !fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_FILE_TXT)) {
				return Message.error(FileMessageConstant.FILE_MESSAGE_FORMAT_INCORRENT);
			}
		} else if (flag == 3) {
			// 上传位置不同，用于登录预览加快浏览速度，可更改与正式登录方式一样
			basePath = FilePathConfig.getUploadPath();
			// 登录设置，图片
			if (!fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_IMAGE_JPG)
					&& !fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_IMAGE_PNG)
					&& !fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_IMAGE_JPEG)
					&& !fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_IMAGE_GIF)) {
				return Message.error(FileMessageConstant.FILE_MESSAGE_FORMAT_INCORRENT);
			}
		}

		// 重命名
		fileName = FileUploadUtils.renameToUUID(fileName);
		// 先上传
		try {
			FileUploadUtils.uploadFile(file.getBytes(), basePath, fileName);
		} catch (Exception e) {
			return Message.error();
		}
		// 图片特殊
		if (flag == 3) {
			// 对字节数组Base64编码
			// 可以先处理下图片大小
			try {
				String imgbase64String = OtherConstant.OTHER_DATAIMAGE
						+ fileName.substring(fileName.lastIndexOf(CommonSymbolicConstant.POINT) + 1)
						+ OtherConstant.OTHER_BASE64 + new String(Base64.encodeBase64(file.getBytes()));

				Message message = new Message();
				message = message.success();
				message.put(ReturnMessageConstant.RETURN_MESSAGE_KEY_1, imgbase64String);
				message.put(ReturnMessageConstant.RETURN_MESSAGE_KEY_2, fileName);
				return message;
			} catch (IOException e) {
				e.printStackTrace();
				return Message.error(FileMessageConstant.FILE_MESSAGE_FILE_UPLOAD_FAILED);
			}
		}

		// 解析获取数据然后返回
		String filePath = FilePathConfig.getUploadCachePath() + File.separator + fileName;
		String codeString = FileUtils.getFileToString(filePath).trim();

		return Message.success(codeString);
	}

	/**
	 * 上传文件,手册
	 */
	@Log(title = "基础设置", action = "详细信息-手册上传")
	@ResponseBody
	@PostMapping("/uploadManualFile")
	public Message uploadManualFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		// 判断文件大小，格式等等
		try {
			FileUploadUtils.assertAllowed(file);
		} catch (FileSizeLimitExceededException e1) {
			e1.printStackTrace();
			return Message.error(FileMessageConstant.FILE_MESSAGE_SIZE_GREATER_TEN_M);
		}
		// 原始名字
		String fileName = file.getOriginalFilename();
		if (!fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_FILE_PDF)
				&& !fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_FILE_DOC)
				&& !fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_FILE_DOCX)) {
			return Message.error(FileMessageConstant.FILE_MESSAGE_FORMAT_INCORRENT);
		}
		// 重命名
		fileName = FileUploadUtils.renameToUUID(fileName);
		// 先上传
		try {
			FileUploadUtils.uploadFile(file.getBytes(), FilePathConfig.getUploadPath(), fileName);
			// 返回文件名
			return Message.success(fileName);
		} catch (Exception e) {
			return Message.error(FileMessageConstant.FILE_MESSAGE_FILE_UPLOAD_FAILED);
		}
	}

	/**
	 * 保存手册详情
	 */
	@Log(title = "基础设置", action = "详细信息-手册保存")
	@PostMapping("/spiderManualSave")
	@ResponseBody
	public Message spiderManualSave(Baseset baseset) {
		if (basesetDetaileditService.spiderManualSave(baseset) > 0) {
			return Message.success();
		}
		return Message.error(ToolConstant.TOOL_SAVE_FAILED);
	}

}
