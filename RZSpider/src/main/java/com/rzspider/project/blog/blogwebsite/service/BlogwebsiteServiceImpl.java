package com.rzspider.project.blog.blogwebsite.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rzspider.common.utils.StringUtils;
import com.rzspider.project.blog.blogcontent.mapper.BlogcontentMapper;
import com.rzspider.project.blog.blogcontent.domain.Blogcontent;
import com.rzspider.project.blog.blogcontent.domain.BlogcontentRecommend;
import com.rzspider.project.blog.blogcontent.service.IBlogcontentService;
import com.rzspider.project.blog.blogset.domain.Blogset;
import com.rzspider.project.blog.blogset.service.IBlogsetService;

/**
 * 文章内容 服务层实现
 * 
 * @author ricozhou
 * @date 2018-06-12
 */
@Service
public class BlogwebsiteServiceImpl implements IBlogwebsiteService {
	@Autowired
	private BlogcontentMapper blogcontentMapper;
	@Autowired
	private IBlogsetService blogsetService;

	/**
	 * 查询文章内容信息
	 * 
	 * @param cid
	 *            文章内容ID
	 * @return 文章内容信息
	 */
	@Override
	public Blogcontent selectBlogcontentByShowId(String showId) {
		return blogcontentMapper.selectBlogcontentByShowId(showId);
	}

	/**
	 * 查询文章内容信息
	 * 
	 * @param cid
	 *            文章内容ID
	 * @return 文章内容信息
	 */
	@Override
	public Blogcontent selectUpBlogcontentByCidWithoutContent(Long cid) {
		return blogcontentMapper.selectUpBlogcontentByCidWithoutContent(cid);
	}

	/**
	 * 查询文章内容信息
	 * 
	 * @param cid
	 *            文章内容ID
	 * @return 文章内容信息
	 */
	@Override
	public Blogcontent selectDownBlogcontentByCidWithoutContent(Long cid) {
		return blogcontentMapper.selectDownBlogcontentByCidWithoutContent(cid);
	}

	/**
	 * 查询文章内容信息
	 * 
	 * @param cid
	 *            文章内容ID
	 * @return 文章内容信息
	 */
	@Override
	public List<Blogcontent> selectRelatedBlogcontentByCidWithoutContent(Blogcontent blogcontent) {
		return blogcontentMapper.selectRelatedBlogcontentByCidWithoutContent(blogcontent);
	}

	/**
	 * 查询文章内容列表
	 * 
	 * @param blogcontent
	 *            文章内容信息
	 * @return 文章内容集合
	 */
	@Override
	public List<Blogcontent> selectBlogcontentList(Blogcontent blogcontent) {
		return blogcontentMapper.selectBlogcontentList(blogcontent);
	}

	/**
	 * 查询文章内容列表
	 * 
	 * @param blogcontent
	 *            文章内容信息
	 * @return 文章内容集合
	 */
	@Override
	public List<Blogcontent> selectBlogcontentListWithoutContent(Blogcontent blogcontent) {
		// 此查询语句有些复杂，首先根据一定的条件和排序条件查询一个结果集，再根据另一条件不同的排序查询结果集，然后将两个结果集有序合并
		// 如：(SELECT * FROM `union_a` ORDER BY `NUMBER` LIMIT 5) UNION ALL
		// (SELECT * FROM `union_b` ORDER BY `NUMBER` LIMIT 5)
		// 注意子语句的limit必须加，不加排序不生效
		List<Blogcontent> listMain = blogcontentMapper.selectBlogcontentListWithoutContent(blogcontent);
		return listMain;
	}

	/**
	 * 查询专栏文章内容列表
	 * 
	 * @param blogcontent
	 *            文章内容信息
	 * @return 文章内容集合
	 */
	@Override
	public List<Blogcontent> selectBlogColumncontentListWithoutContent(Blogcontent blogcontent) {
		if (blogcontent.getFlag() == 0) {
			return blogcontentMapper.selectBlogMColumncontentListWithoutContent(blogcontent);
		} else if (blogcontent.getFlag() == 1) {
			return blogcontentMapper.selectBlogCColumncontentListWithoutContent(blogcontent);
		}
		return null;
	}

	/**
	 * 查询专栏文章内容列表
	 * 
	 * @param blogcontent
	 *            文章内容信息
	 * @return 文章内容集合
	 */
	@Override
	public List<Blogcontent> selectBlogTagscontentListWithoutContent(Blogcontent blogcontent) {
		return blogcontentMapper.selectBlogTagscontentListWithoutContent(blogcontent);
	}

	/**
	 * 查询专栏文章内容列表
	 * 
	 * @param blogcontent
	 *            文章内容信息
	 * @return 文章内容集合
	 */
	@Override
	public List<Blogcontent> selectBlogSearchcontentListWithoutContent(Blogcontent blogcontent) {
		return blogcontentMapper.selectBlogSearchcontentListWithoutContent(blogcontent);
	}

