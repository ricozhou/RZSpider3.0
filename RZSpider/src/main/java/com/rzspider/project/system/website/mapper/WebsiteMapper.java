package com.rzspider.project.system.website.mapper;

import com.rzspider.project.system.website.domain.Website;
import java.util.List;

/**
 * 网站设置 数据层
 * 
 * @author ricozhou
 * @date 2018-09-10
 */
public interface WebsiteMapper {

	/**
	 * 查询网站设置信息
	 * 
	 * @param websiteId
	 *            网站设置ID
	 * @return 网站设置信息
	 */
	public Website selectWebsiteById(Integer websiteId);

	/**
	 * 查询网站设置列表
	 * 
	 * @param website
	 *            网站设置信息
	 * @return 网站设置集合
	 */
	public List<Website> selectWebsiteList(Website website);

	/**
	 * 新增网站设置
	 * 
	 * @param website
	 *            网站设置信息
	 * @return 结果
	 */
	public int insertWebsite(Website website);

	/**
	 * 修改网站设置
	 * 
	 * @param website
	 *            网站设置信息
	 * @return 结果
	 */
	public int updateWebsite(Website website);

	/**
	 * @date Sep 10, 2018 12:30:45 PM
	 * @Desc
	 * @param website
	 * @return
	 */
	public int updateMail(Website website);

	/**
	 * @date Sep 10, 2018 12:30:49 PM
	 * @Desc
	 * @param website
	 * @return
	 */
	public int insertMail(Website website);

	/**
	 * @date Sep 28, 2018 9:13:12 AM
	 * @Desc
	 * @param website
	 * @return
	 */
	public List<Website> selectSomeWebsiteList(Website website);

}