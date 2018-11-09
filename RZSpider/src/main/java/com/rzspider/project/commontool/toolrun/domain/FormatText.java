package com.rzspider.project.commontool.toolrun.domain;

public class FormatText extends CommonToolEntity {
	public Integer formatFunction;
	public Integer sqlType;

	public Integer getSqlType() {
		return sqlType;
	}

	public void setSqlType(Integer sqlType) {
		this.sqlType = sqlType;
	}

	public Integer getFormatFunction() {
		return formatFunction;
	}

	public void setFormatFunction(Integer formatFunction) {
		this.formatFunction = formatFunction;
	}

	@Override
	public String toString() {
		return "FormatText [formatFunction=" + formatFunction + ", sqlType=" + sqlType + ", getToolBackId()="
				+ getToolBackId() + ", getContent()=" + getContent() + ", getExportContent()=" + getExportContent()
				+ ", getImageUrl()=" + getImageUrl() + "]";
	}

}
