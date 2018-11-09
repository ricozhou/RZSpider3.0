package com.rzspider.project.blog.blogcolumn.mapper;

import com.rzspider.project.blog.blogcolumn.domain.Blogcolumn;
import java.util.List;

/**
 * 博客专栏管理 数据层
 * 
 * @author ricozhou
 * @date 2018-09-15
 */
public interface BlogcolumnMapper {

	/**
	 * 查询博客专栏管理信息
	 * 
	 * @param blogColumnId
	 *            博客专栏管理ID
	 * @return 博客专栏管理信息
	 */
	public Blogcolumn selectBlogcolumnById(Integer blogColumnId);

	/**
	 * 查询博客专栏管理列表
	 * 
	 * @param blogcolumn
	 *            博客专栏管理信息
	 * @return 博客专栏管理集合
	 */
	public List<Blogcolumn> selectBlogcolumnListAll(Blogcolumn blogcolumn);

	/**
	 * 新增博客专栏管理
	 * 
	 * @param blogcolumn
	 *            博客专栏管理信息
	 * @return 结果
	 */
	public int insertBlogcolumn(Blogcolumn blogcolumn);

	/**
	 * 修改博客专栏管理
	 * 
	 * @param blogcolumn
	 *            博客专栏管理信息
	 * @return 结果
	 */
	public int updateBlogcolumn(Blogcolumn blogcolumn);

	/**
	 * 删除博客专栏管理
	 * 
	 * @param blogColumnId
	 *            博客专栏管理ID
	 * @return 结果
	 */
	public int deleteBlogcolumnById(Integer blogColumnId);

	/**
	 * 批量删除博客专栏管理
	 * 
	 * @param blogColumnIds
	 *            需要删除的数据ID
	 * @return 结果
	 */
	public int batchDeleteBlogcolumn(Integer[] blogColumnIds);

	/**
	 * @date Sep 17, 2018 11:31:44 AM
	 * @Desc
	 * @param blogcolumn
	 * @return
	 */
	public Blogcolumn checkColumnNameUnique(Blogcolumn blogcolumn);

	/**
	 * @date Sep 17, 2018 11:31:52 AM
	 * @Desc
	 * @param blogcolumn
	 * @return
	 */
	public Blogcolumn checkUrlUnique(Blogcolumn blogcolumn);

	/**
	 * @date Sep 17, 2018 2:17:48 PM
	 * @Desc
	 * @param i
	 * @param string
	 * @param object
	 * @return
	 */
	public List<Blogcolumn> selectBlogcolumnListByStatusAndTypeId(Blogcolumn blogcolumn);

	/**
	 * @date Sep 18, 2018 10:57:03 AM
	 * @Desc
	 * @param url
	 * @return
	 */
	public Blogcolumn selectBlogcolumnByUrl(String url);

	/**
	 * @date Sep 18, 2018 10:57:07 AM
	 * @Desc
	 * @param parentId
	 * @return
	 */
	public Blogcolumn selectBlogPcolumnByPId(Integer parentId);

	/**
	 * @date Sep 20, 2018 1:30:45 PM
	 * @Desc
	 * @return
	 */
	public List<Blogcolumn> selectBlogCcolumnList();

	/**
	 * @date Sep 25, 2018 3:34:32 PM
	 * @Desc
	 * @param columnName
	 * @return
	 */
	public Blogcolumn selectBlogcolumnByColumn(String columnName);

	/**
	 * @date Oct 25, 2018 2:21:40 PM
	 * @Desc
	 * @return
	 */
	public Integer selectAllBlogColumnNum();

}