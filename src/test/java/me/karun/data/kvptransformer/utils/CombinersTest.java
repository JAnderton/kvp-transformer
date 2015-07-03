package me.karun.data.kvptransformer.utils;

import me.karun.data.kvptransformer.entities.message.MessageBuilder;
import org.testng.annotations.Test;

import static me.karun.data.kvptransformer.utils.Combiners.messageBuilderCombiner;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

public class CombinersTest {
  @Test
  public void shouldCombineTwoMessagesWithoutMutation() {
    final MessageBuilder m1 = new MessageBuilder().insert("m1", "v1");
    final MessageBuilder m2 = new MessageBuilder().insert("m2", "v2");
    final MessageBuilder expectedResult = new MessageBuilder().insert("m1", "v1").insert("m2", "v2");

    final MessageBuilder actualResult = messageBuilderCombiner.apply(m1, m2);

    assertEquals(actualResult.getData(), expectedResult.getData());
    assertFalse(actualResult.equals(m1));
    assertFalse(actualResult.equals(m2));
  }
}