package com.rzspider.project.tool.baseset.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.tomcat.util.http.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.rzspider.project.common.file.utilt.FileUtils;
import com.rzspider.project.tool.baseset.domain.Baseset;
import com.rzspider.project.tool.baseset.domain.Musiclist;
import com.rzspider.project.tool.baseset.domain.NECMusicInfo;
import com.rzspider.project.tool.baseset.service.IBasesetService;
import com.rzspider.project.tool.baseset.service.IMusiclistService;
import com.itextpdf.text.log.SysoCounter;
import com.rzspider.common.constant.CommonSymbolicConstant;
import com.rzspider.common.constant.FileExtensionConstant;
import com.rzspider.common.constant.FileMessageConstant;
import com.rzspider.common.constant.project.SpiderConstant;
import com.rzspider.common.constant.project.ToolConstant;
import com.rzspider.common.utils.FileUploadUtils;
import com.rzspider.framework.aspectj.lang.annotation.Log;
import com.rzspider.framework.config.FilePathConfig;
import com.rzspider.framework.web.controller.BaseController;
import com.rzspider.framework.web.page.TableDataInfo;
import com.rzspider.framework.web.domain.Message;

/**
 * 歌曲 信息操作处理
 * 
 * @author ricozhou
 * @date 2018-06-28
 */
@Controller
@RequestMapping("/tool/baseset/detailedit/musiclist")
public class MusiclistController extends BaseController {
	private String prefix = "tool/baseset/detailedit/musiclist";

	@Autowired
	private IMusiclistService musiclistService;
	@Autowired
	private IBasesetService basesetService;

	/**
	 * 查询歌曲列表
	 */
	@GetMapping("/list")
	@ResponseBody
	public TableDataInfo list(Musiclist musiclist) {
		startPage();
		List<Musiclist> list = musiclistService.selectMusiclistList(musiclist);
		return getDataTable(list);
	}

	/**
	 * 新增歌曲
	 */
	@GetMapping("/add/{basesetId}")
	public String add(@PathVariable("basesetId") Integer basesetId, Model model) {
		if (basesetService.selectBasesetById(basesetId) == null) {
			return prefix + "/error";
		}
		model.addAttribute("basesetId", basesetId);
		return prefix + "/add";
	}

	/**
	 * 音乐其它设置
	 */
	@GetMapping("/otherSet/{basesetId}")
	public String otherSet(@PathVariable("basesetId") Integer basesetId, Model model) {
		// 查询设置
		Baseset baseset = basesetService.selectBasesetById(basesetId);
		model.addAttribute("baseset", baseset);
		return prefix + "/otherSet";
	}

	/**
	 * 保存其它设置
	 */
	@Log(title = "基础设置", action = "音乐设置-保存其他设置")
	@PostMapping("/otherSet")
	@ResponseBody
	public Message otherSet(Baseset baseset) {
		if (basesetService.updateMusicOtherSet(baseset) > 0) {
			return Message.success();
		}
		return Message.error(ToolConstant.TOOL_SAVE_FAILED);
	}

	/**
	 * 批量新增歌曲
	 */
	@GetMapping("/batchAdd/{basesetId}")
	public String batchAdd(@PathVariable("basesetId") Integer basesetId, Model model) {
		if (basesetService.selectBasesetById(basesetId) == null) {
			return prefix + "/error";
		}
		model.addAttribute("basesetId", basesetId);
		return prefix + "/batchAdd";
	}

	/**
	 * 搜索新增歌曲
	 */
	@GetMapping("/searchAdd/{basesetId}")
	public String searchAdd(@PathVariable("basesetId") Integer basesetId, Model model) {
		if (basesetService.selectBasesetById(basesetId) == null) {
			return prefix + "/error";
		}
		model.addAttribute("basesetId", basesetId);
		return prefix + "/searchAdd";
	}

	/**
	 * 修改歌曲
	 */
	@GetMapping("/edit/{musicId}")
	public String edit(@PathVariable("musicId") Integer musicId, Model model) {
		Musiclist musiclist = musiclistService.selectMusiclistById(musicId);
		if (musiclist == null || musiclist.getBasesetId() == null) {
			return prefix + "/error";
		}
		model.addAttribute("musiclist", musiclist);
		return prefix + "/edit";
	}

	/**
	 * 保存歌曲
	 */
	@Log(title = "基础设置", action = "音乐设置-保存新增")
	@PostMapping("/save")
	@ResponseBody
	public Message save(Musiclist musiclist) {
		if (musiclistService.saveMusiclist(musiclist) > 0) {
			return Message.success();
		}
		return Message.error(ToolConstant.TOOL_SAVE_FAILED);
	}

	/**
	 * 批量保存歌曲
	 */
	@Log(title = "基础设置", action = "音乐设置-批量保存")
	@PostMapping("/batchSave")
	@ResponseBody
	public Message batchSave(Musiclist musiclist) {
		if (musiclistService.batchSaveMusiclist(musiclist) > 0) {
			return Message.success();
		}
		return Message.error(ToolConstant.TOOL_SAVE_FAILED);
	}

	/**
	 * 删除歌曲
	 */
	@Log(title = "基础设置", action = "音乐设置-删除歌曲")
	@PostMapping("/remove/{musicId}")
	@ResponseBody
	public Message remove(@PathVariable("musicId") Integer musicId) {
		if (musiclistService.deleteMusiclistById(musicId) > 0) {
			return Message.success();
		}
		return Message.error(ToolConstant.TOOL_BASESET_MESSAGE_NO_EXIST);
	}

