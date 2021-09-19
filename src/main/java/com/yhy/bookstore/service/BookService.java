package com.yhy.bookstore.service;

import com.yhy.bookstore.entity.Book;
import net.sf.json.JSONArray;

import java.util.List;

public interface BookService {
    Book findBookById(Integer id);

    List<Book> getBooks();

    void addOrEditBook(Book book);

    void delBook(Integer bookId);

    int getBookPrice(Integer bookId);

    boolean decBookInventory(JSONArray jsonArray);
}
