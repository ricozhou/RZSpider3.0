package com.rzspider.project.spider.spidertask.mainwork;

import java.io.File;

import org.springframework.stereotype.Component;

import com.rzspider.common.constant.CommonSymbolicConstant;
import com.rzspider.common.constant.FileExtensionConstant;
import com.rzspider.common.constant.project.SpiderConstant;
import com.rzspider.common.utils.StringUtils;
import com.rzspider.common.utils.spring.SpringUtils;
import com.rzspider.framework.config.FilePathConfig;
import com.rzspider.project.spider.customspider.domain.Customspider;
import com.rzspider.project.spider.customspider.service.ICSFileService;
import com.rzspider.project.spider.customspider.service.ICustomspiderService;
import com.rzspider.project.spider.spidertask.mainwork.domain.StartSpiderInfo;
import com.rzspider.project.spider.spidertask.mainwork.service.ICustomspiderRunService;
import com.rzspider.project.spider.spidertask.service.ISpidertaskService;

/**
 * 定时任务调度
 * 
 * @author ricozhou
 */
@Component("customSpiderTask")
public class CustomSpiderTask {
	ICustomspiderRunService customspiderRunService = (ICustomspiderRunService) SpringUtils
			.getBean(ICustomspiderRunService.class);
	ICustomspiderService customspiderService = (ICustomspiderService) SpringUtils.getBean(ICustomspiderService.class);

	// 自定义爬虫无论是定时任务还是直接启动均通过这个地方启动
	// 自定义爬虫控制器，根据语言类型分类执行
	public void startCustomSpider(StartSpiderInfo startSpiderInfo) throws RuntimeException,Exception {
		// 通过后台id查询详细信息
		// 查询出详细信息
		Customspider customspider = customspiderService
				.selectCustomspiderByCustomSpiderBackId(startSpiderInfo.getSpiderBackId());
		customspider
				.setSpiderJavaPackagePrefix(StringUtils.getNotNullString(customspider.getSpiderJavaPackagePrefix()));
		startSpiderInfo.setCustomspider(customspider);
		String basePath = FilePathConfig.getCustomSpiderPath() + File.separator + customspider.getSpiderCodeTypeFolder()
				+ File.separator + customspider.getCustomSpiderBackId();
		String entryFilePath = CommonSymbolicConstant.EMPTY_STRING;
		if (startSpiderInfo.getCustomspider() == null) {
			// 不存在直接抛出异常
			throw new RuntimeException();
		}
		if (startSpiderInfo.getSpiderLanguageType() == 0) {
			// 0是java
			// 获取程序的入口，若为空，或者找不到编译文件则停止任务，，后续改进可再次编译
			// 判断入口文件是否存在
			entryFilePath = basePath + File.separator + SpiderConstant.SPIDER_FILE_JAVA_BIN_NAME + File.separator
					+ customspider.getSpiderJavaPackagePrefix() + CommonSymbolicConstant.UNDERLINE
					+ customspider.getCustomSpiderBackId() + File.separator + customspider.getEntryFileName()
					+ FileExtensionConstant.FILE_EXTENSION_POINT_CODEFILE_CLASS;
			File file = new File(entryFilePath);
			if (!file.exists()) {
				// 不存在直接抛出异常
				throw new RuntimeException();
			}
			// 正式运行
			customspiderRunService.runJavaCode(startSpiderInfo);
		} else if (startSpiderInfo.getSpiderLanguageType() == 1) {
			// 1是python,2是js
			// 判断入口文件是否存在
			entryFilePath = basePath + File.separator + customspider.getSpiderJavaPackagePrefix()
					+ CommonSymbolicConstant.UNDERLINE + customspider.getCustomSpiderBackId() + File.separator
					+ customspider.getEntryFileName();
			File file = new File(entryFilePath);
			if (!file.exists()) {
				// 不存在直接抛出异常
				throw new RuntimeException();
			}
			// 正式运行
			customspiderRunService.runPythonCode(startSpiderInfo);
		} else if (startSpiderInfo.getSpiderLanguageType() == 2) {
			// 1是python,2是js
			// 判断入口文件是否存在
			entryFilePath = basePath + File.separator + customspider.getSpiderJavaPackagePrefix()
					+ CommonSymbolicConstant.UNDERLINE + customspider.getCustomSpiderBackId() + File.separator
					+ customspider.getEntryFileName();
			File file = new File(entryFilePath);
			if (!file.exists()) {
				// 不存在直接抛出异常
				throw new RuntimeException();
			}
			// 正式运行
			customspiderRunService.runJSCode(startSpiderInfo);
		} else if (startSpiderInfo.getSpiderLanguageType() == 3) {
			// 3是jar
			// 判断入口文件是否存在
			entryFilePath = basePath + File.separator + customspider.getEntryFileName();
			File file = new File(entryFilePath);
			if (!file.exists()) {
				// 不存在直接抛出异常
				throw new RuntimeException();
			}
			// 正式运行
			customspiderRunService.runJAR(startSpiderInfo);
		}
	}

}
