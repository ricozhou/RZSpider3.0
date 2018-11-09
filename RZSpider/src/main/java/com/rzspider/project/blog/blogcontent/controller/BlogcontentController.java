package com.rzspider.project.blog.blogcontent.controller;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.socket.TextMessage;

import com.rzspider.project.blog.blogcontent.domain.Blogcontent;
import com.rzspider.project.blog.blogcontent.domain.BlogcontentRecommend;
import com.rzspider.project.blog.blogcontent.domain.Blogmove;
import com.rzspider.project.blog.blogcontent.service.IBlogcontentService;
import com.rzspider.project.blog.blogcontent.utils.BlogUtil;
import com.rzspider.project.blog.blogset.domain.Blogset;
import com.rzspider.project.blog.blogset.service.IBlogsetService;
import com.rzspider.project.blog.blogtags.domain.Blogtags;
import com.rzspider.project.blog.blogtags.service.IBlogtagsService;
import com.rzspider.project.common.file.FileType;
import com.rzspider.project.common.file.domain.FileDao;
import com.rzspider.project.common.file.service.IFileService;
import com.rzspider.project.common.file.utilt.FileUtils;
import com.rzspider.project.common.image.utils.WaterMarkUtils;
import com.rzspider.project.system.role.service.IRoleService;
import com.gargoylesoftware.htmlunit.javascript.host.Console;
import com.rzspider.common.constant.CommonConstant;
import com.rzspider.common.constant.CommonSymbolicConstant;
import com.rzspider.common.constant.FileExtensionConstant;
import com.rzspider.common.constant.FileMessageConstant;
import com.rzspider.common.constant.FileOtherConstant;
import com.rzspider.common.constant.ReturnMessageConstant;
import com.rzspider.common.constant.UserConstants;
import com.rzspider.common.constant.WebSocketConstants;
import com.rzspider.common.constant.project.BlogConstant;
import com.rzspider.common.utils.FileUploadUtils;
import com.rzspider.common.utils.ImageUtils;
import com.rzspider.common.utils.OtherUtils;
import com.rzspider.common.utils.security.ShiroUtils;
import com.rzspider.framework.aspectj.lang.annotation.Log;
import com.rzspider.framework.config.FilePathConfig;
import com.rzspider.framework.web.controller.BaseController;
import com.rzspider.framework.web.page.TableDataInfo;
import com.rzspider.framework.websocket.service.WebSocketPushHandler;
import com.rzspider.implementspider.blogmove.controller.BlogMoveSpiderController;
import com.rzspider.framework.web.domain.Message;

/**
 * 文章内容 信息操作处理
 * 
 * @author ricozhou
 * @date 2018-06-12
 */
@Controller
@RequestMapping("/blog/blogcontent")
public class BlogcontentController extends BaseController {
	private String prefix = "blog/blogcontent";

	@Autowired
	private IBlogcontentService blogcontentService;
	@Autowired
	private IFileService fileService;

	@Autowired
	private IBlogsetService blogsetService;
	@Autowired
	private IBlogtagsService blogtagsService;
	@Autowired
	private IRoleService roleService;

	@Log(title = "博客管理", action = "文章管理-查看")
	@GetMapping()
	@RequiresPermissions("blog:blogcontent:view")
	public String blogcontent() {
		return prefix + "/blogcontent";
	}

	/**
	 * 查询文章内容列表
	 */
	@RequiresPermissions("blog:blogcontent:list")
	@GetMapping("/list")
	@ResponseBody
	public TableDataInfo list(Blogcontent blogcontent) {
		startPage();
		List<Blogcontent> list = blogcontentService.selectBlogcontentList(blogcontent);
		return getDataTable(list);
	}

	/**
	 * 新增文章内容
	 */
	@RequiresPermissions("blog:blogcontent:add")
	@GetMapping("/add")
	public String add(Model model) {
		// 先创建一个临时文件夹
		String blogFileName = String.valueOf(UUID.randomUUID());
		FileUtils.createFolder(FilePathConfig.getUploadBlogPath() + File.separator + blogFileName);
		model.addAttribute("blogFileName", blogFileName);
		// 将设置中的博主信息发送过去
		Blogset blogset = blogsetService.selectSomeBlogsetsById(1);
		model.addAttribute("bloggersetBloggerName",
				blogset != null ? blogset.getBloggersetBloggerName() : CommonSymbolicConstant.EMPTY_STRING);
		model.addAttribute("basicsetGlobalAllowComment", blogset != null ? blogset.getBasicsetGlobalAllowComment() : 1);
		model.addAttribute("basicsetGlobalAllowReprint", blogset != null ? blogset.getBasicsetGlobalAllowReprint() : 1);
		model.addAttribute("basicsetOpenBlogDownload", blogset != null ? blogset.getBasicsetOpenBlogDownload() : 1);
		model.addAttribute("basicsetArticleEditor", blogset != null ? blogset.getBasicsetArticleEditor() : 0);
		List<Blogtags> tags = blogtagsService.selectBlogtagsList(null);
		model.addAttribute("tags", tags);
		return prefix + "/add";
	}

