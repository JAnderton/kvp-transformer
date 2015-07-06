package me.karun.data.kvptransformer.entities.message;


import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import static me.karun.data.kvptransformer.entities.message.Message.message;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

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

  @Test
  public void shouldProvideFullyQualifiedKeysInMessage() {
    final Set<String> expectedResult = new HashSet<>(Arrays.asList(
      "application.name",
      "application.version",
      "test.value",
      "test123.value"
    ));
    final Message message = message()
      .withKey("application.name").andValue("kvp-transformer")
      .withKey("application.version").andValue(1)
      .withKey("test.value").andValue("val123")
      .withKey("test123.value").andValue("val123")
      .build();

    assertEquals(message.getQualifiedKeys(), expectedResult);
  }


  @Test
  public void shouldProvideFullyQualifiedKeysForNonNestedMessages() {
    final Set<String> expectedResult = new HashSet<>(Arrays.asList(
      "application",
      "test"
    ));
    final Message message = message()
      .withKey("application").andValue("kvp-transformer")
      .withKey("test").andValue("val123")
      .build();

    assertEquals(message.getQualifiedKeys(), expectedResult);
  }

  @Test
  public void shouldProvideToStringWithFullyQualifiedObjectNameAndObjectIdAndAllFields() {
    final String actualValue = message()
      .withKey("application").andValue("kvp-transformer")
      .withKey("test").andValue("val123")
      .build().toString();
    final String toStringExpression = String.format("%s@.*%s",
      Message.class.getCanonicalName(),
      "\\[dataTree=\\{application=kvp-transformer, test=val123\\}\\]");

    assertTrue(Pattern.compile(toStringExpression).matcher(actualValue).matches(),
      "Message#toString() needs to meet a specific contract => classCanonicalName@objectId[objectFieldsWithValues]\n" +
        "Expected format: \"" + toStringExpression + "\"\n" +
        "Actual value   : \"" + actualValue + "\"");
  }
}