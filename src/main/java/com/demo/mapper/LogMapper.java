package com.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.demo.pojo.Log;
@Mapper
public interface LogMapper {
	int insertObject(Log entity);
    List<Log> findPageObjects(
    		@Param("username")String username,
    		@Param("startIndex")Integer startIndex,
    		@Param("pageSize")Integer pageSize
    );
	int getRowCount(
			@Param("username")String username
	);
}