package me.karun.data.kvptransformer.phases.parsers;

import javafx.util.Pair;
import me.karun.data.kvptransformer.entities.message.Message;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.assertEquals;

public class JsonRecordParserTest {

  @Test
  public void shouldParseCorrectKeyValuePairs() {
    final List<String> lines = Arrays.asList(
      "{\"key1\":2.5,\"key2\":2.0}", // TODO: change this test case to 2 instead of 2.0
      "{\"key1\":\"value1.1\",\"key2\":\"value2.1\"}"
    );
    final List<List<Pair<String, Object>>> expectedResult = Arrays.asList(
      Arrays.asList(new Pair<>("key1", 2.5), new Pair<>("key2", 2.0)),
      Arrays.asList(new Pair<>("key1", "value1.1"), new Pair<>("key2", "value2.1"))
    );

    final List<Message> parsedMessages = new JsonRecordParser().parse(lines);
    final List<List<Pair<String, Object>>> actualResult = parsedMessages.stream()
      .map(parsedMessage -> parsedMessage.getQualifiedKeys().stream()
        .map(key -> new Pair<>(key, parsedMessage.getValue(key).orElseGet(() -> "")))
        .collect(Collectors.toList()))
      .collect(Collectors.toList());

    assertEquals(actualResult, expectedResult);
  }

  @Test(
    expectedExceptions = {IndexOutOfBoundsException.class}, // TODO: Requires update
    expectedExceptionsMessageRegExp = "Index: 1, Size: 1"
  )
  public void shouldThrowErrorWhenDelimiterIsNotFound() {
    final List<String> incorrectLines = Arrays.asList(
      "{\"key1\":\"value1.0\",\"key2\":\"value2.0\"}",
      "{\"key1\":\"value1.1\",\"key2\":value2.1}"
    );

    new DelimitedLineParser("=").parse(incorrectLines);
  }
}