package com.sh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan(value="com.sh.dao")
@SpringBootApplication
public class BootShiroStartApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootShiroStartApplication.class, args);
	}

}
