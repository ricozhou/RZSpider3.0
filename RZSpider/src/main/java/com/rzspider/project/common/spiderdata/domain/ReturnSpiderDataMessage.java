package com.rzspider.project.common.spiderdata.domain;

import java.util.Arrays;

public class ReturnSpiderDataMessage {
	// 只有三个变量
	// byte格式数据
	public byte[] bytes;
	// 文件格式,0是text文本，1是excel表格，2是csv
	public int fileType;
	// 其他标志
	public int flag;

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public int getFileType() {
		return fileType;
	}

	public void setFileType(int fileType) {
		this.fileType = fileType;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	@Override
	public String toString() {
		return "ReturnSpiderDataMessage [bytes=" + Arrays.toString(bytes) + ", fileType=" + fileType + ", flag=" + flag
				+ "]";
	}

}
