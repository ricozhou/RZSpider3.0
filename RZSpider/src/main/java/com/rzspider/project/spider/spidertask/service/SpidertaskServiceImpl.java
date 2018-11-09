package com.rzspider.project.spider.spidertask.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import javax.annotation.PostConstruct;

import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rzspider.common.constant.ScheduleConstants;
import com.rzspider.common.constant.UserConstants;
import com.rzspider.common.utils.OtherUtils;
import com.rzspider.common.utils.StringUtils;
import com.rzspider.common.utils.security.ShiroUtils;
import com.rzspider.project.spider.spidertask.mapper.SpidertaskMapper;
import com.rzspider.project.spider.spidertask.mapper.SpidertaskinfoMapper;
import com.rzspider.project.monitor.job.domain.Job;
import com.rzspider.project.monitor.job.util.ScheduleUtils;
import com.rzspider.project.spider.spidermanage.domain.SpiderManage;
import com.rzspider.project.spider.spidermanage.service.ISpiderManageService;
import com.rzspider.project.spider.spidertask.domain.Spidertask;
import com.rzspider.project.spider.spidertask.domain.Spidertaskinfo;
import com.rzspider.project.spider.spidertask.service.ISpidertaskService;
import com.rzspider.project.spider.spidertask.utils.SpiderScheduleUtils;
import com.rzspider.project.spider.spidertask.utils.SpidertaskUtils;
import com.rzspider.project.system.role.service.IRoleService;

/**
 * 爬虫任务详情 服务层实现
 * 
 * @author ricozhou
 * @date 2018-05-29
 */
@Service
public class SpidertaskServiceImpl implements ISpidertaskService {
	@Autowired
	private SpidertaskMapper spidertaskMapper;
	@Autowired
	private Scheduler scheduler;
	@Autowired
	private ISpiderManageService spiderManageService;
	@Autowired
	private IRoleService roleService;

	@Autowired
	private SpidertaskinfoMapper spidertaskinfoMapper;
	// 设置一个全局静态的map存放所有的线程，以便于对应的取出线程中止他
	public static Map<Integer, Thread> spiderTaskThreadMap = new HashMap<Integer, Thread>();

	/**
	 * 项目启动时，初始化定时器
	 */
	@PostConstruct
	public void init() {
		List<Spidertask> spidertaskList = spidertaskMapper.selectSpidertaskList(new Spidertask());
		for (Spidertask spidertask : spidertaskList) {
			CronTrigger cronTrigger = SpiderScheduleUtils.getCronTrigger(scheduler, spidertask.getTaskId());
			// 如果不存在，则创建
			if (cronTrigger == null) {
				SpiderScheduleUtils.createScheduleJob(scheduler, spidertask);
			} else {
				SpiderScheduleUtils.updateScheduleJob(scheduler, spidertask);
			}
		}
	}

	/**
	 * 查询爬虫任务详情信息
	 * 
	 * @param taskId
	 *            爬虫任务详情ID
	 * @return 爬虫任务详情信息
	 */
	@Override
	public Spidertask selectSpidertaskById(Integer taskId) {
		return spidertaskMapper.selectSpidertaskById(taskId);
	}

	/**
	 * 查询爬虫任务详情列表
	 * 
	 * @param spidertask
	 *            爬虫任务详情信息
	 * @return 爬虫任务详情集合
	 */
	@Override
	public List<Spidertask> selectSpidertaskList(Spidertask spidertask) {
		// 是否是管理员
		if (!OtherUtils.isManager(roleService.selectRoleKeys(ShiroUtils.getUserId()))) {
			spidertask.setCreateBy(ShiroUtils.getLoginName());
		}
		return spidertaskMapper.selectSpidertaskList(spidertask);
	}

	/**
	 * 新增爬虫任务详情
	 * 
	 * @param spidertask
	 *            爬虫任务详情信息
	 * @return 结果
	 */
	@Override
	public int insertSpidertask(Spidertask spidertask) {
		return spidertaskMapper.insertSpidertask(spidertask);
	}

	/**
	 * 修改爬虫任务详情
	 * 
	 * @param spidertask
	 *            爬虫任务详情信息
	 * @return 结果
	 */
	@Override
	public int updateSpidertask(Spidertask spidertask) {
		return spidertaskMapper.updateSpidertask(spidertask);
	}

