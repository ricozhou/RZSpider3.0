package com.rzspider.framework.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;

/**
 * 
 * @ClassName: WebSocketStompConfig
 * @Description: springboot websocket stomp配置
 */

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketStompConfig extends AbstractWebSocketMessageBrokerConfigurer {

	/**
	 * 注册stomp的端点
	 */
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// 允许使用socketJs方式访问，访问点为webSocketServer，允许跨域
		// 在网页上我们就可以通过这个链接
		// http://localhost:8080/webSocketServer
		// 来和服务器的WebSocket连接
		registry.addEndpoint("/webSocketServer2").setAllowedOrigins("*").withSockJS();
	}

	/**
	 * 配置信息代理
	 */
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		// 订阅Broker名称
		registry.enableSimpleBroker("/topic", "/queue");
		// 全局使用的消息前缀（客户端订阅路径上会体现出来）
		registry.setApplicationDestinationPrefixes("/app");
		// 点对点使用的订阅前缀（客户端订阅路径上会体现出来），不设置的话，默认也是/user/
//		registry.setUserDestinationPrefix("/user/");
	}
	@Override
	public void configureWebSocketTransport(final WebSocketTransportRegistration registration) {
		registration.addDecoratorFactory(new WebSocketHandlerDecoratorFactory() {
			@Override
			public WebSocketHandler decorate(final WebSocketHandler handler) {
				return new WebSocketHandlerDecorator(handler) {
					@Override
					public void afterConnectionEstablished(final WebSocketSession session) throws Exception {
                        // 客户端与服务器端建立连接后，此处记录谁上线了
						String username = session.getPrincipal().getName();
						System.out.println(("online: " + username));
						super.afterConnectionEstablished(session);
					}
 
					@Override
					public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus)
							throws Exception {
                        // 客户端与服务器端断开连接后，此处记录谁下线了
						String username = session.getPrincipal().getName();
						System.out.println("offline: " + username);
						super.afterConnectionClosed(session, closeStatus);
					}
				};
			}
		});
		super.configureWebSocketTransport(registration);
	}
}