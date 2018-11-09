package com.rzspider.implementspider.suzhouhouse.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlKeyboard;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlRadioButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.rzspider.implementspider.suzhouhouse.domain.housequery.HouseQueryCompanyInfo;
import com.rzspider.implementspider.suzhouhouse.domain.housequery.HouseQueryDetailInfo;
import com.rzspider.implementspider.suzhouhouse.domain.housequery.HouseQueryMessage;
import com.rzspider.implementspider.suzhouhouse.domain.housequery.HouseQueryParams;
import com.rzspider.implementspider.suzhouhouse.domain.houseshow.HouseShowCompanyInfo;
import com.rzspider.implementspider.suzhouhouse.domain.houseshow.HouseShowMessage;
import com.rzspider.implementspider.suzhouhouse.utils.HouseCommonUtils;

public class HouseQueryService {

	// 页数
	public int[] getPageAndNum(HouseQueryParams houseQueryParams)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		int[] num = new int[2];
		String urlOne = "http://spf.szfcweb.com/szfcweb/DataSerach/CanSaleHouseSelectIndex.aspx";
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
		hti.setValueAttribute(houseQueryParams.getProjectName());
		// 公司名称
		HtmlTextInput hti2 = hf.getInputByName("ctl00$MainContent$txt_Com");
		hti2.setValueAttribute(houseQueryParams.getCompanyName());
		// 下拉框，项目区域
		HtmlSelect hs = hf.getSelectByName("ctl00$MainContent$ddl_qy");
		// hs.setSelectedIndex(5);
		// 此处注意value的值
		String projectArea = "RD002";
		if ("工业园区".equals(houseQueryParams.getProjectArea())) {
			projectArea = "RD002";
		} else if ("吴中区".equals(houseQueryParams.getProjectArea())) {
			projectArea = "RD003";
		} else if ("相城区".equals(houseQueryParams.getProjectArea())) {
			projectArea = "RD004";
		} else if ("高新区".equals(houseQueryParams.getProjectArea())) {
			projectArea = "RD005";
		} else if ("姑苏区".equals(houseQueryParams.getProjectArea())) {
			projectArea = "RD001";
		} else if ("吴江区".equals(houseQueryParams.getProjectArea())) {
			projectArea = "RD008";
		}
		hs.setSelectedAttribute(projectArea, true);

		// 装修情况
		List<HtmlRadioButtonInput> listr = hf.getRadioButtonsByName("ctl00$MainContent$rb_HF_CODE");
		if (houseQueryParams.getFitmentSelect() == 1) {
			listr.get(1).setChecked(true);
		} else if (houseQueryParams.getFitmentSelect() == 2) {
			listr.get(2).setChecked(true);
		}

		// Htmlr
		// 房屋总价
		HtmlTextInput hti3 = hf.getInputByName("ctl00$MainContent$txt_Price1");
		hti3.setValueAttribute(houseQueryParams.getHouseMinPrice());
		HtmlTextInput hti4 = hf.getInputByName("ctl00$MainContent$txt_Price2");
		hti4.setValueAttribute(houseQueryParams.getHouseMaxPrice());

		// 房屋面积
		HtmlTextInput hti5 = hf.getInputByName("ctl00$MainContent$txt_Area1");
		hti5.setValueAttribute(houseQueryParams.getHouseMinArea());
		HtmlTextInput hti6 = hf.getInputByName("ctl00$MainContent$txt_Area2");
		hti6.setValueAttribute(houseQueryParams.getHouseMaxArea());

		// 房屋用途
		HtmlSelect hs2 = hf.getSelectByName("ctl00$MainContent$ddl_houseclass");
		String houseUse = "1";
		if ("住宅".equals(houseQueryParams.getHouseUse())) {
			houseUse = "1";
		} else if ("非住宅".equals(houseQueryParams.getHouseUse())) {
			houseUse = "2";
		} else if ("其它".equals(houseQueryParams.getHouseUse())) {
			houseUse = "3";
		}
		hs2.setSelectedAttribute(houseUse, true);