	/**
	 * 保存爬虫任务详情
	 * 
	 * @param spidertask
	 *            爬虫任务详情信息
	 * @return 结果
	 */
	@Override
	public int saveSpidertask(Spidertask spidertask) {
		Integer taskId = spidertask.getTaskId();
		int rows = 0;
		// 根据爬虫名称查询爬虫详细信息
		SpiderManage spiderManage = new SpiderManage();
		spiderManage.setSpiderName(spidertask.getSpiderName());
		spiderManage.setCreateBy(ShiroUtils.getLoginName());
		SpiderManage sm = spiderManageService.selectSpiderManageByName(spiderManage);
		// 拼接出cron表达式
		String cronExpression = SpidertaskUtils.getCorrectCronExpression(spidertask);
		if (cronExpression == null) {
			return 0;
		}
		spidertask.setCronExpression(cronExpression);
		if (sm != null) {
			spidertask.setSpiderId(Integer.valueOf(String.valueOf(sm.getSpiderId())));
			spidertask.setSpiderBackId(Integer.valueOf(String.valueOf(sm.getSpiderBackId())));
			spidertask.setCreateType(sm.getCreateType());
			spidertask.setStatus(sm.getStatus());
			spidertask.setSpiderType(sm.getSpiderType());
		} else {
			return 0;
		}
		if (StringUtils.isNotNull(taskId)) {
			// 需要同时更改所有任务详情中的信息，如果是正常的则任务详情中
			spidertask.setUpdateBy(ShiroUtils.getLoginName());
			// 任务状态是暂停
			spidertask.setJobStatus(ScheduleConstants.Status.PAUSE.getValue());
			rows = spidertaskMapper.updateSpidertask(spidertask);
			if (rows > 0) {
				// 更新
				SpiderScheduleUtils.updateScheduleJob(scheduler, spidertask);
			}
		} else {
			// 只有插入才设置参数，修改则不修改参数
			spidertask.setSpiderDefaultParamsAll(sm.getSpiderDefaultParamsAll());
			spidertask.setTaskParams(sm.getSpiderDefaultParams());
			spidertask.setCreateBy(ShiroUtils.getLoginName());
			// 任务状态是暂停
			spidertask.setJobStatus(ScheduleConstants.Status.PAUSE.getValue());
			rows = spidertaskMapper.insertSpidertask(spidertask);
			if (rows > 0) {
				// 创建
				SpiderScheduleUtils.createScheduleJob(scheduler, spidertask);
			}
		}
		return rows;
	}

	/**
	 * 删除爬虫任务详情信息 删除任务后，所对应的trigger也将被删除
	 * 
	 * @param taskId
	 *            爬虫任务详情ID
	 * @return 结果
	 */
	@Override
	public int deleteSpidertaskById(Integer taskId) {
		// 检查所有相关的爬虫任务，必须全部关掉才能删除

		// 要把任务详情相关一起删除
		// int rows=0;
		// rows=spidertaskMapper.deleteSpidertaskById(taskId);
		// if(){
		//
		// }
		int rows = spidertaskMapper.deleteSpidertaskById(taskId);
		if (rows > 0) {
			// 所对应的trigger也将被删除
			SpiderScheduleUtils.deleteScheduleJob(scheduler, taskId);
		}
		return rows;
	}

	/**
	 * 批量删除爬虫任务详情对象
	 * 
	 * @param taskIds
	 *            需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int batchDeleteSpidertask(Integer[] taskIds) {
		int rows = 0;
		for (Integer taskId : taskIds) {
			rows = deleteSpidertaskById(taskId);
		}
		return rows;
	}

	@Override
	public String checkTaskNameUnique(Spidertask spidertask) {
		if (spidertask.getTaskId() == null) {
			spidertask.setTaskId(-1);
		}
		int taskId = spidertask.getTaskId();
		spidertask.setCreateBy(ShiroUtils.getLoginName());
		Spidertask info = spidertaskMapper.checkTaskNameUnique(spidertask);
		if (StringUtils.isNotNull(info) && StringUtils.isNotNull(info.getTaskId()) && info.getTaskId() != taskId) {
			return UserConstants.TASK_NAME_NOT_UNIQUE;
		}
		return UserConstants.TASK_NAME_UNIQUE;
	}

	/**
	 * 任务调度状态修改
	 * 
	 */
	@Override
	public int changeStatus(Spidertask spidertask) {
		int rows = 0;
		int jobStatus = spidertask.getJobStatus();
		if (jobStatus == 1) {
			// 恢复
			rows = resumeJob(spidertask);
		} else if (jobStatus == 0) {
			// 停止
			rows = pauseJob(spidertask);
		}
		// return rows;
		return 1;
	}

