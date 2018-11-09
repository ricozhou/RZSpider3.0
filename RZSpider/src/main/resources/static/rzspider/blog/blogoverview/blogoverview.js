$().ready(
		function() {
			// 显示时间
			var websiteOnlinetime = blogoverview.websiteOnlinetime;
			// 计算时间
			if (websiteOnlinetime != null && websiteOnlinetime != '') {
				$('#blogTimeOnline2').html('上线时间：' + websiteOnlinetime);
				window.setInterval(function() {
					timeDifc(websiteOnlinetime);
				}, 1000);
			}

			// 显示数据
			showAllMessage();
			// 显示数据
			showDayMessage();
			// 显示数据
			showMonthMessage();
			// 显示数据
			showYearMessage();

			// 显示地图分布
			showBroweMap(blogoverview.cbnList, blogoverview.allBrowseNum);

			// 显示折线图
			showEChart('line', blogoverview.weekBrowseNumArray,
					blogoverview.weekCommentNumArray,
					blogoverview.weekArticleNumArray);
			// 显示柱状图
			showEChart('bar', blogoverview.weekBrowseNumArray,
					blogoverview.weekCommentNumArray,
					blogoverview.weekArticleNumArray);
		})

// 计算时间差
function timeDifc(start) {
	var starts = new Date(start), ends = new Date(), message = '';
	if (starts.getTime() > ends.getTime()) {
		return message = "已上线：0 年 0 天 0 时 0 分 0 秒";
	}

	if ((ends.getTime() - starts.getTime()) / (1000 * 60) < 1) {
		return message = "已上线：0 年 0 天 0 时 0 分 0 秒";
	}

	var date3 = new Date().getTime() - new Date(start).getTime(); // 时间差的毫秒数
	// 计算出相差天数
	var years = Math.floor(date3 / (24 * 3600 * 1000 * 365))
	var days = Math.floor(date3 / (24 * 3600 * 1000))

	// 计算出小时数

	var leave1 = date3 % (24 * 3600 * 1000) // 计算天数后剩余的毫秒数
	var hours = Math.floor(leave1 / (3600 * 1000))
	// 计算相差分钟数
	var leave2 = leave1 % (3600 * 1000) // 计算小时数后剩余的毫秒数
	var minutes = Math.floor(leave2 / (60 * 1000))
	// 计算相差秒数
	var leave3 = leave2 % (60 * 1000) // 计算分钟数后剩余的毫秒数
	var seconds = Math.round(leave3 / 1000)

	message = '已上线：' + years + ' 年 ' + days + ' 天 ' + hours + ' 时 ' + minutes
			+ ' 分 ' + seconds + ' 秒';

	$('#blogTimeOnline').html(message);
};

