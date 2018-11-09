var prefix = ctx + "blog/blogset"

$(function() {
});

// 一开始便加载
$(document).ready(function() {
	document.getElementById("radio1").removeAttribute("checked");
	document.getElementById("radio2").removeAttribute("checked");
	if ($('#basicsetArticleEditor').val() == 1) {
		// markdown
		document.getElementById("radio1").removeAttribute("checked");
		document.getElementById("radio2").setAttribute("checked", true);
		$(".i-checks").iCheck({
			checkboxClass : "icheckbox_square-green",
			radioClass : "iradio_square-green",
		})
	} else {
		// html
		document.getElementById("radio1").setAttribute("checked", true);
		document.getElementById("radio2").removeAttribute("checked");
		$(".i-checks").iCheck({
			checkboxClass : "icheckbox_square-green",
			radioClass : "iradio_square-green",
		})
	}

	// 初始化水印信息
	if (basicsetWaterMarkSetMsg != null && basicsetWaterMarkSetMsg != '') {
		var basicsetWaterMarkSetMsgArray = basicsetWaterMarkSetMsg.split(',');
		// 设置基础信息
		// 颜色
		// 由于引入了select2 必须这样改值才行
		var itemNameSelect = $("#markFont").select2();
		itemNameSelect.val(basicsetWaterMarkSetMsgArray[1]).trigger("change");
		$('#markColor').val(basicsetWaterMarkSetMsgArray[0]);
		// 字体
		// $('#markFont').val(basicsetWaterMarkSetMsgArray[1]);
		// 样式
		var itemNameSelect2 = $("#markStyle").select2();
		itemNameSelect2.val(basicsetWaterMarkSetMsgArray[2]).trigger("change");
		// $('#markStyle').val(basicsetWaterMarkSetMsgArray[2]);
		// 大小
		$('#markSize').val(basicsetWaterMarkSetMsgArray[3]);
		// 样式
		var itemNameSelect3 = $("#markPosition").select2();
		itemNameSelect3.val(basicsetWaterMarkSetMsgArray[4]).trigger("change");
	}

	// 初始化时间插件
	$('.form_datetime').datetimepicker({
		language : 'zh-CN',
		weekStart : 0, // 一周从哪一天开始
		todayBtn : 1, //
		autoclose : 1,
		todayHighlight : 1,
		startView : 2,
		forceParse : 0,
		showMeridian : 1,
		todayBtn : true,
		todayHighlight : true,
		keyboardNavigation : true
	});
	// 插件最小时间
	$(".form_datetime").datetimepicker("setStartDate", '1970-01-01 00:00:00');

	// 初始化显示参数
	// 解析json
	var blogsetFriendLinks = $('#blogsetFriendLinks').val();
	var obj = "{}";
	if (blogsetFriendLinks.replace(/(^s*)|(s*$)/g, "").length != 0) {
		obj = JSON.parse(blogsetFriendLinks);
		if (obj != null) {
			if (obj.length != 0) {
				// 遍历添加已存在的参数
				for ( var index in obj) {
					key = obj[index].key;
					value = obj[index].value;
					// 写到网页中
					dynamicAddBlogsetFriendLinks(key, value)
				}
			}
		}
	}
	var blogsetSidebarOtherMessage = $('#blogsetSidebarOtherMessage').val();
	var obj2 = "{}";
	if (blogsetSidebarOtherMessage.replace(/(^s*)|(s*$)/g, "").length != 0) {
		obj2 = JSON.parse(blogsetSidebarOtherMessage);
		if (obj2 != null) {
			if (obj2.length != 0) {
				// 遍历添加已存在的参数
				for ( var index in obj2) {
					key2 = obj2[index].key;
					value2 = obj2[index].value;
					// 写到网页中
					dynamicAddBlogsetSidebarOtherMessage(key2, value2)
				}
			}
		}
	}
})

// 动态添加
function dynamicAddBlogsetFriendLinks(key, value) {
	html = '<div class="input-group inputParams">'
			+ '<label class="input-group-addon">显示</label>'
			+ '<input type="text" class="form-control" id="keyInput" value="'
			+ key
			+ '">'
			+ '<label class="input-group-addon">URL</label>'
			+ '<input type="text" class="form-control" id="valueInput" value="'
			+ value
			+ '">'
			+ '<span class="input-group-btn">'
			+ '<button class="btn btn-info" type="button" data-toggle="tooltip" title="删除" id="delBlogsetFriendLinks"><span class="glyphicon glyphicon-minus"></span></button>'
			+ '</span>' + '</div>';
	// 添加到增加参数按钮之前
	document.getElementById('addBlogsetFriendLinks2').insertAdjacentHTML(
			'beforebegin', html)
}

