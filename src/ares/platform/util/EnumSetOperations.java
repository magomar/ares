package ares.platform.util;

import java.util.EnumSet;
import java.util.Set;

/**
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public final class EnumSetOperations {

    public static <T extends Enum<T>> Set<T> union(Set<T> set1, Set<T> set2) {
        Set<T> result = EnumSet.copyOf(set1);
        result.addAll(set2);
        return result;
    }

    public static <T extends Enum<T>> Set<T> intersection(Set<T> set1, Set<T> set2) {
        Set<T> result = EnumSet.copyOf(set1);
        result.retainAll(set2);
        return result;
    }

    public static <T extends Enum<T>> boolean nonEmptyIntersection(Set<T> set1, Set<T> set2) {
        if (set1.isEmpty()) {
            return false;
        }
        if (set2.isEmpty()) {
            return false;
        }
        Set<T> result = EnumSet.copyOf(set1);
        result.retainAll(set2);
        return !result.isEmpty();
    }
}
