package com.rzspider.project.tool.baseset.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.AbstractImageProvider;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import com.rzspider.common.constant.CodingConstant;
import com.rzspider.common.constant.OtherConstant;
import com.rzspider.common.constant.RegularExpressionConstant;
import com.rzspider.framework.config.FilePathConfig;
import com.rzspider.project.common.file.utilt.FileUtils;

//其它工具
public class BasesetUtils {

	// html转pdf
	public static boolean htmlCodeComeString(String htmlCode, String pdfPath) {
		// 先创建文件
		if (!FileUtils.createFile(pdfPath)) {
			return false;
		}
		try {
			// 创建document对象
			Document doc = new Document();
			// 创建PdfWriter实例
			PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(pdfPath));
			// 打开文档
			doc.open();

			// 处理文档
			htmlCode = getCorrectHtmlCode(htmlCode);

			XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
			ByteArrayInputStream is = new ByteArrayInputStream(htmlCode.getBytes(CodingConstant.CODING_UTF_8));
			worker.parseXHtml(writer, doc, is, Charset.forName(CodingConstant.CODING_UTF_8), new AsianFontProvider());
			doc.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// 处理html代码
	private static String getCorrectHtmlCode(String htmlCode) {
		// 添加开头结尾
		htmlCode = "<!DOCTYPE html><html><head><meta charset=\"utf-8\"></meta></head><body>" + htmlCode
				+ "</body></html>";

		// 处理标签闭合
		htmlCode = htmlCode.replaceAll("<br>", "<br/>");

		// 所有的图片地址转为绝对路径
		htmlCode = getImgSrcList(htmlCode);
		return htmlCode;
	}

	// 替换
	public static String getImgSrcList(String content) {
		// 开始匹配content中的<img />标签
		Pattern pImg = Pattern.compile(RegularExpressionConstant.REGULAR_EXPRESSION_IMG_LABEL);
		Matcher mImg = pImg.matcher(content);
		boolean resultImg = mImg.find();
		if (resultImg) {
			while (resultImg) {
				// 获取到匹配的<img />标签中的内容
				String strImg = mImg.group();
				// 替换
				content = content.replace(strImg, strImg + "</img>");
				// 开始匹配<img />标签中的src
				Pattern pSrc = Pattern.compile(RegularExpressionConstant.REGULAR_EXPRESSION_IMG_SRC_LABEL);
				Matcher mSrc = pSrc.matcher(strImg);
				if (mSrc.find()) {
					String strSrc = mSrc.group(3);
					// 拼接
					content = content.replace(strSrc,
							FilePathConfig.getUploadPath() + File.separator + strSrc.substring(7));
				}
				resultImg = mImg.find();
			}
		}
		return content;
	}
}

// 中文问题
class AsianFontProvider extends XMLWorkerFontProvider {

	@Override
	public Font getFont(final String fontname, String encoding, float size, final int style) {
		BaseFont bfChinese;
		try {
			bfChinese = BaseFont.createFont(OtherConstant.OTHER_STSONG, OtherConstant.OTHER_UNIGB,
					BaseFont.NOT_EMBEDDED);
			Font keyfont = new Font(bfChinese, 12);
			return keyfont;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
