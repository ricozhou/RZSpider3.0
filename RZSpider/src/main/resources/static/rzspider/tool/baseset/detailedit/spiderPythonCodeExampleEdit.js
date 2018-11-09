var editor;
$(function() {
	// 根据编辑器id构造出一个编辑器
	editor = CodeMirror.fromTextArea(document.getElementById("code"), {
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
	editor.setValue($('#spiderPythonCodeExample').val());

});

//绑定监听事件
$(function() {
	// 此地方注意，复选框被包裹在label内，所以click事件绑定label的,不是很好，待改进
	$('#clickLabel1').click(function() {
		$("#show1").css('display', 'block');
		$("#show2").css('display', 'none');
	});
	$('#clickLabel2').click(function() {
		$("#show2").css('display', 'block');
		$("#show1").css('display', 'none');
	});
});

// js选择
function selectFileOnline() {
	// 这种方式有效
	document.getElementById("radio2").checked = false;
	document.getElementById("radio1").checked = true;
	$(".i-checks").iCheck({
		checkboxClass : "icheckbox_square-green",
		radioClass : "iradio_square-green",
	})
	$("#show1").css('display', 'block');
	$("#show2").css('display', 'none');
}

// 表单验证
$("#form-spiderPythonCodeExample-edit").validate({
	rules : {
		// 此验证不成功需要重新验证
		code : {
			required : true,
			maxlength : 20000
		}

	},
	messages : {
		"code" : {
			maxlength : "最长20000字符",
		}
	},
	submitHandler : function(form) {
		update();
	}
})

// 保存
function update() {
	// id
	var basesetId = $("#basesetId").val();
	var radio1 = $("#radio1").is(':checked');
	var spiderPythonCodeExample;
	if (radio1) {
		spiderPythonCodeExample = $.trim(editor.getValue());
	}else{
		// 文件上传
		if (codeString == null) {
			$.modalAlert('请先上传文件', modal_status.FAIL);
			return;
		}
	}
	$.ajax({
		cache : true,
		type : "POST",
		url : ctx + "tool/baseset/detailedit/spiderCodeExampleSave",
		data : {
			"basesetId" : basesetId,
			"spiderPythonCodeExample" : spiderPythonCodeExample
		},
		async : false,
		error : function(request) {
			$.modalAlert("系统错误", modal_status.FAIL);
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("编辑成功,正在刷新数据请稍后……", {
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
