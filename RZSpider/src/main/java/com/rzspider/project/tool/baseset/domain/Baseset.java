package com.rzspider.project.tool.baseset.domain;

import com.rzspider.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 基础设置详情表 tool_baseset
 * 
 * @author ricozhou
 * @date 2018-06-23
 */
public class Baseset extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**  */
	private Integer basesetId;
	/** 主题名称 */
	private String themeName;
	/** 登录背景图片名称 */
	private String loginbgName;
	/** 上传图片修改后的名字，用于预览 */
	private String loginbgFileName;
	/** 登录背景类型 */
	// 0是自带图片或者图片链接，1上传图片，2是特效背景
	private Integer loginbgType;
	/** 首页介绍 */
	private String homeIntroduction;
	/** 爬虫java代码包名前缀 */
	private String spiderJavaPackagePrefix;
	/** 爬虫java代码示例 */
	private String spiderJavaCodeExample;
	/** 爬虫python代码示例 */
	private String spiderPythonCodeExample;
	/** 爬虫javascript代码示例 */
	private String spiderJavascriptCodeExample;
	/** 下载文件名前缀 */
	private String downloadFileNamePrefix;
	/** 网站使用手册内容 */
	private String spiderWebsiteManual;
	/** 网站使用手册类型，0是在线编辑文本，1是文件 */
	private Integer spiderWebsiteManualType;
	/** 爬虫使用手册内容 */
	private String spiderUseManual;
	/** 爬虫使用手册类型，0是在线编辑文本，1是文件 */
	private Integer spiderUseManualType;
	/** 是否显示音乐插件状态 */
	private Integer showMusicToolStatus;
	/** 音乐内容 */
	private String musicInfo;
	/** 音乐设置 */
	// 其它音乐插件设置
	// 是否自动播放
	private Integer musicAutoPlay;
	// 是否开启歌词
	private Integer musicLrcStart;

	// 是否显示歌词
	private Integer musicShowLrc;

	// 是否固定底部
	private Integer musicFixed;

	// 列表是否折叠
	private Integer musicListFolded;

	// 列表最大高度
	private Integer musicListMaxHeight;
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

	@Override
	public String toString() {
		return "Baseset [basesetId=" + basesetId + ", themeName=" + themeName + ", loginbgName=" + loginbgName
				+ ", loginbgFileName=" + loginbgFileName + ", loginbgType=" + loginbgType + ", homeIntroduction="
				+ homeIntroduction + ", spiderJavaPackagePrefix=" + spiderJavaPackagePrefix + ", spiderJavaCodeExample="
				+ spiderJavaCodeExample + ", spiderPythonCodeExample=" + spiderPythonCodeExample
				+ ", spiderJavascriptCodeExample=" + spiderJavascriptCodeExample + ", downloadFileNamePrefix="
				+ downloadFileNamePrefix + ", spiderWebsiteManual=" + spiderWebsiteManual + ", spiderWebsiteManualType="
				+ spiderWebsiteManualType + ", spiderUseManual=" + spiderUseManual + ", spiderUseManualType="
				+ spiderUseManualType + ", showMusicToolStatus=" + showMusicToolStatus + ", musicInfo=" + musicInfo
				+ ", musicAutoPlay=" + musicAutoPlay + ", musicLrcStart=" + musicLrcStart + ", musicShowLrc="
				+ musicShowLrc + ", musicFixed=" + musicFixed + ", musicListFolded=" + musicListFolded
				+ ", musicListMaxHeight=" + musicListMaxHeight + ", status=" + status + ", useStatus=" + useStatus
				+ ", createBy=" + createBy + ", createTime=" + createTime + ", updateBy=" + updateBy + ", updateTime="
				+ updateTime + ", remark=" + remark + "]";
	}

	public Integer getMusicAutoPlay() {
		return musicAutoPlay;
	}

	public void setMusicAutoPlay(Integer musicAutoPlay) {
		this.musicAutoPlay = musicAutoPlay;
	}

	public Integer getMusicLrcStart() {
		return musicLrcStart;
	}

	public void setMusicLrcStart(Integer musicLrcStart) {
		this.musicLrcStart = musicLrcStart;
	}

	public Integer getMusicShowLrc() {
		return musicShowLrc;
	}

	public void setMusicShowLrc(Integer musicShowLrc) {
		this.musicShowLrc = musicShowLrc;
	}

	public Integer getMusicFixed() {
		return musicFixed;
	}

	public void setMusicFixed(Integer musicFixed) {
		this.musicFixed = musicFixed;
	}

	public Integer getMusicListFolded() {
		return musicListFolded;
	}

	public void setMusicListFolded(Integer musicListFolded) {
		this.musicListFolded = musicListFolded;
	}

	public Integer getMusicListMaxHeight() {
		return musicListMaxHeight;
	}

	public void setMusicListMaxHeight(Integer musicListMaxHeight) {
		this.musicListMaxHeight = musicListMaxHeight;
	}

	public String getLoginbgFileName() {
		return loginbgFileName;
	}

	public void setLoginbgFileName(String loginbgFileName) {
		this.loginbgFileName = loginbgFileName;
	}

	public Integer getLoginbgType() {
		return loginbgType;
	}

	public void setLoginbgType(Integer loginbgType) {
		this.loginbgType = loginbgType;
	}

	public String getMusicInfo() {
		return musicInfo;
	}

	public void setMusicInfo(String musicInfo) {
		this.musicInfo = musicInfo;
	}

	public Integer getSpiderWebsiteManualType() {
		return spiderWebsiteManualType;
	}

	public void setSpiderWebsiteManualType(Integer spiderWebsiteManualType) {
		this.spiderWebsiteManualType = spiderWebsiteManualType;
	}

	public Integer getSpiderUseManualType() {
		return spiderUseManualType;
	}

	public void setSpiderUseManualType(Integer spiderUseManualType) {
		this.spiderUseManualType = spiderUseManualType;
	}

	public Integer getShowMusicToolStatus() {
		return showMusicToolStatus;
	}

	public void setShowMusicToolStatus(Integer showMusicToolStatus) {
		this.showMusicToolStatus = showMusicToolStatus;
	}

	/**
	 * 设置：
	 */
	public void setBasesetId(Integer basesetId) {
		this.basesetId = basesetId;
	}

	/**
	 * 获取：
	 */
	public Integer getBasesetId() {
		return basesetId;
	}

	/**
	 * 设置：主题名称
	 */
	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}

	/**
	 * 获取：主题名称
	 */
	public String getThemeName() {
		return themeName;
	}

	/**
	 * 设置：登录背景图片名称
	 */
	public void setLoginbgName(String loginbgName) {
		this.loginbgName = loginbgName;
	}

	/**
	 * 获取：登录背景图片名称
	 */
	public String getLoginbgName() {
		return loginbgName;
	}

	/**
	 * 设置：首页介绍
	 */
	public void setHomeIntroduction(String homeIntroduction) {
		this.homeIntroduction = homeIntroduction;
	}

	/**
	 * 获取：首页介绍
	 */
	public String getHomeIntroduction() {
		return homeIntroduction;
	}

	/**
	 * 设置：爬虫java代码包名前缀
	 */
	public void setSpiderJavaPackagePrefix(String spiderJavaPackagePrefix) {
		this.spiderJavaPackagePrefix = spiderJavaPackagePrefix;
	}

	/**
	 * 获取：爬虫java代码包名前缀
	 */
	public String getSpiderJavaPackagePrefix() {
		return spiderJavaPackagePrefix;
	}

	/**
	 * 设置：爬虫java代码示例
	 */
	public void setSpiderJavaCodeExample(String spiderJavaCodeExample) {
		this.spiderJavaCodeExample = spiderJavaCodeExample;
	}

	/**
	 * 获取：爬虫java代码示例
	 */
	public String getSpiderJavaCodeExample() {
		return spiderJavaCodeExample;
	}

	/**
	 * 设置：爬虫python代码示例
	 */
	public void setSpiderPythonCodeExample(String spiderPythonCodeExample) {
		this.spiderPythonCodeExample = spiderPythonCodeExample;
	}

	/**
	 * 获取：爬虫python代码示例
	 */
	public String getSpiderPythonCodeExample() {
		return spiderPythonCodeExample;
	}

	/**
	 * 设置：爬虫javascript代码示例
	 */
	public void setSpiderJavascriptCodeExample(String spiderJavascriptCodeExample) {
		this.spiderJavascriptCodeExample = spiderJavascriptCodeExample;
	}

	/**
	 * 获取：爬虫javascript代码示例
	 */
	public String getSpiderJavascriptCodeExample() {
		return spiderJavascriptCodeExample;
	}

	/**
	 * 设置：下载文件名前缀
	 */
	public void setDownloadFileNamePrefix(String downloadFileNamePrefix) {
		this.downloadFileNamePrefix = downloadFileNamePrefix;
	}

	/**
	 * 获取：下载文件名前缀
	 */
	public String getDownloadFileNamePrefix() {
		return downloadFileNamePrefix;
	}

	/**
	 * 设置：网站使用手册内容
	 */
	public void setSpiderWebsiteManual(String spiderWebsiteManual) {
		this.spiderWebsiteManual = spiderWebsiteManual;
	}

	/**
	 * 获取：网站使用手册内容
	 */
	public String getSpiderWebsiteManual() {
		return spiderWebsiteManual;
	}

	/**
	 * 设置：爬虫使用手册内容
	 */
	public void setSpiderUseManual(String spiderUseManual) {
		this.spiderUseManual = spiderUseManual;
	}

	/**
	 * 获取：爬虫使用手册内容
	 */
	public String getSpiderUseManual() {
		return spiderUseManual;
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
