$("#form-codeType-edit").validate({
	rules : {

	},

	submitHandler : function(form) {
		update();
	}
});

function update() {
	var spiderCodeTypeId = $('#spiderCodeTypeId').val();
	var status = $("input[name='status']").is(':checked') == true ? 0 : 1;
	$.ajax({
		cache : true,
		type : "POST",
		url : ctx + "spider/codeType/save",
		data : {
			"spiderCodeTypeId" : spiderCodeTypeId,
			"status" : status
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