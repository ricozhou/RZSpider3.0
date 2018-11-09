package com.rzspider.project.spider.spidertask.mainwork.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.log.SysoCounter;
import com.rzspider.common.constant.CMDConstant;
import com.rzspider.common.constant.CommonSymbolicConstant;
import com.rzspider.common.constant.project.SpiderConstant;
import com.rzspider.common.utils.MapUtils;
import com.rzspider.common.utils.StringUtils;
import com.rzspider.common.utils.SystemInfoUtils;
import com.rzspider.framework.config.FilePathConfig;
import com.rzspider.project.spider.customspider.domain.Customspider;
import com.rzspider.project.spider.customspider.service.CSFileServiceImpl;
import com.rzspider.project.spider.customspider.utils.BaseCSUtils;
import com.rzspider.project.spider.customspider.utils.CompilerUtils;
import com.rzspider.project.spider.customspider.utils.RunUtils;
import com.rzspider.project.spider.spidertask.mainwork.domain.StartSpiderInfo;
import com.rzspider.project.spider.spidertask.mainwork.runutils.CustomSpiderRunUtils;
import com.rzspider.project.spider.spidertask.mainwork.utils.SpiderTaskThreadUtils;
import com.rzspider.project.spider.spidertask.service.ISpidertaskinfoService;

//正式环境运行自定义爬虫类
@Service
public class CustomspiderRunServiceImpl implements ICustomspiderRunService {
	// 全部map用于存储正式运行的项目
	public static Map<String, Object> runTaskCSMap = new HashMap<String, Object>();
	@Autowired
	private ISpidertaskinfoService spidertaskinfoService;

	// java
	@Override
	public boolean runJavaCode(StartSpiderInfo startSpiderInfo) throws Exception {
		// 处理参数
		String spiderDefaultParams = "";
		if (startSpiderInfo.getSpiderParams() != null) {
			spiderDefaultParams = StringUtils.replaceSpecialCharForCmd(startSpiderInfo.getSpiderParams().trim()
					.replaceAll(CommonSymbolicConstant.SPACE, CommonSymbolicConstant.EMPTY_STRING));
		}

		if (CommonSymbolicConstant.EMPTY_STRING.equals(spiderDefaultParams.trim())) {
			// 必须保证有三个参数 ，第一个是爬虫参数，第二个是任务详情id，第三个是任务标志
			spiderDefaultParams = CommonSymbolicConstant.BIG_BRACKETS;
		}
		// 任务详情id
		Integer taskInfoId = startSpiderInfo.getTaskInfoId();
		// 任务标志正式环境默认1
		int taskFlag = 1;
		// 拼接命令
		String basePath = FilePathConfig.getCustomSpiderPath() + File.separator
				+ startSpiderInfo.getCustomspider().getSpiderCodeTypeFolder() + File.separator
				+ startSpiderInfo.getSpiderBackId();
		// class所在路径
		String classPath = basePath + File.separator + SpiderConstant.SPIDER_FILE_JAVA_BIN_NAME;
		// class名字
		String className = startSpiderInfo.getCustomspider().getEntryFileName();
		// 获取jar路径字符串
		String jars = CommonSymbolicConstant.EMPTY_STRING;
		jars = CompilerUtils.getJarFiles(basePath + File.separator + SpiderConstant.SPIDER_FILE_JAVA_LIB_NAME)
				+ CompilerUtils.getJarFiles(FilePathConfig.getCustomSpiderPath() + File.separator
						+ startSpiderInfo.getCustomspider().getSpiderCodeTypeFolder() + File.separator
						+ SpiderConstant.SPIDER_JAVA_PUBLIC_DEPENDENCY_FOLDER);

		// 开始拼接命令
		String jarCmd = CommonSymbolicConstant.EMPTY_STRING;
		if (CommonSymbolicConstant.EMPTY_STRING.equals(jars)) {
			jarCmd = CommonSymbolicConstant.EMPTY_STRING;
		} else {
			jarCmd = CMDConstant.CMD_COMMAND_JAVA_1 + jars;
		}
		// 运行
		// jre目录使用绝对目录
		// class文件使用绝对目录
		String jrePath = FilePathConfig.getJreRunPath();
		String jreCmd = CMDConstant.CMD_COMMAND_JAVA_2;
		File file = new File(jrePath);
		if (file.exists()) {
			jreCmd = file.getAbsolutePath() + CMDConstant.CMD_COMMAND_JAVA_3;
		}
		String cmd = jreCmd + CMDConstant.CMD_COMMAND_JAVA_4 + jarCmd + classPath + CommonSymbolicConstant.SPACE
				+ startSpiderInfo.getCustomspider().getSpiderJavaPackagePrefix() + CommonSymbolicConstant.UNDERLINE
				+ startSpiderInfo.getSpiderBackId() + CommonSymbolicConstant.POINT + className
				+ CommonSymbolicConstant.SPACE + spiderDefaultParams + CommonSymbolicConstant.SPACE + taskInfoId
				+ CommonSymbolicConstant.SPACE + taskFlag;
		// 把map信息拼接加入到全局map用于统一管理
		Map<String, Object> messageMap = CustomSpiderRunUtils.run(cmd, startSpiderInfo.getTaskInfoId());

		runTaskCSMap.put(String.valueOf(startSpiderInfo.getTaskInfoId()), messageMap);
		return true;
	}

