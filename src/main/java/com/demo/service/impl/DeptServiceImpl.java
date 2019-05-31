package com.demo.service.impl;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.demo.common.exception.ServiceException;
import com.demo.common.vo.Node;
import com.demo.mapper.DeptMapper;
import com.demo.pojo.Dept;
import com.demo.service.DeptService;

@Service
public class DeptServiceImpl implements DeptService {
	@Autowired
	private DeptMapper deptMapper;
	//@Autowired
	//private UserMapper userMapper;
	@Override
	public List<Map<String, Object>> findObjects() {
		return deptMapper.findObjects();
	}
	@Override
	public List<Node> findZTreeNodes() {
		return deptMapper.findZTreeNodes();
	}
	@Override
	public int saveObject(Dept dept) {
		if(dept == null)
			throw new IllegalArgumentException("保存对象不能为空");
		if(StringUtils.isEmpty(dept.getName()))
			throw new IllegalArgumentException("部门不能为空");
		int rows = 0;
		try{
			rows = deptMapper.insertObject(dept);
		}catch(Exception e){
			e.printStackTrace();
			throw new ServiceException("添加失败");
		}
		return rows;
	}
	@Override
	public int updateObject(Dept dept) {
		if(dept == null)
			throw new IllegalArgumentException("保存对象不能为空");
		if(StringUtils.isEmpty(dept.getName()))
			throw new IllegalArgumentException("部门不能为空");
		int rows = 0;
		try{
			rows = deptMapper.updateObject(dept);
		}catch(Exception e){
			e.printStackTrace();
			throw new ServiceException("更新失败");
		}
		return rows;
	}
	@Override
	public int deleteObject(Integer id) {
		if(id==null||id<=0)
			throw new ServiceException("数据不合法,id="+id);
		int childCount = deptMapper.getChildCount(id);
		//判定此id对应的菜单是否有子元素
		if(childCount > 0)
			throw new ServiceException("此元素有子元素，不允许删除");
		//判定此部门是否有用户
		//int userCount = userMapper.getUserCountByDeptId(id);
		//判定此部门是否已经被用户使用,假如有则拒绝删除
		//if(userCount > 0)
		//	throw new ServiceException("此部门有员工，不允许对部门进行删除");
		//执行删除操作
		int rows = deptMapper.deleteObject(id);
		if(rows==0)
			throw new ServiceException("此信息可能已经不存在");
		return rows;
	}
}