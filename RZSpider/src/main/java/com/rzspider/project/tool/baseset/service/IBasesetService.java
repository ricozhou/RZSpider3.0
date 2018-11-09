package com.rzspider.project.tool.baseset.service;

import com.rzspider.framework.web.domain.Message;
import com.rzspider.project.tool.baseset.domain.Baseset;
import java.util.List;

/**
 * 基础设置详情 服务层
 * 
 * @author ricozhou
 * @date 2018-06-23
 */
public interface IBasesetService {

	/**
	 * 查询基础设置详情信息
	 * 
	 * @param basesetId
	 *            基础设置详情ID
	 * @return 基础设置详情信息
	 */
	public Baseset selectBasesetById(Integer basesetId);

	/**
	 * 查询基础设置详情列表
	 * 
	 * @param baseset
	 *            基础设置详情信息
	 * @return 基础设置详情集合
	 */
	public List<Baseset> selectBasesetList(Baseset baseset);

	/**
	 * 新增基础设置详情
	 * 
	 * @param baseset
	 *            基础设置详情信息
	 * @return 结果
	 */
	public int insertBaseset(Baseset baseset);

	/**
	 * 修改基础设置详情
	 * 
	 * @param baseset
	 *            基础设置详情信息
	 * @return 结果
	 */
	public int updateBaseset(Baseset baseset);

	/**
	 * 保存基础设置详情
	 * 
	 * @param baseset
	 *            基础设置详情信息
	 * @return 结果
	 */
	public int saveBaseset(Baseset baseset);

	/**
	 * 删除基础设置详情信息
	 * 
	 * @param basesetId
	 *            基础设置详情ID
	 * @return 结果
	 */
	public int deleteBasesetById(Integer basesetId);

	/**
	 * 批量删除基础设置详情信息
	 * 
	 * @param basesetIds
	 *            需要删除的数据ID
	 * @return 结果
	 */
	public int batchDeleteBaseset(Integer[] basesetIds);

	// 启用
	public int startUse(Integer basesetId);

	// 接下来是工具方法，获取各种参数
	// 首页介绍
	public Integer getShowMusicToolStatus();

	public String getLoginbgName();

	public String getHomeIntroduction();

	public String getSpiderJavaPackagePrefix();

	public String getSpiderJavaCodeExample();

	public String getSpiderPythonCodeExample();

	public String getSpiderJavascriptCodeExample();

	public String getDownloadFileNamePrefix();

	public String getSpiderWebsiteManual();

	public String getSpiderUseManual();

	public String checkThemeNameUnique(Baseset baseset);

	public int updateMusicInfo(Baseset baseset);

	String getMusicInfo();

	Baseset getBaseset();

	public byte[] getPdfToByte(String fileName);

	Integer getLoginbgType();

	public int updateMusicOtherSet(Baseset baseset);

	public Message clearCacheFile();

	public Message clearCacheDB();

}
