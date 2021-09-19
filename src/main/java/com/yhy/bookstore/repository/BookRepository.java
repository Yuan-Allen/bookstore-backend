package com.yhy.bookstore.repository;

import com.yhy.bookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.Entity;
import java.util.List;


public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query("select b from Book b")
    List<Book> getBooks();

    @Query("select price from Book where bookId =:bookId")
    int getBookPriceById(@Param("bookId")Integer bookId);
}