	/**
	 * 批量删除歌曲
	 */
	@Log(title = "基础设置", action = "音乐设置-批量删除")
	@PostMapping("/batchRemove")
	@ResponseBody
	public Message remove(@RequestParam("ids[]") Integer[] musicIds) {
		int rows = musiclistService.batchDeleteMusiclist(musicIds);
		if (rows > 0) {
			return Message.success();
		}
		return Message.error(ToolConstant.TOOL_BASESET_MESSAGE_NO_EXIST);
	}

	/**
	 * 上传文件,音乐MP3,封面图片
	 */
	@Log(title = "基础设置", action = "音乐设置-上传音乐文件")
	@ResponseBody
	@PostMapping("/uploadMusicFile/{flag}")
	public Message uploadMusicFile(@PathVariable("flag") Integer flag, @RequestParam("file") MultipartFile file,
			HttpServletRequest request) {
		// 原始名字
		String fileName = file.getOriginalFilename();
		if (flag == 0) {
			if (!fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_FILE_MP3)
					&& !fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_FILE_WAV)
					&& !fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_FILE_MIDI)
					&& !fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_FILE_WMA)) {
				return Message.error(FileMessageConstant.FILE_MESSAGE_FORMAT_INCORRENT);
			}
		} else if (flag == 1) {
			if (!fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_IMAGE_JPG)
					&& !fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_IMAGE_PNG)
					&& !fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_IMAGE_JPEG)
					&& !fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_IMAGE_GIF)) {
				return Message.error(FileMessageConstant.FILE_MESSAGE_FORMAT_INCORRENT);
			}
		}

		// 重命名
		fileName = FileUploadUtils.renameToUUID(fileName);
		// 先上传
		try {
			FileUploadUtils.uploadFile(file.getBytes(), FilePathConfig.getUploadPath(), fileName);
			// 返回文件名
			return Message.success(fileName);
		} catch (Exception e) {
			return Message.error(FileMessageConstant.FILE_MESSAGE_FILE_UPLOAD_FAILED);
		}
	}

	// 根据歌曲名返回歌曲列表
	/**
	 * 爬虫查询网易云音乐
	 */
	@Log(title = "基础设置", action = "音乐设置-搜索歌曲")
	@ResponseBody
	@PostMapping("/searchMusic")
	public List<NECMusicInfo> searchMusic(String musicName) {
		if (musicName == null) {
			return null;
		}
		List<NECMusicInfo> list = musiclistService.searchMusic(musicName);
		return list;
	}

	/**
	 * 搜索歌曲单个添加
	 */
	@Log(title = "基础设置", action = "音乐设置-搜索歌曲单个添加")
	@PostMapping("/searchAdd/add")
	@ResponseBody
	public Message searchAddAdd(Musiclist musiclist) {
		if (musiclistService.searchAddAdd(musiclist) > 0) {
			return Message.success();
		}
		return Message.error(ToolConstant.TOOL_SAVE_FAILED);
	}

	/**
	 * 批量搜索歌曲添加
	 */
	@Log(title = "基础设置", action = "音乐设置-批量搜索歌曲添加")
	@PostMapping("/searchAdd/batchAdd")
	@ResponseBody
	public Message searchAddbatchAdd(@RequestParam("basesetId") Integer basesetId,
			@RequestParam("musicIds[]") Integer[] musicIds, @RequestParam("titles[]") String[] titles,
			@RequestParam("authors[]") String[] authors, @RequestParam("pics[]") String[] pics) {
		if (musicIds == null) {
			return Message.error(ToolConstant.TOOL_SAVE_FAILED);
		}
		Musiclist musiclist;
		for (int i = 0; i < musicIds.length; i++) {
			musiclist = new Musiclist();
			musiclist.setBasesetId(basesetId);
			musiclist.setMusicId(musicIds[i]);
			musiclist.setTitle(titles[i]);
			musiclist.setAuthor(authors[i]);
			musiclist.setPic(pics[i]);
			if (musiclistService.searchAddAdd(musiclist) < 1) {
				return Message.error(ToolConstant.TOOL_SAVE_FAILED);
			}
		}

		return Message.success();
	}

	/**
	 * 导出JSON
	 */
	@Log(title = "基础设置", action = "音乐设置-导出json")
	@GetMapping("/exportJson")
	@ResponseBody
	public void exportJson(HttpServletResponse response, @RequestParam("ids") Integer[] musicIds) throws IOException {
		response.reset();
		if (musicIds == null) {
			return;
		}
		byte[] data = musiclistService.getExportJsonBytes(musicIds);
		String fileName = ToolConstant.TOOL_BASESET_MUSICEXPORT_DEFAULT_NAME
				+ FileExtensionConstant.FILE_EXTENSION_POINT_FILE_JSON;
		// 处理中文乱码
		try {
			fileName = FileUtils.getNewString(fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.reset();
		response.setHeader(FileMessageConstant.FILE_CONTENT_DISPOSITION,
				FileMessageConstant.FILE_ATTACHMENT_FILENAME + fileName);
		response.addHeader(FileMessageConstant.FILE_CONTENT_LENGTH, CommonSymbolicConstant.EMPTY_STRING + data.length);
		response.setContentType(FileMessageConstant.FILE_CONTENT_TYPE);

		IOUtils.write(data, response.getOutputStream());
		response.getOutputStream().close();
	}
}
