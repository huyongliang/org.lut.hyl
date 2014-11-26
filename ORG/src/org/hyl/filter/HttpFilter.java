package org.hyl.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义的Filter半成品，实现接口javax.servlet.Filter<br>
 * 
 * @author HuYongliang
 *
 */
public abstract class HttpFilter implements Filter {

	private FilterConfig filterConfig;

	/****
	 * 空的原生的destroy()方法,实现类可以在需要时直接覆盖
	 */
	@Override
	public void destroy() {
	}

	/***
	 * 原生的doFilter()方法，在方法内部将ServletRequest和ServletResponse转换为<br>
	 * 了HttpServletRequest和HttpServletResponse，并调用了供实现类实现的带有HttpServletRequest和<br>
	 * HttpServletResponse的doFilter()方法<br>
	 * <b>不建议子类直接覆盖该方法，而应该将filter的业务逻辑放在须实现的抽象方法doFilter(HttpServletRequest req,
	 * HttpServletResponse res, FilterChain chain)中</b>
	 * **/
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		this.doFilter(request, response, chain);

	}

	/****
	 * 原生的init()方法，经此封装后<b>不建议</b>子类直接覆盖
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;

		this.init();
	}

	/**
	 * 
	 * Filter业务逻辑代码实现方法
	 * 
	 * @param req
	 *            HttpServletRequest
	 * @param res
	 *            HttpServletResponse
	 * @param chain
	 *            FilterChain
	 */
	public abstract void doFilter(HttpServletRequest req,
			HttpServletResponse res, FilterChain chain) throws IOException,
			ServletException;

	/**
	 * 供子类重写的初始化方法<br>
	 * 可通过getFilterConfig()方法获得FilterConfig的对象引用
	 */
	protected void init() {
	}

	public FilterConfig getFilterConfig() {
		return filterConfig;
	}
}
