package com.rzspider.project.blog.blogcontent.service;

import com.rzspider.project.blog.blogcontent.domain.Blogcontent;
import com.rzspider.project.blog.blogcontent.domain.BlogcontentRecommend;

import java.io.PrintWriter;
import java.util.List;

/**
 * 文章内容 服务层
 * 
 * @author ricozhou
 * @date 2018-06-12
 */
public interface IBlogcontentService {

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
	 * 保存文章内容
	 * 
	 * @param blogcontent
	 *            文章内容信息
	 * @return 结果
	 */
	public int saveBlogcontent(Blogcontent blogcontent);

	/**
	 * 删除文章内容信息
	 * 
	 * @param cid
	 *            文章内容ID
	 * @return 结果
	 */
	public int deleteBlogcontentById(Long cid);

	/**
	 * 批量删除文章内容信息
	 * 
	 * @param cids
	 *            需要删除的数据ID
	 * @return 结果
	 */
	public int batchDeleteBlogcontent(Long[] cids);

	/**
	 * @date Sep 27, 2018 9:53:59 AM
	 * @Desc
	 * @param i
	 * @return
	 */
	public List<Blogcontent> selectBlogcontentRecommendWithoutContent();

	/**
	 * @date Sep 27, 2018 11:03:07 AM
	 * @Desc
	 * @param blogcontentRecommend
	 * @return
	 */
	public int recommendSetSave(BlogcontentRecommend blogcontentRecommend);

	/**
	 * @date Oct 17, 2018 1:57:30 PM
	 * @Desc
	 * @param blogcontent
	 * @return
	 */
	List<Blogcontent> selectBlogcontentListWithoutContent(Blogcontent blogcontent);

	/**
	 * @date Oct 18, 2018 2:48:09 PM
	 * @Desc
	 * @param folderName
	 * @return
	 */
	boolean deleteBlogcontentFolderByFolderName(String folderName);

	/**
	 * @date Oct 25, 2018 2:05:41 PM
	 * @Desc
	 * @return
	 */
	public Integer selectAllBlogLikeNum();

	/**
	 * @date Oct 25, 2018 2:06:01 PM
	 * @Desc
	 * @return
	 */
	public Integer selectAllBlogArticleNum();

	/**
	 * @date Oct 25, 2018 4:41:01 PM
	 * @Desc
	 * @return
	 */
	public Integer selectDayBlogArticleNum();

	/**
	 * @date Oct 25, 2018 4:41:10 PM
	 * @Desc
	 * @return
	 */
	public Integer selectMonthBlogArticleNum();

	/**
	 * @date Oct 25, 2018 4:41:17 PM
	 * @Desc
	 * @return
	 */
	public Integer selectYearBlogArticleNum();

	/**
	 * @date Oct 25, 2018 5:06:22 PM
	 * @Desc
	 * @return
	 */
	public Integer selectDayBlogArticleNumPre();

	/**
	 * @date Oct 25, 2018 5:07:04 PM
	 * @Desc
	 * @return
	 */
	public Integer selectMonthBlogArticleNumPre();

	/**
	 * @date Oct 25, 2018 5:09:04 PM
	 * @Desc
	 * @return
	 */
	public Integer selectYearBlogArticleNumPre();

	/**
	 * @date Oct 26, 2018 12:08:25 PM
	 * @Desc
	 * @param i
	 * @return
	 */
	public Integer selectBlogArticleNumPreByDayNum(Integer dayNum);

	/**
	 * @date Oct 29, 2018 9:26:06 AM
	 * @Desc
	 * @param status
	 * @param cids
	 * @return
	 */
	public int batchReleaseBlogcontent(Integer status, Long[] cids);

	/**
	 * @date Nov 9, 2018 5:05:14 PM
	 * @Desc
	 * @param cid
	 * @param articleTop
	 * @return
	 */
	public int articleTop(Long cid, Integer articleTop);

}
