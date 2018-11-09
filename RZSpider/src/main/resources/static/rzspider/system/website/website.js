//设置网站基础信息
$("#form-website-websitesave").validate({
	rules : {
		websiteTitleName : {
			required : true,
			maxlength : 50
		},
		websiteDes : {
			maxlength : 100
		},
		websiteDomainName : {
			url : true,
			maxlength : 50
		},
		websitePublicIp : {
			required : true,
			checkIP : true,
			maxlength : 15,
			minlength : 7
		},
		websitePublicPort : {
			required : true,
			digits : true,
			max : 99999,
			min : 1
		},
		websiteCopyrightInformation : {
			required : true,
			maxlength : 500
		}

	},
	submitHandler : function(form) {
		websitesave();
	}
});

function websitesave() {
	var websiteId = $('#websiteId').val();
	var websiteTitleName = $('#websiteTitleName').val();
	var websiteDes = $('#websiteDes').val();
	var websiteDomainName = $('#websiteDomainName').val();
	if (websiteDomainName.indexOf('http://') == 0
			|| websiteDomainName.indexOf('https://') == 0) {
		if (websiteDomainName.substring(websiteDomainName.indexOf("://") + 3)
				.indexOf("/") != -1) {
			$.modalAlert("网址不正确", modal_status.FAIL);
			return;
		}
	} else if (websiteDomainName != '') {
		$.modalAlert("网址不正确", modal_status.FAIL);
		return;
	}
	var websitePublicIp = $('#websitePublicIp').val();
	var websitePublicPort = $('#websitePublicPort').val();
	var websiteCopyrightInformation = $('#websiteCopyrightInformation').val();
	var remark = $('#remark').val();
	$.ajax({
		cache : true,
		type : "POST",
		url : ctx + "system/website/websitesave",
		data : {
			"websiteId" : websiteId,
			"websiteTitleName" : websiteTitleName,
			"websiteIco" : fileBase64_1,
			"websiteDes" : websiteDes,
			"websiteDomainName" : websiteDomainName,
			"websitePublicIp" : websitePublicIp,
			"websitePublicPort" : websitePublicPort,
			"websiteCopyrightInformation" : websiteCopyrightInformation,
			"remark" : remark
		},
		async : false,
		error : function(request) {
			$.modalAlert("系统错误", modal_status.FAIL);
		},
		success : function(data) {
			if (data.code == 0) {
				layer.msg("设置成功,正在刷新数据请稍后……", {
					icon : 1,
					time : 500,
					shade : [ 0.1, '#fff' ]
				}, function() {
					// refreshTab();
				});
			} else {
				$.modalAlert(data.msg, modal_status.FAIL);
			}

		}
	});
}

// 设置邮件信息
$("#form-website-mailsave").validate({
	rules : {
		websiteMailSmtpUrl : {
			required : true,
			maxlength : 100
		},
		websiteMailSmtpPort : {
			required : true,
			digits : true,
			max : 99999,
			min : 1
		},
		websiteMailSenderMailbox : {
			required : true,
			email : true
		},
		websiteMailSenderNickname : {
			required : true,
			maxlength : 100
		},
		websiteMailSenderPassword : {
			required : true,
			maxlength : 100,
			minlength : 6
		}
	},
	submitHandler : function(form) {
		mailsave();
	}
});

function mailsave() {
	_ajax_save2(ctx + "system/website/mailsave", $('#form-website-mailsave')
			.serialize());
}