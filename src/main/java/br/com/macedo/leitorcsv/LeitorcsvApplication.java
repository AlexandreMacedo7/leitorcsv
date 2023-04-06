package br.com.macedo.leitorcsv;

import br.com.macedo.leitorcsv.utility.FileMonitoramento;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LeitorcsvApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeitorcsvApplication.class, args);
	}

	@Bean
	public FileMonitoramento fileMonitoramento(){
		return new FileMonitoramento();
	}
}
