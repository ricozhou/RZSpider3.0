package com.rzspider.project.blog.blogcontent.mapper;

import com.rzspider.project.blog.blogcontent.domain.Blogcontent;
import com.rzspider.project.blog.blogcontent.domain.BlogcontentTags;

import java.util.List;

/**
 * 文章内容 数据层
 * 
 * @author ricozhou
 * @date 2018-06-12
 */
public interface BlogcontentTagsMapper {

	/**
	 * 根据id删除级联关系
	 * 
	 * @param cid
	 *            文章内容ID
	 * @return 文章内容信息
	 */
	public int deleteBlogcontentTagsById(Long cid);

	/**
	 * 批量新增关系信息
	 * 
	 * @param
	 * @return 结果
	 */
	public int batchBlogcontentTags(List<BlogcontentTags> blogcontentTagsList);

	/**
	 * @date Sep 21, 2018 12:26:09 PM
	 * @Desc
	 * @param cids
	 */
	public int deleteBlogcontentTags(Long[] cids);

	/**
	 * @date Sep 25, 2018 11:56:48 AM
	 * @Desc
	 * @param blogTagsId
	 */
	public int deleteBlogcontentTagsByBlogTagsId(Integer blogTagsId);

	/**
	 * @date Sep 25, 2018 11:56:59 AM
	 * @Desc
	 * @param blogTagsIds
	 */
	public int batchDeleteBlogcontentTagsByBlogTagsId(Integer[] blogTagsIds);

}