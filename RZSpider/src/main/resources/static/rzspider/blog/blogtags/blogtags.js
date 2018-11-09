var prefix = ctx + "blog/blogtags"

$(function() {
	var columns = [
			{
				checkbox : true
			},
			{
				field : 'blogTagsId',
				align : 'center',
				title : '标签id'
			},
			{
				field : 'blogTagsName',
				align : 'center',
				title : '标签名'
			},
			{
				field : 'status',
				title : '标签是否可用',
				align : 'center',
				formatter : function(value, row, index) {
					if (value == '0') {
						return '<span class="label label-primary">可用</span>';
					} else if (value == '1') {
						return '<span class="label label-danger">禁用</span>';
					}
				}
			},
			{
				field : 'tagsMessage',
				align : 'center',
				width : '30%',
				title : '标签寄语'
			},
			{
				field : 'backImg',
				title : '标签背景',
				align : 'center',
				width : '15%',
				formatter : function(value, row, index) {
					var link = row.backImg;
					if (link == null || link == '') {
						link = getSysDefaultBgImg();
					}
					return '<a href="'
							+ link
							+ '" target="_Blank"><img width="150px" height="50px" src="'
							+ link + '" ></img></a>';

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
									+ row.blogTagsId
									+ '\')"><i class="fa fa-edit"></i>编辑</a> ');
					actions.push('<a class="btn btn-warning btn-xs '
							+ removeFlag
							+ '" href="#" title="删除" onclick="remove(\''
							+ row.blogTagsId
							+ '\')"><i class="fa fa-remove"></i>删除</a>');
					return actions.join('');
				}
			} ];
	var url = prefix + "/list";
	$.initTable(columns, url, '请输入标签名');
});

/* 博客标签-新增 */
function add() {
	var url = prefix + '/add';
	layer_showAuto("新增博客标签", url);
}

/* 博客标签-修改 */
function edit(blogTagsId) {
	var url = prefix + '/edit/' + blogTagsId;
	layer_showAuto("修改博客标签", url);
}

// 单条删除
function remove(id) {
	$.modalConfirm("确定要删除选中博客标签吗？", function() {
		_ajax(prefix + "/remove/" + id, "", "post");
	})
}

// 批量删除
function batchRemove() {
	var rows = $.getSelections("blogTagsId");
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
// 专栏背景图片默认,系统图片
function getSysDefaultBgImg() {
	// 从系统中抽取
	// 默认只有5张
	return '/img/blog/column_bg' + getRandomNum(0, 5) + '.jpg';
}
// 获取随机数
function getRandomNum(n, m) {
	// 前闭后开
	return Math.floor(Math.random() * (m - n) + n);
}