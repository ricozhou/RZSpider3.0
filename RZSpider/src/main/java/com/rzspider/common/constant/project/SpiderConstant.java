package com.rzspider.common.constant.project;

/**
 * 通用爬虫常量信息
 * 
 * @author ricozhou
 */
public class SpiderConstant {
	/**
	 * 通用爬虫常量
	 */
	public static final String SPIDER_JAVA_PUBLIC_DEPENDENCY_FOLDER = "publiclib";
	/**
	 * 通用爬虫常量
	 */
	public static final String SPIDER_PYTHON_PUBLIC_DEPENDENCY_FOLDER = "publiclib";
	/**
	 * 通用爬虫常量
	 */
	public static final String SPIDER_JAVASCRIPT_PUBLIC_DEPENDENCY_FOLDER = "publiclib";
	/**
	 * 通用爬虫常量
	 */
	public static final String SPIDER_PYTHON_DEPENDENCY_PACKAGE_FOLDER = "site-packages";
	/**
	 * 通用爬虫常量
	 */
	public static final String SPIDER_JAVASCRIPT_DEPENDENCY_PACKAGE_FOLDER = "node_modules";
	/**
	 * 通用爬虫常量
	 */
	public static final String SPIDER_EXPORTFILE_DEFAULT_NAME = "spider_file.zip";
	/**
	 * 通用爬虫常量
	 */
	public static final String SPIDER_EXPORTFILE_BACKUPCODE_DEFAULT_NAME = "spider_backupcode.txt";
	/**
	 * 通用爬虫常量
	 */
	public static final String SPIDER_EXPORTPROJECT_DEFAULT_NAME = "spider_src.zip";
	/**
	 * 通用爬虫常量
	 */
	public static final String SPIDER_FILE_JAVA_SRC_NAME = "src";
	/**
	 * 通用爬虫常量
	 */
	public static final String SPIDER_FILE_JAVA_BIN_NAME = "bin";
	/**
	 * 通用爬虫常量
	 */
	public static final String SPIDER_FILE_JAVA_LIB_NAME = "lib";
	/**
	 * 通用爬虫常量
	 */
	public static final String SPIDER_KEYWORD_JAVA_MAIN = "main";
	/**
	 * 通用爬虫常量
	 */
	public static final String SPIDER_KEYWORD_JAVA_VOID = "void";
	/**
	 * 通用爬虫常量
	 */
	public static final String SPIDER_STITCHINGWORD_JAVA_1 = "package ";
	/**
	 * 通用爬虫常量
	 */
	public static final String SPIDER_STITCHINGWORD_JAVA_2 = ";\n\r";
	/**
	 * 通用爬虫常量
	 */
	public static final String SPIDER_STITCHINGWORD_JAVA_3 = "public class ";
	/**
	 * 通用爬虫常量
	 */
	public static final String SPIDER_STITCHINGWORD_JAVA_4 = " {\n\r\n\r}";
	/**
	 * 通用爬虫常量
	 */
	public static final String SPIDER_KEYWORD_PROCESSMAP_PID = "pid";
	/**
	 * 通用爬虫常量
	 */
	public static final String SPIDER_KEYWORD_PROCESSMAP_PROCESS = "proess";
	/**
	 * 通用爬虫常量
	 */
	public static final String SPIDER_KEYWORD_PROCESSMAP_INPUTSTREAMTHREAD = "inputStreamThread";
	/**
	 * 通用爬虫常量
	 */
	public static final String SPIDER_KEYWORD_PROCESSMAP_INPUTSTREAMCONSOLE = "inputStreamConsole";
	/**
	 * 通用爬虫常量
	 */
	public static final String SPIDER_KEYWORD_PROCESSMAP_ERRORSTREAMTHREAD = "errorStreamThread";
	/**
	 * 通用爬虫常量
	 */
	public static final String SPIDER_KEYWORD_PROCESSMAP_ERRORSTREAMCONSOLE = "errorStreamConsole";
	/**
	 * 通用爬虫常量
	 */
	public static final String SPIDER_KEYWORD_PROCESSMAP_RUNSTATUS = "runStatus";
	/**
	 * 通用爬虫常量
	 */
	public static final String SPIDER_KEYWORD_PROCESSMAP_SENDCONSOLETHREAD = "sendConsoleThread";
	/**
	 * 通用爬虫常量
	 */
	public static final String SPIDER_KEYWORD_PROCESSMAP_SENDCONSOLE = "sendConsole";
	/**
	 * 通用爬虫常量
	 */
	public static final String SPIDER_KEYWORD_FILETREE_ID = "id";
	/**
	 * 通用爬虫常量
	 */
	public static final String SPIDER_KEYWORD_FILETREE_PID = "pId";
	/**
	 * 通用爬虫常量
	 */
	public static final String SPIDER_KEYWORD_FILETREE_NAME = "name";
	/**
	 * 通用爬虫常量
	 */
	public static final String SPIDER_KEYWORD_FILETREE_TITLE = "title";
	/**
	 * 通用爬虫常量
	 */
	public static final String SPIDER_KEYWORD_FILETREE_FILEPATH = "filePath";
	/**
	 * 通用爬虫常量
	 */
	public static final String SPIDER_KEYWORD_PYTHON_VENV = "venv";
	/**
	 * 通用爬虫常量
	 */
	public static final String SPIDER_KEYWORD_PYTHON_LIB = "Lib";
	/**
	 * 通用爬虫常量
	 */
	public static final String SPIDER_KEYWORD_PYTHON_SCRIPTS = "Scripts";
	/**
	 * 通用爬虫常量
	 */
	public static final String SPIDER_ENTRYFILE_PYTHON_DEFAULT_NAME = "MainSpider.py";
	/**
	 * 通用爬虫常量
	 */
	public static final String SPIDER_INITFILE_PYTHON_DEFAULT_NAME = "_init_.py";
	/**
	 * 通用爬虫常量
	 */
	public static final String SPIDER_ENTRYFILE_JAVASCRIPT_DEFAULT_NAME = "MainSpider.js";
	/**
	 * 通用爬虫常量
	 */
	public static final String SPIDER_PYTHON_ENV_EXTERNAL_PATH_FILENAME = "externalspider.pth";
	/**
	 * 通用爬虫常量
	 */
	public static final String[] SPIDER_KEYWORD_JAVA_KEYWORDS = { "abstract", "assert", "boolean", "break", "byte",
			"case", "catch", "char", "class", "const", "continue", "default", "do", "double", "else", "enum", "extends",
			"final", "finally", "float", "for", "if", "implements", "import", "instanceof", "int", "interface", "long",
			"native", "new", "package", "private", "protected", "public", "return", "short", "static", "strictfp",
			"super", "switch", "synchronized", "this", "throw", "throws", "transient", "try", "void", "volatile",
			"while", "byValue", "cast", "false", "future", "generic", "inner", "operator", "outer", "rest", "true",
			"var", "goto", "const", "null" };
	/**
	 * 通用爬虫常量
	 */
	public static final String SPIDER_RETURN_MESSAGE_FLAG_1 = "message";
	/**
	 * 通用爬虫常量
	 */
	public static final String SPIDER_RETURN_MESSAGE_FLAG_2 = "isover";