// 动态添加
function dynamicAddBlogsetSidebarOtherMessage(key, value) {
	html = '<div class="input-group inputParams2">'
			+ '<label class="input-group-addon">标题</label>'
			+ '<input type="text" class="form-control" id="keyInput2" value="'
			+ key
			+ '">'
			+ '<label class="input-group-addon">内容</label>'
			+ '<input type="text" class="form-control" id="valueInput2" value="'
			+ value
			+ '">'
			+ '<span class="input-group-btn">'
			+ '<button class="btn btn-info" type="button" data-toggle="tooltip" title="删除" id="delBlogsetSidebarOtherMessage"><span class="glyphicon glyphicon-minus"></span></button>'
			+ '</span>' + '</div>';
	// 添加到增加参数按钮之前
	document.getElementById('addBlogsetSidebarOtherMessage2')
			.insertAdjacentHTML('beforebegin', html)
}

// 绑定参数添加
function addBlogsetFriendLinks(obj) {
	html = '<div class="input-group inputParams">'
			+ '<label class="input-group-addon">显示</label>'
			+ '<input type="text" class="form-control" id="keyInput">'
			+ '<label class="input-group-addon">URL</label>'
			+ '<input type="text" class="form-control" id="valueInput">'
			+ '<span class="input-group-btn">'
			+ '<button class="btn btn-info" type="button" data-toggle="tooltip" title="删除" id="delBlogsetFriendLinks"><span class="glyphicon glyphicon-minus"></span></button>'
			+ '</span>' + '</div>'
	obj.insertAdjacentHTML('beforebegin', html)
}
// 绑定参数添加
function addBlogsetSidebarOtherMessage(obj) {
	html = '<div class="input-group inputParams2">'
			+ '<label class="input-group-addon">标题</label>'
			+ '<input type="text" class="form-control" id="keyInput2">'
			+ '<label class="input-group-addon">内容</label>'
			+ '<input type="text" class="form-control" id="valueInput2">'
			+ '<span class="input-group-btn">'
			+ '<button class="btn btn-info" type="button" data-toggle="tooltip" title="删除" id="delBlogsetSidebarOtherMessage"><span class="glyphicon glyphicon-minus"></span></button>'
			+ '</span>' + '</div>'
	obj.insertAdjacentHTML('beforebegin', html)
}

// 绑定删除
$(document).on('click', '#delBlogsetFriendLinks', function() {
	var el = this.parentNode.parentNode
	$.modalConfirm("确定要删除此友情链接？", function(e) {
		if (e) {
			el.parentNode.removeChild(el)
		}
		// 关闭弹窗
		layer.closeAll('dialog');
	})
})
// 绑定删除
$(document).on('click', '#delBlogsetSidebarOtherMessage', function() {
	var el = this.parentNode.parentNode
	$.modalConfirm("确定要删除此侧边栏信息？", function(e) {
		if (e) {
			el.parentNode.removeChild(el)
		}
		// 关闭弹窗
		layer.closeAll('dialog');
	})
})

// 设置网站基础信息
$("#form-blogset-basicset").validate({
	rules : {
		basicsetTitle : {
			required : true,
			maxlength : 30
		},
		basicsetDes : {
			maxlength : 100
		},
		basicsetCopyrightNoticeInfo : {
			maxlength : 100
		},
		basicsetRemark : {
			maxlength : 100
		},
		basicsetWaterMarkMsg : {
			required : true,
			maxlength : 100
		},
		markColor : {
			required : true
		},
	},
	submitHandler : function(form) {
		saveBasicset();
	}
});

