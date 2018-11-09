$("#form-straightlinkmanage-add").validate({
	rules : {
		straightlinkFileName : {
			required : true,
			maxlength : 50
		},
		straightlinkFileLimitDownTime : {
			required : true
		},
	},
	submitHandler : function(form) {
		add();
	}
});

// 一开始便加载
$(document).ready(function() {

})

function add() {
	if (straightlinkFileUuidName == null || straightlinkFileUuidName == '') {
		$.modalAlert('请先上传文件', modal_status.FAIL);
		return;
	}
	var straightlinkFileType = $("#radio1").is(':checked') == true ? 0 : ($(
			"#radio2").is(':checked') == true ? 1 : ($("#radio3")
			.is(':checked') == true ? 2
			: ($("#radio4").is(':checked') == true ? 3 : 4)));
	var straightlinkFileName2 = $("#straightlinkFileName").val();
	var status = $("input[name='status']").is(':checked') == true ? 0 : 1;
	var remark = $("input[name='remark']").val();
	$.ajax({
		cache : true,
		type : "POST",
		url : ctx + "tool/straightlinkmanage/save",
		data : {
			"straightlinkFileName" : straightlinkFileName2,
			"straightlinkFileType" : straightlinkFileType,
			"straightlinkFileUuidName" : straightlinkFileUuidName,
			"straightlinkFileUrlKey" : straightlinkFileUrlKey,
			"straightlinkFileExtensionName" : straightlinkFileExtensionName,
			"straightlinkFileSize" : straightlinkFileSize,
			"status" : status,
			"remark" : remark
		},
		async : false,
		error : function(request) {
			$.modalAlert("系统错误", modal_status.FAIL);
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("保存成功,正在刷新数据请稍后……", {
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
/** 关闭弹出框口 */
function layer_close_to_back() {
	// 发送到后台删除缓存文件夹
	$.ajax({
		cache : true,
		type : "POST",
		url : ctx + "tool/straightlinkmanage/deleteCacheFile",
		data : {
			"straightlinkFileUrlKey" : straightlinkFileUrlKey,
		},
		async : false,
		error : function(request) {
		},
		success : function(data) {
		}
	});
	var index = parent.layer.getFrameIndex(window.name);
	parent.layer.close(index);
}