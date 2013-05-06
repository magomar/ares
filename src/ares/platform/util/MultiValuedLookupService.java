package ares.platform.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public final class MultiValuedLookupService<T> {

    private final Map<Class<? extends T>, Set<T>> lookup = new HashMap<>();

    public void put(Class<? extends T> key, T value) {
        Set<T> instances;
        if (lookup.containsKey(key)) {
            instances = lookup.get(key);
        } else {
            instances = new HashSet<>();
        }
        instances.add(value);
        lookup.put(key, instances);
    }

    public void remove(Class<? extends T> key) {
        lookup.remove(key);
    }

    public Set<T> get(Class<? extends T> key) {
        return lookup.get(key);
    }

    public Set<Class<? extends T>> keySet() {
        return lookup.keySet();
    }

    public Collection<Set<T>> values() {
        return lookup.values();
    }
}
