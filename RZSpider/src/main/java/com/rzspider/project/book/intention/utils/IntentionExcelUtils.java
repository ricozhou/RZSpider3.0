package com.rzspider.project.book.intention.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.rzspider.common.utils.security.ShiroUtils;
import com.rzspider.project.book.intention.domain.Intention;

public class IntentionExcelUtils {

	// 解析excel工具类
	public static List<Intention> readExcel(File file) {
		List<Intention> itList = null;
		try {
			itList = new ArrayList<Intention>();
			Intention intention = null;
			Workbook wb = null;
			Sheet sheet = null;
			Row row = null;
			String cellData = null;
			// 获取工作簿
			wb = getWorkbook(file);
			if (wb != null) {
				// 获取第一个sheet
				sheet = (Sheet) wb.getSheetAt(0);
				// 获取最大行数
				int rownum = sheet.getPhysicalNumberOfRows();
				// 获取第一行
				row = sheet.getRow(0);
				// 获取最大列数
				int colnum = row.getPhysicalNumberOfCells();
				// 从第二行第二列开始读取
				for (int i = 1; i < rownum; i++) {
					intention = new Intention();
					row = sheet.getRow(i);
					if (row != null) {
						// 图书名称
						if (!"".equals(row.getCell(1))) {
							intention.setBookName(row.getCell(1).toString());
						}
						intention.setBookAuthor(row.getCell(2).toString());
						intention.setBookPublisher(row.getCell(3).toString());
						intention.setBookIsbn(row.getCell(4).toString());
						intention.setBookDes(row.getCell(5).toString());
						intention.setBookOriginalPrice(row.getCell(6).toString());
						intention.setBookClassification(row.getCell(7).toString());
						intention.setBookBinding(row.getCell(8).toString());
						if (!"".equals(row.getCell(9).toString())) {
							intention.setBookPages(Double.valueOf(row.getCell(9).toString()).intValue());
						}
						if (!"".equals(row.getCell(10).toString())) {
							if (Double.valueOf(row.getCell(10).toString()).intValue() > 5) {
								intention.setBookReadStar(5);
							} else {
								intention.setBookReadStar(Double.valueOf(row.getCell(10).toString()).intValue());
							}
						}

						intention.setUserId(Integer.valueOf(String.valueOf(ShiroUtils.getUserId())));
						intention.setCreateBy(ShiroUtils.getLoginName());
						intention.setRemark(row.getCell(11).toString());
					} else {
						break;
					}
					itList.add(intention);
				}
			}
			return itList;
		} catch (Exception e) {
			e.printStackTrace();
			return itList;
		}

	}

	// 读取excel返回workbook
	public static Workbook getWorkbook(File file) {
		Workbook wb = null;
		String extString = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf("."));
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			if (".xls".equals(extString)) {
				return wb = new HSSFWorkbook(is);
			} else if (".xlsx".equals(extString)) {
				return wb = new XSSFWorkbook(is);
			} else {
				return wb = null;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return wb;
	}