	/**
	 * 通用爬虫常量
	 */
	public static final String SPIDER_KEYWORD_SPIDERMANAGE_ALL = "全部";
	/**
	 * 通用爬虫常量
	 */
	public static final String SPIDER_KEYWORD_SPIDERTASKINFO_DATA_ALL = "all";
	/**
	 * 通用爬虫常量
	 */
	public static final String SPIDER_KEYWORD_SPIDERDATA_ID = "id";
	/**
	 * 通用爬虫常量
	 */
	public static final String SPIDER_KEYWORD_SPIDERDATA_OTHERFORMAT_FIELD = "textformat";
	/**
	 * 通用爬虫常量
	 */
	public static final String SPIDER_KEYWORD_SPIDERDATA_OTHERFORMAT_TITLE = "文本格式";
	/**
	 * 通用爬虫常量
	 */
	public static final String SPIDER_KEYWORD_SPIDERDATA_FILE_PREFIX = "spiderdata_";
	/**
	 * 通用爬虫常量
	 */
	public final static String JOB_NAME = "TASK_SPIDER_";
	/**
	 * 通用爬虫常量
	 */
	public static final String SPIDER_DEFAULT_PYTHON_EXE_PATH = "C:\\Users\\Administrator\\AppData\\Local\\Programs\\Python\\Python36\\python.exe";
}
