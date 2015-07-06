package me.karun.data.kvptransformer.phases.read;

import javafx.util.Pair;
import me.karun.data.kvptransformer.entities.message.Message;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.assertEquals;

public class DelimitedLineParserTest {

  @Test
  public void shouldParseCorrectKeyValuePairs() {
    final List<String> lines = Arrays.asList("key1=value1", "key2=value2");
    final List<Pair<String, String>> expectedResult = Arrays.asList(new Pair<>("key1", "value1"), new Pair<>("key2", "value2"));

    final Message parsedMessage = new DelimitedLineParser(lines, "=").parse();
    final List<Pair<String, String>> actualResult = parsedMessage.getQualifiedKeys().stream()
      .map(key -> new Pair<>(key, parsedMessage.getValue(key).orElseGet(() -> "").toString()))
      .collect(Collectors.toList());

    assertEquals(actualResult, expectedResult);
  }

  @Test(expectedExceptions = {IndexOutOfBoundsException.class})
  public void shouldThrowErrorWhenDelimiterIsNotFound() {
    final List<String> incorrectLines = Arrays.asList("key1=value1", "key2value2");

    new DelimitedLineParser(incorrectLines, "=").parse();
  }
}