package me.karun.data.kvptransformer.entities.creation;

import me.karun.data.kvptransformer.entities.message.Builder;
import me.karun.data.kvptransformer.entities.mapping.FluentJoiner;

public interface JoinerAndBuilder<J, B> extends FluentJoiner<J>, Builder<B> {
}
