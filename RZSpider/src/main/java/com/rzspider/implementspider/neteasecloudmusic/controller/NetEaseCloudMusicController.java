package com.rzspider.implementspider.neteasecloudmusic.controller;

import com.rzspider.implementspider.neteasecloudmusic.service.NetEaseCloudMusicService;

//网易云音乐爬虫主控制器
public class NetEaseCloudMusicController {
	NetEaseCloudMusicService netEaseCloudMusicService = new NetEaseCloudMusicService();

	// 根据歌曲名返回搜索歌曲列表json
	// 用于返回前端
	public String getNECMDataByMusicName(String musicName, int pageNum) {
		try {
			return netEaseCloudMusicService.getNECMDataByMusicName(musicName, pageNum);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 根据歌曲id返回此歌曲播放url
	public String getNECMusicUrlById(int id) {
		return netEaseCloudMusicService.getNECMusicUrlById(id);
	}

	// 根据歌曲id返回此歌曲歌词
	public String getNECMusicLyricById(int id) {
		return netEaseCloudMusicService.getNECMusicLyricById(id);
	}
}
