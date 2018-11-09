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

public class ZJLMNSpiderTaskService {

	// 浙江
	public void getZJLMNLandDetail(LMNLandParams lMNLandParams, List<LMNLandMessageDetail> lMNLandMessageDetailList,
			int num) throws IndexOutOfBoundsException, IOException {
		String urlOne = "http://land.zjgtjy.cn/GTJY_ZJ/go_home";
		// 模拟浏览器操作
		// 创建WebClient
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		// 关闭css代码功能
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setCssEnabled(false);
		// 如若有可能找不到文件js则加上这句代码
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		// 获取第一级网页html
		HtmlPage page = webClient.getPage(urlOne);
		page = page.getElementById("sy_main").getElementsByTagName("a").get(1).click();
		// 获取第一页的六个土地代码
		List<String> landCodeList = getZJLandCode(num);
		// 遍历
		for (String landCode : landCodeList) {
			// 执行对应的js
			ScriptResult result = page.executeJavaScript("goRes(" + landCode + ",01)");
			// 获取执行后获取的page
			HtmlPage newPage = (HtmlPage) result.getNewPage();
			// 找到对应的基本信息
			DomNodeList<DomElement> he = newPage.getElementsByTagName("td");
			boolean isSuccess;
			for (DomElement temp : he) {
				if (temp.getAttribute("class").equals("td_line2")) {
					// 此时已获取第一条信息
					HtmlTable ht = (HtmlTable) temp.getElementsByTagName("table").get(0);

					// 传递table循环取数据
					isSuccess = setZJLandMeg(lMNLandMessageDetailList, ht, landCode);
				}
			}
		}

	}

