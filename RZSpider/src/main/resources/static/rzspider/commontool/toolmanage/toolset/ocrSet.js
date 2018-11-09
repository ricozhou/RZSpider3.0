$("#form-toolset-ocrset").validate({
	rules : {},
	submitHandler : function(form) {
		ocrSave();
	}
});

// 绑定监听事件
$(function() {
	// 初始化
	var toolOcrsetType = $('#toolOcrsetType2').val();
	if (toolOcrsetType == 0) {
		// BaiduOCR
		controBaiduOCROnline();
		document.getElementById("radio1").checked = true;
	} else if (toolOcrsetType == 1) {
		// T
		controTesseractOCROnline();
		document.getElementById("radio2").checked = true;
	}

	// 此地方注意，复选框被包裹在label内，所以click事件绑定label的,不是很好，待改进
	$('#clickLabel1').click(function() {
		controBaiduOCROnline();
	});
	$('#clickLabel2').click(function() {
		controTesseractOCROnline();
	});

});

function controBaiduOCROnline() {
	$("#show1").css('display', 'block');
	$("#show2").css('display', 'block');
	$("#show3").css('display', 'block');
}
function controTesseractOCROnline() {
	$("#show1").css('display', 'none');
	$("#show2").css('display', 'none');
	$("#show3").css('display', 'none');
}

function ocrSave() {
	var id = $("#id").val();
	var toolOcrsetType = $("#radio1").is(':checked') == true ? 0 : 1;
	var toolOcrsetBaiduocrApiId = $("#toolOcrsetBaiduocrApiId").val();
	var toolOcrsetBaiduocrApiKey = $("#toolOcrsetBaiduocrApiKey").val();
	var toolOcrsetBaiduocrSecretKey = $("#toolOcrsetBaiduocrSecretKey").val();
	var toolOcrsetRemark = $("input[name='toolOcrsetRemark']").val();
	$.ajax({
		cache : true,
		type : "POST",
		url : ctx + "commontool/toolmanage/toolset/ocrSave",
		data : {
			"id" : id,
			"toolOcrsetType" : toolOcrsetType,
			"toolOcrsetBaiduocrApiId" : toolOcrsetBaiduocrApiId,
			"toolOcrsetBaiduocrApiKey" : toolOcrsetBaiduocrApiKey,
			"toolOcrsetBaiduocrSecretKey" : toolOcrsetBaiduocrSecretKey,
			"toolOcrsetRemark" : toolOcrsetRemark
		},
		async : false,
		error : function(request) {
			$.modalAlert("系统错误", modal_status.FAIL);
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("保存成功,正在刷新数据请稍后……", {
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