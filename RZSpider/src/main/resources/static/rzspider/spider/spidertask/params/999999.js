var prefix = ctx + "spider/spidertask"
$("#form-spidertask-params").validate({
	rules : {},
	messages : {},
	submitHandler : function(form) {
		update();
	}
});

// 一开始便加载
$(document).ready(function() {
	// 初始化显示参数
	// 解析json
	var spiderDefaultParamsAll2 = $('#spiderDefaultParamsAll').val();
	var obj = "{}";
	if (spiderDefaultParamsAll2.replace(/(^s*)|(s*$)/g, "").length != 0) {
		obj = JSON.parse(spiderDefaultParamsAll2);
		if (obj != null) {
			if (obj.length != 0) {
				// 遍历添加已存在的参数
				for ( var index in obj) {
					des = obj[index].des;
					key = obj[index].key;
					value = obj[index].value;
					type = obj[index].type;
					// 写到网页中
					dynamicAddSpiderDefaultParams(des, key, value, type, index)
				}
			}
		}
	}

})
// 动态添加
function dynamicAddSpiderDefaultParams(des, key, value, type, index) {
	// 根据type动态显示参数
	// 字符串和int都用input
	valueHtml = '<input id="valueInput" name="valueInput" class="form-control" type="text" value="'
			+ value + '">';
	if (type == 1) {
		// int
		valueHtml = '<div class="input-group spinner" data-trigger="spinner"><input type="text" class="form-control text-center" value="'
				+ value
				+ '" data-rule="quantity" data-max="1000000000" data-min="-1000000000" id="valueInput" name="valueInput"><div class="input-group-addon"><a href="javascript:;" class="spin-up" data-spin="up"><i class="fa fa-caret-up"></i></a> <a href="javascript:;" class="spin-down" data-spin="down"><i class="fa fa-caret-down"></i></a></div></div>';
	} else if (type == 2) {
		// double
		valueHtml = '<div class="input-group spinner" data-trigger="spinner"><input type="text" class="form-control text-center" value="'
				+ value
				+ '" data-rule="currency" data-max="1000000000.00" data-min="-1000000000.00" id="valueInput" name="valueInput"><div class="input-group-addon"><a href="javascript:;" class="spin-up" data-spin="up"><i class="fa fa-caret-up"></i></a> <a href="javascript:;" class="spin-down" data-spin="down"><i class="fa fa-caret-down"></i></a></div></div>';
	} else if (type == 3) {
		// boolean,使用bootstrap switch开关控件
		valueHtml = '<div class="switch" data-on="primary" data-off="info"><input id="valueInput" name="valueCheckbox'
				+ index + '" type="checkbox"/></div>';
	} else if (type == 4) {
		// date
		valueHtml = '<div class="input-group date"><span class="input-group-addon"><i class="fa fa-calendar"></i></span><input type="text" class="form-control" value="'
				+ value + '" id="valueInput" name="valueInput" /></div>';
	} else if (type == 5) {
		// enum下拉框
		startHtml = '<select class="form-control" id="valueInput" name="valueInput">';
		middleHtml = '';
		endHtml = '</select>';
		// 循环添加
		// 分割
		var enumArray = new Array();
		enumArray = value.split("#");
		var enumArray2 = new Array();
		if (enumArray.length > 1) {
			for (var i = 1; i < enumArray.length; i++) {
				enumArray2 = enumArray[i].split("=");
				if (enumArray[0] == enumArray2[0]) {
					// 选中
					middleHtml = middleHtml + '<option value="' + enumArray2[0]
							+ '" selected>' + enumArray2[1] + '</option>';
				} else {
					middleHtml = middleHtml + '<option value="' + enumArray2[0]
							+ '">' + enumArray2[1] + '</option>';
				}
			}
		}
		valueHtml = startHtml
				+ middleHtml
				+ endHtml
				+ '<input id="valueEnum" name="valueEnum" class="form-control" type="hidden" value="'
				+ value + '">';
	}
	html = '<div class="form-group inputParams"><label class="col-sm-3 control-label"></label><div class="col-sm-3">'
			+ '<label class="control-label" id="desInput" name="desInput">'
			+ des
			+ '：</label></div>'
			+ '<div class="col-sm-5">'
			+ '<input id="keyInput" name="keyInput" class="form-control" type="hidden" value="'
			+ key
			+ '">'
			+ valueHtml
			+ '<input id="typeSelect" name="typeSelect" class="form-control" type="hidden" value="'
			+ type + '">' + '</div></div>'
	// 添加到增加参数按钮之前
	document.getElementById('params').insertAdjacentHTML('beforebegin', html)

	// 动态设置值
	if (type == 3) {
		// 添加完之后初始化开关控件
		$("[name='valueCheckbox" + index + "']").bootstrapSwitch({
			onText : 'true',
			offText : 'false'
		});
		// 设置原有值
		if (value == 'true') {
			$("[name='valueCheckbox" + index + "']").bootstrapSwitch('state',
					true);
		} else {
			$("[name='valueCheckbox" + index + "']").bootstrapSwitch('state',
					false);
		}
	}
}

