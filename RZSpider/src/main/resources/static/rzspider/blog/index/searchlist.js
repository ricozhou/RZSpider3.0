$(document)
		.ready(
				function() {
					title = basicsetTitle + "-搜索（" + keyword + "）";
					// 设置搜索信息
					if (keyword != null && keyword != '') {
						$("#n1").css('display', 'block');
						$("#n2").css('display', 'block');
						// 首页，
						document.getElementById("n1").href = "/rzblog";
						document.getElementById("n1").innerText = "首页";

						document.getElementById("n2").href = '/rzblog/search/?keyword='
								+ keyword;
						document.getElementById("n2").innerText = "搜索（"
								+ keyword + "）";

					}
					document.title = title;
					// 设置搜索框显示
					$('.search_bar').toggleClass('search_open');
					$('#keyboard').val(keyword);
				})

var pageSize = blogset.blogsetPerpageShowNum;
var pageNum = 1;
var total;
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
//是否显示文章来源
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
});
// 下一页
function nextPage() {
	bindList(pageNum)
}
// 绑定查询列表
function bindList() {
	$
			.ajax({
				url : '/rzblog/searchList',
				method : 'post',
				data : {
					status : 1,
					content : keyword,
					pageSize : pageSize,
					pageNum : pageNum,
					orderByColumn : "gtm_create",
					isAsc : "desc"
				},
				dataType : 'json',
				success : function(data) {
					var rows = data.rows;
					if (rows == null || rows == '') {
						var nodataHtml = '<p class="news_title">暂无文章，请搜索其他关键字！</p>';
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
						articleSourceHtml = getHtmlArticleSource(rows[i],basicsetShowArticleSource);
						// 专栏
						// 默认的不显示，其他的显示
						column = getHtmlColumn(rows[i]);
						htmlText = '<div class="blogs" style="background-color:'
								+ backColor
								+ ';border-radius:'
								+ blogsRadius
								+ 'px;" data-scroll-reveal="enter bottom over 1s"><h3 class="blogtitle">'
								+ '<a href="/rzblog/blogcontent/details/'
								+ rows[i].showId
								+ '" target="_blank"><span class="article-type type-'
								+ (rows[i].type == '原创' ? '1'
										: (rows[i].type == '转载' ? '2' : '3'))
								+ '">'
								+ (rows[i].type == '原创' ? '原'
										: (rows[i].type == '转载' ? '转' : '译'))
								+ '</span>'
								+ getHeightKeyword(rows[i].title)
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

// 获取高亮text
function getHeightKeyword(html) {
	return html.split(keyword).join(
			'<span style="color:red;">' + keyword + '</span>');
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
				// 不止一张
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
			+ '" onerror="this.src=' + defaultImg + '"></a></span>';
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
				+ getHeightKeyword(data.introduction) + '......</p></a>';
	}
	return htmlIntroduction;
}
// 专栏
function getHtmlColumn(data) {
	var htmlColumn = '';
	if (data.blogColumnName != '默认') {
		htmlColumn = '<li class="lmname">' + data.blogColumnName + '</li>';
	}
	return htmlColumn;
}
//来源
function getHtmlArticleSource(data, basicsetShowArticleSource) {
	var html = '';
	if (basicsetShowArticleSource == 0 && data.articleSource != null
			&& data.articleSource != '' && data.articleSource != '本站') {
		html = '<li class="articleSource"><i class="fa fa-thumb-tack"></i> '
				+ data.articleSource + '</li>';
	}
	return html;
}