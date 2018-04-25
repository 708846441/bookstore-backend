package com.yifan.bookstore.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderFormRepository extends JpaRepository<OrderForm, Long>{
    @Query("select o from OrderForm o where (o.username=:username) and (o.book_id=:book_id)")
    List<OrderForm> getOrderFormsByUsernameAndBook_id(@Param("username") String username, @Param("book_id") int book_id);

    @Query("select o from OrderForm o where (o.username=:username)")
    List<OrderForm> getOrderFormsByUsername(@Param("username") String username);

    @Query("select o from OrderForm o where (o.orderId=:orderId)")
    OrderForm getOrderFormsByOrderId(@Param("orderId") int orderId);
}