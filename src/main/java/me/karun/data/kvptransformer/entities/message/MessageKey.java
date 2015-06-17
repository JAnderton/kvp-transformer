package me.karun.data.kvptransformer.entities.message;

public class MessageKey {
  private final MessageBuilder builder;
  private final String key;

  public MessageKey(final MessageBuilder builder, final String key) {
    this.builder = builder;
    this.key = key;
  }

  public MessageBuilder andValue(final Object value) {
    return builder.insert(key, value);
  }
}
