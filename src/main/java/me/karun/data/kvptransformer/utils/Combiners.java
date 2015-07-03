package me.karun.data.kvptransformer.utils;

import com.google.common.collect.FluentIterable;
import me.karun.data.kvptransformer.entities.message.MessageBuilder;

import java.util.function.BinaryOperator;

public class Combiners {
  public static final BinaryOperator<MessageBuilder> messageBuilderCombiner =
    (m1, m2) -> new MessageBuilder(FluentIterable.from(m1.getData()).append(m2.getData()).toList());
}
