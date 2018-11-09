package com.rzspider.project.spider.codeType.service;

import com.rzspider.project.spider.codeType.domain.CodeType;
import java.util.List;

/**
 * 爬虫代码类型详情 服务层
 * 
 * @author ricozhou
 * @date 2018-06-08
 */
public interface ICodeTypeService 
{
	
	/**
     * 查询爬虫代码类型详情信息
     * 
     * @param spiderCodeTypeId 爬虫代码类型详情ID
     * @return 爬虫代码类型详情信息
     */
	public CodeType selectCodeTypeById(Integer spiderCodeTypeId);
	
	/**
     * 查询爬虫代码类型详情列表
     * 
     * @param codeType 爬虫代码类型详情信息
     * @return 爬虫代码类型详情集合
     */
	public List<CodeType> selectCodeTypeList(CodeType codeType);
	
	/**
     * 新增爬虫代码类型详情
     * 
     * @param codeType 爬虫代码类型详情信息
     * @return 结果
     */
	public int insertCodeType(CodeType codeType);
	
	/**
     * 修改爬虫代码类型详情
     * 
     * @param codeType 爬虫代码类型详情信息
     * @return 结果
     */
	public int updateCodeType(CodeType codeType);
	
	/**
     * 保存爬虫代码类型详情
     * 
     * @param codeType 爬虫代码类型详情信息
     * @return 结果
     */
	public int saveCodeType(CodeType codeType);
	
	/**
     * 删除爬虫代码类型详情信息
     * 
     * @param spiderCodeTypeId 爬虫代码类型详情ID
     * @return 结果
     */
	public int deleteCodeTypeById(Integer spiderCodeTypeId);
	
	/**
     * 批量删除爬虫代码类型详情信息
     * 
     * @param spiderCodeTypeIds 需要删除的数据ID
     * @return 结果
     */
	public int batchDeleteCodeType(Integer[] spiderCodeTypeIds);

	//校验类型标志
	public String checkCustomSpiderTypeUnique(CodeType codeType);

	//校验目录
	public String checkSpiderCodeTypeFolderUnique(CodeType codeType);

	//根据状态查询
	public List<CodeType> selectSpiderCodeTypeByStatus(Integer status);
	//检查爬虫代码类型是否存在或者禁用
	public CodeType selectSpiderCodeTypeByCustomSpiderType(Integer customSpiderType);
	
}
