package com.rzspider.project.common.file.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.rzspider.common.constant.CommonSymbolicConstant;
import com.rzspider.common.constant.FileOtherConstant;
import com.rzspider.framework.config.FilePathConfig;
import com.rzspider.project.common.file.domain.FileDao;
import com.rzspider.project.common.file.mapper.FileMapper;

@Service
public class FileServiceImpl implements IFileService {
	@Autowired
	private FileMapper sysFileMapper;

	@Autowired
	private FilePathConfig filePathConfig;

	@Override
	public FileDao get(Long id) {
		return sysFileMapper.get(id);
	}

	@Override
	public List<FileDao> list(Map<String, Object> map) {
		return sysFileMapper.list(map);
	}

	@Override
	public int count(Map<String, Object> map) {
		return sysFileMapper.count(map);
	}

	@Override
	public int save(FileDao sysFile) {
		return sysFileMapper.save(sysFile);
	}

	@Override
	public int update(FileDao sysFile) {
		return sysFileMapper.update(sysFile);
	}

	@Override
	public int remove(Long id) {
		return sysFileMapper.remove(id);
	}

	@Override
	public int batchRemove(Long[] ids) {
		return sysFileMapper.batchRemove(ids);
	}

	@Override
	public Boolean isExist(String url) {
		Boolean isExist = false;
		if (!StringUtils.isEmpty(url)) {
			String filePath = url.replace(FileOtherConstant.FILE_JUMP_PATH_PREFIX, CommonSymbolicConstant.EMPTY_STRING);
			filePath = filePathConfig.getUploadPath() + filePath;
			File file = new File(filePath);
			if (file.exists()) {
				isExist = true;
			}
		}
		return isExist;
	}
}
