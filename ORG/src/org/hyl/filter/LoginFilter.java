package org.hyl.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * 登陆过滤器
 * 
 * <pre>
 * web.xml<b>必须</b>配置的参数context-param：
 * 1.userSessionKey----用户登录后的session标记，如USERSESSIONKEY
 * 2.redirectPage----拦截后需重定向到的页面，如/login/Login.jsp
 * 3.unCheckedUrls----不需要被拦截的页面，如/login/A.jsp,/login/List.jsp
 * </pre>
 * 
 * @author HuYongliang<br>
 *         exam-LoginFilter.xml
 */
public class LoginFilter extends HttpFilter {
	// 1.从web.xml文件中获取sessionKey，redirectUrl，uncheckedUrls
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
		// 1.获取URL
		String servletPath = req.getServletPath();

		// 2.servletPath不需要拦截 方法结束
		if (this.unCheckedUrls.indexOf(servletPath) != -1) {
			chain.doFilter(req, res);
			return;
		}

		// 3.获取sessionKey 不存在则重定向到redirectPage
		Object user = req.getSession().getAttribute(this.sessionKey);
		if (user == null) {
			System.out.println(req.getContextPath() + this.redirectPageUrl);
			res.sendRedirect(req.getContextPath() + this.redirectPageUrl);
			return;
		}
		// 4.sessionKey存在，放行。
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
