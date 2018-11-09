$(document).ready(function() {
	title = basicsetTitle + "-时间轴";
	// 设置
	// 设置专栏背景
	var bgImg = '/img/blog/timeline.jpg';
	$(".sh").css("background", 'url(' + bgImg + ') no-repeat');
	// 动态设置css实现拉伸效果
	$(".sh").css("background-size", "cover")
	$(".sh").css("background-position", "top center")

	// 设置专栏信息
	$("#n1").css('display', 'block');
	$("#n2").css('display', 'block');
	document.getElementById("n1").href = "/rzblog";
	document.getElementById("n1").innerText = "首页";

	document.getElementById("n2").href = '/rzblog/timeline';
	document.getElementById("n2").innerText = '时间轴';
	document.title = title;

	// 设置寄语
	document.getElementById("timelineMsg").innerHTML = '时间轴寄语';
})

var pageSize = (blogset.blogsetPerpageShowNum) * 4;
var pageNum = 1;
var total;
var backColor = (blogset.stylesetBackColor == null || blogset.stylesetBackColor == '') ? '#FFF'
		: blogset.stylesetBackColor;
function resetScroll() {
	// 检测ie 6789
	if (!(/msie [6|7|8|9]/i.test(navigator.userAgent))) {
		new scrollReveal({
			reset : true
		});
	}
}
// 滚动事件
// 到底部自动加载
$(document).scroll(function() {
	if ($(document).scrollTop() + window.innerHeight == $(document).height()) {
		if (total > (pageNum - 1) * pageSize) {
			nextPage();
		}
	}
});

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
	$.ajax({
		url : '/rzblog/blogList',
		method : 'post',
		data : {
			status : 1,
			pageSize : pageSize,
			pageNum : pageNum,
			orderByColumn : "gtm_create",
			isAsc : "desc"
		},
		dataType : 'json',
		success : function(data) {
			var rows = data.rows;
			if (rows == null || rows == '') {
				return;
			}
			total = data.total;

			var htmlText = "";

			for (i = 0; i < rows.length; i++) {
				htmlText = '<li><span >' + rows[i].gtmCreate
						+ '</span><a href="/rzblog/blogcontent/details/'
						+ rows[i].showId + '" target="_blank" title="'
						+ rows[i].title + '" style="background-color:'
						+ backColor + ';" >' + rows[i].title + '</a></li>';
				$('#list').append(htmlText);
			}
			// 异步加载html每次需要重新滚动设置
			resetScroll();
			pageNum++;
		}
	});
}
