package com.rzspider.project.blog.blogset.domain;

import com.rzspider.framework.web.domain.BaseEntity;

/**
 * 博客侧边栏表 blog_blogsiderbar
 * 
 * @author ricozhou
 * @date 2018-10-09
 */
public class Blogsiderbar extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 侧边栏内容id */
	private Integer blogSiderbrId;
	/** 侧边栏名称 */
	private String blogSiderbrName;
	/** 是否右侧展示 */
	private Integer blogShowRightSiderbr;
	/** 是否左侧展示 */
	private Integer blogShowLeftSiderbr;
	/** 显示顺序 */
	private Integer blogShowOrderNum;
	/** 是否内置 */
	private Integer blogInternalSiderbar;

	@Override
	public String toString() {
		return "Blogsiderbar [blogSiderbrId=" + blogSiderbrId + ", blogSiderbrName=" + blogSiderbrName
				+ ", blogShowRightSiderbr=" + blogShowRightSiderbr + ", blogShowLeftSiderbr=" + blogShowLeftSiderbr
				+ ", blogShowOrderNum=" + blogShowOrderNum + ", blogInternalSiderbar=" + blogInternalSiderbar + "]";
	}

	public Integer getBlogInternalSiderbar() {
		return blogInternalSiderbar;
	}

	public void setBlogInternalSiderbar(Integer blogInternalSiderbar) {
		this.blogInternalSiderbar = blogInternalSiderbar;
	}

	/**
	 * 设置：侧边栏内容id
	 */
	public void setBlogSiderbrId(Integer blogSiderbrId) {
		this.blogSiderbrId = blogSiderbrId;
	}

	/**
	 * 获取：侧边栏内容id
	 */
	public Integer getBlogSiderbrId() {
		return blogSiderbrId;
	}

	/**
	 * 设置：侧边栏名称
	 */
	public void setBlogSiderbrName(String blogSiderbrName) {
		this.blogSiderbrName = blogSiderbrName;
	}

	/**
	 * 获取：侧边栏名称
	 */
	public String getBlogSiderbrName() {
		return blogSiderbrName;
	}

	/**
	 * 设置：是否右侧展示
	 */
	public void setBlogShowRightSiderbr(Integer blogShowRightSiderbr) {
		this.blogShowRightSiderbr = blogShowRightSiderbr;
	}

	/**
	 * 获取：是否右侧展示
	 */
	public Integer getBlogShowRightSiderbr() {
		return blogShowRightSiderbr;
	}

	/**
	 * 设置：是否左侧展示
	 */
	public void setBlogShowLeftSiderbr(Integer blogShowLeftSiderbr) {
		this.blogShowLeftSiderbr = blogShowLeftSiderbr;
	}

	/**
	 * 获取：是否左侧展示
	 */
	public Integer getBlogShowLeftSiderbr() {
		return blogShowLeftSiderbr;
	}

	/**
	 * 设置：显示顺序
	 */
	public void setBlogShowOrderNum(Integer blogShowOrderNum) {
		this.blogShowOrderNum = blogShowOrderNum;
	}

	/**
	 * 获取：显示顺序
	 */
	public Integer getBlogShowOrderNum() {
		return blogShowOrderNum;
	}

}
