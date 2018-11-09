package com.rzspider.project.system.website.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rzspider.common.utils.StringUtils;
import com.rzspider.common.utils.security.ShiroUtils;
import com.rzspider.project.system.website.mapper.WebsiteMapper;
import com.rzspider.project.system.website.domain.Website;
import com.rzspider.project.system.website.service.IWebsiteService;

/**
 * 网站设置 服务层实现
 * 
 * @author ricozhou
 * @date 2018-09-10
 */
@Service
public class WebsiteServiceImpl implements IWebsiteService {
	@Autowired
	private WebsiteMapper websiteMapper;

	/**
	 * 查询网站设置信息
	 * 
	 * @param websiteId
	 *            网站设置ID
	 * @return 网站设置信息
	 */
	@Override
	public Website selectWebsiteById(Integer websiteId) {
		return websiteMapper.selectWebsiteById(websiteId);
	}
	
	/**
	 * 查询网站设置信息
	 * 
	 * @param websiteId
	 *            网站设置ID
	 * @return 网站设置信息
	 */
	@Override
	public Website getWebsiteSetMsg() {
		List<Website> list = selectWebsiteList(null);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 查询网站设置信息
	 * 
	 * @param websiteId
	 *            网站设置ID
	 * @return 网站设置信息
	 */
	@Override
	public Website getSomeWebsiteSetMsg() {
		List<Website> list = selectSomeWebsiteList(null);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 查询网站设置列表
	 * 
	 * @param website
	 *            网站设置信息
	 * @return 网站设置集合
	 */
	@Override
	public List<Website> selectWebsiteList(Website website) {
		return websiteMapper.selectWebsiteList(website);
	}
	
	/**
	 * 查询网站设置列表
	 * 
	 * @param website
	 *            网站设置信息
	 * @return 网站设置集合
	 */
	@Override
	public List<Website> selectSomeWebsiteList(Website website) {
		return websiteMapper.selectSomeWebsiteList(website);
	}

	/**
	 * 保存网站设置
	 * 
	 * @param website
	 *            网站设置信息
	 * @return 结果
	 */
	@Override
	public int saveWebsite(Website website) {
		Integer websiteId = website.getWebsiteId();
		int rows = 0;
		website.setUpdateBy(ShiroUtils.getLoginName());
		if (StringUtils.isNotNull(websiteId)) {
			rows = websiteMapper.updateWebsite(website);
		} else {
			website.setCreateBy(ShiroUtils.getLoginName());
			rows = websiteMapper.insertWebsite(website);
		}
		return rows;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.rzspider.project.system.website.service.IWebsiteService#savemail(com.
	 * rzspider.project.system.website.domain.Website)
	 */
	@Override
	public int saveMail(Website website) {
		Integer websiteId = website.getWebsiteId();
		int rows = 0;
		website.setUpdateBy(ShiroUtils.getLoginName());
		if (StringUtils.isNotNull(websiteId)) {
			rows = websiteMapper.updateMail(website);
		} else {
			website.setCreateBy(ShiroUtils.getLoginName());
			rows = websiteMapper.insertMail(website);
		}
		return rows;
	}

}
