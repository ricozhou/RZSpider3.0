var prefix = ctx + "spider/codeType"

$(function() {
	var columns = [
			{
				checkbox : true
			},
			{
				field : 'spiderCodeTypeId',
				title : '爬虫代码类型ID'
			},
			{
				field : 'customSpiderType',
				title : '爬虫代码类型标志ＩＤ',
				align : 'center',
				formatter : function(value, row, index) {
					if (value == 0) {
						return '<span>JAVA（0）</span>';
					} else if (value == 1) {
						return '<span>PYTHON（1）</span>';
					} else if (value == 2) {
						return '<span>JAVASCRIPT（2）</span>';
					} else if (value == 3) {
						return '<span>JAR包（3）</span>';
					} else if (value == 4) {
						return '<span>其他（4）</span>';
					}
				}
			},
			{
				field : 'spiderCodeTypeName',
				title : '爬虫代码类型名称'
			},
			{
				field : 'spiderCodeTypeFolder',
				title : '爬虫代码类型对应目录'
			},
			{
				field : 'status',
				title : '爬虫代码类型状态',
				align : 'center',
				formatter : function(value, row, index) {
					if (value == 0) {
						return '<span class="label label-success">可用</span>';
					} else if (value == 1) {
						return '<span class="label label-danger">禁用</span>';
					}
				}
			},
			{
				title : '操作',
				align : 'center',
				formatter : function(value, row, index) {
					var actions = [];
					actions
							.push('<a class="btn btn-success btn-xs '
									+ editFlag
									+ '" href="#" title="编辑" mce_href="#" onclick="edit(\''
									+ row.spiderCodeTypeId
									+ '\')"><i class="fa fa-edit"></i>编辑</a> ');
					actions.push('<a class="btn btn-warning btn-xs '
							+ removeFlag
							+ '" href="#" title="删除" onclick="remove(\''
							+ row.spiderCodeTypeId
							+ '\')"><i class="fa fa-remove"></i>删除</a>');
					return actions.join('');
				}
			} ];
	var url = prefix + "/list";
	$.initTable(columns, url,'请输入爬虫类型名称');
});

/* 爬虫代码类型详情-新增 */
function add() {
	var url = prefix + '/add';
	layer_showAuto("新增爬虫代码类型详情", url);
}

/* 爬虫代码类型详情-修改 */
function edit(spiderCodeTypeId) {
	var url = prefix + '/edit/' + spiderCodeTypeId;
	layer_showAuto("修改爬虫代码类型详情", url);
}

// 单条删除
function remove(id) {
	$.modalConfirm("确定要删除选中爬虫代码类型详情吗？", function() {
		_ajax(prefix + "/remove/" + id, "", "post");
	})
}

// 批量删除
function batchRemove() {
	var rows = $.getSelections("spiderCodeTypeId");
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
