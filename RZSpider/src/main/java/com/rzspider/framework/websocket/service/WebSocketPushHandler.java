package com.rzspider.framework.websocket.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * 
 * @ClassName: WebSocketPushHandler
 * @Description: 创建处理器
 */
// 处理类
@Service
public class WebSocketPushHandler extends TextWebSocketHandler {

	public static final List<WebSocketSession> userList = new ArrayList<>();

	/**
	 * 用户进入系统监听
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {

		System.out.println("用户信息:" + session);
		// System.out.println("用户信息:" +
		// session.getAttributes().get("customSpiderBackId"));
		Map<String, Object> map = session.getAttributes();
		for (String key : map.keySet()) {
			System.out.println("key:" + key + " and value:" + map.get(key));
		}
		userList.add(session);
	}

	/**
	 * 处理用户请求
	 */
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.println("系统处理xxx用户的请求信息。。。");
	}

	/**
	 * 用户退出后的处理
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		if (session.isOpen()) {
			session.close();
		}
		userList.remove(session);
		System.out.println("xxx用户退出系统。。。");
	}

	/**
	 * 自定义函数 给所有的在线用户发送消息
	 */
	public static void sendMessagesToUsers(TextMessage message) {
		for (WebSocketSession user : userList) {
			try {
				// isOpen()在线就发送
				if (user.isOpen()) {
					user.sendMessage(message);
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getLocalizedMessage());
			}
		}
	}

	/**
	 * 自定义函数 发送消息给指定的在线用户
	 */
	public static void sendMessageToUser(String paramName, String getParamName, TextMessage message) {
		for (WebSocketSession user : userList) {
			if (user.getAttributes().get(paramName).equals(getParamName)) {
				try {
					// isOpen()在线就发送
					if (user.isOpen()) {
						user.sendMessage(message);
					}
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println(e.getLocalizedMessage());
				}
			}
		}
	}
}
