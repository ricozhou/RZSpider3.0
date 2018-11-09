package com.rzspider.project.spider.spidertask.mainwork;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.rzspider.common.constant.CommonSymbolicConstant;
import com.rzspider.common.constant.project.SpiderConstant;
import com.rzspider.project.common.spiderdata.domain.ReturnSpiderDataMessage;
import com.rzspider.project.common.spiderdata.domain.Spiderdata;
import com.rzspider.project.spider.spidertask.mainwork.domain.StartSpiderInfo;
import com.rzspider.project.spider.spidertask.mainwork.utils.CustomSpiderDataUtils;

/**
 * 具体数据解析
 * 
 * @author ricozhou
 */
@Component("customSpiderData")
public class CustomSpiderData {
	// 需要两个参数，一个是封装信息的，一个是封装数据的
	public ReturnSpiderDataMessage analyzeCustomSpiderData(StartSpiderInfo startSpiderInfo, List<Spiderdata> list) {
		ReturnSpiderDataMessage rsdm = new ReturnSpiderDataMessage();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		if (list == null || list.size() < 1) {
			return null;
		}
		// 思路是获取每条json键值对的数量，将key和value分别加入数组，list第一个值是key，其它是是value
		// 自定义爬虫数据，直接将json的value取出成list<String[]>
		System.out.println(list);
		List<String[]> stringArrayList;
		try {
			stringArrayList = CustomSpiderDataUtils.jsonStringListToStringArrayList(list);
		} catch (Exception e) {
			// 以文本返回
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
		String[] title = stringArrayList.get(0);
		// 创建Excel工作
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 创建一个sheet表
		XSSFSheet sheet = workbook.createSheet();
		// 设置列宽
		for (int i = 0; i < title.length + 1; i++) {
			sheet.setColumnWidth(i, 40 * 256);
		}
		// 创建第一行
		XSSFRow row = sheet.createRow(0);
		// 插入第一行数据
		XSSFCell cell = row.createCell(0);
		cell.setCellValue(SpiderConstant.SPIDER_KEYWORD_SPIDERDATA_ID);
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i + 1);
			cell.setCellValue(title[i]);
		}

		// 追加数据,向第二行开始加入数据 i = 1
		for (int i = 1; i < stringArrayList.size(); i++) {
			XSSFRow row2 = sheet.createRow(i);
			XSSFCell cell2 = row2.createCell(0);
			cell2.setCellValue(i);
			for (int j = 0; j < stringArrayList.get(0).length; j++) {
				cell2 = row2.createCell(j + 1);
				cell2.setCellValue(stringArrayList.get(i)[j]);
			}
		}

		// 写入文件
		CustomSpiderDataUtils.writeWBToStream(workbook, outputStream);
		byte[] bytes = outputStream.toByteArray();
		rsdm.setBytes(bytes);
		// 表格
		rsdm.setFileType(1);
		return rsdm;
	}
}
