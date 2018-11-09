package com.rzspider.implementspider.suzhouhouse.domain.houseshow;

public class HouseShowParams {
	// 模式
	public int selectPattern;
	// 项目名称
	public String projectName;
	// 项目区域
	public String projectArea;
	// 公司名称
	public String companyName;
	// 填写页数
	public String pageNumber;
	// 是否爬取全部
	public boolean isAllData;

	public int getSelectPattern() {
		return selectPattern;
	}

	public void setSelectPattern(int selectPattern) {
		this.selectPattern = selectPattern;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectArea() {
		return projectArea;
	}

	public String getPageNumber() {
		return pageNumber;
	}

	public void setProjectArea(String projectArea) {
		this.projectArea = projectArea;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}

	public boolean isAllData() {
		return isAllData;
	}

	public void setAllData(boolean isAllData) {
		this.isAllData = isAllData;
	}

	@Override
	public String toString() {
		return "HouseShowParams [selectPattern=" + selectPattern + ", projectName=" + projectName + ", projectArea="
				+ projectArea + ", companyName=" + companyName + ", pageNumber=" + pageNumber + ", isAllData="
				+ isAllData + "]";
	}

}
