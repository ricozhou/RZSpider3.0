package com.rzspider.implementspider.landmarketnetwork.domain;

public class LMNLandParams {
	// 网站选择
	public int websiteId;
	public String startPage;
	public String endPage;
	// 时间条件最小值
	public String startDateTime;
	// 时间最大值
	public String endDateTime;
	// 价格最小值
	public String startPrice;
	// 价格最大值
	public String endPrice;
	// 土地面积最小值
	public String startArea;
	// 土地面积最大值
	public String endArea;
	// 是否爬取详情
	public boolean getDetails;
	// 土地用途
	public String landUse;

	public int getWebsiteId() {
		return websiteId;
	}

	public void setWebsiteId(int websiteId) {
		this.websiteId = websiteId;
	}

	public String getStartPage() {
		return startPage;
	}

	public void setStartPage(String startPage) {
		this.startPage = startPage;
	}

	public String getEndPage() {
		return endPage;
	}

	public void setEndPage(String endPage) {
		this.endPage = endPage;
	}

	public String getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}

	public String getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(String endDateTime) {
		this.endDateTime = endDateTime;
	}

	public String getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(String startPrice) {
		this.startPrice = startPrice;
	}

	public String getEndPrice() {
		return endPrice;
	}

	public void setEndPrice(String endPrice) {
		this.endPrice = endPrice;
	}

	public String getStartArea() {
		return startArea;
	}

	public void setStartArea(String startArea) {
		this.startArea = startArea;
	}

	public String getEndArea() {
		return endArea;
	}

	public void setEndArea(String endArea) {
		this.endArea = endArea;
	}

	public boolean isGetDetails() {
		return getDetails;
	}

	public void setGetDetails(boolean getDetails) {
		this.getDetails = getDetails;
	}

	public String getLandUse() {
		return landUse;
	}

	public void setLandUse(String landUse) {
		this.landUse = landUse;
	}

	@Override
	public String toString() {
		return "LMNLandParams [websiteId=" + websiteId + ", startPage=" + startPage + ", endPage=" + endPage
				+ ", startDateTime=" + startDateTime + ", endDateTime=" + endDateTime + ", startPrice=" + startPrice
				+ ", endPrice=" + endPrice + ", startArea=" + startArea + ", endArea=" + endArea + ", getDetails="
				+ getDetails + ", landUse=" + landUse + "]";
	}

}
