var prefix = ctx + "tool/baseset"

$(function() {
	var columns = [
			{
				checkbox : true
			},
			{
				field : 'basesetId',
				title : '主题ID'
			},
			{
				field : 'themeName',
				title : '主题名称'
			},
			{
				field : 'loginbgName',
				title : '登录设置',
				align : 'center',
				formatter : function(value, row, index) {
					var actions = [];
					actions
							.push('<a class="btn btn-success btn-xs" href="#" title="编辑" mce_href="#" onclick="loginSetEdit(\''
									+ row.basesetId
									+ '\')"><i class="fa fa-edit"></i>编辑</a> ');
					return actions.join('');
				}
			},
			{
				field : 'homeIntroduction',
				title : '首页介绍',
				align : 'center',
				formatter : function(value, row, index) {
					var actions = [];
					actions
							.push('<a class="btn btn-success btn-xs" href="#" title="编辑" mce_href="#" onclick="homeIntroductionEdit(\''
									+ row.basesetId
									+ '\')"><i class="fa fa-edit"></i>编辑</a> ');
					return actions.join('');
				}
			},
			{
				visible : false,
				field : 'spiderJavaPackagePrefix',
				title : '代码包前缀'
			},
			{
				visible : false,
				field : 'downloadFileNamePrefix',
				title : '下载文件前缀'
			},
			{
				field : 'spiderJavaCodeExample',
				title : '代码示例',
				align : 'center',
				formatter : function(value, row, index) {
					var actions = [];
					actions
							.push('<select class="form-control" onchange="selectCodeExampleEdit(this,'
									+ row.basesetId
									+ ')"><option value="0">请选择</option><option '
									+ spiderJavaCodeExampleEditFlag
									+ ' value="1">JAVA代码</option><option '
									+ spiderPythonCodeExampleEditFlag
									+ ' value="2">PYTHON代码</option><option '
									+ spiderJavascriptCodeExampleEditFlag
									+ ' value="3">JS代码</option></select>');
					return actions.join('');
				}
			},
			{
				field : 'spiderWebsiteManual',
				title : '手册编辑',
				align : 'center',
				formatter : function(value, row, index) {
					var actions = [];
					actions
							.push('<select class="form-control" onchange="selectManualEdit(this,'
									+ row.basesetId
									+ ')"><option value="0">请选择</option><option value="1">网站手册</option><option value="2">爬虫手册</option></select>');
					return actions.join('');
				}
			},
			{
				field : 'showMusicToolStatus',
				title : '音乐插件',
				align : 'center',
				formatter : function(value, row, index) {
					if (value == 0) {
						return '<a class="btn btn-success btn-xs" href="#" title="编辑" mce_href="#" onclick="spiderMusicToolEdit(\''
								+ row.basesetId
								+ '\',\''
								+ row.themeName
								+ '\')"><i class="fa fa-edit"></i>编辑</a> ';
					} else if (value == 1) {
						return '<a class="btn btn-danger btn-xs" href="#" title="编辑" mce_href="#" onclick="spiderMusicToolEdit(\''
								+ row.basesetId
								+ '\',\''
								+ row.themeName
								+ '\')"><i class="fa fa-edit"></i>编辑</a> ';
					}
				}
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
				title : '使用状态',
				align : 'center',
				formatter : function(value, row, index) {
					if (value == 0) {
						return '<span class="label label-success">启用中</span>';
					} else if (value == 1) {
						return '<span class="label label-danger">停用中</span>';
					}
				}
			},
			{
				title : '操作',
				align : 'center',
				formatter : function(value, row, index) {
					var actions = [];
					if (row.status == 0) {
						actions
								.push('<a class="btn btn-info btn-xs '
										+ startUseFlag
										+ '" href="#" title="启用" mce_href="#" onclick="startUse(\''
										+ row.basesetId
										+ '\',\''
										+ row.useStatus
										+ '\')"><i class="fa fa-play"></i>启用</a> ');
					}
					actions
							.push('<a class="btn btn-success btn-xs '
									+ editFlag
									+ '" href="#" title="编辑" mce_href="#" onclick="edit(\''
									+ row.basesetId
									+ '\')"><i class="fa fa-edit"></i>编辑</a> ');
					actions.push('<a class="btn btn-warning btn-xs '
							+ removeFlag
							+ '" href="#" title="删除" onclick="remove(\''
							+ row.basesetId
							+ '\')"><i class="fa fa-remove"></i>删除</a>');
					return actions.join('');
				}
			} ];
	var url = prefix + "/list";
	$.initTable(columns, url, '请输入主题名称');
});

