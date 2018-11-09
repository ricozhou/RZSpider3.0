package com.rzspider.project.spider.codeType.controller;

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
import com.rzspider.project.spider.codeType.domain.CodeType;
import com.rzspider.project.spider.codeType.service.ICodeTypeService;
import com.rzspider.project.spider.spidermanage.domain.SpiderManage;
import com.rzspider.common.constant.CommonConstant;
import com.rzspider.framework.aspectj.lang.annotation.Log;
import com.rzspider.framework.web.controller.BaseController;
import com.rzspider.framework.web.page.TableDataInfo;
import com.rzspider.framework.web.domain.Message;

/**
 * 爬虫代码类型详情 信息操作处理
 * 
 * @author ricozhou
 * @date 2018-06-08
 */
@Controller
@RequestMapping("/spider/codeType")
public class CodeTypeController extends BaseController {
	private String prefix = "spider/codeType";

	@Autowired
	private ICodeTypeService codeTypeService;

	@GetMapping()
	@RequiresPermissions("spider:codeType:view")
	public String codeType() {
		return prefix + "/codeType";
	}

	/**
	 * 查询爬虫代码类型详情列表
	 */
	@Log(title = "网页爬虫", action = "代码类型-代码类型查询")
	@RequiresPermissions("spider:codeType:list")
	@GetMapping("/list")
	@ResponseBody
	public TableDataInfo list(CodeType codeType) {
		startPage();
		List<CodeType> list = codeTypeService.selectCodeTypeList(codeType);
		return getDataTable(list);
	}

	/**
	 * 新增爬虫代码类型详情
	 */
	@RequiresPermissions("spider:codeType:add")
	@GetMapping("/add")
	public String add() {
		return prefix + "/add";
	}

	/**
	 * 修改爬虫代码类型详情
	 */
	@RequiresPermissions("spider:codeType:edit")
	@GetMapping("/edit/{spiderCodeTypeId}")
	public String edit(@PathVariable("spiderCodeTypeId") Integer spiderCodeTypeId, Model model) {
		CodeType codeType = codeTypeService.selectCodeTypeById(spiderCodeTypeId);
		model.addAttribute("codeType", codeType);
		return prefix + "/edit";
	}

	/**
	 * 保存爬虫代码类型详情
	 */
	@Log(title = "网页爬虫", action = "代码类型-代码类型保存")
	@RequiresPermissions("spider:codeType:save")
	@PostMapping("/save")
	@ResponseBody
	public Message save(CodeType codeType) {
		if (codeTypeService.saveCodeType(codeType) > 0) {
			return Message.success();
		}
		return Message.error();
	}

	/**
	 * 删除爬虫代码类型详情
	 */
	@Log(title = "网页爬虫", action = "代码类型-代码类型删除")
	@RequiresPermissions("spider:codeType:remove")
	@PostMapping("/remove/{spiderCodeTypeId}")
	@ResponseBody
	public Message remove(@PathVariable("spiderCodeTypeId") Integer spiderCodeTypeId) {
		if (codeTypeService.deleteCodeTypeById(spiderCodeTypeId) > 0) {
			return Message.success();
		}
		return Message.error();
	}

	/**
	 * 批量删除爬虫代码类型详情
	 */
	@Log(title = "网页爬虫", action = "代码类型-代码类型批量删除")
	@RequiresPermissions("spider:codeType:batchRemove")
	@PostMapping("/batchRemove")
	@ResponseBody
	public Message remove(@RequestParam("ids[]") Integer[] spiderCodeTypeIds) {
		int rows = codeTypeService.batchDeleteCodeType(spiderCodeTypeIds);
		if (rows > 0) {
			return Message.success();
		}
		return Message.error();
	}

	/**
	 * 校验爬虫代码标志ID
	 */
	@PostMapping("/checkCustomSpiderTypeUnique")
	@ResponseBody
	public String checkCustomSpiderTypeUnique(CodeType codeType) {
		String uniqueFlag = CommonConstant.UNIQUE;
		if (codeType != null) {
			uniqueFlag = codeTypeService.checkCustomSpiderTypeUnique(codeType);
		}
		return uniqueFlag;
	}

	/**
	 * 校验爬虫目录标志ID
	 */
	@PostMapping("/checkSpiderCodeTypeFolderUnique")
	@ResponseBody
	public String checkSpiderCodeTypeFolderUnique(CodeType codeType) {
		String uniqueFlag = CommonConstant.UNIQUE;
		if (codeType != null) {
			uniqueFlag = codeTypeService.checkSpiderCodeTypeFolderUnique(codeType);
		}
		return uniqueFlag;
	}
}
