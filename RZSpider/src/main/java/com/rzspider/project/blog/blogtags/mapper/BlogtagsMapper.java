package com.rzspider.project.blog.blogtags.mapper;

import com.rzspider.project.blog.blogtags.domain.Blogtags;
import java.util.List;

/**
 * 博客标签 数据层
 * 
 * @author ricozhou
 * @date 2018-09-20
 */
public interface BlogtagsMapper {

	/**
	 * 查询博客标签信息
	 * 
	 * @param blogTagsId
	 *            博客标签ID
	 * @return 博客标签信息
	 */
	public Blogtags selectBlogtagsById(Integer blogTagsId);

	/**
	 * 查询博客标签列表
	 * 
	 * @param blogtags
	 *            博客标签信息
	 * @return 博客标签集合
	 */
	public List<Blogtags> selectBlogtagsListByStatus(Integer status);

	/**
	 * 查询博客标签列表
	 * 
	 * @param blogtags
	 *            博客标签信息
	 * @return 博客标签集合
	 */
	public List<Blogtags> selectBlogtagsList(Blogtags blogtags);

	/**
	 * 新增博客标签
	 * 
	 * @param blogtags
	 *            博客标签信息
	 * @return 结果
	 */
	public int insertBlogtags(Blogtags blogtags);

	/**
	 * 修改博客标签
	 * 
	 * @param blogtags
	 *            博客标签信息
	 * @return 结果
	 */
	public int updateBlogtags(Blogtags blogtags);

	/**
	 * 删除博客标签
	 * 
	 * @param blogTagsId
	 *            博客标签ID
	 * @return 结果
	 */
	public int deleteBlogtagsById(Integer blogTagsId);

	/**
	 * 批量删除博客标签
	 * 
	 * @param blogTagsIds
	 *            需要删除的数据ID
	 * @return 结果
	 */
	public int batchDeleteBlogtags(Integer[] blogTagsIds);

	/**
	 * @date Sep 21, 2018 11:31:44 AM
	 * @Desc
	 * @param blogtags
	 * @return
	 */
	public int insertBlogtagsName(Blogtags blogtags);

	/**
	 * @date Sep 21, 2018 12:37:44 PM
	 * @Desc
	 * @param cid
	 * @return
	 */
	public List<Blogtags> selectBlogtagsByCid(Long cid);

	/**
	 * @date Sep 21, 2018 3:06:28 PM
	 * @Desc
	 * @param blogTagsName
	 * @return
	 */
	public Blogtags selectBlogtagsByName(String blogTagsName);

	/**
	 * @date Sep 25, 2018 1:09:08 PM
	 * @Desc
	 * @param blogtags
	 * @return
	 */
	public List<Blogtags> selectBlogtagsListWithoutImg(Blogtags blogtags);

	/**
	 * @date Sep 25, 2018 1:16:55 PM
	 * @Desc
	 * @param blogtags
	 * @return
	 */
	public Blogtags selectBlogtagsByStatusAndName(Blogtags blogtags);

	/**
	 * @date Oct 25, 2018 2:19:49 PM
	 * @Desc
	 * @return
	 */
	public Integer selectAllBlogTagsNum();

}