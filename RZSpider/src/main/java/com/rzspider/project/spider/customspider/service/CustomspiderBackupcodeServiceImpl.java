package com.rzspider.project.spider.customspider.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rzspider.common.utils.StringUtils;
import com.rzspider.project.spider.customspider.domain.Customspider;
import com.rzspider.project.spider.customspider.domain.CustomspiderBackupcode;
import com.rzspider.project.spider.customspider.domain.FileTree;
import com.rzspider.project.spider.customspider.mapper.CustomspiderBackupcodeMapper;

/**
 * 自定义爬虫代码备份 服务层实现
 * 
 * @author ricozhou
 * @date 2018-08-29
 */
@Service
public class CustomspiderBackupcodeServiceImpl implements ICustomspiderBackupcodeService {
	@Autowired
	private CustomspiderBackupcodeMapper customspiderBackupcodeMapper;
	@Autowired
	private ICustomspiderService customspiderService;
	@Autowired
	private ICSFileService cSFileService;

	/**
	 * 查询自定义爬虫代码备份信息
	 * 
	 * @param spiderCustomspiderBackupcodeId
	 *            自定义爬虫代码备份ID
	 * @return 自定义爬虫代码备份信息
	 */
	@Override
	public CustomspiderBackupcode selectCustomspiderBackupcodeById(Integer spiderCustomspiderBackupcodeId) {
		return customspiderBackupcodeMapper.selectCustomspiderBackupcodeById(spiderCustomspiderBackupcodeId);
	}

	/**
	 * 查询自定义爬虫代码备份列表
	 * 
	 * @param customspiderBackupcode
	 *            自定义爬虫代码备份信息
	 * @return 自定义爬虫代码备份集合
	 */
	@Override
	public List<CustomspiderBackupcode> selectCustomspiderBackupcodeList(
			CustomspiderBackupcode customspiderBackupcode) {
		return customspiderBackupcodeMapper.selectCustomspiderBackupcodeList(customspiderBackupcode);
	}

	/**
	 * 新增自定义爬虫代码备份
	 * 
	 * @param customspiderBackupcode
	 *            自定义爬虫代码备份信息
	 * @return 结果
	 */
	@Override
	public int insertCustomspiderBackupcode(CustomspiderBackupcode customspiderBackupcode) {
		return customspiderBackupcodeMapper.insertCustomspiderBackupcode(customspiderBackupcode);
	}

	/**
	 * 修改自定义爬虫代码备份
	 * 
	 * @param customspiderBackupcode
	 *            自定义爬虫代码备份信息
	 * @return 结果
	 */
	@Override
	public int updateCustomspiderBackupcode(CustomspiderBackupcode customspiderBackupcode) {
		return customspiderBackupcodeMapper.updateCustomspiderBackupcode(customspiderBackupcode);
	}

	/**
	 * 保存自定义爬虫代码备份
	 * 
	 * @param customspiderBackupcode
	 *            自定义爬虫代码备份信息
	 * @return 结果
	 */
	@Override
	public int saveCustomspiderBackupcode(CustomspiderBackupcode customspiderBackupcode) {
		Integer spiderCustomspiderBackupcodeId = customspiderBackupcode.getSpiderCustomspiderBackupcodeId();
		int rows = 0;
		if (StringUtils.isNotNull(spiderCustomspiderBackupcodeId)) {
			rows = customspiderBackupcodeMapper.updateCustomspiderBackupcode(customspiderBackupcode);
		} else {
			rows = customspiderBackupcodeMapper.insertCustomspiderBackupcode(customspiderBackupcode);
		}
		return rows;
	}

	/**
	 * 删除自定义爬虫代码备份信息
	 * 
	 * @param spiderCustomspiderBackupcodeId
	 *            自定义爬虫代码备份ID
	 * @return 结果
	 */
	@Override
	public int deleteCustomspiderBackupcodeById(Integer spiderCustomspiderBackupcodeId) {
		return customspiderBackupcodeMapper.deleteCustomspiderBackupcodeById(spiderCustomspiderBackupcodeId);
	}

	/**
	 * 批量删除自定义爬虫代码备份对象
	 * 
	 * @param spiderCustomspiderBackupcodeIds
	 *            需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int batchDeleteCustomspiderBackupcode(Integer[] spiderCustomspiderBackupcodeIds) {
		return customspiderBackupcodeMapper.batchDeleteCustomspiderBackupcode(spiderCustomspiderBackupcodeIds);
	}

	// 获取备份的详细信息
	@Override
	public CustomspiderBackupcode getCustomSpiderBackupCode(FileTree fileTree) {
		fileTree = cSFileService.getFileContent(fileTree);
		// 查询出详细信息
		Customspider customspider = customspiderService
				.selectCustomspiderByCustomSpiderBackId(fileTree.getCustomSpiderBackId());
		CustomspiderBackupcode customspiderBackupcode = new CustomspiderBackupcode();
		customspiderBackupcode.setCustomSpiderBackId(customspider.getCustomSpiderBackId());
		customspiderBackupcode.setCustomSpiderType(customspider.getCustomSpiderType());
		customspiderBackupcode.setBackupcodeType(fileTree.getFlag());
		customspiderBackupcode.setSpiderCodeTypeName(customspider.getSpiderCodeTypeName());
		customspiderBackupcode.setSpiderVersion(customspider.getSpiderVersion());
		customspiderBackupcode.setSpiderFileName(fileTree.getFileName());
		customspiderBackupcode.setSpiderFilePath(fileTree.getFilePath());
		customspiderBackupcode.setSpiderFileCodeContent(fileTree.getFileContent());
		return customspiderBackupcode;
	}

}
