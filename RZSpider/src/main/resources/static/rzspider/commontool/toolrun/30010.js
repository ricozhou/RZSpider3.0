var prefix = ctx + "commontool/toolrun";
$("#form-toolrun-run").validate({
	rules : {},
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
	initCodeeditor();
	initConsoleEditor();
	initOther();
}
// 初始化其他
function initOther() {
	// 绑定下拉框
	$('#codeType').bind('change', function() {
		var codeType = $(this).val();
		if (codeType == 4) {
			$("#show1").css('display', 'block');
			$("#show2").css('display', 'block');
		} else {
			$("#show1").css('display', 'none');
			$("#show2").css('display', 'none');
		}

	});
}
function initCodeeditor() {
	// 根据编辑器id构造出一个编辑器
	editor = CodeMirror.fromTextArea(document.getElementById("content"), {
		mode : "text/groovy", // 实现groovy代码高亮
		mode : "text/x-java", // 实现Java代码高亮
		lineNumbers : true, // 显示行号
		theme : "dracula", // 设置主题
		lineWrapping : false, // 代码有滚动条
		lineWiseCopyCut : true,// 打开复制整行
		dragDrop : true,// 可拖动
		foldGutter : true,
		gutters : [ "CodeMirror-linenumbers", "CodeMirror-foldgutter" ],
		matchBrackets : true, // 括号匹配
	});

	// editor.setSize('1000px', '950px'); //设置代码框的长宽
	editor.setValue(""); // 先将代码框的值清空
};
function initConsoleEditor() {
	// 根据编辑器id构造出一个编辑器
	consoleEditor = CodeMirror.fromTextArea(document
			.getElementById("exportContent"), {
		// mode
		// :
		// "text/groovy",
		// //
		// 实现groovy代码高亮
		mode : "text/x-java", // 实现Java代码高亮
		lineNumbers : true, // 显示行号
		theme : "dracula", // 设置主题
		lineWrapping : false, // 代码有滚动条
		lineWiseCopyCut : true,// 打开复制整行
		dragDrop : true,// 可拖动
		foldGutter : true,
		gutters : [ "CodeMirror-linenumbers", "CodeMirror-foldgutter" ],
	// matchBrackets : true, // 括号匹配
	});

	// editor.setSize('1000px', '950px'); //设置代码框的长宽
	consoleEditor.setValue(""); // 先将代码框的值清空
	consoleEditor.setOption("readOnly", true);
};
// 运行
function run() {
	var toolBackId = $('#toolBackId').val();
	var formatFunction = $('#codeType').val();
	var content = $.trim(editor.getValue());
	if ($.trim(content) == '') {
		$.modalAlert("不能为空", modal_status.FAIL);
		return;
	}
	var sqlType;
	if (formatFunction == 4) {
		sqlType = $('#sqlType').val();
	}
	// 请求后台处理
	$.ajax({
		cache : true,
		type : "POST",
		url : prefix + "/runFormatCode",
		data : {
			"toolBackId" : toolBackId,
			"formatFunction" : formatFunction,
			"sqlType" : sqlType,
			"content" : content
		},
		async : false,
		error : function(request) {
			$.modalAlert("系统错误", modal_status.FAIL);
		},
		beforeSend : function() {
			// 禁用运行按钮
			document.getElementById("runFormatCode").setAttribute("disabled",
					true);
			$('#showCodeStatus').html("");
		},
		success : function(data) {
			if (data.code == 0 && data.exportContent != null) {
				consoleEditor.setValue(data.exportContent);
				// 显示复制按钮
				document.getElementById("copyText2")
						.removeAttribute("disabled");
				if (formatFunction == 0 && data.flag == 1) {
					$('#showCodeStatus').html("JAVA格式不正确");
				}
				if (formatFunction == 4 && data.flag == 1) {
					$('#showCodeStatus').html("SQL格式不正确");
				}
			} else {
				$.modalAlert(data.msg, modal_status.FAIL);
			}
			// 可用运行按钮
			document.getElementById("runFormatCode")
					.removeAttribute("disabled");
		}
	});

}
// 清空
function clearText() {
	editor.setValue("");
	consoleEditor.setValue("");
	$('#showCodeStatus').html("");
}

// 复制到剪切板
function copyText() {
	const
	textarea = document.createElement('textarea');
	document.body.appendChild(textarea);
	textarea.setAttribute('readonly', 'readonly');
	textarea.value = consoleEditor.getValue();
	textarea.select();
	// 执行浏览器复制命令
	document.execCommand("Copy");
	// 删除
	document.body.removeChild(textarea);
}