package com.zyc.gateway;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.zyc.gateway.filter.AdvertisementFilter;
import com.zyc.gateway.filter.SecurityFilter;

@EnableZuulProxy
@SpringCloudApplication
public class GatewayApplication {

	public static void main(String[] args) {
		//SpringApplication.run(GatewayApplication.class, args);
		new SpringApplicationBuilder(GatewayApplication.class).web(WebApplicationType.SERVLET).run(args);
	}
	
	@Bean
	public SecurityFilter securityFilter() {
		return new SecurityFilter();
	}
	
	@Bean
	public AdvertisementFilter advertisementFilter() {
		return new AdvertisementFilter();
	}
}
