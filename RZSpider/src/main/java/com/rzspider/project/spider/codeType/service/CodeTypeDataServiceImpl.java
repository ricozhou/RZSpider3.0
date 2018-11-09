package com.rzspider.project.spider.codeType.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rzspider.common.utils.StringUtils;
import com.rzspider.project.spider.codeType.domain.CodeTypeData;
import com.rzspider.project.spider.codeType.mapper.CodeTypeDataMapper;

/**
 * 爬虫代码类型数据 服务层实现
 * 
 * @author ricozhou
 * @date 2018-09-03
 */
@Service
public class CodeTypeDataServiceImpl implements ICodeTypeDataService {
	@Autowired
	private CodeTypeDataMapper codeTypeDataMapper;

	/**
	 * 查询爬虫代码类型数据列表
	 * 
	 * @param codeTypeData
	 *            爬虫代码类型数据信息
	 * @return 爬虫代码类型数据集合
	 */
	@Override
	public List<CodeTypeData> selectCodeTypeDataListByStatus(CodeTypeData codeTypeData) {
		return codeTypeDataMapper.selectCodeTypeDataListByStatus(codeTypeData);
	}

}
