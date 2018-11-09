package com.rzspider.common.constant;

/**
 * 用户常量信息
 * 
 * @author ricozhou
 */
public class UserConstants {

	/** 正常状态 */
	public static final int NORMAL = 0;

	/** 异常状态 */
	public static final int EXCEPTION = 1;

	/** 用户封禁状态 */
	public static final int USER_BLOCKED = 1;

	/** 角色封禁状态 */
	public static final int ROLE_BLOCKED = 1;

	/** 部门正常状态 */
	public static final int DEPT_NORMAL = 0;

	/**
	 * 用户名长度限制
	 */
	public static final int USERNAME_MIN_LENGTH = 2;
	public static final int USERNAME_MAX_LENGTH = 20;

	/** 登录名称是否唯一的返回结果码 */
	public final static String USER_NAME_UNIQUE = "0";
	public final static String USER_NAME_NOT_UNIQUE = "1";

	/** 手机号码是否唯一的返回结果 */
	public final static String USER_PHONE_UNIQUE = "0";
	public final static String USER_PHONE_NOT_UNIQUE = "1";

	/** e-mail 是否唯一的返回结果 */
	public final static String USER_EMAIL_UNIQUE = "0";
	public final static String USER_EMAIL_NOT_UNIQUE = "1";

	/** 部门名称是否唯一的返回结果码 */
	public final static String DEPT_NAME_UNIQUE = "0";
	public final static String DEPT_NAME_NOT_UNIQUE = "1";

	/** 角色名称是否唯一的返回结果码 */
	public final static String ROLE_NAME_UNIQUE = "0";
	public final static String ROLE_NAME_NOT_UNIQUE = "1";

	/** 爬虫名称是否唯一的返回结果码 */
	public final static String SPIDER_NAME_UNIQUE = "0";
	public final static String SPIDER_NAME_NOT_UNIQUE = "1";

	/** 爬虫任务名称是否唯一的返回结果码 */
	public final static String TASK_NAME_UNIQUE = "0";
	public final static String TASK_NAME_NOT_UNIQUE = "1";

	/** 爬虫后台id是否唯一的返回结果码 */
	public final static String SPIDER_BACK_ID_UNIQUE = "0";
	public final static String SPIDER_BACK_ID_NOT_UNIQUE = "1";

	/** 爬虫代码类型是否唯一的返回结果码 */
	public final static String CUSTOM_SPIDER_TYPE_UNIQUE = "0";
	public final static String CUSTOM_SPIDER_TYPE_NOT_UNIQUE = "1";

	/** 爬虫代码目录是否唯一的返回结果码 */
	public final static String SPIDER_CODE_TYPE_FOLDER_UNIQUE = "0";
	public final static String SPIDER_CODE_TYPE_FOLDER_NOT_UNIQUE = "1";
	/** 工具名称是否唯一的返回结果码 */
	public final static String TOOL_NAME_UNIQUE = "0";
	public final static String TOOL_NAME_NOT_UNIQUE = "1";
	/** 工具后台id是否唯一的返回结果码 */
	public final static String TOOL_BACK_ID_UNIQUE = "0";
	public final static String TOOL_BACK_ID_NOT_UNIQUE = "1";
	/** 工具昵称是否唯一的返回结果码 */
	public final static String TOOL_NICK_NAME_UNIQUE = "0";
	public final static String TOOL_NICK_NAME_NOT_UNIQUE = "1";

	/** 菜单名称是否唯一的返回结果码 */
	public final static String MENU_NAME_UNIQUE = "0";
	public final static String MENU_NAME_NOT_UNIQUE = "1";

	/** 字典类型是否唯一的返回结果码 */
	public final static String DICT_TYPE_UNIQUE = "0";
	public final static String DICT_TYPE_NOT_UNIQUE = "1";

	/** 参数键名是否唯一的返回结果码 */
	public final static String CONFIG_KEY_UNIQUE = "0";
	public final static String CONFIG_KEY_NOT_UNIQUE = "1";

	/** 主题名称是否唯一的返回结果码 */
	public final static String THEME_NAME_UNIQUE = "0";
	public final static String THEME_NAME_NOT_UNIQUE = "1";
	/**
	 * 密码长度限制
	 */
	public static final int PASSWORD_MIN_LENGTH = 5;
	public static final int PASSWORD_MAX_LENGTH = 20;

	/**
	 * 手机号码格式限制
	 */
	public static final String MOBILE_PHONE_NUMBER_PATTERN = "^0{0,1}(13[0-9]|15[0-9]|14[0-9]|18[0-9])[0-9]{8}$";

	/**
	 * 邮箱格式限制
	 */
	public static final String EMAIL_PATTERN = "^((([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+(\\.([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+)*)|((\\x22)((((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(([\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x7f]|\\x21|[\\x23-\\x5b]|[\\x5d-\\x7e]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(\\\\([\\x01-\\x09\\x0b\\x0c\\x0d-\\x7f]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF]))))*(((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(\\x22)))@((([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.)+(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.?";

	/**
	 * 
	 */
	public static final String USER_KEY_ADMIN = "admin";
	/**
	 * 
	 */
	public static final String USER_KEY_TESTADMIN = "testadmin";

	/**
	 * 
	 */
	public static final String USER_KEY_SUPERADMIN = "superadmin";
	/**
	 * 
	 */
	public static final String USER_MESSAGE_TEATADMIN_DISABLED_USE = "测试管理员禁用此功能";

}
