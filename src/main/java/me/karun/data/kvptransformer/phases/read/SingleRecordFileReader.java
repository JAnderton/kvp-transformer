package me.karun.data.kvptransformer.phases.read;

import javafx.util.Pair;
import me.karun.data.kvptransformer.entities.message.Message;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static me.karun.data.kvptransformer.entities.message.Message.message;
import static me.karun.data.kvptransformer.utils.Accumulators.pairToMessageBuilderAccumulator;
import static me.karun.data.kvptransformer.utils.Combiners.messageBuilderCombiner;

public class SingleRecordFileReader {
  private final String fileName;

  public SingleRecordFileReader(String fileName) {
    this.fileName = fileName;
  }

  public Message read() throws IOException, URISyntaxException {
    return new FileParser(Files.readAllLines(Paths.get(fileName))).parse();
  }

  class FileParser {
    private final List<String> lines;

    FileParser(List<String> lines) {
      this.lines = lines;
    }

    public Message parse() {
      return lines.stream()
        .map(s -> Arrays.asList(s.split("=")))
        .map(l -> l.stream().map(String::trim).collect(Collectors.toList()))
        .map(l -> new Pair<>(l.get(0), l.get(1)))
        .reduce(message(), pairToMessageBuilderAccumulator, messageBuilderCombiner)
        .build();
    }
  }
}
