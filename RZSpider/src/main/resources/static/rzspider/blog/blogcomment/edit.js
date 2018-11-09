$("#form-blogcomment-edit").validate({
	rules : {
		remark : {
			maxlength : 1000
		},
	},
	submitHandler : function(form) {
		update();
	}
});

function update() {
	var blogCommentId = $("#blogCommentId").val();
	var status = $("#status").val();
	var remark = $("input[name='remark']").val();
	$.ajax({
		cache : true,
		type : "POST",
		url : ctx + "blog/blogcomment/save",
		data : {
			"blogCommentId" : blogCommentId,
			"status" : status,
			"remark" : remark,
		},
		async : false,
		error : function(request) {
			$.modalAlert("系统错误", modal_status.FAIL);
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("审核成功,正在刷新数据请稍后……", {
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
