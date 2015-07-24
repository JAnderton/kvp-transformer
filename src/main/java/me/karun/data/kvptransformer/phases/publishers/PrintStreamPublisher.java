package me.karun.data.kvptransformer.phases.publishers;

import me.karun.data.kvptransformer.entities.message.Message;

import java.io.PrintStream;

public class PrintStreamPublisher {

  private final PrintStream stream;

  public PrintStreamPublisher() {
    this(System.out);
  }

  public PrintStreamPublisher(final PrintStream stream) {
    this.stream = stream;
  }

  public void publish(final Message message) {
    stream.println(message.toString());
  }
}
