package com.rzspider.common.constant;

import java.io.File;

/**
 * 通用cmd常量信息
 * 
 * @author ricozhou
 */
public class CMDConstant {
	/**
	 * 通用CMD
	 */
	public static final String CMD_COMMAND_JAVA_1 = ",;";
	/**
	 * 通用CMD
	 */
	public static final String CMD_COMMAND_JAVA_2 = "java ";
	/**
	 * 通用CMD
	 */
	public static final String CMD_COMMAND_JAVA_3 = File.separator + "bin" + File.separator + "java ";
	/**
	 * 通用CMD
	 */
	public static final String CMD_COMMAND_JAVA_4 = "-cp ";
	/**
	 * 通用CMD
	 */
	public static final String CMD_COMMAND_JAVA_5 = "-jar ";
	/**
	 * 通用CMD
	 */
	public static final String CMD_COMMAND_NODEJS_1 = "node ";
	/**
	 * 通用CMD
	 */
	public static final String CMD_COMMAND_NODEJS_2 = File.separator + "node ";
	/**
	 * 通用CMD
	 */
	public static final String CMD_COMMAND_PYTHON_1 = "python ";
	/**
	 * 通用CMD
	 */
	public static final String CMD_COMMAND_PYTHON_2 = File.separator + "python ";
	/**
	 * 通用CMD
	 */
	public static final String CMD_COMMAND_WIN_PYTHON_3 = "where python";
	/**
	 * 通用CMD
	 */
//	public static final String CMD_COMMAND_PYTHON_3 = "python -c \"from\" \"distutils.sysconfig\" \"import\" \"get_python_lib; print get_python_lib()\"";

	/**
	 * 通用CMD
	 */
	public static final String CMD_COMMAND_PYTHON_WIN_NPM_1 = "npm.cmd init -y";
	/**
	 * 通用CMD
	 */
	public static final String CMD_COMMAND_PYTHON_NPM_1 = "npm init -y";
	/**
	 * 通用CMD
	 */
	public static final String CMD_COMMAND_PYTHON_WIN_NPM_2 = "npm.cmd install ";
	/**
	 * 通用CMD
	 */
	public static final String CMD_COMMAND_PYTHON_NPM_2 = "npm install ";
	/**
	 * 通用CMD
	 */
	public static final String CMD_COMMAND_PIP_1 = "pip install --target=";
	/**
	 * 通用CMD
	 */
	public static final String CMD_COMMAND_WIN_PID_1 = "taskkill /PID ";
	/**
	 * 通用CMD
	 */
	public static final String CMD_COMMAND_PID_1 = "kill -9 ";
	/**
	 * 通用CMD
	 */
	public static final String CMD_COMMAND_WIN_PID_2 = " /F";
	/**
	 * 通用CMD
	 */
	public static final String CMD_COMMAND_JAVACOMPILE_1 = "-encoding";
	/**
	 * 通用CMD
	 */
	public static final String CMD_COMMAND_JAVACOMPILE_2 = "-classpath";
	/**
	 * 通用CMD
	 */
	public static final String CMD_COMMAND_JAVACOMPILE_3 = "-d";
	/**
	 * 通用CMD
	 */
	public static final String CMD_COMMAND_JAVACOMPILE_4 = "-sourcepath";
}
