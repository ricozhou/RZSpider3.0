var prefix = ctx + "spider/spidertask/spidertaskinfo"

$(function() {
	// 启动websocket
	startWebsocket($('#taskId').val());
	$('.bootstrap-table').bootstrapTable('refresh', queryParams);
	var columns = [
			{
				checkbox : true
			},
			{
				field : 'taskInfoId',
				title : '任务详情ID'
			},
			{
				field : 'taskStatus',
				title : '任务状态',
				align : 'center',
				formatter : function(value, row, index) {
					if (value == 0) {
						return '<span class="label label-success"><i class="fa fa-spinner faa-spin animated"></i>运行中</span>';
					} else if (value == 1) {
						return '<span class="label label-danger">中止中</span>';
					} else if (value == 2) {
						return '<span class="label label-danger">已失效</span>';
					}
				}
			},
			{
				field : 'finishStatus',
				title : '完成状态',
				align : 'center',
				formatter : function(value, row, index) {
					if (value == 0) {
						return '<span class="label label-danger">未完成</span>';
					} else if (value == 1) {
						return '<span class="label label-danger">部分完成</span>';
					} else if (value == 2) {
						return '<span class="label label-success">全部完成</span>';
					} else if (value == 3) {
						return '<span class="label label-danger">爬虫出错</span>';
					}
				}
			},
			{
				field : 'startTime',
				title : '启动时间'
			},
			{
				field : 'endTime',
				title : '结束时间'
			},
			{
				field : 'consumeTime',
				title : '耗时'
			},
			{
				title : '基本操作',
				align : 'center',
				formatter : function(value, row, index) {
					var actions = [];
					actions
							.push('<a class="btn btn-primary btn-xs" href="#" title="详情" mce_href="#" onclick="params(\''
									+ row.taskId
									+ '\',\''
									+ row.taskInfoId
									+ '\')"><i class="fa fa-edit"></i>详情</a> ');
					actions.push('<a class="btn btn-warning btn-xs '
							+ removeFlag
							+ '" href="#" title="删除" onclick="remove(\''
							+ row.taskInfoId + '\')" id=\'' + row.taskInfoId
							+ '\'><i class="fa fa-remove"></i>删除</a>');
					return actions.join('');
				}
			},
			{
				title : '启动操作',
				align : 'center',
				formatter : function(value, row, index) {
					var actions = [];
					actions.push(statusTools(row));
					return actions.join('');
				}
			},
			{
				title : '数据操作',
				align : 'center',
				formatter : function(value, row, index) {
					var actions = [];
					if (row.taskStatus == 1 && row.endTime != null) {
						actions
								.push('<a class="btn btn-primary btn-xs '
										+ downloadDataFlag
										+ '" href="#" title="预览" onclick="previewSpiderData(\''
										+ row.taskInfoId
										+ '\')"><i class="fa fa-eye"></i>预览</a> ');
						actions
								.push('<a class="btn btn-primary btn-xs '
										+ downloadDataFlag
										+ '" href="#" title="下载" onclick="downloadSpiderData(\''
										+ row.taskInfoId
										+ '\',\''
										+ 1
										+ '\')"><i class="fa fa-download"></i>下载</a> ');
						actions
								.push('<a class="btn btn-primary btn-xs '
										+ downloadDataFlag
										+ '" href="#" title="下载全部" onclick="downloadSpiderData(\''
										+ row.taskInfoId
										+ '\',\''
										+ 2
										+ '\')"><i class="fa fa-download"></i>下载全部</a>');
					}
					return actions.join('');
				}
			} ];
	var url = prefix + "/list";
	// 自定义参数查询
	$.initTableParams(columns, url, queryParams,'请输入任务详情ID');
});

function statusTools(row) {
	if (row.taskStatus == 1) {
		return '<a class="btn btn-info btn-xs ' + startFlag
				+ '" href="#" onclick="start(\'' + row.taskId + '\',\''
				+ row.taskInfoId + '\')"><i class="fa fa-play"></i>启动</a> ';
	} else if (row.taskStatus == 0) {
		return '<a class="btn btn-warning btn-xs ' + stopFlag
				+ '" href="#" onclick="stop(\'' + row.taskId + '\',\''
				+ row.taskInfoId + '\')"><i class="fa fa-pause"></i>中止</a> ';
	}
}

function queryParams(params) {
	return {
		// 传递参数查询参数
		pageSize : params.limit,
		pageNum : params.offset / params.limit + 1,
		searchValue : params.search,
		orderByColumn : params.sort,
		isAsc : params.order,
		taskId : $("#taskId").val()
	};
}

/* 任务参数详情 */
function params(taskId, taskInfoId) {
	var url = prefix + '/params/' + taskInfoId;
	layer_showAuto("任务参数详情", url);
}

/* 数据预览 */
function previewSpiderData(taskInfoId) {
	// 先校验数据是否存在
	$.ajax({
		cache : true,
		type : "POST",
		url : prefix + "/checkData",
		data : {
			"taskInfoId" : taskInfoId,
			"taskFlag" : 1
		},
		async : true,
		error : function(request) {
			$.modalAlert("系统错误", modal_status.FAIL);
		},
		success : function(data) {
			if (data.code == 0) {
				// 有数据则预览
				var url = prefix + '/previewSpiderData/' + taskInfoId;
				layer_showAuto2("爬虫数据预览", url);
			} else {
				$.modalAlert(data.msg, modal_status.FAIL);
			}
		},
	});

}

