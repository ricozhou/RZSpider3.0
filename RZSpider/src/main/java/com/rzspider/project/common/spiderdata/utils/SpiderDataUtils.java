package com.rzspider.project.common.spiderdata.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rzspider.common.constant.OtherConstant;
import com.rzspider.common.constant.project.SpiderConstant;
import com.rzspider.common.utils.JsonUtils;
import com.rzspider.project.common.spiderdata.domain.SpiderDataColumns;
import com.rzspider.project.common.spiderdata.domain.SpiderDataText;
import com.rzspider.project.common.spiderdata.domain.Spiderdata;

//spider通用工具
public class SpiderDataUtils {
	// spiderdatalist转stringlist
	public static List<JSONObject> spiderDataListToJsonStringList(List<Spiderdata> list) {
		List<JSONObject> stringList = new ArrayList<JSONObject>();
		for (Spiderdata sd : list) {
			stringList.add(JSONObject.parseObject(sd.getJsonData()));
		}
		return stringList;
	}

	// spiderdatalist转stringlist,解析出错以文本显示
	public static List<JSONObject> spiderDataListToJsonStringListByText(List<Spiderdata> list) {
		List<JSONObject> stringList = new ArrayList<JSONObject>();
		SpiderDataText sdt = new SpiderDataText();
		for (Spiderdata sd : list) {
			sdt.setTextformat(sd.getJsonData());
			stringList.add(JSONObject.parseObject(JSON.toJSONString(sdt)));
		}
		return stringList;
	}

	// 拼接column
	public static String[] getJsonColumnFromJsonDataString(List<Spiderdata> list) {

		String jsonStr = null;
		SpiderDataColumns sdc = new SpiderDataColumns();
		List<SpiderDataColumns> sdcList = new ArrayList<SpiderDataColumns>();
		ObjectMapper mapper = new ObjectMapper();
		// 0是正常json，1是文本
		int flag = 0;

		// 判断数据是否是在正确的json，只要有不正确的则以文本显示
		if (isAllCorrectJson(list)) {
			// 有序遍历
			LinkedHashMap<String, String> jsonMap = JSONObject.parseObject(list.get(0).getJsonData(),
					new TypeReference<LinkedHashMap<String, String>>() {
					});
			Set<String> key = jsonMap.keySet();
			String[] stringArray = new String[key.size()];
			key.toArray(stringArray);
			for (String str : stringArray) {
				sdc = new SpiderDataColumns();
				sdc.setField(str);
				sdc.setTitle(str);
				// 居中显示
				sdc.setAlign(OtherConstant.OTHER_STRING_CENTER);
				sdcList.add(sdc);
			}
			try {
				jsonStr = mapper.writeValueAsString(sdcList);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		} else {
			// 解析出错
			flag = 1;
			sdc.setField(SpiderConstant.SPIDER_KEYWORD_SPIDERDATA_OTHERFORMAT_FIELD);
			sdc.setTitle(SpiderConstant.SPIDER_KEYWORD_SPIDERDATA_OTHERFORMAT_TITLE);
			// 居中显示
			sdc.setAlign(OtherConstant.OTHER_STRING_CENTER);
			sdcList.clear();
			sdcList.add(sdc);
			try {
				jsonStr = mapper.writeValueAsString(sdcList);
			} catch (JsonProcessingException e1) {
				e1.printStackTrace();
			}
		}
		return new String[] { jsonStr, String.valueOf(flag) };
	}

	// 判断是否包含错误json
	public static boolean isAllCorrectJson(List<Spiderdata> list) {
		for (Spiderdata sd : list) {
			if (!JsonUtils.isCorrectJsonString(sd.getJsonData())) {
				return false;
			}
		}
		return true;
	}
}
