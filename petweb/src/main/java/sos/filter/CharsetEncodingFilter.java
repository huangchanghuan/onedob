package sos.filter;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Ajax请求时的字符编码过滤器
 * @author Administrator
 *
 */
public class CharsetEncodingFilter implements Filter{

	public static final String DEFAULT_AJAX_POST_CONTENT_TYPE = "application/x-www-form-urlencoded";   
    public static final String AJAX_POST_ENCODE = "UTF-8";   //ajax的post编码为utf-8
       
	public void destroy() {
	}
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		String requestedWith = request.getHeader("x-requested-with");   
        String contentType = request.getContentType();   
        if("XMLHttpRequest".equalsIgnoreCase(requestedWith) && null != contentType   
                && contentType.toLowerCase().startsWith(DEFAULT_AJAX_POST_CONTENT_TYPE)){   
            request.setCharacterEncoding(AJAX_POST_ENCODE);   
            request.getParameter("can be anything");
        }   
        chain.doFilter(req,res);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}
}
