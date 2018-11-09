package com.rzspider.project.system.website.domain;

import com.rzspider.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 网站设置表 sys_website
 * 
 * @author ricozhou
 * @date 2018-09-10
 */
public class Website extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 网站主键 */
	private Integer websiteId;
	/** 网站名称 */
	private String websiteTitleName;

	/** 网站图标 */
	private String websiteIco;
	/** 网站描述 */
	private String websiteDes;
	/** 网站域名 */
	private String websiteDomainName;
	/** 网站公网IP */
	private String websitePublicIp;
	/** 网站开放端口 */
	private Integer websitePublicPort;
	/** 网站版权信息 */
	private String websiteCopyrightInformation;
	/** SMTP服务器 */
	private String websiteMailSmtpUrl;
	/** SMTP端口 */
	private Integer websiteMailSmtpPort;
	/** 发件人邮箱 */
	private String websiteMailSenderMailbox;
	/** 发件人昵称 */
	private String websiteMailSenderNickname;
	/** 发件人邮箱密码 */
	private String websiteMailSenderPassword;
	/** 创建者 */
	private String createBy;
	/** 创建时间 */
	private Date createTime;
	/** 更新者 */
	private String updateBy;
	/** 更新时间 */
	private Date updateTime;
	/** 备注 */
	private String remark;

	@Override
	public String toString() {
		return "Website [websiteId=" + websiteId + ", websiteTitleName=" + websiteTitleName + ", websiteIco="
				+ websiteIco + ", websiteDes=" + websiteDes + ", websiteDomainName=" + websiteDomainName
				+ ", websitePublicIp=" + websitePublicIp + ", websitePublicPort=" + websitePublicPort
				+ ", websiteCopyrightInformation=" + websiteCopyrightInformation + ", websiteMailSmtpUrl="
				+ websiteMailSmtpUrl + ", websiteMailSmtpPort=" + websiteMailSmtpPort + ", websiteMailSenderMailbox="
				+ websiteMailSenderMailbox + ", websiteMailSenderNickname=" + websiteMailSenderNickname
				+ ", websiteMailSenderPassword=" + websiteMailSenderPassword + ", createBy=" + createBy
				+ ", createTime=" + createTime + ", updateBy=" + updateBy + ", updateTime=" + updateTime + ", remark="
				+ remark + "]";
	}

	public String getWebsiteIco() {
		return websiteIco;
	}

	public void setWebsiteIco(String websiteIco) {
		this.websiteIco = websiteIco;
	}

	/**
	 * 设置：网站主键
	 */
	public void setWebsiteId(Integer websiteId) {
		this.websiteId = websiteId;
	}

	/**
	 * 获取：网站主键
	 */
	public Integer getWebsiteId() {
		return websiteId;
	}

	/**
	 * 设置：网站名称
	 */
	public void setWebsiteTitleName(String websiteTitleName) {
		this.websiteTitleName = websiteTitleName;
	}

	/**
	 * 获取：网站名称
	 */
	public String getWebsiteTitleName() {
		return websiteTitleName;
	}

	/**
	 * 设置：网站描述
	 */
	public void setWebsiteDes(String websiteDes) {
		this.websiteDes = websiteDes;
	}

	/**
	 * 获取：网站描述
	 */
	public String getWebsiteDes() {
		return websiteDes;
	}

	/**
	 * 设置：网站域名
	 */
	public void setWebsiteDomainName(String websiteDomainName) {
		this.websiteDomainName = websiteDomainName;
	}

	/**
	 * 获取：网站域名
	 */
	public String getWebsiteDomainName() {
		return websiteDomainName;
	}

	/**
	 * 设置：网站公网IP
	 */
	public void setWebsitePublicIp(String websitePublicIp) {
		this.websitePublicIp = websitePublicIp;
	}

	/**
	 * 获取：网站公网IP
	 */
	public String getWebsitePublicIp() {
		return websitePublicIp;
	}

	/**
	 * 设置：网站开放端口
	 */
	public void setWebsitePublicPort(Integer websitePublicPort) {
		this.websitePublicPort = websitePublicPort;
	}

	/**
	 * 获取：网站开放端口
	 */
	public Integer getWebsitePublicPort() {
		return websitePublicPort;
	}

	/**
	 * 设置：网站版权信息
	 */
	public void setWebsiteCopyrightInformation(String websiteCopyrightInformation) {
		this.websiteCopyrightInformation = websiteCopyrightInformation;
	}

	/**
	 * 获取：网站版权信息
	 */
	public String getWebsiteCopyrightInformation() {
		return websiteCopyrightInformation;
	}

	/**
	 * 设置：SMTP服务器
	 */
	public void setWebsiteMailSmtpUrl(String websiteMailSmtpUrl) {
		this.websiteMailSmtpUrl = websiteMailSmtpUrl;
	}

	/**
	 * 获取：SMTP服务器
	 */
	public String getWebsiteMailSmtpUrl() {
		return websiteMailSmtpUrl;
	}

	/**
	 * 设置：SMTP端口
	 */
	public void setWebsiteMailSmtpPort(Integer websiteMailSmtpPort) {
		this.websiteMailSmtpPort = websiteMailSmtpPort;
	}

	/**
	 * 获取：SMTP端口
	 */
	public Integer getWebsiteMailSmtpPort() {
		return websiteMailSmtpPort;
	}

	/**
	 * 设置：发件人邮箱
	 */
	public void setWebsiteMailSenderMailbox(String websiteMailSenderMailbox) {
		this.websiteMailSenderMailbox = websiteMailSenderMailbox;
	}

	/**
	 * 获取：发件人邮箱
	 */
	public String getWebsiteMailSenderMailbox() {
		return websiteMailSenderMailbox;
	}

	/**
	 * 设置：发件人昵称
	 */
	public void setWebsiteMailSenderNickname(String websiteMailSenderNickname) {
		this.websiteMailSenderNickname = websiteMailSenderNickname;
	}

	/**
	 * 获取：发件人昵称
	 */
	public String getWebsiteMailSenderNickname() {
		return websiteMailSenderNickname;
	}

	/**
	 * 设置：发件人邮箱密码
	 */
	public void setWebsiteMailSenderPassword(String websiteMailSenderPassword) {
		this.websiteMailSenderPassword = websiteMailSenderPassword;
	}

	/**
	 * 获取：发件人邮箱密码
	 */
	public String getWebsiteMailSenderPassword() {
		return websiteMailSenderPassword;
	}

	/**
	 * 设置：创建者
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	/**
	 * 获取：创建者
	 */
	public String getCreateBy() {
		return createBy;
	}

	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置：更新者
	 */
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	/**
	 * 获取：更新者
	 */
	public String getUpdateBy() {
		return updateBy;
	}

	/**
	 * 设置：更新时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 获取：更新时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
	}

}
