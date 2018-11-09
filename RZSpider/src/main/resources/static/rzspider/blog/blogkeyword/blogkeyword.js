var prefix = ctx + "blog/blogkeyword"

$(function() {
	var columns = [
			{
				checkbox : true
			},
			{
				field : 'blogKeywordId',
				align : 'center',
				title : 'ID'
			},
			{
				field : 'keywordName',
				align : 'center',
				title : '关键词'
			},
			{
				field : 'keywordCommentBan',
				align : 'center',
				title : '评论禁止',
				formatter : function(value, row, index) {
					if (value == '0') {
						return '<span class="label label-primary">是</span>';
					} else if (value == '1') {
						return '<span class="label label-danger">否</span>';
					}
				}
			},
			{
				field : 'keywordMessageBan',
				align : 'center',
				title : '留言禁止',
				formatter : function(value, row, index) {
					if (value == '0') {
						return '<span class="label label-primary">是</span>';
					} else if (value == '1') {
						return '<span class="label label-danger">否</span>';
					}
				}
			},
			{
				field : 'keywordSuggestionBan',
				align : 'center',
				title : '建议禁止',
				formatter : function(value, row, index) {
					if (value == '0') {
						return '<span class="label label-primary">是</span>';
					} else if (value == '1') {
						return '<span class="label label-danger">否</span>';
					}
				}
			},
			{
				field : 'keywordReplace',
				align : 'center',
				title : '是否替换',
				formatter : function(value, row, index) {
					if (value == '0') {
						return '<span class="label label-primary">是</span>';
					} else if (value == '1') {
						return '<span class="label label-danger">否</span>';
					}
				}
			},
			{
				field : 'keywordReplaceWordName',
				align : 'center',
				title : '替换词'
			},
			{
				field : 'status',
				align : 'center',
				title : '状态',
				formatter : function(value, row, index) {
					if (value == '0') {
						return '<span class="label label-primary">是</span>';
					} else if (value == '1') {
						return '<span class="label label-danger">否</span>';
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
				visible : false,
				title : '创建时间'
			},
			{
				field : 'updateBy',
				align : 'center',
				visible : false,
				title : '更新者'
			},
			{
				field : 'updateTime',
				align : 'center',
				visible : false,
				title : '更新时间'
			},
			{
				field : 'remark',
				align : 'center',
				visible : false,
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
									+ row.blogKeywordId
									+ '\')"><i class="fa fa-edit"></i>编辑</a> ');
					actions.push('<a class="btn btn-warning btn-xs '
							+ removeFlag
							+ '" href="#" title="删除" onclick="remove(\''
							+ row.blogKeywordId
							+ '\')"><i class="fa fa-remove"></i>删除</a>');
					return actions.join('');
				}
			} ];
	var url = prefix + "/list";
	$.initTable(columns, url, '请输入关键词、替换词');
});

/* 关键词管理-新增 */
function add() {
	var url = prefix + '/add';
	layer_showAuto("新增关键词", url);
}

/* 关键词管理-修改 */
function edit(blogKeywordId) {
	var url = prefix + '/edit/' + blogKeywordId;
	layer_showAuto("修改关键词", url);
}

// 单条删除
function remove(id) {
	$.modalConfirm("确定要删除选中关键词吗？", function() {
		_ajax(prefix + "/remove/" + id, "", "post");
	})
}

// 批量删除
function batchRemove() {
	var rows = $.getSelections("blogKeywordId");
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
