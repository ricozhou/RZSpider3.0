$("#form-downloadmanage-edit").validate({
	rules : {
		downloadFileName : {
			required : true,
			maxlength : 50
		},
		downloadFileLimitDownTime : {
			required : true
		},
	},
	submitHandler : function(form) {
		update();
	}
});

// 一开始便加载
$(document).ready(function() {
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
	// 设置显示类型
	var downloadFileType = $('#downloadFileType').val();
	if (downloadFileType == 0) {
		document.getElementById("radio1").checked = true;
	} else if (downloadFileType == 1) {
		document.getElementById("radio2").checked = true;
	} else if (downloadFileType == 2) {
		document.getElementById("radio3").checked = true;
	} else if (downloadFileType == 3) {
		document.getElementById("radio4").checked = true;
	} else if (downloadFileType == 4) {
		document.getElementById("radio5").checked = true;
	}
})

function update() {
	var downloadmanageId = $("#downloadmanageId").val();
	var downloadFileUuidName2 = $("#downloadFileUuidName").val();

	if (downloadFileUuidName2 == null || downloadFileUuidName2 == '') {
		$.modalAlert('请先上传文件', modal_status.FAIL);
		return;
	}
	var downloadFileExtensionName2 = $("#downloadFileExtensionName").val();
	var downloadFileSize2 = $("#downloadFileSize").val();
	var downloadFileType = $("#radio1").is(':checked') == true ? 0 : ($(
			"#radio2").is(':checked') == true ? 1 : ($("#radio3")
			.is(':checked') == true ? 2
			: ($("#radio4").is(':checked') == true ? 3 : 4)));
	var downloadFileName2 = $("#downloadFileName").val();
	var downloadFileLimitDownNum = $("#downloadFileLimitDownNum").val();
	var downloadFileLimitDownTime = $("#downloadFileLimitDownTime").val();
	var status = $("input[name='status']").is(':checked') == true ? 0 : 1;
	var remark = $("input[name='remark']").val();
	$.ajax({
		cache : true,
		type : "POST",
		url : ctx + "tool/downloadmanage/save",
		data : {
			"downloadmanageId" : downloadmanageId,
			"downloadFileName" : downloadFileName2,
			"downloadFileType" : downloadFileType,
			"downloadFileUuidName" : downloadFileUuidName2,
			"downloadFileExtensionName" : downloadFileExtensionName2,
			"downloadFileSize" : downloadFileSize2,
			"downloadFileLimitDownNum" : downloadFileLimitDownNum,
			"downloadFileLimitDownTime" : downloadFileLimitDownTime,
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
