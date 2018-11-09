package com.rzspider.project.tool.downloadmanage.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rzspider.common.constant.CommonSymbolicConstant;
import com.rzspider.common.constant.OtherConstant;
import com.rzspider.common.constant.project.ToolConstant;
import com.rzspider.common.utils.ServiceInfoUtil;
import com.rzspider.common.utils.StringUtils;
import com.rzspider.common.utils.security.ShiroUtils;
import com.rzspider.framework.config.FilePathConfig;
import com.rzspider.framework.config.ResourcesConfig;
import com.rzspider.project.tool.downloadmanage.mapper.DownloadmanageMapper;
import com.rzspider.project.common.file.utilt.FileUtils;
import com.rzspider.project.system.website.domain.Website;
import com.rzspider.project.system.website.service.IWebsiteService;
import com.rzspider.project.tool.downloadmanage.domain.Downloadmanage;
import com.rzspider.project.tool.downloadmanage.service.IDownloadmanageService;

/**
 * 下载管理详情 服务层实现
 * 
 * @author ricozhou
 * @date 2018-09-07
 */
@Service
public class DownloadmanageServiceImpl implements IDownloadmanageService {
	@Autowired
	private DownloadmanageMapper downloadmanageMapper;
	@Autowired
	private IWebsiteService websiteService;

	/**
	 * 查询下载管理详情信息
	 * 
	 * @param downloadmanageId
	 *            下载管理详情ID
	 * @return 下载管理详情信息
	 */
	@Override
	public Downloadmanage selectDownloadmanageById(Integer downloadmanageId) {
		return downloadmanageMapper.selectDownloadmanageById(downloadmanageId);
	}

	/**
	 * 查询下载管理详情列表
	 * 
	 * @param downloadmanage
	 *            下载管理详情信息
	 * @return 下载管理详情集合
	 */
	@Override
	public List<Downloadmanage> selectDownloadmanageList(Downloadmanage downloadmanage) {
		return downloadmanageMapper.selectDownloadmanageList(downloadmanage);
	}

	/**
	 * 新增下载管理详情
	 * 
	 * @param downloadmanage
	 *            下载管理详情信息
	 * @return 结果
	 */
	@Override
	public int insertDownloadmanage(Downloadmanage downloadmanage) {
		return downloadmanageMapper.insertDownloadmanage(downloadmanage);
	}

	/**
	 * 修改下载管理详情
	 * 
	 * @param downloadmanage
	 *            下载管理详情信息
	 * @return 结果
	 */
	@Override
	public int updateDownloadmanage(Downloadmanage downloadmanage) {
		return downloadmanageMapper.updateDownloadmanage(downloadmanage);
	}

	/**
	 * 保存下载管理详情
	 * 
	 * @param downloadmanage
	 *            下载管理详情信息
	 * @return 结果
	 */
	@Override
	public int saveDownloadmanage(Downloadmanage downloadmanage) {
		Integer downloadmanageId = downloadmanage.getDownloadmanageId();
		int rows = 0;
		downloadmanage.setUpdateBy(ShiroUtils.getLoginName());
		if (StringUtils.isNotNull(downloadmanageId)) {
			rows = downloadmanageMapper.updateDownloadmanage(downloadmanage);
		} else {
			downloadmanage.setCreateBy(ShiroUtils.getLoginName());
			// 创建外链key
			downloadmanage.setDownloadFileUrlKey(String.valueOf(UUID.randomUUID()));
			String downloadFileUrl = getDownloadFileUrl(downloadmanage.getDownloadFileUrlKey());

			// 拼接外链
			downloadmanage.setDownloadFileUrl(downloadFileUrl);
			rows = downloadmanageMapper.insertDownloadmanage(downloadmanage);
		}
		return rows;
	}

