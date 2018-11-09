var prefix = ctx + "spider/customspider/backupcode";

$(function() {
	var columns = [
			{
				checkbox : true
			},
			{
				field : 'spiderCustomspiderBackupcodeId',
				align : 'center',
				sortable : true,
				title : 'ID'
			},
			{
				field : 'customSpiderBackId',
				align : 'center',
				sortable : true,
				title : '爬虫后台ID'
			},
			{
				field : 'customSpiderType',
				title : '爬虫类型',
				align : 'center',
				formatter : function(value, row, index) {
					return '<span>' + row.spiderCodeTypeName + '</span>';
				}
			},
			{
				field : 'backupcodeType',
				title : '备份类型',
				sortable : true,
				align : 'center',
				formatter : function(value, row, index) {
					if (value == 0) {
						return '<span class="label label-success">自动</span>';
					} else if (value == 1) {
						return '<span class="label label-danger">手动</span>';
					}
				}
			},
			{
				field : 'spiderVersion',
				visible : false,
				sortable : true,
				align : 'center',
				title : '爬虫版本'
			},
			{
				field : 'spiderFileName',
				align : 'center',
				title : '代码文件名'
			},
			{
				field : 'spiderFilePath',
				visible : false,
				align : 'center',
				title : '代码路径'
			},
			{
				field : 'spiderFileCodeContent',
				title : '代码内容',
				align : 'center',
				formatter : function(value, row, index) {
					var actions = [];
					actions.push('<a class="btn btn-primary btn-xs'
							+ codeContentFlag
							+ '" href="#" title="查看" onclick="codeContent(\''
							+ row.spiderCustomspiderBackupcodeId
							+ '\')"><i class="fa fa-search"></i>查看</a>');
					return actions.join('');
				}
			},
			{
				field : 'createTime',
				align : 'center',
				sortable : true,
				title : '备份时间'
			},
			{
				title : '操作',
				align : 'center',
				formatter : function(value, row, index) {
					var actions = [];
					actions.push('<a class="btn btn-success btn-xs '
							+ exportCodeFlag
							+ '" href="#" title="导出" onclick="exportCode(\''
							+ row.spiderCustomspiderBackupcodeId
							+ '\')"><i class="fa fa-download"></i>导出</a> ');
					actions.push('<a class="btn btn-warning btn-xs '
							+ removeFlag
							+ '" href="#" title="删除" onclick="remove(\''
							+ row.spiderCustomspiderBackupcodeId
							+ '\')"><i class="fa fa-remove"></i>删除</a>');
					return actions.join('');
				}
			} ];
	var url = prefix + "/list";
	$.initTable6(columns, url, '请输入爬虫后台ID、代码类型、代码文件名');
});

// 单条删除
function remove(id) {
	$.modalConfirm("确定要删除选中自定义爬虫代码备份吗？", function() {
		_ajax(prefix + "/remove/" + id, "", "post");
	})
}

// 批量删除
function batchRemove() {
	var rows = $.getSelections("spiderCustomspiderBackupcodeId");
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

/* 爬虫备份查看 */
function codeContent(spiderCustomspiderBackupcodeId) {
	var url = prefix + '/codeContent/' + spiderCustomspiderBackupcodeId;
	layer_showAuto("备份代码查看", url);
}
/* 爬虫备份导出 */
function exportCode(spiderCustomspiderBackupcodeId) {
	location.href = prefix + "/exportCode/" + spiderCustomspiderBackupcodeId;
	layer.msg('正在下载,请稍后…', {
		icon : 1
	});
}