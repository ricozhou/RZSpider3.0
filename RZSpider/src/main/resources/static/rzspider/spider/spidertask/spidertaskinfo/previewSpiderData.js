var prefix = ctx + "spider/spidertask/spidertaskinfo"

$(function() {
	// 注意json字符串要转为json格式
	var columns = JSON.parse($('#jsonColumn').val());
	var jsonData = JSON.parse($('#jsonData').val());
	var taskInfoId = $('#taskInfoId').val();
	// 直接column和data初始化table
	$.initTable4(columns, jsonData);
});

/* 任务-下载 */
function downloadSpiderData(taskFlag) {
	var taskInfoId = $('#taskInfoId').val();
	// 先校验数据是否存在
	$.ajax({
		cache : true,
		type : "POST",
		url : prefix + "/checkData",
		data : {
			"taskInfoId" : taskInfoId,
			"taskFlag" : taskFlag
		},
		async : true,
		error : function(request) {
			$.modalAlert("系统错误", modal_status.FAIL);
		},
		success : function(data) {
			if (data.code == 0) {
				// 有数据则下载
				downloadData(taskInfoId, taskFlag);
			} else {
				$.modalAlert(data.msg, modal_status.FAIL);
			}
		},
	});
}
/* 任务-下载数据 */
function downloadData(taskInfoId, taskFlag) {
	location.href = prefix + "/downloadData/" + taskInfoId + "/" + taskFlag;
	layer.msg('正在下载,请稍后…', {
		icon : 1
	});
}