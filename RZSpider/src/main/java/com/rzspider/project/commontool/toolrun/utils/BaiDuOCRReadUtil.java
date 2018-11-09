package com.rzspider.project.commontool.toolrun.utils;

import java.io.File;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.baidu.aip.ocr.AipOcr;
import com.rzspider.common.constant.CommonSymbolicConstant;
import com.rzspider.common.constant.ThirdPartyCallConstant;
import com.rzspider.project.commontool.toolmanage.domain.Toolset;

public class BaiDuOCRReadUtil {
	// 识别
	public static String recognizeTextByBaiduOCR(Toolset toolset, String filePath, boolean isLocalImg)
			throws Exception {
		// 实例化baiduocr对象
		AipOcr client = new AipOcr(toolset.getToolOcrsetBaiduocrApiId(), toolset.getToolOcrsetBaiduocrApiKey(),
				toolset.getToolOcrsetBaiduocrSecretKey());
		// 调用接口
		// 注意百度的识别文字，本地图片和图片数组使用的是basicGeneral()方法,远程图片使用的是basicGeneralUrl()方法，并未重载
		JSONObject res;
		if (isLocalImg) {
			res = client.basicGeneral(filePath, new HashMap<String, String>());
		} else {
			res = client.basicGeneralUrl(filePath, new HashMap<String, String>());
		}
		// 结果
		String searchResult = res.toString();
		if (searchResult.contains(ThirdPartyCallConstant.THIRD_PARTY_CALL_BAIDU_OCR_KEYWORD_3)) {
			return null;
		}
		// 正常结果
		JSONArray jsonArray = res.getJSONArray(ThirdPartyCallConstant.THIRD_PARTY_CALL_BAIDU_OCR_KEYWORD_1);
		StringBuilder sb = new StringBuilder();
		// 取出json拼接字符串
		JSONObject ob;
		for (int i = 0; i < jsonArray.length(); i++) {
			ob = (JSONObject) jsonArray.get(i);
			sb.append(ob.getString(ThirdPartyCallConstant.THIRD_PARTY_CALL_BAIDU_OCR_KEYWORD_2));
			sb.append(CommonSymbolicConstant.LINEBREAK2);
		}
		// 返回
		return sb.toString();
	}

	// TesseractOCR
	public static String recognizeTextByTesseractOCR(Toolset toolset, String imagePath, boolean isLocalImg) {
		//此方法需要下载TesseractOCR，解压版
		
		return null;
	}
}
