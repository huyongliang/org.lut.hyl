package org.hyl.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * ��½������
 * 
 * <pre>
 * web.xml<b>����</b>���õĲ���context-param��
 * 1.userSessionKey----�û���¼���session��ǣ���USERSESSIONKEY
 * 2.redirectPage----���غ����ض��򵽵�ҳ�棬��/login/Login.jsp
 * 3.unCheckedUrls----����Ҫ�����ص�ҳ�棬��/login/A.jsp,/login/List.jsp
 * </pre>
 * 
 * @author HuYongliang<br>
 *         exam-LoginFilter.xml
 */
public class LoginFilter extends HttpFilter {
	// 1.��web.xml�ļ��л�ȡsessionKey��redirectUrl��uncheckedUrls
	private String sessionKey;
	private String redirectPageUrl;
	private String unCheckedUrls;

	@Override
	protected void init() {
		ServletContext context = this.getFilterConfig().getServletContext();
		this.sessionKey = context.getInitParameter("userSessionKey");
		this.redirectPageUrl = context.getInitParameter("redirectPage");
		this.unCheckedUrls = context.getInitParameter("unCheckedUrls");
	}

	@Override
	public void doFilter(HttpServletRequest req, HttpServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		// 1.��ȡURL
		String servletPath = req.getServletPath();

		// 2.servletPath����Ҫ���� ��������
		if (this.unCheckedUrls.indexOf(servletPath) != -1) {
			chain.doFilter(req, res);
			return;
		}

		// 3.��ȡsessionKey ���������ض���redirectPage
		Object user = req.getSession().getAttribute(this.sessionKey);
		if (user == null) {
			System.out.println(req.getContextPath() + this.redirectPageUrl);
			res.sendRedirect(req.getContextPath() + this.redirectPageUrl);
			return;
		}
		// 4.sessionKey���ڣ����С�
		chain.doFilter(req, res);
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public String getRedirectPageUrl() {
		return redirectPageUrl;
	}

	public String getUnCheckedUrls() {
		return unCheckedUrls;
	}

}
