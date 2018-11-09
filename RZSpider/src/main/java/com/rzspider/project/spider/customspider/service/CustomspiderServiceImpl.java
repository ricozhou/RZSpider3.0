package com.rzspider.project.spider.customspider.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rzspider.common.constant.CommonSymbolicConstant;
import com.rzspider.common.constant.UserConstants;
import com.rzspider.common.constant.project.SpiderConstant;
import com.rzspider.common.utils.StringUtils;
import com.rzspider.common.utils.security.ShiroUtils;
import com.rzspider.framework.config.FilePathConfig;
import com.rzspider.project.spider.customspider.mapper.CustomspiderMapper;
import com.rzspider.project.spider.codeType.domain.CodeType;
import com.rzspider.project.spider.codeType.mapper.CodeTypeMapper;
import com.rzspider.project.spider.customspider.domain.Customspider;
import com.rzspider.project.spider.customspider.domain.CustomspiderBackupcode;
import com.rzspider.project.spider.customspider.domain.FileTree;
import com.rzspider.project.spider.customspider.service.ICustomspiderService;
import com.rzspider.project.spider.customspider.utils.BaseCSUtils;
import com.rzspider.project.spider.spidermanage.domain.SpiderList;
import com.rzspider.project.spider.spidermanage.mapper.SpiderListMapper;
import com.rzspider.project.system.dept.domain.Dept;
import com.rzspider.project.tool.baseset.service.IBasesetService;

/**
 * 自定义爬虫详情 服务层实现
 * 
 * @author ricozhou
 * @date 2018-06-01
 */
@Service
public class CustomspiderServiceImpl implements ICustomspiderService {
	@Autowired
	private CustomspiderMapper customspiderMapper;
	@Autowired
	private SpiderListMapper spiderListMapper;
	@Autowired
	private FilePathConfig filePathConfig;
	@Autowired
	private CodeTypeMapper codeTypeMapper;
	@Autowired
	private IBasesetService basesetService;
	@Autowired
	private ICSFileService cSFileService;
	@Autowired
	private IFileTreeService fileTreeService;

	/**
	 * 查询自定义爬虫详情信息
	 * 
	 * @param customSpiderId
	 *            自定义爬虫详情ID
	 * @return 自定义爬虫详情信息
	 */
	@Override
	public Customspider selectCustomspiderById(Integer customSpiderId) {
		return customspiderMapper.selectCustomspiderById(customSpiderId);
	}

	/**
	 * 查询自定义爬虫详情列表
	 * 
	 * @param customspider
	 *            自定义爬虫详情信息
	 * @return 自定义爬虫详情集合
	 */
	@Override
	public List<Customspider> selectCustomspiderList(Customspider customspider) {
		return customspiderMapper.selectCustomspiderList(customspider);
	}

	/**
	 * 新增自定义爬虫详情
	 * 
	 * @param customspider
	 *            自定义爬虫详情信息
	 * @return 结果
	 */
	@Override
	public int insertCustomspider(Customspider customspider) {
		return customspiderMapper.insertCustomspider(customspider);
	}

	/**
	 * 修改自定义爬虫详情
	 * 
	 * @param customspider
	 *            自定义爬虫详情信息
	 * @return 结果
	 */
	@Override
	public int updateCustomspider(Customspider customspider) {
		return customspiderMapper.updateCustomspider(customspider);
	}

