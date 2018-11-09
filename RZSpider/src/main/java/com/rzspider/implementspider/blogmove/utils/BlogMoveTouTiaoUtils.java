package com.rzspider.implementspider.blogmove.utils;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.rzspider.common.constant.CommonSymbolicConstant;
import com.rzspider.common.constant.FileExtensionConstant;
import com.rzspider.common.constant.FileOtherConstant;
import com.rzspider.common.utils.DateUtils;
import com.rzspider.common.utils.ImageUtils;
import com.rzspider.framework.config.FilePathConfig;
import com.rzspider.project.blog.blogcontent.domain.Blogcontent;
import com.rzspider.project.blog.blogcontent.domain.Blogmove;
import com.rzspider.project.blog.blogset.domain.Blogset;
import com.rzspider.project.common.file.utilt.FileUtils;
import com.rzspider.project.common.image.utils.WaterMarkUtils;

/**
 * @author ricozhou
 * @date Oct 17, 2018 12:51:52 PM
 * @Desc
 */
public class BlogMoveTouTiaoUtils {

	/**
	 * @date Oct 31, 2018 3:59:49 PM
	 * @Desc 获取文章list请求url
	 * @param blogMove
	 * @param num
	 * @param max_behot_time
	 * @return
	 */
	public static String getTouTiaoListUrl(Blogmove blogMove, int num, String max_behot_time) {
		String oneUrl = "https://www.toutiao.com/c/user/article/?page_type=1&user_id=%s&max_behot_time=%s&count=20&as=%s&cp=%s&_signature=%s";
		String user_id = blogMove.getMoveUserId();
		String as = "A1458B8DA9E5C29";
		String cp = "5BD9656C82495E1";
		String _signature = "nMwJOxAdxyHL-0FgbZm0sZzMCS";
		if (num == 1) {
			oneUrl = String.format(oneUrl, user_id, max_behot_time, as, cp, _signature);
		} else {

		}
		return oneUrl;
	}

