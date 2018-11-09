package com.rzspider.project.spider.customspider.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import com.itextpdf.text.log.SysoCounter;
import com.rzspider.common.constant.OtherConstant;
import com.rzspider.common.constant.project.SpiderConstant;
import com.rzspider.framework.config.FilePathConfig;
import com.rzspider.project.common.file.utilt.FileUtils;
import com.rzspider.project.spider.customspider.domain.FileTree;

public class RunUtils {
	// 运行代码有两种方式，一种是通过反射动态运行，一种是另起进程，这里选择另起进程
	// js也使用这个
	public static Map<String, Object> run(String cmd, Integer customSpiderBackId) {
		Map<String, Object> messageMap = new HashMap<String, Object>();
		long pid;
		try {

			Process child = Runtime.getRuntime().exec(cmd);
			messageMap.put(SpiderConstant.SPIDER_KEYWORD_PROCESSMAP_PROCESS, child);

			// 获取此子进程的pid,只windows系统
			pid = getProcessPID(child);
			messageMap.put(SpiderConstant.SPIDER_KEYWORD_PROCESSMAP_PID, pid);

			// 获取程序输入流
			// OutputStream os = child.getOutputStream();
			// 正常输出流和异常输出流
			InputStream stdin = child.getInputStream();
			InputStream stderr = child.getErrorStream();
			// 启动线程，获取输出流

			ConsoleSimulator cs1 = new ConsoleSimulator(stdin, 0, customSpiderBackId);
			ConsoleSimulator cs2 = new ConsoleSimulator(stderr, 1, customSpiderBackId);
			Thread tIn = new Thread(cs1);
			Thread tErr = new Thread(cs2);
			tIn.start();
			tErr.start();
			// 启动线程获取输出之后不在阻塞，直接返回，打印输出以轮播形式推送前台
			messageMap.put(SpiderConstant.SPIDER_KEYWORD_PROCESSMAP_INPUTSTREAMTHREAD, tIn);
			messageMap.put(SpiderConstant.SPIDER_KEYWORD_PROCESSMAP_INPUTSTREAMCONSOLE, cs1);
			messageMap.put(SpiderConstant.SPIDER_KEYWORD_PROCESSMAP_ERRORSTREAMTHREAD, tErr);
			messageMap.put(SpiderConstant.SPIDER_KEYWORD_PROCESSMAP_ERRORSTREAMCONSOLE, cs2);
			// 正在运行
			messageMap.put(SpiderConstant.SPIDER_KEYWORD_PROCESSMAP_RUNSTATUS, 0);
			// 开始发送
			// 运行速度太快，前台反应不及时，另起线程，阻塞发送
			// 启动发送线程
			WebSocketConsoleThread wsct = new WebSocketConsoleThread(cs1, cs2);
			Thread t = new Thread(wsct);
			t.start();
			messageMap.put(SpiderConstant.SPIDER_KEYWORD_PROCESSMAP_SENDCONSOLETHREAD, t);
			messageMap.put(SpiderConstant.SPIDER_KEYWORD_PROCESSMAP_SENDCONSOLE, wsct);
			return messageMap;
		} catch (Exception e) {
			e.printStackTrace();
			return messageMap;
		}
	}

	// 运行代码有两种方式，一种是通过反射动态运行，一种是另起进程，这里选择另起进程
	// js也使用这个
	public static String[] run2(String cmd, Integer customSpiderBackId) {
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

			ConsoleSimulator cs1 = new ConsoleSimulator(stdin, 0, customSpiderBackId);
			ConsoleSimulator cs2 = new ConsoleSimulator(stderr, 1, customSpiderBackId);
			Thread tIn = new Thread(cs1);
			Thread tErr = new Thread(cs2);
			tIn.start();
			tErr.start();
			int result = child.waitFor();
			tIn.join();
			tErr.join();
			returnPrintContent = cs1.getReturnPrintContent();
			returnErrorContent = cs2.getReturnErrorContent();
			// 处理中文乱码，需更改服务器端编码
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

	// 运行代码有两种方式，一种是通过反射动态运行，一种是另起进程，这里选择另起进程
	// js也使用这个
	// 指定目录下执行
	public static String[] run3(String cmd, Integer customSpiderBackId, File file) {
		String returnPrintContent = null;
		String returnErrorContent = null;
		String[] returnContent = new String[2];
		try {

			Process child = Runtime.getRuntime().exec(cmd, null, file);

			// 获取程序输入流
			OutputStream os = child.getOutputStream();
			// 正常输出流和异常输出流
			InputStream stdin = child.getInputStream();
			InputStream stderr = child.getErrorStream();
			// 启动线程

			ConsoleSimulator cs1 = new ConsoleSimulator(stdin, 0, customSpiderBackId);
			ConsoleSimulator cs2 = new ConsoleSimulator(stderr, 1, customSpiderBackId);
			Thread tIn = new Thread(cs1);
			Thread tErr = new Thread(cs2);
			tIn.start();
			tErr.start();
			int result = child.waitFor();
			tIn.join();
			tErr.join();
			returnPrintContent = cs1.getReturnPrintContent();
			returnErrorContent = cs2.getReturnErrorContent();
			// 处理中文乱码，需更改服务器端编码
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

	// 获取pid
	public static long getProcessPID(Process child) {
		long pid = -1;
		Field field = null;
		try {
			field = child.getClass().getDeclaredField(OtherConstant.OTHER_HANDLE);
			field.setAccessible(true);
			pid = Kernel32.INSTANCE.GetProcessId((Long) field.get(child));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pid;
	}
}
