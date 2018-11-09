$("#form-blogtags-add").validate({
	rules : {
		blogTagsName : {
			required : true,
			maxlength : 9,
			remote : {
				url : ctx + "blog/blogtags/checkTagNameUnique",
				type : "post",
				dataType : "json",
				data : {
					"blogTagsName" : function() {
						return $.trim($("#blogTagsName").val());
					}
				},
				dataFilter : function(data, type) {
					if (data == "0")
						return true;
					else
						return false;
				}
			}
		},
		tagsMessage : {
			maxlength : 50,
		},
		remark : {
			maxlength : 50,
		}
	},
	messages : {
		"blogTagsName" : {
			remote : "标签已经存在"
		}
	},
	submitHandler : function(form) {
		add();
	}
});

function add() {
	var blogTagsName = $("#blogTagsName").val();
	var tagsMessage = $("#tagsMessage").val();
	var status = $("input[name='status']").is(':checked') == true ? 0 : 1;
	var remark = $("input[name='remark']").val();
	$.ajax({
		cache : true,
		type : "POST",
		url : ctx + "blog/blogtags/save",
		data : {
			"blogTagsName" : blogTagsName,
			"tagsMessage" : tagsMessage,
			"backImg" : fileBase64_1,
			"status" : status,
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