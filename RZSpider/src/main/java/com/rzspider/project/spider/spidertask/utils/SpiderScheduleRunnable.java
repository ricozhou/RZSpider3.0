package com.rzspider.project.spider.spidertask.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.util.ReflectionUtils;

import com.rzspider.common.utils.StringUtils;
import com.rzspider.common.utils.spring.SpringUtils;
import com.rzspider.project.common.spiderdata.domain.Spiderdata;
import com.rzspider.project.common.spiderdata.service.ISpiderdataService;
import com.rzspider.project.spider.spidertask.domain.Spidertaskinfo;
import com.rzspider.project.spider.spidertask.mainwork.domain.StartSpiderInfo;
import com.rzspider.project.spider.spidertask.mainwork.utils.SpiderTaskThreadUtils;
import com.rzspider.project.spider.spidertask.service.ISpidertaskinfoService;

/**
 * 执行定时任务
 * 
 * @author ricozhou
 *
 */
public class SpiderScheduleRunnable implements Runnable {
	private Object target;
	private Method method;
	private StartSpiderInfo startSpiderInfo;
	ISpidertaskinfoService spidertaskinfoService = (ISpidertaskinfoService) SpringUtils
			.getBean(ISpidertaskinfoService.class);

	public SpiderScheduleRunnable(String beanName, String methodName, StartSpiderInfo startSpiderInfo)
			throws NoSuchMethodException, SecurityException {
		this.target = SpringUtils.getBean(beanName);
		this.startSpiderInfo = startSpiderInfo;

		if (startSpiderInfo != null) {
			this.method = target.getClass().getDeclaredMethod(methodName, StartSpiderInfo.class);
		}
	}

	@Override
	public void run() {
		try {
			System.out.println(1);
			// 反射之前需要执行的任务
			// 清除对应的数据
			// 首先执行
			spidertaskinfoService.firstExection(startSpiderInfo);

			// 中间
			ReflectionUtils.makeAccessible(method);
			if (startSpiderInfo != null) {
				method.invoke(target, startSpiderInfo);
			}
			if (startSpiderInfo.getCreateType() == 0) {
				// 内置爬虫需要运行的程序会阻塞，所以可以再次判断程序已执行完成，但是自定义爬虫是进程，并不阻塞，因此不能在此判断是否完成，而应该在runtime的输出流判断
				// 方法执行完需要的任务
				// 更改显示设置
				// 最终执行
				System.out.println("全部完成");
				spidertaskinfoService.finallyExection(startSpiderInfo, 2);
			}
		} catch (InvocationTargetException ee) {
			System.out.println("爬虫出错");
			spidertaskinfoService.finallyExection(startSpiderInfo, 3);
		} catch (IllegalAccessException e) {
			// e.printStackTrace();
			System.out.println("部分完成");
			// 所有的错误都抛出到这，表明没有完全完成，更改显示到前端
			// 未完全完成
			spidertaskinfoService.finallyExection(startSpiderInfo, 1);
		}
	}
}
