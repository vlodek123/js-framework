package cz.vt.jsframeworks;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import cz.vt.jsframeworks.entity.Framework;
import cz.vt.jsframeworks.repository.FrameworkRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@SpringBootApplication
public class JsFrameworksApplication implements CommandLineRunner{

	FrameworkRepository frameworkRepository;


	public static void main(String[] args) {
		SpringApplication.run(JsFrameworksApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Framework[] frameworks = new Framework[] {
			new Framework("Angular", "4", 10L, LocalDate.parse("2025-07-31")),
			new Framework("React", "18.2.0", 8L, LocalDate.parse("2024-07-31")),
			new Framework("React", "17.0.1", 7L, LocalDate.parse("2024-07-31")),
			new Framework("React", "18.0.0", 6L, LocalDate.parse("2024-07-31")),
			new Framework("Meteor", "2.7.3", 5L, LocalDate.parse("2024-07-31")),
			new Framework("Mithril", "2.1.0", 4L, LocalDate.parse("2024-07-31"))
		};	
		
		for (int i = 0; i < frameworks.length; i++) {
			frameworkRepository.save(frameworks[i]);
		}
	}

}
