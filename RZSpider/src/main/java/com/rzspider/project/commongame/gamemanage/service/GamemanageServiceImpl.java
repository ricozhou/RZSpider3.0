package com.rzspider.project.commongame.gamemanage.service;

import java.io.File;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rzspider.common.constant.FileExtensionConstant;
import com.rzspider.common.constant.FileMessageConstant;
import com.rzspider.common.constant.UserConstants;
import com.rzspider.common.utils.StringUtils;
import com.rzspider.common.utils.security.ShiroUtils;
import com.rzspider.framework.config.FilePathConfig;
import com.rzspider.framework.web.domain.Message;
import com.rzspider.project.commongame.gamemanage.mapper.GamemanageMapper;
import com.rzspider.project.common.file.utilt.FileUtils;
import com.rzspider.project.commongame.gamemanage.domain.Gamemanage;
import com.rzspider.project.commongame.gamemanage.service.IGamemanageService;
import com.rzspider.project.spider.spidermanage.domain.SpiderManage;

/**
 * 通用游戏管理 服务层实现
 * 
 * @author ricozhou
 * @date 2018-06-13
 */
@Service
public class GamemanageServiceImpl implements IGamemanageService {
	@Autowired
	private GamemanageMapper gamemanageMapper;

	/**
	 * 查询通用游戏管理信息
	 * 
	 * @param gameId
	 *            通用游戏管理ID
	 * @return 通用游戏管理信息
	 */
	@Override
	public Gamemanage selectGamemanageById(Integer gameId) {
		return gamemanageMapper.selectGamemanageById(gameId);
	}

	/**
	 * 查询通用游戏管理列表
	 * 
	 * @param gamemanage
	 *            通用游戏管理信息
	 * @return 通用游戏管理集合
	 */
	@Override
	public List<Gamemanage> selectGamemanageList(Gamemanage gamemanage) {
		return gamemanageMapper.selectGamemanageList(gamemanage);
	}

	/**
	 * 新增通用游戏管理
	 * 
	 * @param gamemanage
	 *            通用游戏管理信息
	 * @return 结果
	 */
	@Override
	public int insertGamemanage(Gamemanage gamemanage) {
		return gamemanageMapper.insertGamemanage(gamemanage);
	}

	/**
	 * 修改通用游戏管理
	 * 
	 * @param gamemanage
	 *            通用游戏管理信息
	 * @return 结果
	 */
	@Override
	public int updateGamemanage(Gamemanage gamemanage) {
		return gamemanageMapper.updateGamemanage(gamemanage);
	}

	/**
	 * 保存通用游戏管理
	 * 
	 * @param gamemanage
	 *            通用游戏管理信息
	 * @return 结果
	 */
	@Override
	public int saveGamemanage(Gamemanage gamemanage) {
		Integer gameId = gamemanage.getGameId();
		int rows = 0;
		if (StringUtils.isNotNull(gameId)) {
			gamemanage.setUpdateBy(ShiroUtils.getLoginName());
			rows = gamemanageMapper.updateGamemanage(gamemanage);
		} else {
			gamemanage.setCreateBy(ShiroUtils.getLoginName());
			rows = gamemanageMapper.insertGamemanage(gamemanage);
		}
		return rows;
	}

	/**
	 * 删除通用游戏管理信息
	 * 
	 * @param gameId
	 *            通用游戏管理ID
	 * @return 结果
	 */
	@Override
	public int deleteGamemanageById(Integer gameId) {
		return gamemanageMapper.deleteGamemanageById(gameId);
	}

