package ru.sample.network.complex.utils.wrappers.fields;

import ru.sample.network.complex.utils.wrappers.Ctx;

public abstract class Field<T> {

    private final String name;
    private final T defaultValue;

    Field(String name, T defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
    }

    @SuppressWarnings("unused")
    public final T get(Ctx ctx) {
        return getNullable(ctx.getAttribute(name));
    }

    public final T getOrDefault(Ctx ctx) {
        return getOrDefault(ctx.getAttribute(name));
    }

    protected abstract T get(String value);

    private T getNullable(String value) {
        if (value == null) {
            return null;
        } else {
            return get(value);
        }
    }

    private T getOrDefault(String value) {
        T t = getNullable(value);
        return t != null ? t : defaultValue;
    }
}
