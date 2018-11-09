package com.rzspider.implementspider.suzhouhouse.domain.houseshow;

import java.util.List;

public class HouseShowMessage {
	// 编号
	public int id;
	// 项目名称
	public String projectName;
	// 项目区域
	public String projectArea;
	// 房地产公司名称
	public String companyName;
	// 项目地址
	public String projectAddress;
	// 代理公司名称
	public String agentCompanyName;
	// 楼幢详细信息
	public List<HouseShowDetailInfo> hsdiList;
	// 公司详细信息
	public HouseShowCompanyInfo hsci;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public void setProjectArea(String projectArea) {
		this.projectArea = projectArea;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getProjectAddress() {
		return projectAddress;
	}

	public void setProjectAddress(String projectAddress) {
		this.projectAddress = projectAddress;
	}

	public String getAgentCompanyName() {
		return agentCompanyName;
	}

	public void setAgentCompanyName(String agentCompanyName) {
		this.agentCompanyName = agentCompanyName;
	}

	public List<HouseShowDetailInfo> getHsdiList() {
		return hsdiList;
	}

	public void setHsdiList(List<HouseShowDetailInfo> hsdiList) {
		this.hsdiList = hsdiList;
	}

	public HouseShowCompanyInfo getHsci() {
		return hsci;
	}

	public void setHsci(HouseShowCompanyInfo hsci) {
		this.hsci = hsci;
	}

	@Override
	public String toString() {
		return "HouseShowMessage [id=" + id + ", projectName=" + projectName + ", projectArea=" + projectArea
				+ ", companyName=" + companyName + ", projectAddress=" + projectAddress + ", agentCompanyName="
				+ agentCompanyName + ", hsdiList=" + hsdiList + ", hsci=" + hsci + "]";
	}

}
