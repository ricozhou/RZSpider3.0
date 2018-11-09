var prefix = ctx + "toolbox"

$(function() {
	// 初始化加载
	// 查询可用工具
	// 请求后台处理
	$.ajax({
		cache : true,
		type : "POST",
		url : prefix + "/canUseToolList",
		data : {},
		async : false,
		error : function(request) {
			$.modalAlert("系统错误", modal_status.FAIL);
		},
		success : function(data) {
			// 开始渲染html
			createToolHtml(data);
		}
	});

});
// 开始渲染html
function createToolHtml(data) {
	for (var i = 0; i < data.length; i++) {
		addHtml(data[i]['toolBackId'], data[i]['toolName'], data[i]['toolDes'])
	}
}
function addHtml(toolBackId, toolName, toolDes) {
	// 图片不存在将以默认图片显示
	defaultImg = "'img/toolbox/default.png'";
	html = '<div class="col-sm-4"><div class="contact-box" style="height: 31%;"><a target="_Blank" href="/toolbox/'
			+ toolBackId
			+ '"><div class="col-sm-4"><div class="text-center"><img alt="image" class="img-circle m-t-xs img-responsive"  onerror="this.src='
			+ defaultImg
			+ '" src="img/toolbox/'
			+ toolBackId
			+ '.png"></div></div><div class="col-sm-8"><h2 style="font-size: 25px"><strong>'
			+ toolName
			+ '</strong></h2><br /><p>'
			+ toolDes
			+ '</p></div><div class="clearfix"></div></a></div></div>'
	// 添加
	document.getElementById('tool').insertAdjacentHTML('beforebegin', html)
}

/* 通用工具管理-新增 */
function add() {
	var url = prefix + '/add';
	layer_showAuto("新增通用工具", url);
}

/* 通用工具管理-修改 */
function edit(toolId) {
	var url = prefix + '/edit/' + toolId;
	layer_showAuto("修改通用工具", url);
}
/* 预览工具 */
function preview(toolBackId, toolName) {
	var url = prefix + '/preview/' + toolBackId;
	createMenuItem(url, "预览工具（" + toolName + "）");
}

// 单条删除
function remove(id) {
	$.modalConfirm("确定要删除选中通用工具吗？", function() {
		_ajax(prefix + "/remove/" + id, "", "post");
	})
}

// 批量删除
function batchRemove() {
	var rows = $.getSelections("toolId");
	if (rows.length == 0) {
		$.modalMsg("请选择要删除的数据", "warning");
		return;
	}
	$.modalConfirm("确认要删除选中的" + rows.length + "条数据吗?", function() {
		_ajax(prefix + '/batchRemove', {
			"ids" : rows
		}, "post");
	});
}
/* 通用工具管理-其他设置 */
function ocrSet() {
	var url = prefix + '/toolset/ocrSet';
	layer_showAuto("OCR设置", url);
}
