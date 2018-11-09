package com.rzspider.implementspider.suzhouhouse.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.rzspider.implementspider.suzhouhouse.domain.houseshow.HouseShowCompanyInfo;
import com.rzspider.implementspider.suzhouhouse.domain.houseshow.HouseShowMessage;
import com.rzspider.implementspider.suzhouhouse.domain.houseshow.HouseShowParams;

public class HouseShowService {

	// 获取条数，页数
	public int[] getPageAndNum(HouseShowParams houseShowParams)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		int[] num = new int[2];
		String urlOne = "http://spf.szfcweb.com/szfcweb/DataSerach/SaleInfoProListIndex.aspx";
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
		// 获取表单
		HtmlForm hf = page.getFormByName("aspnetForm");
		// 项目名称
		HtmlTextInput hti = hf.getInputByName("ctl00$MainContent$txt_Pro");
		hti.setValueAttribute(houseShowParams.getProjectName());
		// 公司名称
		HtmlTextInput hti2 = hf.getInputByName("ctl00$MainContent$txt_Com");
		hti2.setValueAttribute(houseShowParams.getCompanyName());
		// 下拉框
		HtmlSelect hs = hf.getSelectByName("ctl00$MainContent$ddl_RD_CODE");
		// hs.setSelectedIndex(5);
		hs.setSelectedAttribute(houseShowParams.getProjectArea(), true);
		// 登录按钮
		HtmlSubmitInput sub = hf.getInputByName("ctl00$MainContent$bt_select");
		HtmlPage hp = sub.click();
		// 获取表格信息
		HtmlTable ht = (HtmlTable) hp.getElementById("ctl00_MainContent_OraclePager1");
		if (ht.getRow(0).getCell(0).equals("没有数据")) {
			num[0] = 0;
			num[1] = 0;
			return num;
		}
		// 获取页数条数
		HtmlTable htt = (HtmlTable) hp.getElementsByTagName("table").get(0).getElementsByTagName("table").get(0)
				.getElementsByTagName("table").get(1);
		String s = htt.asText().toString();
		String ss = s.substring(s.indexOf("共") + 6, s.indexOf("条")).trim();
		String ss2 = s.substring(s.lastIndexOf("共") + 6);
		String sss = ss2.substring(0, ss2.indexOf("页")).trim();
		num[0] = Integer.parseInt(ss);
		num[1] = Integer.parseInt(sss);
		return num;
	}

	// 获取详细信息，每十条一次
	public void getHouseShowDetail(HouseShowParams houseShowParams, List<HouseShowMessage> houseList, int num)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		// 获取随机码，有效期较短所以只够这十条使用
		String ranNum = getOrUrl2();
		// 第一级真实地址，有效期几分钟，只够这十条信息查询
		String urlOne = "http://spf.szfcweb.com/szfcweb/" + ranNum + "/DataSerach/SaleInfoProListIndex.aspx";
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
		// 获取表单
		HtmlForm hf = page.getFormByName("aspnetForm");
		// 项目名称
		HtmlTextInput hti = hf.getInputByName("ctl00$MainContent$txt_Pro");
		hti.setValueAttribute(houseShowParams.getProjectName());
		// 公司名称
		HtmlTextInput hti2 = hf.getInputByName("ctl00$MainContent$txt_Com");
		hti2.setValueAttribute(houseShowParams.getCompanyName());
		// 下拉框
		HtmlSelect hs = hf.getSelectByName("ctl00$MainContent$ddl_RD_CODE");
		// hs.setSelectedIndex(5);
		hs.setSelectedAttribute(houseShowParams.getProjectArea(), true);

		// 登录按钮,即可获取本页所有html
		HtmlSubmitInput sub = hf.getInputByName("ctl00$MainContent$bt_select");
		HtmlPage hp = sub.click();
		// 但要区分需要第几页，由于直接点击select不起作用，所以只能循环点击
		for (int i = 0; i < num; i++) {
			hp = hp.getElementById("ctl00_MainContent_OraclePager1_ctl12_Next").click();
		}
		// 这下彻底获取目标页的html了
		// 传递page循环取数据
		boolean isSuccess = setHouseShowMeg(houseList, hp, ranNum);

	}

	public String getOrUrl2() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		String url = "http://www.szfcweb.com/szfcweb/DataSerach/MITShowList.aspx";
		WebClient webClient = new WebClient();
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setCssEnabled(false);
		HtmlPage page = webClient.getPage(url);
		String url2 = page.getBaseURI().toString().substring(page.getBaseURI().toString().indexOf("szfcweb/") + 8,
				page.getBaseURI().toString().indexOf("))") + 2);
		return url2;
	}

	// 取数据
	public boolean setHouseShowMeg(List<HouseShowMessage> houseList, HtmlPage hp, String ranNum) throws IOException {
		// 获取表格信息
		HtmlTable ht = (HtmlTable) hp.getElementById("ctl00_MainContent_OraclePager1");
		int n = ht.getRowCount();
		HouseShowMessage hsm;
		HtmlTableRow row;
		HtmlElement he;
		HtmlPage hp2;
		String url2;
		String url3;
		String url4;
		String urlTwo;
		for (int i = 1; i < n; i++) {
			// 新建一个数据对象,存储每一条
			hsm = new HouseShowMessage();
			// 从第二行开始
			row = ht.getRow(i);
			// 获取第一列项目详细信息
			he = row.getCell(0).getElementsByTagName("a").get(0);
			hp2 = he.click();
			// 这是为了获取楼幢信息，暂不实现

			// 设置项目名称
			hsm.setProjectName(he.asText().toString().trim());

			// 第二列比较详细
			// 获取第二列公司信息
			url2 = row.getCell(1).asXml().toString();
			url3 = url2.substring(url2.indexOf("showModalDialog('") + 17);
			url4 = url3.substring(0, url3.indexOf("',"));
			urlTwo = "http://spf.szfcweb.com/szfcweb/" + ranNum + "/DataSerach/" + url4;
			// 根据网址再去请求公司详细信息
			getCompanyDetail(urlTwo, hsm);

			// 设置区域
			hsm.setProjectArea(row.getCell(2).asText().trim());
			// 添加到集合
			houseList.add(hsm);
		}

		return true;

	}

	// 取公司相关信息
	private void getCompanyDetail(String urlTwo, HouseShowMessage hsm)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		// 模拟浏览器操作
		// 创建WebClient
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		// 关闭css代码功能
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setCssEnabled(false);
		// 如若有可能找不到文件js则加上这句代码
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		// 获取第一级网页html
		HtmlPage page = webClient.getPage(urlTwo);
		HouseShowCompanyInfo hsci = new HouseShowCompanyInfo();
		// 公司名称
		hsci.setCompanyName(page.getElementById("ctl00_MainContent_lb_C_NAME").asText().trim());
		// 法定代表人姓名
		hsci.setLegalRepresentative(page.getElementById("ctl00_MainContent_lb_C_LEGALPERSON").asText().trim());
		// 法定代表人电话
		hsci.setLegalRepreTel(page.getElementById("ctl00_MainContent_lb_C_LEGALPERSONTEL").asText().trim());
		// 法定地址
		hsci.setLegalRepreAddress(page.getElementById("ctl00_MainContent_lb_C_REGAREA").asText().trim());
		// 营业执照注册号
		hsci.setBusinessLicenseNum(page.getElementById("ctl00_MainContent_lb_C_BLICENSESN").asText().trim());
		// 资质证书编号
		hsci.setQualificatiomNum(page.getElementById("ctl00_MainContent_lb_C_GRADE").asText().trim());
		// 企业类型
		hsci.setCompanyType(page.getElementById("ctl00_MainContent_lb_C_TYPE").asText().trim());
		// 通讯地址
		hsci.setMailAddress(page.getElementById("ctl00_MainContent_lb_C_MAREA").asText().trim());
		// 邮政编码
		hsci.setPostalCode(page.getElementById("ctl00_MainContent_lb_C_MPOST").asText().trim());
		// E-mail
		hsci.setEmail(page.getElementById("ctl00_MainContent_lb_C_MEMAIL").asText().trim());
		// 网站
		hsci.setWebsiteUrl(page.getElementById("ctl00_MainContent_lb_C_WEBURL").asText().trim());
		// 联系人
		hsci.setLinkman(page.getElementById("ctl00_MainContent_lb_C_LINKMAN").asText().trim());
		// 联系电话
		hsci.setLinkNum(page.getElementById("ctl00_MainContent_lb_C_CONTACTTEL").asText().trim());
		hsm.setHsci(hsci);
	}
}
