package com.github.valfed.homework16.context;

import com.github.valfed.homework16.annotation.Bean;
import com.github.valfed.homework16.exception.NoSuchBeanException;
import com.github.valfed.homework16.exception.NoUniqueBeanException;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ApplicationContextImpl implements ApplicationContext {

    private final Map<String, Object> nameToBean = new HashMap<>();

    public ApplicationContextImpl(String packageName) {
        Set<Class<?>> beanAnnotated = findClassesAnnotatedWithBean(packageName);
        init(beanAnnotated);
    }

    private Set<Class<?>> findClassesAnnotatedWithBean(String packageName) {
        Reflections reflections = new Reflections(packageName);
        return reflections.getTypesAnnotatedWith(Bean.class);
    }

    private void init(Set<Class<?>> classes) {
        try {
            for (Class<?> clazz : classes) {
                String name = clazz.getAnnotation(Bean.class).value();
                if (name.isBlank()) {
                    name = clazz.getSimpleName().substring(0, 1).toLowerCase() + clazz.getSimpleName().substring(1);
                }
                nameToBean.put(name, clazz.getConstructor().newInstance());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T getBean(Class<T> beanType) {
        List<Object> beans = nameToBean.values().stream()
                .filter(beanType::isInstance)
                .toList();
        if (beans.isEmpty()) {
            throw new NoSuchBeanException();
        }
        if (beans.size() > 1) {
            throw new NoUniqueBeanException();
        }
        return beanType.cast(beans.get(0));
    }

    @Override
    public <T> T getBean(String name, Class<T> beanType) {
        if (!nameToBean.containsKey(name)) {
            throw new NoSuchBeanException();
        }
        return beanType.cast(nameToBean.get(name));
    }

    @Override
    public <T> Map<String, T> getAllBeans(Class<T> beanType) {
        return nameToBean.entrySet().stream()
                .filter(entry -> beanType.isInstance(entry.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> beanType.cast(entry.getValue())));
    }
}
