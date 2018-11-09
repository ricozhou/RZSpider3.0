package com.rzspider.project.system.website.controller;

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
import com.rzspider.project.system.website.domain.Website;
import com.rzspider.project.system.website.service.IWebsiteService;
import com.rzspider.common.constant.project.SystemConstant;
import com.rzspider.framework.web.controller.BaseController;
import com.rzspider.framework.web.page.TableDataInfo;
import com.rzspider.framework.web.domain.Message;

/**
 * 网站设置 信息操作处理
 * 
 * @author ricozhou
 * @date 2018-09-10
 */
@Controller
@RequestMapping("/system/website")
public class WebsiteController extends BaseController {
	private String prefix = "system/website";

	@Autowired
	private IWebsiteService websiteService;

	@GetMapping()
	@RequiresPermissions("system:website:view")
	public String website(Model model) {
		List<Website> list = websiteService.selectWebsiteList(null);
		if (list != null && list.size() > 0) {
			model.addAttribute("website", list.get(0));
		}

		return prefix + "/website";
	}

	/**
	 * 保存网站设置
	 */
	@RequiresPermissions("system:website:save")
	@PostMapping("/websitesave")
	@ResponseBody
	public Message websitesave(Website website) {
		if (websiteService.saveWebsite(website) > 0) {
			return Message.success(SystemConstant.SYSTEM_MESSAGE_WEBSTIE_SAVE_SUCCESS);
		}
		return Message.error(SystemConstant.SYSTEM_MESSAGE_WEBSTIE_SAVE_FAILED);
	}

	/**
	 * 保存网站设置邮件设置
	 */
	@RequiresPermissions("system:website:save")
	@PostMapping("/mailsave")
	@ResponseBody
	public Message mailsave(Website website) {
		if (websiteService.saveMail(website) > 0) {
			return Message.success(SystemConstant.SYSTEM_MESSAGE_MAIL_SAVE_SUCCESS);
		}
		return Message.error(SystemConstant.SYSTEM_MESSAGE_MAIL_SAVE_FAILED);
	}

}
