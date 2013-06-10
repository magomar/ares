package ares.platform.util;

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
    
    public void put(Class<? extends T> key, T value) {
        classMap.put(key, value);
    }

//    public <C extends T> void put(Class<C> key, C value) {
//        classMap.put(key, value);
//    }
    public void remove(Class<? extends T> key) {
        classMap.remove(key);
    }

    public T get(Class<? extends T> key) {
        return classMap.get(key);
    }

    public Set<Class<? extends T>> keySet() {
        return classMap.keySet();
    }

    public Collection<? extends T> values() {
        return classMap.values();
    }
}
