package com.youtu.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *@author:张晓芬
 *@date:2018年2月15日  下午9:49:30
**/
@Controller
public class IndexController {
	@RequestMapping("/index")
	public String showIndex(){
		return "index";
	}

}
