package com.rzspider.implementspider.taobaojud.domain;

public class TBJLandParams {
	// 标的物类型
	public String targetType;
	// 拍卖状态
	public String auctionStatus;
	// 所在地省
	public String targetAddressProvince;
	// 所在地市
	public String targetAddressCity;
	// 起始页
	public int startPage;
	// 结束页
	public int endPage;
	// 是否爬取详情
	public boolean getDetails;

	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	public String getAuctionStatus() {
		return auctionStatus;
	}

	public void setAuctionStatus(String auctionStatus) {
		this.auctionStatus = auctionStatus;
	}

	public String getTargetAddressProvince() {
		return targetAddressProvince;
	}

	public void setTargetAddressProvince(String targetAddressProvince) {
		this.targetAddressProvince = targetAddressProvince;
	}

	public String getTargetAddressCity() {
		return targetAddressCity;
	}

	public void setTargetAddressCity(String targetAddressCity) {
		this.targetAddressCity = targetAddressCity;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public boolean isGetDetails() {
		return getDetails;
	}

	public void setGetDetails(boolean getDetails) {
		this.getDetails = getDetails;
	}

	@Override
	public String toString() {
		return "TBJLandParams [targetType=" + targetType + ", auctionStatus=" + auctionStatus
				+ ", targetAddressProvince=" + targetAddressProvince + ", targetAddressCity=" + targetAddressCity
				+ ", startPage=" + startPage + ", endPage=" + endPage + ", getDetails=" + getDetails + "]";
	}

}
