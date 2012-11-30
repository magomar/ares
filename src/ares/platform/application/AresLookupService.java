package ares.platform.application;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public final class AresLookupService<T> {

    private final Map<Class<? extends T>, Set<T>> lookup = new HashMap<>();

    public <C extends T> void put(Class<C> key, C value) {
        Set<T> instances;
        if (lookup.containsKey(key)) {
            instances = lookup.get(key);
        } else {
            instances = new HashSet<>();
        }
        instances.add(value);
        lookup.put(key, instances);
    }

//    public <C extends T> void put(Class<C> key, C value) {
//        classMap.put(key, value);
//    }
    public void remove(Class<? extends T> key) {
        lookup.remove(key);
    }

    public <C extends T> C get(Class<C> key) {
        return (C) lookup.get(key);
    }

    public Set<Class<? extends T>> keySet() {
        return lookup.keySet();
    }

    public Collection<Set<T>> values() {
        return lookup.values();
    }
}
