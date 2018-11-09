//验证参数
function checkCommentContent(obj) {
	if (obj == null || obj == '' || obj.replyContent == null
			|| obj.replyContent == '' || obj.replyName == null
			|| obj.replyName == '') {
		return 1;
	}

	if (obj.replyContent.length > 400) {
		return 2;
	}
	if (obj.replyQqNum != null && obj.replyQqNum != '' && !isQQ(obj.replyQqNum)) {
		return 3;
	}

	if (obj.replyEmail != null && obj.replyEmail != ''
			&& !isEmail(obj.replyEmail)) {
		return 4;
	}
	if (obj.replyName.length > 20) {
		return 5;
	}
}
// 验证QQ
function isQQ(qq) {
	if (qq.search(/^[1-9]\d{4,8}$/) != -1) {
		return true;
	} else {
		return false;
	}
}

// 验证邮箱
function isEmail(email) {
	if (email
			.search(/^([a-zA-Z0-9]+[_|_|.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|_|.]?)*[a-zA-Z0-9]+\.(?:com|cn)$/) != -1) {
		return true;
	} else {
		return false;
	}
}
// 提交
function submitComment(obj) {
	// 验证
	// 请求评论提交
	var flag = 1;
	var blogCommentId = '';
	var msg = new Array();
	$.ajax({
		cache : true,
		type : "POST",
		url : "/rzblog/blogcomment/addBlogcomment",
		data : {
			"parentId" : obj.parentId,
			"showId" : obj.showId,
			"title" : obj.title,
			"replyName" : obj.replyName,
			"beReplyName" : obj.beReplyName,
			"replyQqNum" : obj.replyQqNum,
			"replyEmail" : obj.replyEmail,
			"replyHeadImg" : obj.replyHeadImg,
			"replyContent" : obj.replyContent,
			"replyTime" : obj.replyTime
		},
		async : false,
		error : function(request) {
			$.modalMsg("评论失败！", "fail");
			flag = -1;
			blogCommentId = '';
		},
		success : function(data) {
			if (data.code == 0) {
				flag = data.flag;
				blogCommentId = data.id;
			} else {
				$.modalMsg("评论失败！", "error");
				flag = -1;
				blogCommentId = '';
			}
		}
	});

	msg[0] = flag;
	msg[1] = blogCommentId;
	return msg;
}
// 点赞请求
function addCommentLike(id) {
	$.ajax({
		cache : true,
		type : "POST",
		url : "/rzblog/blogcomment/addCommentLike",
		data : {
			"blogCommentId" : id,
		},
		async : false,
		error : function(request) {
			return;
		},
		success : function(data) {
			if (data.code == 0) {
				// 更改点赞量
				likeNum = parseInt($('#comment-like-' + id).html().trim()) + 1;
				$('#comment-like-' + id).html(' ' + likeNum);
			}
		}
	});
}

