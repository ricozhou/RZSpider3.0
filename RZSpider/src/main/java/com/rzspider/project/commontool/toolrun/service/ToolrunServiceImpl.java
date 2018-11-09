package com.rzspider.project.commontool.toolrun.service;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.itextpdf.text.log.SysoCounter;
import com.rzspider.common.constant.CodingConstant;
import com.rzspider.common.constant.CommonSymbolicConstant;
import com.rzspider.common.constant.FileExtensionConstant;
import com.rzspider.common.constant.FileOtherConstant;
import com.rzspider.common.constant.ReturnMessageConstant;
import com.rzspider.common.utils.ImageUtils;
import com.rzspider.common.utils.JsonUtils;
import com.rzspider.common.utils.StringUtils;
import com.rzspider.framework.config.FilePathConfig;
import com.rzspider.project.common.file.utilt.FileUtils;
import com.rzspider.project.commontool.toolmanage.domain.Toolmanage;
import com.rzspider.project.commontool.toolmanage.domain.Toolset;
import com.rzspider.project.commontool.toolmanage.service.IToolmanageService;
import com.rzspider.project.commontool.toolmanage.service.IToolsetService;
import com.rzspider.project.commontool.toolrun.domain.CobeImage;
import com.rzspider.project.commontool.toolrun.domain.CommonToolEntity;
import com.rzspider.project.commontool.toolrun.domain.FormatText;
import com.rzspider.project.commontool.toolrun.domain.GifMsg;
import com.rzspider.project.commontool.toolrun.domain.ImgToChar;
import com.rzspider.project.commontool.toolrun.domain.MatchRegularExpression;
import com.rzspider.project.commontool.toolrun.domain.ORCode;
import com.rzspider.project.commontool.toolrun.utils.BaiDuOCRReadUtil;
import com.rzspider.project.commontool.toolrun.utils.ToolrunUtils;

/**
 * 通用工具运行 服务层
 * 
 * @author ricozhou
 * @date 2018-07-23
 */
@Service
public class ToolrunServiceImpl implements IToolrunService {
	@Autowired
	private IToolmanageService toolmanageService;
	@Autowired
	private IToolsetService toolsetService;

	// 检验工具状态
	@Override
	public int checkCommontoolStatus(Integer toolBackId) {
		// 判断是否可用
		Toolmanage toolmanage = toolmanageService.selectToolmanageByToolBackId(toolBackId);
		if (toolmanage == null) {
			return 2;
		}
		if (toolmanage.getStatus() != 0) {
			return 1;
		}
		return 0;
	}

