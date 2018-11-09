var prefix = ctx + "commontool/toolrun";
$("#form-toolrun-run").validate({
	rules : {
		pic : {
			required : true,
			maxlength : 2000
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
	// 绑定复选框
	// 此地方注意，复选框被包裹在label内，所以click事件绑定label的,不是很好，待改进
	$('#clickLabel1').click(function() {
		document.getElementById("radio2").checked = false;
		document.getElementById("radio1").checked = true;
		controImgOnline();
		// 显示图片
	});
	$('#clickLabel2').click(function() {
		document.getElementById("radio1").checked = false;
		document.getElementById("radio2").checked = true;
		controImgUpload();
		// 显示图片
	});
	// 监听文本框预览随时成像
	$('#pic').bind('input propertychange', function() {
		$("#show4").css('display', 'block');
		showImg();
	});
}
// 设置图片
function showImg() {
	document.getElementById("showImg").src = $('#pic').val();
	setImgWH(285, 285);
}
// 设置显示大小
function setImgWH(w, h) {
	var showImg = document.getElementById("showImg");
	var sw = showImg.width;
	var sh = showImg.height;
	// 按比例调整显示大小
	if (sw > sh) {
		showImg.width = w;
	} else {
		showImg.height = h;
	}
}
function controImgOnline() {
	$("#show3").css('display', 'none');
	chanageLabel();
}
function controImgUpload() {
	$("#show3").css('display', 'block');
	chanageLabel();
}
// js选择
function selectImgOnline() {
	// 这种方式有效
	document.getElementById("radio2").checked = false;
	document.getElementById("radio1").checked = true;
	controImgOnline();
}
// 改变选择框颜色
function chanageLabel() {
	$(".i-checks").iCheck({
		checkboxClass : "icheckbox_square-green",
		radioClass : "iradio_square-green",
	})
}
// 运行
function run() {
	var toolBackId = $('#toolBackId').val();
	var radio2 = $("#radio2").is(':checked');
	if (radio2) {
		// 文件上传
		if (imgFileName == null) {
			$.modalAlert('请先上传文件', modal_status.FAIL);
			return;
		}
	}
	var imageUrl = $('#pic').val();
	// 请求后台处理
	$.ajax({
		cache : true,
		type : "POST",
		url : prefix + "/runOCRRead",
		data : {
			"toolBackId" : toolBackId,
			"imageUrl" : imageUrl
		},
		async : false,
		error : function(request) {
			$.modalAlert("系统错误", modal_status.FAIL);
		},
		beforeSend : function() {
			// 禁用运行按钮
			document.getElementById("runOCRRead")
					.setAttribute("disabled", true);
		},
		success : function(data) {
			if (data.code == 0 && data.exportContent != null) {
				$('#content').val(data.exportContent);
				// 显示复制按钮
				document.getElementById("copyText2")
						.removeAttribute("disabled");
			} else {
				$.modalAlert(data.msg, modal_status.FAIL);
			}
			// 可用运行按钮
			document.getElementById("runOCRRead").removeAttribute("disabled");
		}
	});

}
// 复制到剪切板
function copyText() {
	// 选中对象
	var exportContent = document.getElementById("content");
	exportContent.select();
	// 执行浏览器复制命令
	document.execCommand("Copy");
}