package com.yifan.bookstore.management;


import com.yifan.bookstore.database.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;

import java.util.List;


@RestController
@SpringBootApplication
public class AdminProcess {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    OrderFormRepository orderFormRepository;

    @RequestMapping("admin/modify_book")
    public String modifyBook(HttpSession httpSession,
                             int book_id, String name,
                             String author, String summary,
                             int inventory, float price,
                             String language)
    {
        try {
            Object user = httpSession.getAttribute("user");
            if(user==null || customerRepository.getCustomerByUsername(user.toString()).getIsAdmin()==0){
                return "Not admin";
            }
            Book book = bookRepository.getBookByBookId(book_id);
            if (book==null)
                book = new Book();
            book.setBookId(book_id);
            book.setInventory(inventory);
            book.setAuthor(author);
            book.setBookName(name);
            book.setLanguage(language);
            book.setPrice((int) (100 * price));
            book.setSummary(summary);
            bookRepository.save(book);
            return "Success";
        }
        catch (Exception e){
            return e.toString();
        }
    }

    @RequestMapping("admin/delete_book")
    public String modifyBook(HttpSession httpSession, int book_id)
    {
        try {
            Object user = httpSession.getAttribute("user");
            if(user==null || customerRepository.getCustomerByUsername(user.toString()).getIsAdmin()==0){
                return "Not admin";
            }
            Book book = bookRepository.getBookByBookId(book_id);
            if (book==null)
                return "Nothing to delete";
            bookRepository.delete(book);
            return "Success";
        }
        catch (Exception e){
            return e.toString();
        }
    }
}
