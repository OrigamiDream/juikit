package studio.avis.juikit.internal;

import studio.avis.juikit.Juikit;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface JuikitConsumer<T> extends BiConsumer<Juikit, T> {

    static <T> EmptyJuikitConsumer<T> empty() {
        return (juikit, t) -> { };
    }

    default void acceptOptional(Juikit juikit, T t) {
        if(!(this instanceof EmptyJuikitConsumer)) {
            accept(juikit, t);
        }
    }
}
interface EmptyJuikitConsumer<T> extends JuikitConsumer<T> {
}