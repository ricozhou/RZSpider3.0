package com.rzspider.project.blog.blogcontent.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.log.SysoCounter;
import com.rzspider.common.utils.DateUtils;
import com.rzspider.common.utils.MapUtils;
import com.rzspider.common.utils.StringUtils;
import com.rzspider.common.utils.ThreadUtils;
import com.rzspider.common.utils.security.ShiroUtils;
import com.rzspider.implementspider.blogmove.controller.BlogMoveSpiderController;
import com.rzspider.implementspider.blogmove.controller.BlogMoveThread;
import com.rzspider.project.blog.blogcontent.mapper.BlogmoveMapper;
import com.rzspider.project.blog.blogcontent.domain.Blogmove;
import com.rzspider.project.blog.blogcontent.service.IBlogmoveService;

/**
 * 博客搬家详情 服务层实现
 * 
 * @author ricozhou
 * @date 2018-10-19
 */
@Service
public class BlogmoveServiceImpl implements IBlogmoveService {
	@Autowired
	private BlogmoveMapper blogmoveMapper;
	// 设置一个全局静态的map存放所有的线程，以便于对应的取出线程中止他
	public static Map<String, Thread> blogMoveThreadMap = new HashMap<String, Thread>();
	public static Map<String, BlogMoveSpiderController> blogMoveThreadBMCMap = new HashMap<String, BlogMoveSpiderController>();
	public static Map<String, Thread> blogMoveOutThreadMap = new HashMap<String, Thread>();

	/**
	 * 查询博客搬家详情信息
	 * 
	 * @param blogMoveId
	 *            博客搬家详情ID
	 * @return 博客搬家详情信息
	 */
	@Override
	public Blogmove selectBlogmoveById(Integer blogMoveId) {
		return blogmoveMapper.selectBlogmoveById(blogMoveId);
	}

	/**
	 * 查询博客搬家详情列表
	 * 
	 * @param blogmove
	 *            博客搬家详情信息
	 * @return 博客搬家详情集合
	 */
	@Override
	public List<Blogmove> selectBlogmoveList(Blogmove blogmove) {
		return blogmoveMapper.selectBlogmoveList(blogmove);
	}

	/**
	 * 新增博客搬家详情
	 * 
	 * @param blogmove
	 *            博客搬家详情信息
	 * @return 结果
	 */
	@Override
	public int insertBlogmove(Blogmove blogmove) {
		return blogmoveMapper.insertBlogmove(blogmove);
	}

	/**
	 * 修改博客搬家详情
	 * 
	 * @param blogmove
	 *            博客搬家详情信息
	 * @return 结果
	 */
	@Override
	public int updateBlogmove(Blogmove blogmove) {
		// 结束时间和消耗时间
		blogmove.setFinishTime(new Date());
		// 消耗时间
		long costTime = DateUtils.getTimeFromTwoDate(blogmove.getCreateTime(), blogmove.getFinishTime());
		// 返回时间格式几天几小时
		// 注意服务器时间
		String consumeTime = DateUtils.formatDuring(costTime);
		blogmove.setTakeTime(consumeTime);

		return blogmoveMapper.updateBlogmove(blogmove);
	}

	/**
	 * 删除博客搬家详情信息
	 * 
	 * @param blogMoveId
	 *            博客搬家详情ID
	 * @return 结果
	 */
	@Override
	public int deleteBlogmoveById(Integer blogMoveId) {
		return blogmoveMapper.deleteBlogmoveById(blogMoveId);
	}

	/**
	 * 批量删除博客搬家详情对象
	 * 
	 * @param blogMoveIds
	 *            需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int batchDeleteBlogmove(Integer[] blogMoveIds) {
		return blogmoveMapper.batchDeleteBlogmove(blogMoveIds);
	}

	/**
	 * 博客搬家启动
	 * 
	 * @param
	 * 
	 * @return 结果
	 */
	@Override
	public Integer blogMoveRun(Blogmove blogMove) {
		BlogMoveSpiderController blogMoveSpiderController = new BlogMoveSpiderController();
		Thread t = new Thread(new BlogMoveThread(blogMove, blogMoveSpiderController));
		t.start();
		// 加入静态变量方便中止控制
		blogMoveThreadMap.put(ShiroUtils.getLoginName(), t);
		blogMoveThreadBMCMap.put(ShiroUtils.getLoginName(), blogMoveSpiderController);
		// 详细信息先插入数据库
		blogMove.setCreateBy(ShiroUtils.getLoginName());
		blogMove.setCreateTime(new Date());
		int row = insertBlogmove(blogMove);
		return blogMove == null ? -1 : blogMove.getBlogMoveId();
	}

	/**
	 * 博客搬家中止
	 * 
	 * @param
	 * 
	 * @return 结果
	 */
	@Override
	public boolean blogMoveStop(Blogmove blogMove) {
		// 从map中中止
		ThreadUtils.stopThreadFromMap(BlogmoveServiceImpl.blogMoveThreadMap, ShiroUtils.getLoginName());
		// 从map中去除线程
		MapUtils.removeObjectFromMap(BlogmoveServiceImpl.blogMoveThreadMap, ShiroUtils.getLoginName());
		// 更新数据库
		blogMove.setMoveStopMode(1);
		// 结束时间和消耗时间
		blogMove.setFinishTime(new Date());
		// 消耗时间
		long costTime = DateUtils.getTimeFromTwoDate(selectBlogmoveById(blogMove.getBlogMoveId()).getCreateTime(),
				blogMove.getFinishTime());
		// 返回时间格式几天几小时
		// 注意服务器时间
		String consumeTime = DateUtils.formatDuring(costTime);
		blogMove.setTakeTime(consumeTime);

		// 获取成功条数，日志详情信息
		BlogMoveSpiderController blogMoveSpiderController = blogMoveThreadBMCMap.get(ShiroUtils.getLoginName());
		blogMove.setMoveSuccessNum(blogMoveSpiderController.getNum());
		blogMove.setMoveMessage(blogMoveSpiderController.getSbmsg());

		// 从map中删除
		MapUtils.removeObjectFromMap(BlogmoveServiceImpl.blogMoveThreadBMCMap, ShiroUtils.getLoginName());

		blogmoveMapper.updateMoveStopMoveMsg(blogMove);
		return true;
	}

	/**
	 * 博客搬家状态校验详情
	 * 
	 * @param blogmove
	 *            博客搬家详情信息
	 * @return 结果
	 */
	@Override
	public int checkMoveStatus(String loginName) {
		// 从常量中校验每一个用户都有唯一的一个博客搬家启动实例
		if (BlogmoveServiceImpl.blogMoveThreadMap.get(loginName) == null) {
			return 1;
		}
		// 正在运行
		return 0;
	}

}
