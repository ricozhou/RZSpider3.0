package com.rzspider.framework.WebSocketStomp;

public class ServerMessage {
	private String responseMessage;

	public ServerMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
}
