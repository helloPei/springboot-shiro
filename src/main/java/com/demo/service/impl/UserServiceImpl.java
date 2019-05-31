package com.demo.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.common.exception.ServiceException;
import com.demo.common.vo.PageObject;
import com.demo.mapper.UserMapper;
import com.demo.mapper.UserRoleMapper;
import com.demo.pojo.User;
import com.demo.pojo.vo.UserDeptResult;
import com.demo.service.UserService;
/**
 * User接口实现类
 * @author Administrator
 *
 */
@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserRoleMapper userRoleMapper;
	
	@Override
	public User findUserByUserName(String username) {
		User user = userMapper.findUserByUserName(username);
		return user;
	}
	@Override
	public int saveObject(User user, Integer[] roleIds) {
		//2.对密码进行加密
		//2.1获取一个盐值(salt):借助UUID获得一个随机数
		String salt = UUID.randomUUID().toString();
		//2.2对密码进行加密(借助Shiro框架API):MD5加密(消息摘要)
		SimpleHash sHash = new SimpleHash(
				"MD5",//algorithmName(算法名称)
				user.getPassword(),//Source(原密码)
				salt);//salt盐值
		String hashPassword = sHash.toHex();//将加密结果转换
		user.setSalt(salt);
		user.setPassword(hashPassword);
		//2.3获取当前登录用户
		User currentUser = (User) SecurityUtils.getSubject().getPrincipal();
		user.setCreatedUser(currentUser.getUsername());
		//2.4设置对象其它属性默认值
		user.setCreatedTime(new Date());
		//2.5保存用户自身信息
		int row = userMapper.insertObject(user);
		if(row != 1)throw new ServiceException("添加用户失败");
		//2.6保存用户与角色的关系数据
		int row1 = userRoleMapper.insertObject(user.getId(), roleIds);
		if(row1 != 1)throw new ServiceException("添加用户与角色关系失败");
		return row;
	}
//	@RequiredLog(operation="更新用户信息")
//	@RequiredClearCache
	@Override
	public int updateObject(User user, Integer[] roleIds) {
		//2.添加修改用户
		//2.1获取当前登录用户
		User modifiedUser = (User) SecurityUtils.getSubject().getPrincipal();
		user.setModifiedUser(modifiedUser.getUsername());
		//2.5保存用户自身信息
		int row = userMapper.updateObject(user);
		if(row != 1)throw new ServiceException("修改用户失败");
		//2.6保存用户与角色的关系数据
		int row1 = userRoleMapper.deleteObjectsByUserId(user.getId());
		if(row1 != 1)throw new ServiceException("删除用户与角色关系失败");
		int row2 = userRoleMapper.insertObject(user.getId(), roleIds);
		if(row2 != 1)throw new ServiceException("添加用户与角色关系失败");
		return row;
	}
	/**
	 *	 通过此注解，告诉系统，执行此方法需要进行授权操作
	 * 1)获取用户权限(登录用户):{"sys:user:update","sys:user:valid"}
	 * 2)获取注解内部定义的权限标识，例如:"sys:user:valid"
	 * 3)判定用户权限中是否包含注解内部定义的权限标识
	 * 
	 * 	系统访问此方法是会获取Subject对象，然后执行如下调用
	 * Subject.isPermitted("sys:user:valid")
	 *	 通过此方法验证用户是否有权限访问
	 */
//	@RequiresPermissions("sys:user:valid")
	@Override
	public int validById(Integer id, Integer valid, String modifiedUser) {
		//2.执行禁用或启动操作
		int rows = userMapper.validById(id, valid, modifiedUser);
		if(rows != 1)throw new ServiceException("修改用户状态失败");
		return rows;
	}
	/**
		事务隔离级别：
		事务隔离级别越高，并发性越差
		通常默认成 @Transactional(isolation=Isolation.READ_COMMITTED)
		READ_COMMITTED 可以提交其它用户未提交的数据
		1)READ_UNCOMMITTED (此级别可能会出现脏读)
		2)READ_COMMITTED(此级别可能会出现不可重复读)
		3)REPEATABLE_READ(此级别可能会出现幻读)
		4)SERIALIZABLE(多事务串行执行)
	 */
//	@Transactional(isolation=Isolation.READ_COMMITTED)
	@Override
	public Map<String, Object> findObjectById(Integer id) {
		//2.查询用户以及关联的部门信息
		UserDeptResult user = userMapper.findObjectById(id);
		if(user == null)throw new ServiceException("查询用户和相关部门结果为空");
		//3.查询用户对应的角色信息
		List<Integer> roleIds = userRoleMapper.findRoleIdsByUserId(id);
		if(roleIds == null || roleIds.size()==0)
			throw new ServiceException("查询用户对应的角色结果为空");
		//4.封装数据
		Map<String,Object> map = new HashMap<>();
		map.put("user", user);
		map.put("roleIds", roleIds);
		return map;
	}
//	@RequiredCache
//	@RequiredLog(operation="query")
	@Override
	public PageObject<UserDeptResult> findPageObjects(String username, Integer pageCurrent) {
		//2.依据条件获取总记录数
		int rowCount = userMapper.getRowCount(username);
		if(rowCount == 0)throw new ServiceException("用户总记录数为0");
		//3.计算startIndex的值
		int pageSize = 8;
		int startIndex = (pageCurrent - 1) * pageSize;
		//4.依据条件获取当前页数据
		List<UserDeptResult> records = 
				userMapper.findPageObjects(username, startIndex, pageSize);
		if(records == null || records.size()==0)throw new ServiceException("用户查询结果为空");
		//5.封装数据
		PageObject<UserDeptResult> pageObject = new PageObject<>();
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setRowCount(rowCount);
		pageObject.setPageSize(pageSize);
		pageObject.setRecords(records);
		return pageObject;
	}
}