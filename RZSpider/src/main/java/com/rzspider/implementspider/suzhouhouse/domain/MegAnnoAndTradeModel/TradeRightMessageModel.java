package com.rzspider.implementspider.suzhouhouse.domain.MegAnnoAndTradeModel;

public class TradeRightMessageModel {
	// 时间
	public String tradeTime;
	// 均价
	public String tradeAveragePrice;

	public String getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}

	public String getTradeAveragePrice() {
		return tradeAveragePrice;
	}

	public void setTradeAveragePrice(String tradeAveragePrice) {
		this.tradeAveragePrice = tradeAveragePrice;
	}

	@Override
	public String toString() {
		return "TradeRightMessageModel [tradeTime=" + tradeTime + ", tradeAveragePrice=" + tradeAveragePrice + "]";
	}

}
