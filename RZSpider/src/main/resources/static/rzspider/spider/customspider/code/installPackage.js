var prefix = ctx + "spider/customspider/code";
$("#form-pyjs-installPackage").validate({
	rules : {
		installPackageName : {
			required : true,
			minlength : 1,
			maxlength : 20
		}
	},
	messages : {},
	submitHandler : function(form) {
		installPackageSave();
	}
});

// 绑定监听事件
$(function() {
	var flag = $('#flag').val();
	// 动态显示需要的包
	$("#installPackageNameChoice").find("option").remove();
	var option = '';
	if (flag == 0) {
		// py
		option = '<option value="0" selected="selected">请选择</option><option value="Scrapy">Scrapy（爬虫框架）</option><option value="beautifulsoup4">beautifulsoup4（html标签解析框架）</option><option value="lxml">lxml（html标签解析）</option><option value="PyMySQL">PyMySQL（mysql数据库）</option><option value="urllib">urllib（爬取网页）</option>'
	} else if (flag == 1) {
		// js
		option = '<option value="0" selected="selected">请选择</option><option value="cheerio">cheerio（html标签解析）</option><option value="mysql">mysql（mysql数据库）</option>'
	}
	// 添加
	document.getElementById('installPackageNameChoice').insertAdjacentHTML(
			'afterBegin', option)

	$('#installPackageNameChoice').bind('change', function() {
		var installPackageName = $(this).val();
		if (installPackageName == 0) {
			installPackageName = '';
		}
		$("#installPackageName").val(installPackageName);
	});
});

function installPackageSave() {
	var customSpiderId = $("#customSpiderId").val();
	var childId = $("#childId").val();
	// 0是python，1是js
	var flag = $("#flag").val();
	console.log(flag)
	// 库包名
	var installPackageName = $("#installPackageName").val();
	$.ajax({
		cache : true,
		type : "POST",
		url : prefix + "/installPackageSave",
		data : {
			"customSpiderId" : customSpiderId,
			"childId" : childId,
			"fileName" : installPackageName,
			"flag" : flag
		},
		async : true,
		error : function(request) {
			$.modalAlert("系统错误", modal_status.FAIL);
		},
		beforeSend : function() {
			// 禁用按钮防止重复提交
			document.getElementById("installPackageSave").setAttribute(
					"disabled", true);
			layer.msg("正在安装,请稍后……", {
				icon : 7,
				time : 0,
				shade : [ 0.1, '#fff' ]
			});
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("安装包库成功,正在刷新数据请稍后……", {
					icon : 1,
					time : 500,
					shade : [ 0.1, '#fff' ]
				}, function() {
					layer.closeAll('dialog');
					$.parentReload();
				});
			} else {
				$.modalAlert(data.msg, modal_status.FAIL);
			}
		},
	// // 完成后取消禁用
	// complete : function() {
	// document.getElementById("installPackageSave").removeAttribute(
	// "disabled");
	// console.log(23);
	// // 关闭提示窗
	// layer.closeAll('dialog');
	// $.parentReload();
	// }
	});
}