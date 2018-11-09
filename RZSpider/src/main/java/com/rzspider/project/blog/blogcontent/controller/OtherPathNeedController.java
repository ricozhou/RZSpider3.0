package com.rzspider.project.blog.blogcontent.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.socket.TextMessage;

import com.rzspider.common.constant.CommonSymbolicConstant;
import com.rzspider.common.constant.FileExtensionConstant;
import com.rzspider.common.constant.FileMessageConstant;
import com.rzspider.common.constant.FileOtherConstant;
import com.rzspider.common.constant.ReturnMessageConstant;
import com.rzspider.common.constant.UserConstants;
import com.rzspider.common.constant.WebSocketConstants;
import com.rzspider.common.constant.project.BlogConstant;
import com.rzspider.common.utils.FileUploadUtils;
import com.rzspider.common.utils.OtherUtils;
import com.rzspider.common.utils.security.ShiroUtils;
import com.rzspider.framework.aspectj.lang.annotation.Log;
import com.rzspider.framework.config.FilePathConfig;
import com.rzspider.framework.web.controller.BaseController;
import com.rzspider.framework.web.domain.Message;
import com.rzspider.framework.web.page.TableDataInfo;
import com.rzspider.framework.websocket.service.WebSocketPushHandler;
import com.rzspider.project.blog.blogcontent.domain.Blogmove;
import com.rzspider.project.blog.blogcontent.service.IBlogmoveService;
import com.rzspider.project.system.role.service.IRoleService;

/**
 * 为了一些非常特殊的需求设置
 * 
 * @author ricozhou
 * @date 2018-10-19
 */
@Controller
@RequestMapping("/otherpath/pathneed")
public class OtherPathNeedController extends BaseController {
	private String prefix = "otherpath/pathneed";

	/**
	 * 在爬取今日头条文章时可以爬取到，但是由于今日头条下拉加载下一页，
	 * get请求url需要参数签名，解密了签名算法后写出为html在浏览器执行获取_signature可以使用，
	 * 但是使用htmlunit直接执行TAV.sign方法得到的_signature却无法使用不知为何，
	 * 使用htmlunit读取本地html也是如此，大概是签名需要一些浏览器的信息，
	 * 为了能够获取可以使用的_signature只能在自己的项目中加上一个路径访问此html然后获取有效的_signature， 曲线救国
	 */
	@GetMapping("/blogmovesign")
	public String blogmovesign(Model model) {
		//参数存入map中，立刻存入立刻取出立刻删除，保证每一次请求每个用户唯一参数
		model.addAttribute("user_id", "101528687217");
		model.addAttribute("max_behot_time", "1539352612");

		return prefix + "/blogmovesign";
	}

}
