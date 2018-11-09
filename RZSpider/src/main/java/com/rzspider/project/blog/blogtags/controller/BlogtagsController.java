package com.rzspider.project.blog.blogtags.controller;

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

import com.rzspider.project.blog.blogcolumn.domain.Blogcolumn;
import com.rzspider.project.blog.blogtags.domain.Blogtags;
import com.rzspider.project.blog.blogtags.service.IBlogtagsService;
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
 * 博客标签 信息操作处理
 * 
 * @author ricozhou
 * @date 2018-09-20
 */
@Controller
@RequestMapping("/blog/blogtags")
public class BlogtagsController extends BaseController {
	private String prefix = "blog/blogtags";

	@Autowired
	private IBlogtagsService blogtagsService;
	@Autowired
	private IRoleService roleService;

	@GetMapping()
	@RequiresPermissions("blog:blogtags:view")
	public String blogtags() {
		return prefix + "/blogtags";
	}

	/**
	 * 查询博客标签列表
	 */
	@RequiresPermissions("blog:blogtags:list")
	@GetMapping("/list")
	@ResponseBody
	public TableDataInfo list(Blogtags blogtags) {
		startPage();
		List<Blogtags> list = blogtagsService.selectBlogtagsList(blogtags);
		return getDataTable(list);
	}

	/**
	 * 新增博客标签
	 */
	@RequiresPermissions("blog:blogtags:add")
	@GetMapping("/add")
	public String add() {
		return prefix + "/add";
	}

	/**
	 * 修改博客标签
	 */
	@RequiresPermissions("blog:blogtags:edit")
	@GetMapping("/edit/{blogTagsId}")
	public String edit(@PathVariable("blogTagsId") Integer blogTagsId, Model model) {
		Blogtags blogtags = blogtagsService.selectBlogtagsById(blogTagsId);
		model.addAttribute("blogtags", blogtags);
		return prefix + "/edit";
	}

	/**
	 * 保存博客标签
	 */
	@RequiresPermissions("blog:blogtags:save")
	@PostMapping("/save")
	@ResponseBody
	public Message save(Blogtags blogtags) {
		// 测试管理员禁用
		if (OtherUtils.isTestManager(roleService.selectRoleKeys(ShiroUtils.getUserId()))) {
			return Message.error(UserConstants.USER_MESSAGE_TEATADMIN_DISABLED_USE);
		}
		if (blogtagsService.saveBlogtags(blogtags) > 0) {
			return Message.success();
		}
		return Message.error();
	}

	/**
	 * 删除博客标签
	 */
	@RequiresPermissions("blog:blogtags:remove")
	@PostMapping("/remove/{blogTagsId}")
	@ResponseBody
	public Message remove(@PathVariable("blogTagsId") Integer blogTagsId) {
		// 测试管理员禁用
		if (OtherUtils.isTestManager(roleService.selectRoleKeys(ShiroUtils.getUserId()))) {
			return Message.error(UserConstants.USER_MESSAGE_TEATADMIN_DISABLED_USE);
		}
		if (blogtagsService.deleteBlogtagsById(blogTagsId) > 0) {
			return Message.success();
		}
		return Message.error();
	}

	/**
	 * 批量删除博客标签
	 */
	@RequiresPermissions("blog:blogtags:batchRemove")
	@PostMapping("/batchRemove")
	@ResponseBody
	public Message remove(@RequestParam("ids[]") Integer[] blogTagsIds) {
		// 测试管理员禁用
		if (OtherUtils.isTestManager(roleService.selectRoleKeys(ShiroUtils.getUserId()))) {
			return Message.error(UserConstants.USER_MESSAGE_TEATADMIN_DISABLED_USE);
		}
		int rows = blogtagsService.batchDeleteBlogtags(blogTagsIds);
		if (rows > 0) {
			return Message.success();
		}
		return Message.error();
	}

	/**
	 * 校验名称
	 */
	@Log(title = "博客管理", action = "标签管理-校验标签名称")
	@PostMapping("/checkTagNameUnique")
	@ResponseBody
	public String checkTagNameUnique(Blogtags blogtags) {
		String uniqueFlag = CommonConstant.UNIQUE;
		if (blogtags != null) {
			uniqueFlag = blogtagsService.checkTagNameUnique(blogtags);
		}
		return uniqueFlag;
	}

}
