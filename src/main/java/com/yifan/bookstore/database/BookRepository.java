package com.yifan.bookstore.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long>{
    @Query("SELECT b from Book b")
    List<Book> getAll();
    @Query("select b from Book b where b.bookId=:book_id")
    Book getBookByBookId(@Param("book_id") int book_id);
}
