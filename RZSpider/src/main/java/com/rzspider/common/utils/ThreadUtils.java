package com.rzspider.common.utils;

import java.util.Iterator;
import java.util.Map;

/**
 * @author ricozhou
 * @date Oct 22, 2018 10:09:40 AM
 * @Desc
 */
public class ThreadUtils {
	// 从map中止线程
	public static void stopThreadFromMap(Map map, Object key) {
		Thread t = (Thread) map.get(key);
		if (t != null) {
			t.stop();
		}
	}
}
