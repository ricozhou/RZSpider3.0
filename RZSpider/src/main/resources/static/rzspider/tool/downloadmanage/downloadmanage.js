var prefix = ctx + "tool/downloadmanage"

$(function() {
	var columns = [
			{
				checkbox : true
			},
			{
				field : 'downloadmanageId',
				align : 'center',
				title : 'ID'
			},
			{
				field : 'downloadFileName',
				align : 'center',
				title : '文件名'
			},
			{
				field : 'downloadFileUuidName',
				title : '文件uuid名',
				align : 'center',
				visible : false,
			},
			{
				field : 'downloadFileType',
				align : 'center',
				title : '文件类型',
				formatter : function(value, row, index) {
					if (value == 0) {
						return '<span class="label">文件</span>';
					} else if (value == 1) {
						return '<span class="label">图片</span>';
					} else if (value == 2) {
						return '<span class="label">视频</span>';
					} else if (value == 3) {
						return '<span class="label">音频</span>';
					} else if (value == 4) {
						return '<span class="label">其他</span>';
					}
				}
			},
			{
				field : 'downloadFileExtensionName',
				title : '文件后缀',
				align : 'center',
				visible : false,
			},
			{
				field : 'downloadFileSize',
				align : 'center',
				title : '文件大小'
			},
			{
				field : 'downloadFileUrl',
				align : 'center',
				title : '下载外链',
				width : 320,
				formatter : function(value, row, index) {
					return '<a  href="' + value + '" target="_Blank">' + value
							+ '</a>';
				}
			},
			{
				field : 'downloadFileLimitDownNum',
				align : 'center',
				title : '限制下载次数',
				visible : false,
			},
			{
				field : 'downloadFileLimitDownTime',
				align : 'center',
				title : '限制下载时间',
				visible : false,
			},
			{
				field : 'downloadFileDownNum',
				align : 'center',
				title : '已下载次数'
			},
			{
				field : 'status',
				title : '可用状态',
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
				field : 'useStatus',
				title : '下载状态',
				align : 'center',
				formatter : function(value, row, index) {
					if (value == 0) {
						return '<span class="label label-danger">下载中</span>';
					} else if (value == 1) {
						return '<span class="label label-success">无下载</span>';
					}
				}
			},
			{
				field : 'createBy',
				title : '创建者',
				align : 'center',
				visible : false
			},
			{
				field : 'createTime',
				title : '创建时间',
				align : 'center',
				visible : false
			},
			{
				field : 'remark',
				title : '备注',
				align : 'center',
				visible : false
			},
			{
				title : '操作',
				align : 'center',
				formatter : function(value, row, index) {
					var actions = [];
					actions
							.push('<a class="btn btn-primary btn-xs '
									+ editFlag
									+ '" href="#" title="编辑" mce_href="#" onclick="edit(\''
									+ row.downloadmanageId
									+ '\')"><i class="fa fa-edit"></i>编辑</a> ');
					actions.push('<a class="btn btn-warning btn-xs '
							+ removeFlag
							+ '" href="#" title="删除" onclick="remove(\''
							+ row.downloadmanageId
							+ '\')"><i class="fa fa-remove"></i>删除</a>');
					return actions.join('');
				}
			} ];
	var url = prefix + "/list";
	$.initTable(columns, url, '请输入文件名');
});

/* 下载管理详情-新增 */
function add() {
	var url = prefix + '/add';
	layer_showAuto("新增下载文件", url);
}

/* 下载管理详情-修改 */
function edit(downloadmanageId) {
	var url = prefix + '/edit/' + downloadmanageId;
	layer_showAuto("修改下载文件", url);
}

// 单条删除
function remove(id) {
	$.modalConfirm("确定要删除选中下载文件吗？", function() {
		_ajax(prefix + "/remove/" + id, "", "post");
	})
}

// 批量删除
function batchRemove() {
	var rows = $.getSelections("downloadmanageId");
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
