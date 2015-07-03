package me.karun.data.kvptransformer.phases.read;

import me.karun.data.kvptransformer.entities.message.Message;
import org.testng.annotations.Test;

import static me.karun.data.kvptransformer.entities.message.Message.message;
import static org.testng.Assert.*;

public class SingleRecordFileReaderTest {

  @Test
  public void shouldReadFilesCorrectly() throws Exception {
    final Message expectedMessage = message()
      .withKey("key1").andValue("value1")
      .withKey("key2").andValue("2")
      .build();
    final Message actualMessage = new SingleRecordFileReader("src/test/resources/single-record").read(); // FIXME should be paths relative to resources

    assertEquals(actualMessage, expectedMessage);
  }
}