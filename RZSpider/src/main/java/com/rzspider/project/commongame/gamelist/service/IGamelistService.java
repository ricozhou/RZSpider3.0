package com.rzspider.project.commongame.gamelist.service;

import com.rzspider.project.commongame.gamelist.domain.Gamelist;
import com.rzspider.project.commongame.gamemanage.domain.Gamemanage;

import java.util.List;

/**
 * 通用游戏列 服务层
 * 
 * @author ricozhou
 * @date 2018-06-13
 */
public interface IGamelistService {

	/**
	 * 查询通用游戏列信息
	 * 
	 * @param gameListId
	 *            通用游戏列ID
	 * @return 通用游戏列信息
	 */
	public Gamelist selectGamelistById(Integer gameListId);

	/**
	 * 查询通用游戏列列表
	 * 
	 * @param gamelist
	 *            通用游戏列信息
	 * @return 通用游戏列集合
	 */
	public List<Gamelist> selectGamelistList(Gamelist gamelist);

	/**
	 * 新增通用游戏列
	 * 
	 * @param gamelist
	 *            通用游戏列信息
	 * @return 结果
	 */
	public int insertGamelist(Gamelist gamelist);

	/**
	 * 修改通用游戏列
	 * 
	 * @param gamelist
	 *            通用游戏列信息
	 * @return 结果
	 */
	public int updateGamelist(Gamelist gamelist);

	/**
	 * 保存通用游戏列
	 * 
	 * @param gamelist
	 *            通用游戏列信息
	 * @return 结果
	 */
	public int saveGamelist(Gamelist gamelist);

	/**
	 * 删除通用游戏列信息
	 * 
	 * @param gameListId
	 *            通用游戏列ID
	 * @return 结果
	 */
	public int deleteGamelistById(Integer gameListId);

	/**
	 * 批量删除通用游戏列信息
	 * 
	 * @param gameListIds
	 *            需要删除的数据ID
	 * @return 结果
	 */
	public int batchDeleteGamelist(Integer[] gameListIds);

	// 校验游戏昵称
	public String checkGameNickNameUnique(Gamelist gamelist);

	public String getFileName(Gamemanage gamemanage, String string);

	public byte[] downGameFile(Gamemanage gamemanage, String setupexeFileName);

}
