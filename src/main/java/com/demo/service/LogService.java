package com.demo.service;
import com.demo.common.vo.PageObject;
import com.demo.pojo.Log;

public interface LogService{
    PageObject<Log> findPageObjects(String username, Integer pageCurrent);
}