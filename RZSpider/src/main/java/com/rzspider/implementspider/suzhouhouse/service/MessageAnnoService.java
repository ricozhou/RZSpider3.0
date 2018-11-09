package com.rzspider.implementspider.suzhouhouse.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.rzspider.implementspider.suzhouhouse.domain.MegAnnoAndTradeModel.MessageAnnoModel;

public class MessageAnnoService {

	public void getMessageAnnoDetail(List<MessageAnnoModel> messList)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		String meaaageAnnoaUrl = "http://spf.szfcweb.com/szfcweb/DataSerach/CanSaleHouseGroIndex.aspx";
		// 模拟浏览器操作
		// 创建WebClient
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		// 关闭css代码功能
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setCssEnabled(false);
		// 如若有可能找不到文件js则加上这句代码
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		// 获取第一级网页html
		HtmlPage page = webClient.getPage(meaaageAnnoaUrl);
		HtmlElement he = page.getHtmlElementById("ctl00_MainContent_Panel1");
		DomNodeList<HtmlElement> hts = he.getElementsByTagName("table");
		HtmlTable ht;
		MessageAnnoModel mam;
		String totalNum;
		String totalStructureArea;
		String houseTotalNum;
		String houseTotalStructureArea;
		for (int i = 0; i < hts.size(); i++) {
			mam = new MessageAnnoModel();
			ht = (HtmlTable) hts.get(i);
			// 区域
			mam.setHouseArea(ht.getRow(0).getCell(0).getTextContent().toString().trim().replaceAll(" ", ""));
			// 小计套数
			totalNum = ht.getRow(0).getCell(3).getTextContent().toString().trim();
			if (totalNum != null && !"".equals(totalNum) && totalNum.length() > 1) {
				totalNum = totalNum.substring(0, totalNum.length() - 1);
			}
			mam.setTotalNum(totalNum);
			// 小计建筑面积
			totalStructureArea = ht.getRow(0).getCell(5).getTextContent().toString().trim();
			if (totalStructureArea != null && !"".equals(totalStructureArea) && totalStructureArea.length() > 1) {
				totalStructureArea = totalStructureArea.substring(0, totalStructureArea.length() - 3);
			}
			mam.setTotalStructureArea(totalStructureArea);

			// 住宅套数
			houseTotalNum = ht.getRow(1).getCell(3).getTextContent().toString().trim();
			if (houseTotalNum != null && !"".equals(houseTotalNum) && houseTotalNum.length() > 1) {
				houseTotalNum = houseTotalNum.substring(0, houseTotalNum.length() - 1);
			}
			mam.setHouseTotalNum(houseTotalNum);
			// 住宅建筑面积
			houseTotalStructureArea = ht.getRow(1).getCell(5).getTextContent().toString().trim();
			if (houseTotalStructureArea != null && !"".equals(houseTotalStructureArea)
					&& houseTotalStructureArea.length() > 1) {
				houseTotalStructureArea = houseTotalStructureArea.substring(0, houseTotalStructureArea.length() - 3);
			}
			mam.setHouseTotalStructureArea(houseTotalStructureArea);
			messList.add(mam);
		}

	}

}
