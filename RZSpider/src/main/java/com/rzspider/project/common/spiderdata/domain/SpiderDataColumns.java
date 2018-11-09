package com.rzspider.project.common.spiderdata.domain;

//前端显示column
public class SpiderDataColumns {
	// 字段
	public String field;
	// 显示
	public String title;
	// 居中
	public String align;

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "SpiderDataColumns [field=" + field + ", title=" + title + ", align=" + align + "]";
	}

}
