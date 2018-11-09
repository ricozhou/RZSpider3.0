var prefix = ctx + "spider/customspider/code"

$(document).ready(function() {
	$('body').layout({
		west__size : 225
	});
	// 初始化
	init();
});

function init() {
	// 初始化文件树结构
	queryFileTreeDaTa();
	initCodeeditor();
	initConsoleEditor();
	initOther();
}

// 初始化其它
var runStatus;
function initOther() {
	// 初始化运行状态
	runStatus = $('#runStatus').val();
	if (runStatus == 0) {
		// 正在运行
		// 初始化运行按钮
		// 显示停止按钮
		$("#stop").css("display", "inline");
		$("#run").css("display", "none");
		// 保存按钮不可用
		$("#save2").css("display", "inline");
		$("#save").css("display", "none");

		// 启动websocket轮询获取后台传递的打印数据

	} else if (runStatus == 1) {
		// 停止中
		$("#stop").css("display", "none");
		$("#run").css("display", "inline");
		document.getElementById("run").setAttribute("disabled", true);
		// 保存按钮不可用
		$("#save2").css("display", "inline");
		$("#save").css("display", "none");
	}
	// 全屏监听
	// 全屏显示
	$('#fullScreen').on('click', function() {
		$('#showfull').fullScreen();
	});
}

var editor;
function initCodeeditor() {
	// 根据编辑器id构造出一个编辑器
	editor = CodeMirror.fromTextArea(document.getElementById("code"), {
		mode : "text/groovy", // 实现groovy代码高亮
		mode : "text/x-java", // 实现Java代码高亮
		lineNumbers : true, // 显示行号
		theme : "dracula", // 设置主题
		lineWrapping : false, // 横向代码有滚动条
		lineWiseCopyCut : true,// 打开复制整行
		dragDrop : false,// 可拖动
		foldGutter : true,
		extraKeys : {
			// 停止运行快捷键
			"Ctrl-Q" : function(cm) {
				// stop可以用的时候
				if ($("#stop").css("display") === 'inline-block') {
					stop();
				}

			},
			// 运行快捷键
			"Ctrl-R" : function(cm) {
				// run显示并且可用的时候
				if ($("#run").css("display") === 'inline-block'
						&& $("#run").prop('disabled') == false) {
					run();
				}
			},
			// 保存快捷键
			"Ctrl-S" : function(cm) {
				// save显示并且可用的时候
				if ($("#save").css("display") === 'inline-block'
						&& $("#save").prop('disabled') == false) {
					saveFile();
				}
			}
		},
		gutters : [ "CodeMirror-linenumbers", "CodeMirror-foldgutter" ],
		matchBrackets : true, // 括号匹配
	});

	editor.setSize('auto', 'auto'); // 设置代码框的长宽
	editor.setValue(""); // 先将代码框的值清空
	// editor.setValue(obj.scriptCode); //给代码框赋值
	editor.setOption("readOnly", true);
};

// 控制台
var consoleEditor;
function initConsoleEditor() {
	// 根据编辑器id构造出一个编辑器
	consoleEditor = CodeMirror
			.fromTextArea(
					document.getElementById("console"),
					{
						// mode
						// :
						// "text/groovy",
						// //
						// 实现groovy代码高亮
						mode : "text/x-java", // 实现Java代码高亮
						lineNumbers : true, // 显示行号
						theme : "dracula", // 设置主题
						lineWrapping : false, // 代码有滚动条
						lineWiseCopyCut : true,// 打开复制整行
						dragDrop : true,// 可拖动
						foldGutter : true,
						extraKeys : {
							// 停止运行快捷键
							"Ctrl-Q" : function(cm) {
								// stop可以用的时候
								if ($("#stop").css("display") === 'inline-block') {
									stop();
								}

							}
						},
						gutters : [ "CodeMirror-linenumbers",
								"CodeMirror-foldgutter" ],
					// matchBrackets : true, // 括号匹配
					});

	// editor.setSize('1000px', '950px'); //设置代码框的长宽
	consoleEditor.setValue(""); // 先将代码框的值清空
	// editor.setValue(obj.scriptCode); //给代码框赋值
	// 不可编辑
	consoleEditor.setOption("readOnly", true);
};