	// python
	@Override
	public boolean runPythonCode(StartSpiderInfo startSpiderInfo) throws IOException {
		// 处理参数
		String spiderDefaultParams = "";
		if (startSpiderInfo.getSpiderParams() != null) {
			spiderDefaultParams = StringUtils.replaceSpecialCharForCmd(startSpiderInfo.getSpiderParams().trim()
					.replaceAll(CommonSymbolicConstant.SPACE, CommonSymbolicConstant.EMPTY_STRING));
		}

		if (CommonSymbolicConstant.EMPTY_STRING.equals(spiderDefaultParams.trim())) {
			// 必须保证有三个参数 ，第一个是爬虫参数，第二个是任务详情id，第三个是任务标志
			spiderDefaultParams = CommonSymbolicConstant.BIG_BRACKETS;
		}
		// 任务详情id
		Integer taskInfoId = startSpiderInfo.getTaskInfoId();
		// 任务标志正式环境默认1
		int taskFlag = 1;

		// 拼接命令
		String basePath = FilePathConfig.getCustomSpiderPath() + File.separator
				+ startSpiderInfo.getCustomspider().getSpiderCodeTypeFolder() + File.separator
				+ startSpiderInfo.getSpiderBackId() + File.separator
				+ startSpiderInfo.getCustomspider().getSpiderJavaPackagePrefix() + CommonSymbolicConstant.UNDERLINE
				+ startSpiderInfo.getSpiderBackId();
		String cmd = CommonSymbolicConstant.EMPTY_STRING;
		// 运行
		// 开始拼接命令
		String pythonRunPath = FilePathConfig.getPythonRunPath();
		String jscmd = CMDConstant.CMD_COMMAND_PYTHON_1;
		File file = new File(pythonRunPath);
		if (file.exists()) {
			jscmd = file.getAbsolutePath() + CMDConstant.CMD_COMMAND_PYTHON_2;
		}
		String entryFileName = startSpiderInfo.getCustomspider().getEntryFileName();
		cmd = jscmd + basePath + File.separator + entryFileName + CommonSymbolicConstant.SPACE + spiderDefaultParams
				+ CommonSymbolicConstant.SPACE + taskInfoId + CommonSymbolicConstant.SPACE + taskFlag;
		// 把map信息拼接加入到全局map用于统一管理
		Map<String, Object> messageMap = CustomSpiderRunUtils.run(cmd, startSpiderInfo.getTaskInfoId());

		runTaskCSMap.put(String.valueOf(startSpiderInfo.getTaskInfoId()), messageMap);
		return true;
	}

	// js
	@Override
	public boolean runJSCode(StartSpiderInfo startSpiderInfo) throws IOException {
		// 处理参数
		String spiderDefaultParams = "";
		if (startSpiderInfo.getSpiderParams() != null) {
			spiderDefaultParams = StringUtils.replaceSpecialCharForCmd(startSpiderInfo.getSpiderParams().trim()
					.replaceAll(CommonSymbolicConstant.SPACE, CommonSymbolicConstant.EMPTY_STRING));
		}

		if (CommonSymbolicConstant.EMPTY_STRING.equals(spiderDefaultParams.trim())) {
			// 必须保证有三个参数 ，第一个是爬虫参数，第二个是任务详情id，第三个是任务标志
			spiderDefaultParams = CommonSymbolicConstant.BIG_BRACKETS;
		}
		// 任务详情id
		Integer taskInfoId = startSpiderInfo.getTaskInfoId();
		// 任务标志正式环境默认1
		int taskFlag = 1;
		// 拼接命令
		String basePath = FilePathConfig.getCustomSpiderPath() + File.separator
				+ startSpiderInfo.getCustomspider().getSpiderCodeTypeFolder() + File.separator
				+ startSpiderInfo.getSpiderBackId() + File.separator
				+ startSpiderInfo.getCustomspider().getSpiderJavaPackagePrefix() + CommonSymbolicConstant.UNDERLINE
				+ startSpiderInfo.getSpiderBackId();
		String cmd = CommonSymbolicConstant.EMPTY_STRING;
		// 运行
		// nodejs目录使用绝对目录
		// 开始拼接命令
		String nodeJSRunPath = FilePathConfig.getNodeJSRunPath();
		String jscmd = CMDConstant.CMD_COMMAND_NODEJS_1;
		File file = new File(nodeJSRunPath);
		if (file.exists()) {
			jscmd = file.getAbsolutePath() + CMDConstant.CMD_COMMAND_NODEJS_2;
		}
		String entryFileName = startSpiderInfo.getCustomspider().getEntryFileName();
		cmd = jscmd + basePath + File.separator + entryFileName + CommonSymbolicConstant.SPACE + spiderDefaultParams
				+ CommonSymbolicConstant.SPACE + taskInfoId + CommonSymbolicConstant.SPACE + taskFlag;
		// 把map信息拼接加入到全局map用于统一管理
		Map<String, Object> messageMap = CustomSpiderRunUtils.run(cmd, startSpiderInfo.getTaskInfoId());

		runTaskCSMap.put(String.valueOf(startSpiderInfo.getTaskInfoId()), messageMap);
		return true;
	}

