package me.karun.data.kvptransformer.entities.mapping;

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

  public <I, O> MappingBuilder withTransform(Function<I, O> transform) {
    return new MappingBuilder(mapping, source, source).withTransform(transform);
  }
}
