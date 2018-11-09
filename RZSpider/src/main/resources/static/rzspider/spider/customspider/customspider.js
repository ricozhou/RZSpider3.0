var prefix = ctx + "spider/customspider"

$(function() {
	var columns = [
			{
				checkbox : true
			},
			{
				field : 'customSpiderId',
				title : '自定义爬虫ID'
			},
			{
				field : 'customSpiderBackId',
				title : '自定义爬虫后台编号ID'
			},
			{
				field : 'customSpiderType',
				title : '自定义爬虫类型',
				align : 'center',
				formatter : function(value, row, index) {
					return '<span>' + row.spiderCodeTypeName + '</span>';
				}
			},
			{
				field : 'spiderDes',
				align : 'center',
				title : '爬虫描述',
				visible : false,
			},
			{
				field : 'spiderVersion',
				align : 'center',
				title : '爬虫版本'
			},
			{
				field : 'customStatus',
				title : '自定义爬虫状态',
				align : 'center',
				formatter : function(value, row, index) {
					if (value == 0) {
						return '<span class="label label-success">正常</span>';
					} else if (value == 1) {
						return '<span class="label label-danger">禁用</span>';
					}
				}
			},
			{
				field : 'runStatus',
				title : '运行状态',
				align : 'center',
				formatter : function(value, row, index) {
					if (value == 0) {
						return '<span class="label label-danger">运行中</span>';
					} else if (value == 1) {
						return '<span class="label label-success">中止中</span>';
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
									+ codeFlag
									+ '" href="#" title="编程" mce_href="#" onclick="code(\''
									+ row.customSpiderId + '\',\''
									+ row.customSpiderBackId
									+ '\')"><i class="fa fa-edit"></i>编程</a> ');
					actions
							.push('<a class="btn btn-success btn-xs '
									+ editFlag
									+ '" href="#" title="编辑" mce_href="#" onclick="edit(\''
									+ row.customSpiderId
									+ '\')"><i class="fa fa-edit"></i>编辑</a> ');
					actions.push('<a class="btn btn-danger btn-xs '
							+ removeFlag
							+ '" href="#" title="删除" onclick="remove(\''
							+ row.customSpiderId
							+ '\')"><i class="fa fa-remove"></i>删除</a>');
					return actions.join('');
				}
			} ];
	var url = prefix + "/list";
	$.initTable(columns, url, '请输入爬虫后台ID');
});

/* 自定义爬虫详情-新增 */
function add() {
	var url = prefix + '/add';
	layer_showAuto2("新增自定义爬虫详情", url);
}

/* 自定义爬虫详情-修改 */
function edit(customSpiderId) {
	var url = prefix + '/edit/' + customSpiderId;
	layer_showAuto2("修改自定义爬虫详情", url);
}

/* 编写代码 */
function code(customSpiderId, customSpiderBackId) {
	// 判断项目文件是否存在
	var data = csisexist(customSpiderId);
	if (data.code != 0) {
		$.modalAlert(data.msg, modal_status.FAIL);
		// $.refreshTable();
		// 关闭标签页
		// closeTab();
		return;
	}
	var url = prefix + '/code/' + customSpiderId;
	createMenuItem(url, "编写代码（" + customSpiderBackId + "）");
}
/* 查看备份代码 */
function backupCode() {
	var url = prefix + '/backupcode';
	createMenuItem(url, "备份代码");
}

// 单条删除
function remove(id) {
	$.modalConfirm("爬虫所有代码将会被删除，确定要删除选中自定义爬虫详情吗？", function() {
		_ajax(prefix + "/remove/" + id, "", "post");
	})
}

// 批量删除
function batchRemove() {
	var rows = $.getSelections("customSpiderId");
	if (rows.length == 0) {
		$.modalMsg("请选择要删除的数据", "warning");
		return;
	}
	$.modalConfirm("爬虫所有代码将会被删除，确认要删除选中的" + rows.length + "条数据吗?", function() {
		_ajax(prefix + '/batchRemove', {
			"ids" : rows
		}, "post");
	});
}
// 判断自定义爬虫文件项目是否存在
function csisexist(customSpiderId) {
	var data2;
	$.ajax({
		cache : true,
		type : "POST",
		url : prefix + "/checkCSFileExist",
		data : {
			"customSpiderId" : customSpiderId
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
