package com.rzspider.project.spider.spidertask.mainwork.service;

import java.io.IOException;

import com.rzspider.project.spider.spidertask.mainwork.domain.StartSpiderInfo;

public interface ICustomspiderRunService {
	// 运行java代码
	public boolean runJavaCode(StartSpiderInfo startSpiderInfo) throws Exception;

	public boolean runPythonCode(StartSpiderInfo startSpiderInfo) throws IOException;

	// 运行js代码
	public boolean runJSCode(StartSpiderInfo startSpiderInfo) throws IOException;

	boolean stopTaskCSProcess(StartSpiderInfo startSpiderInfo);

	boolean runJAR(StartSpiderInfo startSpiderInfo) throws IOException;
}
