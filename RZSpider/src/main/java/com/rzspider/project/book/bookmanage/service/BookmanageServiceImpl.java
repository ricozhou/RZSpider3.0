package com.rzspider.project.book.bookmanage.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rzspider.common.utils.OtherUtils;
import com.rzspider.common.utils.StringUtils;
import com.rzspider.common.utils.security.ShiroUtils;
import com.rzspider.project.book.bookmanage.mapper.BookmanageMapper;
import com.rzspider.project.book.bookmanage.domain.Bookmanage;
import com.rzspider.project.book.bookmanage.service.IBookmanageService;
import com.rzspider.project.book.bookmanage.utils.ExcelUtils;
import com.rzspider.project.system.role.service.IRoleService;
import com.rzspider.project.tool.gen.domain.ColumnInfo;
import com.rzspider.project.tool.gen.domain.TableInfo;

/**
 * 图书详情 服务层实现
 * 
 * @author ricozhou
 * @date 2018-05-28
 */
@Service
public class BookmanageServiceImpl implements IBookmanageService {
	@Autowired
	private BookmanageMapper bookmanageMapper;

	/**
	 * 查询图书详情信息
	 * 
	 * @param bookId
	 *            图书详情ID
	 * @return 图书详情信息
	 */
	@Override
	public Bookmanage selectBookmanageById(Integer bookId) {
		return bookmanageMapper.selectBookmanageById(bookId);
	}

	/**
	 * 查询图书详情列表
	 * 
	 * @param bookmanage
	 *            图书详情信息
	 * @return 图书详情集合
	 */
	@Override
	public List<Bookmanage> selectBookmanageList(Bookmanage bookmanage) {
		bookmanage.setUserId(Integer.valueOf(String.valueOf(ShiroUtils.getUserId())));
		return bookmanageMapper.selectBookmanageList(bookmanage);
	}

	/**
	 * 新增图书详情
	 * 
	 * @param bookmanage
	 *            图书详情信息
	 * @return 结果
	 */
	@Override
	public int insertBookmanage(Bookmanage bookmanage) {
		return bookmanageMapper.insertBookmanage(bookmanage);
	}

	/**
	 * 修改图书详情
	 * 
	 * @param bookmanage
	 *            图书详情信息
	 * @return 结果
	 */
	@Override
	public int updateBookmanage(Bookmanage bookmanage) {
		return bookmanageMapper.updateBookmanage(bookmanage);
	}

	/**
	 * 保存图书详情
	 * 
	 * @param bookmanage
	 *            图书详情信息
	 * @return 结果
	 */
	@Override
	public int saveBookmanage(Bookmanage bookmanage) {
		Integer bookId = bookmanage.getBookId();
		int rows = 0;
		if (StringUtils.isNotNull(bookId)) {
			bookmanage.setUpdateBy(ShiroUtils.getLoginName());
			rows = bookmanageMapper.updateBookmanage(bookmanage);
		} else {
			bookmanage.setCreateBy(ShiroUtils.getLoginName());
			bookmanage.setUserId(Integer.valueOf(String.valueOf(ShiroUtils.getUserId())));
			rows = bookmanageMapper.insertBookmanage(bookmanage);
		}
		return rows;
	}

	/**
	 * 删除图书详情信息
	 * 
	 * @param bookId
	 *            图书详情ID
	 * @return 结果
	 */
	@Override
	public int deleteBookmanageById(Integer bookId) {
		return bookmanageMapper.deleteBookmanageById(bookId);
	}

	/**
	 * 批量删除图书详情对象
	 * 
	 * @param bookIds
	 *            需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int batchDeleteBookmanage(Integer[] bookIds) {
		return bookmanageMapper.batchDeleteBookmanage(bookIds);
	}

	// 批量上传图书并解析
	@Override
	public List<Bookmanage> batchAnalyzeList(String uploadPath, String fileName) {
		File f = new File(uploadPath + File.separator + fileName);
		if (!f.exists()) {
			return null;
		}
		List<Bookmanage> bmList = ExcelUtils.readExcel(f);
		// 删除复制的文件
		if (f.exists()) {
			f.delete();
		}
		return bmList;
	}

	// 批量保存到数据库
	@Override
	public int batchSaveBookmanage(List<Bookmanage> bmList) {
		int rows = 0;
		for (Bookmanage bm : bmList) {
			rows = bookmanageMapper.insertBookmanage(bm);
		}
		return rows;
	}

	// 下载模板
	@Override
	public byte[] downExcelTemplate() {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		// 生成xls
		XSSFWorkbook workbook = ExcelUtils.createExcelFile();
		// workbook写入输出流
		ExcelUtils.writeWBToStream(workbook, outputStream);
		return outputStream.toByteArray();
	}

	// 批量导出excel
	@Override
	public byte[] batchExport(Bookmanage bookmanage) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		// 数据库读取数据
		List<Bookmanage> bmList = bookmanageMapper.selectBookmanageList(bookmanage);
		if (bmList == null || bmList.size() < 1) {
			return null;
		}
		// 生成xls
		XSSFWorkbook workbook = ExcelUtils.createExcelFile(bmList);
		// workbook写入输出流
		ExcelUtils.writeWBToStream(workbook, outputStream);
		return outputStream.toByteArray();
	}

}
