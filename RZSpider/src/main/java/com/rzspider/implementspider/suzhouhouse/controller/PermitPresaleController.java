package com.rzspider.implementspider.suzhouhouse.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.rzspider.implementspider.commonutils.JavaCommonSpiderUtils;
import com.rzspider.implementspider.suzhouhouse.domain.permitpresale.PermitPresaleMessage;
import com.rzspider.implementspider.suzhouhouse.domain.permitpresale.PermitPresaleParams;
import com.rzspider.implementspider.suzhouhouse.service.PermitPresaleService;

public class PermitPresaleController {
	public JavaCommonSpiderUtils javaInternalSpiderUtils = new JavaCommonSpiderUtils();

	public void permitPresaleController(PermitPresaleParams permitPresaleParams, Integer taskInfoId)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		PermitPresaleService permitPresaleService = new PermitPresaleService();
		// 先获取条数,页数
		int[] pageNum = permitPresaleService.getPageAndNum2(permitPresaleParams);
		// 页数超过了则重置,根据模式选择
		if (Integer.parseInt(permitPresaleParams.getPageNumber()) > pageNum[1]) {
			permitPresaleParams.setPageNumber(String.valueOf(pageNum[1]));
		}
		if (pageNum[0] == 0) {
			// 没数据则返回
			return;
		}
		int num = Integer.parseInt(permitPresaleParams.getPageNumber());

		// 开始采集数据
		List<PermitPresaleMessage> ppmList;
		List<String> jsonDataList;
		for (int i = 0; i < num; i++) {
			ppmList = new ArrayList<PermitPresaleMessage>();
			permitPresaleService.getPermitPresaleDetail(permitPresaleParams, ppmList, i);
			// 每一页完事
			// 转换成字符串
			jsonDataList = new ArrayList<String>();
			// 使用通用方法，直接objectlist转jsonstringlist
			jsonDataList = JavaCommonSpiderUtils.objectListToJsonStringList(ppmList);
			// 插入正常数据
			javaInternalSpiderUtils.insertListDataToDB(taskInfoId, jsonDataList, 2);
			javaInternalSpiderUtils.insertListDataToDB(taskInfoId, jsonDataList, 1);
			// 完事清除
			ppmList.clear();
			jsonDataList.clear();
		}
	}

}
