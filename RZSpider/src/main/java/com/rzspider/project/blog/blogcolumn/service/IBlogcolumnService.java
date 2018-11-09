package com.rzspider.project.blog.blogcolumn.service;

import com.rzspider.project.blog.blogcolumn.domain.Blogcolumn;
import java.util.List;

/**
 * 博客专栏管理 服务层
 * 
 * @author ricozhou
 * @date 2018-09-15
 */
public interface IBlogcolumnService 
{
	
	/**
     * 查询博客专栏管理信息
     * 
     * @param blogColumnId 博客专栏管理ID
     * @return 博客专栏管理信息
     */
	public Blogcolumn selectBlogcolumnById(Integer blogColumnId);
	
	/**
     * 查询博客专栏管理列表
     * 
     * @param blogcolumn 博客专栏管理信息
     * @return 博客专栏管理集合
     */
	public List<Blogcolumn> selectBlogcolumnListAll(Blogcolumn blogcolumn);
	
	/**
     * 新增博客专栏管理
     * 
     * @param blogcolumn 博客专栏管理信息
     * @return 结果
     */
	public int insertBlogcolumn(Blogcolumn blogcolumn);
	
	/**
     * 修改博客专栏管理
     * 
     * @param blogcolumn 博客专栏管理信息
     * @return 结果
     */
	public int updateBlogcolumn(Blogcolumn blogcolumn);
	
	/**
     * 保存博客专栏管理
     * 
     * @param blogcolumn 博客专栏管理信息
     * @return 结果
     */
	public int saveBlogcolumn(Blogcolumn blogcolumn);
	
	/**
     * 删除博客专栏管理信息
     * 
     * @param blogColumnId 博客专栏管理ID
     * @return 结果
     */
	public int deleteBlogcolumnById(Integer blogColumnId);
	
	/**
	 * @date Sep 17, 2018 11:23:35 AM
	 * @Desc
	 * @param blogcolumn
	 * @return
	 */
	public String checkColumnNameUnique(Blogcolumn blogcolumn);

	/**
	 * @date Sep 17, 2018 11:23:41 AM
	 * @Desc
	 * @param blogcolumn
	 * @return
	 */
	public String checkUrlUnique(Blogcolumn blogcolumn);

	/**
	 * @date Sep 17, 2018 1:55:55 PM
	 * @Desc
	 * @param i
	 * @return
	 */
	public List<Blogcolumn> selectBlogcolumnMessageList();

	/**
	 * @date Sep 18, 2018 10:45:41 AM
	 * @Desc
	 * @param parentUrl
	 * @param b
	 * @return
	 */
	public Blogcolumn selectBlogcolumnMessageByUrl(String url, boolean isM);

	/**
	 * @date Sep 20, 2018 1:16:18 PM
	 * @Desc
	 * @return
	 */
	public List<Blogcolumn> selectBlogCcolumnList();

	/**
	 * @date Sep 25, 2018 3:33:53 PM
	 * @Desc
	 * @param blogColumnName
	 * @param b
	 * @return
	 */
	public Blogcolumn selectBlogcolumnMessageByColumn(String blogColumnName, boolean isM);

	/**
	 * @date Oct 25, 2018 2:06:36 PM
	 * @Desc
	 * @return
	 */
	public Integer selectAllBlogColumnNum();
	
}
