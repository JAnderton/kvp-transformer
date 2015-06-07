package me.karun.data.kvptransformer.message;

import com.google.gson.Gson;
import javafx.util.Pair;

import java.util.*;
import java.util.function.Function;

public class Message {
  private final Map<String, Object> dataTree;

  public Message(final Map<String, Object> dataTree) {
    this.dataTree = dataTree;
  }

  public static MessageBuilder message() {
    return new MessageBuilder();
  }

  public Optional<Object> getValue(final String key) {
    return Optional.ofNullable(dataTree.get(key));
  }

  public String toJson() {
    return new Gson().toJson(dataTree);
  }
}

class MessageKey {
  private MessageBuilder builder;
  private final String key;

  public MessageKey(MessageBuilder builder, final String key) {
    this.builder = builder;
    this.key = key;
  }

  public MessageBuilder andValue(final Object value) {
    return builder.insert(key, value);
  }
}