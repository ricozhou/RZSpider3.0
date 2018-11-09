package com.rzspider.project.system.user.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.rzspider.framework.config.RZSpiderConfig;
import com.rzspider.framework.web.controller.BaseController;
import com.rzspider.project.system.menu.domain.Menu;
import com.rzspider.project.system.menu.service.IMenuService;
import com.rzspider.project.system.user.domain.User;
import com.rzspider.project.system.website.domain.Website;
import com.rzspider.project.system.website.service.IWebsiteService;
import com.rzspider.project.tool.baseset.service.IBasesetService;

/**
 * 首页 业务处理
 * 
 * @author ricozhou
 */
@Controller
public class IndexController extends BaseController {
	@Autowired
	private IMenuService menuService;

	@Autowired
	private RZSpiderConfig rZSpiderConfig;
	@Autowired
	private IBasesetService basesetService;
	@Autowired
	private IWebsiteService websiteService;

	// 系统首页
	@GetMapping("/index")
	public String index(Model model) {
		// 取身份信息
		User user = getUser();
		// 根据用户id取出菜单
		List<Menu> menus = menuService.selectMenusByUserId(user.getUserId());
		model.addAttribute("menus", menus);
		model.addAttribute("user", user);
		model.addAttribute("copyrightYear", rZSpiderConfig.getCopyrightYear());
		model.addAttribute("baseset", basesetService.getBaseset());

		Website website = websiteService.getWebsiteSetMsg();
		model.addAttribute("websiteTitleName", website.getWebsiteTitleName());
		model.addAttribute("websiteIco", website.getWebsiteIco());
		model.addAttribute("websiteCopyrightInformation", website.getWebsiteCopyrightInformation());
		return "index";
	}

	// 系统介绍
	@GetMapping("/system/main")
	public String main(Model model) {
		model.addAttribute("version", rZSpiderConfig.getVersion());
		model.addAttribute("homeIntroduction", basesetService.getHomeIntroduction());
		model.addAttribute("websiteTitleName", websiteService.getSomeWebsiteSetMsg().getWebsiteTitleName());
		return "main";
	}

}
