package com.rzspider.project.spider.customspider.utils;

import org.springframework.web.socket.TextMessage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rzspider.common.constant.CommonConstant;
import com.rzspider.common.constant.WebSocketConstants;
import com.rzspider.common.constant.project.SpiderConstant;
import com.rzspider.common.utils.MapUtils;
import com.rzspider.common.utils.spring.SpringUtils;
import com.rzspider.framework.websocket.domain.ReturnMessage;
import com.rzspider.framework.websocket.service.WebSocketPushHandler;
import com.rzspider.project.spider.customspider.domain.Customspider;
import com.rzspider.project.spider.customspider.domain.FileTree;
import com.rzspider.project.spider.customspider.service.CSFileServiceImpl;
import com.rzspider.project.spider.customspider.service.ICSFileService;

//发送线程
public class WebSocketConsoleThread implements Runnable {
	public ConsoleSimulator cs;
	public ConsoleSimulator cs2;
	public boolean isStop = false;
	ICSFileService cSFileService = (ICSFileService) SpringUtils.getBean(ICSFileService.class);

	public WebSocketConsoleThread(ConsoleSimulator cs, ConsoleSimulator cs2) {
		this.cs = cs;
		this.cs2 = cs2;
	}

	@Override
	public void run() {
		// 对象转接送字符串
		ObjectMapper mapper = new ObjectMapper();
		ReturnMessage rm = new ReturnMessage();
		rm.setFlag(SpiderConstant.SPIDER_RETURN_MESSAGE_FLAG_1);
		try {
			while (!isStop && !cs.isStop) {
				rm.setReturnMessage(cs.getReturnPrintContent().trim() + cs2.getReturnErrorContent().trim());
				// 轮询
				WebSocketPushHandler.sendMessageToUser(WebSocketConstants.WEBSOCKET_PARAMS_CSID,
						String.valueOf(cs.customSpiderBackId), new TextMessage(mapper.writeValueAsString(rm)));
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			rm.setReturnMessage(cs.getReturnPrintContent().trim() + cs2.getReturnErrorContent().trim());
			// 最后再轮询一次，为了防止0.5秒反应不及时
			WebSocketPushHandler.sendMessageToUser(WebSocketConstants.WEBSOCKET_PARAMS_CSID,
					String.valueOf(cs.customSpiderBackId), new TextMessage(mapper.writeValueAsString(rm)));
			// 再次发送一条运行完成的消息给前台
			rm.setFlag(SpiderConstant.SPIDER_RETURN_MESSAGE_FLAG_2);
			rm.setReturnMessage(CommonConstant.FINISH);

			WebSocketPushHandler.sendMessageToUser(WebSocketConstants.WEBSOCKET_PARAMS_CSID,
					String.valueOf(cs.customSpiderBackId), new TextMessage(mapper.writeValueAsString(rm)));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 更新表数据,改变状态
			cSFileService.stopCSProcess(new FileTree(cs.customSpiderBackId));
		}
	}

}
