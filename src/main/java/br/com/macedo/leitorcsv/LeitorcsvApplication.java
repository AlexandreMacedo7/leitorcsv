package br.com.macedo.leitorcsv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class LeitorcsvApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeitorcsvApplication.class, args);
	}

//	@Bean
//	public FileMonitoramento fileMonitoramento(){
//		return new FileMonitoramento();
//	}
}
