package com.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PageController {
	/**
	 * Login登录页面
	 * @return
	 */
	@RequestMapping("doLoginUI")
	public String doLoginUI(){
		return "login";
	}
	/**
	 * Index主页
	 * @return
	 */
	@RequestMapping("doIndexUI")
	public String doIndexUI(){
		return "index";
	}
	/**
	 * Page分页页面
	 * @return
	 */
	@RequestMapping("doPageUI")
	public String doPageUI(){
		return "common/page";
	}
}