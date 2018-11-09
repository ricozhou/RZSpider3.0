package com.rzspider.common.utils;

/**
 * @author ricozhou
 * @date Sep 11, 2018 8:36:49 AM
 * @Desc 判断系统获取服务器信息
 */
public class SystemInfoUtils {
	private static String OS = System.getProperty("os.name").toLowerCase();

	public static boolean isLinux() {
		return OS.indexOf("linux") >= 0;
	}

	public static boolean isWindows() {
		return OS.indexOf("windows") >= 0;
	}
}
