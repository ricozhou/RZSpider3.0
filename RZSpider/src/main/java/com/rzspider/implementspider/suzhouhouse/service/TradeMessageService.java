package com.rzspider.implementspider.suzhouhouse.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.rzspider.implementspider.suzhouhouse.domain.MegAnnoAndTradeModel.MessageAnnoModel;
import com.rzspider.implementspider.suzhouhouse.domain.MegAnnoAndTradeModel.TradeMessageModel;
import com.rzspider.implementspider.suzhouhouse.domain.MegAnnoAndTradeModel.TradeRightMessageModel;
import com.rzspider.implementspider.suzhouhouse.utils.HouseCommonUtils;

public class TradeMessageService {

	public TradeMessageModel getTradeMessageDetail()
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		TradeMessageModel tmm = new TradeMessageModel();
		String tradeMessageUrl = "http://www.szfcweb.com/szfcweb/DataSerach/XSFWINFO.aspx";
		// 模拟浏览器操作
		// 创建WebClient
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		// 关闭css代码功能
		webClient.waitForBackgroundJavaScript(30000);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setCssEnabled(false);
		// 如若有可能找不到文件js则加上这句代码
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		// 获取第一级网页html
		HtmlPage page = webClient.getPage(tradeMessageUrl);

		tmm.setTradeName("成交房屋信息【" + page.getElementById("ctl00_TitleContent_lbl_date").getTextContent().trim() + "】");
		tmm.setTradeNameTwo(page.getElementById("ctl00_MainContent_lbl_jjtit").getTextContent().trim());
		HtmlTable ht = (HtmlTable) page.getElementById("ctl00_MainContent_mytable");
		List<MessageAnnoModel> messList = new ArrayList<MessageAnnoModel>();
		MessageAnnoModel mam;
		for (int i = 1; i < ht.getRowCount(); i = i + 2) {
			mam = new MessageAnnoModel();
			mam.setHouseArea(ht.getRow(i).getCell(0).getTextContent().trim());
			mam.setTotalNum(ht.getRow(i).getCell(2).getTextContent().trim());
			mam.setTotalStructureArea(ht.getRow(i).getCell(3).getTextContent().trim());
			mam.setHouseTotalNum(ht.getRow(i + 1).getCell(1).getTextContent().trim());
			mam.setHouseTotalStructureArea(ht.getRow(i + 1).getCell(2).getTextContent().trim());
			messList.add(mam);
		}
		tmm.setMamList(messList);

		HtmlTable htt = (HtmlTable) page.getElementsByTagName("table").get(0);
		String trm = htt.getRow(1).getCell(0).getElementsByTagName("script").get(4).getTextContent().trim();
		// 转化为数组返回
		String[][] trmData = HouseCommonUtils.getStringDetail(trm);
		TradeRightMessageModel trmm;
		List<TradeRightMessageModel> trmList = new ArrayList<TradeRightMessageModel>();
		for (int i = 0; i < trmData[0].length; i++) {
			trmm = new TradeRightMessageModel();
			trmm.setTradeTime(trmData[0][i]);
			trmm.setTradeAveragePrice(trmData[1][i]);
			trmList.add(trmm);
		}
		tmm.setTrmmList(trmList);
		return tmm;
	}

}
