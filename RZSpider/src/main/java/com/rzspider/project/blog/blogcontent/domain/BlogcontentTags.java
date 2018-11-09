package com.rzspider.project.blog.blogcontent.domain;

/**
 * @author ricozhou
 * @date Sep 21, 2018 12:04:01 PM
 * @Desc
 */
public class BlogcontentTags {
	/**  */
	private Long cid;
	/** 博客标签id */
	private Integer blogTagsId;

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public Integer getBlogTagsId() {
		return blogTagsId;
	}

	public void setBlogTagsId(Integer blogTagsId) {
		this.blogTagsId = blogTagsId;
	}

	@Override
	public String toString() {
		return "BlogcontentTags [cid=" + cid + ", blogTagsId=" + blogTagsId + "]";
	}

}
