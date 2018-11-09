package com.rzspider.project.tool.straightlinkmanage.domain;

import com.rzspider.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 直链管理详情表 tool_straightlinkmanage
 * 
 * @author ricozhou
 * @date 2018-10-16
 */
public class Straightlinkmanage extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private Integer straightlinkmanageId;
	/** 直链文件名称 */
	private String straightlinkFileName;
	/** 直链文件uuid名称 */
	private String straightlinkFileUuidName;
	/** 直链文件类型 */
	private Integer straightlinkFileType;
	/** 直链文件后缀 */
	private String straightlinkFileExtensionName;
	/** 直链文件大小 */
	private String straightlinkFileSize;
	/** 直链key */
	private String straightlinkFileUrlKey;
	/** 直链文件外链 */
	private String straightlinkFileUrl;
	/** 直链访问次数 */
	private Integer straightlinkFileDownNum;
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

	/**
	 * 设置：
	 */
	public void setStraightlinkmanageId(Integer straightlinkmanageId) 
	{
		this.straightlinkmanageId = straightlinkmanageId;
	}
	
	/**
	 * 获取：
	 */
	public Integer getStraightlinkmanageId() 
	{
		return straightlinkmanageId;
	}
	
	/**
	 * 设置：直链文件名称
	 */
	public void setStraightlinkFileName(String straightlinkFileName) 
	{
		this.straightlinkFileName = straightlinkFileName;
	}
	
	/**
	 * 获取：直链文件名称
	 */
	public String getStraightlinkFileName() 
	{
		return straightlinkFileName;
	}
	
	/**
	 * 设置：直链文件uuid名称
	 */
	public void setStraightlinkFileUuidName(String straightlinkFileUuidName) 
	{
		this.straightlinkFileUuidName = straightlinkFileUuidName;
	}
	
	/**
	 * 获取：直链文件uuid名称
	 */
	public String getStraightlinkFileUuidName() 
	{
		return straightlinkFileUuidName;
	}
	
	/**
	 * 设置：直链文件类型
	 */
	public void setStraightlinkFileType(Integer straightlinkFileType) 
	{
		this.straightlinkFileType = straightlinkFileType;
	}
	
	/**
	 * 获取：直链文件类型
	 */
	public Integer getStraightlinkFileType() 
	{
		return straightlinkFileType;
	}
	
	/**
	 * 设置：直链文件后缀
	 */
	public void setStraightlinkFileExtensionName(String straightlinkFileExtensionName) 
	{
		this.straightlinkFileExtensionName = straightlinkFileExtensionName;
	}
	
	/**
	 * 获取：直链文件后缀
	 */
	public String getStraightlinkFileExtensionName() 
	{
		return straightlinkFileExtensionName;
	}
	
	/**
	 * 设置：直链文件大小
	 */
	public void setStraightlinkFileSize(String straightlinkFileSize) 
	{
		this.straightlinkFileSize = straightlinkFileSize;
	}
	
	/**
	 * 获取：直链文件大小
	 */
	public String getStraightlinkFileSize() 
	{
		return straightlinkFileSize;
	}
	
	/**
	 * 设置：直链key
	 */
	public void setStraightlinkFileUrlKey(String straightlinkFileUrlKey) 
	{
		this.straightlinkFileUrlKey = straightlinkFileUrlKey;
	}
	
	/**
	 * 获取：直链key
	 */
	public String getStraightlinkFileUrlKey() 
	{
		return straightlinkFileUrlKey;
	}
	
	/**
	 * 设置：直链文件外链
	 */
	public void setStraightlinkFileUrl(String straightlinkFileUrl) 
	{
		this.straightlinkFileUrl = straightlinkFileUrl;
	}
	
	/**
	 * 获取：直链文件外链
	 */
	public String getStraightlinkFileUrl() 
	{
		return straightlinkFileUrl;
	}
	
	/**
	 * 设置：直链访问次数
	 */
	public void setStraightlinkFileDownNum(Integer straightlinkFileDownNum) 
	{
		this.straightlinkFileDownNum = straightlinkFileDownNum;
	}
	
	/**
	 * 获取：直链访问次数
	 */
	public Integer getStraightlinkFileDownNum() 
	{
		return straightlinkFileDownNum;
	}
	
	/**
	 * 设置：可用状态
	 */
	public void setStatus(Integer status) 
	{
		this.status = status;
	}
	
	/**
	 * 获取：可用状态
	 */
	public Integer getStatus() 
	{
		return status;
	}
	
	/**
	 * 设置：使用状态
	 */
	public void setUseStatus(Integer useStatus) 
	{
		this.useStatus = useStatus;
	}
	
	/**
	 * 获取：使用状态
	 */
	public Integer getUseStatus() 
	{
		return useStatus;
	}
	
	/**
	 * 设置：创建者
	 */
	public void setCreateBy(String createBy) 
	{
		this.createBy = createBy;
	}
	
	/**
	 * 获取：创建者
	 */
	public String getCreateBy() 
	{
		return createBy;
	}
	
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) 
	{
		this.createTime = createTime;
	}
	
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() 
	{
		return createTime;
	}
	
	/**
	 * 设置：更新者
	 */
	public void setUpdateBy(String updateBy) 
	{
		this.updateBy = updateBy;
	}
	
	/**
	 * 获取：更新者
	 */
	public String getUpdateBy() 
	{
		return updateBy;
	}
	
	/**
	 * 设置：更新时间
	 */
	public void setUpdateTime(Date updateTime) 
	{
		this.updateTime = updateTime;
	}
	
	/**
	 * 获取：更新时间
	 */
	public Date getUpdateTime() 
	{
		return updateTime;
	}
	
	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) 
	{
		this.remark = remark;
	}
	
	/**
	 * 获取：备注
	 */
	public String getRemark() 
	{
		return remark;
	}
	
}
