// 一开始便加载
$(document).ready(function() {
	// 获取数据库中的参数
	var taskParams = $("#taskParams").val();
	var projectArea = "";
	var projectName = "";
	var companyName = "";
	var preSaleCertificateNumber = "";
	var pageNumber = "";
	var obj = "{}";
	if (taskParams.replace(/(^s*)|(s*$)/g, "").length != 0) {
		obj = JSON.parse(taskParams);
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
