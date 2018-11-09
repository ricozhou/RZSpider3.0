package com.rzspider.project.spider.customspider.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gargoylesoftware.htmlunit.javascript.host.Console;
import com.rzspider.common.constant.CMDConstant;
import com.rzspider.common.constant.CommonSymbolicConstant;
import com.rzspider.common.constant.FileExtensionConstant;
import com.rzspider.common.constant.RegularExpressionConstant;
import com.rzspider.common.constant.UserConstants;
import com.rzspider.common.constant.project.SpiderConstant;
import com.rzspider.common.utils.MapUtils;
import com.rzspider.common.utils.StringUtils;
import com.rzspider.common.utils.SystemInfoUtils;
import com.rzspider.common.utils.security.ShiroUtils;
import com.rzspider.framework.config.FilePathConfig;
import com.rzspider.project.spider.customspider.mapper.CustomspiderMapper;
import com.rzspider.project.spider.customspider.mapper.FileTreeMapper;
import com.rzspider.project.common.file.utilt.FileUtils;
import com.rzspider.project.spider.codeType.domain.CodeType;
import com.rzspider.project.spider.codeType.mapper.CodeTypeMapper;
import com.rzspider.project.spider.codeType.service.ICodeTypeService;
import com.rzspider.project.spider.customspider.domain.Customspider;
import com.rzspider.project.spider.customspider.domain.CustomspiderBackupcode;
import com.rzspider.project.spider.customspider.domain.FileTree;
import com.rzspider.project.spider.customspider.service.ICustomspiderService;
import com.rzspider.project.spider.customspider.utils.BaseCSUtils;
import com.rzspider.project.spider.customspider.utils.CompilerUtils;
import com.rzspider.project.spider.customspider.utils.ConsoleSimulator;
import com.rzspider.project.spider.customspider.utils.RunUtils;
import com.rzspider.project.spider.spidermanage.domain.SpiderList;
import com.rzspider.project.spider.spidermanage.mapper.SpiderListMapper;
import com.rzspider.project.spider.spidermanage.service.ISpiderListService;
import com.rzspider.project.tool.baseset.service.IBasesetService;

/**
 * 自定义爬虫文件操作 服务层实现
 * 
 * @author rico
 * @date 2018-06-01
 */
@Service
public class CSFileServiceImpl implements ICSFileService {
	@Autowired
	private FilePathConfig filePathConfig;
	@Autowired
	private ICustomspiderService customspiderService;
	@Autowired
	private ICodeTypeService codeTypeService;
	@Autowired
	private FileTreeMapper fileTreeMapper;
	@Autowired
	private IFileTreeService fileTreeService;
	// 全部map用于存储测试运行的项目
	public static Map<String, Object> runCSMap = new HashMap<String, Object>();

	// 加载文件树list
	@Override
	public List<FileTree> selectCSFileTreeToList(Integer customSpiderId) {
		// 查询出详细信息
		Customspider customspider = customspiderService.selectCustomspiderById(customSpiderId);
		String spiderCodeTypeFolder = customspider.getSpiderCodeTypeFolder();
		Integer customSpiderBackId = customspider.getCustomSpiderBackId();
		List<FileTree> fileTreeList = new ArrayList<FileTree>();

		// 换一种封装好的方法加载
		// 需要呈现的文件路径
		String showFolder = filePathConfig.getCustomSpiderPath() + File.separator + spiderCodeTypeFolder
				+ File.separator + customSpiderBackId;
		// 注意起始id
		fileTreeList = BaseCSUtils.getFileTree(fileTreeList, new File(showFolder), 1, 0);
		// 需要呈现的文件路径(并列呈现)
		String showFolder2 = CommonSymbolicConstant.EMPTY_STRING;
		if (customspider.getCustomSpiderType() == 0) {
			// java代码
			showFolder2 = filePathConfig.getCustomSpiderPath() + File.separator + spiderCodeTypeFolder + File.separator
					+ SpiderConstant.SPIDER_JAVA_PUBLIC_DEPENDENCY_FOLDER;
		} else if (customspider.getCustomSpiderType() == 1) {
			// python代码
			showFolder2 = filePathConfig.getCustomSpiderPath() + File.separator + spiderCodeTypeFolder + File.separator
					+ SpiderConstant.SPIDER_PYTHON_PUBLIC_DEPENDENCY_FOLDER;
		} else if (customspider.getCustomSpiderType() == 2) {
			// javascript代码
			showFolder2 = filePathConfig.getCustomSpiderPath() + File.separator + spiderCodeTypeFolder + File.separator
					+ SpiderConstant.SPIDER_JAVASCRIPT_PUBLIC_DEPENDENCY_FOLDER;
		} else if (customspider.getCustomSpiderType() == 3) {
			// jar包
			showFolder2 = filePathConfig.getCustomSpiderPath() + File.separator + spiderCodeTypeFolder + File.separator
					+ SpiderConstant.SPIDER_JAVA_PUBLIC_DEPENDENCY_FOLDER;
		}
		fileTreeList = BaseCSUtils.getFileTree(fileTreeList, new File(showFolder2), fileTreeList.size() + 1, 0);
		return fileTreeList;
	}

