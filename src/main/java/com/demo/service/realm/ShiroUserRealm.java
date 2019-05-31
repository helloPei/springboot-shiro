package com.demo.service.realm;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.demo.mapper.MenuMapper;
import com.demo.mapper.RoleMenuMapper;
import com.demo.mapper.UserMapper;
import com.demo.mapper.UserRoleMapper;
import com.demo.pojo.User;
/**
 * 自定义Realm
 * @author Administrator
 *
 */
public class ShiroUserRealm extends AuthorizingRealm {//AuthenticationRealm(提供了认证数据的获取方法)
	private static Logger logger = Logger.getLogger(ShiroUserRealm.class);
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserRoleMapper userRoleMapper;
	@Autowired
	private RoleMenuMapper roleMenuMapper;
	@Autowired
	private MenuMapper menuMapper;
	//	自定义缓存map(缓存用户权限信息)，线程安全，高并发，红黑树ConcurrentHashMap
	private Map<String, SimpleAuthorizationInfo> authorMap = new ConcurrentHashMap<>();
	
	/**
	 * Authentication认证处理
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
			throws AuthenticationException {
		logger.info("执行用户认证信息");//获取用户认证信息
		//1.获取用户名
		String username = (String)token.getPrincipal();
		logger.info("用户名：" + username);//用户名
		//2.基于用户名执行查询操作获取用户对象
		User user = userMapper.findUserByUserName(username);
		//3.对用名对象进行判定
		//3.1判定用户是否存在
		if(user == null)
			throw new UnknownAccountException();//用户名不存在
		//3.2判定用户是否被禁用
		if(user.getValid() == 0)
			throw new LockedAccountException();//用户已被禁用
		//4.对用户相关信息进行认证(用户已有身份，密码，盐值等)
		//4.1封装了一个字节数组以及一些编码操作
		ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt());
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(
				user, //principal(用户新身份)
				user.getPassword(), //hashedCredentials(已加密的凭证)
				credentialsSalt, //credentialsSalt(凭证对象的盐值)
				getName());//realmName(real name)
		//5.返回封装好的数据(返回给认证管理器)
		return info;//交给认证管理器
	}
	/**
	 * Authorization授权处理
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//1.获取登录用户信息(用户身份对象)
		User user = (User)principals.getPrimaryPrincipal();
		//从缓存获取用户权限
		if(authorMap.containsKey(user.getUsername()))
			return authorMap.get(user.getUsername());
		
		//2.获取用户具备的权限信息
		//2.1获取用户id获取用户拥有的角色（一个用户多个权限）
		List<Integer> roleIds = userRoleMapper.findRoleIdsByUserId(user.getId());
		if(roleIds == null || roleIds.size() == 0)
			throw new AuthorizationException("您无权访问");
		
		Integer[] array = {};
		//2.2基于角色id获取角色对应的菜单id（一个权限多个菜单）
		List<Integer> menuIds = roleMenuMapper.findMenuIdsByRoleId(roleIds.toArray(array));
		if(menuIds == null || menuIds.size() == 0)
			throw new AuthorizationException("您无权访问");
		
		//2.3基于菜单id获取菜单表中定义的权限标识(权限)
		List<String> permissions =  menuMapper.findPermissions(menuIds.toArray(array));
		//3.封装用户权限信息
		Set<String> permissionSet = new HashSet<>();
		for(String permission : permissions){
			if(!StringUtils.isEmpty(permission)){//去重，去空(null,"")
				permissionSet.add(permission);
			}
		}
		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(permissionSet);
		//4.返回封装结果
		authorMap.put(user.getUsername(), info);
		return info;
	}
}