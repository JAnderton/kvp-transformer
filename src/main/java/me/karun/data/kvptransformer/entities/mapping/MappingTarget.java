package me.karun.data.kvptransformer.entities.mapping;

import me.karun.data.kvptransformer.entities.creation.JoinerAndBuilder;

import java.util.function.Function;

public class MappingTarget {
  private final Mapping mapping;
  private final String source;

  public MappingTarget(final Mapping mapping, final String source) {
    this.mapping = mapping;
    this.source = source;
  }

  public MappingBuilder toTarget(final String target) {
    return new MappingBuilder(mapping, source, target);
  }

  public <I, O> JoinerAndBuilder<MappingSource, Mapping> withTransform(Function<I, O> transform) {
    return new MappingBuilder(mapping, source, source).withTransform(transform);
  }
}
