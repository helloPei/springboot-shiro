package com.demo.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

//import com.demo.common.anno.RequestLog;
import com.demo.common.vo.PageObject;
import com.demo.common.exception.ServiceException;
import com.demo.mapper.ConfigMapper;
import com.demo.pojo.Config;
import com.demo.service.ConfigService;

@Service
@Transactional(rollbackFor=Throwable.class,propagation=Propagation.REQUIRED)
public class ConfigServiceImpl implements ConfigService {
	@Autowired
	private ConfigMapper configMapper;
	
	@Override
	public int saveObject(Config entity) {
		//1.合法校验
		if(entity == null)
			throw new IllegalArgumentException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getName()))
			throw new IllegalArgumentException("参数名不能为空");
		if(StringUtils.isEmpty(entity.getValue()))
			throw new IllegalArgumentException("参数值不能为空");
		//2.保存数据
		int rows = 0;
		try{
			rows = configMapper.insertObject(entity);
		}catch(Throwable e){
			e.printStackTrace();
			//给运维人员发短信.
			throw new ServiceException("系统故障,正在修复中");
		}
		if(rows == 0)
			throw new ServiceException("保存失败");
		//3.返回更新的行数
		return rows;
	}
	
	@Override
	public int updateObject(Config entity) {
		//1.合法校验
		if(entity == null)
			throw new IllegalArgumentException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getName()))
			throw new IllegalArgumentException("参数名不能为空");
		if(StringUtils.isEmpty(entity.getValue()))
			throw new IllegalArgumentException("参数值不能为空");
		//2.保存数据
		int rows = 0;
		try{
			rows = configMapper.updateObject(entity);
		}catch(Throwable e){
			e.printStackTrace();
			throw new ServiceException("系统故障,正在修复中");//给运维人员发短信.
		}
		//3.返回更新的行数
		return rows;
	}
	
//	@RequestLog("删除配置信息")
	@Override
	public int deleteObjects(Integer... ids) {
		//1.验证有效性
		if(ids == null || ids.length == 0)
			throw new IllegalArgumentException("必须选中要删除的内容");
		//2.执行删除操作
		int rows = 0;
		try{
			rows = configMapper.deleteObjects(ids);
		}catch(Throwable e){
			e.printStackTrace();
			throw new RuntimeException("系统修复中");//给运维人员发短信
		}
		//3.判定结果并返回
		if(rows == 0)
			throw new ServiceException("对应的记录已经不存在");
		return rows;
	}
	
//	@Transactional(readOnly=true,timeout=30,isolation=Isolation.READ_COMMITTED)//只读事务
//	@RequestLog("分页查询配置信息")
	@Override
	public PageObject<Config> findPageObjects(
		String name, Integer pageCurrent) {
		//1.参数的合法性校验
		if(pageCurrent == null || pageCurrent < 1)
			throw new IllegalArgumentException("当前页码值无效");
		//2.查询总记录数;
		int rowCount = configMapper.getRowCount(name);
		//3.验证总记录数(假如总记录数为0,则抛出异常)
		if(rowCount == 0)
	    throw new ServiceException("系统中没有找到对应数据");
		//4.查询当前页数据(配置信息)
		int pageSize = 5;
		int startIndex = (pageCurrent - 1) * pageSize;
		List<Config> pageRecords = configMapper.findPageObjects(name, startIndex, pageSize);
		//5.封装数据并返回.
		PageObject<Config> pageObject = new PageObject<Config>();
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setPageSize(pageSize);
		pageObject.setRowCount(rowCount);
		pageObject.setRecords(pageRecords);
		return pageObject;
	}
}