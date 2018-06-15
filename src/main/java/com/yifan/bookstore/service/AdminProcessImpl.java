package com.yifan.bookstore.service;


import com.yifan.bookstore.database.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;

import java.util.List;


@RestController
@SpringBootApplication
public class AdminProcessImpl implements AdminProcess {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    OrderFormRepository orderFormRepository;

    @Autowired
    IndentRepository indentRepository;

    @Autowired
    IndentItemsRepository indentItemsRepository;


    public String adminGetBook(HttpSession httpSession){
        Object user = httpSession.getAttribute("user");
        if(user==null || customerRepository.getCustomerByUsername(user.toString()).getIsAdmin()==0){
            return "Not admin";
        }
        List<Book> bookList = bookRepository.getAll();
        StringBuffer buf = new StringBuffer("[");
        for (Book book:bookList){
            buf.append(
                    "{\"ID\" : \"" + book.getBookId() +
                            "\", \"Name\" : \"" + book.getBookName() +
                            "\", \"Author\" : \"" +book.getAuthor() +
                            "\", \"Price\" : \"" +book.getPrice() +
                            "\", \"Category\" : \"" +book.getCategory() +
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


    public String modifyBook(HttpSession httpSession,
                             int book_id, String name,
                             String author, String summary,
                             int inventory, float price,
                             String language, String category)
    {
        try {
            Object user = httpSession.getAttribute("user");
            if(user==null || customerRepository.getCustomerByUsername(user.toString()).getIsAdmin()==0){
                return "Not admin";
            }
            if(inventory<0 || price<=0){
                return "Invalid inventory or price";
            }
            Book book = bookRepository.getBookByBookId(book_id);
            if (book==null)
                book = new Book();
            book.setBookId(book_id);
            book.setInventory(inventory);
            book.setAuthor(author);
            book.setBookName(name);
            book.setCategory(category);
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


    public String getUser(HttpSession httpSession){
        Object user = httpSession.getAttribute("user");
        if(user==null || customerRepository.getCustomerByUsername(user.toString()).getIsAdmin()==0){
            return "Not admin";
        }

        List<Customer> customers = customerRepository.getAll();
        StringBuffer buf = new StringBuffer("[");
        for (Customer customer:customers){
            buf.append(
                    "{\"Username\" : \"" + customer.getUsername() +
                            "\", \"Name\" : \"" + customer.getName() +
                            "\", \"Email\" : \"" +customer.getEmail() +
                            "\", \"Phone\" : \"" +customer.getPhone() +
                            "\", \"isValid\" : \"" +customer.getIs_valid() +
                            "\", \"Address\" : \""+ customer.getAddress() +"\"}");
            buf.append(',');
        }
        buf.deleteCharAt(buf.length()-1);
        buf.append("]");
        return buf.toString();
    }


    public String modifyUser(HttpSession httpSession,
                             String username, int isValid)
    {
        try {
            Object user = httpSession.getAttribute("user");
            if(user==null || customerRepository.getCustomerByUsername(user.toString()).getIsAdmin()==0){
                return "Not admin";
            }
            Customer customer = customerRepository.getCustomerByUsername(username);
            if (customer==null)
                return "No such user found.";
            customer.setIs_valid(isValid);
            customerRepository.save(customer);
            return "Success";
        }
        catch (Exception e){
            return e.toString();
        }
    }



    public String modifyUser(HttpSession httpSession)
    {
        try {
            Object user = httpSession.getAttribute("user");
            if(user==null || customerRepository.getCustomerByUsername(user.toString()).getIsAdmin()==0){
                return "Not admin";
            }
            List<IndentItems> indentItemsList = indentItemsRepository.getAll();
            StringBuffer buf = new StringBuffer("[");
            for (IndentItems item:indentItemsList){
                Book book = bookRepository.getBookByBookId(item.getBookId());
                buf.append(
                        "{\"OrderID\" : \"" + item.getIndentId() +
                                "\", \"Name\" : \"" + book.getBookName() +
                                "\", \"Username\" : \"" +item.getUsername() +
                                "\", \"Category\" : \"" +book.getCategory() +
                                "\", \"Amount\" : \"" + item.getAmount() +
                                "\", \"Author\" : \"" + book.getAuthor() +
                                "\", \"Price\" : \"" + item.getAmount() * book.getPrice() +
                                "\", \"Time\" : \""+ indentRepository.getIndentByIndentId(item.getIndentId()).getCreateTime() +"\"}");
                buf.append(',');
            }
            buf.deleteCharAt(buf.length()-1);
            buf.append("]");
            return buf.toString();
        }
        catch (Exception e){
            return e.toString();
        }
    }

}
