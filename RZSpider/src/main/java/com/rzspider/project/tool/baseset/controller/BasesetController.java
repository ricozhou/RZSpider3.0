package com.rzspider.project.tool.baseset.controller;

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

import com.rzspider.project.commontool.toolmanage.domain.Toolmanage;
import com.rzspider.project.tool.baseset.domain.Baseset;
import com.rzspider.project.tool.baseset.service.IBasesetService;
import com.rzspider.common.constant.CommonConstant;
import com.rzspider.common.constant.project.ToolConstant;
import com.rzspider.framework.aspectj.lang.annotation.Log;
import com.rzspider.framework.web.controller.BaseController;
import com.rzspider.framework.web.page.TableDataInfo;
import com.rzspider.framework.web.domain.Message;

/**
 * 基础设置详情 信息操作处理
 * 
 * @author ricozhou
 * @date 2018-06-23
 */
@Controller
@RequestMapping("/tool/baseset")
public class BasesetController extends BaseController {
	private String prefix = "tool/baseset";

	@Autowired
	private IBasesetService basesetService;

	@GetMapping()
	@RequiresPermissions("tool:baseset:view")
	public String baseset() {
		return prefix + "/baseset";
	}

	/**
	 * 查询基础设置详情列表
	 */
	@RequiresPermissions("tool:baseset:list")
	@GetMapping("/list")
	@ResponseBody
	public TableDataInfo list(Baseset baseset) {
		startPage();
		List<Baseset> list = basesetService.selectBasesetList(baseset);
		return getDataTable(list);
	}

	/**
	 * 新增基础设置详情
	 */
	@RequiresPermissions("tool:baseset:add")
	@GetMapping("/add")
	public String add() {
		return prefix + "/add";
	}

	/**
	 * 清楚缓存基础设置详情
	 */
	@RequiresPermissions("tool:baseset:clear")
	@GetMapping("/clear")
	public String clear() {
		return prefix + "/clear";
	}

	/**
	 * 修改基础设置详情
	 */
	@RequiresPermissions("tool:baseset:edit")
	@GetMapping("/edit/{basesetId}")
	public String edit(@PathVariable("basesetId") Integer basesetId, Model model) {
		Baseset baseset = basesetService.selectBasesetById(basesetId);
		model.addAttribute("baseset", baseset);
		return prefix + "/edit";
	}

	/**
	 * 保存基础设置详情
	 */
	@Log(title = "基础设置", action = "基础设置-新增主题")
	@RequiresPermissions("tool:baseset:save")
	@PostMapping("/save")
	@ResponseBody
	public Message save(Baseset baseset) {
		if (basesetService.saveBaseset(baseset) > 0) {
			return Message.success();
		}
		return Message.error();
	}

	/**
	 * 清除缓存基础设置详情
	 */
	@Log(title = "基础设置", action = "基础设置-清除缓存")
	@PostMapping("/clearCache")
	@ResponseBody
	public Message clearCache(Integer flag) {
		if (flag == 0) {
			// 清除文件
			return basesetService.clearCacheFile();
		} else if (flag == 1) {
			// 清除数据
			return basesetService.clearCacheDB();
		}
		return Message.error(ToolConstant.TOOL_BASESET_UNKNOWN_ERROR);
	}

	/**
	 * 删除基础设置详情
	 */
	@Log(title = "基础设置", action = "基础设置-删除主题")
	@Transactional(rollbackFor = Exception.class)
	@RequiresPermissions("tool:baseset:remove")
	@PostMapping("/remove/{basesetId}")
	@ResponseBody
	public Message remove(@PathVariable("basesetId") Integer basesetId) {
		Baseset baseset = basesetService.selectBasesetById(basesetId);
		if (baseset.getUseStatus() == 0) {
			return Message.error(ToolConstant.TOOL_BASESET_MESSAGE_USING_NO_DELETE);
		}

		if (basesetService.deleteBasesetById(basesetId) > 0) {
			return Message.success();
		}
		return Message.error(ToolConstant.TOOL_BASESET_MESSAGE_DELETE_FAILED);
	}

	/**
	 * 批量删除基础设置详情
	 */
	@Log(title = "基础设置", action = "基础设置-批量删除主题")
	@Transactional(rollbackFor = Exception.class)
	@RequiresPermissions("tool:baseset:batchRemove")
	@PostMapping("/batchRemove")
	@ResponseBody
	public Message remove(@RequestParam("ids[]") Integer[] basesetIds) {
		Baseset baseset;
		for (Integer basesetId : basesetIds) {
			baseset = basesetService.selectBasesetById(basesetId);
			if (baseset.getUseStatus() == 0) {
				return Message.error(ToolConstant.TOOL_BASESET_MESSAGE_USING_NO_DELETE);
			}

		}

		int rows = basesetService.batchDeleteBaseset(basesetIds);
		if (rows > 0) {
			return Message.success();
		}
		return Message.error(ToolConstant.TOOL_BASESET_MESSAGE_DELETE_FAILED);
	}

	/**
	 * 启用基础设置详情
	 */
	@Log(title = "基础设置", action = "基础设置-启动主题")
	@RequiresPermissions("tool:baseset:startUse")
	@PostMapping("/startUse/{basesetId}")
	@Transactional(rollbackFor = Exception.class)
	@ResponseBody
	public Message startUse(@PathVariable("basesetId") Integer basesetId) {
		Baseset baseset = basesetService.selectBasesetById(basesetId);
		if (baseset.getStatus() == 1) {
			return Message.error(ToolConstant.TOOL_BASESET_MESSAGE_STOP_USE);
		}
		if (basesetService.startUse(basesetId) > 0) {
			return Message.success(ToolConstant.TOOL_BASESET_MESSAGE_USE_SUCCESS);
		}
		return Message.error(ToolConstant.TOOL_BASESET_MESSAGE_USE_FAILED);
	}

	/**
	 * 校验主题名称
	 */
	@PostMapping("/checkThemeNameUnique")
	@ResponseBody
	public String checkThemeNameUnique(Baseset baseset) {
		String uniqueFlag = CommonConstant.UNIQUE;
		if (baseset != null) {
			uniqueFlag = basesetService.checkThemeNameUnique(baseset);
		}
		return uniqueFlag;
	}

	/**
	 * 检查爬虫是否存在
	 */
	@Log(title = "基础设置", action = "基础设置-校验主题存在")
	@PostMapping("/checkBasesetExist")
	@ResponseBody
	public Message checkBasesetExist(Integer basesetId) {
		Baseset baseset = basesetService.selectBasesetById(basesetId);
		if (baseset == null) {
			return Message.error(ToolConstant.TOOL_BASESET_MESSAGE_NO_EXIST);
		}
		return Message.success();
	}
}