(function($) {
	function crateCommentInfo(obj, flag) {
		if (typeof (obj.replyTime) == "undefined" || obj.replyTime == "") {
			obj.replyTime = getNowDateFormat();
		}

		var el = "<div class='comment-info'><div class='headerimg'><img class='img' src='"
				+ obj.replyHeadImg
				+ "'></div><div class='comment-right'><div class='commenterinfo'><input value='"
				+ obj.blogCommentId
				+ "' type='hidden'></input><a class='comment-nickname' href='javascript:void(0)'>"
				+ obj.replyName
				+ "</a><span class='commenttime'><i class='fa fa-calendar-o'></i> "
				+ obj.replyTime
				+ "</span></div>"
				+ "<div class='comment-content-header'>";

		if (typeof (obj.address) != "undefined" && obj.browse != "") {
			el = el + "<span><i class='glyphicon glyphicon-map-marker'></i>"
					+ obj.address + "</span>";
		}
		el = el + "</div><p class='content'>" + obj.replyContent
				+ "</p><div class='comment-content-footer'><div class='row'>";

		if (flag == 0) {
			el = el
					+ "<div class='replydiv1'><span class='reply-btn'>回复</span><span class='remove-reply-btn' style='display: none'>取消回复</span><span class='comment-like fa fa-thumbs-o-up' id='comment-like-"
					+ obj.blogCommentId + "'> "
					+ (obj.likeNum == null ? '0' : obj.likeNum)
					+ "</span></div></div></div><div class='reply-list'>";
		} else if (flag == 1) {
			el = el
					+ "<div class='replydiv1'><span class='reply-btn-2'>正在审核</span></div></div></div><div class='reply-list'>";
		} else if (flag == 2) {
			el = el
					+ "<div class='replydiv1'><span class='reply-btn-3'>未通过审核</span></div></div></div><div class='reply-list'>";
		}
		if (obj.replyBody != null && obj.replyBody != ""
				&& obj.replyBody.length > 0) {
			var arr = obj.replyBody;
			for (var j = 0; j < arr.length; j++) {
				var replyObj = arr[j];
				el = el + createReplyComment(replyObj, flag);
			}
		}
		el = el + "</div></div></div>";
		return el;
	}

	// 返回每个回复体内容
	function createReplyComment(reply, flag) {
		var html = "";
		if (flag == 0) {
			html = "<span class='reply-list-btn'>回复</span><span class='comment-like fa fa-thumbs-o-up' id='comment-like-"
					+ reply.blogCommentId
					+ "'> "
					+ (reply.likeNum == null ? '0' : reply.likeNum) + "</span>";
		} else if (flag == 1) {
			html = "<span class='reply-list-btn-2'>正在审核</span>";
		} else if (flag == 2) {
			html = "<span class='reply-list-btn-3'>未通过审核</span>";
		}
		var replyEl = "<div class='reply'><div><input value='"
				+ (reply.blogCommentId != null ? reply.blogCommentId : '')
				+ "' type='hidden'></input><a href='javascript:void(0)' class='replyname'>"
				+ reply.replyName
				+ "</a> : <a class='beReplyAName' href='javascript:void(0)'>@ "
				+ reply.beReplyName + "</a><div class='replyContentSpan'>"
				+ reply.replyContent + "</div></div>" + "<p><span>"
				+ reply.replyTime + "</span> " + html + "</p></div>";
		return replyEl;
	}
	function getNowDateFormat() {
		var nowDate = new Date();
		var year = nowDate.getFullYear();
		var month = filterNum(nowDate.getMonth() + 1);
		var day = filterNum(nowDate.getDate());
		var hours = filterNum(nowDate.getHours());
		var min = filterNum(nowDate.getMinutes());
		var seconds = filterNum(nowDate.getSeconds());
		return year + "-" + month + "-" + day + " " + hours + ":" + min + ":"
				+ seconds;
	}
	function filterNum(num) {
		if (num < 10) {
			return "0" + num;
		} else {
			return num;
		}
	}
	function replyClick(el) {
		// 回复时，所有其他的回复窗口都关闭
		$(".replybox").remove();
		$(".remove-reply-btn").css('display', 'none');
		// $(".reply-btn").css('display', 'block');
		// 线性显示
		$(".reply-btn").css('display', 'inline');
		$(".reply-list-btn").html("回复");
		// 当前回复窗口打开
		el.parent().find(".remove-reply-btn").css('display', 'inline');
		el.parent().find(".reply-btn").css('display', 'none');
		el
				.parent()
				.parent()
				.append(
						"<div class='replybox'><form class='reply-commentform' id='form-blogcolumn-add'><div class='commentformInput'><input id='reply-comnickname' class='comnickname' placeholder='昵称' type='text'></input><input id='reply-comqqnum' class='comqqnum' placeholder='QQ号' type='text'></input><input id='reply-comemail' class='comemail' placeholder='邮箱' type='text'></input></div><textarea cols='80' rows='50' placeholder='请输入评论内容......' class='mytextarea' ></textarea><div class='sendcomment' id='reply-comment'>评论</div></form></div>")
				.find("#reply-comment")
				.click(
						function() {
							var content = $(this).prev().val();
							var reply_comnickname = $('#reply-comnickname')
									.val();
							if (content != "" && reply_comnickname != '') {
								var parentEl = $(this).parent().parent()
										.parent().parent();
								var obj = new Object();
								// 获取评论名
								if (el.parent().parent().hasClass("reply")) {
									// 下级回复再回复
									console.log(1)
									obj.beReplyName = el.parent().parent()
											.find("a:first").text();
									obj.parentId = parentEl.parent().find(
											"input:first").val();
								} else {
									// 只是下级回复
									console.log(2)
									obj.beReplyName = parentEl.parent().find(
											".commenterinfo").find("a").text();
									obj.parentId = parentEl.parent().find(
											".commenterinfo").find("input")
											.val();
								}
								// 回复人
								obj.replyName = reply_comnickname;
								// 内容
								obj.replyContent = content;
								// 日期
								obj.replyTime = getNowDateFormat();
								// 头像
								obj.replyHeadImg = "/img/blog/default1.jpg";
								obj.replyQqNum = $('#reply-comqqnum').val();
								obj.replyEmail = $('#reply-comemail').val();
								obj.showId = blogcontent.showId;
								obj.title = blogcontent.title;
								// 刷新输出
								var replyString = '';
								// 验证参数
								var flag = checkCommentContent(obj);
								if (flag == 1) {
									$.modalMsg("请输入昵称和评论内容！", "warning");
									return;
								} else if (flag == 2) {
									$.modalMsg("评论内容不能超过200字！", "warning");
									return;
								} else if (flag == 3) {
									$.modalMsg("QQ号格式不正确！", "warning");
									return;
								} else if (flag == 4) {
									$.modalMsg("邮箱格式不正确！", "warning");
									return;
								} else if (flag == 5) {
									$.modalMsg("昵称不能超过10个字！", "warning");
									return;
								}
								// 先提交验证
								var msg = submitComment(obj);
								if (msg[0] != -1) {
									obj.blogCommentId = msg[1];
									replyString = createReplyComment(obj,
											msg[0]);
								}

								// 回复窗口删除
								$(".replybox").remove();
								parentEl.parent().find(".reply-list").append(
										replyString).find(
										".reply-list-btn:last").click(
										function() {
											// $.modalMsg("不能回复自己！", "warning");
											return;
										});
								// 恢复此人的回复
								el.parent().find(".remove-reply-btn").css(
										'display', 'none');
								el.parent().find(".reply-btn").css('display',
										'inline');
								$(".reply-list-btn").html("回复");

								// 监听新回复
								$(".reply-list-btn").click(
										function() {
											if ($(this).parent().parent().find(
													".replybox").length > 0) {
												$(".replybox").remove();
												// 当前回复窗口打开
												$(this).parent().find(
														".reply-list-btn")
														.html("回复");
											} else {
												$(".replybox").remove();
												replyClick($(this));
												// 当前回复窗口打开
												$(this).parent().find(
														".reply-list-btn")
														.html('取消回复');
											}
										})
								// 监听新点赞
								$(".comment-like")
										.click(
												function() {
													var id = this.id;
													if (id != null
															&& id
																	.indexOf('comment-like-') > -1) {
														id = id.substring(13);
														if (id != null
																&& id != '') {
															// 点赞请求
															addCommentLike(id);
														}
													}
												})
							} else {
								$.modalMsg("请输入昵称和评论内容！", "warning");
								return;
							}
						});
	}

	// function updateReplyClick(el) {
	// // 更改为取消回复
	// el.removeClass('remove-reply-btn');
	// el.addClass('reply-btn');
	// el.text('回复');
	// // $(".reply-btn").click(function() {
	// // replyClick($(this));
	// // });
	// }

	$.fn.addCommentList = function(options) {
		// 打开及执行
		var defaults = {
			data : [],
			add : ""
		}
		var option = $.extend(defaults, options);
		// 加载原有数据并渲染
		if (option.data.length > 0) {
			var dataList = option.data;
			var totalString = "";
			for (var i = 0; i < dataList.length; i++) {
				var obj = dataList[i];
				var objString = crateCommentInfo(obj, 0);
				totalString = totalString + objString;
			}
			$(this).append(totalString).find(".reply-btn").click(function() {
				if ($(this).parent().parent().find(".replybox").length > 0) {
					$(".replybox").remove();
				} else {
					$(".replybox").remove();
					replyClick($(this));
				}
			});
			$(".reply-list-btn").click(function() {
				if ($(this).parent().parent().find(".replybox").length > 0) {
					$(".replybox").remove();
					// 当前回复窗口打开
					$(this).parent().find(".reply-list-btn").html("回复");
				} else {
					$(".replybox").remove();
					replyClick($(this));
					// 当前回复窗口打开
					$(this).parent().find(".reply-list-btn").html('取消回复');
				}
			})
		}

		// 添加新数据，产生新评论
		if (option.add != "") {
			obj = option.add;
			obj.replyTime = getNowDateFormat();
			// 先提交验证
			var str = '';
			if (option.data.length < 1) {
				var msg = submitComment(obj);
				if (msg[0] != -1) {
					obj.blogCommentId = msg[1];
					str = crateCommentInfo(obj, msg[0]);
				}
			}

		}
		$(this).prepend(str).find(".reply-btn").click(function() {
			// 出现评论input
			replyClick($(this));
		});
		$(".remove-reply-btn").click(function() {
			// 清除回复
			$(".replybox").remove();
			$(".remove-reply-btn").css('display', 'none');
			$(".reply-btn").css('display', 'inline');
		});

		// 清除框内内容
		$('#commentcontent').val('');
		$('#comnickname').val('');

		// 监听点赞
		$(".comment-like").click(function() {
			var id = this.id;
			if (id != null && id.indexOf('comment-like-') > -1) {
				id = id.substring(13);
				if (id != null && id != '') {
					// 点赞请求
					addCommentLike(id);
				}
			}
		})
	}

})(jQuery);