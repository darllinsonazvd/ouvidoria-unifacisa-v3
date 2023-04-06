package com.unifacisa.ouvidoria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.unifacisa.ouvidoria.application.AppComponent;

@SpringBootApplication
public class OuvidoriaApplication implements CommandLineRunner {
	@Autowired
	AppComponent appComponent;
	
	public static void main(String[] args) {
		SpringApplication.run(OuvidoriaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		appComponent.start();
	}
}
