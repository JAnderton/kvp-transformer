package me.karun.data.kvptransformer.message;

import com.google.gson.Gson;

import java.util.Map;
import java.util.Optional;

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
