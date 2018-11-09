$("#form-downloadmanage-add").validate({
	rules:{
		downloadFileName:{
			required:true,
			maxlength:50
		},
		downloadFileLimitDownTime:{
			required:true
		},
	},
	submitHandler:function(form){
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
	// 往后加十秒
	nowDate.setDate(nowDate.getDate() + 100);
	$('#downloadFileLimitDownTime').val(nowDate.format("yyyy-MM-dd hh:mm:ss"));
	// 设置组件起始时间
    $(".form_datetime").datetimepicker("setStartDate",nowDate);

})

function add() {
	if(downloadFileUuidName==null||downloadFileUuidName==''){
		$.modalAlert('请先上传文件', modal_status.FAIL);
		return;
	}
    var downloadFileType = $("#radio1").is(':checked') == true ? 0 : ($("#radio2")
			.is(':checked') == true ? 1 : ($("#radio3")
					.is(':checked') == true ? 2 : ($("#radio4")
							.is(':checked') == true ? 3 : 4)));
    var downloadFileName2 = $("#downloadFileName").val();
	var downloadFileLimitDownNum = $("#downloadFileLimitDownNum").val();
	var downloadFileLimitDownTime = $("#downloadFileLimitDownTime").val();
	var status = $("input[name='status']").is(':checked') == true ? 0 : 1;
	var remark = $("input[name='remark']").val();
	$.ajax({
		cache : true,
		type : "POST",
		url : ctx + "tool/downloadmanage/save",
		data : {
			"downloadFileName" : downloadFileName2,
			"downloadFileType" : downloadFileType,
			"downloadFileUuidName" : downloadFileUuidName,
			"downloadFileExtensionName" : downloadFileExtensionName,
			"downloadFileSize" : downloadFileSize,
			"downloadFileLimitDownNum" : downloadFileLimitDownNum,
			"downloadFileLimitDownTime" : downloadFileLimitDownTime,
			"status" : status,
			"remark" : remark
		},
		async : false,
		error : function(request) {
			$.modalAlert("系统错误", modal_status.FAIL);
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("保存成功,正在刷新数据请稍后……", {
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

// 格式化时间
Date.prototype.format = function(fmt) { 
    var o = { 
       "M+" : this.getMonth()+1,                 // 月份
       "d+" : this.getDate(),                    // 日
       "h+" : this.getHours(),                   // 小时
       "m+" : this.getMinutes(),                 // 分
       "s+" : this.getSeconds(),                 // 秒
       "q+" : Math.floor((this.getMonth()+3)/3), // 季度
       "S"  : this.getMilliseconds()             // 毫秒
   }; 
   if(/(y+)/.test(fmt)) {
           fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
   }
    for(var k in o) {
       if(new RegExp("("+ k +")").test(fmt)){
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        }
    }
   return fmt; 
} 