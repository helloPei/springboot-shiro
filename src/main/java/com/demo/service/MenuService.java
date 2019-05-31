package com.demo.service;

import java.util.List;
import java.util.Map;

import com.demo.common.vo.Node;
import com.demo.pojo.Menu;

public interface MenuService {
	/**添加菜单*/
	int saveObject(Menu menu);
	/**删除基于id统计菜单子元素的个数*/
	int getChildCount(Integer id);
	/**删除菜单*/
	int deleteObject(Integer id);
	/**修改菜单*/
	int updateObject(Menu menu);
	/**菜单查询*/
	List<Map<String,Object>> findObjects();
	/**菜单树节点数据查询*/
	List<Node> findZtreeMenuNodes();
}
