package avis.juikit.internal;

import avis.juikit.Juikit;

@FunctionalInterface
public interface JuikitConsumer<T> extends Consumer2d<Juikit, T> {

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