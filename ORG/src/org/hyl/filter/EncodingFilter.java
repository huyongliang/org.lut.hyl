package org.hyl.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 继承自HttpFilter的编码过滤器<br>
 * 本类初始化方法init():<br>
 * 从web.xml配置文件读取初始化参数(context-param)<code>encoding</code><br>
 * 
 * 若web.xml不提供初始化参数encoding的配置，默认使用UTF-8编码
 * 
 * @author HuYongliang <br>
 *         exam-EncodingFilter.xml
 */
public class EncodingFilter extends HttpFilter {
	private String encoding;

	/**
	 * 本类初始化方法init():<br>
	 * 从web.xml配置文件读取初始化参数<code>encoding</code><br>
	 * <blockquote>
	 * 
	 * <pre>
	 * <context-param>
	 * 		<param-name>encoding</param-name>
	 * 		<param-value>UTF-8</param-value>
	 * 	</context-param>
	 * </pre>
	 * 
	 * </blockquote> 若web.xml不提供初始化参数encoding的配置，默认使用UTF-8编码
	 */
	@Override
	public void init() {
		this.encoding = this.getFilterConfig().getServletContext()
				.getInitParameter("encoding");
		// 设置默认编码为UTF-8
		if (this.encoding == null)
			this.encoding = "UTF-8";
	}

	@Override
	public void doFilter(HttpServletRequest req, HttpServletResponse res,
			FilterChain chain) throws IOException, ServletException {

		res.setCharacterEncoding(encoding);
		req.setCharacterEncoding(encoding);

		chain.doFilter(req, res);

	}

}
