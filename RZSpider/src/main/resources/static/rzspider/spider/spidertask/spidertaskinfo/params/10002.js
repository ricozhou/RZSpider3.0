// 一开始便加载
$(document)
		.ready(
				function() {
					// 获取数据库中的参数
					var taskParams = $("#taskParams").val();
					var websiteId = "";
					var startPage = "";
					var endPage = "";
					var startDateTime = "";
					var endDateTime = "";
					var startPrice = "";
					var endPrice = "";
					var startArea = "";
					var endArea = "";
					var landUse = "";
					var getDetails = false;
					var obj = "{}";
					if (taskParams.replace(/(^s*)|(s*$)/g, "").length != 0) {
						obj = JSON.parse(taskParams);
					}
					if (obj != null) {
						if (obj.length != 0) {
							websiteId = obj["websiteId"];
							startPage = obj["startPage"];
							endPage = obj["endPage"];
							startDateTime = obj["startDateTime"];
							endDateTime = obj["endDateTime"];
							startPrice = obj["startPrice"];
							endPrice = obj["endPrice"];
							startArea = obj["startArea"];
							endArea = obj["endArea"];
							landUse = obj["landUse"];
							getDetails = obj["getDetails"];
						}
					}
					// 设置目标网站
					if (websiteId != undefined) {
						var websiteIdObj = document.getElementById("websiteId");
						for (var i = 0; i < websiteIdObj.options.length; i++) {
							var tmp = websiteIdObj.options[i].value;
							if (tmp == websiteId) {
								websiteIdObj.options[i].selected = 'selected';
								break;
							}
						}
					}

					// 爬取页数
					if (startPage != undefined) {
						$("#startPage").val(startPage);
					}
					if (endPage != undefined) {
						$("#endPage").val(endPage);
					}

					// 设置时间条件
					if (startDateTime != undefined
							&& endDateTime != undefined
							&& (startDateTime != '1992-09-09' || endDateTime != '9999-09-09')) {
						$("#startDateTime").val(startDateTime);
						$("#endDateTime").val(endDateTime);
					} else {
						// 否则通通认为是不限
						document.getElementById("checkbox1").checked = true;
						checkbox1Checked();
					}
					// 设置起始价格
					if (startPrice != undefined && endPrice != undefined
							&& (startPrice != '0' || endPrice != '99999999')) {
						$("#startPrice").val(startPrice);
						$("#endPrice").val(endPrice);
					} else {
						// 否则通通认为是不限
						document.getElementById("checkbox2").checked = true;
						checkbox2Checked();
					}
					// 设置土地面积
					if (startArea != undefined && endArea != undefined
							&& (startArea != '0' || endArea != '99999999')) {
						$("#startArea").val(startArea);
						$("#endArea").val(endArea);
					} else {
						// 否则通通认为是不限
						document.getElementById("checkbox3").checked = true;
						checkbox3Checked();
					}
					// 设置土地用途
					if (landUse != undefined) {
						var landUseObj = document.getElementById("landUse");
						for (var i = 0; i < landUseObj.options.length; i++) {
							var tmp = landUseObj.options[i].value;
							if (tmp == landUse) {
								landUseObj.options[i].selected = 'selected';
								break;
							}
						}
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
				})

function checkbox1Checked() {
	$("#startDateTime").val("");
	$("#endDateTime").val("");
	document.getElementById("startDateTime").setAttribute("disabled", true);
	document.getElementById("endDateTime").setAttribute("disabled", true);
}
function checkbox2Checked() {
	$("#startPrice").val("");
	$("#endPrice").val("");
	document.getElementById("startPrice").setAttribute("disabled", true);
	document.getElementById("endPrice").setAttribute("disabled", true);
}
function checkbox3Checked() {
	$("#startArea").val("");
	$("#endArea").val("");
	document.getElementById("startArea").setAttribute("disabled", true);
	document.getElementById("endArea").setAttribute("disabled", true);
}

// 打开网页
function openWebsite() {
	var websiteId = $("#websiteId").val();
	var web = "http://www.baidu.com";
	if (websiteId == 0) {
		web = 'http://www.landjs.com/web/gygg_list.aspx?gglx=1';
	} else if (websiteId == 1) {
		web = 'http://land.zjgtjy.cn/GTJY_ZJ/go_home';
	} else if (websiteId == 2) {
		web = 'http://www.hngtjy.cn/home.jsp';
	} else {
		web = 'http://www.baidu.com';
	}
	window.open(web);
}
