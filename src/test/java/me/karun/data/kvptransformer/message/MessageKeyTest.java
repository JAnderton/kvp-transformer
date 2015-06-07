package me.karun.data.kvptransformer.message;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class MessageKeyTest {

  @Test
  public void shouldInsertDataIntoMessageBuilder() {
    final String key = "key";
    final String value = "value";
    final MessageBuilder inputMessageBuilder = mock(MessageBuilder.class);
    final MessageKey messageKey = new MessageKey(inputMessageBuilder, key);
    when(inputMessageBuilder.insert(key, value)).thenReturn(inputMessageBuilder);

    final MessageBuilder outputMessageBuilder = messageKey.andValue(value);

    assertThat(outputMessageBuilder, is(inputMessageBuilder));
    verify(inputMessageBuilder).insert(key, value);
  }
}