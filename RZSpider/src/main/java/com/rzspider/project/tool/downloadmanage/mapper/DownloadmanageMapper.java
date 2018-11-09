package com.rzspider.project.tool.downloadmanage.mapper;

import com.rzspider.project.tool.downloadmanage.domain.Downloadmanage;
import java.util.List;

/**
 * 下载管理详情 数据层
 * 
 * @author ricozhou
 * @date 2018-09-07
 */
public interface DownloadmanageMapper {

	/**
	 * 查询下载管理详情信息
	 * 
	 * @param downloadmanageId
	 *            下载管理详情ID
	 * @return 下载管理详情信息
	 */
	public Downloadmanage selectDownloadmanageById(Integer downloadmanageId);

	/**
	 * 查询下载管理详情列表
	 * 
	 * @param downloadmanage
	 *            下载管理详情信息
	 * @return 下载管理详情集合
	 */
	public List<Downloadmanage> selectDownloadmanageList(Downloadmanage downloadmanage);

	/**
	 * 新增下载管理详情
	 * 
	 * @param downloadmanage
	 *            下载管理详情信息
	 * @return 结果
	 */
	public int insertDownloadmanage(Downloadmanage downloadmanage);

	/**
	 * 修改下载管理详情
	 * 
	 * @param downloadmanage
	 *            下载管理详情信息
	 * @return 结果
	 */
	public int updateDownloadmanage(Downloadmanage downloadmanage);

	/**
	 * 删除下载管理详情
	 * 
	 * @param downloadmanageId
	 *            下载管理详情ID
	 * @return 结果
	 */
	public int deleteDownloadmanageById(Integer downloadmanageId);

	/**
	 * 批量删除下载管理详情
	 * 
	 * @param downloadmanageIds
	 *            需要删除的数据ID
	 * @return 结果
	 */
	public int batchDeleteDownloadmanage(Integer[] downloadmanageIds);

	/**
	 * @date Sep 7, 2018 5:09:31 PM
	 * @Desc
	 * @param downloadFileUrlKey
	 * @return
	 */
	public Downloadmanage selectDownloadmanageByDownloadFileUrlKey(String downloadFileUrlKey);

	/**
	 * @date Sep 7, 2018 5:15:29 PM
	 * @Desc
	 * @param downloadmanageId
	 * @param downloadFileDownNum
	 * @return
	 */
	public int updateDownloadmanageDownloadFileDownNum(Integer downloadmanageId, int downloadFileDownNum);

}