	// 生成二维码
	@Override
	public boolean runORCodeCreate(ORCode orCode, String fileName) throws Exception {
		// 验证参数
		if (orCode == null || CommonSymbolicConstant.EMPTY_STRING.equals(orCode.getContent())) {
			return false;
		}

		// 生成的主逻辑
		Hashtable hints = new Hashtable();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hints.put(EncodeHintType.CHARACTER_SET, CodingConstant.CODING_UTF_8);
		hints.put(EncodeHintType.MARGIN, 1);

		BitMatrix bitMatrix = new MultiFormatWriter().encode(orCode.getContent(), BarcodeFormat.QR_CODE,
				orCode.getOrCodeSizeW(), orCode.getOrCodeSizeH(), hints);
		int width = bitMatrix.getWidth();
		int height = bitMatrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
			}
		}
		// 插入logo
		if (orCode.isInsertLogo()) {
			// 插入图片 是否压缩图片
			image = ToolrunUtils.insertORLogoImage(image, orCode, true);
		}
		// 写入缓存文件夹并返回文件名
		return FileUtils.imageToCachePath(image, orCode.getOrCodeImgFormat(), fileName);
	}

	// 解析二维码
	@Override
	public String runORCodeAnalyze(CommonToolEntity commonToolEntity) {
		BufferedImage image;
		try {
			if (commonToolEntity.getImageUrl().startsWith(FileOtherConstant.FILE_JUMP_PATH_PREFIX2)) {
				// 拼接出url
				String filePath = FilePathConfig.getUploadCachePath() + File.separator
						+ commonToolEntity.getImageUrl().substring(12);
				File file = new File(filePath);
				if (file.exists()) {
					image = ImageIO.read(file);
				} else {
					return null;
				}
			} else {
				URL url = new URL(commonToolEntity.getImageUrl());
				image = ImageIO.read(url);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 读取文件失败则直接返回
			return null;
		}

		// 解析
		return ToolrunUtils.analyQR(image);
	}

	// ocr识别文字
	@Override
	public String runOCRRead(CommonToolEntity commonToolEntity) {
		String imagePath;
		String exportContent = null;
		boolean isLocalImg;
		try {
			if (commonToolEntity.getImageUrl().startsWith(FileOtherConstant.FILE_JUMP_PATH_PREFIX2)) {
				isLocalImg = true;
				// 拼接出url
				String filePath = FilePathConfig.getUploadCachePath() + File.separator
						+ commonToolEntity.getImageUrl().substring(12);
				File file = new File(filePath);
				if (file.exists()) {
					imagePath = file.getAbsolutePath();
				} else {
					return null;
				}
			} else {
				isLocalImg = false;
				imagePath = commonToolEntity.getImageUrl();
			}
			// 读取其他设置信息
			Toolset toolset = toolsetService.selectToolsetById(0);
			if (toolset.getToolOcrsetType() == 0) {
				exportContent = BaiDuOCRReadUtil.recognizeTextByBaiduOCR(toolset, imagePath, isLocalImg);
			} else if (toolset.getToolOcrsetType() == 1) {
				exportContent = BaiDuOCRReadUtil.recognizeTextByTesseractOCR(toolset, imagePath, isLocalImg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 读取文件失败则直接返回
			return null;
		}

		// 解析
		return exportContent;
	}

	// 格式化文本
	@Override
	public String runFormatText(FormatText formatText) {
		if (formatText.getContent() == null) {
			return null;
		}
		if (formatText.getFormatFunction() == 0) {
			// json
			return JsonUtils.formatJson(formatText.getContent());
		} else if (formatText.getFormatFunction() == 1) {
			// 格式化为一行
			return StringUtils.formatStringToOneString(formatText.getContent());
		}
		return null;
	}

	// 匹配正则表达式
	@Override
	public String[] runMatchRegularExpression(MatchRegularExpression matchRegularExpression) {
		if (matchRegularExpression == null || matchRegularExpression.getContent() == null
				|| matchRegularExpression.getRegularExpression() == null) {
			return null;
		}
		List<String> listString = StringUtils.GetRegResultToList(matchRegularExpression.getRegularExpression(),
				matchRegularExpression.getContent());
		StringBuilder sb = new StringBuilder();
		if (listString == null) {
			return null;
		}
		for (String str : listString) {
			sb.append(str);
			sb.append(CommonSymbolicConstant.LINEBREAK2);
		}
		return new String[] { String.valueOf(listString.size()), sb.toString() };
	}

	// 生成字符画
	@Override
	public boolean runImgToChar(ImgToChar imgToChar, String fileName) {
		if (FileExtensionConstant.FILE_EXTENSION_IMAGE_GIF.equals(imgToChar.getImgPre().toLowerCase())) {
			// gif
			// 原图和压缩图
			List<BufferedImage> list = new ArrayList<BufferedImage>();
			// 创建的缓存新图
			List<BufferedImage> list2 = new ArrayList<BufferedImage>();
			// 最终生成的图
			List<BufferedImage> list3 = new ArrayList<BufferedImage>();
			String filePath = imgToChar.getImageUrl();
			if (imgToChar.getImageUrl().startsWith(FileOtherConstant.FILE_JUMP_PATH_PREFIX2)) {
				filePath = FilePathConfig.getUploadCachePath() + File.separator + imgToChar.getImageUrl().substring(12);
			}
			// 拆分gif
			GifMsg gifMsg = ImageUtils.splitGif(filePath);
			// 对获取的图片进行缩放
			for (BufferedImage bi : gifMsg.getList()) {
				list.add(ImageUtils.zoom2(bi, imgToChar.getSfImgSizeW(), imgToChar.getSfImgSizeH()));
			}

			// 创建一个缓存图片list用于写入字符
			int w = list.get(0).getWidth();
			int h = list.get(0).getHeight();
			if (imgToChar.getCreateImgSizeW() > 0 && imgToChar.getCreateImgSizeH() > 0) {
				w = imgToChar.getCreateImgSizeW();
				h = imgToChar.getCreateImgSizeH();
			}
			for (int i = 0; i < list.size(); i++) {
				list2.add(ImageUtils.createBufferedImage(w, h));
			}
			// 处理下字符大小
			int fontSize = 10;
			if (imgToChar.getCharSize() == 0) {
				// 如果自适应的话
				// 先不处理
				fontSize = 10;
			} else {
				fontSize = imgToChar.getCharSize();
			}
			// 密集度
			int imgIntensity = 3;
			if (imgToChar.getImgIntensity() == 0) {
				// 如果自适应的话
				// 先不处理
				imgIntensity = 3;
			} else {
				imgIntensity = imgToChar.getImgIntensity();
			}
			// 注意：字体和密集度不能过小，否则程序会死掉
			if (fontSize < 1 || imgIntensity < 1) {
				return false;
			}
			// 将处理好的图片集放入list3
			for (int i = 0; i < list2.size(); i++) {
				list3.add(ToolrunUtils.createAsciiPic(list.get(i), list2.get(i), imgToChar.getCharArray(), fontSize,
						imgIntensity));
			}
			// 将list合成图片
			return ImageUtils.jpgToGif(list3, gifMsg.getList2(),
					FilePathConfig.getUploadCachePath() + File.separator + fileName);
		} else {
			// 获取图片
			BufferedImage image = ImageUtils.getLocalAndUrlImage(imgToChar.getImageUrl());
			if (image == null) {
				return false;
			}
			// 对获取的图片进行缩放
			image = ImageUtils.zoom2(image, imgToChar.getSfImgSizeW(), imgToChar.getSfImgSizeH());

			// 创建一个缓存图片用于写入字符
			int w = image.getWidth();
			int h = image.getHeight();
			if (imgToChar.getCreateImgSizeW() > 0 && imgToChar.getCreateImgSizeH() > 0) {
				w = imgToChar.getCreateImgSizeW();
				h = imgToChar.getCreateImgSizeH();
			}
			BufferedImage createImg = ImageUtils.createBufferedImage(w, h);
			// 处理下字符大小
			int fontSize = 10;
			if (imgToChar.getCharSize() == 0) {
				// 如果自适应的话
				// 先不处理
				fontSize = 10;
			} else {
				fontSize = imgToChar.getCharSize();
			}
			int imgIntensity = 3;
			if (imgToChar.getImgIntensity() == 0) {
				// 如果自适应的话
				// 先不处理
				imgIntensity = 3;
			} else {
				imgIntensity = imgToChar.getImgIntensity();
			}
			// 注意：字体和密集度不能过小，否则程序会死掉
			if (fontSize < 1 || imgIntensity < 1) {
				return false;
			}
			// 生成
			createImg = ToolrunUtils.createAsciiPic(image, createImg, imgToChar.getCharArray(), fontSize, imgIntensity);
			// 写入
			if (createImg == null) {
				return false;
			}
			// 写入缓存文件夹并返回文件名
			return FileUtils.imageToCachePath(createImg, FileUtils.getFileNameFromPoint(fileName), fileName);
		}

	}

	// 魔方拼图
	@Override
	public boolean runCobeImageCreate(CobeImage cobeImage, String fileName) {
		// 获取图片对象
		BufferedImage image = ImageUtils.getLocalAndUrlImage(cobeImage.getImageUrl());
		if (image == null) {
			return false;
		}
		// 缩放到一个固定尺寸
		image = ImageUtils.zoom2(image, 1500, 1500);
		int cobeNum = 1;
		if (cobeImage.getCobeNum() > 1) {
			cobeNum = cobeImage.getCobeNum();
		}
		// 根据图片大小，魔方个数，计算每个方块的大小
		int[] sizeWH = ToolrunUtils.proCubeImag(image, cobeNum);

		// 生成,原图片，方块尺寸
		BufferedImage newImage = ToolrunUtils.createCobeImage(image, sizeWH);
		if (newImage == null) {
			return false;
		}
		// 写入文件
		// 写入缓存文件夹并返回文件名
		return FileUtils.imageToCachePath(newImage, FileUtils.getFileNameFromPoint(fileName), fileName);
	}

	// 格式化代码
	@Override
	public String[] runFormatCode(FormatText formatText) {
		if (formatText.getContent() == null) {
			return null;
		}
		if (formatText.getFormatFunction() == 0) {
			// java
			String code = ToolrunUtils.formatJavaCode(formatText.getContent());
			if (CommonSymbolicConstant.EMPTY_STRING.equals(code)) {
				return new String[] { ReturnMessageConstant.RETURN_MESSAGE_FAILED, code };
			}
			return new String[] { ReturnMessageConstant.RETURN_MESSAGE_SUCCESS, code };
		} else if (formatText.getFormatFunction() == 1) {
			// python
			return null;
		} else if (formatText.getFormatFunction() == 2) {
			// js
			return null;
		} else if (formatText.getFormatFunction() == 3) {
			// html
			String code = ToolrunUtils.formatHtmlCode(formatText.getContent());
			if (code == null) {
				return new String[] { ReturnMessageConstant.RETURN_MESSAGE_FAILED, code };
			}
			return new String[] { ReturnMessageConstant.RETURN_MESSAGE_SUCCESS, code };
		} else if (formatText.getFormatFunction() == 4) {
			// sql
			String code = ToolrunUtils.formatSqlCode(formatText.getContent(), formatText.getSqlType());
			if (code == null) {
				return new String[] { ReturnMessageConstant.RETURN_MESSAGE_FAILED, code };
			}
			return new String[] { ReturnMessageConstant.RETURN_MESSAGE_SUCCESS, code };
		}
		return null;
	}
}
