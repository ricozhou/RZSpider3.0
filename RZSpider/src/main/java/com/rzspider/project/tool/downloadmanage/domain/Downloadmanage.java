package com.rzspider.project.tool.downloadmanage.domain;

import com.rzspider.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 下载管理详情表 tool_downloadmanage
 * 
 * @author ricozhou
 * @date 2018-09-07
 */
public class Downloadmanage extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**  */
	private Integer downloadmanageId;
	/** 下载文件名称 */
	private String downloadFileName;
	/** 下载文件uuid名称 */
	private String downloadFileUuidName;
	/** 下载文件类型 */
	private Integer downloadFileType;
	/** 下载文件后缀 */
	private String downloadFileExtensionName;
	/** 下载文件大小 */
	private String downloadFileSize;
	// 外链key
	private String downloadFileUrlKey;
	/** 外链 */
	private String downloadFileUrl;
	/** 下载文件限制次数 */
	private Integer downloadFileLimitDownNum;
	/** 下载文件限制时间 */
	private String downloadFileLimitDownTime;
	/** 下载文件次数 */
	private Integer downloadFileDownNum;

	@Override
	public String toString() {
		return "Downloadmanage [downloadmanageId=" + downloadmanageId + ", downloadFileName=" + downloadFileName
				+ ", downloadFileUuidName=" + downloadFileUuidName + ", downloadFileType=" + downloadFileType
				+ ", downloadFileExtensionName=" + downloadFileExtensionName + ", downloadFileSize=" + downloadFileSize
				+ ", downloadFileUrlKey=" + downloadFileUrlKey + ", downloadFileUrl=" + downloadFileUrl
				+ ", downloadFileLimitDownNum=" + downloadFileLimitDownNum + ", downloadFileLimitDownTime="
				+ downloadFileLimitDownTime + ", downloadFileDownNum=" + downloadFileDownNum + ", status=" + status
				+ ", useStatus=" + useStatus + ", createBy=" + createBy + ", createTime=" + createTime + ", updateBy="
				+ updateBy + ", updateTime=" + updateTime + ", remark=" + remark + "]";
	}

	/** 可用状态 */
	private Integer status;
	/** 使用状态 */
	private Integer useStatus;
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

	public String getDownloadFileUrlKey() {
		return downloadFileUrlKey;
	}

	public void setDownloadFileUrlKey(String downloadFileUrlKey) {
		this.downloadFileUrlKey = downloadFileUrlKey;
	}

	/**
	 * 设置：
	 */
	public void setDownloadmanageId(Integer downloadmanageId) {
		this.downloadmanageId = downloadmanageId;
	}

	/**
	 * 获取：
	 */
	public Integer getDownloadmanageId() {
		return downloadmanageId;
	}

	/**
	 * 设置：下载文件名称
	 */
	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}

	/**
	 * 获取：下载文件名称
	 */
	public String getDownloadFileName() {
		return downloadFileName;
	}

	/**
	 * 设置：下载文件uuid名称
	 */
	public void setDownloadFileUuidName(String downloadFileUuidName) {
		this.downloadFileUuidName = downloadFileUuidName;
	}

	/**
	 * 获取：下载文件uuid名称
	 */
	public String getDownloadFileUuidName() {
		return downloadFileUuidName;
	}

	/**
	 * 设置：下载文件类型
	 */
	public void setDownloadFileType(Integer downloadFileType) {
		this.downloadFileType = downloadFileType;
	}

	/**
	 * 获取：下载文件类型
	 */
	public Integer getDownloadFileType() {
		return downloadFileType;
	}

	/**
	 * 设置：下载文件后缀
	 */
	public void setDownloadFileExtensionName(String downloadFileExtensionName) {
		this.downloadFileExtensionName = downloadFileExtensionName;
	}

	/**
	 * 获取：下载文件后缀
	 */
	public String getDownloadFileExtensionName() {
		return downloadFileExtensionName;
	}

	/**
	 * 设置：下载文件大小
	 */
	public void setDownloadFileSize(String downloadFileSize) {
		this.downloadFileSize = downloadFileSize;
	}

	/**
	 * 获取：下载文件大小
	 */
	public String getDownloadFileSize() {
		return downloadFileSize;
	}

	/**
	 * 设置：
	 */
	public void setDownloadFileUrl(String downloadFileUrl) {
		this.downloadFileUrl = downloadFileUrl;
	}

	/**
	 * 获取：
	 */
	public String getDownloadFileUrl() {
		return downloadFileUrl;
	}

	/**
	 * 设置：下载文件限制次数
	 */
	public void setDownloadFileLimitDownNum(Integer downloadFileLimitDownNum) {
		this.downloadFileLimitDownNum = downloadFileLimitDownNum;
	}

	/**
	 * 获取：下载文件限制次数
	 */
	public Integer getDownloadFileLimitDownNum() {
		return downloadFileLimitDownNum;
	}

	/**
	 * 设置：下载文件限制时间
	 */
	public void setDownloadFileLimitDownTime(String downloadFileLimitDownTime) {
		this.downloadFileLimitDownTime = downloadFileLimitDownTime;
	}

	/**
	 * 获取：下载文件限制时间
	 */
	public String getDownloadFileLimitDownTime() {
		return downloadFileLimitDownTime;
	}

	/**
	 * 设置：下载文件次数
	 */
	public void setDownloadFileDownNum(Integer downloadFileDownNum) {
		this.downloadFileDownNum = downloadFileDownNum;
	}

	/**
	 * 获取：下载文件次数
	 */
	public Integer getDownloadFileDownNum() {
		return downloadFileDownNum;
	}

	/**
	 * 设置：可用状态
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 获取：可用状态
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 设置：使用状态
	 */
	public void setUseStatus(Integer useStatus) {
		this.useStatus = useStatus;
	}

	/**
	 * 获取：使用状态
	 */
	public Integer getUseStatus() {
		return useStatus;
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
