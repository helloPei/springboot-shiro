package com.demo.service;

import com.demo.common.vo.PageObject;
import com.demo.pojo.Config;

public interface ConfigService {
	/**更新配置信息*/
	int updateObject(Config entity);
	/**保存配置信息*/
	int saveObject(Config entity);
	/**删除配置信息*/
	int deleteObjects(Integer... ids);
	/** 查询当前页面总记录数据*/
	PageObject<Config> findPageObjects(String name, Integer pageCurrent);
}