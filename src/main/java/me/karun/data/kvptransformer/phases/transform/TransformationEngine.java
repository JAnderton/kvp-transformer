package me.karun.data.kvptransformer.phases.transform;

import javafx.util.Pair;
import me.karun.data.kvptransformer.entities.mapping.Mapping;
import me.karun.data.kvptransformer.entities.message.Message;
import me.karun.data.kvptransformer.entities.message.MessageBuilder;

import java.util.Optional;

import static me.karun.data.kvptransformer.entities.message.Message.message;

public class TransformationEngine {

  private final Optional<Mapping> mapping;

  public TransformationEngine() {
    this.mapping = Optional.empty();
  }

  public TransformationEngine(final Mapping mapping) {
    this.mapping = Optional.ofNullable(mapping);
  }

  public Message  process(final Message input) {
    if (mapping.isPresent()) {
      final MessageBuilder outputBuilder = message();

      input.getQualifiedKeys().stream()
        .map(k -> new Pair<>(k, input.getValue(k).get()))
        .map(p -> new Pair<>(
          mapping.get().getMapping(p.getKey()).orElse(p.getKey()),
          mapping.get().getTransform(p.getKey()).orElse(o -> o)
            .apply(p.getValue())))
        .forEach(p -> outputBuilder.withKey(p.getKey()).andValue(p.getValue()));

      return outputBuilder.build();
    }
    return input;
  }
}
