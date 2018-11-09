package com.rzspider.implementspider.taobaojud.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.rzspider.framework.aspectj.lang.annotation.Log;
import com.rzspider.implementspider.taobaojud.domain.TBJLandParams;
import com.rzspider.implementspider.taobaojud.service.TBJSpiderTaskService;

public class TBJSpiderTaskController {

	// 主程序
	public void TBJSpiderTaskController(TBJLandParams tBJLandParams, Integer taskInfoId)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		if (tBJLandParams == null) {
			return;
		}
		TBJSpiderTaskService tBJSpiderTaskService = new TBJSpiderTaskService();

		// 处理参数，页数
		int startPage = tBJLandParams.getStartPage();
		int endPage = tBJLandParams.getEndPage();
		if (startPage > endPage) {
			// 出现结束页比开始页大的则只爬取起始页
			endPage = startPage;
		}
		// 通过解析网站获取，时间较长
		String url = "";
		url = tBJSpiderTaskService.getOneUrl(tBJLandParams);
		String finishUrl;
		// 循环爬取
		for (int i = startPage; i < endPage; i++) {
			finishUrl = url + "&page=" + i;
			tBJSpiderTaskService.getDataFromTaoBaoToDB(finishUrl, taskInfoId);
		}
	}
}
