package com.rzspider.project.blog.blogcomment.service;

import com.rzspider.project.blog.blogcomment.domain.Blogcomment;
import java.util.List;

/**
 * 博客评论 服务层
 * 
 * @author ricozhou
 * @date 2018-11-07
 */
public interface IBlogcommentService {

	/**
	 * 查询博客评论信息
	 * 
	 * @param blogCommentId
	 *            博客评论ID
	 * @return 博客评论信息
	 */
	public Blogcomment selectBlogcommentById(Integer blogCommentId);

	/**
	 * 查询博客评论列表
	 * 
	 * @param blogcomment
	 *            博客评论信息
	 * @return 博客评论集合
	 */
	public List<Blogcomment> selectBlogcommentList(Blogcomment blogcomment);

	/**
	 * 新增博客评论
	 * 
	 * @param blogcomment
	 *            博客评论信息
	 * @return 结果
	 */
	public int insertBlogcomment(Blogcomment blogcomment);

	/**
	 * 修改博客评论
	 * 
	 * @param blogcomment
	 *            博客评论信息
	 * @return 结果
	 */
	public int updateBlogcomment(Blogcomment blogcomment);

	/**
	 * 保存博客评论
	 * 
	 * @param blogcomment
	 *            博客评论信息
	 * @return 结果
	 */
	public int saveBlogcomment(Blogcomment blogcomment);

	/**
	 * 删除博客评论信息
	 * 
	 * @param blogCommentId
	 *            博客评论ID
	 * @return 结果
	 */
	public int deleteBlogcommentById(Integer blogCommentId);

	/**
	 * 批量删除博客评论信息
	 * 
	 * @param blogCommentIds
	 *            需要删除的数据ID
	 * @return 结果
	 */
	public int batchDeleteBlogcomment(Integer[] blogCommentIds);

	/**
	 * @param showId
	 * @date Nov 7, 2018 4:42:58 PM
	 * @Desc
	 * @return
	 */
	public List<Blogcomment> selectBlogcommentListToShow(String showId);

	/**
	 * @date Nov 8, 2018 9:19:24 AM
	 * @Desc
	 * @param blogcomment
	 * @return
	 */
	public int[] addBlogcomment(Blogcomment blogcomment);

	/**
	 * @date Nov 8, 2018 4:07:40 PM
	 * @Desc
	 * @param showId
	 */
	public int deleteBlogcommentByShowId(String showId);

	/**
	 * @date Nov 9, 2018 1:19:02 PM
	 * @Desc
	 * @param blogCommentId
	 * @return
	 */
	public int addCommentLike(Integer blogCommentId);

}