	/**
	 * 文章推荐设置
	 */
	@RequiresPermissions("blog:blogcontent:recommendSet")
	@GetMapping("/recommendSet")
	public String recommendSet(Model model) {
		// 将设置中的博主信息发送过去
		Blogset blogset = blogsetService.selectSomeBlogsetById(1);
		model.addAttribute("blogset", blogset);
		// 把所有文章列表发送过去
		List<Blogcontent> blogcontentRecommends = blogcontentService.selectBlogcontentRecommendWithoutContent();
		model.addAttribute("blogcontentRecommends", blogcontentRecommends);
		return prefix + "/recommendset";
	}

	/**
	 * 博客搬家
	 */
	@RequiresPermissions("blog:blogcontent:blogMove")
	@GetMapping("/blogMove")
	public String blogMove(Model model) {
		// 将设置中的博主信息发送过去
		Blogset blogset = blogsetService.selectBlogsetBlogMoveMsgById(1);
		model.addAttribute("blogset", blogset);
		return prefix + "/blogmove/blogmove";
	}

	/**
	 * 文章推荐设置保存
	 */
	@Log(title = "博客管理", action = "文章管理-推荐设置保存")
	@Transactional(rollbackFor = Exception.class)
	@RequiresPermissions("blog:blogcontent:recommendSetSave")
	@PostMapping("/recommendSetSave")
	@ResponseBody
	public Message recommendSetSave(BlogcontentRecommend blogcontentRecommend) {
		// 测试管理员禁用
		if (OtherUtils.isTestManager(roleService.selectRoleKeys(ShiroUtils.getUserId()))) {
			return Message.error(UserConstants.USER_MESSAGE_TEATADMIN_DISABLED_USE);
		}
		if (blogcontentService.recommendSetSave(blogcontentRecommend) > 0) {
			return Message.success();
		}
		return Message.error();
	}

	/**
	 * 修改文章内容
	 */
	@RequiresPermissions("blog:blogcontent:edit")
	@GetMapping("/edit/{cid}")
	public String edit(@PathVariable("cid") Long cid, Model model) {
		Blogcontent blogcontent = blogcontentService.selectBlogcontentById(cid);
		model.addAttribute("blogcontent", blogcontent);

		// 将设置发送过去
		Blogset blogset = blogsetService.selectSomeBloggersetById(1);
		model.addAttribute("basicsetGlobalAllowComment", blogset != null ? blogset.getBasicsetGlobalAllowComment() : 1);
		model.addAttribute("basicsetGlobalAllowReprint", blogset != null ? blogset.getBasicsetGlobalAllowReprint() : 1);

		// 标签
		List<Blogtags> tags = blogtagsService.selectBlogtagsByCid(cid);
		model.addAttribute("tags", tags);
		return prefix + "/edit";
	}

	/**
	 * 保存文章内容
	 */
	@Log(title = "博客管理", action = "文章管理-文章保存")
	@RequiresPermissions("blog:blogcontent:save")
	@PostMapping("/save")
	@ResponseBody
	public Message save(Blogcontent blogcontent) {
		// 测试管理员禁用
		if (OtherUtils.isTestManager(roleService.selectRoleKeys(ShiroUtils.getUserId()))) {
			return Message.error(UserConstants.USER_MESSAGE_TEATADMIN_DISABLED_USE);
		}
		if (blogcontent.getType() == null) {
			blogcontent.setType("article");
		}
		if (blogcontentService.saveBlogcontent(blogcontent) > 0) {
			return Message.success();
		}
		return Message.error();
	}

	/**
	 * 删除缓存文件夹
	 */
	@PostMapping("/deleteCacheFile")
	@ResponseBody
	public Message deleteCacheFile(String blogFileName) {
		String path = FilePathConfig.getUploadBlogPath() + File.separator + blogFileName;
		if (FileUtils.deleteFile(path)) {
			return Message.success();
		}
		return Message.error();
	}

	/**
	 * 删除文章内容
	 */
	@Log(title = "博客管理", action = "文章管理-文章删除")
	@RequiresPermissions("blog:blogcontent:remove")
	@PostMapping("/remove/{cid}")
	@ResponseBody
	public Message remove(@PathVariable("cid") Long cid) {
		// 测试管理员禁用
		if (OtherUtils.isTestManager(roleService.selectRoleKeys(ShiroUtils.getUserId()))) {
			return Message.error(UserConstants.USER_MESSAGE_TEATADMIN_DISABLED_USE);
		}
		if (blogcontentService.deleteBlogcontentById(cid) > 0) {
			return Message.success();
		}
		return Message.error();
	}

	/**
	 * 批量删除文章内容
	 */
	@Log(title = "博客管理", action = "文章管理-批量删除文章")
	@RequiresPermissions("blog:blogcontent:batchRemove")
	@PostMapping("/batchRemove")
	@ResponseBody
	public Message remove(@RequestParam("ids[]") Long[] cids) {
		// 测试管理员禁用
		if (OtherUtils.isTestManager(roleService.selectRoleKeys(ShiroUtils.getUserId()))) {
			return Message.error(UserConstants.USER_MESSAGE_TEATADMIN_DISABLED_USE);
		}
		int rows = blogcontentService.batchDeleteBlogcontent(cids);
		if (rows > 0) {
			return Message.success();
		}
		return Message.error();
	}

