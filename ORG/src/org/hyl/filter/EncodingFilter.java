package org.hyl.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * �̳���HttpFilter�ı��������<br>
 * �����ʼ������init():<br>
 * ��web.xml�����ļ���ȡ��ʼ������(context-param)<code>encoding</code><br>
 * 
 * ��web.xml���ṩ��ʼ������encoding�����ã�Ĭ��ʹ��UTF-8����
 * 
 * @author HuYongliang <br>
 *         exam-EncodingFilter.xml
 */
public class EncodingFilter extends HttpFilter {
	private String encoding;

	/**
	 * �����ʼ������init():<br>
	 * ��web.xml�����ļ���ȡ��ʼ������<code>encoding</code><br>
	 * <blockquote>
	 * 
	 * <pre>
	 * <context-param>
	 * 		<param-name>encoding</param-name>
	 * 		<param-value>UTF-8</param-value>
	 * 	</context-param>
	 * </pre>
	 * 
	 * </blockquote> ��web.xml���ṩ��ʼ������encoding�����ã�Ĭ��ʹ��UTF-8����
	 */
	@Override
	public void init() {
		this.encoding = this.getFilterConfig().getServletContext()
				.getInitParameter("encoding");
		// ����Ĭ�ϱ���ΪUTF-8
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
