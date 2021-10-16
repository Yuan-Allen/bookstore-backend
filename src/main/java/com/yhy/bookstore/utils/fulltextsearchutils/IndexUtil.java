package com.yhy.bookstore.utils.fulltextsearchutils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;

public class IndexUtil {
  public static void indexDocs(final IndexWriter writer, Path path) throws IOException {
    if (Files.isDirectory(path)) {
      Files.walkFileTree(
          path,
          new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
              try {
                indexDoc(writer, file);
              } catch (IOException ignore) {
                // don't index files that can't be read.
              }
              return FileVisitResult.CONTINUE;
            }
          });
    } else {
      indexDoc(writer, path);
    }
  }

  public static void indexDoc(IndexWriter writer, Path file) throws IOException {
    try (InputStream stream = Files.newInputStream(file)) {
      // make a new, empty document
      Document doc = new Document();

      ObjectMapper objectMapper = new ObjectMapper();
      Map<String, Object> map = objectMapper.readValue(new File(file.toString()), Map.class);

      doc.add(new StoredField("id", map.get("id").toString()));
      doc.add(new TextField("description", map.get("description").toString(), Field.Store.NO));
      //      System.out.println(
      //          "index file id = "
      //              + map.get("id").toString()
      //              + " description = "
      //              + map.get("description").toString());

      if (writer.getConfig().getOpenMode() == IndexWriterConfig.OpenMode.CREATE) {
        // New index, so we just add the document (no old document can be there):
        System.out.println("adding " + file);
        writer.addDocument(doc);
      } else {
        // Existing index (an old copy of this document may have been indexed) so
        // we use updateDocument instead to replace the old one matching the exact
        // path, if present:
        System.out.println("updating " + file);
        writer.updateDocument(new Term("path", file.toString()), doc);
      }
    }
  }
}
