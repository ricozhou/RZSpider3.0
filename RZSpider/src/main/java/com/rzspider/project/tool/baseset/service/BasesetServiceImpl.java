package com.rzspider.project.tool.baseset.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rzspider.common.constant.CommonSymbolicConstant;
import com.rzspider.common.constant.UserConstants;
import com.rzspider.common.constant.project.ToolConstant;
import com.rzspider.common.utils.StringUtils;
import com.rzspider.common.utils.security.ShiroUtils;
import com.rzspider.framework.config.FilePathConfig;
import com.rzspider.framework.web.domain.Message;
import com.rzspider.project.tool.baseset.mapper.BasesetMapper;
import com.rzspider.project.common.file.utilt.FileUtils;
import com.rzspider.project.common.spiderdata.service.ISpiderdataService;
import com.rzspider.project.commontool.toolmanage.domain.Toolmanage;
import com.rzspider.project.spider.customspider.service.ICustomspiderService;
import com.rzspider.project.tool.baseset.domain.Baseset;
import com.rzspider.project.tool.baseset.service.IBasesetService;
import com.rzspider.project.tool.downloadmanage.domain.Downloadmanage;
import com.rzspider.project.tool.downloadmanage.service.IDownloadmanageService;

/**
 * 基础设置详情 服务层实现
 * 
 * @author ricozhou
 * @date 2018-06-23
 */
@Service
public class BasesetServiceImpl implements IBasesetService {
	@Autowired
	private BasesetMapper basesetMapper;
	@Autowired
	private IMusiclistService musiclistService;
	@Autowired
	private ICustomspiderService customspiderService;
	@Autowired
	private ISpiderdataService spiderdataService;
	@Autowired
	private IDownloadmanageService downloadmanageService;

	/**
	 * 查询基础设置详情信息
	 * 
	 * @param basesetId
	 *            基础设置详情ID
	 * @return 基础设置详情信息
	 */
	@Override
	public Baseset selectBasesetById(Integer basesetId) {
		return basesetMapper.selectBasesetById(basesetId);
	}

	/**
	 * 查询基础设置详情列表
	 * 
	 * @param baseset
	 *            基础设置详情信息
	 * @return 基础设置详情集合
	 */
	@Override
	public List<Baseset> selectBasesetList(Baseset baseset) {
		return basesetMapper.selectBasesetList(baseset);
	}

	/**
	 * 新增基础设置详情
	 * 
	 * @param baseset
	 *            基础设置详情信息
	 * @return 结果
	 */
	@Override
	public int insertBaseset(Baseset baseset) {
		return basesetMapper.insertBaseset(baseset);
	}

	/**
	 * 修改基础设置详情
	 * 
	 * @param baseset
	 *            基础设置详情信息
	 * @return 结果
	 */
	@Override
	public int updateBaseset(Baseset baseset) {
		return basesetMapper.updateBaseset(baseset);
	}

	/**
	 * 保存基础设置详情
	 * 
	 * @param baseset
	 *            基础设置详情信息
	 * @return 结果
	 */
	@Override
	public int saveBaseset(Baseset baseset) {
		Integer basesetId = baseset.getBasesetId();
		int rows = 0;
		if (StringUtils.isNotNull(basesetId)) {
			baseset.setUpdateBy(ShiroUtils.getLoginName());
			rows = basesetMapper.updateBaseset(baseset);
		} else {
			baseset.setCreateBy(ShiroUtils.getLoginName());
			rows = basesetMapper.insertBaseset(baseset);
		}
		return rows;
	}

	/**
	 * 删除基础设置详情信息
	 * 
	 * @param basesetId
	 *            基础设置详情ID
	 * @return 结果
	 */
	@Override
	public int deleteBasesetById(Integer basesetId) {
		// 先删除音乐
		musiclistService.deleteMusiclistByBasesetId(basesetId);
		return basesetMapper.deleteBasesetById(basesetId);
	}

	/**
	 * 批量删除基础设置详情对象
	 * 
	 * @param basesetIds
	 *            需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int batchDeleteBaseset(Integer[] basesetIds) {
		int rows = 0;
		for (Integer basesetId : basesetIds) {
			rows = deleteBasesetById(basesetId);
		}
		return rows;
	}

	// 启用
	@Override
	public int startUse(Integer basesetId) {
		int rows = 0;
		// 先把其他的所有设置为停用，再将此主题设置成启用中
		if (basesetMapper.changeAllUseStatusToStop(1) > 0) {
			return basesetMapper.changeUseStatusToStartByBasesetId(basesetId);
		}
		return rows;
	}

	// 接下来是工具方法，获取各种参数
	/**
	 * 获取：所有
	 */
	@Override
	public Baseset getBaseset() {
		Baseset baseset = basesetMapper.selectBasesetByUseStatus(0);
		return baseset;
	}

	/**
	 * 获取：是否显示音乐插件
	 */
	@Override
	public Integer getShowMusicToolStatus() {
		Baseset baseset = basesetMapper.selectBasesetByUseStatus(0);
		// 默认显示
		Integer showMusicToolStatus = 0;
		if (baseset != null) {
			showMusicToolStatus = baseset.getShowMusicToolStatus();
		}
		return showMusicToolStatus;
	}

