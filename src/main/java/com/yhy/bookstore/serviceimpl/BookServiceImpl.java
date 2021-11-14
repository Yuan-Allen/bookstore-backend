package com.yhy.bookstore.serviceimpl;

import com.yhy.bookstore.dao.BookDao;
import com.yhy.bookstore.entity.Book;
import com.yhy.bookstore.entity.Label;
import com.yhy.bookstore.service.BookService;
import com.yhy.bookstore.utils.fulltextsearchutils.IndexUtil;
import com.yhy.bookstore.utils.fulltextsearchutils.SearchUtil;
import net.sf.json.JSONArray;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
  @Autowired private BookDao bookDao;

  @Override
  public Book findBookById(Integer id) {
    return bookDao.findOne(id);
  }

  @Override
  public List<Book> getBooks() {
    return bookDao.getBooks();
  }

  @Override
  public void addOrEditBook(Book book) {
    bookDao.addOrEditBook(book);
  }

  @Override
  public void delBook(Integer bookId) {
    bookDao.delBook(bookId);
  }

  @Override
  public int getBookPrice(Integer bookId) {
    return bookDao.getBookPrice(bookId);
  }

  @Override
  public boolean decBookInventory(JSONArray jsonArray) {
    return bookDao.decBookInventory(jsonArray);
  }

  @Override
  public long indexBooks(Path indexDirPath, Path dataDirPath) {
    SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
    IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
    iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
    long numIndexed = 0;
    try {
      Directory dir = FSDirectory.open(indexDirPath);
      IndexWriter writer = new IndexWriter(dir, iwc);
      IndexUtil.indexDocs(writer, dataDirPath);
      numIndexed = writer.getPendingNumDocs();
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return numIndexed;
  }

  @Override
  public List<Book> fullTextSearch(Path indexDirPath, String queryString) throws Exception {
    IndexReader reader = DirectoryReader.open(FSDirectory.open(indexDirPath));
    IndexSearcher searcher = new IndexSearcher(reader);
    Analyzer analyzer = new SmartChineseAnalyzer();
    String field = "description";
    ScoreDoc[] scoreDocs = SearchUtil.search(searcher, field, analyzer, queryString);
    List<Book> result = new ArrayList<>();
    for (ScoreDoc scoreDoc : scoreDocs) {
      Document doc = searcher.doc(scoreDoc.doc);
      Integer bookId = Integer.parseInt(doc.get("id"));
      result.add(bookDao.findOne(bookId));
    }
    reader.close();
    return result;
  }

  @Override
  public List<Book> getRelatedBooksByLable(String label) {
    List<Label> labels = bookDao.getAboutLables(label);
    return bookDao.getBooksByLables(labels);
  }
}
