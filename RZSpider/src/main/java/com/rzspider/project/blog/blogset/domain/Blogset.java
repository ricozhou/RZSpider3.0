package com.rzspider.project.blog.blogset.domain;

import com.rzspider.framework.web.domain.BaseEntity;
import com.rzspider.project.system.website.domain.Website;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 博客设置详情表 blog_blogset
 * 
 * @author ricozhou
 * @date 2018-09-13
 */
public class Blogset extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**  */
	private Integer blogSetId;
	/** 博客标题 */
	private String basicsetTitle;

	/** 博客网站图标 */
	private String basicsetWebsiteIco;
	/** 博客描述 */
	private String basicsetDes;
	/** 博客皮肤 */
	private Integer basicsetSkin;

	/** 文章编辑器 */
	private Integer basicsetArticleEditor;
	/** 网站上线时间 */
	private String basicsetWebsiteOnlinetime;
	/** 全局允许评论 */
	private Integer basicsetGlobalAllowComment;
	/** 全局展示评论 */
	private Integer basicsetGlobalShowComment;
	/** 全局允许转载 */
	private Integer basicsetGlobalAllowReprint;
	/** 评论是否通知 */
	private Integer basicsetCommentNotice;
	/** 赞赏是否开启 */
	private Integer basicsetOpenAppreciate;
	/** 文章允许下载 */
	private Integer basicsetOpenBlogDownload;
	/** 网站是否开启公告 */
	private Integer basicsetOpenNotice;
	/** 文章自动加载 */
	private Integer basicsetAutoLoading;
	/** 显示文章来源 */
	private Integer basicsetShowArticleSource;
	/** 评论自动审核 */
	private Integer basicsetCommentAutoReview;
	/** 文章图片是否打水印 */
	private Integer basicsetAddWaterMark;
	/** 水印信息 */
	private String basicsetWaterMarkMsg;
	/** 水印设置信息 */
	private String basicsetWaterMarkSetMsg;
	/** 是否添加版权声明 */
	private Integer basicsetAddCopyrightNotice;
	/** 版权声明信息 */
	private String basicsetCopyrightNoticeInfo;
	/** 基础设置备注 */
	private String basicsetRemark;
	/** 博主姓名 */
	private String bloggersetBloggerName;
	/** 博主介绍 */
	private String bloggersetBloggerDes;
	/** 博主头像 */
	private String bloggersetBloggerProfile;
	/** 博主新浪微博 */
	private String bloggersetBloggerSinaWeibo;
	/** 博主QQ */
	private String bloggersetBloggerQqNumber;
	/** 博主QQ二维码 */
	private String bloggersetBloggerQqOrcode;
	/** QQ群 */
	private String bloggersetBloggerQqGroupNumber;
	/** QQ群二维码 */
	private String bloggersetBloggerQqGroupOrcode;
	/** 博主微信 */
	private String bloggersetBloggerWechatNumber;
	/** 博主微信二维码 */
	private String bloggersetBloggerWechatOrcode;

	/** 博主微信群名 */
	private String bloggersetBloggerWechatGroupName;
	/** 微信群二维码 */
	private String bloggersetBloggerWechatGroupOrcode;
	/** 博主电子邮箱 */
	private String bloggersetBloggerEmail;
	/** 博主CSDN */
	private String bloggersetBloggerCsdn;
	/** 博主码云 */
	private String bloggersetBloggerGitee;
	/** 博主GitHub */
	private String bloggersetBloggerGithub;
	/** 打赏支付宝收款码 */
	private String bloggersetBloggerRewardAlipayCollectionCode;
	/** 打赏微信收款码 */
	private String bloggersetBloggerRewardWechatCollectionCode;
	/** 博主设置备注 */
	private String bloggersetRemark;
	/** 每页显示文章条数 */
	private Integer blogsetPerpageShowNum;
	/** 最新文章显示条数 */
	private Integer blogsetLatestShowNum;
	/** 滚动推荐显示条数 */
	private Integer blogsetScrollRecommendedShowNum;
	/** 列表推荐显示条数 */
	private Integer blogsetRecommendedShowNum;
	/** 点击排行显示条数 */
	private Integer blogsetRankingShowNum;
	/** 特别推荐显示条数 */
	private Integer blogsetSpecialRecdShowNum;
	/** 导航菜单显示条数 */
	private Integer blogsetNavBarShowNum;
	/** 主文章无图使用默认图片 */
	private Integer blogsetNoPicUseDefault;
	/** 无封面图使用内容图 */
	private Integer blogsetNoCoverpicUseContentpic;
	/** 默认图片集合 */
	private String blogsetDefaultPic;
	/** 友情链接 */
	private String blogsetFriendLinks;
	/** 侧边栏其他信息 */
	private String blogsetSidebarOtherMessage;
	/** 博客设置备注 */
	private String blogsetRemark;
	/** 创建者 */
	private String createBy;
	/** 创建时间 */
	private Date createTime;
	/** 更新者 */
	private String updateBy;
	/** 更新时间 */
	private Date updateTime;
	/** 博主QQ二维码图片名 */
	private String bloggersetBloggerQqOrcodeUuidName;
	/** 博主QQ群二维码图片名 */
	private String bloggersetBloggerQqGroupOrcodeUuidName;
	/** 博主微信二维码图片名 */
	private String bloggersetBloggerWechatOrcodeUuidName;
	/** 博主微信群二维码图片名 */
	private String bloggersetBloggerWechatGroupOrcodeUuidName;
	/** 打赏支付宝收款码图片名 */
	private String bloggersetBloggerRewardAlipayCollectionCodeUuidName;
	/** 打赏微信收款码图片名 */
	private String bloggersetBloggerRewardWechatCollectionCodeUuidName;

	// 主网站部分设置
	private Website website;

	// 主网站部分设置
	private List<Blogsiderbar> blogsiderbarList = new ArrayList<Blogsiderbar>();

	/** 网站色调 */
	private String stylesetColor;
	/** 网站背景颜色 */
	private String stylesetBackColor;
	/** 平滑样式 */
	private Integer stylesetSmoothStyle;
	/** 是否显示右侧边栏 */
	private Integer stylesetShowRightSiderbar;
	/** 右侧边栏内容 */
	private String stylesetRightSiderbarContent;
	/** 是否显示左侧边栏 */
	private Integer stylesetShowLeftSiderbar;
	/** 左侧边栏内容 */
	private String stylesetLeftSiderbarContent;
	/** 样式设置备注 */
	private String stylesetRemark;

	public Integer getBasicsetShowArticleSource() {
		return basicsetShowArticleSource;
	}

	public void setBasicsetShowArticleSource(Integer basicsetShowArticleSource) {
		this.basicsetShowArticleSource = basicsetShowArticleSource;
	}

	public Integer getBasicsetCommentAutoReview() {
		return basicsetCommentAutoReview;
	}

	public void setBasicsetCommentAutoReview(Integer basicsetCommentAutoReview) {
		this.basicsetCommentAutoReview = basicsetCommentAutoReview;
	}

	public Integer getBasicsetAutoLoading() {
		return basicsetAutoLoading;
	}

	public void setBasicsetAutoLoading(Integer basicsetAutoLoading) {
		this.basicsetAutoLoading = basicsetAutoLoading;
	}

	public String getBasicsetWebsiteOnlinetime() {
		return basicsetWebsiteOnlinetime;
	}

	public void setBasicsetWebsiteOnlinetime(String basicsetWebsiteOnlinetime) {
		this.basicsetWebsiteOnlinetime = basicsetWebsiteOnlinetime;
	}

	public Integer getBasicsetOpenNotice() {
		return basicsetOpenNotice;
	}

	public void setBasicsetOpenNotice(Integer basicsetOpenNotice) {
		this.basicsetOpenNotice = basicsetOpenNotice;
	}

	public String getBasicsetWaterMarkSetMsg() {
		return basicsetWaterMarkSetMsg;
	}

	public void setBasicsetWaterMarkSetMsg(String basicsetWaterMarkSetMsg) {
		this.basicsetWaterMarkSetMsg = basicsetWaterMarkSetMsg;
	}

	public Integer getBasicsetAddWaterMark() {
		return basicsetAddWaterMark;
	}

	public void setBasicsetAddWaterMark(Integer basicsetAddWaterMark) {
		this.basicsetAddWaterMark = basicsetAddWaterMark;
	}

	public String getBasicsetWaterMarkMsg() {
		return basicsetWaterMarkMsg;
	}

	public void setBasicsetWaterMarkMsg(String basicsetWaterMarkMsg) {
		this.basicsetWaterMarkMsg = basicsetWaterMarkMsg;
	}

	public Integer getBasicsetOpenBlogDownload() {
		return basicsetOpenBlogDownload;
	}

	public void setBasicsetOpenBlogDownload(Integer basicsetOpenBlogDownload) {
		this.basicsetOpenBlogDownload = basicsetOpenBlogDownload;
	}

	public List<Blogsiderbar> getBlogsiderbarList() {
		return blogsiderbarList;
	}

	public void setBlogsiderbarList(List<Blogsiderbar> blogsiderbarList) {
		this.blogsiderbarList = blogsiderbarList;
	}

	public String getStylesetColor() {
		return stylesetColor;
	}

	public void setStylesetColor(String stylesetColor) {
		this.stylesetColor = stylesetColor;
	}

	public String getStylesetBackColor() {
		return stylesetBackColor;
	}

	public void setStylesetBackColor(String stylesetBackColor) {
		this.stylesetBackColor = stylesetBackColor;
	}

	public Integer getStylesetSmoothStyle() {
		return stylesetSmoothStyle;
	}

	public void setStylesetSmoothStyle(Integer stylesetSmoothStyle) {
		this.stylesetSmoothStyle = stylesetSmoothStyle;
	}

	public Integer getStylesetShowRightSiderbar() {
		return stylesetShowRightSiderbar;
	}

	public void setStylesetShowRightSiderbar(Integer stylesetShowRightSiderbar) {
		this.stylesetShowRightSiderbar = stylesetShowRightSiderbar;
	}

	public String getStylesetRightSiderbarContent() {
		return stylesetRightSiderbarContent;
	}

	public void setStylesetRightSiderbarContent(String stylesetRightSiderbarContent) {
		this.stylesetRightSiderbarContent = stylesetRightSiderbarContent;
	}

	public Integer getStylesetShowLeftSiderbar() {
		return stylesetShowLeftSiderbar;
	}

	public void setStylesetShowLeftSiderbar(Integer stylesetShowLeftSiderbar) {
		this.stylesetShowLeftSiderbar = stylesetShowLeftSiderbar;
	}

	public String getStylesetLeftSiderbarContent() {
		return stylesetLeftSiderbarContent;
	}

	public void setStylesetLeftSiderbarContent(String stylesetLeftSiderbarContent) {
		this.stylesetLeftSiderbarContent = stylesetLeftSiderbarContent;
	}

	public String getStylesetRemark() {
		return stylesetRemark;
	}

	public void setStylesetRemark(String stylesetRemark) {
		this.stylesetRemark = stylesetRemark;
	}

	public Integer getBasicsetArticleEditor() {
		return basicsetArticleEditor;
	}

	public void setBasicsetArticleEditor(Integer basicsetArticleEditor) {
		this.basicsetArticleEditor = basicsetArticleEditor;
	}

	public String getBlogsetDefaultPic() {
		return blogsetDefaultPic;
	}

	public void setBlogsetDefaultPic(String blogsetDefaultPic) {
		this.blogsetDefaultPic = blogsetDefaultPic;
	}

	public String getBasicsetWebsiteIco() {
		return basicsetWebsiteIco;
	}

	public void setBasicsetWebsiteIco(String basicsetWebsiteIco) {
		this.basicsetWebsiteIco = basicsetWebsiteIco;
	}

	public Website getWebsite() {
		return website;
	}

	public void setWebsite(Website website) {
		this.website = website;
	}

	public Integer getBlogsetScrollRecommendedShowNum() {
		return blogsetScrollRecommendedShowNum;
	}

	public void setBlogsetScrollRecommendedShowNum(Integer blogsetScrollRecommendedShowNum) {
		this.blogsetScrollRecommendedShowNum = blogsetScrollRecommendedShowNum;
	}

	public Integer getBasicsetOpenAppreciate() {
		return basicsetOpenAppreciate;
	}

	public void setBasicsetOpenAppreciate(Integer basicsetOpenAppreciate) {
		this.basicsetOpenAppreciate = basicsetOpenAppreciate;
	}

	public String getBloggersetBloggerProfile() {
		return bloggersetBloggerProfile;
	}

	public void setBloggersetBloggerProfile(String bloggersetBloggerProfile) {
		this.bloggersetBloggerProfile = bloggersetBloggerProfile;
	}

	public String getBlogsetSidebarOtherMessage() {
		return blogsetSidebarOtherMessage;
	}

	public void setBlogsetSidebarOtherMessage(String blogsetSidebarOtherMessage) {
		this.blogsetSidebarOtherMessage = blogsetSidebarOtherMessage;
	}

	public String getBloggersetBloggerWechatGroupName() {
		return bloggersetBloggerWechatGroupName;
	}

	public void setBloggersetBloggerWechatGroupName(String bloggersetBloggerWechatGroupName) {
		this.bloggersetBloggerWechatGroupName = bloggersetBloggerWechatGroupName;
	}

	public String getBloggersetBloggerQqOrcodeUuidName() {
		return bloggersetBloggerQqOrcodeUuidName;
	}

	public void setBloggersetBloggerQqOrcodeUuidName(String bloggersetBloggerQqOrcodeUuidName) {
		this.bloggersetBloggerQqOrcodeUuidName = bloggersetBloggerQqOrcodeUuidName;
	}

	public String getBloggersetBloggerQqGroupOrcodeUuidName() {
		return bloggersetBloggerQqGroupOrcodeUuidName;
	}

	public void setBloggersetBloggerQqGroupOrcodeUuidName(String bloggersetBloggerQqGroupOrcodeUuidName) {
		this.bloggersetBloggerQqGroupOrcodeUuidName = bloggersetBloggerQqGroupOrcodeUuidName;
	}

	public String getBloggersetBloggerWechatOrcodeUuidName() {
		return bloggersetBloggerWechatOrcodeUuidName;
	}

	public void setBloggersetBloggerWechatOrcodeUuidName(String bloggersetBloggerWechatOrcodeUuidName) {
		this.bloggersetBloggerWechatOrcodeUuidName = bloggersetBloggerWechatOrcodeUuidName;
	}

	public String getBloggersetBloggerWechatGroupOrcodeUuidName() {
		return bloggersetBloggerWechatGroupOrcodeUuidName;
	}

	public void setBloggersetBloggerWechatGroupOrcodeUuidName(String bloggersetBloggerWechatGroupOrcodeUuidName) {
		this.bloggersetBloggerWechatGroupOrcodeUuidName = bloggersetBloggerWechatGroupOrcodeUuidName;
	}

	public String getBloggersetBloggerRewardAlipayCollectionCodeUuidName() {
		return bloggersetBloggerRewardAlipayCollectionCodeUuidName;
	}

	public void setBloggersetBloggerRewardAlipayCollectionCodeUuidName(
			String bloggersetBloggerRewardAlipayCollectionCodeUuidName) {
		this.bloggersetBloggerRewardAlipayCollectionCodeUuidName = bloggersetBloggerRewardAlipayCollectionCodeUuidName;
	}

	public String getBloggersetBloggerRewardWechatCollectionCodeUuidName() {
		return bloggersetBloggerRewardWechatCollectionCodeUuidName;
	}

	public void setBloggersetBloggerRewardWechatCollectionCodeUuidName(
			String bloggersetBloggerRewardWechatCollectionCodeUuidName) {
		this.bloggersetBloggerRewardWechatCollectionCodeUuidName = bloggersetBloggerRewardWechatCollectionCodeUuidName;
	}

	/**
	 * 设置：
	 */
	public void setBlogSetId(Integer blogSetId) {
		this.blogSetId = blogSetId;
	}

	/**
	 * 获取：
	 */
	public Integer getBlogSetId() {
		return blogSetId;
	}

	/**
	 * 设置：博客标题
	 */
	public void setBasicsetTitle(String basicsetTitle) {
		this.basicsetTitle = basicsetTitle;
	}

	/**
	 * 获取：博客标题
	 */
	public String getBasicsetTitle() {
		return basicsetTitle;
	}

	/**
	 * 设置：博客描述
	 */
	public void setBasicsetDes(String basicsetDes) {
		this.basicsetDes = basicsetDes;
	}

	/**
	 * 获取：博客描述
	 */
	public String getBasicsetDes() {
		return basicsetDes;
	}

	/**
	 * 设置：博客皮肤
	 */
	public void setBasicsetSkin(Integer basicsetSkin) {
		this.basicsetSkin = basicsetSkin;
	}

	/**
	 * 获取：博客皮肤
	 */
	public Integer getBasicsetSkin() {
		return basicsetSkin;
	}

	/**
	 * 设置：全局允许评论
	 */
	public void setBasicsetGlobalAllowComment(Integer basicsetGlobalAllowComment) {
		this.basicsetGlobalAllowComment = basicsetGlobalAllowComment;
	}

	/**
	 * 获取：全局允许评论
	 */
	public Integer getBasicsetGlobalAllowComment() {
		return basicsetGlobalAllowComment;
	}

	/**
	 * 设置：全局展示评论
	 */
	public void setBasicsetGlobalShowComment(Integer basicsetGlobalShowComment) {
		this.basicsetGlobalShowComment = basicsetGlobalShowComment;
	}

	/**
	 * 获取：全局展示评论
	 */
	public Integer getBasicsetGlobalShowComment() {
		return basicsetGlobalShowComment;
	}

	/**
	 * 设置：全局允许转载
	 */
	public void setBasicsetGlobalAllowReprint(Integer basicsetGlobalAllowReprint) {
		this.basicsetGlobalAllowReprint = basicsetGlobalAllowReprint;
	}

	/**
	 * 获取：全局允许转载
	 */
	public Integer getBasicsetGlobalAllowReprint() {
		return basicsetGlobalAllowReprint;
	}

	/**
	 * 设置：评论是否通知
	 */
	public void setBasicsetCommentNotice(Integer basicsetCommentNotice) {
		this.basicsetCommentNotice = basicsetCommentNotice;
	}

	/**
	 * 获取：评论是否通知
	 */
	public Integer getBasicsetCommentNotice() {
		return basicsetCommentNotice;
	}

	/**
	 * 设置：是否添加版权声明
	 */
	public void setBasicsetAddCopyrightNotice(Integer basicsetAddCopyrightNotice) {
		this.basicsetAddCopyrightNotice = basicsetAddCopyrightNotice;
	}

	/**
	 * 获取：是否添加版权声明
	 */
	public Integer getBasicsetAddCopyrightNotice() {
		return basicsetAddCopyrightNotice;
	}

	/**
	 * 设置：版权声明信息
	 */
	public void setBasicsetCopyrightNoticeInfo(String basicsetCopyrightNoticeInfo) {
		this.basicsetCopyrightNoticeInfo = basicsetCopyrightNoticeInfo;
	}

	/**
	 * 获取：版权声明信息
	 */
	public String getBasicsetCopyrightNoticeInfo() {
		return basicsetCopyrightNoticeInfo;
	}

	/**
	 * 设置：基础设置备注
	 */
	public void setBasicsetRemark(String basicsetRemark) {
		this.basicsetRemark = basicsetRemark;
	}

	/**
	 * 获取：基础设置备注
	 */
	public String getBasicsetRemark() {
		return basicsetRemark;
	}

	/**
	 * 设置：博主姓名
	 */
	public void setBloggersetBloggerName(String bloggersetBloggerName) {
		this.bloggersetBloggerName = bloggersetBloggerName;
	}

	/**
	 * 获取：博主姓名
	 */
	public String getBloggersetBloggerName() {
		return bloggersetBloggerName;
	}

	/**
	 * 设置：博主介绍
	 */
	public void setBloggersetBloggerDes(String bloggersetBloggerDes) {
		this.bloggersetBloggerDes = bloggersetBloggerDes;
	}

	/**
	 * 获取：博主介绍
	 */
	public String getBloggersetBloggerDes() {
		return bloggersetBloggerDes;
	}

	/**
	 * 设置：博主新浪微博
	 */
	public void setBloggersetBloggerSinaWeibo(String bloggersetBloggerSinaWeibo) {
		this.bloggersetBloggerSinaWeibo = bloggersetBloggerSinaWeibo;
	}

	/**
	 * 获取：博主新浪微博
	 */
	public String getBloggersetBloggerSinaWeibo() {
		return bloggersetBloggerSinaWeibo;
	}

	/**
	 * 设置：博主QQ
	 */
	public void setBloggersetBloggerQqNumber(String bloggersetBloggerQqNumber) {
		this.bloggersetBloggerQqNumber = bloggersetBloggerQqNumber;
	}

	/**
	 * 获取：博主QQ
	 */
	public String getBloggersetBloggerQqNumber() {
		return bloggersetBloggerQqNumber;
	}

	/**
	 * 设置：博主QQ二维码
	 */
	public void setBloggersetBloggerQqOrcode(String bloggersetBloggerQqOrcode) {
		this.bloggersetBloggerQqOrcode = bloggersetBloggerQqOrcode;
	}

	/**
	 * 获取：博主QQ二维码
	 */
	public String getBloggersetBloggerQqOrcode() {
		return bloggersetBloggerQqOrcode;
	}

	/**
	 * 设置：QQ群
	 */
	public void setBloggersetBloggerQqGroupNumber(String bloggersetBloggerQqGroupNumber) {
		this.bloggersetBloggerQqGroupNumber = bloggersetBloggerQqGroupNumber;
	}

	/**
	 * 获取：QQ群
	 */
	public String getBloggersetBloggerQqGroupNumber() {
		return bloggersetBloggerQqGroupNumber;
	}

	/**
	 * 设置：QQ群二维码
	 */
	public void setBloggersetBloggerQqGroupOrcode(String bloggersetBloggerQqGroupOrcode) {
		this.bloggersetBloggerQqGroupOrcode = bloggersetBloggerQqGroupOrcode;
	}

	/**
	 * 获取：QQ群二维码
	 */
	public String getBloggersetBloggerQqGroupOrcode() {
		return bloggersetBloggerQqGroupOrcode;
	}

	/**
	 * 设置：博主微信
	 */
	public void setBloggersetBloggerWechatNumber(String bloggersetBloggerWechatNumber) {
		this.bloggersetBloggerWechatNumber = bloggersetBloggerWechatNumber;
	}

	/**
	 * 获取：博主微信
	 */
	public String getBloggersetBloggerWechatNumber() {
		return bloggersetBloggerWechatNumber;
	}

	/**
	 * 设置：博主微信二维码
	 */
	public void setBloggersetBloggerWechatOrcode(String bloggersetBloggerWechatOrcode) {
		this.bloggersetBloggerWechatOrcode = bloggersetBloggerWechatOrcode;
	}

	/**
	 * 获取：博主微信二维码
	 */
	public String getBloggersetBloggerWechatOrcode() {
		return bloggersetBloggerWechatOrcode;
	}

	/**
	 * 设置：微信群二维码
	 */
	public void setBloggersetBloggerWechatGroupOrcode(String bloggersetBloggerWechatGroupOrcode) {
		this.bloggersetBloggerWechatGroupOrcode = bloggersetBloggerWechatGroupOrcode;
	}

	/**
	 * 获取：微信群二维码
	 */
	public String getBloggersetBloggerWechatGroupOrcode() {
		return bloggersetBloggerWechatGroupOrcode;
	}

	/**
	 * 设置：博主电子邮箱
	 */
	public void setBloggersetBloggerEmail(String bloggersetBloggerEmail) {
		this.bloggersetBloggerEmail = bloggersetBloggerEmail;
	}

	/**
	 * 获取：博主电子邮箱
	 */
	public String getBloggersetBloggerEmail() {
		return bloggersetBloggerEmail;
	}

	/**
	 * 设置：博主CSDN
	 */
	public void setBloggersetBloggerCsdn(String bloggersetBloggerCsdn) {
		this.bloggersetBloggerCsdn = bloggersetBloggerCsdn;
	}

	/**
	 * 获取：博主CSDN
	 */
	public String getBloggersetBloggerCsdn() {
		return bloggersetBloggerCsdn;
	}

	/**
	 * 设置：博主码云
	 */
	public void setBloggersetBloggerGitee(String bloggersetBloggerGitee) {
		this.bloggersetBloggerGitee = bloggersetBloggerGitee;
	}

	/**
	 * 获取：博主码云
	 */
	public String getBloggersetBloggerGitee() {
		return bloggersetBloggerGitee;
	}

	/**
	 * 设置：博主GitHub
	 */
	public void setBloggersetBloggerGithub(String bloggersetBloggerGithub) {
		this.bloggersetBloggerGithub = bloggersetBloggerGithub;
	}

	/**
	 * 获取：博主GitHub
	 */
	public String getBloggersetBloggerGithub() {
		return bloggersetBloggerGithub;
	}

	/**
	 * 设置：打赏支付宝收款码
	 */
	public void setBloggersetBloggerRewardAlipayCollectionCode(String bloggersetBloggerRewardAlipayCollectionCode) {
		this.bloggersetBloggerRewardAlipayCollectionCode = bloggersetBloggerRewardAlipayCollectionCode;
	}

	/**
	 * 获取：打赏支付宝收款码
	 */
	public String getBloggersetBloggerRewardAlipayCollectionCode() {
		return bloggersetBloggerRewardAlipayCollectionCode;
	}

	/**
	 * 设置：打赏微信收款码
	 */
	public void setBloggersetBloggerRewardWechatCollectionCode(String bloggersetBloggerRewardWechatCollectionCode) {
		this.bloggersetBloggerRewardWechatCollectionCode = bloggersetBloggerRewardWechatCollectionCode;
	}

	/**
	 * 获取：打赏微信收款码
	 */
	public String getBloggersetBloggerRewardWechatCollectionCode() {
		return bloggersetBloggerRewardWechatCollectionCode;
	}

	/**
	 * 设置：博主设置备注
	 */
	public void setBloggersetRemark(String bloggersetRemark) {
		this.bloggersetRemark = bloggersetRemark;
	}

	/**
	 * 获取：博主设置备注
	 */
	public String getBloggersetRemark() {
		return bloggersetRemark;
	}

	/**
	 * 设置：每页显示文章条数
	 */
	public void setBlogsetPerpageShowNum(Integer blogsetPerpageShowNum) {
		this.blogsetPerpageShowNum = blogsetPerpageShowNum;
	}

	/**
	 * 获取：每页显示文章条数
	 */
	public Integer getBlogsetPerpageShowNum() {
		return blogsetPerpageShowNum;
	}

	/**
	 * 设置：最新文章显示条数
	 */
	public void setBlogsetLatestShowNum(Integer blogsetLatestShowNum) {
		this.blogsetLatestShowNum = blogsetLatestShowNum;
	}

	/**
	 * 获取：最新文章显示条数
	 */
	public Integer getBlogsetLatestShowNum() {
		return blogsetLatestShowNum;
	}

	/**
	 * 设置：推荐文章显示条数
	 */
	public void setBlogsetRecommendedShowNum(Integer blogsetRecommendedShowNum) {
		this.blogsetRecommendedShowNum = blogsetRecommendedShowNum;
	}

	/**
	 * 获取：推荐文章显示条数
	 */
	public Integer getBlogsetRecommendedShowNum() {
		return blogsetRecommendedShowNum;
	}

	/**
	 * 设置：点击排行显示条数
	 */
	public void setBlogsetRankingShowNum(Integer blogsetRankingShowNum) {
		this.blogsetRankingShowNum = blogsetRankingShowNum;
	}

	/**
	 * 获取：点击排行显示条数
	 */
	public Integer getBlogsetRankingShowNum() {
		return blogsetRankingShowNum;
	}

	/**
	 * 设置：特别推荐显示条数
	 */
	public void setBlogsetSpecialRecdShowNum(Integer blogsetSpecialRecdShowNum) {
		this.blogsetSpecialRecdShowNum = blogsetSpecialRecdShowNum;
	}

	/**
	 * 获取：特别推荐显示条数
	 */
	public Integer getBlogsetSpecialRecdShowNum() {
		return blogsetSpecialRecdShowNum;
	}

	/**
	 * 设置：导航菜单显示条数
	 */
	public void setBlogsetNavBarShowNum(Integer blogsetNavBarShowNum) {
		this.blogsetNavBarShowNum = blogsetNavBarShowNum;
	}

	/**
	 * 获取：导航菜单显示条数
	 */
	public Integer getBlogsetNavBarShowNum() {
		return blogsetNavBarShowNum;
	}

	/**
	 * 设置：主文章无图使用默认图片
	 */
	public void setBlogsetNoPicUseDefault(Integer blogsetNoPicUseDefault) {
		this.blogsetNoPicUseDefault = blogsetNoPicUseDefault;
	}

	/**
	 * 获取：主文章无图使用默认图片
	 */
	public Integer getBlogsetNoPicUseDefault() {
		return blogsetNoPicUseDefault;
	}

	/**
	 * 设置：无封面图使用内容图
	 */
	public void setBlogsetNoCoverpicUseContentpic(Integer blogsetNoCoverpicUseContentpic) {
		this.blogsetNoCoverpicUseContentpic = blogsetNoCoverpicUseContentpic;
	}

	/**
	 * 获取：无封面图使用内容图
	 */
	public Integer getBlogsetNoCoverpicUseContentpic() {
		return blogsetNoCoverpicUseContentpic;
	}

	/**
	 * 设置：友情链接
	 */
	public void setBlogsetFriendLinks(String blogsetFriendLinks) {
		this.blogsetFriendLinks = blogsetFriendLinks;
	}

	/**
	 * 获取：友情链接
	 */
	public String getBlogsetFriendLinks() {
		return blogsetFriendLinks;
	}

	/**
	 * 设置：博客设置备注
	 */
	public void setBlogsetRemark(String blogsetRemark) {
		this.blogsetRemark = blogsetRemark;
	}

	/**
	 * 获取：博客设置备注
	 */
	public String getBlogsetRemark() {
		return blogsetRemark;
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

	@Override
	public String toString() {
		return "Blogset [blogSetId=" + blogSetId + ", basicsetTitle=" + basicsetTitle + ", basicsetWebsiteIco="
				+ basicsetWebsiteIco + ", basicsetDes=" + basicsetDes + ", basicsetSkin=" + basicsetSkin
				+ ", basicsetArticleEditor=" + basicsetArticleEditor + ", basicsetWebsiteOnlinetime="
				+ basicsetWebsiteOnlinetime + ", basicsetGlobalAllowComment=" + basicsetGlobalAllowComment
				+ ", basicsetGlobalShowComment=" + basicsetGlobalShowComment + ", basicsetGlobalAllowReprint="
				+ basicsetGlobalAllowReprint + ", basicsetCommentNotice=" + basicsetCommentNotice
				+ ", basicsetOpenAppreciate=" + basicsetOpenAppreciate + ", basicsetOpenBlogDownload="
				+ basicsetOpenBlogDownload + ", basicsetOpenNotice=" + basicsetOpenNotice + ", basicsetAutoLoading="
				+ basicsetAutoLoading + ", basicsetCommentAutoReview=" + basicsetCommentAutoReview
				+ ", basicsetAddWaterMark=" + basicsetAddWaterMark + ", basicsetWaterMarkMsg=" + basicsetWaterMarkMsg
				+ ", basicsetWaterMarkSetMsg=" + basicsetWaterMarkSetMsg + ", basicsetAddCopyrightNotice="
				+ basicsetAddCopyrightNotice + ", basicsetCopyrightNoticeInfo=" + basicsetCopyrightNoticeInfo
				+ ", basicsetRemark=" + basicsetRemark + ", bloggersetBloggerName=" + bloggersetBloggerName
				+ ", bloggersetBloggerDes=" + bloggersetBloggerDes + ", bloggersetBloggerProfile="
				+ bloggersetBloggerProfile + ", bloggersetBloggerSinaWeibo=" + bloggersetBloggerSinaWeibo
				+ ", bloggersetBloggerQqNumber=" + bloggersetBloggerQqNumber + ", bloggersetBloggerQqOrcode="
				+ bloggersetBloggerQqOrcode + ", bloggersetBloggerQqGroupNumber=" + bloggersetBloggerQqGroupNumber
				+ ", bloggersetBloggerQqGroupOrcode=" + bloggersetBloggerQqGroupOrcode
				+ ", bloggersetBloggerWechatNumber=" + bloggersetBloggerWechatNumber
				+ ", bloggersetBloggerWechatOrcode=" + bloggersetBloggerWechatOrcode
				+ ", bloggersetBloggerWechatGroupName=" + bloggersetBloggerWechatGroupName
				+ ", bloggersetBloggerWechatGroupOrcode=" + bloggersetBloggerWechatGroupOrcode
				+ ", bloggersetBloggerEmail=" + bloggersetBloggerEmail + ", bloggersetBloggerCsdn="
				+ bloggersetBloggerCsdn + ", bloggersetBloggerGitee=" + bloggersetBloggerGitee
				+ ", bloggersetBloggerGithub=" + bloggersetBloggerGithub
				+ ", bloggersetBloggerRewardAlipayCollectionCode=" + bloggersetBloggerRewardAlipayCollectionCode
				+ ", bloggersetBloggerRewardWechatCollectionCode=" + bloggersetBloggerRewardWechatCollectionCode
				+ ", bloggersetRemark=" + bloggersetRemark + ", blogsetPerpageShowNum=" + blogsetPerpageShowNum
				+ ", blogsetLatestShowNum=" + blogsetLatestShowNum + ", blogsetScrollRecommendedShowNum="
				+ blogsetScrollRecommendedShowNum + ", blogsetRecommendedShowNum=" + blogsetRecommendedShowNum
				+ ", blogsetRankingShowNum=" + blogsetRankingShowNum + ", blogsetSpecialRecdShowNum="
				+ blogsetSpecialRecdShowNum + ", blogsetNavBarShowNum=" + blogsetNavBarShowNum
				+ ", blogsetNoPicUseDefault=" + blogsetNoPicUseDefault + ", blogsetNoCoverpicUseContentpic="
				+ blogsetNoCoverpicUseContentpic + ", blogsetDefaultPic=" + blogsetDefaultPic + ", blogsetFriendLinks="
				+ blogsetFriendLinks + ", blogsetSidebarOtherMessage=" + blogsetSidebarOtherMessage + ", blogsetRemark="
				+ blogsetRemark + ", createBy=" + createBy + ", createTime=" + createTime + ", updateBy=" + updateBy
				+ ", updateTime=" + updateTime + ", bloggersetBloggerQqOrcodeUuidName="
				+ bloggersetBloggerQqOrcodeUuidName + ", bloggersetBloggerQqGroupOrcodeUuidName="
				+ bloggersetBloggerQqGroupOrcodeUuidName + ", bloggersetBloggerWechatOrcodeUuidName="
				+ bloggersetBloggerWechatOrcodeUuidName + ", bloggersetBloggerWechatGroupOrcodeUuidName="
				+ bloggersetBloggerWechatGroupOrcodeUuidName + ", bloggersetBloggerRewardAlipayCollectionCodeUuidName="
				+ bloggersetBloggerRewardAlipayCollectionCodeUuidName
				+ ", bloggersetBloggerRewardWechatCollectionCodeUuidName="
				+ bloggersetBloggerRewardWechatCollectionCodeUuidName + ", website=" + website + ", blogsiderbarList="
				+ blogsiderbarList + ", stylesetColor=" + stylesetColor + ", stylesetBackColor=" + stylesetBackColor
				+ ", stylesetSmoothStyle=" + stylesetSmoothStyle + ", stylesetShowRightSiderbar="
				+ stylesetShowRightSiderbar + ", stylesetRightSiderbarContent=" + stylesetRightSiderbarContent
				+ ", stylesetShowLeftSiderbar=" + stylesetShowLeftSiderbar + ", stylesetLeftSiderbarContent="
				+ stylesetLeftSiderbarContent + ", stylesetRemark=" + stylesetRemark + "]";
	}

}
