$("#form-customspider-edit")
		.validate(
				{
					rules : {
						customSpiderBackId : {
							required : true,
							digits : true,
							min : 20000,
							max : 99999,
							remote : {
								url : ctx
										+ "spider/customspider/checkCustomSpiderBackIdUnique",
								type : "post",
								dataType : "json",
								data : {
									"customSpiderId" : function() {
										return $.trim($("#customSpiderId")
												.val());
									},
									"customSpiderBackId" : function() {
										return $.trim($("#customSpiderBackId")
												.val());
									}
								},
								dataFilter : function(data, type) {
									if (data == "0")
										return true;
									else
										return false;
								}
							}
						},
						spiderDes : {
							maxlength : 100
						}

					},
					messages : {
						"customSpiderBackId" : {
							remote : "此爬虫已经存在",
						}
					},
					submitHandler : function(form) {
						update();
					}
				});

// 一开始便加载
$(document).ready(function() {
	// 显示版本号
	var spiderVersionArray = $('#spiderVersion').val().split('.');
	// 设置
	$('#spiderVersionOne').val(spiderVersionArray[0]);
	$('#spiderVersionTwo').val(spiderVersionArray[1]);
	$('#spiderVersionThree').val(spiderVersionArray[2]);
	// 查询所有爬虫代码类型
	$.ajax({
		cache : true,
		type : "GET",
		url : ctx + "spider/customspider/selectSpiderCodeType",
		async : false,
		error : function(request) {
			$.modalAlert("系统错误", modal_status.FAIL);
		},
		success : function(data) {
			if (data != null) {
				// 动态显示爬虫类型
				$("#customSpiderType").find("option").remove();
				for (var i = 0; i < data.length; i++) {
					var newOption = document.createElement("option");
					newOption.text = data[i].spiderCodeTypeName;
					newOption.value = data[i].customSpiderType;
					document.getElementById("customSpiderType").add(newOption);
					if ($('#customSpiderType2').val() == newOption.value) {
						newOption.selected = 'selected';
					}
				}
			} else {
				$.modalAlert(data.msg, modal_status.FAIL);
			}

		}
	});

	// 初始化显示参数
	// 解析json
	var spiderDefaultParamsAll2 = $('#spiderDefaultParamsAll2').val();
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
					dynamicAddSpiderDefaultParams(des, key, value, type)
				}
			}
		}
	}

})

// 动态添加
function dynamicAddSpiderDefaultParams(des, key, value, type) {
	// key 和 type由于可能已用于生产，则不再可以更改
	select = '<select class="form-control" id="typeSelect" disabled><option value="0">String</option><option  value="1">int</option><option  value="2">double</option><option  value="3">boolean</option><option  value="4">date</option><option  value="5">enum</option></select>';
	if (type == 1) {
		select = '<select class="form-control" id="typeSelect" disabled><option value="0">String</option><option  value="1" selected>int</option><option  value="2">double</option><option  value="3">boolean</option><option  value="4">date</option><option  value="5">enum</option></select>';
	} else if (type == 2) {
		select = '<select class="form-control" id="typeSelect" disabled><option value="0">String</option><option  value="1">int</option><option  value="2" selected>double</option><option  value="3">boolean</option><option  value="4">date</option><option  value="5">enum</option></select>';
	} else if (type == 3) {
		select = '<select class="form-control" id="typeSelect" disabled><option value="0">String</option><option  value="1">int</option><option  value="2">double</option><option  value="3" selected>boolean</option><option  value="4">date</option><option  value="5">enum</option></select>';
	} else if (type == 4) {
		select = '<select class="form-control" id="typeSelect" disabled><option value="0">String</option><option  value="1">int</option><option  value="2">double</option><option  value="3">boolean</option><option  value="4" selected>date</option><option  value="5">enum</option></select>';
	} else if (type == 5) {
		select = '<select class="form-control" id="typeSelect" disabled><option value="0">String</option><option  value="1">int</option><option  value="2">double</option><option  value="3">boolean</option><option  value="4" >date</option><option  value="5" selected>enum</option></select>';
	}
	html = '<div class="input-group inputParams">'
			+ '<label class="input-group-addon">描述</label>'
			+ '<input type="text" class="form-control" id="desInput" value="'
			+ des
			+ '">'
			+ '<label class="input-group-addon">key</label>'
			+ '<input type="text" class="form-control" id="keyInput" readonly value="'
			+ key
			+ '">'
			+ '<label class="input-group-addon">value</label>'
			+ '<input type="text" class="form-control" id="valueInput" value="'
			+ value
			+ '">'
			+ '<label class="input-group-addon">type</label>'
			+ select
			+ '<span class="input-group-btn">'
			+ '<button class="btn btn-info" type="button" data-toggle="tooltip" title="删除" id="delSpiderDefaultParams"><span class="glyphicon glyphicon-minus"></span></button>'
			+ '</span>' + '</div>';
	// 添加到增加参数按钮之前
	document.getElementById('addSpiderDefaultParams2').insertAdjacentHTML(
			'beforebegin', html)
}

