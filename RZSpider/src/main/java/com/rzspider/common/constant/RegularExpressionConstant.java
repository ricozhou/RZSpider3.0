package com.rzspider.common.constant;

/**
 * 通用正则表达式常量信息
 * 
 * @author ricozhou
 */
public class RegularExpressionConstant {
	/**
	 * 通用正则 文件名校验
	 */
	public static final String REGULAR_EXPRESSION_FILENAME_CHECK = "[^\\s\\\\/:\\*\\?\\\"<>\\|](\\x20|[^\\s\\\\/:\\*\\?\\\"<>\\|])*[^\\s\\\\/:\\*\\?\\\"<>\\|\\.]$";
	/**
	 * 通用正则
	 */
	public static final String REGULAR_EXPRESSION_JAVA_CLASSNAME_CHECK = "(public)\\s+(class)\\s+[\\S]+\\s+[{]";
	/**
	 * 通用正则
	 */
	public static final String REGULAR_EXPRESSION_JAVA_MAINMETHOD_CHECK = "(public)\\s+(static)\\s+(void)\\s+[\\S]+\\s*(\\(String\\[])";
	/**
	 * 通用正则
	 */
	public static final String REGULAR_EXPRESSION_CHECK_1 = "[a-zA-Z]+[0-9a-zA-Z_$]*";
	/**
	 * 通用正则所有空格
	 */
	public static final String REGULAR_EXPRESSION_ALLBLANK_CHARACTER = "\\s*";
	/**
	 * 通用正则
	 */
	public static final String REGULAR_EXPRESSION_IMG_LABEL = "<(img)(.*?)(>)";
	/**
	 * 通用正则
	 */
	public static final String REGULAR_EXPRESSION_IMG_SRC_LABEL = "(src)=(\"|\')(/files/(\\w){8}-(\\w){4}-(\\w){4}-(\\w){4}-(\\w){12}\\..*?)(\"|\')";
	/**
	 * 通用正则只能是中文英文和数字
	 */
	public static final String REGULAR_EXPRESSION_ONLY_CNENNUM = "^[\u4e00-\u9fa5_a-zA-Z0-9]+$";
	/**
	 * 通用正则16进制颜色hex
	 */
	public static final String REGULAR_EXPRESSION_HEX = "^#([0-9a-fA-f]{3}|[0-9a-fA-f]{6})$";
}
