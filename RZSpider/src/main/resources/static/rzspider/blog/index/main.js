var pageSize = blogset.blogsetPerpageShowNum;
var pageNum = 1;
var total;
// 背景颜色
var backColor = (blogset.stylesetBackColor == null || blogset.stylesetBackColor == '') ? '#FFF'
		: blogset.stylesetBackColor;
// 平滑度
var blogsRadius = getBorderRadius(100, blogset.stylesetSmoothStyle);
function resetScroll() {
	// 检测ie 6789
	if (!(/msie [6|7|8|9]/i.test(navigator.userAgent))) {
		new scrollReveal({
			reset : true
		});
	}
}
// 是否手动自动加载
var autoLoading = blogset.basicsetAutoLoading;
// 是否显示文章来源
var basicsetShowArticleSource = blogset.basicsetShowArticleSource;
// 滚动事件
// 到底部自动加载
if (autoLoading == 0) {
	$(document).scroll(
			function() {
				if ($(document).scrollTop() + window.innerHeight == $(document)
						.height()) {
					if (total > (pageNum - 1) * pageSize) {
						nextPage();
					}
				}
			});
}

$(function() {
	bindList(pageNum);
	// resetScroll();

	// 请求推荐信息(包括关于我关于本站)
	blogRecommendListData2();

	// 设置滚动推荐界面
	if (blogRecommendList2 != null && blogRecommendList2 != '') {
		// 生成
		var html = '';
		var imgHtml = '';
		// 滚动推荐
		// 滚动推荐不存在则取出列表推荐
		if (blogRecommendList2.scrollRecommendedBlogcontentList != null
				&& blogRecommendList2.scrollRecommendedBlogcontentList != ''
				&& blogRecommendList2.scrollRecommendedBlogcontentList.length > 0) {

			for (var i = 0; i < blogRecommendList2.scrollRecommendedBlogcontentList.length; i++) {
				imgHtml = getSpecialRecommendedImg(
						blogRecommendList2.scrollRecommendedBlogcontentList[i],
						blogset.blogsetDefaultPic)
				html = html
						+ '<li class="slide"><a href="/rzblog/blogcontent/details/'
						+ blogRecommendList2.scrollRecommendedBlogcontentList[i].showId
						+ '" target="_blank" title="'
						+ blogRecommendList2.scrollRecommendedBlogcontentList[i].title
						+ '">'
						+ imgHtml
						+ '<span class="imginfo">'
						+ blogRecommendList2.scrollRecommendedBlogcontentList[i].title
						+ '</span></a></li>';
			}
		} else if ((blogRecommendList2.aboutmeBlogcontent != null && blogRecommendList2.aboutmeBlogcontent != '')
				|| (blogRecommendList2.aboutwebsiteBlogcontent != null && blogRecommendList2.aboutwebsiteBlogcontent != '')) {
			if (blogRecommendList2.aboutmeBlogcontent != null
					&& blogRecommendList2.aboutmeBlogcontent != '') {
				imgHtml = getSpecialRecommendedImg(
						blogRecommendList2.aboutmeBlogcontent,
						blogset.blogsetDefaultPic)
				html = html
						+ '<li class="slide"><a href="/rzblog/blogcontent/details/'
						+ blogRecommendList2.aboutmeBlogcontent.showId
						+ '" target="_blank" title="'
						+ blogRecommendList2.aboutmeBlogcontent.title + '">'
						+ imgHtml + '<span class="imginfo">'
						+ blogRecommendList2.aboutmeBlogcontent.title
						+ '</span></a></li>';
			}
			if (blogRecommendList2.aboutwebsiteBlogcontent != null
					&& blogRecommendList2.aboutwebsiteBlogcontent != '') {
				imgHtml = getSpecialRecommendedImg(
						blogRecommendList2.aboutwebsiteBlogcontent,
						blogset.blogsetDefaultPic)
				html = html
						+ '<li class="slide"><a href="/rzblog/blogcontent/details/'
						+ blogRecommendList2.aboutwebsiteBlogcontent.showId
						+ '" target="_blank" title="'
						+ blogRecommendList2.aboutwebsiteBlogcontent.title
						+ '">' + imgHtml + '<span class="imginfo">'
						+ blogRecommendList2.aboutwebsiteBlogcontent.title
						+ '</span></a></li>';
			}
		}
		// 添加
		document.getElementById('banner')
				.insertAdjacentHTML('afterBegin', html)
		// 生效
		/* banner */
		$('#banner').easyFader();

		// 设置右侧关于我和关于本站
		if ((blogRecommendList2.aboutmeBlogcontent == null || blogRecommendList2.aboutmeBlogcontent == '')
				&& (blogRecommendList2.aboutwebsiteBlogcontent == null || blogRecommendList2.aboutwebsiteBlogcontent == '')) {
			document.getElementById("banner2").style.width = "100%";
		} else {
			var html = '';
			var imgHtml = '';
			if (blogRecommendList2.aboutmeBlogcontent != null
					&& blogRecommendList2.aboutmeBlogcontent != '') {
				// 显示
				$("#toppic").css('display', 'block');
				imgHtml = getSpecialRecommendedImg(
						blogRecommendList2.aboutmeBlogcontent,
						blogset.blogsetDefaultPic);
				html = html + '<li><a href="/rzblog/blogcontent/details/'
						+ blogRecommendList2.aboutmeBlogcontent.showId
						+ '" target="_blank">' + imgHtml + '<h2>'
						+ blogRecommendList2.aboutmeBlogcontent.title
						+ '</h2> <span>' + '关于我' + '</span></a></li>'
			}
			if (blogRecommendList2.aboutwebsiteBlogcontent != null
					&& blogRecommendList2.aboutwebsiteBlogcontent != '') {
				// 显示
				$("#toppic").css('display', 'block');
				imgHtml = getSpecialRecommendedImg(
						blogRecommendList2.aboutwebsiteBlogcontent,
						blogset.blogsetDefaultPic);
				html = html + '<li><a href="/rzblog/blogcontent/details/'
						+ blogRecommendList2.aboutwebsiteBlogcontent.showId
						+ '" target="_blank">' + imgHtml + '<h2>'
						+ blogRecommendList2.aboutwebsiteBlogcontent.title
						+ '</h2> <span>' + '关于本站' + '</span></a></li>'
			}

			// 添加
			document.getElementById('toppic').insertAdjacentHTML('afterBegin',
					html)
		}

	}
	// 请求公告
	if (blogset.basicsetOpenNotice == 0) {
		// 如果开启公告
		// 请求
		blogNoticeListData();
		if (blogNoticeList != null && blogNoticeList != ''
				&& blogNoticeList.length > 0) {
			// 显示
			$("#breadcrumb").css('display', 'block');
			// 添加
			var noticeHtml = '';
			for (var i = 0; i < blogNoticeList.length; i++) {
				noticeHtml = noticeHtml + '<li class="scrolltext-title">'
						+ blogNoticeList[i].noticeContent + '</li>';
			}
			// 添加
			document.getElementById('notice-box').insertAdjacentHTML(
					'afterBegin', noticeHtml)
			// 设置滚动
			// 调用 公告滚动函数
			setInterval("noticeUp('.scrolltext ul','-20px',700)", 3000);
		}
	}

});

