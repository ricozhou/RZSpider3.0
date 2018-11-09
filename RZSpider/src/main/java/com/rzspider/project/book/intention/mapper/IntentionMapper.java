package com.rzspider.project.book.intention.mapper;

import com.rzspider.project.book.intention.domain.Intention;
import java.util.List;	

/**
 * 图书详情 数据层
 * 
 * @author ricozhou
 * @date 2018-05-28
 */
public interface IntentionMapper 
{

	/**
     * 查询图书详情信息
     * 
     * @param bookId 图书详情ID
     * @return 图书详情信息
     */
	public Intention selectIntentionById(Integer bookId);
	
	/**
     * 查询图书详情列表
     * 
     * @param intention 图书详情信息
     * @return 图书详情集合
     */
	public List<Intention> selectIntentionList(Intention intention);
	
	/**
     * 新增图书详情
     * 
     * @param intention 图书详情信息
     * @return 结果
     */
	public int insertIntention(Intention intention);
	
	/**
     * 修改图书详情
     * 
     * @param intention 图书详情信息
     * @return 结果
     */
	public int updateIntention(Intention intention);
	
	/**
     * 删除图书详情
     * 
     * @param bookId 图书详情ID
     * @return 结果
     */
	public int deleteIntentionById(Integer bookId);
	
	/**
     * 批量删除图书详情
     * 
     * @param bookIds 需要删除的数据ID
     * @return 结果
     */
	public int batchDeleteIntention(Integer[] bookIds);
	
}