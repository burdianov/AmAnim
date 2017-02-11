package com.crackncrunch.amanim.di;

import android.content.Context;
import android.support.annotation.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DaggerService {
    public static final String SERVICE_NAME = "DAGGER_SERVICE";
    private static Map<Class, Object> sComponentMap = new HashMap<>();

    @SuppressWarnings("uncheked")
    public static <T> T createComponent(Class<T> componentClass, Class daggerClass, Object... dependencies) {
        Object component = sComponentMap.get(componentClass);
        if (component != null) {
            return (T) component;
        } else {
            try {
                Object builder = daggerClass.getMethod("builder").invoke(null);
                for (Method method : builder.getClass().getDeclaredMethods()) {
                    Class<?>[] params = method.getParameterTypes();
                    if (params.length == 1) {
                        Class<?> dependencyClass = params[0];
                        for (Object dependency : dependencies) {
                            if (dependencyClass.isAssignableFrom(dependency.getClass())) {
                                method.invoke(builder, dependency);
                                break;
                            }
                        }
                    }
                }
                component = builder.getClass().getMethod("build").invoke(builder);
                if (component != null) {
                    registerComponent(componentClass, component);
                    return (T) component;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T getDaggerComponent(Context context) {
        //noinspection ResourceType
        return (T) context.getSystemService(SERVICE_NAME);
    }

    public static void registerComponent(Class componentClass, Object
            dagggerComponent) {
        sComponentMap.put(componentClass, dagggerComponent);
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public static <T> T getComponent(Class<T> componentClass) {
        Object component = sComponentMap.get(componentClass);

        return (T) component;
    }

    public static void unregisterScope(Class<? extends Annotation>
                                               scopeAnnotation) {
        Iterator<Map.Entry<Class, Object>> iterator = sComponentMap.entrySet()
                .iterator();
        while (iterator.hasNext()) {
            Map.Entry<Class, Object> entry = iterator.next();
            if (entry.getKey().isAnnotationPresent(scopeAnnotation)) {
                iterator.remove();
            }
        }
    }
}
