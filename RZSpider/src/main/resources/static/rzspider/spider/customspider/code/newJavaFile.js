var prefix = ctx + "spider/customspider/code";
$("#form-javacs-newJavaFile").validate({
	rules : {
		newJavaFileName : {
			required : true,
			minlength : 1,
			maxlength : 20
		}
	},
	messages : {
	},
	submitHandler : function(form) {
		newJavaFileSave();
	}
});

function newJavaFileSave() {
	var customSpiderId = $("#customSpiderId").val();
	var childId = $("#childId").val();
	// 不带后缀的文件名
	var fileName = $("#newJavaFileName").val();
	$.ajax({
		cache : true,
		type : "POST",
		url : prefix + "/newJavaFileSave",
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