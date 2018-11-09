package com.rzspider.implementspider.suzhouhouse.domain.permitpresale;

//许可证详细信息
public class PermitPresaleDetailInfo {
	// 售房单位名称
	public String companyName;
	// 商品房名称
	public String houseName;
	// 商品房地址
	public String houseAddress;
	// 预售总建筑面积
	public String yuHouseArea;
	// 住宅面积
	public String zhuHouseArea;
	// 住宅套数
	public String zhuHouseNum;
	// 非住宅面积
	public String notZhuHouseArea;
	// 非住宅套数
	public String notZhuHouseNum;
	// 其他面积
	public String otherArea;
	// 其他套数
	public String otherNum;
	// 颁发日期
	public String startDate;
	// 截止日期
	public String overDate;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getHouseName() {
		return houseName;
	}

	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	public String getHouseAddress() {
		return houseAddress;
	}

	public void setHouseAddress(String houseAddress) {
		this.houseAddress = houseAddress;
	}

	public String getYuHouseArea() {
		return yuHouseArea;
	}

	public void setYuHouseArea(String yuHouseArea) {
		this.yuHouseArea = yuHouseArea;
	}

	public String getZhuHouseArea() {
		return zhuHouseArea;
	}

	public void setZhuHouseArea(String zhuHouseArea) {
		this.zhuHouseArea = zhuHouseArea;
	}

	public String getZhuHouseNum() {
		return zhuHouseNum;
	}

	public void setZhuHouseNum(String zhuHouseNum) {
		this.zhuHouseNum = zhuHouseNum;
	}

	public String getNotZhuHouseArea() {
		return notZhuHouseArea;
	}

	public void setNotZhuHouseArea(String notZhuHouseArea) {
		this.notZhuHouseArea = notZhuHouseArea;
	}

	public String getNotZhuHouseNum() {
		return notZhuHouseNum;
	}

	public void setNotZhuHouseNum(String notZhuHouseNum) {
		this.notZhuHouseNum = notZhuHouseNum;
	}

	public String getOtherArea() {
		return otherArea;
	}

	public void setOtherArea(String otherArea) {
		this.otherArea = otherArea;
	}

	public String getOtherNum() {
		return otherNum;
	}

	public void setOtherNum(String otherNum) {
		this.otherNum = otherNum;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getOverDate() {
		return overDate;
	}

	public void setOverDate(String overDate) {
		this.overDate = overDate;
	}

	@Override
	public String toString() {
		return "PermitPresaleDetailInfo [companyName=" + companyName + ", houseName=" + houseName + ", houseAddress="
				+ houseAddress + ", yuHouseArea=" + yuHouseArea + ", zhuHouseArea=" + zhuHouseArea + ", zhuHouseNum="
				+ zhuHouseNum + ", notZhuHouseArea=" + notZhuHouseArea + ", notZhuHouseNum=" + notZhuHouseNum
				+ ", otherArea=" + otherArea + ", otherNum=" + otherNum + ", startDate=" + startDate + ", overDate="
				+ overDate + "]";
	}

}
