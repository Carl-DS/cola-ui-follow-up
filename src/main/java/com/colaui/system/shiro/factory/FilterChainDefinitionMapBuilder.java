package com.colaui.system.shiro.factory;

import java.util.LinkedHashMap;

public class FilterChainDefinitionMapBuilder {

	public LinkedHashMap<String, String> buildFilterChainDefinitionMap(){
		LinkedHashMap<String, String> map = new LinkedHashMap<>();

		/*配置哪些页面需要受保护, 以及访问这些页面需要的权限.
		1). anon 可以被匿名访问
		2). authc 必须认证(即登录)后才可能访问的页面.
		3). logout 登出.
		4). roles 角色过滤器*/
		map.put("/frame/account/login.html", "anon");
		map.put("/service/frame/account/logout", "logout");
		map.put("/frame/system/rolecomponent/rolecomponent.html", "authc,roles[admin]");
		map.put("/frame/system/user/user.html", "authc,roles[user]"); // 需要认证且是user权限
		map.put("/frame/main.html", "authc");
		map.put("/frame/system/role/role.html", "user"); //
		//map.put("/**", "authc");

		return map;
	}
	
}
