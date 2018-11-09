package com.rzspider.implementspider.suzhouhouse.domain.MegAnnoAndTradeModel;

public class MessageAnnoModel {
	// 区域
	public String houseArea;
	// 小计套数
	public String totalNum;
	// 小计总建筑面积
	public String totalStructureArea;
	// 住宅套数
	public String houseTotalNum;
	// 住宅总建筑面积
	public String houseTotalStructureArea;

	public String getHouseArea() {
		return houseArea;
	}

	public void setHouseArea(String houseArea) {
		this.houseArea = houseArea;
	}

	public String getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}

	public String getTotalStructureArea() {
		return totalStructureArea;
	}

	public void setTotalStructureArea(String totalStructureArea) {
		this.totalStructureArea = totalStructureArea;
	}

	public String getHouseTotalNum() {
		return houseTotalNum;
	}

	public void setHouseTotalNum(String houseTotalNum) {
		this.houseTotalNum = houseTotalNum;
	}

	public String getHouseTotalStructureArea() {
		return houseTotalStructureArea;
	}

	public void setHouseTotalStructureArea(String houseTotalStructureArea) {
		this.houseTotalStructureArea = houseTotalStructureArea;
	}

	@Override
	public String toString() {
		return "MessageAnnoModel [houseArea=" + houseArea + ", totalNum=" + totalNum + ", totalStructureArea="
				+ totalStructureArea + ", houseTotalNum=" + houseTotalNum + ", houseTotalStructureArea="
				+ houseTotalStructureArea + "]";
	}

}
