package com.rzspider.implementspider.commonutils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rzspider.common.utils.spring.SpringUtils;
import com.rzspider.project.common.spiderdata.domain.Spiderdata;
import com.rzspider.project.common.spiderdata.service.ISpiderdataService;

public class JavaCommonSpiderUtils {
	// 实例化非常重要，线程里无法直接使用注解
	ISpiderdataService spiderDataService = (ISpiderdataService) SpringUtils.getBean(ISpiderdataService.class);

	// 插入数据
	public void insertListDataToDB(Integer taskInfoId, List<String> jsonDataList, int flag) {
		Spiderdata sd = new Spiderdata();
		if (jsonDataList != null && jsonDataList.size() > 0) {
			// 爬虫任务详情id
			sd.setTaskInfoId(taskInfoId);
			// 标志
			sd.setTaskFlag(flag);
			for (String jsonData : jsonDataList) {
				// 主要数据
				sd.setJsonData(jsonData);
				spiderDataService.saveData(sd);
			}
		}
	}

	// 处理json转为对象
	public static Object jsonStringToObject(String json, Object object) {
		if ("".equals(json)) {
			return object;
		}
		object = JSONObject.parseObject(json, object.getClass());
		return object;
	}

	// 处理json(value)转为list
	public static List<String> jsonStringToList(String json) {
		List<String> objectList = new ArrayList<String>();
		// 有序遍历
		LinkedHashMap<String, String> jsonMap = JSONObject.parseObject(json,
				new TypeReference<LinkedHashMap<String, String>>() {
				});
		for (Entry<String, String> entry : jsonMap.entrySet()) {
			objectList.add(entry.getValue());
		}
		return objectList;
	}

	// 处理json转为数组
	public static String[] jsonStringToArray(String json) {
		List<String> objectList = jsonStringToList(json);
		if (objectList == null || objectList.size() < 1) {
			return null;
		}
		String[] arrayJson = new String[objectList.size()];
		objectList.toArray(arrayJson);
		return arrayJson;
	}
	// 以上是参数转换

	// 下面是爬取的数据转换
	// 对象转json
	public static String objectToJsonString(Object object) {
		String jsonString = JSONObject.toJSONString(object);
		return jsonString;
	}

	// list对象转json字符串list
	public static List<String> objectListToJsonStringList(List objectList) {
		List<String> stringList = new ArrayList<String>();
		String jsonStr = null;
		if (objectList == null || objectList.size() < 1) {
			return stringList;
		}
		for (Object object : objectList) {
			stringList.add(JSONObject.toJSONString(object));
		}

		return stringList;
	}

	// json字符串转list字符串
	public static List<String> jsonStringToJsonStringList(String jsonString) {
		List<String> stringList = new ArrayList<String>();
		if (jsonString == null) {
			return stringList;
		}
		JSONArray j = JSONArray.parseArray(jsonString);
		for (int i = 0; i < j.size(); i++) {
			stringList.add(j.get(i).toString());
		}

		return stringList;
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
}
