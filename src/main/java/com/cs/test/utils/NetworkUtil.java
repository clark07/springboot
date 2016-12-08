package com.cs.test.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 常用获取客户端信息的工具
 * 
 */
public final class NetworkUtil {
	/**
	 * Logger for this class
	 */
	private static Logger logger = Logger.getLogger(NetworkUtil.class);

	/**
	 * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址;
	 */
	public final static String getIpAddress(HttpServletRequest request)
			throws IOException {
		// 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址

		String ip = request.getHeader("X-Forwarded-For");
		logger.debug("getIpAddress(HttpServletRequest) - X-Forwarded-For - String ip="
					+ ip);

		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
				logger.debug("getIpAddress(HttpServletRequest) - Proxy-Client-IP - String ip="
							+ ip);
			}
			if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
				logger.debug("getIpAddress(HttpServletRequest) - WL-Proxy-Client-IP - String ip="
							+ ip);
			}
			if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
				logger.debug("getIpAddress(HttpServletRequest) - HTTP_CLIENT_IP - String ip="
							+ ip);
			}
			if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
				logger.debug("getIpAddress(HttpServletRequest) - HTTP_X_FORWARDED_FOR - String ip="
							+ ip);
			}
			if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
				logger.debug("getIpAddress(HttpServletRequest) - getRemoteAddr - String ip="
							+ ip);
			}
		} else if (ip.length() > 15) {
			String[] ips = ip.split(",");
			for (int index = 0; index < ips.length; index++) {
				String strIp = (String) ips[index];
				if (!("unknown".equalsIgnoreCase(strIp))) {
					ip = strIp;
					break;
				}
			}
		}
		if(StringUtils.isNotBlank(ip) && ip.length()>50) {
			ip = ip.substring(0, 46) + "...";
		}
		return ip;
	}
}