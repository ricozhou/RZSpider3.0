package com.rzspider.project.blog.blogbrowe.utils;

/**
 * @author ricozhou
 * @date Oct 25, 2018 5:00:23 PM
 * @Desc
 */
public class BlogbroweUtils {

	/**
	 * @date Oct 25, 2018 5:00:47 PM
	 * @Desc
	 * @param dayBrowseNum
	 * @param selectDayBlogBroweNumPre
	 * @return
	 */
	public static int getBlogMsgNumPer(int dayBrowseNum, int selectDayBlogBroweNumPre) {
		if (selectDayBlogBroweNumPre <= 0) {
			if (dayBrowseNum > 0) {
				return dayBrowseNum * 100;
			} else {
				return 0;
			}
		} else {
			return ((dayBrowseNum - selectDayBlogBroweNumPre) * 100) / selectDayBlogBroweNumPre;
		}

	}

}
