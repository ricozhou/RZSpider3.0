package com.rzspider.project.spider.spidertask.service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import com.itextpdf.text.log.SysoCounter;
import com.rzspider.common.constant.CommonSymbolicConstant;
import com.rzspider.common.constant.ReturnMessageConstant;
import com.rzspider.common.constant.ScheduleConstants;
import com.rzspider.common.constant.WebSocketConstants;
import com.rzspider.common.utils.DateUtils;
import com.rzspider.common.utils.StringUtils;
import com.rzspider.common.utils.spring.SpringUtils;
import com.rzspider.framework.websocket.service.WebSocketPushHandler;
import com.rzspider.project.spider.spidertask.mapper.SpidertaskinfoMapper;
import com.rzspider.project.common.spiderdata.domain.Spiderdata;
import com.rzspider.project.common.spiderdata.mapper.SpiderdataMapper;
import com.rzspider.project.common.spiderdata.service.ISpiderdataService;
import com.rzspider.project.spider.spidertask.domain.Spidertaskinfo;
import com.rzspider.project.spider.spidertask.mainwork.domain.StartSpiderInfo;
import com.rzspider.project.spider.spidertask.mainwork.service.ICustomspiderRunService;
import com.rzspider.project.spider.spidertask.mainwork.utils.SpiderTaskThreadUtils;
import com.rzspider.project.spider.spidertask.service.ISpidertaskinfoService;
import com.rzspider.project.spider.spidertask.utils.SpiderScheduleJob;
import com.rzspider.project.spider.spidertask.utils.SpiderScheduleRunnable;

/**
 * 爬虫任务运行记录详情 服务层实现
 * 
 * @author ricozhou
 * @date 2018-06-05
 */
@Service
public class SpidertaskinfoServiceImpl implements ISpidertaskinfoService {
	@Autowired
	private SpidertaskinfoMapper spidertaskinfoMapper;
	@Autowired
	private SpiderdataMapper dataMapper;
	@Autowired
	private ISpiderdataService spiderdataService;
	@Autowired
	private ICustomspiderRunService customspiderRunService;

	/**
	 * 查询爬虫任务运行记录详情信息
	 * 
	 * @param taskInfoId
	 *            爬虫任务运行记录详情ID
	 * @return 爬虫任务运行记录详情信息
	 */
	@Override
	public Spidertaskinfo selectSpidertaskinfoById(Integer taskInfoId) {
		return spidertaskinfoMapper.selectSpidertaskinfoById(taskInfoId);
	}

	/**
	 * 查询爬虫任务运行记录详情列表
	 * 
	 * @param spidertaskinfo
	 *            爬虫任务运行记录详情信息
	 * @return 爬虫任务运行记录详情集合
	 */
	@Override
	public List<Spidertaskinfo> selectSpidertaskinfoList(Spidertaskinfo spidertaskinfo) {
		return spidertaskinfoMapper.selectSpidertaskinfoList(spidertaskinfo);
	}

	/**
	 * 新增爬虫任务运行记录详情
	 * 
	 * @param spidertaskinfo
	 *            爬虫任务运行记录详情信息
	 * @return 结果
	 */
	@Override
	public int insertSpidertaskinfo(Spidertaskinfo spidertaskinfo) {
		return spidertaskinfoMapper.insertSpidertaskinfo(spidertaskinfo);
	}

	/**
	 * 修改爬虫任务运行记录详情
	 * 
	 * @param spidertaskinfo
	 *            爬虫任务运行记录详情信息
	 * @return 结果
	 */
	@Override
	public int updateSpidertaskinfo(Spidertaskinfo spidertaskinfo) {
		return spidertaskinfoMapper.updateSpidertaskinfo(spidertaskinfo);
	}

