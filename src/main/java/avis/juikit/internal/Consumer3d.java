package avis.juikit.internal;

import java.util.Map;

public interface Consumer3d<K, T1, T2> extends Consumer2d<K, Map.Entry<T1, T2>> {

    void accept(K k, T1 t1, T2 t2);

    @Override
    default void accept(K k, Map.Entry<T1, T2> t1T2Entry) {
        accept(k, t1T2Entry.getKey(), t1T2Entry.getValue());
    }
}