	// 加载文件树map
	@Override
	public List<Map<String, Object>> selectCSFileTree(Integer customSpiderId) {
		Customspider customspider = customspiderService.selectCustomspiderById(customSpiderId);
		List<FileTree> fileTreeList = selectCSFileTreeToList(customSpiderId);

		// 转换
		List<Map<String, Object>> trees = BaseCSUtils.turnFileToTrees(fileTreeList);
		// 新逻辑，把一个项目的所有的文件树在每次重新加载的时候都更新到数据库，删除项目的时候整体删除
		// 待定,更改为新建，删除，上传，刷新需要更新文件树
		// if (updateFileTreeToDB(fileTreeList,
		// customspider.getCustomSpiderId(),
		// customspider.getCustomSpiderBackId())) {
		// return trees;
		// }
		return trees;
	}

	// 新逻辑，把一个项目的所有的文件树在每次重新加载的时候都更新到数据库，删除项目的时候整体删除
	// 新建，删除，上传，刷新需要更新文件树
	@Override
	public boolean updateFileTreeToDB(List<FileTree> fileTreeList, Integer customSpiderId, Integer customSpiderBackId) {
		// 先清除再插入
		fileTreeService.deleteFileTreeByCustomSpiderId(customSpiderId);
		for (FileTree fileTree : fileTreeList) {
			fileTree.setCustomSpiderId(customSpiderId);
			fileTree.setCustomSpiderBackId(customSpiderBackId);
			fileTreeService.insertFileTree(fileTree);
		}
		return true;
	}

	// 获取文件内容
	@Override
	public FileTree getFileContent(FileTree fileTree) {
		// 查出地址
		// 查询出详细信息
		Customspider customspider = customspiderService
				.selectCustomspiderByCustomSpiderBackId(fileTree.getCustomSpiderBackId());
		String basePath = filePathConfig.getCustomSpiderPath() + File.separator + customspider.getSpiderCodeTypeFolder()
				+ File.separator + fileTree.getCustomSpiderBackId();
		String filePath = CommonSymbolicConstant.EMPTY_STRING;
		if (customspider.getCustomSpiderType() == 0) {
			// java代码
			filePath = basePath + File.separator + SpiderConstant.SPIDER_FILE_JAVA_SRC_NAME + File.separator
					+ StringUtils.getNotNullString(customspider.getSpiderJavaPackagePrefix())
					+ CommonSymbolicConstant.UNDERLINE + fileTree.getCustomSpiderBackId() + File.separator
					+ fileTree.getFileName();
		} else if (customspider.getCustomSpiderType() == 1) {
			// python代码
			filePath = basePath + File.separator
					+ StringUtils.getNotNullString(customspider.getSpiderJavaPackagePrefix())
					+ CommonSymbolicConstant.UNDERLINE + fileTree.getCustomSpiderBackId() + File.separator
					+ fileTree.getFileName();
		} else if (customspider.getCustomSpiderType() == 2) {
			// javascript代码
			filePath = basePath + File.separator
					+ StringUtils.getNotNullString(customspider.getSpiderJavaPackagePrefix())
					+ CommonSymbolicConstant.UNDERLINE + fileTree.getCustomSpiderBackId() + File.separator
					+ fileTree.getFileName();
		}

		String content = BaseCSUtils.getFileToString(filePath);
		if (content == null) {
			return null;
		} else {
			fileTree.setFilePath(filePath);
			// 以上内容以供代码备份
			fileTree.setFileContent(content);
			return fileTree;
		}

	}