	/**
	 * @date Oct 31, 2018 3:59:49 PM
	 * @Desc 获取文章list请求url
	 * @param blogMove
	 * @param num
	 * @param max_behot_time
	 * @return
	 */
	public static String getTouTiaoArticleMsgJsonString(Elements pageMsg22) {
		String value = null;

		try {
			String data;
			// 正文
			/* 循环遍历script下面的JS变量 */
			for (Element element : pageMsg22) {
				/* 取得JS变量数组 */
				data = element.data().toString();
				/* 过滤variable为空的数据 */
				if (data.contains("BASE_DATA") && data.contains("=")) {
					/* 取到满足条件的JS变量 */
					if (data.contains("articleInfo") && data.contains("content")) {
						value = data.substring(data.indexOf("BASE_DATA") + 11).trim();
						value = value.trim().endsWith("//]]>") ? (value.trim().substring(0, value.trim().length() - 8))
								: value;
						value = value.replace(".replace(/<br \\/>/ig, '')", "");
						value = value.replaceAll("'", "\"");
						value = value.replaceAll("\\\\", "\\\\\\\\");

						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	// /**
	// * @date Oct 17, 2018 1:10:19 PM
	// * @Desc 获取标题
	// * @param doc
	// * @return
	// */
	// public static String getTouTiaoArticleTitle(Document doc) {
	// // 标题
	// Element pageMsg2 =
	// doc.select("div.article-detail").first().select("h1.header").first();
	// return pageMsg2.ownText();
	// }
	//
	// /**
	// * @date Oct 17, 2018 1:10:28 PM
	// * @Desc 获取作者
	// * @param doc
	// * @return
	// */
	// public static String getTouTiaoArticleAuthor(Document doc) {
	// Element pageMsg2 =
	// doc.select("div.article-detail").first().select("a.__user").first().select("span").first();
	// return pageMsg2.html();
	// }
	//
	// /**
	// * @date Oct 17, 2018 1:10:33 PM
	// * @Desc 获取时间
	// * @param doc
	// * @return
	// */
	// public static Date getTouTiaoArticleTime(Document doc) {
	// Element pageMsg2 =
	// doc.select("div.article-detail").first().select("div.item").first();
	// String date = pageMsg2.ownText().trim();
	// if (date.startsWith("发布于")) {
	// date = date.substring(date.indexOf("发布于") + 3).trim();
	// }
	// if (date.indexOf(CommonSymbolicConstant.FORWARD_SLASH) < 4) {
	// date = DateUtils.format(new Date(), DateUtils.YYYY) +
	// CommonSymbolicConstant.FORWARD_SLASH + date;
	// }
	// // 这地方时间格式变化太多暂时不实现
	// Date d = DateUtils.formatStringDate(date,
	// DateUtils.YYYY_MM_DD_HH_MM_SS3);
	// // 注意有些格式不正确
	// return d == null ? new Date() : d;
	// }
	//
	/**
	 * @date Oct 17, 2018 1:10:37 PM
	 * @Desc 获取类型
	 * @param doc
	 * @return
	 */
	public static String getTouTiaoArticleType(String msgJsonString, String moveArticleType) {
		if ("默认".equals(moveArticleType)) {
			return "原创";
		}

		return moveArticleType;
	}

	/**
	 * @date Oct 17, 2018 1:10:41 PM
	 * @Desc 获取正文
	 * @param content
	 * @param object
	 * @param blogcontent
	 * @return
	 */
	public static String getTouTiaoArticleContent(String content, Blogmove blogMove, Blogcontent blogcontent) {
		String images;
		// 注意是否需要替换图片
		if (blogMove.getMoveSaveImg() == 0) {
			// 保存图片到本地
			// 先获取所有图片连接，再按照每个链接下载图片，最后替换原有链接
			// 先创建一个文件夹
			// 先创建一个临时文件夹
			String blogFileName = String.valueOf(UUID.randomUUID());
			FileUtils.createFolder(FilePathConfig.getUploadBlogPath() + File.separator + blogFileName);
			blogcontent.setBlogFileName(blogFileName);
			// 匹配出所有链接
			List<String> imgList = BlogMoveCommonUtils.getArticleImgList(content);
			// 下载并返回重新生成的imgurllist
			List<String> newImgList = getTouTiaoArticleNewImgList(blogMove, imgList, blogFileName);
			// 拼接文章所有链接
			images = BlogMoveCommonUtils.getArticleImages(newImgList);
			blogcontent.setImages(images);
			// 替换所有链接按顺序
			content = getTouTiaoNewArticleContent(content, imgList, newImgList);

		}

		return content;
	}

	/**
	 * @date Oct 22, 2018 3:31:40 PM
	 * @Desc
	 * @param content
	 * @param imgList
	 * @param newImgList
	 * @return
	 */
	private static String getTouTiaoNewArticleContent(String content, List<String> imgList, List<String> newImgList) {
		Document doc = Jsoup.parse(content);
		Elements imgTags = doc.select("img[src]");
		if (imgList == null || imgList.size() < 1 || newImgList == null || newImgList.size() < 1 || imgTags == null
				|| "".equals(imgTags)) {
			return content;
		}
		for (int i = 0; i < imgTags.size(); i++) {
			imgTags.get(i).attr("src", newImgList.get(i));
		}
		return doc.body().toString();
	}

	/**
	 * @date Oct 22, 2018 3:31:33 PM
	 * @Desc
	 * @param imgList
	 * @return
	 */
	private static List<String> getTouTiaoArticleNewImgList(Blogmove blogMove, List<String> imgList,
			String blogFileName) {
		// 下载图片
		if (imgList == null || imgList.size() < 1) {
			return null;
		}
		List<String> newImgList = new ArrayList<String>();
		String uuid;
		String filePath = FilePathConfig.getUploadBlogPath() + File.separator + blogFileName;
		String fileName;
		for (String url : imgList) {
			uuid = String.valueOf(UUID.randomUUID());
			fileName = BlogMoveCommonUtils.downloadImg(url, uuid, filePath, blogMove);
			// 打水印
			if (blogMove.getBlogset().getBasicsetAddWaterMark() == 0 && blogMove.getMoveAddWaterMark() == 0
					&& blogMove.getBlogset().getBasicsetWaterMarkMsg() != null
					&& !CommonSymbolicConstant.EMPTY_STRING.equals(blogMove.getBlogset().getBasicsetWaterMarkMsg())) {
				BlogMoveCommonUtils.addImgWaterMark(blogFileName, fileName, blogMove);
			}

			newImgList.add(FileOtherConstant.FILE_JUMP_PATH_PREFIX3 + blogFileName + File.separator + fileName);
		}

		return newImgList;
	}

}
