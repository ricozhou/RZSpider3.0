package com.rzspider.project.blog.blogkeyword.domain;

import com.rzspider.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 关键词管理表 blog_blogkeyword
 * 
 * @author ricozhou
 * @date 2018-11-08
 */
public class Blogkeyword extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** id */
	private Integer blogKeywordId;
	/** 关键词 */
	private String keywordName;
	/** 评论禁止 */
	private Integer keywordCommentBan;
	/** 留言禁止 */
	private Integer keywordMessageBan;
	/** 建议禁止 */
	private Integer keywordSuggestionBan;
	/** 是否替换 */
	private Integer keywordReplace;
	/** 替换词 */
	private String keywordReplaceWordName;
	/** 状态 */
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
	 * 设置：id
	 */
	public void setBlogKeywordId(Integer blogKeywordId) {
		this.blogKeywordId = blogKeywordId;
	}

	/**
	 * 获取：id
	 */
	public Integer getBlogKeywordId() {
		return blogKeywordId;
	}

	/**
	 * 设置：关键词
	 */
	public void setKeywordName(String keywordName) {
		this.keywordName = keywordName;
	}

	/**
	 * 获取：关键词
	 */
	public String getKeywordName() {
		return keywordName;
	}

	/**
	 * 设置：评论禁止
	 */
	public void setKeywordCommentBan(Integer keywordCommentBan) {
		this.keywordCommentBan = keywordCommentBan;
	}

	/**
	 * 获取：评论禁止
	 */
	public Integer getKeywordCommentBan() {
		return keywordCommentBan;
	}

	/**
	 * 设置：留言禁止
	 */
	public void setKeywordMessageBan(Integer keywordMessageBan) {
		this.keywordMessageBan = keywordMessageBan;
	}

	/**
	 * 获取：留言禁止
	 */
	public Integer getKeywordMessageBan() {
		return keywordMessageBan;
	}

	/**
	 * 设置：建议禁止
	 */
	public void setKeywordSuggestionBan(Integer keywordSuggestionBan) {
		this.keywordSuggestionBan = keywordSuggestionBan;
	}

	/**
	 * 获取：建议禁止
	 */
	public Integer getKeywordSuggestionBan() {
		return keywordSuggestionBan;
	}

	/**
	 * 设置：是否替换
	 */
	public void setKeywordReplace(Integer keywordReplace) {
		this.keywordReplace = keywordReplace;
	}

	/**
	 * 获取：是否替换
	 */
	public Integer getKeywordReplace() {
		return keywordReplace;
	}

	/**
	 * 设置：替换词
	 */
	public void setKeywordReplaceWordName(String keywordReplaceWordName) {
		this.keywordReplaceWordName = keywordReplaceWordName;
	}

	/**
	 * 获取：替换词
	 */
	public String getKeywordReplaceWordName() {
		return keywordReplaceWordName;
	}

	/**
	 * 设置：状态
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 获取：状态
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
		return "Blogkeyword [blogKeywordId=" + blogKeywordId + ", keywordName=" + keywordName + ", keywordCommentBan="
				+ keywordCommentBan + ", keywordMessageBan=" + keywordMessageBan + ", keywordSuggestionBan="
				+ keywordSuggestionBan + ", keywordReplace=" + keywordReplace + ", keywordReplaceWordName="
				+ keywordReplaceWordName + ", status=" + status + ", createBy=" + createBy + ", createTime="
				+ createTime + ", updateBy=" + updateBy + ", updateTime=" + updateTime + ", remark=" + remark + "]";
	}

}
