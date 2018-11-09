package com.rzspider.implementspider.suzhouhouse.domain.housequery;

public class HouseQueryParams {
	// 装修情况
	public int fitmentSelect;
	// 项目区域
	public String projectArea;
	// 房屋最低价
	public String houseMinPrice;
	// 房屋最高价
	public String houseMaxPrice;
	// 房屋用途
	public String houseUse;
	// 房屋最低面积
	public String houseMinArea;
	// 房屋最高面积
	public String houseMaxArea;
	// 项目名称
	public String projectName;
	// 公司名称
	public String companyName;
	// 页数
	public String pageNumber;

	public String getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getFitmentSelect() {
		return fitmentSelect;
	}

	public void setFitmentSelect(int fitmentSelect) {
		this.fitmentSelect = fitmentSelect;
	}

	public String getProjectArea() {
		return projectArea;
	}

	public void setProjectArea(String projectArea) {
		this.projectArea = projectArea;
	}

	public String getHouseMinPrice() {
		return houseMinPrice;
	}

	public void setHouseMinPrice(String houseMinPrice) {
		this.houseMinPrice = houseMinPrice;
	}

	public String getHouseMaxPrice() {
		return houseMaxPrice;
	}

	public void setHouseMaxPrice(String houseMaxPrice) {
		this.houseMaxPrice = houseMaxPrice;
	}

	public String getHouseUse() {
		return houseUse;
	}

	public void setHouseUse(String houseUse) {
		this.houseUse = houseUse;
	}

	public String getHouseMinArea() {
		return houseMinArea;
	}

	public void setHouseMinArea(String houseMinArea) {
		this.houseMinArea = houseMinArea;
	}

	public String getHouseMaxArea() {
		return houseMaxArea;
	}

	public void setHouseMaxArea(String houseMaxArea) {
		this.houseMaxArea = houseMaxArea;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Override
	public String toString() {
		return "HouseQueryParams [fitmentSelect=" + fitmentSelect + ", projectArea=" + projectArea + ", houseMinPrice="
				+ houseMinPrice + ", houseMaxPrice=" + houseMaxPrice + ", houseUse=" + houseUse + ", houseMinArea="
				+ houseMinArea + ", houseMaxArea=" + houseMaxArea + ", projectName=" + projectName + ", companyName="
				+ companyName + ", pageNumber=" + pageNumber + "]";
	}

}
