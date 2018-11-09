package com.rzspider.project.blog.blogwebsite.service;

import com.rzspider.project.blog.blogcontent.domain.Blogcontent;
import com.rzspider.project.blog.blogcontent.domain.BlogcontentRecommend;

import java.util.List;

/**
 * 文章内容 服务层
 * 
 * @author ricozhou
 * @date 2018-06-12
 */
public interface IBlogwebsiteService {

	/**
	 * 查询文章内容信息
	 * 
	 * @param showId
	 *            文章内容ID
	 * @return 文章内容信息
	 */
	public Blogcontent selectBlogcontentByShowId(String showId);

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
	 * @date Sep 18, 2018 9:17:54 AM
	 * @Desc
	 * @param blogcontent
	 * @return
	 */
	public List<Blogcontent> selectBlogColumncontentListWithoutContent(Blogcontent blogcontent);

	/**
	 * @date Sep 25, 2018 1:30:12 PM
	 * @Desc
	 * @param blogcontent
	 * @return
	 */
	List<Blogcontent> selectBlogTagscontentListWithoutContent(Blogcontent blogcontent);

	/**
	 * @date Sep 25, 2018 4:54:06 PM
	 * @Desc
	 * @param cid
	 * @param flag
	 * @return
	 */
	public int addBrowseAndKikeNum(Long cid, Integer flag);

	/**
	 * @date Sep 27, 2018 11:35:40 AM
	 * @Desc
	 * @return
	 */
	public BlogcontentRecommend selectBlogcontentRecommendListWithoutContent();

	/**
	 * @date Oct 8, 2018 11:02:44 AM
	 * @Desc
	 * @param cid
	 * @return
	 */
	public Blogcontent selectUpBlogcontentByCidWithoutContent(Long cid);

	/**
	 * @date Oct 8, 2018 11:02:57 AM
	 * @Desc
	 * @param cid
	 * @return
	 */
	public Blogcontent selectDownBlogcontentByCidWithoutContent(Long cid);

	/**
	 * @date Oct 8, 2018 12:44:35 PM
	 * @Desc
	 * @param blogcontent
	 * @return
	 */
	public List<Blogcontent> selectRelatedBlogcontentByCidWithoutContent(Blogcontent blogcontent);

	/**
	 * @date Oct 11, 2018 2:52:38 PM
	 * @Desc
	 * @param blogcontent
	 * @return
	 */
	public List<Blogcontent> selectBlogSearchcontentListWithoutContent(Blogcontent blogcontent);

	/**
	 * @date Oct 15, 2018 8:53:03 AM
	 * @Desc
	 * @return
	 */
	BlogcontentRecommend selectBlogcontentRecommendList2WithoutContent();

}
