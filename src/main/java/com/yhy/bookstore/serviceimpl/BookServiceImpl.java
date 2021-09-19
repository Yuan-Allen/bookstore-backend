package com.yhy.bookstore.serviceimpl;

import com.yhy.bookstore.dao.BookDao;
import com.yhy.bookstore.entity.Book;
import com.yhy.bookstore.service.BookService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookDao bookDao;

    @Override
    public Book findBookById(Integer id){
        return bookDao.findOne(id);
    }

    @Override
    public List<Book> getBooks() {
        return bookDao.getBooks();
    }

    @Override
    public void addOrEditBook(Book book) {bookDao.addOrEditBook(book);}

    @Override
    public void delBook(Integer bookId) {bookDao.delBook(bookId);}

    @Override
    public int getBookPrice(Integer bookId) {return bookDao.getBookPrice(bookId);}

    @Override
    public boolean decBookInventory(JSONArray jsonArray) {return bookDao.decBookInventory(jsonArray);}

}