function saveBasicset() {
	var blogSetId = $('#blogSetId').val();
	var basicsetTitle = $('#basicsetTitle').val();
	var basicsetDes = $('#basicsetDes').val();
	var basicsetSkin = $('#basicsetSkin').val();
	var basicsetWebsiteOnlinetime = $('#basicsetWebsiteOnlinetime').val();
	var basicsetArticleEditor = $("#radio1").is(':checked') == true ? 0 : 1;
	var basicsetGlobalAllowComment = $("#basicsetGlobalAllowComment").is(
			':checked') == true ? '0' : '1';
	var basicsetGlobalShowComment = $("#basicsetGlobalShowComment").is(
			':checked') == true ? '0' : '1';
	var basicsetGlobalAllowReprint = $("#basicsetGlobalAllowReprint").is(
			':checked') == true ? '0' : '1';
	var basicsetCommentNotice = $("#basicsetCommentNotice").is(':checked') == true ? '0'
			: '1';
	var basicsetOpenAppreciate = $("#basicsetOpenAppreciate").is(':checked') == true ? '0'
			: '1';
	var basicsetOpenBlogDownload = $("#basicsetOpenBlogDownload")
			.is(':checked') == true ? '0' : '1';
	var basicsetOpenNotice = $("#basicsetOpenNotice").is(':checked') == true ? '0'
			: '1';
	var basicsetAutoLoading = $("#basicsetAutoLoading").is(':checked') == true ? '0'
			: '1';
	var basicsetShowArticleSource = $("#basicsetShowArticleSource").is(
			':checked') == true ? '0' : '1';
	var basicsetCommentAutoReview = $("#basicsetCommentAutoReview").is(
			':checked') == true ? '0' : '1';
	var basicsetAddWaterMark = $("#basicsetAddWaterMark").is(':checked') == true ? '0'
			: '1';
	var basicsetWaterMarkMsg = $('#basicsetWaterMarkMsg').val();
	var basicsetWaterMarkSetMsg = $('#markColor').val() + ','
			+ $('#markFont').val() + ',' + $('#markStyle').val() + ','
			+ $('#markSize').val() + ',' + $('#markPosition').val();

	var basicsetAddCopyrightNotice = $("#basicsetAddCopyrightNotice").is(
			':checked') == true ? '0' : '1';
	var basicsetCopyrightNoticeInfo = $('#basicsetCopyrightNoticeInfo').val();
	var basicsetRemark = $('#basicsetRemark').val();

	$.ajax({
		cache : true,
		type : "POST",
		url : prefix + "/saveBasicset",
		data : {
			"blogSetId" : blogSetId,
			"basicsetTitle" : basicsetTitle,
			"basicsetWebsiteIco" : fileBase64_8,
			"basicsetDes" : basicsetDes,
			"basicsetSkin" : basicsetSkin,
			"basicsetArticleEditor" : basicsetArticleEditor,
			"basicsetWebsiteOnlinetime" : basicsetWebsiteOnlinetime,
			"basicsetGlobalAllowComment" : basicsetGlobalAllowComment,
			"basicsetGlobalShowComment" : basicsetGlobalShowComment,
			"basicsetGlobalAllowReprint" : basicsetGlobalAllowReprint,
			"basicsetCommentNotice" : basicsetCommentNotice,
			"basicsetOpenAppreciate" : basicsetOpenAppreciate,
			"basicsetOpenBlogDownload" : basicsetOpenBlogDownload,
			"basicsetOpenNotice" : basicsetOpenNotice,
			"basicsetAutoLoading" : basicsetAutoLoading,
			"basicsetShowArticleSource" : basicsetShowArticleSource,
			"basicsetCommentAutoReview" : basicsetCommentAutoReview,
			"basicsetAddWaterMark" : basicsetAddWaterMark,
			"basicsetWaterMarkMsg" : basicsetWaterMarkMsg,
			"basicsetWaterMarkSetMsg" : basicsetWaterMarkSetMsg,
			"basicsetAddCopyrightNotice" : basicsetAddCopyrightNotice,
			"basicsetCopyrightNoticeInfo" : basicsetCopyrightNoticeInfo,
			"basicsetRemark" : basicsetRemark,
		},
		async : false,
		error : function(request) {
			$.modalAlert("系统错误", modal_status.FAIL);
		},
		success : function(data) {
			if (data.code == 0) {
				layer.msg("设置成功,正在刷新数据请稍后……", {
					icon : 1,
					time : 500,
					shade : [ 0.1, '#fff' ]
				}, function() {
					// refreshTab();
					// $('.tabReload').click();
				});
			} else {
				$.modalAlert(data.msg, modal_status.FAIL);
			}

		}
	});
}

