package com.rzspider.framework.websocket.domain;

public class ReturnMessage{
	public String flag;
	public String returnMessage;

	public ReturnMessage(){
		
	}
	
	public ReturnMessage(String flag, String returnMessage) {
		this.flag = flag;
		this.returnMessage = returnMessage;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getReturnMessage() {
		return returnMessage;
	}

	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}

	@Override
	public String toString() {
		return "ReturnMessage [flag=" + flag + ", returnMessage=" + returnMessage + "]";
	}

}
