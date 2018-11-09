package com.rzspider.framework.WebSocketStomp;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketAction {
	@Autowired
    private SimpMessagingTemplate template;
	@MessageMapping("/welcome")
	@SendTo("/topic/websocket") 
	public ServerMessage sendDemo(ClientMessage message) {
		System.out.println(21);
		return new ServerMessage("你发送的消息为:" + message.getName());
	}

	@SubscribeMapping("/websocket")
	public ServerMessage sub() {
		System.out.println(21);
		this.send(new ServerMessage("test"));
		return new ServerMessage("感谢你订阅了我。。。");
	}
	@MessageMapping("/websocket")
	public ServerMessage sub2() {
		System.out.println(212);
		this.send(new ServerMessage("test"));
		return new ServerMessage("感谢你订阅了我。。。");
	}
	private void send(ServerMessage message) {
		System.out.println(231);
        template.convertAndSendToUser("", "/topic/chat", "");
    }

}
