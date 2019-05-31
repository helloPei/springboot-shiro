package com.demo.service.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.demo.common.exception.ServiceException;
import com.demo.common.vo.CheckBox;
import com.demo.common.vo.PageObject;

import com.demo.mapper.RoleMapper;
import com.demo.mapper.RoleMenuMapper;
import com.demo.mapper.UserRoleMapper;
import com.demo.pojo.Role;
import com.demo.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	/**角色自身表*/
	@Autowired
	private RoleMapper roleMapper;
	/**角色和菜单关系表*/
	@Autowired
	private RoleMenuMapper roleMenuMapper;
	/**角色和用户关系表*/
	@Autowired
	private UserRoleMapper userRoleMapper;
	
	@Override
	public List<CheckBox> findObjects() {
		return roleMapper.findObjects();
	}
	@Override
	public Map<String, Object> findObjectById(Integer id) {
		//1.合法性验证
		if(id == null || id < 1)
			throw new IllegalArgumentException("id的值无效");
		//2.基于id查询角色自身信息
		Role role = roleMapper.findObjectById(id);
		if(role == null)
			throw new ServiceException("此记录已经不存在");
		//3.基于id查询角色对应的菜单id
		List<Integer> menuIds = roleMenuMapper.findMenuIdsByRoleId(id);
		//3.封装查询结果
		Map<String,Object> map = new HashMap<>();
		map.put("role", role);
		map.put("menuIds", menuIds);
		return map;
	}
	@Override
	public int saveObject(Role role, Integer[] menuIds) {
		//1.合法性验证
		if(role == null)
			throw new IllegalArgumentException("角色信息不能为空");
		if(StringUtils.isEmpty(role.getName()))
			throw new IllegalArgumentException("角色名不能为空");
		if(menuIds==null||menuIds.length==0)
			throw new IllegalArgumentException("必须为角色分配权限");
		//2.保存角色自身信息
		int rows = roleMapper.insertObject(role);
		//3.保存角色和菜单的关系数据
		roleMenuMapper.insertObject(role.getId(), menuIds);
		return rows;
	}
	@Override
	public int updateObject(Role role,Integer[] menuIds) {
		//1.合法性验证
		if(role == null)
			throw new IllegalArgumentException("角色信息不能为空");
		if(StringUtils.isEmpty(role.getName()))
			throw new IllegalArgumentException("角色不能为空");
		if(menuIds == null || menuIds.length == 0)
			throw new IllegalArgumentException("必须为角色分配一个资源");
		//2.保存自身角色数据
		int rows = roleMapper.updateObject(role);
		//3.删除旧的角色和菜单关系
		roleMenuMapper.deleteObjectsByRoleId(role.getId());
		//4.保存角色和菜单新的关系数据
		roleMenuMapper.insertObject(role.getId(), menuIds);
		return rows;
	}
	@Override
	public int deleteObject(Integer id) {
		//1.验证参数的有效性
		if(id == null || id < 1)
			throw new IllegalArgumentException("参数值不正确");
		//2.删除角色自身信息
		int rows = roleMapper.deleteObject(id);
		if(rows == 0)
			throw new ServiceException("记录可能已经不存在");
		//3.删除角色和菜单关系数据
		roleMenuMapper.deleteObjectsByRoleId(id);
		//4.删除用户和角色关系数据
		userRoleMapper.deleteObjectsByRoleId(id);
		return rows;
	}
	@Override
	public PageObject<Role> findPageObjects(String name, Integer pageCurrent) {
		//1.参数有效性验证
		if(pageCurrent == null || pageCurrent < 1)
			throw new IllegalArgumentException("参数值无效");
		//2.基于条件查询总记录数
		int rowCount = roleMapper.getRowCount(name);
		//3.判定总记录数
		if(rowCount == 0)
			throw new ServiceException("记录不存在");
		//4.查询当前页数据
		Integer pageSize=2;
		Integer startIndex=(pageCurrent-1)*pageSize;//起始位置
		List<Role> records=
		roleMapper.findPageObjects(name, startIndex, pageSize);
		//5.封装结果
		PageObject<Role> pageObject=new PageObject<>();
		pageObject.setRowCount(rowCount);
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setPageSize(pageSize);
		pageObject.setRecords(records);
		return pageObject;
	}
}