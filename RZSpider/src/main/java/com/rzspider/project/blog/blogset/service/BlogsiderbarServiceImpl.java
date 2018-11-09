package com.rzspider.project.blog.blogset.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rzspider.common.constant.CommonSymbolicConstant;
import com.rzspider.common.constant.project.BlogConstant;
import com.rzspider.common.utils.JsonUtils;
import com.rzspider.common.utils.StringUtils;
import com.rzspider.project.blog.blogset.mapper.BlogsiderbarMapper;
import com.rzspider.project.blog.blogset.domain.Blogset;
import com.rzspider.project.blog.blogset.domain.Blogsiderbar;
import com.rzspider.project.blog.blogset.service.IBlogsiderbarService;

/**
 * 博客侧边栏 服务层实现
 * 
 * @author ricozhou
 * @date 2018-10-09
 */
@Service
public class BlogsiderbarServiceImpl implements IBlogsiderbarService {
	@Autowired
	private BlogsiderbarMapper blogsiderbarMapper;

	/**
	 * 查询博客侧边栏信息
	 * 
	 * @param blogSiderbrId
	 *            博客侧边栏ID
	 * @return 博客侧边栏信息
	 */
	@Override
	public Blogsiderbar selectBlogsiderbarById(Integer blogSiderbrId) {
		return blogsiderbarMapper.selectBlogsiderbarById(blogSiderbrId);
	}

	/**
	 * 查询博客侧边栏列表
	 * 
	 * @param blogsiderbar
	 *            博客侧边栏信息
	 * @return 博客侧边栏集合
	 */
	@Override
	public List<Blogsiderbar> selectBlogsiderbarList(Blogsiderbar blogsiderbar) {
		return blogsiderbarMapper.selectBlogsiderbarList(blogsiderbar);
	}

	/**
	 * 新增博客侧边栏
	 * 
	 * @param blogsiderbar
	 *            博客侧边栏信息
	 * @return 结果
	 */
	@Override
	public int insertBlogsiderbar(Blogsiderbar blogsiderbar) {
		return blogsiderbarMapper.insertBlogsiderbar(blogsiderbar);
	}

	/**
	 * 新增博客侧边栏
	 * 
	 * @param blogsiderbar
	 *            博客侧边栏信息
	 * @return 结果
	 */
	@Override
	public int insertCustomBlogsiderbar(String blogsetSidebarOtherMessage) {
		if (blogsetSidebarOtherMessage == null) {
			return 1;
		}
		// 先清除
		deleteBlogsiderbarByInternal(1);

		// 转为json并遍历
		List<String> strAry = JsonUtils.jsonArrayStringToListByKey(blogsetSidebarOtherMessage,
				BlogConstant.BLOG_BLOGSET_KEY_WORD_1);
		if (strAry == null || strAry.size() < 1) {
			return 1;
		}
		int row = 0;
		Blogsiderbar blogsiderbar = new Blogsiderbar();
		for (String str : strAry) {
			blogsiderbar.setBlogSiderbrName(str);
			blogsiderbar.setBlogShowRightSiderbr(0);
			blogsiderbar.setBlogShowLeftSiderbr(1);
			blogsiderbar.setBlogInternalSiderbar(1);
			row = blogsiderbarMapper.insertBlogsiderbar(blogsiderbar);
		}
		return row;
	}

	/**
	 * 修改博客侧边栏
	 * 
	 * @param blogsiderbar
	 *            博客侧边栏信息
	 * @return 结果
	 */
	@Override
	public int updateBlogsiderbar(Blogset blogset) {
		int row = 0;
		if (blogset == null || blogset.getStylesetRightSiderbarContent() == null
				|| blogset.getStylesetLeftSiderbarContent() == null) {
			return 1;
		}
		// 更新右侧显示内容
		// 先全部不显示
		blogsiderbarMapper.updateBlogsiderbarShowRight(1);
		String[] strRights = blogset.getStylesetRightSiderbarContent().split(CommonSymbolicConstant.COMMA);
		for (String id : strRights) {
			// 更新
			if (!CommonSymbolicConstant.EMPTY_STRING.equals(id)) {
				row = blogsiderbarMapper.updateBlogsiderbarShowRightById(Integer.valueOf(id), 0);
			}
		}

		// 更新左侧显示内容
		// 先全部不显示
		blogsiderbarMapper.updateBlogsiderbarShowLeft(1);
		String[] strLefts = blogset.getStylesetLeftSiderbarContent().split(CommonSymbolicConstant.COMMA);
		for (String id : strLefts) {
			// 更新
			if (!CommonSymbolicConstant.EMPTY_STRING.equals(id)) {
				row = blogsiderbarMapper.updateBlogsiderbarShowLeftById(Integer.valueOf(id), 0);
			}
		}

		return row;
	}

	/**
	 * 修改博客侧边栏
	 * 
	 * @param blogsiderbar
	 *            博客侧边栏信息
	 * @return 结果
	 */
	@Override
	public int updateBlogsiderbar(Blogsiderbar blogsiderbar) {
		return blogsiderbarMapper.updateBlogsiderbar(blogsiderbar);
	}

	/**
	 * 保存博客侧边栏
	 * 
	 * @param blogsiderbar
	 *            博客侧边栏信息
	 * @return 结果
	 */
	@Override
	public int saveBlogsiderbar(Blogsiderbar blogsiderbar) {
		Integer blogSiderbrId = blogsiderbar.getBlogSiderbrId();
		int rows = 0;
		if (StringUtils.isNotNull(blogSiderbrId)) {
			rows = blogsiderbarMapper.updateBlogsiderbar(blogsiderbar);
		} else {
			rows = blogsiderbarMapper.insertBlogsiderbar(blogsiderbar);
		}
		return rows;
	}

	/**
	 * 删除博客侧边栏信息
	 * 
	 * @param blogSiderbrId
	 *            博客侧边栏ID
	 * @return 结果
	 */
	@Override
	public int deleteBlogsiderbarById(Integer blogSiderbrId) {
		return blogsiderbarMapper.deleteBlogsiderbarById(blogSiderbrId);
	}

	/**
	 * 删除博客侧边栏信息
	 * 
	 * @param blogSiderbrId
	 *            博客侧边栏ID
	 * @return 结果
	 */
	@Override
	public int deleteBlogsiderbarByInternal(Integer blogInternalSiderbar) {
		return blogsiderbarMapper.deleteBlogsiderbarByInternal(blogInternalSiderbar);
	}

	/**
	 * 批量删除博客侧边栏对象
	 * 
	 * @param blogSiderbrIds
	 *            需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int batchDeleteBlogsiderbar(Integer[] blogSiderbrIds) {
		return blogsiderbarMapper.batchDeleteBlogsiderbar(blogSiderbrIds);
	}

}
