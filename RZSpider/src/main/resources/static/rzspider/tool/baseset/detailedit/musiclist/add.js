$("#form-musiclist-add").validate({
	rules : {
		url : {
			required : true,
			maxlength : 1000
		},
		title : {
			required : true,
			maxlength : 100
		}
	},
	submitHandler : function(form) {
		add();
	}
});

// 绑定监听事件
$(function() {
	// 初始化
	document.getElementById("radio1").checked = true;
	document.getElementById("27").checked = true;
	// 此地方注意，复选框被包裹在label内，所以click事件绑定label的,不是很好，待改进
	$('#clickLabel1').click(function() {
		controFileOnline();
	});
	$('#clickLabel2').click(function() {
		controFileUpload();
	});

	$('#27').click(function() {
		controImgOnline();
	});
	$('#28').click(function() {
		controImgUpload();
	});

});

function controFileOnline() {
	$("#show2").css('display', 'none');
	$("#url2").css('display', 'block');
}
function controFileUpload() {
	$("#url2").css('display', 'none');
	$("#show2").css('display', 'block');
}
function controImgOnline() {
	$("#pic2").css('display', 'block');
	$("#show3").css('display', 'none');
}
function controImgUpload() {
	$("#show3").css('display', 'block');
	$("#pic2").css('display', 'none');
}
// js选择
function selectFileOnline() {
	// 这种方式有效
	document.getElementById("radio2").checked = false;
	document.getElementById("radio1").checked = true;
	// 这种无效
	// document.getElementById("radio2").removeAttribute("checked");
	// document.getElementById("radio1").setAttribute("checked", true);
	$(".i-checks").iCheck({
		checkboxClass : "icheckbox_square-green",
		radioClass : "iradio_square-green",
	})
	controFileOnline();
}

// js选择
function selectImgOnline() {
	// 这种方式有效
	document.getElementById("28").checked = false;
	document.getElementById("27").checked = true;
	controImgOnline();
}

function add() {
	var basesetId = $("#basesetId").val();
	// 判断是否存在
	var data = csisexist(basesetId);
	if (data.code != 0) {
		$.modalAlert(data.msg, modal_status.FAIL);
		$.parentReload();
		// 关闭标签页
		// closeTab();
		return;
	}
	var radio2 = $("#radio2").is(':checked');
	if (radio2) {
		// 文件上传
		if (fileName == null) {
			$.modalAlert('请先上传文件', modal_status.FAIL);
			return;
		}
	}

	var radio3 = $("#28").is(':checked');
	if (radio3) {
		// 文件上传
		if (imgFileName == null) {
			$.modalAlert('请先上传文件', modal_status.FAIL);
			return;
		}
	}

	var title = $.trim($("#title").val());
	var author = ($.trim($("#author").val()) == '') ? "未知" : ($.trim($(
			"#author").val()));
	var url = $.trim($("#url").val());
	var pic = $.trim($("#pic").val());
	var lrc = $.trim($("#lrc").val());
	var remark = $("input[name='remark']").val();
	$.ajax({
		cache : true,
		type : "POST",
		url : ctx + "tool/baseset/detailedit/musiclist/save",
		data : {
			"basesetId" : basesetId,
			"title" : title,
			"author" : author,
			"url" : url,
			"pic" : pic,
			"lrc" : lrc,
			"remark" : remark
		},
		async : false,
		error : function(request) {
			$.modalAlert("系统错误", modal_status.FAIL);
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("新增成功,正在刷新数据请稍后……", {
					icon : 1,
					time : 500,
					shade : [ 0.1, '#fff' ]
				}, function() {
					$.parentReload();
				});
			} else {
				$.modalAlert(data.msg, modal_status.FAIL);
			}

		}
	});

}
// 判断主题是否存在
function csisexist(basesetId) {
	var data2;
	$.ajax({
		cache : true,
		type : "POST",
		url : ctx + "tool/baseset/checkBasesetExist",
		data : {
			"basesetId" : basesetId
		},
		async : false,
		error : function(request) {
			$.modalAlert("系统错误", modal_status.FAIL);
		},
		success : function(data) {
			data2 = data;
		}
	});
	return data2;
}