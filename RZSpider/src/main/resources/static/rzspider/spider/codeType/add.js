$("#form-codeType-add").validate({
	rules : {
		customSpiderType : {
			required : true,
			digits : true,
			min : 0,
			max : 20,
			remote : {
				url : ctx + "spider/codeType/checkCustomSpiderTypeUnique",
				type : "post",
				dataType : "json",
				data : {
					"customSpiderType" : function() {
						return $.trim($("#customSpiderType").val());
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
		spiderCodeTypeFolder : {
			required : true,
			maxlength : 20,
			remote : {
				url : ctx + "spider/codeType/checkSpiderCodeTypeFolderUnique",
				type : "post",
				dataType : "json",
				data : {
					"spiderCodeTypeFolder" : function() {
						return $.trim($("#spiderCodeTypeFolder").val());
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
	},
	messages : {
		"customSpiderType" : {
			remote : "此代码类型已经存在",
		},
		"spiderCodeTypeFolder" : {
			remote : "此代码目录已经存在",
		}
	},
	submitHandler : function(form) {
		add();
	}
});

// 一开始便加载
$(document).ready(
		function() {
			// 查询所有爬虫代码类型
			$.ajax({
				cache : true,
				type : "GET",
				url : ctx + "spider/codeType/codeTypeData",
				async : false,
				error : function(request) {
					$.modalAlert("系统错误", modal_status.FAIL);
				},
				success : function(data) {
					if (data != null) {
						// 动态显示爬虫类型
						$("#customSpiderType").find("option").remove();
						for (var i = 0; i < data.length; i++) {
							var newOption = document.createElement("option");
							newOption.text = data[i].customSpiderTypeName + '（'
									+ data[i].customSpiderType + '）';
							newOption.value = data[i].customSpiderType;
							document.getElementById("customSpiderType").add(
									newOption);
						}
					} else {
						$.modalAlert(data.msg, modal_status.FAIL);
					}

				}
			});

		})

function add() {
	var customSpiderType = $('#customSpiderType').val();
	var spiderCodeTypeName = $('#spiderCodeTypeName').val();
	var spiderCodeTypeFolder = $('#spiderCodeTypeFolder').val();
	var status = $("input[name='status']").is(':checked') == true ? 0 : 1;
	data = {
		"customSpiderType" : customSpiderType,
		"spiderCodeTypeName" : spiderCodeTypeName,
		"spiderCodeTypeFolder" : spiderCodeTypeFolder,
		"status" : status
	}
	_ajax_save(ctx + "spider/codeType/save", data);
}