// 更新设置参数
function update() {
	// id
	var taskId = $("input[name='taskId']").val();
	// 修改前判断自定义爬虫是否存在，是否可用
	// 判断是否存在
	var data = csisexist(taskId);
	if (data.code != 0) {
		$.modalAlert(data.msg, modal_status.FAIL);
		$.refreshTable();
		// 关闭标签页
		// closeTab();
		return;
	}
	// 以下开始参数获取
	var spiderDefaultParamsAll = '';
	var taskParams = '';
	// 拼接默认参数为json
	var des = '';
	var key = '';
	var array = new Array();
	var num = 0;
	var success = true;
	var map = new Map();
	var paramMap = new Map();
	// 特殊字符限制限制
	var regEn = /[`~!@#$%^&*()+<>?:"{},\/;'[\]\s]/im, regCn = /[·！#￥（——）：；“”‘、，|《。》？、【】[\]]/im;
	var regOnlyNum = /^[0-9]*$/;
	var regOnlyDouble = /^[-+]?(\d+(\.\d*)?|\.\d+)([eE]([-+]?([012]?\d{1,2}|30[0-7])|-3([01]?[4-9]|[012]?[0-3])))?[dD]?$/;
	var regDate = /^((?:19|20)\d\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/;
	// 遍历所有的参数
	$(".inputParams")
			.each(
					function() {
						map = new Map();
						des = $(this).find("#desInput").html();
						// 去掉问号
						des = des.substring(0, des.length - 1);
						key = $(this).find("#keyInput").val();
						value = $(this).find("#valueInput").val();
						type = $(this).find("#typeSelect").val();
						if (type == 3) {
							value = $(this).find("#valueInput").is(':checked') == true ? 'true'
									: 'false';
						}
						// 验证
						// 长度限制，类型等
						// 长度限制
						if (value.length > 15) {
							$.modalAlert('字符串超过规定长度', modal_status.FAIL);
							success = false;
							return;
						}
						// 特殊字符限制限制
						if (regEn.test(value) || regCn.test(value)) {
							$.modalAlert('不能携带特殊字符', modal_status.FAIL);
							success = false;
							return;
						}
						// 类型验证
						if (type == 1) {
							// int
							if (!regOnlyNum.test(value)) {
								$.modalAlert('value只能是数字', modal_status.FAIL);
								success = false;
								return;
							}
							// 判断大小
							if (parseInt(value) < -1000000000
									|| parseInt(value) > 1000000000) {
								$.modalAlert('value值应在正负10亿之间',
										modal_status.FAIL);
								success = false;
								return;
							}
						} else if (type == 2) {
							// double
							if (!regOnlyDouble.test(value)) {
								$.modalAlert('value只能是小数', modal_status.FAIL);
								success = false;
								return;
							}
						} else if (type == 4) {
							// date
							if (!regDate.test(value)) {
								$.modalAlert('value日期格式为2018-08-16',
										modal_status.FAIL);
								success = false;
								return;
							}
						} else if (type == 5) {
							// enum
							valueEnumAll = $(this).find("#valueEnum").val();
							valueEnumAll = value
									+ valueEnumAll.substring(valueEnumAll
											.indexOf('#'));
						}

						map.put('des', des);
						map.put('key', key);

						if (type == 5) {
							map.put('value', valueEnumAll);
						} else {
							map.put('value', value);
						}
						map.put('type', type);
						array[num] = map.data;
						// key value纯参数单独存放
						paramMap.put(key, value);
						num++;
					});

	if (!success) {
		return;
	}

	// 设置参数
	if (array.length > 0) {
		spiderDefaultParamsAll = JSON.stringify(array);
	}
	if (!paramMap.isEmpty()) {
		taskParams = JSON.stringify(paramMap.data);
	}
	// 发送请求
	$.ajax({
		cache : true,
		type : "POST",
		url : ctx + "spider/spidertask/saveParams",
		data : {
			"taskId" : taskId,
			"spiderDefaultParamsAll" : spiderDefaultParamsAll,
			"taskParams" : taskParams
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
// 判断爬虫是否存在
function csisexist(taskId) {
	var data2;
	$.ajax({
		cache : true,
		type : "POST",
		url : prefix + "/checkSpiderExist",
		data : {
			"taskId" : taskId
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