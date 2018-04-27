package com.yifan.bookstore.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IndentItemsRepository extends JpaRepository<IndentItems, Long>{
    @Query("select item from IndentItems item where item.username=:username")
    List<IndentItems> getByUsername(@Param("username") String username);
}