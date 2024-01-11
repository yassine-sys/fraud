package com.backend.Filtre;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.etl.login.loginMbean;

public class Etl_Filtre implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String url = req.getRequestURL().toString();
	
		loginMbean authBean = (loginMbean) req.getSession().getAttribute("login.bean");

		boolean letGo = false;
		
		if ((authBean != null) && (authBean.isLoggedIn())) {
			System.out.println("url=" + url);
			letGo = true;
		}

		if (letGo) {
			chain.doFilter(request, response);
		} else {
			System.out.println("letGo false empty loginbean ");
			resp.sendRedirect(req.getContextPath() + "/login.jsf");
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
