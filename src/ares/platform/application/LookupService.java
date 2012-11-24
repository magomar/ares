package ares.platform.application;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class LookupService<T> {
    Map<Class<? extends T>, T> classMap = new HashMap<>();

    public <C extends T, D extends T> void put(Class<C> key, D value) {
        classMap.put(key, value);
    }

//    public <C extends T> void put(Class<C> key, C value) {
//        classMap.put(key, value);
//    }

    public void remove(Class<? extends T> key) {
        classMap.remove(key);
    }
    @SuppressWarnings("unchecked")
    public <C extends T> C get(Class<C> key) {
        return (C) classMap.get(key);
    }

    public Set<Class<? extends T>> keySet() {
        return classMap.keySet();
    }

    public Collection<? extends T> values() {
        return classMap.values();
    }
}
