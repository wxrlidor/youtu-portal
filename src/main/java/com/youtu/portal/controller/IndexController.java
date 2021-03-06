package com.youtu.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.youtu.portal.service.ContentService;

/**
 * @author:张晓芬
 * @date:2018年2月15日 下午9:49:30
 **/
@Controller
public class IndexController {
	@Autowired
	private ContentService contentService;
	/**
	 * 展示首页返回一个逻辑视图，需要把首页大广告位的json数据传递给jsp
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String showIndex(Model model) {
		// 取得首页大广告位数据
		String adJson = contentService.getAdContentList();
		model.addAttribute("ad1", adJson);
		// 取得右上角广告位数据
		adJson = contentService.getRightAdContentList();
		model.addAttribute("rightAd", adJson);
		// 取得小广告的数据
		adJson = contentService.getLittleAdContentList();
		model.addAttribute("littleAd", adJson);
		// 返回的index是逻辑视图
		return "index";
	}

	@RequestMapping(value = "/httpclient/post", method = RequestMethod.POST)
	@ResponseBody
	public String testPost(String username, String password) {
		return "username:" + username + "\tpassword:" + password;
	}
	// public YouTuResult testPost(){
		// return YouTuResult.ok();
		// }
}
