package com.rzspider.implementspider.suzhouhouse.domain.permitpresale;

public class PermitPresaleParams {
	// 项目区域
	public String projectArea;
	// 填写页数
	public String pageNumber;
	// 公司名称
	public String companyName;
	// 项目名称
	public String projectName;
	// 预售证号
	public String preSaleCertificateNumber;

	public String getProjectArea() {
		return projectArea;
	}

	public void setProjectArea(String projectArea) {
		this.projectArea = projectArea;
	}

	public String getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getPreSaleCertificateNumber() {
		return preSaleCertificateNumber;
	}

	public void setPreSaleCertificateNumber(String preSaleCertificateNumber) {
		this.preSaleCertificateNumber = preSaleCertificateNumber;
	}

	@Override
	public String toString() {
		return "PermitPresaleParams [projectArea=" + projectArea + ", pageNumber=" + pageNumber + ", companyName="
				+ companyName + ", projectName=" + projectName + ", preSaleCertificateNumber="
				+ preSaleCertificateNumber + "]";
	}

}
