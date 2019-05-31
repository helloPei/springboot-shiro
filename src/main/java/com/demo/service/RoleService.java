package com.demo.service;

import java.util.List;
import java.util.Map;

import com.demo.common.vo.CheckBox;
import com.demo.common.vo.PageObject;
import com.demo.pojo.Role;

public interface RoleService {
	
	 /**
	  * 查询所有角色的id,name字段的值，每行记录封装一个checbox对象
	  * @return
	  */
	 List<CheckBox> findObjects();
	
	 /**
	  * 基于角色id查询自身信息以及关联的菜单id
	  * @param id
	  * @return	封装了查询结果的对象
	  */
	 Map<String,Object> findObjectById(Integer id) ;
	
	
	 int updateObject(
			 Role entity,Integer[] menuIds);
	 
	 int saveObject(
			 Role entity,Integer[] menuIds);
	 
	 /**
	  * 基于id删除角色以及关系数据
	  * @param id 角色id
	  * @return	删除的行数
	  */
	 int deleteObject(Integer id);
	 
	 /**
	  * 分页查询角色信息
	  * @param name
	  * @param pageCurrent
	  * @return
	  */
	 PageObject<Role> findPageObjects(
			 String name,Integer pageCurrent);
}
