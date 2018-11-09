package com.rzspider.project.spider.spidertask.mainwork;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.rzspider.common.constant.CommonSymbolicConstant;
import com.rzspider.implementspider.landmarketnetwork.data.LMNSpiderDataExport;
import com.rzspider.implementspider.landmarketnetwork.domain.LMNLandMessageDetail;
import com.rzspider.implementspider.landmarketnetwork.domain.LMNLandParams;
import com.rzspider.implementspider.suzhouhouse.controller.HouseShowController;
import com.rzspider.implementspider.suzhouhouse.data.HouseQueryDataExport;
import com.rzspider.implementspider.suzhouhouse.data.HouseShowDataExport;
import com.rzspider.implementspider.suzhouhouse.data.MegAnnoAndTradeMegDataExport;
import com.rzspider.implementspider.suzhouhouse.data.PermitPresaleDataExport;
import com.rzspider.implementspider.suzhouhouse.domain.housequery.HouseQueryParams;
import com.rzspider.implementspider.suzhouhouse.domain.houseshow.HouseShowParams;
import com.rzspider.implementspider.suzhouhouse.domain.permitpresale.PermitPresaleParams;
import com.rzspider.implementspider.taobaojud.data.TBJSpiderDataExport;
import com.rzspider.implementspider.taobaojud.domain.TBJLandMessageDetail;
import com.rzspider.project.common.spiderdata.domain.ReturnSpiderDataMessage;
import com.rzspider.project.common.spiderdata.domain.Spiderdata;
import com.rzspider.project.spider.spidertask.mainwork.domain.StartSpiderInfo;
import com.rzspider.project.spider.spidertask.mainwork.utils.InternalSpiderDataUtils;

/**
 * 具体数据解析
 * 
 * @author ricozhou
 */
@Component("internalSpiderData")
public class InternalSpiderData {
	// 需要两个参数，一个是封装信息的，一个是封装数据的
	public ReturnSpiderDataMessage analyzeInternalSpiderData_10001(StartSpiderInfo startSpiderInfo,
			List<Spiderdata> list) {
		return TBJSpiderDataExport.TBJSpiderDataExport(list);
	}

	// 需要两个参数，一个是封装信息的，一个是封装数据的
	public ReturnSpiderDataMessage analyzeInternalSpiderData_10002(StartSpiderInfo startSpiderInfo,
			List<Spiderdata> list) {
		// 解析出json变object参数
		LMNLandParams lMNLandParams = new LMNLandParams();
		lMNLandParams = (LMNLandParams) InternalSpiderDataUtils.jsonStringToObject(startSpiderInfo.getSpiderParams(),
				lMNLandParams);
		return LMNSpiderDataExport.LMNSpiderDataExport(lMNLandParams, list);
	}

	// 苏州房产网交易系统（可售楼盘展示）
	public ReturnSpiderDataMessage analyzeInternalSpiderData_10003(StartSpiderInfo startSpiderInfo,
			List<Spiderdata> list) {
		// 解析出json变object参数
		HouseShowParams houseShowParams = new HouseShowParams();
		houseShowParams = (HouseShowParams) InternalSpiderDataUtils
				.jsonStringToObject(startSpiderInfo.getSpiderParams(), houseShowParams);

		// 传入二个参数，一个是爬虫具体参数，一个是任务详情id以供插入数据库
		// 正式执行任务
		return HouseShowDataExport.houseShowDataExport(houseShowParams, list);

	}

	// 苏州房产网交易系统（预售证查询）
	public ReturnSpiderDataMessage analyzeInternalSpiderData_10004(StartSpiderInfo startSpiderInfo,
			List<Spiderdata> list) {
		// 解析出json变object参数
		HouseQueryParams houseQueryParams = new HouseQueryParams();
		houseQueryParams = (HouseQueryParams) InternalSpiderDataUtils
				.jsonStringToObject(startSpiderInfo.getSpiderParams(), houseQueryParams);

		// 传入二个参数，一个是爬虫具体参数，一个是任务详情id以供插入数据库
		// 正式执行任务
		return HouseQueryDataExport.houseQueryDataExport(houseQueryParams, list);

	}

	// 苏州房产网交易系统（预售证查询）
	public ReturnSpiderDataMessage analyzeInternalSpiderData_10005(StartSpiderInfo startSpiderInfo,
			List<Spiderdata> list) {
		// 解析出json变object参数
		PermitPresaleParams permitPresaleParams = new PermitPresaleParams();
		permitPresaleParams = (PermitPresaleParams) InternalSpiderDataUtils
				.jsonStringToObject(startSpiderInfo.getSpiderParams(), permitPresaleParams);

		// 传入二个参数，一个是爬虫具体参数，一个是任务详情id以供插入数据库
		// 正式执行任务
		return PermitPresaleDataExport.permitPresaleDataExport(permitPresaleParams, list);

	}

	// 苏州房产网交易系统（预售公示）
	public ReturnSpiderDataMessage analyzeInternalSpiderData_10006(StartSpiderInfo startSpiderInfo,
			List<Spiderdata> list) {
		// 正式执行任务
		return MegAnnoAndTradeMegDataExport.megAnnoDataExport(list);

	}

	// 苏州房产网交易系统（即时成交）
	public ReturnSpiderDataMessage analyzeInternalSpiderData_10007(StartSpiderInfo startSpiderInfo,
			List<Spiderdata> list) {
		// 正式执行任务
		return MegAnnoAndTradeMegDataExport.tradeMegDataExport(list);

	}

}
