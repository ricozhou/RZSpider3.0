package com.rzspider.project.tool.baseset.mapper;

import com.rzspider.project.tool.baseset.domain.Baseset;
import com.rzspider.project.tool.baseset.domain.Musiclist;

import java.util.List;	

/**
 * 基础设置详情 数据层
 * 
 * @author ricozhou
 * @date 2018-06-23
 */
public interface BasesetMapper 
{

	/**
     * 查询基础设置详情信息
     * 
     * @param basesetId 基础设置详情ID
     * @return 基础设置详情信息
     */
	public Baseset selectBasesetById(Integer basesetId);
	
	/**
     * 查询基础设置详情列表
     * 
     * @param baseset 基础设置详情信息
     * @return 基础设置详情集合
     */
	public List<Baseset> selectBasesetList(Baseset baseset);
	
	/**
     * 新增基础设置详情
     * 
     * @param baseset 基础设置详情信息
     * @return 结果
     */
	public int insertBaseset(Baseset baseset);
	
	/**
     * 修改基础设置详情
     * 
     * @param baseset 基础设置详情信息
     * @return 结果
     */
	public int updateBaseset(Baseset baseset);
	
	/**
     * 删除基础设置详情
     * 
     * @param basesetId 基础设置详情ID
     * @return 结果
     */
	public int deleteBasesetById(Integer basesetId);
	
	/**
     * 批量删除基础设置详情
     * 
     * @param basesetIds 需要删除的数据ID
     * @return 结果
     */
	public int batchDeleteBaseset(Integer[] basesetIds);

	//停用所有
	public int changeAllUseStatusToStop(Integer useStatus);

	//根据id启用
	public int changeUseStatusToStartByBasesetId(Integer basesetId);

	public Baseset selectBasesetByUseStatus(Integer useStatus);

	public Baseset checkThemeNameUnique(Baseset baseset);

	public int updateBasesetEdit(Baseset baseset);

	public int updateManualEdit(Baseset baseset);

	public int updateMusicInfo(Baseset baseset);

	public int updateLoginsetEdit(Baseset baseset);

	public Musiclist selectMusiclistByBasesetId(Integer basesetId);

	public int updateMusicOtherSet(Baseset baseset);
	
}