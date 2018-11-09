package com.rzspider.project.spider.spidertask.mainwork.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.log.SysoCounter;
import com.rzspider.project.common.spiderdata.domain.Spiderdata;

public class CustomSpiderDataUtils {
	// 将json字符串转为字符串数组list
	public static List<String[]> jsonStringListToStringArrayList(List<Spiderdata> list) {
		List<String[]> stringArrayList = new ArrayList<String[]>();
		if (list != null && list.size() > 0) {
			// 有序遍历
			LinkedHashMap<String, String> jsonMap = JSONObject.parseObject(list.get(0).getJsonData(),
					new TypeReference<LinkedHashMap<String, String>>() {
					});
			Set<String> key = jsonMap.keySet();
			String[] stringArray = new String[key.size()];
			key.toArray(stringArray);
			stringArrayList.add(stringArray);
			String[] stringArray2 = new String[key.size()];
			JSONObject jsonObject;
			for (int i = 0; i < list.size(); i++) {
				stringArray2 = null;
				stringArray2 = new String[key.size()];
				jsonObject = JSONObject.parseObject(list.get(i).getJsonData());
				for (int j = 0; j < stringArray.length; j++) {
					stringArray2[j] = String.valueOf(jsonObject.get(stringArray[j]));
				}
				stringArrayList.add(stringArray2);
			}
		}
		return stringArrayList;
	}

	// 写入流
	public static void writeWBToStream(XSSFWorkbook workbook, ByteArrayOutputStream outputStream) {
		try {
			workbook.write(outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
