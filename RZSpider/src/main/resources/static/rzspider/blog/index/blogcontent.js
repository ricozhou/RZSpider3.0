$(document)
		.ready(
				function() {
					title =  blogcontent.title + "-" +basicsetTitle;
					// 设置专栏信息
					$("#n1").css('display', 'block');
					if (blogcolumn != null && blogcolumn != '') {
						// 设置专栏信息
						$("#n2").css('display', 'block');
						if (blogcolumn.columnType == 'M') {
							// 主专栏
							// 首页，主专栏
							document.getElementById("n1").href = "/rzblog";
							document.getElementById("n1").innerText = "首页";

							document.getElementById("n2").href = '/rzblog'
									+ blogcolumn.url;
							document.getElementById("n2").innerText = blogcolumn.blogColumnName;
							// 设置右侧位置信息
							document.getElementById("columnMsg").innerHTML = '您现在的位置是：<a href="/rzblog">首页</a> > <a href="/rzblog'
									+ blogcolumn.url
									+ '">'
									+ blogcolumn.blogColumnName + '</a> > ';
						} else if (blogcolumn.columnType == 'C') {
							// 主专栏，次专栏
							document.getElementById("n1").href = '/rzblog'
									+ blogcolumn.parent.url;
							document.getElementById("n1").innerText = blogcolumn.parent.blogColumnName;
							document.getElementById("n2").href = '/rzblog'
									+ blogcolumn.parent.url + blogcolumn.url;
							document.getElementById("n2").innerText = blogcolumn.blogColumnName;
							// 设置右侧位置信息
							document.getElementById("columnMsg").innerHTML = '您现在的位置是：<a href="/rzblog">首页</a> > <a href="/rzblog'
									+ blogcolumn.parent.url
									+ '">'
									+ blogcolumn.parent.blogColumnName
									+ '</a> > <a href="/rzblog'
									+ blogcolumn.parent.url
									+ blogcolumn.url
									+ '">'
									+ blogcolumn.blogColumnName
									+ '</a> > ';
						}
					} else {
						// 设置右侧位置信息
						document.getElementById("columnMsg").innerHTML = '您现在的位置是：<a href="/rzblog">首页</a> > ';
					}
					document.title = title;

					// 设置文章信息栏
					// 默认的不显示，其他的显示
					column = getHtmlColumn(blogcolumn);
					// 来源
					articleSourceHtml = getHtmlArticleSource(blogcontent,blogset.basicsetShowArticleSource);
					var browseNum=parseInt(blogcontent.browseNum)+1;
					var bloginfoHtml = '<li class="author"><i class="fa fa-user"></i> '
							+ blogcontent.author
							+ '</li>'
							+ column
							+ '<li class="timer"><i class="fa fa-calendar-o"></i> '
							+ new Date(blogcontent.gtmCreate)
									.format("yyyy-MM-dd hh:mm:ss")
							+ '</li><li class="view"><i class="fa fa-eye"></i> ' +browseNum
							+ '</li><li class="like" id="likeNum"><i class="fa fa-thumbs-up"></i> ' + blogcontent.likeNum
							+ '</li>'+articleSourceHtml;
					// 添加
					document.getElementById("bloginfo").insertAdjacentHTML(
							'afterBegin', bloginfoHtml);

					// 设置标题
						var titleHtml = '<span class="article-type type-'
							+ (blogcontent.type == '原创' ? '1'
									: (blogcontent.type == '转载' ? '2' : '3'))
							+ '">'
							+ (blogcontent.type == '原创' ? '原'
									: (blogcontent.type == '转载' ? '转' : '译'))
							+ '</span>'
							+ blogcontent.title;
					// 添加
					document.getElementById("news_title").insertAdjacentHTML(
							'afterBegin', titleHtml);
						
					// 设置标签信息
					var tagsHtml = getHtmlTags(blogtags);
					// 添加
					document.getElementById("tags").insertAdjacentHTML(
							'afterBegin', tagsHtml);

					// 设置版权信息
					if(blogset.basicsetGlobalAllowReprint==0){
						if(blogcontent.allowPing==0){
							if(blogset.basicsetAddCopyrightNotice==0&&blogcontent.type=='原创'){
								var copyright="版权声明：本文为博主原创文章，未经博主允许不得转载。  "+window.location.href;
								if(blogset.basicsetCopyrightNoticeInfo!=null&&blogset.basicsetCopyrightNoticeInfo!=''){
									copyright=blogset.basicsetCopyrightNoticeInfo+'  '+window.location.href;
								}
								addCopyrightMsg(copyright);
							}
						}else{
							// 不允许转载
							addCopyrightMsg('不允许转载！');
						}
					}else{
						// 不允许转载
						addCopyrightMsg('不允许转载！');
					}
					
					// 设置简介信息
					if (blogcontent.showIntroduction == 0) {
						$("#news_about").css('display', 'block');
						var introductionHtml = getHtmlIntroduction(blogcontent);
						// 添加
						document.getElementById("news_about")
								.insertAdjacentHTML('afterBegin',
										introductionHtml);
					}
					
					// 设置是否开启赞赏
					if(blogset.basicsetOpenAppreciate==0){
						// 开启
						$("#dasbox").css('display', 'block');
					}else{
						$(".diggit").css('float', 'none');
					}
					
					// 设置是否文章可下载
					if(blogset.basicsetOpenBlogDownload==0){
						if(blogcontent.allowDownload==0){
							// 开启
							$("#downContent").css('display', 'block');
						}
					}
					
					// 上下篇文章
					var upblogcontentHtml='<i class="fa fa-caret-up"></i> 上一篇：<a href="/rzblog">返回首页</a>';
					if(upblogcontent!=null&&upblogcontent!=''){
						upblogcontentHtml='<i class="fa fa-caret-up"></i> 上一篇：<a href="/rzblog/blogcontent/details/'+upblogcontent.showId+'" target="_blank" title="'+upblogcontent.title+'">'+upblogcontent.title+'</a>';
					}
					
					var downblogcontentHtml='<i class="fa fa-caret-down"></i> 下一篇：<a href="/rzblog">返回首页</a>'
					if(downblogcontent!=null&&downblogcontent!=''){
						downblogcontentHtml='<i class="fa fa-caret-down"></i> 下一篇：<a href="/rzblog/blogcontent/details/'+downblogcontent.showId+'" target="_blank" title="'+downblogcontent.title+'">'+downblogcontent.title+'</a>';
					}
					// 添加
					document.getElementById("upblogcontent")
					.insertAdjacentHTML('afterBegin',
							upblogcontentHtml);
					document.getElementById("downblogcontent")
					.insertAdjacentHTML('afterBegin',
							downblogcontentHtml);
					
					// 相关文章
					if(drelatedblogcontentList!=null&&drelatedblogcontentList!=''&&drelatedblogcontentList.length>0){
						$("#otherlink").css('display', 'block');
						var drelatedblogcontentListHtml='';
						for(var i=0;i<drelatedblogcontentList.length;i++){
							drelatedblogcontentListHtml=drelatedblogcontentListHtml+'<li><a href="/rzblog/blogcontent/details/'+drelatedblogcontentList[i].showId+'" target="_blank" title="'+drelatedblogcontentList[i].title+'">'+drelatedblogcontentList[i].title+'</a></li>';
						}
						document.getElementById("drelated")
						.insertAdjacentHTML('afterBegin',
								drelatedblogcontentListHtml);
					}
					
					// 初始化评论
					$(".comment-list").addCommentList({data:blogcomentList,add:""});
					// 监听
					$("#comment").click(function(){
						sendComment();
					});
					
					// 设置是否允许评论
					if(!blogset.basicsetGlobalAllowComment==0||!blogcontent.allowComment==0){
						if(blogcomentList==null||blogcomentList==''||blogcomentList.length<1){
							// 不允许
							$("#news_pl").css('display', 'none');
						}else{
							// 只隐藏form
							$(".commentbox").css('display', 'none');
							$("#gbko").css('display', 'none');
							$(".reply-btn").css('display', 'none');
     						$(".reply-list-btn").css('display', 'none');
						}
					}
					
					// 页面浏览量加一
					addBrowseAndKikeNum(0);
				})
	
