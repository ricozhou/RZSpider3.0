$("#form-recommendset-update").validate({
	rules : {},
	submitHandler : function(form) {
		update();
	}
});
function update() {
	// 滚动
	var scrollRecommendedIds = $.getSelects("scrollRecommended");
	// 特别
	var specialRecommendedIds = $.getSelects("specialRecommended");
	// 列表
	var recommendedIds = $.getSelects("recommended");
	// 验证数量
	if (scrollRecommendedIds.split(',').length > blogset.blogsetScrollRecommendedShowNum) {
		$.modalAlert("滚动推荐文章超过规定数量！", modal_status.FAIL);
		return;
	}
	// 验证数量
	if (scrollRecommendedIds == null || scrollRecommendedIds == ''
			|| scrollRecommendedIds.split(',').length < 1) {
		$.modalAlert("滚动推荐文章数量不能为0！", modal_status.FAIL);
		return;
	}
	if (specialRecommendedIds.split(',').length > blogset.blogsetSpecialRecdShowNum) {
		$.modalAlert("特别推荐文章超过规定数量！", modal_status.FAIL);
		return;
	}
	if (recommendedIds.split(',').length > blogset.blogsetRecommendedShowNum) {
		$.modalAlert("列表推荐文章超过规定数量！", modal_status.FAIL);
		return;
	}
	$.ajax({
		cache : true,
		type : "POST",
		url : ctx + "blog/blogcontent/recommendSetSave",
		data : {
			"scrollRecommendedIds" : scrollRecommendedIds,
			"specialRecommendedIds" : specialRecommendedIds,
			"recommendedIds" : recommendedIds
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