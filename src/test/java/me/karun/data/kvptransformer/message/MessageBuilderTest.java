package me.karun.data.kvptransformer.message;

import org.junit.Test;

import static me.karun.data.kvptransformer.message.Message.message;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class MessageBuilderTest {

  @Test
  public void shouldInsertValueInMessage() {
    final String key = "key";
    final String value = "value";
    final Message message = message().withKey(key).andValue(value).build();

    assertTrue(message.getValue(key).isPresent());
    assertThat(message.getValue(key).get(), is(value));
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
      assertThat(t.getMessage(), is(expectedErrorMessage));
    }
  }
}