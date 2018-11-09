var prefix = ctx + "blog/blogcolumn"
$("#form-blogcolumn-add").validate({
	rules : {
		blogColumnName : {
			required : true,
			maxlength : 8,
			remote : {
				url : prefix + "/checkColumnNameUnique",
				type : "post",
				dataType : "json",
				data : {
					"blogColumnName" : function() {
						return $.trim($("#blogColumnName").val());
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
		url : {
			required : true,
			maxlength : 20,
			remote : {
				url : prefix + "/checkUrlUnique",
				type : "post",
				dataType : "json",
				data : {
					"url" : function() {
						return $.trim($("#url").val());
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
		columnMessage : {
			maxlength : 50
		},
	},
	messages : {
		"blogColumnName" : {
			remote : "专栏已经存在"
		},
		"url" : {
			remote : "URL不能重复"
		}
	},
	submitHandler : function(form) {
		add();
	}
});

// 监听
$(function() {
	$("input[name='icon']").focus(function() {
		$(".icon-drop").show();
	});
	$("#form-blogcolumn-add").click(function(event) {
		var obj = event.srcElement || event.target;
		if (!$(obj).is("input[name='icon']")) {
			$(".icon-drop").hide();
		}
	});
	$(".icon-drop").find(".ico-list i").on("click", function() {
		$('#icon').val($(this).attr('class'));
	});
});

function add() {
	var blogColumnName = $("#blogColumnName").val();
	var parentId;
	var columnType;
	if (flag == 0) {
		parentId = 0;
		columnType = 'M';
	} else if (flag == 1) {
		parentId = parentId2;
		columnType = 'C';
	}
	var url = $("#url").val();
	regUrl = /^[//]{1}[a-z]+$/;
	if (!regUrl.test(url)) {
		$.modalAlert("URL不正确", modal_status.FAIL);
		return;
	}
	var orderNum = $("#orderNum").val();
	var icon = $("#icon").val();

	var visible = $("input[name='status']").is(':checked') == true ? 0 : 1;
	var columnMessage = $("#columnMessage").val();
	var remark = $("input[name='remark']").val();
	$.ajax({
		cache : true,
		type : "POST",
		url : prefix + "/save",
		data : {
			"blogColumnName" : blogColumnName,
			"parentId" : parentId,
			"url" : url,
			"orderNum" : orderNum,
			"columnType" : columnType,
			"visible" : visible,
			"icon" : icon,
			"backImg" : fileBase64_1,
			"columnMessage" : columnMessage,
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