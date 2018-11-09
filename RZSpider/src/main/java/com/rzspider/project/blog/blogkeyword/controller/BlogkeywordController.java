package com.rzspider.project.blog.blogkeyword.controller;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.rzspider.project.blog.blogkeyword.domain.Blogkeyword;
import com.rzspider.project.blog.blogkeyword.service.IBlogkeywordService;
import com.rzspider.project.blog.blogtags.domain.Blogtags;
import com.rzspider.project.system.role.service.IRoleService;
import com.rzspider.common.constant.CommonConstant;
import com.rzspider.common.constant.UserConstants;
import com.rzspider.common.utils.OtherUtils;
import com.rzspider.common.utils.security.ShiroUtils;
import com.rzspider.framework.aspectj.lang.annotation.Log;
import com.rzspider.framework.web.controller.BaseController;
import com.rzspider.framework.web.page.TableDataInfo;
import com.rzspider.framework.web.domain.Message;

/**
 * 关键词管理 信息操作处理
 * 
 * @author ricozhou
 * @date 2018-11-08
 */
@Controller
@RequestMapping("/blog/blogkeyword")
public class BlogkeywordController extends BaseController {
	private String prefix = "blog/blogkeyword";

	@Autowired
	private IBlogkeywordService blogkeywordService;
	@Autowired
	private IRoleService roleService;

	@GetMapping()
	@RequiresPermissions("blog:blogkeyword:view")
	public String blogkeyword() {
		return prefix + "/blogkeyword";
	}

	/**
	 * 查询关键词管理列表
	 */
	@RequiresPermissions("blog:blogkeyword:list")
	@GetMapping("/list")
	@ResponseBody
	public TableDataInfo list(Blogkeyword blogkeyword) {
		startPage();
		List<Blogkeyword> list = blogkeywordService.selectBlogkeywordList(blogkeyword);
		return getDataTable(list);
	}

	/**
	 * 新增关键词管理
	 */
	@RequiresPermissions("blog:blogkeyword:add")
	@GetMapping("/add")
	public String add() {
		return prefix + "/add";
	}

	/**
	 * 修改关键词管理
	 */
	@RequiresPermissions("blog:blogkeyword:edit")
	@GetMapping("/edit/{blogKeywordId}")
	public String edit(@PathVariable("blogKeywordId") Integer blogKeywordId, Model model) {
		Blogkeyword blogkeyword = blogkeywordService.selectBlogkeywordById(blogKeywordId);
		model.addAttribute("blogkeyword", blogkeyword);
		return prefix + "/edit";
	}

	/**
	 * 保存关键词管理
	 */
	@RequiresPermissions("blog:blogkeyword:save")
	@PostMapping("/save")
	@ResponseBody
	public Message save(Blogkeyword blogkeyword) {
		// 测试管理员禁用
		if (OtherUtils.isTestManager(roleService.selectRoleKeys(ShiroUtils.getUserId()))) {
			return Message.error(UserConstants.USER_MESSAGE_TEATADMIN_DISABLED_USE);
		}
		if (blogkeywordService.saveBlogkeyword(blogkeyword) > 0) {
			return Message.success();
		}
		return Message.error();
	}

	/**
	 * 删除关键词管理
	 */
	@RequiresPermissions("blog:blogkeyword:remove")
	@PostMapping("/remove/{blogKeywordId}")
	@ResponseBody
	public Message remove(@PathVariable("blogKeywordId") Integer blogKeywordId) {
		// 测试管理员禁用
		if (OtherUtils.isTestManager(roleService.selectRoleKeys(ShiroUtils.getUserId()))) {
			return Message.error(UserConstants.USER_MESSAGE_TEATADMIN_DISABLED_USE);
		}
		if (blogkeywordService.deleteBlogkeywordById(blogKeywordId) > 0) {
			return Message.success();
		}
		return Message.error();
	}

	/**
	 * 批量删除关键词管理
	 */
	@RequiresPermissions("blog:blogkeyword:batchRemove")
	@PostMapping("/batchRemove")
	@ResponseBody
	public Message remove(@RequestParam("ids[]") Integer[] blogKeywordIds) {
		// 测试管理员禁用
		if (OtherUtils.isTestManager(roleService.selectRoleKeys(ShiroUtils.getUserId()))) {
			return Message.error(UserConstants.USER_MESSAGE_TEATADMIN_DISABLED_USE);
		}
		int rows = blogkeywordService.batchDeleteBlogkeyword(blogKeywordIds);
		if (rows > 0) {
			return Message.success();
		}
		return Message.error();
	}

	/**
	 * 校验名称
	 */
	@PostMapping("/checkKeywordNameUnique")
	@ResponseBody
	public String checkKeywordNameUnique(Blogkeyword blogkeyword) {
		String uniqueFlag = CommonConstant.UNIQUE;
		if (blogkeyword != null) {
			uniqueFlag = blogkeywordService.checkKeywordNameUnique(blogkeyword);
		}
		return uniqueFlag;
	}
}
