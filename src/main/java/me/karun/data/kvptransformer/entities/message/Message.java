package me.karun.data.kvptransformer.entities.message;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

  public Set<String> getQualifiedKeys() {
    return fetchQualifiedKeys(dataTree, "");
  }

  private Set<String> fetchQualifiedKeys(final Map<String, Object> tree, final String parentKey) {
    final Set<String> keys = tree.keySet().stream()
      .filter(k -> !(tree.get(k) instanceof Map))
      .map(s -> parentKey + "." + s)
      .collect(Collectors.toSet());

    tree.keySet().stream()
      .filter(k -> tree.get(k) instanceof Map)
      .map(k -> fetchQualifiedKeys((Map<String, Object>)tree.get(k), k))
      .forEach(keys::addAll);

    return keys;
  }

  public Map<String, Object> toMap() {
    return deepCopyMap(dataTree);
  }

  private Map<String, Object> deepCopyMap(final Map<String, Object> baseMap) {
    final Map<String, Object> treeCopy = new HashMap<>();

    baseMap.forEach((k, v) -> {
      if (!(v instanceof Map)) {
        treeCopy.put(k, v);
      } else {
        @SuppressWarnings("unchecked")
        final Map<String, Object> subMap = (Map<String, Object>) v;

        treeCopy.put(k, deepCopyMap(subMap));
      }
    });
    return treeCopy;
  }
}
