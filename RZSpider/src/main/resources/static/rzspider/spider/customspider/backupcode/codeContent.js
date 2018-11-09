var prefix = ctx + "spider/customspider/backupcode";
var editor;
$(function() {
	var customSpiderType = $('#customSpiderType').val();
	if (customSpiderType == 0) {
		// java
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
	} else if (customSpiderType == 1) {
		// py
		// 根据编辑器id构造出一个编辑器
		editor = CodeMirror.fromTextArea(document.getElementById("code"), {
			mode : "text/groovy", // 实现groovy代码高亮
			mode : "text/x-python", // 实现python代码高亮
			lineNumbers : true, // 显示行号
			theme : "dracula", // 设置主题
			lineWrapping : false, // 代码有滚动条
			lineWiseCopyCut : true,// 打开复制整行
			dragDrop : true,// 可拖动
			foldGutter : true,
			gutters : [ "CodeMirror-linenumbers", "CodeMirror-foldgutter" ],
			matchBrackets : true, // 括号匹配
		});
	} else if (customSpiderType == 2) {
		// js
		// 根据编辑器id构造出一个编辑器
		editor = CodeMirror.fromTextArea(document.getElementById("code"), {
			mode : "text/groovy", // 实现groovy代码高亮
			mode : "text/javascript", // 实现Js代码高亮
			lineNumbers : true, // 显示行号
			theme : "dracula", // 设置主题
			lineWrapping : false, // 代码有滚动条
			lineWiseCopyCut : true,// 打开复制整行
			dragDrop : true,// 可拖动
			foldGutter : true,
			gutters : [ "CodeMirror-linenumbers", "CodeMirror-foldgutter" ],
			matchBrackets : true, // 括号匹配
		});
	}
	editor.setOption("readOnly", true);
	editor.setValue($('#spiderFileCodeContent').val());
});
/* 爬虫备份导出 */
function exportCode() {
	location.href = prefix + "/exportCode/"
			+ $('#spiderCustomspiderBackupcodeId').val();
	layer.msg('正在下载,请稍后…', {
		icon : 1
	});
}