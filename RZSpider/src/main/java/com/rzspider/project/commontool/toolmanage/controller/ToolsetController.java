package com.rzspider.project.commontool.toolmanage.controller;

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
import com.rzspider.framework.web.controller.BaseController;
import com.rzspider.framework.web.page.TableDataInfo;
import com.rzspider.project.commontool.toolmanage.domain.Toolset;
import com.rzspider.project.commontool.toolmanage.service.IToolsetService;
import com.rzspider.framework.web.domain.Message;

/**
 * 通用工具管理工具设置 信息操作处理
 * 
 * @author ricozhou
 * @date 2018-08-27
 */
@Controller
@RequestMapping("/commontool/toolmanage/toolset")
public class ToolsetController extends BaseController {
	private String prefix = "commontool/toolmanage/toolset";

	@Autowired
	private IToolsetService toolsetService;

	@GetMapping("/ocrSet")
	public String ocrSet(Model model) {
		// 默认只有一条数据
		Integer id = 1;
		Toolset toolset = toolsetService.selectToolsetById(id);
		model.addAttribute("toolset", toolset);
		return prefix + "/ocrSet";
	}

	/**
	 * 保存通用工具管理工具设置
	 */
	@PostMapping("/ocrSave")
	@ResponseBody
	public Message ocrSave(Toolset toolset) {
		if (toolsetService.ocrSaveToolset(toolset) > 0) {
			return Message.success();
		}
		return Message.error();
	}

}
