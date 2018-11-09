package com.rzspider.project.book.bookmanage.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
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

import com.rzspider.project.book.bookmanage.domain.Bookmanage;
import com.rzspider.project.book.bookmanage.service.IBookmanageService;
import com.rzspider.project.book.bookmanage.utils.BookmanageUtils;
import com.rzspider.project.common.file.utilt.FileUtils;
import com.rzspider.project.monitor.operlog.domain.OperLog;
import com.rzspider.project.system.role.service.IRoleService;
import com.rzspider.project.tool.baseset.service.IBasesetService;
import com.rzspider.common.constant.CommonSymbolicConstant;
import com.rzspider.common.constant.FileExtensionConstant;
import com.rzspider.common.constant.FileMessageConstant;
import com.rzspider.common.constant.project.BookConstant;
import com.rzspider.common.utils.FileUploadUtils;
import com.rzspider.common.utils.OtherUtils;
import com.rzspider.common.utils.security.ShiroUtils;
import com.rzspider.framework.aspectj.lang.annotation.Log;
import com.rzspider.framework.config.FilePathConfig;
import com.rzspider.framework.web.controller.BaseController;
import com.rzspider.framework.web.page.TableDataInfo;
import com.rzspider.framework.web.domain.Message;

/**
 * 图书详情 信息操作处理
 * 
 * @author ricozhou
 * @date 2018-05-28
 */
@Controller
@RequestMapping("/book/bookmanage")
public class BookmanageController extends BaseController {
	private String prefix = "book/bookmanage";
	public List<Bookmanage> bmList;

	@Autowired
	private IBookmanageService bookmanageService;
	@Autowired
	private IBasesetService basesetService;

	@Autowired
	private FilePathConfig filePathConfig;

	@Log(title = "图书管理", action = "个人图书管理-管理查看")
	@GetMapping()
	@RequiresPermissions("book:bookmanage:view")
	public String bookmanage() {
		return prefix + "/bookmanage";
	}

	/**
	 * 查询图书详情列表
	 */
	@RequiresPermissions("book:bookmanage:list")
	@GetMapping("/list")
	@ResponseBody
	public TableDataInfo list(Bookmanage bookmanage) {
		startPage();
		List<Bookmanage> list = bookmanageService.selectBookmanageList(bookmanage);
		return getDataTable(list);
	}

	/**
	 * 新增图书详情
	 */
	@Log(title = "图书管理", action = "个人图书管理-图书新增")
	@RequiresPermissions("book:bookmanage:add")
	@GetMapping("/add")
	public String add() {
		return prefix + "/add";
	}

	/**
	 * 批量新增图书
	 */
	@Log(title = "图书管理", action = "个人图书管理-批量新增")
	@RequiresPermissions("book:bookmanage:batchAdd")
	@GetMapping("/batchAdd")
	public String batchAdd() {
		return prefix + "/batchAdd";
	}

