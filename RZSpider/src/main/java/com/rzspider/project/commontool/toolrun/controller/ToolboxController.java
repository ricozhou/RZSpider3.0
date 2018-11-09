package com.rzspider.project.commontool.toolrun.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rzspider.framework.aspectj.lang.annotation.Log;
import com.rzspider.project.commontool.toolmanage.domain.Toolmanage;
import com.rzspider.project.commontool.toolmanage.service.IToolmanageService;

//工具箱公共控制

/**
 * 通用工具运行 信息操作处理
 * 
 * @author ricozhou
 * @date 2018-07-23
 */
@Controller
@RequestMapping("/toolbox")
public class ToolboxController {
	private String prefix = "commontool/toolrun";
	@Autowired
	private IToolmanageService toolmanageService;
	// 到主界面
	@GetMapping()
	public String toolbox() {
		return prefix + "/toolbox";
	}

	/**
	 * 跳转具体页面
	 */
	@GetMapping("/{toolBackId}")
	public String preview(@PathVariable("toolBackId") Integer toolBackId, Model model) {
		model.addAttribute("toolBackId", toolBackId);
		return "commontool/toolrun/" + toolBackId;
	}
	//获取可用工具
	@PostMapping("/canUseToolList")
	@ResponseBody
	public List<Toolmanage> canUseToolList(){
		//查询可用
		List<Toolmanage> list = toolmanageService.selectToolmanageListByStatusAndToolType(0,0);
		return list;
	}
}
