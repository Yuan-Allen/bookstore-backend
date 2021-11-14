package com.yhy.bookstore.entity;

import lombok.Data;
import org.neo4j.ogm.annotation.*;

import java.util.Set;

@Data
@NodeEntity(label = "LABLE")
public class Label {
  @Id @GeneratedValue private long id;

  @Property(name = "lable")
  private String label;

  public Label() {}

  public Label(String label) {
    this.label = label;
  }

  @Relationship(type = "about")
  public Set<Label> abouts;
}
