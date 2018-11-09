package com.rzspider.project.spider.customspider.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.rzspider.common.constant.CMDConstant;
import com.rzspider.common.constant.CodingConstant;
import com.rzspider.common.constant.CommonSymbolicConstant;
import com.rzspider.common.constant.ConfigConstant;
import com.rzspider.common.constant.FileExtensionConstant;
import com.rzspider.common.constant.RegularExpressionConstant;
import com.rzspider.common.constant.project.SpiderConstant;
import com.rzspider.common.utils.StringUtils;
import com.rzspider.common.utils.SystemInfoUtils;
import com.rzspider.framework.config.FilePathConfig;
import com.rzspider.project.common.file.utilt.FileUtils;
import com.rzspider.project.spider.customspider.domain.Customspider;
import com.rzspider.project.spider.customspider.domain.FileTree;

public class BaseCSUtils {
	public static boolean createCustomSpiderFile(String basePath, Customspider customspider, String spiderPackagePrefix,
			String codeExample) {
		String filePath = basePath + File.separator + customspider.getCustomSpiderBackId();
		File file = new File(filePath);
		// 文件存在则删除所有重新创建
		if (file.exists()) {
			if (!deleteAllFile(file)) {
				return false;
			}
		}

		if (customspider.getCustomSpiderType() == 0) {
			// java代码
			return createJAVACodeCustomSpiderFile(basePath, filePath,
					String.valueOf(customspider.getCustomSpiderBackId()), spiderPackagePrefix, codeExample);
		} else if (customspider.getCustomSpiderType() == 1) {
			// python代码
			return createPYTHONCodeCustomSpiderFile(basePath, filePath,
					String.valueOf(customspider.getCustomSpiderBackId()), spiderPackagePrefix, codeExample);
		} else if (customspider.getCustomSpiderType() == 2) {
			// javascript代码
			return createJAVASCRIPTCodeCustomSpiderFile(basePath, filePath,
					String.valueOf(customspider.getCustomSpiderBackId()), spiderPackagePrefix, codeExample);
		} else if (customspider.getCustomSpiderType() == 3) {
			// jar包
			return createJARFILECustomSpiderFile(basePath, filePath);
		}

		return true;
	}

	// javacode目录创建,初始化
	private static boolean createJAVACodeCustomSpiderFile(String basePath, String filePath, String customSpiderBackId,
			String spiderJavaPackagePrefix, String codeExample) {
		File file = new File(filePath);
		// 重新创建文件夹
		File file2 = new File(filePath + File.separator + SpiderConstant.SPIDER_FILE_JAVA_SRC_NAME + File.separator
				+ spiderJavaPackagePrefix + CommonSymbolicConstant.UNDERLINE + customSpiderBackId);
		File file3 = new File(filePath + File.separator + SpiderConstant.SPIDER_FILE_JAVA_BIN_NAME);
		File file4 = new File(filePath + File.separator + SpiderConstant.SPIDER_FILE_JAVA_LIB_NAME);
		if (!file.mkdirs() || !file2.mkdirs() || !file3.mkdirs() || !file4.mkdirs()) {
			return false;
		}
		// 还需要在项目的同级目录创建一个libs文件夹用以存放公共的jar包文件，可供其他项目一起使用
		File file5 = new File(basePath + File.separator + SpiderConstant.SPIDER_JAVA_PUBLIC_DEPENDENCY_FOLDER);
		if (!file5.exists()) {
			file5.mkdirs();
		}
		// 把必须的java包一并导入
		File file6 = new File(ConfigConstant.CONFIG_JAVACSDEPENDENCYLIB);
		// 把此文件夹下所有的jar复制到publiclib
		FileUtils.copyAllFileToFolder(file6.getAbsolutePath(), file5.getAbsolutePath(), false);

		// 再创建一个示例java文件
		String exampleJava = CommonSymbolicConstant.EMPTY_STRING;
		if (codeExample != null && !CommonSymbolicConstant.EMPTY_STRING.equals(codeExample)) {
			exampleJava = SpiderConstant.SPIDER_STITCHINGWORD_JAVA_1 + spiderJavaPackagePrefix
					+ CommonSymbolicConstant.UNDERLINE + customSpiderBackId + SpiderConstant.SPIDER_STITCHINGWORD_JAVA_2
					+ codeExample;

			// 没有则返回
			String className = getClassNameFromCodeString(codeExample);
			if (className == null) {
				return false;
			}
			// 写入
			if (!setCodeToFile(
					filePath + File.separator + SpiderConstant.SPIDER_FILE_JAVA_SRC_NAME + File.separator
							+ spiderJavaPackagePrefix + CommonSymbolicConstant.UNDERLINE + customSpiderBackId
							+ File.separator + className + FileExtensionConstant.FILE_EXTENSION_POINT_CODEFILE_JAVA,
					exampleJava)) {
				return false;
			}
		}
		return true;
	}

