package com.rzspider.project.blog.blogoverview.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rzspider.project.blog.blogbrowe.service.IBlogbroweService;
import com.rzspider.project.blog.blogbrowe.utils.BlogbroweUtils;
import com.rzspider.project.blog.blogcolumn.service.IBlogcolumnService;
import com.rzspider.project.blog.blogcontent.service.IBlogcontentService;
import com.rzspider.project.blog.blognotice.service.IBlognoticeService;
import com.rzspider.project.blog.blogoverview.domain.Blogoverview;
import com.rzspider.project.blog.blogoverview.domain.CityBroweNum;
import com.rzspider.project.blog.blogoverview.domain.CityMap;
import com.rzspider.project.blog.blogset.domain.Blogset;
import com.rzspider.project.blog.blogset.service.IBlogsetService;
import com.rzspider.project.blog.blogtags.service.IBlogtagsService;

/**
 * 文章内容 服务层实现
 * 
 * @author ricozhou
 * @date 2018-06-12
 */
@Service
public class BlogoverviewServiceImpl implements IBlogoverviewService {
	@Autowired
	private IBlogsetService blogsetService;
	@Autowired
	private IBlogbroweService blogbroweService;
	@Autowired
	private IBlogcontentService blogcontentService;
	@Autowired
	private IBlogtagsService blogtagsService;

	@Autowired
	private IBlogcolumnService blogcolumnService;
	@Autowired
	private IBlognoticeService blognoticeService;

	/**
	 * 查询概览内容信息
	 * 
	 * @param
	 * 
	 * @return
	 */
	@Override
	public Blogoverview selectBlogoverview(Blogoverview blogoverview) {
		// 设置信息
		Blogset blogset = blogsetService.selectSomeOverViewBlogsetById(1);
		// 网站上线时间
		blogoverview.setWebsiteOnlinetime(blogset.getBasicsetWebsiteOnlinetime());

		// 总
		// 总浏览量
		blogoverview.setAllBrowseNum(blogbroweService.selectAllBlogBroweNum());

		// 总点赞数
		blogoverview.setAllLikeNum(blogcontentService.selectAllBlogLikeNum());

		// 总评论数
		blogoverview.setAllCommentNum(0);

		// 总赞助数
		blogoverview.setAllAppreciateNum(0);

		// 总文章数
		blogoverview.setAllArticleNum(blogcontentService.selectAllBlogArticleNum());

		// 总标签数
		blogoverview.setAllTagNum(blogtagsService.selectAllBlogTagsNum());

		// 总专栏数
		blogoverview.setAllColumnNum(blogcolumnService.selectAllBlogColumnNum());

		// 总公告数
		blogoverview.setAllNoticeNum(blognoticeService.selectAllBlogNoticeNum());

		// 日
		// 日浏览量
		blogoverview.setDayBrowseNum(blogbroweService.selectDayBlogBroweNum());
		blogoverview.setDayBrowseNumPer(BlogbroweUtils.getBlogMsgNumPer(blogoverview.getDayBrowseNum(),
				blogbroweService.selectDayBlogBroweNumPre()));

		// 日文章数
		blogoverview.setDayArticleNum(blogcontentService.selectDayBlogArticleNum());
		blogoverview.setDayArticleNumPer(BlogbroweUtils.getBlogMsgNumPer(blogoverview.getDayArticleNum(),
				blogcontentService.selectDayBlogArticleNumPre()));

		// 日评论数
		blogoverview.setDayCommentNum(0);
		blogoverview.setDayCommentNumPer(BlogbroweUtils.getBlogMsgNumPer(blogoverview.getDayCommentNum(), 0));

		// 日赞助数
		blogoverview.setDayAppreciateNum(0);
		blogoverview.setDayAppreciateNumPer(0);

		// 月
		// 月浏览量
		blogoverview.setMonthBrowseNum(blogbroweService.selectMonthBlogBroweNum());
		blogoverview.setMonthBrowseNumPer(BlogbroweUtils.getBlogMsgNumPer(blogoverview.getMonthBrowseNum(),
				blogbroweService.selectMonthBlogBroweNumPre()));

		// 月文章数
		blogoverview.setMonthArticleNum(blogcontentService.selectMonthBlogArticleNum());
		blogoverview.setMonthArticleNumPer(BlogbroweUtils.getBlogMsgNumPer(blogoverview.getMonthArticleNum(),
				blogcontentService.selectMonthBlogArticleNumPre()));

		// 月评论数
		blogoverview.setMonthCommentNum(0);
		blogoverview.setMonthCommentNumPer(BlogbroweUtils.getBlogMsgNumPer(blogoverview.getMonthCommentNum(), 0));

		// 月赞助数
		blogoverview.setMonthAppreciateNum(0);
		blogoverview.setMonthAppreciateNumPer(0);

		// 年
		// 年浏览量
		blogoverview.setYearBrowseNum(blogbroweService.selectYearBlogBroweNum());
		blogoverview.setYearBrowseNumPer(BlogbroweUtils.getBlogMsgNumPer(blogoverview.getYearBrowseNum(),
				blogbroweService.selectYearBlogBroweNumPre()));

		// 年文章数
		blogoverview.setYearArticleNum(blogcontentService.selectYearBlogArticleNum());
		blogoverview.setYearArticleNumPer(BlogbroweUtils.getBlogMsgNumPer(blogoverview.getYearArticleNum(),
				blogcontentService.selectYearBlogArticleNumPre()));

		// 年评论数
		blogoverview.setYearCommentNum(0);
		blogoverview.setYearCommentNumPer(BlogbroweUtils.getBlogMsgNumPer(blogoverview.getYearCommentNum(), 0));

		// 年赞助数
		blogoverview.setYearAppreciateNum(0);
		blogoverview.setYearAppreciateNumPer(0);

		// 流量分布地图
		blogoverview.setCbnList(getBlogBroweNumCitpMapMessagecity());

		// 近一周流量
		blogoverview.setWeekBrowseNumArray(getWeekBrowseNumArray());

		// 近一周评论
		blogoverview.setWeekCommentNumArray(getWeekCommentNumArray());

		// 近一周文章
		blogoverview.setWeekArticleNumArray(getWeekArticleNumArray());

		System.out.println(blogoverview);
		return blogoverview;
	}

