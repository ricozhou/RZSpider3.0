package com.rzspider.implementspider.suzhouhouse.domain.housequery;

public class HouseQueryMessage {
	// 房屋坐落
	public String houseLacation;
	// 房屋用途
	public String houseUse;
	// 房屋套型
	public String houseType;
	// 建筑面积
	public String houseBuildArea;
	// 参考单价
	public String houseReferencePrice;

	// 房屋详情
	public HouseQueryDetailInfo hqdi;

	// 公司详情
	public HouseQueryCompanyInfo hqci;

	public String getHouseLacation() {
		return houseLacation;
	}

	public void setHouseLacation(String houseLacation) {
		this.houseLacation = houseLacation;
	}

	public String getHouseUse() {
		return houseUse;
	}

	public void setHouseUse(String houseUse) {
		this.houseUse = houseUse;
	}

	public String getHouseType() {
		return houseType;
	}

	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}

	public String getHouseBuildArea() {
		return houseBuildArea;
	}

	public void setHouseBuildArea(String houseBuildArea) {
		this.houseBuildArea = houseBuildArea;
	}

	public String getHouseReferencePrice() {
		return houseReferencePrice;
	}

	public void setHouseReferencePrice(String houseReferencePrice) {
		this.houseReferencePrice = houseReferencePrice;
	}

	public HouseQueryDetailInfo getHqdi() {
		return hqdi;
	}

	public void setHqdi(HouseQueryDetailInfo hqdi) {
		this.hqdi = hqdi;
	}

	public HouseQueryCompanyInfo getHqci() {
		return hqci;
	}

	public void setHqci(HouseQueryCompanyInfo hqci) {
		this.hqci = hqci;
	}

	@Override
	public String toString() {
		return "HouseQueryMessage [houseLacation=" + houseLacation + ", houseUse=" + houseUse + ", houseType="
				+ houseType + ", houseBuildArea=" + houseBuildArea + ", houseReferencePrice=" + houseReferencePrice
				+ ", hqdi=" + hqdi + ", hqci=" + hqci + "]";
	}

}
