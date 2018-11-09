$("#form-straightlinkmanage-edit").validate({
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
		update();
	}
});

// 一开始便加载
$(document).ready(function() {
	// 设置显示类型
	var straightlinkFileType = $('#straightlinkFileType').val();
	if (straightlinkFileType == 0) {
		document.getElementById("radio1").checked = true;
	} else if (straightlinkFileType == 1) {
		document.getElementById("radio2").checked = true;
	} else if (straightlinkFileType == 2) {
		document.getElementById("radio3").checked = true;
	} else if (straightlinkFileType == 3) {
		document.getElementById("radio4").checked = true;
	} else if (straightlinkFileType == 4) {
		document.getElementById("radio5").checked = true;
	}
})

function update() {
	var straightlinkmanageId = $("#straightlinkmanageId").val();
	var straightlinkFileUuidName2 = $("#straightlinkFileUuidName").val();
	var straightlinkFileUrlKey = $("#straightlinkFileUrlKey").val();

	if (straightlinkFileUuidName2 == null || straightlinkFileUuidName2 == '') {
		$.modalAlert('请先上传文件', modal_status.FAIL);
		return;
	}
	var straightlinkFileExtensionName2 = $("#straightlinkFileExtensionName")
			.val();
	var straightlinkFileSize2 = $("#straightlinkFileSize").val();
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
			"straightlinkmanageId" : straightlinkmanageId,
			"straightlinkFileName" : straightlinkFileName2,
			"straightlinkFileType" : straightlinkFileType,
			"straightlinkFileUuidName" : straightlinkFileUuidName2,
			"straightlinkFileUrlKey" : straightlinkFileUrlKey,
			"straightlinkFileExtensionName" : straightlinkFileExtensionName2,
			"straightlinkFileSize" : straightlinkFileSize2,
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
