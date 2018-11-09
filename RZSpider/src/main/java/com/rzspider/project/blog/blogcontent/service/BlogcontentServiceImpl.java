package com.rzspider.project.blog.blogcontent.service;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rzspider.common.constant.CommonSymbolicConstant;
import com.rzspider.common.utils.DateUtils;
import com.rzspider.common.utils.StringUtils;
import com.rzspider.common.utils.security.ShiroUtils;
import com.rzspider.framework.config.FilePathConfig;
import com.rzspider.implementspider.blogmove.controller.BlogMoveThread;
import com.rzspider.project.blog.blogcontent.mapper.BlogcontentMapper;
import com.rzspider.project.blog.blogcontent.mapper.BlogcontentTagsMapper;
import com.rzspider.project.blog.blogcomment.service.IBlogcommentService;
import com.rzspider.project.blog.blogcontent.domain.Blogcontent;
import com.rzspider.project.blog.blogcontent.domain.BlogcontentRecommend;
import com.rzspider.project.blog.blogcontent.domain.BlogcontentTags;
import com.rzspider.project.blog.blogcontent.service.IBlogcontentService;
import com.rzspider.project.blog.blogtags.domain.Blogtags;
import com.rzspider.project.blog.blogtags.service.IBlogtagsService;
import com.rzspider.project.common.file.utilt.FileUtils;
import com.rzspider.project.system.user.domain.UserPost;

/**
 * 文章内容 服务层实现
 * 
 * @author ricozhou
 * @date 2018-06-12
 */
@Service
public class BlogcontentServiceImpl implements IBlogcontentService {
	@Autowired
	private BlogcontentMapper blogcontentMapper;

	@Autowired
	private BlogcontentTagsMapper blogcontentTagsMapper;
	@Autowired
	private IBlogtagsService blogtagsService;
	@Autowired
	private IBlogcommentService blogcommentService;

	/**
	 * 查询文章内容信息
	 * 
	 * @param cid
	 *            文章内容ID
	 * @return 文章内容信息
	 */
	@Override
	public Blogcontent selectBlogcontentById(Long cid) {
		return blogcontentMapper.selectBlogcontentById(cid);
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
		return blogcontentMapper.selectBlogcontentListWithoutContent(blogcontent);
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
		Blogtags blogtags = new Blogtags();
		// 首先插入新增的标签
		if (blogcontent.getBlogtags() != null) {
			for (String tag : blogcontent.getBlogtags()) {
				blogtags.setBlogTagsName(tag);
				// 插入标签表并返回id添加到list中
				blogtagsService.insertBlogtagsName(blogtags);
				blogcontent.getTagsIds().add(blogtags.getBlogTagsId());
			}
		}
		blogcontent.setGtmModified(new Date());
		if (StringUtils.isNotNull(cid)) {
			rows = blogcontentMapper.updateBlogcontent(blogcontent);
			// 删除标签与文章的关系
			blogcontentTagsMapper.deleteBlogcontentTagsById(blogcontent.getCid());
			// 重新插入标签与文章的关系
			insertBlogcontentTags(blogcontent);
		} else {
			// 生成文章唯一显示id
			// 先根据时间戳显示
			blogcontent.setShowId(DateUtils.format(new Date(), DateUtils.YYYYMMDDHHMMSSSSS));

			// 更改sql，时间不再自动，方便博客搬家
			blogcontent.setGtmCreate(new Date());
			rows = blogcontentMapper.insertBlogcontent(blogcontent);

			// 插入标签与文章的关系
			insertBlogcontentTags(blogcontent);
		}
		return rows;
	}

	/**
	 * @date Sep 21, 2018 12:08:09 PM
	 * @Desc 插入文章与标签关系表
	 * @param blogcontent
	 */
	private void insertBlogcontentTags(Blogcontent blogcontent) {
		// 新增文章与标签关系
		List<BlogcontentTags> list = new ArrayList<BlogcontentTags>();
		BlogcontentTags blogcontentTags;
		for (Integer blogTagsId : blogcontent.getTagsIds()) {
			blogcontentTags = new BlogcontentTags();
			blogcontentTags.setBlogTagsId(blogTagsId);
			blogcontentTags.setCid(blogcontent.getCid());
			list.add(blogcontentTags);
		}
		if (list.size() > 0) {
			blogcontentTagsMapper.batchBlogcontentTags(list);
		}
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
		// 删除文件夹
		Blogcontent blogcontent = blogcontentMapper.selectBlogFolderNameAndShowIdByCid(cid);
		deleteBlogcontentFolderByFolderName(blogcontent.getBlogFileName());
		// 删除标签与文章的关系
		blogcontentTagsMapper.deleteBlogcontentTagsById(cid);
		// 删除相关评论
		blogcommentService.deleteBlogcommentByShowId(blogcontent.getShowId());
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
		// 删除文件夹
		Blogcontent blogcontent;
		for (Long cid : cids) {
			blogcontent = blogcontentMapper.selectBlogFolderNameAndShowIdByCid(cid);
			deleteBlogcontentFolderByFolderName(blogcontent.getBlogFileName());
			// 删除相关评论
			blogcommentService.deleteBlogcommentByShowId(blogcontent.getShowId());
		}
		blogcontentTagsMapper.deleteBlogcontentTags(cids);
		return blogcontentMapper.batchDeleteBlogcontent(cids);
	}

