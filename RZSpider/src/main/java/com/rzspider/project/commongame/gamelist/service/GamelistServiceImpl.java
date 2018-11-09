package com.rzspider.project.commongame.gamelist.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rzspider.common.constant.CommonSymbolicConstant;
import com.rzspider.common.constant.UserConstants;
import com.rzspider.common.utils.OtherUtils;
import com.rzspider.common.utils.StringUtils;
import com.rzspider.common.utils.security.ShiroUtils;
import com.rzspider.framework.config.FilePathConfig;
import com.rzspider.project.commongame.gamelist.mapper.GamelistMapper;
import com.rzspider.project.book.bookmanage.utils.ExcelUtils;
import com.rzspider.project.commongame.gamelist.domain.Gamelist;
import com.rzspider.project.commongame.gamelist.service.IGamelistService;
import com.rzspider.project.commongame.gamemanage.domain.Gamemanage;
import com.rzspider.project.system.role.service.IRoleService;

/**
 * 通用游戏列 服务层实现
 * 
 * @author ricozhou
 * @date 2018-06-13
 */
@Service
public class GamelistServiceImpl implements IGamelistService {
	@Autowired
	private GamelistMapper gamelistMapper;

	/**
	 * 查询通用游戏列信息
	 * 
	 * @param gameListId
	 *            通用游戏列ID
	 * @return 通用游戏列信息
	 */
	@Override
	public Gamelist selectGamelistById(Integer gameListId) {
		return gamelistMapper.selectGamelistById(gameListId);
	}

	/**
	 * 查询通用游戏列列表
	 * 
	 * @param gamelist
	 *            通用游戏列信息
	 * @return 通用游戏列集合
	 */
	@Override
	public List<Gamelist> selectGamelistList(Gamelist gamelist) {
		gamelist.setCreateBy(ShiroUtils.getLoginName());
		return gamelistMapper.selectGamelistList(gamelist);
	}

	/**
	 * 新增通用游戏列
	 * 
	 * @param gamelist
	 *            通用游戏列信息
	 * @return 结果
	 */
	@Override
	public int insertGamelist(Gamelist gamelist) {
		return gamelistMapper.insertGamelist(gamelist);
	}

	/**
	 * 修改通用游戏列
	 * 
	 * @param gamelist
	 *            通用游戏列信息
	 * @return 结果
	 */
	@Override
	public int updateGamelist(Gamelist gamelist) {
		return gamelistMapper.updateGamelist(gamelist);
	}

	/**
	 * 保存通用游戏列
	 * 
	 * @param gamelist
	 *            通用游戏列信息
	 * @return 结果
	 */
	@Override
	public int saveGamelist(Gamelist gamelist) {
		Integer gameListId = gamelist.getGameListId();
		int rows = 0;
		if (StringUtils.isNotNull(gameListId)) {
			gamelist.setUpdateBy(ShiroUtils.getLoginName());
			rows = gamelistMapper.updateGamelist(gamelist);
		} else {
			gamelist.setCreateBy(ShiroUtils.getLoginName());
			rows = gamelistMapper.insertGamelist(gamelist);
		}
		return rows;
	}

	/**
	 * 删除通用游戏列信息
	 * 
	 * @param gameListId
	 *            通用游戏列ID
	 * @return 结果
	 */
	@Override
	public int deleteGamelistById(Integer gameListId) {
		return gamelistMapper.deleteGamelistById(gameListId);
	}

	/**
	 * 批量删除通用游戏列对象
	 * 
	 * @param gameListIds
	 *            需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int batchDeleteGamelist(Integer[] gameListIds) {
		return gamelistMapper.batchDeleteGamelist(gameListIds);
	}

	// 校验游戏昵称
	@Override
	public String checkGameNickNameUnique(Gamelist gamelist) {
		if (gamelist.getGameListId() == null) {
			gamelist.setGameListId(-1);
		}
		Integer gameListId = gamelist.getGameListId();
		Gamelist info = gamelistMapper.checkGameNickNameUnique(gamelist);
		if (StringUtils.isNotNull(info) && StringUtils.isNotNull(info.getGameListId())
				&& info.getGameListId() != gameListId) {
			return UserConstants.TOOL_NICK_NAME_NOT_UNIQUE;
		}
		return UserConstants.TOOL_NICK_NAME_UNIQUE;
	}

	// 获取文件名
	@Override
	public String getFileName(Gamemanage gamemanage, String string) {
		String fileName;
		String basePath = FilePathConfig.getUploadGamePath() + File.separator + gamemanage.getGameBackId();
		File file = new File(basePath);
		// 所有文件名
		File[] files = file.listFiles();
		// 遍历查询
		String filesuffix = CommonSymbolicConstant.EMPTY_STRING;
		for (File file2 : files) {
			if (file2.isFile()) {
				if (file2.getName()
						.startsWith((gamemanage.getGameName() + CommonSymbolicConstant.UNDERLINE + string))) {
					// 取得后缀
					filesuffix = file2.getName()
							.substring(file2.getName().lastIndexOf(CommonSymbolicConstant.POINT) + 1);
					break;
				}
			}
		}
		if (CommonSymbolicConstant.EMPTY_STRING.equals(filesuffix)) {
			return null;
		}
		fileName = gamemanage.getGameName() + CommonSymbolicConstant.UNDERLINE + string + CommonSymbolicConstant.POINT
				+ filesuffix;
		return fileName;
	}

	// 下载取得数据
	@Override
	public byte[] downGameFile(Gamemanage gamemanage, String fileName) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		// 读取文件放入byte流中
		String filePath = FilePathConfig.getUploadGamePath() + File.separator + gamemanage.getGameBackId()
				+ File.separator + fileName;
		File file = new File(filePath);
		if (!file.exists() || !file.isFile()) {
			return null;
		}
		try {
			int length = 0;
			FileInputStream inputStream = new FileInputStream(file);
			byte[] buffer = new byte[100];
			while ((length = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, length);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outputStream.toByteArray();
	}

}
