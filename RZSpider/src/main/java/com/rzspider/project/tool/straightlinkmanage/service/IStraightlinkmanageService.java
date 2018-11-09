package com.rzspider.project.tool.straightlinkmanage.service;

import com.rzspider.project.tool.straightlinkmanage.domain.Straightlinkmanage;
import java.util.List;

/**
 * 直链管理详情 服务层
 * 
 * @author ricozhou
 * @date 2018-10-16
 */
public interface IStraightlinkmanageService 
{
	
	/**
     * 查询直链管理详情信息
     * 
     * @param straightlinkmanageId 直链管理详情ID
     * @return 直链管理详情信息
     */
	public Straightlinkmanage selectStraightlinkmanageById(Integer straightlinkmanageId);
	
	/**
     * 查询直链管理详情列表
     * 
     * @param straightlinkmanage 直链管理详情信息
     * @return 直链管理详情集合
     */
	public List<Straightlinkmanage> selectStraightlinkmanageList(Straightlinkmanage straightlinkmanage);
	
	/**
     * 新增直链管理详情
     * 
     * @param straightlinkmanage 直链管理详情信息
     * @return 结果
     */
	public int insertStraightlinkmanage(Straightlinkmanage straightlinkmanage);
	
	/**
     * 修改直链管理详情
     * 
     * @param straightlinkmanage 直链管理详情信息
     * @return 结果
     */
	public int updateStraightlinkmanage(Straightlinkmanage straightlinkmanage);
	
	/**
     * 保存直链管理详情
     * 
     * @param straightlinkmanage 直链管理详情信息
     * @return 结果
     */
	public int saveStraightlinkmanage(Straightlinkmanage straightlinkmanage);
	
	/**
     * 删除直链管理详情信息
     * 
     * @param straightlinkmanageId 直链管理详情ID
     * @return 结果
     */
	public int deleteStraightlinkmanageById(Integer straightlinkmanageId);
	
	/**
     * 批量删除直链管理详情信息
     * 
     * @param straightlinkmanageIds 需要删除的数据ID
     * @return 结果
     */
	public int batchDeleteStraightlinkmanage(Integer[] straightlinkmanageIds);

	/**
	 * @date Oct 16, 2018 1:22:46 PM
	 * @Desc
	 * @param straightlinkFileUrlKey
	 * @return
	 */
	boolean deleteStraightlinkmanageFileByFolderName(String straightlinkFileUrlKey);
	
}
