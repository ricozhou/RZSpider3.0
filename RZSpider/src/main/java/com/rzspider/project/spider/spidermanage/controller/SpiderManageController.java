package com.rzspider.project.spider.spidermanage.controller;

import java.util.List;
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

import com.rzspider.common.constant.CommonConstant;
import com.rzspider.common.constant.CommonSymbolicConstant;
import com.rzspider.common.constant.project.SpiderConstant;
import com.rzspider.common.constant.project.SpiderMessageConstant;
import com.rzspider.framework.aspectj.lang.annotation.Log;
import com.rzspider.framework.web.controller.BaseController;
import com.rzspider.framework.web.domain.Message;
import com.rzspider.framework.web.page.TableDataInfo;
import com.rzspider.project.spider.customspider.domain.Customspider;
import com.rzspider.project.spider.spidermanage.domain.SpiderList;
import com.rzspider.project.spider.spidermanage.domain.SpiderManage;
import com.rzspider.project.spider.spidermanage.service.ISpiderListService;
import com.rzspider.project.spider.spidermanage.service.ISpiderManageService;
import com.rzspider.project.spider.spidertask.domain.Spidertask;
import com.rzspider.project.system.role.domain.Role;
import com.rzspider.project.system.role.service.IRoleService;

/**
 * 爬虫信息
 * 
 * @author ricozhou
 */
@Controller
@RequestMapping("/spider/spidermanage")
public class SpiderManageController extends BaseController {

	private String prefix = "spider/spidermanage";

	@Autowired
	private ISpiderManageService spiderManageService;
	@Autowired
	private ISpiderListService spiderListService;

	// 视图
	@RequiresPermissions("spider:spidermanage:view")
	@GetMapping()
	public String spidermanage() {
		return prefix + "/spidermanage";
	}

	// 所有爬虫
	@RequiresPermissions("spider:spidermanage:list")
	@GetMapping("/list")
	@ResponseBody
	public TableDataInfo list(SpiderManage spiderManage) {
		startPage();
		List<SpiderManage> list = spiderManageService.selectSpiderManageList(spiderManage);
		return getDataTable(list);
	}

	// 爬虫名称和类型
	@Log(title = "网页爬虫", action = "爬虫管理-查询爬虫类型和名称")
	@RequiresPermissions("spider:spidertask:spiderTypeAndNameList")
	@GetMapping("/spiderTypeAndNameList")
	@ResponseBody
	public List<SpiderManage> spiderTypeAndNameList(SpiderManage spiderManage) {
		if (SpiderConstant.SPIDER_KEYWORD_SPIDERMANAGE_ALL.equals(spiderManage.getSpiderType())) {
			spiderManage.setSpiderType(CommonSymbolicConstant.EMPTY_STRING);
		}
		List<SpiderManage> list = spiderManageService.selectSpiderManageList2(spiderManage);
		return list;
	}

	// 爬虫后台ID
	@Log(title = "网页爬虫", action = "爬虫管理-查询爬虫后台ID")
	@GetMapping("/selectSpiderBackId")
	@ResponseBody
	public List<SpiderList> selectSpiderBackId() {
		List<SpiderList> list = spiderListService.selectSpiderListListByStatus(0);
		return list;
	}

	/**
	 * 新增爬虫
	 */
	@RequiresPermissions("spider:spidermanage:add")
	@Log(title = "网页爬虫", action = "爬虫管理-新增爬虫")
	@GetMapping("/add")
	public String add(Model model) {
		return prefix + "/add";
	}

	/**
	 * 校验爬虫名称
	 */
	@Log(title = "网页爬虫", action = "爬虫管理-校验爬虫名称")
	@PostMapping("/checkSpiderNameUnique")
	@ResponseBody
	public String checkSpiderNameUnique(SpiderManage spiderManage) {
		String uniqueFlag = CommonConstant.UNIQUE;
		if (spiderManage != null) {
			uniqueFlag = spiderManageService.checkSpiderNameUnique(spiderManage);
		}
		return uniqueFlag;
	}

	/**
	 * 校验爬虫后台ID
	 */
	@Log(title = "网页爬虫", action = "爬虫管理-校验后台ID")
	@PostMapping("/checkSpiderBackIdUnique")
	@ResponseBody
	public String checkSpiderBackIdUnique(SpiderManage spiderManage) {
		String uniqueFlag = CommonConstant.UNIQUE;
		if (spiderManage != null) {
			uniqueFlag = spiderManageService.checkSpiderBackIdUnique(spiderManage);
		}
		return uniqueFlag;
	}

