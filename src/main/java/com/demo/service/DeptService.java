package com.demo.service;
import java.util.List;
import java.util.Map;
import com.demo.common.vo.Node;
import com.demo.pojo.Dept;

public interface DeptService {
	/**查询全部部门信息*/
	List<Map<String,Object>> findObjects();
	/**查询部门树节点*/
	List<Node> findZTreeNodes();
	/**添加部门*/
	int saveObject(Dept dept);
	/**修改部门*/
	int updateObject(Dept dept);
	/**删除部门*/
	int deleteObject(Integer id);
}