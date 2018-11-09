package com.rzspider.project.blog.blogbrowe.controller;

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
import com.rzspider.project.blog.blogbrowe.domain.Blogbrowe;
import com.rzspider.project.blog.blogbrowe.service.IBlogbroweService;
import com.rzspider.project.system.role.service.IRoleService;
import com.rzspider.common.constant.UserConstants;
import com.rzspider.common.utils.OtherUtils;
import com.rzspider.common.utils.security.ShiroUtils;
import com.rzspider.framework.web.controller.BaseController;
import com.rzspider.framework.web.page.TableDataInfo;
import com.rzspider.framework.web.domain.Message;

/**
 * 博客浏览日志 信息操作处理
 * 
 * @author ricozhou
 * @date 2018-10-25
 */
@Controller
@RequestMapping("/blog/blogbrowe")
public class BlogbroweController extends BaseController {
	private String prefix = "blog/blogbrowe";

	@Autowired
	private IBlogbroweService blogbroweService;
	@Autowired
	private IRoleService roleService;

	@GetMapping()
	@RequiresPermissions("blog:blogbrowe:view")
	public String blogbrowe() {
		return prefix + "/blogbrowe";
	}

	/**
	 * 查询博客浏览日志列表
	 */
	@RequiresPermissions("blog:blogbrowe:list")
	@GetMapping("/list")
	@ResponseBody
	public TableDataInfo list(Blogbrowe blogbrowe) {
		startPage();
		List<Blogbrowe> list = blogbroweService.selectBlogbroweList(blogbrowe);
		return getDataTable(list);
	}

	// /**
	// * 新增博客浏览日志
	// */
	// @RequiresPermissions("blog:blogbrowe:add")
	// @GetMapping("/add")
	// public String add() {
	// return prefix + "/add";
	// }

	/**
	 * 查看博客浏览日志
	 */
	@GetMapping("/details/{blogBroweId}")
	public String details(@PathVariable("blogBroweId") Integer blogBroweId, Model model) {
		Blogbrowe blogbrowe = blogbroweService.selectBlogbroweById(blogBroweId);
		model.addAttribute("blogbrowe", blogbrowe);
		return prefix + "/details";
	}

	// /**
	// * 保存博客浏览日志
	// */
	// @RequiresPermissions("blog:blogbrowe:save")
	// @PostMapping("/save")
	// @ResponseBody
	// public Message save(Blogbrowe blogbrowe) {
	// if (blogbroweService.saveBlogbrowe(blogbrowe) > 0) {
	// return Message.success();
	// }
	// return Message.error();
	// }

	/**
	 * 删除博客浏览日志
	 */
	@RequiresPermissions("blog:blogbrowe:remove")
	@PostMapping("/remove/{blogBroweId}")
	@ResponseBody
	public Message remove(@PathVariable("blogBroweId") Integer blogBroweId) {
		// 测试管理员禁用
		if (OtherUtils.isTestManager(roleService.selectRoleKeys(ShiroUtils.getUserId()))) {
			return Message.error(UserConstants.USER_MESSAGE_TEATADMIN_DISABLED_USE);
		}
		if (blogbroweService.deleteBlogbroweById(blogBroweId) > 0) {
			return Message.success();
		}
		return Message.error();
	}

	/**
	 * 批量删除博客浏览日志
	 */
	@RequiresPermissions("blog:blogbrowe:batchRemove")
	@PostMapping("/batchRemove")
	@ResponseBody
	public Message remove(@RequestParam("ids[]") Integer[] blogBroweIds) {
		// 测试管理员禁用
		if (OtherUtils.isTestManager(roleService.selectRoleKeys(ShiroUtils.getUserId()))) {
			return Message.error(UserConstants.USER_MESSAGE_TEATADMIN_DISABLED_USE);
		}
		int rows = blogbroweService.batchDeleteBlogbrowe(blogBroweIds);
		if (rows > 0) {
			return Message.success();
		}
		return Message.error();
	}

}
