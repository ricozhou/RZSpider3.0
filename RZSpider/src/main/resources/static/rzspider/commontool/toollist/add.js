$("#form-toollist-add").validate({
	rules : {
		toolNickName : {
			required : true,
			maxlength : 50,
			remote : {
				url : ctx + "commontool/toollist/checkToolNickNameUnique",
				type : "post",
				dataType : "json",
				data : {
					"toolNickName" : function() {
						return $.trim($("#toolNickName").val());
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
		toolName : {
			required : true,
		},
		toolInstruction : {
			maxlength : 500
		},

	},
	messages : {
		"toolNickName" : {
			maxlength : '最长50字',
			remote : "工具昵称必须唯一"
		},
		"toolInstruction" : {
			maxlength : "最长500字",
		},
		"toolName" : {
			required : "无可用工具",
		}
	},
	submitHandler : function(form) {
		add();
	}
});
var toolData;
// 一开始便加载
$(document).ready(
		function() {
			// 查询所有工具名称
			$.ajax({
				cache : true,
				type : "GET",
				url : ctx + "commontool/toolmanage/selectAllToolName",
				async : false,
				error : function(request) {
					$.modalAlert("系统错误", modal_status.FAIL);
				},
				success : function(data) {
					toolData = data;
					if (data != null) {
						// 动态显示
						$("#toolName").find("option").remove();
						for (var i = 0; i < data.length; i++) {
							var newOption = document.createElement("option");
							newOption.text = data[i].toolName + "（"
									+ data[i].toolBackId + "）";
							newOption.value = data[i].toolName;
							document.getElementById("toolName").add(newOption);
						}
						if (data[0] != null) {
							setBaseData(data[0]);
						}
					} else {
						$.modalAlert(data.msg, modal_status.FAIL);
					}

				}
			});
		})

// 绑定监听事件
$(function() {
	$('#toolName').bind('change', function() {
		var toolName = $(this).val();
		for (var i = 0; i < toolData.length; i++) {
			if (toolName == toolData[i].toolName) {
				setBaseData(toolData[i]);
			}
		}

	});
});

// 设置初始值
function setBaseData(data) {
	// 设置默认参数
	$("#toolInstruction").val('');
	$("#toolInstruction").val(data.toolDes);

}

// 添加
function add() {
	var toolNickName = $("#toolNickName").val();
	var toolName = $("#toolName").val();
	var toolInstruction = $("#toolInstruction").val();
	var remark = $("input[name='remark']").val();
	if (toolName == null) {
		$.modalAlert('暂无工具可使用', modal_status.FAIL);
		return;
	}
	$.ajax({
		cache : true,
		type : "POST",
		url : ctx + "commontool/toollist/save",
		data : {
			"toolNickName" : toolNickName,
			"toolName" : toolName,
			"toolInstruction" : toolInstruction,
			"remark" : remark
		},
		async : false,
		error : function(request) {
			$.modalAlert("系统错误", modal_status.FAIL);
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("添加工具成功,正在刷新数据请稍后……", {
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