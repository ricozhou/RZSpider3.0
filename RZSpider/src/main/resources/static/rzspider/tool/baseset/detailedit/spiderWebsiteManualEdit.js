//加载文本编辑器
$().ready(function() {
	$('.summernote').summernote({
		height : '220px',
		lang : 'zh-CN',
		callbacks : {
			onImageUpload : function(files, editor, $editable) {
				sendFile(files);
			}
		}
	});
	var content = $("#spiderWebsiteManual").val();
	// 初始化
	if ($('#spiderWebsiteManualType').val() == 0) {
		$("#show1").css('display', 'block');
		$("#show2").css('display', 'none');
	} else {
		content = "";
		$("#show2").css('display', 'block');
		$("#show1").css('display', 'none');
	}
	$('#content_sn').summernote('code', content);
});

// 绑定监听事件
$(function() {
	// 此地方注意，复选框被包裹在label内，所以click事件绑定label的,不是很好，待改进
	$('#clickLabel1').click(function() {
		var radio1 = $("#radio1").val();
		$("#show1").css('display', 'block');
		$("#show2").css('display', 'none');
	});
	$('#clickLabel2').click(function() {
		var radio2 = $("#radio2").val();
		$("#show2").css('display', 'block');
		$("#show1").css('display', 'none');
	});
});

// 表单验证
$("#form-spiderWebsiteManual-edit").validate({
	rules : {
	// // 此验证不成功需要重新验证
	// code : {
	// required : true,
	// maxlength : 20000
	// }

	},
	messages : {
	// "code" : {
	// maxlength : "最长20000字符",
	// }
	},
	submitHandler : function(form) {
		update();
	}
})

// 保存
function update() {
	// id
	var basesetId = $("#basesetId").val();
	var spiderWebsiteManualType = $("#radio1").is(':checked') == true ? 0 : 1;
	var spiderWebsiteManual;
	if (spiderWebsiteManualType == 0) {
		spiderWebsiteManual = $("#content_sn").summernote('code');
	} else {
		// 文件上传
		if (fileName == null) {
			$.modalAlert('请先上传文件', modal_status.FAIL);
			return;
		}
		spiderWebsiteManual = fileName;
	}
	$.ajax({
		cache : true,
		type : "POST",
		url : ctx + "tool/baseset/detailedit/spiderManualSave",
		data : {
			"basesetId" : basesetId,
			"spiderWebsiteManualType" : spiderWebsiteManualType,
			"spiderWebsiteManual" : spiderWebsiteManual
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
