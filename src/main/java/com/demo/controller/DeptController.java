package com.demo.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.common.vo.JsonResult;
import com.demo.common.vo.Node;
import com.demo.pojo.Dept;
import com.demo.service.DeptService;

@Controller
@RequestMapping("/dept/")
@RequiresPermissions("dept")
public class DeptController {
	@Autowired
	private DeptService deptService;
	
	/**部门页面*/
	@RequestMapping("doDeptListUI")
	public String doDeptListUI(){
		return "sys/dept_list";
	}
	/**部门编辑页面*/
	@RequestMapping("doDeptEditUI")
	@RequiresPermissions(value = {"addDept", "updateDept"})
	public String doDeptEditUI(){
		return "sys/dept_edit";
	}
	@RequestMapping("doFindObjects")
	@ResponseBody
	public JsonResult doFindObjects(){
		List<Map<String, Object>> findObjects = deptService.findObjects();
		return new JsonResult(findObjects);
	}
	@RequestMapping("doSaveObject")
	@ResponseBody
	public JsonResult doSaveObject(Dept dept){
		deptService.saveObject(dept);
		return new JsonResult("save ok", 1);
	}
	@RequestMapping("doFindZTreeNodes")
	@ResponseBody
	public JsonResult doFindZTreeNodes(){
		List<Node> findZTree = deptService.findZTreeNodes();
		return new JsonResult(findZTree);
	}
	@RequestMapping("doDeleteObject")
	@ResponseBody
	public JsonResult doDeleteObject(Integer id){
		deptService.deleteObject(id);
		return new JsonResult("delete ok", 1);
	}
	@RequestMapping("doUpdateObject")
	@ResponseBody
	public JsonResult doUpdateObject(Dept dept){
		deptService.updateObject(dept);
	    return new JsonResult("update ok", 1);
	}
}