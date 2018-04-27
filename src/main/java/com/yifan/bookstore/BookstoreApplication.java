package com.yifan.bookstore;

import com.yifan.bookstore.database.Book;
import com.yifan.bookstore.database.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@SpringBootApplication
public class BookstoreApplication {
	@Autowired
	BookRepository bookRepository;

    @RequestMapping("/getBook")
   	public String getBooklist(){
		List<Book> bookList = bookRepository.getAll();
		StringBuffer buf = new StringBuffer("[");
		for (Book book:bookList){
			buf.append(
					"{\"ID\" : \"" + book.getBookId() +
							"\", \"Name\" : \"" + book.getBookName() +
							"\", \"Author\" : \"" +book.getAuthor() +
							"\", \"Price\" : \"" +book.getPrice() +
							"\", \"Sales\" : \"" +book.getSales() +
							"\", \"Inventory\" : \"" +book.getInventory() +
							"\", \"Summary\" : \"" +book.getSummary() +
							"\", \"Language\" : \""+ book.getLanguage() +"\"}");
			buf.append(',');
		}
		buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}


	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}
}
