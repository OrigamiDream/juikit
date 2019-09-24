package studio.avis.juikit.internal;

import java.util.Map;
import java.util.function.BiConsumer;

public interface TernaryConsumer<K, T1, T2> extends BiConsumer<K, Map.Entry<T1, T2>> {

    void accept(K k, T1 t1, T2 t2);

    @Override
    default void accept(K k, Map.Entry<T1, T2> t1T2Entry) {
        accept(k, t1T2Entry.getKey(), t1T2Entry.getValue());
    }
}