	/**
	 * 暂停任务
	 * 
	 * @param job
	 *            调度信息
	 */
	@Override
	public int pauseJob(Spidertask spidertask) {
		spidertask.setJobStatus(ScheduleConstants.Status.PAUSE.getValue());
		spidertask.setUpdateBy(ShiroUtils.getLoginName());
		int rows = spidertaskMapper.updateSpiderJobStatus(spidertask);
		if (rows > 0) {
			SpiderScheduleUtils.pauseJob(scheduler, spidertask.getTaskId());
		}
		return rows;
	}

	/**
	 * 恢复任务
	 * 
	 * @param job
	 *            调度信息
	 */
	@Override
	public int resumeJob(Spidertask spidertask) {
		spidertask.setJobStatus(ScheduleConstants.Status.NORMAL.getValue());
		spidertask.setUpdateBy(ShiroUtils.getLoginName());
		int rows = spidertaskMapper.updateSpiderJobStatus(spidertask);
		if (rows > 0) {
			SpiderScheduleUtils.resumeJob(scheduler, spidertask.getTaskId());
		}
		return rows;
	}

	// 修改爬虫参数
	@Override
	public int saveSpidertaskParams(Spidertask spidertask) {
		return spidertaskMapper.updateSpidertaskParams(spidertask);
	}

	// 任务详情启动任务
	@Override
	public int startTask(Spidertask spidertask) {
		// 根据任务id查询所需要的必须内容然后添加到另一张表
		Spidertask spidertask2 = spidertaskMapper.selectSpidertaskById(spidertask.getTaskId());
		if (spidertask2.getTaskStatus() == 1) {
			// 状态暂停则不能添加(触发)
			return 2;
		}
		// 判断是否是立即执行，是的话则更改执行时间表达式cron并启动执行
		// 判断是否是立即执行
		if (spidertask.getJobStatus() == 1 && spidertask2.getTaskExecfrequency() == 3) {
			// 获取表达式
			// 编辑的时候也会更新表达式，重复，待改进
			spidertask2.setCronExpression(SpidertaskUtils.getCurrentTimeCronExpression());
			// 更新表达式
			if (spidertaskMapper.updateSpidertaskCronExpression(spidertask2) < 1) {
				return 0;
			} else {
				// 更新任务
				SpiderScheduleUtils.updateScheduleJob(scheduler, spidertask2);
			}
		}

		int rows = 0;
		rows = changeStatus(spidertask);
		return rows;
	}

	// 添加任务2
	@Override
	public Spidertaskinfo addTask2(Spidertask spidertask) {
		// 根据任务id查询所需要的必须内容然后添加到另一张表
		Spidertask spidertask2 = spidertaskMapper.selectSpidertaskById(spidertask.getTaskId());
		Spidertaskinfo spidertaskinfo = new Spidertaskinfo();
		spidertaskinfo.setTaskId(spidertask2.getTaskId());
		spidertaskinfo.setTaskName(spidertask2.getTaskName());
		spidertaskinfo.setSpiderName(spidertask2.getSpiderName());
		// 选择客户端时间，为了计算耗时
		spidertaskinfo.setStartTime(new Date());
		// 添加默认状态是结束状态/修改逻辑，使用定时框架，添加即启动
		spidertaskinfo.setTaskStatus(0);
		// 完成状态是未完成
		spidertaskinfo.setFinishStatus(0);
		spidertaskinfo.setSpiderDefaultParamsAll(spidertask2.getSpiderDefaultParamsAll());
		spidertaskinfo.setTaskParams(spidertask2.getTaskParams());
		int rows = 0;
		rows = spidertaskinfoMapper.insertSpidertaskinfo2(spidertaskinfo);
		return spidertaskinfo;
	}
}
