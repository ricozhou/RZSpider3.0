package com.rzspider.project.spider.spidertask.utils;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.log.SysoCounter;
import com.rzspider.common.constant.ScheduleConstants;
import com.rzspider.common.constant.project.SpiderConstant;
import com.rzspider.project.spider.spidertask.domain.Spidertask;

/**
 * 定时任务工具类
 * 
 * @author ricozhou
 *
 */
public class SpiderScheduleUtils {
	private static final Logger log = LoggerFactory.getLogger(SpiderScheduleUtils.class);

	/**
	 * 获取触发器key
	 */
	public static TriggerKey getTriggerKey(Integer taskId) {
		return TriggerKey.triggerKey(SpiderConstant.JOB_NAME + taskId);
	}

	/**
	 * 获取jobKey
	 */
	public static JobKey getJobKey(Integer taskId) {
		return JobKey.jobKey(SpiderConstant.JOB_NAME + taskId);
	}

	/**
	 * 获取表达式触发器
	 */
	public static CronTrigger getCronTrigger(Scheduler scheduler, Integer taskId) {
		try {
			return (CronTrigger) scheduler.getTrigger(getTriggerKey(taskId));
		} catch (SchedulerException e) {
			log.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 创建定时任务
	 */
	public static void createScheduleJob(Scheduler scheduler, Spidertask spidertask) {
		try {
			// 构建job信息
			JobDetail jobDetail = JobBuilder.newJob(SpiderScheduleJob.class)
					.withIdentity(getJobKey(spidertask.getTaskId())).build();

			// 表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(spidertask.getCronExpression());

			// 按新的cronExpression表达式构建一个新的trigger
			CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(spidertask.getTaskId()))
					.withSchedule(scheduleBuilder).build();

			// 放入参数，运行时的方法可以获取
			jobDetail.getJobDataMap().put(ScheduleConstants.JOB_PARAM_KEY, spidertask);

			scheduler.scheduleJob(jobDetail, trigger);

			// 暂停任务
			if (spidertask.getJobStatus() == ScheduleConstants.Status.PAUSE.getValue()) {
				pauseJob(scheduler, spidertask.getTaskId());
			}
		} catch (SchedulerException e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * 更新定时任务
	 */
	public static void updateScheduleJob(Scheduler scheduler, Spidertask spidertask) {
		try {
			TriggerKey triggerKey = getTriggerKey(spidertask.getTaskId());

			// 表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(spidertask.getCronExpression());

			CronTrigger trigger = getCronTrigger(scheduler, spidertask.getTaskId());

			// 按新的cronExpression表达式重新构建trigger
			// 捕捉异常，万一此任务已经运行结束没有了cron会报异常，需要新建
			try {
				trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
				// 参数
				trigger.getJobDataMap().put(ScheduleConstants.JOB_PARAM_KEY, spidertask);
				scheduler.rescheduleJob(triggerKey, trigger);
			} catch (Exception e) {
				e.printStackTrace();
				createScheduleJob(scheduler, spidertask);
			}

			// 暂停任务
			if (spidertask.getJobStatus() == ScheduleConstants.Status.PAUSE.getValue()) {
				pauseJob(scheduler, spidertask.getTaskId());
			}

		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * 立即执行任务
	 */
	public static void run(Scheduler scheduler, Spidertask spidertask) {
		try {
			// 参数
			JobDataMap dataMap = new JobDataMap();
			dataMap.put(ScheduleConstants.JOB_PARAM_KEY, spidertask);

			scheduler.triggerJob(getJobKey(spidertask.getTaskId()), dataMap);
		} catch (SchedulerException e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * 暂停任务
	 */
	public static void pauseJob(Scheduler scheduler, Integer taskId) {
		try {
			scheduler.pauseJob(getJobKey(taskId));
		} catch (SchedulerException e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * 恢复任务
	 */
	public static void resumeJob(Scheduler scheduler, Integer taskId) {
		try {
			scheduler.resumeJob(getJobKey(taskId));
		} catch (SchedulerException e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * 删除定时任务
	 */
	public static void deleteScheduleJob(Scheduler scheduler, Integer taskId) {
		try {
			scheduler.deleteJob(getJobKey(taskId));
		} catch (SchedulerException e) {
			log.error(e.getMessage());
		}
	}
}
