package com.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.demo.pojo.Config;
@Mapper
public interface ConfigMapper {
	/**保存配置信息持久化*/
	int insertObject(Config entity);
	/**更新配置信息*/
	int updateObject(Config entity);
	/**基于id删除配置操作*/
	int deleteObjects(
			@Param("ids")Integer... ids
	);
    /**基于条件查询当前页数据
     * @param name 查询时输出的参数名
     * @param startIndex 当前起始位置
     * @param pageSize 每页最多显示的记录数,页面大小.
     * @return
     */
	List<Config> findPageObjects(
			@Param("name")String name,
			@Param("startIndex")Integer startIndex,
			@Param("pageSize")Integer pageSize
	);
	/** 基于条件查询总记录数
	 * @param name 查询条件
	 * @return
	 */
	int getRowCount(
			@Param("name")String name
	);
}