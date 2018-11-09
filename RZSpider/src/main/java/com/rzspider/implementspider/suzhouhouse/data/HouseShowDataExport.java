package com.rzspider.implementspider.suzhouhouse.data;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.rzspider.common.constant.CommonSymbolicConstant;
import com.rzspider.implementspider.commonutils.SpecificInternalSpiderDataUtils;
import com.rzspider.implementspider.suzhouhouse.domain.houseshow.HouseShowMessage;
import com.rzspider.implementspider.suzhouhouse.domain.houseshow.HouseShowParams;
import com.rzspider.project.common.spiderdata.domain.ReturnSpiderDataMessage;
import com.rzspider.project.common.spiderdata.domain.Spiderdata;

public class HouseShowDataExport {

	// 导出数据
	public static ReturnSpiderDataMessage houseShowDataExport(HouseShowParams houseShowParams, List<Spiderdata> list) {
		ReturnSpiderDataMessage rsdm = new ReturnSpiderDataMessage();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		if (list == null || list.size() < 1) {
			return null;
		}
		// 先解析json成对象，然后在处理
		List<HouseShowMessage> houseList;
		try {
			houseList = SpecificInternalSpiderDataUtils.jsonStringListToObjectList(list, new HouseShowMessage());
		} catch (Exception e) {
			// 解析出错则以文本返回
			// 解析文本
			StringBuilder sb = new StringBuilder();
			for (Spiderdata spiderdata : list) {
				sb.append(spiderdata.getJsonData() + CommonSymbolicConstant.LINEBREAK2);
			}
			rsdm.setBytes(sb.toString().getBytes());
			// 0是文本
			rsdm.setFileType(0);
			return rsdm;
		}

		String[] title = { "序号", "编号", "项目名称", "项目区域", "房地产公司名称", "项目地址", "代理公司名称", "楼幢详细信息", "法定代表人姓名", "法定代表人电话",
				"法定地址", "营业执照注册号", "资质证书编号", "企业类型", "通讯地址", "邮政编码", "E-mail", "网站", "联系人", "联系电话" };
		// 创建Excel工作
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 创建一个sheet表
		XSSFSheet sheet = workbook.createSheet();
		// 设置列宽
		sheet.setColumnWidth(0, 6 * 256);
		sheet.setColumnWidth(1, 6 * 256);
		sheet.setColumnWidth(2, 18 * 256);
		sheet.setColumnWidth(3, 10 * 256);
		sheet.setColumnWidth(4, 30 * 256);
		sheet.setColumnWidth(5, 50 * 256);
		sheet.setColumnWidth(6, 20 * 256);
		sheet.setColumnWidth(7, 16 * 256);
		sheet.setColumnWidth(8, 20 * 256);
		sheet.setColumnWidth(9, 15 * 256);
		sheet.setColumnWidth(10, 50 * 256);
		sheet.setColumnWidth(11, 20 * 256);
		sheet.setColumnWidth(12, 20 * 256);
		sheet.setColumnWidth(13, 20 * 256);
		sheet.setColumnWidth(14, 50 * 256);
		sheet.setColumnWidth(15, 20 * 256);
		sheet.setColumnWidth(16, 20 * 256);
		sheet.setColumnWidth(17, 20 * 256);
		sheet.setColumnWidth(18, 15 * 256);
		sheet.setColumnWidth(19, 15 * 256);
		// 创建第一行
		XSSFRow row = sheet.createRow(0);
		XSSFCell cell;
		// 插入第一行数据
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(title[i]);
		}
		// 追加数据,向第二行开始加入数据 i = 1
		for (int i = 1; i < houseList.size() + 1; i++) {
			XSSFRow row2 = sheet.createRow(i);
			XSSFCell cell2 = row2.createCell(0);
			cell2.setCellValue(i);
			cell2 = row2.createCell(1);
			cell2.setCellValue(i);
			cell2 = row2.createCell(2);
			cell2.setCellValue(houseList.get(i - 1).getProjectName());
			cell2 = row2.createCell(3);
			cell2.setCellValue(houseList.get(i - 1).getProjectArea());
			cell2 = row2.createCell(4);
			cell2.setCellValue(houseList.get(i - 1).getHsci().getCompanyName());
			cell2 = row2.createCell(5);
			// cell2.setCellValue(houseList.get(i - 1).getItemUrl());
			cell2 = row2.createCell(6);
			// cell2.setCellValue(houseList.get(i - 1).getInitialPrice());
			cell2 = row2.createCell(7);
			// cell2.setCellValue(houseList.get(i - 1).getConsultPrice());
			cell2 = row2.createCell(8);
			cell2.setCellValue(houseList.get(i - 1).getHsci().getLegalRepresentative());
			cell2 = row2.createCell(9);
			cell2.setCellValue(houseList.get(i - 1).getHsci().getLegalRepreTel());
			cell2 = row2.createCell(10);
			cell2.setCellValue(houseList.get(i - 1).getHsci().getLegalRepreAddress());
			cell2 = row2.createCell(11);
			cell2.setCellValue(houseList.get(i - 1).getHsci().getBusinessLicenseNum());
			cell2 = row2.createCell(12);
			cell2.setCellValue(houseList.get(i - 1).getHsci().getQualificatiomNum());
			cell2 = row2.createCell(13);
			cell2.setCellValue(houseList.get(i - 1).getHsci().getCompanyType());
			cell2 = row2.createCell(14);
			cell2.setCellValue(houseList.get(i - 1).getHsci().getMailAddress());
			cell2 = row2.createCell(15);
			cell2.setCellValue(houseList.get(i - 1).getHsci().getPostalCode());
			cell2 = row2.createCell(16);
			cell2.setCellValue(houseList.get(i - 1).getHsci().getEmail());
			cell2 = row2.createCell(17);
			cell2.setCellValue(houseList.get(i - 1).getHsci().getWebsiteUrl());
			cell2 = row2.createCell(18);
			cell2.setCellValue(houseList.get(i - 1).getHsci().getLinkman());
			cell2 = row2.createCell(19);
			cell2.setCellValue(houseList.get(i - 1).getHsci().getLinkNum());
		}

		// 写入文件
		SpecificInternalSpiderDataUtils.writeWBToStream(workbook, outputStream);
		byte[] bytes = outputStream.toByteArray();
		rsdm.setBytes(bytes);
		// 表格
		rsdm.setFileType(1);
		return rsdm;
	}

}
