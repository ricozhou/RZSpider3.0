package com.rzspider.project.tool.straightlinkmanage.mapper;

import com.rzspider.project.tool.straightlinkmanage.domain.Straightlinkmanage;
import java.util.List;	

/**
 * 直链管理详情 数据层
 * 
 * @author ricozhou
 * @date 2018-10-16
 */
public interface StraightlinkmanageMapper 
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
     * 删除直链管理详情
     * 
     * @param straightlinkmanageId 直链管理详情ID
     * @return 结果
     */
	public int deleteStraightlinkmanageById(Integer straightlinkmanageId);
	
	/**
     * 批量删除直链管理详情
     * 
     * @param straightlinkmanageIds 需要删除的数据ID
     * @return 结果
     */
	public int batchDeleteStraightlinkmanage(Integer[] straightlinkmanageIds);
	
}