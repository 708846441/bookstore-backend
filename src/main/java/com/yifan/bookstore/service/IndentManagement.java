package com.yifan.bookstore.service;

import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

public interface IndentManagement {

    @RequestMapping("/purchase/create_indent")
    String createIndent(HttpSession httpSession);

    @RequestMapping("/fetch_indents")
    String fetchIndents(HttpSession httpSession, String book_filter,
                        String author_filter, String time_filter);
}
