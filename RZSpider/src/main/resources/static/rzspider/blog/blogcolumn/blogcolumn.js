var prefix = ctx + "blog/blogcolumn"

window.onload = function() {
	loading();
};
function loading() {
	var columns = [
			{
				field : 'blogColumnName',
				title : '专栏名称',
				align : 'center',
				formatter : function(row, index) {
					if (row.icon == null || row == "") {
						return row.blogColumnName;
					} else {
						return '<i class="' + row.icon
								+ '"></i> <span class="nav-label">'
								+ row.blogColumnName + '</span>';
					}
				}
			},
			{
				field : 'orderNum',
				align : 'center',
				width : '10%',
				title : '显示顺序'
			},
			{
				field : 'url',
				align : 'center',
				title : '请求地址'
			},
			{
				title : '类型',
				field : 'columnType',
				align : 'center',
				width : '15%',
				formatter : function(item, index) {
					if (item.columnType == 'M') {
						return '<span class="label label-success">主专栏</span>';
					}
					if (item.columnType == 'C') {
						return '<span class="label label-primary">次专栏</span>';
					}
				}
			},
			{
				field : 'visible',
				title : '专栏可见',
				align : 'center',
				width : '10%',
				formatter : function(row, index) {
					if (row.visible == 0) {
						return '<span class="label label-success">显示</span>';
					} else if (row.visible == 1) {
						return '<span class="label label-danger">隐藏</span>';
					}
				}
			},
			{
				field : 'backImg',
				title : '专栏背景',
				align : 'center',
				width : '10%',
				formatter : function(row, index) {
					var link = row.backImg;
					if (link == null || link == '') {
						link = getSysDefaultBgImg();
					}
					return '<a href="'
							+ link
							+ '" target="_Blank"><img width="100px" height="50px" src="'
							+ link + '" ></img></a>';

				}
			},
			{
				title : '操作',
				align : 'center',
				formatter : function(row, index) {
					var actions = [];
					var flag;
					if (row.columnType == 'M') {
						flag = 0;
					} else if (row.columnType == 'C') {
						flag = 1;
					}
					actions
							.push('<a class="btn btn-success btn-xs '
									+ editFlag
									+ '" href="#" title="编辑" mce_href="#" onclick="edit(\''
									+ row.blogColumnId + '\',\'' + flag
									+ '\')"><i class="fa fa-edit"></i>编辑</a> ');
					if (row.columnType == 'M') {
						actions
								.push('<a class="btn btn-info btn-xs '
										+ addFlag
										+ '" href="#" title="新增" mce_href="#" onclick="add(1,\''
										+ row.blogColumnId
										+ '\')"><i class="fa fa-plus"></i>新增</a> ');
					} else if (row.columnType == 'C') {
						actions
								.push('<a class="btn btn-info btn-xs '
										+ addFlag
										+ '" href="#" title="查看" mce_href="#" onclick="add(\''
										+ row.blogColumnId
										+ '\')"><i class="fa fa-play"></i>查看</a> ');
					}

					actions.push('<a class="btn btn-warning btn-xs '
							+ removeFlag
							+ '" href="#" title="删除" onclick="remove(\''
							+ row.blogColumnId
							+ '\')"><i class="fa fa-remove"></i>删除</a>');
					return actions.join('');
				}
			} ];
	var url = prefix + "/list";
	$.initTreeTable('blogColumnId', 'parentId', columns, url, false);
}

/* 博客专栏管理-新增 */
function add(flag, parentId) {
	var url;
	if (flag == 0) {
		url = prefix + '/add/' + flag + '/0';
		layer_showAuto("新增博客主专栏", url);
	} else if (flag == 1) {
		console.log(parentId)
		url = prefix + '/add/' + flag + '/' + parentId;
		layer_showAuto("新增博客次专栏", url);
	}

}

/* 博客专栏管理-修改 */
function edit(blogColumnId, flag) {
	var url = prefix + '/edit/' + flag + '/' + blogColumnId;
	if (flag == 0) {
		layer_showAuto("修改博客主专栏", url);
	} else if (flag == 1) {
		layer_showAuto("修改博客次专栏", url);
	}
}

// 单条删除
function remove(blogColumnId) {
	layer.confirm("确定要删除选中博客专栏吗？专栏内文章将全部清除！", {
		icon : 3,
		title : '提示'
	}, function(index) {
		$.ajax({
			type : 'post',
			url : prefix + "/remove/" + blogColumnId,
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