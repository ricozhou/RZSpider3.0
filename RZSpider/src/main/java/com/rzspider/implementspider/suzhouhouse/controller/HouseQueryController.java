package com.rzspider.implementspider.suzhouhouse.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.rzspider.implementspider.commonutils.JavaCommonSpiderUtils;
import com.rzspider.implementspider.suzhouhouse.domain.housequery.HouseQueryMessage;
import com.rzspider.implementspider.suzhouhouse.domain.housequery.HouseQueryParams;
import com.rzspider.implementspider.suzhouhouse.domain.permitpresale.PermitPresaleMessage;
import com.rzspider.implementspider.suzhouhouse.service.HouseQueryService;

public class HouseQueryController {
	public JavaCommonSpiderUtils javaInternalSpiderUtils = new JavaCommonSpiderUtils();

	// 苏州房产网交易系统（可售楼盘展示）
	public void houseQueryController(HouseQueryParams houseQueryParams, Integer taskInfoId)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		HouseQueryService houseQueryService = new HouseQueryService();
		// 先获取条数,页数
		int[] pageNum = houseQueryService.getPageAndNum(houseQueryParams);
		// 页数超过了则重置,根据模式选择
		if (Integer.parseInt(houseQueryParams.getPageNumber()) > pageNum[1]) {
			houseQueryParams.setPageNumber(String.valueOf(pageNum[1]));
		}
		if (pageNum[0] == 0) {
			// 没数据则返回
			return;
		}
		int num = Integer.parseInt(houseQueryParams.getPageNumber());

		// 开始采集数据
		List<HouseQueryMessage> ppmList;
		List<String> jsonDataList;
		for (int i = 0; i < num; i++) {
			ppmList = new ArrayList<HouseQueryMessage>();
			houseQueryService.getHouseQueryDetail(houseQueryParams, ppmList, i);
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
