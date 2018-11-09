package com.rzspider.project.spider.customspider.mapper;

import com.rzspider.project.spider.customspider.domain.FileTree;
import java.util.List;	

/**
 * 文件树 数据层
 * 
 * @author ricozhou
 * @date 2018-07-02
 */
public interface FileTreeMapper 
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
     * @param spiderfiletree 文件树信息
     * @return 文件树集合
     */
	public List<FileTree> selectFileTreeList(FileTree spiderfiletree);
	
	/**
     * 新增文件树
     * 
     * @param spiderfiletree 文件树信息
     * @return 结果
     */
	public int insertFileTree(FileTree spiderfiletree);
	
	/**
     * 修改文件树
     * 
     * @param spiderfiletree 文件树信息
     * @return 结果
     */
	public int updateFileTree(FileTree spiderfiletree);
	
	/**
     * 删除文件树
     * 
     * @param filetreeId 文件树ID
     * @return 结果
     */
	public int deleteFileTreeById(Integer filetreeId);
	
	/**
     * 批量删除文件树
     * 
     * @param filetreeIds 需要删除的数据ID
     * @return 结果
     */
	public int batchDeleteFileTree(Integer[] filetreeIds);

	public int deleteFileTreeByCustomSpiderId(Integer customSpiderId);

	public FileTree selectFileTreeByCSIdAndChildId(FileTree fileTree);
	
}