package com.rzspider.framework.websocket;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.rzspider.common.constant.WebSocketConstants;

/**
 * HandshakeInterceptor WebSocket握手请求的拦截器. 检查握手请求和响应, 对WebSocketHandler传递属性
 */
// 拦截器主要是用于用户登录标识（userId）的记录，便于后面获取指定用户的会话标识并向指定用户发送消息
public class MyWebSocketInterceptorBlogMove implements HandshakeInterceptor {

	/**
	 * 在握手之前执行该方法, 继续握手返回true, 中断握手返回false. 通过attributes参数设置WebSocketSession的属性
	 */
	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {

		if (request instanceof ServletServerHttpRequest) {
			String userId = ((ServletServerHttpRequest) request).getServletRequest().getParameter(WebSocketConstants.WEBSOCKET_PARAMS_BLOGMOVEID);
			attributes.put(WebSocketConstants.WEBSOCKET_PARAMS_BLOGMOVEID, userId);
		}
		return true;
	}

	/**
	 * 在握手之后执行该方法. 无论是否握手成功都指明了响应状态码和相应头.
	 */
	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {

	}

}