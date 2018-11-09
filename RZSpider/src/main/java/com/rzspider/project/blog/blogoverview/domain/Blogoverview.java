package com.rzspider.project.blog.blogoverview.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.rzspider.framework.web.domain.BaseEntity;

/**
 * @author ricozhou
 * @date Sep 15, 2018 11:07:10 AM
 * @Desc
 */
public class Blogoverview extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 博客概览id */
	private Integer blogOverviewId;

	/** 上线时间 */
	private String websiteOnlinetime;

	/** 总浏览量 */
	private Integer allBrowseNum;

	/** 总点赞数 */
	private Integer allLikeNum;

	/** 总评论数 */
	private Integer allCommentNum;

	/** 总赞助数 */
	private Integer allAppreciateNum;

	/** 总文章数 */
	private Integer allArticleNum;

	/** 总标签数 */
	private Integer allTagNum;

	/** 总专栏 */
	private Integer allColumnNum;

	/** 总公告数 */
	private Integer allNoticeNum;

	/** 日浏览量 */
	private Integer dayBrowseNum;

	/** 日文章数 */
	private Integer dayArticleNum;

	/** 日评论数 */
	private Integer dayCommentNum;

	/** 日赞助数 */
	private Integer dayAppreciateNum;

	/** 月浏览量 */
	private Integer monthBrowseNum;

	/** 月文章数 */
	private Integer monthArticleNum;

	/** 月评论数 */
	private Integer monthCommentNum;

	/** 月赞助数 */
	private Integer monthAppreciateNum;

	/** 年浏览量 */
	private Integer yearBrowseNum;

	/** 年文章数 */
	private Integer yearArticleNum;

	/** 年评论数 */
	private Integer yearCommentNum;

	/** 年赞助数 */
	private Integer yearAppreciateNum;

	// 变化百分比
	/** 日浏览量 */
	private Integer dayBrowseNumPer;

	/** 日文章数 */
	private Integer dayArticleNumPer;

	/** 日评论数 */
	private Integer dayCommentNumPer;

	/** 日赞助数 */
	private Integer dayAppreciateNumPer;

	/** 月浏览量 */
	private Integer monthBrowseNumPer;

	/** 月文章数 */
	private Integer monthArticleNumPer;

	/** 月评论数 */
	private Integer monthCommentNumPer;

	/** 月赞助数 */
	private Integer monthAppreciateNumPer;

	/** 年浏览量 */
	private Integer yearBrowseNumPer;

	/** 年文章数 */
	private Integer yearArticleNumPer;

	/** 年评论数 */
	private Integer yearCommentNumPer;

	/** 年赞助数 */
	private Integer yearAppreciateNumPer;

	/** 流量分布图 */
	private List<CityBroweNum> cbnList = new ArrayList<CityBroweNum>();

	/** 近一周流量 */
	private Integer[] weekBrowseNumArray;

	/** 近一周评论 */
	private Integer[] weekCommentNumArray;

	/** 近一周文章 */
	private Integer[] weekArticleNumArray;

	public Integer[] getWeekBrowseNumArray() {
		return weekBrowseNumArray;
	}

	public void setWeekBrowseNumArray(Integer[] weekBrowseNumArray) {
		this.weekBrowseNumArray = weekBrowseNumArray;
	}

	public Integer[] getWeekCommentNumArray() {
		return weekCommentNumArray;
	}

	public void setWeekCommentNumArray(Integer[] weekCommentNumArray) {
		this.weekCommentNumArray = weekCommentNumArray;
	}

	public Integer[] getWeekArticleNumArray() {
		return weekArticleNumArray;
	}

	public void setWeekArticleNumArray(Integer[] weekArticleNumArray) {
		this.weekArticleNumArray = weekArticleNumArray;
	}

	public List<CityBroweNum> getCbnList() {
		return cbnList;
	}

	public void setCbnList(List<CityBroweNum> cbnList) {
		this.cbnList = cbnList;
	}

	public Integer getBlogOverviewId() {
		return blogOverviewId;
	}

	public void setBlogOverviewId(Integer blogOverviewId) {
		this.blogOverviewId = blogOverviewId;
	}

	public String getWebsiteOnlinetime() {
		return websiteOnlinetime;
	}

	public void setWebsiteOnlinetime(String websiteOnlinetime) {
		this.websiteOnlinetime = websiteOnlinetime;
	}

	public Integer getAllBrowseNum() {
		return allBrowseNum;
	}

	public void setAllBrowseNum(Integer allBrowseNum) {
		this.allBrowseNum = allBrowseNum;
	}

	public Integer getAllLikeNum() {
		return allLikeNum;
	}

	public void setAllLikeNum(Integer allLikeNum) {
		this.allLikeNum = allLikeNum;
	}

	public Integer getAllCommentNum() {
		return allCommentNum;
	}

	public void setAllCommentNum(Integer allCommentNum) {
		this.allCommentNum = allCommentNum;
	}

	public Integer getAllAppreciateNum() {
		return allAppreciateNum;
	}

	public void setAllAppreciateNum(Integer allAppreciateNum) {
		this.allAppreciateNum = allAppreciateNum;
	}

	public Integer getAllArticleNum() {
		return allArticleNum;
	}

	public void setAllArticleNum(Integer allArticleNum) {
		this.allArticleNum = allArticleNum;
	}

	public Integer getAllTagNum() {
		return allTagNum;
	}

	public void setAllTagNum(Integer allTagNum) {
		this.allTagNum = allTagNum;
	}

	public Integer getAllColumnNum() {
		return allColumnNum;
	}

	public void setAllColumnNum(Integer allColumnNum) {
		this.allColumnNum = allColumnNum;
	}

	public Integer getAllNoticeNum() {
		return allNoticeNum;
	}

	public void setAllNoticeNum(Integer allNoticeNum) {
		this.allNoticeNum = allNoticeNum;
	}

	public Integer getDayBrowseNum() {
		return dayBrowseNum;
	}

	public void setDayBrowseNum(Integer dayBrowseNum) {
		this.dayBrowseNum = dayBrowseNum;
	}

	public Integer getDayCommentNum() {
		return dayCommentNum;
	}

	public void setDayCommentNum(Integer dayCommentNum) {
		this.dayCommentNum = dayCommentNum;
	}

	public Integer getDayAppreciateNum() {
		return dayAppreciateNum;
	}

	public void setDayAppreciateNum(Integer dayAppreciateNum) {
		this.dayAppreciateNum = dayAppreciateNum;
	}

	public Integer getMonthBrowseNum() {
		return monthBrowseNum;
	}

	public void setMonthBrowseNum(Integer monthBrowseNum) {
		this.monthBrowseNum = monthBrowseNum;
	}

	public Integer getMonthCommentNum() {
		return monthCommentNum;
	}

	public void setMonthCommentNum(Integer monthCommentNum) {
		this.monthCommentNum = monthCommentNum;
	}

	public Integer getMonthAppreciateNum() {
		return monthAppreciateNum;
	}

	public void setMonthAppreciateNum(Integer monthAppreciateNum) {
		this.monthAppreciateNum = monthAppreciateNum;
	}

	public Integer getYearBrowseNum() {
		return yearBrowseNum;
	}

	public void setYearBrowseNum(Integer yearBrowseNum) {
		this.yearBrowseNum = yearBrowseNum;
	}

	public Integer getYearCommentNum() {
		return yearCommentNum;
	}

	public void setYearCommentNum(Integer yearCommentNum) {
		this.yearCommentNum = yearCommentNum;
	}

	public Integer getYearAppreciateNum() {
		return yearAppreciateNum;
	}

	public void setYearAppreciateNum(Integer yearAppreciateNum) {
		this.yearAppreciateNum = yearAppreciateNum;
	}

	public Integer getDayArticleNum() {
		return dayArticleNum;
	}

	public void setDayArticleNum(Integer dayArticleNum) {
		this.dayArticleNum = dayArticleNum;
	}

	public Integer getMonthArticleNum() {
		return monthArticleNum;
	}

	public void setMonthArticleNum(Integer monthArticleNum) {
		this.monthArticleNum = monthArticleNum;
	}

	public Integer getYearArticleNum() {
		return yearArticleNum;
	}

	public void setYearArticleNum(Integer yearArticleNum) {
		this.yearArticleNum = yearArticleNum;
	}

	public Integer getDayBrowseNumPer() {
		return dayBrowseNumPer;
	}

	public void setDayBrowseNumPer(Integer dayBrowseNumPer) {
		this.dayBrowseNumPer = dayBrowseNumPer;
	}

	public Integer getDayArticleNumPer() {
		return dayArticleNumPer;
	}

	public void setDayArticleNumPer(Integer dayArticleNumPer) {
		this.dayArticleNumPer = dayArticleNumPer;
	}

	public Integer getDayCommentNumPer() {
		return dayCommentNumPer;
	}

	public void setDayCommentNumPer(Integer dayCommentNumPer) {
		this.dayCommentNumPer = dayCommentNumPer;
	}

	public Integer getDayAppreciateNumPer() {
		return dayAppreciateNumPer;
	}

	public void setDayAppreciateNumPer(Integer dayAppreciateNumPer) {
		this.dayAppreciateNumPer = dayAppreciateNumPer;
	}

	public Integer getMonthBrowseNumPer() {
		return monthBrowseNumPer;
	}

	public void setMonthBrowseNumPer(Integer monthBrowseNumPer) {
		this.monthBrowseNumPer = monthBrowseNumPer;
	}

	public Integer getMonthArticleNumPer() {
		return monthArticleNumPer;
	}

	public void setMonthArticleNumPer(Integer monthArticleNumPer) {
		this.monthArticleNumPer = monthArticleNumPer;
	}

	public Integer getMonthCommentNumPer() {
		return monthCommentNumPer;
	}

	public void setMonthCommentNumPer(Integer monthCommentNumPer) {
		this.monthCommentNumPer = monthCommentNumPer;
	}

	public Integer getMonthAppreciateNumPer() {
		return monthAppreciateNumPer;
	}

	public void setMonthAppreciateNumPer(Integer monthAppreciateNumPer) {
		this.monthAppreciateNumPer = monthAppreciateNumPer;
	}

	public Integer getYearBrowseNumPer() {
		return yearBrowseNumPer;
	}

	public void setYearBrowseNumPer(Integer yearBrowseNumPer) {
		this.yearBrowseNumPer = yearBrowseNumPer;
	}

	public Integer getYearArticleNumPer() {
		return yearArticleNumPer;
	}

	public void setYearArticleNumPer(Integer yearArticleNumPer) {
		this.yearArticleNumPer = yearArticleNumPer;
	}

	public Integer getYearCommentNumPer() {
		return yearCommentNumPer;
	}

	public void setYearCommentNumPer(Integer yearCommentNumPer) {
		this.yearCommentNumPer = yearCommentNumPer;
	}

	public Integer getYearAppreciateNumPer() {
		return yearAppreciateNumPer;
	}

	public void setYearAppreciateNumPer(Integer yearAppreciateNumPer) {
		this.yearAppreciateNumPer = yearAppreciateNumPer;
	}

	@Override
	public String toString() {
		return "Blogoverview [blogOverviewId=" + blogOverviewId + ", websiteOnlinetime=" + websiteOnlinetime
				+ ", allBrowseNum=" + allBrowseNum + ", allLikeNum=" + allLikeNum + ", allCommentNum=" + allCommentNum
				+ ", allAppreciateNum=" + allAppreciateNum + ", allArticleNum=" + allArticleNum + ", allTagNum="
				+ allTagNum + ", allColumnNum=" + allColumnNum + ", allNoticeNum=" + allNoticeNum + ", dayBrowseNum="
				+ dayBrowseNum + ", dayArticleNum=" + dayArticleNum + ", dayCommentNum=" + dayCommentNum
				+ ", dayAppreciateNum=" + dayAppreciateNum + ", monthBrowseNum=" + monthBrowseNum + ", monthArticleNum="
				+ monthArticleNum + ", monthCommentNum=" + monthCommentNum + ", monthAppreciateNum="
				+ monthAppreciateNum + ", yearBrowseNum=" + yearBrowseNum + ", yearArticleNum=" + yearArticleNum
				+ ", yearCommentNum=" + yearCommentNum + ", yearAppreciateNum=" + yearAppreciateNum
				+ ", dayBrowseNumPer=" + dayBrowseNumPer + ", dayArticleNumPer=" + dayArticleNumPer
				+ ", dayCommentNumPer=" + dayCommentNumPer + ", dayAppreciateNumPer=" + dayAppreciateNumPer
				+ ", monthBrowseNumPer=" + monthBrowseNumPer + ", monthArticleNumPer=" + monthArticleNumPer
				+ ", monthCommentNumPer=" + monthCommentNumPer + ", monthAppreciateNumPer=" + monthAppreciateNumPer
				+ ", yearBrowseNumPer=" + yearBrowseNumPer + ", yearArticleNumPer=" + yearArticleNumPer
				+ ", yearCommentNumPer=" + yearCommentNumPer + ", yearAppreciateNumPer=" + yearAppreciateNumPer
				+ ", cbnList=" + cbnList + ", weekBrowseNumArray=" + Arrays.toString(weekBrowseNumArray)
				+ ", weekCommentNumArray=" + Arrays.toString(weekCommentNumArray) + ", weekArticleNumArray="
				+ Arrays.toString(weekArticleNumArray) + "]";
	}

}