	// 获取workbook
	public static XSSFWorkbook createExcelFile() {
		String[] title = { "序号", "图书名称", "图书作者", "图书出版社", "图书ISBN", "图书简介", "图书定价", "图书分类", "图书装帧", "图书页数", "购买星级",
				"备注" };
		// 创建Excel工作
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 创建一个sheet表
		XSSFSheet sheet = workbook.createSheet();
		// 设置列宽
		sheet.setColumnWidth(1, 40 * 256);
		sheet.setColumnWidth(2, 30 * 256);
		sheet.setColumnWidth(3, 40 * 256);
		sheet.setColumnWidth(4, 30 * 256);
		sheet.setColumnWidth(5, 50 * 256);

		sheet.setColumnWidth(7, 30 * 256);
		sheet.setColumnWidth(11, 50 * 256);
		// 创建第一行
		XSSFRow row = sheet.createRow(0);
		XSSFCell cell;
		// 插入第一行数据
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(title[i]);
		}
		// 追加数据,向第二行开始加入数据 i = 1
		for (int i = 1; i < 4; i++) {
			XSSFRow row2 = sheet.createRow(i);
			XSSFCell cell2 = row2.createCell(0);
			cell2.setCellValue(i);
			if (i == 1) {
				cell2 = row2.createCell(1);
				cell2.setCellValue("万历十五年");
				cell2 = row2.createCell(2);
				cell2.setCellValue("黄仁宇");
				cell2 = row2.createCell(3);
				cell2.setCellValue("生活·读书·新知三联书店");
				cell2 = row2.createCell(4);
				cell2.setCellValue("0123456789123");
				cell2 = row2.createCell(5);
				cell2.setCellValue("讲述明朝万历年间的故事");
				cell2 = row2.createCell(6);
				cell2.setCellValue("18.00");
				cell2 = row2.createCell(7);
				cell2.setCellValue("历史地理");
				cell2 = row2.createCell(8);
				cell2.setCellValue("平装");
				cell2 = row2.createCell(9);
				cell2.setCellValue("210");
				cell2 = row2.createCell(10);
				cell2.setCellValue("5");
				cell2 = row2.createCell(11);
				cell2.setCellValue("无");
			} else if (i == 2) {
				cell2 = row2.createCell(1);
				cell2.setCellValue("做书");
				cell2 = row2.createCell(2);
				cell2.setCellValue("李昕");
				cell2 = row2.createCell(3);
				cell2.setCellValue("商务印书馆");
				cell2 = row2.createCell(4);
				cell2.setCellValue("0123456789124");
				cell2 = row2.createCell(5);
				cell2.setCellValue("讲述作者身为图书编辑的故事");
				cell2 = row2.createCell(6);
				cell2.setCellValue("39.80");
				cell2 = row2.createCell(7);
				cell2.setCellValue("科教文体");
				cell2 = row2.createCell(8);
				cell2.setCellValue("软精装");
				cell2 = row2.createCell(9);
				cell2.setCellValue("340");
				cell2 = row2.createCell(10);
				cell2.setCellValue("4");
				cell2 = row2.createCell(11);
				cell2.setCellValue("无");
			} else if (i == 3) {
				cell2 = row2.createCell(1);
				cell2.setCellValue("和平与战争");
				cell2 = row2.createCell(2);
				cell2.setCellValue("【法】雷蒙·阿隆");
				cell2 = row2.createCell(3);
				cell2.setCellValue("中央编译出版社");
				cell2 = row2.createCell(4);
				cell2.setCellValue("0123456789125");
				cell2 = row2.createCell(5);
				cell2.setCellValue("国际关系理论");
				cell2 = row2.createCell(6);
				cell2.setCellValue("168.00");
				cell2 = row2.createCell(7);
				cell2.setCellValue("政治法律");
				cell2 = row2.createCell(8);
				cell2.setCellValue("精装");
				cell2 = row2.createCell(9);
				cell2.setCellValue("880");
				cell2 = row2.createCell(10);
				cell2.setCellValue("5");
				cell2 = row2.createCell(11);
				cell2.setCellValue("无");
			}
		}
		// 写入文件
		return workbook;
	}

	// workbook写入输出流
	public static boolean writeWBToStream(XSSFWorkbook workbook, ByteArrayOutputStream outputStream) {
		try {
			workbook.write(outputStream);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	// 批量导出
	public static XSSFWorkbook createExcelFile(List<Intention> itList) {

		String[] title = { "序号", "图书名称", "图书作者", "图书出版社", "图书ISBN", "图书简介", "图书定价", "图书分类", "图书装帧", "图书页数", "购买星级",
				"备注" };
		// 创建Excel工作
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 创建一个sheet表
		XSSFSheet sheet = workbook.createSheet();
		// 设置列宽
		sheet.setColumnWidth(1, 40 * 256);
		sheet.setColumnWidth(2, 30 * 256);
		sheet.setColumnWidth(3, 40 * 256);
		sheet.setColumnWidth(4, 30 * 256);
		sheet.setColumnWidth(5, 50 * 256);

		sheet.setColumnWidth(7, 30 * 256);
		sheet.setColumnWidth(11, 50 * 256);
		// 创建第一行
		XSSFRow row = sheet.createRow(0);
		XSSFCell cell;
		// 插入第一行数据
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(title[i]);
		}
		// 追加数据,向第二行开始加入数据 i = 1
		for (int i = 1; i < itList.size() + 1; i++) {
			XSSFRow row2 = sheet.createRow(i);
			XSSFCell cell2 = row2.createCell(0);
			cell2.setCellValue(i);
			cell2 = row2.createCell(1);
			cell2.setCellValue(itList.get(i - 1).getBookName());
			cell2 = row2.createCell(2);
			cell2.setCellValue(itList.get(i - 1).getBookAuthor() != null ? itList.get(i - 1).getBookAuthor() : "");
			cell2 = row2.createCell(3);
			cell2.setCellValue(
					itList.get(i - 1).getBookPublisher() != null ? itList.get(i - 1).getBookPublisher() : "");
			cell2 = row2.createCell(4);
			cell2.setCellValue(itList.get(i - 1).getBookIsbn() != null ? itList.get(i - 1).getBookIsbn() : "");
			cell2 = row2.createCell(5);
			cell2.setCellValue(itList.get(i - 1).getBookDes() != null ? itList.get(i - 1).getBookDes() : "");
			cell2 = row2.createCell(6);
			cell2.setCellValue(
					itList.get(i - 1).getBookOriginalPrice() != null ? itList.get(i - 1).getBookOriginalPrice() : "");
			cell2 = row2.createCell(7);
			cell2.setCellValue(
					itList.get(i - 1).getBookClassification() != null ? itList.get(i - 1).getBookClassification() : "");
			cell2 = row2.createCell(8);
			cell2.setCellValue(itList.get(i - 1).getBookBinding() != null ? itList.get(i - 1).getBookBinding() : "");
			cell2 = row2.createCell(9);
			cell2.setCellValue(
					String.valueOf(itList.get(i - 1).getBookPages() != null ? itList.get(i - 1).getBookPages() : ""));
			cell2 = row2.createCell(10);
			cell2.setCellValue(String.valueOf(itList.get(i - 1).getBookReadStar()));
			cell2 = row2.createCell(11);
			cell2.setCellValue(itList.get(i - 1).getRemark() != null ? itList.get(i - 1).getRemark() : "");
		}
		// 写入文件
		return workbook;
	}
}
