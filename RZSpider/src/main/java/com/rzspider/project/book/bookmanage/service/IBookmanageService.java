package com.rzspider.project.book.bookmanage.service;

import com.rzspider.project.book.bookmanage.domain.Bookmanage;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/**
 * 图书详情 服务层
 * 
 * @author ricozhou
 * @date 2018-05-28
 */
public interface IBookmanageService 
{
	
	/**
     * 查询图书详情信息
     * 
     * @param bookId 图书详情ID
     * @return 图书详情信息
     */
	public Bookmanage selectBookmanageById(Integer bookId);
	
	/**
     * 查询图书详情列表
     * 
     * @param bookmanage 图书详情信息
     * @return 图书详情集合
     */
	public List<Bookmanage> selectBookmanageList(Bookmanage bookmanage);
	
	/**
     * 新增图书详情
     * 
     * @param bookmanage 图书详情信息
     * @return 结果
     */
	public int insertBookmanage(Bookmanage bookmanage);
	
	/**
     * 修改图书详情
     * 
     * @param bookmanage 图书详情信息
     * @return 结果
     */
	public int updateBookmanage(Bookmanage bookmanage);
	
	/**
     * 保存图书详情
     * 
     * @param bookmanage 图书详情信息
     * @return 结果
     */
	public int saveBookmanage(Bookmanage bookmanage);
	
	/**
     * 删除图书详情信息
     * 
     * @param bookId 图书详情ID
     * @return 结果
     */
	public int deleteBookmanageById(Integer bookId);
	
	/**
     * 批量删除图书详情信息
     * 
     * @param bookIds 需要删除的数据ID
     * @return 结果
     */
	public int batchDeleteBookmanage(Integer[] bookIds);

	//批量上传图书并解析
	public List<Bookmanage> batchAnalyzeList(String uploadPath, String fileName);

	//批量保存到数据库
	public int batchSaveBookmanage(List<Bookmanage> bmList);

	//下载模板
	public byte[] downExcelTemplate();

	//批量导出excel
	public byte[] batchExport(Bookmanage bookmanage);
	
}
