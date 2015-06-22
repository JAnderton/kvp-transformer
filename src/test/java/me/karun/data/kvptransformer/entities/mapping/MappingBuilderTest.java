package me.karun.data.kvptransformer.entities.mapping;

import javafx.util.Pair;
import org.testng.annotations.Test;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class MappingBuilderTest {

  private final BiConsumer<Mapping, Pair<String, String>> assertOnMapping = (m, p) -> {
    final Optional<String> mappedValue = m.getMapping(p.getKey());
    assertTrue(mappedValue.isPresent());
    assertEquals(mappedValue.get(), p.getValue());
  };

  private final BiConsumer<Mapping, Pair<String, Function>> assertOnTransform = (m, p) -> {
    final Optional<Function<Object, Object>> transform = m.getTransform(p.getKey());
    assertTrue(transform.isPresent());
    assertEquals(transform.get(), p.getValue());
  };

  @Test
  public void shouldBuildSingleMapping() {
    final Mapping expectedMapping = new Mapping();
    final String source = "source";
    final String target = "target";

    final Mapping actualMapping = new MappingBuilder(expectedMapping, source, target).build();

    assertEquals(actualMapping, expectedMapping);
    assertOnMapping.accept(actualMapping, new Pair<>(source, target));
  }

  @Test
  public void shouldBuildMultipleMappings() {
    final Mapping expectedMapping = new Mapping();
    final String source1 = "source1";
    final String target1 = "target1";
    final String source2 = "source2";
    final String target2 = "target2";

    final Mapping actualMapping = new MappingBuilder(expectedMapping, source1, target1).and()
      .source(source2).toTarget(target2)
      .build();

    assertEquals(actualMapping, expectedMapping);
    assertOnMapping.accept(actualMapping, new Pair<>(source1, target1));
    assertOnMapping.accept(actualMapping, new Pair<>(source2, target2));
  }

  @Test
  public void shouldBuildSingleMappingAndTransformation() {
    final Mapping expectedMapping = new Mapping();
    final String source = "source";
    final String target = "target";
    final Function<String, Integer> transform = Integer::parseInt;

    final Mapping actualMapping = new MappingBuilder(expectedMapping, source, target).withTransform(transform)
      .build();

    assertEquals(actualMapping, expectedMapping);
    assertOnMapping.accept(actualMapping, new Pair<>(source, target));
    assertOnTransform.accept(actualMapping, new Pair<>(source, transform));
  }

  @Test
  public void shouldBuildMultipleMappingAndTransformation() {
    final Mapping expectedMapping = new Mapping();
    final String source1 = "source1";
    final String target1 = "target1";
    final String source2 = "source2";
    final String target2 = "target2";
    final Function<String, Integer> transform1 = Integer::parseInt;
    final Function<Integer, String> transform2 = String::valueOf;

    final Mapping actualMapping = new MappingBuilder(expectedMapping, source1, target1).withTransform(transform1).and()
      .source(source2).toTarget(target2).withTransform(transform2)
      .build();

    assertEquals(actualMapping, expectedMapping);
    assertOnMapping.accept(actualMapping, new Pair<>(source1, target1));
    assertOnTransform.accept(actualMapping, new Pair<>(source1, transform1));
    assertOnMapping.accept(actualMapping, new Pair<>(source2, target2));
    assertOnTransform.accept(actualMapping, new Pair<>(source2, transform2));
  }
}