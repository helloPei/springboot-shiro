package com.demo.controller;

import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.common.vo.JsonResult;
import com.demo.pojo.User;
import com.demo.pojo.vo.UserDeptResult;
import com.demo.service.UserService;
import com.demo.common.vo.PageObject;
/**
 * User控制层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/user/")
@RequiresPermissions("user")
public class UserController {
	@Autowired
	private UserService userService;
	/**用户管理页面*/
	@RequestMapping("doUserListUI")
	public String doUserListUI() {
		return "sys/user_list";
	}
	/**用户编辑页面*/
	@RequestMapping("doUserEditUI")
	@RequiresPermissions(value={"addUser","updateUser"})
	public String doUserEditUI() {
		return "sys/user_edit";
	}
	@RequestMapping("doSaveObject")
	@ResponseBody
	public JsonResult doSaveObject(User user, Integer[] roleIds){
		//1.验证数据合法性
		if(user == null)return new JsonResult("保存对象不能为空");
		if(StringUtils.isEmpty(user.getUsername()))
			return new JsonResult("用户名不能为空");
		if(StringUtils.isEmpty(user.getPassword()))
			return new JsonResult("密码不能为空");
		if(user.getDeptId() == null)
			return new JsonResult("必须为用户指定部门");
		if(roleIds == null || roleIds.length == 0)
			return new JsonResult("需要为用户分配角色");
		User userData = userService.findUserByUserName(user.getUsername());
		if(!StringUtils.isEmpty(userData)) {
			if((userData.getUsername()).equals(user.getUsername()))
				return new JsonResult("用户名已存在");
		}
		int rows = userService.saveObject(user, roleIds);
		return new JsonResult("Save OK!", rows);
	}
	@RequestMapping("doUpdateObject")
	@ResponseBody
	public JsonResult doUpdateObject(User user, Integer[] roleIds){
		//1.验证数据合法性
		if(user == null)return new JsonResult("保存对象不能为空");
		if(StringUtils.isEmpty(user.getUsername()))
			return new JsonResult("用户名不能为空");
		if(roleIds == null || roleIds.length == 0)
			return new JsonResult("需要为用户分配角色");
		
		int rows = userService.updateObject(user, roleIds);
		return new JsonResult("Update OK!", rows);
	}
	@RequestMapping("doValidById")
	@ResponseBody
	public JsonResult doValidById(Integer id,Integer valid){
		//1.有效性验证
		if(id == null || id < 1)return new JsonResult("ID值无效：" + id);
		if(valid == null || (valid != 1 && valid != 0))
			return new JsonResult("用户状态值不合法：" + valid);
		//1.1获取当前登录用户
		User user = (User) SecurityUtils.getSubject().getPrincipal();//session
		if(StringUtils.isEmpty(user.getUsername()))
			return new JsonResult("当前用户不能为空");
		
		int rows = userService.validById(id, valid, user.getUsername());
		return new JsonResult("Update OK！", rows);
	}
	@RequestMapping("doFindObjectById")
	@ResponseBody
	public JsonResult doFindObjectById(Integer id){
		//1.参数有效性验证
		if(id == null || id < 1)return new JsonResult("ID值不合法");
		
		Map<String, Object> map = userService.findObjectById(id);
		return new JsonResult(map);
	}
	@RequestMapping("doFindPageObjects")
	@ResponseBody
	public JsonResult doFindPageObjects(String username, Integer pageCurrent){
		//1.数据合法性验证
		if(pageCurrent == null || pageCurrent <= 0)
			return new JsonResult("参数不合法");
		
		PageObject<UserDeptResult> pageObject = 
				userService.findPageObjects(username, pageCurrent);
		return new JsonResult(pageObject);
	}
}