	// 保存并编译
	@Override
	public boolean saveFile(FileTree fileTree) {
		// 查出地址
		// 查询出详细信息
		Customspider customspider = customspiderService
				.selectCustomspiderByCustomSpiderBackId(fileTree.getCustomSpiderBackId());
		String basePath = filePathConfig.getCustomSpiderPath() + File.separator + customspider.getSpiderCodeTypeFolder()
				+ File.separator + fileTree.getCustomSpiderBackId();
		fileTree.setSpiderCodeTypeFolder(customspider.getSpiderCodeTypeFolder());
		if (customspider.getCustomSpiderType() == 0) {
			// java代码
			// 保存
			if (!BaseCSUtils.saveJAVACodeFile(fileTree, basePath,
					StringUtils.getNotNullString(customspider.getSpiderJavaPackagePrefix()))) {
				return false;
			}
			// 编译
			if (!CompilerUtils.compilerJAVACodeFile(fileTree, basePath)) {
				return false;
			}
		} else if (customspider.getCustomSpiderType() == 1) {
			// python代码
			if (!BaseCSUtils.setCodeToFile(
					basePath + File.separator + StringUtils.getNotNullString(customspider.getSpiderJavaPackagePrefix())
							+ CommonSymbolicConstant.UNDERLINE + fileTree.getCustomSpiderBackId() + File.separator
							+ fileTree.getFileName(),
					fileTree.getFileContent())) {
				return false;
			}
		} else if (customspider.getCustomSpiderType() == 2) {
			// javascript代码
			if (!BaseCSUtils.setCodeToFile(
					basePath + File.separator + StringUtils.getNotNullString(customspider.getSpiderJavaPackagePrefix())
							+ CommonSymbolicConstant.UNDERLINE + fileTree.getCustomSpiderBackId() + File.separator
							+ fileTree.getFileName(),
					fileTree.getFileContent())) {
				return false;
			}
		}

		return true;
	}

	// 校验类名
	@Override
	public boolean checkClassName(FileTree fileTree) {
		// 正则匹配取出类名,先从文本中找到public修饰的类名(public)\s+(class)\s+[\S]+\s+[{]
		String reg = RegularExpressionConstant.REGULAR_EXPRESSION_JAVA_CLASSNAME_CHECK;
		if (fileTree != null && fileTree.getFileContent() != null) {
			String[] classNames = StringUtils.GetRegResult(reg, fileTree.getFileContent());
			// 只能有一个
			if (classNames != null && classNames.length == 1) {
				// 再判断名字的规则
				String s = classNames[0].replaceAll(CommonSymbolicConstant.SPACE, CommonSymbolicConstant.EMPTY_STRING);
				String className = s.substring(s.indexOf(FileExtensionConstant.FILE_EXTENSION_CODEFILE_CLASS) + 5,
						s.length() - 1);
				if (BaseCSUtils.formatClassName(className)) {
					if ((className + FileExtensionConstant.FILE_EXTENSION_POINT_CODEFILE_JAVA)
							.equals(fileTree.getFileName())) {
						return true;
					}

				}
			}
		}
		return false;
	}

	// 校验类名,并且是否重复
	@Override
	public boolean checkClassNameAndRepeated(FileTree fileTree) {
		String className = fileTree.getFileName();
		// 先判断是否存在
		// 判断是否重复
		if (!BaseCSUtils.containFileFromFolder(fileTree.getFilePath(), className)) {
			return false;
		}
		if (!BaseCSUtils.formatClassName(className)) {
			return false;
		}
		return true;
	}

