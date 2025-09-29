package ru.itis.mangashop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MangaShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(MangaShopApplication.class, args);
	}

}
