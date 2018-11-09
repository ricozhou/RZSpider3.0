package com.rzspider.implementspider.suzhouhouse.domain.housequery;

//房屋详情
public class HouseQueryDetailInfo {

	// 座号
	public String seatNumber;
	// 层次
	public String level;
	// 所属自然层
	public String naturalLayer;
	// 单元号
	public String unitNumber;
	// 室号(编号)
	public String roomNumber;
	// 套型
	public String nesting;
	// 房屋性质
	public String propertyNature;
	// 类型
	public String typesOf;
	// 用途
	public String use;
	// 建成日期
	public String dateCompletion;
	// 结构类型
	public String structureType;
	// 房屋所有权登记证明编号
	public String horcn;
	// 土地分割转让许可证编号
	public String ldtln;
	// 抵押情况
	public String mortgage;
	// 装修情况
	public String renovationCondition;
	// 抵押起始日期
	public String mortgageStartDate;
	// 抵押终止日期
	public String mortgageTerminationDate;
	// 套内建筑面积（平方米）
	public String inbuildingArea;
	// 分摊建筑面积（平方米）：
	public String distributionBuildingArea;
	// 总建筑面积（平方米）
	public String totalFloorArea;
	// 总价格（元）
	public String totalPrice;
	// 单价（元/平方米）
	public String unitPrice;
	// 公安门牌号
	public String publicSecurityNumber;
	// 房屋层高是否超过2.2米
	public String heightThanTPTmeters;
	// 房屋层高（米）
	public String houseHeight;

	public String getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getNaturalLayer() {
		return naturalLayer;
	}

	public void setNaturalLayer(String naturalLayer) {
		this.naturalLayer = naturalLayer;
	}

	public String getUnitNumber() {
		return unitNumber;
	}

	public void setUnitNumber(String unitNumber) {
		this.unitNumber = unitNumber;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getNesting() {
		return nesting;
	}

	public void setNesting(String nesting) {
		this.nesting = nesting;
	}

	public String getPropertyNature() {
		return propertyNature;
	}

	public void setPropertyNature(String propertyNature) {
		this.propertyNature = propertyNature;
	}

	public String getTypesOf() {
		return typesOf;
	}

	public void setTypesOf(String typesOf) {
		this.typesOf = typesOf;
	}

	public String getUse() {
		return use;
	}

	public void setUse(String use) {
		this.use = use;
	}

	public String getDateCompletion() {
		return dateCompletion;
	}

	public void setDateCompletion(String dateCompletion) {
		this.dateCompletion = dateCompletion;
	}

	public String getStructureType() {
		return structureType;
	}

	public void setStructureType(String structureType) {
		this.structureType = structureType;
	}

	public String getHorcn() {
		return horcn;
	}

	public void setHorcn(String horcn) {
		this.horcn = horcn;
	}

	public String getLdtln() {
		return ldtln;
	}

	public void setLdtln(String ldtln) {
		this.ldtln = ldtln;
	}

	public String getMortgage() {
		return mortgage;
	}

	public void setMortgage(String mortgage) {
		this.mortgage = mortgage;
	}

	public String getRenovationCondition() {
		return renovationCondition;
	}

	public void setRenovationCondition(String renovationCondition) {
		this.renovationCondition = renovationCondition;
	}

	public String getMortgageStartDate() {
		return mortgageStartDate;
	}

	public void setMortgageStartDate(String mortgageStartDate) {
		this.mortgageStartDate = mortgageStartDate;
	}

	public String getMortgageTerminationDate() {
		return mortgageTerminationDate;
	}

	public void setMortgageTerminationDate(String mortgageTerminationDate) {
		this.mortgageTerminationDate = mortgageTerminationDate;
	}

	public String getInbuildingArea() {
		return inbuildingArea;
	}

	public void setInbuildingArea(String inbuildingArea) {
		this.inbuildingArea = inbuildingArea;
	}

	public String getDistributionBuildingArea() {
		return distributionBuildingArea;
	}

	public void setDistributionBuildingArea(String distributionBuildingArea) {
		this.distributionBuildingArea = distributionBuildingArea;
	}

	public String getTotalFloorArea() {
		return totalFloorArea;
	}

	public void setTotalFloorArea(String totalFloorArea) {
		this.totalFloorArea = totalFloorArea;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getPublicSecurityNumber() {
		return publicSecurityNumber;
	}

	public void setPublicSecurityNumber(String publicSecurityNumber) {
		this.publicSecurityNumber = publicSecurityNumber;
	}

	public String getHeightThanTPTmeters() {
		return heightThanTPTmeters;
	}

	public void setHeightThanTPTmeters(String heightThanTPTmeters) {
		this.heightThanTPTmeters = heightThanTPTmeters;
	}

	public String getHouseHeight() {
		return houseHeight;
	}

	public void setHouseHeight(String houseHeight) {
		this.houseHeight = houseHeight;
	}

	@Override
	public String toString() {
		return "HouseQueryDetailInfo [seatNumber=" + seatNumber + ", level=" + level + ", naturalLayer=" + naturalLayer
				+ ", unitNumber=" + unitNumber + ", roomNumber=" + roomNumber + ", nesting=" + nesting
				+ ", propertyNature=" + propertyNature + ", typesOf=" + typesOf + ", use=" + use + ", dateCompletion="
				+ dateCompletion + ", structureType=" + structureType + ", horcn=" + horcn + ", ldtln=" + ldtln
				+ ", mortgage=" + mortgage + ", renovationCondition=" + renovationCondition + ", mortgageStartDate="
				+ mortgageStartDate + ", mortgageTerminationDate=" + mortgageTerminationDate + ", inbuildingArea="
				+ inbuildingArea + ", distributionBuildingArea=" + distributionBuildingArea + ", totalFloorArea="
				+ totalFloorArea + ", totalPrice=" + totalPrice + ", unitPrice=" + unitPrice + ", publicSecurityNumber="
				+ publicSecurityNumber + ", heightThanTPTmeters=" + heightThanTPTmeters + ", houseHeight=" + houseHeight
				+ "]";
	}

}
