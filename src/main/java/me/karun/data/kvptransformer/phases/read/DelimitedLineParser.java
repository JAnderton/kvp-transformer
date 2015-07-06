package me.karun.data.kvptransformer.phases.read;

import javafx.util.Pair;
import me.karun.data.kvptransformer.entities.message.Message;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static me.karun.data.kvptransformer.entities.message.Message.message;
import static me.karun.data.kvptransformer.utils.Accumulators.pairToMessageBuilderAccumulator;
import static me.karun.data.kvptransformer.utils.Combiners.messageBuilderCombiner;

public class DelimitedLineParser {
  private final List<String> lines;
  private final String delimiter;

  DelimitedLineParser(List<String> lines, String delimiter) {
    this.lines = lines;
    this.delimiter = delimiter;
  }

  public Message parse() {
    return lines.stream()
      .map(s -> Arrays.asList(s.split(delimiter)))
      .map(l -> l.stream().map(String::trim).collect(Collectors.toList()))
      .map(l -> new Pair<>(l.get(0), l.get(1)))
      .reduce(message(), pairToMessageBuilderAccumulator, messageBuilderCombiner)
      .build();
  }
}
