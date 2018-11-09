package com.rzspider.common.utils;

import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

/**
 * @author ricozhou
 * @date Sep 10, 2018 1:47:01 PM
 * @Desc
 */
@Configuration
public class ServiceInfoUtil implements ApplicationListener<EmbeddedServletContainerInitializedEvent> {
	private static EmbeddedServletContainerInitializedEvent event;

	@Override
	public void onApplicationEvent(EmbeddedServletContainerInitializedEvent event) {
		ServiceInfoUtil.event = event;
	}

	public static int getPort() {
		Assert.notNull(event);
		int port = event.getEmbeddedServletContainer().getPort();
		Assert.state(port != -1, "端口号获取失败");
		return port;
	}
}
