package com.rzspider.project.commongame.gamelist.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rzspider.project.common.file.utilt.FileUtils;
import com.rzspider.project.commongame.gamelist.domain.Gamelist;
import com.rzspider.project.commongame.gamelist.service.IGamelistService;
import com.rzspider.project.commongame.gamemanage.domain.Gamemanage;
import com.rzspider.project.commongame.gamemanage.service.IGamemanageService;
import com.rzspider.project.tool.baseset.service.IBasesetService;
import com.rzspider.common.constant.CommonSymbolicConstant;
import com.rzspider.common.constant.FileMessageConstant;
import com.rzspider.common.constant.project.CommonGameConstant;
import com.rzspider.common.utils.StringUtils;
import com.rzspider.framework.aspectj.lang.annotation.Log;
import com.rzspider.framework.web.controller.BaseController;
import com.rzspider.framework.web.page.TableDataInfo;
import com.rzspider.framework.web.domain.Message;

/**
 * 通用游戏列 信息操作处理
 * 
 * @author ricozhou
 * @date 2018-06-13
 */
@Controller
@RequestMapping("/commongame/gamelist")
public class GamelistController extends BaseController {
	private String prefix = "commongame/gamelist";

	@Autowired
	private IGamelistService gamelistService;
	@Autowired
	private IGamemanageService gamemanageService;
	@Autowired
	private IBasesetService basesetService;

	@GetMapping()
	@RequiresPermissions("commongame:gamelist:view")
	public String gamelist() {
		return prefix + "/gamelist";
	}

	/**
	 * 查询通用游戏列列表
	 */
	@RequiresPermissions("commongame:gamelist:list")
	@GetMapping("/list")
	@ResponseBody
	public TableDataInfo list(Gamelist gamelist) {
		startPage();
		List<Gamelist> list = gamelistService.selectGamelistList(gamelist);
		return getDataTable(list);
	}

	/**
	 * 新增通用游戏列
	 */
	@RequiresPermissions("commongame:gamelist:add")
	@GetMapping("/add")
	public String add() {
		return prefix + "/add";
	}

	/**
	 * 修改通用游戏列
	 */
	@RequiresPermissions("commongame:gamelist:edit")
	@GetMapping("/edit/{gameListId}")
	public String edit(@PathVariable("gameListId") Integer gameListId, Model model) {
		Gamelist gamelist = gamelistService.selectGamelistById(gameListId);
		model.addAttribute("gamelist", gamelist);
		return prefix + "/edit";
	}

	/**
	 * 详情通用游戏列
	 */
	@GetMapping("/detail/{gameListId}")
	public String detail(@PathVariable("gameListId") Integer gameListId, Model model) {
		Gamelist gamelist = gamelistService.selectGamelistById(gameListId);
		model.addAttribute("gamelist", gamelist);
		return prefix + "/detail";
	}

	/**
	 * 保存通用游戏列
	 */
	@RequiresPermissions("commongame:gamelist:save")
	@PostMapping("/save")
	@ResponseBody
	public Message save(Gamelist gamelist) {
		Integer gameListId = gamelist.getGameListId();
		if (StringUtils.isNotNull(gameListId)) {
			// 判断是否还存在
			Gamemanage gamemanage = gamemanageService.selectGamemanageByGameName(gamelist.getGameName());
			if (gamemanage == null) {
				return Message.error(CommonGameConstant.COMMON_GAME_GAME_NO_EXIST);
			}
		}
		if (gamelistService.saveGamelist(gamelist) > 0) {
			return Message.success();
		}
		return Message.error();
	}

	/**
	 * 删除通用游戏列
	 */
	@RequiresPermissions("commongame:gamelist:remove")
	@PostMapping("/remove/{gameListId}")
	@ResponseBody
	public Message remove(@PathVariable("gameListId") Integer gameListId) {
		if (gamelistService.deleteGamelistById(gameListId) > 0) {
			return Message.success();
		}
		return Message.error();
	}

	/**
	 * 批量删除通用游戏列
	 */
	@RequiresPermissions("commongame:gamelist:batchRemove")
	@PostMapping("/batchRemove")
	@ResponseBody
	public Message remove(@RequestParam("ids[]") Integer[] gameListIds) {
		int rows = gamelistService.batchDeleteGamelist(gameListIds);
		if (rows > 0) {
			return Message.success();
		}
		return Message.error();
	}

	/**
	 * 校验游戏昵称
	 */
	@PostMapping("/checkGameNickNameUnique")
	@ResponseBody
	public String checkGameNickNameUnique(Gamelist gamelist) {
		String uniqueFlag = CommonGameConstant.COMMON_GAME_UNIQUE_FLAG;
		if (gamelist != null) {
			uniqueFlag = gamelistService.checkGameNickNameUnique(gamelist);
		}
		return uniqueFlag;
	}

