package com.rzspider.implementspider.taobaojud.data;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.rzspider.common.constant.CommonSymbolicConstant;
import com.rzspider.implementspider.commonutils.SpecificInternalSpiderDataUtils;
import com.rzspider.implementspider.taobaojud.domain.TBJLandMessageDetail;
import com.rzspider.project.common.spiderdata.domain.ReturnSpiderDataMessage;
import com.rzspider.project.common.spiderdata.domain.Spiderdata;

//淘宝司法土拍信息导出
public class TBJSpiderDataExport {
	// 淘宝司法土拍信息导出
	public static ReturnSpiderDataMessage TBJSpiderDataExport(List<Spiderdata> list) {
		ReturnSpiderDataMessage rsdm = new ReturnSpiderDataMessage();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		if (list == null || list.size() < 1) {
			return null;
		}
		// 先解析json成对象，然后在处理
		List<TBJLandMessageDetail> tbjlmList;
		try {
			tbjlmList = SpecificInternalSpiderDataUtils.jsonStringListToObjectList(list, new TBJLandMessageDetail());
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

		String[] title = { "序号", "编号", "自带ID", "拍卖名称", "拍卖状态", "网址", "起拍价", "评估价", "开拍时间", "成交时间" };
		// 创建Excel工作
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 创建一个sheet表
		XSSFSheet sheet = workbook.createSheet();
		// 设置列宽
		// 设置列宽
		sheet.setColumnWidth(0, 6 * 256);
		sheet.setColumnWidth(1, 6 * 256);
		sheet.setColumnWidth(2, 18 * 256);
		sheet.setColumnWidth(3, 80 * 256);
		sheet.setColumnWidth(4, 10 * 256);
		sheet.setColumnWidth(5, 50 * 256);
		sheet.setColumnWidth(6, 16 * 256);
		sheet.setColumnWidth(7, 16 * 256);
		sheet.setColumnWidth(8, 20 * 256);
		sheet.setColumnWidth(9, 20 * 256);

		// 创建第一行
		XSSFRow row = sheet.createRow(0);
		XSSFCell cell;
		// 插入第一行数据
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(title[i]);
		}
		// 追加数据,向第二行开始加入数据 i = 1
		for (int i = 1; i < tbjlmList.size() + 1; i++) {
			XSSFRow row2 = sheet.createRow(i);
			XSSFCell cell2 = row2.createCell(0);
			cell2.setCellValue(i);
			cell2 = row2.createCell(1);
			cell2.setCellValue(tbjlmList.get(i - 1).getId());
			cell2 = row2.createCell(2);
			cell2.setCellValue(tbjlmList.get(i - 1).getUrlId());
			cell2 = row2.createCell(3);
			cell2.setCellValue(tbjlmList.get(i - 1).getSaleName());
			cell2 = row2.createCell(4);
			cell2.setCellValue(tbjlmList.get(i - 1).getSaleStatus());
			cell2 = row2.createCell(5);
			cell2.setCellValue(tbjlmList.get(i - 1).getItemUrl());
			cell2 = row2.createCell(6);
			cell2.setCellValue(tbjlmList.get(i - 1).getInitialPrice());
			cell2 = row2.createCell(7);
			cell2.setCellValue(tbjlmList.get(i - 1).getConsultPrice());
			cell2 = row2.createCell(8);
			cell2.setCellValue(tbjlmList.get(i - 1).getStartDate());
			cell2 = row2.createCell(9);
			cell2.setCellValue(tbjlmList.get(i - 1).getEndDate());
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