	/**
	 * @date Sep 10, 2018 1:24:20 PM
	 * @Desc
	 * @param downloadFileUrlKey
	 * @return
	 */
	private String getDownloadFileUrl(String downloadFileUrlKey) {
		String downloadFileUrl = null;
		List<Website> list = websiteService.selectWebsiteList(null);
		Website website;
		String domain;
		if (list != null && list.size() > 0) {
			website = list.get(0);
			domain = website.getWebsiteDomainName();
			if (domain != null || CommonSymbolicConstant.EMPTY_STRING.equals(domain)) {
				if (domain.endsWith(CommonSymbolicConstant.FORWARD_SLASH)) {
					domain = domain.substring(0, domain.length() - 1);
				}
				downloadFileUrl = domain + CommonSymbolicConstant.FORWARD_SLASH
						+ ToolConstant.TOOL_BASESET_DOWNLOAD_URL_DEFAULT_NAME + CommonSymbolicConstant.FORWARD_SLASH
						+ downloadFileUrlKey;
			} else {
				downloadFileUrl = OtherConstant.OTHER_URL_START + website.getWebsitePublicIp()
						+ CommonSymbolicConstant.COLON + website.getWebsitePublicPort()
						+ CommonSymbolicConstant.FORWARD_SLASH + ToolConstant.TOOL_BASESET_DOWNLOAD_URL_DEFAULT_NAME
						+ CommonSymbolicConstant.FORWARD_SLASH + downloadFileUrlKey;
			}
		} else {
			downloadFileUrl = OtherConstant.OTHER_URL_START + OtherConstant.OTHER_DEFAULT_IP
					+ CommonSymbolicConstant.COLON + ServiceInfoUtil.getPort() + CommonSymbolicConstant.FORWARD_SLASH
					+ ToolConstant.TOOL_BASESET_DOWNLOAD_URL_DEFAULT_NAME + CommonSymbolicConstant.FORWARD_SLASH
					+ downloadFileUrlKey;
		}

		return downloadFileUrl;
	}

	/**
	 * 删除下载管理详情信息
	 * 
	 * @param downloadmanageId
	 *            下载管理详情ID
	 * @return 结果
	 */
	@Override
	public int deleteDownloadmanageById(Integer downloadmanageId) {
		// 删除文件
		Downloadmanage downloadmanage = selectDownloadmanageById(downloadmanageId);
		if (!deleteDownloadmanageFileByFileName(downloadmanage.getDownloadFileUuidName())) {
			return 0;
		}
		return downloadmanageMapper.deleteDownloadmanageById(downloadmanageId);
	}

	/**
	 * 删除下载管理详情信息
	 * 
	 * @param downloadmanageId
	 *            下载管理详情ID
	 * @return 结果
	 */
	@Override
	public boolean deleteDownloadmanageFileByFileName(String downloadFileUuidName) {
		// 删除文件
		File file = new File(FilePathConfig.getDownloadManagePath() + File.separator + downloadFileUuidName);
		if (!file.exists()) {
			return true;
		}
		return file.delete();
	}

	/**
	 * 批量删除下载管理详情对象
	 * 
	 * @param downloadmanageIds
	 *            需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int batchDeleteDownloadmanage(Integer[] downloadmanageIds) {
		// return
		// downloadmanageMapper.batchDeleteDownloadmanage(downloadmanageIds);
		int row = 0;
		for (Integer id : downloadmanageIds) {
			row = deleteDownloadmanageById(id);
		}
		return row;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.rzspider.project.tool.downloadmanage.service.IDownloadmanageService#
	 * selectDownloadmanageByDownloadFileUrlKey(java.lang.String)
	 */
	@Override
	public Downloadmanage selectDownloadmanageByDownloadFileUrlKey(String downloadFileUrlKey) {
		return downloadmanageMapper.selectDownloadmanageByDownloadFileUrlKey(downloadFileUrlKey);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.rzspider.project.tool.downloadmanage.service.IDownloadmanageService#
	 * getDownloadFileToBytes(com.rzspider.project.tool.downloadmanage.domain.
	 * Downloadmanage)
	 */
	@Override
	public byte[] getDownloadFileToBytes(Downloadmanage downloadmanage) {
		// 读取文件放入byte流中
		String filePath = FilePathConfig.getDownloadManagePath() + File.separator
				+ downloadmanage.getDownloadFileUuidName();
		File file = new File(filePath);
		return FileUtils.readFileToByteArray(file);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.rzspider.project.tool.downloadmanage.service.IDownloadmanageService#
	 * updateDownloadmanageDownloadFileDownNum(java.lang.Integer, int)
	 */
	@Override
	public int updateDownloadmanageDownloadFileDownNum(Integer downloadmanageId, int downloadFileDownNum) {
		return downloadmanageMapper.updateDownloadmanageDownloadFileDownNum(downloadmanageId, downloadFileDownNum);
	}

}
