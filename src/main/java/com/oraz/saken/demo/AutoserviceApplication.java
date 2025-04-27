package com.oraz.saken.demo;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Set;

@SpringBootApplication
public class AutoserviceApplication {
	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure()
				.ignoreIfMissing()
				.load();

		Set<DotenvEntry> dotenvEntries = dotenv.entries();

		for (DotenvEntry entry : dotenvEntries) {
			System.setProperty(entry.getKey(), entry.getValue());
		}

		SpringApplication.run(AutoserviceApplication.class, args);
	}

}
