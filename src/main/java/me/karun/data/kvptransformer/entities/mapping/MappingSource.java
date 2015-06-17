package me.karun.data.kvptransformer.entities.mapping;

public class MappingSource {
  private final Mapping mapping;

  public MappingSource(final Mapping mapping) {
    this.mapping = mapping;
  }

  public MappingTarget source(final String source) {
    return new MappingTarget(mapping, source);
  }
}
