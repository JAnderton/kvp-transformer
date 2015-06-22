package me.karun.data.kvptransformer.entities.mapping;

import com.google.common.annotations.VisibleForTesting;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class Mapping {

  private final Map<String, String> mappings;
  private final Map<String, Function> transforms;

  @VisibleForTesting
  protected Mapping() {
    this.mappings = new HashMap<>();
    this.transforms = new HashMap<>();
  }

  public static MappingSource map() {
    return new MappingSource(new Mapping());
  }

  public MappingSource addMapping(final String source, final String target) {
    mappings.put(source, target);
    return new MappingSource(this);
  }

  public <T, U> MappingSource addTransform(String source, Function<T, U> transform) {
    transforms.put(source, transform);
    return new MappingSource(this);
  }

  public Optional<String> getMapping(final String key) {
    return Optional.ofNullable(mappings.get(key));
  }

  public Optional<Function> getTransform(final String key) {
    return Optional.ofNullable(transforms.get(key));
  }
}
