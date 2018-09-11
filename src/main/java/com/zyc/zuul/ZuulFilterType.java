package com.zyc.zuul;

public enum ZuulFilterType {
	PRE("pre"),
	ROUTING("routing"),
	POST("post"),
	ERROR("error");
	
	private String value;
	
	private ZuulFilterType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
