$("#form-spidertask-params").validate({
	rules : {
		startPage : {
			required : true,
			digits : true,
			min : 1,
			max : 100000,
			// 自定义方法
			checkStartEndPage : true
		},
		endPage : {
			required : true,
			digits : true,
			min : 1,
			max : 100000,
			checkStartEndPage : true
		}
	},
	messages : {},
	submitHandler : function(form) {
		update();
	}
});

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

// 绑定监听事件
$(function() {
	$('#province').bind('change', function() {
		var province = $(this).val();
		bingCity(ChineseDistricts[province], '')
	});
});

// 拼接字符串然后更新设置参数
function update() {
	var taskId = $("input[name='taskId']").val();
	var targetType = $("#targetType").val();
	var auctionStatus = $("#auctionStatus").val();
	var targetAddressProvince = $("#province").val();
	var targetAddressCity = $("#city").val();
	var startPage = $("input[name='startPage']").val();
	var endPage = $("input[name='endPage']").val();
	var getDetails = $("#13").is(':checked') == true ? 'true': 'false';
	var data = '{ "targetType":"' + targetType + '","auctionStatus":"'
			+ auctionStatus + '","targetAddressProvince":"'
			+ targetAddressProvince + '","targetAddressCity":"'
			+ targetAddressCity + '","startPage":' + startPage + ',"endPage":'
			+ endPage + ',"getDetails":' + getDetails + '}';
	$.ajax({
		cache : true,
		type : "POST",
		url : ctx + "spider/spidertask/saveParams",
		data : {
			"taskId" : taskId,
			"taskParams" : data
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
//打开网页
function openWebsite() {
	var web = "https://sf.taobao.com/list/0.htm?spm=a213w.7398504.filter.1.RxqQ5o&auction_start_seg=-1";
	window.open(web);
}