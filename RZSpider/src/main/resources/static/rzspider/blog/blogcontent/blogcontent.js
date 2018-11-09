var prefix = ctx + "blog/blogcontent";

$(function() {
	var columns = [
			{
				checkbox : true
			},
			{
				field : 'cid',
				align : 'center',
				sortable : true,
				title : '文章ID'
			},
			{
				field : 'title',
				title : '标题',
				align : 'center',
				width : 320,
				formatter : function(value, row, index) {
					if (row.status == 1) {
						return '<a href="#" onclick="preview(\'' + row.cid
								+ '\',\'' + row.showId + '\')">' + row.title
								+ '</a>';
					} else {
						return '<span>' + row.title + '</span>';
					}
				}

			},
			{
				field : 'introduction',
				title : '简介',
				align : 'center',
				visible : false
			},
			{
				field : 'articleTop',
				title : '置顶',
				align : 'center',
				formatter : function(value, row, index) {
					if (value == '0') {
						return '<a class="btn btn-danger btn-xs btn-top'
								+ editFlag
								+ '" href="#" title="取消" mce_href="#" onclick="articleTop(1,'
								+ row.cid
								+ ')"><i class="fa fa-arrow-circle-down"></i>取消</a>';
					} else {
						return '<a class="btn btn-success btn-xs btn-top'
								+ editFlag
								+ '" href="#" title="置顶" mce_href="#" onclick="articleTop(0,'
								+ row.cid
								+ ')"><i class="fa fa-arrow-circle-up"></i>置顶</a>';
					}
				}
			},
			{
				field : 'type',
				title : '类型',
				align : 'center',
				formatter : function(value, row, index) {
					if (value == '原创') {
						return '<span class="label label-primary">' + value
								+ '</span>';
					} else if (value == '转载') {
						return '<span class="label label-danger">' + value
								+ '</span>';
					} else if (value == '翻译') {
						return '<span class="label label-success">' + value
								+ '</span>';
					}
				}
			},
			{
				field : 'blogColumnName',
				align : 'center',
				title : '专栏'
			},
			{
				field : 'browseNum',
				align : 'center',
				sortable : true,
				title : '浏览量'
			},
			{
				visible : false,
				field : 'commentsNum',
				align : 'center',
				sortable : true,
				title : '评论数量'
			},
			{
				visible : false,
				field : 'likeNum',
				align : 'center',
				sortable : true,
				title : '点赞数量'
			},
			{
				field : 'articleSource',
				visible : false,
				title : '文章来源',
				align : 'center'
			},
			{
				field : 'allowComment',
				title : '开启评论',
				align : 'center',
				formatter : function(value, row, index) {
					if (value == '0') {
						return '<span class="label label-primary">是</span>';
					} else if (value == '1') {
						return '<span class="label label-danger">否</span>';
					}
				}
			},
			{
				field : 'allowPing',
				title : '允许转载',
				align : 'center',
				formatter : function(value, row, index) {
					if (value == '0') {
						return '<span class="label label-primary">是</span>';
					} else if (value == '1') {
						return '<span class="label label-danger">否</span>';
					}
				}
			},
			{
				field : 'showIntroduction',
				title : '开启简介',
				visible : false,
				align : 'center',
				formatter : function(value, row, index) {
					if (value == '0') {
						return '<span class="label label-primary">是</span>';
					} else if (value == '1') {
						return '<span class="label label-danger">否</span>';
					}
				}
			},
			{
				field : 'privateArticle',
				title : '私密文章',
				align : 'center',
				visible : false,
				formatter : function(value, row, index) {
					if (value == '0') {
						return '<span class="label label-primary">是</span>';
					} else if (value == '1') {
						return '<span class="label label-danger">否</span>';
					}
				}
			},
			{
				field : 'status',
				title : '状态',
				align : 'center',
				formatter : function(value, row, index) {
					if (value == '0') {
						return '<span class="label label-danger">草稿</span>';
					} else if (value == '1') {
						return '<span class="label label-primary">发布</span>';
					}
				}
			},
			{
				field : 'author',
				title : '作者'
			},
			{
				field : 'gtmCreate',
				align : 'center',
				visible : false,
				sortable : true,
				title : '创建时间'
			},
			{
				field : 'gtmModified',
				align : 'center',
				sortable : true,
				title : '修改时间'
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
									+ row.cid
									+ '\')"><i class="fa fa-edit"></i>编辑</a> ');
					if (row.cid != 1 && row.cid != 2) {
						// 1和2分别是关于我和关于本站，无法删除，但是可以保存为草稿隐藏
						actions.push('<a class="btn btn-warning btn-xs '
								+ removeFlag
								+ '" href="#" title="删除" onclick="remove(\''
								+ row.cid
								+ '\')"><i class="fa fa-remove"></i>删除</a> ');
					}

					if (row.status == 1) {
						// 发布才能预览
						actions
								.push('<a class="btn btn-success btn-xs " href="#" title="预览" onclick="preview(\''
										+ row.cid
										+ '\',\''
										+ row.showId
										+ '\')"><i class="fa fa-rocket"></i>预览</a>');
					}

					return actions.join('');
				}
			} ];
	var url = prefix + "/list";
	$.initTable7(columns, url, '请输入标题');
});

