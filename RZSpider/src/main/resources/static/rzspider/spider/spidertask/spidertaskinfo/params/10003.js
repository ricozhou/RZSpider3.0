// 一开始便加载
$(document).ready(function() {
	// 获取数据库中的参数
	var taskParams = $("#taskParams").val();
	var selectPattern = "";
	var projectName = "";
	var projectArea = "";
	var companyName = "";
	var pageNumber = "";
	var isAllData = false;
	var obj = "{}";
	if (taskParams.replace(/(^s*)|(s*$)/g, "").length != 0) {
		obj = JSON.parse(taskParams);
	}
	if (obj != null) {
		if (obj.length != 0) {
			selectPattern = obj["selectPattern"];
			projectName = obj["projectName"];
			projectArea = obj["projectArea"];
			companyName = obj["companyName"];
			pageNumber = obj["pageNumber"];
			isAllData = obj["isAllData"];
		}
	}
	// 设置模式
	if (selectPattern != undefined) {
		var selectPatternObj = document.getElementById("selectPattern");
		for (var i = 0; i < selectPatternObj.options.length; i++) {
			var tmp = selectPatternObj.options[i].value;
			if (tmp == selectPattern) {
				selectPatternObj.options[i].selected = 'selected';
				break;
			}
		}
	}
	// 页数
	if (pageNumber != undefined) {
		$("#pageNumber").val(pageNumber);
	}

	// 设置是否爬取详细信息
	if (isAllData != undefined) {
		if (isAllData == true) {
			document.getElementById("13").checked = true;
			document.getElementById("14").checked = false;
		} else {
			document.getElementById("14").checked = true;
			document.getElementById("13").checked = false;
		}
	} else {
		document.getElementById("14").checked = true;
		document.getElementById("13").checked = false;
	}

	// 设置项目区域
	if (projectArea != undefined) {
		var projectAreaObj = document.getElementById("projectArea");
		for (var i = 0; i < projectAreaObj.options.length; i++) {
			var tmp = projectAreaObj.options[i].value;
			if (tmp == projectArea) {
				projectAreaObj.options[i].selected = 'selected';
				break;
			}
		}
	}

	// 项目名称
	if (projectName != undefined) {
		$("#projectName").val(projectName);
	}
	// 公司名称
	if (companyName != undefined) {
		$("#companyName").val(companyName);
	}

	// 设置显示问题
	var s = $('#selectPattern').val();
	if (s == 0) {
		controSP1Online();
	} else if (s == 1) {
		controSP2Online();
	}
})

// 绑定监听事件
$(function() {
	$('#selectPattern').bind('change', function() {
		var selectPattern = $(this).val();
		if (selectPattern == 0) {
			controSP1Online();
		} else if (selectPattern == 1) {
			controSP2Online();
		}
	});
});

function controSP1Online() {
	$("#show2").css('display', 'block');
	$("#show3").css('display', 'none');
	$("#show4").css('display', 'none');
	$("#show5").css('display', 'none');
}
function controSP2Online() {
	$("#show2").css('display', 'none');
	$("#show3").css('display', 'block');
	$("#show4").css('display', 'block');
	$("#show5").css('display', 'block');
}
// 打开网页
function openWebsite() {
	var web = "http://spf.szfcweb.com/szfcweb/DataSerach/SaleInfoProListIndex.aspx";
	window.open(web);
}
