package com.rzspider.project.blog.blogset.service;

import com.rzspider.project.blog.blogset.domain.Blogset;
import com.rzspider.project.blog.blogset.domain.Blogsiderbar;
import java.util.List;

/**
 * 博客侧边栏 服务层
 * 
 * @author ricozhou
 * @date 2018-10-09
 */
public interface IBlogsiderbarService {

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
	 * 保存博客侧边栏
	 * 
	 * @param blogsiderbar
	 *            博客侧边栏信息
	 * @return 结果
	 */
	public int saveBlogsiderbar(Blogsiderbar blogsiderbar);

	/**
	 * 删除博客侧边栏信息
	 * 
	 * @param blogSiderbrId
	 *            博客侧边栏ID
	 * @return 结果
	 */
	public int deleteBlogsiderbarById(Integer blogSiderbrId);

	/**
	 * 批量删除博客侧边栏信息
	 * 
	 * @param blogSiderbrIds
	 *            需要删除的数据ID
	 * @return 结果
	 */
	public int batchDeleteBlogsiderbar(Integer[] blogSiderbrIds);

	/**
	 * @date Oct 9, 2018 2:36:36 PM
	 * @Desc
	 * @param blogsetSidebarOtherMessage
	 */
	public int insertCustomBlogsiderbar(String blogsetSidebarOtherMessage);

	/**
	 * @date Oct 9, 2018 2:45:51 PM
	 * @Desc
	 * @param blogInternalSiderbar
	 * @return
	 */
	int deleteBlogsiderbarByInternal(Integer blogInternalSiderbar);

	/**
	 * @date Oct 9, 2018 3:28:34 PM
	 * @Desc
	 * @param blogset
	 */
	public int updateBlogsiderbar(Blogset blogset);

}
