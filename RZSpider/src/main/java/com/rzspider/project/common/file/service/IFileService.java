package com.rzspider.project.common.file.service;

import java.util.List;
import java.util.Map;

import com.rzspider.project.common.file.domain.FileDao;

/**
 * 文件上传
 * 
 */
public interface IFileService {

	FileDao get(Long id);

	List<FileDao> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(FileDao sysFile);

	int update(FileDao sysFile);

	int remove(Long id);

	int batchRemove(Long[] ids);

	/**
	 * 判断一个文件是否存在
	 * 
	 * @param url
	 *            FileDO中存的路径
	 * @return
	 */
	Boolean isExist(String url);
}
