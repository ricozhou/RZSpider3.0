package com.rzspider.project.commongame.gamemanage.controller;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.tomcat.util.http.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.rzspider.project.common.file.FileType;
import com.rzspider.project.common.file.domain.FileDao;
import com.rzspider.project.common.file.service.IFileService;
import com.rzspider.project.common.file.utilt.FileUtils;
import com.rzspider.project.commongame.gamemanage.domain.Gamemanage;
import com.rzspider.project.commongame.gamemanage.service.IGamemanageService;
import com.rzspider.project.spider.codeType.domain.CodeType;
import com.rzspider.project.spider.spidermanage.domain.SpiderManage;
import com.rzspider.common.constant.CommonConstant;
import com.rzspider.common.constant.FileMessageConstant;
import com.rzspider.common.constant.project.CommonGameConstant;
import com.rzspider.common.utils.FileUploadUtils;
import com.rzspider.common.utils.StringUtils;
import com.rzspider.framework.config.FilePathConfig;
import com.rzspider.framework.web.controller.BaseController;
import com.rzspider.framework.web.page.TableDataInfo;
import com.rzspider.framework.web.domain.Message;

/**
 * 通用游戏管理 信息操作处理
 * 
 * @author ricozhou
 * @date 2018-06-13
 */
@Controller
@RequestMapping("/commongame/gamemanage")
public class GamemanageController extends BaseController {
	private String prefix = "commongame/gamemanage";

	@Autowired
	private IGamemanageService gamemanageService;
	@Autowired
	private IFileService fileService;

	@GetMapping()
	@RequiresPermissions("commongame:gamemanage:view")
	public String gamemanage() {
		return prefix + "/gamemanage";
	}

	/**
	 * 查询通用游戏管理列表
	 */
	@RequiresPermissions("commongame:gamemanage:list")
	@GetMapping("/list")
	@ResponseBody
	public TableDataInfo list(Gamemanage gamemanage) {
		startPage();
		List<Gamemanage> list = gamemanageService.selectGamemanageList(gamemanage);
		return getDataTable(list);
	}

	/**
	 * 新增通用游戏管理
	 */
	@RequiresPermissions("commongame:gamemanage:add")
	@GetMapping("/add")
	public String add() {
		return prefix + "/add";
	}

	/**
	 * 修改通用游戏管理
	 */
	@RequiresPermissions("commongame:gamemanage:edit")
	@GetMapping("/edit/{gameId}")
	public String edit(@PathVariable("gameId") Integer gameId, Model model) {
		Gamemanage gamemanage = gamemanageService.selectGamemanageById(gameId);
		model.addAttribute("gamemanage", gamemanage);
		return prefix + "/edit";
	}

	/**
	 * 保存通用游戏管理
	 */
	@RequiresPermissions("commongame:gamemanage:save")
	@Transactional(rollbackFor = Exception.class)
	@PostMapping("/save")
	@ResponseBody
	public Message save(Gamemanage gamemanage) {
		// check文件名
		// 校验文件名
		if (!FileUtils.isValidFileName(gamemanage.getGameName())
				|| !FileUtils.isValidFileName(String.valueOf(gamemanage.getGameBackId()))) {
			return Message.error(CommonGameConstant.COMMON_GAME_GAME_NAME_ILLEGAL);
		}

		// 创建文件夹
		String filePath = FilePathConfig.getUploadGamePath() + File.separator + gamemanage.getGameBackId();
		if (!FileUtils.createFolder(filePath)) {
			return Message.error(FileMessageConstant.FILE_MESSAGE_FILE_UNKNOWN_MISTAKE);
		}
		// 转移文件（更换思路，不再重命名，下载的时候在重命名）
		if (!gamemanageService.copyGameFileToFolder(gamemanage)) {
			return Message.error(FileMessageConstant.FILE_MESSAGE_FILE_SAVE_FAILED);
		}

		// 保存至数据库
		if (gamemanageService.saveGamemanage(gamemanage) > 0) {
			return Message.success();
		}
		return Message.error();
	}