// 查询文件树
function queryFileTreeDaTa() {
	// 树结构初始化加载
	var setting = {
		view : {
			selectedMulti : false,
			// 打开鼠标悬停显示
			showTitle : true
		},
		data : {
			// 鼠标悬停title
			key : {
				title : "title"
			},
			simpleData : {
				enable : true
			}
		},
		callback : {
			// 单击
			onClick : function(event, treeId, treeNode) {
			},
			// 双击
			onDblClick : function(event, treeId, treeNode) {
				// 打开文件
				// 新逻辑，后台直接返回文件的地址，可能出现安全问题，暂时不管，先前的逻辑可以改也可以不改，暂时不改
				if (treeNode != null
						&& treeNode.getParentNode() != null
						&& treeNode.getParentNode().getParentNode() != null
						&& treeNode.getParentNode().getParentNode().name == 'src'
						&& treeNode.name.endWith(".java")) {
					openFileToShow(treeNode);
				}
			},
			// 右击事件
			// 此事件比较复杂，小心谨慎
			onRightClick : function(event, treeId, treeNode) {
				OnRightClick(event, treeId, treeNode)
				// zTreeOnRightClick(event, treeId, treeNode)
			}
		}
	}, tree, loadTree = function() {
		// 判断是否存在
		var data = csisexist();
		if (data.code != 0) {
			$.modalAlert(data.msg, modal_status.FAIL);
			// 关闭标签页
			// closeTab();
			return;
		}
		$.get(prefix + "/treeData", $('#customSpiderId'), function(data) {
			tree = $.fn.zTree.init($("#tree"), setting, data); // .expandAll(true);
			// 展开第一级节点
			var nodes = tree.getNodesByParam("level", 0);
			for (var i = 0; i < nodes.length; i++) {
				tree.expandNode(nodes[i], true, false, false);
			}
			// 展开第二级节点
			nodes = tree.getNodesByParam("level", 1);
			for (var i = 0; i < nodes.length; i++) {
				tree.expandNode(nodes[i], true, false, false);
			}
			// 展开第三级节点
			nodes = tree.getNodesByParam("level", 2);
			for (var i = 0; i < nodes.length; i++) {
				tree.expandNode(nodes[i], true, false, false);
			}
		}, null, null, "正在加载，请稍后...");
	};
	loadTree();

	$('#btnExpand').click(function() {
		tree.expandAll(true);
		$(this).hide();
		$('#btnCollapse').show();
	});
	$('#btnCollapse').click(function() {
		tree.expandAll(false);
		$(this).hide();
		$('#btnExpand').show();
	});
	$('#btnRefresh').click(function() {
		loadTree();
	});
	// 隐藏右键菜单
	hideRMenu();
}

// 在ztree上的右击事件
function OnRightClick(event, treeId, treeNode) {
	if (!treeNode && event.target.tagName.toLowerCase() != "button"
			&& $(event.target).parents("a").length == 0) {
		showRMenu("root", event.clientX, event.clientY, treeNode);
	} else if (treeNode && !treeNode.noR) {
		showRMenu("node", event.clientX, event.clientY, treeNode);
	}
}
var selectTreeNode;
// 显示右键菜单
function showRMenu(type, x, y, treeNode) {
	$("#rMenu ul").show();
	// 根据节点设置哪些右键菜单事件可以显示
	if (treeNode == null) {
		return;
	}
	// 所有都可导出
	// 下载
	$("#exportFile").css('display', 'block');
	if (treeNode.name == 'publiclib' || treeNode.name == 'lib') {
		// 可上传，下载
		$("#importFile").css('display', 'block');
		// 其他禁用
		$("#openFile").css('display', 'none');
		$("#newFile").css('display', 'none');
		$("#deleteFile").css('display', 'none');
	} else if (treeNode.getParentNode() != null
			&& treeNode.getParentNode().name == 'src') {
		// 其他禁用
		$("#importFile").css('display', 'none');
		$("#openFile").css('display', 'none');
		$("#deleteFile").css('display', 'none');
		// 可新建
		$("#newFile").css('display', 'block');
	} else if (treeNode.getParentNode() != null
			&& treeNode.getParentNode().getParentNode() != null
			&& treeNode.getParentNode().getParentNode().name == 'src') {
		// 其他禁用
		$("#importFile").css('display', 'none');
		$("#newFile").css('display', 'none');
		// 可打开，删除
		$("#openFile").css('display', 'block');
		$("#deleteFile").css('display', 'block');
	} else if (treeNode.getParentNode() != null
			&& (treeNode.getParentNode().name == 'lib' || treeNode
					.getParentNode().name == 'publiclib')) {
		// 可上传，下载,删除
		// $("#importFile").css('display', 'block');
		$("#deleteFile").css('display', 'block');
		// 其他禁用
		$("#openFile").css('display', 'none');
		$("#newFile").css('display', 'none');
		$("#importFile").css('display', 'none');
	} else {
		// 其他禁用
		$("#importFile").css('display', 'none');
		$("#openFile").css('display', 'none');
		$("#newFile").css('display', 'none');
		$("#deleteFile").css('display', 'none');
	}

	// 判断权限
	checkPermission();

	$("#rMenu").css({
		"top" : y + "px",
		"left" : x + "px",
		"display" : "block"
	}); // 设置右键菜单的位置、可见
	$("body").bind("mousedown", onBodyMouseDown);
	selectTreeNode = treeNode;
}

