package com.rzspider.project.spider.customspider.service;

import com.rzspider.project.spider.codeType.domain.CodeType;
import com.rzspider.project.spider.customspider.domain.Customspider;
import com.rzspider.project.spider.customspider.domain.CustomspiderBackupcode;
import com.rzspider.project.spider.customspider.domain.FileTree;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

/**
 * 自定义爬虫详情 服务层
 * 
 * @author ricozhou
 * @date 2018-06-01
 */
public interface ICustomspiderService {

	/**
	 * 查询自定义爬虫详情信息
	 * 
	 * @param customSpiderId
	 *            自定义爬虫详情ID
	 * @return 自定义爬虫详情信息
	 */
	public Customspider selectCustomspiderById(Integer customSpiderId);

	/**
	 * 查询自定义爬虫详情列表
	 * 
	 * @param customspider
	 *            自定义爬虫详情信息
	 * @return 自定义爬虫详情集合
	 */
	public List<Customspider> selectCustomspiderList(Customspider customspider);

	/**
	 * 新增自定义爬虫详情
	 * 
	 * @param customspider
	 *            自定义爬虫详情信息
	 * @return 结果
	 */
	public int insertCustomspider(Customspider customspider);

	/**
	 * 修改自定义爬虫详情
	 * 
	 * @param customspider
	 *            自定义爬虫详情信息
	 * @return 结果
	 */
	public int updateCustomspider(Customspider customspider);

	/**
	 * 保存自定义爬虫详情
	 * 
	 * @param customspider
	 *            自定义爬虫详情信息
	 * @param codeType
	 * @return 结果
	 */
	public int saveCustomspider(Customspider customspider, CodeType codeType);

	/**
	 * 删除自定义爬虫详情信息
	 * 
	 * @param customSpiderId
	 *            自定义爬虫详情ID
	 * @return 结果
	 */
	public int deleteCustomspiderById(Integer customSpiderId);

	/**
	 * 批量删除自定义爬虫详情信息
	 * 
	 * @param customSpiderIds
	 *            需要删除的数据ID
	 * @return 结果
	 */
	public int batchDeleteCustomspider(Integer[] customSpiderIds);

	// 根据后台id查询
	public Customspider selectCustomspiderByCustomSpiderBackId(Integer customSpiderBackId);

	public int updateCustomspiderRunStatus(Customspider customspider);

	public int updateCustomspiderEntryFileName(Customspider customspider);

	public List<Customspider> selectCustomSpiderByRunStatus(Integer runStatus);

}
