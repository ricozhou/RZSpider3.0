/**
 * 通用方法封装处理 Copyright (c) 2018 rzspider
 */
$(function() {
	// 消息窗体
	$.modalMsg = function(content, type) {
		if (type != undefined) {
			var icon = "";
			if (type == modal_status.WARNING) {
				icon = 0;
			} else if (type == modal_status.SUCCESS) {
				icon = 1;
			} else if (type == modal_status.FAIL) {
				icon = 2;
			}
			layer.msg(content, {
				icon : icon,
				time : 2000,
				shift : 0
			});
			$(".layui-layer-msg").find('i.' + icon).parents('.layui-layer-msg')
					.addClass('layui-layer-msg-' + type);
		} else {
			layer.msg(content);
		}
	}
	// 弹出窗体
	$.modalAlert = function(content, type) {
		var icon = "";
		if (type == modal_status.WARNING) {
			icon = 0;
		} else if (type == modal_status.SUCCESS) {
			icon = 1;
		} else if (type == modal_status.FAIL) {
			icon = 2;
		} else {
			icon = 3;
		}
		layer.alert(content, {
			icon : icon,
			title : "系统提示",
			btn : [ '确认' ],
			btnclass : [ 'btn btn-primary' ],
		});
	}
	// 确认窗体
	$.modalConfirm = function(content, callBack) {
		layer.confirm(content, {
			icon : 3,
			title : "系统提示",
			btn : [ '确认', '取消' ],
			btnclass : [ 'btn btn-primary', 'btn btn-danger' ],
		}, function() {
			callBack(true);
		}, function() {
			// callBack(false)
		});
	}
	// 关闭窗体
	$.modalClose = function() {
		var index = parent.layer.getFrameIndex(window.name); // 先得到当前iframe层的索引
		var $IsdialogClose = top.$("#layui-layer" + index).find(
				'.layui-layer-btn').find("#IsdialogClose");
		var IsClose = $IsdialogClose.is(":checked");
		if ($IsdialogClose.length == 0) {
			IsClose = true;
		}
		if (IsClose) {
			parent.layer.close(index);
		} else {
			parent.location.reload();
		}
	}
	// 刷新父窗体
	$.parentReload = function() {
		parent.location.reload();
		return false;
	}
	// 初始bootstrap table数据
	// 正常
	$.initTable = function(_columns, _url, searchPlaceholder) {
		$('.bootstrap-table').bootstrapTable({
			method : 'get', // 请求方式（*）
			dataType : "json", // 返回格式（*）
			url : _url, // 请求后台的URL（*）
			pagination : true, // 是否显示分页（*）
			pageSize : 10, // 每页的记录行数（*）
			pageNumber : 1, // 初始化加载第一页，默认第一页
			pageList : [ 10, 25, 50, 100, 500 ], // 可供选择的每页的行数（*）
			search : true, // 是否显示搜索框功能
			singleSelect : false, // 是否禁止多选
			iconSize : 'outline', // 图标大小：undefined默认的按钮尺寸 xs超小按钮sm小按钮lg大按钮
			toolbar : '#tableToolbar', // 指定工作栏
			sidePagination : "server", // 启用服务端分页
			showRefresh : true, // 是否显示刷新按钮
			showColumns : true, // 是否显示隐藏某列下拉框
			showToggle : true, // 是否显示详细视图和列表视图的切换按钮
			cache : false, // 是否使用缓存
			showExport : true, // 是否支持导出文件
			striped : true,// 是否隔行变色
			queryParams : function(params) {
				return {
					// 传递参数查询参数
					pageSize : params.limit,
					pageNum : params.offset / params.limit + 1,
					searchValue : params.search,
					orderByColumn : params.sort,
					isAsc : params.order
				};
			},
			columns : _columns
		});

		// 设置提示
		var $search = $('.bootstrap-table').find('.form-control.input-outline');
		$search.attr('placeholder', searchPlaceholder);
	}

	// 初始bootstrap table数据
	// 正常无搜索
	$.initTable1 = function(_columns, _url) {
		$('.bootstrap-table').bootstrapTable({
			method : 'get', // 请求方式（*）
			dataType : "json", // 返回格式（*）
			url : _url, // 请求后台的URL（*）
			pagination : true, // 是否显示分页（*）
			pageSize : 10, // 每页的记录行数（*）
			pageNumber : 1, // 初始化加载第一页，默认第一页
			pageList : [ 10, 25, 50, 100, 500 ], // 可供选择的每页的行数（*）
			search : false, // 是否显示搜索框功能
			singleSelect : false, // 是否禁止多选
			iconSize : 'outline', // 图标大小：undefined默认的按钮尺寸 xs超小按钮sm小按钮lg大按钮
			toolbar : '#tableToolbar', // 指定工作栏
			sidePagination : "server", // 启用服务端分页
			showRefresh : true, // 是否显示刷新按钮
			showColumns : true, // 是否显示隐藏某列下拉框
			showToggle : true, // 是否显示详细视图和列表视图的切换按钮
			cache : false, // 是否使用缓存
			showExport : true, // 是否支持导出文件
			striped : true,// 是否隔行变色
			queryParams : function(params) {
				return {
					// 传递参数查询参数
					pageSize : params.limit,
					pageNum : params.offset / params.limit + 1,
					orderByColumn : params.sort,
					isAsc : params.order
				};
			},
			columns : _columns
		});

	}
	// 初始bootstrap table数据
	// 无参数无搜索框等等
	$.initTable2 = function(_columns, _url) {
		$('.bootstrap-table').bootstrapTable({
			method : 'get', // 请求方式（*）
			dataType : "json", // 返回格式（*）
			url : _url, // 请求后台的URL（*）
			pagination : false, // 是否显示分页（*）
			// pageSize: 10, // 每页的记录行数（*）
			// pageNumber: 1, // 初始化加载第一页，默认第一页
			// pageList: [10, 25, 50,100], // 可供选择的每页的行数（*）
			search : false, // 是否显示搜索框功能
			singleSelect : true, // 是否禁止多选
			iconSize : 'outline', // 图标大小：undefined默认的按钮尺寸 xs超小按钮sm小按钮lg大按钮
			toolbar : '#tableToolbar', // 指定工作栏
			sidePagination : "server", // 启用服务端分页
			showRefresh : false, // 是否显示刷新按钮
			showColumns : false, // 是否显示隐藏某列下拉框
			showToggle : false, // 是否显示详细视图和列表视图的切换按钮
			cache : false, // 是否使用缓存
			showExport : false, // 是否支持导出文件
			striped : true,// 是否隔行变色
			// queryParams: function(params) {
			// return {
			// // 传递参数查询参数
			// pageSize: params.limit,
			// pageNum: params.offset / params.limit + 1,
			// searchValue: params.search,
			// orderByColumn: params.sort,
			// isAsc: params.order
			// };
			// },
			columns : _columns
		});
	}
	// 初始bootstrap table数据
	// 有column选择
	$.initTable3 = function(_columns, _url) {
		$('.bootstrap-table').bootstrapTable({
			method : 'get', // 请求方式（*）
			dataType : "json", // 返回格式（*）
			url : _url, // 请求后台的URL（*）
			pagination : false, // 是否显示分页（*）
			pageSize : 10, // 每页的记录行数（*）
			// pageNumber: 1, // 初始化加载第一页，默认第一页
			// pageList: [10, 25, 50,100], // 可供选择的每页的行数（*）
			search : false, // 是否显示搜索框功能
			singleSelect : true, // 是否禁止多选
			iconSize : 'outline', // 图标大小：undefined默认的按钮尺寸 xs超小按钮sm小按钮lg大按钮
			toolbar : '#tableToolbar', // 指定工作栏
			sidePagination : "server", // 启用服务端分页
			showRefresh : false, // 是否显示刷新按钮
			showColumns : true, // 是否显示隐藏某列下拉框
			showToggle : false, // 是否显示详细视图和列表视图的切换按钮
			cache : false, // 是否使用缓存
			showExport : false, // 是否支持导出文件
			sortable : true,
			striped : true,// 是否隔行变色
			// queryParams: function(params) {
			// return {
			// // 传递参数查询参数
			// pageSize: params.limit,
			// pageNum: params.offset / params.limit + 1,
			// searchValue: params.search,
			// orderByColumn: params.sort,
			// isAsc: params.order
			// };
			// },
			columns : _columns
		});
	}
	// 初始bootstrap table数据
	// 有column选择，直接data
	$.initTable4 = function(_columns, _data) {
		$('.bootstrap-table').bootstrapTable({
			pagination : true, // 是否显示分页（*）
			pageSize : 10, // 每页的记录行数（*）
			pageNumber : 1, // 初始化加载第一页，默认第一页
			pageList : [ 10, 25, 50, 100 ], // 可供选择的每页的行数（*）
			search : true, // 是否显示搜索框功能
			singleSelect : true, // 是否禁止多选
			iconSize : 'outline', // 图标大小：undefined默认的按钮尺寸 xs超小按钮sm小按钮lg大按钮
			toolbar : '#tableToolbar', // 指定工作栏
			sidePagination : "client", // 启用客户端端分页
			showRefresh : false, // 是否显示刷新按钮
			showColumns : true, // 是否显示隐藏某列下拉框
			showToggle : false, // 是否显示详细视图和列表视图的切换按钮
			cache : false, // 是否使用缓存
			showExport : false, // 是否支持导出文件
			sortable : true,
			striped : true,// 是否隔行变色
			columns : _columns,
			data : _data
		});
	}
	// 初始bootstrap table数据
	// 有column选择，直接data
	$.initTable5 = function(_columns, _data) {
		$('.bootstrap-table').bootstrapTable({
			pagination : true, // 是否显示分页（*）
			pageSize : 10, // 每页的记录行数（*）
			pageNumber : 1, // 初始化加载第一页，默认第一页
			pageList : [ 10, 25, 50, 100 ], // 可供选择的每页的行数（*）
			search : true, // 是否显示搜索框功能
			singleSelect : false, // 是否禁止多选
			iconSize : 'outline', // 图标大小：undefined默认的按钮尺寸 xs超小按钮sm小按钮lg大按钮
			toolbar : '#tableToolbar', // 指定工作栏
			sidePagination : "client", // 启用客户端端分页
			showRefresh : false, // 是否显示刷新按钮
			showColumns : true, // 是否显示隐藏某列下拉框
			showToggle : false, // 是否显示详细视图和列表视图的切换按钮
			cache : false, // 是否使用缓存
			showExport : false, // 是否支持导出文件
			sortable : true,
			striped : true,// 是否隔行变色
			columns : _columns,
			data : _data
		});
	}
	// 初始bootstrap table数据
	// 用于备份代码多字段排序
	$.initTable6 = function(_columns, _url, searchPlaceholder) {
		$('.bootstrap-table').bootstrapTable({
			method : 'get', // 请求方式（*）
			dataType : "json", // 返回格式（*）
			url : _url, // 请求后台的URL（*）
			pagination : true, // 是否显示分页（*）
			pageSize : 10, // 每页的记录行数（*）
			pageNumber : 1, // 初始化加载第一页，默认第一页
			pageList : [ 10, 25, 50, 100, 500 ], // 可供选择的每页的行数（*）
			search : true, // 是否显示搜索框功能
			singleSelect : false, // 是否禁止多选
			iconSize : 'outline', // 图标大小：undefined默认的按钮尺寸 xs超小按钮sm小按钮lg大按钮
			toolbar : '#tableToolbar', // 指定工作栏
			sidePagination : "server", // 启用服务端分页
			showRefresh : true, // 是否显示刷新按钮
			showColumns : true, // 是否显示隐藏某列下拉框
			showToggle : true, // 是否显示详细视图和列表视图的切换按钮
			cache : false, // 是否使用缓存
			showExport : true, // 是否支持导出文件
			striped : true,// 是否隔行变色
			sortable : true, // 是否启用排序
			sortOrder : "asc", // 排序方式
			queryParams : function(params) {
				var sortName = 'spider_customspider_backupcode_id';
				if (params.sort == 'customSpiderBackId') {
					sortName = 'custom_spider_back_id';
				} else if (params.sort == 'backupcodeType') {
					sortName = 'backupcode_type';
				} else if (params.sort == 'spiderVersion') {
					sortName = 'spider_version';
				} else if (params.sort == 'createTime') {
					sortName = 'create_time';
				}
				return {
					// 传递参数查询参数
					pageSize : params.limit,
					pageNum : params.offset / params.limit + 1,
					searchValue : params.search,
					orderByColumn : sortName,
					isAsc : params.order
				};
			},
			columns : _columns
		});

		// 设置提示
		var $search = $('.bootstrap-table').find('.form-control.input-outline');
		$search.attr('placeholder', searchPlaceholder);
	}

	// 初始bootstrap table数据
	// 用于博客显示多字段排序
	$.initTable7 = function(_columns, _url, searchPlaceholder) {
		$('.bootstrap-table').bootstrapTable({
			method : 'get', // 请求方式（*）
			dataType : "json", // 返回格式（*）
			url : _url, // 请求后台的URL（*）
			pagination : true, // 是否显示分页（*）
			pageSize : 10, // 每页的记录行数（*）
			pageNumber : 1, // 初始化加载第一页，默认第一页
			pageList : [ 10, 25, 50, 100, 500 ], // 可供选择的每页的行数（*）
			search : true, // 是否显示搜索框功能
			singleSelect : false, // 是否禁止多选
			iconSize : 'outline', // 图标大小：undefined默认的按钮尺寸 xs超小按钮sm小按钮lg大按钮
			toolbar : '#tableToolbar', // 指定工作栏
			sidePagination : "server", // 启用服务端分页
			showRefresh : true, // 是否显示刷新按钮
			showColumns : true, // 是否显示隐藏某列下拉框
			showToggle : true, // 是否显示详细视图和列表视图的切换按钮
			cache : false, // 是否使用缓存
			showExport : true, // 是否支持导出文件
			striped : true,// 是否隔行变色
			sortable : true, // 是否启用排序
			sortOrder : "desc", // 排序方式
			queryParams : function(params) {
				var sortName = 'gtm_create';
				if (params.sort == 'cid') {
					sortName = 'cid';
				} else if (params.sort == 'gtmModified') {
					sortName = 'gtm_modified';
				} else if (params.sort == 'commentsNum') {
					sortName = 'comments_num';
				} else if (params.sort == 'likeNum') {
					sortName = 'like_num';
				} else if (params.sort == 'browseNum') {
					sortName = 'browse_num';
				} else if (params.sort == 'articleTop') {
					sortName = 'article_top';
				}

				return {
					// 传递参数查询参数
					pageSize : params.limit,
					pageNum : params.offset / params.limit + 1,
					searchValue : params.search,
					orderByColumn : sortName,
					isAsc : params.order
				};
			},
			columns : _columns
		});

		// 设置提示
		var $search = $('.bootstrap-table').find('.form-control.input-outline');
		$search.attr('placeholder', searchPlaceholder);
	}
	// 初始bootstrap table 自定义参数
	$.initTableParams = function(_columns, _url, _queryParams,
			searchPlaceholder) {
		$('.bootstrap-table').bootstrapTable({
			method : 'get', // 请求方式（*）
			dataType : "json", // 返回格式（*）
			url : _url, // 请求后台的URL（*）
			pagination : true, // 是否显示分页（*）
			pageSize : 10, // 每页的记录行数（*）
			pageNumber : 1, // 初始化加载第一页，默认第一页
			pageList : [ 10, 25, 50 ], // 可供选择的每页的行数（*）
			search : true, // 是否显示搜索框功能
			singleSelect : false, // 是否禁止多选
			iconSize : 'outline', // 图标大小：undefined默认的按钮尺寸 xs超小按钮sm小按钮lg大按钮
			toolbar : '#tableToolbar', // 指定工作栏
			sidePagination : "server", // 启用服务端分页
			showRefresh : true, // 是否显示刷新按钮
			showColumns : true, // 是否显示隐藏某列下拉框
			showToggle : true, // 是否显示详细视图和列表视图的切换按钮
			cache : false, // 是否使用缓存
			showExport : true, // 是否支持导出文件
			striped : true,// 是否隔行变色
			queryParams : _queryParams,
			columns : _columns
		});
		// 设置提示
		var $search = $('.bootstrap-table').find('.form-control.input-outline');
		$search.attr('placeholder', searchPlaceholder);
	}
	// 初始化表格树，并展开树
	$.initTreeTable = function(_id, _parentId, _columns, _url) {
		$.initTreeTable(_id, _parentId, _columns, _url, true);
	}
	// 初始化表格树，_expandAll true展开 false 不展开
	$.initTreeTable = function(_id, _parentId, _columns, _url, _expandAll) {
		$('.bootstrap-table').bootstrapTreeTable({
			code : _id, // 用于设置父子关系
			parentCode : _parentId, // 用于设置父子关系
			type : 'get', // 请求方式（*）
			url : _url, // 请求后台的URL（*）
			ajaxParams : {}, // 请求数据的ajax的data属性
			expandColumn : '0', // 在哪一列上面显示展开按钮
			striped : true, // 是否各行渐变色
			bordered : true, // 是否显示边框
			expandAll : _expandAll, // 是否全部展开
			showRefresh : true, // 是否显示刷新按钮
			columns : _columns
		});
	}
	// 刷新bootstrap table数据
	$.refreshTable = function() {
		$('.bootstrap-table').bootstrapTable('refresh');
	}
	// 获取bootstrap table选中项
	$.getSelections = function(_id) {
		return $.map($('.bootstrap-table').bootstrapTable('getSelections'),
				function(row) {
					return row[_id];
				});
	}
	// 获取选中复选框项
	$.getCheckeds = function(_name) {
		var checkeds = "";
		$('input:checkbox[name="' + _name + '"]:checked').each(function(i) {
			if (0 == i) {
				checkeds = $(this).val();
			} else {
				checkeds += ("," + $(this).val());
			}
		});
		return checkeds;
	}
	// 获取选中复选框项
	$.getSelects = function(_name) {
		var selects = "";
		$('#' + _name + ' option:selected').each(function(i) {
			if (0 == i) {
				selects = $(this).val();
			} else {
				selects += ("," + $(this).val());
			}
		});
		return selects;
	}
	// 复选框事件绑定
	if ($.fn.select2 !== undefined) {
		$("select.form-control:not(.noselect2)").each(function() {
			$(this).select2().on("change", function() {
				$(this).valid();
			})
		})
	}
});