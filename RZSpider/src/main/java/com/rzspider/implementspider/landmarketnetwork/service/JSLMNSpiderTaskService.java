package com.rzspider.implementspider.landmarketnetwork.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.WebResponseData;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.util.WebConnectionWrapper;
import com.rzspider.implementspider.landmarketnetwork.domain.LMNLandMessageDetail;
import com.rzspider.implementspider.landmarketnetwork.domain.LMNLandParams;
import com.rzspider.implementspider.landmarketnetwork.utils.LMNLandCommonUtils;

public class JSLMNSpiderTaskService {

	// 爬取页数
	// 江苏
	public int[] getJSPageAndNum() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		String urlOne = "http://www.landjs.com/web/gygg_list.aspx?gglx=1";
		// 模拟浏览器操作
		// 创建WebClient
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		// 关闭css代码功能
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setCssEnabled(false);
		// 如若有可能找不到文件js则加上这句代码
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);

		HtmlPage page = webClient.getPage(urlOne);

		int[] num = new int[2];
		HtmlElement he = page.getHtmlElementById("AspNetPager1");
		he = he.getElementsByTagName("div").get(0);
		String s = he.asText().replaceAll(" ", "");
		String ss = s.substring(s.indexOf("记录") + 3, s.indexOf("条"));
		String sss = s.substring(s.indexOf("/") + 1);
		num[0] = Integer.parseInt(ss);
		num[1] = Integer.parseInt(sss);
		return num;
	}

	// 获取详细信息
	// 江苏
	public void getJSLMNLandDetail(LMNLandParams lMNLandParams, List<LMNLandMessageDetail> lMNLandMessageDetailList,
			int num) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		String urlOne = "http://www.landjs.com/web/gygg_list.aspx?gglx=1";
		// 模拟浏览器操作
		// 创建WebClient
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		// 关闭css代码功能
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setCssEnabled(false);
		// 如若有可能找不到文件js则加上这句代码
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		// webClient.setJavaScriptTimeout(100000);
		// webClient.getOptions().setTimeout(30000);
		// 获取第一级网页html
		HtmlPage page = webClient.getPage(urlOne);
		// 但要区分需要第几页，由于直接点击select不起作用，所以只能循环点击
		for (int i = 0; i < num; i++) {
			page = page.getElementById("AspNetPager1").getElementsByTagName("div").get(1).getElementsByTagName("a")
					.get(3).click();
		}
		// 这下彻底获取目标页的html了
		// 传递page循环取数据
		// boolean isSuccess =
		// lMNSpiderTaskService.setLMNLandMeg(lMNLandMessageDetailList,
		// page);
		boolean isSuccess = setJSLMNLandMeg(lMNLandMessageDetailList, page);
	}

	// 取数据
	// 江苏
	public boolean setJSLMNLandMeg(List<LMNLandMessageDetail> lMNLandMessageDetailList, HtmlPage page)
			throws IndexOutOfBoundsException, IOException {
		HtmlTable ht = (HtmlTable) page.getElementById("GridView1");
		int n = ht.getRowCount();
		HtmlPage pg;
		String notice;
		String noticeLink;
		for (int i = 1; i < n; i++) {
			// 验证状态
			// if (!JSLandMainGUI.JSLandFlag) {
			// return false;
			// }
			// 获取第一个公告（里面可能包含好几个）
			pg = ht.getRow(i).getCell(0).getElementsByTagName("a").get(0).click();
			notice = ht.getRow(i).getCell(0).getElementsByTagName("a").get(0).getTextContent().trim().toString();
			noticeLink = pg.getBaseURI();
			HtmlTable ht2 = (HtmlTable) pg.getElementById("GridView1");
			// 此处出错2018年1月21日
			int n2 = ht2.getRowCount();
			// 循环取出里面的公告
			for (int j = 1; j < n2; j++) {
				// 点击进入详情页面
				HtmlPage pg2 = ht2.getRow(j).getCell(2).getElementsByTagName("a").get(0).click();
				// 获取详细信息存入实体类
				// lMNSpiderTaskService.getLMNLandDetail(lMNLandMessageDetailList,
				// pg2, notice, noticeLink);
				getJSLMNLandDetail(lMNLandMessageDetailList, pg2, notice, noticeLink);
			}
		}
		return true;
	}

	// 存入到对象中
	// 江苏
	public void getJSLMNLandDetail(List<LMNLandMessageDetail> lMNLandMessageDetailList, HtmlPage pg, String notice,
			String noticeLink) {
		LMNLandMessageDetail lmnmd = new LMNLandMessageDetail();
		// 公告名称
		lmnmd.setNoticeName(notice);
		// 公告链接
		lmnmd.setNoticeLink(noticeLink);
		// 公告地块编号
		lmnmd.setParcelNum(pg.getElementById("PARCEL_NO").getTextContent().trim().toString());
		// 公告编号
		lmnmd.setAfficheNum(pg.getElementById("AFFICHE_NO").getTextContent().trim().toString());
		// 挂牌起始时间
		lmnmd.setBidStartTime(pg.getElementById("BID_STARTTIME").getTextContent().trim().toString());
		// 挂牌截止时间
		lmnmd.setBidEndTime(pg.getElementById("BID_ENDTIME").getTextContent().trim().toString());
		// 报名开始时间
		lmnmd.setSignStartTime(pg.getElementById("SIGN_STARTTIME").getTextContent().trim().toString());
		// 报名截止时间
		lmnmd.setSignEndTime(pg.getElementById("SIGN_ENDTIME").getTextContent().trim().toString());
		// 保证金交纳开始时间
		lmnmd.setPaymentStartTime(pg.getElementById("PAYMENT_STARTTIME").getTextContent().trim().toString());
		// 保证金到账截止时间
		lmnmd.setPaymentEndTime(pg.getElementById("PAYMENT_ENDTIME").getTextContent().trim().toString());
		// 土地权属单位
		lmnmd.setRemiseUnit(pg.getElementById("REMISE_UNIT").getTextContent().trim().toString());
		// 交易方式
		lmnmd.setRemiseType(pg.getElementById("REMISE_TYPE").getTextContent().trim().toString());
		// 地块名称
		lmnmd.setParcelName(pg.getElementById("PARCEL_NAME").getTextContent().trim().toString());
		// 土地位置
		lmnmd.setLandPosition(pg.getElementById("LAND_POSITION").getTextContent().trim().toString());
		// 竞价规则
		lmnmd.setBidRules(pg.getElementById("BID_RULES").getTextContent().trim().toString());
		// 所属行政区
		lmnmd.setXzqDm(pg.getElementById("XZQ_DM").getTextContent().trim().toString()
				+ pg.getElementById("XZQ_DM0").getTextContent().trim().toString());
		// 出让面积（平方米）
		lmnmd.setRemiseArea(pg.getElementById("REMISE_AREA").getTextContent().trim().toString());
		// 建筑面积（平方米）
		lmnmd.setConstructArea(pg.getElementById("CONSTRUCT_AREA").getTextContent().trim().toString());
		// 规划用途
		lmnmd.setLandUse(pg.getElementById("LAND_USE").getTextContent().trim().toString());
		// 出让年限（年）
		lmnmd.setUseYear(pg.getElementById("USE_YEAR").getTextContent().trim().toString());
		// 竞买保证金（万元）
		lmnmd.setBail(pg.getElementById("BAIL").getTextContent().trim().toString());
		// 起始价
		lmnmd.setStartPrice(pg.getElementById("START_PRICE").getTextContent().trim().toString());
		// 竞价幅度
		lmnmd.setBidScope(pg.getElementById("BID_SCOPE").getTextContent().trim().toString());
		// 出价方式
		lmnmd.setCjfs(pg.getElementById("CJFS").getTextContent().trim().toString());
		// 出价单位
		lmnmd.setCjdw(pg.getElementById("CJDW").getTextContent().trim().toString());
		// 特定竞价方式
		lmnmd.setCrxzfs(pg.getElementById("CRXZFS").getTextContent().trim().toString());
		// 最高限价
		lmnmd.setZgxj(pg.getElementById("ZGXJ").getTextContent().trim().toString());
		// 容积率
		lmnmd.setRjl(pg.getElementById("rjl").getTextContent().trim().toString());
		// 绿化率
		lmnmd.setLhl(pg.getElementById("lhl").getTextContent().trim().toString());
		// 建筑密度
		lmnmd.setJzmd(pg.getElementById("jzmd").getTextContent().trim().toString());
		// 建筑限高（米）
		lmnmd.setJzxg(pg.getElementById("jzxg").getTextContent().trim().toString());
		// 办公与服务设施用地比例（%）
		lmnmd.setSharePercent(pg.getElementById("SHAREPERCENT").getTextContent().trim().toString());
		// 投资强度
		lmnmd.setLowestInvest(pg.getElementById("LOWESTINVEST").getTextContent().trim().toString());
		// 建设内容
		lmnmd.setBuildMatter(pg.getElementById("BUILDMATTER").getTextContent().trim().toString());
		lMNLandMessageDetailList.add(lmnmd);

		// 处理下载附件
	}

	// 筛选
	// 江苏
	public List<LMNLandMessageDetail> checkJSLandList(List<LMNLandMessageDetail> jsLandList,
			LMNLandParams lMNLandParams) {
		// 迭代器循环
		// 不能使用普通循环
		Iterator<LMNLandMessageDetail> it = jsLandList.iterator();
		LMNLandMessageDetail jslmd;
		String paymentEndTime;
		String startPrice;
		while (it.hasNext()) {
			jslmd = it.next();
			// 格式化时间为19700101
			paymentEndTime = LMNLandCommonUtils.formatPaymentTime(jslmd.getPaymentEndTime());
			// 格式化起始价格
			startPrice = LMNLandCommonUtils.formatStartPrice(jslmd.getStartPrice());
			if (Integer.parseInt(lMNLandParams.getStartDateTime().replaceAll("-", "")) > Integer
					.parseInt(paymentEndTime)
					|| Integer.parseInt(lMNLandParams.getEndDateTime().replaceAll("-", "")) < Integer
							.parseInt(paymentEndTime)
					|| Double.parseDouble(lMNLandParams.getStartPrice()) > Double.parseDouble(startPrice)
					|| Double.parseDouble(lMNLandParams.getEndPrice()) < Double.parseDouble(startPrice)
					|| Double.parseDouble(lMNLandParams.getStartArea()) > Double.parseDouble(jslmd.getRemiseArea())
					|| Double.parseDouble(lMNLandParams.getEndArea()) < Double.parseDouble(jslmd.getRemiseArea())) {
				it.remove();
				continue;
			}
			if (!"不限".equals(lMNLandParams.getLandUse())) {
				if (!jslmd.getLandUse().contains(lMNLandParams.getLandUse())) {
					it.remove();
				}
			}
		}
		return jsLandList;
	}
}
