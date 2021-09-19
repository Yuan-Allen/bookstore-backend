package com.yhy.bookstore.daoimpl;

import com.yhy.bookstore.constant.Constant;
import com.yhy.bookstore.dao.BookDao;
import com.yhy.bookstore.entity.Book;
import com.yhy.bookstore.repository.BookRepository;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookDaoImpl implements BookDao {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book findOne(Integer id){
        return bookRepository.getOne(id);
    }


    @Override
    public List<Book> getBooks() {
        List<Book> books = bookRepository.getBooks();
        List<Book> result = new ArrayList<Book>();
        for (int i=0;i<books.size();++i) {
            if(!books.get(i).getIsDeleted())
                result.add(books.get(i));
        }
        return result;
    }

    @Override
    public void addOrEditBook(Book book) {bookRepository.saveAndFlush(book);}

    @Override
    public void delBook(Integer bookId)
    {
//        if(bookRepository.existsById(bookId))
//            bookRepository.deleteById(bookId);
        Book book = bookRepository.getOne(bookId);
        book.setIsDeleted(true);
        bookRepository.saveAndFlush(book);
    }

    @Override
    public int getBookPrice(Integer bookId)
    {
        if(bookRepository.existsById(bookId))
            return bookRepository.getBookPriceById(bookId);
        else return 0;
    }

    @Override
    public boolean decBookInventory(JSONArray jsonArray)
    {
        //先检查会不会有某本书库存不够
        Integer arraySize = jsonArray.size();
        for (int i=0;i<arraySize;++i) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Integer bookId = Integer.parseInt(jsonObject.get(Constant.BOOK_ID).toString());
            Integer num = Integer.parseInt(jsonObject.get(Constant.BOOK_NUMBER).toString());
            Book book = bookRepository.getOne(bookId);
            if(book.getInventory()<num||book.getIsDeleted()) {
                return false;
            }
        }
        //若订单里所有书库存都够，才修改库存，并且返回true
        for (int i=0;i<arraySize;++i) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Integer bookId = Integer.parseInt(jsonObject.get(Constant.BOOK_ID).toString());
            Integer num = Integer.parseInt(jsonObject.get(Constant.BOOK_NUMBER).toString());
            Book book = bookRepository.getOne(bookId);
            book.setInventory(book.getInventory()-num);
            bookRepository.saveAndFlush(book);
        }
        return true;
    }
}
