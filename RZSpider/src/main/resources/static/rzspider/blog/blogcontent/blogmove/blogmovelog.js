var prefix = ctx + "blog/blogcontent/blogmove"

$(function() {
	var columns = [
			{
				checkbox : true
			},
			{
				field : 'blogMoveId',
				align : 'center',
				title : 'ID'
			},
			{
				field : 'moveMode',
				align : 'center',
				title : '运行模式',
				formatter : function(value, row, index) {
					if (value == '0') {
						return '<span>列表文章</span>';
					} else if (value == '1') {
						return '<span>单篇文章</span>';
					} else if (value == '2') {
						return '<span>本地WORD文档</span>';
					} else if (value == '9') {
						return '<span>其他</span>';
					}
				}
			},
			{
				field : 'moveWebsiteId',
				align : 'center',
				title : '网站'
			},
			{
				field : 'moveNum',
				align : 'center',
				visible : false,
				title : '爬取条数'
			},
			{
				field : 'moveArticleEditor',
				align : 'center',
				visible : false,
				title : '默认编辑器',
				formatter : function(value, row, index) {
					if (value == '0') {
						return '<span>HTML编辑器（summernote）</span>';
					} else if (value == '1') {
						return '<span>MarkDown编辑器（simplemde）</span>';
					}
				}
			},
			{
				field : 'moveColumn',
				align : 'center',
				visible : false,
				title : '默认专栏'
			},
			{
				field : 'moveBlogStatus',
				align : 'center',
				title : '发布',
				formatter : function(value, row, index) {
					if (value == '1') {
						return '<span class="label label-primary">是</span>';
					} else if (value == '0') {
						return '<span class="label label-danger">否</span>';
					}
				}
			},
			{
				field : 'moveUseOriginalTime',
				align : 'center',
				visible : false,
				title : '使用原时间',
				formatter : function(value, row, index) {
					if (value == '0') {
						return '<span class="label label-primary">是</span>';
					} else if (value == '1') {
						return '<span class="label label-danger">否</span>';
					}
				}
			},
			{
				field : 'moveSaveImg',
				align : 'center',
				visible : false,
				title : '保存图片',
				formatter : function(value, row, index) {
					if (value == '0') {
						return '<span class="label label-primary">是</span>';
					} else if (value == '1') {
						return '<span class="label label-danger">否</span>';
					}
				}
			},
			{
				field : 'moveRemoveRepeat',
				align : 'center',
				visible : false,
				title : '去除重复',
				formatter : function(value, row, index) {
					if (value == '0') {
						return '<span class="label label-primary">是</span>';
					} else if (value == '1') {
						return '<span class="label label-danger">否</span>';
					}
				}
			},
			{
				field : 'moveMessage',
				align : 'center',
				title : '日志详情',
				formatter : function(value, row, index) {
					var actions = [];
					actions
							.push('<a class="btn btn-primary btn-xs" href="#" title="查看" onclick="viewMoveLog(\''
									+ row.blogMoveId
									+ '\')"><i class="fa fa-play"></i>查看</a>');
					return actions.join('');
				}
			},
			{
				field : 'moveStopMode',
				align : 'center',
				title : '中止方式',
				formatter : function(value, row, index) {
					if (value == '0') {
						return '<span class="label label-primary">自然</span>';
					} else if (value == '1') {
						return '<span class="label label-danger">强制</span>';
					}
				}
			},
			{
				field : 'moveSuccess',
				align : 'center',
				title : '成功',
				formatter : function(value, row, index) {
					if (value == '0') {
						return '<span class="label label-primary">是</span>';
					} else if (value == '1') {
						return '<span class="label label-danger">否</span>';
					}
				}
			},
			{
				field : 'moveSuccessNum',
				align : 'center',
				title : '搬家条数'
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
				field : 'finishTime',
				align : 'center',
				visible : false,
				title : '结束时间'
			},
			{
				field : 'takeTime',
				align : 'center',
				title : '消耗时间'
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
					actions.push('<a class="btn btn-warning btn-xs  '
							+ removeFlag
							+ '" href="#" title="删除" onclick="remove(\''
							+ row.blogMoveId
							+ '\')"><i class="fa fa-remove"></i>删除</a>');
					return actions.join('');
				}
			} ];
	var url = prefix + "/list";
	$.initTable1(columns, url);
});

// 博客搬家日志详情-查看
function viewMoveLog(blogMoveId) {
	var url = prefix + '/viewMoveLog/' + blogMoveId;
	layer_showAuto("博客搬家日志详情", url);
}

// 单条删除
function remove(id) {
	$.modalConfirm("确定要删除选中博客搬家日志吗？", function() {
		_ajax(prefix + "/remove/" + id, "", "post");
	})
}

// 批量删除
function batchRemove() {
	var rows = $.getSelections("blogMoveId");
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
