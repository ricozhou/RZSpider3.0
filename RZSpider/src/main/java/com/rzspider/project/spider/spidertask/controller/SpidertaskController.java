package com.rzspider.project.spider.spidertask.controller;

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

import com.rzspider.project.monitor.job.domain.Job;
import com.rzspider.project.spider.customspider.domain.Customspider;
import com.rzspider.project.spider.spidermanage.domain.SpiderManage;
import com.rzspider.project.spider.spidermanage.service.ISpiderManageService;
import com.rzspider.project.spider.spidertask.domain.Spidertask;
import com.rzspider.project.spider.spidertask.domain.Spidertaskinfo;
import com.rzspider.project.spider.spidertask.service.ISpidertaskService;
import com.rzspider.project.spider.spidertask.service.ISpidertaskinfoService;
import com.rzspider.project.spider.spidertask.utils.SpidertaskUtils;
import com.rzspider.common.constant.CommonConstant;
import com.rzspider.common.constant.project.SpiderMessageConstant;
import com.rzspider.common.utils.security.ShiroUtils;
import com.rzspider.framework.aspectj.lang.annotation.Log;
import com.rzspider.framework.web.controller.BaseController;
import com.rzspider.framework.web.page.TableDataInfo;
import com.rzspider.framework.web.domain.Message;

/**
 * 爬虫任务详情 信息操作处理
 * 
 * @author ricozhou
 * @date 2018-05-29
 */
@Controller
@RequestMapping("/spider/spidertask")
public class SpidertaskController extends BaseController {
	private String prefix = "spider/spidertask";

	@Autowired
	private ISpidertaskService spidertaskService;
	@Autowired
	private ISpidertaskinfoService spidertaskinfoService;
	@Autowired
	private ISpiderManageService spiderManageService;

	@GetMapping()
	@RequiresPermissions("spider:spidertask:view")
	public String spidertask() {
		return prefix + "/spidertask";
	}

	/**
	 * 查询爬虫任务详情列表
	 */
	@RequiresPermissions("spider:spidertask:list")
	@GetMapping("/list")
	@ResponseBody
	public TableDataInfo list(Spidertask spidertask) {
		startPage();
		List<Spidertask> list = spidertaskService.selectSpidertaskList(spidertask);
		return getDataTable(list);
	}

	/**
	 * 新增爬虫任务详情
	 */
	@Log(title = "网页爬虫", action = "爬虫任务-任务新增")
	@RequiresPermissions("spider:spidertask:add")
	@GetMapping("/add")
	public String add() {
		return prefix + "/add";
	}

	/**
	 * 校验爬虫任务名称
	 */
	@Log(title = "网页爬虫", action = "爬虫任务-校验任务名称")
	@PostMapping("/checkTaskNameUnique")
	@ResponseBody
	public String checkTaskNameUnique(Spidertask spidertask) {
		String uniqueFlag = CommonConstant.UNIQUE;
		if (spidertask != null) {
			uniqueFlag = spidertaskService.checkTaskNameUnique(spidertask);
		}
		return uniqueFlag;
	}

	/**
	 * 修改爬虫任务详情
	 */
	@Log(title = "网页爬虫", action = "爬虫任务-修改任务")
	@RequiresPermissions("spider:spidertask:edit")
	@GetMapping("/edit/{taskId}")
	public String edit(@PathVariable("taskId") Integer taskId, Model model) {
		Spidertask spidertask = spidertaskService.selectSpidertaskById(taskId);
		model.addAttribute("spidertask", spidertask);
		return prefix + "/edit";
	}

	/**
	 * 爬虫任务参数详情
	 */
	@Log(title = "网页爬虫", action = "爬虫任务-查看参数")
	@RequiresPermissions("spider:spidertask:params")
	@GetMapping("/params/{taskId}")
	public String params(@PathVariable("taskId") Integer taskId, Model model) {
		Spidertask spidertask = spidertaskService.selectSpidertaskById(taskId);
		model.addAttribute("spidertask", spidertask);
		// 页面以后台id显示，设置一个自定义的页面id
		// 六位数的999999表示自定义页面
		if (spidertask.getCreateType() == 1) {
			// 自定义爬虫同一使用一个参数页面
			return prefix + "/params/999999";
		}
		return prefix + "/params/" + spidertask.getSpiderBackId();
	}

