package com.rzspider.implementspider.suzhouhouse.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.rzspider.implementspider.suzhouhouse.domain.permitpresale.PermitPresaleCompanyInfo;
import com.rzspider.implementspider.suzhouhouse.domain.permitpresale.PermitPresaleDetailInfo;
import com.rzspider.implementspider.suzhouhouse.domain.permitpresale.PermitPresaleMessage;
import com.rzspider.implementspider.suzhouhouse.domain.permitpresale.PermitPresaleParams;
import com.rzspider.implementspider.suzhouhouse.utils.HouseCommonUtils;

public class PermitPresaleService {
	// 预售许可证页数
	public int[] getPageAndNum2(PermitPresaleParams permitPresaleParams)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		int[] num = new int[2];
		String urlOne = "http://www.szfcweb.com/szfcweb/DataSerach/MITShowList.aspx";
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
		// 获取表格信息
		HtmlTable ht = (HtmlTable) page.getElementsByTagName("table").get(3);
		String s = ht.getRow(0).asText().toString();
		String ss = s.substring(s.indexOf("共") + 6, s.indexOf("条")).trim();
		String ss2 = s.substring(s.lastIndexOf("共") + 6);
		String sss = ss2.substring(0, ss2.indexOf("页")).trim();
		num[0] = Integer.parseInt(ss);
		num[1] = Integer.parseInt(sss);
		return num;
	}

	// 获取详细信息，每十条一次
	// 获取数据
	public void getPermitPresaleDetail(PermitPresaleParams permitPresaleParams, List<PermitPresaleMessage> ppmList,
			int num) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		// 获取随机码，有效期较短所以只够这十条使用
		String ranNum = getOrUrl2();
		// 第一级真实地址，有效期几分钟，只够这十条信息查询
		String urlOne = "http://www.szfcweb.com/szfcweb/" + ranNum + "/DataSerach/MITShowList.aspx";
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
		hti.setValueAttribute(permitPresaleParams.getProjectName());
		// 公司名称
		HtmlTextInput hti2 = hf.getInputByName("ctl00$MainContent$txt_Com");
		hti2.setValueAttribute(permitPresaleParams.getCompanyName());
		// 预售证号
		HtmlTextInput hti3 = hf.getInputByName("ctl00$MainContent$txt_ysz");
		hti3.setValueAttribute(permitPresaleParams.getPreSaleCertificateNumber());
		// 下拉框
		HtmlSelect hs = hf.getSelectByName("ctl00$MainContent$ddl_RD_CODE");
		hs.setSelectedAttribute(permitPresaleParams.getProjectArea(), true);
		// 登录按钮,即可获取本页所有html
		HtmlSubmitInput sub = hf.getInputByName("ctl00$MainContent$bt_select");
		HtmlPage hp = sub.click();
		// 但要区分需要第几页，由于直接点击select不起作用，所以只能循环点击
		for (int i = 0; i < num; i++) {
			hp = hp.getElementById("ctl00_MainContent_PageGridView1_ctl12_Next").click();
		}
		// 放数据
		boolean isSuccess = setPermitPresaleMeg(ppmList, hp, ranNum);
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
	private boolean setPermitPresaleMeg(List<PermitPresaleMessage> ppmList, HtmlPage hp, String ranNum)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		// 获取表格信息
		HtmlTable ht = (HtmlTable) hp.getElementById("ctl00_MainContent_PageGridView1");
		int n = ht.getRowCount();
		PermitPresaleMessage ppm;
		HtmlTableRow row;
		String url2;
		String url21;
		for (int i = 1; i < n; i++) {
			// 新建一个数据对象,存储每一条
			ppm = new PermitPresaleMessage();
			// 从第二行开始
			row = ht.getRow(i);
			// 设置许可证号
			ppm.setPermitNum(row.getCell(0).asText().trim());
			// 获取项目名称
			ppm.setProjectName(row.getCell(1).asText().trim());

			// 拼接网址
			url2 = row.getCell(0).asXml().toString();
			// 根据网址再去请求许可证详细信息
			getPermitPresaleDetail(HouseCommonUtils.getDetailUrl(url2, ranNum), ppm);

			// 获取第三列公司信息
			url21 = row.getCell(2).asXml().toString();
			// 根据网址再去请求公司详细信息
			getCompanyDetail(HouseCommonUtils.getDetailUrl(url21, ranNum), ppm);

			// 添加到集合
			ppmList.add(ppm);
		}
		return true;
	}

	// 公司详细信息
	private void getCompanyDetail(String detailUrl, PermitPresaleMessage ppm)
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
		HtmlPage page = webClient.getPage(detailUrl);
		PermitPresaleCompanyInfo ppci = new PermitPresaleCompanyInfo();
		// 公司名称
		ppci.setCompanyName(page.getElementById("ctl00_MainContent_lb_C_NAME").asText().trim());
		// 法定代表人姓名
		ppci.setLegalRepresentative(page.getElementById("ctl00_MainContent_lb_C_LEGALPERSON").asText().trim());
		// 法定代表人电话
		ppci.setLegalRepreTel(page.getElementById("ctl00_MainContent_lb_C_LEGALPERSONTEL").asText().trim());
		// 法定地址
		ppci.setLegalRepreAddress(page.getElementById("ctl00_MainContent_lb_C_REGAREA").asText().trim());
		// 营业执照注册号
		ppci.setBusinessLicenseNum(page.getElementById("ctl00_MainContent_lb_C_BLICENSESN").asText().trim());
		// 资质证书编号
		ppci.setQualificatiomNum(page.getElementById("ctl00_MainContent_lb_C_GRADE").asText().trim());
		// 企业类型
		ppci.setCompanyType(page.getElementById("ctl00_MainContent_lb_C_TYPE").asText().trim());
		// 通讯地址
		ppci.setMailAddress(page.getElementById("ctl00_MainContent_lb_C_MAREA").asText().trim());
		// 邮政编码
		ppci.setPostalCode(page.getElementById("ctl00_MainContent_lb_C_MPOST").asText().trim());
		// E-mail
		ppci.setEmail(page.getElementById("ctl00_MainContent_lb_C_MEMAIL").asText().trim());
		// 网站
		ppci.setWebsiteUrl(page.getElementById("ctl00_MainContent_lb_C_WEBURL").asText().trim());
		// 联系人
		ppci.setLinkman(page.getElementById("ctl00_MainContent_lb_C_LINKMAN").asText().trim());
		// 联系电话
		ppci.setLinkNum(page.getElementById("ctl00_MainContent_lb_C_CONTACTTEL").asText().trim());
		ppm.setPpci(ppci);
	}

	// 许可证详细信息
	private void getPermitPresaleDetail(String detailUrl, PermitPresaleMessage ppm)
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
		HtmlPage page = webClient.getPage(detailUrl);

		PermitPresaleDetailInfo ppdi = new PermitPresaleDetailInfo();
		String companyName = page.getElementById("ctl00_MainContent_lb_PP_CORPName").getTextContent();
		String houseName = page.getElementById("ctl00_MainContent_lb_Pro_Name").getTextContent();
		String houseAddress = page.getElementById("ctl00_MainContent_lb_Pro_Address").getTextContent();
		String yuHouseArea = page.getElementById("ctl00_MainContent_lb_Pre_SumArea").getTextContent();
		String zhuHouseArea = page.getElementById("ctl00_MainContent_lb_ZG_Area").getTextContent();
		String zhuHouseNum = page.getElementById("ctl00_MainContent_lb_ZG_Count").getTextContent();
		String notZhuHouseArea = page.getElementById("ctl00_MainContent_lb_FZG_Area").getTextContent();
		String notZhuHouseNum = page.getElementById("ctl00_MainContent_lb_FZG_Count").getTextContent();
		String otherArea = page.getElementById("ctl00_MainContent_lb_QT_Area").getTextContent();
		String otherNum = page.getElementById("ctl00_MainContent_lb_QT_Count").getTextContent();
		String startDate = page.getElementById("ctl00_MainContent_lb_PP_IDate").getTextContent();
		String overDate = page.getElementById("ctl00_MainContent_lb_JZ_IDate").getTextContent();
		ppdi.setCompanyName(companyName);
		ppdi.setHouseName(houseName);
		ppdi.setHouseAddress(houseAddress);
		ppdi.setYuHouseArea(yuHouseArea);
		ppdi.setZhuHouseArea(zhuHouseArea);
		ppdi.setZhuHouseNum(zhuHouseNum);
		ppdi.setNotZhuHouseArea(notZhuHouseArea);
		ppdi.setNotZhuHouseNum(notZhuHouseNum);
		ppdi.setOtherArea(otherArea);
		ppdi.setOtherNum(otherNum);
		ppdi.setStartDate(startDate);
		ppdi.setOverDate(overDate);
		ppm.setPpdi(ppdi);
	}

}
