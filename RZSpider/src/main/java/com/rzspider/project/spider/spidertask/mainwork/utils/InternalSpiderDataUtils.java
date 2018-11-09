package com.rzspider.project.spider.spidertask.mainwork.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rzspider.common.constant.CommonSymbolicConstant;
import com.rzspider.implementspider.landmarketnetwork.domain.LMNLandMessageDetail;
import com.rzspider.implementspider.landmarketnetwork.domain.LMNLandParams;
import com.rzspider.project.common.spiderdata.domain.Spiderdata;

public class InternalSpiderDataUtils {
	// 写入流
	public static void writeWBToStream(XSSFWorkbook workbook, ByteArrayOutputStream outputStream) {
		try {
			workbook.write(outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 转化json数据list
	public static List jsonStringListToObjectList(List<Spiderdata> list, Object object) throws Exception {
		List objectList = new ArrayList<Object>();
		ObjectMapper mapper = new ObjectMapper();
		for (Spiderdata spiderdata : list) {
			object = mapper.readValue(spiderdata.getJsonData(), object.getClass());
			objectList.add(object);
		}
		return objectList;
	}

	// json转object
	// 转化json数据
	public static Object jsonStringToObject(String json, Object object) {
		if (CommonSymbolicConstant.EMPTY_STRING.equals(json)) {
			return object;
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			object = mapper.readValue(json, object.getClass());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

}