	/**
	 * 文本编辑上传图片
	 */
	@ResponseBody
	@PostMapping("/uploadBlogImg")
	public Message uploadimg(@RequestParam("file") MultipartFile file, HttpServletRequest request, String blogFileName,
			boolean addWaterMark) {
		String fileName = file.getOriginalFilename();
		if (!fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_IMAGE_JPG)
				&& !fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_IMAGE_PNG)
				&& !fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_IMAGE_GIF)
				&& !fileName.endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_IMAGE_JPEG)) {
			return Message.error(FileMessageConstant.FILE_MESSAGE_FORMAT_INCORRENT);
		}
		// 重命名
		fileName = FileUploadUtils.renameToUUID(fileName);
		if (blogFileName == null || CommonSymbolicConstant.EMPTY_STRING.equals(blogFileName)
				|| CommonConstant.UNDEFINED.equals(blogFileName)) {
			blogFileName = String.valueOf(UUID.randomUUID());
		} else {
			// 验证 blogFileName

		}
		FileDao sysFile = new FileDao(FileType.fileType(fileName),
				FileOtherConstant.FILE_JUMP_PATH_PREFIX3 + blogFileName + File.separator + fileName, new Date());
		// 先上传
		try {
			FileUploadUtils.uploadFile(file.getBytes(),
					FilePathConfig.getUploadBlogPath() + File.separator + blogFileName, fileName);
		} catch (Exception e) {
			return Message.error(FileMessageConstant.FILE_MESSAGE_FILE_UPLOAD_FAILED);
		}
		// 打水印
		if (addWaterMark) {
			// 查询总设置是否需要打水印
			Blogset blogset = blogsetService.selectBlogsetWaterMarkMsgById(1);
			if (blogset.getBasicsetAddWaterMark() == 0 && blogset.getBasicsetWaterMarkMsg() != null
					&& !CommonSymbolicConstant.EMPTY_STRING.equals(blogset.getBasicsetWaterMarkMsg())) {
				String srcImgPath = FilePathConfig.getUploadBlogPath() + File.separator + blogFileName + File.separator
						+ fileName; // 源图片地址
				String tarImgPath = FilePathConfig.getUploadBlogPath() + File.separator + blogFileName + File.separator
						+ fileName; // 待存储的地址
				String waterMarkContent = blogset.getBasicsetWaterMarkMsg(); // 水印内容
				// 第一个是颜色16进制，第二个是字体，第三个是字体样式，第四个是字体大小,第五个是水印方位
				String[] markSetMsg = blogset.getBasicsetWaterMarkSetMsg().split(CommonSymbolicConstant.COMMA);

				int[] rgb = ImageUtils.hexToRgb(markSetMsg[0]);
				Color color = new Color(rgb[0], rgb[1], rgb[2], 110);
				Font font = new Font(markSetMsg[1], Integer.valueOf(markSetMsg[2]), Integer.valueOf(markSetMsg[3]));
				WaterMarkUtils.addWaterMark(srcImgPath, tarImgPath, waterMarkContent, color, font,
						Integer.valueOf(markSetMsg[4]));
			}
		}
		if (fileService.save(sysFile) > 0) {
			Message message = new Message();
			message = message.success();
			message.put(ReturnMessageConstant.RETURN_MESSAGE_KEY_2, sysFile.getUrl());
			message.put(ReturnMessageConstant.RETURN_MESSAGE_KEY_6, blogFileName);
			return message;
		}
		return Message.error();
	}

	/**
	 * 批量发布文章内容
	 */
	@Log(title = "博客管理", action = "文章管理-批量发布文章")
	@RequiresPermissions("blog:blogcontent:batchRelease")
	@PostMapping("/batchRelease")
	@ResponseBody
	public Message batchRelease(@RequestParam("status") Integer status, @RequestParam("ids[]") Long[] cids) {
		// 测试管理员禁用
		if (OtherUtils.isTestManager(roleService.selectRoleKeys(ShiroUtils.getUserId()))) {
			return Message.error(UserConstants.USER_MESSAGE_TEATADMIN_DISABLED_USE);
		}
		int rows = blogcontentService.batchReleaseBlogcontent(status, cids);
		if (rows > 0) {
			return Message.success();
		}
		return Message.error();
	}

	/**
	 * 置顶文章
	 */
	@RequiresPermissions("blog:blogcontent:edit")
	@PostMapping("/articleTop")
	@ResponseBody
	public Message articleTop(@RequestParam("cid") Long cid, @RequestParam("articleTop") Integer articleTop) {
		// 测试管理员禁用
		if (OtherUtils.isTestManager(roleService.selectRoleKeys(ShiroUtils.getUserId()))) {
			return Message.error(UserConstants.USER_MESSAGE_TEATADMIN_DISABLED_USE);
		}
		if (blogcontentService.articleTop(cid, articleTop) > 0) {
			return Message.success();
		}
		return Message.error(BlogConstant.BLOG_BLOGTOP_SAVE_FAILED);
	}
}
