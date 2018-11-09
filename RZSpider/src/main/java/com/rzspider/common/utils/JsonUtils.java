package com.rzspider.common.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rzspider.project.common.spiderdata.domain.Spiderdata;
import com.rzspider.project.tool.baseset.domain.MusicInfo;

//json操作类
public class JsonUtils {
	public static List jsonArrayStringToListByKey(String json, Object key) {
		List list2 = new ArrayList();
		List<HashMap> list = JSON.parseArray(json, HashMap.class);
		for (int i = 0; i < list.size(); i++) {
			list2.add(list.get(i).get(key));
		}
		return list2;
	}

	public static List jsonArrayStringToList(String json) {
		List<HashMap> list = JSON.parseArray(json, HashMap.class);
		return list;
	}
	// 简单的后写，先写需要的

	// 音乐list转jsonstring
	public static String MusicInfoListToJsonString(List<MusicInfo> musicInfoList) {
		String jsonStr = null;
		if (musicInfoList == null || musicInfoList.size() < 1) {
			return jsonStr;
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			jsonStr = mapper.writeValueAsString(musicInfoList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonStr;
	}

	// 音乐list转jsonstring
	public static String MusicInfoListToJsonString2(List<MusicInfo> musicInfoList) {
		String json = JSON.toJSONString(musicInfoList);
		return json;
	}

	// 音乐json转list
	public static List<MusicInfo> jsonStringToMusicInfoList(String musicInfoJson) {
		return (List<MusicInfo>) jsonStringToObjectList(musicInfoJson, new MusicInfo());
	}

	// 转化json数据list
	public static List jsonStringToObjectList(String jsonString, Object object) {
		List objectList = new ArrayList<Object>();
		ObjectMapper mapper = new ObjectMapper();
		JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, object.getClass());
		try {
			objectList = mapper.readValue(jsonString, javaType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectList;
	}

	// 格式化json
	public static String formatJson2(String content) {
		StringBuffer sb = new StringBuffer();
		int index = 0;
		int count = 0;
		while (index < content.length()) {
			char ch = content.charAt(index);
			if (ch == '{' || ch == '[') {
				sb.append(ch);
				sb.append('\n');
				count++;
				for (int i = 0; i < count; i++) {
					sb.append('\t');
				}
			} else if (ch == '}' || ch == ']') {
				sb.append('\n');
				count--;
				for (int i = 0; i < count; i++) {
					sb.append('\t');
				}
				sb.append(ch);
			} else if (ch == ',') {
				sb.append(ch);
				sb.append('\n');
				for (int i = 0; i < count; i++) {
					sb.append('\t');
				}
			} else {
				sb.append(ch);
			}
			index++;
		}
		return sb.toString();
	}

	/**
	 * 返回格式化JSON字符串。
	 * 
	 * @param json
	 *            未格式化的JSON字符串。
	 * @return 格式化的JSON字符串。
	 */
	public static String formatJson(String json) {
		StringBuffer result = new StringBuffer();

		int length = json.length();
		int number = 0;
		char key = 0;

		// 遍历输入字符串。
		for (int i = 0; i < length; i++) {
			// 1、获取当前字符。
			key = json.charAt(i);

			// 2、如果当前字符是前方括号、前花括号做如下处理：
			if ((key == '[') || (key == '{')) {
				// （1）如果前面还有字符，并且字符为“：”，打印：换行和缩进字符字符串。
				if ((i - 1 > 0) && (json.charAt(i - 1) == ':')) {
					result.append('\n');
					result.append(indent(number));
				}

				// （2）打印：当前字符。
				result.append(key);

				// （3）前方括号、前花括号，的后面必须换行。打印：换行。
				result.append('\n');

				// （4）每出现一次前方括号、前花括号；缩进次数增加一次。打印：新行缩进。
				number++;
				result.append(indent(number));

				// （5）进行下一次循环。
				continue;
			}

			// 3、如果当前字符是后方括号、后花括号做如下处理：
			if ((key == ']') || (key == '}')) {
				// （1）后方括号、后花括号，的前面必须换行。打印：换行。
				result.append('\n');

				// （2）每出现一次后方括号、后花括号；缩进次数减少一次。打印：缩进。
				number--;
				result.append(indent(number));

				// （3）打印：当前字符。
				result.append(key);

				// （4）如果当前字符后面还有字符，并且字符不为“，”，打印：换行。
				if (((i + 1) < length) && (json.charAt(i + 1) != ',')) {
					result.append('\n');
				}

				// （5）继续下一次循环。
				continue;
			}

			// 4、如果当前字符是逗号。逗号后面换行，并缩进，不改变缩进次数。
			if ((key == ',')) {
				result.append(key);
				result.append('\n');
				result.append(indent(number));
				continue;
			}

			// 5、打印：当前字符。
			result.append(key);
		}

		// 去掉空行

		return result.toString().replaceAll("((\r\n)|\n)[\\s\t ]*(\\1)+", "$1");
	}

	/**
	 * 返回指定次数的缩进字符串。每一次缩进三个空格，即SPACE。
	 * 
	 * @param number
	 *            缩进次数。
	 * @return 指定缩进次数的字符串。
	 */
	private static String indent(int number) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < number; i++) {
			result.append("    ");
		}
		return result.toString();
	}

	// 获取转义后的json字符串
	public static String getEscapeJson(String json) {
		if (json == null) {
			return json;
		}
		return json.replaceAll("\\{\"", "{'").replaceAll("\":\"", "':'").replaceAll("\",\"", "','")
				.replaceAll("\":", "':").replaceAll(",\"", ",'").replaceAll("\"\\}", "'}").replaceAll("\"", "")
				.replaceAll("'", "\"").replaceAll("\\\\", "?");
	}

	// 判断json是否正确
	public static boolean isCorrectJsonString(String json) {
		try {
			JSONObject jsonStr = JSONObject.parseObject(json);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
