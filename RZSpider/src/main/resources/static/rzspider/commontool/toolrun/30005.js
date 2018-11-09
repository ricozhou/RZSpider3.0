var prefix = ctx + "commontool/toolrun";
$("#form-toolrun-run").validate({
	rules : {
		sfImgSizeW : {
			required : true,
			digits : true,
			min : 1,
			max : 5000
		},
		sfImgSizeH : {
			required : true,
			digits : true,
			min : 1,
			max : 5000
		},
		createImgSizeW : {
			required : true,
			digits : true,
			min : 1,
			max : 5000
		},
		createImgSizeH : {
			required : true,
			digits : true,
			min : 1,
			max : 5000
		},
		charArray2 : {
			required : true,
			minlength : 2,
			maxlength : 30
		},
		charSize2 : {
			required : true,
			digits : true,
			min : 1,
			max : 40
		},
		pic : {
			required : true,
			maxlength : 2000
		},
		imgIntensity2 : {
			required : true,
			digits : true,
			min : 1,
			max : 20
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
	$('#sfImgSize').bind(
			'change',
			function() {
				var sfImgSize = $(this).val();
				if (sfImgSize == -1) {
					// 显示自定义框
					$("#show1").css('visibility', 'visible');
					$("#show2").css('visibility', 'visible');
					document.getElementById("sfImgSizeW").removeAttribute(
							"disabled");
					document.getElementById("sfImgSizeH").removeAttribute(
							"disabled");
				} else {
					// 隐藏自定义框
					$("#show1").css('visibility', 'hidden');
					$("#show2").css('visibility', 'hidden');
					document.getElementById("sfImgSizeW").setAttribute(
							"disabled", true);
					document.getElementById("sfImgSizeH").setAttribute(
							"disabled", true);
				}
			});
	// 绑定下拉框
	$('#createImgSize').bind(
			'change',
			function() {
				var createImgSize = $(this).val();
				if (createImgSize == -1) {
					// 显示自定义框
					$("#show3").css('visibility', 'visible');
					$("#show4").css('visibility', 'visible');
					document.getElementById("createImgSizeW").removeAttribute(
							"disabled");
					document.getElementById("createImgSizeH").removeAttribute(
							"disabled");
				} else {
					// 隐藏自定义框
					$("#show3").css('visibility', 'hidden');
					$("#show4").css('visibility', 'hidden');
					document.getElementById("createImgSizeW").setAttribute(
							"disabled", true);
					document.getElementById("createImgSizeH").setAttribute(
							"disabled", true);
				}
			});
	// 绑定下拉框
	$('#charArray').bind(
			'change',
			function() {
				var charArray = $(this).val();
				if (charArray == -1) {
					// 显示自定义框
					$("#show5").css('visibility', 'visible');
					document.getElementById("charArray2").removeAttribute(
							"disabled");
				} else {
					// 隐藏自定义框
					$("#show5").css('visibility', 'hidden');
					document.getElementById("charArray2").setAttribute(
							"disabled", true);
				}
			});
	// 绑定下拉框
	$('#charSize').bind(
			'change',
			function() {
				var charSize = $(this).val();
				if (charSize == -1) {
					// 显示自定义框
					$("#show6").css('visibility', 'visible');
					document.getElementById("charSize2").removeAttribute(
							"disabled");
				} else {
					// 隐藏自定义框
					$("#show6").css('visibility', 'hidden');
					document.getElementById("charSize2").setAttribute(
							"disabled", true);
				}
			});
	// 绑定下拉框
	$('#imgIntensity').bind(
			'change',
			function() {
				var imgIntensity = $(this).val();
				if (imgIntensity == -1) {
					// 显示自定义框
					$("#show7").css('visibility', 'visible');
					document.getElementById("imgIntensity2").removeAttribute(
							"disabled");
				} else {
					// 隐藏自定义框
					$("#show7").css('visibility', 'hidden');
					document.getElementById("imgIntensity2").setAttribute(
							"disabled", true);
				}
			});

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
		$("#show9").css('display', 'block');
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
	$("#show8").css('display', 'none');
	chanageLabel();
}
function controImgUpload() {
	$("#show8").css('display', 'block');
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
	var w = document.getElementById("showImg").width;
	var h = document.getElementById("showImg").height;

	var sfImgSize = $('#sfImgSize').val();
	var sfImgSizeW = sfImgSize;
	var sfImgSizeH = sfImgSize;
	if (sfImgSize == -1) {
		sfImgSizeW = $('#sfImgSizeW').val();
		sfImgSizeH = $('#sfImgSizeH').val();
	} else if (sfImgSize == 0) {
		sfImgSizeW = w;
		sfImgSizeH = h;
	}

	var createImgSize = $('#createImgSize').val();
	var createImgSizeW = createImgSize;
	var createImgSizeH = createImgSize;
	if (createImgSize == -1) {
		createImgSizeW = $('#createImgSizeW').val();
		createImgSizeH = $('#createImgSizeH').val();
	} else if (createImgSize == 0) {
		createImgSizeW = w;
		createImgSizeH = h;
	}

	var charArray = $('#charArray').val();
	if (charArray == -1) {
		charArray = $('#charArray2').val();
	} else if (charArray == 0) {
		charArray = '@&;.';
	}
	var charSize = $('#charSize').val();
	if (charSize == -1) {
		charSize = $('#charSize2').val();
	}

	var imgIntensity = $('#imgIntensity').val();
	if (imgIntensity == -1) {
		imgIntensity = $('#imgIntensity2').val();
	}
	// 请求后台处理
	$
			.ajax({
				cache : true,
				type : "POST",
				url : prefix + "/runImgToChar",
				data : {
					"toolBackId" : toolBackId,
					"imageUrl" : imageUrl,
					"sfImgSizeW" : sfImgSizeW,
					"sfImgSizeH" : sfImgSizeH,
					"createImgSizeW" : createImgSizeW,
					"createImgSizeH" : createImgSizeH,
					"charArray" : charArray,
					"charSize" : charSize,
					"imgIntensity" : imgIntensity
				},
				async : false,
				error : function(request) {
					$.modalAlert("系统错误", modal_status.FAIL);
				},
				beforeSend : function() {
					// 禁用运行按钮
					document.getElementById("runImgToChar").setAttribute(
							"disabled", true);
				},
				success : function(data) {
					if (data.code == 0 && data.fileName != null) {
						// 显示
						$("#show10").css('display', 'block');
						document.getElementById("showImg2").src = '/cachefiles/'
								+ data.fileName;
						// 把生成的图片名存放一下
						$('#imgToCharFileName').val(data.fileName);
						// 显示保存按钮
						document.getElementById("imgSave2").removeAttribute(
								"disabled");
					} else {
						$.modalAlert(data.msg, modal_status.FAIL);
					}
					// 可用运行按钮
					document.getElementById("runImgToChar").removeAttribute(
							"disabled");
				}
			});
}
// 保存
function imgSave() {
	// 先确认是否存在
	if (!checkImgFileExist($('#imgToCharFileName').val())) {
		// 刷新
		$.modalAlert('文件已不存', modal_status.FAIL);
		return;
	}
	location.href = prefix + "/downloadImg?imgFileName="
			+ $('#imgToCharFileName').val();
	layer.msg('正在下载,请稍后…', {
		icon : 1
	});
}
// 下载前检查文件是否存在
function checkImgFileExist(fileName) {
	var fileExist = false;
	var toolBackId = $('#toolBackId').val();
	// 请求后台处理
	$.ajax({
		cache : true,
		type : "POST",
		url : prefix + "/checkImgFileExist",
		data : {
			"toolBackId" : toolBackId,
			"content" : fileName
		},
		async : false,
		error : function(request) {
			$.modalAlert("系统错误", modal_status.FAIL);
		},
		success : function(data) {
			if (data.code == 0) {
				fileExist = true;
			} else {
				fileExist = false;
			}
		}
	});
	return fileExist;
}