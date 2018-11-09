package com.rzspider.project.blog.blogtags.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rzspider.common.constant.CommonConstant;
import com.rzspider.common.constant.UserConstants;
import com.rzspider.common.utils.StringUtils;
import com.rzspider.common.utils.security.ShiroUtils;
import com.rzspider.project.blog.blogtags.mapper.BlogtagsMapper;
import com.rzspider.project.blog.blogcolumn.domain.Blogcolumn;
import com.rzspider.project.blog.blogcontent.mapper.BlogcontentTagsMapper;
import com.rzspider.project.blog.blogtags.domain.Blogtags;
import com.rzspider.project.blog.blogtags.service.IBlogtagsService;
import com.rzspider.project.spider.spidermanage.domain.SpiderManage;

/**
 * 博客标签 服务层实现
 * 
 * @author ricozhou
 * @date 2018-09-20
 */
@Service
public class BlogtagsServiceImpl implements IBlogtagsService {
	@Autowired
	private BlogtagsMapper blogtagsMapper;
	@Autowired
	private BlogcontentTagsMapper blogcontentTagsMapper;

	/**
	 * 查询博客标签信息
	 * 
	 * @param blogTagsId
	 *            博客标签ID
	 * @return 博客标签信息
	 */
	@Override
	public Blogtags selectBlogtagsById(Integer blogTagsId) {
		return blogtagsMapper.selectBlogtagsById(blogTagsId);
	}

	/**
	 * 查询博客标签信息
	 * 
	 * @param blogTagsId
	 *            博客标签ID
	 * @return 博客标签信息
	 */
	@Override
	public List<Blogtags> selectBlogtagsByCid(Long cid) {

		List<Blogtags> contentTags = blogtagsMapper.selectBlogtagsByCid(cid);
		List<Blogtags> tags = blogtagsMapper.selectBlogtagsListByStatus(0);
		for (Blogtags tag : tags) {
			for (Blogtags contentTag : contentTags) {
				if (tag.getBlogTagsId() == contentTag.getBlogTagsId()) {
					tag.setFlag(true);
					break;
				}
			}
		}
		return tags;
	}

	/**
	 * 查询博客标签列表
	 * 
	 * @param blogtags
	 *            博客标签信息
	 * @return 博客标签集合
	 */
	@Override
	public List<Blogtags> selectBlogtagsList(Blogtags blogtags) {
		return blogtagsMapper.selectBlogtagsList(blogtags);
	}
	/**
	 * 查询博客标签列表
	 * 
	 * @param blogtags
	 *            博客标签信息
	 * @return 博客标签集合
	 */
	@Override
	public Blogtags selectBlogtagsByStatusAndName(Blogtags blogtags) {
		return blogtagsMapper.selectBlogtagsByStatusAndName(blogtags);
	}

	/**
	 * 查询博客标签列表
	 * 
	 * @param blogtags
	 *            博客标签信息
	 * @return 博客标签集合
	 */
	@Override
	public List<Blogtags> selectBlogtagsListWithoutImg(Blogtags blogtags) {
		return blogtagsMapper.selectBlogtagsListWithoutImg(blogtags);
	}

	/**
	 * 新增博客标签
	 * 
	 * @param blogtags
	 *            博客标签信息
	 * @return 结果
	 */
	@Override
	public int insertBlogtags(Blogtags blogtags) {
		return blogtagsMapper.insertBlogtags(blogtags);
	}

	/**
	 * 新增博客标签
	 * 
	 * @param blogtags
	 *            博客标签信息
	 * @return 结果
	 */
	@Override
	public int insertBlogtagsName(Blogtags blogtags) {
		return blogtagsMapper.insertBlogtagsName(blogtags);
	}

	/**
	 * 修改博客标签
	 * 
	 * @param blogtags
	 *            博客标签信息
	 * @return 结果
	 */
	@Override
	public int updateBlogtags(Blogtags blogtags) {
		return blogtagsMapper.updateBlogtags(blogtags);
	}

	/**
	 * 保存博客标签
	 * 
	 * @param blogtags
	 *            博客标签信息
	 * @return 结果
	 */
	@Override
	public int saveBlogtags(Blogtags blogtags) {
		Integer blogTagsId = blogtags.getBlogTagsId();
		int rows = 0;
		blogtags.setUpdateBy(ShiroUtils.getLoginName());
		if (StringUtils.isNotNull(blogTagsId)) {
			rows = blogtagsMapper.updateBlogtags(blogtags);
		} else {
			blogtags.setCreateBy(ShiroUtils.getLoginName());
			rows = blogtagsMapper.insertBlogtags(blogtags);
		}
		return rows;
	}

	/**
	 * 删除博客标签信息
	 * 
	 * @param blogTagsId
	 *            博客标签ID
	 * @return 结果
	 */
	@Override
	public int deleteBlogtagsById(Integer blogTagsId) {
		// 先删除关系表
		blogcontentTagsMapper.deleteBlogcontentTagsByBlogTagsId(blogTagsId);
		return blogtagsMapper.deleteBlogtagsById(blogTagsId);
	}

	/**
	 * 批量删除博客标签对象
	 * 
	 * @param blogTagsIds
	 *            需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int batchDeleteBlogtags(Integer[] blogTagsIds) {
		// 先删除关系表
		blogcontentTagsMapper.batchDeleteBlogcontentTagsByBlogTagsId(blogTagsIds);
		return blogtagsMapper.batchDeleteBlogtags(blogTagsIds);
	}

	/**
	 * 校验
	 * 
	 * @return 结果
	 */
	@Override
	public String checkTagNameUnique(Blogtags blogtags) {
		if (blogtags.getBlogTagsId() == null) {
			blogtags.setBlogTagsId(-1);
		}
		Integer blogTagsId = blogtags.getBlogTagsId();

		Blogtags blogtags2 = blogtagsMapper.selectBlogtagsByName(blogtags.getBlogTagsName());
		if (StringUtils.isNotNull(blogtags2) && StringUtils.isNotNull(blogtags2.getBlogTagsId())
				&& blogtags2.getBlogTagsId() != blogTagsId) {
			return CommonConstant.NOT_UNIQUE;
		}
		return CommonConstant.UNIQUE;
	}

	/**
	 * 总标签数
	 */
	@Override
	public Integer selectAllBlogTagsNum() {
		return blogtagsMapper.selectAllBlogTagsNum();
	}
}