	// 校验类名,并且是否重复
	@Override
	public boolean checkRepeated(FileTree fileTree) {
		String fileName = fileTree.getFileName();
		// 先判断是否存在
		// 判断是否重复
		if (!BaseCSUtils.containFileFromFolder(fileTree.getFilePath(),
				fileName.substring(0, fileName.lastIndexOf(CommonSymbolicConstant.POINT)))) {
			return false;
		}
		return true;
	}

	// 运行文件
	@Override
	public boolean runJavaCode(FileTree fileTree, String spiderJavaPackagePrefix, String spiderDefaultParams) {
		// 处理参数
		spiderDefaultParams = StringUtils.replaceSpecialCharForCmd(spiderDefaultParams);
		if (CommonSymbolicConstant.EMPTY_STRING.equals(spiderDefaultParams.trim())) {
			// 必须保证有三个参数 ，测试环境，第一个是爬虫参数，第二个是任务详情id，第三个是任务标志
			spiderDefaultParams = CommonSymbolicConstant.BIG_BRACKETS;
		}
		// 任务详情id测试环境默认0
		int taskInfoId = 0;
		// 任务标志测试环境是0
		int taskFlag = 0;
		// 拼接命令
		String basePath = filePathConfig.getCustomSpiderPath() + File.separator + fileTree.getSpiderCodeTypeFolder()
				+ File.separator + fileTree.getCustomSpiderBackId();
		// class所在路径
		String classPath = basePath + File.separator + SpiderConstant.SPIDER_FILE_JAVA_BIN_NAME;
		// class名字
		String className = fileTree.getFileName().substring(0,
				fileTree.getFileName().lastIndexOf(FileExtensionConstant.FILE_EXTENSION_POINT_CODEFILE_JAVA));
		// 获取jar路径字符串
		String jars = CommonSymbolicConstant.EMPTY_STRING;
		try {
			jars = CompilerUtils.getJarFiles(basePath + File.separator + SpiderConstant.SPIDER_FILE_JAVA_LIB_NAME)
					+ CompilerUtils.getJarFiles(
							filePathConfig.getCustomSpiderPath() + File.separator + fileTree.getSpiderCodeTypeFolder()
									+ File.separator + SpiderConstant.SPIDER_JAVA_PUBLIC_DEPENDENCY_FOLDER);
		} catch (Exception e) {
			e.printStackTrace();
			jars = CommonSymbolicConstant.EMPTY_STRING;
		}

		// 开始拼接命令
		String jarCmd = CommonSymbolicConstant.EMPTY_STRING;
		if (CommonSymbolicConstant.EMPTY_STRING.equals(jars)) {
			jarCmd = CommonSymbolicConstant.EMPTY_STRING;
		} else {
			jarCmd = CMDConstant.CMD_COMMAND_JAVA_1 + jars;
		}
		Customspider customspider = new Customspider();
		customspider.setCustomSpiderBackId(fileTree.getCustomSpiderBackId());
		// 运行
		try {
			// jre目录使用绝对目录
			// class文件使用绝对目录
			String jrePath = FilePathConfig.getJreRunPath();
			String jreCmd = CMDConstant.CMD_COMMAND_JAVA_2;
			File file = new File(jrePath);
			if (file.exists()) {
				jreCmd = file.getAbsolutePath() + CMDConstant.CMD_COMMAND_JAVA_3;
			}
			String cmd = jreCmd + CMDConstant.CMD_COMMAND_JAVA_4 + jarCmd + classPath + CommonSymbolicConstant.SPACE
					+ spiderJavaPackagePrefix + CommonSymbolicConstant.UNDERLINE
					+ String.valueOf(fileTree.getCustomSpiderBackId()) + CommonSymbolicConstant.POINT + className
					+ CommonSymbolicConstant.SPACE + spiderDefaultParams + CommonSymbolicConstant.SPACE + taskInfoId
					+ CommonSymbolicConstant.SPACE + taskFlag;
			// 把map信息拼接加入到全局map用于统一管理
			Map<String, Object> messageMap = RunUtils.run(cmd, fileTree.getCustomSpiderBackId());

			runCSMap.put(String.valueOf(fileTree.getCustomSpiderBackId()), messageMap);
			// 更新表
			customspider.setRunStatus(0);
			if (customspiderService.updateCustomspiderRunStatus(customspider) > 0) {
				return true;
			}
			return false;
		} catch (Throwable e1) {
			e1.printStackTrace();
			customspider.setRunStatus(1);
			customspiderService.updateCustomspiderRunStatus(customspider);
			return false;
		}

	}

