// 启动websocket
function startWebsocket(taskId) {
	var websocket = null;
	// 判断当前浏览器是否支持WebSocket
	if ('WebSocket' in window) {
		websocket = new WebSocket(getUrl());
	}

	// 拼接地址
	function getUrl() {
		return (location.protocol == 'https:' ? 'wss' : 'ws') + '://'
				+ document.domain + ':' + location.port
				+ '/websocketspidertask?taskId=' + taskId;
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
		showMessage(event.data);
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
	function showMessage(data) {
		// 刷新table
		$.refreshTable();

		// 关闭连接
		websocket.onclose();
	}

}