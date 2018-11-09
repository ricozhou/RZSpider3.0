//// 表单验证
//$("#form-bookmanage-add").validate({
//	rules : {},
//	submitHandler : function(form) {
//		clearCacheFile();
//	}
//})

// 清除缓存文件
function clearCacheFile() {
	$.modalConfirm("确定要清除缓存文件吗？", function() {
		clearCache(0);
	})
}

// 清除缓存数据
function clearCacheDB() {
	$.modalConfirm("确定要清除缓存数据吗？", function() {
		clearCache(1);
	})
}
function clearCache(flag) {
	$.ajax({
		cache : true,
		type : "POST",
		url : ctx + "tool/baseset/clearCache",
		data : {
			"flag" : flag
		},
		async : false,
		error : function(request) {
			$.modalAlert("系统错误", modal_status.FAIL);
		},
		beforeSend : function() {
			// 禁用按钮
			if (flag == 0) {
				document.getElementById("clearCacheFile").setAttribute(
						"disabled", true);
			} else if (flag == 1) {
				document.getElementById("clearCacheDB").setAttribute(
						"disabled", true);
			}
			layer.msg("正在清除,请稍后……", {
				icon : 1,
				time : 0,
				shade : [ 0.1, '#fff' ]
			});
		},
		success : function(data) {
			if (data.code == 0) {

				// parent.layer.msg("清除成功,正在刷新数据请稍后……", {
				// icon : 1,
				// time : 500,
				// shade : [ 0.1, '#fff' ]
				// }, function() {
				// $.parentReload();
				// });
				// 关闭提示窗
				layer.closeAll('dialog');
			} else {
				$.modalAlert(data.msg, modal_status.FAIL);
			}
			// 启用按钮
			if (flag == 0) {
				document.getElementById("clearCacheFile").removeAttribute(
						"disabled");
			} else if (flag == 1) {
				document.getElementById("clearCacheDB").removeAttribute(
						"disabled");
			}
		}
	});
}