	/**
	 * @date Oct 26, 2018 11:24:09 AM
	 * @Desc
	 * @return
	 */
	private Integer[] getWeekBrowseNumArray() {
		Integer[] weekBrowseNumArray = new Integer[7];

		for (int i = 0; i < weekBrowseNumArray.length; i++) {
			weekBrowseNumArray[i] = blogbroweService.selectBlogBroweNumPreByDayNum(weekBrowseNumArray.length - i - 1);
		}

		return weekBrowseNumArray;
	}

	/**
	 * @date Oct 26, 2018 11:24:29 AM
	 * @Desc
	 * @return
	 */
	private Integer[] getWeekCommentNumArray() {
		Integer[] weekCommentNumArray = new Integer[7];

		for (int i = 0; i < weekCommentNumArray.length; i++) {
			weekCommentNumArray[i] = 0;
		}

		return weekCommentNumArray;
	}

	/**
	 * @date Oct 26, 2018 11:24:26 AM
	 * @Desc
	 * @return
	 */
	private Integer[] getWeekArticleNumArray() {
		Integer[] weekArticleNumArray = new Integer[7];

		for (int i = 0; i < weekArticleNumArray.length; i++) {
			weekArticleNumArray[i] = blogcontentService
					.selectBlogArticleNumPreByDayNum(weekArticleNumArray.length - i - 1);
		}

		return weekArticleNumArray;
	}

	/**
	 * @date Oct 26, 2018 10:16:23 AM
	 * @Desc 获取流量分布图
	 * @return
	 */
	private List<CityBroweNum> getBlogBroweNumCitpMapMessagecity() {
		CityMap cityMap = new CityMap();
		List<CityBroweNum> cbnList = new ArrayList<CityBroweNum>();
		CityBroweNum cbn;
		for (String key : cityMap.model.keySet()) {
			cbn = new CityBroweNum();
			cbn.setName(key);
			cbn.setValue(blogbroweService.selectBlogBroweNumByCityName(key));
			cbnList.add(cbn);
		}
		return cbnList;
	}

}
