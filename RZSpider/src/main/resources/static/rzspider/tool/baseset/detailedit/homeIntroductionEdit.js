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
	var content = $("#homeIntroduction").val();
	$('#content_sn').summernote('code', content);
});

// 表单验证
$("#form-homeIntroduction-edit").validate({
	rules : {
		homeIntroduction : {
			required : true,
			maxlength : 20000
		}

	},
	messages : {
		"homeIntroduction" : {
			maxlength : "最长20000字",
		}
	},
	submitHandler : function(form) {
		update();
	}
})
function update() {
	// id
	var basesetId = $("#basesetId").val();
	// 内容
	var homeIntroduction = $("#content_sn").summernote('code');
	$.ajax({
		cache : true,
		type : "POST",
		url : ctx + "tool/baseset/detailedit/homeIntroductionSave",
		data : {
			"basesetId" : basesetId,
			"homeIntroduction" : homeIntroduction
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
