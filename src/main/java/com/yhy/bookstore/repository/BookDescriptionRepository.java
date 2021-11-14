package com.yhy.bookstore.repository;

import com.yhy.bookstore.entity.BookDescription;
import org.springframework.data.mongodb.repository.MongoRepository;

// @RepositoryRestResource(collectionResourceRel = "book description", path = "book description")
public interface BookDescriptionRepository extends MongoRepository<BookDescription, Integer> {}
