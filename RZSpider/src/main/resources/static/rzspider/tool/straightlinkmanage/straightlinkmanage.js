var prefix = ctx + "tool/straightlinkmanage"

$(function() {
	var columns = [
			{
				checkbox : true
			},
			{
				field : 'straightlinkmanageId',
				align : 'center',
				title : 'ID'
			},
			{
				field : 'straightlinkFileName',
				align : 'center',
				title : '文件名'
			},
			{
				field : 'straightlinkFileUuidName',
				align : 'center',
				title : '文件uuid名',
				visible : false
			},
			{
				field : 'straightlinkFileType',
				align : 'center',
				title : '文件类型',
				formatter : function(value, row, index) {
					if (value == 0) {
						return '<span class="label">压缩文件</span>';
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
				field : 'straightlinkFileExtensionName',
				align : 'center',
				title : '文件后缀',
				visible : false
			},
			{
				field : 'straightlinkFileSize',
				align : 'center',
				title : '文件大小'
			},
			{
				field : 'straightlinkFileUrl',
				align : 'center',
				width : 320,
				title : '浏览直链',
				formatter : function(value, row, index) {
					return '<a  href="' + value + '" target="_Blank">' + value
							+ '</a>';
				}
			},
			{
				field : 'straightlinkFileDownNum',
				align : 'center',
				title : '浏览次数'
			},
			{
				field : 'status',
				align : 'center',
				title : '可用状态',
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
				align : 'center',
				title : '浏览状态',
				formatter : function(value, row, index) {
					if (value == 0) {
						return '<span class="label label-danger">浏览中</span>';
					} else if (value == 1) {
						return '<span class="label label-success">无浏览</span>';
					}
				}
			},
			{
				field : 'createBy',
				align : 'center',
				title : '创建者',
				visible : false
			},
			{
				field : 'createTime',
				align : 'center',
				title : '创建时间',
				visible : false
			},
			{
				field : 'remark',
				align : 'center',
				title : '备注',
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
									+ row.straightlinkmanageId
									+ '\')"><i class="fa fa-edit"></i>编辑</a> ');
					actions.push('<a class="btn btn-warning btn-xs '
							+ removeFlag
							+ '" href="#" title="删除" onclick="remove(\''
							+ row.straightlinkmanageId
							+ '\')"><i class="fa fa-remove"></i>删除</a>');
					return actions.join('');
				}
			} ];
	var url = prefix + "/list";
	$.initTable(columns, url, '请输入文件名');
});

/* 直链管理详情-新增 */
function add() {
	var url = prefix + '/add';
	layer_showAuto("新增直链", url);
}

/* 直链管理详情-修改 */
function edit(straightlinkmanageId) {
	var url = prefix + '/edit/' + straightlinkmanageId;
	layer_showAuto("修改直链", url);
}

// 单条删除
function remove(id) {
	$.modalConfirm("确定要删除选中直链吗？", function() {
		_ajax(prefix + "/remove/" + id, "", "post");
	})
}

// 批量删除
function batchRemove() {
	var rows = $.getSelections("straightlinkmanageId");
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
