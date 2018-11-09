$("#form-baseset-edit").validate({
	rules : {
		themeName : {
			required : true,
			maxlength : 20,
			remote : {
				url : ctx + "tool/baseset/checkThemeNameUnique",
				type : "post",
				dataType : "json",
				data : {
					"basesetId" : function() {
						return $("#basesetId").val();
					},
					"themeName" : function() {
						return $.trim($("#themeName").val());
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
		spiderJavaPackagePrefix:{
			english:true,
			isBlank:true
		},
		downloadFileNamePrefix:{
			isBlank:true
		},
	},
	messages : {
		"themeName" : {
			remote: "主题已经存在"
		},
	},
	submitHandler : function(form) {
		update();
	}
});

// 一开始便加载
$(document).ready(function() {
	var useStatus = $("#useStatus").val();
	// 正在使用的过程不能修改状态
	if (useStatus == 0) {
		//状态按钮灰掉
		document.getElementById("status").setAttribute("disabled", true);
	}
})

function update() {
	var basesetId = $("#basesetId").val();
	var themeName = $("input[name='themeName']").val();
	var spiderJavaPackagePrefix = $("#spiderJavaPackagePrefix").val();
	var downloadFileNamePrefix = $("#downloadFileNamePrefix").val();
	var showMusicToolStatus = $("#srcDownloadStatus").is(':checked') == true ? 0 : 1;
	var status = $("input[name='status']").is(':checked') == true ? 0 : 1;
	var useStatus = $("#useStatus").val();
	var remark = $("input[name='remark']").val();
	$.ajax({
		cache : true,
		type : "POST",
		url : ctx + "tool/baseset/save",
		data : {
			"basesetId" : basesetId,
			"themeName" : themeName,
			"spiderJavaPackagePrefix" : spiderJavaPackagePrefix,
			"downloadFileNamePrefix" : downloadFileNamePrefix,
			"showMusicToolStatus" : showMusicToolStatus,
			"status" : status,
			"useStatus" : useStatus,
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