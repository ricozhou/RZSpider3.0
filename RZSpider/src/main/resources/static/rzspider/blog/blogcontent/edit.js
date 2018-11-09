//加载文本编辑器
var imgArray = new Array();
var simplemde;
$().ready(
		function() {
			if($('#articleEditor').val()==1){
				$("#articleField").css('display', 'block');
				// 初始化编辑器
				simplemde = new SimpleMDE({
			        // textarea的DOM对象
			        element: document.getElementById("articleField"),
			        // 自动下载FontAwesome，设为false为不下载(如果设为false则必须手动引入)
			        autoDownloadFontAwesome: false,
			        placeholder: "请输入文章内容",
			        autosave: {
			            // 启用自动保存功能
			            enabled: false,
			            // 自动保存的间隔，以毫秒为单位。默认为10000（10s）
			            delay: 15000,
			            // 唯一的字符串标识符(保证每个SimpleMDE编辑器的uniqueId唯一)
			            uniqueId: "editor-1"
			        },
			        renderingConfig: {
			            // 如果设置为true，将使用highlight.js高亮显示。默认为false
			            codeSyntaxHighlighting: true
			        },
			        showIcons: ["code", "table", "clean-block", "horizontal-rule"],
			        tabSize: 4,
			        // 编辑器底部的状态栏
			        status: true,
			        status: [ "lines", "words"] // Optional usage
			    });
				$(".editor-preview-side").addClass("markdown-body");
			}else{
				$("#content_sn").css('display', 'block');
				$('.summernote').summernote({
					height : '220px',
					lang : 'zh-CN',
					callbacks : {
						onImageUpload : function(files, editor, $editable) {
							sendBlogFile(files, imgArray,blogFileName,true);
						}
					}
				});
				var content2 = $("#content").val();
				$('#content_sn').summernote('code', content2);
			}
			// 赋值images
			if ($('#images').val() != null&&$('#images').val() !='') {
				imgArray = $('#images').val().split(",");
			}

			// 初始化专栏
			$.ajax({
				cache : true,
				type : "GET",
				url : ctx + "blog/blogcolumn/selectBlogCcolumnList",
				async : false,
				error : function(request) {
					$.modalAlert("系统错误", modal_status.FAIL);
				},
				success : function(data) {
					// 动态显示专栏类型
					var newOption;
					$("#blogColumnName").find("option").remove();
					newOption = document.createElement("option");
					newOption.text = '默认';
					newOption.value = '默认';
					document.getElementById("blogColumnName").add(newOption);
					if (data != null) {
						for (var i = 0; i < data.length; i++) {
							newOption = document.createElement("option");
							newOption.text = data[i].blogColumnName;
							newOption.value = data[i].blogColumnName;
							document.getElementById("blogColumnName").add(
									newOption);
						}
						// 设置初始值
						var blogColumnNameh = $("#blogColumnNameh").val();
						var obj = document.getElementById("blogColumnName");
						for (var i = 0; i < obj.options.length; i++) {
							var tmp = obj.options[i].value;
							if (tmp == blogColumnNameh) {
								obj.options[i].selected = 'selected';
								break;
							}
						}
					} else {
						$.modalAlert(data.msg, modal_status.FAIL);
					}

				}
			});

			// 初始化全局设置
			if (basicsetGlobalAllowComment == 1) {
				document.getElementById("allow_comment").removeAttribute(
						"checked");
				// 全局不允许评论
				document.getElementById("allow_comment").setAttribute(
						"disabled", true);
			}
			if (basicsetGlobalAllowReprint == 1) {
				document.getElementById("ping").removeAttribute("checked");
				// 全局不允许转载
				document.getElementById("ping").setAttribute("disabled", true);
			}
			if (basicsetOpenBlogDownload == 1) {
				document.getElementById("allow_download").removeAttribute("checked");
				// 全局不允许下载
				document.getElementById("allow_download").setAttribute("disabled", true);
			}
		});
