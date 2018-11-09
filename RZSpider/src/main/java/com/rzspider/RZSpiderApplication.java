package com.rzspider;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 启动程序
 * 
 * @author ricozhou
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.rzspider.*.*.*.mapper")
public class RZSpiderApplication {

	public static void main(String[] args) {
		SpringApplication.run(RZSpiderApplication.class, args);
		System.out.println(" RZSpider启动成功 \n"
				+ "  .---------.                .-----------.              .-----------.                   \n"
				+ " |           \\               | _ _ __    |              |   _ _ _ _ |                  \n"
				+ " |    ---     \\                      /  /               |  |                           \n"
				+ " |   ( ' )    |                     /  /                |  |                            \n"
				+ " |  (_ o _)   /                    /  /                 |  |_ _ _ _                     \n"
				+ " |   (_,_) . '   __               /  /                  | _ _ _ _   |                   \n"
				+ " |   |  \\  \\    |  |             /  /                            |  |                 \n"
				+ " |   |   \\  \\   /  /            /  /                             |  |                 \n"
				+ " |   |    \\  \\ /  /            /  /                              |  |                 \n"
				+ " |   |     \\  `  /            /  /_ _ _ __              .--------'  |                  \n"
				+ " ''-''      `'-'             |_ _ _ _ _ __|             |_ _ _ _ _ _|                    ");

	}

	// // 以下配置自动转https
	// // 配置https取消以下代码注释，并实现接口：implements EmbeddedServletContainerCustomizer
	// // 拦截所有请求
	// @Bean
	// public EmbeddedServletContainerFactory servletContainer() {
	// TomcatEmbeddedServletContainerFactory tomcat = new
	// TomcatEmbeddedServletContainerFactory() {
	// @Override
	// protected void postProcessContext(Context context) {
	// SecurityConstraint constraint = new SecurityConstraint();
	// constraint.setUserConstraint("CONFIDENTIAL");
	// SecurityCollection collection = new SecurityCollection();
	// collection.addPattern("/*");
	// constraint.addCollection(collection);
	// context.addConstraint(constraint);
	// }
	// };
	// tomcat.addAdditionalTomcatConnectors(httpConnector());
	// return tomcat;
	// }
	//
	// // 配置http转https
	// @Bean
	// public Connector httpConnector() {
	// Connector connector = new
	// Connector(TomcatEmbeddedServletContainerFactory.DEFAULT_PROTOCOL);
	// connector.setScheme("http");
	// // Connector监听的http的端口号
	// connector.setPort(80);
	// connector.setSecure(false);
	// // 监听到http的端口号后转向到的https的端口号
	// connector.setRedirectPort(443);
	// return connector;
	// }
	//
	// // 这里设置默认端口为443，即https的，如果这里不设置，会https和http争夺80端口
	// @Override
	// public void customize(ConfigurableEmbeddedServletContainer container) {
	// container.setPort(443);
	// }
}