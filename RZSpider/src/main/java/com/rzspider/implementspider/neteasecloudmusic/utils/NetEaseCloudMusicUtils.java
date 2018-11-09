package com.rzspider.implementspider.neteasecloudmusic.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;

//参考zb代码
public class NetEaseCloudMusicUtils {

	public static HttpURLConnection openConnection(String urlString) throws Exception {
		URL url = new URL(urlString);
		return (HttpURLConnection) url.openConnection();
	}

	public static String readInput(InputStream is, String encode) {
		StringBuilder content = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, encode));) {
			String line = "";
			while ((line = reader.readLine()) != null) {
				content.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content.toString();
	}

	public static String formatLyricJson(String lyricJson) {
		if (lyricJson == null) {
			return "";
		}

		if (StringUtils.isEmpty(lyricJson)) {
			return "";
		}
		JSONObject obj = JSONObject.parseObject(lyricJson);
		int code = obj.getIntValue("code");
		if (code != 200) {
			return "";
		}
		Boolean uncollected = obj.getBoolean("uncollected");
		if (uncollected != null && uncollected) {
			return "";
		}
		obj = obj.getJSONObject("lrc");
		if (obj == null) {
			return "";
		}
		String lyric = obj.getString("lyric");
		if (lyric != null) {
			lyric = lyric.replaceAll("(\\.[0-9]*)", "");
		}
		return lyric;
	}

}