// 添加版权信息
function addCopyrightMsg(msg){
	// 显示
	$("#articleCopyright").css('display', 'block');
	// 添加
	document.getElementById("articleCopyright")
			.insertAdjacentHTML('afterBegin',
					msg);
}				
// 添加评论
function sendComment(){
	
	var obj = new Object();
	obj.replyHeadImg="/img/blog/default1.jpg";
	obj.replyName=$('#comnickname').val();
	obj.beReplyName='';
	obj.replyContent=$("#commentcontent").val();
	obj.replyQqNum=$('#comqqnum').val();
	obj.replyEmail=$('#comemail').val();
	obj.parentId=0;
	obj.showId=blogcontent.showId;
	obj.title=blogcontent.title;
	// 验证参数
	var flag=checkCommentContent(obj);
	if(flag==1){
		$.modalMsg("请输入昵称和评论内容！", "warning");
		return;
	}else if(flag==2){
		$.modalMsg("评论内容不能超过200字！", "warning");
		return;
	}else if(flag==3){
		$.modalMsg("QQ号格式不正确！", "warning");
		return;
	}else if(flag==4){
		$.modalMsg("邮箱格式不正确！", "warning");
		return;
	}else if(flag==5){
		$.modalMsg("昵称不能超过10个字！", "warning");
		return;
	}
	
	$(".comment-list").addCommentList({data:[],add:obj});
}		
				
