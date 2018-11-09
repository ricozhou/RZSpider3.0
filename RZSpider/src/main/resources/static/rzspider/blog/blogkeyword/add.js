$("#form-blogkeyword-add").validate({
	rules : {
		keywordName : {
			required : true,
			maxlength : 200,
			remote : {
				url : ctx + "blog/blogkeyword/checkKeywordNameUnique",
				type : "post",
				dataType : "json",
				data : {
					"keywordName" : function() {
						return $.trim($("#keywordName").val());
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
		keywordReplaceWordName : {
			maxlength : 200
		},
		remark : {
			maxlength : 1000
		},
	},
	messages : {
		"keywordName" : {
			remote : "关键词已经存在"
		}
	},
	submitHandler : function(form) {
		add();
	}
});

function add() {
	var keywordName = $("#keywordName").val();
	var keywordCommentBan = $("#allow_introduction").is(':checked') == true ? 0
			: 1;
	var keywordMessageBan = $("#private_article").is(':checked') == true ? 0
			: 1;
	var keywordSuggestionBan = $("#allow_download").is(':checked') == true ? 0
			: 1;
	var keywordReplace = $("#allow_comment").is(':checked') == true ? 0 : 1;
	var keywordReplaceWordName = $("#keywordReplaceWordName").val();
	var status = $("input[name='status']").is(':checked') == true ? 0 : 1;
	var remark = $("input[name='remark']").val();
	$.ajax({
		cache : true,
		type : "POST",
		url : ctx + "blog/blogkeyword/save",
		data : {
			"keywordName" : keywordName,
			"keywordCommentBan" : keywordCommentBan,
			"keywordMessageBan" : keywordMessageBan,
			"keywordSuggestionBan" : keywordSuggestionBan,
			"keywordReplace" : keywordReplace,
			"keywordReplaceWordName" : keywordReplaceWordName,
			"status" : status,
			"remark" : remark
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