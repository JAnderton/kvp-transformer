package me.karun.data.kvptransformer.phases.publishers;

import me.karun.data.kvptransformer.entities.message.Message;
import org.testng.annotations.Test;

import java.io.PrintStream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ConsolePublisherTest {

  @Test
  public void testPublish() throws Exception {
    final Message message = mock(Message.class);
    final PrintStream stream = mock(PrintStream.class);
    final ConsolePublisher publisher = new ConsolePublisher(stream);

    when(message.toString()).thenReturn("{key1=1,key2=2}");
    publisher.publish(message);

    verify(stream).println("{key1=1,key2=2}");
  }
}