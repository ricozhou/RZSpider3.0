package com.rzspider.project.spider.codeType.mapper;

import java.util.List;

import com.rzspider.project.spider.codeType.domain.CodeTypeData;

/**
 * 爬虫代码类型数据 数据层
 * 
 * @author ricozhou
 * @date 2018-09-03
 */
public interface CodeTypeDataMapper {

	/**
	 * 查询爬虫代码类型数据列表
	 * 
	 * @param codeTypeData
	 *            爬虫代码类型数据信息
	 * @return 爬虫代码类型数据集合
	 */
	public List<CodeTypeData> selectCodeTypeDataListByStatus(CodeTypeData codeTypeData);

}