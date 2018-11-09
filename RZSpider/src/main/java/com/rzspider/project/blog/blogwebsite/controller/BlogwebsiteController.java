package com.rzspider.project.blog.blogwebsite.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.rzspider.common.constant.RegularExpressionConstant;
import com.rzspider.common.constant.ReturnMessageConstant;
import com.rzspider.common.utils.DateUtils;
import com.rzspider.framework.web.controller.BaseController;
import com.rzspider.framework.web.domain.Message;
import com.rzspider.framework.web.page.TableDataInfo;
import com.rzspider.project.blog.blogbrowe.domain.Blogbrowe;
import com.rzspider.project.blog.blogbrowe.service.IBlogbroweService;
import com.rzspider.project.blog.blogcolumn.domain.Blogcolumn;
import com.rzspider.project.blog.blogcolumn.service.IBlogcolumnService;
import com.rzspider.project.blog.blogcomment.domain.Blogcomment;
import com.rzspider.project.blog.blogcomment.service.IBlogcommentService;
import com.rzspider.project.blog.blogcontent.domain.Blogcontent;
import com.rzspider.project.blog.blogcontent.domain.BlogcontentRecommend;
import com.rzspider.project.blog.blognotice.domain.Blognotice;
import com.rzspider.project.blog.blognotice.service.IBlognoticeService;
import com.rzspider.project.blog.blogset.domain.Blogset;
import com.rzspider.project.blog.blogset.service.IBlogsetService;
import com.rzspider.project.blog.blogtags.domain.Blogtags;
import com.rzspider.project.blog.blogtags.service.IBlogtagsService;
import com.rzspider.project.blog.blogwebsite.service.IBlogwebsiteService;
import com.rzspider.project.common.checkutils.CheckStringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 博客网站 信息操作处理
 * 
 * @author ricozhou
 * @date 2018-06-12
 */
@Controller
@RequestMapping("/rzblog")
public class BlogwebsiteController extends BaseController {
	@Autowired
	private IBlogwebsiteService blogwebsiteService;
	@Autowired
	private IBlogsetService blogsetService;
	@Autowired
	private IBlogcolumnService blogcolumnService;
	@Autowired
	private IBlogtagsService blogtagsService;
	@Autowired
	private IBlognoticeService blognoticeService;
	@Autowired
	private IBlogbroweService blogbroweService;
	@Autowired
	private IBlogcommentService blogcommentService;

	// 到主界面
	@GetMapping()
	public String blog(Model model) {
		// 把设置数据发送到前端
		model.addAttribute("blogset", blogsetService.getBlogsetMsg());
		return "blog/index/main";
	}

	/**
	 * 查询文章内容列表 获取文章列表用于展示 blog/open/list?type=article&limit=10&offset=
	 */
	@PostMapping("/blogList")
	@ResponseBody
	public TableDataInfo blogList(Blogcontent blogcontent) {
		// 不要把文章内容发送过去
		startPage();
		blogcontent.setStatus(1);
		List<Blogcontent> list = blogwebsiteService.selectBlogcontentListWithoutContent(blogcontent);
		return getDataTable(list);
	}

	/**
	 * 根据id查询，反馈到单独页面
	 */
	@GetMapping("/blogcontent/details/{showId}")
	public String blogcontent(@PathVariable("showId") String showId, Model model) {
		// 把设置数据发送到前端
		Blogset blogset = blogsetService.getBlogsetMsg();
		model.addAttribute("blogset", blogset);

		// 把文章信息发送到前端
		Blogcontent blogcontent = blogwebsiteService.selectBlogcontentByShowId(showId);

		// 主页其他信息，上下篇，相关推荐等
		if (blogcontent == null) {
			return "blog/index/noblog";
		}
		model.addAttribute("blogcontent", blogcontent);
		model.addAttribute("upblogcontent",
				blogwebsiteService.selectUpBlogcontentByCidWithoutContent(blogcontent.getCid()));
		model.addAttribute("downblogcontent",
				blogwebsiteService.selectDownBlogcontentByCidWithoutContent(blogcontent.getCid()));

		// 相关文章，根据专栏和标签获取前十条
		model.addAttribute("drelatedblogcontentList",
				blogwebsiteService.selectRelatedBlogcontentByCidWithoutContent(blogcontent));

		// 把此专栏的信息一起发送到前端
		Blogcolumn blogcolumn = blogcolumnService.selectBlogcolumnMessageByColumn(blogcontent.getBlogColumnName(),
				false);
		model.addAttribute("blogcolumn", blogcolumn);

		// 标签信息
		// 标签
		List<Blogtags> blogtags = blogtagsService.selectBlogtagsByCid(blogcontent.getCid());
		model.addAttribute("blogtags", blogtags);

		// 评论信息
		// 评论可能很多后台先验证一下是否查询
		List<Blogcomment> blogcomentList = new ArrayList<Blogcomment>();
		if (blogset.getBasicsetGlobalShowComment() == 0) {
			blogcomentList = blogcommentService.selectBlogcommentListToShow(showId);
		}
		model.addAttribute("blogcomentList", blogcomentList);

		return "blog/index/blogcontent";
	}

