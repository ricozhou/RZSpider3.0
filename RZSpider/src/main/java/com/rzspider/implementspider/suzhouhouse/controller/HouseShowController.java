package com.rzspider.implementspider.suzhouhouse.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.rzspider.implementspider.commonutils.JavaCommonSpiderUtils;
import com.rzspider.implementspider.suzhouhouse.domain.houseshow.HouseShowMessage;
import com.rzspider.implementspider.suzhouhouse.domain.houseshow.HouseShowParams;
import com.rzspider.implementspider.suzhouhouse.service.HouseShowService;

public class HouseShowController {
	public JavaCommonSpiderUtils javaInternalSpiderUtils = new JavaCommonSpiderUtils();

	// 苏州房产网交易系统（可售楼盘展示）
	public void houseShowController(HouseShowParams houseShowParams, Integer taskInfoId)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		HouseShowService houseShowService = new HouseShowService();
		// 模式一模式二都一样
		// 先获取条数,页数
		int[] pageNum = houseShowService.getPageAndNum(houseShowParams);
		// 页数超过了则重置,根据模式选择
		if (Integer.parseInt(houseShowParams.getPageNumber()) > pageNum[1]) {
			houseShowParams.setPageNumber(String.valueOf(pageNum[1]));
		}
		if (pageNum[0] == 0) {
			// 没数据则返回
			return;
		}
		int num = Integer.parseInt(houseShowParams.getPageNumber());
		// 存入数据
		// HouseShowMessage hsm=new HouseShowMessage();
		List<HouseShowMessage> houseList;
		List<String> jsonDataList;
		String[] area = { "吴中区", "相城区", "高新区", "姑苏区", "吴江区" };
		for (int i = 0; i < num; i++) {
			houseList = new ArrayList<HouseShowMessage>();
			houseShowService.getHouseShowDetail(houseShowParams, houseList, i);
			// 如果爬取全部，则需要更改区域
			if (houseShowParams.isAllData) {
				for (int k = 0; k < area.length; k++) {
					houseShowParams.setProjectArea(area[k]);
					houseShowService.getHouseShowDetail(houseShowParams, houseList, i);
				}
			}

			// 每一页完事
			// 转换成字符串
			jsonDataList = new ArrayList<String>();
			// 使用通用方法，直接objectlist转jsonstringlist
			jsonDataList = JavaCommonSpiderUtils.objectListToJsonStringList(houseList);
			// 插入正常数据
			javaInternalSpiderUtils.insertListDataToDB(taskInfoId, jsonDataList, 2);
			javaInternalSpiderUtils.insertListDataToDB(taskInfoId, jsonDataList, 1);
			// 完事清除
			houseList.clear();
			jsonDataList.clear();
		}

	}

}
