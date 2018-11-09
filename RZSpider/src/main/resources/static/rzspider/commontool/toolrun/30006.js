var prefix = ctx + "commontool/toolrun";
$("#form-toolrun-run").validate({
	rules : {
		content : {
			required : true,
			maxlength : 1000000
		}
	},
	submitHandler : function(form) {
		run();
	}
});
// 运行
function run() {
	var toolBackId = $('#toolBackId').val();
	var formatFunction = $('#formatFunction').val();
	var content = $('#content').val();

	// if(formatFunction==0){
	// Process(content);
	// return;
	// }

	// 请求后台处理
	$.ajax({
		cache : true,
		type : "POST",
		url : prefix + "/runFormatText",
		data : {
			"toolBackId" : toolBackId,
			"formatFunction" : formatFunction,
			"content" : content
		},
		async : false,
		error : function(request) {
			$.modalAlert("系统错误", modal_status.FAIL);
		},
		beforeSend : function() {
			// 禁用运行按钮
			document.getElementById("runFormatText").setAttribute("disabled",
					true);
			$('#showJsonStatus').html("");
		},
		success : function(data) {
			if (data.code == 0 && data.exportContent != null) {
				$('#exportContent').val(data.exportContent);
				// 显示复制按钮
				document.getElementById("copyText2")
						.removeAttribute("disabled");
				// 提示是否是正确的json格式
				if (formatFunction == 0) {
					if (!isJSON(data.exportContent)) {
						// 提示
						$('#showJsonStatus').html("JSON格式不正确");
					}
				}
			} else {
				$.modalAlert(data.msg, modal_status.FAIL);
			}
			// 可用运行按钮
			document.getElementById("runFormatText")
					.removeAttribute("disabled");
		}
	});

}
// 清空
function clearText() {
	$('#content').val('');
	$('#exportContent').val('');
	$('#showJsonStatus').html("");
}

// 复制到剪切板
function copyText() {
	// 选中对象
	var exportContent = document.getElementById("exportContent");
	exportContent.select();
	// 执行浏览器复制命令
	document.execCommand("Copy");
}
// 判断是否为json
function isJSON(str) {
	if (typeof str == 'string') {
		try {
			var obj = JSON.parse(str.replace(/\n/g, "").replace(/\r/g, "")
					.replace(/\f/g, "").replace(/\t/g, "").replace(/\b/g, ""));
			if (typeof obj == 'object' && obj) {
				return true;
			} else {
				return false;
			}
		} catch (e) {
			return false;
		}
	}
}