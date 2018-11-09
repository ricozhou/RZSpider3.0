var prefix = ctx + "blog/blogcontent/blogmove";

$().ready(
		function() {
			// 启动websocket
			startWebsocket('superadmin');
			// 初始化专栏
			$.ajax({
				cache : true,
				type : "GET",
				url : ctx + "blog/blogcolumn/selectBlogCcolumnList",
				async : false,
				error : function(request) {
					$.modalAlert("系统错误", modal_status.FAIL);
				},
				success : function(data) {
					// 动态显示专栏类型
					var newOption;
					$("#moveColumn").find("option").remove();
					newOption = document.createElement("option");
					newOption.text = '默认';
					newOption.value = '默认';
					document.getElementById("moveColumn").add(newOption);
					if (data != null) {
						for (var i = 0; i < data.length; i++) {
							newOption = document.createElement("option");
							newOption.text = data[i].blogColumnName;
							newOption.value = data[i].blogColumnName;
							document.getElementById("moveColumn")
									.add(newOption);
						}
					} else {
						$.modalAlert(data.msg, modal_status.FAIL);
					}

				}
			});

			// 初始化全局是否打水印
			if (blogset.basicsetAddWaterMark == 1) {
				// 全局不允许打水印
				document.getElementById("allow_download").setAttribute(
						"disabled", true);
			}

		});

