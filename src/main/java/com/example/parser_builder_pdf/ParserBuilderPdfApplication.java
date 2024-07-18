package com.example.parser_builder_pdf;

import com.example.parser_builder_pdf.builder.parser_pdf.ParserBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class ParserBuilderPdfApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ParserBuilderPdfApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			ParserBuilder parserBuilder = new ParserBuilder();
			parserBuilder.builder();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
