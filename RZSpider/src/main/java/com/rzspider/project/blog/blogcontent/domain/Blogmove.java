package com.rzspider.project.blog.blogcontent.domain;

import com.rzspider.framework.web.domain.BaseEntity;
import com.rzspider.project.blog.blogset.domain.Blogset;

import java.util.Date;

/**
 * 博客搬家详情表 blog_blogmove
 * 
 * @author ricozhou
 * @date 2018-10-19
 */
public class Blogmove extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** id */
	private Integer blogMoveId;
	/** 网站id */
	private String moveWebsiteId;
	/** 博客用户id */
	private String moveUserId;
	/** 爬取条数 */
	private Integer moveNum;
	/** 默认编辑器 */
	private Integer moveArticleEditor;
	/** 文章类型 */
	private String moveArticleType;

	/** 专栏 */
	private String moveColumn;

	/** 是否发布 */
	private Integer moveBlogStatus;
	/** 是否使用原时间 */
	private Integer moveUseOriginalTime;
	/** 是否保存图片 */
	private Integer moveSaveImg;
	/** 是否去除重复 */
	private Integer moveRemoveRepeat;
	/** 另加标志 */
	private Integer flag;
	/** 中止方式 */
	private Integer moveStopMode;
	/** 是否成功搬家 */
	private Integer moveSuccess;
	/** 搬家文章条数 */
	private Integer moveSuccessNum;
	/** 搬家日志信息 */
	private String moveMessage;

	/** 启动运行模式 */
	private Integer moveMode;

	/** 网站url前缀 */
	private String moveWebsiteUrl;

	/** 上传文件集合 */
	private String moveFileNames;
	/** 上传文件原名集合 */
	private String moveFileONames;

	/** 图片是否打水印 */
	private Integer moveAddWaterMark;
	/** 创建者 */
	private String createBy;
	/** 创建时间 */
	private Date createTime;
	/** 结束时间 */
	private Date finishTime;
	/** 消耗时间 */
	private String takeTime;
	/** 备注 */
	private String remark;
	/** 设置信息 */
	private Blogset blogset;

	/** word导入作者 */
	private String blogAuthor;

	public String getMoveArticleType() {
		return moveArticleType;
	}

	public void setMoveArticleType(String moveArticleType) {
		this.moveArticleType = moveArticleType;
	}

	public String getBlogAuthor() {
		return blogAuthor;
	}

	public void setBlogAuthor(String blogAuthor) {
		this.blogAuthor = blogAuthor;
	}

	public String getMoveFileONames() {
		return moveFileONames;
	}

	public void setMoveFileONames(String moveFileONames) {
		this.moveFileONames = moveFileONames;
	}

	public Blogset getBlogset() {
		return blogset;
	}

	public void setBlogset(Blogset blogset) {
		this.blogset = blogset;
	}

	public Integer getMoveMode() {
		return moveMode;
	}

	public void setMoveMode(Integer moveMode) {
		this.moveMode = moveMode;
	}

	public String getMoveWebsiteUrl() {
		return moveWebsiteUrl;
	}

	public void setMoveWebsiteUrl(String moveWebsiteUrl) {
		this.moveWebsiteUrl = moveWebsiteUrl;
	}

	public String getMoveFileNames() {
		return moveFileNames;
	}

	public void setMoveFileNames(String moveFileNames) {
		this.moveFileNames = moveFileNames;
	}

	public Integer getMoveAddWaterMark() {
		return moveAddWaterMark;
	}

	public void setMoveAddWaterMark(Integer moveAddWaterMark) {
		this.moveAddWaterMark = moveAddWaterMark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getMoveMessage() {
		return moveMessage;
	}

	public void setMoveMessage(String moveMessage) {
		this.moveMessage = moveMessage;
	}

	public String getMoveWebsiteId() {
		return moveWebsiteId;
	}

	public void setMoveWebsiteId(String moveWebsiteId) {
		this.moveWebsiteId = moveWebsiteId;
	}

	public String getMoveUserId() {
		return moveUserId;
	}

	public void setMoveUserId(String moveUserId) {
		this.moveUserId = moveUserId;
	}

	public Integer getMoveNum() {
		return moveNum;
	}

	public void setMoveNum(Integer moveNum) {
		this.moveNum = moveNum;
	}

	public Integer getMoveArticleEditor() {
		return moveArticleEditor;
	}

	public void setMoveArticleEditor(Integer moveArticleEditor) {
		this.moveArticleEditor = moveArticleEditor;
	}

	public String getMoveColumn() {
		return moveColumn;
	}

	public void setMoveColumn(String moveColumn) {
		this.moveColumn = moveColumn;
	}

	public Integer getMoveBlogStatus() {
		return moveBlogStatus;
	}

	public void setMoveBlogStatus(Integer moveBlogStatus) {
		this.moveBlogStatus = moveBlogStatus;
	}

	public Integer getMoveUseOriginalTime() {
		return moveUseOriginalTime;
	}

	public void setMoveUseOriginalTime(Integer moveUseOriginalTime) {
		this.moveUseOriginalTime = moveUseOriginalTime;
	}

	public Integer getMoveSaveImg() {
		return moveSaveImg;
	}

	public void setMoveSaveImg(Integer moveSaveImg) {
		this.moveSaveImg = moveSaveImg;
	}

	public Integer getMoveRemoveRepeat() {
		return moveRemoveRepeat;
	}

	public void setMoveRemoveRepeat(Integer moveRemoveRepeat) {
		this.moveRemoveRepeat = moveRemoveRepeat;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	/**
	 * 设置：id
	 */
	public void setBlogMoveId(Integer blogMoveId) {
		this.blogMoveId = blogMoveId;
	}

	/**
	 * 获取：id
	 */
	public Integer getBlogMoveId() {
		return blogMoveId;
	}

	/**
	 * 设置：中止方式
	 */
	public void setMoveStopMode(Integer moveStopMode) {
		this.moveStopMode = moveStopMode;
	}

	@Override
	public String toString() {
		return "Blogmove [blogMoveId=" + blogMoveId + ", moveWebsiteId=" + moveWebsiteId + ", moveUserId=" + moveUserId
				+ ", moveNum=" + moveNum + ", moveArticleEditor=" + moveArticleEditor + ", moveArticleType="
				+ moveArticleType + ", moveColumn=" + moveColumn + ", moveBlogStatus=" + moveBlogStatus
				+ ", moveUseOriginalTime=" + moveUseOriginalTime + ", moveSaveImg=" + moveSaveImg
				+ ", moveRemoveRepeat=" + moveRemoveRepeat + ", flag=" + flag + ", moveStopMode=" + moveStopMode
				+ ", moveSuccess=" + moveSuccess + ", moveSuccessNum=" + moveSuccessNum + ", moveMessage=" + moveMessage
				+ ", moveMode=" + moveMode + ", moveWebsiteUrl=" + moveWebsiteUrl + ", moveFileNames=" + moveFileNames
				+ ", moveFileONames=" + moveFileONames + ", moveAddWaterMark=" + moveAddWaterMark + ", createBy="
				+ createBy + ", createTime=" + createTime + ", finishTime=" + finishTime + ", takeTime=" + takeTime
				+ ", remark=" + remark + ", blogset=" + blogset + ", blogAuthor=" + blogAuthor + "]";
	}

	/**
	 * 获取：中止方式
	 */
	public Integer getMoveStopMode() {
		return moveStopMode;
	}

	/**
	 * 设置：是否成功搬家
	 */
	public void setMoveSuccess(Integer moveSuccess) {
		this.moveSuccess = moveSuccess;
	}

	/**
	 * 获取：是否成功搬家
	 */
	public Integer getMoveSuccess() {
		return moveSuccess;
	}

	/**
	 * 设置：搬家文章条数
	 */
	public void setMoveSuccessNum(Integer moveSuccessNum) {
		this.moveSuccessNum = moveSuccessNum;
	}

	/**
	 * 获取：搬家文章条数
	 */
	public Integer getMoveSuccessNum() {
		return moveSuccessNum;
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
	 * 设置：结束时间
	 */
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	/**
	 * 获取：结束时间
	 */
	public Date getFinishTime() {
		return finishTime;
	}

	/**
	 * 设置：消耗时间
	 */
	public void setTakeTime(String takeTime) {
		this.takeTime = takeTime;
	}

	/**
	 * 获取：消耗时间
	 */
	public String getTakeTime() {
		return takeTime;
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
