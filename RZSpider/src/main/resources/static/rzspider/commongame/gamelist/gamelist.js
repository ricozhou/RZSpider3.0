var prefix = ctx + "commongame/gamelist"

$(function() {
	var columns = [
			{
				checkbox : true
			},
			{
				field : 'gameListId',
				title : '游戏id'
			},
			{
				field : 'gameNickName',
				title : '游戏昵称'
			},
			{
				field : 'gameName',
				title : '游戏名称'
			},
			{
				field : 'gameType',
				title : '游戏类型',
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
				field : 'gameInstruction',
				title : '游戏说明',
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
									+ row.gameListId
									+ '\')"><i class="fa fa-edit"></i>编辑</a> ');
					actions.push('<a class="btn btn-warning btn-xs '
							+ removeFlag
							+ '" href="#" title="删除" onclick="remove(\''
							+ row.gameListId
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
									+ row.gameListId
									+ '\')"><i class="fa fa-edit"></i>详情</a> ');
					if (row.gameType == '0') {
						actions.push('<a class="btn btn-warning btn-xs '
								+ runFlag
								+ '" href="#" title="运行" onclick="run(\''
								+ row.gameListId
								+ '\')"><i class="fa fa-play"></i>运行</a>');
					} else if (row.gameType == '1') {
						// actions
						// .push('<span class="btn btn-warning btn-sm '
						// + runFlag
						// + '" href ="#" title="运行"><i class="fa
						// fa-play"></i>运行</span>');
					}
					return actions.join('');
				}
			} ];
	var url = prefix + "/list";
	$.initTable(columns, url);
});

/* 通用游戏列-新增 */
function add() {
	var url = prefix + '/add';
	layer_showAuto("新增通用游戏", url);
}

/* 通用游戏列-修改 */
function edit(gameListId) {
	var url = prefix + '/edit/' + gameListId;
	layer_showAuto("修改通用游戏", url);
}

/* 通用游戏列-详情 */
function detail(gameListId) {
	var url = prefix + '/detail/' + gameListId;
	layer_showAuto("通用游戏详情", url);
}
// 单条删除
function remove(id) {
	$.modalConfirm("确定要删除选中通用游戏吗？", function() {
		_ajax(prefix + "/remove/" + id, "", "post");
	})
}

// 批量删除
function batchRemove() {
	var rows = $.getSelections("gameListId");
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
