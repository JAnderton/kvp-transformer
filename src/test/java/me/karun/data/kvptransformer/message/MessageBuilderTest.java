package me.karun.data.kvptransformer.message;

import org.testng.annotations.Test;

import java.util.Optional;

import static me.karun.data.kvptransformer.message.Message.message;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

public class MessageBuilderTest {

  @Test
  public void shouldInsertDataCorrectly() {
    final String key = "key";
    final String expectedValue = "value";
    final Message message = message().insert(key, expectedValue).build();

    final Optional<Object> actualValue = message.getValue(key);
    assertTrue(actualValue.isPresent());
    assertEquals(actualValue.get(), expectedValue);
  }

  @Test
  public void shouldInsertValueInMessage() {
    final String key = "key";
    final String expectedValue = "value";
    final Message message = message().withKey(key).andValue(expectedValue).build();

    final Optional<Object> actualValue = message.getValue(key);
    assertTrue(actualValue.isPresent());
    assertEquals(actualValue.get(), expectedValue);
  }

  @Test
  public void shouldNotCreateMessageWhenNodeExists() {
    final MessageBuilder messageBuilder = message()
      .withKey("application.name").andValue("kvp-transformer")
      .withKey("application.version").andValue(1)
      .withKey("application").andValue("should-fail");
    final String errorMessage = "Element already present at location";

    expectErrorOnBuildingMessage(messageBuilder, errorMessage);
  }

  @Test
  public void shouldNotCreateMessageWhenLeafExists() {
    final MessageBuilder messageBuilder = message()
      .withKey("application.name").andValue("kvp-transformer")
      .withKey("application.version").andValue(1)
      .withKey("application.version.minorRelease").andValue(2);
    final String errorMessage = "Leaf exists at specified location";

    expectErrorOnBuildingMessage(messageBuilder, errorMessage);
  }

  private void expectErrorOnBuildingMessage(final MessageBuilder messageBuilder, final String expectedErrorMessage) {
    try {
      messageBuilder.build();
      fail("Expected RuntimeException: " + expectedErrorMessage);
    } catch (Throwable t) {
      assertTrue(t instanceof RuntimeException);
      assertEquals(t.getMessage(), expectedErrorMessage);
    }
  }
}