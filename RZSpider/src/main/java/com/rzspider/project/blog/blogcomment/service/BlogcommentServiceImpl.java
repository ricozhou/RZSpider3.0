package com.rzspider.project.blog.blogcomment.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rzspider.common.constant.project.BlogConstant;
import com.rzspider.common.utils.StringUtils;
import com.rzspider.common.utils.security.ShiroUtils;
import com.rzspider.project.blog.blogcomment.mapper.BlogcommentMapper;
import com.rzspider.project.blog.blogcomment.domain.Blogcomment;
import com.rzspider.project.blog.blogcomment.service.IBlogcommentService;
import com.rzspider.project.blog.blogkeyword.domain.Blogkeyword;
import com.rzspider.project.blog.blogkeyword.service.IBlogkeywordService;
import com.rzspider.project.blog.blogset.service.IBlogsetService;

/**
 * 博客评论 服务层实现
 * 
 * @author ricozhou
 * @date 2018-11-07
 */
@Service
public class BlogcommentServiceImpl implements IBlogcommentService {
	@Autowired
	private BlogcommentMapper blogcommentMapper;
	@Autowired
	private IBlogsetService blogsetService;
	@Autowired
	private IBlogkeywordService blogkeywordService;

	/**
	 * 查询博客评论信息
	 * 
	 * @param blogCommentId
	 *            博客评论ID
	 * @return 博客评论信息
	 */
	@Override
	public Blogcomment selectBlogcommentById(Integer blogCommentId) {
		return blogcommentMapper.selectBlogcommentById(blogCommentId);
	}

	/**
	 * 查询博客评论列表
	 * 
	 * @param blogcomment
	 *            博客评论信息
	 * @return 博客评论集合
	 */
	@Override
	public List<Blogcomment> selectBlogcommentList(Blogcomment blogcomment) {
		return blogcommentMapper.selectBlogcommentList(blogcomment);
	}

	/**
	 * 查询博客评论列表,以供前台展示
	 * 
	 * @param blogcomment
	 *            博客评论信息
	 * @return 博客评论集合
	 */
	@Override
	public List<Blogcomment> selectBlogcommentListToShow(String showId) {
		List<Blogcomment> list = blogcommentMapper.selectBlogcommentListByParentId(showId, 0);
		if (list != null && list.size() > 0) {
			for (Blogcomment bc : list) {
				bc.setReplyBody(blogcommentMapper.selectBlogcommentListByParentId(showId, bc.getBlogCommentId()));
			}
		}

		return list;
	}

	/**
	 * 新增博客评论
	 * 
	 * @param blogcomment
	 *            博客评论信息
	 * @return 结果
	 */
	@Override
	public int insertBlogcomment(Blogcomment blogcomment) {
		return blogcommentMapper.insertBlogcomment(blogcomment);
	}

	/**
	 * 新增博客评论点赞
	 * 
	 * @param blogcomment
	 *            博客评论信息
	 * @return 结果
	 */
	@Override
	public int addCommentLike(Integer blogCommentId) {
		return blogcommentMapper.addCommentLike(blogCommentId);
	}

	/**
	 * 新增博客评论
	 * 
	 * @param blogcomment
	 *            博客评论信息
	 * @return 结果
	 */
	@Override
	public int[] addBlogcomment(Blogcomment blogcomment) {
		// 逻辑是先判断是否开启自动审核评论，没开启则以正在审核状态插入数据库并返回正在审核状态
		// 开启了自动审核则先审核评论，然后无论结果如何插入数据库，并返回审核状态
		int[] msg = new int[2];
		int row;
		int status;
		int id;
		int basicsetCommentAutoReview = blogsetService.selectBlogsetCommentReviewMsgById(1)
				.getBasicsetCommentAutoReview();
		if (basicsetCommentAutoReview == 0) {
			// 自动审核
			// 先审核
			String replyContent = blogcomment.getReplyContent();
			status = checkAutoCommentContent(blogcomment);
			// 如果不通过则内容还原
			if (status == 2) {
				blogcomment.setReplyContent(replyContent);
				blogcomment.setRemark(BlogConstant.BLOG_BLOGCOMMENT_SAVE_FAILED_REMARK);
			}
			// 插入数据库
			blogcomment.setStatus(status);
			row = blogcommentMapper.insertBlogcomment(blogcomment);
		} else {
			// 手动审核
			// 插入数据库
			blogcomment.setStatus(1);
			row = blogcommentMapper.insertBlogcomment(blogcomment);
			status = 1;
		}
		id = blogcomment.getBlogCommentId() != null ? blogcomment.getBlogCommentId() : -1;
		msg[0] = status;
		msg[1] = id;
		if (row < 1) {
			msg[0] = -1;
			return msg;
		}
		return msg;
	}

