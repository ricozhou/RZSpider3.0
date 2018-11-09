$("#form-musiclist-batchAdd").validate({
	rules : {
		musicInfoJson : {
			required : true,
			maxlength : 30000,
			checkJson : true
		},
	},
	submitHandler : function(form) {
		batchAdd();
	}
});

function batchAdd() {
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
	// 利用title字段传值
	var title = $.trim($("#musicInfoJson").val()).replace(/\n/g, "").replace(
			/\r/g, "").replace(/\f/g, "").replace(/\t/g, "").replace(/\b/g, "");
	;
	var remark = $("input[name='remark']").val();
	$.ajax({
		cache : true,
		type : "POST",
		url : ctx + "tool/baseset/detailedit/musiclist/batchSave",
		data : {
			"basesetId" : basesetId,
			"title" : title,
			"remark" : remark
		},
		async : false,
		error : function(request) {
			$.modalAlert("系统错误", modal_status.FAIL);
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("批量添加成功,正在刷新数据请稍后……", {
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