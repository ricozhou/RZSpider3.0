var prefix = ctx + "spider/spidermanage"

$(function() {
	var columns = [
			{
				checkbox : true
			},
			{
				field : 'spiderId',
				title : '爬虫编号'
			},
			{
				field : 'spiderBackId',
				title : '爬虫后台编号'
			},
			{
				field : 'spiderName',
				title : '爬虫名称'
			},
			{
				field : 'spiderType',
				title : '爬虫类型'
			},
			{
				field : 'createType',
				title : '创建类型',
				align : 'center',
				formatter : function(value, row, index) {
					if (value == 0) {
						return '<span>内置</span>';
					} else if (value == 1) {
						return '<span>自定义</span>';
					}
				}
			},
			{
				field : 'spiderDes',
				title : '功能描述'

			},
			{
				field : 'spiderLink',
				visible : false,
				title : '网站链接',
				align : 'center',
				formatter : function(value, row, index) {
					var link = value;
					return '<a  href="' + link + '" target="_Blank">' + value
							+ '</a>';
				}
			},
			{
				field : 'status',
				title : '状态',
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
				visible : false,
				field : 'createDateTimeStr',
				title : '创建时间'
			},
			{
				title : '操作',
				align : 'center',
				formatter : function(value, row, index) {
					var actions = [];
					actions
							.push('<a class="btn btn-success btn-xs " href="#" title="参数" mce_href="#" onclick="params(\''
									+ row.spiderId
									+ '\')"><i class="fa fa-edit"></i>参数</a> ');
					actions.push('<a class="btn btn-success btn-xs ' + editFlag
							+ '" href="#" onclick="edit(\'' + row.spiderId
							+ '\')"><i class="fa fa-edit"></i>编辑</a> ');
					actions.push('<a class="btn btn-danger btn-xs '
							+ removeFlag + '" href="#" onclick="remove(\''
							+ row.spiderId
							+ '\')"><i class="fa fa-remove"></i>删除</a>');
					return actions.join('');
				}
			} ];
	var url = prefix + "/list";
	$.initTable(columns, url, '请输入爬虫名称、爬虫类型');
});

/* 爬虫管理-新增 */
function add() {
	var url = prefix + '/add';
	layer_showAuto("新增爬虫", url);
}

/* 爬虫管理-修改 */
function edit(spiderId) {
	// 修改前判断自定义爬虫是否存在，是否可用
	// 判断是否存在
	var data = csisexist(spiderId);
	if (data.code != 0) {
		$.modalAlert(data.msg, modal_status.FAIL);
		$.refreshTable();
		// 关闭标签页
		// closeTab();
		return;
	}

	var url = prefix + '/edit/' + spiderId;
	layer_showAuto("修改爬虫", url);
}

/* 爬虫参数详情 */
function params(spiderId) {
	// 修改前判断自定义爬虫是否存在，是否可用
	// 判断是否存在
	var data = csisexist(spiderId);
	if (data.code != 0) {
		$.modalAlert(data.msg, modal_status.FAIL);
		$.refreshTable();
		// 关闭标签页
		// closeTab();
		return;
	}
	var url = prefix + '/params/' + spiderId;
	layer_showAuto("设置默认爬虫参数", url);
}

// 单条删除
function remove(spiderId) {
	$.modalConfirm("确定要删除选中爬虫吗？", function() {
		_ajax(prefix + "/remove/" + spiderId, "", "post");
	})
}

// 批量删除
function batchRemove() {
	var rows = $.getSelections("spiderId");
	if (rows.length == 0) {
		$.modalMsg("请选择要删除的数据", modal_status.WARNING);
		return;
	}
	$.modalConfirm("确认要删除选中的" + rows.length + "条数据吗?", function() {
		_ajax(prefix + '/batchRemove', {
			"ids" : rows
		}, "post");
	});
}
// 判断自定义爬虫是否存在
function csisexist(spiderId) {
	var data2;
	$.ajax({
		cache : true,
		type : "POST",
		url : prefix + "/checkSpiderExist",
		data : {
			"spiderId" : spiderId
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
