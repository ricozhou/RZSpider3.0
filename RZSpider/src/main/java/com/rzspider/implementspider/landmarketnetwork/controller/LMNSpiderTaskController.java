package com.rzspider.implementspider.landmarketnetwork.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.rzspider.implementspider.commonutils.JavaCommonSpiderUtils;
import com.rzspider.implementspider.landmarketnetwork.domain.LMNLandMessageDetail;
import com.rzspider.implementspider.landmarketnetwork.domain.LMNLandParams;
import com.rzspider.implementspider.landmarketnetwork.service.HNLMNSpiderTaskService;
import com.rzspider.implementspider.landmarketnetwork.service.JSLMNSpiderTaskService;
import com.rzspider.implementspider.landmarketnetwork.service.ZJLMNSpiderTaskService;
import com.rzspider.implementspider.landmarketnetwork.utils.LMNLandCommonUtils;
import com.rzspider.project.spider.spidermanage.service.ISpiderListService;
import com.rzspider.project.spider.spidertask.mainwork.domain.StartSpiderInfo;

public class LMNSpiderTaskController {
	public JSLMNSpiderTaskService jSlMNSpiderTaskService = new JSLMNSpiderTaskService();
	public ZJLMNSpiderTaskService zJLMNSpiderTaskService = new ZJLMNSpiderTaskService();
	public HNLMNSpiderTaskService hNLMNSpiderTaskService = new HNLMNSpiderTaskService();
	public LMNLandCommonUtils lmnlcu = new LMNLandCommonUtils();
	public JavaCommonSpiderUtils javaInternalSpiderUtils = new JavaCommonSpiderUtils();

