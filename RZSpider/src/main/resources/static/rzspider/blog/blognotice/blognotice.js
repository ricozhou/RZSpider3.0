var prefix = ctx + "blog/blognotice"

$(function() {
	var columns = [
			{
				checkbox : true
			},
			{
				field : 'blogNoticeId',
				align : 'center',
				title : 'ID'
			},
			{
				field : 'noticeTitle',
				align : 'center',
				title : '标题'
			},
			{
				field : 'noticeContent',
				align : 'center',
				title : '内容'
			},
			{
				field : 'status',
				align : 'center',
				title : '状态',
				formatter : function(value, row, index) {
					if (value == '0') {
						return '<span class="label label-primary">发布</span>';
					} else if (value == '1') {
						return '<span class="label label-danger">草稿</span>';
					}
				}
			},
			{
				field : 'createBy',
				align : 'center',
				visible : false,
				title : '创建者'
			},
			{
				field : 'createTime',
				align : 'center',
				title : '创建时间'
			},
			{
				field : 'remark',
				visible : false,
				align : 'center',
				title : '备注'
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
									+ row.blogNoticeId
									+ '\')"><i class="fa fa-edit"></i>编辑</a> ');
					actions.push('<a class="btn btn-warning btn-xs '
							+ removeFlag
							+ '" href="#" title="删除" onclick="remove(\''
							+ row.blogNoticeId
							+ '\')"><i class="fa fa-remove"></i>删除</a>');
					return actions.join('');
				}
			} ];
	var url = prefix + "/list";
	$.initTable(columns, url, '请输入公告标题');
});

/* 博客公告-新增 */
function add() {
	var url = prefix + '/add';
	layer_showAuto("新增博客公告", url);
}

/* 博客公告-修改 */
function edit(blogNoticeId) {
	var url = prefix + '/edit/' + blogNoticeId;
	layer_showAuto("修改博客公告", url);
}

// 单条删除
function remove(id) {
	$.modalConfirm("确定要删除选中博客公告吗？", function() {
		_ajax(prefix + "/remove/" + id, "", "post");
	})
}

// 批量删除
function batchRemove() {
	var rows = $.getSelections("blogNoticeId");
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