// 下一页
function nextPage() {
	bindList(pageNum)
}
// 绑定查询列表
function bindList() {
	if(pageNum==1){
		//首次请求先请求置顶文章
		
	}
	$
			.ajax({
				url : '/rzblog/blogList',
				method : 'post',
				data : {
					status : 1,
					pageSize : pageSize,
					pageNum : pageNum
					
				},
				dataType : 'json',
				success : function(data) {
					console.log(data)
					var rows = data.rows;
					if (rows == null || rows == '') {
						var nodataHtml = '<p class="news_title">暂无文章！</p>';
						$('#blogsbox').append(nodataHtml);
						return;
					}
					// 手动加载删除
					if (autoLoading == 1) {
						var removeObj = document
								.getElementsByClassName('clearfix')[0];
						if (removeObj) {
							removeObj.parentNode.removeChild(removeObj);
						}
					}

					total = data.total;

					var htmlText = "";

					for (i = 0; i < rows.length; i++) {
						// 封面
						coverHtml = getCoverHtml(rows[i]);
						// 简介
						htmlIntroduction = getHtmlIntroduction(rows[i]);
						// 来源
						articleSourceHtml = getHtmlArticleSource(rows[i],
								basicsetShowArticleSource);
						topHtml = getTopClassElement(rows[i]);
						// 专栏
						// 默认的不显示，其他的显示
						column = getHtmlColumn(rows[i]);
						htmlText = '<div class="blogs" style="background-color:'
								+ backColor
								+ ';border-radius:'
								+ blogsRadius
								+ 'px;" data-scroll-reveal="enter bottom over 1s">'
								+ topHtml
								+ '<h3 class="blogtitle">'
								+ '<a href="/rzblog/blogcontent/details/'
								+ rows[i].showId
								+ '" target="_blank"><span class="article-type type-'
								+ (rows[i].type == '原创' ? '1'
										: (rows[i].type == '转载' ? '2' : '3'))
								+ '">'
								+ (rows[i].type == '原创' ? '原'
										: (rows[i].type == '转载' ? '转' : '译'))
								+ '</span>'
								+ rows[i].title
								+ '</a></h3>'
								+ coverHtml
								+ htmlIntroduction
								+ '<div class="bloginfo"><ul>'
								+ '<li class="author"><i class="fa fa-user"></i> '
								+ rows[i].author
								+ '</li>'
								+ column
								+ '<li class="timer"><i class="fa fa-calendar-o"></i> '
								+ rows[i].gtmCreate
								+ '</li>'
								+ '<li class="view"><span><i class="fa fa-eye"></i> '
								+ rows[i].browseNum
								+ '</span></li>'
								+ '<li class="like"><i class="fa fa-thumbs-up"></i> '
								+ rows[i].likeNum
								+ '</li>'
								+ articleSourceHtml
								+ '</ul></div></div>';
						$('#blogsbox').append(htmlText);
					}
					// 设置手动加载
					var clearfixHtml;
					if (total < pageNum * pageSize + 1) {
						clearfixHtml = '<div class="clearfix"><p id="flagLoaded" >已全部加载</p></div>';
						$('#blogsbox').append(clearfixHtml);
					} else {
						if (autoLoading == 1) {
							clearfixHtml = '<div class="clearfix"><a id="flagLoad" href="javascript:void(0)" onclick="nextPage()">加载更多</a></div>';
							$('#blogsbox').append(clearfixHtml);
						}
					}

					// 异步加载html每次需要重新滚动设置
					resetScroll();
					pageNum++;
				}
			});
}

