package com.rzspider.project.spider.customspider.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rzspider.project.spider.codeType.domain.CodeType;
import com.rzspider.project.spider.codeType.service.ICodeTypeService;
import com.rzspider.project.spider.customspider.domain.Customspider;
import com.rzspider.project.spider.customspider.service.ICustomspiderService;
import com.rzspider.project.spider.spidermanage.domain.SpiderManage;
import com.rzspider.project.spider.spidermanage.service.ISpiderListService;
import com.rzspider.project.spider.spidertask.utils.SpidertaskUtils;
import com.rzspider.common.constant.CommonConstant;
import com.rzspider.common.constant.project.SpiderMessageConstant;
import com.rzspider.framework.aspectj.lang.annotation.Log;
import com.rzspider.framework.config.FilePathConfig;
import com.rzspider.framework.web.controller.BaseController;
import com.rzspider.framework.web.page.TableDataInfo;
import com.rzspider.framework.web.domain.Message;

/**
 * 自定义爬虫详情 信息操作处理
 * 
 * @author ricozhou
 * @date 2018-06-01
 */
@Controller
@RequestMapping("/spider/customspider")
public class CustomspiderController extends BaseController {
	private String prefix = "spider/customspider";

	@Autowired
	private ICustomspiderService customspiderService;
	@Autowired
	private ISpiderListService spiderListService;
	@Autowired
	private ICodeTypeService codeTypeService;

	@GetMapping()
	@RequiresPermissions("spider:customspider:view")
	public String customspider() {
		return prefix + "/customspider";
	}

	/**
	 * 查询自定义爬虫详情列表
	 */
	@RequiresPermissions("spider:customspider:list")
	@GetMapping("/list")
	@ResponseBody
	public TableDataInfo list(Customspider customspider) {
		startPage();
		List<Customspider> list = customspiderService.selectCustomspiderList(customspider);
		return getDataTable(list);
	}

	/**
	 * 新增自定义爬虫详情
	 */
	@RequiresPermissions("spider:customspider:add")
	@GetMapping("/add")
	public String add() {
		return prefix + "/add";
	}

	/**
	 * 修改自定义爬虫详情
	 */
	@RequiresPermissions("spider:customspider:edit")
	@GetMapping("/edit/{customSpiderId}")
	public String edit(@PathVariable("customSpiderId") Integer customSpiderId, Model model) {
		Customspider customspider = customspiderService.selectCustomspiderById(customSpiderId);
		customspider.setSpiderDefaultParams(SpidertaskUtils.formatJson(
				customspider.getSpiderDefaultParams() != null ? customspider.getSpiderDefaultParams() : ""));
		model.addAttribute("customspider", customspider);
		return prefix + "/edit";
	}

	/**
	 * 编写自定义爬虫详情
	 */
	@RequiresPermissions("spider:customspider:code:view")
	@GetMapping("/code/{customSpiderId}")
	public String code(@PathVariable("customSpiderId") Integer customSpiderId, Model model) {
		Customspider customspider = customspiderService.selectCustomspiderById(customSpiderId);
		model.addAttribute("customspider", customspider);
		// 页面以自定义爬虫类型显示
		if (customspider.getCustomSpiderType() == 0) {
			// JAVA代码
			return prefix + "/code/javacs";
		} else if (customspider.getCustomSpiderType() == 1) {
			// python代码
			return prefix + "/code/pythoncs";
		} else if (customspider.getCustomSpiderType() == 2) {
			// js代码
			return prefix + "/code/javascriptcs";
		} else if (customspider.getCustomSpiderType() == 3) {
			// JAR包
			return prefix + "/code/jarcs";
		} else {
			// 其他代码
			return prefix + "/code/othercs";
		}
	}

