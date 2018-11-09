package com.rzspider.project.spider.customspider.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rzspider.common.utils.StringUtils;
import com.rzspider.project.spider.customspider.mapper.FileTreeMapper;
import com.rzspider.project.spider.customspider.domain.FileTree;
import com.rzspider.project.spider.customspider.service.IFileTreeService;

/**
 * 文件树 服务层实现
 * 
 * @author ricozhou
 * @date 2018-07-02
 */
@Service
public class FileTreeServiceImpl implements IFileTreeService {
	@Autowired
	private FileTreeMapper fileTreeMapper;

	/**
	 * 查询文件树信息
	 * 
	 * @param filetreeId
	 *            文件树ID
	 * @return 文件树信息
	 */
	@Override
	public FileTree selectFileTreeById(Integer filetreeId) {
		return fileTreeMapper.selectFileTreeById(filetreeId);
	}

	/**
	 * 查询文件树列表
	 * 
	 * @param fileTree
	 *            文件树信息
	 * @return 文件树集合
	 */
	@Override
	public List<FileTree> selectFileTreeList(FileTree fileTree) {
		return fileTreeMapper.selectFileTreeList(fileTree);
	}

	/**
	 * 新增文件树
	 * 
	 * @param fileTree
	 *            文件树信息
	 * @return 结果
	 */
	@Override
	public int insertFileTree(FileTree fileTree) {
		return fileTreeMapper.insertFileTree(fileTree);
	}

	/**
	 * 修改文件树
	 * 
	 * @param fileTree
	 *            文件树信息
	 * @return 结果
	 */
	@Override
	public int updateFileTree(FileTree fileTree) {
		return fileTreeMapper.updateFileTree(fileTree);
	}

	/**
	 * 保存文件树
	 * 
	 * @param fileTree
	 *            文件树信息
	 * @return 结果
	 */
	@Override
	public int saveFileTree(FileTree fileTree) {
		Integer filetreeId = fileTree.getFiletreeId();
		int rows = 0;
		if (StringUtils.isNotNull(filetreeId)) {
			rows = fileTreeMapper.updateFileTree(fileTree);
		} else {
			rows = fileTreeMapper.insertFileTree(fileTree);
		}
		return rows;
	}

	/**
	 * 删除文件树信息
	 * 
	 * @param filetreeId
	 *            文件树ID
	 * @return 结果
	 */
	@Override
	public int deleteFileTreeById(Integer filetreeId) {
		return fileTreeMapper.deleteFileTreeById(filetreeId);
	}

	/**
	 * 批量删除文件树对象
	 * 
	 * @param filetreeIds
	 *            需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int batchDeleteFileTree(Integer[] filetreeIds) {
		return fileTreeMapper.batchDeleteFileTree(filetreeIds);
	}

	// 根据id删除
	@Override
	public int deleteFileTreeByCustomSpiderId(Integer customSpiderId) {
		return fileTreeMapper.deleteFileTreeByCustomSpiderId(customSpiderId);
	}

	// 查询每一条文件信息，根据csid和childid
	@Override
	public FileTree selectFileTreeByCSIdAndChildId(FileTree fileTree) {
		return fileTreeMapper.selectFileTreeByCSIdAndChildId(fileTree);
	}

}
