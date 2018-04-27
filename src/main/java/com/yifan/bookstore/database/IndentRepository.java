package com.yifan.bookstore.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IndentRepository extends JpaRepository<Indent, Long>{
@Query("select i from Indent i where i.indentId=:id")
    Indent getIndentByIndentId(@Param("id") int id);
}