	// python代码,初始化
	private static boolean createPYTHONCodeCustomSpiderFile(String basePath, String filePath, String customSpiderBackId,
			String spiderPythonPackagePrefix, String codeExample) {
		File file = new File(filePath);
		// 重新创建文件夹
		File file2 = new File(filePath + File.separator + spiderPythonPackagePrefix + CommonSymbolicConstant.UNDERLINE
				+ customSpiderBackId);
		File file3 = new File(filePath + File.separator + SpiderConstant.SPIDER_KEYWORD_PYTHON_VENV + File.separator
				+ SpiderConstant.SPIDER_KEYWORD_PYTHON_LIB + File.separator
				+ SpiderConstant.SPIDER_PYTHON_DEPENDENCY_PACKAGE_FOLDER);
		File file4 = new File(filePath + File.separator + SpiderConstant.SPIDER_KEYWORD_PYTHON_VENV + File.separator
				+ SpiderConstant.SPIDER_KEYWORD_PYTHON_SCRIPTS);
		if (!file.mkdirs() || !file2.mkdirs() || !file3.mkdirs() || !file4.mkdirs()) {
			return false;
		}
		// 还需要在项目的同级目录创建一个libs文件夹用以存放公共的包文件，可供其他项目一起使用
		File file5 = new File(basePath + File.separator + SpiderConstant.SPIDER_PYTHON_PUBLIC_DEPENDENCY_FOLDER
				+ File.separator + SpiderConstant.SPIDER_PYTHON_DEPENDENCY_PACKAGE_FOLDER);
		if (!file5.exists()) {
			file5.mkdirs();
		}
		// 先在包里创建一个_init_.py python文件,空内容
		// 写入
		if (!setCodeToFile(
				filePath + File.separator + spiderPythonPackagePrefix + CommonSymbolicConstant.UNDERLINE
						+ customSpiderBackId + File.separator + SpiderConstant.SPIDER_INITFILE_PYTHON_DEFAULT_NAME,
				CommonSymbolicConstant.EMPTY_STRING)) {
			return false;
		}

		// 再创建一个示例python文件
		if (codeExample != null && !CommonSymbolicConstant.EMPTY_STRING.equals(codeExample)) {
			// 写入
			if (!setCodeToFile(
					filePath + File.separator + spiderPythonPackagePrefix + CommonSymbolicConstant.UNDERLINE
							+ customSpiderBackId + File.separator + SpiderConstant.SPIDER_ENTRYFILE_PYTHON_DEFAULT_NAME,
					codeExample)) {
				return false;
			}
		}

		// 需要添加一个文件，用于python自动扫描包路径
		List<String> pathList = new ArrayList<String>();
		pathList.add(filePath + File.separator + SpiderConstant.SPIDER_KEYWORD_PYTHON_VENV + File.separator
				+ SpiderConstant.SPIDER_KEYWORD_PYTHON_LIB + File.separator
				+ SpiderConstant.SPIDER_PYTHON_DEPENDENCY_PACKAGE_FOLDER);
		pathList.add(basePath + File.separator + SpiderConstant.SPIDER_PYTHON_PUBLIC_DEPENDENCY_FOLDER + File.separator
				+ SpiderConstant.SPIDER_PYTHON_DEPENDENCY_PACKAGE_FOLDER);
		if (!addPackagePathToPython(pathList)) {
			return false;
		}

		return true;
	}

