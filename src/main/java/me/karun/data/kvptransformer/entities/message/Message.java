package me.karun.data.kvptransformer.entities.message;

import com.google.gson.Gson;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
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
    final Function<String, List<String>> splitKeys = k -> Arrays.asList(k.split("\\."));
    final BiFunction<String, Map<String, Object>, Object> iterateTree = (k, source) -> source.get(k);

    Map<String, Object> treeHead = dataTree;
    for (final String k : splitKeys.apply(key)) {
      final Object node = iterateTree.apply(k, treeHead);
      if (!(node instanceof Map)) {
        return Optional.ofNullable(node);
      }
      treeHead = (Map<String, Object>) node;
    }

    return Optional.empty();
  }

  public String toJson() {
    return new Gson().toJson(dataTree);
  }

  public Set<String> getQualifiedKeys() {
    return fetchQualifiedKeys(dataTree, Optional.empty());
  }

  private Set<String> fetchQualifiedKeys(final Map<String, Object> tree, final Optional<String> parentKey) {
    final Set<String> keys = tree.keySet().stream()
      .filter(k -> !(tree.get(k) instanceof Map))
      .map(s -> Arrays.<Optional<String>>asList(parentKey, Optional.of(s.toString())).stream()
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(Collectors.joining(".")))
      .collect(Collectors.toSet());

    tree.keySet().stream()
      .filter(k -> tree.get(k) instanceof Map)
      .map(k -> fetchQualifiedKeys((Map<String, Object>) tree.get(k), Optional.of(k)))
      .forEach(keys::addAll);

    return keys;
  }

  @Override
  public boolean equals(Object o) {
    return EqualsBuilder.reflectionEquals(this, o);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(97, 31, this);
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
