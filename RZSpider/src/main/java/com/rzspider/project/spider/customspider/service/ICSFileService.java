package com.rzspider.project.spider.customspider.service;

import com.rzspider.project.spider.customspider.domain.Customspider;
import com.rzspider.project.spider.customspider.domain.CustomspiderBackupcode;
import com.rzspider.project.spider.customspider.domain.FileTree;

import java.util.List;
import java.util.Map;

/**
 * 自定义爬虫文件操作 服务层
 * 
 * @author rico
 * @date 2018-06-01
 */
public interface ICSFileService {
	// 加载文件树
	public List<Map<String, Object>> selectCSFileTree(Integer customSpiderBackId);

	// 获取文件内容
	public FileTree getFileContent(FileTree fileTree);

	// 保存并编译
	public boolean saveFile(FileTree fileTree);

	// 校验类名
	public boolean checkClassName(FileTree fileTree);

	// 校验java文件内容
	public boolean checkJavaFile(FileTree fileTree, String spiderJavaPackagePrefix);

	// 中止
	public boolean stopCSProcess(FileTree fileTree);

	// 运行java代码
	public boolean runJavaCode(FileTree fileTree, String spiderJavaPackagePrefix, String spiderDefaultParams);

	public boolean runPythonCode(FileTree fileTree, String spiderPythonPackagePrefix, String spiderDefaultParams);

	// 运行js代码
	public boolean runJSCode(FileTree fileTree, String spiderPythonPackagePrefix, String spiderDefaultParams);

	List<FileTree> selectCSFileTreeToList(Integer customSpiderId);

	boolean updateFileTreeToDB(List<FileTree> fileTreeList, Integer customSpiderId, Integer customSpiderBackId);

	boolean checkClassNameAndRepeated(FileTree fileTree);

	public boolean newJavaFileSave(FileTree fileTree);

	public boolean deleteFile(FileTree fileTree);

	public boolean checkRepeated(FileTree fileTree);

	public boolean newJSPYFileSave(FileTree fileTree);

	public String[] installPackageSave(FileTree fileTree);

	public boolean runJAR(FileTree fileTree, String spiderDefaultParams);

}
