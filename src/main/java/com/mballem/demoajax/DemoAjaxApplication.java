package com.mballem.demoajax;

import org.directwebremoting.spring.DwrSpringServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

@ImportResource(locations = "classpath:dwr-spring.xml")
@SpringBootApplication
public class DemoAjaxApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoAjaxApplication.class, args);
	}
	
	@Bean
	public ServletRegistrationBean<DwrSpringServlet> dwrSpringServlet() {
		DwrSpringServlet dwrServlet = new DwrSpringServlet();
		
		ServletRegistrationBean<DwrSpringServlet> registrationBean = new ServletRegistrationBean<>(dwrServlet, "/dwr/*");
		registrationBean.addInitParameter("debug", "true");
		registrationBean.addInitParameter("activeReverseAjaxEnabled", "true");
		
		return registrationBean;
	}
}
