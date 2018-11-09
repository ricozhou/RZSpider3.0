package com.rzspider.project.commontool.toolrun.domain;

public class CobeImage extends CommonToolEntity {
	// 魔方数量
	public int cobeNum;

	public int getCobeNum() {
		return cobeNum;
	}

	public void setCobeNum(int cobeNum) {
		this.cobeNum = cobeNum;
	}

	@Override
	public String toString() {
		return "CobeImage [cobeNum=" + cobeNum + ", getToolBackId()=" + getToolBackId() + ", getContent()="
				+ getContent() + ", getExportContent()=" + getExportContent() + ", getImageUrl()=" + getImageUrl()
				+ "]";
	}

}
