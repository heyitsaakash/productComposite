package com.bbb.composite.product.details;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

import com.bbb.core.application.BaseApplication;

import io.prometheus.client.spring.boot.EnablePrometheusEndpoint;
/**
 * Product details composite microservice main class
 *
 * @author skhur6
 */
@SpringBootApplication
@EnablePrometheusEndpoint
@EnableHystrixDashboard
@EnableCircuitBreaker
//@EnableRedisRepositories(basePackages={"com.bbb.composite.product.details.repositories"})
public class ProductDetailsCompositeApplication extends BaseApplication {

	/**
	 * Main method to run the spring boot application
	 * @param args jvm args to be passed
	 */
	public static void main(String[] args) {
		ProductDetailsCompositeApplication.args = args;	
		ProductDetailsCompositeApplication.applicationBeanName = "productDetailsCompositeApplication";
		ProductDetailsCompositeApplication.mainClass = ProductDetailsCompositeApplication.class;
		ProductDetailsCompositeApplication.builder =  new SpringApplicationBuilder(ProductDetailsCompositeApplication.mainClass);
		ProductDetailsCompositeApplication.configurableApplicationContext =  ProductDetailsCompositeApplication.builder.build().run(args);
	}
}
