package com.rzspider.project.blog.blognotice.controller;

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
import com.rzspider.project.blog.blognotice.domain.Blognotice;
import com.rzspider.project.blog.blognotice.service.IBlognoticeService;
import com.rzspider.project.system.role.service.IRoleService;
import com.rzspider.common.constant.UserConstants;
import com.rzspider.common.utils.OtherUtils;
import com.rzspider.common.utils.security.ShiroUtils;
import com.rzspider.framework.web.controller.BaseController;
import com.rzspider.framework.web.page.TableDataInfo;
import com.rzspider.framework.web.domain.Message;

/**
 * 博客公告 信息操作处理
 * 
 * @author ricozhou
 * @date 2018-10-23
 */
@Controller
@RequestMapping("/blog/blognotice")
public class BlognoticeController extends BaseController {
	private String prefix = "blog/blognotice";

	@Autowired
	private IBlognoticeService blognoticeService;
	@Autowired
	private IRoleService roleService;

	@GetMapping()
	@RequiresPermissions("blog:blognotice:view")
	public String blognotice() {
		return prefix + "/blognotice";
	}

	/**
	 * 查询博客公告列表
	 */
	@RequiresPermissions("blog:blognotice:list")
	@GetMapping("/list")
	@ResponseBody
	public TableDataInfo list(Blognotice blognotice) {
		startPage();
		List<Blognotice> list = blognoticeService.selectBlognoticeList(blognotice);
		return getDataTable(list);
	}

	/**
	 * 新增博客公告
	 */
	@RequiresPermissions("blog:blognotice:add")
	@GetMapping("/add")
	public String add() {
		return prefix + "/add";
	}

	/**
	 * 修改博客公告
	 */
	@RequiresPermissions("blog:blognotice:edit")
	@GetMapping("/edit/{blogNoticeId}")
	public String edit(@PathVariable("blogNoticeId") Integer blogNoticeId, Model model) {
		Blognotice blognotice = blognoticeService.selectBlognoticeById(blogNoticeId);
		model.addAttribute("blognotice", blognotice);
		return prefix + "/edit";
	}

	/**
	 * 保存博客公告
	 */
	@RequiresPermissions("blog:blognotice:save")
	@PostMapping("/save")
	@ResponseBody
	public Message save(Blognotice blognotice) {
		// 测试管理员禁用
		if (OtherUtils.isTestManager(roleService.selectRoleKeys(ShiroUtils.getUserId()))) {
			return Message.error(UserConstants.USER_MESSAGE_TEATADMIN_DISABLED_USE);
		}
		if (blognoticeService.saveBlognotice(blognotice) > 0) {
			return Message.success();
		}
		return Message.error();
	}

	/**
	 * 删除博客公告
	 */
	@RequiresPermissions("blog:blognotice:remove")
	@PostMapping("/remove/{blogNoticeId}")
	@ResponseBody
	public Message remove(@PathVariable("blogNoticeId") Integer blogNoticeId) {
		// 测试管理员禁用
		if (OtherUtils.isTestManager(roleService.selectRoleKeys(ShiroUtils.getUserId()))) {
			return Message.error(UserConstants.USER_MESSAGE_TEATADMIN_DISABLED_USE);
		}
		if (blognoticeService.deleteBlognoticeById(blogNoticeId) > 0) {
			return Message.success();
		}
		return Message.error();
	}

	/**
	 * 批量删除博客公告
	 */
	@RequiresPermissions("blog:blognotice:batchRemove")
	@PostMapping("/batchRemove")
	@ResponseBody
	public Message remove(@RequestParam("ids[]") Integer[] blogNoticeIds) {
		// 测试管理员禁用
		if (OtherUtils.isTestManager(roleService.selectRoleKeys(ShiroUtils.getUserId()))) {
			return Message.error(UserConstants.USER_MESSAGE_TEATADMIN_DISABLED_USE);
		}
		int rows = blognoticeService.batchDeleteBlognotice(blogNoticeIds);
		if (rows > 0) {
			return Message.success();
		}
		return Message.error();
	}

}
