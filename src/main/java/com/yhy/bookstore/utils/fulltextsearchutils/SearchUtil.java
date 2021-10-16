package com.yhy.bookstore.utils.fulltextsearchutils;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

public class SearchUtil {
  public static ScoreDoc[] search(
      IndexSearcher searcher, String field, Analyzer analyzer, String queryString)
      throws Exception {
    // System.out.println("queryString: " + queryString);
    QueryParser parser = new QueryParser(field, analyzer);
    Query query = parser.parse(queryString);
    TopDocs hits = searcher.search(query, 100);
    // System.out.println(hits.totalHits);
    return hits.scoreDocs;
  }
}
