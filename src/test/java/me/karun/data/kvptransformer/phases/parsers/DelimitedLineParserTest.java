package me.karun.data.kvptransformer.phases.parsers;

import javafx.util.Pair;
import me.karun.data.kvptransformer.entities.message.Message;
import me.karun.data.kvptransformer.phases.parsers.DelimitedLineParser;
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

    final Message parsedMessage = new DelimitedLineParser("=").parse(lines);
    final List<Pair<String, String>> actualResult = parsedMessage.getQualifiedKeys().stream()
      .map(key -> new Pair<>(key, parsedMessage.getValue(key).orElseGet(() -> "").toString()))
      .collect(Collectors.toList());

    assertEquals(actualResult, expectedResult);
  }

  @Test(
    expectedExceptions = {IndexOutOfBoundsException.class},
    expectedExceptionsMessageRegExp = "Index: 1, Size: 1"
  )
  public void shouldThrowErrorWhenDelimiterIsNotFound() {
    final List<String> incorrectLines = Arrays.asList("key1=value1", "key2value2");

    new DelimitedLineParser("=").parse(incorrectLines);
  }
}