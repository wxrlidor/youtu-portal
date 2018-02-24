package com.youtu.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.youtu.portal.service.ContentService;

/**
 *@author:张晓芬
 *@date:2018年2月15日  下午9:49:30
**/
@Controller
public class IndexController {
	@Autowired
	private ContentService contentService;
	@RequestMapping("/index")
	//展示首页返回一个逻辑视图，需要把首页大广告位的json数据传递给jsp
	public String showIndex(Model model){
		String adJson = contentService.getContentList();
		//从index.jsp里面的${ad1}获取ad1
		model.addAttribute("ad1", adJson);
		//返回的index是逻辑视图
		return "index";
	}
	@RequestMapping(value = "/httpclient/post", method = RequestMethod.POST)
    @ResponseBody
//    public YouTuResult testPost(){
//		return YouTuResult.ok();		
//	}
    public String testPost(String username, String password){
		return "username:" + username + "\tpassword:"+ password;
	}
}
