package com.rzspider.project.blog.blognotice.domain;

import com.rzspider.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 博客公告表 blog_blognotice
 * 
 * @author ricozhou
 * @date 2018-10-23
 */
public class Blognotice extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 博客公告id */
	private Integer blogNoticeId;
	/** 公告标题 */
	private String noticeTitle;
	/** 公告内容 */
	private String noticeContent;
	/** 公告状态 */
	private Integer status;
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

	/**
	 * 设置：博客公告id
	 */
	public void setBlogNoticeId(Integer blogNoticeId) {
		this.blogNoticeId = blogNoticeId;
	}

	/**
	 * 获取：博客公告id
	 */
	public Integer getBlogNoticeId() {
		return blogNoticeId;
	}

	/**
	 * 设置：公告标题
	 */
	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}

	/**
	 * 获取：公告标题
	 */
	public String getNoticeTitle() {
		return noticeTitle;
	}

	/**
	 * 设置：公告内容
	 */
	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	/**
	 * 获取：公告内容
	 */
	public String getNoticeContent() {
		return noticeContent;
	}

	/**
	 * 设置：公告状态
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 获取：公告状态
	 */
	public Integer getStatus() {
		return status;
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

	@Override
	public String toString() {
		return "Blognotice [blogNoticeId=" + blogNoticeId + ", noticeTitle=" + noticeTitle + ", noticeContent="
				+ noticeContent + ", status=" + status + ", createBy=" + createBy + ", createTime=" + createTime
				+ ", updateBy=" + updateBy + ", updateTime=" + updateTime + ", remark=" + remark + "]";
	}

}