	/**
	 * 删除通用游戏管理
	 */
	@RequiresPermissions("commongame:gamemanage:remove")
	@Transactional(rollbackFor = Exception.class)
	@PostMapping("/remove/{gameId}")
	@ResponseBody
	public Message remove(@PathVariable("gameId") Integer gameId) {
		// 查询后台id删除文件夹
		Gamemanage gamemanage = gamemanageService.selectGamemanageById(gameId);
		// 删除文件夹
		if (gamemanage != null) {
			String filePath = FilePathConfig.getUploadGamePath() + File.separator + gamemanage.getGameBackId();
			if (!FileUtils.deleteFile(filePath)) {
				return Message.error(FileMessageConstant.FILE_MESSAGE_FILE_DELETE_FAILED);
			}
		}

		if (gamemanageService.deleteGamemanageById(gameId) > 0) {
			return Message.success();
		}
		return Message.error();
	}

	/**
	 * 批量删除通用游戏管理
	 */
	@RequiresPermissions("commongame:gamemanage:batchRemove")
	@Transactional(rollbackFor = Exception.class)
	@PostMapping("/batchRemove")
	@ResponseBody
	public Message remove(@RequestParam("ids[]") Integer[] gameIds) {
		// 查询后台id删除文件夹
		Gamemanage gamemanage;
		for (Integer gameId : gameIds) {
			// 查询后台id删除文件夹
			gamemanage = gamemanageService.selectGamemanageById(gameId);
			// 删除文件夹
			if (gamemanage != null) {
				String filePath = FilePathConfig.getUploadGamePath() + File.separator + gamemanage.getGameBackId();
				if (!FileUtils.deleteFile(filePath)) {
					return Message.error(FileMessageConstant.FILE_MESSAGE_FILE_DELETE_FAILED);
				}
			}
		}
		int rows = gamemanageService.batchDeleteGamemanage(gameIds);
		if (rows > 0) {
			return Message.success();
		}
		return Message.error();
	}

	/**
	 * 校验游戏名称
	 */
	@PostMapping("/checkGameNameUnique")
	@ResponseBody
	public String checkGameNameUnique(Gamemanage gamemanage) {
		String uniqueFlag = CommonConstant.UNIQUE;
		if (gamemanage != null) {
			uniqueFlag = gamemanageService.checkGameNameUnique(gamemanage);
		}
		return uniqueFlag;
	}

	/**
	 * 校验游戏后台id
	 */
	@PostMapping("/checkGameBackIdUnique")
	@ResponseBody
	public String checkgameBackIdUnique(Gamemanage gamemanage) {
		String uniqueFlag = CommonConstant.UNIQUE;
		if (gamemanage != null) {
			uniqueFlag = gamemanageService.checkgameBackIdUnique(gamemanage);
		}
		return uniqueFlag;
	}

	// 可用游戏
	@GetMapping("/selectAllGameName")
	@ResponseBody
	public List<Gamemanage> selectAllGameName() {
		List<Gamemanage> list = gamemanageService.selectAllGameNameByStatus(0);
		return list;
	}

	/**
	 * 上传游戏文件
	 */
	@Transactional(rollbackFor = Exception.class)
	@ResponseBody
	@PostMapping("/uploadGameFile")
	public Message uploadGameFile(@RequestParam("file") MultipartFile file, HttpServletRequest request,
			Gamemanage gamemanage) {
		// 总体思路是先上传到指定文件夹下，并文件名保存到数据库，并返回文件名，最后表单提交的时候一并修改文件名获着重新复制过去
		// 同一处理
		Message message = gamemanageService.uploadGameFile(file, gamemanage);
		if (!CommonConstant.MESSAGE_CODE.equals(message.get(CommonConstant.MESSAGE_CODE_NAME).toString())) {
			return message;
		}

		// 重命名
		String fileName = file.getOriginalFilename();
		// 重命名
		fileName = FileUploadUtils.renameToUUID(fileName);

		// 先上传
		try {
			FileUploadUtils.uploadFile(file.getBytes(), FilePathConfig.getUploadGamePath(), fileName);
		} catch (Exception e) {
			return Message.error(FileMessageConstant.FILE_MESSAGE_FILE_UPLOAD_FAILED);
		}
		return Message.success(fileName);
	}
}
