package me.karun.data.kvptransformer.message;

import org.junit.Test;

import static me.karun.data.kvptransformer.message.Message.message;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class MessageTest {

  @Test
  public void shouldCreateSimpleJsonFromMessage() {
    final String expectedResult = "{\"key\":\"value\"}";
    final Message message = message().withKey("key").andValue("value").build();

    assertThat(message.toJson(), is(expectedResult));
  }

  @Test
  public void shouldCreateComplexJsonFromMessage() {
    final String expectedResult = "{\"application\":{\"name\":\"kvp-transformer\",\"version\":1}}";
    final Message message = message()
      .withKey("application.name").andValue("kvp-transformer")
      .withKey("application.version").andValue(1)
      .build();

    assertThat(message.toJson(), is(expectedResult));
  }
}