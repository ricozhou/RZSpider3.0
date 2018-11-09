package com.rzspider.implementspider.taobaojud.domain;

public class TBJLandMessageDetail {
	// 编号
	private String id;
	// 自带id
	private String urlId;
	// URL地址
	private String itemUrl;
	// 拍卖状态
	private String saleStatus;
	// 拍卖土地名称
	private String saleName;
	// 起拍价
	private String initialPrice;
	// 评估价
	private String consultPrice;
	//
	private String sellOff;
	// 开拍时间
	private String startDate;
	// 成交时间
	private String endDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrlId() {
		return urlId;
	}

	public void setUrlId(String urlId) {
		this.urlId = urlId;
	}

	public String getItemUrl() {
		return itemUrl;
	}

	public void setItemUrl(String itemUrl) {
		this.itemUrl = itemUrl;
	}

	public String getSaleStatus() {
		return saleStatus;
	}

	public void setSaleStatus(String saleStatus) {
		this.saleStatus = saleStatus;
	}

	public String getSaleName() {
		return saleName;
	}

	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}

	public String getInitialPrice() {
		return initialPrice;
	}

	public void setInitialPrice(String initialPrice) {
		this.initialPrice = initialPrice;
	}

	public String getConsultPrice() {
		return consultPrice;
	}

	public void setConsultPrice(String consultPrice) {
		this.consultPrice = consultPrice;
	}

	public String getSellOff() {
		return sellOff;
	}

	public void setSellOff(String sellOff) {
		this.sellOff = sellOff;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "SimpleJudSaleInfo [id=" + id + ", urlId=" + urlId + ", itemUrl=" + itemUrl + ", saleStatus="
				+ saleStatus + ", saleName=" + saleName + ", initialPrice=" + initialPrice + ", consultPrice="
				+ consultPrice + ", sellOff=" + sellOff + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}

}
