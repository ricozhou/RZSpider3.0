package com.rzspider.project.spider.customspider.service;

import com.rzspider.project.spider.customspider.domain.FileTree;
import java.util.List;

/**
 * 文件树 服务层
 * 
 * @author ricozhou
 * @date 2018-07-02
 */
public interface IFileTreeService 
{
	
	/**
     * 查询文件树信息
     * 
     * @param filetreeId 文件树ID
     * @return 文件树信息
     */
	public FileTree selectFileTreeById(Integer filetreeId);
	
	/**
     * 查询文件树列表
     * 
     * @param fileTree 文件树信息
     * @return 文件树集合
     */
	public List<FileTree> selectFileTreeList(FileTree fileTree);
	
	/**
     * 新增文件树
     * 
     * @param fileTree 文件树信息
     * @return 结果
     */
	public int insertFileTree(FileTree fileTree);
	
	/**
     * 修改文件树
     * 
     * @param fileTree 文件树信息
     * @return 结果
     */
	public int updateFileTree(FileTree fileTree);
	
	/**
     * 保存文件树
     * 
     * @param fileTree 文件树信息
     * @return 结果
     */
	public int saveFileTree(FileTree fileTree);
	
	/**
     * 删除文件树信息
     * 
     * @param filetreeId 文件树ID
     * @return 结果
     */
	public int deleteFileTreeById(Integer filetreeId);
	
	/**
     * 批量删除文件树信息
     * 
     * @param filetreeIds 需要删除的数据ID
     * @return 结果
     */
	public int batchDeleteFileTree(Integer[] filetreeIds);

	public int deleteFileTreeByCustomSpiderId(Integer customSpiderId);

	public FileTree selectFileTreeByCSIdAndChildId(FileTree fileTree);
	
}
