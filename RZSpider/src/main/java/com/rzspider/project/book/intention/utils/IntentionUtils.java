package com.rzspider.project.book.intention.utils;

import com.rzspider.common.constant.CommonSymbolicConstant;
import com.rzspider.common.constant.project.BookConstant;

//通用工具类
public class IntentionUtils {
	// 获取文件名
	public static String getExcelTemplateFileName(String downloadFileNamePrefix) {
		return (downloadFileNamePrefix != null ? (downloadFileNamePrefix + CommonSymbolicConstant.UNDERLINE)
				: CommonSymbolicConstant.EMPTY_STRING) + BookConstant.BOOK_INTENTION_DEFAULT_EXCELTEMPLATE_NAME;
	}

	// 获取导出文件名
	public static String getBookExportFileName(String downloadFileNamePrefix) {
		return (downloadFileNamePrefix != null ? (downloadFileNamePrefix + CommonSymbolicConstant.UNDERLINE)
				: CommonSymbolicConstant.EMPTY_STRING) + BookConstant.BOOK_INTENTION_DEFAULT_BOOKEXPORT_NAME;
	}
}
