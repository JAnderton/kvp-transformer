package me.karun.data.kvptransformer.phases.parsers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.internal.LazilyParsedNumber;
import javafx.util.Pair;
import me.karun.data.kvptransformer.entities.message.Message;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


public class JsonRecordParser {

  private final Function<LazilyParsedNumber, Number> lazyNumberToNumber = ln ->
    ln.toString().contains("\\.")
      ? Double.valueOf(ln.doubleValue())
      : Long.valueOf(ln.longValue());

  private final Function<Number, Number> forceNumberEvaluation = n ->
    n instanceof LazilyParsedNumber
      ? lazyNumberToNumber.apply((LazilyParsedNumber) n)
      : n;

  private final Function<JsonElement, Object> elementToObject = element -> {
    final JsonPrimitive primitive = element.getAsJsonPrimitive();
    if (primitive.isNumber()) {
      final Number n = primitive.getAsNumber();
//      return  forceNumberEvaluation.apply(n); // TODO: this should be the actual return here
      return n.doubleValue();
    } else if (primitive.isString()) {
      return primitive.getAsString();
    }
    throw new IllegalArgumentException("JSON elements should only contain primitives");
  };

  private final Function<JsonObject, Message> jsonToMessage = jObject -> jObject.entrySet().stream()
    .map(e ->
      new Pair<>(e.getKey(), elementToObject.apply(e.getValue())))
    .collect(me.karun.data.kvptransformer.utils.Collectors.asMessage())
    .build();

  public List<Message> parse(final List<String> lines) {
    final List<Message> messages = lines.stream()
      .map(s -> new JsonParser().parse(s).getAsJsonObject())
      .map(jsonToMessage)
      .collect(Collectors.toList());

    return messages;
  }
}
