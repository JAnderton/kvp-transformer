package me.karun.data.kvptransformer.entities.mapping;

import javafx.util.Pair;
import org.testng.annotations.Test;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static org.testng.Assert.*;

public class MappingTargetTest {


  private final BiConsumer<Mapping, Pair<String, String>> assertOnMapping = (m, p) -> {
    final Optional<String> mappedValue = m.getMapping(p.getKey());
    assertTrue(mappedValue.isPresent());
    assertEquals(mappedValue.get(), p.getValue());
  };

  private final BiConsumer<Mapping, Pair<String, Function>> assertOnTransform = (m, p) -> {
    final Optional<Function> transform = m.getTransform(p.getKey());
    assertTrue(transform.isPresent());
    assertEquals(transform.get(), p.getValue());
  };

  @Test
  public void shouldProduceCorrectBuilderWithTarget() {
    final Mapping expectedMapping = new Mapping();
    final String source = "source";
    final String target = "target";

    final Mapping actualMapping = new MappingTarget(expectedMapping, source).toTarget(target).build();

    assertEquals(actualMapping, expectedMapping);
    assertOnMapping.accept(actualMapping, new Pair<>(source, target));
  }

  @Test
  public void shouldProduceCorrectBuilderWithTransform() {
    final Mapping expectedMapping = new Mapping();
    final String source = "source";
    final Function<String, Integer> transform = Integer::parseInt;

    final Mapping actualMapping = new MappingTarget(expectedMapping, source).withTransform(transform).build();

    assertEquals(actualMapping, expectedMapping);
    assertOnMapping.accept(actualMapping, new Pair<>(source, source));
    assertOnTransform.accept(actualMapping, new Pair<>(source, transform));
  }
}