	/**
	 * 保存爬虫任务运行记录详情
	 * 
	 * @param spidertaskinfo
	 *            爬虫任务运行记录详情信息
	 * @return 结果
	 */
	@Override
	public int saveSpidertaskinfo(Spidertaskinfo spidertaskinfo) {
		Integer taskInfoId = spidertaskinfo.getTaskInfoId();
		int rows = 0;
		if (StringUtils.isNotNull(taskInfoId)) {
			rows = spidertaskinfoMapper.updateSpidertaskinfo(spidertaskinfo);
		} else {
			rows = spidertaskinfoMapper.insertSpidertaskinfo(spidertaskinfo);
		}
		return rows;
	}

	/**
	 * 删除爬虫任务运行记录详情信息
	 * 
	 * @param taskInfoId
	 *            爬虫任务运行记录详情ID
	 * @return 结果
	 */
	@Override
	public int deleteSpidertaskinfoById(Integer taskInfoId) {
		// 需要把数据也一起删除
		dataMapper.deleteAllDataByTaskinfoId(taskInfoId);
		return spidertaskinfoMapper.deleteSpidertaskinfoById(taskInfoId);
	}

	/**
	 * 批量删除爬虫任务运行记录详情对象
	 * 
	 * @param taskInfoIds
	 *            需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int batchDeleteSpidertaskinfo(Integer[] taskInfoIds) {
		int rows = 0;
		for (Integer taskInfoId : taskInfoIds) {
			rows = deleteSpidertaskinfoById(taskInfoId);
		}
		return rows;
	}

	// 查询状态
	@Override
	public int checkSpidertaskinfoStatus(Integer taskInfoId) {
		Spidertaskinfo spidertaskinfo = spidertaskinfoMapper.selectSpidertaskinfoById(taskInfoId);
		if (spidertaskinfo == null) {
			return -1;
		}
		return spidertaskinfo.getTaskStatus();
	}

	// 根据taskid查询
	@Override
	public List<Spidertaskinfo> selectSpidertaskinfoByTaskId(Integer taskId) {
		return spidertaskinfoMapper.selectSpidertaskinfoByTaskId(taskId);
	}

	// 改变状态启动
	@Override
	public int chanageSpidertaskinfoTaskStatus(Spidertaskinfo spidertaskinfo) {
		spidertaskinfo.setTaskStatus(0);
		spidertaskinfo.setStartTime(new Date());
		// 完成状态是未完成
		spidertaskinfo.setFinishStatus(0);
		spidertaskinfo.setConsumeTime(CommonSymbolicConstant.EMPTY_STRING);
		spidertaskinfo.setEndTime(null);
		return spidertaskinfoMapper.updateSpidertaskinfoTaskStatus(spidertaskinfo);
	}

	// 改变状态中止
	@Override
	public int chanageSpidertaskinfoTaskStatus2(Spidertaskinfo spidertaskinfo) {
		spidertaskinfo.setTaskStatus(1);
		// 拼接耗时时间
		// 先查询出开始时间，拼接好之后再计算耗时更新
		Spidertaskinfo spidertaskinfo2 = spidertaskinfoMapper.selectSpidertaskinfoById(spidertaskinfo.getTaskInfoId());
		// 计算时间
		long costTime = DateUtils.getTimeFromTwoDate(spidertaskinfo2.getStartTime(), new Date());
		// 返回时间格式几天几小时
		// 注意服务器时间
		String consumeTime = DateUtils.formatDuring(costTime);
		spidertaskinfo.setConsumeTime(consumeTime);
		spidertaskinfo.setEndTime(new Date());
		return spidertaskinfoMapper.updateSpidertaskinfoTaskStatus2(spidertaskinfo);
	}

	// 启动
	@Override
	public boolean start(StartSpiderInfo startSpiderInfo) {
		// 开始解析
		try {
			// 0是内置
			if (startSpiderInfo.getCreateType() == 0) {
				// 正式启动
				SpiderScheduleRunnable task = new SpiderScheduleRunnable(
						ScheduleConstants.INTERNAL_SPIDER_TASK_BEAN_NAME, ScheduleConstants.INTERNAL_SPIDER_METHOD_NAME
								+ CommonSymbolicConstant.UNDERLINE + startSpiderInfo.getSpiderBackId(),
						startSpiderInfo);
				Thread t = new Thread(task);
				t.start();
				SpidertaskServiceImpl.spiderTaskThreadMap.put(startSpiderInfo.getTaskInfoId(), t);
				// Future<?> future = service.submit(task);
				// future.get();

			} else if (startSpiderInfo.getCreateType() == 1) {
				// 正式启动
				// ExecutorService service =
				// Executors.newSingleThreadExecutor();
				// 加入map统一管理
				// SpidertaskServiceImpl.spiderTaskThreadMap.put(startSpiderInfo.getTaskInfoId(),
				// service);
				SpiderScheduleRunnable task = new SpiderScheduleRunnable(ScheduleConstants.CUSTOM_SPIDER_TASK_BEAN_NAME,
						ScheduleConstants.CUSTOM_SPIDER_TASK_MOTHOD_NAME, startSpiderInfo);
				Thread t = new Thread(task);
				t.start();
				SpidertaskServiceImpl.spiderTaskThreadMap.put(startSpiderInfo.getTaskInfoId(), t);
				// Future<?> future = service.submit(task);
				// future.get();

			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// 中止
	@Override
	public boolean stop(StartSpiderInfo startSpiderInfo) {
		// 0是内置
		if (startSpiderInfo.getCreateType() == 0) {
			// 中止此线程
			SpiderTaskThreadUtils.stopSpiderTaskThread(startSpiderInfo.getTaskInfoId());
			// 将线程中止并从map中删除
			SpiderTaskThreadUtils.removeObjectFromMap(startSpiderInfo.getTaskInfoId());
			return true;
		} else if (startSpiderInfo.getCreateType() == 1) {
			// 中止此线程
			SpiderTaskThreadUtils.stopSpiderTaskThread(startSpiderInfo.getTaskInfoId());
			// 将线程中止并从map中删除
			SpiderTaskThreadUtils.removeObjectFromMap(startSpiderInfo.getTaskInfoId());
			return customspiderRunService.stopTaskCSProcess(startSpiderInfo);
		}
		return false;
	}

	// 首先执行的
	@Override
	public void firstExection(StartSpiderInfo startSpiderInfo) {
		// 删除原来对应的任务详情id数据
		Spiderdata spiderdata = new Spiderdata();
		spiderdata.setTaskInfoId(startSpiderInfo.getTaskInfoId());
		// 0是测试数据，1是正常数据，2是完整数据
		// 1是正常数据
		spiderdata.setTaskFlag(1);
		spiderdataService.deleteDataByTaskinfoId(spiderdata);
		// 2是完整数据
		spiderdata.setTaskFlag(2);
		spiderdataService.deleteDataByTaskinfoId(spiderdata);
	}

	// 最终必须执行的
	@Override
	public void finallyExection(StartSpiderInfo startSpiderInfo, Integer finishStatus) {
		// 更新状态终止
		Spidertaskinfo spidertaskinfo = new Spidertaskinfo();
		spidertaskinfo.setTaskInfoId(startSpiderInfo.getTaskInfoId());
		// 完成状态
		spidertaskinfo.setFinishStatus(finishStatus);
		chanageSpidertaskinfoTaskStatus2(spidertaskinfo);
		Spidertaskinfo sti = selectSpidertaskinfoById(startSpiderInfo.getTaskInfoId());
		// 结束通知前台
		WebSocketPushHandler.sendMessageToUser(WebSocketConstants.WEBSOCKET_PARAMS_TASKID,
				String.valueOf(sti.getTaskId()), new TextMessage(ReturnMessageConstant.RETURN_MESSAGE_SUCCESS));
		// 中止此线程
		SpiderTaskThreadUtils.stopSpiderTaskThread(startSpiderInfo.getTaskInfoId());
		// 将线程中止并从map中删除
		SpiderTaskThreadUtils.removeObjectFromMap(startSpiderInfo.getTaskInfoId());
		// 再次之后不会再执行代码
	}

}
