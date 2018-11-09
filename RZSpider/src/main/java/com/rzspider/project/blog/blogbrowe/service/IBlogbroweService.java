package com.rzspider.project.blog.blogbrowe.service;

import com.rzspider.project.blog.blogbrowe.domain.Blogbrowe;
import java.util.List;

/**
 * 博客浏览日志 服务层
 * 
 * @author ricozhou
 * @date 2018-10-25
 */
public interface IBlogbroweService {

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
	 * 保存博客浏览日志
	 * 
	 * @param blogbrowe
	 *            博客浏览日志信息
	 * @return 结果
	 */
	public int saveBlogbrowe(Blogbrowe blogbrowe);

	/**
	 * 删除博客浏览日志信息
	 * 
	 * @param blogBroweId
	 *            博客浏览日志ID
	 * @return 结果
	 */
	public int deleteBlogbroweById(Integer blogBroweId);

	/**
	 * 批量删除博客浏览日志信息
	 * 
	 * @param blogBroweIds
	 *            需要删除的数据ID
	 * @return 结果
	 */
	public int batchDeleteBlogbrowe(Integer[] blogBroweIds);

	/**
	 * @date Oct 25, 2018 2:05:34 PM
	 * @Desc
	 * @return
	 */
	public Integer selectAllBlogBroweNum();

	/**
	 * @date Oct 25, 2018 2:44:34 PM
	 * @Desc
	 * @return
	 */
	public Integer selectDayBlogBroweNum();

	/**
	 * @date Oct 25, 2018 2:44:50 PM
	 * @Desc
	 * @return
	 */
	public Integer selectMonthBlogBroweNum();

	/**
	 * @date Oct 25, 2018 2:45:05 PM
	 * @Desc
	 * @return
	 */
	public Integer selectYearBlogBroweNum();

	/**
	 * @date Oct 25, 2018 5:00:36 PM
	 * @Desc
	 * @return
	 */
	public Integer selectDayBlogBroweNumPre();

	/**
	 * @date Oct 25, 2018 5:06:58 PM
	 * @Desc
	 * @return
	 */
	public Integer selectMonthBlogBroweNumPre();

	/**
	 * @date Oct 25, 2018 5:08:32 PM
	 * @Desc
	 * @return
	 */
	public Integer selectYearBlogBroweNumPre();

	/**
	 * @date Oct 26, 2018 10:20:05 AM
	 * @Desc
	 * @param key
	 * @return
	 */
	public Integer selectBlogBroweNumByCityName(String clientBrowseCity);

	/**
	 * @date Oct 26, 2018 11:32:44 AM
	 * @Desc
	 * @param i
	 * @return
	 */
	public Integer selectBlogBroweNumPreByDayNum(Integer dayNum);

}
