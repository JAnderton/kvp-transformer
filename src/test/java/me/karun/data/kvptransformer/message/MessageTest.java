package me.karun.data.kvptransformer.message;


import org.testng.annotations.Test;

import static me.karun.data.kvptransformer.message.Message.message;
import static org.testng.Assert.assertEquals;

public class MessageTest {

  @Test
  public void shouldCreateSimpleJsonFromMessage() {
    final String expectedResult = "{\"key\":\"value\"}";
    final Message message = message().withKey("key").andValue("value").build();

    assertEquals(message.toJson(), expectedResult);
  }

  @Test
  public void shouldCreateComplexJsonFromMessage() {
    final String expectedResult = "{\"application\":{\"name\":\"kvp-transformer\",\"version\":1}}";
    final Message message = message()
      .withKey("application.name").andValue("kvp-transformer")
      .withKey("application.version").andValue(1)
      .build();

    assertEquals(message.toJson(), expectedResult);
  }
}