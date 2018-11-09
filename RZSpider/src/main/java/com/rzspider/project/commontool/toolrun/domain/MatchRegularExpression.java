package com.rzspider.project.commontool.toolrun.domain;

public class MatchRegularExpression extends CommonToolEntity {
	public String regularExpression;

	public String getRegularExpression() {
		return regularExpression;
	}

	public void setRegularExpression(String regularExpression) {
		this.regularExpression = regularExpression;
	}

	@Override
	public String toString() {
		return "MatchRegularExpression [regularExpression=" + regularExpression + ", getToolBackId()=" + getToolBackId()
				+ ", getContent()=" + getContent() + ", getExportContent()=" + getExportContent() + ", getImageUrl()="
				+ getImageUrl() + "]";
	}

}