	// 校验java文件内容
	@Override
	public boolean checkJavaFile(FileTree fileTree, String spiderJavaPackagePrefix) {
		String filePath = filePathConfig.getCustomSpiderPath() + File.separator + fileTree.getSpiderCodeTypeFolder()
				+ File.separator + fileTree.getCustomSpiderBackId() + File.separator
				+ SpiderConstant.SPIDER_FILE_JAVA_SRC_NAME + File.separator + spiderJavaPackagePrefix
				+ CommonSymbolicConstant.UNDERLINE + fileTree.getCustomSpiderBackId() + File.separator
				+ fileTree.getFileName();
		File file = new File(filePath);
		if (!file.exists()) {
			return false;
		}
		// 取出文件的内容
		String fileContent = BaseCSUtils.getFileToString(filePath);
		// 正则匹配main方法
		// 不允许static和void替换顺序
		String reg = RegularExpressionConstant.REGULAR_EXPRESSION_JAVA_MAINMETHOD_CHECK;
		String[] mainFuncNames = StringUtils.GetRegResult(reg, fileContent);
		if (mainFuncNames != null && mainFuncNames.length > 0) {
			// 再判断名字的规则
			// 遍历
			String s = mainFuncNames[0].replaceAll(CommonSymbolicConstant.SPACE, CommonSymbolicConstant.EMPTY_STRING);
			String mainFuncName;
			int count = 0;
			for (int i = 0; i < mainFuncNames.length; i++) {
				mainFuncName = s.substring(s.indexOf(SpiderConstant.SPIDER_KEYWORD_JAVA_VOID) + 4, s.length() - 9);
				if (SpiderConstant.SPIDER_KEYWORD_JAVA_MAIN.equals(mainFuncName)) {
					count++;
				}
			}
			if (count == 1) {
				return true;
			}
		}

		return false;
	}

	// 运行js
	@Override
	public boolean runJSCode(FileTree fileTree, String spiderPythonPackagePrefix, String spiderDefaultParams) {
		// 处理参数
		spiderDefaultParams = StringUtils.replaceSpecialCharForCmd(spiderDefaultParams);
		if (CommonSymbolicConstant.EMPTY_STRING.equals(spiderDefaultParams.trim())) {
			// 必须保证有三个参数 ，测试环境，第一个是爬虫参数，第二个是任务详情id，第三个是任务标志
			spiderDefaultParams = CommonSymbolicConstant.BIG_BRACKETS;
		}
		// 任务详情id测试环境默认0
		int taskInfoId = 0;
		// 任务标志测试环境是0
		int taskFlag = 0;
		// 拼接命令
		String basePath = filePathConfig.getCustomSpiderPath() + File.separator + fileTree.getSpiderCodeTypeFolder()
				+ File.separator + fileTree.getCustomSpiderBackId() + File.separator + spiderPythonPackagePrefix
				+ CommonSymbolicConstant.UNDERLINE + fileTree.getCustomSpiderBackId();
		String cmd = CommonSymbolicConstant.EMPTY_STRING;
		Customspider customspider = new Customspider();
		customspider.setCustomSpiderBackId(fileTree.getCustomSpiderBackId());
		// 运行
		try {
			// nodejs目录使用绝对目录
			// 开始拼接命令
			String nodeJSRunPath = FilePathConfig.getNodeJSRunPath();
			String jscmd = CMDConstant.CMD_COMMAND_NODEJS_1;
			File file = new File(nodeJSRunPath);
			if (file.exists()) {
				jscmd = file.getAbsolutePath() + CMDConstant.CMD_COMMAND_NODEJS_2;
			}
			cmd = jscmd + basePath + File.separator + fileTree.getFileName() + CommonSymbolicConstant.SPACE
					+ spiderDefaultParams + CommonSymbolicConstant.SPACE + taskInfoId + CommonSymbolicConstant.SPACE
					+ taskFlag;
			// 把map信息拼接加入到全局map用于统一管理
			Map<String, Object> messageMap = RunUtils.run(cmd, fileTree.getCustomSpiderBackId());
			runCSMap.put(String.valueOf(fileTree.getCustomSpiderBackId()), messageMap);
			// 更新表
			customspider.setRunStatus(0);
			if (customspiderService.updateCustomspiderRunStatus(customspider) > 0) {
				return true;
			}
			return false;
		} catch (Throwable e1) {
			e1.printStackTrace();
			customspider.setRunStatus(1);
			customspiderService.updateCustomspiderRunStatus(customspider);
			return false;
		}

	}

