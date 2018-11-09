package com.rzspider.project.spider.spidertask.mainwork.runutils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.itextpdf.text.log.SysoCounter;
import com.rzspider.common.constant.CodingConstant;
import com.rzspider.common.constant.CommonSymbolicConstant;
import com.rzspider.common.constant.RegularExpressionConstant;
import com.rzspider.common.utils.spring.SpringUtils;
import com.rzspider.project.spider.customspider.domain.Customspider;
import com.rzspider.project.spider.customspider.service.ICustomspiderService;
import com.rzspider.project.spider.spidertask.mainwork.domain.StartSpiderInfo;
import com.rzspider.project.spider.spidertask.service.ISpidertaskinfoService;

public class TaskConsoleSimulator implements Runnable {
	public boolean isStop = false;
	public boolean isForceStop = false;
	public int INFO = 0;
	public int ERROR = 1;
	public InputStream is;
	public int type;
	public Integer taskInfoId;
	public StringBuilder returnPrintContent = new StringBuilder();
	public StringBuilder returnErrorContent = new StringBuilder();
	public StringBuilder returnCommonContent = new StringBuilder();
	ISpidertaskinfoService spidertaskinfoService = (ISpidertaskinfoService) SpringUtils
			.getBean(ISpidertaskinfoService.class);

	public TaskConsoleSimulator(InputStream is, int type, Integer taskInfoId) {
		this.is = is;
		this.type = type;
		this.taskInfoId = taskInfoId;
	}

	public void run() {
		InputStreamReader isr = null;
		try {
			isr = new InputStreamReader(is, CodingConstant.CODING_UTF_8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		BufferedReader reader = new BufferedReader(isr);
		String s;
		try {
			// 此地方执行python的时候阻塞，不知为何线程会停止
			while ((!isStop) && (s = reader.readLine()) != null) {
				if (s.length() != 0) {
					if (type == INFO) {
						returnPrintContent.append(s + CommonSymbolicConstant.LINEBREAK2);
					} else {
						returnErrorContent.append(s + CommonSymbolicConstant.LINEBREAK2);
					}
				}
				System.out.println(returnPrintContent);
				System.out.println(returnErrorContent);
			}
			isStop = true;
			if (taskInfoId != null) {
				// 没数据了就更改状态
				// 更新表数据,改变状态
				// 最终执行
				Integer finishStatus = 2;
				if (isForceStop) {
					finishStatus = 1;
				} else if (this.isErrorProcess()) {
					// 3是爬虫出错
					finishStatus = 3;
				}
				StartSpiderInfo startSpiderInfo = new StartSpiderInfo();
				startSpiderInfo.setTaskInfoId(taskInfoId);
				spidertaskinfoService.finallyExection(startSpiderInfo, finishStatus);
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				isr.close();
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String getReturnPrintContent() {
		return returnPrintContent.toString();
	}

	public String getReturnErrorContent() {
		return returnErrorContent.toString();
	}

	public String getReturnCommonContent() {
		return returnCommonContent.toString();
	}

	public void stop() {
		isStop = true;
	}

	// 判断程序是否出错
	public boolean isErrorProcess() {
		String error = this.getReturnErrorContent();
		error = error.trim().replaceAll(CommonSymbolicConstant.SPACE, CommonSymbolicConstant.EMPTY_STRING).replaceAll(
				RegularExpressionConstant.REGULAR_EXPRESSION_ALLBLANK_CHARACTER, CommonSymbolicConstant.EMPTY_STRING);
		return !CommonSymbolicConstant.EMPTY_STRING.equals(error);
	}

}
