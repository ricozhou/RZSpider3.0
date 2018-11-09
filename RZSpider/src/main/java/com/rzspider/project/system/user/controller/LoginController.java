package com.rzspider.project.system.user.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.rzspider.common.utils.StringUtils;
import com.rzspider.framework.web.controller.BaseController;
import com.rzspider.framework.web.domain.Message;
import com.rzspider.project.system.website.domain.Website;
import com.rzspider.project.system.website.service.IWebsiteService;
import com.rzspider.project.tool.baseset.service.IBasesetService;

/**
 * 登录验证
 * 
 * @author ricozhou
 */
@Controller
public class LoginController extends BaseController {

	@Autowired
	private IBasesetService basesetService;
	@Autowired
	private IWebsiteService websiteService;

	@GetMapping("/login")
	public String login(Model model) {
		// 返回图片的base64编码字符串
		model.addAttribute("loginbgName", basesetService.getLoginbgName());
		model.addAttribute("loginbgType", basesetService.getLoginbgType());
		Website website = websiteService.getWebsiteSetMsg();
		model.addAttribute("websiteTitleName", website.getWebsiteTitleName());
		model.addAttribute("websiteIco", website.getWebsiteIco());
		model.addAttribute("websiteCopyrightInformation", website.getWebsiteCopyrightInformation());
		return "login";
	}

	@PostMapping("/login")
	@ResponseBody
	public Message ajaxLogin(String username, String password, Boolean rememberMe) {
		UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			return Message.success();
		} catch (AuthenticationException e) {
			String msg = "用户或密码错误";
			if (StringUtils.isNotEmpty(e.getMessage())) {
				msg = e.getMessage();
			}
			return Message.error(msg);
		}
	}

	@GetMapping("/unauth")
	public String unauth() {
		return "/error/unauth";
	}
}
