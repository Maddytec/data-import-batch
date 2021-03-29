package br.com.maddytec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ReaderftpApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReaderftpApplication.class, args);
	}

}
