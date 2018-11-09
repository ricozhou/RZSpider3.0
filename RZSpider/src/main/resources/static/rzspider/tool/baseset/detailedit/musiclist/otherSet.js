$("#form-musiclist-otherset").validate({
	rules : {
		musicListMaxHeight : {
			digits : true,
			min : 0,
			max : 3000
		},
	},
	submitHandler : function(form) {
		otherSet();
	}
});

function otherSet() {
	var basesetId = $("#basesetId").val();
	// 判断是否存在
	var data = csisexist(basesetId);
	if (data.code != 0) {
		$.modalAlert(data.msg, modal_status.FAIL);
		$.parentReload();
		// 关闭标签页
		// closeTab();
		return;
	}
	var musicAutoPlay = $("#musicAutoPlay").is(':checked') == true ? 0 : 1;
	var musicLrcStart = $("#musicLrcStart").is(':checked') == true ? 0 : 1;
	var musicShowLrc = $("#musicShowLrc").is(':checked') == true ? 0 : 1;
	var musicFixed = $("#musicFixed").is(':checked') == true ? 0 : 1;
	var musicListFolded = $("#musicListFolded").is(':checked') == true ? 0 : 1;
	var musicListMaxHeight = $("#musicListMaxHeight").val() != '' ? $(
			"#musicListMaxHeight").val() : 240;
	$.ajax({
		cache : true,
		type : "POST",
		url : ctx + "tool/baseset/detailedit/musiclist/otherSet",
		data : {
			"basesetId" : basesetId,
			"musicAutoPlay" : musicAutoPlay,
			"musicLrcStart" : musicLrcStart,
			"musicShowLrc" : musicShowLrc,
			"musicFixed" : musicFixed,
			"musicListFolded" : musicListFolded,
			"musicListMaxHeight" : musicListMaxHeight
		},
		async : false,
		error : function(request) {
			$.modalAlert("系统错误", modal_status.FAIL);
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("设置成功,正在刷新数据请稍后……", {
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
// 判断主题是否存在
function csisexist(basesetId) {
	var data2;
	$.ajax({
		cache : true,
		type : "POST",
		url : ctx + "tool/baseset/checkBasesetExist",
		data : {
			"basesetId" : basesetId
		},
		async : false,
		error : function(request) {
			$.modalAlert("系统错误", modal_status.FAIL);
		},
		success : function(data) {
			data2 = data;
		}
	});
	return data2;
}