$("#form-baseset-add").validate({
	rules:{
		themeName:{
			required:true,
			maxlength : 20,
			remote : {
				url : ctx + "tool/baseset/checkThemeNameUnique",
				type : "post",
				dataType : "json",
				data : {
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
	submitHandler:function(form){
		add();
	}
});

function add() {
    var themeName = $("input[name='themeName']").val();
	var spiderJavaPackagePrefix = $("#spiderJavaPackagePrefix").val();
	var downloadFileNamePrefix = $("#downloadFileNamePrefix").val();
	var showMusicToolStatus = $("#srcDownloadStatus").is(':checked') == true ? 0 : 1;
	var status = $("input[name='status']").is(':checked') == true ? 0 : 1;
	//1是停用中
	var useStatus = 1;
	var remark = $("input[name='remark']").val();
	$.ajax({
		cache : true,
		type : "POST",
		url : ctx + "tool/baseset/save",
		data : {
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