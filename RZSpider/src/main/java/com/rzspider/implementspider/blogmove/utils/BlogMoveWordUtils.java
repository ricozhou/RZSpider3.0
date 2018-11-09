package com.rzspider.implementspider.blogmove.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xml.sax.SAXException;

import com.rzspider.common.constant.FileExtensionConstant;
import com.rzspider.common.constant.FileOtherConstant;
import com.rzspider.framework.config.FilePathConfig;
import com.rzspider.project.blog.blogcontent.domain.Blogcontent;
import com.rzspider.project.blog.blogcontent.domain.Blogmove;
import com.rzspider.project.common.file.utilt.FileUtils;

/**
 * @author ricozhou
 * @date Oct 29, 2018 5:22:42 PM
 * @Desc
 */
public class BlogMoveWordUtils {

	/**
	 * @date Oct 29, 2018 5:22:53 PM
	 * @Desc
	 * @param fileName
	 * @param blogMove
	 * @param blogcontent
	 * @return
	 * @throws SAXException
	 * @throws TransformerException
	 * @throws ParserConfigurationException
	 * @throws IOException
	 */
	public static String getWordArticleContent(String fileName, Blogmove blogMove, Blogcontent blogcontent)
			throws IOException, ParserConfigurationException, TransformerException, SAXException {
		// 注意是否需要替换图片
		// 保存图片到本地
		// 先获取所有图片连接，再按照每个链接下载图片，最后替换原有链接
		// 先创建一个文件夹
		// 先创建一个临时文件夹
		String blogFileName = String.valueOf(UUID.randomUUID());
		FileUtils.createFolder(FilePathConfig.getUploadBlogPath() + File.separator + blogFileName);
		blogcontent.setBlogFileName(blogFileName);
		// 核心方法读取word并缓存到此文件夹
		String content = BlogMoveWordToHtml.wordToHtml(FilePathConfig.getUploadCachePath() + File.separator + fileName,
				FilePathConfig.getUploadBlogPath() + File.separator + blogFileName + File.separator + blogFileName
						+ FileExtensionConstant.FILE_EXTENSION_POINT_FILE_HTML,
				FilePathConfig.getUploadBlogPath() + File.separator + blogFileName,
				FilePathConfig.getUploadBlogPath() + File.separator + blogFileName,
				FileOtherConstant.FILE_JUMP_PATH_PREFIX3 + blogFileName);
		// content = Jsoup.parse(htmlText).body().toString();
		// 匹配出所有链接
		List<String> imgList = BlogMoveCommonUtils.getArticleImgList(content);
		// 拼接文章所有链接
		String images = BlogMoveCommonUtils.getArticleImages(imgList);
		blogcontent.setImages(images);
		return content;
	}

	/**
	 * @date Oct 17, 2018 1:10:37 PM
	 * @Desc 获取类型
	 * @param doc
	 * @return
	 */
	public static String getWordArticleType(String moveArticleType) {
		if ("默认".equals(moveArticleType)) {
			return "原创";
		}

		return moveArticleType;
	}

}
