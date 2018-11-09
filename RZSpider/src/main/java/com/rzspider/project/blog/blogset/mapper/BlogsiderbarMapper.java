package com.rzspider.project.blog.blogset.mapper;

import com.rzspider.project.blog.blogset.domain.Blogsiderbar;
import java.util.List;

/**
 * 博客侧边栏 数据层
 * 
 * @author ricozhou
 * @date 2018-10-09
 */
public interface BlogsiderbarMapper {

	/**
	 * 查询博客侧边栏信息
	 * 
	 * @param blogSiderbrId
	 *            博客侧边栏ID
	 * @return 博客侧边栏信息
	 */
	public Blogsiderbar selectBlogsiderbarById(Integer blogSiderbrId);

	/**
	 * 查询博客侧边栏列表
	 * 
	 * @param blogsiderbar
	 *            博客侧边栏信息
	 * @return 博客侧边栏集合
	 */
	public List<Blogsiderbar> selectBlogsiderbarList(Blogsiderbar blogsiderbar);

	/**
	 * 新增博客侧边栏
	 * 
	 * @param blogsiderbar
	 *            博客侧边栏信息
	 * @return 结果
	 */
	public int insertBlogsiderbar(Blogsiderbar blogsiderbar);

	/**
	 * 修改博客侧边栏
	 * 
	 * @param blogsiderbar
	 *            博客侧边栏信息
	 * @return 结果
	 */
	public int updateBlogsiderbar(Blogsiderbar blogsiderbar);

	/**
	 * 删除博客侧边栏
	 * 
	 * @param blogSiderbrId
	 *            博客侧边栏ID
	 * @return 结果
	 */
	public int deleteBlogsiderbarById(Integer blogSiderbrId);

	/**
	 * 批量删除博客侧边栏
	 * 
	 * @param blogSiderbrIds
	 *            需要删除的数据ID
	 * @return 结果
	 */
	public int batchDeleteBlogsiderbar(Integer[] blogSiderbrIds);

	/**
	 * @date Oct 9, 2018 2:45:57 PM
	 * @Desc
	 * @param blogInternalSiderbar
	 * @return
	 */
	public int deleteBlogsiderbarByInternal(Integer blogInternalSiderbar);

	/**
	 * @date Oct 9, 2018 3:40:13 PM
	 * @Desc
	 * @param i
	 */
	public int updateBlogsiderbarShowRight(Integer blogShowRightSiderbr);

	/**
	 * @date Oct 9, 2018 3:40:53 PM
	 * @Desc
	 * @param valueOf
	 * @param i
	 * @return
	 */
	public int updateBlogsiderbarShowRightById(Integer blogSiderbrId, Integer blogShowRightSiderbr);

	/**
	 * @date Oct 9, 2018 3:40:58 PM
	 * @Desc
	 * @param i
	 */
	public int updateBlogsiderbarShowLeft(Integer blogShowLeftSiderbr);

	/**
	 * @date Oct 9, 2018 3:41:02 PM
	 * @Desc
	 * @param valueOf
	 * @param i
	 * @return
	 */
	public int updateBlogsiderbarShowLeftById(Integer blogSiderbrId, Integer blogShowLeftSiderbr);

}