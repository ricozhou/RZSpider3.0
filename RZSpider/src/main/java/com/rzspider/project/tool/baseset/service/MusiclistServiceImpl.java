package com.rzspider.project.tool.baseset.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rzspider.common.constant.project.ToolConstant;
import com.rzspider.common.utils.JsonUtils;
import com.rzspider.common.utils.StringUtils;
import com.rzspider.common.utils.security.ShiroUtils;
import com.rzspider.implementspider.neteasecloudmusic.controller.NetEaseCloudMusicController;
import com.rzspider.project.tool.baseset.mapper.BasesetMapper;
import com.rzspider.project.tool.baseset.mapper.MusiclistMapper;
import com.rzspider.project.tool.baseset.domain.Baseset;
import com.rzspider.project.tool.baseset.domain.MusicInfo;
import com.rzspider.project.tool.baseset.domain.Musiclist;
import com.rzspider.project.tool.baseset.domain.NECMusicInfo;
import com.rzspider.project.tool.baseset.service.IMusiclistService;
import com.rzspider.project.tool.baseset.utils.MusicUtils;

/**
 * 歌曲 服务层实现
 * 
 * @author ricozhou
 * @date 2018-06-28
 */
@Service
public class MusiclistServiceImpl implements IMusiclistService {
	@Autowired
	private MusiclistMapper musiclistMapper;
	@Autowired
	private IBasesetService basesetService;
	@Autowired
	private BasesetMapper basesetMapper;
	// 实例化爬虫
	NetEaseCloudMusicController netEaseCloudMusicController = new NetEaseCloudMusicController();

	/**
	 * 查询歌曲信息
	 * 
	 * @param musicId
	 *            歌曲ID
	 * @return 歌曲信息
	 */
	@Override
	public Musiclist selectMusiclistById(Integer musicId) {
		return musiclistMapper.selectMusiclistById(musicId);
	}

	/**
	 * 查询歌曲列表
	 * 
	 * @param musiclist
	 *            歌曲信息
	 * @return 歌曲集合
	 */
	@Override
	public List<Musiclist> selectMusiclistList(Musiclist musiclist) {
		return musiclistMapper.selectMusiclistList(musiclist);
	}

	/**
	 * 新增歌曲
	 * 
	 * @param musiclist
	 *            歌曲信息
	 * @return 结果
	 */
	@Override
	public int insertMusiclist(Musiclist musiclist) {
		return musiclistMapper.insertMusiclist(musiclist);
	}

	/**
	 * 修改歌曲
	 * 
	 * @param musiclist
	 *            歌曲信息
	 * @return 结果
	 */
	@Override
	public int updateMusiclist(Musiclist musiclist) {
		return musiclistMapper.updateMusiclist(musiclist);
	}

	/**
	 * 保存歌曲
	 * 
	 * @param musiclist
	 *            歌曲信息
	 * @return 结果
	 */
	@Override
	public int saveMusiclist(Musiclist musiclist) {
		Integer musicId = musiclist.getMusicId();
		int rows = 0;
		if (StringUtils.isNotNull(musicId)) {
			musiclist.setUpdateBy(ShiroUtils.getLoginName());
			rows = musiclistMapper.updateMusiclist(musiclist);
		} else {
			musiclist.setCreateBy(ShiroUtils.getLoginName());
			rows = musiclistMapper.insertMusiclist(musiclist);
		}
		// 完事同步更新json到baseset表
		rows = updateMusicSet(musiclist.getBasesetId());
		return rows;
	}

	/**
	 * 删除歌曲信息
	 * 
	 * @param musicId
	 *            歌曲ID
	 * @return 结果
	 */
	@Override
	public int deleteMusiclistById(Integer musicId) {
		int rows = 0;
		// 完事同步更新json到baseset表
		Musiclist musiclist = musiclistMapper.selectMusiclistById(musicId);
		rows = musiclistMapper.deleteMusiclistById(musicId);
		if (musiclist == null || musiclist.getBasesetId() == null) {
			return rows;
		}
		rows = updateMusicSet(musiclist.getBasesetId());
		return rows;
	}

