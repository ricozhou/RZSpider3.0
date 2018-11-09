package com.rzspider.project.blog.blogbrowe.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rzspider.common.utils.StringUtils;
import com.rzspider.project.blog.blogbrowe.mapper.BlogbroweMapper;
import com.rzspider.project.blog.blogbrowe.domain.Blogbrowe;
import com.rzspider.project.blog.blogbrowe.service.IBlogbroweService;

/**
 * 博客浏览日志 服务层实现
 * 
 * @author ricozhou
 * @date 2018-10-25
 */
@Service
public class BlogbroweServiceImpl implements IBlogbroweService {
	@Autowired
	private BlogbroweMapper blogbroweMapper;

	/**
	 * 查询博客浏览日志信息
	 * 
	 * @param blogBroweId
	 *            博客浏览日志ID
	 * @return 博客浏览日志信息
	 */
	@Override
	public Blogbrowe selectBlogbroweById(Integer blogBroweId) {
		return blogbroweMapper.selectBlogbroweById(blogBroweId);
	}

	/**
	 * 查询博客浏览日志列表
	 * 
	 * @param blogbrowe
	 *            博客浏览日志信息
	 * @return 博客浏览日志集合
	 */
	@Override
	public List<Blogbrowe> selectBlogbroweList(Blogbrowe blogbrowe) {
		return blogbroweMapper.selectBlogbroweList(blogbrowe);
	}

	/**
	 * 新增博客浏览日志
	 * 
	 * @param blogbrowe
	 *            博客浏览日志信息
	 * @return 结果
	 */
	@Override
	public int insertBlogbrowe(Blogbrowe blogbrowe) {
		return blogbroweMapper.insertBlogbrowe(blogbrowe);
	}

	/**
	 * 保存博客浏览日志
	 * 
	 * @param blogbrowe
	 *            博客浏览日志信息
	 * @return 结果
	 */
	@Override
	public int saveBlogbrowe(Blogbrowe blogbrowe) {
		int rows = blogbroweMapper.insertBlogbrowe(blogbrowe);
		return rows;
	}

	/**
	 * 删除博客浏览日志信息
	 * 
	 * @param blogBroweId
	 *            博客浏览日志ID
	 * @return 结果
	 */
	@Override
	public int deleteBlogbroweById(Integer blogBroweId) {
		return blogbroweMapper.deleteBlogbroweById(blogBroweId);
	}

	/**
	 * 批量删除博客浏览日志对象
	 * 
	 * @param blogBroweIds
	 *            需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int batchDeleteBlogbrowe(Integer[] blogBroweIds) {
		return blogbroweMapper.batchDeleteBlogbrowe(blogBroweIds);
	}

	/**
	 * 查询总浏览量
	 * 
	 */
	@Override
	public Integer selectAllBlogBroweNum() {
		return blogbroweMapper.selectAllBlogBroweNum();
	}

	/**
	 * 查询日浏览量
	 * 
	 */
	@Override
	public Integer selectDayBlogBroweNum() {
		return blogbroweMapper.selectDayBlogBroweNum();
	}

	/**
	 * 查询月浏览量
	 * 
	 */
	@Override
	public Integer selectMonthBlogBroweNum() {
		return blogbroweMapper.selectMonthBlogBroweNum();
	}

	/**
	 * 查询年浏览量
	 * 
	 */
	@Override
	public Integer selectYearBlogBroweNum() {
		return blogbroweMapper.selectYearBlogBroweNum();
	}

	/**
	 * 查询上一天浏览量
	 * 
	 */
	@Override
	public Integer selectDayBlogBroweNumPre() {
		return blogbroweMapper.selectDayBlogBroweNumPre();
	}

	/**
	 * 查询上一月浏览量
	 * 
	 */
	@Override
	public Integer selectMonthBlogBroweNumPre() {
		return blogbroweMapper.selectMonthBlogBroweNumPre();
	}

	/**
	 * 查询上一年浏览量
	 * 
	 */
	@Override
	public Integer selectYearBlogBroweNumPre() {
		return blogbroweMapper.selectYearBlogBroweNumPre();
	}

	/**
	 * 根据省份查询浏览量
	 * 
	 */
	@Override
	public Integer selectBlogBroweNumByCityName(String clientBrowseCity) {
		return blogbroweMapper.selectBlogBroweNumByCityName(clientBrowseCity);
	}

	/**
	 * 根据相差天数查询
	 * 
	 */
	@Override
	public Integer selectBlogBroweNumPreByDayNum(Integer dayNum) {
		return blogbroweMapper.selectBlogBroweNumPreByDayNum(dayNum);
	}

}