	/**
	 * 新增文章内容
	 * 
	 * @param blogcontent
	 *            文章内容信息
	 * @return 结果
	 */
	@Override
	public int insertBlogcontent(Blogcontent blogcontent) {
		return blogcontentMapper.insertBlogcontent(blogcontent);
	}

	/**
	 * 修改文章内容
	 * 
	 * @param blogcontent
	 *            文章内容信息
	 * @return 结果
	 */
	@Override
	public int updateBlogcontent(Blogcontent blogcontent) {
		return blogcontentMapper.updateBlogcontent(blogcontent);
	}

	/**
	 * 保存文章内容
	 * 
	 * @param blogcontent
	 *            文章内容信息
	 * @return 结果
	 */
	@Override
	public int saveBlogcontent(Blogcontent blogcontent) {
		Long cid = blogcontent.getCid();
		int rows = 0;
		if (StringUtils.isNotNull(cid)) {
			rows = blogcontentMapper.updateBlogcontent(blogcontent);
		} else {
			rows = blogcontentMapper.insertBlogcontent(blogcontent);
		}
		return rows;
	}

	/**
	 * 删除文章内容信息
	 * 
	 * @param cid
	 *            文章内容ID
	 * @return 结果
	 */
	@Override
	public int deleteBlogcontentById(Long cid) {
		return blogcontentMapper.deleteBlogcontentById(cid);
	}

	/**
	 * 批量删除文章内容对象
	 * 
	 * @param cids
	 *            需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int batchDeleteBlogcontent(Long[] cids) {
		return blogcontentMapper.batchDeleteBlogcontent(cids);
	}

	/**
	 * 浏览点赞加一
	 */
	@Override
	public int addBrowseAndKikeNum(Long cid, Integer flag) {
		int rows = 0;
		if (flag == 0) {
			rows = blogcontentMapper.updateBrowseNum(cid);
		} else if (flag == 1) {
			rows = blogcontentMapper.updateLikeNum(cid);
		}
		return rows;
	}

	/**
	 * 查询文章推荐内容列表
	 * 
	 * @param blogcontent
	 *            文章内容信息
	 * @return 文章内容集合
	 */
	@Override
	public BlogcontentRecommend selectBlogcontentRecommendListWithoutContent() {
		Blogset blogset = blogsetService.selectSomeBlogsetById(1);

		BlogcontentRecommend blogcontentRecommend = new BlogcontentRecommend();
		// // 滚动推荐
		// blogcontentRecommend.setScrollRecommendedBlogcontentList(
		// blogcontentMapper.selectBlogcontentScrollRecommendedList(blogset.getBlogsetScrollRecommendedShowNum()));
		// 特别推荐
		blogcontentRecommend.setSpecialRecommendedBlogcontentList(
				blogcontentMapper.selectBlogcontentSpecialRecommendedList(blogset.getBlogsetSpecialRecdShowNum()));
		// 列表推荐
		blogcontentRecommend.setRecommendedBlogcontentList(
				blogcontentMapper.selectBlogcontentRecommendedList(blogset.getBlogsetRecommendedShowNum()));
		// 点赞排行
		blogcontentRecommend
				.setLikeBlogcontentList(blogcontentMapper.selectBlogcontentLikeList(blogset.getBlogsetLatestShowNum()));
		// 点击排行
		blogcontentRecommend.setBrowseBlogcontentList(
				blogcontentMapper.selectBlogcontentBrowseList(blogset.getBlogsetRankingShowNum()));

		// 关于我
		// blogcontentRecommend.setAboutmeBlogcontent(blogcontentMapper.selectBlogcontentByIdAndStatus(1L,
		// 1));
		// // 关于本站
		// blogcontentRecommend.setAboutwebsiteBlogcontent(blogcontentMapper.selectBlogcontentByIdAndStatus(2L,
		// 1));

		return blogcontentRecommend;
	}

	/**
	 * 查询文章推荐内容列表
	 * 
	 * @param blogcontent
	 *            文章内容信息
	 * @return 文章内容集合
	 */
	@Override
	public BlogcontentRecommend selectBlogcontentRecommendList2WithoutContent() {
		Blogset blogset = blogsetService.selectSomeBlogsetById(1);

		BlogcontentRecommend blogcontentRecommend = new BlogcontentRecommend();
		// 滚动推荐
		blogcontentRecommend.setScrollRecommendedBlogcontentList(
				blogcontentMapper.selectBlogcontentScrollRecommendedList(blogset.getBlogsetScrollRecommendedShowNum()));

		// 关于我
		blogcontentRecommend.setAboutmeBlogcontent(blogcontentMapper.selectBlogcontentByIdAndStatus(1L, 1));
		// 关于本站
		blogcontentRecommend.setAboutwebsiteBlogcontent(blogcontentMapper.selectBlogcontentByIdAndStatus(2L, 1));

		return blogcontentRecommend;
	}

}