// 拼接封面
function getCoverHtml(data) {
	// 图片不存在将以默认图片显示
	defaultImg = getOneDefaultImg(1);
	var image;
	var imgArray;
	var coverHtml = '';
	// 原则是：如果有封面图片则使用封面图片，没有封面图片根据设置是否使用文章内容图片，文章没有图片的根据设置判断是否使用默认图片，不使用默认图片的则直接文字显示
	// 出错使用默认图片
	if (data.cover != null && (data.cover.indexOf('/blogfiles/') == 0)) {
		image = data.cover;
	} else if (blogset.blogsetNoCoverpicUseContentpic == 0) {
		// 使用内容图片
		imgArray = getContentImgArray(data);
		if (imgArray != null && imgArray.length > 0) {
			// 内容图片
			if (imgArray.length == 1) {
				image = imgArray[0];
			} else {
				coverHtml = '<span class="bplist"><a href="/rzblog/blogcontent/details/'
						+ data.showId
						+ '" target="_blank" title="'
						+ data.title + '">';
				// 不止一张,最多3张
				for (var i = 0; i < (imgArray.length > 3 ? 3 : imgArray.length); i++) {
					coverHtml = coverHtml + '<li><img src="' + imgArray[i]
							+ '" alt="' + data.title + '"></li>';
				}

				return coverHtml + '</a></span>';
			}
		} else {
			if (blogset.blogsetNoPicUseDefault == 0) {
				// 是否使用默认图片
				image = defaultImg;
			} else {
				// 不使用图片
				return coverHtml;
			}
		}
	} else {
		if (blogset.blogsetNoPicUseDefault == 0) {
			// 是否使用默认图片
			image = defaultImg;
		} else {
			// 不使用图片
			return coverHtml;
		}
	}

	coverHtml = '<span class="blogpic"><a href="/rzblog/blogcontent/details/'
			+ data.showId + '" target="_blank" title="' + data.title
			+ '"><img src="' + image + '" alt="' + data.title
			+ '" ></a></span>';
	return coverHtml;
}

// 获取文章所有图片
function getContentImgArray(data) {
	// 此判断不正确，后续跟进
	if (data.images == null || data.images == '') {
		return null;
	}
	return data.images.split(",");

}

