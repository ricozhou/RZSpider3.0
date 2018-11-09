package com.rzspider.project.book.intention.domain;

import com.rzspider.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 图书详情表 book_intention
 * 
 * @author ricozhou
 * @date 2018-05-28
 */
public class Intention extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 图书ID */
	private Integer bookId;
	/** 图书名称 */
	private String bookName;
	/** 图书作者 */
	private String bookAuthor;
	/** 图书出版社 */
	private String bookPublisher;
	/** 图书ISBN */
	private String bookIsbn;
	/** 图书简介 */
	private String bookDes;
	/** 图书定价 */
	private String bookOriginalPrice;
	/** 图书分类 */
	private String bookClassification;
	/** 图书装帧 */
	private String bookBinding;
	/** 图书页数 */
	private Integer bookPages;
	/** 购买星级 */
	private Integer bookReadStar;
	/** 用户ID */
	private Integer userId;
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

	/**
	 * 设置：图书ID
	 */
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	/**
	 * 获取：图书ID
	 */
	public Integer getBookId() {
		return bookId;
	}

	/**
	 * 设置：图书名称
	 */
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	/**
	 * 获取：图书名称
	 */
	public String getBookName() {
		return bookName;
	}

	/**
	 * 设置：图书作者
	 */
	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}

	/**
	 * 获取：图书作者
	 */
	public String getBookAuthor() {
		return bookAuthor;
	}

	/**
	 * 设置：图书出版社
	 */
	public void setBookPublisher(String bookPublisher) {
		this.bookPublisher = bookPublisher;
	}

	/**
	 * 获取：图书出版社
	 */
	public String getBookPublisher() {
		return bookPublisher;
	}

	/**
	 * 设置：图书ISBN
	 */
	public void setBookIsbn(String bookIsbn) {
		this.bookIsbn = bookIsbn;
	}

	/**
	 * 获取：图书ISBN
	 */
	public String getBookIsbn() {
		return bookIsbn;
	}

	/**
	 * 设置：图书简介
	 */
	public void setBookDes(String bookDes) {
		this.bookDes = bookDes;
	}

	/**
	 * 获取：图书简介
	 */
	public String getBookDes() {
		return bookDes;
	}

	/**
	 * 设置：图书定价
	 */
	public void setBookOriginalPrice(String bookOriginalPrice) {
		this.bookOriginalPrice = bookOriginalPrice;
	}

	/**
	 * 获取：图书定价
	 */
	public String getBookOriginalPrice() {
		return bookOriginalPrice;
	}

	/**
	 * 设置：图书分类
	 */
	public void setBookClassification(String bookClassification) {
		this.bookClassification = bookClassification;
	}

	/**
	 * 获取：图书分类
	 */
	public String getBookClassification() {
		return bookClassification;
	}

	/**
	 * 设置：图书装帧
	 */
	public void setBookBinding(String bookBinding) {
		this.bookBinding = bookBinding;
	}

	/**
	 * 获取：图书装帧
	 */
	public String getBookBinding() {
		return bookBinding;
	}

	/**
	 * 设置：图书页数
	 */
	public void setBookPages(Integer bookPages) {
		this.bookPages = bookPages;
	}

	/**
	 * 获取：图书页数
	 */
	public Integer getBookPages() {
		return bookPages;
	}

	/**
	 * 设置：阅读星级
	 */
	public void setBookReadStar(Integer bookReadStar) {
		this.bookReadStar = bookReadStar;
	}

	/**
	 * 获取：购买星级
	 */
	public Integer getBookReadStar() {
		return bookReadStar;
	}

	/**
	 * 设置：用户ID
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * 获取：用户ID
	 */
	public Integer getUserId() {
		return userId;
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
