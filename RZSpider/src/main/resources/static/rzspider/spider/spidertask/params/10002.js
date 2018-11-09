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
		},
		// 校验时间格式
		startDateTime : {
			required : true,
			checkDateTime : true,
			// 自定义方法
			checkDateSize : true
		},
		endDateTime : {
			required : true,
			checkDateTime : true,
			// 自定义方法
			checkDateSize : true
		},
		// 校验价格
		startPrice : {
			required : true,
			digits : true,
			min : 0,
			max : 99999999,
			// 自定义方法
			checkPrice : true
		},
		endPrice : {
			required : true,
			digits : true,
			min : 0,
			max : 99999999,
			checkPrice : true
		},
		// 校验土地面积
		startArea : {
			required : true,
			digits : true,
			min : 0,
			max : 99999999,
			// 自定义方法
			checkArea : true
		},
		endArea : {
			required : true,
			digits : true,
			min : 0,
			max : 99999999,
			checkArea : true
		},
	},
	messages : {},
	submitHandler : function(form) {
		update();
	}
});

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

function checkbox1UnChecked() {
	$("#startDateTime").val("");
	$("#endDateTime").val("");
	document.getElementById("startDateTime").removeAttribute("disabled");
	document.getElementById("endDateTime").removeAttribute("disabled");
}
function checkbox2UnChecked() {
	$("#startPrice").val("");
	$("#endPrice").val("");
	document.getElementById("startPrice").removeAttribute("disabled");
	document.getElementById("endPrice").removeAttribute("disabled");
}
function checkbox3UnChecked() {
	$("#startArea").val("");
	$("#endArea").val("");
	document.getElementById("startArea").removeAttribute("disabled");
	document.getElementById("endArea").removeAttribute("disabled");
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

// 绑定监听事件

$(function() {
	// 此地方注意，复选框被包裹在label内，所以click事件绑定label的
	$('#clickLabel1').click(function() {
		var checkbox1 = document.getElementById("checkbox1");
		if (checkbox1.checked) {
			checkbox1UnChecked();
		} else {
			checkbox1Checked();
		}
	});
	$('#clickIns1').click(function() {
		var checkbox1 = document.getElementById("checkbox1");
		if (checkbox1.checked) {
			checkbox1UnChecked();
		} else {
			checkbox1Checked();
		}
	});
	$('#clickLabel2').click(function() {
		var checkbox2 = document.getElementById("checkbox2");
		if (checkbox2.checked) {
			checkbox2UnChecked();
		} else {
			checkbox2Checked();
		}
	});
	$('#clickIns2').click(function() {
		var checkbox2 = document.getElementById("checkbox2");
		if (checkbox2.checked) {
			checkbox2UnChecked();
		} else {
			checkbox2Checked();
		}
	});
	$('#clickLabel3').click(function() {
		var checkbox3 = document.getElementById("checkbox3");
		if (checkbox3.checked) {
			checkbox3UnChecked();
		} else {
			checkbox3Checked();
		}
	});
	$('#clickIns3').click(function() {
		var checkbox3 = document.getElementById("checkbox3");
		if (checkbox3.checked) {
			checkbox3UnChecked();
		} else {
			checkbox3Checked();
		}
	});
});

// 拼接字符串然后更新设置参数
function update() {
	// id
	var taskId = $("input[name='taskId']").val();
	// web
	var websiteId = $("#websiteId").val();
	// page
	var startPage = $("input[name='startPage']").val();
	var endPage = $("input[name='endPage']").val();
	// time
	var checkbox1 = document.getElementById("checkbox1");
	var startDateTime = '';
	var endDateTime = '';
	// 如果不限
	if (checkbox1.checked) {
		startDateTime = "1992-09-09";
		endDateTime = "9999-09-09";
	} else {
		startDateTime = $("input[name='startDateTime']").val();
		endDateTime = $("input[name='endDateTime']").val();
	}
	// price
	var checkbox2 = document.getElementById("checkbox2");
	var startPrice = '';
	var endPrice = '';
	if (checkbox2.checked) {
		startPrice = "0";
		endPrice = "99999999";
	} else {
		startPrice = $("input[name='startPrice']").val();
		endPrice = $("input[name='endPrice']").val();
	}

	// area
	var checkbox3 = document.getElementById("checkbox3");
	var startArea = '';
	var endArea = '';
	if (checkbox3.checked) {
		startArea = "0";
		endArea = "99999999";
	} else {
		startArea = $("input[name='startArea']").val();
		endArea = $("input[name='endArea']").val();
	}
	// landuse
	var landUse = $("#landUse").val();
	var getDetails = $("#13").is(':checked') == true ? 'true' : 'false';
	var data = '{"websiteId":' + websiteId + ',"startPage":' + startPage
			+ ',"endPage":' + endPage + ',"startDateTime":"' + startDateTime
			+ '","endDateTime":"' + endDateTime + '","startPrice":'
			+ startPrice + ',"endPrice":' + endPrice + ',"startArea":'
			+ startArea + ',"endArea":' + endArea + ',"landUse":"' + landUse
			+ '","getDetails":' + getDetails + '}';
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
