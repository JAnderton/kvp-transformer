package me.karun.data.kvptransformer.process;

import me.karun.data.kvptransformer.phases.parsers.DelimitedLineParser;
import me.karun.data.kvptransformer.phases.publishers.ConsolePublisher;
import me.karun.data.kvptransformer.phases.readers.SingleRecordFileReader;
import me.karun.data.kvptransformer.phases.transformers.TransformationEngine;

import java.io.IOException;
import java.net.URISyntaxException;

public class Chain {
  private final SingleRecordFileReader reader;
  private final DelimitedLineParser parser;
  private final TransformationEngine engine;
  private final ConsolePublisher publisher;

  public Chain(final SingleRecordFileReader reader,
               final DelimitedLineParser parser,
               final TransformationEngine engine,
               final ConsolePublisher publisher) {
    this.reader = reader;
    this.parser = parser;
    this.engine = engine;
    this.publisher = publisher;
  }

  public void execute() throws IOException, URISyntaxException {
    publisher.publish(engine.process(parser.parse(reader.read())));
  }
}