// 绑定监听事件
$(function() {
	$('#moveMode')
			.bind(
					'change',
					function() {
						var moveMode = $(this).val();
						if (moveMode == 0) {
							$('#moveWebsitel').css('display', 'block');
							$('#moveWebsited').css('display', 'block');
							$('#idurll').css('display', 'block');
							$('#moveWebsiteUrl').css('display', 'block');
							$('#moveWebsiteInput').css('display', 'block');
							$('#moveNuml').css('display', 'block');
							$('#moveNumd').css('display', 'block');
							$('#fileuploadd').css('display', 'none');
							$('#idurll')
									.html(
											'<em class="gg-star" style="color: red; font-style: normal;">*&nbsp;</em>用户ID：');
							$('#moveWebsitel').html('网站：');
							$('#moveWebsiteInput').removeClass('col-sm-5');
							$('#moveWebsiteInput').addClass('col-sm-2');
							$('#moveWebsiteUrl').html('https://blog.csdn.net/');

							document.getElementById("moveNum").removeAttribute(
									"disabled");
							// 设置使用原时间
							document.getElementById("allow_comment")
									.setAttribute("checked", true);
							document.getElementById("allow_comment")
									.removeAttribute("disabled");
							// 保存图片按钮可变
							document.getElementById("allow_introduction")
									.removeAttribute("disabled");
							$('#moveAuthorl').css('display', 'none');
							$('#moveAuthord').css('display', 'none');

						} else if (moveMode == 1) {
							$('#moveWebsitel').css('display', 'block');
							$('#moveWebsited').css('display', 'block');
							$('#idurll').css('display', 'block');
							$('#moveWebsiteUrl').css('display', 'block');
							$('#moveWebsiteInput').css('display', 'block');
							$('#moveNuml').css('display', 'block');
							$('#moveNumd').css('display', 'block');
							$('#moveNum').val(1);
							$('#fileuploadd').css('display', 'none');
							$('#moveWebsiteUrl').html('');
							$('#idurll')
									.html(
											'<em class="gg-star" style="color: red; font-style: normal;">*&nbsp;</em>URL：');
							$('#moveWebsitel').html('网站：');
							$('#moveWebsiteUrl').css('display', 'none');
							$('#moveWebsiteInput').removeClass('col-sm-2');
							$('#moveWebsiteInput').addClass('col-sm-5');

							document.getElementById("moveNum").setAttribute(
									"disabled", true);

							// 设置使用原时间
							document.getElementById("allow_comment")
									.setAttribute("checked", true);
							document.getElementById("allow_comment")
									.removeAttribute("disabled");
							// 保存图片按钮可变
							document.getElementById("allow_introduction")
									.removeAttribute("disabled");

							$('#moveAuthorl').css('display', 'none');
							$('#moveAuthord').css('display', 'none');
						} else if (moveMode == 2) {
							$('#idurll').css('display', 'none');
							$('#moveWebsited').css('display', 'none');
							$('#moveWebsiteUrl').css('display', 'none');
							$('#moveWebsiteInput').css('display', 'none');
							$('#moveNuml').css('display', 'none');
							$('#moveNumd').css('display', 'none');

							$('#moveWebsitel')
									.html(
											'<em class="gg-star" style="color: red; font-style: normal;">*&nbsp;</em>上传：');
							$('#fileuploadd').css('display', 'block');

							// 设置不使用原时间
							document.getElementById("allow_comment")
									.setAttribute("checked", false);
							document.getElementById("allow_comment")
									.setAttribute("disabled", true);

							// 保存图片按钮不可变
							document.getElementById("allow_introduction")
									.setAttribute("disabled", true);

							$('#moveAuthorl').css('display', 'block');
							$('#moveAuthord').css('display', 'block');
						} else if (moveMode == 9) {
							$('#fileuploadd').css('display', 'none');
						}
					});

	$('#moveWebsite').bind('change', function() {
		var moveWebsite = $(this).val();
		console.log(moveWebsite)
		if ($('#moveMode').val() == 0) {
			if (moveWebsite == 'CSDN') {
				$('#moveWebsiteInput').removeClass('col-sm-5');
				$('#moveWebsiteInput').addClass('col-sm-2');
				$('#moveWebsiteUrl').css('display', 'block');
				$('#moveWebsiteUrl').html('https://blog.csdn.net/');
			} else if (moveWebsite == '博客园') {
				$('#moveWebsiteInput').removeClass('col-sm-5');
				$('#moveWebsiteInput').addClass('col-sm-2');
				$('#moveWebsiteUrl').css('display', 'block');
				$('#moveWebsiteUrl').html('http://www.cnblogs.com/');
			} else if (moveWebsite == '今日头条') {
				$('#moveWebsiteInput').removeClass('col-sm-5');
				$('#moveWebsiteInput').addClass('col-sm-2');
				$('#moveWebsiteUrl').css('display', 'block');
				$('#moveWebsiteUrl').html('https://www.toutiao.com/c/user/');
			} else if (moveWebsite == '简书') {
				$('#moveWebsiteInput').removeClass('col-sm-5');
				$('#moveWebsiteInput').addClass('col-sm-2');
				$('#moveWebsiteUrl').css('display', 'block');
				$('#moveWebsiteUrl').html('https://www.jianshu.com/u/');
			} else if (moveWebsite == '开源中国') {
				$('#moveWebsiteInput').removeClass('col-sm-5');
				$('#moveWebsiteInput').addClass('col-sm-2');
				$('#moveWebsiteUrl').css('display', 'block');
				$('#moveWebsiteUrl').html('https://my.oschina.net/u/');
			}
		} else if ($('#moveMode').val() == 1) {

		}
	});
});

// 表单验证
$("#form-blogmove-add").validate({
	rules : {
		moveUserId : {
			required : true,
			maxlength : 100
		},
		moveAuthor : {
			required : true,
			maxlength : 100
		},
		moveNum : {
			required : true,
			digits : true,
			max : 999,
			min : 1
		}
	},
	blogmovemessages : {},
	submitHandler : function(form) {
		moveRun();
	}
})

