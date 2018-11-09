package com.rzspider.common.utils;

import java.util.Iterator;
import java.util.Map;

import com.rzspider.project.spider.spidertask.service.SpidertaskServiceImpl;

public class MapUtils {

	// 从map中去除某个元素
	public static void removeObjectFromMap(Map map, Object key) {
		// 获取迭代器
		Iterator it = map.keySet().iterator();
		while (it.hasNext()) {
			Object key2 = it.next();
			if (key2.equals(key)) {
				it.remove();
			}
		}
	}

}
