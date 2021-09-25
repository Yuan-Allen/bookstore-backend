package com.yhy.bookstore.utils.websocketutils.chatutils.messages;

/* Represents an information message, like
 * an user entering or leaving the chat */
public class InfoMessage extends Message {

  private String info;

  public InfoMessage(String info) {
    this.info = info;
  }

  public String getInfo() {
    return info;
  }

  /* For logging purposes */
  @Override
  public String toString() {
    return "[InfoMessage] " + info;
  }
}
