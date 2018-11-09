// 一开始便加载
$(document).ready(function() {
	// 获取数据库中的参数
	var taskParams = $("#taskParams").val();
	var fitmentSelect = "";
	var projectArea = "";
	var houseMinPrice = "";
	var houseMaxPrice = "";
	var houseUse = "";
	var houseMinArea = "";
	var houseMaxArea = "";
	var projectName = "";
	var companyName = "";
	var pageNumber = "";
	var obj = "{}";
	if (taskParams.replace(/(^s*)|(s*$)/g, "").length != 0) {
		obj = JSON.parse(taskParams);
	}
	if (obj != null) {
		if (obj.length != 0) {
			fitmentSelect = obj["fitmentSelect"];
			projectArea = obj["projectArea"];
			houseMinPrice = obj["houseMinPrice"];
			houseMaxPrice = obj["houseMaxPrice"];
			houseUse = obj["houseUse"];
			houseMinArea = obj["houseMinArea"];
			houseMaxArea = obj["houseMaxArea"];
			projectName = obj["projectName"];
			companyName = obj["companyName"];
			pageNumber = obj["pageNumber"];
		}
	}
	// 装修情况
	if (fitmentSelect != undefined) {
		var fitmentSelectObj = document.getElementById("fitmentSelect");
		for (var i = 0; i < fitmentSelectObj.options.length; i++) {
			var tmp = fitmentSelectObj.options[i].value;
			if (tmp == fitmentSelect) {
				fitmentSelectObj.options[i].selected = 'selected';
				break;
			}
		}
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
	// 房屋用途
	if (houseUse != undefined) {
		var houseUseObj = document.getElementById("houseUse");
		for (var i = 0; i < houseUseObj.options.length; i++) {
			var tmp = houseUseObj.options[i].value;
			if (tmp == houseUse) {
				houseUseObj.options[i].selected = 'selected';
				break;
			}
		}
	}
	// 房屋总价
	if (houseMinPrice != undefined) {
		$("#houseMinPrice").val(houseMinPrice);
	}
	// 房屋总价
	if (houseMaxPrice != undefined) {
		$("#houseMaxPrice").val(houseMaxPrice);
	}
	// 房屋面积
	if (houseMinArea != undefined) {
		$("#houseMinArea").val(houseMinArea);
	}
	// 房屋面积
	if (houseMaxArea != undefined) {
		$("#houseMaxArea").val(houseMaxArea);
	}
	// 项目名称
	if (projectName != undefined) {
		$("#projectName").val(projectName);
	}
	// 公司名称
	if (companyName != undefined) {
		$("#companyName").val(companyName);
	}
	// 页数
	if (pageNumber != undefined) {
		$("#pageNumber").val(pageNumber);
	}
})

// 打开网页
function openWebsite() {
	var web = "http://spf.szfcweb.com/szfcweb/DataSerach/CanSaleHouseSelectIndex.aspx";
	window.open(web);
}
