package com.rzspider.project.spider.customspider.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import org.springframework.web.socket.TextMessage;

import com.itextpdf.text.log.SysoCounter;
import com.rzspider.common.constant.CodingConstant;
import com.rzspider.common.constant.CommonSymbolicConstant;
import com.rzspider.common.utils.spring.SpringUtils;
import com.rzspider.project.spider.customspider.domain.Customspider;
import com.rzspider.project.spider.customspider.service.ICustomspiderService;
import com.rzspider.project.spider.spidertask.service.ISpidertaskService;

//另起线程截取程序流
public class ConsoleSimulator implements Runnable {
	public boolean isStop = false;
	public int INFO = 0;
	public int ERROR = 1;
	public InputStream is;
	public int type;
	public Integer customSpiderBackId;
	public StringBuilder returnPrintContent = new StringBuilder();
	public StringBuilder returnErrorContent = new StringBuilder();
	public StringBuilder returnCommonContent = new StringBuilder();
	ICustomspiderService customspiderService = (ICustomspiderService) SpringUtils.getBean(ICustomspiderService.class);
	Customspider customspider;

	public ConsoleSimulator(InputStream is, int type, Integer customSpiderBackId) {
		this.is = is;
		this.type = type;
		this.customSpiderBackId = customSpiderBackId;
	}

	public void run() {
		InputStreamReader isr = null;
		customspider = customspiderService.selectCustomspiderByCustomSpiderBackId(customSpiderBackId);
		try {
			// cmd执行，为了防止乱码，读取使用gbk编码
			if (customspider != null && customspider.getCustomSpiderType() == 2) {
				// js使用utf-8接收
				isr = new InputStreamReader(is, CodingConstant.CODING_UTF_8);
			} else {
				isr = new InputStreamReader(is, CodingConstant.CODING_GBK);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		BufferedReader reader = new BufferedReader(isr);
		String s;
		try {
			// 此地方执行python的时候阻塞，不知为何线程会停止
			while ((!isStop) && (s = reader.readLine()) != null) {
				if (s.length() != 0) {
					System.out.println(s);
					if (type == INFO) {
						returnPrintContent.append(s + CommonSymbolicConstant.LINEBREAK2);
					} else {
						returnErrorContent.append(s + CommonSymbolicConstant.LINEBREAK2);
					}
				}
			}
			isStop = true;
			if (customSpiderBackId != null) {
				// 没数据了就更改状态
				// 更新表数据,改变状态
				customspider = new Customspider();
				customspider.setCustomSpiderBackId(customSpiderBackId);
				customspider.setRunStatus(1);
				customspiderService.updateCustomspiderRunStatus(customspider);
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

}