	/**
	 * 下载源码
	 */
	@RequiresPermissions("commongame:gamelist:downSrcFile")
	@GetMapping("/downSrcFile/{gameBackId}")
	public void downSrcFile(HttpServletResponse response, @PathVariable("gameBackId") Integer gameBackId)
			throws IOException {
		response.reset();
		// 查询是否存在
		// 判断是否还存在
		Gamemanage gamemanage = gamemanageService.selectGamemanageByGameBackId(gameBackId);
		if (gamemanage == null) {
			// return Message.error("游戏已不存在，请删除");
			return;
		}
		// 返回对应文件名
		String fileName = gamemanage.getSrcFileName();
		String srcFileName = (basesetService.getDownloadFileNamePrefix()!=null
				? (basesetService.getDownloadFileNamePrefix() + CommonSymbolicConstant.UNDERLINE)
				: CommonSymbolicConstant.EMPTY_STRING) + gamemanage.getGameName()
				+ CommonGameConstant.COMMON_GAME_SRC_FILENAME_INFIX + FileUtils.getFileNameFromPoint(fileName);
		byte[] data = gamelistService.downGameFile(gamemanage, fileName);
		if (data == null) {
			return;
		}
		// 处理中文乱码
		try {
			srcFileName = FileUtils.getNewString(srcFileName);
		} catch (Exception e) {
			e.printStackTrace();
			srcFileName = CommonGameConstant.COMMON_GAME_DEFULAT_SRC_FILENAME_PREFIX
					+ FileUtils.getFileNameFromPoint(fileName);
		}

		response.setHeader(FileMessageConstant.FILE_CONTENT_DISPOSITION,
				FileMessageConstant.FILE_ATTACHMENT_FILENAME + srcFileName);
		response.addHeader(FileMessageConstant.FILE_CONTENT_LENGTH, CommonSymbolicConstant.EMPTY_STRING + data.length);
		response.setContentType(FileMessageConstant.FILE_CONTENT_TYPE);

		IOUtils.write(data, response.getOutputStream());
		response.getOutputStream().close();
	}

	/**
	 * 下载可执行exe
	 */
	@RequiresPermissions("commongame:gamelist:downExecexeFile")
	@GetMapping("/downExecexeFile/{gameBackId}")
	public void downExecexeFile(HttpServletResponse response, @PathVariable("gameBackId") Integer gameBackId)
			throws IOException {
		response.reset();
		// 查询是否存在
		// 判断是否还存在
		Gamemanage gamemanage = gamemanageService.selectGamemanageByGameBackId(gameBackId);
		if (gamemanage == null) {
			return;
		}

		// 返回对应文件名
		String fileName = gamemanage.getExecexeFileName();
		String execexeFileName = (basesetService.getDownloadFileNamePrefix()!=null
				? (basesetService.getDownloadFileNamePrefix() + CommonSymbolicConstant.UNDERLINE)
				: CommonSymbolicConstant.EMPTY_STRING) + gamemanage.getGameName()
				+ CommonGameConstant.COMMON_GAME_EXECEXE_FILENAME_INFIX + FileUtils.getFileNameFromPoint(fileName);
		byte[] data = gamelistService.downGameFile(gamemanage, fileName);
		if (data == null) {
			return;
		}
		// 处理中文乱码
		try {
			execexeFileName = FileUtils.getNewString(execexeFileName);
		} catch (Exception e) {
			e.printStackTrace();
			execexeFileName = CommonGameConstant.COMMON_GAME_DEFULAT_EXECEXE_FILENAME_PREFIX
					+ FileUtils.getFileNameFromPoint(fileName);
		}
		response.setHeader(FileMessageConstant.FILE_CONTENT_DISPOSITION,
				FileMessageConstant.FILE_ATTACHMENT_FILENAME + execexeFileName);
		response.addHeader(FileMessageConstant.FILE_CONTENT_LENGTH, CommonSymbolicConstant.EMPTY_STRING + data.length);
		response.setContentType(FileMessageConstant.FILE_CONTENT_TYPE);

		IOUtils.write(data, response.getOutputStream());
		response.getOutputStream().close();
	}

	/**
	 * 下载安装版exe
	 */
	@RequiresPermissions("commongame:gamelist:downSetupexeFile")
	@GetMapping("/downSetupexeFile/{gameBackId}")
	public void downSetupexeFile(HttpServletResponse response, @PathVariable("gameBackId") Integer gameBackId)
			throws IOException {
		response.reset();
		// 查询是否存在
		// 判断是否还存在
		Gamemanage gamemanage = gamemanageService.selectGamemanageByGameBackId(gameBackId);
		if (gamemanage == null) {
			// return Message.error("游戏已不存在，请删除");
			return;
		}

		// 返回对应文件名
		String fileName = gamemanage.getSetupexeName();
		String setupexeFileName = (basesetService.getDownloadFileNamePrefix()!=null
				? (basesetService.getDownloadFileNamePrefix() + CommonSymbolicConstant.UNDERLINE)
				: CommonSymbolicConstant.EMPTY_STRING) + gamemanage.getGameName()
				+ CommonGameConstant.COMMON_GAME_SETUPEXE_FILENAME_INFIX + FileUtils.getFileNameFromPoint(fileName);
		byte[] data = gamelistService.downGameFile(gamemanage, fileName);
		if (data == null) {
			return;
		}
		// 处理中文乱码
		try {
			setupexeFileName = FileUtils.getNewString(setupexeFileName);
		} catch (Exception e) {
			e.printStackTrace();
			setupexeFileName = CommonGameConstant.COMMON_GAME_DEFULAT_SETUPEXE_FILENAME_PREFIX
					+ FileUtils.getFileNameFromPoint(fileName);
		}
		response.setHeader(FileMessageConstant.FILE_CONTENT_DISPOSITION,
				FileMessageConstant.FILE_ATTACHMENT_FILENAME + setupexeFileName);
		response.addHeader(FileMessageConstant.FILE_CONTENT_LENGTH, CommonSymbolicConstant.EMPTY_STRING + data.length);
		response.setContentType(FileMessageConstant.FILE_CONTENT_TYPE);

		IOUtils.write(data, response.getOutputStream());
		response.getOutputStream().close();
	}
}
