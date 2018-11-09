package com.rzspider.implementspider.suzhouhouse.data;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.rzspider.common.constant.CommonSymbolicConstant;
import com.rzspider.implementspider.commonutils.SpecificInternalSpiderDataUtils;
import com.rzspider.implementspider.suzhouhouse.domain.MegAnnoAndTradeModel.MessageAnnoModel;
import com.rzspider.implementspider.suzhouhouse.domain.MegAnnoAndTradeModel.TradeMessageModel;
import com.rzspider.project.common.spiderdata.domain.ReturnSpiderDataMessage;
import com.rzspider.project.common.spiderdata.domain.Spiderdata;

public class MegAnnoAndTradeMegDataExport {

	public static ReturnSpiderDataMessage megAnnoDataExport(List<Spiderdata> list) {
		ReturnSpiderDataMessage rsdm = new ReturnSpiderDataMessage();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		if (list == null || list.size() < 1) {
			return null;
		}
		// 先解析json成对象，然后在处理
		List<MessageAnnoModel> messList;
		try {
			messList = SpecificInternalSpiderDataUtils.jsonStringListToObjectList(list, new MessageAnnoModel());
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

		String[] title = { "序号", "区域", "区域总计套数", "区域总建筑面积（㎡）", "住宅套数", "住宅总建筑面积（㎡）" };
		// 创建Excel工作
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 创建一个sheet表
		XSSFSheet sheet = workbook.createSheet();
		// 设置列宽
		sheet.setColumnWidth(2, 20 * 256);
		sheet.setColumnWidth(3, 20 * 256);
		sheet.setColumnWidth(4, 20 * 256);
		sheet.setColumnWidth(5, 20 * 256);
		// 创建第一行
		XSSFRow row = sheet.createRow(0);
		XSSFCell cell;
		// 插入第一行数据
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(title[i]);
		}
		// 追加数据,向第二行开始加入数据 i = 1
		for (int i = 1; i < messList.size() + 1; i++) {
			XSSFRow row2 = sheet.createRow(i);
			XSSFCell cell2 = row2.createCell(0);
			cell2.setCellValue(i);

			cell2 = row2.createCell(1);
			cell2.setCellValue(messList.get(i - 1).getHouseArea());
			cell2 = row2.createCell(2);
			cell2.setCellValue(messList.get(i - 1).getTotalNum());
			cell2 = row2.createCell(3);
			cell2.setCellValue(messList.get(i - 1).getTotalStructureArea());
			cell2 = row2.createCell(4);
			cell2.setCellValue(messList.get(i - 1).getHouseTotalNum());
			cell2 = row2.createCell(5);
			cell2.setCellValue(messList.get(i - 1).getHouseTotalStructureArea());

		}

		// 写入文件
		SpecificInternalSpiderDataUtils.writeWBToStream(workbook, outputStream);
		byte[] bytes = outputStream.toByteArray();
		rsdm.setBytes(bytes);
		// 表格
		rsdm.setFileType(1);
		return rsdm;
	}

	public static ReturnSpiderDataMessage tradeMegDataExport(List<Spiderdata> list) {
		ReturnSpiderDataMessage rsdm = new ReturnSpiderDataMessage();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		if (list == null || list.size() < 1) {
			return null;
		}
		// 先解析json成对象，然后在处理
		List<TradeMessageModel> ppmList;
		try {
			ppmList = SpecificInternalSpiderDataUtils.jsonStringListToObjectList(list, new TradeMessageModel());
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
		TradeMessageModel tmm = ppmList.get(0);
		String[] title = { "序号", "区域", "区域总计套数", "区域总建筑面积（㎡）", "住宅套数", "住宅总建筑面积（㎡）" };
		// 创建Excel工作
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 创建一个sheet表
		XSSFSheet sheet = workbook.createSheet();
		// 设置列宽
		sheet.setColumnWidth(2, 20 * 256);
		sheet.setColumnWidth(3, 20 * 256);
		sheet.setColumnWidth(4, 20 * 256);
		sheet.setColumnWidth(5, 20 * 256);
		// 创建第一行
		XSSFRow row = sheet.createRow(0);
		XSSFCell cell;
		// 插入第一行数据
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(title[i]);
		}
		// 追加数据,向第二行开始加入数据 i = 1
		for (int i = 1; i < tmm.getMamList().size() + 1; i++) {
			XSSFRow row2 = sheet.createRow(i);
			XSSFCell cell2 = row2.createCell(0);
			cell2.setCellValue(i);

			cell2 = row2.createCell(1);
			cell2.setCellValue(tmm.getMamList().get(i - 1).getHouseArea());
			cell2 = row2.createCell(2);
			cell2.setCellValue(tmm.getMamList().get(i - 1).getTotalNum());
			cell2 = row2.createCell(3);
			cell2.setCellValue(tmm.getMamList().get(i - 1).getTotalStructureArea());
			cell2 = row2.createCell(4);
			cell2.setCellValue(tmm.getMamList().get(i - 1).getHouseTotalNum());
			cell2 = row2.createCell(5);
			cell2.setCellValue(tmm.getMamList().get(i - 1).getHouseTotalStructureArea());

		}
		// 右侧信息
		XSSFRow row3 = sheet.createRow(tmm.getMamList().size() + 3);
		XSSFCell cell3 = row3.createCell(title.length + 3);
		cell3.setCellValue(tmm.getTradeNameTwo());
		// 设置时间
		XSSFRow row4 = sheet.createRow(tmm.getMamList().size() + 4);
		XSSFCell cell4;
		for (int i = title.length + 4; i < tmm.getTrmmList().size() + title.length + 4; i++) {
			cell4 = row4.createCell(i);
			cell4.setCellValue(tmm.getTrmmList().get(i - title.length - 4).getTradeTime());
		}

		// 设置值
		XSSFRow row5 = sheet.createRow(tmm.getMamList().size() + 5);
		XSSFCell cell5;
		for (int i = title.length + 4; i < tmm.getTrmmList().size() + title.length + 4; i++) {
			cell5 = row5.createCell(i);
			cell5.setCellValue(tmm.getTrmmList().get(i - title.length - 4).getTradeAveragePrice());
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
