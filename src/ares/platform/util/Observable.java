package ares.platform.util;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A generic implementation of an Observable object as defined by the Observer pattern.
 * As a type parameter the interface for the Observer needs to be specified.
 *
 * @param <T> The interface which should be implemented by the observers.
 * @author Steven Jeuris
 */
public class Observable<T> {

    private T m_eventDispatcher = null;
    private final ObserverPool<T> m_observers = new ObserverPool<>();

    /**
     * Get the event dispatcher through which you can notify the observers.
     *
     * @return The event dispatcher through which you can notify the observers.
     */
    protected T getEventDispatcher() {
        // Only create one instance of the dispatcher.
        if (m_eventDispatcher == null) {
            // Use reflection to get the generic parameter type.
            Type superClass = this.getClass().getGenericSuperclass();
            if (superClass instanceof Class<?>) {
                throw new RuntimeException("Observable requires a parameter type!");
            } else {
                // Get the parameter type.
                ParameterizedType genericType = (ParameterizedType) superClass;
                Type[] typeArguments = genericType.getActualTypeArguments();

                m_eventDispatcher = m_observers.createEventDispatcher((Class<?>) typeArguments[0]);
            }

        }

        return m_eventDispatcher;
    }

    /**
     * Add an observer which will listen to the actions of this object.
     *
     * @param observer The observer which should listen to this observable.
     */
    public void addObserver(T observer) {
        m_observers.addObserver(observer);
    }

    /**
     * Remove an observer which was listening to this object.
     *
     * @param observer The observer to remove.
     * @return True, when observer was found and removed, false otherwise.
     */
    public boolean removeObserver(T observer) {
        return m_observers.removeObserver(observer);
    }

}

/**
 * The ObserverPool is a proxy which allows calls to an interface to be forwarded to a set of listeners.
 *
 * @param <T> The interface which defines which calls can be made to the listeners.
 * @author Steven Jeuris
 */
class ObserverPool<T> implements InvocationHandler {

    private List<T> m_pool = new ArrayList<>();


    /**
     * Add an observer to which the calls will be made.
     *
     * @param observer The observer to add.
     */
    public void addObserver(T observer) {
        m_pool.add(observer);
    }

    /**
     * Remove an observer to which calls where being made.
     *
     * @param observer The observer to remove.
     * @return True, when the observer was found and removed, false otherwise.
     */
    public boolean removeObserver(T observer) {
        return m_pool.remove(observer);
    }

    /**
     * Create the proxy which allows to dispatch all calls to the observers.
     *
     * @param observerClass The interface class of the observers.
     * @return The dispatcher which can be used to make calls to all added observers.
     */
    @SuppressWarnings("unchecked")
    public T createEventDispatcher(Class observerClass) {
        T dispatcher = (T) Proxy.newProxyInstance(
                observerClass.getClassLoader(),
                new Class[]{observerClass},
                this
        );

        return dispatcher;
    }

    /**
     * invoke() implementation of InvocationHandler.
     * This is called whenever a call is made to an event dispatcher.
     */
    @Override
    public Object invoke(Object object, Method method, Object[] args) throws Throwable {
        // Forward the call to all observers.
        for (T observer : m_pool) {
            method.invoke(observer, args);
        }

        // No return object available.
        return null;
    }

}