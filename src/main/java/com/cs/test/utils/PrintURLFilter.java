package com.cs.test.utils;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 打印访问路径的过滤器 在项目开发中用于帮助调试 在web.xml配置文件
 * @author libo
 *
 */
public class PrintURLFilter implements Filter {
	
	Logger logger = Logger.getLogger(PrintURLFilter.class);

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response,
						 FilterChain chain) throws IOException, ServletException {
		
		String urlstr = getFullURL((HttpServletRequest)request);
		if(urlstr.indexOf("action")>=1 || urlstr.indexOf(".do")>=1 ){
			//System.out.println(urlstr);
			//logger.info("request action:"+request.getParameter("action"));
			//logger.info(urlstr);
		}
		logger.info(urlstr);
		chain.doFilter(request, response);
	}

	public String getFullURL(HttpServletRequest request) {

		StringBuffer urlstrbuf = request.getRequestURL();

		if (request.getQueryString() != null) {
			urlstrbuf.append("?");
			urlstrbuf.append(request.getQueryString());
		}

		return urlstrbuf.toString();
	}

	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
