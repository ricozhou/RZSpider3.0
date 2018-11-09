package com.rzspider.project.blog.blogcontent.mapper;

import com.rzspider.project.blog.blogcontent.domain.Blogcontent;
import java.util.List;

/**
 * 文章内容 数据层
 * 
 * @author ricozhou
 * @date 2018-06-12
 */
public interface BlogcontentMapper {

	/**
	 * 查询文章内容信息
	 * 
	 * @param cid
	 *            文章内容ID
	 * @return 文章内容信息
	 */
	public Blogcontent selectBlogcontentById(Long cid);

	/**
	 * 查询文章内容列表
	 * 
	 * @param blogcontent
	 *            文章内容信息
	 * @return 文章内容集合
	 */
	public List<Blogcontent> selectBlogcontentList(Blogcontent blogcontent);

	/**
	 * 查询文章内容列表
	 * 
	 * @param blogcontent
	 *            文章内容信息
	 * @return 文章内容集合
	 */
	public List<Blogcontent> selectBlogcontentListWithoutContent(Blogcontent blogcontent);

	/**
	 * 新增文章内容
	 * 
	 * @param blogcontent
	 *            文章内容信息
	 * @return 结果
	 */
	public int insertBlogcontent(Blogcontent blogcontent);

	/**
	 * 修改文章内容
	 * 
	 * @param blogcontent
	 *            文章内容信息
	 * @return 结果
	 */
	public int updateBlogcontent(Blogcontent blogcontent);

	/**
	 * 删除文章内容
	 * 
	 * @param cid
	 *            文章内容ID
	 * @return 结果
	 */
	public int deleteBlogcontentById(Long cid);

	/**
	 * 批量删除文章内容
	 * 
	 * @param cids
	 *            需要删除的数据ID
	 * @return 结果
	 */
	public int batchDeleteBlogcontent(Long[] cids);

	/**
	 * @date Sep 18, 2018 9:20:23 AM
	 * @Desc
	 * @param blogcontent
	 * @return
	 */
	public List<Blogcontent> selectBlogMColumncontentListWithoutContent(Blogcontent blogcontent);

	/**
	 * @date Sep 18, 2018 9:20:28 AM
	 * @Desc
	 * @param blogcontent
	 * @return
	 */
	public List<Blogcontent> selectBlogCColumncontentListWithoutContent(Blogcontent blogcontent);

	/**
	 * @date Sep 21, 2018 2:41:22 PM
	 * @Desc
	 * @param showId
	 * @return
	 */
	public Blogcontent selectBlogcontentByShowId(String showId);

	/**
	 * @date Sep 25, 2018 1:30:24 PM
	 * @Desc
	 * @param blogcontent
	 * @return
	 */
	public List<Blogcontent> selectBlogTagscontentListWithoutContent(Blogcontent blogcontent);

	/**
	 * @date Sep 25, 2018 4:56:35 PM
	 * @Desc
	 * @param cid
	 * @return
	 */
	public int updateBrowseNum(Long cid);

	/**
	 * @date Sep 25, 2018 4:56:40 PM
	 * @Desc
	 * @param cid
	 * @return
	 */
	public int updateLikeNum(Long cid);

	/**
	 * @date Sep 27, 2018 10:01:58 AM
	 * @Desc
	 * @param i
	 * @return
	 */
	public List<Blogcontent> selectBlogcontentRecommendWithoutContentByStatus(Integer status);

	/**
	 * @date Sep 27, 2018 11:14:00 AM
	 * @Desc
	 * @return
	 */
	public int updateBlogcontentRecommendToNo();

	/**
	 * @date Sep 27, 2018 11:14:19 AM
	 * @Desc
	 * @param cid
	 * @return
	 */
	public int updateBlogcontentScrollRecommendedToYes(Long cid);

	/**
	 * @date Sep 27, 2018 11:14:26 AM
	 * @Desc
	 * @param cid
	 * @return
	 */
	public int updateBlogcontentSpecialRecommendedToYes(Long cid);

	/**
	 * @date Sep 27, 2018 11:14:30 AM
	 * @Desc
	 * @param cid
	 * @return
	 */
	public int updateBlogcontentRecommendedToYes(Long cid);

	/**
	 * @date Sep 27, 2018 12:07:25 PM
	 * @Desc
	 * @return
	 */
	public List<Blogcontent> selectBlogcontentScrollRecommendedList(Integer num);

	/**
	 * @date Sep 27, 2018 12:07:33 PM
	 * @Desc
	 * @return
	 */
	public List<Blogcontent> selectBlogcontentSpecialRecommendedList(Integer num);

	/**
	 * @date Sep 27, 2018 12:07:37 PM
	 * @Desc
	 * @return
	 */
	public List<Blogcontent> selectBlogcontentRecommendedList(Integer num);

