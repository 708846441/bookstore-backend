package com.yifan.bookstore.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
    @Query("select c from Customer c where c.username=:username")
    Customer getCustomerByUsername(@Param("username") String username);
}