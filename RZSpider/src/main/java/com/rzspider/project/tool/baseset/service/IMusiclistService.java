package com.rzspider.project.tool.baseset.service;

import com.rzspider.project.tool.baseset.domain.MusicInfo;
import com.rzspider.project.tool.baseset.domain.Musiclist;
import com.rzspider.project.tool.baseset.domain.NECMusicInfo;

import java.util.List;

/**
 * 歌曲 服务层
 * 
 * @author ricozhou
 * @date 2018-06-28
 */
public interface IMusiclistService {

	/**
	 * 查询歌曲信息
	 * 
	 * @param musicId
	 *            歌曲ID
	 * @return 歌曲信息
	 */
	public Musiclist selectMusiclistById(Integer musicId);

	/**
	 * 查询歌曲列表
	 * 
	 * @param musiclist
	 *            歌曲信息
	 * @return 歌曲集合
	 */
	public List<Musiclist> selectMusiclistList(Musiclist musiclist);

	/**
	 * 新增歌曲
	 * 
	 * @param musiclist
	 *            歌曲信息
	 * @return 结果
	 */
	public int insertMusiclist(Musiclist musiclist);

	/**
	 * 修改歌曲
	 * 
	 * @param musiclist
	 *            歌曲信息
	 * @return 结果
	 */
	public int updateMusiclist(Musiclist musiclist);

	/**
	 * 保存歌曲
	 * 
	 * @param musiclist
	 *            歌曲信息
	 * @return 结果
	 */
	public int saveMusiclist(Musiclist musiclist);

	/**
	 * 删除歌曲信息
	 * 
	 * @param musicId
	 *            歌曲ID
	 * @return 结果
	 */
	public int deleteMusiclistById(Integer musicId);

	/**
	 * 批量删除歌曲信息
	 * 
	 * @param musicIds
	 *            需要删除的数据ID
	 * @return 结果
	 */
	public int batchDeleteMusiclist(Integer[] musicIds);

	public int updateMusicSet(Integer basesetId);

	public int deleteMusiclistByBasesetId(Integer basesetId);

	public int batchSaveMusiclist(Musiclist musiclist);

	public List<NECMusicInfo> searchMusic(String musicName);

	public int searchAddAdd(Musiclist musiclist);

	public byte[] getExportJsonBytes(Integer[] musicIds);

	List<MusicInfo> getExportJsonObjectList(Integer[] musicIds);

}
