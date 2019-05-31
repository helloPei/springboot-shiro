package com.demo.controller;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.common.vo.CheckBox;
import com.demo.common.vo.JsonResult;
import com.demo.common.vo.PageObject;
import com.demo.pojo.Role;
import com.demo.service.RoleService;

@Controller
@RequestMapping("/role/")
@RequiresPermissions("role")
public class RoleController {
	 @Autowired
	 private RoleService roleService;
	 
	 /**角色页面*/
	 @RequestMapping("doRoleListUI")
	 public String doRoleListUI(){
		 return "sys/role_list";
	 }
	 /**角色编辑页面*/
	 @RequestMapping("doRoleEditUI")
	 @RequiresPermissions(value = {"addRole", "updateRole"})
	 public String doRoleEditUI(){
		 return "sys/role_edit";
	 }
	 /**查询角色*/
	 @RequestMapping("doFindRoles")
	 @ResponseBody
	 public JsonResult doFindRoles(){
	    List<CheckBox> list = roleService.findObjects();
	    return new JsonResult(list);
	 }
	 /**根据ID查询角色*/
	 @RequestMapping("doFindObjectById")
	 @ResponseBody
	 public JsonResult doFindObjectById(Integer id){
		 Map<String,Object> map = roleService.findObjectById(id);
		 return new JsonResult(map);
	 }
	 /**查询当前页面角色信息*/
	 @RequestMapping("doFindPageObjects")
	 @ResponseBody
	 public JsonResult doFindPageObjects(String name,Integer pageCurrent){
		 PageObject<Role> findPageObjects = roleService.findPageObjects(name, pageCurrent);
		 return new JsonResult(findPageObjects);
	 }
	 /**添加角色*/
	 @RequestMapping("doSaveObject")
	 @ResponseBody
	 public JsonResult doSaveObject(Role role,Integer[] menuIds){
		 roleService.saveObject(role, menuIds);
		 return new JsonResult("save ok", 1);
	 }
	 /**修改角色*/
	 @RequestMapping("doUpdateObject")
	 @ResponseBody
	 public JsonResult doUpdateObject(Role role,Integer[] menuIds){
		 roleService.updateObject(role, menuIds);
		 return new JsonResult("update ok", 1);
	 }
	 /**删除角色*/
	 @RequestMapping("doDeleteObject")
	 @RequiresPermissions("deleteRole")
	 @ResponseBody
	 public JsonResult doDeleteObject(Integer id){
		 roleService.deleteObject(id);
		 return new JsonResult("delete ok", 1);
	 }
}