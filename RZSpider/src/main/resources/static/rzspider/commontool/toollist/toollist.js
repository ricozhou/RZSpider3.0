var prefix = ctx + "commontool/toollist"

$(function() {
	var columns = [
			{
				checkbox : true
			},
			{
				field : 'toolListId',
				title : '工具id'
			},
			{
				field : 'toolNickName',
				title : '工具昵称'
			},
			{
				field : 'toolName',
				title : '工具名称'
			},
			{
				field : 'toolType',
				title : '工具类型',
				align : 'center',
				formatter : function(value, row, index) {
					if (value == '0') {
						return '<span>网页版</span>';
					} else if (value == '1') {
						return '<span>客户端版</span>';
					}
				}
			},
			{
				field : 'toolInstruction',
				title : '工具说明',
				width : 400
			},
			{
				title : '基本操作',
				align : 'center',
				formatter : function(value, row, index) {
					var actions = [];
					actions
							.push('<a class="btn btn-success btn-xs '
									+ editFlag
									+ '" href="#" title="编辑" mce_href="#" onclick="edit(\''
									+ row.toolListId
									+ '\')"><i class="fa fa-edit"></i>编辑</a> ');
					actions.push('<a class="btn btn-warning btn-xs '
							+ removeFlag
							+ '" href="#" title="删除" onclick="remove(\''
							+ row.toolListId
							+ '\')"><i class="fa fa-remove"></i>删除</a>');
					return actions.join('');
				}
			},
			{
				title : '详情操作',
				align : 'center',
				formatter : function(value, row, index) {
					var actions = [];
					actions
							.push('<a class="btn btn-primary btn-xs " href="#" title="详情" mce_href="#" onclick="detail(\''
									+ row.toolListId
									+ '\')"><i class="fa fa-edit"></i>详情</a> ');
					// 只有网页版标志才可直接运行
					if (row.toolType == '0') {
						actions.push('<a class="btn btn-warning btn-xs '
								+ runFlag
								+ '" href="#" title="运行" onclick="run(\''
								+ row.toolBackId + '\',\'' + row.toolNickName
								+ '\')"><i class="fa fa-play"></i>运行</a>');
					}
					return actions.join('');
				}
			} ];
	var url = prefix + "/list";
	$.initTable(columns, url,'请输入工具昵称、工具名称');
});

/* 通用工具列-新增 */
function add() {
	var url = prefix + '/add';
	layer_showAuto("新增通用工具", url);
}

/* 通用工具列-修改 */
function edit(toolListId) {
	var url = prefix + '/edit/' + toolListId;
	layer_showAuto("修改通用工具", url);
}

/* 通用工具列-详情 */
function detail(toolListId) {
	var url = prefix + '/detail/' + toolListId;
	layer_showAuto("通用工具详情", url);
}
// 单条删除
function remove(id) {
	$.modalConfirm("确定要删除选中通用工具吗？", function() {
		_ajax(prefix + "/remove/" + id, "", "post");
	})
}

// 批量删除
function batchRemove() {
	var rows = $.getSelections("toolListId");
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
/* 工具-启用 */
function run(toolBackId, toolNickName) {
	var url = prefix + '/startTool/' + toolBackId;
	createMenuItem(url, "启动工具（" + toolNickName + "）");
}