	// 获取关键码,相当于每个公告有个代码
	// 浙江
	private List<String> getZJLandCode(int pageNum)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		List<String> landCodeList = new ArrayList<String>();
		String url = "http://land.zjgtjy.cn/GTJY_ZJ/deala_js_action?resourcelb=01&dealtype=&JYLB=&JYFS=&JYZT=&RESOURCENO=&RESOURCEMC=&endDate=&ZYWZ=&zylb=01&currentPage="
				+ pageNum;
		// 模拟浏览器操作
		// 创建WebClient
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		// 关闭css代码功能
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setCssEnabled(false);
		// 如若有可能找不到文件js则加上这句代码
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		// 获取网页html
		HtmlPage page = webClient.getPage(url);
		DomNodeList<DomElement> he = page.getElementsByTagName("input");
		String s;
		String ss;
		for (DomElement temp : he) {
			if (temp.getAttribute("class").equals("list1_btn1")) {
				s = temp.asXml().trim().toString().replaceAll(" ", "");
				ss = s.substring(s.indexOf("goRes('") + 7, s.indexOf("goRes('") + 12);
				landCodeList.add(ss);
				// 5200
			}
		}
		return landCodeList;
	}

	// 取数据
	// 浙江
	private boolean setZJLandMeg(List<LMNLandMessageDetail> lMNLandMessageDetailList, HtmlTable ht, String landCode)
			throws IndexOutOfBoundsException, IOException {
		// 创建实体类对象
		LMNLandMessageDetail lMNLandMessageDetail = new LMNLandMessageDetail();
		int rowNum = ht.getRowCount();

		// 添加获取的数据到实体类中
		// 编号

		// 新方法
		// 遍历table下所有的tr，td，然后判断，写入，以防止遗漏
		// 遍历行
		HtmlTableRow row;
		String label = "";
		String content = "";
		for (int i = 0; i < rowNum; i++) {
			// 遍历列
			row = ht.getRow(i);
			for (int j = 0; j < row.getCells().size(); j = j + 2) {
				// 得到列标签
				label = row.getCell(j).getTextContent().trim().toString();
				// 得到数据
				content = row.getCell(j + 1).getTextContent().trim().toString();
				if ("竞买人条件".equals(label)) {
					if ("[]".equals(row.getCell(j + 1).getElementsByTagName("div").toString().trim())) {
						lMNLandMessageDetail.setBidCondition("");
					} else {
						lMNLandMessageDetail.setBidCondition(row.getCell(j + 1).getElementsByTagName("div").get(0)
								.getTextContent().trim().toString());
					}
				}
				setZJContentByLabel(label, content, lMNLandMessageDetail);
			}
			row = null;
			label = "";
			content = "";
		}

		// 获取其他内容需要另想办法
		// http://land.zjgtjy.cn/GTJY_ZJ/crgg_info?rid=5185&JYFS=01
		// 尚未找到好办法
		getZJOtherLandDetail(lMNLandMessageDetail, landCode);
		lMNLandMessageDetailList.add(lMNLandMessageDetail);
		// 处理下载附件

		return true;
	}

	// 填充信息
	// 浙江
	private void setZJContentByLabel(String label, String content, LMNLandMessageDetail lMNLandMessageDetail) {
		if ("地块编号".equals(label)) {
			lMNLandMessageDetail.setParcelNum(content);
		} else if ("挂牌起始时间".equals(label) || "拍卖开始时间".equals(label)) {
			lMNLandMessageDetail.setBidStartTime(content);
		} else if ("挂牌截止时间".equals(label) || "限时竞价开始时间".equals(label)) {
			lMNLandMessageDetail.setBidEndTime(content);
		} else if ("报名开始时间".equals(label)) {
			lMNLandMessageDetail.setSignStartTime(content);
		} else if ("保证金到账截止时间".equals(label)) {
			lMNLandMessageDetail.setPaymentEndTime(content.replaceAll(" ", ""));
		} else if ("报名截止时间".equals(label)) {
			lMNLandMessageDetail.setSignEndTime(content);
		} else if ("是否有底价".equals(label)) {
			lMNLandMessageDetail.setYnBasePrice(content);
		} else if ("地块名称".equals(label)) {
			lMNLandMessageDetail.setParcelName(content);
		} else if ("土地位置".equals(label)) {
			lMNLandMessageDetail.setLandPosition(content);
		} else if ("土地用途".equals(label)) {
			lMNLandMessageDetail.setLandUse(content.replaceAll(" ", ""));
		} else if ("容积率".equals(label)) {
			lMNLandMessageDetail.setRjl(content);
		} else if ("所属行政区".equals(label)) {
			lMNLandMessageDetail.setXzqDm(content);
		} else if ("出让面积".equals(label)) {
			lMNLandMessageDetail.setRemiseArea(content.replaceAll(" ", ""));
		} else if ("出让年限".equals(label)) {
			lMNLandMessageDetail.setUseYear(content);
		} else if ("起始价".equals(label)) {
			lMNLandMessageDetail.setStartPrice(content.replaceAll(" ", ""));
		} else if ("竞买保证金".equals(label)) {
			lMNLandMessageDetail.setBail(content);
		} else if ("竞价增价幅度".equals(label)) {
			lMNLandMessageDetail.setBidScope(content);
		} else if ("固定资产投资强度".equals(label)) {
			lMNLandMessageDetail.setLowestInvest(content);
		} else if ("联系人".equals(label)) {
			lMNLandMessageDetail.setLinkMan(content);
		} else if ("联系人地址".equals(label)) {
			lMNLandMessageDetail.setLinkManAdd(content);
		} else if ("联系人电话".equals(label)) {
			lMNLandMessageDetail.setLinkManTel(content);
		} else if ("最高限价".equals(label)) {
			lMNLandMessageDetail.setMaxLimPrice(content);
		} else if ("配套用房起始面积".equals(label)) {
			lMNLandMessageDetail.setPtyfStartArea(content);
		} else if ("投报幅度".equals(label)) {
			lMNLandMessageDetail.setTbfuPrice(content);
		} else if ("".equals(label)) {
			lMNLandMessageDetail.setOtherMeg(content);
		}

	}

	// 获取其他信息
	// 浙江
	private void getZJOtherLandDetail(LMNLandMessageDetail lMNLandMessageDetail, String landCode)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		String url = "http://land.zjgtjy.cn/GTJY_ZJ/crgg_info?rid=" + landCode + "&JYFS=01";
		// 模拟浏览器操作
		// 创建WebClient
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		// 关闭css代码功能
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setCssEnabled(false);
		// 如若有可能找不到文件js则加上这句代码
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		// 获取网页html
		HtmlPage page = webClient.getPage(url);

		// 公告名称
		lMNLandMessageDetail.setNoticeName("");
		// 公告链接
		lMNLandMessageDetail.setNoticeLink(url);
		// 公告编号
		lMNLandMessageDetail.setAfficheNum("");
		// 保证金交纳开始时间
		// jslmd.setPaymentStartTime();
		// 土地权属单位
		// jslmd.setRemiseUnit();

		// 竞价规则
		// jslmd.setBidRules();
		// 建筑面积（平方米）
		lMNLandMessageDetail.setConstructArea("");
		// 出价方式
		// jslmd.setCjfs();
		// 出价单位
		// jslmd.setCjdw();
		// 特定竞价方式
		// jslmd.setCrxzfs();
		// 最高限价
		// jslmd.setZgxj();
		// 绿化率
		lMNLandMessageDetail.setLhl("");
		// 建筑密度
		lMNLandMessageDetail.setJzmd("");
		// 建筑限高（米）
		lMNLandMessageDetail.setJzxg("");
		// 办公与服务设施用地比例（%）
		// jslmd.setSharePercent();
		// 建设内容
		// jslmd.setBuildMatter();

	}

	// 筛选
	// 浙江
	public List<LMNLandMessageDetail> checkZJLandList(List<LMNLandMessageDetail> jsLandList,
			LMNLandParams lMNLandParams) {
		// try {
		// 迭代器循环
		// 不能使用普通循环
		Iterator<LMNLandMessageDetail> it = jsLandList.iterator();
		String paymentEndTime;
		String remiseArea;
		String startPrice;
		while (it.hasNext()) {
			LMNLandMessageDetail jslmd = it.next();
			// 格式化时间为19700101
			paymentEndTime = LMNLandCommonUtils.formatPaymentTime2(jslmd.getPaymentEndTime());
			// 格式化面积
			remiseArea = LMNLandCommonUtils.formatRemiseArea2(jslmd.getRemiseArea());
			// 格式化起始价格
			startPrice = LMNLandCommonUtils.formatStartPrice2(jslmd.getStartPrice(), remiseArea);

			if (Integer.parseInt(lMNLandParams.getStartDateTime().replaceAll("-", "")) > Integer
					.parseInt(paymentEndTime)
					|| Integer.parseInt(lMNLandParams.getEndDateTime().replaceAll("-", "")) < Integer
							.parseInt(paymentEndTime)
					|| Double.parseDouble(lMNLandParams.getStartPrice()) > Double.parseDouble(startPrice)
					|| Double.parseDouble(lMNLandParams.getEndPrice()) < Double.parseDouble(startPrice)
					|| Double.parseDouble(lMNLandParams.getStartArea()) > Double.parseDouble(remiseArea)
					|| Double.parseDouble(lMNLandParams.getEndArea()) < Double.parseDouble(remiseArea)) {
				it.remove();
				continue;
			}
			if (!"不限".equals(lMNLandParams.getLandUse())) {
				if ("商业".equals(lMNLandParams.getLandUse())) {
					if (!jslmd.getLandUse().contains("业")) {
						it.remove();
					} else if (jslmd.getLandUse().contains("工业") && !jslmd.getLandUse().contains("商业")) {
						it.remove();
					}
				} else if ("商住".equals(lMNLandParams.getLandUse())) {
					if (!jslmd.getLandUse().contains("住")) {
						it.remove();
					}
				} else if ("住宅".equals(lMNLandParams.getLandUse())) {
					if (!jslmd.getLandUse().contains("住")) {
						it.remove();
					}
				} else if ("其他".equals(lMNLandParams.getLandUse())) {
					if (jslmd.getLandUse().contains("住")
							|| (jslmd.getLandUse().contains("业") && !jslmd.getLandUse().contains("工业"))) {
						it.remove();
					}
				}
			}
		}
		// } catch (Exception e) {
		// e.printStackTrace();
		// // 筛选报错不处理
		// }
		return jsLandList;
	}
}
