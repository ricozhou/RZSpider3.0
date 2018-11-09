package com.rzspider.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.rzspider.framework.websocket.MyWebSocketInterceptor;
import com.rzspider.framework.websocket.MyWebSocketInterceptorBlogMove;
import com.rzspider.framework.websocket.MyWebSocketInterceptorSpidertask;
import com.rzspider.framework.websocket.service.WebSocketPushHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	/**
	 * 注册WebSocket处理类
	 */
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(createWebSocketPushHandler(), "/websocketconsole")
				.addInterceptors(createHhandshakeInterceptor()).setAllowedOrigins("*");
		registry.addHandler(createWebSocketPushHandler(), "/sockjs/websocketconsole")
				.addInterceptors(createHhandshakeInterceptor()).withSockJS();

		registry.addHandler(createWebSocketPushHandler(), "/websocketspidertask")
				.addInterceptors(createHhandshakeInterceptor2()).setAllowedOrigins("*");
		registry.addHandler(createWebSocketPushHandler(), "/sockjs/websocketspidertask")
				.addInterceptors(createHhandshakeInterceptor2()).withSockJS();

		registry.addHandler(createWebSocketPushHandler(), "/websocketblogmove")
				.addInterceptors(createHhandshakeInterceptor3()).setAllowedOrigins("*");
		registry.addHandler(createWebSocketPushHandler(), "/sockjs/websocketblogmove")
				.addInterceptors(createHhandshakeInterceptor3()).withSockJS();
	}

	/**
	 * 
	 * @Title: createHhandshakeInterceptor
	 * @Description: 握手拦截器
	 * @return
	 */
	// 拦截器主要是用于用户登录标识（userId）的记录，便于后面获取指定用户的会话标识并向指定用户发送消息
	@Bean
	public HandshakeInterceptor createHhandshakeInterceptor() {
		return new MyWebSocketInterceptor();
	}

	/**
	 * 
	 * @Title: createHhandshakeInterceptor
	 * @Description: 握手拦截器
	 * @return
	 */
	// 拦截器主要是用于用户登录标识（userId）的记录，便于后面获取指定用户的会话标识并向指定用户发送消息
	@Bean
	public HandshakeInterceptor createHhandshakeInterceptor2() {
		return new MyWebSocketInterceptorSpidertask();
	}

	/**
	 * 
	 * @Title: createHhandshakeInterceptor
	 * @Description: 握手拦截器
	 * @return
	 */
	// 拦截器主要是用于用户登录标识（userId）的记录，便于后面获取指定用户的会话标识并向指定用户发送消息
	@Bean
	public HandshakeInterceptor createHhandshakeInterceptor3() {
		return new MyWebSocketInterceptorBlogMove();
	}

	/**
	 * 
	 * @Title: createWebSocketPushHandler
	 * @Description: 处理类
	 * @return
	 */
	@Bean
	public WebSocketHandler createWebSocketPushHandler() {
		return new WebSocketPushHandler();
	}

}