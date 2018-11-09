package com.rzspider.project.spider.spidertask.mainwork;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.stereotype.Component;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.rzspider.common.utils.spring.SpringUtils;
import com.rzspider.implementspider.landmarketnetwork.controller.LMNSpiderTaskController;
import com.rzspider.project.common.spiderdata.domain.Spiderdata;
import com.rzspider.project.common.spiderdata.service.ISpiderdataService;
import com.rzspider.project.spider.spidertask.domain.Spidertaskinfo;
import com.rzspider.project.spider.spidertask.mainwork.domain.StartSpiderInfo;
import com.rzspider.project.spider.spidertask.mainwork.utils.InternalSpiderDataUtils;
import com.rzspider.project.spider.spidertask.mainwork.utils.SpiderTaskThreadUtils;
import com.rzspider.project.spider.spidertask.service.ISpidertaskinfoService;
import com.rzspider.project.spider.spidertask.utils.SpiderScheduleJob;
import com.rzspider.implementspider.landmarketnetwork.domain.LMNLandParams;
import com.rzspider.implementspider.suzhouhouse.controller.HouseQueryController;
import com.rzspider.implementspider.suzhouhouse.controller.HouseShowController;
import com.rzspider.implementspider.suzhouhouse.controller.MegAnnoAndTradeMegController;
import com.rzspider.implementspider.suzhouhouse.controller.PermitPresaleController;
import com.rzspider.implementspider.suzhouhouse.domain.housequery.HouseQueryParams;
import com.rzspider.implementspider.suzhouhouse.domain.houseshow.HouseShowParams;
import com.rzspider.implementspider.suzhouhouse.domain.permitpresale.PermitPresaleParams;
import com.rzspider.implementspider.taobaojud.controller.TBJSpiderTaskController;
import com.rzspider.implementspider.taobaojud.domain.TBJLandParams;

/**
 * 定时任务调度
 * 
 * @author ricozhou
 */
@Component("internalSpiderTask")
public class InternalSpiderTask {

	// 内置爬虫控制器
	// 淘宝司法拍卖
	public void startInternalSpider_10001(StartSpiderInfo startSpiderInfo)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		System.out.println(startSpiderInfo);
		// 解析出json变object参数
		TBJLandParams tBJLandParams = new TBJLandParams();
		tBJLandParams = (TBJLandParams) InternalSpiderDataUtils.jsonStringToObject(startSpiderInfo.getSpiderParams(),
				tBJLandParams);
		// 传入二个参数，一个是爬虫具体参数，一个是任务详情id以供插入数据库
		// 正式执行任务
		new TBJSpiderTaskController().TBJSpiderTaskController(tBJLandParams, startSpiderInfo.getTaskInfoId());

	}

	// 内置爬虫控制器
	// 全国各地土地资源
	public void startInternalSpider_10002(StartSpiderInfo startSpiderInfo)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		System.out.println(startSpiderInfo);
		// 解析出json变object参数
		LMNLandParams lMNLandParams = new LMNLandParams();
		lMNLandParams = (LMNLandParams) InternalSpiderDataUtils.jsonStringToObject(startSpiderInfo.getSpiderParams(),
				lMNLandParams);

		// 传入二个参数，一个是爬虫具体参数，一个是任务详情id以供插入数据库
		// 正式执行任务
		new LMNSpiderTaskController().LMNSpiderTaskController(lMNLandParams, startSpiderInfo.getTaskInfoId());

	}

	// 内置爬虫控制器
	// 苏州房产网交易系统（可售楼盘展示）
	public void startInternalSpider_10003(StartSpiderInfo startSpiderInfo)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		System.out.println(startSpiderInfo);
		// 解析出json变object参数
		HouseShowParams houseShowParams = new HouseShowParams();
		houseShowParams = (HouseShowParams) InternalSpiderDataUtils
				.jsonStringToObject(startSpiderInfo.getSpiderParams(), houseShowParams);

		// 传入二个参数，一个是爬虫具体参数，一个是任务详情id以供插入数据库
		// 正式执行任务
		new HouseShowController().houseShowController(houseShowParams, startSpiderInfo.getTaskInfoId());

	}

	// 内置爬虫控制器
	// 苏州房产网交易系统（可售房源查询）
	public void startInternalSpider_10004(StartSpiderInfo startSpiderInfo)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		System.out.println(startSpiderInfo);
		// 解析出json变object参数
		HouseQueryParams houseQueryParams = new HouseQueryParams();
		houseQueryParams = (HouseQueryParams) InternalSpiderDataUtils
				.jsonStringToObject(startSpiderInfo.getSpiderParams(), houseQueryParams);
		// 传入二个参数，一个是爬虫具体参数，一个是任务详情id以供插入数据库
		// 正式执行任务
		new HouseQueryController().houseQueryController(houseQueryParams, startSpiderInfo.getTaskInfoId());

	}

	// 内置爬虫控制器
	// 苏州房产网交易系统（预售证查询）
	public void startInternalSpider_10005(StartSpiderInfo startSpiderInfo)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		System.out.println(startSpiderInfo);
		// 解析出json变object参数
		PermitPresaleParams permitPresaleParams = new PermitPresaleParams();
		permitPresaleParams = (PermitPresaleParams) InternalSpiderDataUtils
				.jsonStringToObject(startSpiderInfo.getSpiderParams(), permitPresaleParams);

		// 传入二个参数，一个是爬虫具体参数，一个是任务详情id以供插入数据库
		// 正式执行任务
		new PermitPresaleController().permitPresaleController(permitPresaleParams, startSpiderInfo.getTaskInfoId());

	}

	// 内置爬虫控制器
	// 苏州房产网交易系统（可售信息公示查询）
	public void startInternalSpider_10006(StartSpiderInfo startSpiderInfo)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		System.out.println(startSpiderInfo);

		// 正式执行任务
		new MegAnnoAndTradeMegController().megAnnoController(startSpiderInfo.getTaskInfoId());

	}

	// 内置爬虫控制器
	// 苏州房产网交易系统（即时信息查询）
	public void startInternalSpider_10007(StartSpiderInfo startSpiderInfo)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		System.out.println(startSpiderInfo);
		// 正式执行任务
		new MegAnnoAndTradeMegController().tradeMegController(startSpiderInfo.getTaskInfoId());

	}
}