// 绑定参数添加
function addTags(obj) {
	html = '<div class="col-sm-2"><div class="input-group inputParams">'
			+ '<input type="text" class="form-control" id="tagInput" name="tagInput" onblur="checkTagUniue(this)">'
			+ '<span class="input-group-btn">'
			+ '<button class="btn btn-info" type="button" data-toggle="tooltip" title="删除标签" id="delTags"><span class="glyphicon glyphicon-minus"></span></button>'
			+ '</span>' + '</div></div>'
	document.getElementById('addTags2').insertAdjacentHTML('afterend', html)
}

// 绑定删除
$(document).on('click', '#delTags', function() {
	var el = this.parentNode.parentNode.parentNode
	el.parentNode.removeChild(el);
	// 关闭弹窗
	layer.closeAll('dialog');
});
// 监听光标检查tag唯一
function checkTagUniue(obj) {
	if (obj.value == null || obj.value == '') {
		return;
	}
	// 后台校验
	$.ajax({
		cache : true,
		type : "POST",
		url : ctx + "blog/blogtags/checkTagNameUnique",
		data : {
			"blogTagsName" : obj.value
		},
		async : false,
		error : function(request) {
			$.modalAlert("系统错误", modal_status.FAIL);
		},
		success : function(data) {
			if (data != 0) {
				// 直接去掉此标签
				deleteTags(obj);
			}
		}
	});
}
// 删除标签
function deleteTags(obj) {
	var el = obj.parentNode.parentNode
	el.parentNode.removeChild(el);
	// 关闭弹窗
	layer.closeAll('dialog');
}
// 表单验证
$("#form-blogcontent-edit").validate({
	rules : {
		title : {
			required : true,
			maxlength : 100
		},
		author : {
			required : true,
			maxlength : 50
		},
		introduction : {
			maxlength : 1000
		},
		content : {
			required : true,
			maxlength : 100000
		}

	},
	messages : {
		"title" : {
			maxlength : '最长50字'
		},
		"author" : {
			maxlength : "最长20字",
		},
		"content" : {
			maxlength : "最长500000字",
		}
	},
	submitHandler : function(form) {
		update(1);
	}
})
function update(status) {
	// id
	var cid = $("#cid").val();
	var showId = $("#showId").val();
	// 文章还是草稿的状态
	// 标题
	var title = $("#title").val();
	if (title.length < 1 || title.length > 100) {
		$.modalAlert("标题不能为空且字数不能超过50", modal_status.FAIL);
		return;
	}
	// 作者
	var author = $("#author").val();
	if (author.length < 1 || author.length > 50) {
		$.modalAlert("作者不能为空且字数不能超过20", modal_status.FAIL);
		return;
	}
	// 简介
	var introduction = $("#introduction").val();
	if (introduction.length > 1000) {
		$.modalAlert("简介字数不能超过500", modal_status.FAIL);
		return;
	}
	var articleEditor=$('#articleEditor').val();
	// 内容
	var content='';
	var contentMd='';
	var text='';
	if(articleEditor==1){
		content= simplemde.markdown(simplemde.value());
		contentMd= simplemde.value();
		text=removeHTMLTag(content);
		if (content == "" || content.length > 1000000) {
			$.modalAlert("文章不能为空且字数不能超过50万", modal_status.FAIL);
			return;
		}
	}else{
		content = $("#content_sn").summernote('code');
		if (content == "<p><br></p>" || content.length > 1000000) {
			$.modalAlert("文章不能为空且字数不能超过50万", modal_status.FAIL);
			return;
		}
		text = $($("#content_sn").summernote("code")).text();
	}

	// 类型
	var type = $("#type").val();

	// 专栏
	var blogColumnName = $("#blogColumnName").val();

	// 是否开启评论
	var allowComment = $("#allow_comment").is(':checked') == true ? 0 : 1;
	// 是否允许转载
	var allowPing = $("#ping").is(':checked') == true ? 0 : 1;
	// 是否允许下载
	var allowDownload= $("#allow_download").is(':checked') == true ? 0 : 1;
	// 是否置顶
	var articleTop= $("#article_top").is(':checked') == true ? 0 : 1;
	// 是否开启简介
	var showIntroduction = $("#allow_introduction").is(':checked') == true ? 0
			: 1;
	// 是否私密文章
	var privateArticle = $("#private_article").is(':checked') == true ? 0 : 1;

	// 标签
	var tagsIds = $.getCheckeds("tag");
	// 获取添加的新标签
	// 以下开始
	var blogtags;
	// 拼接默认参数为json
	var tag = '';
	var success = true;
	// 只能英文中文和数字
	var regEnCnNum = /^[A-Za-z0-9\u4e00-\u9fa5]+$/im;
	// 遍历所有的参数
	$(".input-group.inputParams").each(function(i) {
		tag = $(this).find("#tagInput").val();
		if (tag == '') {
			$.modalAlert('标签不能为空', modal_status.FAIL);
			success = false;
			return;
		}
		// 验证
		// 长度限制，类型等
		// 长度限制
		if (tag.length > 10) {
			$.modalAlert('超过规定长度10个字', modal_status.FAIL);
			success = false;
			return;
		}
		// 特殊字符限制限制
		if (!regEnCnNum.test(tag)) {
			$.modalAlert('不能携带特殊字符', modal_status.FAIL);
			success = false;
			return;
		}
		if (0 == i) {
			blogtags = tag;
		} else {
			blogtags += ("," + tag);
		}
	});

	if (!success) {
		return;
	}

	// 设置参数
	// 不再以数组的形式传递，直接以逗号隔开
	// 判断是否重复
	if (blogtags != null && blogtags != '') {
		if (isRepeat(blogtags.split(","))) {
			$.modalAlert("标签重复！", modal_status.FAIL);
			return;
		}
	}

	// 如果简介为空则截取首段
	if (introduction == '') {
		introduction = (text.length > 100) ? text.substring(0, 100) : text;
	}
	// 获取所有图片链接
	var images = getContentImgArray(content, text);
	$.ajax({
		cache : true,
		type : "POST",
		url : ctx + "blog/blogcontent/save",
		data : {
			"cid" : cid,
			"showId" : showId,
			"status" : status,
			"title" : title,
			"blogFileName" : blogFileName,
			"author" : author,
			"introduction" : introduction,
			"content" : content,
			"contentMd" : contentMd,
			"cover" : cover,
			"images" : images,
			"type" : type,
			"blogColumnName" : blogColumnName,
			"allowComment" : allowComment,
			"allowPing" : allowPing,
			"allowDownload" : allowDownload,
			"showIntroduction" : showIntroduction,
			"privateArticle" : privateArticle,
			"tagsIds" : tagsIds,
			"blogtags" : blogtags,
			"articleEditor" : articleEditor,
			"articleTop" : articleTop
		},
		async : false,
		error : function(request) {
			$.modalAlert("系统错误", modal_status.FAIL);
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("修改文章成功,正在刷新数据请稍后……", {
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
// 数组是否重复
function isRepeat(array) {
	let nary = array.slice().sort();
	for (let i = 0; i < array.length; i++) {
		if (nary[i] == nary[i + 1]) {
			return true;
		}
	}
	return false;
}
// 获取文章所有图片
function getContentImgArray(content, text) {
	var images = '';
	if (imgArray != null && imgArray.length > 0) {
		for (var i = 0; i < imgArray.length; i++) {
			if (content.indexOf(imgArray[i]) > 0
					&& text.indexOf(imgArray[i]) < 0) {
				if (i == 0) {
					images = imgArray[i];
				} else {
					images += ("," + imgArray[i]);
				}
			}
		}
	}
	return images;
}
// 获取html纯文本
function removeHTMLTag(str) {
    str = str.replace(/<\/?[^>]*>/g,''); // 去除HTML tag
    str = str.replace(/[ | ]*\n/g,'\n'); // 去除行尾空白
    // str = str.replace(/\n[\s| | ]*\r/g,'\n'); //去除多余空行
    str=str.replace(/&nbsp;/ig,'');// 去掉&nbsp;
    str=str.replace(/\s/g,''); // 将空格去掉
    return str;
}