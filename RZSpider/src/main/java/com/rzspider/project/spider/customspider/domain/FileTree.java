package com.rzspider.project.spider.customspider.domain;

import com.rzspider.framework.web.domain.Message;

public class FileTree {
	/** 爬虫代码目录 */
	private String spiderCodeTypeFolder;
	public Integer filetreeId;
	public Integer customSpiderId;
	public Integer customSpiderBackId;
	// 子id
	public Integer childId;
	// 父id
	public Integer parentId;
	// 文件名
	public String fileName;
	// 文件路径
	public String filePath;
	// 排序
	public int status;
	// 标志
	public int flag;
	/** 自定义爬虫运行状态 */
	// 0是正在测试运行，1是停止中
	public Integer runStatus;
	/** 爬虫入口文件名 */
	// java不带后缀其它的带后缀
	private String entryFileName;
	// 文件内容
	public String fileContent;
	// 打印返回内容
	public String returnPrintContent;

	public String getEntryFileName() {
		return entryFileName;
	}

	public void setEntryFileName(String entryFileName) {
		this.entryFileName = entryFileName;
	}

	public Integer getRunStatus() {
		return runStatus;
	}

	public void setRunStatus(Integer runStatus) {
		this.runStatus = runStatus;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public Integer getFiletreeId() {
		return filetreeId;
	}

	public void setFiletreeId(Integer filetreeId) {
		this.filetreeId = filetreeId;
	}

	public void setChildId(Integer childId) {
		this.childId = childId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getCustomSpiderId() {
		return customSpiderId;
	}

	public void setCustomSpiderId(Integer customSpiderId) {
		this.customSpiderId = customSpiderId;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getSpiderCodeTypeFolder() {
		return spiderCodeTypeFolder;
	}

	public void setSpiderCodeTypeFolder(String spiderCodeTypeFolder) {
		this.spiderCodeTypeFolder = spiderCodeTypeFolder;
	}

	public FileTree() {

	}

	public FileTree(int childId, int parentId, String fileName, String filePath) {
		this.childId = childId;
		this.parentId = parentId;
		this.fileName = fileName;
		this.filePath = filePath;
	}

	public FileTree(Integer customSpiderBackId) {
		this.customSpiderBackId = customSpiderBackId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getReturnPrintContent() {
		return returnPrintContent;
	}

	public void setReturnPrintContent(String returnPrintContent) {
		this.returnPrintContent = returnPrintContent;
	}

	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

	public Integer getCustomSpiderBackId() {
		return customSpiderBackId;
	}

	public void setCustomSpiderBackId(Integer customSpiderBackId) {
		this.customSpiderBackId = customSpiderBackId;
	}

	public Integer getChildId() {
		return childId;
	}

	public Integer getParentId() {
		return parentId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		return "FileTree [spiderCodeTypeFolder=" + spiderCodeTypeFolder + ", filetreeId=" + filetreeId
				+ ", customSpiderId=" + customSpiderId + ", customSpiderBackId=" + customSpiderBackId + ", childId="
				+ childId + ", parentId=" + parentId + ", fileName=" + fileName + ", filePath=" + filePath + ", status="
				+ status + ", flag=" + flag + ", runStatus=" + runStatus + ", entryFileName=" + entryFileName
				+ ", fileContent=" + fileContent + ", returnPrintContent=" + returnPrintContent + "]";
	}

}
