package com.rzspider.project.blog.blogoverview.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.rzspider.framework.web.controller.BaseController;
import com.rzspider.project.blog.blogoverview.domain.Blogoverview;
import com.rzspider.project.blog.blogoverview.service.IBlogoverviewService;
import com.rzspider.project.blog.blogset.domain.Blogset;
import com.rzspider.project.blog.blogset.service.IBlogsetService;

/**
 * 博客网站 信息操作处理
 * 
 * @author ricozhou
 * @date 2018-06-12
 */
@Controller
@RequestMapping("/blog/blogoverview")
public class BlogoverviewController extends BaseController {
	private String prefix = "blog/blogoverview";
	@Autowired
	private IBlogoverviewService blogoverviewService;

	@GetMapping()
	@RequiresPermissions("blog:blogoverview:view")
	public String blogoverview(Blogoverview blogoverview,Model model) {

		model.addAttribute("blogoverview", blogoverviewService.selectBlogoverview(blogoverview));
		return prefix + "/blogoverview";
	}
}
