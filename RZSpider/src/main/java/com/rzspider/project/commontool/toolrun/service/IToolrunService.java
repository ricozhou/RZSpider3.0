package com.rzspider.project.commontool.toolrun.service;

import com.rzspider.project.commontool.toolrun.domain.CobeImage;
import com.rzspider.project.commontool.toolrun.domain.CommonToolEntity;
import com.rzspider.project.commontool.toolrun.domain.FormatText;
import com.rzspider.project.commontool.toolrun.domain.ImgToChar;
import com.rzspider.project.commontool.toolrun.domain.MatchRegularExpression;
import com.rzspider.project.commontool.toolrun.domain.ORCode;

/**
 * 通用工具运行 服务层
 * 
 * @author ricozhou
 * @date 2018-07-23
 */
public interface IToolrunService {

	//生成二维码
	boolean runORCodeCreate(ORCode orCode, String fileName) throws Exception;

	String runORCodeAnalyze(CommonToolEntity commonToolEntity);

	String runOCRRead(CommonToolEntity commonToolEntity);

	String runFormatText(FormatText formatText);

	String[] runMatchRegularExpression(MatchRegularExpression matchRegularExpression);

	boolean runImgToChar(ImgToChar imgToChar, String fileName);

	boolean runCobeImageCreate(CobeImage cobeImage, String fileName);

	int checkCommontoolStatus(Integer toolBackId);

	String[] runFormatCode(FormatText formatText);

}
