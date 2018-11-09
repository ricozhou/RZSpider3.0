package com.rzspider.project.commontool.toolrun.domain;

//通用实体类
public class CommonToolEntity {
	// 工具id
	public Integer toolBackId;
	// 输入内容
	public String content;
	// 输出内容
	public String exportContent;
	// 图片地址
	public String imageUrl;

	public Integer getToolBackId() {
		return toolBackId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getExportContent() {
		return exportContent;
	}

	public void setExportContent(String exportContent) {
		this.exportContent = exportContent;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public void setToolBackId(Integer toolBackId) {
		this.toolBackId = toolBackId;
	}

	@Override
	public String toString() {
		return "CommonToolEntity [toolBackId=" + toolBackId + ", content=" + content + ", exportContent="
				+ exportContent + ", imageUrl=" + imageUrl + "]";
	}

}
