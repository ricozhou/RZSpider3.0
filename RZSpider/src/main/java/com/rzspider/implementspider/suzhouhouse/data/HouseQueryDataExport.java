package com.rzspider.implementspider.suzhouhouse.data;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.rzspider.common.constant.CommonSymbolicConstant;
import com.rzspider.implementspider.commonutils.SpecificInternalSpiderDataUtils;
import com.rzspider.implementspider.suzhouhouse.domain.housequery.HouseQueryMessage;
import com.rzspider.implementspider.suzhouhouse.domain.housequery.HouseQueryParams;
import com.rzspider.implementspider.suzhouhouse.domain.permitpresale.PermitPresaleMessage;
import com.rzspider.project.common.spiderdata.domain.ReturnSpiderDataMessage;
import com.rzspider.project.common.spiderdata.domain.Spiderdata;

public class HouseQueryDataExport {

	public static ReturnSpiderDataMessage houseQueryDataExport(HouseQueryParams houseQueryParams,
			List<Spiderdata> list) {

		ReturnSpiderDataMessage rsdm = new ReturnSpiderDataMessage();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		if (list == null || list.size() < 1) {
			return null;
		}
		// 先解析json成对象，然后在处理
		List<HouseQueryMessage> hqmList;
		try {
			hqmList = SpecificInternalSpiderDataUtils.jsonStringListToObjectList(list, new HouseQueryMessage());
		} catch (Exception e) {
			// 解析出错则以文本返回
			// 解析文本
			StringBuilder sb = new StringBuilder();
			for (Spiderdata spiderdata : list) {
				sb.append(spiderdata.getJsonData() + CommonSymbolicConstant.LINEBREAK2);
			}
			rsdm.setBytes(sb.toString().getBytes());
			// 0是文本
			rsdm.setFileType(0);
			return rsdm;
		}

		String[] title = { "序号", "房屋坐落", "房屋用途", "房屋套型", "建筑面积", "参考单价", "座号", "层次", "所属自然层", "单元号", "室号(编号)", "房屋性质",
				"类型", "用途", "建成日期", "结构类型", "房屋所有权登记证明编号", "土地分割转让许可证编号", "抵押情况", "装修情况", "抵押起始日期", "抵押终止日期",
				"套内建筑面积（平方米）", "分摊建筑面积（平方米）", "总建筑面积（平方米）", "总价格（元）", "单价（元/平方米）", "公安门牌号", "房屋层高是否超过2.2米", "房屋层高（米）",
				"房地产公司名称", "法定代表人姓名", "法定代表人电话", "法定地址", "营业执照注册号", "资质证书编号", "企业类型", "通讯地址", "邮政编码", "E-mail", "网站",
				"联系人", "联系电话" };
		// 创建Excel工作
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 创建一个sheet表
		XSSFSheet sheet = workbook.createSheet();
		// 设置列宽43列
		sheet.setColumnWidth(1, 40 * 256);
		sheet.setColumnWidth(3, 15 * 256);
		sheet.setColumnWidth(12, 15 * 256);
		sheet.setColumnWidth(14, 20 * 256);
		sheet.setColumnWidth(15, 15 * 256);
		sheet.setColumnWidth(18, 15 * 256);
		sheet.setColumnWidth(19, 15 * 256);
		sheet.setColumnWidth(20, 20 * 256);
		sheet.setColumnWidth(21, 20 * 256);
		sheet.setColumnWidth(27, 40 * 256);

		sheet.setColumnWidth(30, 50 * 256);
		sheet.setColumnWidth(32, 20 * 256);
		sheet.setColumnWidth(33, 50 * 256);
		sheet.setColumnWidth(34, 30 * 256);
		sheet.setColumnWidth(35, 20 * 256);
		sheet.setColumnWidth(36, 30 * 256);
		sheet.setColumnWidth(39, 30 * 256);
		sheet.setColumnWidth(42, 20 * 256);
		// 创建第一行
		XSSFRow row = sheet.createRow(0);
		XSSFCell cell;
		// 插入第一行数据
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(title[i]);
		}
		// 追加数据,向第二行开始加入数据 i = 1
		for (int i = 1; i < hqmList.size() + 1; i++) {
			XSSFRow row2 = sheet.createRow(i);
			XSSFCell cell2 = row2.createCell(0);
			cell2.setCellValue(i);
			cell2 = row2.createCell(1);
			cell2.setCellValue(hqmList.get(i - 1).getHouseLacation());
			cell2 = row2.createCell(2);
			cell2.setCellValue(hqmList.get(i - 1).getHouseUse());
			cell2 = row2.createCell(3);
			cell2.setCellValue(hqmList.get(i - 1).getHouseType());
			cell2 = row2.createCell(4);
			cell2.setCellValue(hqmList.get(i - 1).getHouseBuildArea());
			cell2 = row2.createCell(5);
			cell2.setCellValue(hqmList.get(i - 1).getHouseReferencePrice());
			// 详细信息
			cell2 = row2.createCell(6);
			cell2.setCellValue(hqmList.get(i - 1).getHqdi().getSeatNumber());
			cell2 = row2.createCell(7);
			cell2.setCellValue(hqmList.get(i - 1).getHqdi().getLevel());
			cell2 = row2.createCell(8);
			cell2.setCellValue(hqmList.get(i - 1).getHqdi().getNaturalLayer());
			cell2 = row2.createCell(9);
			cell2.setCellValue(hqmList.get(i - 1).getHqdi().getUnitNumber());
			cell2 = row2.createCell(10);
			cell2.setCellValue(hqmList.get(i - 1).getHqdi().getRoomNumber());
			cell2 = row2.createCell(11);
			cell2.setCellValue(hqmList.get(i - 1).getHqdi().getPropertyNature());
			cell2 = row2.createCell(12);
			cell2.setCellValue(hqmList.get(i - 1).getHqdi().getTypesOf());
			cell2 = row2.createCell(13);
			cell2.setCellValue(hqmList.get(i - 1).getHqdi().getUse());
			cell2 = row2.createCell(14);
			cell2.setCellValue(hqmList.get(i - 1).getHqdi().getDateCompletion());
			cell2 = row2.createCell(15);
			cell2.setCellValue(hqmList.get(i - 1).getHqdi().getStructureType());

			cell2 = row2.createCell(16);
			cell2.setCellValue(hqmList.get(i - 1).getHqdi().getHorcn());
			cell2 = row2.createCell(17);
			cell2.setCellValue(hqmList.get(i - 1).getHqdi().getLdtln());
			cell2 = row2.createCell(18);
			cell2.setCellValue(hqmList.get(i - 1).getHqdi().getMortgage());
			cell2 = row2.createCell(19);
			cell2.setCellValue(hqmList.get(i - 1).getHqdi().getRenovationCondition());
			cell2 = row2.createCell(20);
			cell2.setCellValue(hqmList.get(i - 1).getHqdi().getMortgageStartDate());
			cell2 = row2.createCell(21);
			cell2.setCellValue(hqmList.get(i - 1).getHqdi().getMortgageTerminationDate());
			cell2 = row2.createCell(22);
			cell2.setCellValue(hqmList.get(i - 1).getHqdi().getInbuildingArea());
			cell2 = row2.createCell(23);
			cell2.setCellValue(hqmList.get(i - 1).getHqdi().getDistributionBuildingArea());
			cell2 = row2.createCell(24);
			cell2.setCellValue(hqmList.get(i - 1).getHqdi().getTotalFloorArea());
			cell2 = row2.createCell(25);
			cell2.setCellValue(hqmList.get(i - 1).getHqdi().getTotalPrice());

			cell2 = row2.createCell(26);
			cell2.setCellValue(hqmList.get(i - 1).getHqdi().getUnitPrice());
			cell2 = row2.createCell(27);
			cell2.setCellValue(hqmList.get(i - 1).getHqdi().getPublicSecurityNumber());
			cell2 = row2.createCell(28);
			cell2.setCellValue(hqmList.get(i - 1).getHqdi().getHeightThanTPTmeters());
			cell2 = row2.createCell(29);
			cell2.setCellValue(hqmList.get(i - 1).getHqdi().getHouseHeight());

			// 公司详细信息
			cell2 = row2.createCell(30);
			cell2.setCellValue(hqmList.get(i - 1).getHqci().getCompanyName());
			cell2 = row2.createCell(31);
			cell2.setCellValue(hqmList.get(i - 1).getHqci().getLegalRepresentative());
			cell2 = row2.createCell(32);
			cell2.setCellValue(hqmList.get(i - 1).getHqci().getLegalRepreTel());
			cell2 = row2.createCell(33);
			cell2.setCellValue(hqmList.get(i - 1).getHqci().getLegalRepreAddress());
			cell2 = row2.createCell(34);
			cell2.setCellValue(hqmList.get(i - 1).getHqci().getBusinessLicenseNum());
			cell2 = row2.createCell(35);
			cell2.setCellValue(hqmList.get(i - 1).getHqci().getQualificatiomNum());
			cell2 = row2.createCell(36);
			cell2.setCellValue(hqmList.get(i - 1).getHqci().getCompanyType());
			cell2 = row2.createCell(37);
			cell2.setCellValue(hqmList.get(i - 1).getHqci().getMailAddress());
			cell2 = row2.createCell(38);
			cell2.setCellValue(hqmList.get(i - 1).getHqci().getPostalCode());
			cell2 = row2.createCell(39);
			cell2.setCellValue(hqmList.get(i - 1).getHqci().getEmail());
			cell2 = row2.createCell(40);
			cell2.setCellValue(hqmList.get(i - 1).getHqci().getWebsiteUrl());
			cell2 = row2.createCell(41);
			cell2.setCellValue(hqmList.get(i - 1).getHqci().getLinkman());
			cell2 = row2.createCell(42);
			cell2.setCellValue(hqmList.get(i - 1).getHqci().getLinkNum());
		}
		// 写入文件
		SpecificInternalSpiderDataUtils.writeWBToStream(workbook, outputStream);
		byte[] bytes = outputStream.toByteArray();
		rsdm.setBytes(bytes);
		// 表格
		rsdm.setFileType(1);
		return rsdm;
	}

}