		// 登录按钮
		HtmlSubmitInput sub = hf.getInputByName("ctl00$MainContent$bt_select");
		HtmlPage hp = sub.click();
		System.out.println(hp.asText());
		// 获取表格信息
		HtmlTable ht = (HtmlTable) hp.getElementById("ctl00_MainContent_PageGridView1");
		if (ht.getRow(0).getCell(0).equals("没有数据")) {
			num[0] = 0;
			num[1] = 0;
			return num;
		}
		// 获取页数条数
		HtmlTable htt = (HtmlTable) hp.getElementsByTagName("table").get(0).getElementsByTagName("table").get(0)
				.getElementsByTagName("table").get(2);
		String s = htt.asText().toString();
		String ss = s.substring(s.indexOf("共") + 6, s.indexOf("条")).trim();
		String ss2 = s.substring(s.lastIndexOf("共") + 6);
		String sss = ss2.substring(0, ss2.indexOf("页")).trim();
		num[0] = Integer.parseInt(ss);
		num[1] = Integer.parseInt(sss);
		return num;
	}

	// 获取信息
	public void getHouseQueryDetail(HouseQueryParams houseQueryParams, List<HouseQueryMessage> hqmList, int num)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		// 获取随机码，有效期较短所以只够这十条使用
		String ranNum = getOrUrl2();
		// 第一级真实地址，有效期几分钟，只够这十条信息查询
		String urlOne = "http://spf.szfcweb.com/szfcweb/" + ranNum + "/DataSerach/CanSaleHouseSelectIndex.aspx";
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
		hti.setValueAttribute(houseQueryParams.getProjectName());
		// 公司名称
		HtmlTextInput hti2 = hf.getInputByName("ctl00$MainContent$txt_Com");
		hti2.setValueAttribute(houseQueryParams.getCompanyName());
		// 下拉框，项目区域
		HtmlSelect hs = hf.getSelectByName("ctl00$MainContent$ddl_qy");
		// hs.setSelectedIndex(5);
		// 此处注意value的值
		String projectArea = "RD002";
		if ("工业园区".equals(houseQueryParams.getProjectArea())) {
			projectArea = "RD002";
		} else if ("吴中区".equals(houseQueryParams.getProjectArea())) {
			projectArea = "RD003";
		} else if ("相城区".equals(houseQueryParams.getProjectArea())) {
			projectArea = "RD004";
		} else if ("高新区".equals(houseQueryParams.getProjectArea())) {
			projectArea = "RD005";
		} else if ("姑苏区".equals(houseQueryParams.getProjectArea())) {
			projectArea = "RD001";
		} else if ("吴江区".equals(houseQueryParams.getProjectArea())) {
			projectArea = "RD008";
		}
		hs.setSelectedAttribute(projectArea, true);
		// 装修情况
		List<HtmlRadioButtonInput> listr = hf.getRadioButtonsByName("ctl00$MainContent$rb_HF_CODE");
		if (houseQueryParams.getFitmentSelect() == 1) {
			listr.get(1).setChecked(true);
		} else if (houseQueryParams.getFitmentSelect() == 2) {
			listr.get(2).setChecked(true);
		}

		// Htmlr
		// 房屋总价
		HtmlTextInput hti3 = hf.getInputByName("ctl00$MainContent$txt_Price1");
		hti3.setValueAttribute(houseQueryParams.getHouseMinPrice());
		HtmlTextInput hti4 = hf.getInputByName("ctl00$MainContent$txt_Price2");
		hti4.setValueAttribute(houseQueryParams.getHouseMaxPrice());

		// 房屋面积
		HtmlTextInput hti5 = hf.getInputByName("ctl00$MainContent$txt_Area1");
		hti5.setValueAttribute(houseQueryParams.getHouseMinArea());
		HtmlTextInput hti6 = hf.getInputByName("ctl00$MainContent$txt_Area2");
		hti6.setValueAttribute(houseQueryParams.getHouseMaxArea());

		// 房屋用途
		HtmlSelect hs2 = hf.getSelectByName("ctl00$MainContent$ddl_houseclass");
		String houseUse = "1";
		if ("住宅".equals(houseQueryParams.getHouseUse())) {
			houseUse = "1";
		} else if ("非住宅".equals(houseQueryParams.getHouseUse())) {
			houseUse = "2";
		} else if ("其它".equals(houseQueryParams.getHouseUse())) {
			houseUse = "3";
		}
		hs2.setSelectedAttribute(houseUse, true);

		// 登录按钮,即可获取本页所有html
		HtmlSubmitInput sub = hf.getInputByName("ctl00$MainContent$bt_select");
		HtmlPage hp = sub.click();
		// System.out.println(hp.asText());
		// 但要区分需要第几页，由于直接点击select不起作用，所以只能循环点击
		for (int i = 0; i < num; i++) {
			hp = hp.getElementById("ctl00_MainContent_PageGridView1_ctl22_Next").click();
		}
		// 这下彻底获取目标页的html了
		// 传递page循环取数据
		boolean isSuccess = setHouseQueryMeg(hqmList, hp, ranNum);

	}

	public String getOrUrl2() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		String url = "http://spf.szfcweb.com/szfcweb/DataSerach/CanSaleHouseSelectIndex.aspx";
		WebClient webClient = new WebClient();
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setCssEnabled(false);
		HtmlPage page = webClient.getPage(url);
		String url2 = page.getBaseURI().toString().substring(page.getBaseURI().toString().indexOf("szfcweb/") + 8,
				page.getBaseURI().toString().indexOf("))") + 2);
		return url2;
	}

	private boolean setHouseQueryMeg(List<HouseQueryMessage> hqmList, HtmlPage hp, String ranNum)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		// 获取表格信息
		HtmlTable ht = (HtmlTable) hp.getElementById("ctl00_MainContent_PageGridView1");
		int n = ht.getRowCount();
		HouseQueryMessage hsm;
		HtmlTableRow row;
		HtmlElement he;
		HtmlPage hp2;
		String url2;
		String url3;
		String url4;
		String urlTwo;
		for (int i = 1; i < n; i++) {
			// 新建一个数据对象,存储每一条
			hsm = new HouseQueryMessage();
			// 从第二行开始
			row = ht.getRow(i);
			// 获取第一列项目详细信息
			url2 = row.getCell(0).asXml().toString();
			urlTwo = HouseCommonUtils.getDetailUrl2(url2, ranNum);
			// 设置房屋坐落
			hsm.setHouseLacation(row.getCell(0).asText().toString().trim());
			// 房屋详情
			getHouseQueryDetail(urlTwo, hsm);

			// 第二列比较详细
			// 获取第二列公司信息
			url2 = row.getCell(1).asXml().toString();
			urlTwo = HouseCommonUtils.getDetailUrl(url2, ranNum);
			// 根据网址再去请求公司详细信息
			getCompanyDetail(urlTwo, hsm);
			// 房屋用途
			hsm.setHouseUse(row.getCell(2).asText().toString().trim());
			// 房屋套型
			hsm.setHouseType(row.getCell(3).asText().toString().trim());
			// 房屋建筑面积
			hsm.setHouseBuildArea(row.getCell(4).asText().toString().trim());
			// 房屋参考单价
			hsm.setHouseReferencePrice(row.getCell(5).asText().toString().trim());

			// 添加到集合
			hqmList.add(hsm);
		}

		return true;
	}

	// 取详情相关信息
	private void getHouseQueryDetail(String urlTwo, HouseQueryMessage hsm)
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
		HouseQueryDetailInfo hqdi = new HouseQueryDetailInfo();
		//
		hqdi.setSeatNumber(page.getElementById("ctl00_MainContent_txt_HSE_Located").asText().trim());
		hqdi.setLevel(page.getElementById("ctl00_MainContent_txt_HSE_LevelCode").asText().trim());
		hqdi.setNaturalLayer(page.getElementById("ctl00_MainContent_txt_Hes_LevelName").asText().trim());
		hqdi.setUnitNumber(page.getElementById("ctl00_MainContent_txt_HSE_CellCode").asText().trim());
		hqdi.setRoomNumber(page.getElementById("ctl00_MainContent_txt_Hse_URCode").asText().trim());
		hqdi.setPropertyNature(page.getElementById("ctl00_MainContent_txt_ht_name").asText().trim());
		hqdi.setTypesOf(page.getElementById("ctl00_MainContent_txt_SHSE_ISPOLICY").asText().trim());
		hqdi.setUse(page.getElementById("ctl00_MainContent_txt_Hse_Class").asText().trim());
		hqdi.setDateCompletion(page.getElementById("ctl00_MainContent_lb_PH_Cdate").asText().trim());
		hqdi.setStructureType(page.getElementById("ctl00_MainContent_txt_hs_name").asText().trim());
		hqdi.setHorcn(page.getElementById("ctl00_MainContent_txt_HSE_Ownership").asText().trim());
		hqdi.setLdtln(page.getElementById("ctl00_MainContent_txt_HSE_LDTPermit_SN").asText().trim());
		hqdi.setMortgage(page.getElementById("ctl00_MainContent_ddl_ProType").asText().trim());
		hqdi.setRenovationCondition(page.getElementById("ctl00_MainContent_txt_hf_name").asText().trim());
		hqdi.setMortgageStartDate(page.getElementById("ctl00_MainContent_lb_ProStar").asText().trim());
		hqdi.setMortgageTerminationDate(page.getElementById("ctl00_MainContent_lb_ProEnd").asText().trim());
		hqdi.setInbuildingArea(page.getElementById("ctl00_MainContent_txt_SFLOOR_IN").asText().trim());
		hqdi.setDistributionBuildingArea(page.getElementById("ctl00_MainContent_txt_SFLOOR_FH").asText().trim());
		hqdi.setTotalFloorArea(page.getElementById("ctl00_MainContent_txt_AreaAll").asText().trim());
		hqdi.setTotalPrice(page.getElementById("ctl00_MainContent_txt_Hse_TSUM").asText().trim());
		hqdi.setUnitPrice(page.getElementById("ctl00_MainContent_txt_SFLOOR_PRICE").asText().trim());
		hqdi.setPublicSecurityNumber(page.getElementById("ctl00_MainContent_txt_Address").asText().trim());
		String str = page.getElementById("ctl00_MainContent_rbt_HSE_IsLevelHeigh").asText().trim();
		if (str.contains("checked")) {
			str = "是";
		} else {
			str = "否";
		}
		hqdi.setHeightThanTPTmeters(str);
		hqdi.setHouseHeight(page.getElementById("ctl00_MainContent_txt_LevelHeigh").asText().trim());

		hsm.setHqdi(hqdi);
	}

	// 取公司相关信息
	private void getCompanyDetail(String urlTwo, HouseQueryMessage hsm)
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
		HouseQueryCompanyInfo hsci = new HouseQueryCompanyInfo();
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
		hsm.setHqci(hsci);
	}
}
