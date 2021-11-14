package com.yhy.bookstore.repository;

import com.yhy.bookstore.entity.Label;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LableRepository extends Neo4jRepository<Label, Long> {
  Label findByLabel(String label);

  @Query("MATCH (a:LABLE{lable:$l})-[:about]->(b) MATCH (b)-[:about]->(c) RETURN a,b,c")
  List<Label> findAboutsByLabelTwice(@Param("l") String l);
}
