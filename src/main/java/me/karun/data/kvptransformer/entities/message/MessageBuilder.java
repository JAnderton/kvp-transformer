package me.karun.data.kvptransformer.entities.message;

import com.google.common.collect.Lists;
import me.karun.data.kvptransformer.entities.creation.Builder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.function.Function;

import static me.karun.data.kvptransformer.utils.Collectors.asStack;

public class MessageBuilder implements Builder<Message> {

  private final List<Pair<String, Object>> data;

  public MessageBuilder() {
    this.data = new ArrayList<>();
  }

  public MessageBuilder(final List<Pair<String, Object>> data) {
    this.data = data;
  }

  public MessageKey withKey(final String key) {
    return new MessageKey(this, key);
  }

  public MessageBuilder insert(final String key, final Object value) {
    data.add(new ImmutablePair<>(key, value));
    return this;
  }

  @Override
  public Message build() {
    final Function<String, List<String>> splitKeys = key -> Arrays.asList(key.split("\\."));
    final Function<List<String>, List<String>> reverseList = Lists::reverse;
    final Function<List<String>, Stack<String>> toStack = l -> l.stream().collect(asStack());

    final Map<String, Object> dataTree = new HashMap<>();

    data.stream()
      .map(pair -> new ImmutablePair<>(
        splitKeys.andThen(reverseList).andThen(toStack).apply(pair.getKey()),
        pair.getValue()))
      .forEach(p -> insertInMap(dataTree, p.getKey(), p.getValue()));
    // TODO: Move away from insertInMap. Move to using Collectors.

    return new Message(dataTree);
  }

  public List<Pair<String, Object>> getData() {
    return data;
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
      @SuppressWarnings("unchecked")
      final Map<String, Object> treeSubset = (Map<String, Object>) o;

      return insertInMap(treeSubset, keyStack, value);
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
}
