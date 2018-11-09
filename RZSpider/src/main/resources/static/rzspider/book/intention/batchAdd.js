var prefix = ctx + "book/intention"

var columns = [ {
	field : 'bookName',
	title : '图书名称'
}, {
	field : 'bookAuthor',
	title : '图书作者'
}, {
	field : 'bookPublisher',
	title : '图书出版社'
}, {
	field : 'bookDes',
	title : '图书简介'
}, {
	field : 'bookOriginalPrice',
	title : '图书定价'
}, {
	field : 'bookPublishTime',
	title : '图书出版日期'
}, {
	field : 'bookClassification',
	title : '图书分类'
}, {
	field : 'bookBinding',
	title : '图书装帧'
}, {
	field : 'bookPages',
	title : '图书页数'
}, {
	field : 'bookReadStar',
	title : '购买星级'
}];
$(function() {
	layui.use('upload', function() {
		var upload = layui.upload;
		// 执行实例
		var uploadInst = upload.render({
			elem : '#uploadExcel', // 绑定元素
			url : '/book/intention/batchAnalyzeList', // 上传接口
			size : 10000,// 不超过10m
			accept : 'file',
			exts: 'xls|xlsx|XLS|XLSX',
			done : function(data) {
				if (data.code == 0) {
					document.getElementById("uploadExcel").setAttribute(
							"disabled", true);
					layer.msg("批量上传成功,正在刷新数据请稍后……", {
						icon : 1,
						time : 500,
						shade : [ 0.1, '#fff' ]
					}, function() {
						var url = prefix + "/batchAnalyzeList2";
						$.initTable2(columns, url);
						// 保存按钮可用
						document.getElementById("batchSave").removeAttribute(
								"disabled");
					});
				} else {
					$.modalAlert(data.msg, modal_status.FAIL);
				}

			},
		});
	});
});

/* 下载模板 */
function downExcelTemplate() {
	location.href = prefix + "/downExcelTemplate";
	layer.msg('正在下载模板,请稍后…', {
		icon : 1
	});
}

/* 批量保存 */
function batchSave() {
	$
			.ajax({
				cache : true,
				type : "POST",
				url : ctx + "book/intention/batchSave",
				async : true,
				error : function(request) {
					$.modalAlert("系统错误", modal_status.FAIL);
				},
				beforeSend : function() {
					// 禁用按钮防止重复提交
					document.getElementById("batchSave").setAttribute(
							"disabled", true);
					parent.layer.msg("正在批量保存,请稍后……", {
						icon : 1,
						time : 0,
						shade : [ 0.1, '#fff' ]
					});
				},

				success : function(data) {
					if (data.code == 0) {
						parent.layer.msg("批量保存成功,正在刷新数据请稍后……", {
							icon : 1,
							time : 500,
							shade : [ 0.1, '#fff' ]
						}, function() {
							$.parentReload();
						});
					} else {
						$.modalAlert(data.msg, modal_status.FAIL);
					}

				},
				// 完成后取消禁用
				complete : function() {
					document.getElementById("batchSave").removeAttribute(
							"disabled");
					// 关闭提示窗
					layer.closeAll('dialog');
				},
			});
}