	// 需要添加一个文件，用于python自动扫描包路径
	public static boolean addPackagePathToPython(List<String> pathList) {
		String[] returnContent;
		String pythonPath;
		File file;
		String sitePath = null;
		if (SystemInfoUtils.isWindows()) {
			returnContent = RunUtils.run2(CMDConstant.CMD_COMMAND_WIN_PYTHON_3, null);
			pythonPath = returnContent[0];
			if (pythonPath == null) {
				// 没有python则不能创建
				return false;
			}
			file = new File(pythonPath);
			sitePath = file.getParent() + File.separator + SpiderConstant.SPIDER_KEYWORD_PYTHON_LIB + File.separator
					+ SpiderConstant.SPIDER_PYTHON_DEPENDENCY_PACKAGE_FOLDER + File.separator
					+ SpiderConstant.SPIDER_PYTHON_ENV_EXTERNAL_PATH_FILENAME;
		} else if (SystemInfoUtils.isLinux()) {
			// returnContent = RunUtils.run2(CMDConstant.CMD_COMMAND_PYTHON_3,
			// null);
			// sitePath = returnContent[0];
			// if (sitePath == null) {
			// // 没有python则不能创建
			// return false;
			// }
			// 找到site-package路径
		}
		String pathContent = CommonSymbolicConstant.EMPTY_STRING;
		for (String path : pathList) {
			pathContent = pathContent + path + CommonSymbolicConstant.LINEBREAK;
		}
		// 写入,追加
		if (!FileUtils.appendFile(sitePath, pathContent)) {
			return false;
		}
		return true;
	}

	// javascript代码,初始化
	private static boolean createJAVASCRIPTCodeCustomSpiderFile(String basePath, String filePath,
			String customSpiderBackId, String spiderJSPackagePrefix, String codeExample) {
		File file = new File(filePath);
		// 重新创建文件夹
		File file2 = new File(filePath + File.separator + spiderJSPackagePrefix + CommonSymbolicConstant.UNDERLINE
				+ customSpiderBackId);
		if (!file.mkdirs() || !file2.mkdirs()) {
			return false;
		}
		// 还需要在项目的同级目录创建一个publiclib/node_modules文件夹用以存放公共的包文件，可供其他项目一起使用
		// 根据nodejs特性，书写代码引用公共模块导包路径要注意结构
		File file5 = new File(basePath + File.separator + SpiderConstant.SPIDER_JAVASCRIPT_PUBLIC_DEPENDENCY_FOLDER
				+ File.separator + SpiderConstant.SPIDER_JAVASCRIPT_DEPENDENCY_PACKAGE_FOLDER);
		if (!file5.exists()) {
			file5.mkdirs();
		}
		// 再创建一个示例js文件
		if (codeExample != null && !CommonSymbolicConstant.EMPTY_STRING.equals(codeExample)) {
			// 写入
			if (!setCodeToFile(filePath + File.separator + spiderJSPackagePrefix + CommonSymbolicConstant.UNDERLINE
					+ customSpiderBackId + File.separator + SpiderConstant.SPIDER_ENTRYFILE_JAVASCRIPT_DEFAULT_NAME,
					codeExample)) {
				return false;
			}
		}

		// 同时还要创建一个项目下的node_modules和package.json
		File file6 = new File(filePath + File.separator + spiderJSPackagePrefix + CommonSymbolicConstant.UNDERLINE
				+ customSpiderBackId + File.separator + SpiderConstant.SPIDER_JAVASCRIPT_DEPENDENCY_PACKAGE_FOLDER);
		if (!file6.exists()) {
			file6.mkdirs();
		}

		// 执行命令创建package.json
		String[] returnContent = RunUtils.run3(CMDConstant.CMD_COMMAND_PYTHON_NPM_1, null, file2);
		if (!CommonSymbolicConstant.EMPTY_STRING.equals(
				returnContent[1].replaceAll(CommonSymbolicConstant.SPACE, CommonSymbolicConstant.EMPTY_STRING))) {
			return false;
		}
		return true;
	}

