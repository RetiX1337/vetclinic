package com.bukup.vetclinic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class})
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class VetclinicApplication
{

	public static void main(final String[] args)
	{
		SpringApplication.run(VetclinicApplication.class, args);
	}

}
