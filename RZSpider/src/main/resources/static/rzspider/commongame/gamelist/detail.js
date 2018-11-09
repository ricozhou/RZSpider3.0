var prefix = ctx + "commongame/gamelist"
// 一开始便加载
$(document).ready(function() {
	// 调整按钮显示
	setButtonUploadSrcFile();
	setButtonUploadExecexeFile();
	setButtonUploadSetupexeFile()
})

// 按钮状态函数
function setButtonUploadSrcFile() {
	var srcDownloadStatus = $('#srcDownloadStatus').is(':checked');
	if(downSrcFileFlag=='hidden'){
		return ;
	}
	// 选中则可用
	if (srcDownloadStatus) {
		document.getElementById("uploadSrcFile").removeAttribute("disabled");
	} else {
		document.getElementById("uploadSrcFile").setAttribute("disabled", true);
	}
}
// 按钮状态函数
function setButtonUploadExecexeFile() {
	var execexeDownloadStatus = $('#execexeDownloadStatus').is(':checked');
	if(downExecexeFileFlag=='hidden'){
		return ;
	}
	// 选中则可用
	if (execexeDownloadStatus) {
		document.getElementById("uploadExecexeFile")
				.removeAttribute("disabled");
	} else {
		document.getElementById("uploadExecexeFile").setAttribute("disabled",
				true);
	}
}
// 按钮状态函数
function setButtonUploadSetupexeFile() {
	var setupexeDownloadStatus = $('#setupexeDownloadStatus').is(':checked');
	if(downSetupexeFileFlag=='hidden'){
		return ;
	}
	// 选中则可用
	if (setupexeDownloadStatus) {
		document.getElementById("uploadSetupexeFile").removeAttribute(
				"disabled");
	} else {
		document.getElementById("uploadSetupexeFile").setAttribute("disabled",
				true);
	}
}

var gameBackId=$('#gameBackId').val();
/* 下载 */
function downSrcFile() {
	location.href = prefix + "/downSrcFile/"+gameBackId;
	layer.msg('正在下载,请稍后…', {
		icon : 1
	});
}
/* 下载 */
function downExecexeFile() {
	location.href = prefix + "/downExecexeFile/"+gameBackId;
	layer.msg('正在下载,请稍后…', {
		icon : 1
	});
}
/* 下载 */
function downSetupexeFile() {
	location.href = prefix + "/downSetupexeFile/"+gameBackId;
	layer.msg('正在下载,请稍后…', {
		icon : 1
	});
}

