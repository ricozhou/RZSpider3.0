package com.rzspider.project.commongame.gamemanage.domain;

import com.rzspider.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 通用游戏管理表 commongame_gamemanage
 * 
 * @author ricozhou
 * @date 2018-06-13
 */
public class Gamemanage extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 游戏id */
	private Integer gameId;
	/** 游戏后台id */
	private Integer gameBackId;
	/** 游戏名称 */
	private String gameName;
	/** 游戏描述 */
	private String gameDes;
	/** 游戏类型（网页版客户端版） */
	private Integer gameType;
	/** 游戏代码类型 */
	private Integer gameCodeType;
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
	 * 设置：游戏id
	 */
	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}

	/**
	 * 获取：游戏id
	 */
	public Integer getGameId() {
		return gameId;
	}

	@Override
	public String toString() {
		return "Gamemanage [gameId=" + gameId + ", gameBackId=" + gameBackId + ", gameName=" + gameName + ", gameDes="
				+ gameDes + ", gameType=" + gameType + ", gameCodeType=" + gameCodeType + ", srcDownloadStatus="
				+ srcDownloadStatus + ", execexeDownloadStatus=" + execexeDownloadStatus + ", setupexeDownloadStatus="
				+ setupexeDownloadStatus + ", status=" + status + ", createType=" + createType + ", createBy="
				+ createBy + ", createTime=" + createTime + ", updateBy=" + updateBy + ", updateTime=" + updateTime
				+ ", remark=" + remark + ", srcFileName=" + srcFileName + ", execexeFileName=" + execexeFileName
				+ ", setupexeName=" + setupexeName + "]";
	}

	public Integer getGameBackId() {
		return gameBackId;
	}

	public void setGameBackId(Integer gameBackId) {
		this.gameBackId = gameBackId;
	}

	/**
	 * 设置：游戏名称
	 */
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	/**
	 * 获取：游戏名称
	 */
	public String getGameName() {
		return gameName;
	}

	/**
	 * 设置：游戏描述
	 */
	public void setGameDes(String gameDes) {
		this.gameDes = gameDes;
	}

	/**
	 * 获取：游戏描述
	 */
	public String getGameDes() {
		return gameDes;
	}

	/**
	 * 设置：游戏类型（网页版客户端版）
	 */
	public void setGameType(Integer gameType) {
		this.gameType = gameType;
	}

	/**
	 * 获取：游戏类型（网页版客户端版）
	 */
	public Integer getGameType() {
		return gameType;
	}

	/**
	 * 设置：游戏代码类型
	 */
	public void setGameCodeType(Integer gameCodeType) {
		this.gameCodeType = gameCodeType;
	}

	/**
	 * 获取：游戏代码类型
	 */
	public Integer getGameCodeType() {
		return gameCodeType;
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
