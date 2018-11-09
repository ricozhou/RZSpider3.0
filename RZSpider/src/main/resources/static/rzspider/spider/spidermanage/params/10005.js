$("#form-spidermanage-params").validate({
	rules : {
		pageNumber : {
			required : true,
			digits : true,
			min : 1,
			max : 100
		},
		projectName : {
			maxlength : 100
		},
		companyName : {
			maxlength : 100
		},
		preSaleCertificateNumber : {
			maxlength : 100
		}
	},
	messages : {},
	submitHandler : function(form) {
		update();
	}
});

// 一开始便加载
$(document).ready(function() {
	// 获取数据库中的参数
	var spiderDefaultParams = $("#spiderDefaultParams").val();
	var projectArea = "";
	var projectName = "";
	var companyName = "";
	var preSaleCertificateNumber = "";
	var pageNumber = "";
	var obj = "{}";
	if (spiderDefaultParams.replace(/(^s*)|(s*$)/g, "").length != 0) {
		obj = JSON.parse(spiderDefaultParams);
	}
	if (obj != null) {
		if (obj.length != 0) {
			projectArea = obj["projectArea"];
			projectName = obj["projectName"];
			companyName = obj["companyName"];
			preSaleCertificateNumber = obj["preSaleCertificateNumber"];
			pageNumber = obj["pageNumber"];
		}
	}
	// 设置项目区域
	if (projectArea != undefined) {
		var projectAreaObj = document.getElementById("projectArea");
		for (var i = 0; i < projectAreaObj.options.length; i++) {
			var tmp = projectAreaObj.options[i].value;
			if (tmp == projectArea) {
				projectAreaObj.options[i].selected = 'selected';
				break;
			}
		}
	}
	// 项目名称
	if (projectName != undefined) {
		$("#projectName").val(projectName);
	}
	// 公司名称
	if (companyName != undefined) {
		$("#companyName").val(companyName);
	}
	// 预售证号
	if (preSaleCertificateNumber != undefined) {
		$("#preSaleCertificateNumber").val(preSaleCertificateNumber);
	}
	// 页数
	if (pageNumber != undefined) {
		$("#pageNumber").val(pageNumber);
	}
})

// 打开网页
function openWebsite() {
	var web = "http://www.szfcweb.com/szfcweb/DataSerach/MITShowList.aspx";
	window.open(web);
}
// 拼接字符串然后更新设置参数
function update() {
	var spiderId = $("input[name='spiderId']").val();
	var projectArea = $("#projectArea").val();
	var projectName = $("#projectName").val();
	var companyName = $("#companyName").val();
	var preSaleCertificateNumber = $("#preSaleCertificateNumber").val();
	var pageNumber = $("#pageNumber").val();
	// 验证特殊字符
	// 特殊字符限制限制
	var regEn = /[`~!@#$%^&*()+<>?:"{},\/;'[\]\s]/im;
	// 特殊字符限制限制
	if (regEn.test(projectName) || regEn.test(companyName)
			|| regEn.test(preSaleCertificateNumber)) {
		$.modalAlert('不能携带特殊字符', modal_status.FAIL);
		return;
	}

	var data = '{ "projectArea":"' + projectArea + '","projectName":"'
			+ projectName + '","companyName":"' + companyName
			+ '","preSaleCertificateNumber":"' + preSaleCertificateNumber
			+ '","pageNumber":"' + pageNumber + '"}';
	$.ajax({
		cache : true,
		type : "POST",
		url : ctx + "spider/spidermanage/saveParams",
		data : {
			"spiderId" : spiderId,
			"spiderDefaultParams" : data
		},
		async : false,
		error : function(request) {
			$.modalAlert("系统错误", modal_status.FAIL);
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("修改成功,正在刷新数据请稍后……", {
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