	/**
	 * 批量上传Excel解析返回数据并显示
	 */
	@Log(title = "图书管理", action = "个人图书管理-批量上传")
	@RequiresPermissions("book:bookmanage:batchAnalyzeList")
	@ResponseBody
	@PostMapping("/batchAnalyzeList")
	public Message upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		// 判断文件大小，格式等等
		try {
			FileUploadUtils.assertAllowed(file);
		} catch (FileSizeLimitExceededException e1) {
			e1.printStackTrace();
			bmList.clear();
			return Message.error(FileMessageConstant.FILE_MESSAGE_SIZE_GREATER_TEN_M);
		}
		// 原始名字
		String fileName = file.getOriginalFilename();
		if (!fileName.toLowerCase().endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_EXCEL_XLS)
				&& !fileName.toLowerCase().endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_EXCEL_XLSX)) {
			bmList.clear();
			return Message.error(FileMessageConstant.FILE_MESSAGE_FORMAT_INCORRENT);
		}
		// 重命名
		fileName = FileUploadUtils.renameToUUID(fileName);
		// 先上传
		try {
			FileUploadUtils.uploadFile(file.getBytes(), filePathConfig.getUploadCachePath(), fileName);
		} catch (Exception e) {
			bmList.clear();
			return Message.error();
		}
		// 解析并返回数据
		bmList = bookmanageService.batchAnalyzeList(filePathConfig.getUploadCachePath(), fileName);
		if (bmList == null || bmList.size() < 1) {
			return Message.error(BookConstant.BOOK_MESSAGE_NO_DATA);
		}
		return Message.success();
	}

	@Log(title = "图书管理", action = "个人图书管理-批量分析")
	@ResponseBody
	@GetMapping("/batchAnalyzeList2")
	public TableDataInfo batchAnalyzeList2() {
		TableDataInfo tdi = getDataTable(bmList);
		return tdi;
	}

	/**
	 * 修改图书详情
	 */
	@Log(title = "图书管理", action = "个人图书管理-图书修改")
	@RequiresPermissions("book:bookmanage:edit")
	@GetMapping("/edit/{bookId}")
	public String edit(@PathVariable("bookId") Integer bookId, Model model) {
		Bookmanage bookmanage = bookmanageService.selectBookmanageById(bookId);
		model.addAttribute("bookmanage", bookmanage);
		return prefix + "/edit";
	}

	/**
	 * 保存图书详情
	 */
	@Log(title = "图书管理", action = "个人图书管理-图书保存")
	@RequiresPermissions("book:bookmanage:save")
	@Transactional(rollbackFor = Exception.class)
	@PostMapping("/save")
	@ResponseBody
	public Message save(Bookmanage bookmanage) {
		if (bookmanageService.saveBookmanage(bookmanage) > 0) {
			return Message.success();
		}
		return Message.error();
	}

	/**
	 * 批量保存图书详情
	 */
	@Log(title = "图书管理", action = "个人图书管理-批量保存")
	@RequiresPermissions("book:bookmanage:batchSave")
	@Transactional(rollbackFor = Exception.class)
	@PostMapping("/batchSave")
	@ResponseBody
	public Message batchSave() {
		if (bmList == null || bmList.size() < 1) {
			bmList.clear();
			return Message.error();
		}
		if (bookmanageService.batchSaveBookmanage(bmList) > 0) {
			bmList.clear();
			return Message.success();
		}
		bmList.clear();
		return Message.error();
	}

	/**
	 * 下载模板
	 */
	@RequiresPermissions("book:bookmanage:downExcelTemplate")
	@Log(title = "图书管理", action = "个人图书管理-下载模板")
	@GetMapping("/downExcelTemplate")
	public void downExcelTemplate(HttpServletResponse response) throws IOException {
		response.reset();
		byte[] data = bookmanageService.downExcelTemplate();

		String excelName = BookmanageUtils.getExcelTemplateFileName(basesetService.getDownloadFileNamePrefix());
		// 处理中文乱码
		try {
			excelName = FileUtils.getNewString(excelName);
		} catch (Exception e) {
			e.printStackTrace();
			excelName = BookConstant.BOOK_DEFAULT_EXCELTEMPLATE_NAME;
		}
		response.setHeader(FileMessageConstant.FILE_CONTENT_DISPOSITION,
				FileMessageConstant.FILE_ATTACHMENT_FILENAME + excelName);
		response.addHeader(FileMessageConstant.FILE_CONTENT_LENGTH, CommonSymbolicConstant.EMPTY_STRING + data.length);
		response.setContentType(FileMessageConstant.FILE_CONTENT_TYPE);

		IOUtils.write(data, response.getOutputStream());
		response.getOutputStream().close();
	}

	/**
	 * 批量导出excel
	 */
	@RequiresPermissions("book:bookmanage:batchExport")
	@Log(title = "图书管理", action = "个人图书管理-批量导出excel")
	@GetMapping("/batchExport")
	public void batchExport(HttpServletResponse response, Bookmanage bookmanage) {
		try {
			response.reset();
			bookmanage.setUserId(Integer.valueOf(String.valueOf(ShiroUtils.getUserId())));
			byte[] data = bookmanageService.batchExport(bookmanage);
			if (data == null) {
				return;
			}
			String excelName = BookmanageUtils.getBookExportFileName(basesetService.getDownloadFileNamePrefix());
			// 处理中文乱码
			try {
				excelName = FileUtils.getNewString(excelName);
			} catch (Exception e) {
				e.printStackTrace();
				excelName = BookConstant.BOOK_DEFAULT_BOOKEXPORT_NAME;
			}
			response.setHeader(FileMessageConstant.FILE_CONTENT_DISPOSITION,
					FileMessageConstant.FILE_ATTACHMENT_FILENAME + excelName);
			response.addHeader(FileMessageConstant.FILE_CONTENT_LENGTH,
					CommonSymbolicConstant.EMPTY_STRING + data.length);
			response.setContentType(FileMessageConstant.FILE_CONTENT_TYPE);
			IOUtils.write(data, response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除图书详情
	 */
	@Log(title = "图书管理", action = "个人图书管理-图书删除")
	@RequiresPermissions("book:bookmanage:remove")
	@PostMapping("/remove/{bookId}")
	@Transactional(rollbackFor = Exception.class)
	@ResponseBody
	public Message remove(@PathVariable("bookId") Integer bookId) {
		if (bookmanageService.deleteBookmanageById(bookId) > 0) {
			return Message.success();
		}
		return Message.error();
	}

	/**
	 * 批量删除图书详情
	 */
	@Log(title = "图书管理", action = "个人图书管理-批量删除")
	@RequiresPermissions("book:bookmanage:batchRemove")
	@PostMapping("/batchRemove")
	@ResponseBody
	public Message remove(@RequestParam("ids[]") Integer[] bookIds) {
		int rows = bookmanageService.batchDeleteBookmanage(bookIds);
		if (rows > 0) {
			return Message.success();
		}
		return Message.error();
	}

	// 详情
	@Log(title = "图书管理", action = "个人图书管理-查看详情")
	@RequiresPermissions("book:bookmanage:detail")
	@GetMapping("/detail/{bookId}")
	public String detail(@PathVariable("bookId") Integer bookId, Model model) {
		Bookmanage bookmanage = bookmanageService.selectBookmanageById(bookId);
		model.addAttribute("bookmanage", bookmanage);
		return prefix + "/detail";
	}
}