	/**
	 * @date Nov 8, 2018 9:29:42 AM
	 * @Desc 自动验证内容是否合法
	 * @param blogcomment
	 * @return
	 */
	private int checkAutoCommentContent(Blogcomment blogcomment) {
		if (blogcomment == null || blogcomment.getReplyContent() == null) {
			return 2;
		}
		// 先把相关的敏感词取出，然后挨个校验
		List<Blogkeyword> keywordList = blogkeywordService.selectBlogCommentkeywordList();

		if (keywordList == null || keywordList.size() < 1) {
			return 0;
		}
		for (Blogkeyword bk : keywordList) {
			if (blogcomment.getReplyContent().contains(bk.getKeywordName())) {
				if (bk.getKeywordReplace() == 0) {
					blogcomment.setReplyContent(blogcomment.getReplyContent().replaceAll(bk.getKeywordName(),
							bk.getKeywordReplaceWordName()));
				} else {
					return 2;
				}
			}
			if (blogcomment.getReplyName().contains(bk.getKeywordName())) {
				if (bk.getKeywordReplace() == 0) {
					blogcomment.setReplyName(
							blogcomment.getReplyName().replaceAll(bk.getKeywordName(), bk.getKeywordReplaceWordName()));
				} else {
					return 2;
				}
			}
		}

		return 0;
	}

	/**
	 * 修改博客评论
	 * 
	 * @param blogcomment
	 *            博客评论信息
	 * @return 结果
	 */
	@Override
	public int updateBlogcomment(Blogcomment blogcomment) {
		return blogcommentMapper.updateBlogcomment(blogcomment);
	}

	/**
	 * 保存博客评论
	 * 
	 * @param blogcomment
	 *            博客评论信息
	 * @return 结果
	 */
	@Override
	public int saveBlogcomment(Blogcomment blogcomment) {
		Integer blogCommentId = blogcomment.getBlogCommentId();
		int rows = 0;
		if (StringUtils.isNotNull(blogCommentId)) {
			blogcomment.setUpdateBy(ShiroUtils.getLoginName());
			rows = blogcommentMapper.updateBlogcomment(blogcomment);
		} else {
			rows = blogcommentMapper.insertBlogcomment(blogcomment);
		}
		return rows;
	}

	/**
	 * 删除博客评论信息
	 * 
	 * @param blogCommentId
	 *            博客评论ID
	 * @return 结果
	 */
	@Override
	public int deleteBlogcommentById(Integer blogCommentId) {
		return blogcommentMapper.deleteBlogcommentById(blogCommentId);
	}

	/**
	 * 批量删除博客评论对象
	 * 
	 * @param blogCommentIds
	 *            需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int batchDeleteBlogcomment(Integer[] blogCommentIds) {
		return blogcommentMapper.batchDeleteBlogcomment(blogCommentIds);
	}

	/**
	 * 删除博客评论信息
	 * 
	 * @param blogCommentId
	 *            博客评论ID
	 * @return 结果
	 */
	@Override
	public int deleteBlogcommentByShowId(String showId) {
		return blogcommentMapper.deleteBlogcommentByShowId(showId);
	}

}
