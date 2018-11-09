$("#form-loginSet-edit").validate({
	rules : {},
	submitHandler : function(form) {
		update();
	}
});

// 绑定监听事件
$(function() {
	// 初始化
	var loginbgType = $('#loginbgType').val();
	var loginbgName = $('#loginbgName2').val();
	if (loginbgType == 0) {
		// 默认或者链接
		// 显示区域
		controUrlOnline();
		document.getElementById("radio1").checked = true;
		// 添加链接显示
		$('#loginbgName').val(loginbgName);
	} else if (loginbgType == 1) {
		// 上传图片
		controImgOnline();
		document.getElementById("radio2").checked = true;
	} else if (loginbgType == 2) {
		// 特效
		// 显示特效区域
		controSpecialEffectsOnline();
		document.getElementById("radio3").checked = true;
		// 选中特效
		$('#specialEffects').val(loginbgName);
	}
	// 绑定监听
	// 此地方注意，复选框被包裹在label内，所以click事件绑定label的,不是很好，待改进
	$('#clickLabel1').click(function() {
		controUrlOnline();
	});
	$('#clickLabel2').click(function() {
		controImgOnline();
	});
	$('#clickLabel3').click(function() {
		controSpecialEffectsOnline();
	});
	// // 监听文本框预览随时成像
	// $('#loginbgName').bind('input propertychange', function() {
	// document.getElementById("showImg").src = $('#loginbgName').val();
	// });
});

// 在线编辑显示
function controUrlOnline() {
	$("#show1").css('display', 'block');
	$("#show2").css('display', 'none');
	$("#show3").css('display', 'none');
}

// 上传显示
function controImgOnline() {
	$("#show1").css('display', 'none');
	$("#show2").css('display', 'block');
	$("#show3").css('display', 'none');
}
// 特效显示
function controSpecialEffectsOnline() {
	$("#show1").css('display', 'none');
	$("#show2").css('display', 'none');
	$("#show3").css('display', 'block');
}

function update() {
	var loginbgType = $("#radio1").is(':checked') == true ? 0 : ($("#radio2")
			.is(':checked') == true ? 1 : 2);
	var loginbgName = '';
	var loginbgFileName;
	if (loginbgType == 0) {
		loginbgName = $("#loginbgName").val();
	} else if (loginbgType == 1) {
		// 文件上传
		if (imgFileName == null) {
			$.modalAlert('请先上传文件', modal_status.FAIL);
			return;
		}
		loginbgName = imgFileName;
		loginbgFileName = $('#loginbgFileName').val();
		console.log(loginbgFileName);
	} else if (loginbgType == 2) {
		loginbgName = $('#specialEffects').val();
	}
	var basesetId = $("#basesetId").val();
	$.ajax({
		cache : true,
		type : "POST",
		url : ctx + "tool/baseset/detailedit/loginSetsave",
		data : {
			"basesetId" : basesetId,
			"loginbgType" : loginbgType,
			"loginbgName" : loginbgName,
			"loginbgFileName" : loginbgFileName
		},
		async : false,
		error : function(request) {
			$.modalAlert("系统错误", modal_status.FAIL);
		},
		beforeSend : function() {
			layer.msg("正在保存,请稍后……", {
				icon : 1,
				time : 0,
				shade : [ 0.1, '#fff' ]
			});
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("编辑成功,正在刷新数据请稍后……", {
					icon : 1,
					time : 500,
					shade : [ 0.1, '#fff' ]
				}, function() {
					$.parentReload();
				});
				// 关闭提示窗
				layer.closeAll('dialog');
			} else {
				$.modalAlert(data.msg, modal_status.FAIL);
			}

		}
	});
}

// 预览
function previewLoginbg(obj) {
	var baseurl = ctx + "tool/baseset/detailedit/previewLoginbg/";
	var url;
	var loginbgType = $("#radio1").is(':checked') == true ? 0 : ($("#radio2")
			.is(':checked') == true ? 1 : 2);
	var loginbgName = '';
	if (loginbgType == 0) {
		loginbgName = $("#loginbgName").val();
		if ($.trim(loginbgName) == '') {
			loginbgName = "-1"
		}
		url = baseurl + URLencode(loginbgName) + "/" + loginbgType;
	} else if (loginbgType == 1) {
		var loginbgFileName = $('#loginbgFileName').val();
		// 文件上传
		if (loginbgFileName == null || loginbgFileName == '') {
			$.modalAlert('请先上传文件', modal_status.FAIL);
			return;
		}
		loginbgName = loginbgFileName;
		url = baseurl + loginbgName + "/" + loginbgType;
	} else if (loginbgType == 2) {
		loginbgName = $('#specialEffects').val();
		url = baseurl + loginbgName + "/" + loginbgType;
	}

	// 跳转新界面
	obj.setAttribute("href", url);
}
// 替换特殊字符
function URLencode(sStr) {
	if ($.trim(sStr) == '') {
		return sStr
	} else {
		return encodeURI(sStr.replace(/\+/g, '%2B').replace(/\"/g, '%22')
				.replace(/\'/g, '%27').replace(/\//g, '%2F').replace(/\:/g,
						'%3A').replace(/\?/g, '%3F').replace(/\=/g, '%3D')
				.replace(/\&/g, '%26').replace(/\(/g, '%28').replace(/\)/g,
						'%29').replace(/\@/g, '%40'));
	}
}