	/**
	 * 爬虫参数详情
	 */
	@Log(title = "网页爬虫", action = "爬虫管理-查看参数")
	@GetMapping("/params/{spiderId}")
	public String params(@PathVariable("spiderId") Long spiderId, Model model) {
		SpiderManage spiderManage = spiderManageService.selectSpiderManageById(spiderId);
		model.addAttribute("spiderManage", spiderManage);
		// 页面以后台id显示，设置一个自定义的页面id
		// 六位数的999999表示自定义页面
		if (spiderManage.getCreateType() == 1) {
			// 自定义爬虫同一使用一个参数页面
			return prefix + "/params/999999";
		}
		return prefix + "/params/" + spiderManage.getSpiderBackId();
	}

	/**
	 * 保存爬虫参数详情
	 */
	@Log(title = "网页爬虫", action = "爬虫管理-保存参数")
	@PostMapping("/saveParams")
	@ResponseBody
	public Message saveParams(SpiderManage spiderManage) {
		if (spiderManageService.saveSpidermanageParams(spiderManage) > 0) {
			return Message.success();
		}
		return Message.error(SpiderMessageConstant.SPIDER_MESSAGE_SAVEPARAMS_FAILED);
	}

	/**
	 * 保存爬虫
	 */
	@RequiresPermissions("spider:spidermanage:save")
	@Log(title = "网页爬虫", action = "爬虫管理-保存爬虫")
	@PostMapping("/save")
	@Transactional(rollbackFor = Exception.class)
	@ResponseBody
	public Message save(SpiderManage spiderManage) {
		if (spiderManageService.saveSpider(spiderManage) > 0) {
			return Message.success();
		}
		return Message.error();
	}

	/**
	 * 修改爬虫
	 */
	@RequiresPermissions("spider:spidermanage:edit")
	@Log(title = "网页爬虫", action = "爬虫管理-修改爬虫")
	@GetMapping("/edit/{spiderId}")
	public String edit(@PathVariable("spiderId") Long spiderId, Model model) {
		SpiderManage spiderManage = spiderManageService.selectSpiderManageById(spiderId);
		model.addAttribute("spiderManage", spiderManage);
		return prefix + "/edit";
	}

	// 删除爬虫
	@RequiresPermissions("spider:spidermanage:remove")
	@Log(title = "网页爬虫", action = "爬虫管理-删除爬虫")
	@RequestMapping("/remove/{spiderId}")
	@Transactional(rollbackFor = Exception.class)
	@ResponseBody
	public Message remove(@PathVariable("spiderId") Long spiderId) {
		SpiderManage spiderManage = spiderManageService.selectSpiderManageById(spiderId);
		if (spiderManage == null) {
			return Message.error("爬虫不存在");
		}
		if (spiderManageService.deleteSpiderManageById(spiderId) > 0) {
			return Message.success();
		}
		return Message.error();
	}

	// 批量删除爬虫
	@RequiresPermissions("spider:spidermanage:batchRemove")
	@Log(title = "网页爬虫", action = "爬虫管理-批量删除爬虫")
	@PostMapping("/batchRemove")
	@ResponseBody
	public Message batchRemove(@RequestParam("ids[]") Long[] ids) {
		int rows = spiderManageService.batchDeleteSpiderManage(ids);
		if (rows > 0) {
			return Message.success();
		}
		return Message.error();
	}

	/**
	 * 检查爬虫是否存在
	 */
	@Log(title = "网页爬虫", action = "爬虫管理-校验爬虫存在")
	@PostMapping("/checkSpiderExist")
	@ResponseBody
	public Message checkSpiderExist(Long spiderId) {
		SpiderManage spiderManage = spiderManageService.checkSpiderExist(spiderId);
		if (spiderManage == null) {
			// 删除
			spiderManageService.deleteSpiderManageById(spiderId);
			return Message.error(SpiderMessageConstant.SPIDER_MESSAGE_SPIDER_NO_EXIST_CONTACT_ADMIN);
		} else if (spiderManage.getStatus() != 0) {
			return Message.error(SpiderMessageConstant.SPIDER_MESSAGE_SPIDER_DISABLED_CONTACT_ADMIN);
		}
		return Message.success();
	}

}