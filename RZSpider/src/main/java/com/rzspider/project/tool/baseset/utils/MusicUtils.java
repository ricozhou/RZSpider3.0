package com.rzspider.project.tool.baseset.utils;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rzspider.common.utils.DateUtils;
import com.rzspider.project.tool.baseset.domain.NECMusicInfo;

public class MusicUtils {

	// 解析json
	public static List<NECMusicInfo> getMusicInfoList(String json) {
		List<NECMusicInfo> list = new ArrayList<NECMusicInfo>();
		JSONObject jo = JSON.parseObject(json);
		jo = JSON.parseObject(jo.getString("result"));
		JSONArray ja = JSONArray.parseArray(jo.getString("songs"));
		JSONObject jsonObject;
		NECMusicInfo nECMusicInfo;
		if (ja == null || ja.size() < 1) {
			return list;
		}
		for (int i = 0; i < ja.size(); i++) {
			nECMusicInfo = new NECMusicInfo();
			jsonObject = ja.getJSONObject(i);
			// 歌曲名
			nECMusicInfo.setTitle(jsonObject.getString("name"));
			// 歌曲id
			nECMusicInfo.setId(jsonObject.getString("id"));
			nECMusicInfo.setUrl(getNECMusicUrlById(jsonObject.getString("id")));
			// 时间format
			nECMusicInfo.setMusicTime(DateUtils.formatDuring(Long.valueOf(jsonObject.getString("dt"))));

			// 作者
			nECMusicInfo.setAuthor(MusicUtils.getMusicAuthorFromJsonData(jsonObject.getString("ar")));

			// 专辑
			nECMusicInfo.setAlbumName(MusicUtils.getMusicAlbumNameFromJsonData(jsonObject.getString("al")));
			// 封面
			nECMusicInfo.setPic(MusicUtils.getMusicPic(jsonObject.getString("al")));

			list.add(nECMusicInfo);
		}

		return list;
	}

	// 获取专辑名
	private static String getMusicAlbumNameFromJsonData(String data) {
		JSONObject jo = JSON.parseObject(data);
		return jo.getString("name");
	}

	// 获取封面
	private static String getMusicPic(String data) {
		JSONObject jo = JSON.parseObject(data);
		String pic = jo.getString("picUrl");
		if (pic == null) {
			return "";
		}
		return jo.getString("picUrl") + "?param=300x300";
	}

	// 获取作者
	private static String getMusicAuthorFromJsonData(String data) {
		JSONArray ja = JSONArray.parseArray(data);
		JSONObject jsonObject;
		StringBuilder author = new StringBuilder();
		for (int i = 0; i < ja.size(); i++) {
			jsonObject = ja.getJSONObject(i);
			author.append(jsonObject.getString("name") + "/");
		}
		String authorString = author.toString();
		if (authorString == null || "".equals(authorString)) {
			return "未知";
		}
		return authorString.substring(0, authorString.length() - 1);
	}

	// url
	public static String getNECMusicUrlById(String id) {
		return "http://music.163.com/song/media/outer/url?id=" + id + ".mp3";
	}
}
