package com.rzspider.project.common.checkutils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ricozhou
 * @date Oct 11, 2018 2:29:50 PM
 * @Desc 校验前台传递的字符串
 */
public class CheckStringUtils {
	// 通过正则表达式校验
	public static boolean checkStringByRegularExpression(String string, String reg) {
		if (string == null || reg == null) {
			return false;
		}
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(string);
		if (!m.find()) {
			return false;
		}
		return true;
	}

}