// 专栏
function getHtmlColumn(data) {
	var htmlColumn = '';
	if (data != null && data.blogColumnName != '默认') {
		htmlColumn = '<li class="lmname"><i class="fa fa-list"></i> ' + data.blogColumnName + '</li>';
	}
	return htmlColumn;
}
// 来源
function getHtmlArticleSource(data, basicsetShowArticleSource) {
	var html = '';
	if (basicsetShowArticleSource == 0 && data.articleSource != null
			&& data.articleSource != '本站') {
		html = '<li class="articleSource"><i class="fa fa-thumb-tack"></i> '
				+ data.articleSource + '</li>';
	}
	return html;
}
// 标签信息
function getHtmlTags(data) {
	var tagsHtml = '';
	if (data == null || data == '' || data.length < 1) {
		return tagsHtml;
	}
	for (var i = 0; i < data.length; i++) {
		if (data[i].flag == true) {
			tagsHtml = tagsHtml + '<a href="/rzblog/blogtags/?tags='
					+ data[i].blogTagsName + '" target="_blank">'
					+ data[i].blogTagsName + '</a> &nbsp;';
		}
	}
	return tagsHtml;
}
// 简介
function getHtmlIntroduction(data) {
	var introductionHtml = '';
	if (data != null && data.showIntroduction == 0) {
		introductionHtml = '<strong>简介</strong>' + data.introduction;
	}
	return introductionHtml;
}

var num=0;
// 增加浏览和点赞量
function addBrowseAndKikeNum(flag) {
	// flag0是浏览量，1是点赞量
	$.ajax({
		cache : true,
		type : "POST",
		url : "/rzblog/blogcontent/addBrowseAndKikeNum",
		data : {
			"cid" : blogcontent.cid,
			"flag" : flag
		},
		async : false,
		error : function(request) {
			
		},
		success : function(data) {
			if (data.code == 0&&flag==1) {
				// 更改点赞量
				num=parseInt($('#diggnum').html())+1;
				$('#likeNum').html('<i class="fa fa-thumbs-up"></i> '+num);
				num=parseInt($('#diggnum').html())+1;
				$('#diggnum').html(num);
			} 
		}
	});
}

// 下载文章
// 直接前端实现，减少服务器压力
function downloadBlog(fileType){
	var fileName=blogcontent.title+'_'+blogcontent.author;
	if(fileType=='png'){
		// 图片直接前端截取生成下载
		screenshot(fileName,fileType);
	}else if(fileType=='txt'){
		var textstr =removeHTMLTag(document.getElementById("news_con").innerHTML);
		downBlog('data:text/csv;charset=utf-8,\ufeff'+encodeURIComponent(textstr),fileName,fileType);
	}else if(fileType=='doc'){
		exportWord(fileName);
	}else if(fileType=='pdf'){
		exportPdf(fileName);
	}
}

