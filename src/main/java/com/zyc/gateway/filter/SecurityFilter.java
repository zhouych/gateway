package com.zyc.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.zuul.exception.ZuulException;
import com.zyc.zuul.ZuulFilterType;
import com.zyc.zuul.ZycZuulFilter;

public class SecurityFilter extends ZycZuulFilter {
	
    private static Logger logger = LoggerFactory.getLogger(SecurityFilter.class);

	@Override
	public boolean shouldFilter() {
		// 是否需要执行过滤：true - 会执行run函数；false - 不执行run函数
		return true; 
	}

	@Override
	public String filterType() {
		return ZuulFilterType.PRE.getValue();
	}
	
	@Override
	public int zycFilterOrder() {
		return 1;
	}

	@Override
	public Object run() throws ZuulException {
		logger.info(String.format("%s request to %s", this.getRequest().getMethod(), this.getRequest().getRequestURL().toString()));
		
		Object token = this.getRequest().getParameter("token");
		if(token == null) {
		    logger.warn("token is empty.");
		    this.getContext().setSendZuulResponse(false);
		    this.getContext().setResponseStatusCode(401);
		    return null;
		}
		
		logger.info("token is ok.");
		
		return null;
	}
}
