package com.yhy.bookstore.daoimpl;

import com.yhy.bookstore.constant.Constant;
import com.yhy.bookstore.dao.BookDao;
import com.yhy.bookstore.entity.Book;
import com.yhy.bookstore.entity.BookDescription;
import com.yhy.bookstore.entity.Label;
import com.yhy.bookstore.repository.BookDescriptionRepository;
import com.yhy.bookstore.repository.BookRepository;
import com.yhy.bookstore.repository.LableRepository;
import com.yhy.bookstore.utils.redisutils.RedisUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class BookDaoImpl implements BookDao {
  @Autowired private BookRepository bookRepository;
  @Autowired private BookDescriptionRepository bookDescriptionRepository;
  @Autowired private LableRepository lableRepository;
  @Autowired RedisUtil redisUtil;

  @Override
  public Book findOne(Integer id) {
    Book book;
    Object p = redisUtil.get("book:" + id);
    if (p == null) {
      System.out.println("Book: " + id + " is not in Redis");
      System.out.println("Searching Book: " + id + " in DB");
      book = bookRepository.getOne(id);
      Optional<BookDescription> description = bookDescriptionRepository.findById(id);
      if (description.isPresent()) {
        book.setDescription(description.get().getDescripton());
      } else {
        book.setDescription("");
      }
      redisUtil.set("book:" + id, com.alibaba.fastjson.JSONArray.toJSON(book));
    } else {
      book = com.alibaba.fastjson.JSONArray.parseObject(p.toString(), Book.class);
      System.out.println("Book: " + id + " is in Redis");
    }
    return book;
  }

  @Override
  public List<Book> getBooks() {
    List<Book> books;
    Object p = redisUtil.get("book:books");
    if (p == null) {
      System.out.println("books is not in redis");
      books = bookRepository.getBooks();
      for (Book book : books) {
        Optional<BookDescription> description =
            bookDescriptionRepository.findById(book.getBookId());
        if (description.isPresent()) {
          book.setDescription(description.get().getDescripton());
        } else {
          book.setDescription("");
        }
      }
      redisUtil.set("book:books", com.alibaba.fastjson.JSONArray.toJSON(books));
    } else {
      books = com.alibaba.fastjson.JSONArray.parseArray(p.toString(), Book.class);
      System.out.println("books is in redis");
    }
    List<Book> result = new ArrayList<>();
    for (Book book : books) {
      if (!book.getIsDeleted()) result.add(book);
    }
    return result;
  }

  @Override
  public void addOrEditBook(Book book) {
    redisUtil.del("book:" + book.getBookId());
    redisUtil.del("book:books");
    bookRepository.saveAndFlush(book);
    bookDescriptionRepository.save(new BookDescription(book.getBookId(), book.getDescription()));
  }

  @Override
  public void delBook(Integer bookId) {
    Book book = bookRepository.getOne(bookId);
    book.setIsDeleted(true);
    redisUtil.del("book:" + book.getBookId());
    redisUtil.del("book:books");
    bookRepository.saveAndFlush(book);
    bookDescriptionRepository.deleteById(bookId);
  }

  @Override
  public int getBookPrice(Integer bookId) {
    Object p = redisUtil.get("book:" + bookId);
    if (p == null) {
      if (bookRepository.existsById(bookId)) return bookRepository.getBookPriceById(bookId);
      else return 0;
    } else {
      Book book = com.alibaba.fastjson.JSONArray.parseObject(p.toString(), Book.class);
      return book.getPrice();
    }
  }

  @Override
  public boolean decBookInventory(JSONArray jsonArray) {
    // ??????????????????????????????????????????
    int arraySize = jsonArray.size();
    for (int i = 0; i < arraySize; ++i) {
      JSONObject jsonObject = jsonArray.getJSONObject(i);
      Integer bookId = Integer.parseInt(jsonObject.get(Constant.BOOK_ID).toString());
      Integer num = Integer.parseInt(jsonObject.get(Constant.BOOK_NUMBER).toString());
      Book book = bookRepository.getOne(bookId);
      if (book.getInventory() < num || book.getIsDeleted()) {
        return false;
      }
    }
    // ??????????????????????????????????????????????????????????????????true
    for (int i = 0; i < arraySize; ++i) {
      JSONObject jsonObject = jsonArray.getJSONObject(i);
      Integer bookId = Integer.parseInt(jsonObject.get(Constant.BOOK_ID).toString());
      Integer num = Integer.parseInt(jsonObject.get(Constant.BOOK_NUMBER).toString());
      Book book = bookRepository.getOne(bookId);
      book.setInventory(book.getInventory() - num);
      bookRepository.saveAndFlush(book);
      redisUtil.del("book:" + bookId);
    }
    redisUtil.del("book:books");
    return true;
  }

  @Override
  public List<Label> getAboutLables(String label) {
    return lableRepository.findAboutsByLabelTwice(label);
  }

  @Override
  public List<Book> getBooksByLables(List<Label> labels) {
    List<Book> result = new ArrayList<>();
    List<Book> books = bookRepository.getBooks();
    for (Book book : books) {
      for (Label label : labels) {
        if (label.getLabel().equals(book.getType())) {
          result.add(book);
        }
      }
    }
    for (Book book : result) {
      Optional<BookDescription> description = bookDescriptionRepository.findById(book.getBookId());
      if (description.isPresent()) {
        book.setDescription(description.get().getDescripton());
      } else {
        book.setDescription("");
      }
    }
    return result;
  }
}
