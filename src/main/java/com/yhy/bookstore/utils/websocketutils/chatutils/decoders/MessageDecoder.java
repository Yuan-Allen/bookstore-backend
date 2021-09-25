package com.yhy.bookstore.utils.websocketutils.chatutils.decoders;

import com.yhy.bookstore.utils.websocketutils.chatutils.messages.ChatMessage;
import com.yhy.bookstore.utils.websocketutils.chatutils.messages.JoinMessage;
import com.yhy.bookstore.utils.websocketutils.chatutils.messages.Message;

import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.StringReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/* Decode a JSON message into a JoinMessage or a ChatMessage.
 * For example, the incoming message:
 * {"type":"chat","name":"Peter","target":"Duke","message":"How are you?"}
 * is decoded as (new ChatMessage("Peter", "Duke", "How are you?"))
 */
public class MessageDecoder implements Decoder.Text<Message> {
  /* Stores the name-value pairs from a JSON message as a Map */
  private Map<String, String> messageMap;

  @Override
  public void init(EndpointConfig ec) {}

  @Override
  public void destroy() {}

  /* Create a new Message object if the message can be decoded */
  @Override
  public Message decode(String string) throws DecodeException {
    Message msg = null;
    if (willDecode(string)) {
      switch (messageMap.get("type")) {
        case "join":
          msg = new JoinMessage(messageMap.get("name"));
          break;
        case "chat":
          msg =
              new ChatMessage(
                  messageMap.get("name"), messageMap.get("target"), messageMap.get("message"));
      }
    } else {
      throw new DecodeException(string, "[Message] Can't decode.");
    }
    return msg;
  }

  /* Decode a JSON message into a Map and check if it contains
   * all the required fields according to its type. */
  @Override
  public boolean willDecode(String string) {
    boolean decodes = false;
    /* Convert the message into a map */
    messageMap = new HashMap<>();
    JsonParser parser = Json.createParser(new StringReader(string));
    while (parser.hasNext()) {
      if (parser.next() == JsonParser.Event.KEY_NAME) {
        String key = parser.getString();
        parser.next();
        String value = parser.getString();
        messageMap.put(key, value);
      }
    }
    /* Check the kind of message and if all fields are included */
    Set keys = messageMap.keySet();
    if (keys.contains("type")) {
      switch (messageMap.get("type")) {
        case "join":
          if (keys.contains("name")) decodes = true;
          break;
        case "chat":
          String[] chatMsgKeys = {"name", "target", "message"};
          if (keys.containsAll(Arrays.asList(chatMsgKeys))) decodes = true;
          break;
      }
    }
    return decodes;
  }
}
