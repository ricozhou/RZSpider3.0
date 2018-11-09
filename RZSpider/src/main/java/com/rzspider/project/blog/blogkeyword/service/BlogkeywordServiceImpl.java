package com.rzspider.project.blog.blogkeyword.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rzspider.common.constant.CommonConstant;
import com.rzspider.common.utils.StringUtils;
import com.rzspider.common.utils.security.ShiroUtils;
import com.rzspider.project.blog.blogkeyword.mapper.BlogkeywordMapper;
import com.rzspider.project.blog.blogkeyword.domain.Blogkeyword;
import com.rzspider.project.blog.blogkeyword.service.IBlogkeywordService;
import com.rzspider.project.blog.blogtags.domain.Blogtags;

/**
 * 关键词管理 服务层实现
 * 
 * @author ricozhou
 * @date 2018-11-08
 */
@Service
public class BlogkeywordServiceImpl implements IBlogkeywordService {
	@Autowired
	private BlogkeywordMapper blogkeywordMapper;

	/**
	 * 查询关键词管理信息
	 * 
	 * @param blogKeywordId
	 *            关键词管理ID
	 * @return 关键词管理信息
	 */
	@Override
	public Blogkeyword selectBlogkeywordById(Integer blogKeywordId) {
		return blogkeywordMapper.selectBlogkeywordById(blogKeywordId);
	}

	/**
	 * 查询关键词管理列表
	 * 
	 * @param blogkeyword
	 *            关键词管理信息
	 * @return 关键词管理集合
	 */
	@Override
	public List<Blogkeyword> selectBlogkeywordList(Blogkeyword blogkeyword) {
		return blogkeywordMapper.selectBlogkeywordList(blogkeyword);
	}

	/**
	 * 新增关键词管理
	 * 
	 * @param blogkeyword
	 *            关键词管理信息
	 * @return 结果
	 */
	@Override
	public int insertBlogkeyword(Blogkeyword blogkeyword) {
		return blogkeywordMapper.insertBlogkeyword(blogkeyword);
	}

	/**
	 * 修改关键词管理
	 * 
	 * @param blogkeyword
	 *            关键词管理信息
	 * @return 结果
	 */
	@Override
	public int updateBlogkeyword(Blogkeyword blogkeyword) {
		return blogkeywordMapper.updateBlogkeyword(blogkeyword);
	}

	/**
	 * 保存关键词管理
	 * 
	 * @param blogkeyword
	 *            关键词管理信息
	 * @return 结果
	 */
	@Override
	public int saveBlogkeyword(Blogkeyword blogkeyword) {
		Integer blogKeywordId = blogkeyword.getBlogKeywordId();
		int rows = 0;
		blogkeyword.setUpdateBy(ShiroUtils.getLoginName());
		if (StringUtils.isNotNull(blogKeywordId)) {
			rows = blogkeywordMapper.updateBlogkeyword(blogkeyword);
		} else {
			blogkeyword.setCreateBy(ShiroUtils.getLoginName());
			rows = blogkeywordMapper.insertBlogkeyword(blogkeyword);
		}
		return rows;
	}

	/**
	 * 删除关键词管理信息
	 * 
	 * @param blogKeywordId
	 *            关键词管理ID
	 * @return 结果
	 */
	@Override
	public int deleteBlogkeywordById(Integer blogKeywordId) {
		return blogkeywordMapper.deleteBlogkeywordById(blogKeywordId);
	}

	/**
	 * 批量删除关键词管理对象
	 * 
	 * @param blogKeywordIds
	 *            需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int batchDeleteBlogkeyword(Integer[] blogKeywordIds) {
		return blogkeywordMapper.batchDeleteBlogkeyword(blogKeywordIds);
	}

	/**
	 * 校验
	 * 
	 * @return 结果
	 */
	@Override
	public String checkKeywordNameUnique(Blogkeyword blogkeyword) {
		if (blogkeyword.getBlogKeywordId() == null) {
			blogkeyword.setBlogKeywordId(-1);
		}
		Integer blogKeywordId = blogkeyword.getBlogKeywordId();

		Blogkeyword blogkeyword2 = blogkeywordMapper.selectBlogkeywordByName(blogkeyword.getKeywordName());
		if (StringUtils.isNotNull(blogkeyword2) && StringUtils.isNotNull(blogkeyword2.getBlogKeywordId())
				&& blogkeyword2.getBlogKeywordId() != blogKeywordId) {
			return CommonConstant.NOT_UNIQUE;
		}
		return CommonConstant.UNIQUE;
	}

	/**
	 * @date Nov 8, 2018 3:10:03 PM
	 * @Desc
	 * @return
	 */
	public List<Blogkeyword> selectBlogCommentkeywordList() {
		return blogkeywordMapper.selectBlogCommentkeywordList();
	};

	/**
	 * @date Nov 8, 2018 3:10:03 PM
	 * @Desc
	 * @return
	 */
	public List<Blogkeyword> selectBlogMessagekeywordList() {
		return blogkeywordMapper.selectBlogMessagekeywordList();
	};

	/**
	 * @date Nov 8, 2018 3:10:03 PM
	 * @Desc
	 * @return
	 */
	public List<Blogkeyword> selectBlogSuggestionkeywordList() {
		return blogkeywordMapper.selectBlogSuggestionkeywordList();
	};
}
