var prefix = ctx + "tool/baseset/detailedit/musiclist"

$("#form-musiclist-searchAdd").validate({
	rules : {
		musicName : {
			required : true,
			maxlength : 20
		// 校验特殊字符
		},
	},
	submitHandler : function(form) {
		searchAdd();
	}
});

// 表头
var columns = [
		{
			checkbox : true
		},
		{
			field : 'id',
			align : 'center',
			title : '歌曲id'
		},
		{
			field : 'title',
			align : 'center',
			title : '单曲',
			formatter : function(value, row, index) {

				return '<a  href="#" title="试听" mce_href="#" onclick="tryListen2(\''
						+ row.url + '\')">' + value + '</a>';
			}
		},
		{
			field : 'author',
			align : 'center',
			title : '歌手'
		},
		{
			field : 'albumName',
			align : 'center',
			title : '专辑'
		},
		{
			field : 'musicTime',
			align : 'center',
			title : '时长'
		},
		{
			field : 'pic',
			align : 'center',
			visible : true,
			formatter : function(value, row, index) {
				var link = value;
				if (link != null && link != '') {
					return '<a href="'
							+ link
							+ '" target="_Blank"><img width="50px" height="50px" src="'
							+ link + '"></img></a>';
				}

			}
		},
		{
			title : '操作',
			align : 'center',
			formatter : function(value, row, index) {
				var actions = [];
				actions
						.push('<a class="btn btn-primary btn-xs" href="#" title="试听" mce_href="#" onclick="tryListen(\''
								+ row.id
								+ '\')"><i class="fa fa-play"></i>试听</a> ');
				actions
						.push('<a class="btn btn-warning btn-xs" href="#" title="添加" onclick="add(\''
								+ row.id
								+ '\',\''
								+ row.title
								+ '\',\''
								+ row.author
								+ '\',\''
								+ row.pic
								+ '\')"><i class="fa fa-plus"></i>添加</a>');
				return actions.join('');
			}
		} ];

function searchAdd() {
	var musicName = $.trim($("#musicName").val());
	$.ajax({
		cache : true,
		type : "POST",
		url : prefix + "/searchMusic",
		data : {
			"musicName" : musicName
		},
		async : false,
		error : function(request) {
			$.modalAlert("系统错误", modal_status.FAIL);
		},
		success : function(data) {
			if (data != null) {
				// 重置表结构先
				$(".bootstrap-table").bootstrapTable('destroy');
				// 直接column和data初始化table
				$.initTable5(columns, data);
				// 显示批量添加按钮
				if (data != null && data != '') {
					$("#batchAdd2").css("display", "inline");
				} else {
					$("#batchAdd2").css("display", "none");
				}
			} else {
				$.modalAlert(data.msg, modal_status.FAIL);
			}

		}
	});

}

// 试听
function tryListen(id) {
	html = '<iframe frameborder="no" border="0" marginwidth="0" marginheight="0" width=330 height=86 src="//music.163.com/outchain/player?type=2&id='
			+ id + '&auto=1&height=66"></iframe>';
	layer.open({
		title : '试听',
		type : 1,
		area : [ 'auto', 'auto' ],
		content : html,
	});

}
// 试听
function tryListen2(url) {
	html = '<video controls autoplay name="media"><source src="' + url
			+ '" type="audio/mpeg"></video>';
	layer.open({
		title : '试听',
		type : 1,
		area : [ 'auto', 'auto' ],
		content : html,
	});

}
// 单条添加
function add(id, title, author, pic) {
	// 判断是否存在
	var data = csisexist(basesetId);
	if (data.code != 0) {
		$.modalAlert(data.msg, modal_status.FAIL);
		$.parentReload();
		// 关闭标签页
		// closeTab();
		return;
	}
	$.modalConfirm("确定要添加选中歌曲吗？", function() {
		$.ajax({
			cache : true,
			type : "POST",
			url : prefix + "/searchAdd/add",
			data : {
				"basesetId" : basesetId,
				"musicId" : id,
				"title" : title,
				"author" : author,
				"pic" : pic
			},
			async : false,
			error : function(request) {
				$.modalAlert("系统错误", modal_status.FAIL);
			},
			success : function(data) {
				if (data.code == 0) {
					parent.layer.msg("添加成功", {
						icon : 1,
						time : 500,
						shade : [ 0.1, '#fff' ]
					});
				} else {
					$.modalAlert(data.msg, modal_status.FAIL);
				}
			}
		});
		// 关闭提示窗
		layer.closeAll('dialog');
	})
}

// 批量添加
function batchAdd() {
	// 判断是否存在
	var data = csisexist(basesetId);
	if (data.code != 0) {
		$.modalAlert(data.msg, modal_status.FAIL);
		$.parentReload();
		// 关闭标签页
		// closeTab();
		return;
	}
	var musicIds = $.getSelections("id");
	var titles = $.getSelections("title");
	var authors = $.getSelections("author");
	var pics = $.getSelections("pic");
	if (musicIds.length == 0) {
		$.modalMsg("请选择要添加的数据", "warning");
		return;
	}
	$.modalConfirm("确认要添加选中的" + musicIds.length + "条数据吗?", function() {
		_ajax(prefix + '/searchAdd/batchAdd', {
			"basesetId" : basesetId,
			"musicIds" : musicIds,
			"titles" : titles,
			"authors" : authors,
			"pics" : pics
		}, "post");
	});
}
// 判断主题是否存在
function csisexist(basesetId) {
	var data2;
	$.ajax({
		cache : true,
		type : "POST",
		url : ctx + "tool/baseset/checkBasesetExist",
		data : {
			"basesetId" : basesetId
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