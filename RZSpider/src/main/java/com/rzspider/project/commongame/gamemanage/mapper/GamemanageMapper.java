package com.rzspider.project.commongame.gamemanage.mapper;

import com.rzspider.project.commongame.gamemanage.domain.Gamemanage;
import java.util.List;

/**
 * 通用游戏管理 数据层
 * 
 * @author ricozhou
 * @date 2018-06-13
 */
public interface GamemanageMapper {

	/**
	 * 查询通用游戏管理信息
	 * 
	 * @param gameId
	 *            通用游戏管理ID
	 * @return 通用游戏管理信息
	 */
	public Gamemanage selectGamemanageById(Integer gameId);

	/**
	 * 查询通用游戏管理列表
	 * 
	 * @param gamemanage
	 *            通用游戏管理信息
	 * @return 通用游戏管理集合
	 */
	public List<Gamemanage> selectGamemanageList(Gamemanage gamemanage);

	/**
	 * 新增通用游戏管理
	 * 
	 * @param gamemanage
	 *            通用游戏管理信息
	 * @return 结果
	 */
	public int insertGamemanage(Gamemanage gamemanage);

	/**
	 * 修改通用游戏管理
	 * 
	 * @param gamemanage
	 *            通用游戏管理信息
	 * @return 结果
	 */
	public int updateGamemanage(Gamemanage gamemanage);

	/**
	 * 删除通用游戏管理
	 * 
	 * @param gameId
	 *            通用游戏管理ID
	 * @return 结果
	 */
	public int deleteGamemanageById(Integer gameId);

	/**
	 * 批量删除通用游戏管理
	 * 
	 * @param gameIds
	 *            需要删除的数据ID
	 * @return 结果
	 */
	public int batchDeleteGamemanage(Integer[] gameIds);

	// 校验游戏名称
	public Gamemanage checkGameNameUnique(Gamemanage gamemanage);

	// 可用游戏
	public List<Gamemanage> selectAllGameNameByStatus(Integer status);
	// 校验游戏后台id
	public Gamemanage checkgameBackIdUnique(Gamemanage gamemanage);

	public Gamemanage selectGamemanageByGameName(String gameName);

	public Gamemanage selectGamemanageByGameBackId(Integer gameBackId);

}