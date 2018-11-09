$("#form-toolmanage-edit").validate({
	rules : {
		toolName : {
			required : true,
			maxlength : 50,
			remote : {
				url : ctx + "commontool/toolmanage/checkToolNameUnique",
				type : "post",
				dataType : "json",
				data : {
					"toolId" : function() {
						return $("#toolId").val();
					},
					"toolName" : function() {
						return $.trim($("#toolName").val());
					}
				},
				dataFilter : function(data, type) {
					if (data == "0")
						return true;
					else
						return false;
				}
			}
		},
		toolDes : {
			maxlength : 500
		},
	},
	messages : {
		"toolName" : {
			maxlength : '最长50字',
			remote : "工具名字必须唯一"
		},
		"toolDes" : {
			maxlength : "最长500字",
		}
	},
	submitHandler : function(form) {
		update();
	}
});
// 一开始便加载
$(document).ready(function() {
	// 调整按钮显示
	setButtonUploadSrcFile();
	setButtonUploadExecexeFile();
	setButtonUploadSetupexeFile()
})

// 绑定状态变化以便于隐藏显示上传按钮
$(function() {
	$('#srcDownloadStatus').bind('change', function() {
		setButtonUploadSrcFile()
	});
	$('#execexeDownloadStatus').bind('change', function() {
		setButtonUploadExecexeFile()
	});
	$('#setupexeDownloadStatus').bind('change', function() {
		setButtonUploadSetupexeFile()
	});
});

// 按钮状态函数
function setButtonUploadSrcFile() {
	var srcDownloadStatus = $('#srcDownloadStatus').is(':checked');
	// 选中则可用
	if (srcDownloadStatus) {
		document.getElementById("uploadSrcFile").removeAttribute("disabled");
	} else {
		document.getElementById("uploadSrcFile").setAttribute("disabled", true);
	}
}
// 按钮状态函数
function setButtonUploadExecexeFile() {
	var execexeDownloadStatus = $('#execexeDownloadStatus').is(':checked');
	// 选中则可用
	if (execexeDownloadStatus) {
		document.getElementById("uploadExecexeFile")
				.removeAttribute("disabled");
	} else {
		document.getElementById("uploadExecexeFile").setAttribute("disabled",
				true);
	}
}
// 按钮状态函数
function setButtonUploadSetupexeFile() {
	var setupexeDownloadStatus = $('#setupexeDownloadStatus').is(':checked');
	// 选中则可用
	if (setupexeDownloadStatus) {
		document.getElementById("uploadSetupexeFile").removeAttribute(
				"disabled");
	} else {
		document.getElementById("uploadSetupexeFile").setAttribute("disabled",
				true);
	}
}

