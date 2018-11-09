package com.rzspider.implementspider.suzhouhouse.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.rzspider.implementspider.commonutils.JavaCommonSpiderUtils;
import com.rzspider.implementspider.suzhouhouse.domain.MegAnnoAndTradeModel.MessageAnnoModel;
import com.rzspider.implementspider.suzhouhouse.domain.MegAnnoAndTradeModel.TradeMessageModel;
import com.rzspider.implementspider.suzhouhouse.service.MessageAnnoService;
import com.rzspider.implementspider.suzhouhouse.service.TradeMessageService;

public class MegAnnoAndTradeMegController {
	public JavaCommonSpiderUtils javaInternalSpiderUtils = new JavaCommonSpiderUtils();

	public void megAnnoController(Integer taskInfoId)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		MessageAnnoService messageAnnoService = new MessageAnnoService();
		// 信息列表存储
		List<MessageAnnoModel> messList = new ArrayList<MessageAnnoModel>();
		messageAnnoService.getMessageAnnoDetail(messList);
		// 使用通用方法，直接objectlist转jsonstringlist
		List<String> jsonDataList = JavaCommonSpiderUtils.objectListToJsonStringList(messList);
		// 插入正常数据
		javaInternalSpiderUtils.insertListDataToDB(taskInfoId, jsonDataList, 2);
		javaInternalSpiderUtils.insertListDataToDB(taskInfoId, jsonDataList, 1);
	}

	public void tradeMegController(Integer taskInfoId) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		TradeMessageService tradeMessageService = new TradeMessageService();
		// 信息列表存储
		TradeMessageModel tmm =tradeMessageService.getTradeMessageDetail();
		List<TradeMessageModel> messList = new ArrayList<TradeMessageModel>();
		messList.add(tmm);
		// 使用通用方法，直接objectlist转jsonstringlist
		List<String> jsonDataList = JavaCommonSpiderUtils.objectListToJsonStringList(messList);
		// 插入正常数据
		javaInternalSpiderUtils.insertListDataToDB(taskInfoId, jsonDataList, 2);
		javaInternalSpiderUtils.insertListDataToDB(taskInfoId, jsonDataList, 1);

	}

}
