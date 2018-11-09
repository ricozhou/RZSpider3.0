package com.rzspider.implementspider.suzhouhouse.domain.permitpresale;

public class PermitPresaleCompanyInfo {
	// 房地产公司名称
	public String companyName;
	// 法定代表人姓名
	public String legalRepresentative;
	// 法定代表人电话
	public String legalRepreTel;
	// 法定地址
	public String legalRepreAddress;
	// 营业执照注册号
	public String businessLicenseNum;
	// 资质证书编号
	public String qualificatiomNum;
	// 企业类型
	public String companyType;
	// 通讯地址
	public String mailAddress;
	// 邮政编码
	public String postalCode;
	// E-mail
	public String email;
	// 网站
	public String websiteUrl;
	// 联系人
	public String linkman;
	// 联系电话
	public String linkNum;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getLegalRepresentative() {
		return legalRepresentative;
	}

	public void setLegalRepresentative(String legalRepresentative) {
		this.legalRepresentative = legalRepresentative;
	}

	public String getLegalRepreTel() {
		return legalRepreTel;
	}

	public void setLegalRepreTel(String legalRepreTel) {
		this.legalRepreTel = legalRepreTel;
	}

	public String getLegalRepreAddress() {
		return legalRepreAddress;
	}

	public void setLegalRepreAddress(String legalRepreAddress) {
		this.legalRepreAddress = legalRepreAddress;
	}

	public String getBusinessLicenseNum() {
		return businessLicenseNum;
	}

	public void setBusinessLicenseNum(String businessLicenseNum) {
		this.businessLicenseNum = businessLicenseNum;
	}

	public String getQualificatiomNum() {
		return qualificatiomNum;
	}

	public void setQualificatiomNum(String qualificatiomNum) {
		this.qualificatiomNum = qualificatiomNum;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebsiteUrl() {
		return websiteUrl;
	}

	public void setWebsiteUrl(String websiteUrl) {
		this.websiteUrl = websiteUrl;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getLinkNum() {
		return linkNum;
	}

	public void setLinkNum(String linkNum) {
		this.linkNum = linkNum;
	}

	@Override
	public String toString() {
		return "HouseShowCompanyInfo [companyName=" + companyName + ", legalRepresentative=" + legalRepresentative
				+ ", legalRepreTel=" + legalRepreTel + ", legalRepreAddress=" + legalRepreAddress
				+ ", businessLicenseNum=" + businessLicenseNum + ", qualificatiomNum=" + qualificatiomNum
				+ ", companyType=" + companyType + ", mailAddress=" + mailAddress + ", postalCode=" + postalCode
				+ ", email=" + email + ", websiteUrl=" + websiteUrl + ", linkman=" + linkman + ", linkNum=" + linkNum
				+ "]";
	}
}
