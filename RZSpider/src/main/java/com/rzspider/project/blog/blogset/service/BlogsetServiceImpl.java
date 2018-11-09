package com.rzspider.project.blog.blogset.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rzspider.common.utils.StringUtils;
import com.rzspider.common.utils.security.ShiroUtils;
import com.rzspider.project.blog.blogset.mapper.BlogsetMapper;
import com.rzspider.project.blog.blogcontent.domain.Blogcontent;
import com.rzspider.project.blog.blogset.domain.Blogset;
import com.rzspider.project.blog.blogset.service.IBlogsetService;
import com.rzspider.project.system.website.service.IWebsiteService;

/**
 * 博客设置详情 服务层实现
 * 
 * @author ricozhou
 * @date 2018-09-13
 */
@Service
public class BlogsetServiceImpl implements IBlogsetService {
	@Autowired
	private BlogsetMapper blogsetMapper;

	@Autowired
	private IWebsiteService websiteService;
	@Autowired
	private IBlogsiderbarService blogsiderbarService;

	/**
	 * 查询博客设置详情信息
	 * 
	 * @param blogSetId
	 *            博客设置详情ID
	 * @return 博客设置详情信息
	 */
	@Override
	public Blogset selectBlogsetById(Integer blogSetId) {
		return blogsetMapper.selectBlogsetById(blogSetId);
	}

	/**
	 * 查询博客设置详情信息
	 * 
	 * @param blogSetId
	 *            博客设置详情ID
	 * @return 博客设置详情信息
	 */
	@Override
	public Blogset selectBlogsetWaterMarkMsgById(Integer blogSetId) {
		return blogsetMapper.selectBlogsetWaterMarkMsgById(blogSetId);
	}

	/**
	 * 查询博客设置详情信息
	 * 
	 * @param blogSetId
	 *            博客设置详情ID
	 * @return 博客设置详情信息
	 */
	@Override
	public Blogset selectBlogsetBlogMoveMsgById(Integer blogSetId) {
		return blogsetMapper.selectBlogsetBlogMoveMsgById(blogSetId);
	}

	/**
	 * 查询博客设置详情信息
	 * 
	 * @param blogSetId
	 *            博客设置详情ID
	 * @return 博客设置详情信息
	 */
	@Override
	public Blogset selectSomeOverViewBlogsetById(Integer blogSetId) {
		return blogsetMapper.selectSomeOverViewBlogsetById(blogSetId);
	}

	/**
	 * 查询博客设置详情信息
	 * 
	 * @param blogSetId
	 *            博客设置详情ID
	 * @return 博客设置详情信息
	 */
	@Override
	public Blogset getBlogsetMsg() {
		List<Blogset> list = selectBlogsetList(null);
		if (list != null && list.size() > 0) {
			// 获取主网站部分设置
			Blogset blogset = list.get(0);
			blogset.setWebsite(websiteService.getSomeWebsiteSetMsg());
			// 获取需要展示的侧边栏信息
			blogset.setBlogsiderbarList(blogsiderbarService.selectBlogsiderbarList(null));
			return blogset;
		}
		return null;
	}

	/**
	 * 查询博客设置详情列表
	 * 
	 * @param blogset
	 *            博客设置详情信息
	 * @return 博客设置详情集合
	 */
	@Override
	public List<Blogset> selectBlogsetList(Blogset blogset) {
		return blogsetMapper.selectBlogsetList(blogset);
	}

	/**
	 * 保存博客设置详情
	 * 
	 * @param blogset
	 *            博客设置详情信息
	 * @return 结果
	 */
	@Override
	public int saveBlogset(Blogset blogset) {
		Integer blogSetId = blogset.getBlogSetId();
		blogset.setUpdateBy(ShiroUtils.getLoginName());
		int rows = 0;
		if (StringUtils.isNotNull(blogSetId)) {
			rows = blogsetMapper.updateBlogset(blogset);
			// 将侧边栏其他信息保存到另一张表
			blogsiderbarService.insertCustomBlogsiderbar(blogset.getBlogsetSidebarOtherMessage());
		}
		return rows;
	}

	/**
	 * 保存博客基础设置详情
	 * 
	 * @param blogset
	 *            博客设置详情信息
	 * @return 结果
	 */
	@Override
	public int saveBasicset(Blogset blogset) {
		Integer blogSetId = blogset.getBlogSetId();
		blogset.setUpdateBy(ShiroUtils.getLoginName());
		int rows = 0;
		if (StringUtils.isNotNull(blogSetId)) {
			rows = blogsetMapper.updateBasicset(blogset);
		}
		return rows;
	}

	/**
	 * 保存博主设置详情
	 * 
	 * @param blogset
	 *            博客设置详情信息
	 * @return 结果
	 */
	@Override
	public int saveBloggerset(Blogset blogset) {
		Integer blogSetId = blogset.getBlogSetId();
		blogset.setUpdateBy(ShiroUtils.getLoginName());
		int rows = 0;
		if (StringUtils.isNotNull(blogSetId)) {
			rows = blogsetMapper.updateBloggerset(blogset);
		}
		return rows;
	}

	/**
	 * 保存博客设置详情
	 * 
	 * @param blogset
	 *            博客设置详情信息
	 * @return 结果
	 */
	@Override
	public int saveStyleset(Blogset blogset) {
		Integer blogSetId = blogset.getBlogSetId();
		blogset.setUpdateBy(ShiroUtils.getLoginName());
		int rows = 0;
		if (StringUtils.isNotNull(blogSetId)) {
			rows = blogsetMapper.updateStyleset(blogset);
			// 将侧边栏其他信息更新到另一张表
			blogsiderbarService.updateBlogsiderbar(blogset);
		}
		return rows;
	}

	/**
	 * 查询部分博客设置
	 * 
	 * @param blogset
	 *            博客设置详情信息
	 * @return 结果返回博主姓名
	 */
	@Override
	public Blogset selectSomeBloggersetById(Integer blogSetId) {
		return blogsetMapper.selectSomeBloggersetById(blogSetId);
	}

	/**
	 * 查询部分博客设置
	 * 
	 * @param blogset
	 *            博客设置详情信息
	 * @return 结果
	 */
	@Override
	public Blogset selectSomeBlogsetById(Integer blogSetId) {
		return blogsetMapper.selectSomeBlogsetById(blogSetId);
	}

	/**
	 * 查询部分博客设置
	 * 
	 * @param blogset
	 *            博客设置详情信息
	 * @return 结果
	 */
	@Override
	public Blogset selectSomeBlogsetsById(Integer blogSetId) {
		return blogsetMapper.selectSomeBlogsetsById(blogSetId);
	}

	/**
	 * @date Nov 8, 2018 2:59:32 PM
	 * @Desc
	 * @param i
	 * @return
	 */
	public Blogset selectBlogsetCommentReviewMsgById(Integer blogSetId) {
		return blogsetMapper.selectBlogsetCommentReviewMsgById(blogSetId);
	}
}
