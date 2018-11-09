package com.rzspider.project.blog.blogcontent.domain;

import com.rzspider.framework.web.domain.BaseEntity;
import com.rzspider.project.blog.blogtags.domain.Blogtags;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 文章内容表 blog_blogcontent
 * 
 * @author ricozhou
 * @date 2018-06-12
 */
public class Blogcontent extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**  */
	private Long cid;
	/** 文章显示id */
	private String showId;
	/** 文章存储目录 */
	private String blogFileName;
	/** 标题 */
	private String title;
	/**  */
	private String slug;
	/** 创建人id */
	private Long created;
	/** 最近修改人id */
	private Long modified;
	/** 内容 */
	private String content;
	/** markdown内容 */
	private String contentMd;
	/** 封面链接 */
	private String cover;

	/** 图片链接合集 */
	private String images;
	/** 类型 */
	private String type;
	/** 标签 */
	private String tags;
	/** 分类 */
	private String categories;
	/**  */
	private Integer hits;
	/** 评论数量 */
	private Integer commentsNum;
	/** 点赞数量 */
	private Integer likeNum;

	/** 浏览量 */
	private Integer browseNum;
	/** 开启评论 */
	private Integer allowComment;
	/** 允许ping */
	private Integer allowPing;
	/** 允许下载 */
	private Integer allowDownload;
	/** 允许反馈 */
	private Integer allowFeed;
	/** 状态 */
	private Integer status;
	/** 作者 */
	private String author;
	/** 创建时间 */
	private Date gtmCreate;
	/** 修改时间 */
	private Date gtmModified;

	/** 是否开启简介 */
	private Integer showIntroduction;
	/** 简介 */
	private String introduction;
	/** 是否私密文章 */
	private Integer privateArticle;

	/** 专栏 */
	private String blogColumnName;
	/** 标签 */
	private String blogTagsName;
	/** 另加标志 */
	private Integer flag;

	/** 是否滚动推荐 */
	private Integer scrollRecommended;

	/** 是否特别推荐 */
	private Integer specialRecommended;

	/** 是否推荐 */
	private Integer recommended;
	/** 编辑器 */
	private Integer articleEditor;
	/** 文章来源 */
	private String articleSource;
	/** 文章是否置顶 */
	private Integer articleTop;
	/** 标签 */
	private List<String> blogtags = new ArrayList<String>();

	/** 标签id */
	private List<Integer> tagsIds = new ArrayList<Integer>();

	public Integer getArticleTop() {
		return articleTop;
	}

	public void setArticleTop(Integer articleTop) {
		this.articleTop = articleTop;
	}

	public String getArticleSource() {
		return articleSource;
	}

	public void setArticleSource(String articleSource) {
		this.articleSource = articleSource;
	}

	public String getBlogFileName() {
		return blogFileName;
	}

	public void setBlogFileName(String blogFileName) {
		this.blogFileName = blogFileName;
	}

	public Integer getAllowDownload() {
		return allowDownload;
	}

	public void setAllowDownload(Integer allowDownload) {
		this.allowDownload = allowDownload;
	}

	public String getContentMd() {
		return contentMd;
	}

	public void setContentMd(String contentMd) {
		this.contentMd = contentMd;
	}

	public Integer getArticleEditor() {
		return articleEditor;
	}

	public void setArticleEditor(Integer articleEditor) {
		this.articleEditor = articleEditor;
	}

	public String getBlogTagsName() {
		return blogTagsName;
	}

	public Integer getScrollRecommended() {
		return scrollRecommended;
	}

	public void setScrollRecommended(Integer scrollRecommended) {
		this.scrollRecommended = scrollRecommended;
	}

	public Integer getSpecialRecommended() {
		return specialRecommended;
	}

	public void setSpecialRecommended(Integer specialRecommended) {
		this.specialRecommended = specialRecommended;
	}

	public Integer getRecommended() {
		return recommended;
	}

	public void setRecommended(Integer recommended) {
		this.recommended = recommended;
	}

	public void setBlogTagsName(String blogTagsName) {
		this.blogTagsName = blogTagsName;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public List<String> getBlogtags() {
		return blogtags;
	}

	public void setBlogtags(List<String> blogtags) {
		this.blogtags = blogtags;
	}

	public List<Integer> getTagsIds() {
		return tagsIds;
	}

	public void setTagsIds(List<Integer> tagsIds) {
		this.tagsIds = tagsIds;
	}

	public Integer getBrowseNum() {
		return browseNum;
	}

	public void setBrowseNum(Integer browseNum) {
		this.browseNum = browseNum;
	}

	public String getShowId() {
		return showId;
	}

	public void setShowId(String showId) {
		this.showId = showId;
	}

	public Integer getShowIntroduction() {
		return showIntroduction;
	}

	public void setShowIntroduction(Integer showIntroduction) {
		this.showIntroduction = showIntroduction;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public Integer getPrivateArticle() {
		return privateArticle;
	}

	public void setPrivateArticle(Integer privateArticle) {
		this.privateArticle = privateArticle;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getBlogColumnName() {
		return blogColumnName;
	}

	public void setBlogColumnName(String blogColumnName) {
		this.blogColumnName = blogColumnName;
	}

	public Integer getLikeNum() {
		return likeNum;
	}

	public void setLikeNum(Integer likeNum) {
		this.likeNum = likeNum;
	}

	/**
	 * 设置：
	 */
	public void setCid(Long cid) {
		this.cid = cid;
	}

	/**
	 * 获取：
	 */
	public Long getCid() {
		return cid;
	}

	/**
	 * 设置：标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 获取：标题
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置：
	 */
	public void setSlug(String slug) {
		this.slug = slug;
	}

	/**
	 * 获取：
	 */
	public String getSlug() {
		return slug;
	}

	/**
	 * 设置：创建人id
	 */
	public void setCreated(Long created) {
		this.created = created;
	}

	/**
	 * 获取：创建人id
	 */
	public Long getCreated() {
		return created;
	}

	/**
	 * 设置：最近修改人id
	 */
	public void setModified(Long modified) {
		this.modified = modified;
	}

	/**
	 * 获取：最近修改人id
	 */
	public Long getModified() {
		return modified;
	}

	/**
	 * 设置：内容
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 获取：内容
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 设置：类型
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 获取：类型
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置：标签
	 */
	public void setTags(String tags) {
		this.tags = tags;
	}

	/**
	 * 获取：标签
	 */
	public String getTags() {
		return tags;
	}

	/**
	 * 设置：分类
	 */
	public void setCategories(String categories) {
		this.categories = categories;
	}

	/**
	 * 获取：分类
	 */
	public String getCategories() {
		return categories;
	}

	/**
	 * 设置：
	 */
	public void setHits(Integer hits) {
		this.hits = hits;
	}

	/**
	 * 获取：
	 */
	public Integer getHits() {
		return hits;
	}

	/**
	 * 设置：评论数量
	 */
	public void setCommentsNum(Integer commentsNum) {
		this.commentsNum = commentsNum;
	}

	/**
	 * 获取：评论数量
	 */
	public Integer getCommentsNum() {
		return commentsNum;
	}

	/**
	 * 设置：开启评论
	 */
	public void setAllowComment(Integer allowComment) {
		this.allowComment = allowComment;
	}

	/**
	 * 获取：开启评论
	 */
	public Integer getAllowComment() {
		return allowComment;
	}

	/**
	 * 设置：允许ping
	 */
	public void setAllowPing(Integer allowPing) {
		this.allowPing = allowPing;
	}

	/**
	 * 获取：允许ping
	 */
	public Integer getAllowPing() {
		return allowPing;
	}

	/**
	 * 设置：允许反馈
	 */
	public void setAllowFeed(Integer allowFeed) {
		this.allowFeed = allowFeed;
	}

	/**
	 * 获取：允许反馈
	 */
	public Integer getAllowFeed() {
		return allowFeed;
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
	 * 设置：作者
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * 获取：作者
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * 设置：创建时间
	 */
	public void setGtmCreate(Date gtmCreate) {
		this.gtmCreate = gtmCreate;
	}

	/**
	 * 获取：创建时间
	 */
	public Date getGtmCreate() {
		return gtmCreate;
	}

	/**
	 * 设置：修改时间
	 */
	public void setGtmModified(Date gtmModified) {
		this.gtmModified = gtmModified;
	}

	/**
	 * 获取：修改时间
	 */
	public Date getGtmModified() {
		return gtmModified;
	}

	@Override
	public String toString() {
		return "Blogcontent [cid=" + cid + ", showId=" + showId + ", blogFileName=" + blogFileName + ", title=" + title
				+ ", slug=" + slug + ", created=" + created + ", modified=" + modified + ", content=" + content
				+ ", contentMd=" + contentMd + ", cover=" + cover + ", images=" + images + ", type=" + type + ", tags="
				+ tags + ", categories=" + categories + ", hits=" + hits + ", commentsNum=" + commentsNum + ", likeNum="
				+ likeNum + ", browseNum=" + browseNum + ", allowComment=" + allowComment + ", allowPing=" + allowPing
				+ ", allowDownload=" + allowDownload + ", allowFeed=" + allowFeed + ", status=" + status + ", author="
				+ author + ", gtmCreate=" + gtmCreate + ", gtmModified=" + gtmModified + ", showIntroduction="
				+ showIntroduction + ", introduction=" + introduction + ", privateArticle=" + privateArticle
				+ ", blogColumnName=" + blogColumnName + ", blogTagsName=" + blogTagsName + ", flag=" + flag
				+ ", scrollRecommended=" + scrollRecommended + ", specialRecommended=" + specialRecommended
				+ ", recommended=" + recommended + ", articleEditor=" + articleEditor + ", articleSource="
				+ articleSource + ", articleTop=" + articleTop + ", blogtags=" + blogtags + ", tagsIds=" + tagsIds
				+ "]";
	}

}
