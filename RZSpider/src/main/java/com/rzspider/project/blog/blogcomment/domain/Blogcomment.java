package com.rzspider.project.blog.blogcomment.domain;

import com.rzspider.framework.web.domain.BaseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 博客评论表 blog_blogcomment
 * 
 * @author ricozhou
 * @date 2018-11-07
 */
public class Blogcomment extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 评论id */
	private Integer blogCommentId;
	/** 父id */
	private Integer parentId;
	/** 文章id */
	private String showId;
	/** 文章标题 */
	private String title;
	/** 评论人名称 */
	private String replyName;
	/** 被评论人名称 */
	private String beReplyName;
	/** 评论人qq */
	private String replyQqNum;
	/** 评论人邮箱 */
	private String replyEmail;
	/** 评论人头像 */
	private String replyHeadImg;
	/** 评论内容 */
	private String replyContent;
	/** 评论时间 */
	private String replyTime;
	/** 评论点赞量 */
	private Integer likeNum;
	/** 评论状态0通过1审核2驳回 */
	private Integer status;
	/** 更新者 */
	private String updateBy;
	/** 更新时间 */
	private Date updateTime;
	/** 备注 */
	private String remark;

	/** 其回复体 */
	private List<Blogcomment> replyBody = new ArrayList<Blogcomment>();

	public Integer getLikeNum() {
		return likeNum;
	}

	public void setLikeNum(Integer likeNum) {
		this.likeNum = likeNum;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	/**
	 * 设置：评论id
	 */
	public void setBlogCommentId(Integer blogCommentId) {
		this.blogCommentId = blogCommentId;
	}

	/**
	 * 获取：评论id
	 */
	public Integer getBlogCommentId() {
		return blogCommentId;
	}

	/**
	 * 设置：文章id
	 */
	public void setShowId(String showId) {
		this.showId = showId;
	}

	/**
	 * 获取：文章id
	 */
	public String getShowId() {
		return showId;
	}

	/**
	 * 设置：评论人名称
	 */
	public void setReplyName(String replyName) {
		this.replyName = replyName;
	}

	/**
	 * 获取：评论人名称
	 */
	public String getReplyName() {
		return replyName;
	}

	/**
	 * 设置：被评论人名称
	 */
	public void setBeReplyName(String beReplyName) {
		this.beReplyName = beReplyName;
	}

	/**
	 * 获取：被评论人名称
	 */
	public String getBeReplyName() {
		return beReplyName;
	}

	/**
	 * 设置：评论人qq
	 */
	public void setReplyQqNum(String replyQqNum) {
		this.replyQqNum = replyQqNum;
	}

	/**
	 * 获取：评论人qq
	 */
	public String getReplyQqNum() {
		return replyQqNum;
	}

	/**
	 * 设置：评论人邮箱
	 */
	public void setReplyEmail(String replyEmail) {
		this.replyEmail = replyEmail;
	}

	/**
	 * 获取：评论人邮箱
	 */
	public String getReplyEmail() {
		return replyEmail;
	}

	/**
	 * 设置：评论人头像
	 */
	public void setReplyHeadImg(String replyHeadImg) {
		this.replyHeadImg = replyHeadImg;
	}

	/**
	 * 获取：评论人头像
	 */
	public String getReplyHeadImg() {
		return replyHeadImg;
	}

	/**
	 * 设置：评论内容
	 */
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	/**
	 * 获取：评论内容
	 */
	public String getReplyContent() {
		return replyContent;
	}

	/**
	 * 设置：评论时间
	 */
	public void setReplyTime(String replyTime) {
		this.replyTime = replyTime;
	}

	/**
	 * 获取：评论时间
	 */
	public String getReplyTime() {
		return replyTime;
	}

	/**
	 * 设置：评论状态0通过1审核2驳回
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 获取：评论状态0通过1审核2驳回
	 */
	public Integer getStatus() {
		return status;
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

	public List<Blogcomment> getReplyBody() {
		return replyBody;
	}

	public void setReplyBody(List<Blogcomment> replyBody) {
		this.replyBody = replyBody;
	}

	@Override
	public String toString() {
		return "Blogcomment [blogCommentId=" + blogCommentId + ", parentId=" + parentId + ", showId=" + showId
				+ ", title=" + title + ", replyName=" + replyName + ", beReplyName=" + beReplyName + ", replyQqNum="
				+ replyQqNum + ", replyEmail=" + replyEmail + ", replyHeadImg=" + replyHeadImg + ", replyContent="
				+ replyContent + ", replyTime=" + replyTime + ", likeNum=" + likeNum + ", status=" + status
				+ ", updateBy=" + updateBy + ", updateTime=" + updateTime + ", remark=" + remark + ", replyBody="
				+ replyBody + "]";
	}

}
