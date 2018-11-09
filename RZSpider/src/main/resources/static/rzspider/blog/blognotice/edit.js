$("#form-blognotice-edit").validate({
	rules : {
		noticeTitle : {
			required : true,
			maxlength : 200
		},
		noticeContent : {
			required : true,
			maxlength : 10000
		},
		remark : {
			maxlength : 1000
		}
	},
	submitHandler : function(form) {
		update();
	}
});

function update() {
	var blogNoticeId = $("#blogNoticeId").val();
	var noticeTitle = $("#noticeTitle").val();
	var noticeContent = $("#noticeContent").val();
	var status = $("input[name='status']").is(':checked') == true ? 0 : 1;
	var remark = $("input[name='remark']").val();
	$.ajax({
		cache : true,
		type : "POST",
		url : ctx + "blog/blognotice/save",
		data : {
			"blogNoticeId" : blogNoticeId,
			"noticeTitle" : noticeTitle,
			"noticeContent" : noticeContent,
			"status" : status,
			"remark" : remark
		},
		async : false,
		error : function(request) {
			$.modalAlert("系统错误", modal_status.FAIL);
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("修改成功,正在刷新数据请稍后……", {
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