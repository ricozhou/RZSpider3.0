package com.rzspider.project.common.file.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import com.rzspider.common.utils.FileUploadUtils;
import com.rzspider.framework.aspectj.lang.annotation.Log;
import com.rzspider.framework.config.FilePathConfig;
import com.rzspider.framework.web.domain.Message;
import com.rzspider.project.common.file.FileType;
import com.rzspider.project.common.file.domain.FileDao;
import com.rzspider.project.common.file.service.IFileService;

/**
 * 文件操作
 * 
 * @author ricozhou
 * @date 2018-05-28
 */
@Controller
@RequestMapping("/common/file")
public class FileController {
	private String prefix = "/common/file";
	@Autowired
	private IFileService fileService;

	/**
	 * 文本编辑上传图片
	 */
	@ResponseBody
	@PostMapping("/uploadImg")
	public Message uploadimg(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		String fileName = file.getOriginalFilename();
		if (!fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_IMAGE_JPG)
				&& !fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_IMAGE_PNG)
				&& !fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_IMAGE_GIF)
				&& !fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_IMAGE_JPEG)) {
			return Message.error(FileMessageConstant.FILE_MESSAGE_FORMAT_INCORRENT);
		}
		// 重命名
		fileName = FileUploadUtils.renameToUUID(fileName);
		FileDao sysFile = new FileDao(FileType.fileType(fileName), FileOtherConstant.FILE_JUMP_PATH_PREFIX3 + fileName,
				new Date());
		// 先上传
		try {
			FileUploadUtils.uploadFile(file.getBytes(), FilePathConfig.getUploadBlogPath(), fileName);
		} catch (Exception e) {
			return Message.error(FileMessageConstant.FILE_MESSAGE_FILE_UPLOAD_FAILED);
		}

		if (fileService.save(sysFile) > 0) {
			return Message.success(sysFile.getUrl());
		}
		return Message.error();
	}

	/**
	 * 上传文件，直接获取内容存储到数据库
	 */
	@ResponseBody
	@PostMapping("/uploadImgFile")
	public Message uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		String basePath = FilePathConfig.getUploadCachePath();
		// 判断文件大小，格式等等
		try {
			FileUploadUtils.assertAllowedSetSize(file, 2 * 1024 * 1024);
		} catch (FileSizeLimitExceededException e1) {
			e1.printStackTrace();
			return Message.error(FileMessageConstant.FILE_MESSAGE_SIZE_GREATER_SIZE);
		}
		// 原始名字
		String fileName = file.getOriginalFilename();

		// 重命名
		fileName = FileUploadUtils.renameToUUID(fileName);
		// 先上传
		try {
			FileUploadUtils.uploadFile(file.getBytes(), basePath, fileName);
		} catch (Exception e) {
			return Message.error();
		}
		// 对字节数组Base64编码
		// 可以先处理下图片大小
		// 后期如果不需要存储到数据库则更改一下返回路径即可
		try {
			String imgbase64String = OtherConstant.OTHER_DATAIMAGE
					+ fileName.substring(fileName.lastIndexOf(CommonSymbolicConstant.POINT) + 1)
					+ OtherConstant.OTHER_BASE64 + new String(Base64.encodeBase64(file.getBytes()));

			Message message = new Message();
			message = message.success();
			message.put(ReturnMessageConstant.RETURN_MESSAGE_KEY_1, imgbase64String);
			// message.put(ReturnMessageConstant.RETURN_MESSAGE_KEY_2,
			// fileName);
			return message;
		} catch (IOException e) {
			e.printStackTrace();
			return Message.error(FileMessageConstant.FILE_MESSAGE_IMAGE_UPLOAD_FAILED);
		}

	}
}
