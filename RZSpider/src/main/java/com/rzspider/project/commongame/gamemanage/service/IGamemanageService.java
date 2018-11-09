package com.rzspider.project.commongame.gamemanage.service;

import com.rzspider.framework.web.domain.Message;
import com.rzspider.project.commongame.gamemanage.domain.Gamemanage;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/**
 * 通用游戏管理 服务层
 * 
 * @author ricozhou
 * @date 2018-06-13
 */
public interface IGamemanageService {

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
	 * 保存通用游戏管理
	 * 
	 * @param gamemanage
	 *            通用游戏管理信息
	 * @return 结果
	 */
	public int saveGamemanage(Gamemanage gamemanage);

	/**
	 * 删除通用游戏管理信息
	 * 
	 * @param gameId
	 *            通用游戏管理ID
	 * @return 结果
	 */
	public int deleteGamemanageById(Integer gameId);

	/**
	 * 批量删除通用游戏管理信息
	 * 
	 * @param gameIds
	 *            需要删除的数据ID
	 * @return 结果
	 */
	public int batchDeleteGamemanage(Integer[] gameIds);

	// 校验游戏名称
	public String checkGameNameUnique(Gamemanage gamemanage);

	// 可用游戏
	public List<Gamemanage> selectAllGameNameByStatus(Integer status);

	// 校验游戏后台id
	public String checkgameBackIdUnique(Gamemanage gamemanage);

	public Message uploadGameFile(MultipartFile file, Gamemanage gamemanage);

	// 转移文件
	public boolean copyGameFileToFolder(Gamemanage gamemanage);

	// 更新文件名
//	public boolean updateGameFileName(Gamemanage gamemanage, Gamemanage gamemanage2);

	public Gamemanage selectGamemanageByGameName(String gameName);

	public Gamemanage selectGamemanageByGameBackId(Integer gameBackId);

}