// 截图处理
function screenshot(fileName,fileType) {

	// 以下代码为了图片更清楚
	
// var shareContent = document.getElementById("news_con");
// var width = shareContent.offsetWidth;
// var height = shareContent.offsetHeight;
// var canvas = document.createElement("canvas");
// var scale = 2;
//
// canvas.width = width * scale;
// canvas.height = height * scale;
// canvas.getContext("2d").scale(scale, scale);
//
// var opts = {
// scale: scale,
// canvas: canvas,
// logging: true,
// width: width,
// height: height,
// allowTaint: true,
// // 允许污染
// taintTest: true,
// // 在渲染前测试图片(没整明白有啥用)
// useCORS: true,
// // 使用跨域(当allowTaint为true时这段代码没什么用)
// background: "#fff"
// };
// html2canvas(shareContent, opts).then(function (canvas) {
// // console.log(canvas.toDataURL('image/png',1.0))
// // 生成图片（格式）
// // downBlog(canvas.toDataURL('image/png',1.0),fileName,fileType)
// // var img = Canvas2Image.convertToPNG(canvas, canvas.width, canvas.height);
// // console.log(img)
// downBlog(canvas.toDataURL('image/png',1.0),fileName,fileType)
// // Canvas2Image.saveAsImage(canvas, canvas.width, canvas.height,fileType);
// // document.body.appendChild(img);
// // $(img).css({
// // "width": canvas.width / 2 + "px",
// // "height": canvas.height / 2 + "px",
// // })
// });
	var shareContent= document.getElementById("news_con");
	if(blogcontent.articleEditor==1){
		shareContent= document.getElementById("simplemdecontent");
	}
	
    html2canvas(shareContent, {
        allowTaint: true,
        // 允许污染
        taintTest: true,
        // 在渲染前测试图片(没整明白有啥用)
        useCORS: true,
        logging: false,
        // 使用跨域(当allowTaint为true时这段代码没什么用)
        background: "#fff"
    }).then(function(canvas) {
        // 生成图片（格式）
    	downBlog(canvas.toDataURL('image/png',1.0),fileName,fileType);
    });
}
 
 /**
	 * 下载图片,文本
	 */
 function downBlog(url,fileName,fileType) {
	// 以下代码为下载此图片功能  
	     var triggerDownload = $("<a>").attr("href", url).attr("download", fileName+"."+fileType).appendTo("body");  
	     triggerDownload[0].click();  
	     triggerDownload.remove();  
 }

// 导出Word
function exportWord(fileName){
	// 使用插件
	 $("#news_con").wordExport(fileName);
}

// 导出pdf
function exportPdf(fileName){
	var shareContent= document.getElementById("news_con");
	if(blogcontent.articleEditor==1){
		shareContent= document.getElementById("simplemdecontent");
	}
	
    html2canvas(shareContent, {
        allowTaint: true,
        // 允许污染
        taintTest: true,
        // 在渲染前测试图片(没整明白有啥用)
        useCORS: true,
        logging: false,// 不打印
        // 使用跨域(当allowTaint为true时这段代码没什么用)
        background: "#fff"
    }).then(function(canvas) {
    	var contentWidth = canvas.width;
        var contentHeight = canvas.height;

        // 一页pdf显示html页面生成的canvas高度;
        var pageHeight = contentWidth / 595.28 * 841.89;
        // 未生成pdf的html页面高度
        var leftHeight = contentHeight;
        // pdf页面偏移
        var position = 0;
        // a4纸的尺寸[595.28,841.89]，html页面生成的canvas在pdf中图片的宽高
        var imgWidth = 555.28;
        var imgHeight = 555.28/contentWidth * contentHeight;

        var pageData = canvas.toDataURL('image/jpeg', 1.0);

        var pdf = new jsPDF('', 'pt', 'a4');
        // 有两个高度需要区分，一个是html页面的实际高度，和生成pdf的页面高度(841.89)
        // 当内容未超过pdf一页显示的范围，无需分页
        if (leftHeight < pageHeight) {
            pdf.addImage(pageData, 'JPEG', 20, 0, imgWidth, imgHeight );
        } else {
            while(leftHeight > 0) {
                pdf.addImage(pageData, 'JPEG', 20, position, imgWidth, imgHeight)
                leftHeight -= pageHeight;
                position -= 841.89;
                // 避免添加空白页
                if(leftHeight > 0) {
                    pdf.addPage();
                }
            }
        }

        pdf.save(fileName+'.pdf');	
    });
}

 // html去除标签
 function removeHTMLTag(str) {

             str = str.replace(/<\/?[^>]*>/g,''); // 去除HTML tag
             str = str.replace(/[ | ]*\n/g,'\n'); // 去除行尾空白
             // str = str.replace(/\n[\s| | ]*\r/g,'\n'); //去除多余空行
             str=str.replace(/&nbsp;/ig,'');// 去掉&nbsp;
             str=str.replace(/\s/g,''); // 将空格去掉
             return str;
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
