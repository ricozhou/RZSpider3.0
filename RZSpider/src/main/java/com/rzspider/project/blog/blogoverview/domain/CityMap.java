package com.rzspider.project.blog.blogoverview.domain;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author ricozhou
 * @date Oct 26, 2018 10:02:51 AM
 * @Desc
 */
public class CityMap {
	/**
	 * 全国（省，直辖市，自治区）映射集合
	 */
	public Map<String, Integer> model = new LinkedHashMap<String, Integer>();

	public CityMap() {
		model.put("北京", 0);
		model.put("天津", 0);
		model.put("上海", 0);
		model.put("重庆", 0);
		model.put("浙江", 0);
		model.put("江苏", 0);
		model.put("河南", 0);
		model.put("福建", 0);
		model.put("广东", 0);
		model.put("安徽", 0);
		model.put("内蒙古", 0);
		model.put("湖北", 0);
		model.put("云南", 0);
		model.put("山东", 0);
		model.put("海南", 0);
		model.put("江西", 0);
		model.put("广西", 0);
		model.put("湖南", 0);
		model.put("河北", 0);
		model.put("四川", 0);
		model.put("山西", 0);
		model.put("贵州", 0);
		model.put("宁夏", 0);
		model.put("青海", 0);
		model.put("辽宁", 0);
		model.put("吉林", 0);
		model.put("黑龙江", 0);
		model.put("西藏", 0);
		model.put("陕西", 0);
		model.put("甘肃", 0);
		model.put("新疆", 0);
		model.put("香港", 0);
		model.put("澳门", 0);
		model.put("台湾", 0);
	}
}
