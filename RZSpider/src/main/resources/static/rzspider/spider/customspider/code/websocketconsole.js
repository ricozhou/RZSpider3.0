// 启动websocket
function startWebsocket(customSpiderBackId) {
	var websocket = null;
	// 判断当前浏览器是否支持WebSocket
	if ('WebSocket' in window) {
		websocket = new WebSocket(getUrl());
	} else {
		consoleEditor.setValue("不支持websocket，请更换浏览器");
	}

	// 拼接地址
	function getUrl() {
		return (location.protocol == 'https:' ? 'wss' : 'ws') + '://'
				+ document.domain + ':' + location.port
				+ '/websocketconsole?customSpiderBackId=' + customSpiderBackId;
	}
	// 连接发生错误的回调方法
	websocket.onerror = function() {
		websocket.onclose();
	};

	// 连接成功建立的回调方法
	websocket.onopen = function(event) {
		console.log(document.domain)
		console.log(location.port)
	}

	// 接收到消息的回调方法
	websocket.onmessage = function(event) {
		// 显示设置
		showMessageToHtml(JSON.parse(event.data));
	}

	// 连接关闭的回调方法
	websocket.onclose = function() {
		console.log('close');
	}

	// 监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
	window.onbeforeunload = function() {
		websocket.onclose();
	}

	// 将消息显示在网页上
	function showMessageToHtml(data) {
		if (data.length != 0) {
			if (data['flag'] == 'message') {
				// 判断文本内容有多少行，超过3千行则不再显示，以避免浏览器卡顿
				if (data['returnMessage'].split(/\n/).length > 3000) {
					// 断开连接并返回
					websocket.onclose();
					return;
				}
				consoleEditor.setValue("");
				consoleEditor.setValue(data['returnMessage']);
				// 超过10行加入滚动条限制高度
				if (consoleEditor.lineCount() > 30) {
					consoleEditor.setSize('auto', $(window).height() * 0.6);
				} else {
					consoleEditor.setSize('auto', 'auto');
				}
				// 设置光标在最后
				consoleEditor.setCursor(consoleEditor.lastLine());
			} else if (data['flag'] == 'isover' && data['returnMessage'] == '0') {
				// 已结束
				// 运行按钮可用
				// 显示运行按钮
				runStatus = 1;
				$("#stop").css("display", "none");
				$("#run").css("display", "inline");
				document.getElementById("run").removeAttribute("disabled");
				document.getElementById("stop").setAttribute("disabled", true);
				// 关闭连接
				websocket.onclose();
			}

		}
	}

}