// 绑定参数添加
function addSpiderDefaultParams(obj) {
	html = '<div class="input-group inputParams">'
			+ '<label class="input-group-addon">描述</label>'
			+ '<input type="text" class="form-control" id="desInput">'
			+ '<label class="input-group-addon">key</label>'
			+ '<input type="text" class="form-control" id="keyInput">'
			+ '<label class="input-group-addon">value</label>'
			+ '<input type="text" class="form-control" id="valueInput">'
			+ '<label class="input-group-addon">type</label>'
			+ '<select class="form-control" id="typeSelect"><option value="0">String</option><option  value="1">int</option><option  value="2">double</option><option  value="3">boolean</option><option  value="4">date</option><option  value="5">enum</option></select>'

			+ '<span class="input-group-btn">'
			+ '<button class="btn btn-info" type="button" data-toggle="tooltip" title="删除" id="delSpiderDefaultParams"><span class="glyphicon glyphicon-minus"></span></button>'
			+ '</span>' + '</div>'
	obj.insertAdjacentHTML('beforebegin', html)
}

// 绑定删除
$(document).on('click', '#delSpiderDefaultParams', function() {
	var el = this.parentNode.parentNode
	$.modalConfirm("确定要删除此参数？所有参数key更改将会对爬虫产生影响，请慎重！", function(e) {
		if (e) {
			el.parentNode.removeChild(el)
		}
		// 关闭弹窗
		layer.closeAll('dialog');
	})
})

