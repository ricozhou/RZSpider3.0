package com.rzspider.project.commontool.toolrun.domain;

public class ImgToChar extends CommonToolEntity {
	public int sfImgSizeW;
	public int sfImgSizeH;
	public int createImgSizeW;
	public int createImgSizeH;
	public String charArray;
	public int charSize;
	public int imgIntensity;
	// gif
	public boolean gifImg;
	public String imgPre;

	public String getImgPre() {
		return imgPre;
	}

	public void setImgPre(String imgPre) {
		this.imgPre = imgPre;
	}

	public int getSfImgSizeW() {
		return sfImgSizeW;
	}

	public void setSfImgSizeW(int sfImgSizeW) {
		this.sfImgSizeW = sfImgSizeW;
	}

	public int getSfImgSizeH() {
		return sfImgSizeH;
	}

	public void setSfImgSizeH(int sfImgSizeH) {
		this.sfImgSizeH = sfImgSizeH;
	}

	public int getCreateImgSizeW() {
		return createImgSizeW;
	}

	public void setCreateImgSizeW(int createImgSizeW) {
		this.createImgSizeW = createImgSizeW;
	}

	public int getCreateImgSizeH() {
		return createImgSizeH;
	}

	public void setCreateImgSizeH(int createImgSizeH) {
		this.createImgSizeH = createImgSizeH;
	}

	public String getCharArray() {
		return charArray;
	}

	public void setCharArray(String charArray) {
		this.charArray = charArray;
	}

	public int getCharSize() {
		return charSize;
	}

	public void setCharSize(int charSize) {
		this.charSize = charSize;
	}

	public int getImgIntensity() {
		return imgIntensity;
	}

	public void setImgIntensity(int imgIntensity) {
		this.imgIntensity = imgIntensity;
	}

	public boolean isGifImg() {
		return gifImg;
	}

	public void setGifImg(boolean gifImg) {
		this.gifImg = gifImg;
	}

	@Override
	public String toString() {
		return "ImgToChar [sfImgSizeW=" + sfImgSizeW + ", sfImgSizeH=" + sfImgSizeH + ", createImgSizeW="
				+ createImgSizeW + ", createImgSizeH=" + createImgSizeH + ", charArray=" + charArray + ", charSize="
				+ charSize + ", imgIntensity=" + imgIntensity + ", gifImg=" + gifImg + ", imgPre=" + imgPre
				+ ", getToolBackId()=" + getToolBackId() + ", getContent()=" + getContent() + ", getExportContent()="
				+ getExportContent() + ", getImageUrl()=" + getImageUrl() + "]";
	}

}
