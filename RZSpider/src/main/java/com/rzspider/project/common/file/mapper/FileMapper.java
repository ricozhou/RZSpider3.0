package com.rzspider.project.common.file.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.rzspider.project.common.file.domain.FileDao;

/**
 * 文件上传
 */
public interface FileMapper {

	FileDao get(Long id);

	List<FileDao> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(FileDao file);

	int update(FileDao file);

	int remove(Long id);

	int batchRemove(Long[] ids);
}
