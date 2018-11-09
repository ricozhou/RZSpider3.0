package com.rzspider.implementspider.taobaojud.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.rzspider.implementspider.commonutils.JavaCommonSpiderUtils;
import com.rzspider.implementspider.taobaojud.domain.TBJLandParams;
import com.rzspider.implementspider.taobaojud.utils.TBJLandCommonUtils;

//主逻辑
public class TBJSpiderTaskService {
	public JavaCommonSpiderUtils javaInternalSpiderUtils = new JavaCommonSpiderUtils();

	// 开始解析
	public void getDataFromTaoBaoToDB(String url, Integer taskInfoId) {
		String jsonData = startAnalyzeUrl(url);
		if (jsonData == null || "".equals(jsonData)) {
			return;
		}
		// 将数据解析出可用的json存入数据库
		// 插入
		List<String> listJsonData = JavaCommonSpiderUtils.jsonStringToJsonStringList(jsonData);
		List<String> newListJsonData = TBJLandCommonUtils.getUsefulJsonDataFromOriginalData(listJsonData);
		// 插入全部数据
		javaInternalSpiderUtils.insertListDataToDB(taskInfoId, newListJsonData, 2);

		// 插入目标数据
		javaInternalSpiderUtils.insertListDataToDB(taskInfoId, newListJsonData, 1);

	}

	// 开始解析网址
	// 返回有用数据
	public static String startAnalyzeUrl(String url) {
		try {
			// 模拟浏览器操作
			// 创建WebClient
			WebClient webClient = new WebClient(BrowserVersion.CHROME);
			// 关闭css代码功能
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			webClient.getOptions().setCssEnabled(false);
			// 如若有可能找不到文件js则加上这句代码
			webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
			// 获取网页html
			HtmlPage page = webClient.getPage(url);
			// 淘宝懒加载，数据获取有难度，ajax数据，从script中获取data
			String originalData = page.getElementById("sf-item-list-data").asXml().toString();
			// 确保数据不为空
			if (originalData == null || originalData.substring(originalData.indexOf(":[{") + 2).length() < 5) {
				return "";
			}
			// 截取有用字符串
			// 可以直接读取json
			String usefulData = originalData.substring(originalData.indexOf(":[{") + 1, originalData.indexOf("}]") + 2);
			// 关闭
			webClient.close();
			return usefulData;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 传递所有参数获取URL
	public String getOneUrl(TBJLandParams tBJLandParams)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		String baseUrl = "https://sf.taobao.com/list/0.htm?spm=a213w.7398504.filter.1.RxqQ5o&auction_start_seg=-1";
		// 模拟浏览器操作
		// 创建WebClient
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		// 关闭css代码功能
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setCssEnabled(false);
		// 如若有可能找不到文件js则加上这句代码
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		// webClient.getOptions().setUseInsecureSSL(true);
		// // 如若有可能找不到文件js则加上这句代码
		// webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		// webClient.getOptions().setActiveXNative(false);
		// webClient.getOptions().setJavaScriptEnabled(true);
		// webClient.setAjaxController(new
		// NicelyResynchronizingAjaxController());
		// 获取网页html
		HtmlPage page = webClient.getPage(baseUrl);
		// 获取初始页面所有的a标签
		List<DomElement> he = page.getElementsByTagName("a");
		String url = null;
		// 循环找到标的物类型标签
		for (int i = 38; i < 50; i++) {
			if (he.get(i).asText().toString().trim().contains(tBJLandParams.getTargetType())) {
				url = he.get(i).click().getUrl().toString();
			}
		}
		if (url == null) {
			return "";
		}
		// 获取标的物状态标签
		page = webClient.getPage(url);
		he = page.getElementsByTagName("a");
		for (int i = 83; i < 89; i++) {
			if (he.get(i).asText().toString().trim().contains(tBJLandParams.getAuctionStatus())) {
				url = he.get(i).click().getUrl().toString();
			}
		}
		if (url == null) {
			return "";
		}
		// 获取省市信息
		page = webClient.getPage(url);
		he = page.getElementsByTagName("a");
		if ("不限".equals(tBJLandParams.getTargetAddressProvince())) {
			url = he.get(50).click().getUrl().toString();
			return url;
		} else {
			// 循环标签
			for (int i = 51; i < 83; i++) {
				// 判断是否有所选的省级
				if (he.get(i).asText().toString().substring(0, tBJLandParams.getTargetAddressProvince().length()).trim()
						.contains(tBJLandParams.getTargetAddressProvince())) {
					// 获取省级网址
					url = he.get(i).click().getUrl().toString();

					// 否则接着解析省级网址，获取省级a标签
					page = webClient.getPage(url);
					he = page.getElementsByTagName("a");
					// 循环省级a标签
					for (int j = 51; i < he.size() - 50; j++) {
						if (he.get(j).asText().toString().contains(tBJLandParams.getTargetAddressCity())) {
							// 找到地级市对应的网址
							url = he.get(j).click().getUrl().toString();
							// 返回地级市网址
							return url;
						}
					}
				}
			}
		}
		webClient.close();
		// 不存在则返回空字符串
		return "";
	}
}
