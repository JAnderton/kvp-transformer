package me.karun.data.kvptransformer.process;

import me.karun.data.kvptransformer.entities.message.Message;
import me.karun.data.kvptransformer.phases.parsers.DelimitedLineParser;
import me.karun.data.kvptransformer.phases.publishers.PrintStreamPublisher;
import me.karun.data.kvptransformer.phases.readers.SingleRecordFileReader;
import me.karun.data.kvptransformer.phases.transformers.TransformationEngine;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static me.karun.data.kvptransformer.entities.message.Message.message;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ChainTest {

  @Test
  public void shouldExecuteChainInvokingAllDependencies() throws Exception {
    final List<String> lines = Arrays.asList("a=1", "b=2");
    final Message input = message()
      .withKey("a").andValue(1)
      .withKey("b").andValue(2)
      .build();
    final Message output = message()
      .withKey("A").andValue(100)
      .withKey("B").andValue(200)
      .build();

    final SingleRecordFileReader reader = mock(SingleRecordFileReader.class);
    when(reader.read()).thenReturn(lines);
    final DelimitedLineParser parser = mock(DelimitedLineParser.class);
    when(parser.parse(lines)).thenReturn(input);
    final TransformationEngine engine = mock(TransformationEngine.class);
    when(engine.process(input)).thenReturn(output);
    final PrintStreamPublisher publisher = mock(PrintStreamPublisher.class);

    new Chain(reader, parser, engine, publisher).execute();

    verify(reader).read();
    verify(parser).parse(lines);
    verify(engine).process(input);
    verify(publisher).publish(output);
  }
}