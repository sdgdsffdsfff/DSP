package com.aben.iuc.shiro;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.authc.UserFilter;

public class ShiroUserFilter extends UserFilter{
	protected void redirectToLogin(ServletRequest request,
			ServletResponse response) throws IOException {
		String loginUrl = getLoginUrl();
		String service = request.getParameter("service");
		if (service != null) {
			loginUrl = loginUrl + "?service=" + service;
		}
//		将请求处理后重定向
		org.apache.shiro.web.util.WebUtils.issueRedirect(request, response,
				loginUrl);
	}
}