	// 运行python
	@Override
	public boolean runPythonCode(FileTree fileTree, String spiderPythonPackagePrefix, String spiderDefaultParams) {
		// 处理参数
		spiderDefaultParams = StringUtils.replaceSpecialCharForCmd(spiderDefaultParams);
		if (CommonSymbolicConstant.EMPTY_STRING.equals(spiderDefaultParams.trim())) {
			// 必须保证有三个参数 ，测试环境，第一个是爬虫参数，第二个是任务详情id，第三个是任务标志
			spiderDefaultParams = CommonSymbolicConstant.BIG_BRACKETS;
		}
		// 任务详情id测试环境默认0
		int taskInfoId = 0;
		// 任务标志测试环境是0
		int taskFlag = 0;
		// 拼接命令
		String basePath = filePathConfig.getCustomSpiderPath() + File.separator + fileTree.getSpiderCodeTypeFolder()
				+ File.separator + fileTree.getCustomSpiderBackId() + File.separator + spiderPythonPackagePrefix
				+ CommonSymbolicConstant.UNDERLINE + fileTree.getCustomSpiderBackId();
		String cmd = CommonSymbolicConstant.EMPTY_STRING;
		Customspider customspider = new Customspider();
		customspider.setCustomSpiderBackId(fileTree.getCustomSpiderBackId());
		// 运行
		try {
			// 开始拼接命令
			String pythonRunPath = FilePathConfig.getPythonRunPath();
			String jscmd = CMDConstant.CMD_COMMAND_PYTHON_1;
			File file = new File(pythonRunPath);
			if (file.exists()) {
				jscmd = file.getAbsolutePath() + CMDConstant.CMD_COMMAND_PYTHON_2;
			}
			cmd = jscmd + basePath + File.separator + fileTree.getFileName() + CommonSymbolicConstant.SPACE
					+ spiderDefaultParams + CommonSymbolicConstant.SPACE + taskInfoId + CommonSymbolicConstant.SPACE
					+ taskFlag;
			// 把map信息拼接加入到全局map用于统一管理
			Map<String, Object> messageMap = RunUtils.run(cmd, fileTree.getCustomSpiderBackId());
			runCSMap.put(String.valueOf(fileTree.getCustomSpiderBackId()), messageMap);
			// 更新表
			customspider.setRunStatus(0);
			if (customspiderService.updateCustomspiderRunStatus(customspider) > 0) {
				return true;
			}
			return false;
		} catch (Throwable e1) {
			e1.printStackTrace();
			customspider.setRunStatus(1);
			customspiderService.updateCustomspiderRunStatus(customspider);
			return false;
		}

	}