// 校验权限
function checkPermission() {
	if (newFileFlag == 'hidden') {
		$("#newFile").css('display', 'none');
	}
	if (deleteFileFlag == 'hidden') {
		$("#deleteFile").css('display', 'none');
	}
	if (importFileFlag == 'hidden') {
		$("#importFile").css('display', 'none');
	}
	if (exportFileFlag == 'hidden') {
		$("#exportFile").css('display', 'none');
	}
}

// 隐藏右键菜单
function hideRMenu() {
	if (rMenu)
		$("#rMenu").css({
			"display" : "none"
		}); // 设置右键菜单不可见
	$("body").unbind("mousedown", onBodyMouseDown);
}
// 鼠标按下事件
function onBodyMouseDown(event) {
	if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length > 0)) {
		$("#rMenu").css({
			"display" : "none"
		});
	}
}

// 右键事件方法
function openFile() {
	if (selectTreeNode == null) {
		return;
	}
	openFileToShow(selectTreeNode);
}
// 右键事件方法
function newFile() {
	// 新建文件
	if (selectTreeNode == null) {
		return;
	}
	// 危险传参
	// 更改，后台新表实时更新文件树，只需要传递childid即可
	// var param = selectTreeNode.filePath.replace(/[\\]/g, "%2F");
	// param = param.replace(/[:]/g, "%3A");
	// console.log(param);

	// 判断是否存在
	var data = csisexist();
	if (data.code != 0) {
		$.modalAlert(data.msg, modal_status.FAIL);
		// 关闭标签页
		// closeTab();
		return;
	}

	// 0表示java文件，1表示python和js
	var url = prefix + '/newFile/0/' + $('#customSpiderId').val() + "/"
			+ selectTreeNode.id;
	layer_showAuto("创建文件", url);
}
// 右键事件方法
function deleteFile() {
	if (selectTreeNode == null) {
		return;
	}
	// 判断是否存在
	var data = csisexist();
	if (data.code != 0) {
		$.modalAlert(data.msg, modal_status.FAIL);
		// 关闭标签页
		// closeTab();
		return;
	}

	// 删除文件
	// 正在运行则不能删除
	// 不显示的时候正在运行
	var runflag = $("#run").css("display") === 'none';
	if (runflag) {
		$.modalAlert('正在运行，无法删除', modal_status.FAIL);
		return;
	}
	// 封装数据
	var data = {
		"customSpiderId" : $('#customSpiderId').val(),
		"childId" : selectTreeNode.id
	}
	// 删除
	$.modalConfirm("确定要删除选中文件吗？", function() {
		_ajax(prefix + "/deleteFile", data, "post");
		// 重新加载
		queryFileTreeDaTa();
	})
}
// 右键事件方法
function exportFile() {
	if (selectTreeNode == null) {
		return;
	}
	// 判断是否存在
	var data = csisexist();
	if (data.code != 0) {
		$.modalAlert(data.msg, modal_status.FAIL);
		// 关闭标签页
		// closeTab();
		return;
	}

	// 封装数据
	var data = {
		"customSpiderId" : $('#customSpiderId').val(),
		"childId" : selectTreeNode.id
	}
	// 导出
	// $.modalConfirm("确定要导出选中文件吗？", function() {
	// _ajax(prefix + "/exportFile", data, "get");
	// // 重新加载
	// queryFileTreeDaTa();
	// })
	location.href = prefix + "/exportFile/" + $('#customSpiderId').val() + "/"
			+ selectTreeNode.id;
	layer.msg('正在下载,请稍后…', {
		icon : 1
	});
	queryFileTreeDaTa();
}

