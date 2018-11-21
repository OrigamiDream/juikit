package avis.juikit.internal;

@FunctionalInterface
public interface Function2d<P1, P2, V> {

    V apply(P1 p1, P2 p2);

}