	/**
	 * 保存爬虫参数详情
	 */
	@Log(title = "网页爬虫", action = "爬虫任务-保存参数")
	@RequiresPermissions("spider:spidertask:saveParams")
	@PostMapping("/saveParams")
	@ResponseBody
	public Message saveParams(Spidertask spidertask) {
		if (spidertaskService.saveSpidertaskParams(spidertask) > 0) {
			return Message.success();
		}
		return Message.error(SpiderMessageConstant.SPIDER_MESSAGE_SAVEPARAMS_FAILED);
	}

	/**
	 * 保存爬虫任务详情
	 */
	@Log(title = "网页爬虫", action = "爬虫任务-保存任务")
	@RequiresPermissions("spider:spidertask:save")
	@PostMapping("/save")
	@ResponseBody
	public Message save(Spidertask spidertask) {
		if (spidertaskService.saveSpidertask(spidertask) > 0) {
			// 判断是否是立即执行
			if (spidertask.getTaskExecfrequency() == 3) {
				// 立即启动
				spidertask.setJobStatus(1);
				int rows = spidertaskService.startTask(spidertask);
				if (rows == 1) {
					return Message.success();
				} else if (rows == 2) {
					return Message.error(SpiderMessageConstant.SPIDER_MESSAGE_SPIDERTASK_STOP_NO_USE);
				}
			}
			return Message.success();
		}
		return Message.error(SpiderMessageConstant.SPIDER_MESSAGE_CRON_ERROR_NOEXIST_FAILED);
	}

	/**
	 * 删除爬虫任务详情
	 */
	@Log(title = "网页爬虫", action = "爬虫任务-删除任务")
	@RequiresPermissions("spider:spidertask:remove")
	@Transactional(rollbackFor = Exception.class)
	@PostMapping("/remove/{taskId}")
	@ResponseBody
	public Message remove(@PathVariable("taskId") Integer taskId) {
		// 根据taskid查询出所有的对应任务详情id，然后判断是否存在任务进行中，再批量删除所有
		// 查询所有的对应任务详情id
		List<Spidertaskinfo> list = spidertaskinfoService.selectSpidertaskinfoByTaskId(taskId);
		if (list != null && list.size() > 0) {
			// 先转化为数组
			Integer[] taskInfoIds = SpidertaskUtils.turnListToArray(list);
			// 遍历查询是否存在正在运行的任务
			for (int i = 0; i < taskInfoIds.length; i++) {
				int status = spidertaskinfoService.checkSpidertaskinfoStatus(taskInfoIds[i]);
				if (status == 0) {
					return Message.error(SpiderMessageConstant.SPIDER_MESSAGE_SPIDERTASK_RUNNING_TO_STOP);
				} else if (status == -1) {
					return Message.error(SpiderMessageConstant.SPIDER_MESSAGE_SPIDERTASK_NO_EXIST);
				}
			}
			if (spidertaskinfoService.batchDeleteSpidertaskinfo(taskInfoIds) < 1) {
				return Message.error(SpiderMessageConstant.SPIDER_MESSAGE_SPIDERTASK_DELETE_FAILED);
			}
		}

		if (spidertaskService.deleteSpidertaskById(taskId) > 0) {
			return Message.success();
		}
		return Message.error();
	}