// 设置博主信息
$("#form-blogset-bloggerset").validate({
	rules : {
		bloggersetBloggerName : {
			required : true,
			maxlength : 30
		},
		bloggersetBloggerDes : {
			required : true,
			maxlength : 100
		},
		bloggersetBloggerSinaWeibo : {
			maxlength : 100
		},
		bloggersetBloggerQqNumber : {
			digits : true,
			maxlength : 15
		},
		bloggersetBloggerQqGroupNumber : {
			digits : true,
			maxlength : 15
		},
		bloggersetBloggerWechatNumber : {
			maxlength : 50
		},
		bloggersetBloggerWechatGroupName : {
			maxlength : 80
		},
		bloggersetBloggerEmail : {
			maxlength : 100
		},
		bloggersetBloggerCsdn : {
			maxlength : 200
		},
		bloggersetBloggerGitee : {
			maxlength : 200
		},
		bloggersetBloggerGithub : {
			maxlength : 200
		},
		bloggersetRemark : {
			maxlength : 100
		},

	},
	submitHandler : function(form) {
		saveBloggerset();
	}
});

function saveBloggerset() {
	var blogSetId = $('#blogSetId').val();
	var bloggersetBloggerName = $('#bloggersetBloggerName').val();
	var bloggersetBloggerDes = $('#bloggersetBloggerDes').val();
	var bloggersetBloggerSinaWeibo = $('#bloggersetBloggerSinaWeibo').val();
	var bloggersetBloggerQqNumber = $('#bloggersetBloggerQqNumber').val();

	var bloggersetBloggerQqGroupNumber = $('#bloggersetBloggerQqGroupNumber')
			.val();

	var bloggersetBloggerWechatNumber = $('#bloggersetBloggerWechatNumber')
			.val();

	var bloggersetBloggerWechatGroupName = $(
			'#bloggersetBloggerWechatGroupName').val();
	var bloggersetBloggerEmail = $('#bloggersetBloggerEmail').val();
	var bloggersetBloggerCsdn = $('#bloggersetBloggerCsdn').val();
	var bloggersetBloggerGitee = $('#bloggersetBloggerGitee').val();
	var bloggersetBloggerGithub = $('#bloggersetBloggerGithub').val();

	var bloggersetRemark = $('#bloggersetRemark').val();

	$
			.ajax({
				cache : true,
				type : "POST",
				url : prefix + "/saveBloggerset",
				data : {
					"blogSetId" : blogSetId,
					"bloggersetBloggerName" : bloggersetBloggerName,
					"bloggersetBloggerDes" : bloggersetBloggerDes,
					"bloggersetBloggerProfile" : fileBase64_7,
					"bloggersetBloggerSinaWeibo" : bloggersetBloggerSinaWeibo,
					"bloggersetBloggerQqNumber" : bloggersetBloggerQqNumber,
					"bloggersetBloggerQqOrcode" : fileBase64_1,
					"bloggersetBloggerQqGroupNumber" : bloggersetBloggerQqGroupNumber,
					"bloggersetBloggerQqGroupOrcode" : fileBase64_2,
					"bloggersetBloggerWechatNumber" : bloggersetBloggerWechatNumber,
					"bloggersetBloggerWechatOrcode" : fileBase64_3,
					"bloggersetBloggerWechatGroupName" : bloggersetBloggerWechatGroupName,
					"bloggersetBloggerWechatGroupOrcode" : fileBase64_4,
					"bloggersetBloggerEmail" : bloggersetBloggerEmail,
					"bloggersetBloggerCsdn" : bloggersetBloggerCsdn,
					"bloggersetBloggerGitee" : bloggersetBloggerGitee,
					"bloggersetBloggerGithub" : bloggersetBloggerGithub,
					"bloggersetBloggerRewardAlipayCollectionCode" : fileBase64_5,
					"bloggersetBloggerRewardWechatCollectionCode" : fileBase64_6,
					"bloggersetRemark" : bloggersetRemark
				},
				async : false,
				error : function(request) {
					$.modalAlert("系统错误", modal_status.FAIL);
				},
				success : function(data) {
					if (data.code == 0) {
						layer.msg("设置成功,正在刷新数据请稍后……", {
							icon : 1,
							time : 500,
							shade : [ 0.1, '#fff' ]
						}, function() {
							// refreshTab();
						});
					} else {
						$.modalAlert(data.msg, modal_status.FAIL);
					}

				}
			});
}

// 设置博客设置信息
$("#form-blogset-blogset").validate({
	rules : {
		blogsetRemark : {
			maxlength : 100
		},

	},
	submitHandler : function(form) {
		saveBlogset();
	}
});

