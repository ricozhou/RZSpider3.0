package com.rzspider.project.book.intention.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rzspider.common.utils.OtherUtils;
import com.rzspider.common.utils.StringUtils;
import com.rzspider.common.utils.security.ShiroUtils;
import com.rzspider.project.book.intention.mapper.IntentionMapper;
import com.rzspider.project.book.bookmanage.domain.Bookmanage;
import com.rzspider.project.book.bookmanage.utils.ExcelUtils;
import com.rzspider.project.book.intention.domain.Intention;
import com.rzspider.project.book.intention.service.IIntentionService;
import com.rzspider.project.book.intention.utils.IntentionExcelUtils;
import com.rzspider.project.system.role.service.IRoleService;

/**
 * 图书详情 服务层实现
 * 
 * @author ricozhou
 * @date 2018-05-28
 */
@Service
public class IntentionServiceImpl implements IIntentionService {
	@Autowired
	private IntentionMapper intentionMapper;

	/**
	 * 查询图书详情信息
	 * 
	 * @param bookId
	 *            图书详情ID
	 * @return 图书详情信息
	 */
	@Override
	public Intention selectIntentionById(Integer bookId) {
		return intentionMapper.selectIntentionById(bookId);
	}

	/**
	 * 查询图书详情列表
	 * 
	 * @param intention
	 *            图书详情信息
	 * @return 图书详情集合
	 */
	@Override
	public List<Intention> selectIntentionList(Intention intention) {
		intention.setUserId(Integer.valueOf(String.valueOf(ShiroUtils.getUserId())));
		return intentionMapper.selectIntentionList(intention);
	}

	/**
	 * 新增图书详情
	 * 
	 * @param intention
	 *            图书详情信息
	 * @return 结果
	 */
	@Override
	public int insertIntention(Intention intention) {
		return intentionMapper.insertIntention(intention);
	}

	/**
	 * 修改图书详情
	 * 
	 * @param intention
	 *            图书详情信息
	 * @return 结果
	 */
	@Override
	public int updateIntention(Intention intention) {
		return intentionMapper.updateIntention(intention);
	}

	/**
	 * 保存图书详情
	 * 
	 * @param intention
	 *            图书详情信息
	 * @return 结果
	 */
	@Override
	public int saveIntention(Intention intention) {
		Integer bookId = intention.getBookId();
		int rows = 0;
		if (StringUtils.isNotNull(bookId)) {
			intention.setUpdateBy(ShiroUtils.getLoginName());
			rows = intentionMapper.updateIntention(intention);
		} else {
			intention.setCreateBy(ShiroUtils.getLoginName());
			intention.setUserId(Integer.valueOf(String.valueOf(ShiroUtils.getUserId())));
			rows = intentionMapper.insertIntention(intention);
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
	public int deleteIntentionById(Integer bookId) {
		return intentionMapper.deleteIntentionById(bookId);
	}

	/**
	 * 批量删除图书详情对象
	 * 
	 * @param bookIds
	 *            需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int batchDeleteIntention(Integer[] bookIds) {
		return intentionMapper.batchDeleteIntention(bookIds);
	}

	// 解析回显excel数据
	@Override
	public List<Intention> batchAnalyzeList(String uploadPath, String fileName) {
		File f = new File(uploadPath + File.separator + fileName);
		if (!f.exists()) {
			return null;
		}
		List<Intention> itList = IntentionExcelUtils.readExcel(f);
		// 删除复制的文件
		if (f.exists()) {
			f.delete();
		}
		return itList;
	}

	// 批量保存
	@Override
	public int batchSaveIntention(List<Intention> itList) {
		int rows = 0;
		for (Intention intention : itList) {
			rows = intentionMapper.insertIntention(intention);
		}
		return rows;
	}

	// 下载模板
	@Override
	public byte[] downExcelTemplate() {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		// 生成xls
		XSSFWorkbook workbook = IntentionExcelUtils.createExcelFile();
		// workbook写入输出流
		IntentionExcelUtils.writeWBToStream(workbook, outputStream);
		IOUtils.closeQuietly(outputStream);
		return outputStream.toByteArray();
	}

	// 批量导出
	@Override
	public byte[] batchExport(Intention intention) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		// 数据库读取数据
		List<Intention> itList = intentionMapper.selectIntentionList(intention);
		if (itList == null || itList.size() < 1) {
			return null;
		}
		// 生成xls
		XSSFWorkbook workbook = IntentionExcelUtils.createExcelFile(itList);
		// workbook写入输出流
		ExcelUtils.writeWBToStream(workbook, outputStream);
		return outputStream.toByteArray();
	}

}
