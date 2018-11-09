package com.rzspider.implementspider.suzhouhouse.domain.MegAnnoAndTradeModel;

import java.util.List;

public class TradeMessageModel {
	// 左侧信息
	public List<MessageAnnoModel> mamList;
	// 成交名称
	public String tradeName;
	// 右侧信息名称
	public String tradeNameTwo;
	// 右侧详细信息
	public List<TradeRightMessageModel> trmmList;

	public List<MessageAnnoModel> getMamList() {
		return mamList;
	}

	public void setMamList(List<MessageAnnoModel> mamList) {
		this.mamList = mamList;
	}

	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	public String getTradeNameTwo() {
		return tradeNameTwo;
	}

	public void setTradeNameTwo(String tradeNameTwo) {
		this.tradeNameTwo = tradeNameTwo;
	}

	public List<TradeRightMessageModel> getTrmmList() {
		return trmmList;
	}

	public void setTrmmList(List<TradeRightMessageModel> trmmList) {
		this.trmmList = trmmList;
	}

	@Override
	public String toString() {
		return "TradeMessageModel [mamList=" + mamList + ", tradeName=" + tradeName + ", tradeNameTwo=" + tradeNameTwo
				+ ", trmmList=" + trmmList + "]";
	}

}