var srcFileName;
var execexeFileName;
var setupexeName;
// 绑定上传文件
$(function() {
	layui.use('upload', function() {
		var upload = layui.upload;
		// 执行实例
		var uploadInst = upload.render({
			elem : '#uploadSrcFile', // 绑定元素
			url : '/commontool/toolmanage/uploadToolFile', // 上传接口
			size : 100000,// 不超过100m
			accept : 'file',
			exts : 'zip|rar|7z',
			before : function() {
				// 只有在这里data才生效
				this.data = {
					"toolName" : $("#toolName").val(),
					"toolBackId" : $("#toolBackId").val()
				}
				// test();
				layer.load();
			},
			done : function(data) {
				if (data.code == 0) {
					// 禁用按钮
					document.getElementById("uploadSrcFile").setAttribute(
							"disabled", true);
					layer.msg("上传成功,请稍后……", {
						icon : 1,
						time : 500,
						shade : [ 0.1, '#fff' ]
					}, function() {
						srcFileName = data.msg;
						// 按钮可用
						document.getElementById("uploadSrcFile")
								.removeAttribute("disabled");
					});
				} else {
					$.modalAlert(data.msg, modal_status.FAIL);
				}

			},
		});
		// 执行实例
		var uploadInst2 = upload.render({
			elem : '#uploadExecexeFile', // 绑定元素
			url : '/commontool/toolmanage/uploadToolFile', // 上传接口
			size : 100000,// 不超过100m
			accept : 'file',
			exts : 'zip|rar|7z',
			before : function() {
				// 只有在这里data才生效
				this.data = {
					"toolName" : $("#toolName").val(),
					"toolBackId" : $("#toolBackId").val()
				}
				// test();
				layer.load();
			},
			done : function(data) {
				if (data.code == 0) {
					// 禁用按钮
					document.getElementById("uploadExecexeFile").setAttribute(
							"disabled", true);
					layer.msg("正在上传,请稍后……", {
						icon : 1,
						time : 500,
						shade : [ 0.1, '#fff' ]
					}, function() {
						execexeFileName = data.msg;
						// 按钮可用
						document.getElementById("uploadExecexeFile")
								.removeAttribute("disabled");
					});
				} else {
					$.modalAlert(data.msg, modal_status.FAIL);
				}

			},
		});
		// 执行实例
		var uploadInst3 = upload.render({
			elem : '#uploadSetupexeFile', // 绑定元素
			url : '/commontool/toolmanage/uploadToolFile', // 上传接口
			data : {
				id : function() {
					return $('#id').val();
				}
			},
			size : 100000,// 不超过100m
			accept : 'file',
			exts : 'zip|rar|7z',
			before : function() {
				// 只有在这里data才生效
				this.data = {
					"toolName" : $("#toolName").val(),
					"toolBackId" : $("#toolBackId").val()
				}
				// test();
				layer.load();
			},
			done : function(data) {
				if (data.code == 0) {
					// 禁用按钮
					document.getElementById("uploadSetupexeFile").setAttribute(
							"disabled", true);
					layer.msg("正在上传,请稍后……", {
						icon : 1,
						time : 500,
						shade : [ 0.1, '#fff' ]
					}, function() {
						setupexeName = data.msg;
						// 按钮可用
						document.getElementById("uploadSetupexeFile")
								.removeAttribute("disabled");
					});
				} else {
					$.modalAlert(data.msg, modal_status.FAIL);
				}
			},
		});
	});
});

// 添加
function update() {
	var toolId = $("#toolId").val();
	var toolName = $("#toolName").val();
	var toolBackId = $("#toolBackId").val();
	var toolDes = $("#toolDes").val();
	var toolType = $("#19").is(':checked') == true ? 0 : 1
	var toolCodeType = $("#radio1").is(':checked') == true ? 0 : ($("#radio2")
			.is(':checked') == true ? 1
			: ($("#radio3").is(':checked') == true ? 2 : 3));
	var srcDownloadStatus = $("#srcDownloadStatus").is(':checked') == true ? 0
			: 1;
	var execexeDownloadStatus = $("#execexeDownloadStatus").is(':checked') == true ? 0
			: 1;
	var setupexeDownloadStatus = $("#setupexeDownloadStatus").is(':checked') == true ? 0
			: 1;
	var status = $("#status").is(':checked') == true ? 0 : 1;
	var remark = $("input[name='remark']").val();
	$.ajax({
		cache : true,
		type : "POST",
		url : ctx + "commontool/toolmanage/save",
		data : {
			"toolId" : toolId,
			"toolName" : toolName,
			"toolBackId" : toolBackId,
			"toolDes" : toolDes,
			"toolType" : toolType,
			"toolCodeType" : toolCodeType,
			"srcDownloadStatus" : srcDownloadStatus,
			"execexeDownloadStatus" : execexeDownloadStatus,
			"setupexeDownloadStatus" : setupexeDownloadStatus,
			"status" : status,
			"remark" : remark,
			"srcFileName" : srcFileName,
			"execexeFileName" : execexeFileName,
			"setupexeName" : setupexeName
		},
		async : false,
		error : function(request) {
			$.modalAlert("系统错误", modal_status.FAIL);
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("修改成功,正在刷新数据请稍后……", {
					icon : 1,
					time : 500,
					shade : [ 0.1, '#fff' ]
				}, function() {
					$.parentReload();
				});
			} else {
				$.modalAlert(data.msg, modal_status.FAIL);
			}

		}
	});
}