	/**
	 * 获取：登录背景,由于刚开始登录，没有文件读取权限，直接返回base64
	 */
	@Override
	public String getLoginbgName() {
		Baseset baseset = basesetMapper.selectBasesetByUseStatus(0);
		String loginbgName = CommonSymbolicConstant.EMPTY_STRING;
		if (baseset != null && baseset.getLoginbgName() != null) {
			loginbgName = baseset.getLoginbgName();
		}
		return loginbgName;
	}

	/**
	 * 获取：登录背景类型
	 */
	// 0是自带图片或者图片链接，1上传图片，2是特效背景
	@Override
	public Integer getLoginbgType() {
		Baseset baseset = basesetMapper.selectBasesetByUseStatus(0);
		Integer loginbgType = 0;
		if (baseset != null && baseset.getLoginbgType() != null) {
			loginbgType = baseset.getLoginbgType();
		}
		return loginbgType;
	}

	/**
	 * 获取：首页介绍
	 */
	@Override
	public String getHomeIntroduction() {
		Baseset baseset = basesetMapper.selectBasesetByUseStatus(0);
		String homeIntroduction = ToolConstant.TOOL_BASESET_DEFAULT_HOMEINTRODUCTION;
		if (baseset != null && baseset.getHomeIntroduction() != null) {
			homeIntroduction = baseset.getHomeIntroduction();
		}
		return homeIntroduction;
	}

	/**
	 * 获取：爬虫java代码包名前缀
	 */
	@Override
	public String getSpiderJavaPackagePrefix() {
		Baseset baseset = basesetMapper.selectBasesetByUseStatus(0);
		String spiderJavaPackagePrefix = ToolConstant.TOOL_BASESET_DEFAULT_SPIDERPACKAGEPREFIX;
		if (baseset != null && baseset.getSpiderJavaPackagePrefix() != null
				&& !CommonSymbolicConstant.EMPTY_STRING.equals(baseset.getSpiderJavaPackagePrefix())) {
			spiderJavaPackagePrefix = baseset.getSpiderJavaPackagePrefix().replaceAll(CommonSymbolicConstant.SPACE,
					CommonSymbolicConstant.EMPTY_STRING);
		}
		return spiderJavaPackagePrefix;
	}

	/**
	 * 获取：爬虫java代码示例
	 */
	@Override
	public String getSpiderJavaCodeExample() {
		Baseset baseset = basesetMapper.selectBasesetByUseStatus(0);
		String spiderJavaCodeExample = CommonSymbolicConstant.EMPTY_STRING;
		if (baseset != null && baseset.getSpiderJavaCodeExample() != null) {
			spiderJavaCodeExample = baseset.getSpiderJavaCodeExample();
		}
		return spiderJavaCodeExample;
	}

	/**
	 * 获取：爬虫python代码示例
	 */
	@Override
	public String getSpiderPythonCodeExample() {
		Baseset baseset = basesetMapper.selectBasesetByUseStatus(0);
		String spiderPythonCodeExample = CommonSymbolicConstant.EMPTY_STRING;
		if (baseset != null && baseset.getSpiderPythonCodeExample() != null) {
			spiderPythonCodeExample = baseset.getSpiderPythonCodeExample();
		}
		return spiderPythonCodeExample;
	}

	/**
	 * 获取：爬虫javascript代码示例
	 */
	@Override
	public String getSpiderJavascriptCodeExample() {
		Baseset baseset = basesetMapper.selectBasesetByUseStatus(0);
		String spiderJavascriptCodeExample = CommonSymbolicConstant.EMPTY_STRING;
		if (baseset != null && baseset.getSpiderJavascriptCodeExample() != null) {
			spiderJavascriptCodeExample = baseset.getSpiderJavascriptCodeExample();
		}
		return spiderJavascriptCodeExample;
	}

	/**
	 * 获取：下载文件名前缀
	 */
	@Override
	public String getDownloadFileNamePrefix() {
		Baseset baseset = basesetMapper.selectBasesetByUseStatus(0);
		String downloadFileNamePrefix = CommonSymbolicConstant.EMPTY_STRING;
		if (baseset != null && baseset.getDownloadFileNamePrefix() != null
				&& !CommonSymbolicConstant.EMPTY_STRING.equals(baseset.getDownloadFileNamePrefix())) {
			downloadFileNamePrefix = baseset.getDownloadFileNamePrefix().replaceAll(CommonSymbolicConstant.SPACE,
					CommonSymbolicConstant.EMPTY_STRING);
		}
		return downloadFileNamePrefix;
	}

	/**
	 * 获取：网站使用手册内容
	 */
	@Override
	public String getSpiderWebsiteManual() {
		Baseset baseset = basesetMapper.selectBasesetByUseStatus(0);
		String spiderWebsiteManual = ToolConstant.TOOL_BASESET_DEFAULT_HOMEINTRODUCTION;
		if (baseset != null && baseset.getSpiderWebsiteManual() != null) {
			spiderWebsiteManual = baseset.getSpiderWebsiteManual();
		}
		return spiderWebsiteManual;
	}

