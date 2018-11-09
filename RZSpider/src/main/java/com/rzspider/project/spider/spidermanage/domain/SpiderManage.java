package com.rzspider.project.spider.spidermanage.domain;

import java.util.Arrays;
import com.rzspider.framework.web.domain.BaseEntity;

/**
 * 爬虫对象
 * 
 * @author ricozhou
 */
public class SpiderManage extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 爬虫ID */
	private Long spiderId;
	// 爬虫后台ID
	private Long spiderBackId;
	/** 爬虫名 */
	private String spiderName;
	/** 爬虫类型 */
	private String spiderType;
	// 创建的类型，0是自带，1是创建（需要动态执行的.java文件）
	private int createType;
	// 爬虫描述
	private String spiderDes;
	// 默认参数全部属性
	private String spiderDefaultParamsAll;

	// 默认参数
	private String spiderDefaultParams;
	/** 网址 */
	private String spiderLink;
	/** 爬虫状态:0正常,1禁用 */
	private int status;
	/** 爬虫是否存在此角色标识 默认不存在 */
	private boolean flag = false;

	public String getSpiderDefaultParamsAll() {
		return spiderDefaultParamsAll;
	}

	public void setSpiderDefaultParamsAll(String spiderDefaultParamsAll) {
		this.spiderDefaultParamsAll = spiderDefaultParamsAll;
	}

	public String getSpiderDefaultParams() {
		return spiderDefaultParams;
	}

	public void setSpiderDefaultParams(String spiderDefaultParams) {
		this.spiderDefaultParams = spiderDefaultParams;
	}

	public Long getSpiderBackId() {
		return spiderBackId;
	}

	public void setSpiderBackId(Long spiderBackId) {
		this.spiderBackId = spiderBackId;
	}

	public int getCreateType() {
		return createType;
	}

	public void setCreateType(int createType) {
		this.createType = createType;
	}

	public Long getSpiderId() {
		return spiderId;
	}

	public void setSpiderId(Long spiderId) {
		this.spiderId = spiderId;
	}

	public String getSpiderName() {
		return spiderName;
	}

	public void setSpiderName(String spiderName) {
		this.spiderName = spiderName;
	}

	public String getSpiderType() {
		return spiderType;
	}

	public void setSpiderType(String spiderType) {
		this.spiderType = spiderType;
	}

	public String getSpiderDes() {
		return spiderDes;
	}

	public void setSpiderDes(String spiderDes) {
		this.spiderDes = spiderDes;
	}

	public String getSpiderLink() {
		return spiderLink;
	}

	public void setSpiderLink(String spiderLink) {
		this.spiderLink = spiderLink;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "SpiderManage [spiderId=" + spiderId + ", spiderBackId=" + spiderBackId + ", spiderName=" + spiderName
				+ ", spiderType=" + spiderType + ", createType=" + createType + ", spiderDes=" + spiderDes
				+ ", spiderDefaultParamsAll=" + spiderDefaultParamsAll + ", spiderDefaultParams=" + spiderDefaultParams
				+ ", spiderLink=" + spiderLink + ", status=" + status + ", flag=" + flag + "]";
	}

}
