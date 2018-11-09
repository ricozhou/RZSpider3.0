package com.rzspider.project.spider.spidertask.mainwork.utils;

import java.util.Iterator;
import java.util.Map;

import com.rzspider.common.constant.project.SpiderConstant;
import com.rzspider.project.spider.customspider.utils.WebSocketConsoleThread;
import com.rzspider.project.spider.spidertask.mainwork.runutils.TaskConsoleSimulator;
import com.rzspider.project.spider.spidertask.service.SpidertaskServiceImpl;

public class SpiderTaskThreadUtils {

	// 从map中去除某个元素
	public static void removeObjectFromMap(Integer taskInfoId) {
		// 获取迭代器
		Iterator it = SpidertaskServiceImpl.spiderTaskThreadMap.keySet().iterator();
		while (it.hasNext()) {
			Integer key = (Integer) it.next();
			if (key.equals(taskInfoId)) {
				it.remove();
			}
		}
	}

	// 中止线程
	public static void stopSpiderTaskThread(Integer taskInfoId) {
		// ExecutorService es =
		// SpidertaskServiceImpl.spiderTaskThreadMap.get(taskInfoId);
		Thread es = SpidertaskServiceImpl.spiderTaskThreadMap.get(taskInfoId);
		if (es != null) {
			// 中止线程池
			stopSpiderTask(es);
		}

	}

	// 终止线程（不推荐）
	private static void stopSpiderTask(Thread es) {
		try {
			es.stop();
			es.interrupt();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 关闭运行爬虫相关的线程
	public static void closeTaskCSRelatedThread(Map<String, Object> messageMap) {
		// 使用标识结束
		TaskConsoleSimulator cs1 = (TaskConsoleSimulator) messageMap
				.get(SpiderConstant.SPIDER_KEYWORD_PROCESSMAP_INPUTSTREAMCONSOLE);
		TaskConsoleSimulator cs2 = (TaskConsoleSimulator) messageMap
				.get(SpiderConstant.SPIDER_KEYWORD_PROCESSMAP_ERRORSTREAMCONSOLE);
		if (cs1 != null) {
			cs1.isStop = true;
			cs1.isForceStop = true;
		}
		if (cs2 != null) {
			cs2.isStop = true;
			cs2.isForceStop = true;
		}
	}
}
