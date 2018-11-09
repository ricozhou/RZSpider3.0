package com.rzspider.implementspider.suzhouhouse.utils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class HouseCommonUtils {
	// 时间戳
	public static String turnHouseDate() {
		DateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
		String date = df.format(new Date());
		return date;
	}

	// 转化时间
	public static String turnHouseDate2(long s, long e) {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
		String date = df.format(e - s);
		return date;
	}

	// 拼接详细网址
	public static String getDetailUrl(String url2, String ranNum) {
		String url3 = url2.substring(url2.indexOf("showModalDialog('") + 17);
		String url4 = url3.substring(0, url3.indexOf("',"));
		String urlTwo = "http://www.szfcweb.com/szfcweb/" + ranNum + "/DataSerach/" + url4;
		return urlTwo;
	}

	// 拼接详细网址
	public static String getDetailUrl2(String url2, String ranNum) {
		String url3 = url2.substring(url2.indexOf("showModalDialog('") + 17);
		String url4 = url3.substring(0, url3.indexOf("',"));
		String urlTwo = "http://spf.szfcweb.com/szfcweb/" + ranNum + "/DataSerach/" + url4;
		return urlTwo;
	}

	public static String[][] getStringDetail(String trm) {
		String trm2 = trm.replaceAll(" ", "");
		String trmTimeDate = trm.substring(trm.indexOf("[\"") + 1, trm.indexOf("];"));
		String trmPriceData = trm2.substring(trm2.indexOf("data=[") + 6, trm2.lastIndexOf("];"));
		String[] td = trmTimeDate.replaceAll("\"", "").split(",");
		String[] tpd = trmPriceData.split(",");
		String[][] str = new String[2][td.length];
		str[0] = td;
		str[1] = tpd;
		return str;
	}

}