	// jar包
	private static boolean createJARFILECustomSpiderFile(String basePath, String filePath) {
		File file = new File(filePath);
		if (!file.mkdirs()) {
			return false;
		}
		// 还需要在项目的同级目录创建一个libs文件夹用以存放公共的jar包文件，可供其他项目一起使用
		File file5 = new File(basePath + File.separator + SpiderConstant.SPIDER_JAVA_PUBLIC_DEPENDENCY_FOLDER);
		if (!file5.exists()) {
			file5.mkdirs();
		}
		// 把必须的java包一并导入
		File file6 = new File(ConfigConstant.CONFIG_JAVACSDEPENDENCYLIB);
		// 把此文件夹下所有的jar复制到publiclib
		FileUtils.copyAllFileToFolder(file6.getAbsolutePath(), file5.getAbsolutePath(), false);
		return true;
	}

	// 删除所有
	public static boolean deleteAllFile(File file) {
		String[] files = file.list();
		if (files != null && files.length > 0) {
			for (String f : files) {
				boolean success = deleteAllFile(new File(file, f));
				if (!success) {
					return false;
				}
			}
		}
		return file.delete();
	}

	// 重命名
	public static boolean reCustomSpiderFileName(String oldFile, String newFile) {
		try {
			new File(oldFile).renameTo(new File(newFile));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// 删除
	public static boolean deleteCustomSpiderFile(String filePath) {
		File file = new File(filePath);
		// 文件删除
		if (file.exists()) {
			if (!deleteAllFile(file)) {
				return false;
			}
		}
		return true;
	}

	// 转换树结构
	public static List<Map<String, Object>> turnFileToTrees(List<FileTree> fileTreeList) {
		List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
		for (FileTree fileTree : fileTreeList) {
			Map<String, Object> fileMap = new HashMap<String, Object>();
			fileMap.put(SpiderConstant.SPIDER_KEYWORD_FILETREE_ID, fileTree.getChildId());
			fileMap.put(SpiderConstant.SPIDER_KEYWORD_FILETREE_PID, fileTree.getParentId());
			fileMap.put(SpiderConstant.SPIDER_KEYWORD_FILETREE_NAME, fileTree.getFileName());
			fileMap.put(SpiderConstant.SPIDER_KEYWORD_FILETREE_TITLE, fileTree.getFileName());
			fileMap.put(SpiderConstant.SPIDER_KEYWORD_FILETREE_FILEPATH, fileTree.getFilePath());
			trees.add(fileMap);
		}
		return trees;
	}

	// 获取所有文件
	public static String[] getAllFiles(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return null;
		}
		return file.list();
	}

	// 读取文件内容
	public static String getFileToString(String filePath) {
		StringBuilder result = new StringBuilder();
		File file = new File(filePath);
		if (!file.exists()) {
			return null;
		}
		// 读取文件内容并返回
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), CodingConstant.CODING_UTF_8));
			String s = null;
			// 整行读取
			while ((s = br.readLine()) != null) {
				result.append(System.lineSeparator() + s);
			}
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 从代码字符串中取出classname
	public static String getClassNameFromCodeString(String codeString) {
		// 取出class name
		String reg = RegularExpressionConstant.REGULAR_EXPRESSION_JAVA_CLASSNAME_CHECK;
		String[] classNames = StringUtils.GetRegResult(reg, codeString);
		// 只能有一个
		String className = null;
		if (classNames != null && classNames.length == 1) {
			// 再判断名字的规则
			String s = classNames[0].replaceAll(CommonSymbolicConstant.SPACE, CommonSymbolicConstant.EMPTY_STRING);
			className = s.substring(s.indexOf(FileExtensionConstant.FILE_EXTENSION_CODEFILE_CLASS) + 5, s.length() - 1);
			if (BaseCSUtils.formatClassName(className)) {
				return className;
			}
		}
		return className;
	}

	// 生成文件
	public static boolean setCodeToFile(String filePath, String code) {
		File f = new File(filePath);
		FileOutputStream fos;
		OutputStreamWriter osw = null;
		try {
			if (!f.exists()) {
				f.createNewFile();
			}
			fos = new FileOutputStream(new File(filePath));
			osw = new OutputStreamWriter(fos, CodingConstant.CODING_UTF_8);
			osw.write(code);
			osw.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				osw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	// 重新保存文件
	public static boolean saveJAVACodeFile(FileTree fileTree, String basePath, String spiderJavaPackagePrefix) {
		String filePath = basePath + File.separator + SpiderConstant.SPIDER_FILE_JAVA_SRC_NAME + File.separator
				+ spiderJavaPackagePrefix + CommonSymbolicConstant.UNDERLINE + fileTree.getCustomSpiderBackId()
				+ File.separator + fileTree.getFileName();
		if (!setCodeToFile(filePath, fileTree.getFileContent())) {
			return false;
		}
		return true;
	}

	// 类名命名规则判断
	public static boolean formatClassName(String className) {
		String classPat = RegularExpressionConstant.REGULAR_EXPRESSION_CHECK_1;
		if (CommonSymbolicConstant.EMPTY_STRING.equals(className)) {
			return false;
		}
		Pattern p = Pattern.compile(classPat);
		Matcher m = p.matcher(className);
		if (!m.find()) {
			return false;
		}
		String[] javaKeyWords = SpiderConstant.SPIDER_KEYWORD_JAVA_KEYWORDS;
		for (int i = 0; i < javaKeyWords.length; i++) {
			if (javaKeyWords[i].equals(className)) {
				return false;
			}
		}
		return true;
	}

	// 校验java文件
	public static boolean checkJavaFile(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			return false;
		}
		// 取出文件的内容
		String fileContent = getFileToString(filePath);
		// 正则匹配main方法

		return false;
	}

	// 判断文件夹是否存在
	public static boolean checkFolderExists(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			if (file.isDirectory()) {
				return true;
			}
		}
		return false;
	}

	// 判断文件是否存在
	public static boolean checkFileExists(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			if (file.isFile()) {
				return true;
			}
		}
		return false;
	}

	// 将一个文件夹下所有的文件存入转变成文件树结构 文件树list，目标文件，子id，父id
	// 此方法起始id分别是，1,0
	public static List<FileTree> getFileTree(List<FileTree> fileTreeList, File file, int childId, int parentId) {
		if (file.exists()) {
			// 添加文件
			fileTreeList.add(new FileTree(childId, parentId, file.getName(), file.getAbsolutePath()));
			childId++;
			// 如果是目录的话
			if (file.isDirectory()) {
				// 遍历所有接下来的子文件
				File[] files = file.listFiles();
				// 把所有的加入
				for (File f : files) {
					getFileTree(fileTreeList, f, fileTreeList.size() + 1, childId - 1);
				}
			}
		}

		return fileTreeList;
	}

	// 判断文件夹是否包含某个文件,类名不包含后缀
	public static boolean containFileFromFolder(String filePath, String className) {
		File file = new File(filePath);
		if (!file.exists()) {
			return false;
		}
		String[] files = file.list();
		for (String fileName : files) {
			if ((fileName.substring(0, (fileName.lastIndexOf(CommonSymbolicConstant.POINT) < 0 ? fileName.length()
					: fileName.lastIndexOf(CommonSymbolicConstant.POINT))).equals(className))) {
				return false;
			}
		}
		return true;
	}

	// 关闭测试运行爬虫相关的线程
	public static void closeCSRelatedThread(Map<String, Object> messageMap) {
		// 使用标识结束
		ConsoleSimulator cs1 = (ConsoleSimulator) messageMap
				.get(SpiderConstant.SPIDER_KEYWORD_PROCESSMAP_INPUTSTREAMCONSOLE);
		ConsoleSimulator cs2 = (ConsoleSimulator) messageMap
				.get(SpiderConstant.SPIDER_KEYWORD_PROCESSMAP_ERRORSTREAMCONSOLE);
		WebSocketConsoleThread wsct = (WebSocketConsoleThread) messageMap
				.get(SpiderConstant.SPIDER_KEYWORD_PROCESSMAP_SENDCONSOLE);
		if (cs1 != null) {
			cs1.isStop = true;
		}
		if (cs2 != null) {
			cs2.isStop = true;
		}
		if (wsct != null) {
			wsct.isStop = true;
		}

	}

}
