var prefix = ctx + "commontool/toolrun";
$("#form-toolrun-run").validate({
	rules : {
		regularExpression : {
			required : true,
			maxlength : 2000
		},
		content : {
			required : true,
			maxlength : 100000
		}
	},
	submitHandler : function(form) {
		run();
	}
});
// 初始化显示界面
$(document).ready(function() {
	// 初始化
	init();
});
// 初始化
function init() {
	// 绑定下拉框
	$('#commonRE').bind('change', function() {
		var commonRE = $(this).val();
		$('#regularExpression').val(commonRE);
	});
}
// 运行
function run() {
	var toolBackId = $('#toolBackId').val();
	var content = $('#content').val();
	var regularExpression = $('#regularExpression').val();
	// 请求后台处理
	$.ajax({
		cache : true,
		type : "POST",
		url : prefix + "/runMatchRegularExpression",
		data : {
			"toolBackId" : toolBackId,
			"content" : content,
			"regularExpression" : regularExpression
		},
		async : false,
		error : function(request) {
			$.modalAlert("系统错误", modal_status.FAIL);
		},
		beforeSend : function() {
			// 禁用运行按钮
			document.getElementById("runMatchRegularExpression").setAttribute(
					"disabled", true);
		},
		success : function(data) {
			if (data.code == 0 && data.exportContent != null) {
				var exportContent='';
				if (data.number == 0) {
					exportContent = '没有找到匹配'
				} else {
					exportContent = '共找到' + data.number + '处匹配：\r\n'
							+ data.exportContent;
				}
				$('#exportContent').val(exportContent);
				// 显示复制按钮
				document.getElementById("copyText2")
						.removeAttribute("disabled");
			} else {
				$.modalAlert(data.msg, modal_status.FAIL);
			}
			// 可用运行按钮
			document.getElementById("runMatchRegularExpression")
					.removeAttribute("disabled");
		}
	});
}
// 清空
function clearText() {
	$('#content').val('');
	$('#exportContent').val('');
	$('#regularExpression').val('');
}

// 复制到剪切板
function copyText() {
	// 选中对象
	var exportContent = document.getElementById("exportContent");
	exportContent.select();
	// 执行浏览器复制命令
	document.execCommand("Copy");
}