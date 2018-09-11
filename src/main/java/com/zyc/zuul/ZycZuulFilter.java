package com.zyc.zuul;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.sun.jersey.api.NotFoundException;

public abstract class ZycZuulFilter extends ZuulFilter {

	private static final List<String> execOrderClassNames = new ArrayList<String>();
	
	private boolean markedOrder = false;
	
	public ZycZuulFilter() {
		super();
		this.initializeExecOrderIfNecessary();
	}
	
	protected boolean isMarkedExecOrder() {
		return markedOrder;
	}
	
	private void setMarkedOrder(boolean markedOrder) {
		this.markedOrder = markedOrder;
	}
	
	public RequestContext getContext() {
		return RequestContext.getCurrentContext();
	}

	public HttpServletRequest getRequest() {
		return this.getContext().getRequest();
	}
	
	public HttpServletResponse getResponse() {
		return this.getContext().getResponse();
	}

	/**
	 * Filter执行顺序
	 * <p>
	 * 后来优先：多个执行顺序的值相等的Filter，最先实例化的Filter会被后来的Filter挤到后面。
	 * </p>
	 * <p>
	 * 建议：尽可能给前后两个Filter的的执行顺序之间留出足够的值，以备将来在两者之间插入新的Filter。
	 * </p>
	 * @return
	 */
	public abstract int zycFilterOrder();
	
	@Override
	public final int filterOrder() {
		String clazzName = this.getClass().getName();
		for (int i = 0, s = execOrderClassNames.size(); i < s; i++) {
			if(clazzName.equals(execOrderClassNames.get(i))) {
				return i;
			}
		}
		
		throw new NotFoundException("The execution order of the current filter could not be found. (class=" + clazzName + ")");
	}

	private void initializeExecOrderIfNecessary() {
		if(this.isMarkedExecOrder()) {
			return;
		}
		
		int order = this.zycFilterOrder();
		if(order < 0) {
			throw new IllegalArgumentException();
		}
		
		int size = null == execOrderClassNames || execOrderClassNames.isEmpty() ? 0 : execOrderClassNames.size();
		if(order >= size) {
			for (int i = size; i < order; i++) {
				execOrderClassNames.add(null);
			}
		}
		
		if(execOrderClassNames.size() > order && null == execOrderClassNames.get(order)) {
			execOrderClassNames.remove(order);
		}
		
		execOrderClassNames.add(order, this.getClass().getName());
		
		this.setMarkedOrder(true);
	}
}