/* 文章内容-新增 */
function add() {
	var url = prefix + '/add';
	layer_showAutoAll("新增文章内容", url);
}

/* 文章内容-修改 */
function edit(cid) {
	var url = prefix + '/edit/' + cid;
	layer_showAutoAll("修改文章内容", url);
}

/* 文章推荐-设置 */
function recommendSet() {
	var url = prefix + '/recommendSet';
	layer_showAuto("推荐设置", url);
}
/* 博客搬家 */
function blogMove() {
	var url = prefix + '/blogMove';
	// layer_showAuto("博客搬家", url);
	createMenuItem(url, "博客搬家");
}

// 单条删除
function remove(id) {
	$.modalConfirm("确定要删除选中文章内容吗？", function() {
		_ajax(prefix + "/remove/" + id, "", "post");
	})
}

// 批量删除
function batchRemove() {
	var rows = $.getSelections("cid");
	if (rows.length == 0) {
		$.modalMsg("请选择要删除的数据", "warning");
		return;
	}
	for (var i = 0; i < rows.length; i++) {
		if (rows[i] == 1 || rows[i] == 2) {
			$.modalMsg("不允许删除关于我和关于本站！", "warning");
			return;
		}
	}
	$.modalConfirm("确认要删除选中的" + rows.length + "条数据吗?", function() {
		_ajax(prefix + '/batchRemove', {
			"ids" : rows
		}, "post");
	});
}

// 预览
function preview(id, showId) {
	window.open("/rzblog/blogcontent/details/" + showId);
}

// 批量发布
function batchRelease(status) {
	var msg = '发布';
	if (status == 0) {
		msg = '保存为草稿'
	}
	var rows = $.getSelections("cid");
	if (rows.length == 0) {
		$.modalMsg("请选择要" + msg + "的文章", "warning");
		return;
	}
	$.modalConfirm("确认要将选中的" + rows.length + "条文章" + msg + "吗?", function() {
		_ajax(prefix + '/batchRelease', {
			"status" : status,
			"ids" : rows
		}, "post");
	});
}
// 置顶
function articleTop(articleTop, cid) {
	$.modalConfirm("确认要置顶此文章吗?", function() {
		_ajax(prefix + '/articleTop', {
			"cid" : cid,
			"articleTop" : articleTop
		}, "post");
	});
	// $.ajax({
	// cache : true,
	// type : "POST",
	// url : ctx + "blog/blogcontent/articleTop",
	// data : {
	// "cid" : cid,
	// "articleTop" : articleTop
	// },
	// async : false,
	// error : function(request) {
	// $.modalAlert("系统错误", modal_status.FAIL);
	// },
	// success : function(data) {
	// if (data.code == 0) {
	// layer.msg("修改成功,正在刷新数据请稍后……", {
	// icon : 1,
	// time : 500,
	// shade : [ 0.1, '#fff' ]
	// }, function() {
	// $.refreshTable();
	// });
	// } else {
	// $.modalAlert(data.msg, modal_status.FAIL);
	// }
	// }
	// });
}