function update() {
	var customSpiderId = $("input[name='customSpiderId']").val();
	var customSpiderBackId = $("input[name='customSpiderBackId']").val();
	var spiderVersion = $("#spiderVersionOne").val() + '.'
			+ $("#spiderVersionTwo").val() + '.'
			+ $("#spiderVersionThree").val();
	var spiderDes = $("#spiderDes").val();
	var customSpiderType = $("#customSpiderType").val();
	var customStatus = $("input[name='status']").is(':checked') == true ? 0 : 1;
	var remark = $("input[name='remark']").val();

	// 以下开始参数获取
	var spiderDefaultParamsAll = '';
	var spiderDefaultParams = '';
	// 拼接默认参数为json
	var des = '';
	var key = '';
	var array = new Array();
	var num = 0;
	var success = true;
	var map = new Map();
	var paramMap = new Map();
	// 特殊字符限制限制
	var regEn = /[`~!@#$%^&*()+<>?:"{},.\/;'[\]\s]/im, regCn = /[·！#￥（——）：；“”‘、，|《。》？、【】[\]]/im;
	var regEn2 = /[`~!@$%^&*()+<>?"{},.\/;'[\]\s]/im;
	var regOnlyEnAndNum = /^([a-zA-Z]{1}[a-zA-Z0-9_]+)$/;
	var regOnlyNum = /^[0-9]*$/;
	var regOnlyDouble = /^[-+]?(\d+(\.\d*)?|\.\d+)([eE]([-+]?([012]?\d{1,2}|30[0-7])|-3([01]?[4-9]|[012]?[0-3])))?[dD]?$/;
	var regDate = /^(\d{4})(-)(\d{2})(-)(\d{2})$/;
	// 格式xxx=xxx
	var regEnum = /^[^(=)]+[=]{1}[^(=)]+$/;
	// 遍历所有的参数
	$(".input-group.inputParams")
			.each(
					function() {
						map = new Map();
						// let this = $(this);
						des = $(this).find("#desInput").val();
						key = $(this).find("#keyInput").val();
						value = $(this).find("#valueInput").val();
						type = $(this).find("#typeSelect").val();
						if (des == '' || key == '') {
							$.modalAlert('描述和key不能为空', modal_status.FAIL);
							success = false;
							return;
						}
						// 验证
						// 长度限制，类型等
						// 长度限制
						if (des.length > 10 || key.length > 15
								|| value.length > 100) {
							$.modalAlert('字符串超过规定长度', modal_status.FAIL);
							success = false;
							return;
						}
						// 特殊字符限制限制
						if (regEn.test(des) || regCn.test(des)
								|| regEn.test(key) || regCn.test(key)) {
							$.modalAlert('不能携带特殊字符', modal_status.FAIL);
							success = false;
							return;
						}
						// 只能是英文
						if (!regOnlyEnAndNum.test(key)) {
							$.modalAlert('key只能是英文，数字和下划线', modal_status.FAIL);
							success = false;
							return;
						}
						// 类型验证
						// 类型验证
						if (type == 0) {
							// 特殊字符限制限制
							if (regEn.test(value) || regCn.test(value)) {
								$.modalAlert('不能携带特殊字符', modal_status.FAIL);
								success = false;
								return;
							}
						} else if (type == 1) {
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
						} else if (type == 3) {
							// boolean
							if (value != 'true' && value != 'false') {
								$.modalAlert('value只能是ture或者false',
										modal_status.FAIL);
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
							// 特殊字符限制限制
							if (regEn2.test(value)) {
								$.modalAlert('enum类型不能携带特殊字符',
										modal_status.FAIL);
								success = false;
								return;
							}
							// 先按照#分割选项
							var enumArray = new Array();
							enumArray = value.split("#");
							if (enumArray.length < 2) {
								$.modalAlert('下拉选项最少一个', modal_status.FAIL);
								success = false;
								return;
							}

							// 遍历然后判断是否格式正确
							// 第一个是默认value
							enumValue = enumArray[0];
							if (enumValue.indexOf("=") > 0) {
								$.modalAlert('enum格式不正确', modal_status.FAIL);
								success = false;
								return;
							}
							var isCorrectDefaultValue = false;
							for (var i = 1; i < enumArray.length; i++) {
								// 格式为xxx=xxx
								if (!regEnum.test(enumArray[i])) {
									$
											.modalAlert('enum格式不正确',
													modal_status.FAIL);
									success = false;
									return;
								}
								// 判断默认值是否在下拉选项中
								if (enumValue == enumArray[i].split("=")[0]) {
									isCorrectDefaultValue = true;
								}
							}
							if (!isCorrectDefaultValue) {
								$.modalAlert('默认值不存在', modal_status.FAIL);
								success = false;
								return;
							}
						}

						map.put('des', des);
						map.put('key', key);
						map.put('value', value);
						map.put('type', type);
						array[num] = map.data;
						// key value纯参数单独存放
						if (type == 5) {
							paramMap.put(key, enumValue);
						} else {
							paramMap.put(key, value);
						}
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
		spiderDefaultParams = JSON.stringify(paramMap.data);
	}
	// 校验是否key重复
	if (array.length != paramMap.keys.length) {
		$.modalAlert('key不能重复', modal_status.FAIL);
		return;
	}
	// 发送请求
	$.ajax({
		cache : true,
		type : "POST",
		url : ctx + "spider/customspider/save",
		data : {
			"customSpiderId" : customSpiderId,
			"customSpiderBackId" : customSpiderBackId,
			"spiderVersion" : spiderVersion,
			"spiderDes" : spiderDes,
			"customSpiderType" : customSpiderType,
			"spiderDefaultParamsAll" : spiderDefaultParamsAll,
			"spiderDefaultParams" : spiderDefaultParams,
			"customStatus" : customStatus,
			"remark" : remark
		},
		async : false,
		error : function(request) {
			$.modalAlert("系统错误", modal_status.FAIL);
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("修改自定义成功,正在刷新数据请稍后……", {
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