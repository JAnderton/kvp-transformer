package me.karun.data.kvptransformer.utils;

import javafx.util.Pair;
import me.karun.data.kvptransformer.entities.message.MessageBuilder;

import java.util.function.BiFunction;

public class Accumulators {
  public static final BiFunction<MessageBuilder, Pair<String, String>, MessageBuilder> pairToMessageBuilderAccumulator =
    (m, p) -> m.withKey(p.getKey()).andValue(p.getValue());
}
