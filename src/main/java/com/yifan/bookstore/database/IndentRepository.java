package com.yifan.bookstore.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IndentRepository extends JpaRepository<Indent, Long>{

}