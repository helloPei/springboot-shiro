package com.demo.controller;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.common.vo.JsonResult;
import com.demo.common.vo.PageObject;
import com.demo.pojo.Config;
import com.demo.service.ConfigService;

@Controller
@RequestMapping("/config/")
@RequiresPermissions("config")
public class ConfigController {
	@Autowired
    private ConfigService configService;
	
    /**配置页面*/
	@RequestMapping("doConfigListUI")
	public String doConfigListUI(){
		return "sys/config_list";
	}
	/**配置编辑页面*/
	@RequestMapping("doConfigEditUI")
	public String doConfigEditUI(){
		return "sys/config_edit";
	}
	@RequestMapping("doUpdateObject")
	@ResponseBody
	public JsonResult doUpdateObject(Config entity){
		configService.updateObject(entity);
		return new JsonResult("update ok", 1);
	}
	@RequestMapping("doSaveObject")
	@ResponseBody
	public JsonResult doSaveObject(Config entity){
		configService.saveObject(entity);
		return new JsonResult("save ok", 1);
	}
	@RequestMapping("doDeleteObjects")
	@ResponseBody
	public JsonResult doDeleteObjects(Integer... ids){
	 	configService.deleteObjects(ids);
    	return new JsonResult("delete ok", 1);
	}
	@RequestMapping("doFindPageObjects")
	@ResponseBody
	public JsonResult doFindPageObjects(String name, Integer pageCurrent){
		PageObject<Config> pageObject = configService.findPageObjects(name, pageCurrent);
		return new JsonResult(pageObject);
	}
}