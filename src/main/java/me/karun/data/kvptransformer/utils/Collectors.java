package me.karun.data.kvptransformer.utils;

import com.google.common.collect.ImmutableList;
import me.karun.data.kvptransformer.entities.message.MessageBuilder;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class Collectors {
  public static <T> Collector<T, Stack<T>, Stack<T>> asStack() {
    final Supplier<Stack<T>> stackSupplier = Stack::new;
    final BinaryOperator<Stack<T>> stackCombiner = (s1, s2) -> {
      final Stack<T> freshStack = stackSupplier.get();
      s1.forEach(freshStack::push);
      s2.forEach(freshStack::push);
      return freshStack;
    };

    return Collector.of(stackSupplier, Stack::push, stackCombiner);
  }

  public static Collector<Pair<String, Object>, MessageBuilder, MessageBuilder> asMessage() {
    final Supplier<MessageBuilder> supplier = MessageBuilder::new;
    final BiConsumer<MessageBuilder, Pair<String, Object>> accumulator = (b, p) -> b.insert(p.getKey(), p.getValue());
    final BinaryOperator<MessageBuilder> combiner = (b1, b2) -> new MessageBuilder(
      new ImmutableList.Builder<Pair<String, Object>>()
        .addAll(b1.getData())
        .addAll(b2.getData())
        .build()
    );

    return Collector.of(supplier, accumulator, combiner);
  }
}
