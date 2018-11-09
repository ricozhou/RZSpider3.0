var prefix = ctx + "spider/spidertask"

var taskNameTitle = "";
$(function() {
	var columns = [
			{
				checkbox : true
			},
			{
				field : 'taskId',
				title : '任务ID'
			},
			{
				field : 'taskName',
				title : '任务名称'
			},
			{
				field : 'spiderName',
				title : '爬虫名称'
			},
			{
				field : 'taskStatus',
				title : '可用状态',
				align : 'center',
				formatter : function(value, row, index) {
					if (value == 0) {
						return '<span class="label label-success">正常</span>';
					} else if (value == 1) {
						return '<span class="label label-danger">暂停</span>';
					}
				}
			},
			{
				field : 'jobStatus',
				title : '任务状态',
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
				field : 'taskExecfrequency',
				title : '任务执行策略',
				align : 'center',
				formatter : function(value, row, index) {
					if (value == 0) {
						return '<span>只执行一次（定时执行）</span>';
					} else if (value == 1) {
						return '<span>固定频次执行（定时执行）</span>';
					} else if (value == 2) {
						return '<span>cron表达式执行（表达式执行）</span>';
					} else if (value == 3) {
						return '<span>只执行一次（立即执行）</span>';
					}
				}
			},
			{
				field : 'createTime',
				visible : false,
				title : '创建时间'
			},
			{
				title : '基本操作',
				align : 'center',
				formatter : function(value, row, index) {
					var actions = [];
					actions
							.push('<a class="btn btn-success btn-xs '
									+ paramsFlag
									+ '" href="#" title="参数" mce_href="#" onclick="params(\''
									+ row.taskId
									+ '\')"><i class="fa fa-edit"></i>参数</a> ');
					actions
							.push('<a class="btn btn-success btn-xs '
									+ editFlag
									+ '" href="#" title="编辑" mce_href="#" onclick="edit(\''
									+ row.taskId + '\',\'' + row.jobStatus
									+ '\')"><i class="fa fa-edit"></i>编辑</a> ');
					actions.push('<a class="btn btn-danger btn-xs '
							+ removeFlag
							+ '" href="#" title="删除" onclick="remove(\''
							+ row.taskId
							+ '\')"><i class="fa fa-remove"></i>删除</a>');
					return actions.join('');
				}
			},
			{
				title : '任务操作',
				align : 'center',
				formatter : function(value, row, index) {
					var actions = [];
					actions.push(statusTools(row));
					actions
							.push('<a class="btn btn-primary btn-xs '
									+ taskInfoFlag
									+ '" href="#" title="详情" mce_href="#" onclick="taskInfo(\''
									+ row.taskId
									+ '\',\''
									+ row.taskName
									+ '\')"><i class="fa fa-search"></i>详情</a> ');
					return actions.join('');
				}
			} ];
	var url = prefix + "/list";
	$.initTable(columns, url,'请输入任务名称、爬虫名称');
});

function statusTools(row) {
	if (row.jobStatus == 0) {
		return '<a class="btn btn-warning btn-xs ' + startTaskFlag
				+ '" href="#" title="暂停" mce_href="#" onclick="stop(\''
				+ row.taskId + '\',\'' + row.jobStatus
				+ '\')"><i class="fa fa-pause"></i>暂停</a> ';
	} else if (row.jobStatus == 1) {
		return '<a class="btn btn-info btn-xs ' + startTaskFlag
				+ '" href="#" title="启用" mce_href="#" onclick="start(\''
				+ row.taskId + '\',\'' + row.jobStatus
				+ '\')"><i class="fa fa-play"></i>启用</a> ';
	}
}

/* 爬虫任务详情-新增 */
function add() {
	var url = prefix + '/add';
	layer_showAuto("新增爬虫任务详情", url);
}

/* 爬虫任务详情-修改 */
function edit(taskId, jobStatus) {
	// 修改前判断自定义爬虫是否存在，是否可用
	// 判断是否存在
	var data = csisexist(taskId);
	if (data.code != 0) {
		$.modalAlert(data.msg, modal_status.FAIL);
		// 关闭标签页
		// closeTab();
		return;
	}

	if (jobStatus == 0) {
		// 运行中不能修改
		$.modalAlert("任务已启用，请先停用再修改", modal_status.FAIL);
		return;
	}
	var url = prefix + '/edit/' + taskId;
	layer_showAuto("修改爬虫任务详情", url);
}

/* 爬虫任务参数详情 */
function params(taskId) {
	// 修改前判断自定义爬虫是否存在，是否可用
	// 判断是否存在
	var data = csisexist(taskId);
	if (data.code != 0) {
		$.modalAlert(data.msg, modal_status.FAIL);
		// 关闭标签页
		// closeTab();
		return;
	}
	var url = prefix + '/params/' + taskId;
	layer_showAuto("修改爬虫参数", url);
}

/* 调度任务-停用 */
function stop(taskId, jobStatus) {
	$.modalConfirm("确认要停用吗？", function() {
		_ajax(prefix + "/startTask/", {
			"taskId" : taskId,
			"jobStatus" : jobStatus
		}, "post");
	})
}

/* 调度任务-启用 */
function start(taskId, jobStatus) {
	// 修改前判断自定义爬虫是否存在，是否可用
	// 判断是否存在
	var data = csisexist(taskId);
	if (data.code != 0) {
		$.modalAlert(data.msg, modal_status.FAIL);
		// 关闭标签页
		// closeTab();
		return;
	}
	$.modalConfirm("确认要启用吗？", function() {
		_ajax(prefix + "/startTask/", {
			"taskId" : taskId,
			"jobStatus" : jobStatus
		}, "post");
	})
}

/* 爬虫任务详情-详情界面 */
function taskInfo(taskId, taskName) {
	// 即使不存在也可以下载也可以进入查看
	var url = prefix + '/taskInfo/' + taskId;
	createMenuItem(url, "爬虫任务详情（" + taskName + "）");
}

// 单条删除
function remove(id) {
	$.modalConfirm("请注意！删除将会把所有此任务运行记录及数据清除！确定要删除选中爬虫任务详情吗？", function() {
		_ajax(prefix + "/remove/" + id, "", "post");
	})
}

// 批量删除
function batchRemove() {
	var rows = $.getSelections("taskId");
	if (rows.length == 0) {
		$.modalMsg("请选择要删除的数据", "warning");
		return;
	}
	$.modalConfirm("请注意！删除将会把所有此任务运行记录及数据清除！确认要删除选中的" + rows.length + "条数据吗?",
			function() {
				_ajax(prefix + '/batchRemove', {
					"ids" : rows
				}, "post");
			});
}
// 判断爬虫是否存在
function csisexist(taskId) {
	var data2;
	$.ajax({
		cache : true,
		type : "POST",
		url : prefix + "/checkSpiderExist",
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