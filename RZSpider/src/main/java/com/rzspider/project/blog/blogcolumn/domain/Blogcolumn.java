package com.rzspider.project.blog.blogcolumn.domain;

import com.rzspider.framework.web.domain.BaseEntity;
import com.rzspider.project.system.menu.domain.Menu;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 博客专栏管理表 blog_blogcolumn
 * 
 * @author ricozhou
 * @date 2018-09-15
 */
public class Blogcolumn extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 博客专栏ID */
	private Integer blogColumnId;
	/** 博客专栏名称 */
	private String blogColumnName;
	/** 父菜单ID */
	private Integer parentId;
	/** 显示顺序 */
	private Integer orderNum;
	/** 网址 */
	private String url;
	private String columnType;
	/** 专栏状态:0显示,1隐藏 */
	private Integer visible;
	/** 专栏图标 */
	private String icon;

	/** 专栏寄语 */
	private String columnMessage;

	/** 专栏背景 */
	private String backImg;
	/** 创建者 */
	private String createBy;
	/** 创建时间 */
	private Date createTime;
	/** 更新者 */
	private String updateBy;
	/** 更新时间 */
	private Date updateTime;
	/** 备注 */
	private String remark;
	/** 子菜单 */
	private List<Blogcolumn> children = new ArrayList<Blogcolumn>();
	/** 父菜单 */
	private Blogcolumn parent;

	public String getColumnMessage() {
		return columnMessage;
	}

	public void setColumnMessage(String columnMessage) {
		this.columnMessage = columnMessage;
	}

	public String getBackImg() {
		return backImg;
	}

	public void setBackImg(String backImg) {
		this.backImg = backImg;
	}

	public Blogcolumn getParent() {
		return parent;
	}

	public void setParent(Blogcolumn parent) {
		this.parent = parent;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 设置：博客专栏ID
	 */
	public void setBlogColumnId(Integer blogColumnId) {
		this.blogColumnId = blogColumnId;
	}

	/**
	 * 获取：博客专栏ID
	 */
	public Integer getBlogColumnId() {
		return blogColumnId;
	}

	/**
	 * 设置：博客专栏名称
	 */
	public void setBlogColumnName(String blogColumnName) {
		this.blogColumnName = blogColumnName;
	}

	/**
	 * 获取：博客专栏名称
	 */
	public String getBlogColumnName() {
		return blogColumnName;
	}

	/**
	 * 设置：父菜单ID
	 */
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	/**
	 * 获取：父菜单ID
	 */
	public Integer getParentId() {
		return parentId;
	}

	/**
	 * 设置：显示顺序
	 */
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	/**
	 * 获取：显示顺序
	 */
	public Integer getOrderNum() {
		return orderNum;
	}

	/**
	 * 设置：专栏状态:0显示,1隐藏
	 */
	public void setVisible(Integer visible) {
		this.visible = visible;
	}

	/**
	 * 获取：专栏状态:0显示,1隐藏
	 */
	public Integer getVisible() {
		return visible;
	}

	/**
	 * 设置：专栏图标
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * 获取：专栏图标
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * 设置：创建者
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	/**
	 * 获取：创建者
	 */
	public String getCreateBy() {
		return createBy;
	}

	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置：更新者
	 */
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	/**
	 * 获取：更新者
	 */
	public String getUpdateBy() {
		return updateBy;
	}

	/**
	 * 设置：更新时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 获取：更新时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
	}

	public List<Blogcolumn> getChildren() {
		return children;
	}

	public void setChildren(List<Blogcolumn> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "Blogcolumn [blogColumnId=" + blogColumnId + ", blogColumnName=" + blogColumnName + ", parentId="
				+ parentId + ", orderNum=" + orderNum + ", url=" + url + ", columnType=" + columnType + ", visible="
				+ visible + ", icon=" + icon + ", columnMessage=" + columnMessage + ", backImg=" + backImg
				+ ", createBy=" + createBy + ", createTime=" + createTime + ", updateBy=" + updateBy + ", updateTime="
				+ updateTime + ", remark=" + remark + ", children=" + children + ", parent=" + parent + "]";
	}

}
