package com.rzspider.project.spider.spidertask.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.rzspider.common.constant.CommonSymbolicConstant;
import com.rzspider.common.constant.ScheduleConstants;
import com.rzspider.common.utils.spring.SpringUtils;
import com.rzspider.framework.web.domain.Message;
import com.rzspider.project.common.spiderdata.domain.Spiderdata;
import com.rzspider.project.spider.spidermanage.domain.SpiderList;
import com.rzspider.project.spider.spidermanage.service.ISpiderListService;
import com.rzspider.project.spider.spidertask.domain.Spidertask;
import com.rzspider.project.spider.spidertask.domain.Spidertaskinfo;
import com.rzspider.project.spider.spidertask.mainwork.domain.StartSpiderInfo;
import com.rzspider.project.spider.spidertask.service.ISpidertaskService;
import com.rzspider.project.spider.spidertask.service.SpidertaskServiceImpl;

/**
 * 定时任务
 * 
 * @author ricozhou
 *
 */
public class SpiderScheduleJob extends QuartzJobBean {
	private static final Logger log = LoggerFactory.getLogger(SpiderScheduleJob.class);

	// private ExecutorService service = Executors.newSingleThreadExecutor();

	// 执行任务
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		Spidertask spidertask = (Spidertask) context.getMergedJobDataMap().get(ScheduleConstants.JOB_PARAM_KEY);
		try {
			// 执行任务
			// 一是先添加任务详情到数据库，二是直接启动任务
			ISpidertaskService spidertaskService = (ISpidertaskService) SpringUtils.getBean(ISpidertaskService.class);
			ISpiderListService spiderListService = (ISpiderListService) SpringUtils.getBean(ISpiderListService.class);
			if (spidertask.getTaskStatus() != 0) {
				return;
			}
			// 从爬虫总目录判断爬虫是否存在且是否可用
			SpiderList spiderList = spiderListService.selectSpiderListBySpiderBackId(spidertask.getSpiderBackId());

			if (spiderList == null) {
				return;
			}
			Spidertaskinfo spidertaskinfo = spidertaskService.addTask2(spidertask);
			if (spidertaskinfo.getTaskInfoId() == 0) {
				// 插入失败直接结束
				return;
			}
			// 插入成功后，获取需要执行的任务的具体参数
			StartSpiderInfo startSpiderInfo = new StartSpiderInfo();
			// 后台id
			startSpiderInfo.setSpiderBackId(spiderList.getSpiderBackId());
			// 内置的还是自定义
			startSpiderInfo.setCreateType(spiderList.getCreateType());
			// 语言类型
			startSpiderInfo.setSpiderLanguageType(spiderList.getCustomSpiderType());
			// 详细参数
			startSpiderInfo.setSpiderParams(spidertaskinfo.getTaskParams());
			startSpiderInfo.setTaskInfoId(spidertaskinfo.getTaskInfoId());

			// 开始解析
			// 0是内置
			if (startSpiderInfo.getCreateType() == 0) {
				// 正式启动
				ExecutorService service = Executors.newSingleThreadExecutor();
				// 加入map统一管理
				// SpidertaskServiceImpl.spiderTaskThreadMap.put(startSpiderInfo.getTaskInfoId(),
				// service);
				SpiderScheduleRunnable task = new SpiderScheduleRunnable(
						ScheduleConstants.INTERNAL_SPIDER_TASK_BEAN_NAME, ScheduleConstants.INTERNAL_SPIDER_METHOD_NAME
								+ CommonSymbolicConstant.UNDERLINE + startSpiderInfo.getSpiderBackId(),
						startSpiderInfo);
				Thread t = new Thread(task);
				t.start();
				SpidertaskServiceImpl.spiderTaskThreadMap.put(startSpiderInfo.getTaskInfoId(), t);
				// Future<?> future = service.submit(task);
				// future.get();
			} else if (startSpiderInfo.getCreateType() == 1) {
				// 1是自定义
				// 正式启动
				ExecutorService service = Executors.newSingleThreadExecutor();
				// 加入map统一管理
				// SpidertaskServiceImpl.spiderTaskThreadMap.put(startSpiderInfo.getTaskInfoId(),
				// service);
				SpiderScheduleRunnable task = new SpiderScheduleRunnable(ScheduleConstants.CUSTOM_SPIDER_TASK_BEAN_NAME,
						ScheduleConstants.CUSTOM_SPIDER_TASK_MOTHOD_NAME, startSpiderInfo);
				Thread t = new Thread(task);
				t.start();
				SpidertaskServiceImpl.spiderTaskThreadMap.put(startSpiderInfo.getTaskInfoId(), t);
				// Future<?> future = service.submit(task);
				// future.get();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
