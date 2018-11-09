package com.rzspider.common.constant;

/**
 * 任务调度通用常量
 * 
 * @author ricozhou
 */
public interface ScheduleConstants {
	/**
	 * 任务调度参数key
	 */
	public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY";

	// 内置爬虫任务类名
	public static final String INTERNAL_SPIDER_TASK_BEAN_NAME = "internalSpiderTask";
	// 内置爬虫任务数据解析
	public static final String INTERNAL_SPIDER_DATA_BEAN_NAME = "internalSpiderData";
	// 内置爬虫任务数据解析方法前缀
	public static final String INTERNAL_SPIDER_DATA_METHOD_NAME = "analyzeInternalSpiderData";
	// 内置爬虫任务方法名(前缀)如startInternalSpider_10001
	public static final String INTERNAL_SPIDER_METHOD_NAME = "startInternalSpider";
	// 自定义爬虫任务类名
	public static final String CUSTOM_SPIDER_TASK_BEAN_NAME = "customSpiderTask";
	// 自定义爬虫任务方法名(前缀)
	public static final String CUSTOM_SPIDER_TASK_MOTHOD_NAME = "startCustomSpider";
	// 自定义爬虫任务数据解析
	public static final String CUSTOM_SPIDER_DATA_BEAN_NAME = "customSpiderData";
	// 自定义爬虫任务数据解析方法前缀
	public static final String CUSTOM_SPIDER_DATA_METHOD_NAME = "analyzeCustomSpiderData";

	public enum Status {
		/**
		 * 正常
		 */
		NORMAL(0),
		/**
		 * 暂停
		 */
		PAUSE(1);

		private int value;

		private Status(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

}