	// 主控制
	public void LMNSpiderTaskController(LMNLandParams lMNLandParams, Integer taskInfoId)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		if (lMNLandParams.getWebsiteId() == 0) {
			// 江苏土地市场
			JSLMNSpiderTaskController(lMNLandParams, taskInfoId);
		} else if (lMNLandParams.getWebsiteId() == 1) {
			// 浙江土地市场
			ZJLMNSpiderTaskController(lMNLandParams, taskInfoId);
		} else if (lMNLandParams.getWebsiteId() == 2) {
			// 河南土地市场
			// 注意，虽然此网站表面上需要IE才能访问，但是当你找到土地相关信息后，发现与浙江是一模一样
			HNLMNSpiderTaskController(lMNLandParams, taskInfoId);
		}

	}

	// 正式执行任务
	// 只需要两个参数，一个是爬虫参数，一个是id用于插入数据
	// 江苏
	public void JSLMNSpiderTaskController(LMNLandParams lMNLandParams, Integer taskInfoId)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		if (lMNLandParams == null) {
			return;
		}
		// 获取条数,页数
		int[] pageNum = new int[2];
		// 江苏土地市场
		pageNum = jSlMNSpiderTaskService.getJSPageAndNum();
		if (pageNum == null) {
			return;
		}

		if (Integer.valueOf(lMNLandParams.getEndPage()) > pageNum[1]) {
			lMNLandParams.setEndPage(String.valueOf(pageNum[1]));
		}
		if (pageNum[0] == 0) {
			// 没数据则返回
			return;
		}
		int num = Integer.valueOf(lMNLandParams.getEndPage());
		int minNum = Integer.valueOf(lMNLandParams.getStartPage());
		if (minNum < 1) {
			minNum = 1;
		}

		// 获取数据
		List<LMNLandMessageDetail> lMNLandMessageDetailList;
		List<String> jsonDataList;
		// 按页数爬取
		for (int i = minNum - 1; i < num; i++) {
			// 所有的数据存到这里
			lMNLandMessageDetailList = new ArrayList<LMNLandMessageDetail>();
			// 一页一页返回数据，list集合
			// 江苏土地市场
			jSlMNSpiderTaskService.getJSLMNLandDetail(lMNLandParams, lMNLandMessageDetailList, i);
			// 转换成字符串
			jsonDataList = new ArrayList<String>();
			jsonDataList = LMNLandCommonUtils.turnObjectToJsonString(lMNLandMessageDetailList);
			// 存入数据库for循环一页一页插入
			// 先插入完整数据
			javaInternalSpiderUtils.insertListDataToDB(taskInfoId, jsonDataList, 2);
			// 筛选
			// 江苏土地市场
			lMNLandMessageDetailList = jSlMNSpiderTaskService.checkJSLandList(lMNLandMessageDetailList, lMNLandParams);
			// 转换成字符串
			jsonDataList = LMNLandCommonUtils.turnObjectToJsonString(lMNLandMessageDetailList);
			// 再插入筛选正常数据
			javaInternalSpiderUtils.insertListDataToDB(taskInfoId, jsonDataList, 1);
			// 完事清除
			lMNLandMessageDetailList.clear();
			jsonDataList.clear();
		}
	}

	// 正式执行任务
	// 只需要两个参数，一个是爬虫参数，一个是id用于插入数据
	// 浙江
	public void ZJLMNSpiderTaskController(LMNLandParams lMNLandParams, Integer taskInfoId)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		if (lMNLandParams == null) {
			return;
		}
		// 获取条数,页数
		int[] pageNum = new int[2];
		// 浙江土地市场
		pageNum[0] = 1;
		pageNum[1] = 800;
		if (Integer.valueOf(lMNLandParams.getEndPage()) > pageNum[1]) {
			lMNLandParams.setEndPage(String.valueOf(pageNum[1]));
		}
		int num = Integer.valueOf(lMNLandParams.getEndPage());
		int minNum = Integer.valueOf(lMNLandParams.getStartPage());
		if (minNum < 1) {
			minNum = 1;
		}

		// 获取数据
		List<LMNLandMessageDetail> lMNLandMessageDetailList;
		List<String> jsonDataList;
		// 按页数爬取
		for (int i = minNum - 1; i < num; i++) {
			// 所有的数据存到这里
			lMNLandMessageDetailList = new ArrayList<LMNLandMessageDetail>();
			// 一页一页返回数据，list集合
			// 浙江土地市场
			zJLMNSpiderTaskService.getZJLMNLandDetail(lMNLandParams, lMNLandMessageDetailList, i + 1);
			// 转换成字符串
			jsonDataList = new ArrayList<String>();
			jsonDataList = LMNLandCommonUtils.turnObjectToJsonString(lMNLandMessageDetailList);
			// 存入数据库for循环一页一页插入
			// 先插入完整数据
			javaInternalSpiderUtils.insertListDataToDB(taskInfoId, jsonDataList, 2);
			// 筛选
			// 浙江土地市场
			lMNLandMessageDetailList = zJLMNSpiderTaskService.checkZJLandList(lMNLandMessageDetailList, lMNLandParams);
			// 转换成字符串
			jsonDataList = LMNLandCommonUtils.turnObjectToJsonString(lMNLandMessageDetailList);
			// 再插入筛选正常数据
			javaInternalSpiderUtils.insertListDataToDB(taskInfoId, jsonDataList, 1);
			// 完事清除
			lMNLandMessageDetailList.clear();
			jsonDataList.clear();
		}
	}

	// 正式执行任务
	// 只需要两个参数，一个是爬虫参数，一个是id用于插入数据
	// 河南
	public void HNLMNSpiderTaskController(LMNLandParams lMNLandParams, Integer taskInfoId)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		if (lMNLandParams == null) {
			return;
		}
		// 获取条数,页数
		int[] pageNum = new int[2];
		// 浙江土地市场
		pageNum[0] = 1;
		pageNum[1] = 800;
		if (Integer.valueOf(lMNLandParams.getEndPage()) > pageNum[1]) {
			lMNLandParams.setEndPage(String.valueOf(pageNum[1]));
		}
		int num = Integer.valueOf(lMNLandParams.getEndPage());
		int minNum = Integer.valueOf(lMNLandParams.getStartPage());
		if (minNum < 1) {
			minNum = 1;
		}

		// 获取数据
		List<LMNLandMessageDetail> lMNLandMessageDetailList;
		List<String> jsonDataList;
		// 按页数爬取
		for (int i = minNum - 1; i < num; i++) {
			// 所有的数据存到这里
			lMNLandMessageDetailList = new ArrayList<LMNLandMessageDetail>();
			// 一页一页返回数据，list集合
			// 河南土地市场
			hNLMNSpiderTaskService.getHNLMNLandDetail(lMNLandParams, lMNLandMessageDetailList, i + 1);
			// 转换成字符串
			jsonDataList = new ArrayList<String>();
			jsonDataList = LMNLandCommonUtils.turnObjectToJsonString(lMNLandMessageDetailList);
			// 存入数据库for循环一页一页插入
			// 先插入完整数据
			javaInternalSpiderUtils.insertListDataToDB(taskInfoId, jsonDataList, 2);
			// 筛选
			// 河南土地市场
			lMNLandMessageDetailList = hNLMNSpiderTaskService.checkHNLandList(lMNLandMessageDetailList, lMNLandParams);
			// 转换成字符串
			jsonDataList = LMNLandCommonUtils.turnObjectToJsonString(lMNLandMessageDetailList);
			// 再插入筛选正常数据
			javaInternalSpiderUtils.insertListDataToDB(taskInfoId, jsonDataList, 1);
			// 完事清除
			lMNLandMessageDetailList.clear();
			jsonDataList.clear();
		}
	}
}
