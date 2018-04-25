package com.yifan.bookstore.management;


import com.yifan.bookstore.database.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.print.DocFlavor;
import javax.servlet.http.HttpSession;

import java.util.List;
import java.util.Calendar;

@RestController
@SpringBootApplication
public class IndentManagement {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    OrderFormRepository orderFormRepository;

    @Autowired
    IndentItemsRepository indentItemsRepository;

    @Autowired
    IndentRepository indentRepository;

    @RequestMapping("/purchase/create_indent")
    public String createIndent(HttpSession httpSession){
        Object usn = httpSession.getAttribute("user");
        if(usn==null)
            return "Not logged in";
        List<OrderForm> orders = orderFormRepository.getOrderFormsByUsername(usn.toString());
        int len = orders.size();

        Indent new_indent = new Indent();

        //get date
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DATE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        String create_time = year + "/" + month + "/" + date + " " +hour + ":" +minute + ":" + second;
        new_indent.setCreateTime(create_time);

        for (int i=0; i<len; i++){
            OrderForm order = orders.get(i);
            Book book = bookRepository.getBookByBookId(order.getBook_id());
            IndentItems new_item = new IndentItems();
            new_item.setAmount(order.getAmount());
            new_item.setBookId(book.getBookId());
            new_item.setIndentId(new_indent.getIndentId());
            new_item.setUsername(usn.toString());
            new_item.setIndentId(new_indent.getIndentId());
            indentItemsRepository.save(new_item);
        }
        indentRepository.save(new_indent);
        return "Succeed";
    }

}
