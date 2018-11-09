package com.rzspider.project.commontool.toolmanage.domain;

import com.rzspider.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 通用工具管理表 commontool_toolmanage
 * 
 * @author ricozhou
 * @date 2018-06-13
 */
public class Toolmanage extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 工具id */
	private Integer toolId;
	/** 工具后台id */
	private Integer toolBackId;
	/** 工具名称 */
	private String toolName;
	/** 工具描述 */
	private String toolDes;
	/** 工具类型（网页版客户端版） */
	private Integer toolType;
	/** 工具代码类型 */
	private Integer toolCodeType;
	/** 源码是否可下载 */
	private Integer srcDownloadStatus;
	/** 可执行exe是否可下载 */
	private Integer execexeDownloadStatus;
	/** 安装版exe是否可下载 */
	private Integer setupexeDownloadStatus;
	/** 可用状态 */
	private Integer status;
	/** 创建类型 */
	// 0是内置1是自定义添加
	private Integer createType;
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
	// 源码文件名
	public String srcFileName;
	// 可执行exe文件名
	public String execexeFileName;
	// 安装版exe文件名
	public String setupexeName;

	public Integer getCreateType() {
		return createType;
	}

	public void setCreateType(Integer createType) {
		this.createType = createType;
	}

	public String getSrcFileName() {
		return srcFileName;
	}

	public void setSrcFileName(String srcFileName) {
		this.srcFileName = srcFileName;
	}

	public String getExecexeFileName() {
		return execexeFileName;
	}

	public void setExecexeFileName(String execexeFileName) {
		this.execexeFileName = execexeFileName;
	}

	public String getSetupexeName() {
		return setupexeName;
	}

	public void setSetupexeName(String setupexeName) {
		this.setupexeName = setupexeName;
	}

	/**
	 * 设置：工具id
	 */
	public void setToolId(Integer toolId) {
		this.toolId = toolId;
	}

	/**
	 * 获取：工具id
	 */
	public Integer getToolId() {
		return toolId;
	}

	@Override
	public String toString() {
		return "Toolmanage [toolId=" + toolId + ", toolBackId=" + toolBackId + ", toolName=" + toolName + ", toolDes="
				+ toolDes + ", toolType=" + toolType + ", toolCodeType=" + toolCodeType + ", srcDownloadStatus="
				+ srcDownloadStatus + ", execexeDownloadStatus=" + execexeDownloadStatus + ", setupexeDownloadStatus="
				+ setupexeDownloadStatus + ", status=" + status + ", createType=" + createType + ", createBy="
				+ createBy + ", createTime=" + createTime + ", updateBy=" + updateBy + ", updateTime=" + updateTime
				+ ", remark=" + remark + ", srcFileName=" + srcFileName + ", execexeFileName=" + execexeFileName
				+ ", setupexeName=" + setupexeName + "]";
	}

	public Integer getToolBackId() {
		return toolBackId;
	}

	public void setToolBackId(Integer toolBackId) {
		this.toolBackId = toolBackId;
	}

	/**
	 * 设置：工具名称
	 */
	public void setToolName(String toolName) {
		this.toolName = toolName;
	}

	/**
	 * 获取：工具名称
	 */
	public String getToolName() {
		return toolName;
	}

	/**
	 * 设置：工具描述
	 */
	public void setToolDes(String toolDes) {
		this.toolDes = toolDes;
	}

	/**
	 * 获取：工具描述
	 */
	public String getToolDes() {
		return toolDes;
	}

	/**
	 * 设置：工具类型（网页版客户端版）
	 */
	public void setToolType(Integer toolType) {
		this.toolType = toolType;
	}

	/**
	 * 获取：工具类型（网页版客户端版）
	 */
	public Integer getToolType() {
		return toolType;
	}

	/**
	 * 设置：工具代码类型
	 */
	public void setToolCodeType(Integer toolCodeType) {
		this.toolCodeType = toolCodeType;
	}

	/**
	 * 获取：工具代码类型
	 */
	public Integer getToolCodeType() {
		return toolCodeType;
	}

	/**
	 * 设置：源码是否可下载
	 */
	public void setSrcDownloadStatus(Integer srcDownloadStatus) {
		this.srcDownloadStatus = srcDownloadStatus;
	}

	/**
	 * 获取：源码是否可下载
	 */
	public Integer getSrcDownloadStatus() {
		return srcDownloadStatus;
	}

	/**
	 * 设置：可执行exe是否可下载
	 */
	public void setExecexeDownloadStatus(Integer execexeDownloadStatus) {
		this.execexeDownloadStatus = execexeDownloadStatus;
	}

	/**
	 * 获取：可执行exe是否可下载
	 */
	public Integer getExecexeDownloadStatus() {
		return execexeDownloadStatus;
	}

	/**
	 * 设置：安装版exe是否可下载
	 */
	public void setSetupexeDownloadStatus(Integer setupexeDownloadStatus) {
		this.setupexeDownloadStatus = setupexeDownloadStatus;
	}

	/**
	 * 获取：安装版exe是否可下载
	 */
	public Integer getSetupexeDownloadStatus() {
		return setupexeDownloadStatus;
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
