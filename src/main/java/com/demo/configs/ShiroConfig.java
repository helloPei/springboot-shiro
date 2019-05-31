package com.demo.configs;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.demo.service.realm.ShiroUserRealm;

/**
 * Shiro配置类
 * @author Administrator
 *
 */
@Configuration
public class ShiroConfig {
	/**
     * 配置加密匹配，使用MD5的方式(进行1024次加密)
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        //hashedCredentialsMatcher.setHashIterations(1024);
        return hashedCredentialsMatcher;
    }
    /**
     * 自定义Realm（可以多个）
     * 1、需要设置安全管理器：securityManager
     * @return
     */
    @Bean
    public ShiroUserRealm shiroUserRealm() {
    	ShiroUserRealm shiroUserRealm = new ShiroUserRealm();
    	shiroUserRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return shiroUserRealm;
    }
	/**
	 * 安全管理器；Shiro的核心
	 * @return
	 */
	@Bean
	public DefaultWebSecurityManager securityManager(){
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(shiroUserRealm());//设置自定义Realm
//		securityManager.setCacheManager(ehCacheManager());//设置缓存管理器（这个如果执行多次同样的一个对象）
		securityManager.setRememberMeManager(rememberMeManager());//设置“记住我”管理器
		return securityManager;
	}
	/**
     * 配置拦截器
     *  
     * 1. 定义拦截URL权限，优先级从上到下 
     * 1). anon  : 匿名访问，无需登录 
     * 2). authc : 登录后才能访问 
     * 3). logout: 登出
     * 4). roles : 角色过滤器
     * 
     * URL 匹配风格
     * 1). ?：匹配一个字符，如 /admin? 将匹配 /admin1，但不匹配 /admin 或 /admin/；
     * 2). *：匹配零个或多个字符串，如 /admin* 将匹配 /admin 或/admin123，但不匹配 /admin/1；
     * 2). **：匹配路径中的零个或多个路径，如 /admin/** 将匹配 /admin/a 或 /admin/a/b
     * 
     * 2. 配置身份验证成功，失败的跳转路径
     * @return
     */
    @Bean
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(){
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		//设置安全管理器
		shiroFilterFactoryBean.setSecurityManager(securityManager());
		//添加Shiro内置过滤器
		Map<String,String> filterMap = new LinkedHashMap<String,String>();
		filterMap.put("/bower_components/**", "anon");//静态资源匿名访问
		filterMap.put("/build/**", "anon");//静态资源匿名访问
		filterMap.put("/dist/**", "anon");//静态资源匿名访问
		filterMap.put("/plugins/**", "anon");//静态资源匿名访问
		filterMap.put("/doLogin", "anon");//登录匿名访问
		filterMap.put("/doLogout", "logout");//用户退出，只需配置logout即可实现该功能
		filterMap.put("/**", "authc");//其他路径均需要身份认证，一般位于最下面，优先级最低
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
		//修改调整的登录页面
		shiroFilterFactoryBean.setLoginUrl("/doLoginUI");//登录的路径
//		shiroFilterFactoryBean.setSuccessUrl("/doIndexUI");//登录成功后跳转的路径
//		shiroFilterFactoryBean.setUnauthorizedUrl("/403");//验证失败后跳转的路径
		return shiroFilterFactoryBean;
	}
    /**
	 * 配置Shiro生命周期处理器
	 * @return
	 */
	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}
	/**
     * 自动创建代理类，若不添加，Shiro的注解可能不会生效
     * @return
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }
	/**
	 * 开启Shiro的注解（配置授权属性）
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor() {
		AuthorizationAttributeSourceAdvisor authorization = new AuthorizationAttributeSourceAdvisor();
		authorization.setSecurityManager(securityManager());
		return authorization;
	}
	
	/**
     * "记住我"cookie对象
     * @return
     */
    @Bean
	public SimpleCookie rememberMeCookie(){
    	//	这个参数是cookie的名称，对应前端的checkbox的“name = rememberMe”
    	SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
    	simpleCookie.setMaxAge(24*60*60*7);//记住我cookie生效时间7天 （单位秒）
    	return simpleCookie;
    }
    /**
     * "记住我"cookie管理对象
     * 1、需要设置安全管理器：securityManager
     * @return
     */
    @Bean
    public CookieRememberMeManager rememberMeManager(){
       CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
       cookieRememberMeManager.setCookie(rememberMeCookie());
       return cookieRememberMeManager;
    }
    
//    /**
//	 * shiro缓存管理器;
//	 * 1、需要注入对应的其它的实体类中：
//	 * 2、需要设置安全管理器：securityManager
//	 * @return
//	 */
//	@Bean
//	public EhCacheManager ehCacheManager(){
//		EhCacheManager cacheManager = new EhCacheManager();
//		cacheManager.setCacheManagerConfigFile("classpath:config/ehcache-shiro.xml");
//		return cacheManager;
//	}
    
}