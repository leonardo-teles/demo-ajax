package com.mballem.demoajax;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mballem.demoajax.domain.SocialMetaTag;
import com.mballem.demoajax.service.SocialMetaTagService;

@SpringBootApplication
public class DemoAjaxApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoAjaxApplication.class, args);
	}

	@Autowired
	SocialMetaTagService service;
	
	@Override
	public void run(String... args) throws Exception {
		SocialMetaTag og = service.getOpenGraphByUrl("https://www.pichau.com.br/headset-logitech-g933-artemis-spectrum-7-1-surround-usb-wireless-981-000598");
		System.out.println(og.toString());
		
		
		SocialMetaTag twitter = service.getTwitterCardByUrl("https://www.pichau.com.br/headset-logitech-g933-artemis-spectrum-7-1-surround-usb-wireless-981-000598");
		System.out.println(twitter.toString());
	}

}
