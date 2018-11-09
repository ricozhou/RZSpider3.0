package com.rzspider.project.spider.codeType.service;

import java.io.File;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rzspider.common.constant.UserConstants;
import com.rzspider.common.utils.StringUtils;
import com.rzspider.framework.config.FilePathConfig;
import com.rzspider.project.spider.codeType.mapper.CodeTypeMapper;
import com.rzspider.project.spider.codeType.domain.CodeType;
import com.rzspider.project.spider.codeType.service.ICodeTypeService;
import com.rzspider.project.spider.customspider.utils.BaseCSUtils;
import com.rzspider.project.spider.spidermanage.domain.SpiderManage;

/**
 * 爬虫代码类型详情 服务层实现
 * 
 * @author ricozhou
 * @date 2018-06-08
 */
@Service
public class CodeTypeServiceImpl implements ICodeTypeService {
	@Autowired
	private CodeTypeMapper codeTypeMapper;

	/**
	 * 查询爬虫代码类型详情信息
	 * 
	 * @param spiderCodeTypeId
	 *            爬虫代码类型详情ID
	 * @return 爬虫代码类型详情信息
	 */
	@Override
	public CodeType selectCodeTypeById(Integer spiderCodeTypeId) {
		return codeTypeMapper.selectCodeTypeById(spiderCodeTypeId);
	}

	/**
	 * 查询爬虫代码类型详情列表
	 * 
	 * @param codeType
	 *            爬虫代码类型详情信息
	 * @return 爬虫代码类型详情集合
	 */
	@Override
	public List<CodeType> selectCodeTypeList(CodeType codeType) {
		return codeTypeMapper.selectCodeTypeList(codeType);
	}

	/**
	 * 新增爬虫代码类型详情
	 * 
	 * @param codeType
	 *            爬虫代码类型详情信息
	 * @return 结果
	 */
	@Override
	public int insertCodeType(CodeType codeType) {
		return codeTypeMapper.insertCodeType(codeType);
	}

	/**
	 * 修改爬虫代码类型详情
	 * 
	 * @param codeType
	 *            爬虫代码类型详情信息
	 * @return 结果
	 */
	@Override
	public int updateCodeType(CodeType codeType) {
		return codeTypeMapper.updateCodeType(codeType);
	}

	/**
	 * 保存爬虫代码类型详情
	 * 
	 * @param codeType
	 *            爬虫代码类型详情信息
	 * @return 结果
	 */
	@Override
	public int saveCodeType(CodeType codeType) {
		Integer spiderCodeTypeId = codeType.getSpiderCodeTypeId();
		int rows = 0;
		if (StringUtils.isNotNull(spiderCodeTypeId)) {
			rows = codeTypeMapper.updateCodeType(codeType);
		} else {
			rows = codeTypeMapper.insertCodeType(codeType);
		}
		return rows;
	}

	/**
	 * 删除爬虫代码类型详情信息
	 * 
	 * @param spiderCodeTypeId
	 *            爬虫代码类型详情ID
	 * @return 结果
	 */
	@Override
	public int deleteCodeTypeById(Integer spiderCodeTypeId) {
		//加一个判断，如果此类型的爬虫下还有文件，则说明还有爬虫项目，不允许删除
		
		return codeTypeMapper.deleteCodeTypeById(spiderCodeTypeId);
	}

	/**
	 * 批量删除爬虫代码类型详情对象
	 * 
	 * @param spiderCodeTypeIds
	 *            需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int batchDeleteCodeType(Integer[] spiderCodeTypeIds) {
		return codeTypeMapper.batchDeleteCodeType(spiderCodeTypeIds);
	}

	// 校验类型标志
	@Override
	public String checkCustomSpiderTypeUnique(CodeType codeType) {
		if (codeType.getSpiderCodeTypeId() == null) {
			codeType.setSpiderCodeTypeId(-1);
		}
		Integer getSpiderCodeTypeId = codeType.getSpiderCodeTypeId();
		CodeType info = codeTypeMapper.checkCustomSpiderTypeUnique(codeType.getCustomSpiderType());
		if (StringUtils.isNotNull(info) && StringUtils.isNotNull(info.getSpiderCodeTypeId())
				&& info.getSpiderCodeTypeId() != getSpiderCodeTypeId) {
			return UserConstants.CUSTOM_SPIDER_TYPE_NOT_UNIQUE;
		}
		return UserConstants.CUSTOM_SPIDER_TYPE_UNIQUE;
	}

	// 检查目录
	@Override
	public String checkSpiderCodeTypeFolderUnique(CodeType codeType) {
		if (codeType.getSpiderCodeTypeId() == null) {
			codeType.setSpiderCodeTypeId(-1);
		}
		Integer getSpiderCodeTypeId = codeType.getSpiderCodeTypeId();
		CodeType info = codeTypeMapper.checkSpiderCodeTypeFolderUnique(codeType.getSpiderCodeTypeFolder());
		if (StringUtils.isNotNull(info) && StringUtils.isNotNull(info.getSpiderCodeTypeId())
				&& info.getSpiderCodeTypeId() != getSpiderCodeTypeId) {
			return UserConstants.SPIDER_CODE_TYPE_FOLDER_NOT_UNIQUE;
		}

		// 校验环境中有没有此目录
		String filePath = FilePathConfig.getCustomSpiderPath() + File.separator + codeType.getSpiderCodeTypeFolder();
		if (BaseCSUtils.checkFolderExists(filePath)) {
			return UserConstants.SPIDER_CODE_TYPE_FOLDER_NOT_UNIQUE;
		}
		return UserConstants.SPIDER_CODE_TYPE_FOLDER_UNIQUE;
	}

	//根据状态查询
	@Override
	public List<CodeType> selectSpiderCodeTypeByStatus(Integer status) {
		return codeTypeMapper.selectSpiderCodeTypeByStatus(status);
	}
	//检查爬虫代码类型是否存在或者禁用
	@Override
	public CodeType selectSpiderCodeTypeByCustomSpiderType(Integer customSpiderType) {
		return codeTypeMapper.selectSpiderCodeTypeByCustomSpiderType(customSpiderType);
	}

}
