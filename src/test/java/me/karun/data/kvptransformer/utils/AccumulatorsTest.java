package me.karun.data.kvptransformer.utils;

import javafx.util.Pair;
import me.karun.data.kvptransformer.entities.message.MessageBuilder;
import org.testng.annotations.Test;

import static me.karun.data.kvptransformer.entities.message.Message.message;
import static me.karun.data.kvptransformer.utils.Accumulators.pairToMessageBuilderAccumulator;
import static org.testng.Assert.*;

public class AccumulatorsTest {
  @Test
  public void shouldAccumulatePairsInMessageBuilder() {
    final MessageBuilder initialBuilder = message();
    final MessageBuilder expectedResult = message().insert("key", "value");
    final MessageBuilder actualResult = pairToMessageBuilderAccumulator.apply(initialBuilder, new Pair<>("key", "value"));

    assertEquals(actualResult.getData(), expectedResult.getData());
    assertTrue(initialBuilder.equals(actualResult));
  }

}