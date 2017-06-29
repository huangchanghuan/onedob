package sos.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;

public class MiddleFilter extends StrutsPrepareAndExecuteFilter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filter) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
        String url = req.getRequestURI();
        if (url.contains("/shengshang/ueditor/jsp/")) {
            filter.doFilter(request, response);
        }else{
            super.doFilter(request, response, filter);         
        }
	}
}
