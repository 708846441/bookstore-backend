package com.yifan.bookstore;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@org.springframework.stereotype.Controller
public class Controller {

    @RequestMapping(value = "/", method=RequestMethod.GET)
    public String jump()
    {
        return "index.html";
    }

    @RequestMapping(value="/booklist", method=RequestMethod.GET)
    public String errorHandler1(){
        return "index.html";
    }

    @RequestMapping(value="/login", method=RequestMethod.GET)
    public String errorHandler2(){
        return "index.html";
    }

    @RequestMapping(value="/profile", method=RequestMethod.GET)
    public String errorHandler3(){
        return "index.html";
    }

    @RequestMapping(value="/cart", method=RequestMethod.GET)
    public String errorHandler4(){
        return "index.html";
    }

    @RequestMapping(value="/indents", method=RequestMethod.GET)
    public String errorHandler5(){
        return "index.html";
    }

    @RequestMapping(value="/admin_booklist", method=RequestMethod.GET)
    public String errorHandler6(){
        return "index.html";
    }
}
