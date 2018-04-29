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
public class CartManagement {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    OrderFormRepository orderFormRepository;

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
                            "\", \"Category\" : \"" +book.getCategory() +
                            "\", \"Inventory\" : \"" +book.getInventory() +
                            "\", \"Summary\" : \"" +book.getSummary() +
                            "\", \"Language\" : \""+ book.getLanguage() +"\"}");
            buf.append(',');
        }
        buf.deleteCharAt(buf.length()-1);
        buf.append("]");
        return buf.toString();
    }



    @RequestMapping("/purchase/add_to_cart")
    public String addCart(HttpSession httpSession, int book_id){
        if(httpSession.getAttribute("user")==null)
            return "Not logged in";
        String username = httpSession.getAttribute("user").toString();
        List<OrderForm> orders = orderFormRepository.getOrderFormsByUsernameAndBook_id(username, book_id);
        if(orders.size() == 0){
            // a new one
            OrderForm new_order = new OrderForm();
            new_order.setAmount(1);
            new_order.setUsername(username);
            new_order.setBook_id(book_id);
            orderFormRepository.save(new_order);
            return "Succeed";
        }
        else {
            OrderForm order = orders.get(0);
            order.setAmount(order.getAmount()+1);
            orderFormRepository.save(order);
            return "Succeed";
        }
    }

    @RequestMapping("/purchase/fetch_cart")
    public String fetchCart(HttpSession httpSession){
        if(httpSession.getAttribute("user")==null)
            return "Not logged in";
        String usn = httpSession.getAttribute("user").toString();
        List<OrderForm> orders = orderFormRepository.getOrderFormsByUsername(usn);
        int len = orders.size();
        StringBuffer buf = new StringBuffer("[");

        for (int i=0; i<len; i++){
            OrderForm order = orders.get(i);
            Book book = bookRepository.getBookByBookId(order.getBook_id());
            buf.append(
                    "{\"Book_name\" : \"" + book.getBookName() +
                            "\", \"Order_id\" : \"" +order.getOrderId() +
                            "\", \"Book_id\" : \"" +book.getBookId() +
                            "\", \"Author\" : \"" +book.getAuthor() +
                            "\", \"Price\" : \"" +book.getPrice() * order.getAmount() +
                            "\", \"Amount\" : \""+ order.getAmount() +"\"}");
            buf.append(i==len-1?"]":",");
        }
        return buf.toString();
    }

    @RequestMapping("/purchase/change_amount")
    public String changeAmount(HttpSession httpSession, int order_id, int new_amount){
        if(httpSession.getAttribute("user")==null)
            return "Not logged in";
        String usn = httpSession.getAttribute("user").toString();
        if(new_amount<=0){
            OrderForm order = orderFormRepository.getOrderFormsByOrderId(order_id);
            orderFormRepository.delete(order);
            return "Succeed";
        }
        else{

            OrderForm order = orderFormRepository.getOrderFormsByOrderId(order_id);
            if (new_amount >  bookRepository.getBookByBookId(order.getBook_id()).getInventory())
                return "No enough books";
            order.setAmount(new_amount);
            orderFormRepository.save(order);
            return "Succeed";
        }
    }
}
