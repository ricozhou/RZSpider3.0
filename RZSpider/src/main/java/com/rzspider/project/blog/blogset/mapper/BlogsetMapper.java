package com.rzspider.project.blog.blogset.mapper;

import com.rzspider.project.blog.blogset.domain.Blogset;
import java.util.List;

/**
 * 博客设置详情 数据层
 * 
 * @author ricozhou
 * @date 2018-09-13
 */
public interface BlogsetMapper {

	/**
	 * 查询博客设置详情信息
	 * 
	 * @param blogSetId
	 *            博客设置详情ID
	 * @return 博客设置详情信息
	 */
	public Blogset selectBlogsetById(Integer blogSetId);

	/**
	 * 查询博客设置详情列表
	 * 
	 * @param blogset
	 *            博客设置详情信息
	 * @return 博客设置详情集合
	 */
	public List<Blogset> selectBlogsetList(Blogset blogset);

	/**
	 * 新增博客设置详情
	 * 
	 * @param blogset
	 *            博客设置详情信息
	 * @return 结果
	 */
	public int insertBlogset(Blogset blogset);

	/**
	 * 修改博客设置详情
	 * 
	 * @param blogset
	 *            博客设置详情信息
	 * @return 结果
	 */
	public int updateBlogset(Blogset blogset);

	/**
	 * 删除博客设置详情
	 * 
	 * @param blogSetId
	 *            博客设置详情ID
	 * @return 结果
	 */
	public int deleteBlogsetById(Integer blogSetId);

	/**
	 * 批量删除博客设置详情
	 * 
	 * @param blogSetIds
	 *            需要删除的数据ID
	 * @return 结果
	 */
	public int batchDeleteBlogset(Integer[] blogSetIds);

	/**
	 * @date Sep 13, 2018 2:33:23 PM
	 * @Desc
	 * @param blogset
	 * @return
	 */
	public int updateBasicset(Blogset blogset);

	/**
	 * @date Sep 13, 2018 2:33:30 PM
	 * @Desc
	 * @param blogset
	 * @return
	 */
	public int updateBloggerset(Blogset blogset);

	/**
	 * @date Sep 18, 2018 5:25:29 PM
	 * @Desc
	 * @param blogSetId
	 * @return
	 */
	public Blogset selectSomeBloggersetById(Integer blogSetId);

	/**
	 * @date Sep 27, 2018 9:35:46 AM
	 * @Desc
	 * @param blogSetId
	 * @return
	 */
	public Blogset selectSomeBlogsetById(Integer blogSetId);

	/**
	 * @date Oct 8, 2018 3:35:42 PM
	 * @Desc
	 * @param blogSetId
	 * @return
	 */
	public Blogset selectSomeBlogsetsById(Integer blogSetId);

	/**
	 * @date Oct 9, 2018 3:47:04 PM
	 * @Desc
	 * @param blogset
	 * @return
	 */
	public int updateStyleset(Blogset blogset);

	/**
	 * @date Oct 18, 2018 12:49:55 PM
	 * @Desc
	 * @param blogSetId
	 * @return
	 */
	public Blogset selectBlogsetWaterMarkMsgById(Integer blogSetId);

	/**
	 * @date Oct 24, 2018 5:46:16 PM
	 * @Desc
	 * @param blogSetId
	 * @return
	 */
	public Blogset selectSomeOverViewBlogsetById(Integer blogSetId);

	/**
	 * @date Oct 30, 2018 11:05:15 AM
	 * @Desc
	 * @param blogSetId
	 * @return
	 */
	public Blogset selectBlogsetBlogMoveMsgById(Integer blogSetId);

	/**
	 * @date Nov 8, 2018 3:38:48 PM
	 * @Desc
	 * @param blogSetId
	 * @return
	 */
	public Blogset selectBlogsetCommentReviewMsgById(Integer blogSetId);

}