package me.karun.data.kvptransformer.phases.read;

import me.karun.data.kvptransformer.entities.message.Message;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SingleRecordFileReader {
  private final String fileName;

  public SingleRecordFileReader(String fileName) {
    this.fileName = fileName;
  }

  public Message read() throws IOException, URISyntaxException {
    return new DelimitedLineParser(Files.readAllLines(Paths.get(fileName)), "=")
      .parse();
  }
}

