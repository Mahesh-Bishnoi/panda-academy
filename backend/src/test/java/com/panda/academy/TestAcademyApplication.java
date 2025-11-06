package com.panda.academy;

import org.springframework.boot.SpringApplication;

public class TestAcademyApplication {

	public static void main(String[] args) {
		SpringApplication.from(AcademyApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