	/**
	 * 根据url,到专栏 主专栏
	 */
	@GetMapping("/{parentUrl}")
	public String columnListM(@PathVariable("parentUrl") String parentUrl, Model model) {
		// 把设置数据发送到前端
		model.addAttribute("blogset", blogsetService.getBlogsetMsg());

		// 把此专栏的信息一起发送到前端
		Blogcolumn blogcolumn = blogcolumnService.selectBlogcolumnMessageByUrl(parentUrl, true);
		if (blogcolumn == null) {
			return "blog/index/noblog";
		}
		model.addAttribute("blogcolumn", blogcolumn);
		return "blog/index/columnlist";
	}

	/**
	 * 根据url,到专栏 次专栏
	 */
	@GetMapping("/{parentUrl}/{childrenUrl}")
	public String columnListC(@PathVariable("parentUrl") String parentUrl,
			@PathVariable("childrenUrl") String childrenUrl, Model model) {
		// 把设置数据发送到前端
		model.addAttribute("blogset", blogsetService.getBlogsetMsg());

		Blogcolumn blogcolumn = blogcolumnService.selectBlogcolumnMessageByUrl(childrenUrl, false);
		if (blogcolumn == null) {
			return "blog/index/noblog";
		}
		model.addAttribute("blogcolumn", blogcolumn);
		return "blog/index/columnlist";
	}

	/**
	 * 查询专栏文章内容列表
	 */
	@PostMapping("/columnList")
	@ResponseBody
	public TableDataInfo columnList(Blogcontent blogcontent) {
		// 不要把文章内容发送过去
		startPage();
		// 已发布
		blogcontent.setStatus(1);
		List<Blogcontent> list = blogwebsiteService.selectBlogColumncontentListWithoutContent(blogcontent);
		return getDataTable(list);
	}

	/**
	 * 请求专栏信息
	 */
	@PostMapping("/columnMsgList")
	@ResponseBody
	public List<Blogcolumn> columnMsgList() {
		// 专栏信息
		List<Blogcolumn> blogcolumnList = blogcolumnService.selectBlogcolumnMessageList();
		return blogcolumnList;
	}

	/**
	 * 请求标签信息
	 */
	@PostMapping("/tagsMsgList")
	@ResponseBody
	public List<Blogtags> tagsMsgList() {
		// 专栏信息
		List<Blogtags> blogtagsList = blogtagsService.selectBlogtagsListWithoutImg(new Blogtags(0, null));
		return blogtagsList;
	}

	/**
	 * 请求推荐信息
	 */
	@PostMapping("/blogRecommendList")
	@ResponseBody
	public BlogcontentRecommend blogRecommendList() {
		// 推荐信息
		BlogcontentRecommend blogRecommendList = blogwebsiteService.selectBlogcontentRecommendListWithoutContent();
		return blogRecommendList;
	}

	/**
	 * 请求推荐信息 将主页推荐单独请求，减少手机浏览请求量
	 */
	@PostMapping("/blogRecommendList2")
	@ResponseBody
	public BlogcontentRecommend blogRecommendList2() {
		// 推荐信息
		BlogcontentRecommend blogRecommendList2 = blogwebsiteService.selectBlogcontentRecommendList2WithoutContent();
		return blogRecommendList2;
	}

