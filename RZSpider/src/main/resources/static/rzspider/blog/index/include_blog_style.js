$(document).ready(function() {
	// 设置颜色.menu
	setBackColor();

	// 设置样式形状平滑度
});

function setBackColor() {
	// 网站色调
	// 正常导航栏
	$(".menu")
			.css(
					'background-color',
					(blogset.stylesetColor == null || blogset.stylesetColor == '') ? '#000'
							: blogset.stylesetColor);

	// 搜索框
	$(".search_ico")
			.css(
					'background-color',
					(blogset.stylesetColor == null || blogset.stylesetColor == '') ? '#000'
							: blogset.stylesetColor);

	// 分享按钮
	$(".cd-share")
			.css(
					'background-color',
					(blogset.stylesetColor == null || blogset.stylesetColor == '') ? '#000'
							: blogset.stylesetColor);
	// 回到顶部按钮
	$(".cd-top")
			.css(
					'background-color',
					(blogset.stylesetColor == null || blogset.stylesetColor == '') ? '#000'
							: blogset.stylesetColor);

	// 导航标题
	$(".n1")
			.css(
					'background-color',
					(blogset.stylesetColor == null || blogset.stylesetColor == '') ? '#000'
							: blogset.stylesetColor);
	// 尾部
	$("footer")
			.css(
					'background-color',
					(blogset.stylesetColor == null || blogset.stylesetColor == '') ? '#000'
							: blogset.stylesetColor);

	// 缩小的导航栏
	$("#mnav h2")
			.css(
					'background-color',
					(blogset.stylesetColor == null || blogset.stylesetColor == '') ? '#000'
							: blogset.stylesetColor);
	$("#mnav h2")
			.css(
					'background-color',
					(blogset.stylesetColor == null || blogset.stylesetColor == '') ? '#000'
							: blogset.stylesetColor);

	// 点赞
	$(".diggit")
			.css(
					'background-color',
					(blogset.stylesetColor == null || blogset.stylesetColor == '') ? '#000'
							: blogset.stylesetColor);
	// 打赏
	$(".dasbox")
			.css(
					'background-color',
					(blogset.stylesetColor == null || blogset.stylesetColor == '') ? '#000'
							: blogset.stylesetColor);

	// 背景色
	// 侧边栏
	$(".sidebar div")
			.css(
					'background-color',
					(blogset.stylesetBackColor == null || blogset.stylesetBackColor == '') ? '#FFF'
							: blogset.stylesetBackColor);
	$(".sidebarL div")
			.css(
					'background-color',
					(blogset.stylesetBackColor == null || blogset.stylesetBackColor == '') ? '#FFF'
							: blogset.stylesetBackColor);
	// 专栏选择
	$(".nav li .sub-nav")
			.css(
					'background-color',
					(blogset.stylesetBackColor == null || blogset.stylesetBackColor == '') ? '#FFF'
							: blogset.stylesetBackColor);
	// 博客list
	// 此处不生效，因为文章list是ajax异步请求，更改为加载html的时候设置
	// 具体文章背景
	$(".infosbox")
			.css(
					'background-color',
					(blogset.stylesetBackColor == null || blogset.stylesetBackColor == '') ? '#FFF'
							: blogset.stylesetBackColor);
	// 上下篇
	$(".nextinfo")
			.css(
					'background-color',
					(blogset.stylesetBackColor == null || blogset.stylesetBackColor == '') ? '#FFF'
							: blogset.stylesetBackColor);
	// 相关文章
	$(".otherlink, .xzsm, .ffsm")
			.css(
					'background-color',
					(blogset.stylesetBackColor == null || blogset.stylesetBackColor == '') ? '#FFF'
							: blogset.stylesetBackColor);
	// 搜索框
	$(".input")
			.css(
					'background-color',
					(blogset.stylesetBackColor == null || blogset.stylesetBackColor == '') ? '#FFF'
							: blogset.stylesetBackColor);
	// 公告栏breadcrumb
	$(".breadcrumb")
			.css(
					'background-color',
					(blogset.stylesetBackColor == null || blogset.stylesetBackColor == '') ? '#FFF'
							: blogset.stylesetBackColor);

	// 时间轴
	// 此处不生效，因为文章list是ajax异步请求，更改为加载html的时候设置

	// 平滑度
	// 计算方法：后台参数0-100，根据原尺寸控制，100%表示短边变成圆形
	// 文章list 90px
	// 暂一100为基准
	var blogsRadius = getBorderRadius(90, blogset.stylesetSmoothStyle);
	// 滚动推荐
	$(".banner").css('border-radius', blogsRadius);
	// 关于我
	$(".toppic li").css('border-radius', blogsRadius);
	// 侧边栏所有
	$(".sidebar div").css('border-radius', blogsRadius);
	$(".sidebarL div").css('border-radius', blogsRadius);
	// 关注我
	$("#follow-us ul li").css('border-radius', blogsRadius / 2);

	// 具体文章
	$(".infosbox").css('border-radius', blogsRadius);
	// 专栏
	$(".n1").css('border-radius', blogsRadius / 2);
	$(".n2").css('border-radius', blogsRadius / 2);

	// 专栏选择
	$(".nav li .sub-nav").css('border-radius', blogsRadius);
	// 搜索框
	$(".input").css('border-radius', blogsRadius / 2);
	// 公告栏breadcrumb
	$(".breadcrumb").css('border-radius', blogsRadius / 2);
}
