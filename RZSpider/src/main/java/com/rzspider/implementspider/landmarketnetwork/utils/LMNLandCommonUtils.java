package com.rzspider.implementspider.landmarketnetwork.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.rzspider.common.utils.spring.SpringUtils;
import com.rzspider.implementspider.landmarketnetwork.domain.LMNLandMessageDetail;
import com.rzspider.implementspider.landmarketnetwork.domain.LMNLandParams;
import com.rzspider.project.common.spiderdata.domain.Spiderdata;
import com.rzspider.project.common.spiderdata.service.ISpiderdataService;
import com.rzspider.project.common.spiderdata.service.SpiderdataServiceImpl;

public class LMNLandCommonUtils {

	// 对象转json
	public static List<String> turnObjectToJsonString(List<LMNLandMessageDetail> lMNLandMessageDetailList)
			throws JsonProcessingException {
		List<String> jsonDataList = new ArrayList<String>();
		if (lMNLandMessageDetailList != null && lMNLandMessageDetailList.size() > 1) {
			// jackson核心处理类
			ObjectMapper mapper = new ObjectMapper();
			for (LMNLandMessageDetail smnmd : lMNLandMessageDetailList) {
				// LMNLandMessageDetail类转JSON,加入集合
				jsonDataList.add(mapper.writeValueAsString(smnmd));
			}
		}
		return jsonDataList;
	}

	// 格式化保证金到账时间
	public static String formatPaymentTime(String paymentEndTime) {
		String str = paymentEndTime.substring(0, paymentEndTime.indexOf(" "));
		String[] md = str.split("/");
		if (md[1].length() == 1) {
			md[1] = "0" + md[1];
		}
		if (md[2].length() == 1) {
			md[2] = "0" + md[2];
		}
		String formatDate = md[0] + md[1] + md[2];
		return formatDate;
	}

	// 格式化保证金到账时间2
	public static String formatPaymentTime2(String paymentEndTime) {
		String year = paymentEndTime.substring(0, 4);
		String month = paymentEndTime.substring(5, 7);
		String date = paymentEndTime.substring(8, 10);
		String formatDate = year + month + date;
		return formatDate;
	}

	// 格式化起始价格
	public static String formatStartPrice(String startPrice) {
		if (!startPrice.contains("万")) {
			return startPrice;
		}
		String formatPrice = startPrice.substring(0, startPrice.indexOf("万"));
		return formatPrice;
	}

	// 格式化起始价格2
	public static String formatStartPrice2(String startPrice, String remiseArea) {
		String formatPrice = "";
		if (startPrice.contains("万") && !startPrice.contains("/")) {
			formatPrice = startPrice.substring(0, startPrice.indexOf("万"));
			formatPrice = formatPrice.replaceAll(",", "");
			formatPrice = formatPrice.replaceAll("，", "");
		} else if (startPrice.contains("元/")) {
			formatPrice = startPrice.substring(0, startPrice.indexOf("元/"));
			formatPrice = formatPrice.replaceAll(",", "");
			formatPrice = formatPrice.replaceAll("，", "");
			int price = (int) (Double.parseDouble(formatPrice) * Double.parseDouble(remiseArea) / 10000);
			formatPrice = String.valueOf(price);
		}
		return formatPrice;
	}

	// 格式化面积2
	public static String formatRemiseArea2(String remiseArea) {
		if (!remiseArea.contains("平")) {
			return remiseArea;
		}
		String formatRemiseArea = remiseArea.substring(0, remiseArea.indexOf("平"));
		formatRemiseArea.replaceAll(",", "");
		formatRemiseArea.replaceAll("，", "");
		return formatRemiseArea;
	}
}