	/**
	 * 保存自定义爬虫详情
	 */
	@Log(title = "网页爬虫", action = "自定义爬虫-爬虫保存")
	@RequiresPermissions("spider:customspider:save")
	@Transactional(rollbackFor = Exception.class)
	@PostMapping("/save")
	@ResponseBody
	public Message save(Customspider customspider) {
		// 检查爬虫代码类型是否存在或者禁用
		CodeType codeType = codeTypeService.selectSpiderCodeTypeByCustomSpiderType(customspider.getCustomSpiderType());
		if (codeType == null) {
			return Message.error(SpiderMessageConstant.SPIDER_MESSAGE_CODE_TYPE_NO_EXIST);
		}
		if (codeType.getStatus() != 0) {
			return Message.error(SpiderMessageConstant.SPIDER_MESSAGE_CODE_TYPE_DISABLED);
		}

		if (customspiderService.saveCustomspider(customspider, codeType) > 0) {
			return Message.success();
		}
		return Message.error();
	}

	/**
	 * 校验爬虫后台ID
	 */
	@PostMapping("/checkCustomSpiderBackIdUnique")
	@ResponseBody
	public String checkCustomSpiderBackIdUnique(Customspider customspider) {
		String uniqueFlag = CommonConstant.UNIQUE;
		if (customspider != null && customspider.getCustomSpiderBackId() != null) {
			uniqueFlag = spiderListService.checkSpiderBackIdUnique(customspider);
		}
		return uniqueFlag;
	}

	/**
	 * 删除自定义爬虫详情
	 */
	@Log(title = "网页爬虫", action = "自定义爬虫-爬虫删除")
	@RequiresPermissions("spider:customspider:remove")
	@PostMapping("/remove/{customSpiderId}")
	@ResponseBody
	public Message remove(@PathVariable("customSpiderId") Integer customSpiderId) {
		if (customspiderService.deleteCustomspiderById(customSpiderId) > 0) {
			return Message.success();
		}
		return Message.error();
	}

	/**
	 * 批量删除自定义爬虫详情
	 */
	@Log(title = "网页爬虫", action = "自定义爬虫-爬虫批量删除")
	@RequiresPermissions("spider:customspider:batchRemove")
	@PostMapping("/batchRemove")
	@ResponseBody
	public Message remove(@RequestParam("ids[]") Integer[] customSpiderIds) {
		int rows = customspiderService.batchDeleteCustomspider(customSpiderIds);
		if (rows > 0) {
			return Message.success();
		}
		return Message.error();
	}

	// 爬虫代码类型
	@GetMapping("/selectSpiderCodeType")
	@ResponseBody
	public List<CodeType> selectSpiderCodeType() {
		List<CodeType> list = codeTypeService.selectSpiderCodeTypeByStatus(0);
		return list;
	}

	/**
	 * 检查爬虫是否存在
	 */
	@Log(title = "网页爬虫", action = "自定义爬虫-校验爬虫存在")
	@PostMapping("/checkCSExist")
	@ResponseBody
	public Message checkCSExist(Customspider customspider) {
		if (customspiderService.selectCustomspiderByCustomSpiderBackId(customspider.getCustomSpiderBackId()) != null) {
			return Message.success();
		}
		return Message.error(SpiderMessageConstant.SPIDER_MESSAGE_SPIDER_NO_EXIST);
	}

	/**
	 * 检查爬虫项目文件是否存在
	 */
	@Log(title = "网页爬虫", action = "自定义爬虫-校验爬虫项目文件存在")
	@PostMapping("/checkCSFileExist")
	@ResponseBody
	public Message checkCSFileExist(Integer customSpiderId) {
		Customspider customspider = customspiderService.selectCustomspiderById(customSpiderId);
		String path = FilePathConfig.getCustomSpiderPath() + File.separator + customspider.getSpiderCodeTypeFolder()
				+ File.separator + customspider.getCustomSpiderBackId();
		File file = new File(path);
		if (!file.exists()) {
			return Message.error(SpiderMessageConstant.SPIDER_MESSAGE_SPIDER_PROJECTFILE_NO_EXIST);
		}
		return Message.success();
	}

}
