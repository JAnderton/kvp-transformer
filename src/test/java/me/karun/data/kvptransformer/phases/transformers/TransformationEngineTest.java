package me.karun.data.kvptransformer.phases.transformers;

import me.karun.data.kvptransformer.entities.mapping.Mapping;
import me.karun.data.kvptransformer.entities.message.Message;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Optional;
import java.util.function.Function;

import static me.karun.data.kvptransformer.entities.mapping.Mapping.map;
import static me.karun.data.kvptransformer.entities.message.Message.message;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TransformationEngineTest {

  private String mappingSource;
  private int expectedValue;
  private String mappingTarget;
  private Message input;
  private int transformFactor;
  private Function<Object, Integer> transform;

  @BeforeTest
  public void setupTest() {
    mappingSource = "application.version";
    mappingTarget = "APP.VERSION-ID";

    expectedValue = 37;
    input = message()
      .withKey(mappingSource).andValue(expectedValue)
      .build();
    transformFactor = 23;
    transform = o -> Integer.parseInt(o.toString()) * transformFactor;
  }

  @Test
  public void shouldProcessMessageWithNoMapping() {
    final Message input = message().build();
    final TransformationEngine transformationEngine = new TransformationEngine();

    final Message output = transformationEngine.process(input);

    assertEquals(output, input);
  }

  @Test
  public void shouldProcessOneToOneMapping() {
    final Mapping mapping = map()
      .source(mappingSource).toTarget(mappingTarget)
      .build();
    final TransformationEngine transformationEngine = new TransformationEngine(mapping);

    final Message output = transformationEngine.process(input);

    final Optional<Object> actualValue = output.getValue(mappingTarget);
    assertTrue(actualValue.isPresent());
    assertEquals(actualValue.get(), expectedValue);
  }

  @Test
  public void shouldProcessOneToOneTransformation() {
    final Mapping mapping = map()
      .source(mappingSource).toTarget(mappingTarget).withTransform(transform)
      .build();
    final TransformationEngine transformationEngine = new TransformationEngine(mapping);

    final Message output = transformationEngine.process(input);

    final Optional<Object> actualValue = output.getValue(mappingTarget);
    assertTrue(actualValue.isPresent());
    assertEquals(actualValue.get(), transformFactor * expectedValue);
  }

  @Test
  public void shouldNotProcessExtraMappingsAndTransformation() {
    final Mapping mapping = map()
      .source(mappingSource).toTarget(mappingTarget).withTransform(transform).and()
      .source("randomText").toTarget("somethingElse").withTransform(o -> "Value: " + o.toString())
      .build();
    final TransformationEngine transformationEngine = new TransformationEngine(mapping);

    final Message output = transformationEngine.process(input);

    final Optional<Object> value = output.getValue(mappingTarget);
    assertTrue(value.isPresent());
    assertEquals(value.get(), transformFactor * expectedValue);
  }
}