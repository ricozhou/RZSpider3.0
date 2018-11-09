package com.rzspider.project.commontool.toolrun.domain;

//二维码实体类
public class ORCode extends CommonToolEntity {
	// 是否插入logo
	public boolean insertLogo;
	// logo地址
	public String logoImageUrl;
	// 二维码尺寸
	public int orCodeSizeW;
	public int orCodeSizeH;
	// 二维码格式
	public String orCodeImgFormat;

	public boolean isInsertLogo() {
		return insertLogo;
	}

	public void setInsertLogo(boolean insertLogo) {
		this.insertLogo = insertLogo;
	}

	public String getLogoImageUrl() {
		return logoImageUrl;
	}

	public void setLogoImageUrl(String logoImageUrl) {
		this.logoImageUrl = logoImageUrl;
	}

	public int getOrCodeSizeW() {
		return orCodeSizeW;
	}

	public void setOrCodeSizeW(int orCodeSizeW) {
		this.orCodeSizeW = orCodeSizeW;
	}

	public int getOrCodeSizeH() {
		return orCodeSizeH;
	}

	public void setOrCodeSizeH(int orCodeSizeH) {
		this.orCodeSizeH = orCodeSizeH;
	}

	public String getOrCodeImgFormat() {
		return orCodeImgFormat;
	}

	public void setOrCodeImgFormat(String orCodeImgFormat) {
		this.orCodeImgFormat = orCodeImgFormat;
	}

	@Override
	public String toString() {
		return "ORCode [insertLogo=" + insertLogo + ", logoImageUrl=" + logoImageUrl + ", orCodeSizeW=" + orCodeSizeW
				+ ", orCodeSizeH=" + orCodeSizeH + ", orCodeImgFormat=" + orCodeImgFormat + ", getToolBackId()="
				+ getToolBackId() + ", getContent()=" + getContent() + ", getExportContent()=" + getExportContent()
				+ ", getImageUrl()=" + getImageUrl() + "]";
	}

}
