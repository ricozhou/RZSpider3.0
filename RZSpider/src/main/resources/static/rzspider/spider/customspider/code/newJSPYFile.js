var prefix = ctx + "spider/customspider/code";
$("#form-jspycs-newJSPYFile").validate({
	rules : {
		newJSPYFileName : {
			required : true,
			minlength : 1,
			maxlength : 20
		}
	},
	messages : {},
	submitHandler : function(form) {
		newJSPYFileSave();
	}
});

function newJSPYFileSave() {
	var customSpiderId = $("#customSpiderId").val();
	var childId = $("#childId").val();
	// 带后缀的文件名
	var flag = $('#flag').val();
	var fileName = $("#newJSPYFileName").val() + (flag == 1 ? ".py" : ".js");
	$.ajax({
		cache : true,
		type : "POST",
		url : prefix + "/newJSPYFileSave",
		data : {
			"customSpiderId" : customSpiderId,
			"childId" : childId,
			"fileName" : fileName
		},
		async : false,
		error : function(request) {
			$.modalAlert("系统错误", modal_status.FAIL);
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("创建文件成功,正在刷新数据请稍后……", {
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