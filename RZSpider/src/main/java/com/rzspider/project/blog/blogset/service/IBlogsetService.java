package com.rzspider.project.blog.blogset.service;

import com.rzspider.project.blog.blogcontent.domain.Blogcontent;
import com.rzspider.project.blog.blogset.domain.Blogset;
import java.util.List;

/**
 * 博客设置详情 服务层
 * 
 * @author ricozhou
 * @date 2018-09-13
 */
public interface IBlogsetService {

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
	 * 保存博客设置详情
	 * 
	 * @param blogset
	 *            博客设置详情信息
	 * @return 结果
	 */
	public int saveBlogset(Blogset blogset);

	/**
	 * @date Sep 13, 2018 2:30:50 PM
	 * @Desc
	 * @param blogset
	 * @return
	 */
	public int saveBasicset(Blogset blogset);

	/**
	 * @date Sep 13, 2018 2:30:57 PM
	 * @Desc
	 * @param blogset
	 * @return
	 */
	public int saveBloggerset(Blogset blogset);

	/**
	 * @date Sep 18, 2018 5:22:01 PM
	 * @Desc
	 * @param i
	 * @return
	 */
	public Blogset selectSomeBloggersetById(Integer blogSetId);

	/**
	 * @date Sep 27, 2018 9:33:27 AM
	 * @Desc
	 * @param i
	 * @return
	 */
	public Blogset selectSomeBlogsetById(Integer blogSetId);

	/**
	 * @date Sep 28, 2018 9:30:41 AM
	 * @Desc
	 * @return
	 */
	Blogset getBlogsetMsg();

	/**
	 * @date Oct 8, 2018 3:34:53 PM
	 * @Desc
	 * @param i
	 * @return
	 */
	public Blogset selectSomeBlogsetsById(Integer blogSetId);

	/**
	 * @date Oct 9, 2018 3:26:31 PM
	 * @Desc
	 * @param blogset
	 * @return
	 */
	public int saveStyleset(Blogset blogset);

	/**
	 * @date Oct 18, 2018 12:49:12 PM
	 * @Desc
	 * @param i
	 * @return
	 */
	public Blogset selectBlogsetWaterMarkMsgById(Integer blogSetId);

	/**
	 * @date Oct 24, 2018 5:45:43 PM
	 * @Desc
	 * @param i
	 * @return
	 */
	public Blogset selectSomeOverViewBlogsetById(Integer blogSetId);

	/**
	 * @date Oct 30, 2018 11:04:30 AM
	 * @Desc
	 * @param i
	 * @return
	 */
	public Blogset selectBlogsetBlogMoveMsgById(Integer blogSetId);

	/**
	 * @date Nov 8, 2018 2:59:32 PM
	 * @Desc
	 * @param i
	 * @return
	 */
	public Blogset selectBlogsetCommentReviewMsgById(Integer blogSetId);

}