function saveBlogset() {
	var blogSetId = $('#blogSetId').val();
	var blogsetPerpageShowNum = $('#blogsetPerpageShowNum').val();
	var blogsetLatestShowNum = $('#blogsetLatestShowNum').val();
	var blogsetScrollRecommendedShowNum = $('#blogsetScrollRecommendedShowNum')
			.val();
	var blogsetRecommendedShowNum = $('#blogsetRecommendedShowNum').val();
	var blogsetRankingShowNum = $('#blogsetRankingShowNum').val();
	var blogsetSpecialRecdShowNum = $('#blogsetSpecialRecdShowNum').val();
	var blogsetNavBarShowNum = $('#blogsetNavBarShowNum').val();
	var blogsetNoPicUseDefault = $("#allow_comment").is(':checked') == true ? '0'
			: '1';
	var blogsetNoCoverpicUseContentpic = $("#ping").is(':checked') == true ? '0'
			: '1';
	var blogsetRemark = $('#blogsetRemark').val();

	// 以下开始参数获取
	var blogsetFriendLinks2 = '';
	// 拼接默认参数为json
	var key = '';
	var value = '';
	var array = new Array();
	var num = 0;
	var success = true;
	var map = new Map();
	// 特殊字符限制限制
	var regEn = /[`~!@#$%^&*()+<>?:"{},.\/;'[\]\s]/im, regCn = /[·！#￥（——）：；“”‘、，|《。》？、【】[\]]/im;
	var regUrl = /(http|ftp|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&:/~\+#]*[\w\-\@?^=%&/~\+#])?/;
	// 遍历所有的参数
	$(".input-group.inputParams").each(function() {
		map = new Map();
		key = $(this).find("#keyInput").val();
		value = $(this).find("#valueInput").val();
		if (value == '' || key == '') {
			$.modalAlert('显示和URL不能为空', modal_status.FAIL);
			success = false;
			return;
		}
		// 验证
		// 长度限制，类型等
		// 长度限制
		if (key.length > 100 || value.length > 200) {
			$.modalAlert('超过规定长度', modal_status.FAIL);
			success = false;
			return;
		}
		// 特殊字符限制限制
		if (regEn.test(key) || regCn.test(key)) {
			$.modalAlert('不能携带特殊字符', modal_status.FAIL);
			success = false;
			return;
		}

		// 验证url
		if (!regUrl.test(value)) {
			$.modalAlert('网址格式不正确', modal_status.FAIL);
			success = false;
			return;
		}
		map.put('key', key);
		map.put('value', value);
		array[num] = map.data;
		num++;
	});

	if (!success) {
		return;
	}

	// 设置参数
	if (array.length > 0) {
		blogsetFriendLinks2 = JSON.stringify(array);
	}

	// 以下开始参数获取
	var blogsetSidebarOtherMessage2 = '';
	// 拼接默认参数为json
	var key2 = '';
	var value2 = '';
	var array2 = new Array();
	var num2 = 0;
	var success2 = true;
	var map2 = new Map();
	var paramMap = new Map();
	// 特殊字符限制限制
	var regEn2 = /[`~!@#$%^&*()+<>?:"{},.\/;'[\]\s]/im;
	// 遍历所有的参数
	$(".input-group.inputParams2").each(function() {
		map2 = new Map();
		key2 = $(this).find("#keyInput2").val();
		value2 = $(this).find("#valueInput2").val();
		if (value2 == '' || key2 == '') {
			$.modalAlert('标题和内容不能为空', modal_status.FAIL);
			success2 = false;
			return;
		}
		// 验证
		// 长度限制，类型等
		// 长度限制
		if (key2.length > 10 || value2.length > 100) {
			$.modalAlert('超过规定长度', modal_status.FAIL);
			success2 = false;
			return;
		}
		// 特殊字符限制限制
		if (regEn2.test(key2) || regEn2.test(value2)) {
			$.modalAlert('不能携带特殊字符', modal_status.FAIL);
			success2 = false;
			return;
		}

		map2.put('key', key2);
		map2.put('value', value2);
		array2[num2] = map2.data;
		paramMap.put(key2, value2);
		num2++;
	});

	if (!success2) {
		return;
	}

	// 设置参数
	if (array2.length > 0) {
		blogsetSidebarOtherMessage2 = JSON.stringify(array2);
	}

	// 校验标题不能重复
	if (array2.length != paramMap.keys.length) {
		$.modalAlert('标题不能重复！', modal_status.FAIL);
		return;
	}
	// 默认图片
	var blogsetDefaultPic = '';
	if (arrayImg != null && arrayImg != '' && arrayImg.length > 0) {
		if (arrayImg.length > 4) {
			$.modalAlert("默认图片最多4张！", modal_status.FAIL);
			return;
		}
		for (var i = 0; i < arrayImg.length; i++) {
			if (i == 0) {
				blogsetDefaultPic = arrayImg[i];
			} else {
				blogsetDefaultPic += ("," + arrayImg[i]);
			}
		}
	}
	$
			.ajax({
				cache : true,
				type : "POST",
				url : prefix + "/saveBlogset",
				data : {
					"blogSetId" : blogSetId,
					"blogsetPerpageShowNum" : blogsetPerpageShowNum,
					"blogsetLatestShowNum" : blogsetLatestShowNum,
					"blogsetScrollRecommendedShowNum" : blogsetScrollRecommendedShowNum,
					"blogsetRecommendedShowNum" : blogsetRecommendedShowNum,
					"blogsetRankingShowNum" : blogsetRankingShowNum,
					"blogsetSpecialRecdShowNum" : blogsetSpecialRecdShowNum,
					"blogsetNavBarShowNum" : blogsetNavBarShowNum,
					"blogsetNoPicUseDefault" : blogsetNoPicUseDefault,
					"blogsetNoCoverpicUseContentpic" : blogsetNoCoverpicUseContentpic,
					"blogsetDefaultPic" : blogsetDefaultPic,
					"blogsetFriendLinks" : blogsetFriendLinks2,
					"blogsetSidebarOtherMessage" : blogsetSidebarOtherMessage2,
					"blogsetRemark" : blogsetRemark
				},
				async : false,
				error : function(request) {
					$.modalAlert("系统错误", modal_status.FAIL);
				},
				success : function(data) {
					if (data.code == 0) {
						layer.msg("设置成功,正在刷新数据请稍后……", {
							icon : 1,
							time : 500,
							shade : [ 0.1, '#fff' ]
						}, function() {
							// refreshTab();
						});
					} else {
						$.modalAlert(data.msg, modal_status.FAIL);
					}

				}
			});
}

// 设置样式设置
$("#form-blogset-styleset").validate({
	rules : {
		stylesetColor : {
			required : true
		},
		stylesetBackColor : {
			required : true
		},
		stylesetRemark : {
			maxlength : 100
		}

	},
	submitHandler : function(form) {
		saveStyleset();
	}
});

function saveStyleset() {
	var blogSetId = $('#blogSetId').val();
	var stylesetColor = $('#stylesetColor').val();
	var stylesetBackColor = $('#stylesetBackColor').val();
	var stylesetSmoothStyle = $('#stylesetSmoothStyle').val();
	var stylesetShowRightSiderbar = $("#allow_introduction").is(':checked') == true ? 0
			: 1;
	var stylesetRightSiderbarContent = $
			.getSelects("stylesetRightSiderbarContent");
	var stylesetShowLeftSiderbar = $("#private_article").is(':checked') == true ? 0
			: 1;
	var stylesetLeftSiderbarContent = $
			.getSelects("stylesetLeftSiderbarContent");
	var stylesetRemark = $('#stylesetRemark').val();
	$.ajax({
		cache : true,
		type : "POST",
		url : prefix + "/saveStyleset",
		data : {
			"blogSetId" : blogSetId,
			"stylesetColor" : stylesetColor,
			"stylesetBackColor" : stylesetBackColor,
			"stylesetSmoothStyle" : stylesetSmoothStyle,
			"stylesetShowRightSiderbar" : stylesetShowRightSiderbar,
			"stylesetRightSiderbarContent" : stylesetRightSiderbarContent,
			"stylesetShowLeftSiderbar" : stylesetShowLeftSiderbar,
			"stylesetLeftSiderbarContent" : stylesetLeftSiderbarContent,
			"stylesetRemark" : stylesetRemark
		},
		async : false,
		error : function(request) {
			$.modalAlert("系统错误", modal_status.FAIL);
		},
		success : function(data) {
			if (data.code == 0) {
				layer.msg("设置成功,正在刷新数据请稍后……", {
					icon : 1,
					time : 500,
					shade : [ 0.1, '#fff' ]
				}, function() {
					// refreshTab();
				});
			} else {
				$.modalAlert(data.msg, modal_status.FAIL);
			}

		}
	});
}