package com.demo.mapper;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.demo.pojo.Dept;
import com.demo.common.vo.Node;
@Mapper
public interface DeptMapper {
	/**查询所有部门信息*/
	List<Map<String,Object>> findObjects();
	/**查询部门树节点*/
	List<Node> findZTreeNodes();
	/**添加部门*/
	int insertObject(Dept dept);
	/**修改部门*/
	int updateObject(Dept dept);
	/**查询部门是否有用户*/
	int getChildCount(Integer id);
	/**删除部门*/
	int deleteObject(Integer id);
}