// 导出项目
function exportProject() {
	// 判断是否存在
	var data = csisexist();
	if (data.code != 0) {
		$.modalAlert(data.msg, modal_status.FAIL);
		// 关闭标签页
		// closeTab();
		return;
	}

	location.href = prefix + "/exportProject/" + $('#customSpiderId').val();
	layer.msg('正在下载,请稍后…', {
		icon : 1
	});
	queryFileTreeDaTa();
}
// 右键事件方法
function importFile() {
	// 判断是否存在
	var data = csisexist();
	if (data.code != 0) {
		$.modalAlert(data.msg, modal_status.FAIL);
		// 关闭标签页
		// closeTab();
		return;
	}

	if (selectTreeNode == null) {
		return;
	}
	// 直接绑定上传事件
}

// 绑定上传文件
$(function() {
	layui.use('upload', function() {
		// 判断是否存在
		var data = csisexist();
		if (data.code != 0) {
			$.modalAlert(data.msg, modal_status.FAIL);
			// 关闭标签页
			// closeTab();
			return;
		}
		var upload = layui.upload;
		// 执行实例
		var uploadInst = upload.render({
			elem : '#importFile', // 绑定元素
			url : prefix + "/uploadFile", // 上传接口
			size : 10000,// 不超过10m
			accept : 'file',
			exts : 'jar',
			before : function() {
				// 只有在这里data才生效
				this.data = {
					"customSpiderId" : $('#customSpiderId').val(),
					"childId" : selectTreeNode.id
				}
				// test();
				layer.load();
			},
			done : function(data) {
				if (data.code == 0) {
					// // 禁用按钮
					// document.getElementById("uploadSrcFile").setAttribute(
					// "disabled", true);
					layer.msg("上传成功,请稍后……", {
						icon : 1,
						time : 500,
						shade : [ 0.1, '#fff' ]
					}, function() {
						// srcFileName = data.msg;
						// // 按钮可用
						// document.getElementById("uploadSrcFile")
						// .removeAttribute("disabled");
						// 重新加载
						queryFileTreeDaTa();
					});
				} else {
					$.modalAlert(data.msg, modal_status.FAIL);
				}

			},
		});
	});
});

// 设置入口
function setEntryFile() {
	// 判断是否存在
	var data = csisexist();
	if (data.code != 0) {
		$.modalAlert("爬虫已不存在，请刷新", modal_status.FAIL);
		// 关闭标签页
		// closeTab();
		return;
	}
	var customSpiderBackId = $("#customSpiderBackId").val();
	var entryFileName = $("#name").val();
	entryFileName = entryFileName.substring(0, entryFileName.lastIndexOf('.'));
	$.ajax({
		cache : true,
		type : "POST",
		url : prefix + "/setEntryFile",
		data : {
			"customSpiderBackId" : customSpiderBackId,
			"entryFileName" : entryFileName
		},
		async : false,
		error : function(request) {
			$.modalAlert("系统错误", modal_status.FAIL);
		},
		success : function(data) {
			if (data.code == 0) {
				// 显示出名字
				$("#fileName").html(entryFileName + ".java（入口）");
				// 设置样式
				$("#fileName").css("color", "red");
				// 由于此页面设置完入口后并没有重新加载，所以手动设置一下隐藏框
				$('#entryFileName').val(entryFileName);
				layer.msg("设置成功", {
					icon : 1,
					time : 500,
					shade : [ 0.1, '#fff' ]
				});
			} else {
				$.modalAlert(data.msg, modal_status.FAIL);
			}
		}
	});
}

