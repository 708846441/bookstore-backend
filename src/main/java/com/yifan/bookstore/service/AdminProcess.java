package com.yifan.bookstore.service;

import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

public interface AdminProcess {
    @RequestMapping("admin/getBook")
    String adminGetBook(HttpSession httpSession);

    @RequestMapping("admin/modify_book")
    String modifyBook(HttpSession httpSession,
                             int book_id, String name,
                             String author, String summary,
                             int inventory, float price,
                             String language, String category);

    @RequestMapping("admin/delete_book")
    String modifyBook(HttpSession httpSession, int book_id);

    @RequestMapping("admin/get_user")
    String getUser(HttpSession httpSession);

    @RequestMapping("admin/modify_user")
    String modifyUser(HttpSession httpSession, String username, int isValid);

    @RequestMapping("admin/get_indents")
    String modifyUser(HttpSession httpSession);

}
