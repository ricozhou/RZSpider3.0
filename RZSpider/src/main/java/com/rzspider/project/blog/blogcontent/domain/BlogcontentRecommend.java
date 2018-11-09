package com.rzspider.project.blog.blogcontent.domain;

import com.rzspider.framework.web.domain.BaseEntity;
import com.rzspider.project.blog.blogtags.domain.Blogtags;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 文章推荐内容 blog_blogcontent
 * 
 * @author ricozhou
 * @date 2018-06-12
 */
public class BlogcontentRecommend extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 滚动推荐 */
	private List<Long> scrollRecommendedIds = new ArrayList<Long>();

	/** 特别推荐 */
	private List<Long> specialRecommendedIds = new ArrayList<Long>();

	/** 列表推荐 */
	private List<Long> recommendedIds = new ArrayList<Long>();

	/** 滚动推荐 */
	private List<Blogcontent> scrollRecommendedBlogcontentList = new ArrayList<Blogcontent>();

	/** 特别推荐 */
	private List<Blogcontent> specialRecommendedBlogcontentList = new ArrayList<Blogcontent>();

	/** 列表推荐 */
	private List<Blogcontent> recommendedBlogcontentList = new ArrayList<Blogcontent>();
	/** 列表点赞文章 */
	private List<Blogcontent> likeBlogcontentList = new ArrayList<Blogcontent>();
	/** 列表点击排行 */
	private List<Blogcontent> browseBlogcontentList = new ArrayList<Blogcontent>();

	/** 关于我 */
	private Blogcontent aboutmeBlogcontent;
	/** 关于本站 */
	private Blogcontent aboutwebsiteBlogcontent;

	public Blogcontent getAboutmeBlogcontent() {
		return aboutmeBlogcontent;
	}

	public void setAboutmeBlogcontent(Blogcontent aboutmeBlogcontent) {
		this.aboutmeBlogcontent = aboutmeBlogcontent;
	}

	public Blogcontent getAboutwebsiteBlogcontent() {
		return aboutwebsiteBlogcontent;
	}

	public void setAboutwebsiteBlogcontent(Blogcontent aboutwebsiteBlogcontent) {
		this.aboutwebsiteBlogcontent = aboutwebsiteBlogcontent;
	}

	public List<Blogcontent> getLikeBlogcontentList() {
		return likeBlogcontentList;
	}

	public void setLikeBlogcontentList(List<Blogcontent> likeBlogcontentList) {
		this.likeBlogcontentList = likeBlogcontentList;
	}

	public List<Blogcontent> getBrowseBlogcontentList() {
		return browseBlogcontentList;
	}

	public void setBrowseBlogcontentList(List<Blogcontent> browseBlogcontentList) {
		this.browseBlogcontentList = browseBlogcontentList;
	}

	public List<Long> getScrollRecommendedIds() {
		return scrollRecommendedIds;
	}

	public void setScrollRecommendedIds(List<Long> scrollRecommendedIds) {
		this.scrollRecommendedIds = scrollRecommendedIds;
	}

	public List<Long> getSpecialRecommendedIds() {
		return specialRecommendedIds;
	}

	public void setSpecialRecommendedIds(List<Long> specialRecommendedIds) {
		this.specialRecommendedIds = specialRecommendedIds;
	}

	public List<Long> getRecommendedIds() {
		return recommendedIds;
	}

	public void setRecommendedIds(List<Long> recommendedIds) {
		this.recommendedIds = recommendedIds;
	}

	public List<Blogcontent> getScrollRecommendedBlogcontentList() {
		return scrollRecommendedBlogcontentList;
	}

	public void setScrollRecommendedBlogcontentList(List<Blogcontent> scrollRecommendedBlogcontentList) {
		this.scrollRecommendedBlogcontentList = scrollRecommendedBlogcontentList;
	}

	public List<Blogcontent> getSpecialRecommendedBlogcontentList() {
		return specialRecommendedBlogcontentList;
	}

	public void setSpecialRecommendedBlogcontentList(List<Blogcontent> specialRecommendedBlogcontentList) {
		this.specialRecommendedBlogcontentList = specialRecommendedBlogcontentList;
	}

	public List<Blogcontent> getRecommendedBlogcontentList() {
		return recommendedBlogcontentList;
	}

	public void setRecommendedBlogcontentList(List<Blogcontent> recommendedBlogcontentList) {
		this.recommendedBlogcontentList = recommendedBlogcontentList;
	}

	@Override
	public String toString() {
		return "BlogcontentRecommend [scrollRecommendedIds=" + scrollRecommendedIds + ", specialRecommendedIds="
				+ specialRecommendedIds + ", recommendedIds=" + recommendedIds + ", scrollRecommendedBlogcontentList="
				+ scrollRecommendedBlogcontentList + ", specialRecommendedBlogcontentList="
				+ specialRecommendedBlogcontentList + ", recommendedBlogcontentList=" + recommendedBlogcontentList
				+ ", likeBlogcontentList=" + likeBlogcontentList + ", browseBlogcontentList=" + browseBlogcontentList
				+ ", aboutmeBlogcontent=" + aboutmeBlogcontent + ", aboutwebsiteBlogcontent=" + aboutwebsiteBlogcontent
				+ "]";
	}

}
