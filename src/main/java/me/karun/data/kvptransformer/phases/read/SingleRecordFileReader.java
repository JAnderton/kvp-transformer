package me.karun.data.kvptransformer.phases.read;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class SingleRecordFileReader {
  private final String fileName;

  public SingleRecordFileReader(final String fileName) {
    this.fileName = fileName;
  }

  public List<String> read() throws IOException, URISyntaxException {
    return Files.readAllLines(Paths.get(fileName));
  }
}

