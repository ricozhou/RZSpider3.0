$("#form-gamelist-add").validate({
	rules : {
		gameNickName : {
			required : true,
			maxlength : 50,
			remote : {
				url : ctx + "commongame/gamelist/checkGameNickNameUnique",
				type : "post",
				dataType : "json",
				data : {
					"gameNickName" : function() {
						return $.trim($("#gameNickName").val());
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
		gameName: {
			required : true,
		},
		gameInstruction : {
			maxlength : 500
		},

	},
	messages : {
		"gameNickName" : {
			maxlength : '最长50字',
			remote : "游戏昵称必须唯一"
		},
		"gameInstruction" : {
			maxlength : "最长500字",
		},
		"gameName" : {
			required : "无可用游戏",
		}
	},
	submitHandler : function(form) {
		add();
	}
});
var gameData;
// 一开始便加载
$(document).ready(function() {
	// 查询所有游戏名称
	$.ajax({
		cache : true,
		type : "GET",
		url : ctx + "commongame/gamemanage/selectAllGameName",
		async : false,
		error : function(request) {
			$.modalAlert("系统错误", modal_status.FAIL);
		},
		success : function(data) {
			gameData=data;
			if (data != null) {
				// 动态显示
				$("#gameName").find("option").remove();
				for (var i = 0; i < data.length; i++) {
					var newOption = document.createElement("option");
					newOption.text = data[i].gameName+"（"+data[i].gameBackId+"）";
					newOption.value = data[i].gameName;
					document.getElementById("gameName").add(newOption);
				}
				if(data[0]!=null){
					setBaseData(data[0]);
				}
			} else {
				$.modalAlert(data.msg, modal_status.FAIL);
			}

		}
	});
})

// 绑定监听事件
$(function() {
	$('#gameName').bind('change', function() {
		var gameName = $(this).val();
		for (var i = 0; i < gameData.length; i++) {
			if (gameName == gameData[i].gameName) {
				setBaseData(gameData[i]);
			}
		}

	});
});

//设置初始值
function setBaseData(data) {
	// 设置默认参数
	$("#gameInstruction").val('');
	$("#gameInstruction").val(data.gameDes);
	
}

// 添加
function add() {
	var gameNickName = $("#gameNickName").val();
	var gameName = $("#gameName").val();
	var gameInstruction = $("#gameInstruction").val();
	var remark = $("input[name='remark']").val();
	$.ajax({
		cache : true,
		type : "POST",
		url : ctx + "commongame/gamelist/save",
		data : {
			"gameNickName" : gameNickName,
			"gameName" : gameName,
			"gameInstruction" : gameInstruction,
			"remark" : remark
		},
		async : false,
		error : function(request) {
			$.modalAlert("系统错误", modal_status.FAIL);
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("添加游戏成功,正在刷新数据请稍后……", {
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