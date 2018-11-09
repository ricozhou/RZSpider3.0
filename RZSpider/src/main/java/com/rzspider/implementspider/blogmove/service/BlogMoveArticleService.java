package com.rzspider.implementspider.blogmove.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.socket.TextMessage;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.UnexpectedPage;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.rzspider.common.constant.WebSocketConstants;
import com.rzspider.common.constant.project.BlogConstant;
import com.rzspider.common.utils.DateUtils;
import com.rzspider.common.utils.security.ShiroUtils;
import com.rzspider.framework.websocket.service.WebSocketPushHandler;
import com.rzspider.implementspider.blogmove.utils.BlogMoveCSDNUtils;
import com.rzspider.implementspider.blogmove.utils.BlogMoveCnBlogUtils;
import com.rzspider.implementspider.blogmove.utils.BlogMoveCommonUtils;
import com.rzspider.implementspider.blogmove.utils.BlogMoveJianShuUtils;
import com.rzspider.implementspider.blogmove.utils.BlogMoveOsChinaUtils;
import com.rzspider.implementspider.blogmove.utils.BlogMoveTouTiaoUtils;
import com.rzspider.project.blog.blogcontent.domain.Blogcontent;
import com.rzspider.project.blog.blogcontent.domain.Blogmove;

/**
 * @author ricozhou
 * @date Oct 17, 2018 12:28:21 PM
 * @Desc
 */
public class BlogMoveArticleService {

