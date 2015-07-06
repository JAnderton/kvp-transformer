package me.karun.data.kvptransformer.phases.readers;

import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.NoSuchFileException;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class SingleRecordFileReaderTest {

  @Test
  public void shouldReadFilesCorrectly() throws IOException, URISyntaxException {
    final String fileName = "src/test/resources/single-record"; // FIXME should be paths relative to resources
    final List<String> expectedLines = Arrays.asList("key1=value1","key2=2");

    final List<String> actualLines = new SingleRecordFileReader(fileName).read();

    assertEquals(actualLines, expectedLines);
  }

  @Test(expectedExceptions = {NoSuchFileException.class})
  public void shouldThrowErrorWhenFileDoesNotExist() throws IOException, URISyntaxException {
    final String invalidFileName = "some-file-that-does-not-exist";
    new SingleRecordFileReader(invalidFileName).read();
  }
}