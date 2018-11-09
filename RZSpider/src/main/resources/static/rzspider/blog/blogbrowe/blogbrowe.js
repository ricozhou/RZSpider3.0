var prefix = ctx + "blog/blogbrowe"

$(function() {
	var columns = [
			{
				checkbox : true
			},
			{
				field : 'blogBroweId',
				align : 'center',
				title : 'ID'
			},
			{
				field : 'clientPlatform',
				align : 'center',
				visible : false,
				title : '平台'
			},
			{
				field : 'clientUserAgent',
				align : 'center',
				visible : false,
				title : 'UserAgent'
			},
			{
				field : 'clientBrowsePlatform',
				align : 'center',
				title : '系统'
			},
			{
				field : 'clientBrowseName',
				align : 'center',
				title : '浏览器'
			},
			{
				field : 'clientBrowseVersion',
				align : 'center',
				visible : false,
				title : '浏览器版本'
			},
			{
				field : 'clientBrowseIp',
				align : 'center',
				title : 'IP'
			},
			{
				field : 'clientBrowseCity',
				align : 'center',
				title : '所在地'
			},
			{
				field : 'clientBrowseAppAndPc',
				align : 'center',
				title : '客户端类型'
			},
			{
				field : 'clientBrowseUrl',
				align : 'center',
				visible : false,
				title : 'URL',
				formatter : function(value, row, index) {
					return '<a  href="' + value + '" target="_Blank">' + value
							+ '</a>';
				}
			},
			{
				field : 'createTime',
				align : 'center',
				title : '浏览时间'
			},
			{
				title : '操作',
				align : 'center',
				formatter : function(value, row, index) {
					var actions = [];
					actions
							.push('<a class="btn btn-primary btn-xs " href="#" title="查看" mce_href="#" onclick="details(\''
									+ row.blogBroweId
									+ '\')"><i class="fa fa-edit"></i>查看</a> ');
					actions.push('<a class="btn btn-warning btn-xs '
							+ removeFlag
							+ '" href="#" title="删除" onclick="remove(\''
							+ row.blogBroweId
							+ '\')"><i class="fa fa-remove"></i>删除</a>');
					return actions.join('');
				}
			} ];
	var url = prefix + "/list";
	$.initTable(columns, url, '请输入关键字');
});

/* 博客浏览日志-查看 */
function details(blogBroweId) {
	var url = prefix + '/details/' + blogBroweId;
	layer_showAuto("查看博客浏览日志", url);
}

// 单条删除
function remove(id) {
	$.modalConfirm("删除将影响网站概览数据！确定要删除选中博客浏览日志吗？", function() {
		_ajax(prefix + "/remove/" + id, "", "post");
	})
}

// 批量删除
function batchRemove() {
	var rows = $.getSelections("blogBroweId");
	if (rows.length == 0) {
		$.modalMsg("请选择要删除的数据", "warning");
		return;
	}
	$.modalConfirm("删除将影响网站概览数据！确认要删除选中的" + rows.length + "条数据吗?", function() {
		_ajax(prefix + '/batchRemove', {
			"ids" : rows
		}, "post");
	});
}