	/**
	 * 获取：爬虫使用手册内容
	 */
	@Override
	public String getSpiderUseManual() {
		Baseset baseset = basesetMapper.selectBasesetByUseStatus(0);
		String spiderUseManual = ToolConstant.TOOL_BASESET_DEFAULT_HOMEINTRODUCTION;
		if (baseset != null && baseset.getSpiderUseManual() != null) {
			spiderUseManual = baseset.getSpiderUseManual();
		}
		return spiderUseManual;
	}

	/**
	 * 获取：音乐内容
	 */
	@Override
	public String getMusicInfo() {
		Baseset baseset = basesetMapper.selectBasesetByUseStatus(0);
		String musicInfo = ToolConstant.TOOL_BASESET_DEFAULT_MUSICINFO;
		if (baseset != null && baseset.getMusicInfo() != null) {
			musicInfo = baseset.getMusicInfo();
		}
		return musicInfo;
	}

	/**
	 * 校验主题名称
	 */
	@Override
	public String checkThemeNameUnique(Baseset baseset) {
		if (baseset.getBasesetId() == null) {
			baseset.setBasesetId(-1);
		}
		Integer basesetId = baseset.getBasesetId();
		Baseset info = basesetMapper.checkThemeNameUnique(baseset);
		if (StringUtils.isNotNull(info) && StringUtils.isNotNull(info.getBasesetId())
				&& info.getBasesetId() != basesetId) {
			return UserConstants.THEME_NAME_NOT_UNIQUE;
		}
		return UserConstants.THEME_NAME_UNIQUE;
	}

	// 更新音乐内容
	@Override
	public int updateMusicInfo(Baseset baseset) {
		return basesetMapper.updateMusicInfo(baseset);
	}

	// pdf转byte
	@Override
	public byte[] getPdfToByte(String fileName) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		// 读取文件放入byte流中
		String filePath = FilePathConfig.getUploadCachePath() + File.separator + fileName;
		File file = new File(filePath);
		if (!file.exists() || !file.isFile()) {
			return null;
		}
		try {
			int length = 0;
			FileInputStream inputStream = new FileInputStream(file);
			byte[] buffer = new byte[100];
			while ((length = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, length);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outputStream.toByteArray();
	}

	// 保存音乐其它设置
	@Override
	public int updateMusicOtherSet(Baseset baseset) {
		return basesetMapper.updateMusicOtherSet(baseset);
	}

	// 清除缓存文件
	@Override
	public Message clearCacheFile() {
		// 需根据数据库数据比对缓存文件夹再清理
		// 清除缓存文件夹
		String filePath = FilePathConfig.getUploadCachePath();

		// 保留文件夹
		if (FileUtils.deleteAllFileWithoutFolder(filePath) && deleteFileFromDownmanagePath()) {
			return Message.success(ToolConstant.TOOL_BASESET_CLEAR_FINISHED);
		}
		return Message.error(ToolConstant.TOOL_BASESET_CLEAR_FAILED);
	}

	/**
	 * @date Sep 11, 2018 4:18:08 PM
	 * @Desc 清除无用的下载文件
	 * @return
	 */
	private boolean deleteFileFromDownmanagePath() {
		String downmanageFilePath = FilePathConfig.getDownloadManagePath();
		// 获取下载正常的文件列表
		List<Downloadmanage> list = downloadmanageService.selectDownloadmanageList(null);
		// 获取所有下载文件的文件
		File file = new File(downmanageFilePath);
		File[] fileList = file.listFiles();
		if (fileList == null || fileList.length < 1) {
			return true;
		}
		// 遍历
		String fname;
		boolean includeFile = false;
		for (File f : fileList) {
			fname = f.getName();
			for (Downloadmanage dlm : list) {
				if (fname.equals(dlm.getDownloadFileUuidName())) {
					includeFile = true;
					break;
				}
			}
			if (!includeFile) {
				f.delete();
			}
			includeFile = false;
		}

		return true;
	}

	// 清除缓存数据
	@Override
	public Message clearCacheDB() {
		// 主要有以下几个数据表需要清除
		// spiderdata,爬虫测试数据需清除，需判断爬虫是否在测试运行
		// sysfile，博客图片文件，需比对博客在清理
		// 以及sysjoblog，syslogininfor，sysoperlog，操作日志的清理
		// 这里只实现爬虫测试文件的清理
		// 检查所有爬虫是否有在测试运行的
		if (customspiderService.selectCustomSpiderByRunStatus(0).size() > 0) {
			return Message.error(ToolConstant.TOOL_BASESET_SPIDERRUNNING_TO_STOP);
		}
		// 清除
		spiderdataService.deleteSpiderDataByFlag(0);
		return Message.success(ToolConstant.TOOL_BASESET_CLEAR_FINISHED);
	}

}
