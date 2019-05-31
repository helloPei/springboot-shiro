package com.demo.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.common.vo.PageObject;
import com.demo.common.exception.ServiceException;
import com.demo.mapper.LogMapper;
import com.demo.pojo.Log;
import com.demo.service.LogService;

@Service("sysLogService")
public class LogServiceImpl implements LogService {
	@Autowired
	private LogMapper logMapper;
	@Override
	public PageObject<Log> findPageObjects(String username, Integer pageCurrent) {
        //1.验证参数合法性
		if(pageCurrent == null||pageCurrent<1)
			throw new ServiceException("参数不合法");
		//2.查询总记录数
		int rowCount = logMapper.getRowCount(username);
		if(rowCount == 0)
			throw new ServiceException("没有记录");
	    //2.查询当前页记录
		//2.1定义页面大小(每页最多现实多少条记录)
		int pageSize = 5;
		//2.2计算当前页位置
		int startIndex = (pageCurrent - 1) * pageSize;
		//2.3查询当前数据
		List<Log> list = logMapper.findPageObjects(username, startIndex, pageSize);
		//3.封装数据
		PageObject<Log> pageObject = new PageObject<>();
		pageObject.setRecords(list);
		pageObject.setRowCount(rowCount);
		pageObject.setPageSize(pageSize);
		pageObject.setPageCurrent(pageCurrent);
		return pageObject;
	}
}