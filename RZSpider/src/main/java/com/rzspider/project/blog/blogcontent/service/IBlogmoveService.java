package com.rzspider.project.blog.blogcontent.service;

import com.rzspider.project.blog.blogcontent.domain.Blogmove;
import java.util.List;

/**
 * 博客搬家详情 服务层
 * 
 * @author ricozhou
 * @date 2018-10-19
 */
public interface IBlogmoveService {

	/**
	 * 查询博客搬家详情信息
	 * 
	 * @param blogMoveId
	 *            博客搬家详情ID
	 * @return 博客搬家详情信息
	 */
	public Blogmove selectBlogmoveById(Integer blogMoveId);

	/**
	 * 查询博客搬家详情列表
	 * 
	 * @param blogmove
	 *            博客搬家详情信息
	 * @return 博客搬家详情集合
	 */
	public List<Blogmove> selectBlogmoveList(Blogmove blogmove);

	/**
	 * 新增博客搬家详情
	 * 
	 * @param blogmove
	 *            博客搬家详情信息
	 * @return 结果
	 */
	public int insertBlogmove(Blogmove blogmove);

	/**
	 * 修改博客搬家详情
	 * 
	 * @param blogmove
	 *            博客搬家详情信息
	 * @return 结果
	 */
	public int updateBlogmove(Blogmove blogmove);

	/**
	 * 删除博客搬家详情信息
	 * 
	 * @param blogMoveId
	 *            博客搬家详情ID
	 * @return 结果
	 */
	public int deleteBlogmoveById(Integer blogMoveId);

	/**
	 * 批量删除博客搬家详情信息
	 * 
	 * @param blogMoveIds
	 *            需要删除的数据ID
	 * @return 结果
	 */
	public int batchDeleteBlogmove(Integer[] blogMoveIds);

	/**
	 * @date Oct 19, 2018 3:19:13 PM
	 * @Desc
	 * @param blogMove
	 * @return
	 */
	public Integer blogMoveRun(Blogmove blogMove);

	/**
	 * @date Oct 22, 2018 10:17:18 AM
	 * @Desc
	 * @param blogMove
	 * @return
	 */
	boolean blogMoveStop(Blogmove blogMove);

	/**
	 * @date Oct 29, 2018 9:35:28 AM
	 * @Desc
	 * @param loginName
	 * @return
	 */
	public int checkMoveStatus(String loginName);

}
