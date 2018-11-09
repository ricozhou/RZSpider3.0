package com.rzspider.project.tool.baseset.domain;

import com.rzspider.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 歌曲表 music_musiclist
 * 
 * @author ricozhou
 * @date 2018-06-28
 */
public class Musiclist extends MusicInfo {
	private static final long serialVersionUID = 1L;

	/** 歌曲id */
	private Integer musicId;
	/** 基础设置id */
	private Integer basesetId;
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
	/** 搜索值 */
	private String searchValue;

	@Override
	public String toString() {
		return "Musiclist [musicId=" + musicId + ", basesetId=" + basesetId + ", createBy=" + createBy + ", createTime="
				+ createTime + ", updateBy=" + updateBy + ", updateTime=" + updateTime + ", remark=" + remark
				+ ", searchValue=" + searchValue + "]";
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	/**
	 * 设置：歌曲id
	 */
	public void setMusicId(Integer musicId) {
		this.musicId = musicId;
	}

	/**
	 * 获取：歌曲id
	 */
	public Integer getMusicId() {
		return musicId;
	}

	/**
	 * 设置：基础设置id
	 */
	public void setBasesetId(Integer basesetId) {
		this.basesetId = basesetId;
	}

	/**
	 * 获取：基础设置id
	 */
	public Integer getBasesetId() {
		return basesetId;
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
