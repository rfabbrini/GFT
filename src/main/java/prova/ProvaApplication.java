package prova;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import prova.service.FileService;

@Configuration
@SpringBootApplication
public class ProvaApplication {
			
	@Autowired
	FileService fileService;

	public static void main(String[] args) {
		SpringApplication.run(ProvaApplication.class, args);
	}	

	@Bean
	public void init() {		
		fileService.processarTodosArquivosJson();
	}
}
