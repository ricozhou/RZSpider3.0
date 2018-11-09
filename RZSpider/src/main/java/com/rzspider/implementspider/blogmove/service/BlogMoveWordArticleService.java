package com.rzspider.implementspider.blogmove.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.web.socket.TextMessage;
import org.xml.sax.SAXException;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.rzspider.common.constant.CommonSymbolicConstant;
import com.rzspider.common.constant.WebSocketConstants;
import com.rzspider.common.utils.DateUtils;
import com.rzspider.common.utils.security.ShiroUtils;
import com.rzspider.framework.config.FilePathConfig;
import com.rzspider.framework.websocket.service.WebSocketPushHandler;
import com.rzspider.implementspider.blogmove.utils.BlogMoveCSDNUtils;
import com.rzspider.implementspider.blogmove.utils.BlogMoveCommonUtils;
import com.rzspider.implementspider.blogmove.utils.BlogMoveWordUtils;
import com.rzspider.project.blog.blogcontent.domain.Blogcontent;
import com.rzspider.project.blog.blogcontent.domain.Blogmove;
import com.rzspider.project.common.file.utilt.FileUtils;

/**
 * @author ricozhou
 * @date Oct 29, 2018 5:06:05 PM
 * @Desc
 */
public class BlogMoveWordArticleService {

	/**
	 * @date Oct 29, 2018 5:07:15 PM
	 * @Desc
	 * @param blogMove
	 * @param string
	 * @param string2
	 * @param bList
	 * @return
	 * @throws SAXException
	 * @throws TransformerException
	 * @throws ParserConfigurationException
	 * @throws IOException
	 */
	public static Blogcontent getWordArticleMsg(Blogmove blogMove, String fileName, String fileOName,
			List<Blogcontent> bList)
			throws IOException, ParserConfigurationException, TransformerException, SAXException {
		Blogcontent blogcontent = new Blogcontent();
		blogcontent.setArticleSource("WORD");

		// 获取标题
		// 文章标题即word文件名去掉后缀
		String title = FileUtils.getFileNameBeforePoint(fileOName);
		// 是否重复去掉
		if (blogMove.getMoveRemoveRepeat() == 0) {
			// 判断是否重复
			if (BlogMoveCommonUtils.articleRepeat(bList, title)) {
				WebSocketPushHandler.sendMessageToUser(WebSocketConstants.WEBSOCKET_PARAMS_BLOGMOVEID,
						ShiroUtils.getLoginName(), new TextMessage(String.format("-->> 过滤掉文章 --" + title + " ")));
				return null;
			}
		}
		blogcontent.setTitle(title);
		// 获取作者
		blogcontent.setAuthor(blogMove.getBlogAuthor());
		// 获取时间
		blogcontent.setGtmCreate(new Date());
		blogcontent.setGtmModified(blogcontent.getGtmCreate());
		// 获取类型
		blogcontent.setType(BlogMoveWordUtils.getWordArticleType(blogMove.getMoveArticleType()));
		// 获取正文
		// 按照路径解析word并返回html字符串
		blogcontent.setContent(BlogMoveWordUtils.getWordArticleContent(fileName, blogMove, blogcontent));

		// 是否打水印
		// 打水印
		if (blogMove.getBlogset().getBasicsetAddWaterMark() == 0 && blogMove.getMoveAddWaterMark() == 0
				&& blogMove.getBlogset().getBasicsetWaterMarkMsg() != null
				&& !CommonSymbolicConstant.EMPTY_STRING.equals(blogMove.getBlogset().getBasicsetWaterMarkMsg())) {
			String[] fileNames = new File(
					FilePathConfig.getUploadBlogPath() + File.separator + blogcontent.getBlogFileName()).list();
			for (String fileImgName : fileNames) {
				BlogMoveCommonUtils.addImgWaterMark(blogcontent.getBlogFileName(), fileImgName, blogMove);
			}
		}

		// 设置其他
		blogcontent.setStatus(blogMove.getMoveBlogStatus());
		blogcontent.setBlogColumnName(blogMove.getMoveColumn());
		// 特殊处理
		blogcontent.setArticleEditor(blogMove.getMoveArticleEditor());
		blogcontent.setShowId(DateUtils.format(new Date(), DateUtils.YYYYMMDDHHMMSSSSS));
		blogcontent.setAllowComment(0);
		blogcontent.setAllowPing(0);
		blogcontent.setAllowDownload(0);
		blogcontent.setShowIntroduction(1);
		blogcontent.setIntroduction("");
		blogcontent.setPrivateArticle(1);

		return blogcontent;
	}

}