	/**
	 * @date Sep 27, 2018 12:07:41 PM
	 * @Desc
	 * @return
	 */
	public List<Blogcontent> selectBlogcontentLikeList(Integer num);

	/**
	 * @date Sep 27, 2018 12:07:45 PM
	 * @Desc
	 * @return
	 */
	public List<Blogcontent> selectBlogcontentBrowseList(Integer num);

	/**
	 * @date Sep 28, 2018 1:59:28 PM
	 * @Desc
	 * @param l
	 * @param i
	 * @return
	 */
	public Blogcontent selectBlogcontentByIdAndStatus(Long cid, Integer status);

	/**
	 * @date Oct 8, 2018 11:03:55 AM
	 * @Desc
	 * @param cid
	 * @return
	 */
	public Blogcontent selectUpBlogcontentByCidWithoutContent(Long cid);

	/**
	 * @date Oct 8, 2018 11:04:02 AM
	 * @Desc
	 * @param cid
	 * @return
	 */
	public Blogcontent selectDownBlogcontentByCidWithoutContent(Long cid);

	/**
	 * @date Oct 8, 2018 12:46:53 PM
	 * @Desc
	 * @param blogcontent
	 * @return
	 */
	public List<Blogcontent> selectRelatedBlogcontentByCidWithoutContent(Blogcontent blogcontent);

	/**
	 * @date Oct 11, 2018 2:53:49 PM
	 * @Desc
	 * @param blogcontent
	 * @return
	 */
	public List<Blogcontent> selectBlogSearchcontentListWithoutContent(Blogcontent blogcontent);

	/**
	 * @date Oct 25, 2018 2:12:20 PM
	 * @Desc
	 * @return
	 */
	public Integer selectAllBlogLikeNum();

	/**
	 * @date Oct 25, 2018 2:12:25 PM
	 * @Desc
	 * @return
	 */
	// public Integer selectAllBlogCommentNum();

	/**
	 * @date Oct 25, 2018 2:12:29 PM
	 * @Desc
	 * @return
	 */
	public Integer selectAllBlogArticleNum();

	/**
	 * @date Oct 25, 2018 4:44:21 PM
	 * @Desc
	 * @return
	 */
	public Integer selectDayBlogArticleNum();

	/**
	 * @date Oct 25, 2018 4:44:36 PM
	 * @Desc
	 * @return
	 */
	public Integer selectMonthBlogArticleNum();

	/**
	 * @date Oct 25, 2018 4:44:41 PM
	 * @Desc
	 * @return
	 */
	public Integer selectYearBlogArticleNum();

	/**
	 * @date Oct 25, 2018 5:25:37 PM
	 * @Desc
	 * @return
	 */
	public Integer selectDayBlogArticleNumPre();

	/**
	 * @date Oct 25, 2018 5:25:42 PM
	 * @Desc
	 * @return
	 */
	public Integer selectMonthBlogArticleNumPre();

	/**
	 * @date Oct 25, 2018 5:25:50 PM
	 * @Desc
	 * @return
	 */
	public Integer selectYearBlogArticleNumPre();

	/**
	 * @date Oct 26, 2018 12:09:58 PM
	 * @Desc
	 * @param dayNum
	 * @return
	 */
	public Integer selectBlogArticleNumPreByDayNum(Integer dayNum);

	/**
	 * @date Oct 29, 2018 9:28:31 AM
	 * @Desc
	 * @param cids
	 * @return
	 */
	public int batchReleaseBlogcontent(Long[] cids);

	/**
	 * @date Oct 29, 2018 9:28:45 AM
	 * @Desc
	 * @param cids
	 * @return
	 */
	public int batchDraftBlogcontent(Long[] cids);

	/**
	 * @date Nov 8, 2018 4:07:03 PM
	 * @Desc
	 * @param cid
	 * @return
	 */
	public Blogcontent selectBlogFolderNameAndShowIdByCid(Long cid);

	/**
	 * @date Nov 9, 2018 3:09:21 PM
	 * @Desc
	 * @param blogcontent
	 * @return
	 */
	public List<Blogcontent> selectBlogcontentListArticleTopWithoutContent(Blogcontent blogcontent);

	/**
	 * @date Nov 9, 2018 5:06:59 PM
	 * @Desc
	 * @param cid
	 * @param articleTop
	 * @return
	 */
	public int articleTop(Long cid, Integer articleTop);

	/**
	 * @date Oct 25, 2018 3:18:09 PM
	 * @Desc
	 * @return
	 */
	// public Integer selectDayBlogCommentNum();

	/**
	 * @date Oct 25, 2018 3:18:14 PM
	 * @Desc
	 * @return
	 */
	// public Integer selectMonthBlogCommentNum();

	/**
	 * @date Oct 25, 2018 3:18:22 PM
	 * @Desc
	 * @return
	 */
	// public Integer selectYearBlogCommentNum();
}