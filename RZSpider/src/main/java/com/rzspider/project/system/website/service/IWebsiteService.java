package com.rzspider.project.system.website.service;

import com.rzspider.project.system.website.domain.Website;
import java.util.List;

/**
 * 网站设置 服务层
 * 
 * @author ricozhou
 * @date 2018-09-10
 */
public interface IWebsiteService {

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
	 * 保存网站设置
	 * 
	 * @param website
	 *            网站设置信息
	 * @return 结果
	 */
	public int saveWebsite(Website website);

	/**
	 * @date Sep 10, 2018 12:24:32 PM
	 * @Desc
	 * @param website
	 * @return
	 */
	public int saveMail(Website website);

	/**
	 * @date Sep 28, 2018 8:52:53 AM
	 * @Desc
	 * @return
	 */
	Website getWebsiteSetMsg();

	/**
	 * @date Sep 28, 2018 9:12:53 AM
	 * @Desc
	 * @param website
	 * @return
	 */
	List<Website> selectSomeWebsiteList(Website website);

	/**
	 * @date Sep 28, 2018 9:16:16 AM
	 * @Desc
	 * @return
	 */
	Website getSomeWebsiteSetMsg();

}
