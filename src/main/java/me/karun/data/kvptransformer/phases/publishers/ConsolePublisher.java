package me.karun.data.kvptransformer.phases.publishers;

import me.karun.data.kvptransformer.entities.message.Message;

import java.io.PrintStream;

public class ConsolePublisher {

  private final PrintStream stream;

  public ConsolePublisher() {
    this(System.out);
  }

  public ConsolePublisher(final PrintStream stream) {
    this.stream = stream;
  }

  public void publish(final Message message) {
    stream.println(message.toString());
  }
}