var blogMoveId;
function moveRun() {

	var moveWebsiteId = $("#moveWebsite").val();

	var moveUserId = $("#moveUserId").val();
	var moveWebsiteUrl = $("#moveWebsiteUrl").html();

	var moveNum = $("#moveNum").val();
	var moveArticleEditor = $("#moveArticleEditor").val();
	var moveArticleType = $("#moveArticleType").val();
	var moveColumn = $("#moveColumn").val();
	var moveAuthor;

	var moveBlogStatus = $("#ping").is(':checked') == true ? 1 : 0;
	var moveAddWaterMark = $("#allow_download").is(':checked') == true ? 0 : 1;
	var moveUseOriginalTime = $("#allow_comment").is(':checked') == true ? 0
			: 1;
	var moveSaveImg = $("#allow_introduction").is(':checked') == true ? 0 : 1;
	var moveRemoveRepeat = $("#private_article").is(':checked') == true ? 0 : 1;
	var moveMode = $('#moveMode').val();
	if (moveMode == 1) {
		moveUserId = '';
		moveNum = 1;
		moveWebsiteUrl = $("#moveUserId").val();
		moveAuthor = '';
	} else if (moveMode == 2) {
		if (fileNames == null || fileNames == '') {
			$.modalAlert('请先上传文件', modal_status.FAIL);
			return;
		}
		moveWebsiteId = '';
		moveUserId = '';
		moveWebsiteUrl = '';
		moveNum = fileNames.split(',').length;
		moveAuthor = $("#moveAuthor").val();
	} else if (moveMode == 9) {

	}

	// 展示详情
	$("#resultModal").modal('show');
	$("#showResultModal").show();

	$.ajax({
		cache : true,
		type : "POST",
		url : prefix + "/blogMoveRun",
		data : {
			"moveWebsiteId" : moveWebsiteId,
			"moveMode" : moveMode,
			"moveWebsiteUrl" : moveWebsiteUrl,
			"moveFileNames" : fileNames,
			"moveFileONames" : fileONames,
			"moveAddWaterMark" : moveAddWaterMark,
			"moveUserId" : moveUserId,
			"moveNum" : moveNum,
			"blogAuthor" : moveAuthor,
			"moveArticleEditor" : moveArticleEditor,
			"moveArticleType" : moveArticleType,
			"moveColumn" : moveColumn,
			"moveBlogStatus" : moveBlogStatus,
			"moveUseOriginalTime" : moveUseOriginalTime,
			"moveSaveImg" : moveSaveImg,
			"moveRemoveRepeat" : moveRemoveRepeat
		},
		async : false,
		error : function(request) {
			$.modalAlert("系统错误", modal_status.FAIL);
		},
		beforeSend : function() {
			// 禁用爬虫按钮
			document.getElementById("blogMoveRun").setAttribute("disabled",
					true);
			document.getElementById("blogMoveLog1").setAttribute("disabled",
					true);
			document.getElementById("blogMoveStop1")
					.removeAttribute("disabled");
		},
		success : function(data) {
			console.log(data.msg);
			blogMoveId = data.msg;
		}
	});

	$("#blogmovemessage").html("<p> 博客搬家爬虫正在准备...</p>");
}
/* 日志 */
function blogMoveLog() {
	var url = prefix + '/blogmovelog';
	layer_showAuto2("博客搬家日志", url);
}
// 中止爬取
function blogMoveStop() {
	$.ajax({
		cache : true,
		type : "POST",
		url : prefix + "/blogMoveStop",
		data : {
			"blogMoveId" : blogMoveId
		},
		async : false,
		error : function(request) {
			$.modalAlert("系统错误", modal_status.FAIL);
		},
		beforeSend : function() {
			// 禁用爬虫按钮
			document.getElementById("blogMoveRun").setAttribute("disabled",
					true);
			document.getElementById("blogMoveLog1").setAttribute("disabled",
					true);
			document.getElementById("blogMoveStop1").setAttribute("disabled",
					true);
		},
		success : function(data) {
			if (data.code == 0) {
				layer.msg("中止成功,正在刷新数据请稍后……", {
					icon : 1,
					time : 500,
					shade : [ 0.1, '#fff' ]
				});

				document.getElementById("blogMoveRun").removeAttribute(
						"disabled");
				document.getElementById("blogMoveLog1").removeAttribute(
						"disabled");

				document.getElementById("blogMoveStop1").setAttribute(
						"disabled", true);
			} else {
				document.getElementById("blogMoveRun").setAttribute("disabled",
						true);
				document.getElementById("blogMoveLog1").removeAttribute(
						"disabled");
				document.getElementById("blogMoveStop1").removeAttribute(
						"disabled");
				$.modalAlert(data.msg, modal_status.FAIL);
			}

		}
	});
}