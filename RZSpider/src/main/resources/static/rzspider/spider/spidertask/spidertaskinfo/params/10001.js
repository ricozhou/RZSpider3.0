// 一开始便加载
$(document).ready(function() {
	// 获取数据库中的参数
	var taskParams = $("#taskParams").val();
	var targetAddressProvince = "";
	var targetAddressCity = "";
	var targetType = "";
	var auctionStatus = "";
	var startPage = "";
	var endPage = "";
	var getDetails = false;
	var obj = "{}";
	if (taskParams.replace(/(^s*)|(s*$)/g, "").length != 0) {
		obj = JSON.parse(taskParams);
	}
	if (obj != null) {
		if (obj.length != 0) {
			targetAddressProvince = obj["targetAddressProvince"];
			targetAddressCity = obj["targetAddressCity"];
			targetType = obj["targetType"];
			auctionStatus = obj["auctionStatus"];
			startPage = obj["startPage"];
			endPage = obj["endPage"];
			getDetails = obj["getDetails"];
		}
	}
	// 设置标的物类型
	if (targetType != undefined) {
		var targetTypeObj = document.getElementById("targetType");
		for (var i = 0; i < targetTypeObj.options.length; i++) {
			var tmp = targetTypeObj.options[i].value;
			if (tmp == targetType) {
				targetTypeObj.options[i].selected = 'selected';
				break;
			}
		}
	}
	// 设置拍卖状态
	if (auctionStatus != undefined) {
		var auctionStatusObj = document.getElementById("auctionStatus");
		for (var i = 0; i < auctionStatusObj.options.length; i++) {
			var tmp = auctionStatusObj.options[i].value;
			if (tmp == auctionStatus) {
				auctionStatusObj.options[i].selected = 'selected';
				break;
			}
		}
	}

	// 页数
	if (startPage != undefined) {
		$("#startPage").val(startPage);
	}
	if (endPage != undefined) {
		$("#endPage").val(endPage);
	}

	// 设置是否爬取详细信息
	if (getDetails != undefined) {
		if (getDetails == true) {
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

	// 设置省市联动
	// 动态显示省市信息
	$("#province").find("option").remove();
	for (var i = 0; i < ChineseDistricts[86].length; i++) {
		var newOption = document.createElement("option");
		newOption.text = ChineseDistricts[86][i];
		newOption.value = ChineseDistricts[86][i];
		document.getElementById("province").add(newOption);
		if (targetAddressProvince == newOption.value) {
			newOption.selected = 'selected';
		}
	}
	if (targetAddressCity == undefined) {
		$("#city").append("<option value='不限'>不限</option>");
	} else {
		bingCity(ChineseDistricts[targetAddressProvince], targetAddressCity);
	}
})
// 动态绑定
function bingCity(data, targetAddressCity) {
	// 动态显示省市信息
	$("#city").find("option").remove();
	for (var i = 0; i < data.length; i++) {
		var newOption = document.createElement("option");
		newOption.text = data[i];
		newOption.value = data[i];
		document.getElementById("city").add(newOption);
		if (targetAddressCity == newOption.value) {
			newOption.selected = 'selected';
		}
	}
}
//打开网页
function openWebsite() {
	var web = "https://sf.taobao.com/list/0.htm?spm=a213w.7398504.filter.1.RxqQ5o&auction_start_seg=-1";
	window.open(web);
}