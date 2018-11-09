//其他处理
$(document)
		.ready(
				function() {
					// 处理背景显示
					var loginbgType = $('#loginbgType').val();
					var loginbgName = $('#loginbgName').val();
					if (loginbgType == 0 || loginbgType == 1) {
						console.log(loginbgName)
						// 链接显示或者图片显示，base64
						$("#signin")
								.css(
										"background",
										'url('
												+ (loginbgName == '' ? '/img/background/login-background.jpg'
														: loginbgName)
												+ ') no-repeat center fixed');
						$("#signin").css("background-size", 'cover');

					} else if (loginbgType == 2) {
						// 特效显示
						if (loginbgName == 0) {
							// 显示区域
							$(".specialEffects").css('display', 'block');
							// 星星特效
							// 设置样式
							$(".specialEffects").css({
								"width" : "100%",
								"height" : "100%",
								"z-index" : "-1",
								"position" : "absolute",
								"border-width" : "0px",
								"margin" : "0",
								"left" : "0",
								"top" : "0",
								"padding" : "0",
								"outline" : "none",
								"border" : "none",
								"overflow" : "hidden"
							});
							// 动态加载js
							var scriptNode = document.createElement('script');
							scriptNode.src = '/js/plugins/canvaseffects/canvas-stars/js/stars.js';
							document.body.appendChild(scriptNode);
							// 加载完再处理
							scriptNode.onload = scriptNode.onreadystatechange = function() {
								// 设置开启特效，特效参数
								initSpecialEffects("specialEffects", 230, 1000,
										60, 2, 50000, 0.9);
							}
						} else if (loginbgName == 1) {
							// 行星运转
							// 显示区域
							$(".specialEffects2").css('display', 'block');
							// 设置样式
							$(".specialEffects2").css({
								"width" : "100%",
								"height" : "100%",
								"z-index" : "-1",
								"position" : "absolute",
								"border-width" : "0px",
								"margin" : "0",
								"left" : "0",
								"top" : "0",
								"padding" : "0",
								"outline" : "none",
								"border" : "none",
								"overflow" : "hidden",
								"background" : "#000",
								"margin" : "0",
								"padding" : "0",
								"outline" : "0"
							});
							// 动态加载js文件
							var scriptNode = document.createElement('script');
							scriptNode.src = '/js/plugins/canvaseffects/canvas-3d-planet/js/three.min.js';
							document.body.appendChild(scriptNode);
							var scriptNode2 = document.createElement('script');
							scriptNode2.src = '/js/plugins/canvaseffects/canvas-3d-planet/js/CopyShader.js';
							var scriptNode3 = document.createElement('script');
							scriptNode3.src = '/js/plugins/canvaseffects/canvas-3d-planet/js/EffectComposer.js';
							var scriptNode4 = document.createElement('script');
							scriptNode4.src = '/js/plugins/canvaseffects/canvas-3d-planet/js/FilmPass.js';
							var scriptNode5 = document.createElement('script');
							scriptNode5.src = '/js/plugins/canvaseffects/canvas-3d-planet/js/FilmShader.js';
							var scriptNode6 = document.createElement('script');
							scriptNode6.src = '/js/plugins/canvaseffects/canvas-3d-planet/js/ShaderPass.js';
							var scriptNode7 = document.createElement('script');
							scriptNode7.src = '/js/plugins/canvaseffects/canvas-3d-planet/js/RenderPass.js';
							var scriptNode8 = document.createElement('script');
							scriptNode8.src = '/js/plugins/canvaseffects/canvas-3d-planet/js/index.js';

							// 每一个加载完再加载下一个
							scriptNode.onload = scriptNode.onreadystatechange = function() {
								document.body.appendChild(scriptNode2);
								document.body.appendChild(scriptNode3);
								document.body.appendChild(scriptNode4);
								document.body.appendChild(scriptNode5);
								document.body.appendChild(scriptNode6);
								document.body.appendChild(scriptNode7);
								scriptNode7.onload = scriptNode7.onreadystatechange = function() {
									document.body.appendChild(scriptNode8);
									scriptNode8.onload = scriptNode8.onreadystatechange = function() {
										initSpecialEffects('specialEffects2');
									}
								}
							}
						} else if (loginbgName == 2) {
							// 文字球
							// 显示区域
							$(".specialEffects").css('display', 'block');
							// 设置样式
							$(".specialEffects").css({
								"width" : "100%",
								"height" : "100%",
								"z-index" : "-1",
								"position" : "absolute",
								"border-width" : "0px",
								"margin" : "0",
								"left" : "0",
								"top" : "0",
								"padding" : "0",
								"outline" : "none",
								"border" : "none",
								"overflow" : "hidden",
								"background" : "#000",
								"margin" : "0",
								"padding" : "0",
								"outline" : "0"
							});
							// 加载js
							// 动态加载js
							var scriptNode = document.createElement('script');
							scriptNode.src = '/js/plugins/canvaseffects/canvas-3d-char-ball/js/modernizr.min.js';
							var scriptNode2 = document.createElement('script');
							scriptNode2.src = '/js/plugins/canvaseffects/canvas-3d-char-ball/js/3dchar.js';
							document.body.appendChild(scriptNode);
							// 加载完再处理
							scriptNode.onload = scriptNode.onreadystatechange = function() {
								document.body.appendChild(scriptNode2);
								scriptNode2.onload = scriptNode2.onreadystatechange = function() {
									initSpecialEffects('specialEffects');
								}
							}
						} else if (loginbgName == 3) {
							// 梦想树
							// 显示区域
							$(".specialEffects").css('display', 'block');
							// 设置样式
							$(".specialEffects").css({
								"width" : "100%",
								"height" : "100%",
								"z-index" : "-1",
								"position" : "absolute",
								"border-width" : "0px",
								"margin" : "0",
								"left" : "0",
								"top" : "0",
								"padding" : "0",
								"outline" : "none",
								"border" : "none",
								"overflow" : "hidden",
								"background" : "#000",
								"margin" : "0",
								"padding" : "0",
								"outline" : "0"
							});
							// 加载js
							// 动态加载js
							var scriptNode = document.createElement('script');
							scriptNode.src = '/js/plugins/canvaseffects/canvas-dream-tree/js/dreamtree.js';
							document.body.appendChild(scriptNode);
							// 加载完再处理
							scriptNode.onload = scriptNode.onreadystatechange = function() {
								initSpecialEffects('specialEffects');
							}
						} else if (loginbgName == 4) {
							// 太阳系
							// 显示区域
							$(".specialEffects").css('display', 'block');
							// 设置样式
							$(".specialEffects").css({
								"width" : "100%",
								"height" : "100%",
								"z-index" : "-1",
								"position" : "absolute",
								"border-width" : "0px",
								"margin" : "0",
								"left" : "0",
								"top" : "0",
								"padding" : "0",
								"outline" : "none",
								"border" : "none",
								"overflow" : "hidden",
								"background" : "#000",
								"margin" : "0",
								"padding" : "0",
								"outline" : "0"
							});
							// 加载js
							// 动态加载js
							var scriptNode = document.createElement('script');
							scriptNode.src = '/js/plugins/canvaseffects/canvas-sun/js/canvassun.js';
							document.body.appendChild(scriptNode);
							// 加载完再处理
							scriptNode.onload = scriptNode.onreadystatechange = function() {
								initSpecialEffects('specialEffects');
							}
						} else if (loginbgName == 5) {
							// 粒子波浪
							// 显示区域
							$(".specialEffects2").css('display', 'block');
							// 设置样式
							$(".specialEffects2").css({
								"width" : "100%",
								"height" : "100%",
								"z-index" : "-1",
								"position" : "absolute",
								"border-width" : "0px",
								"margin" : "0",
								"left" : "0",
								"top" : "0",
								"padding" : "0",
								"outline" : "none",
								"border" : "none",
								"overflow" : "hidden",
								"background" : "#000",
								"margin" : "0",
								"padding" : "0",
								"outline" : "0"
							});
							// 加载js
							// 动态加载js
							var scriptNode = document.createElement('script');
							scriptNode.src = '/js/plugins/canvaseffects/canvas-3d-wave/js/three.min.js';
							var scriptNode2 = document.createElement('script');
							scriptNode2.src = '/js/plugins/canvaseffects/canvas-3d-wave/js/wave.js';
							document.body.appendChild(scriptNode);
							// 加载完再处理
							scriptNode.onload = scriptNode.onreadystatechange = function() {
								document.body.appendChild(scriptNode2);
								scriptNode2.onload = scriptNode2.onreadystatechange = function() {
									initSpecialEffects('specialEffects2');
								}
							}
						} else if (loginbgName == 6) {
							// 跳动时间
							// 显示区域
							$(".specialEffects").css('display', 'block');
							// 设置样式
							$(".specialEffects").css({
								"width" : "100%",
								"height" : "100%",
								"z-index" : "-1",
								"position" : "absolute",
								"border-width" : "0px",
								"margin" : "0",
								"left" : "0",
								"top" : "0",
								"padding" : "0",
								"outline" : "none",
								"border" : "none",
								"overflow" : "hidden",
								"background" : "#000",
								"margin" : "0",
								"padding" : "0",
								"outline" : "0"
							});
							// 加载js
							// 动态加载js
							var scriptNode = document.createElement('script');
							scriptNode.src = '/js/plugins/canvaseffects/canvas-dance-time/js/zzsc.js';
							document.body.appendChild(scriptNode);
							// 加载完再处理
							scriptNode.onload = scriptNode.onreadystatechange = function() {
								initSpecialEffects('specialEffects');
							}
						} else if (loginbgName == 7) {
							// 网粒效果
							// 显示区域
							$(".specialEffects2").css('display', 'block');
							// 设置样式
							$(".specialEffects2").css({
								"width" : "100%",
								"height" : "100%",
								"z-index" : "-1",
								"position" : "absolute",
								"border-width" : "0px",
								"margin" : "0",
								"left" : "0",
								"top" : "0",
								"padding" : "0",
								"outline" : "none",
								"border" : "none",
								"overflow" : "hidden",
								"background" : "#000",
								"margin" : "0",
								"padding" : "0",
								"outline" : "0"
							});
							// 加载js
							// 动态加载js
							var scriptNode = document.createElement('script');
							scriptNode.src = '/js/plugins/canvaseffects/canvas-particle-effect/js/prefixfree.min.js';
							var scriptNode2 = document.createElement('script');
							scriptNode2.src = '/js/plugins/canvaseffects/canvas-particle-effect/js/RequestAnimationFrame.js';
							var scriptNode3 = document.createElement('script');
							scriptNode3.src = '/js/plugins/canvaseffects/canvas-particle-effect/js/stats.min.js';
							var scriptNode4 = document.createElement('script');
							scriptNode4.src = '/js/plugins/canvaseffects/canvas-particle-effect/js/index.js';
							document.body.appendChild(scriptNode);
							// 加载完再处理
							scriptNode.onload = scriptNode.onreadystatechange = function() {
								document.body.appendChild(scriptNode2);
								document.body.appendChild(scriptNode3);
								document.body.appendChild(scriptNode4);
								scriptNode4.onload = scriptNode4.onreadystatechange = function() {
									initSpecialEffects('specialEffects2');
								}
							}
						}
					}
				});
