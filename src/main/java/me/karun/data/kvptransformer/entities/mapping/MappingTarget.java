package me.karun.data.kvptransformer.entities.mapping;

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
}
