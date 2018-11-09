package com.rzspider.project.spider.spidertask.mainwork.runutils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.rzspider.common.constant.project.SpiderConstant;
import com.rzspider.project.spider.customspider.utils.ConsoleSimulator;
import com.rzspider.project.spider.customspider.utils.RunUtils;
import com.rzspider.project.spider.customspider.utils.WebSocketConsoleThread;

public class CustomSpiderRunUtils {

	// 运行
	public static Map<String, Object> run(String cmd, Integer taskInfoId) throws IOException {
		Map<String, Object> messageMap = new HashMap<String, Object>();
		long pid;
		Process child = Runtime.getRuntime().exec(cmd);
		messageMap.put(SpiderConstant.SPIDER_KEYWORD_PROCESSMAP_PROCESS, child);

		// 获取此子进程的pid,只windows系统
		pid = RunUtils.getProcessPID(child);
		messageMap.put(SpiderConstant.SPIDER_KEYWORD_PROCESSMAP_PID, pid);

		// 获取程序输入流
		// OutputStream os = child.getOutputStream();
		// 正常输出流和异常输出流,虽然不在需要打印，但是为了防止阻塞，还是将其读取出来但是不再显示
		InputStream stdin = child.getInputStream();
		InputStream stderr = child.getErrorStream();
		// 启动线程，获取输出流

		TaskConsoleSimulator cs1 = new TaskConsoleSimulator(stdin, 0, taskInfoId);
		TaskConsoleSimulator cs2 = new TaskConsoleSimulator(stderr, 1, taskInfoId);
		Thread tIn = new Thread(cs1);
		Thread tErr = new Thread(cs2);
		tIn.start();
		tErr.start();
		// 启动线程获取输出之后不在阻塞，直接返回，打印输出以轮播形式推送前台
		messageMap.put(SpiderConstant.SPIDER_KEYWORD_PROCESSMAP_INPUTSTREAMTHREAD, tIn);
		messageMap.put(SpiderConstant.SPIDER_KEYWORD_PROCESSMAP_INPUTSTREAMCONSOLE, cs1);
		messageMap.put(SpiderConstant.SPIDER_KEYWORD_PROCESSMAP_ERRORSTREAMTHREAD, tErr);
		messageMap.put(SpiderConstant.SPIDER_KEYWORD_PROCESSMAP_ERRORSTREAMCONSOLE, cs2);
		return messageMap;
	}

	// 阻塞执行cmd
	public static String[] run2(String cmd) {
		String returnPrintContent = null;
		String returnErrorContent = null;
		String[] returnContent = new String[2];
		try {

			Process child = Runtime.getRuntime().exec(cmd);

			// 获取程序输入流
			OutputStream os = child.getOutputStream();
			// 正常输出流和异常输出流
			InputStream stdin = child.getInputStream();
			InputStream stderr = child.getErrorStream();
			// 启动线程
			TaskConsoleSimulator cs1 = new TaskConsoleSimulator(stdin, 0, null);
			TaskConsoleSimulator cs2 = new TaskConsoleSimulator(stderr, 1, null);
			Thread tIn = new Thread(cs1);
			Thread tErr = new Thread(cs2);
			tIn.start();
			tErr.start();
			int result = child.waitFor();
			tIn.join();
			tErr.join();
			returnPrintContent = cs1.getReturnPrintContent();
			returnErrorContent = cs2.getReturnErrorContent();
			// 0是全部信息
			returnContent[0] = returnPrintContent;
			// 1是错误信息
			returnContent[1] = returnErrorContent;
			return returnContent;
		} catch (Exception e) {
			e.printStackTrace();
			return returnContent;
		}
	}
}
