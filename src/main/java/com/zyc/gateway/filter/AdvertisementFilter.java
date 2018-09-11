package com.zyc.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.zuul.exception.ZuulException;
import com.zyc.zuul.ZuulFilterType;
import com.zyc.zuul.ZycZuulFilter;

public class AdvertisementFilter extends ZycZuulFilter {
	
    private static Logger logger = LoggerFactory.getLogger(AdvertisementFilter.class);

	@Override
	public boolean shouldFilter() {
		// 是否需要执行过滤：true - 会执行run函数；false - 不执行run函数
		return true; 
	}

	@Override
	public String filterType() {
		return ZuulFilterType.POST.getValue();
	}
	
	@Override
	public int zycFilterOrder() {
		return 1;
	}

	@Override
	public Object run() throws ZuulException {
		logger.info(String.format("%s request to %s", this.getRequest().getMethod(), this.getRequest().getRequestURL().toString()));
		this.getContext().getResponse().setHeader("Content-type", "text/html;charset=UTF-8");
		this.adsToHeader();
		return null;
	}
	
	public void adsToHeader() {
		String[] ads = new String[] { "http://127.0.0.1:1400/advertisement/a", "http://127.0.0.1:1400/advertisement/b" };
		for (int i = 0, l = null == ads ? 0 : ads.length; i < l; i++) {
			this.getContext().getResponse().setHeader("ad-url-" + i, ads[i]);
		}
	}
}
