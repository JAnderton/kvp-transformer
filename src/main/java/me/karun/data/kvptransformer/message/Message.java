package me.karun.data.kvptransformer.message;

import com.google.gson.Gson;
import javafx.util.Pair;

import java.util.*;
import java.util.function.Function;

/**
 * Created by karun on 05/06/15.
 */
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

class MessageBuilder {

  private final List<Pair<String, Object>> data;
  private final Function<String, Stack<String>> keyStackCreator = key -> {
    final Stack<String> keyStack = new Stack<>();
    final List<String> keys = Arrays.asList(key.split("\\."));

    for (int i = keys.size() - 1; i >= 0; i--) {
      keyStack.push(keys.get(i));
    }
    return keyStack;
  };

  private final Function<String, List<String>> splitKeys = key -> Arrays.asList(key.split("\\."));
  private final Function<List<String>, List<String>> reverseList = list -> {
    final List<String> reversedList = new ArrayList<>();
    for (int i = list.size() - 1; i >= 0; i--) {
      reversedList.add(list.get(i));
    }

    return reversedList;
  };
  private final Function<List<String>, Stack<String>> toStack = list -> {
    final Stack<String> stack = new Stack<>();
    list.stream().forEach(e -> stack.push(e));
    return stack;
  };

  public MessageBuilder() {
    this.data = new ArrayList<>();
  }

  public MessageKey withKey(final String key) {
    return new MessageKey(this, key);
  }

  public MessageBuilder insert(final String key, final Object value) {
    data.add(new Pair<>(key, value));
    return this;
  }

  private MessageBuilder insertInMap(final Map<String, Object> treeSubset, final Stack<String> keyStack, final Object value) {
    final String keyElement = keyStack.pop();
    final Optional<Object> element = Optional.ofNullable(treeSubset.get(keyElement));

    if (element.isPresent()) {
      return processElementBeingPresent(keyStack, element.get(), value);
    }

    return processElementAbsent(treeSubset, keyElement, keyStack, value);
  }

  private MessageBuilder processElementBeingPresent(final Stack<String> keyStack, final Object o, final Object value) {
    if (keyStack.isEmpty()) {
      throw new RuntimeException("Element already present at location");
    }

    if (o instanceof Map) {
      return insertInMap((Map<String, Object>) o, keyStack, value);
    }

    throw new RuntimeException("Leaf exists at specified location");
  }

  private MessageBuilder processElementAbsent(final Map<String, Object> treeSubset, final String keyElement,
                                              final Stack<String> keyStack, final Object value) {
    final Map<String, Object> node = new HashMap<>();
    treeSubset.put(keyElement, node);

    if (keyStack.isEmpty()) {
      treeSubset.put(keyElement, value);
      return this;
    }

    return insertInMap(node, keyStack, value);
  }

  public Message build() {
    final Map<String, Object> dataTree = new HashMap<>();
    data.stream()
      .map(pair -> new Pair<>(
        splitKeys.andThen(reverseList).andThen(toStack).apply(pair.getKey()),
        pair.getValue()))
      .forEach(p -> insertInMap(dataTree, p.getKey(), p.getValue()));

    return new Message(dataTree);
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