function showAllMessage() {
	// 显示总览数据
	$('#allBrowseNum')
			.html(
					(blogoverview.allBrowseNum == null || blogoverview.allBrowseNum == '') ? '0 次'
							: blogoverview.allBrowseNum + ' 次');
	$('#allLikeNum')
			.html(
					(blogoverview.allLikeNum == null || blogoverview.allLikeNum == '') ? '0 个'
							: blogoverview.allLikeNum + ' 个');
	$('#allCommentNum')
			.html(
					(blogoverview.allCommentNum == null || blogoverview.allCommentNum == '') ? '0 条'
							: blogoverview.allCommentNum + ' 条');
	$('#allAppreciateNum')
			.html(
					(blogoverview.allAppreciateNum == null || blogoverview.allAppreciateNum == '') ? '0 元'
							: blogoverview.allAppreciateNum + ' 元');

	$('#allArticleNum')
			.html(
					(blogoverview.allArticleNum == null || blogoverview.allArticleNum == '') ? '0 篇'
							: blogoverview.allArticleNum + ' 篇');
	$('#allTagNum')
			.html(
					(blogoverview.allTagNum == null || blogoverview.allTagNum == '') ? '0 个'
							: blogoverview.allTagNum + ' 个');
	$('#allColumnNum')
			.html(
					(blogoverview.allColumnNum == null || blogoverview.allColumnNum == '') ? '0 个'
							: blogoverview.allColumnNum + ' 个');
	$('#allNoticeNum')
			.html(
					(blogoverview.allNoticeNum == null || blogoverview.allNoticeNum == '') ? '0 条'
							: blogoverview.allNoticeNum + ' 条');
}
function showDayMessage() {
	// 当日
	$('#dayBrowseNum')
			.html(
					(blogoverview.dayBrowseNum == null || blogoverview.dayBrowseNum == '') ? '0 次'
							: blogoverview.dayBrowseNum + ' 次');
	$('#dayArticleNum')
			.html(
					(blogoverview.dayArticleNum == null || blogoverview.dayArticleNum == '') ? '0 篇'
							: blogoverview.dayArticleNum + ' 篇');
	$('#dayCommentNum')
			.html(
					(blogoverview.dayCommentNum == null || blogoverview.dayCommentNum == '') ? '0 条'
							: blogoverview.dayCommentNum + ' 条');
	$('#dayAppreciateNum')
			.html(
					(blogoverview.dayAppreciateNum == null || blogoverview.dayAppreciateNum == '') ? '0 元'
							: blogoverview.dayAppreciateNum + ' 元');

	// 设置百分比
	$('#dayBrowseNumPer').html(
			setBlogMsgNumPer(blogoverview.dayBrowseNumPer,
					'dayBrowseNumPerStyle'));
	$('#dayArticleNumPer').html(
			setBlogMsgNumPer(blogoverview.dayArticleNumPer,
					'dayArticleNumPerStyle'));
	$('#dayCommentNumPer').html(
			setBlogMsgNumPer(blogoverview.dayCommentNumPer,
					'dayCommentNumPerStyle'));
	$('#dayAppreciateNumPer').html(
			setBlogMsgNumPer(blogoverview.dayAppreciateNumPer,
					'dayAppreciateNumPerStyle'));

}
function showMonthMessage() {
	// 当月
	$('#monthBrowseNum')
			.html(
					(blogoverview.monthBrowseNum == null || blogoverview.monthBrowseNum == '') ? '0 次'
							: blogoverview.monthBrowseNum + ' 次');
	$('#monthArticleNum')
			.html(
					(blogoverview.monthArticleNum == null || blogoverview.monthArticleNum == '') ? '0 篇'
							: blogoverview.monthArticleNum + ' 篇');
	$('#monthCommentNum')
			.html(
					(blogoverview.monthCommentNum == null || blogoverview.monthCommentNum == '') ? '0 条'
							: blogoverview.monthCommentNum + ' 条');
	$('#monthAppreciateNum')
			.html(
					(blogoverview.monthAppreciateNum == null || blogoverview.monthAppreciateNum == '') ? '0 元'
							: blogoverview.monthAppreciateNum + ' 元');

	// 设置百分比
	$('#monthBrowseNumPer').html(
			setBlogMsgNumPer(blogoverview.monthBrowseNumPer,
					'monthBrowseNumPerStyle'));
	$('#monthArticleNumPer').html(
			setBlogMsgNumPer(blogoverview.monthArticleNumPer,
					'monthArticleNumPerStyle'));
	$('#monthCommentNumPer').html(
			setBlogMsgNumPer(blogoverview.monthCommentNumPer,
					'monthCommentNumPerStyle'));
	$('#monthAppreciateNumPer').html(
			setBlogMsgNumPer(blogoverview.monthAppreciateNumPer,
					'monthAppreciateNumPerStyle'));
}
function showYearMessage() {
	// 当年
	$('#yearBrowseNum')
			.html(
					(blogoverview.yearBrowseNum == null || blogoverview.yearBrowseNum == '') ? '0 次'
							: blogoverview.yearBrowseNum + ' 次');
	$('#yearArticleNum')
			.html(
					(blogoverview.yearArticleNum == null || blogoverview.yearArticleNum == '') ? '0 篇'
							: blogoverview.yearArticleNum + ' 篇');
	$('#yearCommentNum')
			.html(
					(blogoverview.yearCommentNum == null || blogoverview.yearCommentNum == '') ? '0 条'
							: blogoverview.yearCommentNum + ' 条');
	$('#yearAppreciateNum')
			.html(
					(blogoverview.yearAppreciateNum == null || blogoverview.yearAppreciateNum == '') ? '0 元'
							: blogoverview.yearAppreciateNum + ' 元');
	// 设置百分比
	$('#yearBrowseNumPer').html(
			setBlogMsgNumPer(blogoverview.yearBrowseNumPer,
					'yearBrowseNumPerStyle'));
	$('#yearArticleNumPer').html(
			setBlogMsgNumPer(blogoverview.yearArticleNumPer,
					'yearArticleNumPerStyle'));
	$('#yearCommentNumPer').html(
			setBlogMsgNumPer(blogoverview.yearCommentNumPer,
					'yearCommentNumPerStyle'));
	$('#yearAppreciateNumPer').html(
			setBlogMsgNumPer(blogoverview.yearAppreciateNumPer,
					'yearAppreciateNumPerStyle'));
}