	/**
	 * 批量删除歌曲对象
	 * 
	 * @param musicIds
	 *            需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int batchDeleteMusiclist(Integer[] musicIds) {
		int rows = 0;
		// 完事同步更新json到baseset表
		int basesetId = -1;
		if (musicIds != null && musicIds.length > 0) {
			Musiclist musiclist = musiclistMapper.selectMusiclistById(musicIds[0]);
			if (musiclist != null) {
				basesetId = musiclist.getBasesetId();
			}
		}
		rows = musiclistMapper.batchDeleteMusiclist(musicIds);
		// 完事同步更新json到baseset表
		rows = updateMusicSet(basesetId);
		return rows;
	}

	/**
	 * 同步更新歌曲对象
	 * 
	 */
	@Override
	public int updateMusicSet(Integer basesetId) {
		int rows = 0;
		if (basesetId == null) {
			return 1;
		}
		// 根据baseset查询所有的音乐
		List<MusicInfo> musicInfoList = musiclistMapper.selectMusiclistListByBasesetId(basesetId);
		// 转json
		String musicInfoJson = JsonUtils.MusicInfoListToJsonString(musicInfoList);
		// 存入baseset数据库
		Baseset baseset = new Baseset();
		baseset.setUpdateBy(ShiroUtils.getLoginName());
		baseset.setBasesetId(basesetId);
		baseset.setMusicInfo(musicInfoJson);
		rows = basesetService.updateMusicInfo(baseset);
		return rows;
	}

	// 根据basesetid删除
	@Override
	public int deleteMusiclistByBasesetId(Integer basesetId) {
		return musiclistMapper.deleteMusiclistByBasesetId(basesetId);
	}

	// 批量新增
	@Override
	public int batchSaveMusiclist(Musiclist musiclist) {
		// 取出json
		String musicInfoJson = musiclist.getTitle();
		System.out.println(musicInfoJson);
		// json转list
		List<MusicInfo> musicInfoList = JsonUtils.jsonStringToMusicInfoList(musicInfoJson);
		System.out.println(musicInfoList);
		if (musicInfoList == null || musicInfoList.size() < 1) {
			return 0;
		}
		Musiclist musiclist2 = new Musiclist();
		int rows = 0;
		for (MusicInfo musicInfo : musicInfoList) {
			musiclist2.setBasesetId(musiclist.getBasesetId());
			musiclist2.setTitle(
					musicInfo.getTitle() == null ? ToolConstant.TOOL_BASESET_MUSIC_NOCONTENT : musicInfo.getTitle());
			musiclist2.setAuthor(
					musicInfo.getAuthor() == null ? ToolConstant.TOOL_BASESET_MUSIC_NOCONTENT : musicInfo.getAuthor());
			musiclist2.setUrl(musicInfo.getUrl());
			musiclist2.setPic(musicInfo.getPic());
			musiclist2.setLrc(musicInfo.getLrc());
			rows = saveMusiclist(musiclist2);
		}
		return rows;
	}

	// 搜索歌曲
	@Override
	public List<NECMusicInfo> searchMusic(String musicName) {
		List<NECMusicInfo> list = null;
		// 启动爬虫
		String data = netEaseCloudMusicController.getNECMDataByMusicName(musicName, 100);
		System.out.println(data);
		if (data != null) {
			list = MusicUtils.getMusicInfoList(data);
		}
		return list;
	}

	// 搜索歌曲单个添加
	@Override
	public int searchAddAdd(Musiclist musiclist) {
		// 启动爬虫根据id查询详情
		musiclist.setUrl(netEaseCloudMusicController.getNECMusicUrlById(musiclist.getMusicId()));
		musiclist.setLrc(netEaseCloudMusicController.getNECMusicLyricById(musiclist.getMusicId()));
		musiclist.setCreateBy(ShiroUtils.getLoginName());
		musiclist.setMusicId(null);
		int rows = musiclistMapper.insertMusiclist(musiclist);

		// 完事同步更新json到baseset表
		rows = updateMusicSet(musiclist.getBasesetId());
		return rows;
	}

	// 获取jsonlist
	@Override
	public List<MusicInfo> getExportJsonObjectList(Integer[] musicIds) {
		return musiclistMapper.getExportJsonObjectList(musicIds);
	}

	// 获取jsonbyte
	@Override
	public byte[] getExportJsonBytes(Integer[] musicIds) {
		List<MusicInfo> musicInfoList = getExportJsonObjectList(musicIds);
		// 转json
		String musicInfoJson = JsonUtils.MusicInfoListToJsonString2(musicInfoList);
		// 去除特殊字符串
		System.out.println(musicInfoJson);
		if (musicInfoJson == null) {
			return null;
		}
		return JsonUtils.formatJson(musicInfoJson).getBytes();
	}
}