	/**
	 * @date Oct 17, 2018 12:30:46 PM
	 * @Desc
	 * @param blogMove
	 * @param oneUrl
	 * @return
	 * @throws IOException
	 * @throws MalformedURLException
	 * @throws FailingHttpStatusCodeException
	 */
	public void getCSDNArticleUrlList(Blogmove blogMove, String oneUrl, List<String> urlList)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		// 模拟浏览器操作
		// 创建WebClient
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		// 关闭css代码功能
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setCssEnabled(false);
		// 如若有可能找不到文件js则加上这句代码
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		// 获取第一级网页html
		HtmlPage page = webClient.getPage(oneUrl);
		// System.out.println(page.asXml());
		Document doc = Jsoup.parse(page.asXml());
		Element pageMsg22 = doc.select("div.article-list").first();
		if (pageMsg22 == null) {
			return;
		}
		Elements pageMsg = pageMsg22.select("div.article-item-box");
		Element linkNode;
		for (Element e : pageMsg) {
			linkNode = e.select("h4 a").first();
			// 不知为何，所有的bloglist第一条都是这个：https://blog.csdn.net/yoyo_liyy/article/details/82762601
			if (linkNode.attr("href").contains(blogMove.getMoveUserId())) {
				if (urlList.size() < blogMove.getMoveNum()) {
					urlList.add(linkNode.attr("href"));
				} else {
					break;
				}
			}
		}
		return;
	}

	/**
	 * @date Oct 17, 2018 12:46:52 PM
	 * @Desc 获取详细信息
	 * @param blogMove
	 * @param url
	 * @return
	 * @throws IOException
	 * @throws MalformedURLException
	 * @throws FailingHttpStatusCodeException
	 */
	public Blogcontent getCSDNArticleMsg(Blogmove blogMove, String url, List<Blogcontent> bList)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		Blogcontent blogcontent = new Blogcontent();
		blogcontent.setArticleSource(blogMove.getMoveWebsiteId());
		// 模拟浏览器操作
		// 创建WebClient
		WebClient webClient = new WebClient(BrowserVersion.EDGE);
		// 关闭css代码功能
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setCssEnabled(false);
		// 如若有可能找不到文件js则加上这句代码
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		// 获取第一级网页html
		HtmlPage page = webClient.getPage(url);

		Document doc = Jsoup.parse(page.asXml());
		// 获取标题
		String title = BlogMoveCSDNUtils.getCSDNArticleTitle(doc);
		// 是否重复去掉
		if (blogMove.getMoveRemoveRepeat() == 0) {
			// 判断是否重复
			if (BlogMoveCommonUtils.articleRepeat(bList, title)) {
				WebSocketPushHandler.sendMessageToUser(WebSocketConstants.WEBSOCKET_PARAMS_BLOGMOVEID,
						ShiroUtils.getLoginName(), new TextMessage(
								String.format("-->> 过滤掉文章 -- <a href=\"%s\" target=\"_blank\">%s</a> ", url, title)));
				return null;
			}
		}
		blogcontent.setTitle(title);
		// 获取作者
		blogcontent.setAuthor(BlogMoveCSDNUtils.getCSDNArticleAuthor(doc));
		// 获取时间
		if (blogMove.getMoveUseOriginalTime() == 0) {
			blogcontent.setGtmCreate(BlogMoveCSDNUtils.getCSDNArticleTime(doc));
		} else {
			blogcontent.setGtmCreate(new Date());
		}
		blogcontent.setGtmModified(new Date());
		// 获取类型
		blogcontent.setType(BlogMoveCSDNUtils.getCSDNArticleType(doc,blogMove.getMoveArticleType()));
		// 获取正文
		blogcontent.setContent(BlogMoveCSDNUtils.getCSDNArticleContent(doc, blogMove, blogcontent));

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

	/**
	 * @date Oct 17, 2018 12:30:46 PM
	 * @Desc
	 * @param blogMove
	 * @param oneUrl
	 * @return
	 * @throws IOException
	 * @throws MalformedURLException
	 * @throws FailingHttpStatusCodeException
	 */
	public void getJianShuArticleUrlList(Blogmove blogMove, String oneUrl, List<String> urlList)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		// 模拟浏览器操作
		// 创建WebClient
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		// 关闭css代码功能
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setCssEnabled(false);
		// 如若有可能找不到文件js则加上这句代码
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		// 获取第一级网页html
		HtmlPage page = webClient.getPage(oneUrl);
		// System.out.println(page.asXml());
		Document doc = Jsoup.parse(page.asXml());
		Element pageMsg22 = doc.select("ul.note-list").first();
		if (pageMsg22 == null) {
			return;
		}
		Elements pageMsg = pageMsg22.select("div.content");
		Element linkNode;
		for (Element e : pageMsg) {
			linkNode = e.select("a.title").first();
			if (linkNode == null) {
				continue;
			}
			if (urlList.size() < blogMove.getMoveNum()) {
				urlList.add(BlogConstant.BLOG_BLOGMOVE_WEBSITE_BASEURL_JIANSHU + linkNode.attr("href"));
			} else {
				break;
			}
		}
		return;
	}

	/**
	 * @date Oct 17, 2018 12:46:52 PM
	 * @Desc 获取详细信息
	 * @param blogMove
	 * @param url
	 * @return
	 * @throws IOException
	 * @throws MalformedURLException
	 * @throws FailingHttpStatusCodeException
	 */
	public Blogcontent getJianShuArticleMsg(Blogmove blogMove, String url, List<Blogcontent> bList)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		Blogcontent blogcontent = new Blogcontent();
		blogcontent.setArticleSource(blogMove.getMoveWebsiteId());
		// 模拟浏览器操作
		// 创建WebClient
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		// 关闭css代码功能
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setCssEnabled(false);
		// 如若有可能找不到文件js则加上这句代码
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		// 获取第一级网页html
		HtmlPage page = webClient.getPage(url);

		Document doc = Jsoup.parse(page.asXml());
		// 获取标题
		String title = BlogMoveJianShuUtils.getJianShuArticleTitle(doc);
		// 是否重复去掉
		if (blogMove.getMoveRemoveRepeat() == 0) {
			// 判断是否重复
			if (BlogMoveCommonUtils.articleRepeat(bList, title)) {
				WebSocketPushHandler.sendMessageToUser(WebSocketConstants.WEBSOCKET_PARAMS_BLOGMOVEID,
						ShiroUtils.getLoginName(), new TextMessage(
								String.format("-->> 过滤掉文章 -- <a href=\"%s\" target=\"_blank\">%s</a> ", url, title)));
				return null;
			}
		}
		blogcontent.setTitle(title);
		// 获取作者
		blogcontent.setAuthor(BlogMoveJianShuUtils.getJianShuArticleAuthor(doc));
		// 获取时间
		if (blogMove.getMoveUseOriginalTime() == 0) {
			blogcontent.setGtmCreate(BlogMoveJianShuUtils.getJianShuArticleTime(doc));
		} else {
			blogcontent.setGtmCreate(new Date());
		}
		blogcontent.setGtmModified(new Date());
		// 获取类型
		blogcontent.setType(BlogMoveJianShuUtils.getJianShuArticleType(doc,blogMove.getMoveArticleType()));
		// 获取正文
		blogcontent.setContent(BlogMoveJianShuUtils.getJianShuArticleContent(doc, blogMove, blogcontent));

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

	/**
	 * @date Oct 17, 2018 12:30:46 PM
	 * @Desc
	 * @param blogMove
	 * @param oneUrl
	 * @return
	 * @throws IOException
	 * @throws MalformedURLException
	 * @throws FailingHttpStatusCodeException
	 */
	public void getOsChinaArticleUrlList(Blogmove blogMove, String oneUrl, List<String> urlList)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		// 模拟浏览器操作
		// 创建WebClient
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		// 关闭css代码功能
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setCssEnabled(false);
		// 如若有可能找不到文件js则加上这句代码
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		// 获取第一级网页html
		HtmlPage page = webClient.getPage(oneUrl);
		// System.out.println(page.asXml());
		Document doc = Jsoup.parse(page.asXml());
		Element pageMsg22 = doc.select("div.list-container.space-list-container").first();
		if (pageMsg22 == null) {
			return;
		}
		Elements pageMsg = pageMsg22.select("div.content");
		Element linkNode;
		for (Element e : pageMsg) {
			linkNode = e.select("a.header").first();
			if (linkNode == null) {
				continue;
			}
			if (urlList.size() < blogMove.getMoveNum()) {
				urlList.add(linkNode.attr("href"));
			} else {
				break;
			}
		}
		return;
	}

	/**
	 * @date Oct 17, 2018 12:46:52 PM
	 * @Desc 获取详细信息
	 * @param blogMove
	 * @param url
	 * @return
	 * @throws IOException
	 * @throws MalformedURLException
	 * @throws FailingHttpStatusCodeException
	 */
	public Blogcontent getOsChinaArticleMsg(Blogmove blogMove, String url, List<Blogcontent> bList)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		Blogcontent blogcontent = new Blogcontent();
		blogcontent.setArticleSource(blogMove.getMoveWebsiteId());
		// 模拟浏览器操作
		// 创建WebClient
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		// 关闭css代码功能
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setCssEnabled(false);
		// 如若有可能找不到文件js则加上这句代码
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		// 获取第一级网页html
		HtmlPage page = webClient.getPage(url);

		Document doc = Jsoup.parse(page.asXml());
		// 获取标题
		String title = BlogMoveOsChinaUtils.getOsChinaArticleTitle(doc);
		// 是否重复去掉
		if (blogMove.getMoveRemoveRepeat() == 0) {
			// 判断是否重复
			if (BlogMoveCommonUtils.articleRepeat(bList, title)) {
				WebSocketPushHandler.sendMessageToUser(WebSocketConstants.WEBSOCKET_PARAMS_BLOGMOVEID,
						ShiroUtils.getLoginName(), new TextMessage(
								String.format("-->> 过滤掉文章 -- <a href=\"%s\" target=\"_blank\">%s</a> ", url, title)));
				return null;
			}
		}
		blogcontent.setTitle(title);
		// 获取作者
		blogcontent.setAuthor(BlogMoveOsChinaUtils.getOsChinaArticleAuthor(doc));
		// 获取时间
		if (blogMove.getMoveUseOriginalTime() == 0) {
			blogcontent.setGtmCreate(BlogMoveOsChinaUtils.getOsChinaArticleTime(doc));
		} else {
			blogcontent.setGtmCreate(new Date());
		}
		blogcontent.setGtmModified(new Date());
		// 获取类型
		blogcontent.setType(BlogMoveOsChinaUtils.getOsChinaArticleType(doc,blogMove.getMoveArticleType()));
		// 获取正文
		blogcontent.setContent(BlogMoveOsChinaUtils.getOsChinaArticleContent(doc, blogMove, blogcontent));

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

	/**
	 * @date Oct 17, 2018 12:30:46 PM
	 * @Desc
	 * @param blogMove
	 * @param oneUrl
	 * @return
	 * @throws IOException
	 * @throws MalformedURLException
	 * @throws FailingHttpStatusCodeException
	 */
	public void getCnBlogArticleUrlList(Blogmove blogMove, String oneUrl, List<String> urlList)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		// 模拟浏览器操作
		// 创建WebClient
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		// 关闭css代码功能
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setCssEnabled(false);
		// 如若有可能找不到文件js则加上这句代码
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		// 获取第一级网页html
		HtmlPage page = webClient.getPage(oneUrl);
		// System.out.println(page.asXml());
		Document doc = Jsoup.parse(page.asXml());
		Elements pageMsg = doc.select("div.postTitle");
		Element linkNode;
		for (Element e : pageMsg) {
			linkNode = e.select("a.postTitle2").first();
			if (linkNode == null) {
				continue;
			}
			if (urlList.size() < blogMove.getMoveNum()) {
				urlList.add(linkNode.attr("href"));
			} else {
				break;
			}
		}
		return;
	}

	/**
	 * @date Oct 17, 2018 12:46:52 PM
	 * @Desc 获取详细信息
	 * @param blogMove
	 * @param url
	 * @return
	 * @throws IOException
	 * @throws MalformedURLException
	 * @throws FailingHttpStatusCodeException
	 */
	public Blogcontent getCnBlogArticleMsg(Blogmove blogMove, String url, List<Blogcontent> bList)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		Blogcontent blogcontent = new Blogcontent();
		blogcontent.setArticleSource(blogMove.getMoveWebsiteId());
		// 模拟浏览器操作
		// 创建WebClient
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		// 关闭css代码功能
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setCssEnabled(false);
		// 如若有可能找不到文件js则加上这句代码
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		// 获取第一级网页html
		HtmlPage page = webClient.getPage(url);

		Document doc = Jsoup.parse(page.asXml());
		// 获取标题
		String title = BlogMoveCnBlogUtils.getCnBlogArticleTitle(doc);
		// 是否重复去掉
		if (blogMove.getMoveRemoveRepeat() == 0) {
			// 判断是否重复
			if (BlogMoveCommonUtils.articleRepeat(bList, title)) {
				WebSocketPushHandler.sendMessageToUser(WebSocketConstants.WEBSOCKET_PARAMS_BLOGMOVEID,
						ShiroUtils.getLoginName(), new TextMessage(
								String.format("-->> 过滤掉文章 -- <a href=\"%s\" target=\"_blank\">%s</a> ", url, title)));
				return null;
			}
		}
		blogcontent.setTitle(title);
		// 获取作者
		blogcontent.setAuthor(BlogMoveCnBlogUtils.getCnBlogArticleAuthor(doc));
		// 获取时间
		if (blogMove.getMoveUseOriginalTime() == 0) {
			blogcontent.setGtmCreate(BlogMoveCnBlogUtils.getCnBlogArticleTime(doc));
		} else {
			blogcontent.setGtmCreate(new Date());
		}
		blogcontent.setGtmModified(new Date());
		// 获取类型
		blogcontent.setType(BlogMoveCnBlogUtils.getCnBlogArticleType(doc,blogMove.getMoveArticleType()));
		// 获取正文
		blogcontent.setContent(BlogMoveCnBlogUtils.getCnBlogArticleContent(doc, blogMove, blogcontent));

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

	/**
	 * @date Oct 17, 2018 12:30:46 PM
	 * @Desc
	 * @param blogMove
	 * @param oneUrl
	 * @return
	 * @throws IOException
	 * @throws MalformedURLException
	 * @throws FailingHttpStatusCodeException
	 */
	public String getTouTiaoArticleUrlList(Blogmove blogMove, String oneUrl, List<String> urlList)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		String max_behot_time = "0";
		// 模拟浏览器操作
		// 创建WebClient
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		// 关闭css代码功能
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setCssEnabled(false);
		// 如若有可能找不到文件js则加上这句代码
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);

		Map<String, String> additionalHeaders = new HashMap<String, String>();
		additionalHeaders.put("user-agent",
				"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
		WebRequest request = new WebRequest(new URL(oneUrl), HttpMethod.GET);
		request.setAdditionalHeaders(additionalHeaders);
		UnexpectedPage page2 = webClient.getPage(request);
		String result = IOUtils.toString(page2.getInputStream(), StandardCharsets.UTF_8);

		JSONObject json = JSON.parseObject(result);
		JSONArray jsonarray = (JSONArray) json.get("data");
		JSONObject json2 = (JSONObject) json.get("next");
		// 用于返回留下一页使用
		max_behot_time = json2.getString("max_behot_time");

		// 读取url
		JSONObject jsonObject;
		String url;
		for (Object obj : jsonarray) {
			jsonObject = (JSONObject) obj;
			url = jsonObject.getString("source_url");
			if (url != null) {
				url = url.substring(6, url.length() - 1);
				if (urlList.size() < blogMove.getMoveNum()) {
					urlList.add("https://www.toutiao.com/i" + url);
				} else {
					break;
				}
			} else {
				url = "";
				continue;
			}
		}

		return max_behot_time;
	}

	/**
	 * @date Oct 17, 2018 12:46:52 PM
	 * @Desc 获取详细信息
	 * @param blogMove
	 * @param url
	 * @return
	 * @throws IOException
	 * @throws MalformedURLException
	 * @throws FailingHttpStatusCodeException
	 */
	public Blogcontent getTouTiaoArticleMsg(Blogmove blogMove, String url, List<Blogcontent> bList)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		Blogcontent blogcontent = new Blogcontent();
		blogcontent.setArticleSource(blogMove.getMoveWebsiteId());
		// 模拟浏览器操作
		// 创建WebClient
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		// 关闭css代码功能
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setCssEnabled(false);
		// 如若有可能找不到文件js则加上这句代码
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		// 获取第一级网页html
		HtmlPage page = webClient.getPage(url);

		Document doc = Jsoup.parse(page.asXml());
		Elements pageMsg22 = doc.body().getElementsByTag("script");

		String msgJsonString = BlogMoveTouTiaoUtils.getTouTiaoArticleMsgJsonString(pageMsg22);

		String content = null;
		String title = null;
		String author = null;
		Date date = null;
		if (msgJsonString != null && !"".equals(msgJsonString)) {
			System.out.println(msgJsonString);
			// 解析
			JSONObject json = JSON.parseObject(msgJsonString);
			json = (JSONObject) json.get("articleInfo");
			content = json.getString("content");
			// 转义
			content = StringEscapeUtils.unescapeHtml3(content);
			title = json.getString("title");
			json = (JSONObject) json.get("subInfo");
			author = json.getString("source");
			date = DateUtils.formatStringDate(json.getString("time"), DateUtils.YYYY_MM_DD_HH_MM_SS);
			date = date == null ? new Date() : date;
		}

		// 是否重复去掉
		if (blogMove.getMoveRemoveRepeat() == 0) {
			// 判断是否重复
			if (BlogMoveCommonUtils.articleRepeat(bList, title)) {
				WebSocketPushHandler.sendMessageToUser(WebSocketConstants.WEBSOCKET_PARAMS_BLOGMOVEID,
						ShiroUtils.getLoginName(), new TextMessage(
								String.format("-->> 过滤掉文章 -- <a href=\"%s\" target=\"_blank\">%s</a> ", url, title)));
				return null;
			}
		}
		blogcontent.setTitle(title);
		// 获取作者
		blogcontent.setAuthor(author);
		// 获取时间
		if (blogMove.getMoveUseOriginalTime() == 0) {
			blogcontent.setGtmCreate(date);
		} else {
			blogcontent.setGtmCreate(new Date());
		}
		blogcontent.setGtmModified(new Date());
		// 获取类型
		blogcontent.setType("原创");
		blogcontent.setType(BlogMoveTouTiaoUtils.getTouTiaoArticleType(msgJsonString,blogMove.getMoveArticleType()));
		// 获取正文
		blogcontent.setContent(BlogMoveTouTiaoUtils.getTouTiaoArticleContent(content, blogMove, blogcontent));

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