// 简介
function getHtmlIntroduction(data) {
	var htmlIntroduction = '';
	if (data.showIntroduction == 0) {
		htmlIntroduction = '<a href="/rzblog/blogcontent/details/'
				+ data.showId + '" target="_blank"><p class="blogtext">'
				+ data.introduction + '......</p></a>';
	}
	return htmlIntroduction;
}
// 专栏
function getHtmlColumn(data) {
	var htmlColumn = '';
	if (data.blogColumnName != '默认') {
		htmlColumn = '<li class="lmname"><i class="fa fa-list"></i> '
				+ data.blogColumnName + '</li>';
	}
	return htmlColumn;
}

// 来源
function getHtmlArticleSource(data, basicsetShowArticleSource) {
	var html = '';
	if (basicsetShowArticleSource == 0 && data.articleSource != null
			&& data.articleSource != '' && data.articleSource != '本站') {
		html = '<li class="articleSource"><i class="fa fa-thumb-tack"></i> '
				+ data.articleSource + '</li>';
	}
	return html;
}

// 置顶html
function getTopClassElement(data) {
	var html = '';
	if (data.articleTop == 0) {
		html = '<svg class="icon settop" aria-hidden="true"><use xlink:href="#csdnc-settop"><svg id="csdnc-settop" viewBox="0 0 1024 1024" width="100%" height="100%"><path d="M0 0h1024v1024z" fill="#7ED321"></path><path d="M571.733333 157.866667l17.066667-12.8-83.2-83.2L552.533333 14.933333l183.466667 183.466667-46.933333 46.933333-81.066667-81.066666-17.066667 12.8 100.266667 100.266666-14.933333 14.933334-102.4-102.4c-6.4 4.266667-10.666667 8.533333-17.066667 10.666666l72.533333 72.533334-110.933333 110.933333 36.266667 36.266667-14.933334 14.933333L313.6 209.066667l14.933333-14.933334 36.266667 36.266667 110.933333-110.933333 61.866667 61.866666c6.4-4.266667 10.666667-8.533333 17.066667-10.666666l-96-96 14.933333-14.933334 98.133333 98.133334z m-72.533333 209.066666l17.066667-17.066666-117.333334-117.333334-17.066666 17.066667 117.333333 117.333333z m27.733333-29.866666l14.933334-14.933334L426.666667 204.8l-14.933334 14.933333 115.2 117.333334z m27.733334-27.733334l17.066666-14.933333-117.333333-117.333333-17.066667 14.933333 117.333334 117.333333z m27.733333-25.6l14.933333-14.933333L482.133333 149.333333l-14.933333 14.933334 115.2 119.466666z m10.666667-202.666666L554.666667 44.8l-21.333334 21.333333 38.4 38.4 21.333334-23.466666z m57.6 57.6l-40.533334-40.533334-21.333333 21.333334 40.533333 40.533333 21.333334-21.333333zM704 192l-38.4-38.4-21.333333 21.333333L682.666667 213.333333l21.333333-21.333333zM571.733333 471.466667l12.8-21.333334c8.533333 10.666667 17.066667 19.2 25.6 27.733334 6.4 6.4 12.8 6.4 21.333334-2.133334l172.8-172.8-38.4-38.4 17.066666-17.066666 87.466667 87.466666-17.066667 17.066667-29.866666-29.866667-177.066667 177.066667c-14.933333 14.933333-29.866667 14.933333-44.8 0l-29.866667-27.733333z m302.933334 21.333333l-44.8 44.8c-27.733333 25.6-55.466667 40.533333-83.2 44.8-27.733333 2.133333-59.733333-6.4-96-25.6l6.4-25.6c34.133333 19.2 64 27.733333 87.466666 25.6 23.466667-4.266667 46.933333-14.933333 68.266667-36.266667l44.8-44.8 17.066667 17.066667z m132.266666-21.333333l-17.066666 19.2-55.466667-55.466667c-10.666667 8.533333-19.2 17.066667-29.866667 23.466667l51.2 51.2-119.466666 119.466666-17.066667-17.066666 102.4-102.4-76.8-76.8-104.533333 100.266666-17.066667-17.066666 121.6-121.6 42.666667 42.666666c10.666667-6.4 19.2-14.933333 29.866666-23.466666L861.866667 362.666667l17.066666-17.066667 128 125.866667zM802.133333 682.666667h-25.6c2.133333-25.6 2.133333-55.466667-2.133333-89.6h23.466667c4.266667 34.133333 4.266667 64 4.266666 89.6z" fill="#FFFFFF"></path></svg></use></svg>';
	}
	return html;
}