// 备份此代码
function backupCodeToDB(flag) {
	// 判断是否存在
	var data = csisexist();
	if (data.code != 0) {
		$.modalAlert("爬虫已不存在，请刷新", modal_status.FAIL);
		// 关闭标签页
		// closeTab();
		return;
	}
	var customSpiderBackId = $("#customSpiderBackId").val();
	var fileName = $("#name").val();
	$.ajax({
		cache : true,
		type : "POST",
		url : ctx + "spider/customspider/backupcode/backupCodeToDB",
		data : {
			"customSpiderBackId" : customSpiderBackId,
			"flag" : flag,
			"fileName" : fileName
		},
		async : false,
		error : function(request) {
			$.modalAlert("系统错误", modal_status.FAIL);
		},
		success : function(data) {
			if (flag == 0) {
				// 自动备份的不再提示
				return;
			}
			if (data.code == 0) {
				layer.msg("备份成功", {
					icon : 1,
					time : 500,
					shade : [ 0.1, '#fff' ]
				});
			} else {
				$.modalAlert(data.msg, modal_status.FAIL);
			}
		}
	});
}

// 双击打开
// 此方法已过时，后续再改进，可直接传文件路径后台打开，但是比较危险
function openFileToShow(treeNode) {
	// 判断是否存在
	var data = csisexist();
	if (data.code != 0) {
		$.modalAlert("爬虫已不存在，请刷新", modal_status.FAIL);
		// 关闭标签页
		// closeTab();
		return;
	}

	// 双击事件打开文件(非文件夹)
	// 将相关信息放入
	$("#childId").val(treeNode.id);
	$("#parentId").val(treeNode.pId);
	$("#name").val(treeNode.name);
	// 可以设置为入口
	$("#setEntryFile").css('display', 'block');
	if (($("#entryFileName").val() + ".java") == treeNode.name) {
		// 如果是入口
		// 显示出名字
		$("#fileName").html(treeNode.name + "（入口）");
		// 设置样式
		$("#fileName").css("color", "red");
	} else {
		// 显示出名字
		$("#fileName").html(treeNode.name);
		// 设置样式
		$("#fileName").css("color", "black");
	}

	// 可以备份此代码
	$("#backupCodeToDB").css('display', 'block');
	// 获取id，pid，name，spiderbackid，content
	var customSpiderBackId = $("#customSpiderBackId").val();
	var childId = treeNode.id;
	var parentId = treeNode.pId;
	var fileName = treeNode.name;
	var filePath = treeNode.filePath;
	$.ajax({
		cache : true,
		type : "POST",
		url : prefix + "/getFile",
		data : {
			"customSpiderBackId" : customSpiderBackId,
			"childId" : childId,
			"parentId" : parentId,
			"fileName" : fileName,
			"filePath" : filePath
		},
		async : false,
		error : function(request) {
			$.modalAlert("系统错误", modal_status.FAIL);
		},
		success : function(data) {
			if (data != '' && data.status == 0) {
				editor.setValue('');
				editor.setValue($.trim(data.fileContent)); // 给代码框赋值
				// 超过30行加入滚动条限制高度
				if (editor.lineCount() > 30) {
					editor.setSize('auto', $(window).height() * 0.6);
				} else {
					editor.setSize('auto', 'auto');
				}
				editor.setOption("readOnly", false);
				document.getElementById("run").setAttribute("disabled", true);
				$("#save2").css("display", "none");
				$("#save").css("display", "inline");
				// 开启监听
				startListenCodeChanage($.trim(data.fileContent));
			} else {
				$.modalAlert('找不到文件或文件为空', modal_status.FAIL);
			}
		}
	});
}
// 监听文本域变化
function startListenCodeChanage(tValue) {
	// 设置定时器监听文本框变化
	setInterval(function(event) {
		if ($.trim(tValue) != $.trim(editor.getValue())) {
			// 出现变化则更改保存编译按钮可用
			$("#save2").css("display", "none");
			$("#save").css("display", "inline");
			// 运行按钮不可用
			document.getElementById("run").setAttribute("disabled", true);
			tValue = $.trim(editor.getValue());
		}
	}, 200);
}