	/**
	 * 删除文章目录
	 * 
	 * @param folderName
	 *            文章目录
	 * @return 结果
	 */
	@Override
	public boolean deleteBlogcontentFolderByFolderName(String folderName) {
		// 删除文件夹
		// 删除文件
		if (folderName == null || CommonSymbolicConstant.EMPTY_STRING.equals(folderName)) {
			// 不能为空否则会将上级目录都删除
			return true;
		}
		File file = new File(FilePathConfig.getUploadBlogPath() + File.separator + folderName);
		if (!file.exists()) {
			return true;
		}
		return FileUtils.deleteFile(file.getAbsolutePath());
	}

	/**
	 * 查询推荐文章
	 * 
	 * @return
	 */
	@Override
	public List<Blogcontent> selectBlogcontentRecommendWithoutContent() {
		return blogcontentMapper.selectBlogcontentRecommendWithoutContentByStatus(1);
	}

	/**
	 * 保存文章推荐设置
	 * 
	 * @param blogcontentRecommend
	 *            文章内容信息
	 * @return 结果
	 */
	@Override
	public int recommendSetSave(BlogcontentRecommend blogcontentRecommend) {
		// 先将所有的推荐设置都清零，再重新设置
		// 以后更新此方法，耗时

		// 清除所有推荐设置
		int rows = blogcontentMapper.updateBlogcontentRecommendToNo();
		// 设置滚动推荐
		if (blogcontentRecommend.getScrollRecommendedIds() != null) {
			for (Long cid : blogcontentRecommend.getScrollRecommendedIds()) {
				rows = blogcontentMapper.updateBlogcontentScrollRecommendedToYes(cid);
			}
		}

		// 设置特别推荐
		if (blogcontentRecommend.getSpecialRecommendedIds() != null) {
			for (Long cid : blogcontentRecommend.getSpecialRecommendedIds()) {
				rows = blogcontentMapper.updateBlogcontentSpecialRecommendedToYes(cid);
			}
		}
		// 设置列表推荐
		if (blogcontentRecommend.getRecommendedIds() != null) {
			for (Long cid : blogcontentRecommend.getRecommendedIds()) {
				rows = blogcontentMapper.updateBlogcontentRecommendedToYes(cid);
			}
		}
		return rows;
	}

	/**
	 * 查询总赞数
	 * 
	 */
	@Override
	public Integer selectAllBlogLikeNum() {
		return blogcontentMapper.selectAllBlogLikeNum();
	}

	/**
	 * 查询总文章数
	 * 
	 */
	@Override
	public Integer selectAllBlogArticleNum() {
		return blogcontentMapper.selectAllBlogArticleNum();
	}

	/**
	 * 查询日文章数
	 * 
	 */
	@Override
	public Integer selectDayBlogArticleNum() {
		return blogcontentMapper.selectDayBlogArticleNum();
	}

	/**
	 * 查询月文章数
	 * 
	 */
	@Override
	public Integer selectMonthBlogArticleNum() {
		return blogcontentMapper.selectMonthBlogArticleNum();
	}

	/**
	 * 查询年文章数
	 * 
	 */
	@Override
	public Integer selectYearBlogArticleNum() {
		return blogcontentMapper.selectYearBlogArticleNum();
	}

	/**
	 * 查询上一天文章数
	 * 
	 */
	@Override
	public Integer selectDayBlogArticleNumPre() {
		return blogcontentMapper.selectDayBlogArticleNumPre();
	}

	/**
	 * 查询上一月文章数
	 * 
	 */
	@Override
	public Integer selectMonthBlogArticleNumPre() {
		return blogcontentMapper.selectMonthBlogArticleNumPre();
	}

	/**
	 * 查询上一年文章数
	 * 
	 */
	@Override
	public Integer selectYearBlogArticleNumPre() {
		return blogcontentMapper.selectYearBlogArticleNumPre();
	}

	/**
	 * 根据天数查询文章数
	 * 
	 */
	@Override
	public Integer selectBlogArticleNumPreByDayNum(Integer dayNum) {
		return blogcontentMapper.selectBlogArticleNumPreByDayNum(dayNum);
	}

	/**
	 * 批量发布文章内容对象
	 * 
	 * @param cids
	 *            需要发布的数据ID
	 * @return 结果
	 */
	@Override
	public int batchReleaseBlogcontent(Integer status, Long[] cids) {
		if (status == 1) {
			// 发布
			return blogcontentMapper.batchReleaseBlogcontent(cids);
		} else {
			// 草稿
			return blogcontentMapper.batchDraftBlogcontent(cids);
		}

	}

	/**
	 * 文章置顶
	 * 
	 * @param cid
	 *            文章内容ID
	 * @return 结果
	 */
	@Override
	public int articleTop(Long cid, Integer articleTop) {
		return blogcontentMapper.articleTop(cid, articleTop);
	}
}