	/**
	 * 根据标签到标签页
	 */
	@GetMapping("/blogtags")
	public String blogtagsList(String tags, Model model) {
		// 校验参数
		if (!CheckStringUtils.checkStringByRegularExpression(tags,
				RegularExpressionConstant.REGULAR_EXPRESSION_ONLY_CNENNUM)) {
			return "blog/index/noblog";
		}
		// 把设置数据发送到前端
		model.addAttribute("blogset", blogsetService.getBlogsetMsg());

		Blogtags blogtags = blogtagsService.selectBlogtagsByStatusAndName(new Blogtags(0, tags));
		if (blogtags == null) {
			return "blog/index/noblog";
		}
		model.addAttribute("blogtags", blogtags);
		return "blog/index/tagslist";
	}

	/**
	 * 查询标签文章内容列表
	 */
	@PostMapping("/tagsList")
	@ResponseBody
	public TableDataInfo tagsList(Blogcontent blogcontent) {
		// 不要把文章内容发送过去
		startPage();
		// 已发布
		blogcontent.setStatus(1);
		List<Blogcontent> list = blogwebsiteService.selectBlogTagscontentListWithoutContent(blogcontent);
		return getDataTable(list);
	}

	/**
	 * 浏览点赞加一
	 */
	@PostMapping("/blogcontent/addBrowseAndKikeNum")
	@ResponseBody
	public Message addBrowseAndKikeNum(Long cid, Integer flag) {
		if (blogwebsiteService.addBrowseAndKikeNum(cid, flag) > 0) {
			return Message.success();
		}
		return Message.error();
	}

	/**
	 * 到时间轴
	 */
	@GetMapping("/timeline")
	public String timelineList(Model model) {
		// 把设置数据发送到前端
		model.addAttribute("blogset", blogsetService.getBlogsetMsg());
		return "blog/index/timeline";
	}

	/**
	 * 搜索
	 */
	@GetMapping("/search")
	public String search(String keyword, Model model) {
		// 校验参数
		if (!CheckStringUtils.checkStringByRegularExpression(keyword,
				RegularExpressionConstant.REGULAR_EXPRESSION_ONLY_CNENNUM)) {
			return "blog/index/noblog";
		}

		// 把设置数据发送到前端
		model.addAttribute("blogset", blogsetService.getBlogsetMsg());

		model.addAttribute("keyword", keyword);
		return "blog/index/searchlist";
	}

	/**
	 * 查询搜索内容列表
	 */
	@PostMapping("/searchList")
	@ResponseBody
	public TableDataInfo searchList(Blogcontent blogcontent) {
		// 不要把文章内容发送过去
		startPage();
		// 已发布
		blogcontent.setStatus(1);
		List<Blogcontent> list = blogwebsiteService.selectBlogSearchcontentListWithoutContent(blogcontent);
		return getDataTable(list);
	}

	/**
	 * 请求公告信息
	 */
	@PostMapping("/blogNoticeList")
	@ResponseBody
	public List<Blognotice> blogNoticeList() {
		// 公告信息
		List<Blognotice> blognoticeList = blognoticeService.selectOpenBlognoticeList();
		return blognoticeList;
	}

	/**
	 * 浏览器详情
	 */
	@PostMapping("/blogbrowe/saveBlogbrowe")
	@ResponseBody
	public Message saveBlogbrowe(Blogbrowe blogbrowe) {
		if (blogbroweService.saveBlogbrowe(blogbrowe) > 0) {
			return Message.success();
		}
		return Message.error();
	}

	/**
	 * 添加评论
	 */
	@PostMapping("/blogcomment/addBlogcomment")
	@ResponseBody
	public Message addBlogcomment(Blogcomment blogcomment) {
		int[] msg = blogcommentService.addBlogcomment(blogcomment);
		if (msg[0] >= 0) {
			Message message = new Message();
			message = message.success();
			message.put(ReturnMessageConstant.RETURN_MESSAGE_KEY_5, String.valueOf(msg[0]));
			message.put(ReturnMessageConstant.RETURN_MESSAGE_KEY_8, String.valueOf(msg[1]));
			return message;
		}
		return Message.error();
	}

	/**
	 * 评论点赞加一
	 */
	@PostMapping("/blogcomment/addCommentLike")
	@ResponseBody
	public Message addCommentLike(Integer blogCommentId) {
		if (blogcommentService.addCommentLike(blogCommentId) > 0) {
			return Message.success();
		}
		return Message.error();
	}
}
