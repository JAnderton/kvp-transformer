package me.karun.data.kvptransformer.message;


import org.testng.annotations.Test;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

public class MessageKeyTest {

  @Test
  public void shouldInsertDataIntoMessageBuilder() {
    final String key = "key";
    final String value = "value";
    final MessageBuilder inputMessageBuilder = mock(MessageBuilder.class);
    final MessageKey messageKey = new MessageKey(inputMessageBuilder, key);
    when(inputMessageBuilder.insert(key, value)).thenReturn(inputMessageBuilder);

    final MessageBuilder outputMessageBuilder = messageKey.andValue(value);

    assertEquals(outputMessageBuilder, inputMessageBuilder);
    verify(inputMessageBuilder).insert(key, value);
  }
}