	// 创建文件java
	@Override
	public boolean newJavaFileSave(FileTree fileTree) {
		// 取出包名
		String packageName = FileUtils.getFileNameFromSlash(fileTree.getFilePath());
		String baseCode = CommonSymbolicConstant.EMPTY_STRING;
		if (packageName != null) {
			baseCode = SpiderConstant.SPIDER_STITCHINGWORD_JAVA_1 + packageName
					+ SpiderConstant.SPIDER_STITCHINGWORD_JAVA_2 + SpiderConstant.SPIDER_STITCHINGWORD_JAVA_3
					+ fileTree.getFileName() + SpiderConstant.SPIDER_STITCHINGWORD_JAVA_4;
		}

		// 创建文件
		if (!BaseCSUtils.setCodeToFile(fileTree.getFilePath() + File.separator + fileTree.getFileName()
				+ FileExtensionConstant.FILE_EXTENSION_POINT_CODEFILE_JAVA, baseCode)) {
			return false;
		}
		// 更新文件树
		if (!updateFileTreeToDB(selectCSFileTreeToList(fileTree.getCustomSpiderId()), fileTree.getCustomSpiderId(),
				fileTree.getCustomSpiderBackId())) {
			return false;
		}
		return true;
	}

	// 创建文件js python
	@Override
	public boolean newJSPYFileSave(FileTree fileTree) {
		// 取出包名
		String baseCode = CommonSymbolicConstant.EMPTY_STRING;
		// 创建文件
		if (!BaseCSUtils.setCodeToFile(fileTree.getFilePath() + File.separator + fileTree.getFileName(), baseCode)) {
			return false;
		}
		// 更新文件树
		if (!updateFileTreeToDB(selectCSFileTreeToList(fileTree.getCustomSpiderId()), fileTree.getCustomSpiderId(),
				fileTree.getCustomSpiderBackId())) {
			return false;
		}
		return true;
	}

	// 删除文件
	@Override
	public boolean deleteFile(FileTree fileTree) {
		if (!BaseCSUtils.deleteAllFile(new File(fileTree.getFilePath()))) {
			return false;
		}
		// 更新文件树
		if (!updateFileTreeToDB(selectCSFileTreeToList(fileTree.getCustomSpiderId()), fileTree.getCustomSpiderId(),
				fileTree.getCustomSpiderBackId())) {
			return false;
		}
		return true;
	}

	// 安装包库
	@Override
	public String[] installPackageSave(FileTree fileTree) {
		String packageFilePath = fileTree.getFilePath();
		// 拼接命令
		String cmd = CommonSymbolicConstant.EMPTY_STRING;
		String[] returnContent = new String[2];
		// 运行
		try {
			// 开始拼接命令
			if (fileTree.getFlag() == 0) {
				cmd = CMDConstant.CMD_COMMAND_PIP_1 + packageFilePath + CommonSymbolicConstant.SPACE
						+ fileTree.getFileName();
				returnContent = RunUtils.run2(cmd, fileTree.getCustomSpiderBackId());
			} else if (fileTree.getFlag() == 1) {
				// js
				cmd = (SystemInfoUtils.isWindows() ? CMDConstant.CMD_COMMAND_PYTHON_WIN_NPM_2
						: CMDConstant.CMD_COMMAND_PYTHON_NPM_2) + fileTree.getFileName();
				returnContent = RunUtils.run3(cmd, fileTree.getCustomSpiderBackId(), new File(packageFilePath));
			}

			// 更新文件树
			if (!updateFileTreeToDB(selectCSFileTreeToList(fileTree.getCustomSpiderId()), fileTree.getCustomSpiderId(),
					fileTree.getCustomSpiderBackId())) {
				return null;
			}
			return returnContent;
		} catch (Throwable e1) {
			e1.printStackTrace();
			return null;
		}
	}