// 代码示例
function selectCodeExampleEdit(obj, basesetId) {
	// 获取被选中的option标签选项
	var flag = obj.selectedIndex;
	if (flag == 1) {
		// java
		spiderJavaCodeExampleEdit(basesetId);
	} else if (flag == 2) {
		// python
		spiderPythonCodeExampleEdit(basesetId);
	} else if (flag == 3) {
		// js
		spiderJavascriptCodeExampleEdit(basesetId);
	}
}

// 手册编辑
function selectManualEdit(obj, basesetId) {
	// 获取被选中的option标签选项
	var flag = obj.selectedIndex;
	if (flag == 1) {
		// 网站手册
		spiderWebsiteManualEdit(basesetId);
	} else if (flag == 2) {
		// 爬虫手册
		spiderUseManualEdit(basesetId);
	}
}

// 启用
function startUse(basesetId) {
	$.modalConfirm("确定要启用此主题设置吗？", function() {
		_ajax(prefix + "/startUse/" + basesetId, "", "post");
	})
}

/* 基础设置详情-新增 */
function add() {
	var url = prefix + '/add';
	layer_showAuto("新增主题设置", url);
}

/* 基础设置详情-清除缓存 */
function clearCache() {
	var url = prefix + '/clear';
	layer_showAuto("清除缓存", url);
}

/* 基础设置详情-修改 */
function edit(basesetId) {
	var url = prefix + '/edit/' + basesetId;
	layer_showAuto("修改主题设置", url);
}

// 单条删除
function remove(id) {
	$.modalConfirm("确定要删除选中主题设置吗？", function() {
		_ajax(prefix + "/remove/" + id, "", "post");
	})
}

// 批量删除
function batchRemove() {
	var rows = $.getSelections("basesetId");
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
/* java代码示例详情 */
function spiderJavaCodeExampleEdit(basesetId) {
	var url = prefix + '/detailedit/spiderJavaCodeExampleEdit/' + basesetId;
	layer_showAuto2("编辑JAVA代码示例", url);
}
/* python代码示例详情 */
function spiderPythonCodeExampleEdit(basesetId) {
	var url = prefix + '/detailedit/spiderPythonCodeExampleEdit/' + basesetId;
	layer_showAuto2("编辑PYTHON代码示例", url);
}
/* javascript代码示例详情 */
function spiderJavascriptCodeExampleEdit(basesetId) {
	var url = prefix + '/detailedit/spiderJavascriptCodeExampleEdit/'
			+ basesetId;
	layer_showAuto2("编辑JAVASCRIPT代码示例", url);
}
/* 登录设置详情 */
function loginSetEdit(basesetId) {
	var url = prefix + '/detailedit/loginSetEdit/' + basesetId;
	layer_showAuto("编辑登录设置", url);
}
/* 首页介绍示例详情 */
function homeIntroductionEdit(basesetId) {
	var url = prefix + '/detailedit/homeIntroductionEdit/' + basesetId;
	layer_showAuto2("编辑首页介绍", url);
}
/* 网站手册示例详情 */
function spiderWebsiteManualEdit(basesetId) {
	var url = prefix + '/detailedit/spiderWebsiteManualEdit/' + basesetId;
	layer_showAuto2("编辑网站手册", url);
}
/* 爬虫手册示例详情 */
function spiderUseManualEdit(basesetId) {
	var url = prefix + '/detailedit/spiderUseManualEdit/' + basesetId;
	layer_showAuto2("编辑爬虫手册", url);
}
/* 音乐工具编辑详情 */
function spiderMusicToolEdit(basesetId, themeName) {
	var url = prefix + '/detailedit/spiderMusicToolEdit/' + basesetId;
	createMenuItem(url, "编辑音乐工具（" + themeName + "）");
}
