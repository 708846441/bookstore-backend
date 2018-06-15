package com.yifan.bookstore.database;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface BookMongoRepository extends MongoRepository<BookMongo, String> {
    @Query("{'bookID':?0}")
    BookMongo findByBookID(int BookID);
}