/* 任务-下载 */
function downloadSpiderData(taskInfoId, taskFlag) {
	// 先校验数据是否存在
	$.ajax({
		cache : true,
		type : "POST",
		url : prefix + "/checkData",
		data : {
			"taskInfoId" : taskInfoId,
			"taskFlag" : taskFlag
		},
		async : true,
		error : function(request) {
			$.modalAlert("系统错误", modal_status.FAIL);
		},
		success : function(data) {
			if (data.code == 0) {
				// 有数据则下载
				downloadData(taskInfoId, taskFlag);
			} else {
				$.modalAlert(data.msg, modal_status.FAIL);
			}
		},
	});
}
/* 任务-下载数据 */
function downloadData(taskInfoId, taskFlag) {
	location.href = prefix + "/downloadData/" + taskInfoId + "/" + taskFlag;
	layer.msg('正在下载,请稍后…', {
		icon : 1
	});
}

/* 任务-停用 */
function stop(taskId, taskInfoId) {
	// 判断是否存在
	var data = csisexist(taskId);
	if (data.code != 0) {
		$.modalAlert(data.msg, modal_status.FAIL);
		// 关闭标签页
		// closeTab();
		return;
	}
	$.modalConfirm("确认要中止吗？", function() {
		$.ajax({
			cache : true,
			type : "POST",
			url : prefix + "/stop",
			data : {
				"taskInfoId" : taskInfoId,
				"taskId" : taskId
			},
			async : true,
			error : function(request) {
				$.modalAlert("系统错误", modal_status.FAIL);
			},
			beforeSend : function() {
				layer.msg("正在中止,请稍后……", {
					icon : 1,
					time : 1000,
					shade : [ 0.1, '#fff' ]
				});
			},
			success : function(data) {
				if (data.code == 0) {
					layer.msg("中止成功,正在刷新数据请稍后……", {
						icon : 1,
						time : 500,
						shade : [ 0.1, '#fff' ]
					}, function() {
						$.refreshTable();
					});
				} else {
					$.modalAlert(data.msg, modal_status.FAIL);
					$.refreshTable();
				}
			},
		});

	})
}

/* 任务-启用 */
function start(taskId, taskInfoId) {
	// 修改前判断自定义爬虫是否存在，是否可用
	// 判断是否存在
	var data = csisexist(taskId);
	if (data.code != 0) {
		$.modalAlert(data.msg, modal_status.FAIL);
		// 关闭标签页
		// closeTab();
		return;
	}
	$.modalConfirm("注意！每次重新启动会将任务此前数据清除！确认要启动吗？", function() {
		$.ajax({
			cache : true,
			type : "POST",
			url : prefix + "/start",
			data : {
				"taskInfoId" : taskInfoId,
				"taskId" : taskId
			},
			async : true,
			error : function(request) {
				$.modalAlert("系统错误", modal_status.FAIL);
			},
			beforeSend : function() {
				layer.msg("正在启动,请稍后……", {
					icon : 1,
					time : 1000,
					shade : [ 0.1, '#fff' ]
				});
			},
			success : function(data) {
				if (data.code == 0) {
					layer.msg("启动成功,正在刷新数据请稍后……", {
						icon : 1,
						time : 500,
						shade : [ 0.1, '#fff' ]
					}, function() {
						$.refreshTable();
					});
				} else {
					$.modalAlert(data.msg, modal_status.FAIL);
				}
			},
		});

	})
}
// 判断爬虫是否存在
function csisexist(taskId) {
	var data2;
	$.ajax({
		cache : true,
		type : "POST",
		url : ctx + "spider/spidertask/checkSpiderExist",
		data : {
			"taskId" : taskId
		},
		async : false,
		error : function(request) {
			$.modalAlert("系统错误", modal_status.FAIL);
		},
		success : function(data) {
			data2 = data;
		}
	});
	return data2;
}
// 单条删除
function remove(id) {
	$.modalConfirm("确定要删除选中爬虫任务运行记录详情吗？", function() {
		_ajax(prefix + "/remove/" + id, "", "post");
		$.refreshTable();
	})
}

// 批量删除
function batchRemove() {
	var rows = $.getSelections("taskInfoId");
	if (rows.length == 0) {
		$.modalMsg("请选择要删除的数据", "warning");
		return;
	}
	$.modalConfirm("确认要删除选中的" + rows.length + "条数据吗?", function() {
		_ajax(prefix + '/batchRemove', {
			"ids" : rows
		}, "post");
		$.refreshTable();
	});
}
// 批量启动
function batchStart() {
	var rows = $.getSelections("taskInfoId");
	if (rows.length == 0) {
		$.modalMsg("请选择要启动的任务", "warning");
		return;
	}
	$.modalConfirm("确认要启动选中的" + rows.length + "条任务吗?", function() {
		_ajax(prefix + '/batchStart', {
			"ids" : rows
		}, "post");
		$.refreshTable();
	});
}
// 批量中止
function batchStop() {
	var rows = $.getSelections("taskInfoId");
	if (rows.length == 0) {
		$.modalMsg("请选择要中止的任务", "warning");
		return;
	}
	$.modalConfirm("确认要中止选中的" + rows.length + "条任务吗?", function() {
		_ajax(prefix + '/batchStop', {
			"ids" : rows
		}, "post");
		$.refreshTable();
	});
}
