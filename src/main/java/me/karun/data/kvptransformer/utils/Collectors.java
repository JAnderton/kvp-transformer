package me.karun.data.kvptransformer.utils;

import java.util.Stack;
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
}
