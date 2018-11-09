$(document).ready(function() {
	// 先判断屏幕大小，小屏不再显示侧边栏后续操作直接屏蔽
	if ($(window).width() < 974) {
		// 不再渲染侧边栏
		return;
	}
	// 不使用js直接改变css，这样会永久改变导致无法适应屏幕，更改为使用js加载css文件，这样既可生效
	// 最后加载
	var link = document.createElement("link");
	link.rel = "stylesheet";
	link.type = "text/css";
	if (blogset.stylesetShowLeftSiderbar == 0) {
		setSiderbarLMsg();
		// 设置样式，因为默认样式是右侧显示
		if (blogset.stylesetShowRightSiderbar == 0) {
			setSiderbarMsg();
			// 加载双侧css
			link = document.createElement("link");
			link.rel = "stylesheet";
			link.type = "text/css";
			link.href = "/css/blog/siderbarD.css";
			document.getElementsByTagName("head")[0].appendChild(link);
			// 加载控制屏幕css
			link = document.createElement("link");
			link.rel = "stylesheet";
			link.type = "text/css";
			link.href = "/css/blog/siderbarmD.css";
			document.getElementsByTagName("head")[0].appendChild(link);

		} else {
			$("#sidebar").css('display', 'none');
			// 加载左侧css
			link = document.createElement("link");
			link.rel = "stylesheet";
			link.type = "text/css";
			link.href = "/css/blog/siderbarL.css";
			document.getElementsByTagName("head")[0].appendChild(link);
			// 加载控制屏幕css
			link = document.createElement("link");
			link.rel = "stylesheet";
			link.type = "text/css";
			link.href = "/css/blog/siderbarmL.css";
			document.getElementsByTagName("head")[0].appendChild(link);

		}

	} else {
		$("#sidebarL").css('display', 'none');
		if (blogset.stylesetShowRightSiderbar != 0) {
			// 右侧也不显示
			// 都不显示则加载新文件
			$("#sidebar").css('display', 'none');
			link = document.createElement("link");
			link.rel = "stylesheet";
			link.type = "text/css";
			link.href = "/css/blog/siderbarN.css";
			document.getElementsByTagName("head")[0].appendChild(link);
		} else {
			setSiderbarMsg();
		}
	}

	setFollowUs();
});

// 右侧
function setSiderbarMsg() {
	// 设置左右侧显示
	// 判断右侧边栏显示哪些
	var data = blogset.blogsiderbarList;
	for (var i = 0; i < data.length; i++) {
		if (data[i].blogSiderbrName == '博主简介') {
			if (data[i].blogShowRightSiderbr == 0) {
				// 显示
				$("#aboutme").css('display', 'block');
			} else {
				$("#aboutme").css('display', 'none');
			}
		} else if (data[i].blogSiderbrName == '特别推荐') {
			if (data[i].blogShowRightSiderbr == 0) {
				// 显示
				$("#specialRecommended").css('display', 'block');
			} else {
				$("#specialRecommended").css('display', 'none');
			}
		} else if (data[i].blogSiderbrName == '列表推荐') {
			if (data[i].blogShowRightSiderbr == 0) {
				// 显示
				$("#tuijian").css('display', 'block');
			} else {
				$("#tuijian").css('display', 'none');
			}
		} else if (data[i].blogSiderbrName == '文章标签') {
			if (data[i].blogShowRightSiderbr == 0) {
				// 显示
				$("#cloud").css('display', 'block');
			} else {
				$("#cloud").css('display', 'none');
			}
		} else if (data[i].blogSiderbrName == '友情链接') {
			if (data[i].blogShowRightSiderbr == 0) {
				// 显示
				$("#links").css('display', 'block');
			} else {
				$("#links").css('display', 'none');
			}
		} else if (data[i].blogSiderbrName == '关注博主') {
			if (data[i].blogShowRightSiderbr == 0) {
				// 显示
				$("#follow-us").css('display', 'block');
			} else {
				$("#follow-us").css('display', 'none');
			}
		} else {
			// 其他显示
			// 此处其他信息由于动态生成html无法通过id改变显示，更改为在主文件中设置
			if (data[i].blogShowRightSiderbr == 0) {
				// 显示
				$("#" + data[i].blogSiderbrName + "").css('display', 'block');
			} else {
				$("#" + data[i].blogSiderbrName + "").css('display', 'none');
			}
		}

	}
}

// 左侧
function setSiderbarLMsg() {
	// 设置左右侧显示

	// 判断右侧边栏显示哪些
	var data = blogset.blogsiderbarList;
	for (var i = 0; i < data.length; i++) {
		if (data[i].blogSiderbrName == '博主简介') {
			if (data[i].blogShowLeftSiderbr == 0) {
				// 显示
				$("#aboutmeL").css('display', 'block');
			} else {
				$("#aboutmeL").css('display', 'none');
			}
		} else if (data[i].blogSiderbrName == '特别推荐') {
			if (data[i].blogShowLeftSiderbr == 0) {
				// 显示
				$("#specialRecommendedL").css('display', 'block');
			} else {
				$("#specialRecommendedL").css('display', 'none');
			}
		} else if (data[i].blogSiderbrName == '列表推荐') {
			if (data[i].blogShowLeftSiderbr == 0) {
				// 显示
				$("#tuijianL").css('display', 'block');
			} else {
				$("#tuijianL").css('display', 'none');
			}
		} else if (data[i].blogSiderbrName == '文章标签') {
			if (data[i].blogShowLeftSiderbr == 0) {
				// 显示
				$("#cloudL").css('display', 'block');
			} else {
				$("#cloudL").css('display', 'none');
			}
		} else if (data[i].blogSiderbrName == '友情链接') {
			if (data[i].blogShowLeftSiderbr == 0) {
				// 显示
				$("#linksL").css('display', 'block');
			} else {
				$("#linksL").css('display', 'none');
			}
		} else if (data[i].blogSiderbrName == '关注博主') {
			if (data[i].blogShowLeftSiderbr == 0) {
				// 显示
				$("#follow-usL").css('display', 'block');
			} else {
				$("#follow-usL").css('display', 'none');
			}
		} else {
			// 其他显示
			if (data[i].blogShowLeftSiderbr == 0) {
				// 显示
				$("#" + data[i].blogSiderbrName + "L").css('display', 'block');
			} else {
				$("#" + data[i].blogSiderbrName + "L").css('display', 'none');
			}
		}

	}
}

function setFollowUs() {
	// 需要在所有动态加载完设置此，否则位移不准确
	// 设置固定关注我们
	if ($('#follow-us')) {
		var followUsPosition = $('#follow-us').offset().top;
		var followUsPositionL = $('#follow-usL').offset().top;
		window.onscroll = function() {
			var nowPosition = document.documentElement.scrollTop;
			if (blogset.stylesetShowRightSiderbar == 0) {
				if (nowPosition - followUsPosition > 0) {
					setTimeout(function() {
						$('#follow-us').attr('class', 'guanzhu gd');
					}, 150);
				} else {
					$('#follow-us').attr('class', 'guanzhu');
				}
			}

			if (blogset.stylesetShowLeftSiderbar == 0) {
				if (nowPosition - followUsPositionL > 0) {
					setTimeout(function() {
						$('#follow-usL').attr('class', 'guanzhu gd');
					}, 150);
				} else {
					$('#follow-usL').attr('class', 'guanzhu');
				}
			}

		};
	}
}
