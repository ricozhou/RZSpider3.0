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
import com.rzspider.implementspider.suzhouhouse.domain.permitpresale.PermitPresaleMessage;
import com.rzspider.implementspider.suzhouhouse.domain.permitpresale.PermitPresaleParams;
import com.rzspider.project.common.spiderdata.domain.ReturnSpiderDataMessage;
import com.rzspider.project.common.spiderdata.domain.Spiderdata;

public class PermitPresaleDataExport {

	public static ReturnSpiderDataMessage permitPresaleDataExport(PermitPresaleParams permitPresaleParams,
			List<Spiderdata> list) {

		ReturnSpiderDataMessage rsdm = new ReturnSpiderDataMessage();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		if (list == null || list.size() < 1) {
			return null;
		}
		// 先解析json成对象，然后在处理
		List<PermitPresaleMessage> ppmList;
		try {
			ppmList = SpecificInternalSpiderDataUtils.jsonStringListToObjectList(list, new PermitPresaleMessage());
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

		String[] title = { "序号", "编号", "许可证号", "项目名称", "售房单位名称", "商品房名称", "商品房坐落", "预售总建筑面积", "住宅面积", "住宅套数", "非住宅面积",
				"非住宅套数", "其他面积", "其他套数", "颁发日期", "截止日期", "房地产公司名称", "法定代表人姓名", "法定代表人电话", "法定地址", "营业执照注册号", "资质证书编号",
				"企业类型", "通讯地址", "邮政编码", "E-mail", "网站", "联系人", "联系电话" };
		// 创建Excel工作
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 创建一个sheet表
		XSSFSheet sheet = workbook.createSheet();
		// 设置列宽
		sheet.setColumnWidth(2, 25 * 256);
		sheet.setColumnWidth(3, 20 * 256);
		sheet.setColumnWidth(4, 50 * 256);
		sheet.setColumnWidth(5, 20 * 256);
		sheet.setColumnWidth(6, 20 * 256);

		sheet.setColumnWidth(14, 20 * 256);
		sheet.setColumnWidth(15, 20 * 256);
		sheet.setColumnWidth(16, 50 * 256);
		sheet.setColumnWidth(18, 20 * 256);
		sheet.setColumnWidth(19, 50 * 256);
		sheet.setColumnWidth(20, 30 * 256);
		sheet.setColumnWidth(21, 20 * 256);
		sheet.setColumnWidth(22, 30 * 256);
		sheet.setColumnWidth(25, 30 * 256);
		sheet.setColumnWidth(28, 20 * 256);
		// 创建第一行
		XSSFRow row = sheet.createRow(0);
		XSSFCell cell;
		// 插入第一行数据
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(title[i]);
		}
		// 追加数据,向第二行开始加入数据 i = 1
		for (int i = 1; i < ppmList.size() + 1; i++) {
			XSSFRow row2 = sheet.createRow(i);
			XSSFCell cell2 = row2.createCell(0);
			cell2.setCellValue(i);
			cell2 = row2.createCell(1);
			cell2.setCellValue(i);
			cell2 = row2.createCell(2);
			cell2.setCellValue(ppmList.get(i - 1).getPermitNum());
			cell2 = row2.createCell(3);
			cell2.setCellValue(ppmList.get(i - 1).getProjectName());
			// 许可证详细信息
			cell2 = row2.createCell(4);
			cell2.setCellValue(ppmList.get(i - 1).getPpdi().getCompanyName());
			cell2 = row2.createCell(5);
			cell2.setCellValue(ppmList.get(i - 1).getPpdi().getHouseName());
			cell2 = row2.createCell(6);
			cell2.setCellValue(ppmList.get(i - 1).getPpdi().getHouseAddress());
			cell2 = row2.createCell(7);
			cell2.setCellValue(ppmList.get(i - 1).getPpdi().getYuHouseArea());
			cell2 = row2.createCell(8);
			cell2.setCellValue(ppmList.get(i - 1).getPpdi().getZhuHouseArea());
			cell2 = row2.createCell(9);
			cell2.setCellValue(ppmList.get(i - 1).getPpdi().getZhuHouseNum());
			cell2 = row2.createCell(10);
			cell2.setCellValue(ppmList.get(i - 1).getPpdi().getNotZhuHouseArea());
			cell2 = row2.createCell(11);
			cell2.setCellValue(ppmList.get(i - 1).getPpdi().getNotZhuHouseNum());
			cell2 = row2.createCell(12);
			cell2.setCellValue(ppmList.get(i - 1).getPpdi().getOtherArea());
			cell2 = row2.createCell(13);
			cell2.setCellValue(ppmList.get(i - 1).getPpdi().getOtherNum());
			cell2 = row2.createCell(14);
			cell2.setCellValue(ppmList.get(i - 1).getPpdi().getStartDate());
			cell2 = row2.createCell(15);
			cell2.setCellValue(ppmList.get(i - 1).getPpdi().getOverDate());

			// 公司详细信息
			cell2 = row2.createCell(16);
			cell2.setCellValue(ppmList.get(i - 1).getPpci().getCompanyName());
			cell2 = row2.createCell(17);
			cell2.setCellValue(ppmList.get(i - 1).getPpci().getLegalRepresentative());
			cell2 = row2.createCell(18);
			cell2.setCellValue(ppmList.get(i - 1).getPpci().getLegalRepreTel());
			cell2 = row2.createCell(19);
			cell2.setCellValue(ppmList.get(i - 1).getPpci().getLegalRepreAddress());
			cell2 = row2.createCell(20);
			cell2.setCellValue(ppmList.get(i - 1).getPpci().getBusinessLicenseNum());
			cell2 = row2.createCell(21);
			cell2.setCellValue(ppmList.get(i - 1).getPpci().getQualificatiomNum());
			cell2 = row2.createCell(22);
			cell2.setCellValue(ppmList.get(i - 1).getPpci().getCompanyType());
			cell2 = row2.createCell(23);
			cell2.setCellValue(ppmList.get(i - 1).getPpci().getMailAddress());
			cell2 = row2.createCell(24);
			cell2.setCellValue(ppmList.get(i - 1).getPpci().getPostalCode());
			cell2 = row2.createCell(25);
			cell2.setCellValue(ppmList.get(i - 1).getPpci().getEmail());
			cell2 = row2.createCell(26);
			cell2.setCellValue(ppmList.get(i - 1).getPpci().getWebsiteUrl());
			cell2 = row2.createCell(27);
			cell2.setCellValue(ppmList.get(i - 1).getPpci().getLinkman());
			cell2 = row2.createCell(28);
			cell2.setCellValue(ppmList.get(i - 1).getPpci().getLinkNum());
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
