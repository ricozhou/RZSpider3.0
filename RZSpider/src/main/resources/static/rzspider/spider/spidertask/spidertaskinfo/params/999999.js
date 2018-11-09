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
			+ value + '" disabled>';
	if (type == 1) {
		// int
		valueHtml = '<div class="input-group spinner" data-trigger="spinner"><input type="text" class="form-control text-center" value="'
				+ value
				+ '" data-rule="quantity" data-max="1000000000" data-min="-1000000000" id="valueInput" name="valueInput" disabled><div class="input-group-addon"><a href="javascript:;" class="spin-up" data-spin="up"><i class="fa fa-caret-up"></i></a> <a href="javascript:;" class="spin-down" data-spin="down"><i class="fa fa-caret-down"></i></a></div></div>';
	} else if (type == 2) {
		// double
		valueHtml = '<div class="input-group spinner" data-trigger="spinner"><input type="text" class="form-control text-center" value="'
				+ value
				+ '" data-rule="currency" data-max="1000000000.00" data-min="-1000000000.00" id="valueInput" name="valueInput" disabled><div class="input-group-addon"><a href="javascript:;" class="spin-up" data-spin="up"><i class="fa fa-caret-up"></i></a> <a href="javascript:;" class="spin-down" data-spin="down"><i class="fa fa-caret-down"></i></a></div></div>';
	} else if (type == 3) {
		// boolean,使用bootstrap switch开关控件
		valueHtml = '<div class="switch" data-on="primary" data-off="info"><input id="valueInput" name="valueCheckbox'
				+ index + '" type="checkbox"/></div>';
	} else if (type == 4) {
		// date
		valueHtml = '<div class="input-group date"><span class="input-group-addon"><i class="fa fa-calendar"></i></span><input type="text" class="form-control" value="'
				+ value
				+ '" id="valueInput" name="valueInput" disabled/></div>';
	} else if (type == 5) {
		// enum下拉框
		startHtml = '<select class="form-control" id="valueInput" name="valueInput" disabled>';
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
		// 不可编辑
		$("[name='valueCheckbox" + index + "']").bootstrapSwitch('disabled',
				true);
	}
}