// 保存文件
function saveFile() {
	// 判断是否存在
	var data = csisexist();
	if (data.code != 0) {
		$.modalAlert(data.msg, modal_status.FAIL);
		// 关闭标签页
		// closeTab();
		return;
	}

	// 首先需要检验类名是否正确
	var fileName = $("#name").val();
	var fileContent = $.trim(editor.getValue());
	$.ajax({
		cache : true,
		type : "POST",
		url : prefix + "/checkClassName",
		data : {
			"fileName" : fileName,
			"fileContent" : fileContent
		},
		async : false,
		error : function(request) {
			$.modalAlert("系统错误", modal_status.FAIL);
		},
		success : function(data) {
			if (data.code == 0) {
				// 类名正确再开始保存编译
				startSaveFile();
			} else {
				$.modalAlert(data.msg, modal_status.FAIL);
			}
		}
	});

}
// 开始校验
function startSaveFile() {
	// 获取id，pid，name，spiderbackid，content
	var customSpiderBackId = $("#customSpiderBackId").val();
	var childId = $("#childId").val();
	var parentId = $("#parentId").val();
	var fileName = $("#name").val();
	var fileContent = $.trim(editor.getValue());
	$.ajax({
		cache : true,
		type : "POST",
		url : prefix + "/saveFile",
		data : {
			"customSpiderBackId" : customSpiderBackId,
			"childId" : childId,
			"parentId" : parentId,
			"fileName" : fileName,
			"fileContent" : fileContent
		},
		async : true,
		error : function(request) {
			$.modalAlert("系统错误", modal_status.FAIL);
		},
		beforeSend : function() {
			// 禁用按钮
			document.getElementById("run").setAttribute("disabled", true);
			// 保存按钮不可用
			$("#save2").css("display", "inline");
			$("#save").css("display", "none");
			layer.msg("正在保存编译,请稍后……", {
				icon : 1,
				time : 0,
				shade : [ 0.1, '#fff' ]
			});
		},
		success : function(data) {
			if (data.code == 0) {
				// 运行按钮可用
				document.getElementById("run").removeAttribute("disabled");
				// 保存按钮不可用
				$("#save2").css("display", "inline");
				$("#save").css("display", "none");
				// 关闭提示窗
				layer.closeAll('dialog');
				// 重新加载
				queryFileTreeDaTa();
				// 自动保存备份
				backupCodeToDB(0);
			} else {
				// 出错后保存可用运行不能用
				$("#save2").css("display", "none");
				$("#save").css("display", "inline");
				// 运行按钮不可用
				document.getElementById("run").setAttribute("disabled", true);
				$.modalAlert(data.msg, modal_status.FAIL);
			}
		},
	// // 完成后取消禁用
	// complete : function() {
	// // 关闭提示窗
	// layer.closeAll('dialog');
	// },
	});
}

// 运行文件
function run() {
	// 判断是否存在
	var data = csisexist();
	if (data.code != 0) {
		$.modalAlert(data.msg, modal_status.FAIL);
		// 关闭标签页
		// closeTab();
		return;
	}

	// 获取项目名，运行的文件名
	// 首先去判读此文件内是否有main方法然后再运行并返回运行结果
	// 获取id，pid，name，spiderbackid，content
	var customSpiderBackId = $("#customSpiderBackId").val();
	var childId = $("#childId").val();
	var parentId = $("#parentId").val();
	var fileName = $("#name").val();
	$.ajax({
		cache : true,
		type : "POST",
		url : prefix + "/run",
		data : {
			"customSpiderBackId" : customSpiderBackId,
			"childId" : childId,
			"parentId" : parentId,
			"fileName" : fileName
		},
		async : true,
		error : function(request) {
			$.modalAlert("系统错误", modal_status.FAIL);
		},
		beforeSend : function() {
			// 启动websocket轮询获取后台传递的打印数据
			startWebsocket(customSpiderBackId);
			// 禁用按钮
			// document.getElementById("run").setAttribute("disabled", true);
			// 显示停止按钮
			$("#stop").css("display", "inline");
			$("#run").css("display", "none");
			// 保存按钮不可用
			$("#save2").css("display", "inline");
			$("#save").css("display", "none");
			layer.msg("正在运行,请稍后……", {
				icon : 1,
				time : 500,
				shade : [ 0.1, '#fff' ]
			});
		},
		success : function(data) {
			if (data.status == 0) {
				runStatus = 0;
				// 运行按钮不可用
				// 停止按钮可用
				// 显示停止按钮
				$("#stop").css("display", "inline");
				$("#run").css("display", "none");
				document.getElementById("stop").removeAttribute("disabled");
				document.getElementById("run").setAttribute("disabled", true);
				// 关闭提示窗
				layer.closeAll('dialog');

			} else if (data.status == 1) {
				// 运行按钮可用
				// 显示运行按钮
				runStatus = 1;
				$("#stop").css("display", "none");
				$("#run").css("display", "inline");
				document.getElementById("run").removeAttribute("disabled");
				$.modalAlert('没有找到main方法', modal_status.FAIL);
			} else if (data.status == 2) {
				// 运行按钮可用
				// 显示运行按钮
				runStatus = 1;
				$("#stop").css("display", "none");
				$("#run").css("display", "inline");
				document.getElementById("run").removeAttribute("disabled");
				$.modalAlert('运行失败', modal_status.FAIL);
			} else if (data.status == 3) {
				runStatus = 0;
				// 运行按钮可用
				// 显示运行按钮
				$.modalAlert('正在运行，请刷新', modal_status.FAIL);
				return;
			} else if (data.status == 4) {
				$.modalAlert('测试管理员禁用此功能', modal_status.FAIL);
				return;
			} else {
				runStatus = 1;
				// 运行按钮可用
				// 显示运行按钮
				$("#stop").css("display", "none");
				$("#run").css("display", "inline");
				document.getElementById("run").removeAttribute("disabled");
				$.modalAlert('未知错误', modal_status.FAIL);
			}

			// 保存按钮不可用
			$("#save2").css("display", "inline");
			$("#save").css("display", "none");

		},
	// // 完成后取消禁用
	// complete : function() {
	// // 关闭提示窗
	// layer.closeAll('dialog');
	// },
	});

}

