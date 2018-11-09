package com.rzspider.project.blog.blognotice.service;

import com.rzspider.project.blog.blognotice.domain.Blognotice;
import java.util.List;

/**
 * 博客公告 服务层
 * 
 * @author ricozhou
 * @date 2018-10-23
 */
public interface IBlognoticeService {

	/**
	 * 查询博客公告信息
	 * 
	 * @param blogNoticeId
	 *            博客公告ID
	 * @return 博客公告信息
	 */
	public Blognotice selectBlognoticeById(Integer blogNoticeId);

	/**
	 * 查询博客公告列表
	 * 
	 * @param blognotice
	 *            博客公告信息
	 * @return 博客公告集合
	 */
	public List<Blognotice> selectBlognoticeList(Blognotice blognotice);

	/**
	 * 新增博客公告
	 * 
	 * @param blognotice
	 *            博客公告信息
	 * @return 结果
	 */
	public int insertBlognotice(Blognotice blognotice);

	/**
	 * 修改博客公告
	 * 
	 * @param blognotice
	 *            博客公告信息
	 * @return 结果
	 */
	public int updateBlognotice(Blognotice blognotice);

	/**
	 * 保存博客公告
	 * 
	 * @param blognotice
	 *            博客公告信息
	 * @return 结果
	 */
	public int saveBlognotice(Blognotice blognotice);

	/**
	 * 删除博客公告信息
	 * 
	 * @param blogNoticeId
	 *            博客公告ID
	 * @return 结果
	 */
	public int deleteBlognoticeById(Integer blogNoticeId);

	/**
	 * 批量删除博客公告信息
	 * 
	 * @param blogNoticeIds
	 *            需要删除的数据ID
	 * @return 结果
	 */
	public int batchDeleteBlognotice(Integer[] blogNoticeIds);

	/**
	 * @date Oct 23, 2018 1:56:29 PM
	 * @Desc
	 * @return
	 */
	List<Blognotice> selectOpenBlognoticeList();

	/**
	 * @date Oct 25, 2018 2:06:41 PM
	 * @Desc
	 * @return
	 */
	public Integer selectAllBlogNoticeNum();

}
