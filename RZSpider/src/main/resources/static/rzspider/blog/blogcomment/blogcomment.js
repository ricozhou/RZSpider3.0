var prefix = ctx + "blog/blogcomment"
window.onload = function() {
	loading();
};
function loading() {
	var columns = [
			{
				field : 'replyName',
				align : 'center',
				title : '评论人',
				formatter : function(row, index) {
					return '<span class="nav-label">' + row.replyName
							+ '</span>';
				}
			},
			{
				field : 'showId',
				align : 'center',
				title : '文章',
				width : '18%',
				formatter : function(row, index) {
					var actions = [];
					// 发布才能预览
					actions.push('<a  href="#" title="' + row.title
							+ '" onclick="preview(\'' + row.showId + '\')">'
							+ row.title + '</a>');

					return actions.join('');
				}

			},
			// {
			// field : 'replyHeadImg',
			// align : 'center',
			// title : '头像',
			// width : '10%',
			// formatter : function(row, index) {
			// var link = row.replyHeadImg;
			// if (link == null || link == '') {
			// return '';
			// }
			// return '<a href="'
			// + link
			// + '" target="_Blank"><img width="30px" height="30px" src="'
			// + link + '" ></img></a>';
			// }
			// },
			{
				field : 'replyContent',
				align : 'center',
				width : '30%',
				title : '内容'
			},
			{
				field : 'replyTime',
				align : 'center',
				width : '14%',
				title : '评论时间'
			},
			{
				field : 'status',
				align : 'center',
				title : '状态',
				width : '7%',
				formatter : function(row, index) {
					if (row.status == 0) {
						return '<span class="label label-success">已通过</span>';
					} else if (row.status == 1) {
						return '<span class="label label-primary">正在审核</span>';
					} else if (row.status == 2) {
						return '<span class="label label-danger">未通过</span>';
					}
				}
			},
			{
				title : '操作',
				align : 'center',
				width : '15%',
				formatter : function(row, index) {
					var actions = [];
					actions
							.push('<a class="btn btn-success btn-xs '
									+ editFlag
									+ '" href="#" title="详情" mce_href="#" onclick="detail(\''
									+ row.blogCommentId
									+ '\')"><i class="fa fa-rocket"></i>详情</a> ');
					actions
							.push('<a class="btn btn-primary btn-xs '
									+ editFlag
									+ '" href="#" title="审核" mce_href="#" onclick="edit(\''
									+ row.blogCommentId
									+ '\')"><i class="fa fa-edit"></i>审核</a> ');
					actions.push('<a class="btn btn-warning btn-xs '
							+ removeFlag
							+ '" href="#" title="删除" onclick="remove(\''
							+ row.blogCommentId
							+ '\')"><i class="fa fa-remove"></i>删除</a>');
					return actions.join('');
				}
			} ];
	var url = prefix + "/list";
	$.initTreeTable('blogCommentId', 'parentId', columns, url, false);
};

/* 博客评论-修改 */
function edit(blogCommentId) {
	var url = prefix + '/edit/' + blogCommentId;
	layer_showAuto("审核博客评论", url);
}

/* 博客评论-详情 */
function detail(blogCommentId) {
	console.log(blogCommentId)
	var url = prefix + '/detail/' + blogCommentId;
	layer_showAuto("博客评论详情", url);
}

// 单条删除
function remove(id) {
	layer.confirm("确定要删除选中博客评论吗？", {
		icon : 3,
		title : '提示'
	}, function(index) {
		$.ajax({
			type : 'post',
			url : prefix + "/remove/" + id,
			success : function(r) {
				if (r.code == 0) {
					layer.msg(r.msg, {
						icon : 1,
						time : 1000
					});
					loading();
				} else {
					layer.alert(r.msg, {
						icon : 2,
						title : "系统提示"
					});
				}
			}
		});
	});
}

// 批量删除
function batchRemove() {
	var rows = $.getSelections("blogCommentId");
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

// 预览
function preview(showId) {
	window.open("/rzblog/blogcontent/details/" + showId);
}