	// jar
	@Override
	public boolean runJAR(StartSpiderInfo startSpiderInfo) throws IOException {
		// 处理参数
		String spiderDefaultParams = "";
		if (startSpiderInfo.getSpiderParams() != null) {
			spiderDefaultParams = StringUtils.replaceSpecialCharForCmd(startSpiderInfo.getSpiderParams().trim()
					.replaceAll(CommonSymbolicConstant.SPACE, CommonSymbolicConstant.EMPTY_STRING));
		}

		if (CommonSymbolicConstant.EMPTY_STRING.equals(spiderDefaultParams.trim())) {
			// 必须保证有三个参数 ，第一个是爬虫参数，第二个是任务详情id，第三个是任务标志
			spiderDefaultParams = CommonSymbolicConstant.BIG_BRACKETS;
		}
		// 任务详情id
		Integer taskInfoId = startSpiderInfo.getTaskInfoId();
		// 任务标志正式环境默认1
		int taskFlag = 1;
		// 拼接命令
		String basePath = FilePathConfig.getCustomSpiderPath() + File.separator
				+ startSpiderInfo.getCustomspider().getSpiderCodeTypeFolder() + File.separator
				+ startSpiderInfo.getSpiderBackId();
		String cmd = CommonSymbolicConstant.EMPTY_STRING;
		String entryFileName = startSpiderInfo.getCustomspider().getEntryFileName();
		String jarPath = basePath + File.separator + entryFileName;
		// 运行
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
		Map<String, Object> messageMap = CustomSpiderRunUtils.run(cmd, startSpiderInfo.getTaskInfoId());

		runTaskCSMap.put(String.valueOf(startSpiderInfo.getTaskInfoId()), messageMap);
		return true;
	}

	// 中止程序
	@Override
	public boolean stopTaskCSProcess(StartSpiderInfo startSpiderInfo) {
		// 从全局map中取出对应的爬虫map，再获取pid然后杀掉子进程，最后移除对应的爬虫map
		Map<String, Object> messageMap = (Map<String, Object>) CustomspiderRunServiceImpl.runTaskCSMap
				.get(String.valueOf(startSpiderInfo.getTaskInfoId()));
		// 没有则返回true，相当于不需要终止
		if (messageMap == null) {
			// 更新表数据,改变状态
			// 最终执行
			spidertaskinfoService.finallyExection(startSpiderInfo, 1);
			return true;
		}
		// 关闭相关线程
		SpiderTaskThreadUtils.closeTaskCSRelatedThread(messageMap);
		// 获取pid
		long pid = (long) messageMap.get(SpiderConstant.SPIDER_KEYWORD_PROCESSMAP_PID);
		// 拼接命令
		String cmd = CMDConstant.CMD_COMMAND_PID_1 + pid;
		if (SystemInfoUtils.isWindows()) {
			cmd = CMDConstant.CMD_COMMAND_WIN_PID_1 + pid + CMDConstant.CMD_COMMAND_WIN_PID_2;
		}
		// 运行命令
		String[] returnContent = CustomSpiderRunUtils.run2(cmd);
		// 判断是否成功
		// if(){
		//
		// }

		// 销毁子进程
		Process child = (Process) messageMap.get(SpiderConstant.SPIDER_KEYWORD_PROCESSMAP_PROCESS);
		child.destroy();
		// 删除map中对应数据
		MapUtils.removeObjectFromMap(CustomspiderRunServiceImpl.runTaskCSMap,
				String.valueOf(startSpiderInfo.getTaskInfoId()));
		// // 更新表数据,改变状态
		// // 最终执行
		// 在子进程的输出流已经进行了最终操作
		// spidertaskinfoService.finallyExection(startSpiderInfo, 1);
		return true;
	}

}
