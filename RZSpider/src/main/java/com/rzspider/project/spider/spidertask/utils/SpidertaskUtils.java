package com.rzspider.project.spider.spidertask.utils;

import java.util.Date;
import java.util.List;

import org.quartz.impl.triggers.CronTriggerImpl;

import com.rzspider.common.constant.CommonSymbolicConstant;
import com.rzspider.common.utils.DateUtils;
import com.rzspider.project.spider.spidertask.domain.Spidertask;
import com.rzspider.project.spider.spidertask.domain.Spidertaskinfo;

public class SpidertaskUtils {

	// 转化为数组
	public static Integer[] turnListToArray(List<Spidertaskinfo> list) {
		Integer[] taskInfoIds = new Integer[list.size()];
		for (int i = 0; i < list.size(); i++) {
			taskInfoIds[i] = list.get(i).getTaskInfoId();
		}
		return taskInfoIds;
	}

	// 格式化json
	public static String formatJson(String content) {
		StringBuffer sb = new StringBuffer();
		int index = 0;
		int count = 0;
		while (index < content.length()) {
			char ch = content.charAt(index);
			if (ch == '{' || ch == '[') {
				sb.append(ch);
				sb.append('\n');
				count++;
				for (int i = 0; i < count; i++) {
					sb.append('\t');
				}
			} else if (ch == '}' || ch == ']') {
				sb.append('\n');
				count--;
				for (int i = 0; i < count; i++) {
					sb.append('\t');
				}
				sb.append(ch);
			} else if (ch == ',') {
				sb.append(ch);
				sb.append('\n');
				for (int i = 0; i < count; i++) {
					sb.append('\t');
				}
			} else {
				sb.append(ch);
			}
			index++;
		}
		return sb.toString();
	}

	// 拼接cron表达式
	public static String getCorrectCronExpression(Spidertask spidertask) {
		String cronExpression = null;
		// format时间
		// if (spidertask.getFirstStartTime() == null ||
		// spidertask.getFirstStartTime() == ""
		// || (DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS,
		// spidertask.getFirstStartTime())
		// .before(DateUtils.getNowDate()))) {
		// spidertask.setFirstStartTime(DateUtils.getTime());
		// }
		if (spidertask.getFirstStartTime() == null
				|| CommonSymbolicConstant.EMPTY_STRING.equals(spidertask.getFirstStartTime())) {
			spidertask.setFirstStartTime(DateUtils.getTime());
		}
		// 取出时间分量
		int[] timeComponent = DateUtils.getTimeComponent(spidertask.getFirstStartTime(), DateUtils.YYYY_MM_DD_HH_MM_SS);
		if (spidertask.getTaskExecfrequency() == 0) {
			// 只执行一次
			// 拼接cron表达式,取出时间的年月日时分秒,往后推5秒不然无法执行
			// 定时执行不再往后推，需要手动启动
			cronExpression = timeComponent[5] + " " + timeComponent[4] + " " + timeComponent[3] + " " + timeComponent[2]
					+ " " + timeComponent[1] + " ?" + " " + timeComponent[0];
		} else if (spidertask.getTaskExecfrequency() == 1) {
			// 固定频次执行
			if (spidertask.getTaskExecfrequency2() == 0) {
				// 每时 1 1 0/1 * * ? *
				cronExpression = timeComponent[5] + " " + timeComponent[4] + " " + timeComponent[3] + "/"
						+ spidertask.getTimes() + " * * ? *";
				// cronExpression="0/59 * * * * ? *";
			} else if (spidertask.getTaskExecfrequency2() == 1) {
				// 每日 1 1 1 * * ? *
				cronExpression = timeComponent[5] + " " + timeComponent[4] + " " + timeComponent[3] + " * * ? *";
			} else if (spidertask.getTaskExecfrequency2() == 2) {
				// 每周 1 1 1 ? * 1 *
				cronExpression = timeComponent[5] + " " + timeComponent[4] + " " + timeComponent[3] + " ? * "
						+ timeComponent[6] + " *";
			} else if (spidertask.getTaskExecfrequency2() == 3) {
				// 每月 1 1 1 1 * ? *
				cronExpression = timeComponent[5] + " " + timeComponent[4] + " " + timeComponent[3] + " "
						+ timeComponent[2] + " * ? *";
			}
		} else if (spidertask.getTaskExecfrequency() == 2) {
			// 直接cron表达式执行
			cronExpression = spidertask.getCronExpression();
		} else if (spidertask.getTaskExecfrequency() == 3) {
			// 立即执行
			// 获取当前时间并往后推5秒
			timeComponent = DateUtils.getTimeComponent(
					DateUtils.format(new Date(new Date().getTime() + 5000), DateUtils.YYYY_MM_DD_HH_MM_SS),
					DateUtils.YYYY_MM_DD_HH_MM_SS);
			// 拼接cron表达式,取出时间的年月日时分秒,往后推5秒不然无法执行
			cronExpression = timeComponent[5] + " " + timeComponent[4] + " " + timeComponent[3] + " " + timeComponent[2]
					+ " " + timeComponent[1] + " ?" + " " + timeComponent[0];
		}
		// 不合法返回null
		if (!isValidExpression(cronExpression)) {
			return null;
		}
		return cronExpression;
	}

	// 判断是否cron正确
	public static boolean isValidExpression(String cronExpression) {
		CronTriggerImpl trigger = new CronTriggerImpl();
		try {
			trigger.setCronExpression(cronExpression);
			Date date = trigger.computeFirstFireTime(null);
			return date != null && date.after(new Date());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// 获取当前时间表达式
	public static String getCurrentTimeCronExpression() {
		// 获取当前时间并往后推3秒
		// 取出时间分量
		int[] timeComponent = DateUtils.getTimeComponent(
				DateUtils.format(new Date(new Date().getTime() + 3000), DateUtils.YYYY_MM_DD_HH_MM_SS),
				DateUtils.YYYY_MM_DD_HH_MM_SS);
		// 拼接cron表达式,取出时间的年月日时分秒,往后推5秒不然无法执行
		String cronExpression = timeComponent[5] + " " + timeComponent[4] + " " + timeComponent[3] + " "
				+ timeComponent[2] + " " + timeComponent[1] + " ?" + " " + timeComponent[0];
		return cronExpression;
	}
}
