package com.rzspider.project.commongame.gamelist.mapper;

import com.rzspider.project.commongame.gamelist.domain.Gamelist;
import java.util.List;

/**
 * 通用游戏列 数据层
 * 
 * @author ricozhou
 * @date 2018-06-13
 */
public interface GamelistMapper {

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
	 * 删除通用游戏列
	 * 
	 * @param gameListId
	 *            通用游戏列ID
	 * @return 结果
	 */
	public int deleteGamelistById(Integer gameListId);

	/**
	 * 批量删除通用游戏列
	 * 
	 * @param gameListIds
	 *            需要删除的数据ID
	 * @return 结果
	 */
	public int batchDeleteGamelist(Integer[] gameListIds);

	// 校验游戏昵称
	public Gamelist checkGameNickNameUnique(Gamelist gamelist);

}