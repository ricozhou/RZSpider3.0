package com.rzspider.project.blog.blogkeyword.service;

import com.rzspider.project.blog.blogkeyword.domain.Blogkeyword;
import java.util.List;

/**
 * 关键词管理 服务层
 * 
 * @author ricozhou
 * @date 2018-11-08
 */
public interface IBlogkeywordService {

	/**
	 * 查询关键词管理信息
	 * 
	 * @param blogKeywordId
	 *            关键词管理ID
	 * @return 关键词管理信息
	 */
	public Blogkeyword selectBlogkeywordById(Integer blogKeywordId);

	/**
	 * 查询关键词管理列表
	 * 
	 * @param blogkeyword
	 *            关键词管理信息
	 * @return 关键词管理集合
	 */
	public List<Blogkeyword> selectBlogkeywordList(Blogkeyword blogkeyword);

	/**
	 * 新增关键词管理
	 * 
	 * @param blogkeyword
	 *            关键词管理信息
	 * @return 结果
	 */
	public int insertBlogkeyword(Blogkeyword blogkeyword);

	/**
	 * 修改关键词管理
	 * 
	 * @param blogkeyword
	 *            关键词管理信息
	 * @return 结果
	 */
	public int updateBlogkeyword(Blogkeyword blogkeyword);

	/**
	 * 保存关键词管理
	 * 
	 * @param blogkeyword
	 *            关键词管理信息
	 * @return 结果
	 */
	public int saveBlogkeyword(Blogkeyword blogkeyword);

	/**
	 * 删除关键词管理信息
	 * 
	 * @param blogKeywordId
	 *            关键词管理ID
	 * @return 结果
	 */
	public int deleteBlogkeywordById(Integer blogKeywordId);

	/**
	 * 批量删除关键词管理信息
	 * 
	 * @param blogKeywordIds
	 *            需要删除的数据ID
	 * @return 结果
	 */
	public int batchDeleteBlogkeyword(Integer[] blogKeywordIds);

	/**
	 * @date Nov 8, 2018 1:54:38 PM
	 * @Desc
	 * @param blogkeyword
	 * @return
	 */
	public String checkKeywordNameUnique(Blogkeyword blogkeyword);

	/**
	 * @date Nov 8, 2018 3:10:03 PM
	 * @Desc
	 * @return
	 */
	public List<Blogkeyword> selectBlogCommentkeywordList();

	/**
	 * @date Nov 8, 2018 3:10:03 PM
	 * @Desc
	 * @return
	 */
	public List<Blogkeyword> selectBlogMessagekeywordList();

	/**
	 * @date Nov 8, 2018 3:10:03 PM
	 * @Desc
	 * @return
	 */
	public List<Blogkeyword> selectBlogSuggestionkeywordList();

}
