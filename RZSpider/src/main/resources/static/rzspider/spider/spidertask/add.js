$("#form-spidertask-add").validate({
	rules : {
		taskName : {
			required : true,
			maxlength : 30,
			remote: {
                url: ctx + "spider/spidertask/checkTaskNameUnique",
                type: "post",
                dataType: "json",
                data: {
                	"taskName" : function() {
                        return $.trim($("#taskName").val());
                    }
                },
                dataFilter: function(data, type) {
                    if (data == "0") return true;
                    else return false;
                }
            }
		},
		times : {
			required : true,
			digits : true,
			min : 1,
			max:31
		},
		cronExpression:{
			required:true,
		},
	},
	
	messages : {
		"taskName" : {
			remote: "任务已经存在",
			maxlength : '最长30字'
		},
	},
	submitHandler : function(form) {
		add();
	}
});

// 一开始便加载
$(document).ready(function() {
	$('.form_datetime').datetimepicker({
		language:  'zh-CN',
	    weekStart: 0, // 一周从哪一天开始
	    todayBtn:  1, //
	    autoclose: 1,
	    todayHighlight: 1,
	    startView: 2,
	    forceParse: 0,
	    showMeridian: 1,
	    todayBtn:true,
	    todayHighlight:true,
	    keyboardNavigation:true
	});
	// 设置显示当前时间now.setSeconds(now.getSeconds() - 10);
	var nowDate=new Date();
	//往后加十秒
	nowDate.setSeconds(nowDate.getSeconds() + 10);
	$('#firstStartTime').val(nowDate.format("yyyy-MM-dd hh:mm:ss"));
	// 设置组件起始时间
    $(".form_datetime").datetimepicker("setStartDate",nowDate);
	// 查询所有爬虫类型和名称
	$.ajax({
		cache : true,
		type : "GET",
		url : ctx + "spider/spidermanage/spiderTypeAndNameList",
		async : false,
		error : function(request) {
			$.modalAlert("系统错误", modal_status.FAIL);
		},
		success : function(data) {
			if (data != null) {
				// 动态显示爬虫类型
				$("#spiderType").find("option").remove();
				$("#spiderType").append("<option value='全部'>全部</option>");
				 let arr = unique(data);
				for (var i = 0; i < arr.length; i++) {
					var newOption = document.createElement("option");
					newOption.text = arr[i];
					newOption.value = arr[i];
					document.getElementById("spiderType").add(newOption);
				}
				bingSpiderName(data);
			} else {
				$.modalAlert(data.msg, modal_status.FAIL);
			}

		}
	});

})

// 去重
function unique(array) {
    let arr = [];
    for(let i in array) {
        if(arr.indexOf(array[i].spiderType) < 0) {
            arr.push(array[i].spiderType);
        }
    }
    return arr;
}

// 动态绑定
function bingSpiderName(data){
	// 动态显示爬虫名称
	$("#spiderName").find("option").remove();
	for (var i = 0; i < data.length; i++) {
		var newOption = document.createElement("option");
		newOption.text = data[i].spiderName+" ("+data[i].spiderBackId+")";
		newOption.value = data[i].spiderName;
		document.getElementById("spiderName").add(newOption);
	}
}

// 绑定监听事件
$(function() {
	$('#spiderType').bind('change', function() {
		var spiderType = $(this).val();
		// 根据分类返回对应的爬虫
		$.ajax({
			cache : true,
			type : "GET",
			url : ctx + "spider/spidermanage/spiderTypeAndNameList",
			data : {
				"spiderType" : spiderType
			},
			async : false,
			error : function(request) {
				$.modalAlert("系统错误", modal_status.FAIL);
			},
			success : function(data) {
				if (data != null) {
					bingSpiderName(data);
				} else {
					$.modalAlert(data.msg, modal_status.FAIL);
				}

			}
		});

	});
	$('#taskExecfrequency').bind('change', function() {
		var taskExecfrequency = $(this).val();
		// 根据分类返回对应的爬虫
		if(taskExecfrequency==0){
			// 自定义
			// 则显示输入框
			// 清空
			$("#cronExpression").val(''); 
			$("#times").val(''); 
			$("#customSet1").css('display','none'); 
			$("#customSet2").css('display','none'); 
			$("#customSet3").css('display','block'); 
			$("#customSet4").css('display','none');
		}else if(taskExecfrequency==1){
			$("#cronExpression").val(''); 
			$("#times").val('1'); 
			$("#customSet1").css('display','block'); 
			$("#customSet2").css('display','block'); 
			$("#customSet3").css('display','block');
			$("#customSet4").css('display','none');
		}else if(taskExecfrequency==2){
			$("#times").val(''); 
			$("#customSet1").css('display','none'); 
			$("#customSet2").css('display','none'); 
			$("#customSet3").css('display','none');
			$("#customSet4").css('display','block');
		}else if(taskExecfrequency==3){
			$("#times").val(''); 
			$("#customSet1").css('display','none'); 
			$("#customSet2").css('display','none'); 
			$("#customSet3").css('display','none');
		}
		
	});
	
});

// 绑定监听事件
$(function() {
	$('#spiderName').bind('change', function() {
		var spiderName = $(this).val();
		// 根据分类返回对应的爬虫
		$.ajax({
			cache : true,
			type : "GET",
			url : ctx + "spider/spidermanage/spiderTypeAndNameList",
			data : {
				"spiderName" : spiderName
			},
			async : false,
			error : function(request) {
				$.modalAlert("系统错误", modal_status.FAIL);
			},
			success : function(data) {
				if (data != null) {
					// 根据爬虫名称选择类型
					 var obj = document.getElementById("spiderType");
					 for(var i = 0; i < obj.options.length; i++){
					        var tmp = obj.options[i].value;
					        if(tmp == data[0].spiderType){
					        	obj.options[i].selected = 'selected';
				                break;
					        }
					    }
				} else {
					$.modalAlert(data.msg, modal_status.FAIL);
				}

			}
		});

	});
});



/* 跳转参数设置界面 */
function add() {
		var taskName = $("input[name='taskName']").val();
		var spiderName = $("#spiderName").val();
		if(spiderName==null){
			$.modalAlert('暂无爬虫可使用', modal_status.FAIL);
			return;
		}
		var taskStatus = $("#11").is(':checked') == true ? 0 : 1;
		var taskExecfrequency = $("#taskExecfrequency").val();
		var cronExpression = $("#cronExpression").val();
		var times = $("#times").val();
		var taskExecfrequency2 = $("#taskExecfrequency2").val();
		var firstStartTime = $("#firstStartTime").val();
		if(taskExecfrequency==3){
			// 立即执行必须获取当前时间
			firstStartTime=new Date().format("yyyy-MM-dd hh:mm:ss");
			// 更新，后台直接获取服务器当前时间避免客户端与服务端时间差
		}
		
		var remark = $("input[name='remark']").val();
		$.ajax({
			cache : true,
			type : "POST",
			url : ctx + "spider/spidertask/save",
			data : {
				"taskName" : taskName,
				"spiderName" : spiderName,
				"taskStatus" : taskStatus,
				"taskExecfrequency" : taskExecfrequency,
				"cronExpression" : cronExpression,
				"times" : times,
				"taskExecfrequency2" : taskExecfrequency2,
				"firstStartTime" : firstStartTime,
				"remark" : remark,
			},
			async : false,
			error : function(request) {
				$.modalAlert("系统错误", modal_status.FAIL);
			},
			success : function(data) {
				if (data.code == 0) {
				
					 parent.layer.msg("新增成功,正在刷新数据请稍后……", {
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
