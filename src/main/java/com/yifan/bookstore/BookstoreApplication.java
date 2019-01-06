package com.yifan.bookstore;

import com.yifan.bookstore.database.Book;
import com.yifan.bookstore.database.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;


import java.util.List;

@RestController
@SpringBootApplication
public class BookstoreApplication extends SpringBootServletInitializer{
	@Autowired
	BookRepository bookRepository;

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
		return builder.sources(BookstoreApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}
}
