/**
 * 通用方法封装处理
 * Copyright (c) 2018 rzspider 
 */

/** 消息状态码 */
web_status = {
	SUCCESS : 0,
	FAIL : 500
};

/** 弹窗状态码 */
modal_status = {
	SUCCESS : "success",
	FAIL : "error",
	WARNING : "warning"
};

/** 弹出层指定宽度 */
function layer_show(title, url, w, h) {
	if (title == null || title == '') {
		title = false;
	}
	;
	if (url == null || url == '') {
		url = "404.html";
	}
	;
	if (w == null || w == '') {
		w = $(window).width() - 300;
	}
	;
	if (h == null || h == '') {
		h = ($(window).height() - 50);
	}
	;
	layer.open({
		type : 2,
		area : [ w + 'px', h + 'px' ],
		fix : false,
		// 不固定
		maxmin : true,
		shade : 0.4,
		title : title,
		content : url
	});
}
/** 弹出层指定宽度 */
function layer_show3(title, url, w, h) {
	if (title == null || title == '') {
		title = false;
	}
	;
	if (url == null || url == '') {
		url = "404.html";
	}
	;
	if (w == null || w == '') {
		w = $(window).width() - 300;
	}
	;
	if (h == null || h == '') {
		h = ($(window).height() - 50);
	}
	;
	parent.layer.open({
		type : 2,
		area : [ w + 'px', h + 'px' ],
		fix : false,
		// 不固定
		maxmin : true,
		shade : 0.4,
		title : title,
		content : url
	});
}
/** 弹出层自动宽高 */
function layer_showAuto(title, url) {
	layer_show(title, url, '', '');
}
/** 弹出层自动宽高 */
function layer_showAuto3(title, url) {
	layer_show3(title, url, '', '');
}
/** 弹出层固定宽高 */
function layer_showAuto2(title, url) {
	// 这是内框的尺寸
	layer_show(title, url, $(window).width() - 100, '');
}

/** 弹出层固定宽高 */
function layer_showAutoAll(title, url) {
	// 这是内框的尺寸
	layer_show(title, url, $(window).width(), $(window).height());
}

/** 关闭弹出框口 */
function layer_close() {
	var index = parent.layer.getFrameIndex(window.name);
	parent.layer.close(index);
}

/** 对ajax的post方法再次封装 */
_ajax_save = function(url, data) {
	var config = {
		url : url,
		type : "post",
		dataType : "json",
		data : data,
		success : function(result) {
			handleSuccess(result);
		}
	};
	$.ajax(config)
};
/** 对ajax的post方法再次封装 */
_ajax_save2 = function(url, data) {
	var config = {
		url : url,
		type : "post",
		dataType : "json",
		data : data,
		success : function(result) {
			handleSuccess2(result);
		}
	};
	$.ajax(config)
};
/** 对jquery的ajax方法再次封装 */
_ajax = function(url, data, type) {
	var config = {
		url : url,
		type : type,
		dataType : "json",
		data : data,
		success : function(result) {
			simpleSuccess(result);
		}
	};
	$.ajax(config)
};

/** 返回结果处理 */
function simpleSuccess(result) {
	if (result.code == web_status.SUCCESS) {
		$.modalMsg(result.msg, modal_status.SUCCESS);
		$.refreshTable();
	} else {
		$.modalAlert(result.msg, modal_status.FAIL);
	}
}

/** 操作结果处理 */
function handleSuccess(result) {
	if (result.code == web_status.SUCCESS) {
		parent.layer.msg("新增成功,正在刷新数据请稍后……", {
			icon : 1,
			time : 500,
			shade : [ 0.1, '#fff' ]
		}, function() {
			$.parentReload();
		});
	} else {
		$.modalAlert(result.msg, modal_status.FAIL);
	}
}

/** 操作结果处理 */
function handleSuccess2(result) {
	if (result.code == web_status.SUCCESS) {
		layer.msg(result.msg, {
			icon : 1,
			time : 500,
			shade : [ 0.1, '#fff' ]
		});
	} else {
		$.modalAlert(result.msg, modal_status.FAIL);
	}
}

/** 时间格式化 */
function formatDate(_date, _pattern) {
	var date = new Date(_date);
	var newDate = date.format(_pattern);
	return newDate;
}

