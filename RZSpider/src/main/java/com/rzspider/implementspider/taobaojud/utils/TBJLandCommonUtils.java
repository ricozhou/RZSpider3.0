package com.rzspider.implementspider.taobaojud.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rzspider.implementspider.commonutils.JavaCommonSpiderUtils;
import com.rzspider.implementspider.taobaojud.domain.TBJLandMessageDetail;

//通用工具
public class TBJLandCommonUtils {

	// 从原始数据得到可用数据
	public static List<String> getUsefulJsonDataFromOriginalData(List<String> listJsonData) {
		List<String> sjsiList = new ArrayList<String>();
		TBJLandMessageDetail tbjlmd;
		JSONObject jsonObject;
		String urlId;
		String itemUrl;
		String saleStatus;
		String saleName;
		String initialPrice;
		String consultPrice;
		String sellOff;
		String startDate;
		String endDate;
		for (int i = 0; i < listJsonData.size(); i++) {
			tbjlmd = new TBJLandMessageDetail();
			// 分别取对应字段
			jsonObject = JSON.parseObject(listJsonData.get(i));
			urlId = jsonObject.getString("id");
			itemUrl = jsonObject.getString("itemUrl");
			saleStatus = jsonObject.getString("status").trim();
			saleName = jsonObject.getString("title");
			initialPrice = jsonObject.getString("initialPrice").trim();
			consultPrice = jsonObject.getString("consultPrice").trim();
			sellOff = jsonObject.getString("sellOff");
			startDate = jsonObject.getString("start").trim();
			endDate = jsonObject.getString("end").trim();

			// 添加到实体类中封装
			tbjlmd.setId(String.valueOf(i));
			tbjlmd.setUrlId(urlId);
			tbjlmd.setItemUrl("https:" + itemUrl);
			tbjlmd.setSaleStatus(turnSaleStatus(saleStatus));
			tbjlmd.setSaleName(saleName);
			tbjlmd.setInitialPrice(turnPrice(initialPrice));
			tbjlmd.setConsultPrice(turnPrice(consultPrice));

			tbjlmd.setStartDate(turnDate(startDate));
			tbjlmd.setEndDate(turnDate(endDate));
			// 加入到集合中并返回
			sjsiList.add(JavaCommonSpiderUtils.objectToJsonString(tbjlmd));
		}

		return sjsiList;
	}

	// 修改状态为汉字
	public static String turnSaleStatus(String saleStatus) {
		String newSaleStatus;
		if ("todo".equals(saleStatus)) {
			newSaleStatus = "即将开始";
		} else if ("done".equals(saleStatus)) {
			newSaleStatus = "已结束";
		} else if ("failure".equals(saleStatus)) {
			newSaleStatus = "已流拍";
		} else if ("doing".equals(saleStatus)) {
			newSaleStatus = "正在进行";
		} else if ("break".equals(saleStatus)) {
			newSaleStatus = "中止";
		} else if ("revocation".equals(saleStatus)) {
			newSaleStatus = "撤回";
		} else {
			newSaleStatus = "未知";
		}
		return newSaleStatus;
	}

	// 转换价格以万为单位
	public static String turnPrice(String price) {
		if (price == null) {
			return "";
		}
		if (price.indexOf(".") != -1) {
			price = price.substring(0, price.indexOf("."));
		}
		if (price.length() > 4) {
			price = price.substring(0, price.length() - 4) + "." + price.substring(price.length() - 4);
		}
		return price;
	}

	// 转换时间
	public static String turnDate(String datee) {
		if (datee == null) {
			return "";
		}
		long date = Long.parseLong(datee);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return df.format(date);
	}
}
