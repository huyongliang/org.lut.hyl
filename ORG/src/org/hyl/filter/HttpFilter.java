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
 * �Զ����Filter���Ʒ��ʵ�ֽӿ�javax.servlet.Filter<br>
 * 
 * @author HuYongliang
 *
 */
public abstract class HttpFilter implements Filter {

	private FilterConfig filterConfig;

	/****
	 * �յ�ԭ����destroy()����,ʵ�����������Ҫʱֱ�Ӹ���
	 */
	@Override
	public void destroy() {
	}

	/***
	 * ԭ����doFilter()�������ڷ����ڲ���ServletRequest��ServletResponseת��Ϊ<br>
	 * ��HttpServletRequest��HttpServletResponse���������˹�ʵ����ʵ�ֵĴ���HttpServletRequest��<br>
	 * HttpServletResponse��doFilter()����<br>
	 * <b>����������ֱ�Ӹ��Ǹ÷�������Ӧ�ý�filter��ҵ���߼�������ʵ�ֵĳ��󷽷�doFilter(HttpServletRequest req,
	 * HttpServletResponse res, FilterChain chain)��</b>
	 * **/
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		this.doFilter(request, response, chain);

	}

	/****
	 * ԭ����init()���������˷�װ��<b>������</b>����ֱ�Ӹ���
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;

		this.init();
	}

	/**
	 * 
	 * Filterҵ���߼�����ʵ�ַ���
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
	 * ��������д�ĳ�ʼ������<br>
	 * ��ͨ��getFilterConfig()�������FilterConfig�Ķ�������
	 */
	protected void init() {
	}

	public FilterConfig getFilterConfig() {
		return filterConfig;
	}
}
