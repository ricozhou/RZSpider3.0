package com.rzspider.project.commontool.toollist.domain;

import com.rzspider.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 通用工具列表 commontool_toollist
 * 
 * @author ricozhou
 * @date 2018-06-13
 */
public class Toollist extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 工具id */
	private Integer toolListId;
	/** 工具后台id */
	private Integer toolBackId;
	/** 工具昵称 */
	private String toolNickName;
	/** 工具名称 */
	private String toolName;
	/** 工具说明 */
	private String toolInstruction;

	// 无法再次继承，先直接加进来
	/** 工具类型（网页版客户端版） */
	private Integer toolType;
	/** 源码是否可下载 */
	private Integer srcDownloadStatus;
	/** 可执行exe是否可下载 */
	private Integer execexeDownloadStatus;
	/** 安装版exe是否可下载 */
	private Integer setupexeDownloadStatus;

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
		return "Toollist [toolListId=" + toolListId + ", toolBackId=" + toolBackId + ", toolNickName=" + toolNickName
				+ ", toolName=" + toolName + ", toolInstruction=" + toolInstruction + ", toolType=" + toolType
				+ ", srcDownloadStatus=" + srcDownloadStatus + ", execexeDownloadStatus=" + execexeDownloadStatus
				+ ", setupexeDownloadStatus=" + setupexeDownloadStatus + ", createBy=" + createBy + ", createTime="
				+ createTime + ", updateBy=" + updateBy + ", updateTime=" + updateTime + ", remark=" + remark + "]";
	}

	public Integer getToolBackId() {
		return toolBackId;
	}

	public void setToolBackId(Integer toolBackId) {
		this.toolBackId = toolBackId;
	}

	public Integer getToolType() {
		return toolType;
	}

	public void setToolType(Integer toolType) {
		this.toolType = toolType;
	}

	public Integer getSrcDownloadStatus() {
		return srcDownloadStatus;
	}

	public void setSrcDownloadStatus(Integer srcDownloadStatus) {
		this.srcDownloadStatus = srcDownloadStatus;
	}

	public Integer getExecexeDownloadStatus() {
		return execexeDownloadStatus;
	}

	public void setExecexeDownloadStatus(Integer execexeDownloadStatus) {
		this.execexeDownloadStatus = execexeDownloadStatus;
	}

	public Integer getSetupexeDownloadStatus() {
		return setupexeDownloadStatus;
	}

	public void setSetupexeDownloadStatus(Integer setupexeDownloadStatus) {
		this.setupexeDownloadStatus = setupexeDownloadStatus;
	}

	/**
	 * 设置：工具id
	 */
	public void setToolListId(Integer toolListId) {
		this.toolListId = toolListId;
	}

	/**
	 * 获取：工具id
	 */
	public Integer getToolListId() {
		return toolListId;
	}

	/**
	 * 设置：工具昵称
	 */
	public void setToolNickName(String toolNickName) {
		this.toolNickName = toolNickName;
	}

	/**
	 * 获取：工具昵称
	 */
	public String getToolNickName() {
		return toolNickName;
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
	 * 设置：工具说明
	 */
	public void setToolInstruction(String toolInstruction) {
		this.toolInstruction = toolInstruction;
	}

	/**
	 * 获取：工具说明
	 */
	public String getToolInstruction() {
		return toolInstruction;
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
