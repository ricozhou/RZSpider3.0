package com.rzspider.project.blog.blogtags.domain;

import com.rzspider.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 博客标签表 blog_blogtags
 * 
 * @author ricozhou
 * @date 2018-09-25
 */
public class Blogtags extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 博客标签id */
	private Integer blogTagsId;
	/** 博客标签名 */
	private String blogTagsName;
	/** 标签是否可用 */
	private Integer status;
	/** 标签寄语 */
	private String tagsMessage;
	/** 标签背景 */
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
	/** 是否存在此专栏标识 默认不存在 */
	private boolean flag = false;

	public Blogtags() {

	}

	public Blogtags(Integer status, String blogTagsName) {
		this.status = status;
		this.blogTagsName = blogTagsName;
	}

	/**
	 * 设置：博客标签id
	 */
	public void setBlogTagsId(Integer blogTagsId) {
		this.blogTagsId = blogTagsId;
	}

	/**
	 * 获取：博客标签id
	 */
	public Integer getBlogTagsId() {
		return blogTagsId;
	}

	/**
	 * 设置：博客标签名
	 */
	public void setBlogTagsName(String blogTagsName) {
		this.blogTagsName = blogTagsName;
	}

	/**
	 * 获取：博客标签名
	 */
	public String getBlogTagsName() {
		return blogTagsName;
	}

	/**
	 * 设置：标签是否可用
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 获取：标签是否可用
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 设置：标签寄语
	 */
	public void setTagsMessage(String tagsMessage) {
		this.tagsMessage = tagsMessage;
	}

	/**
	 * 获取：标签寄语
	 */
	public String getTagsMessage() {
		return tagsMessage;
	}

	/**
	 * 设置：标签背景
	 */
	public void setBackImg(String backImg) {
		this.backImg = backImg;
	}

	/**
	 * 获取：标签背景
	 */
	public String getBackImg() {
		return backImg;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
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
		return "Blogtags [blogTagsId=" + blogTagsId + ", blogTagsName=" + blogTagsName + ", status=" + status
				+ ", tagsMessage=" + tagsMessage + ", backImg=" + backImg + ", createBy=" + createBy + ", createTime="
				+ createTime + ", updateBy=" + updateBy + ", updateTime=" + updateTime + ", remark=" + remark
				+ ", flag=" + flag + "]";
	}
}