	/**
	 * 保存自定义爬虫详情
	 * 
	 * @param customspider
	 *            自定义爬虫详情信息
	 * @return 结果
	 */
	@Override
	public int saveCustomspider(Customspider customspider, CodeType codeType) {
		Integer customSpiderId = customspider.getCustomSpiderId();
		int rows = 0;
		int rows2 = 0;
		SpiderList spiderList = new SpiderList();
		spiderList.setCustomSpiderId(customspider.getCustomSpiderId());
		spiderList.setSpiderBackId(customspider.getCustomSpiderBackId());
		spiderList.setSpiderDes(customspider.getSpiderDes());
		spiderList.setSpiderVersion(customspider.getSpiderVersion());
		spiderList.setCreateType(1);
		spiderList.setCustomSpiderType(customspider.getCustomSpiderType());
		spiderList.setSpiderDefaultParamsAll(customspider.getSpiderDefaultParamsAll());
		spiderList.setSpiderDefaultParams(customspider.getSpiderDefaultParams());
		spiderList.setStatus(customspider.getCustomStatus());
		spiderList.setUpdateBy(ShiroUtils.getLoginName());
		// 获取包前缀
		String spiderJavaPackagePrefix = null;
		// 取出基础设置中的示例代码，以java代码为例
		String codeExample = null;
		if (customspider.getCustomSpiderType() == 0) {
			// java代码
			codeExample = basesetService.getSpiderJavaCodeExample();
			spiderJavaPackagePrefix = basesetService.getSpiderJavaPackagePrefix();
		} else if (customspider.getCustomSpiderType() == 1) {
			// python代码
			// 先用java包前缀代替
			spiderJavaPackagePrefix = basesetService.getSpiderJavaPackagePrefix();
			codeExample = basesetService.getSpiderPythonCodeExample();
		} else if (customspider.getCustomSpiderType() == 2) {
			// javascript代码
			spiderJavaPackagePrefix = basesetService.getSpiderJavaPackagePrefix();
			codeExample = basesetService.getSpiderJavascriptCodeExample();
		}
		String filePath = filePathConfig.getCustomSpiderPath() + File.separator + codeType.getSpiderCodeTypeFolder()
				+ File.separator + customspider.getCustomSpiderBackId();
		if (StringUtils.isNotNull(customSpiderId)) {
			// 先根据id找到原来的后台ID
			Customspider cs = customspiderMapper.selectCustomspiderById(customSpiderId);
			String filePath2 = filePathConfig.getCustomSpiderPath() + File.separator
					+ codeType.getSpiderCodeTypeFolder() + File.separator + cs.getCustomSpiderBackId();
			// 重命名文件
			if (!BaseCSUtils.reCustomSpiderFileName(filePath2, filePath)) {
				return 0;
			}
			customspider.setUpdateBy(ShiroUtils.getLoginName());
			rows = customspiderMapper.updateCustomspider(customspider);
			// 更新到spiderlist表
			rows2 = spiderListMapper.updateSpiderList2(spiderList);
		} else {
			// 服务端创建一个文件夹用于存放自定义爬虫
			String basePath = filePathConfig.getCustomSpiderPath() + File.separator
					+ codeType.getSpiderCodeTypeFolder();
			if (!BaseCSUtils.createCustomSpiderFile(basePath, customspider, spiderJavaPackagePrefix, codeExample)) {
				return 0;
			}
			// 默认程序入口文件
			String entryFileName = CommonSymbolicConstant.EMPTY_STRING;
			if (customspider.getCustomSpiderType() == 0) {
				entryFileName = BaseCSUtils.getClassNameFromCodeString(codeExample);
			} else if (customspider.getCustomSpiderType() == 1) {
				entryFileName = SpiderConstant.SPIDER_ENTRYFILE_PYTHON_DEFAULT_NAME;
			} else if (customspider.getCustomSpiderType() == 2) {
				entryFileName = SpiderConstant.SPIDER_ENTRYFILE_JAVASCRIPT_DEFAULT_NAME;
			}
			customspider.setEntryFileName(entryFileName);
			customspider.setCreateBy(ShiroUtils.getLoginName());
			customspider.setSpiderJavaPackagePrefix(spiderJavaPackagePrefix);
			// 默认未运行
			customspider.setRunStatus(1);
			rows = customspiderMapper.insertCustomspider(customspider);
			Customspider customspider2 = customspiderMapper
					.selectCustomspiderByCustomSpiderBackId(customspider.getCustomSpiderBackId());
			spiderList.setCustomSpiderId(customspider2.getCustomSpiderId());
			// 更新文件树
			cSFileService.updateFileTreeToDB(cSFileService.selectCSFileTreeToList(customspider2.getCustomSpiderId()),
					customspider2.getCustomSpiderId(), customspider2.getCustomSpiderBackId());
			// 更新到spiderlist表
			rows2 = spiderListMapper.insertSpiderList(spiderList);
		}
		// 失败
		if (rows != 1 || rows2 != 1) {
			return 0;
		}
		return rows;
	}

	/**
	 * 删除自定义爬虫详情信息
	 * 
	 * @param customSpiderId
	 *            自定义爬虫详情ID
	 * @return 结果
	 */
	@Override
	public int deleteCustomspiderById(Integer customSpiderId) {
		int f = 0;
		// 先删除文件夹
		// 先根据id找到原来的后台ID
		Customspider cs = customspiderMapper.selectCustomspiderById(customSpiderId);
		// 根据爬虫类型找到对应的目录
		CodeType codeType = codeTypeMapper.selectSpiderCodeTypeByCustomSpiderType(cs.getCustomSpiderType());
		String filePath = filePathConfig.getCustomSpiderPath() + File.separator + codeType.getSpiderCodeTypeFolder()
				+ File.separator + cs.getCustomSpiderBackId();
		if (!BaseCSUtils.deleteCustomSpiderFile(filePath)) {
			return 0;
		}

		// 根据id删除文件树对应内容
		fileTreeService.deleteFileTreeByCustomSpiderId(customSpiderId);

		f = customspiderMapper.deleteCustomspiderById(customSpiderId);
		if (f != 1) {
			return f;
		}
		f = spiderListMapper.deleteSpiderListByCustomSpiderId(customSpiderId);
		return f;
	}

	/**
	 * 批量删除自定义爬虫详情对象
	 * 
	 * @param customSpiderIds
	 *            需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int batchDeleteCustomspider(Integer[] customSpiderIds) {
		int f = 0;
		for (Integer customSpiderId : customSpiderIds) {
			f = deleteCustomspiderById(customSpiderId);
		}
		return f;
	}

	// 根据后台id查询
	@Override
	public Customspider selectCustomspiderByCustomSpiderBackId(Integer customSpiderBackId) {
		return customspiderMapper.selectCustomspiderByCustomSpiderBackId(customSpiderBackId);
	}

	// 改变运行状态
	@Override
	public int updateCustomspiderRunStatus(Customspider customspider) {
		return customspiderMapper.updateCustomspiderRunStatus(customspider);
	}

	// 更新程序入口
	@Override
	public int updateCustomspiderEntryFileName(Customspider customspider) {
		return customspiderMapper.updateCustomspiderEntryFileName(customspider);
	}

	// 根据运行状态获取
	@Override
	public List<Customspider> selectCustomSpiderByRunStatus(Integer runStatus) {
		return customspiderMapper.selectCustomSpiderByRunStatus(runStatus);
	}

}
