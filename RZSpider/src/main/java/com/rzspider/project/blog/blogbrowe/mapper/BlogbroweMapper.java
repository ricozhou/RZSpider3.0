package com.rzspider.project.blog.blogbrowe.mapper;

import com.rzspider.project.blog.blogbrowe.domain.Blogbrowe;
import java.util.List;

/**
 * 博客浏览日志 数据层
 * 
 * @author ricozhou
 * @date 2018-10-25
 */
public interface BlogbroweMapper {

	/**
	 * 查询博客浏览日志信息
	 * 
	 * @param blogBroweId
	 *            博客浏览日志ID
	 * @return 博客浏览日志信息
	 */
	public Blogbrowe selectBlogbroweById(Integer blogBroweId);

	/**
	 * 查询博客浏览日志列表
	 * 
	 * @param blogbrowe
	 *            博客浏览日志信息
	 * @return 博客浏览日志集合
	 */
	public List<Blogbrowe> selectBlogbroweList(Blogbrowe blogbrowe);

	/**
	 * 新增博客浏览日志
	 * 
	 * @param blogbrowe
	 *            博客浏览日志信息
	 * @return 结果
	 */
	public int insertBlogbrowe(Blogbrowe blogbrowe);

	/**
	 * 修改博客浏览日志
	 * 
	 * @param blogbrowe
	 *            博客浏览日志信息
	 * @return 结果
	 */
	public int updateBlogbrowe(Blogbrowe blogbrowe);

	/**
	 * 删除博客浏览日志
	 * 
	 * @param blogBroweId
	 *            博客浏览日志ID
	 * @return 结果
	 */
	public int deleteBlogbroweById(Integer blogBroweId);

	/**
	 * 批量删除博客浏览日志
	 * 
	 * @param blogBroweIds
	 *            需要删除的数据ID
	 * @return 结果
	 */
	public int batchDeleteBlogbrowe(Integer[] blogBroweIds);

	/**
	 * @date Oct 25, 2018 2:08:34 PM
	 * @Desc
	 * @return
	 */
	public Integer selectAllBlogBroweNum();

	/**
	 * @date Oct 25, 2018 3:16:26 PM
	 * @Desc
	 * @return
	 */
	public Integer selectDayBlogBroweNum();

	/**
	 * @date Oct 25, 2018 3:16:32 PM
	 * @Desc
	 * @return
	 */
	public Integer selectMonthBlogBroweNum();

	/**
	 * @date Oct 25, 2018 3:16:38 PM
	 * @Desc
	 * @return
	 */
	public Integer selectYearBlogBroweNum();

	/**
	 * @date Oct 25, 2018 5:11:37 PM
	 * @Desc
	 * @return
	 */
	public Integer selectDayBlogBroweNumPre();

	/**
	 * @date Oct 25, 2018 5:11:42 PM
	 * @Desc
	 * @return
	 */
	public Integer selectMonthBlogBroweNumPre();

	/**
	 * @date Oct 25, 2018 5:11:46 PM
	 * @Desc
	 * @return
	 */
	public Integer selectYearBlogBroweNumPre();

	/**
	 * @date Oct 26, 2018 10:28:38 AM
	 * @Desc
	 * @param clientBrowseCity
	 * @return
	 */
	public Integer selectBlogBroweNumByCityName(String clientBrowseCity);

	/**
	 * @date Oct 26, 2018 11:36:52 AM
	 * @Desc
	 * @param dayNum
	 * @return
	 */
	public Integer selectBlogBroweNumPreByDayNum(Integer dayNum);

}