	// 中止程序
	@Override
	public boolean stopCSProcess(FileTree fileTree) {
		// 从全局map中取出对应的爬虫map，再获取pid然后杀掉子进程，最后移除对应的爬虫map
		Map<String, Object> messageMap = (Map<String, Object>) CSFileServiceImpl.runCSMap
				.get(String.valueOf(fileTree.getCustomSpiderBackId()));
		// 没有则返回true，相当于不需要终止
		if (messageMap == null) {
			// 更新表数据,改变状态
			Customspider customspider = new Customspider();
			customspider.setCustomSpiderBackId(fileTree.getCustomSpiderBackId());
			customspider.setRunStatus(1);
			if (customspiderService.updateCustomspiderRunStatus(customspider) > 0) {
				return true;
			}
		}
		// 获取pid
		long pid = (long) messageMap.get(SpiderConstant.SPIDER_KEYWORD_PROCESSMAP_PID);
		// 拼接命令
		String cmd = CMDConstant.CMD_COMMAND_PID_1 + pid;
		if (SystemInfoUtils.isWindows()) {
			cmd = CMDConstant.CMD_COMMAND_WIN_PID_1 + pid + CMDConstant.CMD_COMMAND_WIN_PID_2;
		}
		// 运行命令
		String[] returnContent = RunUtils.run2(cmd, fileTree.getCustomSpiderBackId());
		// 判断是否成功
		// if(){
		//
		// }

		// 更新表数据,改变状态
		Customspider customspider = new Customspider();
		customspider.setCustomSpiderBackId(fileTree.getCustomSpiderBackId());
		customspider.setRunStatus(1);
		if (customspiderService.updateCustomspiderRunStatus(customspider) > 0) {
			// 关闭相关线程
			BaseCSUtils.closeCSRelatedThread(messageMap);
			// 销毁子进程
			Process child = (Process) messageMap.get(SpiderConstant.SPIDER_KEYWORD_PROCESSMAP_PROCESS);
			child.destroy();
			// 删除map中对应数据
			MapUtils.removeObjectFromMap(CSFileServiceImpl.runCSMap, String.valueOf(fileTree.getCustomSpiderBackId()));
			return true;
		}
		return false;
	}

	// 运行jar
	@Override
	public boolean runJAR(FileTree fileTree, String spiderDefaultParams) {
		// 处理参数
		spiderDefaultParams = StringUtils.replaceSpecialCharForCmd(spiderDefaultParams);
		if (CommonSymbolicConstant.EMPTY_STRING.equals(spiderDefaultParams.trim())) {
			// 必须保证有三个参数 ，测试环境，第一个是爬虫参数，第二个是任务详情id，第三个是任务标志
			spiderDefaultParams = CommonSymbolicConstant.BIG_BRACKETS;
		}
		// 任务详情id测试环境默认0
		int taskInfoId = 0;
		// 任务标志测试环境是0
		int taskFlag = 0;
		// 拼接命令
		String basePath = filePathConfig.getCustomSpiderPath() + File.separator + fileTree.getSpiderCodeTypeFolder()
				+ File.separator + fileTree.getCustomSpiderBackId();
		String cmd = CommonSymbolicConstant.EMPTY_STRING;
		Customspider customspider = new Customspider();
		customspider.setCustomSpiderBackId(fileTree.getCustomSpiderBackId());
		String jarPath = basePath + File.separator + fileTree.getFileName();
		// 运行
		try {
			// 开始拼接命令
			String jrePath = FilePathConfig.getJreRunPath();
			String jreCmd = CMDConstant.CMD_COMMAND_JAVA_2;
			File file = new File(jrePath);
			if (file.exists()) {
				jreCmd = file.getAbsolutePath() + CMDConstant.CMD_COMMAND_JAVA_3;
			}
			cmd = jreCmd + CMDConstant.CMD_COMMAND_JAVA_5 + jarPath + CommonSymbolicConstant.SPACE + spiderDefaultParams
					+ CommonSymbolicConstant.SPACE + taskInfoId + CommonSymbolicConstant.SPACE + taskFlag;
			// 把map信息拼接加入到全局map用于统一管理
			Map<String, Object> messageMap = RunUtils.run(cmd, fileTree.getCustomSpiderBackId());
			runCSMap.put(String.valueOf(fileTree.getCustomSpiderBackId()), messageMap);
			// 更新表
			customspider.setRunStatus(0);
			if (customspiderService.updateCustomspiderRunStatus(customspider) > 0) {
				return true;
			}
			return false;
		} catch (Throwable e1) {
			e1.printStackTrace();
			customspider.setRunStatus(1);
			customspiderService.updateCustomspiderRunStatus(customspider);
			return false;
		}

	}

}