// 设置
function setBlogMsgNumPer(num, id) {
	if (num < 0) {
		num = -num;
		return num + '%  <i class="fa fa-level-down"></i>';
	} else if (num > 0) {
		return num + '%  <i class="fa fa-level-up"></i>';
	} else {
		return num + '%';
	}
}

function showBroweMap(data1, data2) {
	var s = echarts.init(document.getElementById("echarts-map-chart")), c = {
		title : {
			text : "博客网站浏览量分布图",
			subtext : "仅供参考",
			x : "center"
		},
		tooltip : {
			trigger : "item"
		},
		legend : {
			orient : "vertical",
			x : "left",
			data : [ "浏览来源分布" ]
		},
		dataRange : {
			min : 0,
			max : data2,
			x : "left",
			y : "bottom",
			text : [ "高", "低" ],
			calculable : !0
		},
		toolbox : {
			show : !0,
			orient : "vertical",
			x : "right",
			y : "center",
			feature : {
				mark : {
					show : !0
				},
				dataView : {
					show : !0,
					readOnly : !1
				},
				restore : {
					show : !0
				},
				saveAsImage : {
					show : !0
				}
			}
		},
		roamController : {
			show : !0,
			x : "right",
			mapTypeControl : {
				china : !0
			}
		},
		series : [ {
			name : "浏览量",
			type : "map",
			mapType : "china",
			roam : !1,
			itemStyle : {
				normal : {
					label : {
						show : !0
					}
				},
				emphasis : {
					label : {
						show : !0
					}
				}
			},
			data : data1
		} ]
	};
	s.setOption(c), $(window).resize(s.resize);
}

function showEChart(type, data1, data2, data3) {
	var e = echarts.init(document.getElementById("echarts-" + type + "-chart")), a = {
		title : {
			text : "近一周总览变化"
		},
		tooltip : {
			trigger : "axis"
		},
		legend : {
			data : [ "浏览量", "评论数", "文章数" ]
		},
		grid : {
			x : 30,
			x2 : 40,
			y2 : 24
		},
		calculable : !0,
		xAxis : [ {
			type : "category",
			boundaryGap : type == 'line' ? !1 : 10,
			data : [ getDay(-6), getDay(-5), getDay(-4), getDay(-3), "前天",
					"昨天", "今天" ]
		} ],
		yAxis : [ {
			type : "value",
			axisLabel : {
				formatter : "{value} "
			}
		} ],
		series : [ {
			name : "浏览量",
			type : type,
			data : data1,
			markPoint : {
				data : [ {
					type : "max",
					name : "最大值"
				}, {
					type : "min",
					name : "最小值"
				} ]
			},
			markLine : {
				data : [ {
					type : "average",
					name : "平均值"
				} ]
			}
		}, {
			name : "评论数",
			type : type,
			data : data2,
			markPoint : {
				data : [ {
					type : "max",
					name : "最大值"
				}, {
					type : "min",
					name : "最小值"
				} ]
			},
			markLine : {
				data : [ {
					type : "average",
					name : "平均值"
				} ]
			}
		}, {
			name : "文章数",
			type : type,
			data : data3,
			markPoint : {
				data : [ {
					type : "max",
					name : "最大值"
				}, {
					type : "min",
					name : "最小值"
				} ]
			},
			markLine : {
				data : [ {
					type : "average",
					name : "平均值"
				} ]
			}
		} ]
	};
	e.setOption(a), $(window).resize(e.resize);

}

// 根据天数获取日
function getDay(num) {
	var today = new Date();
	var nowTime = today.getTime();
	var ms = 24 * 3600 * 1000 * num;
	today.setTime(parseInt(nowTime + ms));
	var oYear = today.getFullYear();
	var oMoth = (today.getMonth() + 1).toString();
	if (oMoth.length <= 1)
		oMoth = '0' + oMoth;
	var oDay = today.getDate().toString();
	if (oDay.length <= 1)
		oDay = '0' + oDay;
	return oMoth + '.' + oDay;
}
