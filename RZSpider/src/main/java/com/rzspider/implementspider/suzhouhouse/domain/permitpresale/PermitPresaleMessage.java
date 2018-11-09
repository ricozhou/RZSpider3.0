package com.rzspider.implementspider.suzhouhouse.domain.permitpresale;

public class PermitPresaleMessage {
	// 编号
	public int id;
	// 许可证号
	public String permitNum;
	// 项目名称
	public String projectName;
	// 许可证详细信息
	public PermitPresaleDetailInfo ppdi;
	// 公司详细信息
	public PermitPresaleCompanyInfo ppci;

	public int getId() {
		return id;
	}

	public String getPermitNum() {
		return permitNum;
	}

	public void setPermitNum(String permitNum) {
		this.permitNum = permitNum;
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

	public PermitPresaleDetailInfo getPpdi() {
		return ppdi;
	}

	public void setPpdi(PermitPresaleDetailInfo ppdi) {
		this.ppdi = ppdi;
	}

	public PermitPresaleCompanyInfo getPpci() {
		return ppci;
	}

	public void setPpci(PermitPresaleCompanyInfo ppci) {
		this.ppci = ppci;
	}

	@Override
	public String toString() {
		return "PermitPresaleMessage [id=" + id + ", permitNum=" + permitNum + ", projectName=" + projectName
				+ ", ppdi=" + ppdi + ", ppci=" + ppci + "]";
	}

}
