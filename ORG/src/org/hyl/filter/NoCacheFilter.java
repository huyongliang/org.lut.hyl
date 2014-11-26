package org.hyl.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * ½ûÖ¹ä¯ÀÀÆ÷»º´æÒ³ÃæµÄ¹ýÂËÆ÷
 * 
 * @author HuYongliang<br>
 *         exam-NoCacheFilter.xml
 *
 */
public class NoCacheFilter extends HttpFilter {

	@Override
	public void doFilter(HttpServletRequest req, HttpServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		res.setDateHeader("Expires", -1);
		res.setHeader("Cache-Control", "no-cache");
		res.setHeader("Pragma", "no-cache");
		chain.doFilter(req, res);
	}

}