	/**
	 * 批量删除爬虫任务详情
	 */
	@Log(title = "网页爬虫", action = "爬虫任务-批量删除任务")
	@RequiresPermissions("spider:spidertask:batchRemove")
	@Transactional(rollbackFor = Exception.class)
	@PostMapping("/batchRemove")
	@ResponseBody
	public Message remove(@RequestParam("ids[]") Integer[] taskIds) {
		for (Integer taskId : taskIds) {
			// 根据taskid查询出所有的对应任务详情id，然后判断是否存在任务进行中，再批量删除所有
			// 查询所有的对应任务详情id
			List<Spidertaskinfo> list = spidertaskinfoService.selectSpidertaskinfoByTaskId(taskId);
			if (list != null && list.size() > 0) {
				// 先转化为数组
				Integer[] taskInfoIds = SpidertaskUtils.turnListToArray(list);
				// 遍历查询是否存在正在运行的任务
				for (int i = 0; i < taskInfoIds.length; i++) {
					int status = spidertaskinfoService.checkSpidertaskinfoStatus(taskInfoIds[i]);
					if (status == 0) {
						return Message.error(SpiderMessageConstant.SPIDER_MESSAGE_SPIDERTASK_RUNNING_TO_STOP);
					} else if (status == -1) {
						return Message.error(SpiderMessageConstant.SPIDER_MESSAGE_SPIDERTASK_NO_EXIST);
					}
				}
				if (spidertaskinfoService.batchDeleteSpidertaskinfo(taskInfoIds) < 1) {
					return Message.error(SpiderMessageConstant.SPIDER_MESSAGE_SPIDERTASK_DELETE_FAILED);
				}
			}
		}

		int rows = spidertaskService.batchDeleteSpidertask(taskIds);
		if (rows > 0) {
			return Message.success();
		}
		return Message.error();
	}

	/**
	 * 调用爬虫任务
	 */
	@Log(title = "网页爬虫", action = "爬虫任务-启用任务计划")
	@RequiresPermissions("spider:spidertask:startTask")
	@Transactional(rollbackFor = Exception.class)
	@PostMapping("/startTask")
	@ResponseBody
	public Message startTask(Spidertask spidertask) {
		int rows = spidertaskService.startTask(spidertask);
		
		if (rows == 1) {
			return Message.success();
		} else if (rows == 2) {
			return Message.error(SpiderMessageConstant.SPIDER_MESSAGE_SPIDERTASK_STOP_NO_USE);
		}
		return Message.error(SpiderMessageConstant.SPIDER_MESSAGE_SPIDERTASK_ADD_FAILED);
	}

	/**
	 * 任务详情
	 */
	@Log(title = "网页爬虫", action = "爬虫任务-查看任务详情")
	@GetMapping("/taskInfo/{taskId}")
	public String taskInfo(@PathVariable("taskId") Integer taskId, Model model) {
		model.addAttribute("taskId", taskId);
		return prefix + "/spidertaskinfo/spidertaskinfo";
	}

	/**
	 * 检查爬虫是否存在
	 */
	@Log(title = "网页爬虫", action = "爬虫任务-校验爬虫")
	@PostMapping("/checkSpiderExist")
	@ResponseBody
	public Message checkSpiderExist(Integer taskId) {
		// 先从总爬虫里查询
		SpiderManage spiderManage2 = spiderManageService.checkSpiderExist3(taskId);
		System.out.println(spiderManage2);
		if (spiderManage2 == null) {
			return Message.error(SpiderMessageConstant.SPIDER_MESSAGE_SPIDER_NO_EXIST_CONTACT_ADMIN);
		} else if (spiderManage2.getStatus() != 0) {
			return Message.error(SpiderMessageConstant.SPIDER_MESSAGE_SPIDER_DISABLED_CONTACT_ADMIN);
		}
		// 再从爬虫管理中查询
		SpiderManage spiderManage = spiderManageService.checkSpiderExist2(taskId, ShiroUtils.getLoginName());
		if (spiderManage == null) {
			return Message.error(SpiderMessageConstant.SPIDER_MESSAGE_SPIDER_DELETED_READD);
		} else if (spiderManage.getStatus() != 0) {
			return Message.error(SpiderMessageConstant.SPIDER_MESSAGE_SPIDER_DISABLED_REUSE);
		}

		return Message.success();
	}
}
