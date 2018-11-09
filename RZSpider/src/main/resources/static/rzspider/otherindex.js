//其他处理
$(document)
		.ready(
				function() {
					// 音乐处理
					// 是否显示
					if (baseset.showMusicToolStatus != 0) {
						// 不显示
						$("#showMusicToolStatus").css('display', 'none');
					} else {
						// 显示
						$("#showMusicToolStatus").css('display', 'block');
						var musicInfo;
						if (baseset.musicInfo == null) {
							musicInfo = '[{"title":"无","author":"无","url":"","pic":"","lrc":""}]';
						} else {
							if ((baseset.musicInfo).replace(/(^s*)|(s*$)/g, "").length != 0) {
								musicInfo = baseset.musicInfo;
							} else {
								musicInfo = '[{"title":"无","author":"无","url":"","pic":"","lrc":""}]';
							}
						}
						musicInfo = JSON.parse(musicInfo);

						var zp = new zplayer(
								{
									element : document.getElementById('player'),
									autoplay : baseset.musicAutoPlay == 0 ? true
											: false,/* true则开启自动播放 */
									lrcStart : baseset.musicLrcStart == 0 ? true
											: false,/*
													 * 是否开启歌词功能
													 * ，默认false（为true时musics集合中需要传入lrc字段。）
													 */
									showLrc : baseset.musicShowLrc == 0 ? true
											: false,/* 开启歌词功能后是否展示歌词内容 ，默认false */
									fixed : baseset.musicFixed == 0 ? true
											: false, /* 是否固定在底部，默认false */
									listFolded : baseset.musicListFolded == 0 ? true
											: false, /* 列表是否折叠，默认false */
									listMaxHeight : baseset.musicListMaxHeight != '' ? baseset.musicListMaxHeight
											: 240, /* 列表最大高度，默认240 */
									musics : musicInfo
								});
						zp.init();
					}

				});