	/**
	 * 批量删除通用游戏管理对象
	 * 
	 * @param gameIds
	 *            需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int batchDeleteGamemanage(Integer[] gameIds) {
		return gamemanageMapper.batchDeleteGamemanage(gameIds);
	}

	// 校验游戏名称
	@Override
	public String checkGameNameUnique(Gamemanage gamemanage) {
		if (gamemanage.getGameId() == null) {
			gamemanage.setGameId(-1);
		}
		Integer gameId = gamemanage.getGameId();
		Gamemanage info = gamemanageMapper.checkGameNameUnique(gamemanage);
		if (StringUtils.isNotNull(info) && StringUtils.isNotNull(info.getGameId()) && info.getGameId() != gameId) {
			return UserConstants.TOOL_NAME_NOT_UNIQUE;
		}
		return UserConstants.TOOL_NAME_UNIQUE;
	}

	// 校验游戏后台id
	@Override
	public String checkgameBackIdUnique(Gamemanage gamemanage) {
		if (gamemanage.getGameId() == null) {
			gamemanage.setGameId(-1);
		}
		Integer gameId = gamemanage.getGameId();
		Gamemanage info = gamemanageMapper.checkgameBackIdUnique(gamemanage);
		if (StringUtils.isNotNull(info) && StringUtils.isNotNull(info.getGameId()) && info.getGameId() != gameId) {
			return UserConstants.TOOL_BACK_ID_NOT_UNIQUE;
		}
		return UserConstants.TOOL_BACK_ID_UNIQUE;
	}

	// 可用游戏
	@Override
	public List<Gamemanage> selectAllGameNameByStatus(Integer status) {
		return gamemanageMapper.selectAllGameNameByStatus(status);
	}

	// 统一处理
	@Override
	public Message uploadGameFile(MultipartFile file, Gamemanage gamemanage) {
		// 判断文件大小，格式等等
		try {
			FileUtils.assertAllowed(file);
		} catch (FileSizeLimitExceededException e1) {
			e1.printStackTrace();
			return Message.error(FileMessageConstant.FILE_MESSAGE_SIZE_GREATER_HUNDRED_M);
		}
		// 原始名字
		String fileName = file.getOriginalFilename();
		if (!fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_COMPRESSEDFILE_ZIP)
				&& !fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_COMPRESSEDFILE_RAR)
				&& !fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_COMPRESSEDFILE_7Z)) {
			return Message.error(FileMessageConstant.FILE_MESSAGE_FORMAT_INCORRENT);
		}
		return Message.success();
	}

	// （更换思路，不再重命名，下载的时候在重命名）
	// 转移文件
	@Override
	public boolean copyGameFileToFolder(Gamemanage gamemanage) {
		String basePath1 = FilePathConfig.getUploadGamePath() + File.separator + gamemanage.getGameBackId();
		String basePath2 = FilePathConfig.getUploadGamePath();

		if (gamemanage.getSrcFileName() != null && !gamemanage.getSrcFileName().isEmpty()) {
			if (!FileUtils.copyFileToFolder(basePath2 + File.separator + gamemanage.getSrcFileName(),
					basePath1 + File.separator + gamemanage.getSrcFileName())) {
				return false;
			}
			if (StringUtils.isNotNull(gamemanage.getGameId())) {
				// 先把以前的文件如果存在则删除先
				// 查询原名
				Gamemanage gamemanage2 = gamemanageMapper.selectGamemanageById(gamemanage.getGameId());
				// 删除文件
				if (!FileUtils.deleteFile(basePath1 + File.separator + gamemanage2.getSrcFileName())) {
					return false;
				}
			}

		}
		if (gamemanage.getExecexeFileName() != null && !gamemanage.getExecexeFileName().isEmpty()) {
			if (!FileUtils.copyFileToFolder(basePath2 + File.separator + gamemanage.getExecexeFileName(),
					basePath1 + File.separator + gamemanage.getExecexeFileName())) {
				return false;
			}
			if (StringUtils.isNotNull(gamemanage.getGameId())) {
				// 先把以前的文件如果存在则删除先
				// 查询原名
				Gamemanage gamemanage2 = gamemanageMapper.selectGamemanageById(gamemanage.getGameId());
				// 删除文件
				if (!FileUtils.deleteFile(basePath1 + File.separator + gamemanage2.getExecexeFileName())) {
					return false;
				}
			}
		}
		if (gamemanage.getSetupexeName() != null && !gamemanage.getSetupexeName().isEmpty()) {
			if (!FileUtils.copyFileToFolder(basePath2 + File.separator + gamemanage.getSetupexeName(),
					basePath1 + File.separator + gamemanage.getSetupexeName())) {
				return false;
			}
			if (StringUtils.isNotNull(gamemanage.getGameId())) {
				// 先把以前的文件如果存在则删除先
				// 查询原名
				Gamemanage gamemanage2 = gamemanageMapper.selectGamemanageById(gamemanage.getGameId());
				// 删除文件
				if (!FileUtils.deleteFile(basePath1 + File.separator + gamemanage2.getSetupexeName())) {
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public Gamemanage selectGamemanageByGameName(String gameName) {
		return gamemanageMapper.selectGamemanageByGameName(gameName);
	}

	@Override
	public Gamemanage selectGamemanageByGameBackId(Integer gameBackId) {
		return gamemanageMapper.selectGamemanageByGameBackId(gameBackId);
	}

}
