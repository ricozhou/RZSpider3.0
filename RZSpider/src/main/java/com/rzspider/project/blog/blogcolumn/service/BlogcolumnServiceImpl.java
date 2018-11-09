package com.rzspider.project.blog.blogcolumn.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rzspider.common.constant.CommonConstant;
import com.rzspider.common.constant.UserConstants;
import com.rzspider.common.constant.project.BlogConstant;
import com.rzspider.common.utils.StringUtils;
import com.rzspider.common.utils.security.ShiroUtils;
import com.rzspider.project.blog.blogcolumn.mapper.BlogcolumnMapper;
import com.rzspider.project.blog.blogcolumn.domain.Blogcolumn;
import com.rzspider.project.blog.blogcolumn.service.IBlogcolumnService;
import com.rzspider.project.spider.spidermanage.domain.SpiderManage;

/**
 * 博客专栏管理 服务层实现
 * 
 * @author ricozhou
 * @date 2018-09-15
 */
@Service
public class BlogcolumnServiceImpl implements IBlogcolumnService {
	@Autowired
	private BlogcolumnMapper blogcolumnMapper;

	/**
	 * 查询博客专栏管理信息
	 * 
	 * @param blogColumnId
	 *            博客专栏管理ID
	 * @return 博客专栏管理信息
	 */
	@Override
	public Blogcolumn selectBlogcolumnById(Integer blogColumnId) {
		return blogcolumnMapper.selectBlogcolumnById(blogColumnId);
	}

	/**
	 * 查询博客专栏管理列表
	 * 
	 * @param blogcolumn
	 *            博客专栏管理信息
	 * @return 博客专栏管理集合
	 */
	@Override
	public List<Blogcolumn> selectBlogcolumnListAll(Blogcolumn blogcolumn) {
		return blogcolumnMapper.selectBlogcolumnListAll(blogcolumn);
	}

	/**
	 * 新增博客专栏管理
	 * 
	 * @param blogcolumn
	 *            博客专栏管理信息
	 * @return 结果
	 */
	@Override
	public int insertBlogcolumn(Blogcolumn blogcolumn) {
		return blogcolumnMapper.insertBlogcolumn(blogcolumn);
	}

	/**
	 * 修改博客专栏管理
	 * 
	 * @param blogcolumn
	 *            博客专栏管理信息
	 * @return 结果
	 */
	@Override
	public int updateBlogcolumn(Blogcolumn blogcolumn) {
		return blogcolumnMapper.updateBlogcolumn(blogcolumn);
	}

	/**
	 * 保存博客专栏管理
	 * 
	 * @param blogcolumn
	 *            博客专栏管理信息
	 * @return 结果
	 */
	@Override
	public int saveBlogcolumn(Blogcolumn blogcolumn) {
		Integer blogColumnId = blogcolumn.getBlogColumnId();
		blogcolumn.setUpdateBy(ShiroUtils.getLoginName());
		int rows = 0;
		if (StringUtils.isNotNull(blogColumnId)) {
			rows = blogcolumnMapper.updateBlogcolumn(blogcolumn);
		} else {
			blogcolumn.setCreateBy(ShiroUtils.getLoginName());
			rows = blogcolumnMapper.insertBlogcolumn(blogcolumn);
		}
		return rows;
	}

	/**
	 * 删除博客专栏管理信息
	 * 
	 * @param blogColumnId
	 *            博客专栏管理ID
	 * @return 结果
	 */
	@Override
	public int deleteBlogcolumnById(Integer blogColumnId) {
		return blogcolumnMapper.deleteBlogcolumnById(blogColumnId);
	}

	/**
	 * 校验
	 * 
	 * @return 结果
	 */
	@Override
	public String checkColumnNameUnique(Blogcolumn blogcolumn) {
		if (blogcolumn.getBlogColumnId() == null) {
			blogcolumn.setBlogColumnId(-1);
		}
		Integer blogColumnId = blogcolumn.getBlogColumnId();
		Blogcolumn info = blogcolumnMapper.checkColumnNameUnique(blogcolumn);
		if ((StringUtils.isNotNull(info) && StringUtils.isNotNull(info.getBlogColumnId())
				&& info.getBlogColumnId() != blogColumnId)
				|| BlogConstant.BLOG_BLOGCOLUMN_DEFAULT_NAME.equals(blogcolumn.getBlogColumnName())) {
			return CommonConstant.NOT_UNIQUE;
		}
		return CommonConstant.UNIQUE;
	}

	/**
	 * 校验
	 * 
	 * @return 结果
	 */
	@Override
	public String checkUrlUnique(Blogcolumn blogcolumn) {
		if (blogcolumn.getBlogColumnId() == null) {
			blogcolumn.setBlogColumnId(-1);
		}
		Integer blogColumnId = blogcolumn.getBlogColumnId();
		Blogcolumn info = blogcolumnMapper.checkUrlUnique(blogcolumn);
		if (StringUtils.isNotNull(info) && StringUtils.isNotNull(info.getBlogColumnId())
				&& info.getBlogColumnId() != blogColumnId) {
			return CommonConstant.NOT_UNIQUE;
		}
		return CommonConstant.UNIQUE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.rzspider.project.blog.blogcolumn.service.IBlogcolumnService#
	 * selectBlogcolumnMessage(int) 查询专栏树
	 */
	@Override
	public List<Blogcolumn> selectBlogcolumnMessageList() {
		Blogcolumn bb = new Blogcolumn();
		bb.setVisible(0);
		bb.setColumnType("M");
		List<Blogcolumn> mBlogcolumnList = blogcolumnMapper.selectBlogcolumnListByStatusAndTypeId(bb);
		if (mBlogcolumnList != null && mBlogcolumnList.size() > 0) {
			bb.setColumnType("C");
			for (Blogcolumn b : mBlogcolumnList) {
				bb.setBlogColumnId(b.getBlogColumnId());
				b.setChildren(blogcolumnMapper.selectBlogcolumnListByStatusAndTypeId(bb));
			}
			return mBlogcolumnList;
		}
		return null;
	}

	// 获取个别信息
	// 主专栏直接返回，次专栏要加上父专栏一起返回
	@Override
	public Blogcolumn selectBlogcolumnMessageByUrl(String url, boolean isM) {
		Blogcolumn bb = new Blogcolumn();
		url = "/" + url;
		bb = blogcolumnMapper.selectBlogcolumnByUrl(url);
		if (!isM && bb != null) {
			bb.setParent(blogcolumnMapper.selectBlogPcolumnByPId(bb.getParentId()));
		}
		return bb;
	}

	// 获取个别信息
	@Override
	public Blogcolumn selectBlogcolumnMessageByColumn(String columnName, boolean isM) {
		Blogcolumn bb = new Blogcolumn();
		bb = blogcolumnMapper.selectBlogcolumnByColumn(columnName);
		if (!isM && bb != null) {
			bb.setParent(blogcolumnMapper.selectBlogPcolumnByPId(bb.getParentId()));
		}
		return bb;
	}

	/**
	 * 查询所有的次专栏信息
	 */
	@Override
	public List<Blogcolumn> selectBlogCcolumnList() {
		return blogcolumnMapper.selectBlogCcolumnList();
	}

	/**
	 * 总专栏数
	 */
	@Override
	public Integer selectAllBlogColumnNum() {
		return blogcolumnMapper.selectAllBlogColumnNum();
	}

}
