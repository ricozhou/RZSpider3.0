package com.rzspider.project.book.bookmanage.utils;

import com.rzspider.common.constant.CommonSymbolicConstant;
import com.rzspider.common.constant.project.BookConstant;

//通用工具类
public class BookmanageUtils {
	// 获取文件名
	public static String getExcelTemplateFileName(String downloadFileNamePrefix) {
		return (downloadFileNamePrefix != null ? (downloadFileNamePrefix + CommonSymbolicConstant.UNDERLINE)
				: CommonSymbolicConstant.EMPTY_STRING) + BookConstant.BOOK_DEFAULT_EXCELTEMPLATE_NAME;
	}

	// 获取导出文件名
	public static String getBookExportFileName(String downloadFileNamePrefix) {
		return (downloadFileNamePrefix != null ? (downloadFileNamePrefix + CommonSymbolicConstant.UNDERLINE)
				: CommonSymbolicConstant.EMPTY_STRING) + BookConstant.BOOK_DEFAULT_BOOKEXPORT_NAME;
	}
}