// 中止运行
function stop() {
	// 判断是否存在
	var data = csisexist();
	if (data.code != 0) {
		$.modalAlert(data.msg, modal_status.FAIL);
		// 关闭标签页
		// closeTab();
		return;
	}

	// 获取项目名，运行的文件名
	// 首先去判读此文件内是否有main方法然后再运行并返回运行结果
	// 获取id，pid，name，spiderbackid，content
	var customSpiderBackId = $("#customSpiderBackId").val();
	var childId = $("#childId").val();
	var parentId = $("#parentId").val();
	var fileName = $("#name").val();
	$.ajax({
		cache : true,
		type : "POST",
		url : prefix + "/stop",
		data : {
			"customSpiderBackId" : customSpiderBackId,
			"childId" : childId,
			"parentId" : parentId,
			"fileName" : fileName
		},
		async : true,
		error : function(request) {
			$.modalAlert("系统错误", modal_status.FAIL);
		},
		beforeSend : function() {
			// 禁用按钮
			document.getElementById("stop").setAttribute("disabled", true);
			document.getElementById("run").setAttribute("disabled", true);
			layer.msg("正在中止,请稍后……", {
				icon : 1,
				time : 0,
				shade : [ 0.1, '#fff' ]
			});
		},
		success : function(data) {
			if (data.status == 0) {
				runStatus = 1;
				// 运行按钮可用
				// 显示运行按钮
				$("#stop").css("display", "none");
				$("#run").css("display", "inline");
				document.getElementById("run").removeAttribute("disabled");
				document.getElementById("stop").setAttribute("disabled", true);
				// 关闭提示窗
				layer.closeAll('dialog');
			} else if (data.status == 4) {
				$.modalAlert('测试管理员禁用此功能', modal_status.FAIL);
				return;
			} else {
				runStatus = 0;
				document.getElementById("stop").removeAttribute("disabled");
				document.getElementById("run").setAttribute("disabled", true);
				$.modalAlert('中止失败', modal_status.FAIL);
			}
		},
	// // 完成后取消禁用
	// complete : function() {
	// // 关闭提示窗
	// layer.closeAll('dialog');
	// },
	});

}

// 因为是另起的面板，需要控制下万一被删除
// 判断自定义爬虫是否存在
function csisexist() {
	var data2;
	var customSpiderBackId = $("#customSpiderBackId").val();
	$.ajax({
		cache : true,
		type : "POST",
		url : ctx + "spider/customspider/checkCSExist",
		data : {
			"customSpiderBackId" : customSpiderBackId
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

// 清空控制台
function clearConsole() {
	if (runStatus != 0) {
		consoleEditor.setValue("");
		consoleEditor.setSize('auto', 'auto');
	}
}

// 判断以什么字符串结尾
String.prototype.endWith = function(endStr) {
	var d = this.length - endStr.length;
	return (d >= 0 && this.lastIndexOf(endStr) == d)
}
