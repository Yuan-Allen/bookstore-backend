package com.yhy.bookstore.controller;


import com.yhy.bookstore.constant.Constant;
import com.yhy.bookstore.entity.Book;
import com.yhy.bookstore.service.BookService;
import com.yhy.bookstore.utils.msgutils.Msg;
import com.yhy.bookstore.utils.msgutils.MsgCode;
import com.yhy.bookstore.utils.msgutils.MsgUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class BookController {
    @Autowired
    private BookService bookService;

    @RequestMapping("/getBooks")
    public List<Book> getBooks(@RequestBody Map<String, String> params) {
        return bookService.getBooks();
    }

    @RequestMapping("/getBook")
    public Book getBook(@RequestParam("id") Integer id){
        return bookService.findBookById(id);
    }

    @RequestMapping("/editBook")
    public Msg editBook(@RequestBody Map<String, String> params)
    {
        JSONObject jsonObject = JSONObject.fromObject(params);
        //取出书
        int bookId = Integer.parseInt(jsonObject.get(Constant.BOOK_ID).toString());
        Book book = bookService.findBookById(bookId);

        String name = jsonObject.get(Constant.NAME).toString();
        book.setName(name);

        String type = jsonObject.get(Constant.TYPE).toString();
        book.setType(type);

        String author = jsonObject.get(Constant.AUTHOR).toString();
        book.setAuthor(author);

        int price = Integer.parseInt(jsonObject.get(Constant.PRICE).toString());
        book.setPrice(price);

        String description = jsonObject.get(Constant.DESCRIPTION).toString();
        book.setDescription(description);

        int inventory = Integer.parseInt(jsonObject.get(Constant.INVENTORY).toString());
        book.setInventory(inventory);

        String image = jsonObject.get(Constant.IMAGE).toString();
        book.setImage(image);

        int isbn = Integer.parseInt(jsonObject.get(Constant.ISBN).toString());
        book.setIsbn(isbn);

        bookService.addOrEditBook(book);
        return MsgUtil.makeMsg(MsgCode.SUCCESS, MsgUtil.EDIT_SUCCESS_MSG);
    }

    @RequestMapping("/addBook")
    public Msg addBook(@RequestBody Map<String, String> params)
    {
        JSONObject jsonObject = JSONObject.fromObject(params);

        Book book = new Book();

        String name = jsonObject.get(Constant.NAME).toString();
        book.setName(name);

        String type = jsonObject.get(Constant.TYPE).toString();
        book.setType(type);

        String author = jsonObject.get(Constant.AUTHOR).toString();
        book.setAuthor(author);

        int price = Integer.parseInt(jsonObject.get(Constant.PRICE).toString());
        book.setPrice(price);

        String description = jsonObject.get(Constant.DESCRIPTION).toString();
        book.setDescription(description);

        int inventory = Integer.parseInt(jsonObject.get(Constant.INVENTORY).toString());
        book.setInventory(inventory);

        String image = jsonObject.get(Constant.IMAGE).toString();
        book.setImage(image);

        int isbn = Integer.parseInt(jsonObject.get(Constant.ISBN).toString());
        book.setIsbn(isbn);

        book.setIsDeleted(false);

        bookService.addOrEditBook(book);
        return MsgUtil.makeMsg(MsgCode.SUCCESS, MsgUtil.ADD_SUCCESS_MSG);
    }

    @RequestMapping("/delBook")
    public Msg delBook(@RequestBody Map<String, String> params)
    {
        JSONObject jsonObject = JSONObject.fromObject(params);
        int bookId = Integer.parseInt(jsonObject.get(Constant.BOOK_ID).toString());

        bookService.delBook(bookId);

        return MsgUtil.makeMsg(MsgCode.SUCCESS, MsgUtil.DELETE_SUCCESS_MSG);
    }
}
