package com.demo.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.common.vo.JsonResult;
import com.demo.common.vo.Node;
import com.demo.pojo.Menu;
import com.demo.service.MenuService;

@Controller
@RequestMapping("/menu/")
@RequiresPermissions("menu")
public class MenuController {
	@Autowired
	private MenuService menuService;
	
	/**菜单页面*/
	@RequestMapping("doMenuListUI")
	public String doMenuListUI(){
	  return "sys/menu_list";
	}
	/**菜单编辑页面*/
    @RequestMapping("doMenuEditUI")
    @RequiresPermissions(value = {"addMenu", "updateMenu"})
	public String doMenuEditUI(){
		 return "sys/menu_edit";
	}
    /**添加菜单*/
    @RequestMapping("doSaveObject")
    @ResponseBody
    public JsonResult doSaveObject(Menu menu){
    	//1.对参数进行有效性校验
    	if(menu == null)return new JsonResult("保存对象不能为空");
    	if(StringUtils.isEmpty(menu.getName()))
    		return new JsonResult("菜单名不允许为空");
    	int rows = menuService.saveObject(menu);
    	return new JsonResult("Save OK！", rows);
    }
    /**删除菜单*/
    @RequestMapping("doDeleteObject")
    @RequiresPermissions("deleteMenu")
    @ResponseBody
    public JsonResult doDeleteObject(Integer id){
    	//1.验证参数有效性
    	if(id == null || id < 1)return new JsonResult("ID值不合法");
    	//2.基于id统计菜单子元素的个数
    	int count = menuService.getChildCount(id);
    	//3.判定是否有子元素,有则抛出异常,没有则删除
    	if(count > 0)return new JsonResult("请先删除子菜单");
    	int rows = menuService.deleteObject(id);
    	return new JsonResult("Delete OK！", rows);
    }
    /**修改菜单*/
    @RequestMapping("doUpdateObject")
    @ResponseBody
    public JsonResult doUpdateObject(Menu menu){
    	//1.对参数进行有效性校验
    	if(menu == null)return new JsonResult("保存对象不能为空");
    	if(StringUtils.isEmpty(menu.getName()))
    		return new JsonResult("菜单名不允许为空");
    	int rows = menuService.updateObject(menu);
    	return new JsonResult("Update OK！", rows);
    }
    /**菜单查询*/
    @RequestMapping("doFindObjects")
    @ResponseBody
    public JsonResult doFindObjects(){
    	List<Map<String, Object>> list = menuService.findObjects();
    	return new JsonResult(list);
    }
    /**菜单树节点数据查询*/
    @RequestMapping("doFindZtreeMenuNodes")
    @ResponseBody
	public JsonResult doFindZtreeMenuNodes(){
    	List<Node> list = menuService.findZtreeMenuNodes();
		return new JsonResult(list);
	}
}