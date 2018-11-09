package com.rzspider.project.commongame.gamelist.domain;

import com.rzspider.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 通用游戏列表 commongame_gamelist
 * 
 * @author ricozhou
 * @date 2018-06-13
 */
public class Gamelist extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 游戏id */
	private Integer gameListId;
	/** 游戏后台id */
	private Integer gameBackId;
	/** 游戏昵称 */
	private String gameNickName;
	/** 游戏名称 */
	private String gameName;
	/** 游戏说明 */
	private String gameInstruction;

	// 无法再次继承，先直接加进来
	/** 游戏类型（网页版客户端版） */
	private Integer gameType;
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
		return "Gamelist [gameListId=" + gameListId + ", gameBackId=" + gameBackId + ", gameNickName=" + gameNickName
				+ ", gameName=" + gameName + ", gameInstruction=" + gameInstruction + ", gameType=" + gameType
				+ ", srcDownloadStatus=" + srcDownloadStatus + ", execexeDownloadStatus=" + execexeDownloadStatus
				+ ", setupexeDownloadStatus=" + setupexeDownloadStatus + ", createBy=" + createBy + ", createTime="
				+ createTime + ", updateBy=" + updateBy + ", updateTime=" + updateTime + ", remark=" + remark + "]";
	}

	public Integer getGameBackId() {
		return gameBackId;
	}

	public void setGameBackId(Integer gameBackId) {
		this.gameBackId = gameBackId;
	}

	public Integer getGameType() {
		return gameType;
	}

	public void setGameType(Integer gameType) {
		this.gameType = gameType;
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
	 * 设置：游戏id
	 */
	public void setGameListId(Integer gameListId) {
		this.gameListId = gameListId;
	}

	/**
	 * 获取：游戏id
	 */
	public Integer getGameListId() {
		return gameListId;
	}

	/**
	 * 设置：游戏昵称
	 */
	public void setGameNickName(String gameNickName) {
		this.gameNickName = gameNickName;
	}

	/**
	 * 获取：游戏昵称
	 */
	public String getGameNickName() {
		return gameNickName;
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
	 * 设置：游戏说明
	 */
	public void setGameInstruction(String gameInstruction) {
		this.gameInstruction = gameInstruction;
	}

	/**
	 * 获取：游戏说明
	 */
	public String getGameInstruction() {
		return gameInstruction;
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