Date.prototype.format = function(format) {
	var date = {
		"M+" : this.getMonth() + 1,
		"d+" : this.getDate(),
		"h+" : this.getHours(),
		"m+" : this.getMinutes(),
		"s+" : this.getSeconds(),
		"q+" : Math.floor((this.getMonth() + 3) / 3),
		"S+" : this.getMilliseconds()
	};
	if (/(y+)/i.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + '')
				.substr(4 - RegExp.$1.length));
	}
	for ( var k in date) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? date[k]
					: ("00" + date[k]).substr(("" + date[k]).length));
		}
	}
	return format;
}

/** 创建选项卡 */
function createMenuItem(dataUrl, menuName) {
	dataIndex = Math.floor(Math.random() * 100), flag = true;
	if (dataUrl == undefined || $.trim(dataUrl).length == 0)
		return false;
	var topWindow = $(window.parent.document);
	// 选项卡菜单已存在
	$('.menuTab', topWindow).each(
			function() {
				if ($(this).data('id') == dataUrl) {
					if (!$(this).hasClass('active')) {
						$(this).addClass('active').siblings('.menuTab')
								.removeClass('active');
						$('.page-tabs-content').animate({
							marginLeft : ""
						}, "fast");
						// 显示tab对应的内容区
						$('.mainContent .RZSpider_iframe', topWindow).each(
								function() {
									if ($(this).data('id') == dataUrl) {
										$(this).show().siblings(
												'.RZSpider_iframe').hide();
										return false;
									}
								});
					}
					flag = false;
					return false;
				}
			});
	// 选项卡菜单不存在
	if (flag) {
		var str = '<a href="javascript:;" class="active menuTab" data-id="'
				+ dataUrl + '">' + menuName
				+ ' <i class="fa fa-times-circle"></i></a>';
		$('.menuTab', topWindow).removeClass('active');

		// 添加选项卡对应的iframe
		var str1 = '<iframe class="RZSpider_iframe" name="iframe' + dataIndex
				+ '" width="100%" height="100%" src="' + dataUrl
				+ '" frameborder="0" data-id="' + dataUrl
				+ '" seamless></iframe>';
		$('.mainContent', topWindow).find('iframe.RZSpider_iframe').hide()
				.parents('.mainContent').append(str1);

		// 添加选项卡
		$('.menuTabs .page-tabs-content', topWindow).append(str);
	}
	return false;
}

/** 设置全局ajax超时处理 */
$.ajaxSetup({
	complete : function(XMLHttpRequest, textStatus) {
		if (textStatus == "parsererror") {
			$.modalConfirm("登陆超时！请重新登陆！", function() {
				window.location.href = ctx + "login";
			})
		}
	}
});

function Map() {
	/** 存放键的数组(遍历用到) */
	this.keys = new Array();
	/** 存放数据 */
	this.data = new Object();

	/**
	 * 放入一个键值对
	 * 
	 * @param {String}
	 *            key
	 * @param {Object}
	 *            value
	 */
	this.put = function(key, value) {
		if (this.data[key] == null) {
			this.keys.push(key);
		}
		this.data[key] = value;
	};

	/**
	 * 获取某键对应的值
	 * 
	 * @param {String}
	 *            key
	 * @return {Object} value
	 */
	this.get = function(key) {
		return this.data[key];
	};

	/**
	 * 删除一个键值对
	 * 
	 * @param {String}
	 *            key
	 */
	this.remove = function(key) {
		this.keys.remove(key);
		this.data[key] = null;
	};

	/**
	 * 遍历Map,执行处理函数
	 * 
	 * @param {Function}
	 *            回调函数 function(key,value,index){..}
	 */
	this.each = function(fn) {
		if (typeof fn != 'function') {
			return;
		}
		var len = this.keys.length;
		for (var i = 0; i < len; i++) {
			var k = this.keys[i];
			fn(k, this.data[k], i);
		}
	};

	/**
	 * 获取键值数组(类似Java的entrySet())
	 * 
	 * @return 键值对象{key,value}的数组
	 */
	this.entrys = function() {
		var len = this.keys.length;
		var entrys = new Array(len);
		for (var i = 0; i < len; i++) {
			entrys[i] = {
				key : this.keys[i],
				value : this.data[i]
			};
		}
		return entrys;
	};

	/**
	 * 判断Map是否为空
	 */
	this.isEmpty = function() {
		return this.keys.length == 0;
	};

	/**
	 * 获取键值对数量
	 */
	this.size = function() {
		return this.keys.length;
	};

	/**
	 * 重写toString
	 */
	this.toString = function() {
		var s = "{";
		for (var i = 0; i < this.keys.length; i++, s += ',') {
			var k = this.keys[i];
			s += k + "=" + this.data[k];
		}
		s += "}";
		return s;
	};
}