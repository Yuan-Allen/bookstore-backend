package com.yhy.bookstore.dao;

import com.yhy.bookstore.entity.Book;
import net.sf.json.JSONArray;

import java.util.List;

public interface BookDao {
    Book findOne(Integer id);

    List<Book> getBooks();

    void addOrEditBook(Book book);

    void delBook(Integer bookId);

    int getBookPrice(Integer bookId);

    boolean decBookInventory(JSONArray jsonArray);
}
