package me.karun.data.kvptransformer.entities.mapping;

import me.karun.data.kvptransformer.entities.creation.JoinerAndBuilder;

import java.util.function.Function;

public class MappingBuilder implements JoinerAndBuilder<MappingSource, Mapping> {
  private final Mapping mapping;
  private final String source;
  private final String target;

  public MappingBuilder(final Mapping mapping, final String source, final String target) {
    this.mapping = mapping;
    this.source = source;
    this.target = target;
  }

  public <T, U> JoinerAndBuilder<MappingSource, Mapping> withTransform(final Function<T, U> transform) {
    mapping.addTransform(source, transform);

    return this;
  }

  @Override
  public Mapping build() {
    mapping.addMapping(source, target);
    return mapping;
  }

  @Override
  public MappingSource and() {
    return mapping.addMapping(source, target);
  }
}
